package com.lp.student.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.ClassActualSchedule;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserInterests;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("studentDao")
public class StudentDAOImpl extends CustomHibernateDaoSupport implements StudentDAO {

	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private HttpSession httpSession;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public boolean SaveStudent(Student stud) {
		try{
			super.saveOrUpdate(stud);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}
	@Override
	public long getGradeClassId(long gradeId,long classId){
		String query = "select grade_class_id from grade_classes where class_id="
				+ classId+" and grade_id="+gradeId;
		long grade_class_id=0;
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			grade_class_id=rs.getInt(1);
		}
		return grade_class_id;
	}
	@Override
	public boolean SaveUserInterests(UserInterests userInt) {
		try{
			super.saveOrUpdate(userInt);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public List<Student> saveBulkStudents(List<Student> newStuList) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		long i = 0;
		for ( Student stu : newStuList ) {
		    session.save(stu);
		    i++;
		    if ( i % 20 == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		        session.flush();
		        session.clear();
		    }
		}
		   
		tx.commit();
		session.close();
		return newStuList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassActualSchedule> getStudentClassesForRegistration(long gradeId){
		 List<ClassActualSchedule> 	casLt=(List<ClassActualSchedule>) getHibernateTemplate().find("from ClassActualSchedule where"
					+" classStatus.section.gradeClasses.grade.gradeId="+gradeId					
					+" and classStatus.teacher.userRegistration.status='"+WebKeys.ACTIVE+"'");
		 return casLt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherSubjects> getTeachersForRegistration(long gradeId){
		List<TeacherSubjects> teacherSubLt = (List<TeacherSubjects>) getHibernateTemplate().find("from TeacherSubjects where grade.gradeId=" + gradeId);
	return teacherSubLt;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassActualSchedule> getSectionsForRegistration(long teacherId,long studentId,long gradeClassId) {
		List<ClassActualSchedule> casLt = new ArrayList<ClassActualSchedule>();
		List<ClassActualSchedule> casLtWithoutFiilter =(List<ClassActualSchedule>) getHibernateTemplate().find("from ClassActualSchedule where classStatus.teacher.teacherId="+teacherId+"and classStatus.availStatus='"+WebKeys.AVAILABLE+"' and classStatus.section.gradeClasses.gradeClassId="+gradeClassId+" order by classStatus.section.sectionId");
		List<Long> csIds = new ArrayList<Long>();
		String query_for_check_student_registration = "select cs_id from register_for_class where student_id="+studentId+" AND  grade_class_id="+gradeClassId+" AND ((status='"+WebKeys.ACCEPTED+"' AND class_status='"+WebKeys.ALIVE+"') OR status='"+WebKeys.DECLINED+"')";
		SqlRowSet rs_for_check_student_registration = jdbcTemplate.queryForRowSet(query_for_check_student_registration);
		while (rs_for_check_student_registration.next()) {
			csIds.add(rs_for_check_student_registration.getLong(1));
		}
		casLt.addAll(casLtWithoutFiilter);
		return casLt;
	}
	
	@Override
	public String setStatusForClassRegistration(long studentId,long sectionId,long csId,String status,String classStatus,long gradeLevelId,long gradeClassId){
		int rowCount = 0;
		String success = "";
		String existedStatus = "";
		String existedClassStatus = "";
		String query_for_check_student_registration = "select status,class_status from register_for_class where cs_id="+csId+" AND student_id="+studentId+" AND (status='"+WebKeys.ACCEPTED+"' OR status='"+WebKeys.DECLINED+"') AND (class_status='"+WebKeys.ALIVE+"' OR class_status='"+WebKeys.EXPIRED+"')";
		SqlRowSet rs_for_check_student_registration = jdbcTemplate.queryForRowSet(query_for_check_student_registration);
		 if (rs_for_check_student_registration.next()) {
			 rowCount++;
			 existedStatus =  rs_for_check_student_registration.getString(1);
			 existedClassStatus = rs_for_check_student_registration.getString(2);
		 }
		try{
			 if (rowCount == 0) {
				 int count = 0;
				 String query = "select status from register_for_class where cs_id="+csId+" AND student_id="+studentId+" AND class_status='"+classStatus+"' AND status='"+WebKeys.WAITING+"'";
				 SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
				 if (rs.next()) {
					 count++;
				 }
				 if(count > 0){
					 if(status.equalsIgnoreCase(WebKeys.WAITING)){
						 success = "Request under process !!!"; 
					 }else if(status.equalsIgnoreCase(WebKeys.CANCELLED)){
						 jdbcTemplate.update("delete from register_for_class where cs_Id="+csId+" and student_id="+studentId);
						 success = "Request cancelled successfully !!!";
					 }
				 }else{
					 if(status.equalsIgnoreCase(WebKeys.WAITING)){
						 int cnt = 0;
						 String query1 = "select status from register_for_class where student_id="+studentId+" AND class_status='"+classStatus+"' AND status='"+WebKeys.WAITING+"' AND grade_class_id="+gradeClassId;
						 SqlRowSet rs1 = jdbcTemplate.queryForRowSet(query1);
						 if (rs1.next()) {
							 cnt++;
						 }
						 if(cnt > 0){
							 jdbcTemplate.update("delete from register_for_class where student_id="+studentId+" AND class_status='"+classStatus+"' AND status='"+WebKeys.WAITING+"' AND grade_class_id="+gradeClassId); 
						
							 Date currentDate = new Date();
							 String insert_query = "insert into register_for_class (student_id, section_id, cs_id, status, class_status, grade_level_id, grade_class_id,create_date,desktop_status) values(?,?,?,?,?,?,?,?,?)";
							 jdbcTemplate.update(insert_query,studentId,sectionId, csId, status, classStatus, gradeLevelId, gradeClassId,currentDate,WebKeys.LP_STATUS_INACTIVE);
							 success = "Request sent successfully !!!";
						 }else{
							 Date currentDate = new Date();
							 String insert_query = "insert into register_for_class (student_id, section_id, cs_id, status, class_status, grade_level_id, grade_class_id,create_date,desktop_status) values(?,?,?,?,?,?,?,?,?)";
							 jdbcTemplate.update(insert_query,studentId,sectionId, csId, status, classStatus, gradeLevelId, gradeClassId,currentDate,WebKeys.LP_STATUS_INACTIVE);
							 success = "Request sent successfully !!!";
						 }
					 }else if(status.equalsIgnoreCase(WebKeys.CANCELLED)){
						 success = "No request found for this class !!!";
					 }
				 }
			 }else{
				 if(existedStatus.equalsIgnoreCase(WebKeys.ACCEPTED) && status.equalsIgnoreCase(WebKeys.CANCELLED)){
					 success = "Unabled to cancel your request..Request Accepted!!!"; 
				 }else if(existedStatus.equalsIgnoreCase(WebKeys.ACCEPTED)){
					 String update_query = "";
						if(existedClassStatus.equalsIgnoreCase(WebKeys.EXPIRED)){
							update_query = "update register_for_class set status='"+WebKeys.WAITING+"',class_status='"+WebKeys.ALIVE+"', desktop_status ='"+WebKeys.LP_STATUS_INACTIVE+"' where student_id="+studentId+" and grade_class_id="+gradeClassId+" and class_status='"+WebKeys.EXPIRED+"'";
							jdbcTemplate.update(update_query);
							success = "Request sent successfully !!!";
						}else{
							success = "Request Accepted!!!"; 
						}
				 }else if(existedStatus.equalsIgnoreCase(WebKeys.DECLINED)&& status.equalsIgnoreCase(WebKeys.CANCELLED)){
					 success = "Unabled to cancel your request..Request declined !!!"; 
				 }else if(existedStatus.equalsIgnoreCase(WebKeys.DECLINED)){
					 success = "Request declined !!!"; 
				 }
				 	
			 }
		} catch (Exception e) {
	        e.printStackTrace();
	        success = "Got Error..unabled to process !!!";
		}
	return success;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getAllStudents(){
		List<Student> studentList = (List<Student>) getHibernateTemplate().find("FROM Student where gradeStatus='active'");
		return studentList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Student getStudent(long regId) throws DataException {
		List<Student> student = null;
		try {
			student = (List<Student>) getHibernateTemplate().find("from Student where userRegistration.regId="+regId );
		} catch (DataAccessException e) {
			logger.error("Error in getStudent() of StudentDAOImpl"+ e);
			e.printStackTrace();
			throw new DataException("Unexpected System error....");
		}
		if(student.size() > 0 )
			return student.get(0);
		else
			throw new DataException("Student not found in the database");		
	}
	
	@Override
	public long getSectionId(long studentId,long gradeClassId){		
		String query = "select section_id from register_for_class where student_id="+studentId+" AND class_status='"+WebKeys.ACCEPTED+"' AND status='"+WebKeys.ALIVE+"' AND grade_class_id="+gradeClassId;
		long section_id=0;
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			section_id=rs.getInt(1);
		}
		return section_id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getStudentClasses(Student student) {
		List<RegisterForClass> registerForClasses = null;
		try{
			registerForClasses = (List<RegisterForClass>) getHibernateTemplate().find("From RegisterForClass where student.studentId="+student.getStudentId()+" and classStatus_1='"+WebKeys.ALIVE+"' and status='"+WebKeys.ACCEPTED+"' ");
		}
		catch(HibernateException e){
			e.printStackTrace();
		}
		return registerForClasses;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public String sendClassRequest(long studentId,long csId,long gradeClassId, long teacherId){
		String message =null;
		try{
			RegisterForClass registerForClass = new RegisterForClass();
			List<RegisterForClass> reClasses = (List<RegisterForClass>) getHibernateTemplate().find("From RegisterForClass where student.studentId="+studentId+" and classStatus.csId="+csId);
			if(!reClasses.isEmpty()){
				registerForClass = reClasses.get(0);
			}
			if(!reClasses.isEmpty()){
				if(registerForClass.getStatus().equals(WebKeys.LP_STATUS_NEW)){
					Date currentDate = new Date();
					String update_query = "update register_for_class set cs_id=?, status=?, teacher_id=? where student_id=? and grade_class_id=?";
					jdbcTemplate.update(update_query,csId, WebKeys.WAITING, teacherId, studentId, gradeClassId);
					message = "Request sent successfully !!!";
				}
				else{
				message = "Request already sent";
				}
			}
			else{
				Date currentDate = new Date();
				String insert_query = "insert into register_for_class (student_id, cs_id, status, class_status, grade_class_id,create_date,teacher_id) values(?,?,?,?,?,?,?)";
				 jdbcTemplate.update(insert_query,studentId, csId, WebKeys.WAITING, WebKeys.ALIVE, gradeClassId, currentDate,teacherId);
				 message = "Request sent successfully !!!";
			}
		} catch (Exception e) {
	        e.printStackTrace();
		}
	return message;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Student> getStudentsByHomeRoom(long homeroomId) {

		List<Student> list1 = new ArrayList<Student>();
		try {

			list1 = (List<Student>) getHibernateTemplate().find(
					"from Student where homeroomClasses.homeroomId="+homeroomId);

		} catch (DataAccessException e) {
			logger.error("Error in getStudentsByHomeRoom() of StudentDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getActiveStudents(){
		List<Student> studentList = (List<Student>) getHibernateTemplate().find("FROM Student where userRegistration.status='"+WebKeys.ACTIVE+"'");
		return studentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getStudentGradeByYear(AcademicYear academicYear) {
		Student student = (Student) httpSession.getAttribute(WebKeys.STUDENT_OBJECT);
		List<RegisterForClass> registerForClass = Collections.emptyList();
		try{
			if(!academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				registerForClass = (List<RegisterForClass>) getHibernateTemplate().find("FROM RegisterForClass "
						+ "where student="+student.getStudentId()+""
								+ " and (classStatus.startDate >='"+academicYear.getStartDate() +"' "
										+ "and classStatus.endDate <='"+ academicYear.getEndDate()+"')" 
											+ " and classStatus_1='"+WebKeys.EXPIRED+"' and status='"+WebKeys.ACCEPTED+"'");
			}else{
				registerForClass = (List<RegisterForClass>) getHibernateTemplate().find("FROM RegisterForClass "
						+ "where student="+student.getStudentId()
											+ " and classStatus_1='"+WebKeys.ALIVE+"' and status='"+WebKeys.ACCEPTED+"'");
			}
		}catch(Exception e){
			logger.error("Error in getStudentGradeByYear() of StudentDAOImpl"+ e);
		}
		return registerForClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getChildGradeByYear(AcademicYear academicYear, Student student) {
		List<RegisterForClass> registerForClass = Collections.emptyList();
		try{
			if(!academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				registerForClass = (List<RegisterForClass>) getHibernateTemplate().find("FROM RegisterForClass "
						+ "where student="+student.getStudentId()+""
								+ " and (classStatus.startDate >='"+academicYear.getStartDate() +"' "
										+ "and classStatus.endDate <='"+ academicYear.getEndDate()+"')" 
											+ " and classStatus_1='"+WebKeys.EXPIRED+"' and status='"+WebKeys.ACCEPTED+"'");
			}else{
				registerForClass = (List<RegisterForClass>) getHibernateTemplate().find("FROM RegisterForClass "
						+ "where student="+student.getStudentId()
											+ " and classStatus_1='"+WebKeys.ALIVE+"' and status='"+WebKeys.ACCEPTED+"'");
			}
		}catch(Exception e){
			logger.error("Error in getChildGradeByYear() of StudentDAOImpl"+ e);
		}
		return registerForClass;
	}
	
	@Override
	public List<Student> updateStudentGradeAndSchool(List<Student> newStuList) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		long i = 0;
		for ( Student stu : newStuList ) {
		    session.saveOrUpdate(stu.getUserRegistration());
			session.saveOrUpdate(stu);
		    i++;
		    if ( i % 20 == 0 ) { //20, same as the JDBC batch size
		        //flush a batch of inserts and release memory:
		        session.flush();
		        session.clear();
		    }
		}		   
		tx.commit();
		session.close();
		return newStuList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentClass> getStudentScheduledClasses(Student student) {
		List<RegisterForClass> studentClasses = null;
		List<StudentClass> LstStudentClasses=new ArrayList<StudentClass>();
		try{
			studentClasses = (List<RegisterForClass>) getHibernateTemplate().find("From RegisterForClass where student.studentId="+student.getStudentId()+" and classStatus_1='"+WebKeys.ALIVE+"' and status='"+WebKeys.ACCEPTED+"' ");
		   for(RegisterForClass stuCls:studentClasses){
			   LstStudentClasses.add(stuCls.getGradeClasses().getStudentClass());
			   }
		}
		catch(HibernateException e){
			e.printStackTrace();
		}
		return LstStudentClasses;
	}

	
}
