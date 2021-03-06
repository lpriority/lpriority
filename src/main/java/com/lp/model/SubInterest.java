package com.lp.model;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * SubInterest generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sub_interest")
public class SubInterest implements java.io.Serializable {

	private Long subInterestId;
	@JsonManagedReference
	private Interest interest;
	@Transient
	private String interestId;
	private String subInterest;
	private Date createDate;
	private Date changeDate;

	public SubInterest() {
	}

	public SubInterest(Interest interest, String subInterest, Date createDate,
			Date changeDate) {
		this.interest = interest;
		this.subInterest = subInterest;
		this.createDate = createDate;
		this.changeDate = changeDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sub_interest_id", unique = true, nullable = false)
	public Long getSubInterestId() {
		return this.subInterestId;
	}

	public void setSubInterestId(Long subInterestId) {
		this.subInterestId = subInterestId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "interest_id", nullable = false)
	public Interest getInterest() {
		return this.interest;
	}

	public void setInterest(Interest interest) {
		this.interest = interest;
	}

	@Column(name = "sub_interest", nullable = false, length = 45)
	public String getSubInterest() {
		return this.subInterest;
	}

	public void setSubInterest(String subInterest) {
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

	@Transient
	public String getInterestId() {
		return interestId;
	}

	public void setInterestId(String interestId) {
		this.interestId = interestId;
	}


}
