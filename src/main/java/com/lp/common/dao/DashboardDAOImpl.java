package com.lp.common.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.Attendance;
import com.lp.model.ClassActualSchedule;
import com.lp.model.RegisterForClass;
import com.lp.model.Report;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

public class DashboardDAOImpl extends CustomHibernateDaoSupport implements DashboardDAO {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Student> getStudentsByGrade(long gradeId) throws DataException {
		List<Student> students = new ArrayList<Student>();
		try {
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from Student where grade.gradeId=:gradeId and userRegistration.status='"+WebKeys.ACTIVE+"' "
					+ "order by userRegistration.emailId");
			query.setParameter("gradeId", gradeId);
			students = query.list();			
		} catch (DataAccessException e) {
			logger.error("Error in getStudents() of DashboardDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getStudents() of DashboardDAOImpl", e);
		}
		return students;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TeacherSubjects> getTeacherRequestsBySchool(School school)
			throws DataException {
		List<TeacherSubjects> teacherRequests = new ArrayList<TeacherSubjects>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from TeacherSubjects where desktopStatus =:desktopStatus and "
					+ "requestedClassStatus=:requestedClassStatus and teacher.userRegistration.school.schoolId=:schoolId");
			query.setParameter("desktopStatus", WebKeys.LP_STATUS_INACTIVE);
			query.setParameter("requestedClassStatus", WebKeys.WAITING);
			query.setParameter("schoolId", school.getSchoolId());
			teacherRequests = query.list();
		}catch(DataException e){
			logger.error("Error in getTeacherRequestsBySchool() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getTeacherRequestsBySchool() of DashboardServiceImpl", e);
		}
		return teacherRequests;
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RegisterForClass> getStudentRequestsforClass(Teacher teacher) throws DataException {
		List<RegisterForClass> studentRequests = new ArrayList<RegisterForClass>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from RegisterForClass where desktopStatus =:desktopStatus and "
					+ "status=:status and teacher.teacherId=:teacherId");
			query.setParameter("desktopStatus", WebKeys.LP_STATUS_INACTIVE);
			query.setParameter("status", WebKeys.WAITING);
			query.setParameter("teacherId", teacher.getTeacherId());
			studentRequests = query.list();
		}catch(DataException e){
			logger.error("Error in getStudentRequestsByTeacher() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentRequestsByTeacher() of DashboardServiceImpl", e);
		}
		return studentRequests;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RegisterForClass> getTeacherResponseforStudentRequest(Student student) throws DataException {
		List<RegisterForClass> teacherResponse = new ArrayList<RegisterForClass>();
		try{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date yesterday = cal.getTime();			
			Timestamp timestamp = new Timestamp(yesterday.getTime());			
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from RegisterForClass where "
					+ "student.studentId=:studentId and (changeDate <= current_timestamp and changeDate >:timeStamp) and desktopStatus='"+WebKeys.ACTIVE +"'");
			query.setParameter("studentId", student.getStudentId());
			query.setParameter("timeStamp", timestamp);
			teacherResponse = query.list();
		}catch(DataException e){
			logger.error("Error in getTeacherResponseforStudentRequest() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getTeacherResponseforStudentRequest() of DashboardServiceImpl", e);
		}
		return teacherResponse;
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean getStudentRTITests(Student student) throws DataException{
		List<StudentAssignmentStatus> rtiTests = new ArrayList<StudentAssignmentStatus>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from StudentAssignmentStatus where student.studentId=:studentId "
					+ "and assignment.usedFor=:usedFor and status=:testStatus and assignment.assignStatus=:assignStatus");
			query.setParameter("studentId", student.getStudentId());
			query.setParameter("usedFor", WebKeys.LP_USED_FOR_RTI);
			query.setParameter("testStatus", WebKeys.TEST_STATUS_PENDING);
			query.setParameter("assignStatus", WebKeys.ACTIVE);
			rtiTests = query.list();
			if(!rtiTests.isEmpty()){
				return true;
			}
			else{
				return false;
			}
		}catch(DataException e){
			logger.error("Error in getStudentRTITests() of  DashboardDAOImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentRTITests() of DashboardDAOImpl", e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Attendance> getStudentAttendance(UserRegistration parent) throws DataException {
		List<Attendance> attendance = new ArrayList<Attendance>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from Attendance where "
					+ "student.userRegistration.parentRegId=:parentRegId and date=current_date and status!=:status");
			query.setParameter("parentRegId", parent.getRegId());
			query.setParameter("status", WebKeys.ATTENDANCE_STATUS_PRESENT);
			attendance = query.list();
		}catch(DataException e){
			logger.error("Error in getStudentAttendance() of  DashboardDAOImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentAttendance() of DashboardDAOImpl", e);
		}
		return attendance;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RegisterForClass> getTeacherResponseforChildRequests(UserRegistration parent) throws DataException {
		List<RegisterForClass> responses = new ArrayList<RegisterForClass>();
		try{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date yesterday = cal.getTime();			
			Timestamp timestamp = new Timestamp(yesterday.getTime());	
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from RegisterForClass where "
					+ "student.userRegistration.parentRegId=:parentRegId and (changeDate <= current_timestamp and changeDate >:timeStamp)");
			query.setParameter("parentRegId", parent.getRegId());
			query.setParameter("timeStamp", timestamp);
			responses = query.list();
		}catch(DataException e){
			logger.error("Error in getTeacherResponseforChildRequests() of  DashboardDAOImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getTeacherResponseforChildRequests() of DashboardDAOImpl", e);
		}
		return responses;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Report> getParentComments(Teacher teacher) throws DataException {
		List<Report> parentComments = new ArrayList<Report>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from Report where "
					+ "classStatus.teacher.teacherId=:teacherId and parentCommentsDate = current_date");
			query.setParameter("teacherId", teacher.getTeacherId());
			parentComments = query.list();
		}catch(DataException e){
			logger.error("Error in getParentComments() of  DashboardDAOImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getParentComments() of DashboardDAOImpl", e);
		}
		return parentComments;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ClassActualSchedule> getTeacherClasses(Teacher teacher, long dayId) throws DataException {
		List<ClassActualSchedule> teacherClasses = new ArrayList<ClassActualSchedule>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from ClassActualSchedule where "
					+ "classStatus.teacher.teacherId=:teacherId and classStatus.availStatus=:availStatus and days.dayId=:dayId");
			query.setParameter("teacherId", teacher.getTeacherId());
			query.setParameter("availStatus", WebKeys.AVAILABLE);
			query.setParameter("dayId", dayId);
			teacherClasses = query.list();
		}catch(DataException e){
			logger.error("Error in getTeacherClasses() of  DashboardDAOImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getTeacherClasses() of DashboardDAOImpl", e);
		}
		return teacherClasses;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ClassActualSchedule> getStudentClasses(Student student, long dayId) throws DataException {
		List<ClassActualSchedule> studentClasses = new ArrayList<ClassActualSchedule>();
			try{
				Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from ClassActualSchedule where classStatus.csId "
						+ "in(select distinct classStatus.csId from RegisterForClass where student.studentId=:studentId and "
						+ "classStatus_1=:classStatus and status=:status) and days.dayId=:dayId");				
				query.setParameter("studentId", student.getStudentId());
				query.setParameter("classStatus", WebKeys.ALIVE);
				query.setParameter("status", WebKeys.ACCEPTED);
				query.setParameter("dayId", dayId);
				studentClasses = query.list();
			}catch(DataException e){
				logger.error("Error in getStudentClasses() of  DashboardDAOImpl"+ e);
				e.printStackTrace();
				throw new DataException("Error in getStudentClasses() of DashboardDAOImpl", e);
			}
			return studentClasses;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Assignment> getAssignedTests(Teacher teacher) {
		List<Assignment> assignments = new ArrayList<Assignment>();
		try{
			List<String> usedFor = new ArrayList<String> ();
			usedFor.add(WebKeys.LP_USED_FOR_ASSESSMENT);
			usedFor.add(WebKeys.LP_USED_FOR_HOMEWORKS);
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from Assignment where "
					+ "classStatus.teacher.teacherId=:teacherId and assignStatus=:assignStatus and usedFor in (:usedFor) and dateDue>=current_date");
			query.setParameter("teacherId", teacher.getTeacherId());
			query.setParameter("assignStatus", WebKeys.ACTIVE);
			query.setParameterList("usedFor", usedFor);
			assignments = query.list();
		}catch(DataException e){
			logger.error("Error in getAssignedTests() of  DashboardDAOImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getAssignedTests() of DashboardDAOImpl", e);
		}
		return assignments;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<StudentAssignmentStatus> getStudentAssignedTests(Student student) {
		List<StudentAssignmentStatus> assignments = new ArrayList<StudentAssignmentStatus>();
		try{
			List<String> usedFor = new ArrayList<String> ();
			usedFor.add(WebKeys.LP_USED_FOR_ASSESSMENT);
			usedFor.add(WebKeys.LP_USED_FOR_HOMEWORKS);
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from StudentAssignmentStatus where "
					+ "student.studentId=:studentId and assignment.assignStatus=:assignStatus and assignment.usedFor in (:usedFor) and "
					+ "assignment.dateDue>=current_date");
			query.setParameter("studentId", student.getStudentId());
			query.setParameter("assignStatus", WebKeys.ACTIVE);
			query.setParameterList("usedFor", usedFor);
			assignments = query.list();
		}catch(DataException e){
			logger.error("Error in getStudentAssignedTests() of  DashboardDAOImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentAssignedTests() of DashboardDAOImpl", e);
		}
		return assignments;
	}	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getTestsToBeGraded(Teacher teacher){
		List<StudentAssignmentStatus> studentAssignmentStatus = Collections.emptyList();
		try{
			studentAssignmentStatus = (List<StudentAssignmentStatus>) getHibernateTemplate().find("From StudentAssignmentStatus where assignment.classStatus.teacher.teacherId="
					+ teacher.getTeacherId()+" and status='"+WebKeys.TEST_STATUS_SUBMITTED+"' and assignment.classStatus.availStatus='"+WebKeys.AVAILABLE+"'"
							+ " and gradedStatus='"+WebKeys.GRADED_STATUS_NOTGRADED+"' and assignment.assignmentType.assignmentType='"+WebKeys.ASSIGNMENT_TYPE_FLUENCY_READING+"'");
		}
		catch(Exception e){
			logger.error("Error in getTestsToBeGraded() of  DashboardServiceImpl"+ e);
			throw new DataException("Error in getTestsToBeGraded() of DashboardServiceImpl", e);
		}
		return studentAssignmentStatus;
		
	}
}
