package com.lp.appadmin.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lp.admin.service.AdminSchedulerService;
import com.lp.model.Announcements;
import com.lp.model.EventStatus;
import com.lp.model.Events;
import com.lp.model.NotificationStatus;
import com.lp.model.School;
import com.lp.model.SchoolSchedule;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("announcementsNEventsDAO")
public class AnnouncementsNEventsDAOImpl extends CustomHibernateDaoSupport implements AnnouncementsNEventsDAO {
	
	static final Logger logger = Logger.getLogger(AnnouncementsNEventsDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	HttpSession session;
	@Autowired
	private AdminSchedulerService adminSchedulerservice;
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@Override
	public boolean saveorUpdateAnnouncements(Announcements announcement) {
		try {
			super.saveOrUpdate(announcement);
			return true;
		} catch (HibernateException e) {
			logger.error("Error in saveAcademicGrade() of AcademicGradesDAOImpl"
					+ e);
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getAnnouncementsBySchool(School school) {
		List<Announcements> announcements = null;
		 SchoolSchedule schoolSchedule = adminSchedulerservice.getSchoolSchedule(school);
		try {
			announcements = (List<Announcements>) getHibernateTemplate().find(
					"from Announcements where school.schoolId="+ school.getSchoolId()+ " and announceDate BETWEEN '"+schoolSchedule.getStartDate()+"' AND '"+schoolSchedule.getEndDate()+"'");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return announcements;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Announcements getAnnouncements(long announcementId){
		Announcements announce=null;
		try{
			List announcelist = (List<Announcements>) getHibernateTemplate().find("FROM Announcements WHERE announcementId=" + announcementId);
			if(announcelist.size()>0)
				announce= (Announcements) announcelist.get(0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return announce;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean checkAnnouncements(String announcementName){
		boolean flag =false;
		try{
			List announcelist = (List<Announcements>) getHibernateTemplate().find("FROM Announcements WHERE annoncementName='" + announcementName+"'");
			if(announcelist.size()>0)
				flag=true;		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	
	@Override
	public boolean saveorUpdateEvents(Events event) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			int i = 0;
			session.saveOrUpdate(event);
			for(EventStatus evStatus : event.getEventStatus()){
				session.saveOrUpdate(evStatus);
				i++;
				if (i % 10 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			transaction.rollback();
			logger.error("Error in saveAcademicGrade() of AcademicGradesDAOImpl"
					+ e);
			e.printStackTrace();
			return false;
		}
		finally{
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Events> getEventsBySchool(School school) {
		List<Events> events = null;
		try {
			events = (List<Events>) getHibernateTemplate().find( "from Events where school.schoolId=" + school.getSchoolId());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return events;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Events getEvents(long eventId){
		Events event=null;
		try{
			List eventlist = (List<Events>) getHibernateTemplate().find("FROM Events WHERE eventId="+eventId);
			if(eventlist.size()>0)
				event= (Events) eventlist.get(0);
		}
		catch(HibernateException e){
			e.printStackTrace();
		}
		return event;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean checkEvents(String eventName, long eventId, long schoolId){
		boolean flag=false;
		try{
			List eventlist = (List<Events>) getHibernateTemplate().find("FROM Events WHERE eventName='"+eventName+"' and "
					+ "school.schoolId ="+schoolId+" and eventId != "+eventId);
			if(eventlist.size()>0)
				flag= true;
		}
		catch(HibernateException e){
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public void deleteAnnouncement(Announcements announce){
		deleteNotificationStatus(announce.getAnnouncementId());
		super.delete(announce);
	}
	
	@Override
	public void deleteNotificationStatus(long announcementId){
		jdbcTemplate.update("delete from notification_status where announcement_id="+announcementId);
	}
	
	@Override
	public void deleteEvents(Events events){
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			for(EventStatus eventStatus :events.getEventStatus()){
				session.delete(eventStatus);
			}
			session.delete(events);
			transaction.commit();
		}
		catch(Exception e){
			transaction.rollback();
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<NotificationStatus> getCurrentAnnouncementsBySchool(School school) {
		List<NotificationStatus> nsLt = new ArrayList<NotificationStatus>();
		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			String queryStr = "from NotificationStatus where announcements.school.schoolId=:schoolId and announcements.announceDate <= current_date";
			queryStr = queryStr + " and (announcements.createdFor.userTypeid is null or announcements.createdFor.userTypeid in (:userTypeIds)) and userRegistration.regId=:regId";
			queryStr = queryStr + " and status=:status";
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
	public List<Announcements> getAnnouncementsBySchoolAdmin(School school) {
		List<Announcements> announcements = new ArrayList<Announcements>();
		try {
			 UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			 String queryStr = "from Announcements where school.schoolId=:schoolId and announceDate = current_date";
			 queryStr = queryStr + " and (createdFor.userTypeid is null or createdFor.userTypeid in (:userTypeIds))";
			 Query query = getHibernateTemplate().getSessionFactory()
					.openSession()
					.createQuery(queryStr);
			 query.setParameter("schoolId", school.getSchoolId());
			 List<Long> userTypes = new ArrayList<Long>();
			 if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_STUDENT_BELOW_13))
			 {
				 userTypes.add(userReg.getUser().getUserTypeid()-1);
			 }
			 else if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_ADMIN))
			 {
				 userTypes.add(userReg.getUser().getUserTypeid()+1);
				 userTypes.add(userReg.getUser().getUserTypeid()+2);
				 userTypes.add(userReg.getUser().getUserTypeid()+3);
			 }else{
				 userTypes.add(userReg.getUser().getUserTypeid());
			 }
			 query.setParameterList("userTypeIds", userTypes);
			 announcements = query.list();	
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return announcements;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Events> getCurrentEventsBySchool(School school) {
		List<Events> events =new ArrayList<Events>();
		try {
			Query query = getHibernateTemplate().getSessionFactory()
					.openSession()
					.createQuery("from Events where school.schoolId=:schoolId and announcementDate=current_date");
			query.setParameter("schoolId", school.getSchoolId());
			events = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return events;
	}
	
	@Override
	public boolean saveNotificationStatus(List<UserRegistration> userRegLt, Announcements announcements){
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try {
			NotificationStatus ns = null;
			for (UserRegistration userRegistration : userRegLt) {
				 ns = new NotificationStatus();
				 ns.setAnnouncements(announcements);
				 ns.setUserRegistration(userRegistration);
				 ns.setStatus(WebKeys.UN_READ);
				 session.saveOrUpdate(ns);
			}
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<NotificationStatus> getNotificationStatusByRegId(String status){
		 UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		 SchoolSchedule schoolSchedule = adminSchedulerservice.getSchoolSchedule(userReg.getSchool());
		
		List<NotificationStatus> notificationStatusLt= new ArrayList<NotificationStatus>();
		try {
			notificationStatusLt = (List<NotificationStatus>) getHibernateTemplate().find("FROM NotificationStatus WHERE userRegistration.regId="+userReg.getRegId()+" and status ='"+status+"' and announcements.announceDate BETWEEN '"+schoolSchedule.getStartDate()+"' AND '"+schoolSchedule.getEndDate()+"'");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return notificationStatusLt;
	}
	
	@Override
	public boolean setNotificationReadStatus(long notificationStatusId){
		try {
			GregorianCalendar cal = new GregorianCalendar();
			long millis = cal.getTimeInMillis();
			Timestamp currentTimeStamp = new Timestamp(millis);
			String updateQuery = "update notification_status set status='"+WebKeys.READ+"', read_time_stamp='"+currentTimeStamp+"' where notification_status_id="+notificationStatusId;
			jdbcTemplate.update(updateQuery);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean changeReadStatus(long notificationStatusId, String status){
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try {
			NotificationStatus ns = getNotificationStatusByNotificationStatusId(notificationStatusId);
			ns.setStatus(status);
			session.saveOrUpdate(ns);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public NotificationStatus getNotificationStatusByNotificationStatusId(long notificationStatusId){
		List<NotificationStatus> notificationStatusLt= new ArrayList<NotificationStatus>();
		try {
			notificationStatusLt = (List<NotificationStatus>) getHibernateTemplate().find("FROM NotificationStatus WHERE notificationStatusId="+notificationStatusId);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		if(notificationStatusLt.size() > 0)
			return notificationStatusLt.get(0);
		else
			return new NotificationStatus();
	}
}
