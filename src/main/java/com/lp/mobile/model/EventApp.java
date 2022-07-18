package com.lp.mobile.model;

import java.util.Date;

public class EventApp {
	
	private long eventId;
	private String eventName;
	private String eventDescription;
	private String eventTime;
	private Date announcementDate;
	private Date lastDate;
	private Date  scheduleDate;
	private String venue;
	private String contactPerson;
	private String readStatus;	
	
	public EventApp() {
	}

	public EventApp(long eventId, String eventName, String eventDescription,
			String eventTime, Date announcementDate, Date lastDate,
			Date scheduleDate, String venue, String contactPerson,
			String readStatus) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDescription = eventDescription;
		this.eventTime = eventTime;
		this.announcementDate = announcementDate;
		this.lastDate = lastDate;
		this.scheduleDate = scheduleDate;
		this.venue = venue;
		this.contactPerson = contactPerson;
		this.readStatus = readStatus;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public Date getAnnouncementDate() {
		return announcementDate;
	}

	public void setAnnouncementDate(Date announcementDate) {
		this.announcementDate = announcementDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	
		
}
