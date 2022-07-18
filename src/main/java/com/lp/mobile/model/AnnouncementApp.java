package com.lp.mobile.model;

import java.util.Date;

public class AnnouncementApp {
	
	private long notificationStatusId;
	private String announcementName;
	private String announceDescription;
	private String url;
	private Date announceDate;
	private String readStatus;	
	
	public AnnouncementApp() {
	}

	public AnnouncementApp(long notificationStatusId, String announcementName,
			String announceDescription, String url, Date announceDate,
			String readStatus) {
		this.notificationStatusId = notificationStatusId;
		this.announcementName = announcementName;
		this.announceDescription = announceDescription;
		this.url = url;
		this.announceDate = announceDate;
		this.readStatus = readStatus;
	}

	public long getNotificationStatusId() {
		return notificationStatusId;
	}

	public void setNotificationStatusId(long notificationStatusId) {
		this.notificationStatusId = notificationStatusId;
	}

	public String getAnnouncementName() {
		return announcementName;
	}

	public void setAnnouncementName(String announcementName) {
		this.announcementName = announcementName;
	}

	public String getAnnounceDescription() {
		return announceDescription;
	}

	public void setAnnounceDescription(String announceDescription) {
		this.announceDescription = announceDescription;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getAnnounceDate() {
		return announceDate;
	}

	public void setAnnounceDate(Date announceDate) {
		this.announceDate = announceDate;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

		
}
