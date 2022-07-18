package com.lp.appadmin.dao;


import java.util.List;

import com.lp.model.Announcements;
import com.lp.model.Events;
import com.lp.model.NotificationStatus;
import com.lp.model.School;
import com.lp.model.UserRegistration;

public interface AnnouncementsNEventsDAO {
	public boolean saveorUpdateAnnouncements(Announcements announcement);
	@SuppressWarnings("rawtypes")
	public List getAnnouncementsBySchool(School schoool);
	public Announcements getAnnouncements(long announcementId);
	public boolean checkAnnouncements(String announcementName);
	public void deleteAnnouncement(Announcements announce);
	public boolean saveNotificationStatus(List<UserRegistration> userRegLt, Announcements announcements);
	public List<NotificationStatus> getNotificationStatusByRegId(String status);
	public void deleteNotificationStatus(long announcementId);
	
	public boolean saveorUpdateEvents(Events events);
	public List<Events> getEventsBySchool(School schoool);
	public Events getEvents(long eventId);
	public boolean checkEvents(String eventName, long eventId, long schoolId);
	public void deleteEvents(Events events);
	
	public List<Announcements> getAnnouncementsBySchoolAdmin(School school);
	public List<NotificationStatus> getCurrentAnnouncementsBySchool(School schoool);
	public List<Events> getCurrentEventsBySchool(School schoool);
	public boolean setNotificationReadStatus(long notificationStatusId);
	
	public boolean changeReadStatus(long notificationStatusId, String status);

}
