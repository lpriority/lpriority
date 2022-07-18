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
 * SecurityQuestion generated by hbm2java
 */
@Entity
@Table(name = "security_question", uniqueConstraints = @UniqueConstraint(columnNames = "question"))
public class SecurityQuestion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long securityQuestionId;
	@NotNull
	@Size(min = 1, max = 45)
	private String question;
	private Date createDate;
	private Date changeDate;
	
	public SecurityQuestion() {
	}

	public SecurityQuestion(long securityQuestionId, String question,
			Date createDate, Date changeDate) {
		this.securityQuestionId = securityQuestionId;
		this.question = question;
		this.createDate = createDate;
		this.changeDate = changeDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "security_question_id", unique = true, nullable = false)
	public long getSecurityQuestionId() {
		return this.securityQuestionId;
	}

	public void setSecurityQuestionId(long securityQuestionId) {
		this.securityQuestionId = securityQuestionId;
	}

	@Column(name = "question", unique = true, nullable = false, length = 45)
	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
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
