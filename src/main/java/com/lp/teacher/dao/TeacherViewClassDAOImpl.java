package com.lp.teacher.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.Attendance;
import com.lp.model.ClassActualSchedule;
import com.lp.model.Grade;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("teacherViewClassDAO")
public class TeacherViewClassDAOImpl extends CustomHibernateDaoSupport  implements TeacherViewClassDAO {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AndroidPushNotificationsService apns;

	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Attendance> getStudentsAttendance(long csId, String dateToUpdate) {
		List<Attendance> attendance = new ArrayList<Attendance>();
		attendance =(List<Attendance>) getHibernateTemplate().find("from Attendance where classStatus.csId="+csId+" and date='"+dateToUpdate+"' order by student.userRegistration.lastName asc"); 
		return attendance;
	}

	@Override
	public boolean saveAttendance(List<Long> studentId,List<String> status, Date date,long csId,long teacherId) {
		//long csId = 0;
		boolean success = true;
		DateFormat db_formatter = new SimpleDateFormat("yyyy-MM-dd");
		String updateDate = db_formatter.format(date);
		if(csId > 0){
			for(int cnt=0; cnt<studentId.size(); cnt++){
				long attendanceId = 0;
				String query_for_checkAttendance = "select attendance_id from attendance where cs_id="+csId+" AND student_id="+studentId.get(cnt)+" AND date='"+updateDate+"'";
				SqlRowSet rs_for_checkAttendance = jdbcTemplate.queryForRowSet(query_for_checkAttendance);
				 if (rs_for_checkAttendance.next()) {
					 attendanceId = rs_for_checkAttendance.getLong(1);
			}
			try{
				 if (attendanceId == 0) {
					String insert_query = "insert into attendance (cs_id, student_id, date, status, create_date) values(?,?,?,?,?)";
					jdbcTemplate.update(insert_query, csId, studentId.get(cnt),date, status.get(cnt), date);
					if(status.get(cnt).equalsIgnoreCase("Absent") || status.get(cnt).equalsIgnoreCase("ExcusedAbsent") || status.get(cnt).equalsIgnoreCase("Tardy") || status.get(cnt).equalsIgnoreCase("ExcusedTardy")){
						apns.sendAttendanceNotification("Attendance", studentId.get(cnt), status.get(cnt));
					}
					success = true;
				 }else{
				    String update_query = "update attendance set status='"+status.get(cnt)+"', read_status='"+WebKeys.READ_STATUS_NO+"'  where attendance_id="+attendanceId;
					jdbcTemplate.update(update_query);
					if(status.get(cnt).equalsIgnoreCase("Absent") || status.get(cnt).equalsIgnoreCase("ExcusedAbsent") || status.get(cnt).equalsIgnoreCase("Tardy") || status.get(cnt).equalsIgnoreCase("ExcusedTardy")){
						apns.sendAttendanceNotification("Attendance", studentId.get(cnt), status.get(cnt));
					}
					success = true;
				 }
			} catch (Exception e) {
		        e.printStackTrace();
		        success = false;
			}
		
			}
		}else{
			success = false;
		}
		return success;
	}
	public boolean checkTodaysAttendance(long csId,Date date){
		boolean attendanceTaken = false;
		DateFormat db_formatter = new SimpleDateFormat("yyyy-MM-dd");
		String checkDate = db_formatter.format(date);
		String query_for_checkAttendance = "select attendance_id from attendance where cs_id="+csId+" AND date='"+checkDate+"'";
		SqlRowSet rs_for_checkAttendance = jdbcTemplate.queryForRowSet(query_for_checkAttendance);
		 if (rs_for_checkAttendance.next()) {
			 attendanceTaken = true;
		 }

		return attendanceTaken;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Student> getStudentsOfRegistration(long csId, long gradeClassId) {
		List<Student> studentLt = new ArrayList<Student>();
		 Query query= sessionFactory.getCurrentSession().createQuery("from RegisterForClass where classStatus.csId=:csId  and gradeClasses.gradeClassId=:gradeClassId and status=:status");
		 query.setParameter("csId", csId);
		 query.setParameter("gradeClassId", gradeClassId);
		 query.setParameter("status", WebKeys.WAITING);
		 List<RegisterForClass> rfc = (List<RegisterForClass>)query.list();
		 for (RegisterForClass registerForClass : rfc) {
			 studentLt.add(registerForClass.getStudent());
		}
		return studentLt;
	}

	@Override
	public boolean setStudentAction(long studentId, long csId, long gradeClassId,long gradeLevelId,String status) {
		boolean success = false;
		try{
			int count = 0;
			@SuppressWarnings("unused")
			String existedStatus = "";
			@SuppressWarnings("unused")
			String existedClassStatus = "";
			 String query = "select status,class_status from register_for_class where student_id="+studentId+" AND class_status='"+WebKeys.ALIVE+"'  AND (status='"+WebKeys.DECLINED+"' OR status='"+WebKeys.ACCEPTED+"')";
			 SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			 if (rs.next()) {
				 count++;
				 existedStatus =  rs.getString(1);
				 existedClassStatus = rs.getString(2);
			 }
			if(csId > 0 && count > 0){
				if(status.equalsIgnoreCase(WebKeys.ACCEPTED)){
					String update_query1 = "update register_for_class set class_status='"+WebKeys.EXPIRED+"' where student_id="+studentId+" and grade_class_id="+gradeClassId+" and class_status='"+WebKeys.ALIVE+"' and status='"+WebKeys.ACCEPTED+"'";
					jdbcTemplate.update(update_query1);
					
					String update_query2 = "update register_for_class set status='"+status+"', desktop_status='"+WebKeys.LP_STATUS_ACTIVE+"' where student_id="+studentId+" and cs_id="+csId+" and grade_class_id="+gradeClassId+" and grade_level_id="+gradeLevelId+" and class_status='"+WebKeys.ALIVE+"'";
					jdbcTemplate.update(update_query2);
  			  		success = true;
				}else{
					String update_query2 = "update register_for_class set status='"+status+"', desktop_status='"+WebKeys.LP_STATUS_ACTIVE+"',class_status='"+WebKeys.EXPIRED+"' where student_id="+studentId+" and cs_id="+csId+" and grade_class_id="+gradeClassId+" and grade_level_id="+gradeLevelId+" and class_status='"+WebKeys.ALIVE+"'";
					jdbcTemplate.update(update_query2);
  			  		success = true;
				}
				
			}else{
				String update_student_status_query = "update register_for_class set status='"+status+"', desktop_status='"+WebKeys.LP_STATUS_ACTIVE+"' where student_id="+studentId+" and cs_id="+csId+" and grade_class_id="+gradeClassId+" and grade_level_id="+gradeLevelId+" and class_status='"+WebKeys.ALIVE+"'";
				jdbcTemplate.update(update_student_status_query);
			  	success = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Grade> getTeacherGradesForRequest(long teacherId){
		List<Grade> gradeLt = new ArrayList<Grade>();	
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery("SELECT ts.grade from TeacherSubjects ts where ts.teacher.teacherId="+teacherId+" order by ts.grade.masterGrades.masterGradesId");
		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		gradeLt =  query.list();
		return gradeLt;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<StudentClass> getTeacherClassesForRequest(long teacherId,long gradeId){
		List<StudentClass> studentClassLt = new ArrayList<StudentClass>();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery("SELECT ts.studentClass from TeacherSubjects ts where ts.teacher.teacherId="+teacherId+" and ts.grade.gradeId="+gradeId+" order by ts.studentClass.classId");
		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		studentClassLt =  query.list();
		return studentClassLt;
	}

	@Override
	public String sendRequestForAClass(long teacherId, long gradeId,long classId) {
		String requested_class_status = "";
		String query_for_requested_class_status = "select requested_class_status from teacher_subjects where teacher_id="+teacherId+" and grade_id="+gradeId+" and class_id="+classId;
		SqlRowSet rs_for_requested_class_status = jdbcTemplate.queryForRowSet(query_for_requested_class_status);
		 if (rs_for_requested_class_status.next()) {
			 if(rs_for_requested_class_status.getString(1) ==  null){
				 requested_class_status = "";
			 }else{
				 requested_class_status = rs_for_requested_class_status.getString(1); 
			 }
		 }
		 if(requested_class_status.equals(WebKeys.ACCEPTED)){
			 requested_class_status = WebKeys.ACCEPTED;
		 }else if(!requested_class_status.equals(WebKeys.DECLINED) && !requested_class_status.equals(WebKeys.WAITING)){
			String update_requested_class_status = "update teacher_subjects set requested_class_status='"+WebKeys.WAITING+"', desktop_status='"+WebKeys.ASSIGN_STATUS_INACTIVE+"' where teacher_id="+teacherId+" and grade_id="+gradeId+" and class_id="+classId;
			jdbcTemplate.update(update_requested_class_status);
			requested_class_status = WebKeys.SUCCESS;
		 }
		return requested_class_status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassActualSchedule> getMyClassesTimeTable(long teacherId) {
		List<ClassActualSchedule> casLt = new ArrayList<ClassActualSchedule>();
		casLt=(List<ClassActualSchedule>) getHibernateTemplate().find("from ClassActualSchedule where classStatus.teacher.teacherId="+teacherId+"and classStatus.availStatus='"+WebKeys.AVAILABLE+"' order by days.dayId,periods.startTime");
		return casLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassActualSchedule> getRequestedClassSections(long teacherId,long gradeId, long classId) {
		List<ClassActualSchedule> casLt = new ArrayList<ClassActualSchedule>();
		casLt=(List<ClassActualSchedule>) getHibernateTemplate().find("from ClassActualSchedule where classStatus.teacher.teacherId="+teacherId+"and "
				+ "classStatus.availStatus='"+WebKeys.AVAILABLE+"' and classStatus.section.gradeClasses.grade.gradeId="+gradeId+" and "
				+ "classStatus.section.gradeClasses.studentClass.classId="+classId+" order by classStatus.section.sectionId");
		return casLt;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RegisterForClass> getStudentRoster(long csId) throws DataException {
		List<RegisterForClass> students = new ArrayList<RegisterForClass>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from RegisterForClass where status =:status and "
					+ "classStatus_1=:classStatus and classStatus.csId=:csId order by student.userRegistration.lastName");
			query.setParameter("status", WebKeys.ACCEPTED);
			query.setParameter("classStatus", WebKeys.ALIVE);
			query.setParameter("csId", csId);
			students = query.list();
		}catch(DataException e){
			logger.error("Error in getStudentsByCsId() of  TeacherViewClassDAOImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentsByCsId() of TeacherViewClassDAOImpl", e);
		}
		return students;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassActualSchedule> getMyClassesTimeTable(long teacherId, long csId) {
		List<ClassActualSchedule> casLt = new ArrayList<ClassActualSchedule>();
		casLt=(List<ClassActualSchedule>) getHibernateTemplate().find("from ClassActualSchedule where classStatus.teacher.teacherId="+teacherId+"and "
				+ "classStatus.availStatus='"+WebKeys.AVAILABLE+"' and classStatus.csId="+csId+" order by days.dayId,periods.startTime");
		return casLt;
	}
}
