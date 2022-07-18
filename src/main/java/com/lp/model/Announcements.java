package com.lp.model;

// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Announcements generated by hbm2java
 */
@Entity
@Table(name = "announcements")
public class Announcements implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long announcementId;
	@JsonManagedReference
	private School school;
	private String annoncementName;
	private String announceDescription;
    private Date announceDate;
	private String urlLinks;
	private Date createDate;
	private Date changeDate;
	@Transient
	private String operationMode;	
	@JsonManagedReference
	private User createdFor;
	private String fileName;
	@JsonBackReference
	List<NotificationStatus> notificationStatusLt = new ArrayList<NotificationStatus>();
	private String readStatus;

	public Announcements() {
	}

	public Announcements(long announcementId, Date createDate, Date changeDate) {
		this.announcementId = announcementId;
		this.createDate = createDate;
		this.changeDate = changeDate;
	}

	public Announcements(long announcementId, School school,
			String annoncementName, String announceDescription,
			Date announceDate, String urlLinks, Date createDate,
			Date changeDate, User createdFor, String readStatus) {
		this.announcementId = announcementId;
		this.school = school;
		this.annoncementName = annoncementName;
		this.announceDescription = announceDescription;
		this.announceDate = announceDate;
		this.urlLinks = urlLinks;
		this.createDate = createDate;
		this.changeDate = changeDate;
		this.createdFor = createdFor;	
		this.readStatus = readStatus;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "announcement_id", unique = true, nullable = false)
	public long getAnnouncementId() {
		return this.announcementId;
	}

	public void setAnnouncementId(long announcementId) {
		this.announcementId = announcementId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "school_id")
	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	@Column(name = "annoncement_name", length = 300)
	public String getAnnoncementName() {
		return this.annoncementName;
	}

	public void setAnnoncementName(String annoncementName) {
		this.annoncementName = annoncementName;
	}

	@Column(name = "announce_description", length = 1000)
	public String getAnnounceDescription() {
		return this.announceDescription;
	}

	public void setAnnounceDescription(String announceDescription) {
		this.announceDescription = announceDescription;
	}


	@Column(name = "announce_date", length = 10)
	public Date getAnnounceDate() {
		return this.announceDate;
	}

	public void setAnnounceDate(Date announceDate) {
		this.announceDate = announceDate;
	}
	
	@Column(name = "url_links", length = 500)
	public String getUrlLinks() {
		return urlLinks;
	}

	public void setUrlLinks(String urlLinks) {
		this.urlLinks = urlLinks;
	}


	@Column(name = "create_date", nullable = false, length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "change_date", nullable = false, length = 19)
	public Date getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	@Transient
	public String getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_for")
	public User getCreatedFor() {
		return createdFor;
	}

	public void setCreatedFor(User createdFor) {
		this.createdFor = createdFor;
	}
	
	@Column(name = "file_name", length = 300)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "announcements")
	public List<NotificationStatus> getNotificationStatusLt() {
		return notificationStatusLt;
	}

	public void setNotificationStatusLt(
			List<NotificationStatus> notificationStatusLt) {
		this.notificationStatusLt = notificationStatusLt;
	}
	
	@Column(name = "read_status", length = 10)
	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	
}
