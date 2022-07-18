package com.lp.admin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.appadmin.dao.AnnouncementsNEventsDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.common.service.FileService;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.Announcements;
import com.lp.model.EventStatus;
import com.lp.model.Events;
import com.lp.model.NotificationStatus;
import com.lp.model.School;
import com.lp.model.UserRegistration;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "announcementsNEventsService")
public class AnnouncementsNEventsServiceImpl implements
		AnnouncementsNEventsService {
	
	static final Logger logger = Logger.getLogger(AnnouncementsNEventsServiceImpl.class);
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private AnnouncementsNEventsDAO announceDao;
	@Autowired
	private FileService fileService;
	@Autowired
	private UserRegistrationDAO userRegistrationDAO;
	@Autowired
	private AndroidPushNotificationsService apns;

	@Override
	public boolean saveorUpdateAnnouncements(Announcements announce,
			HttpSession session) {
		boolean stat;
		UserRegistration userReg = null;
		if (session.getAttribute(WebKeys.USER_REGISTRATION_OBJ) != null) {
			userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			announce.setSchool(userReg.getSchool());
		}
		stat=announceDao.saveorUpdateAnnouncements(announce);
		if(stat)
			apns.sendSchoolAnnouncements("School Announcements",announce.getSchool().getSchoolId(),announce.getAnnoncementName());
		return stat;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAnnouncementsBySchool(School schoool) {
		return announceDao.getAnnouncementsBySchool(schoool);
	}

	@Override
	public Announcements getAnnouncements(long announcementId) {
		return announceDao.getAnnouncements(announcementId);
	}

	@Override
	public boolean checkAnnouncements(String announcementName) {
		return announceDao.checkAnnouncements(announcementName);
	}

	@Override
	public boolean saveorUpdateEvents(Events event, HttpSession session) {
		boolean stat;
		List<UserRegistration> parentRegIds=new ArrayList<UserRegistration>();
		UserRegistration userReg = null;
		if (session.getAttribute(WebKeys.USER_REGISTRATION_OBJ) != null) {
			userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			event.setSchool(userReg.getSchool());
		}
		stat= announceDao.saveorUpdateEvents(event);
		if(stat){
			apns.sendEventNotification("School Events",event.getSchool().getSchoolId(),event.getEventName());
		}
		return stat;
	}

	@Override
	public List<Events> getEventsBySchool(School schoool) {
		return announceDao.getEventsBySchool(schoool);
	}

	@Override
	public Events getEvents(long eventId) {
		return announceDao.getEvents(eventId);
	}
	
	@Override
	public void deleteAnnouncements(Announcements announce){
		String announcementPath = FileUploadUtil.getSchoolCommonFilesPath(announce.getSchool(), request) + File.separator + WebKeys.SCHOOL_ANNOUNCEMENTS_FOLDER + File.separator + announce.getAnnouncementId();
		fileService.deleteFolder(announcementPath);
		announceDao.deleteAnnouncement(announce);
	}
	
	@Override
	public void deleteNotificationStatus(long announcementId){
		announceDao.deleteNotificationStatus(announcementId);
	}
	
	@Override
	public void deleteEvents(Events events){
		announceDao.deleteEvents(events);
	}

	@Override
	public boolean checkEvents(String eventName, long eventId, long schoolId) {
		return announceDao.checkEvents(eventName,eventId,schoolId);
	}

	@Override
	public List<NotificationStatus> getCurrentAnnouncementsBySchool(School schoool) {
		return announceDao.getCurrentAnnouncementsBySchool(schoool);
	}
	
	@Override
	public List<Announcements> getAnnouncementsBySchoolAdmin(School school){
		return announceDao.getAnnouncementsBySchoolAdmin(school);
	}
	
	@Override
	public List<Events> getCurrentEventsBySchool(School schoool) {
		List<Events> events =new ArrayList<Events>();
		try {
			events = announceDao.getCurrentEventsBySchool(schoool);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return events;
	}
	
	@Override
	public boolean saveNotificationStatus(List<UserRegistration> userRegLt, Announcements announcements){
		return announceDao.saveNotificationStatus(userRegLt, announcements);
	}
	
	@Override
	public List<EventStatus> getEventStatus(List<UserRegistration> userRegLt, Events event){
		List<EventStatus> eventStatus = new ArrayList<EventStatus>();
		EventStatus ns = null;
		for (UserRegistration userRegistration : userRegLt) {
			 ns = new EventStatus();
			 ns.setEvent(event);
			 ns.setUserRegistration(userRegistration);
			 ns.setStatus(WebKeys.UN_READ);
			 eventStatus.add(ns); 
		}
		return eventStatus;
	}
	
	@Override
	@RemoteMethod
	public List<NotificationStatus> getNotificationStatusByRegId(String status){
		return announceDao.getNotificationStatusByRegId(status);
	}
	
	@Override
	public boolean setNotificationReadStatus(long notificationStatusId){
		return announceDao.setNotificationReadStatus(notificationStatusId);
	}
	
	@Override
	@RemoteMethod
	public boolean changeReadStatus(long notificationStatusId, String status){
		return announceDao.changeReadStatus(notificationStatusId, status);
	}
}
