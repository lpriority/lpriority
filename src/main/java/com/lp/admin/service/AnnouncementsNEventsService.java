package com.lp.admin.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.lp.model.Announcements;
import com.lp.model.EventStatus;
import com.lp.model.Events;
import com.lp.model.NotificationStatus;
import com.lp.model.School;
import com.lp.model.UserRegistration;

public interface AnnouncementsNEventsService {
	public boolean saveorUpdateAnnouncements(Announcements announce, HttpSession session);
	@SuppressWarnings("rawtypes")
	public List getAnnouncementsBySchool(School schoool);
	public Announcements getAnnouncements(long announcementId);
	public boolean checkAnnouncements(String announcementName);
	public void deleteAnnouncements(Announcements announce);
	public List<Announcements> getAnnouncementsBySchoolAdmin(School school);
	public List<NotificationStatus> getCurrentAnnouncementsBySchool(School schoool);
	public boolean saveNotificationStatus(List<UserRegistration> userRegLt, Announcements announcements);
	public List<NotificationStatus> getNotificationStatusByRegId(String status);
	public void deleteNotificationStatus(long announcementId);
	
	public boolean saveorUpdateEvents(Events event, HttpSession session);
	public List<Events> getEventsBySchool(School schoool);
	public Events getEvents(long eventId);
	public void deleteEvents(Events events);
	public boolean checkEvents(String eventName, long eventId, long schoolId);
	public List<Events> getCurrentEventsBySchool(School schoool);
	public boolean setNotificationReadStatus(long notificationStatusId);
	public boolean changeReadStatus(long notificationStatusId, String status);
	
	public List<EventStatus> getEventStatus(List<UserRegistration> userRegLt, Events event);
}
