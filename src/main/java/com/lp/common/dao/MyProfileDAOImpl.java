package com.lp.common.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.lp.admin.dao.AdminDAO;
import com.lp.model.GradeClasses;
import com.lp.model.Interest;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.SubInterest;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserInterests;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

public class MyProfileDAOImpl extends CustomHibernateDaoSupport implements MyProfileDAO {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private AdminDAO adminDAO;

	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	@Override
	public boolean updatePersonalInfo(UserRegistration userRegistration) {
		boolean status = true;
		try{
			if(userRegistration != null){
				  String update_query_address = "update address set "
				  		+ " address='"+userRegistration.getAddress().getAddress()+"' ,"
				  		+ " state_id= "+userRegistration.getAddress().getStates().getStateId()+" ,"
				  		+ " city='"+userRegistration.getAddress().getCity()+"' ,"
				  		+ " zipcode ="+userRegistration.getAddress().getZipcode()+" "
				  		+ " where address_id= "+userRegistration.getAddress().getAddressId();
				  jdbcTemplate.update(update_query_address);
				  
				  String update_query_userReg = "update user_registration set "
					  		+ " title='"+userRegistration.getTitle()+"' ,"
					  		+ " first_name = '"+userRegistration.getFirstName()+"' ,"
					  		+ " last_name ='"+userRegistration.getLastName()+"' ,"
					  		+ " phonenumber ='"+userRegistration.getPhonenumber()+"' "
					  		+ " where reg_id= "+userRegistration.getRegId();
					  jdbcTemplate.update(update_query_userReg);
				
				 status = true;
			}else{
				 status = false;	
			}
		}catch(Exception e){
			logger.error("Error in updatePersonalInfo() of MyProfileDAOImpl"+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	@Override
	public boolean updateUserName(UserRegistration userRegistration) {
		boolean status = true;
		try{
			if(userRegistration != null){
				  String update_query_userReg = "update user_registration set "
					  		+ " email_id='"+userRegistration.getNewUserName()+"' "
					  		+ " where reg_id= "+userRegistration.getRegId();
					  jdbcTemplate.update(update_query_userReg);
				
				 status = true;
			}else{
				 status = false;	
			}
		}catch(Exception e){
			logger.error("Error in updateUserName() of MyProfileDAOImpl"+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	@Override
	public boolean updatePassword(UserRegistration userRegistration) {
		boolean status = true;
		try{
			if(userRegistration != null){
				  String update_query_userReg = "update user_registration set "
					  		+ " password='"+userRegistration.getNewPassword()+"' "
					  		+ " where reg_id= "+userRegistration.getRegId();
					  jdbcTemplate.update(update_query_userReg);
				
				 status = true;
			}else{
				 status = false;	
			}
		}catch(Exception e){
			logger.error("Error in updatePassword() of MyProfileDAOImpl"+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	@Override
	public boolean updateHomePage(UserRegistration userRegistration) {
		boolean status = true;
		try{
			if(userRegistration != null){
				  String update_query_userReg = "update user_registration set "
					  		+ " education='"+userRegistration.getEducation()+"',"
					  		+ " experience='"+userRegistration.getExperience()+"',"
					  		+ " subjects='"+userRegistration.getSubjects()+"',"
					  		+ " interestareas='"+userRegistration.getInterestareas()+"',"
					  		+ " projects='"+userRegistration.getProjects()+"',"
					  		+ " contactinfo='"+userRegistration.getContactinfo()+"' "
					  		+ " where reg_id= "+userRegistration.getRegId();
					  jdbcTemplate.update(update_query_userReg);
				
				 status = true;
			}else{
				 status = false;	
			}
		}catch(Exception e){
			logger.error("Error in updateHomePage() of MyProfileDAOImpl"+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SubInterest> getAllUserPersonalInterests(List<Integer> interestIdsLt) {
		String str= "";
		List<SubInterest> subInterestLt = new ArrayList<SubInterest>();
		try{
			for (int i = 0; i < interestIdsLt.size(); i++) {
				str += interestIdsLt.get(i);
				if(i != interestIdsLt.size()-1){
					str += ",";
				}
			}
			subInterestLt = (List<SubInterest>) getHibernateTemplate().find(
						"from SubInterest where interest.interestId in ("+str+")");
				
		}catch(Exception e){
			logger.error("Error in getAllUserPersonalInterests() of MyProfileDAOImpl"+ e);
			e.printStackTrace();
		}
		return subInterestLt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInterests> getUserPersonalInterests(long regId) {
		List<UserInterests> userIntereststLt = new ArrayList<UserInterests>();
		try{
			userIntereststLt = (List<UserInterests>) getHibernateTemplate().find(
						"from UserInterests where userRegistration.regId="+regId);
				
		}catch(Exception e){
			logger.error("Error in updatePersonalInterest() of MyProfileDAOImpl"+ e);
			e.printStackTrace();
		}
		return userIntereststLt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Interest getInterestByInterestId(long interestId) {
		List<Interest> interestLt = (List<Interest>) getHibernateTemplate()
				.find("from Interest where interestId=" + interestId);
		if (interestLt.size() > 0)
			return interestLt.get(0);
		else
			return null;
	}
	
	@Override
	public boolean updatePersonalInterest(ArrayList<String>  userInterestArray,long regId) {
		boolean status = true;
		try{
			Date create_date = new Date(); 
			Date change_date = new Date(); 
			DateFormat formatter = new SimpleDateFormat(WebKeys.DB_DATE_FORMATE);
			formatter.format(create_date);
			create_date = formatter.parse(formatter.format(create_date));
			
			String query_for_userInterest = "select * from user_interests where reg_id="+regId;
			SqlRowSet rs_for_userInterest = jdbcTemplate.queryForRowSet(query_for_userInterest);
			 if (rs_for_userInterest.next())
				 jdbcTemplate.update("delete from user_interests where reg_id="+regId);
			
			for (String userInterest : userInterestArray) {
				String[] interestArray = userInterest.split("-");
				String interestId = interestArray[0]; 
				String subInterestId = interestArray[1];
				
				String insert_query = "insert into user_interests(reg_id,interest_id,sub_interest_id,create_date,change_date) values(?,?,?,?,?)";
				jdbcTemplate.update(insert_query, regId, interestId, subInterestId,create_date,change_date);
			}
			
		}catch(Exception e){
			logger.error("Error in updatePersonalInterest() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherSubjects> getTeacherClasses(long teacherId){
		List<TeacherSubjects> teacherSubjectsLt = new ArrayList<TeacherSubjects>();
		try{
			 teacherSubjectsLt =(List<TeacherSubjects>) getHibernateTemplate().find("from TeacherSubjects where teacher.teacherId="+teacherId+" order by grade.gradeId");
		}catch(Exception e){
			logger.error("Error in getTeacherClasses() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
		}
		return teacherSubjectsLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getStudentClasses(long studentId){
		List<RegisterForClass> registerForClasses = null;
		try{
			registerForClasses = (List<RegisterForClass>) getHibernateTemplate().find("From RegisterForClass where student.studentId="+studentId+" and classStatus_1='"+WebKeys.ALIVE+"'");
		}
		catch(HibernateException e){
			logger.error("Error in getStudentClasses() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
		}
		return registerForClasses;
	
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherSubjects> getTeacherGradeClasses(long gradeId,long teacherId){
		List<TeacherSubjects> teacherSubjectsLt = new ArrayList<TeacherSubjects>();
		try{
			 teacherSubjectsLt =(List<TeacherSubjects>) getHibernateTemplate().find("from TeacherSubjects where teacher.teacherId="+teacherId+" and grade.gradeId="+gradeId+" order by grade.gradeId");
		}catch(Exception e){
			logger.error("Error in getTeacherGradeClasses() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
		}
		return teacherSubjectsLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GradeClasses> getStudentGradeClasses(long gradeId, long studentId){
		List<RegisterForClass> registerForClassLt = new ArrayList<RegisterForClass>() ;
		List<GradeClasses> gradeClassesLt = new ArrayList<GradeClasses>();
		try{
			registerForClassLt = (List<RegisterForClass>) getHibernateTemplate().find("From RegisterForClass where student.studentId="+studentId+" and gradeClasses.grade.gradeId="+gradeId+" and classStatus_1='"+WebKeys.ALIVE+"'");
			 for (RegisterForClass registerForClass : registerForClassLt) {
				 gradeClassesLt.add(registerForClass.getGradeClasses());
			}
		}
		catch(HibernateException e){
			logger.error("Error in getStudentGradeClasses() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
		}
		return gradeClassesLt;
	
	}
	@Override
	public boolean deleteTeacherGradeClass(long teacherSubjectId){
		boolean status = true;
		try{
		 jdbcTemplate.update("delete from teacher_subjects where teacher_subject_id="+teacherSubjectId);
		}catch(Exception e){
			logger.error("Error in deleteTeacherGradeClass() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	@Override
	public boolean deleteStudentGradeClass(long studentId,long sectionId, long gradeClassId){
		boolean status = true;
		try{
		 jdbcTemplate.update("delete from register_for_class where student_id="+studentId+" and section_id="+sectionId+" and grade_class_id="+gradeClassId);
		}catch(Exception e){
			logger.error("Error in deleteStudentGradeClass() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	@Override
	public boolean updateTeacherGradeClass(long teacherSubjectId,long gradeLevelId,int noSectionsPerDay,int noSectionsPerWeek){
		boolean status = true;
		try{
			  String update_query_TeacherGradeClass = "update teacher_subjects set "
				  		+ " grade_level_id = "+gradeLevelId+","
				  		+ " no_sections_per_day= "+noSectionsPerDay+","
				  		+ " no_sections_per_week = "+noSectionsPerWeek+" "
				  		+ " where teacher_subject_id="+teacherSubjectId;
				  jdbcTemplate.update(update_query_TeacherGradeClass);
		}catch(Exception e){
			logger.error("Error in updateTeacherGradeClass() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	@Override
	public boolean updateStudentGradeClass(long studentId,long sectionId, long gradeClassId, long gradeLevelId){
		boolean status = true;
		try{
			String update_query_TeacherGradeClass = "update register_for_class set "
			  		+ " grade_level_id = "+gradeLevelId+" "
			  		+ ", section_id =  "+sectionId+" "
			  		+ " where student_id="+studentId+" and grade_class_id="+gradeClassId+" and class_status='"+WebKeys.ALIVE+"' and status in('"+WebKeys.LP_STATUS_NEW+"','"+WebKeys.WAITING+"')";
			  jdbcTemplate.update(update_query_TeacherGradeClass);
			  
		}catch(Exception e){
			logger.error("Error in updateStudentGradeClass() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	public boolean addTeacherGradeClass(long teacherId,long gradeId,long classId, long gradeLevelId,int noSectionsPerDay,int noSectionsPerWeek){
		boolean status = true;
		try{
			Date create_date = new Date(); 
			Date change_date = new Date(); 
			DateFormat formatter = new SimpleDateFormat(WebKeys.DB_DATE_FORMATE);
			formatter.format(create_date);
			create_date = formatter.parse(formatter.format(create_date));
			long teacherSubjectId = 0;
			String query_for_teacher_subject_id = "select teacher_subject_id from teacher_subjects where teacher_id="+teacherId+" and grade_id="+gradeId+" and class_id="+classId;
			SqlRowSet rs_for_teacher_subject_id = jdbcTemplate.queryForRowSet(query_for_teacher_subject_id);
			 if (rs_for_teacher_subject_id.next())
				 teacherSubjectId = rs_for_teacher_subject_id.getLong(1);
			 
			 if(teacherSubjectId == 0){
				 String insert_query = "insert into teacher_subjects(teacher_id,grade_id,class_id,create_date,change_date,grade_level_id,no_sections_per_day,no_sections_per_week) values(?,?,?,?,?,?,?,?)";
				 jdbcTemplate.update(insert_query, teacherId, gradeId, classId, create_date, change_date, gradeLevelId, noSectionsPerDay, noSectionsPerWeek);
			 }else{
				 updateTeacherGradeClass(teacherSubjectId,gradeLevelId,noSectionsPerDay,noSectionsPerWeek);
			 }
			 
		}catch(Exception e){
			logger.error("Error in addTeacherGradeClass() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	@SuppressWarnings("unchecked")
	public boolean addStudentGradeClass(long studentId, long gradeClassId, long gradeLevelId, long sectionId){
		boolean status = true;
		try{
			Date create_date = new Date(); 
			Date change_date = new Date(); 
			DateFormat formatter = new SimpleDateFormat(WebKeys.DB_DATE_FORMATE);
			formatter.format(create_date);
			create_date = formatter.parse(formatter.format(create_date));
			GradeClasses gradeClasses = adminDAO.getGradeClass(gradeClassId);
			List<Student> student = (List<Student>) getHibernateTemplate()
					.find("from Student where grade.gradeId=" + gradeClasses.getGrade().getGradeId() + " and studentId = "+studentId
							+" and gradeStatus='promoted'");
			if(student.size() > 0){
				student.get(0).setGradeStatus(WebKeys.ACTIVE);
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(student.get(0));
				tx.commit();
				session.close();
			}
			String query_for_check = "select * from register_for_class where student_id="+studentId+" and grade_class_id="+gradeClassId+" and class_status='"+WebKeys.ALIVE+"'";
			SqlRowSet rs_for_check = jdbcTemplate.queryForRowSet(query_for_check);
			 if (rs_for_check.next()){
				 updateStudentGradeClass(studentId, sectionId, gradeClassId, gradeLevelId);
			 }else{			 
				 String insert_query = "insert into register_for_class(student_id,status,class_status,create_date,change_date,grade_class_id,grade_level_id,section_id) values(?,?,?,?,?,?,?,?)";
				 if(sectionId == 0)
					 jdbcTemplate.update(insert_query, studentId, WebKeys.LP_STATUS_NEW, WebKeys.ALIVE, create_date, change_date, gradeClassId, gradeLevelId, null);
				 else
					 jdbcTemplate.update(insert_query, studentId, WebKeys.LP_STATUS_NEW, WebKeys.ALIVE, create_date, change_date, gradeClassId, gradeLevelId, sectionId);
			 }
		}catch(Exception e){
			logger.error("Error in addStudentGradeClass() of MyProfileDAOImpl "+ e);
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	
	
	
	
}
