package com.lp.mobile.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.lp.custom.exception.DataException;
import com.lp.mobile.model.StarScoresReports;
import com.lp.model.Attendance;
import com.lp.model.EventStatus;
import com.lp.model.NotificationStatus;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.StarScores;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentStarStrategies;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

public class NotificationDAOImpl extends CustomHibernateDaoSupport implements NotificationDAO  {

	static final Logger logger = Logger.getLogger(NotificationDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateAttendanceStatus(long notificationId) {
		Session session = null;
		int rowsUpdated = 0;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			//Updating register_for_class table
			String hql2 = "UPDATE Attendance set readStatus = :readStatus"
					+ " WHERE attendanceId = :attendanceId ";
			Query query2 = session.createQuery(hql2);			
			query2.setParameter("readStatus", WebKeys.LP_YES.toLowerCase());
			query2.setParameter("attendanceId", notificationId);
			rowsUpdated = query2.executeUpdate();
			tx.commit();
		}
		catch(Exception e){
			tx.rollback();
			logger.error("Error in updateAttendanceStatus() of NotificationDAOImpl", e);
		}		
		finally{
			session.close();
		}
		if(rowsUpdated > 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateRegisterForClassStatus(long studentId, long csId) {
		// TODO Auto-generated method stub
		Session session = null;
		int rowsUpdated = 0;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			//Updating register_for_class table
			String hql2 = "UPDATE RegisterForClass set readStatus = :readStatus"
					+ " WHERE student.studentId = :studentId and classStatus.csId = :csId";
			Query query2 = session.createQuery(hql2);			
			query2.setParameter("readStatus", WebKeys.LP_YES.toLowerCase());
			query2.setParameter("studentId", studentId);
			query2.setParameter("csId", csId);
			rowsUpdated = query2.executeUpdate();
			tx.commit();
		}
		catch(Exception e){
			tx.rollback();
			logger.error("Error in updateAttendanceStatus() of NotificationDAOImpl", e);
		}		
		finally{
			session.close();
		}
		if(rowsUpdated > 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateEventStatus(long notificationId) {
		// TODO Auto-generated method stub
		Session session = null;
		int rowsUpdated = 0;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			//Updating register_for_class table
			String hql2 = "UPDATE EventStatus set status = :readStatus"
					+ " WHERE eventStatusId = :eventId";
			Query query2 = session.createQuery(hql2);			
			query2.setParameter("readStatus", WebKeys.LP_YES.toLowerCase());
			query2.setParameter("eventId", notificationId);
			rowsUpdated = query2.executeUpdate();
			tx.commit();
		}
		catch(Exception e){
			tx.rollback();
			logger.error("Error in updateAttendanceStatus() of NotificationDAOImpl", e);
		}		
		finally{
			session.close();
		}
		if(rowsUpdated > 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateNotificationStatusStatus(long notificationStatusId) {
		// TODO Auto-generated method stub
		Session session = null;
		int rowsUpdated = 0;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			//Updating register_for_class table
			tx = session.beginTransaction();
			String hql2 = "UPDATE NotificationStatus set status = :status"
					+ " WHERE notificationStatusId = :notificationStatusId";
			Query query2 = session.createQuery(hql2);			
			query2.setParameter("status", WebKeys.READ.toLowerCase());
			query2.setParameter("notificationStatusId", notificationStatusId);
			rowsUpdated = query2.executeUpdate();
			tx.commit();
		}
		catch(Exception e){
			tx.rollback();
			logger.error("Error in updateAttendanceStatus() of NotificationDAOImpl", e);
		}		
		finally{
			session.close();
		}
		if(rowsUpdated > 0)
			return true;
		else
			return false;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateTestResultsStatusStatus(long notificationStatusId) {
		// TODO Auto-generated method stub
		Session session = null;
		int rowsUpdated = 0;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			//Updating register_for_class table
			String hql2 = "UPDATE StudentAssignmentStatus set readStatus = :status"
					+ " WHERE studentAssignmentId = :notificationStatusId";
			Query query2 = session.createQuery(hql2);			
			query2.setParameter("status", WebKeys.READ.toLowerCase());
			query2.setParameter("notificationStatusId", notificationStatusId);
			rowsUpdated = query2.executeUpdate();
			tx.commit();
		}
		catch(Exception e){
			tx.rollback();
			logger.error("Error in updateAttendanceStatus() of NotificationDAOImpl", e);
		}		
		finally{
			session.close();
		}
		if(rowsUpdated > 0)
			return true;
		else
			return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RegisterForClass> getTeacherResponseforChildRequests(UserRegistration parent) throws DataException {
		List<RegisterForClass> responses = new ArrayList<RegisterForClass>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from RegisterForClass where "
					+ "student.userRegistration.parentRegId=:parentRegId and status=:status and classStatus_1=:classStatus ORDER BY changeDate DESC ");
			query.setParameter("parentRegId", parent.getRegId());
			query.setParameter("status", WebKeys.ACCEPTED);
			query.setParameter("classStatus", WebKeys.ALIVE);
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
	public List<Attendance> getStudentAttendance(UserRegistration parent) throws DataException {
		List<Attendance> attendance = new ArrayList<Attendance>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from Attendance where "
					+ "student.userRegistration.parentRegId=:parentRegId and status!=:status and classStatus.availStatus=:availStatus ORDER BY date DESC ");
			query.setParameter("parentRegId", parent.getRegId());
			query.setParameter("status", WebKeys.ATTENDANCE_STATUS_PRESENT);
			query.setParameter("availStatus", WebKeys.AVAILABLE);
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
	public List<NotificationStatus> getCurrentAnnouncementsBySchool(School school, Date fromDate, Date toDate) {
		List<NotificationStatus> nsLt = new ArrayList<NotificationStatus>();
		try {
			 UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			 String queryStr = "from NotificationStatus where announcements.school.schoolId=:schoolId" +
					 " and (announcements.createdFor.userTypeid is null or announcements.createdFor.userTypeid in (:userTypeIds)) and userRegistration.regId=:regId" +
					 " and announcements.announceDate between '"+fromDate+"' and '"+toDate+"' ORDER BY announcements.announceDate DESC";	
			 Query query = getHibernateTemplate().getSessionFactory()
					.openSession()
					.createQuery(queryStr);
			 query.setParameter("schoolId", school.getSchoolId());
			 List<Long> userTypes = new ArrayList<Long>();
			 if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_STUDENT_BELOW_13)){
				userTypes.add(userReg.getUser().getUserTypeid()-1);
			 }else if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_ADMIN)){
				userTypes.add(userReg.getUser().getUserTypeid()+1);
				userTypes.add(userReg.getUser().getUserTypeid()+2);
				userTypes.add(userReg.getUser().getUserTypeid()+3);
			 }else{
				userTypes.add(userReg.getUser().getUserTypeid());
			 }
			 query.setParameterList("userTypeIds", userTypes);
			 query.setParameter("regId", userReg.getRegId());
			 nsLt = query.list();	
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return nsLt;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<EventStatus> getCurrentEventsBySchool(UserRegistration userRegistration, Date fromDate, Date toDate) {
		List<EventStatus> events =new ArrayList<EventStatus>();
		try {
			Query query = getHibernateTemplate().getSessionFactory()
					.openSession()
					.createQuery("from EventStatus where event.school.schoolId=:schoolId and userRegistration.regId=:regId and event.announcementDate between '"
					+fromDate+"' and '"+toDate+"' ORDER BY event.announcementDate DESC");
			query.setParameter("schoolId", userRegistration.getSchool().getSchoolId());
			query.setParameter("regId", userRegistration.getRegId());
			events = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return events;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RegisterForClass> getUnreadTeacherResponseforChildRequests(UserRegistration parent) throws DataException {
		List<RegisterForClass> responses = new ArrayList<RegisterForClass>();
		try{
		
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from RegisterForClass where "
					+ "student.userRegistration.parentRegId=:parentRegId and status=:status and classStatus_1=:classStatus "
					+ "and readStatus =:readStatus");
			query.setParameter("parentRegId", parent.getRegId());
			query.setParameter("status", WebKeys.ACCEPTED);
			query.setParameter("classStatus", WebKeys.ALIVE);
			query.setParameter("readStatus", WebKeys.READ_STATUS_NO);
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
	public List<Attendance> getUnreadStudentAttendance(UserRegistration parent) throws DataException {
		List<Attendance> attendance = new ArrayList<Attendance>();
		try{
			Query query=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from Attendance where "
					+ "student.userRegistration.parentRegId=:parentRegId and status!=:status and classStatus.availStatus=:availStatus  and readStatus =:readStatus");
			query.setParameter("parentRegId", parent.getRegId());
			query.setParameter("status", WebKeys.ATTENDANCE_STATUS_PRESENT);
			query.setParameter("availStatus", WebKeys.AVAILABLE);
			query.setParameter("readStatus", WebKeys.READ_STATUS_NO);
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
	public List<NotificationStatus> getUnreadCurrentAnnouncementsBySchool(School school, Date fromDate, Date toDate) {
		List<NotificationStatus> nsLt = new ArrayList<NotificationStatus>();
		try {
			 UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			 String queryStr = "from NotificationStatus where announcements.school.schoolId=:schoolId" +
					 " and (announcements.createdFor.userTypeid is null or announcements.createdFor.userTypeid in (:userTypeIds)) and userRegistration.regId=:regId" +
					 " and announcements.announceDate between '"+fromDate+"' and '"+toDate+"'  and status =:status";	
			 Query query = getHibernateTemplate().getSessionFactory()
					.openSession()
					.createQuery(queryStr);
			 query.setParameter("schoolId", school.getSchoolId());
			 List<Long> userTypes = new ArrayList<Long>();
			 if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_STUDENT_BELOW_13)){
				userTypes.add(userReg.getUser().getUserTypeid()-1);
			 }else if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_ADMIN)){
				userTypes.add(userReg.getUser().getUserTypeid()+1);
				userTypes.add(userReg.getUser().getUserTypeid()+2);
				userTypes.add(userReg.getUser().getUserTypeid()+3);
			 }else{
				userTypes.add(userReg.getUser().getUserTypeid());
			 }
			 query.setParameterList("userTypeIds", userTypes);
			 query.setParameter("regId", userReg.getRegId());
  			 query.setParameter("status", WebKeys.UN_READ);
			 nsLt = query.list();	
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return nsLt;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<EventStatus> getUnreadCurrentEventsBySchool(School school, Date fromDate, Date toDate) {
		List<EventStatus> events =new ArrayList<EventStatus>();
		try {
			 UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Query query = getHibernateTemplate().getSessionFactory()
					.openSession()
					.createQuery("from EventStatus where event.school.schoolId=:schoolId and userRegistration.regId=:regId and event.announcementDate "
							+ "between '"+fromDate+"' and '"+toDate+"'  and status =:readStatus");
			query.setParameter("schoolId", school.getSchoolId());
			query.setParameter("regId", userReg.getRegId());			
			query.setParameter("readStatus", WebKeys.UN_READ);
			events = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return events;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getChildTestResults(UserRegistration userRegistration, String usedFor) {
		List<StudentAssignmentStatus> tests =new ArrayList<StudentAssignmentStatus>();
		try {
			tests = (List<StudentAssignmentStatus>) getHibernateTemplate().find("from StudentAssignmentStatus where "
					+ "assignment.assignStatus='"+WebKeys.ACTIVE+"' and student.userRegistration.parentRegId="
					+ userRegistration.getRegId()+" and gradedStatus='"+WebKeys.GRADED_STATUS_GRADED+"'"
					+ " and assignment.classStatus.availStatus='"+WebKeys.AVAILABLE+"'"
					+ "and assignment.usedFor='"+usedFor+"' and assignment.assignmentType.assignmentType not in ('"+WebKeys.PHONIC_SKILL_TEST+"','"+WebKeys.GEAR_GAME+"')"
					+ " ORDER BY gradedDate DESC, assignment.title");  /* Needs to be included later "  and gradedDate=current_date "*/
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return tests;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getUnreadChildTestResults(UserRegistration userReg, String usedFor){
		List<StudentAssignmentStatus> tests =new ArrayList<StudentAssignmentStatus>();
		try {
			tests = (List<StudentAssignmentStatus>) getHibernateTemplate().find("from StudentAssignmentStatus where "
					+ "assignment.assignStatus='"+WebKeys.ACTIVE+"' and student.userRegistration.parentRegId="
					+ userReg.getRegId()+" and gradedStatus='"+WebKeys.GRADED_STATUS_GRADED+"'"
					+ " and assignment.classStatus.availStatus='"+WebKeys.AVAILABLE+"' and assignment.assignmentType.assignmentType not"
					+ " in ('"+WebKeys.PHONIC_SKILL_TEST+"','"+WebKeys.GEAR_GAME+"')" //and gradedDate=current_date
					+ " and assignment.usedFor='"+usedFor+"' and readStatus='"+WebKeys.UN_READ+"'");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return tests;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StarScores> getStarReports(long studentId,long caasppTypeId,long gradeId){
		 List<StarScores> studentStarScores = new ArrayList<StarScores>();
		 try {
			 studentStarScores = (List<StarScores>) getHibernateTemplate()
					 .find("from StarScores where caasppType.caasppTypesId="+ caasppTypeId+" and student.studentId="+studentId+" and grade.gradeId="+gradeId+" order by trimester.orderId");
					 		//+ "and grade.gradeId="+gradeId+" and trimester.trimesterId="+trimesterId+"");
		 } catch (DataAccessException e) {
			 logger.error("Error in getStarReports() of NotificationDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return studentStarScores;
	}
	
}
