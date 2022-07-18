package com.lp.model;

// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User generated by hbm2java
 */

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "user_type"))
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long userTypeid;
	@NotNull
	@Size(min = 1, max = 45)
	private String userType;
	private Date createDate;
	private Date changeDate;

	public User() {
	}

	public User(long userTypeid, String userType, Date createDate,
			Date changeDate) {
		this.userTypeid = userTypeid;
		this.userType = userType;
		this.createDate = createDate;
		this.changeDate = changeDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_typeid", unique = true, nullable = false)
	public long getUserTypeid() {
		return this.userTypeid;
	}

	public void setUserTypeid(long userTypeid) {
		this.userTypeid = userTypeid;
	}

	@Column(name = "user_type", unique = true, nullable = false, length = 45)
	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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
}
