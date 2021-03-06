package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * UserInterests generated by hbm2java
 */
@Entity
@Table(name = "user_interests", uniqueConstraints = @UniqueConstraint(columnNames = {
		"reg_id", "interest_id", "sub_interest_id" }))
public class UserInterests implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long userInterestsId;
	@JsonManagedReference
	private Interest interest;
	@JsonManagedReference
	private UserRegistration userRegistration;
	@JsonManagedReference
	private SubInterest subInterest;
	private Date createDate;
	private Date changeDate;
	private String otherUserInterest;

	public UserInterests() {
	}

	public UserInterests(long userInterestsId, Interest interest,
			UserRegistration userRegistration, Date createDate, Date changeDate) {
		this.userInterestsId = userInterestsId;
		this.interest = interest;
		this.userRegistration = userRegistration;
		this.createDate = createDate;
		this.changeDate = changeDate;
	}

	public UserInterests(long userInterestsId, Interest interest,
			UserRegistration userRegistration, SubInterest subInterest,
			Date createDate, Date changeDate, String otherUserInterest) {
		this.userInterestsId = userInterestsId;
		this.interest = interest;
		this.userRegistration = userRegistration;
		this.subInterest = subInterest;
		this.createDate = createDate;
		this.changeDate = changeDate;
		this.otherUserInterest = otherUserInterest;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_interests_id", unique = true, nullable = false)
	public long getUserInterestsId() {
		return this.userInterestsId;
	}

	public void setUserInterestsId(long userInterestsId) {
		this.userInterestsId = userInterestsId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "interest_id", nullable = false)
	public Interest getInterest() {
		return this.interest;
	}

	public void setInterest(Interest interest) {
		this.interest = interest;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reg_id", nullable = false)
	public UserRegistration getUserRegistration() {
		return this.userRegistration;
	}

	public void setUserRegistration(UserRegistration userRegistration) {
		this.userRegistration = userRegistration;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sub_interest_id")
	public SubInterest getSubInterest() {
		return this.subInterest;
	}

	public void setSubInterest(SubInterest subInterest) {
		this.subInterest = subInterest;
	}

	@Temporal(TemporalType.DATE)
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

	@Column(name = "other_user_interest", length = 45)
	public String getOtherUserInterest() {
		return this.otherUserInterest;
	}

	public void setOtherUserInterest(String otherUserInterest) {
		this.otherUserInterest = otherUserInterest;
	}

}
