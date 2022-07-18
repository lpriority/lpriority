package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Minutes generated by hbm2java
 */
@Entity
@Table(name = "minutes")
public class Minutes implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long minId;
	@NotNull
	@Size(min = 1, max = 20)
	private String minute;

	public Minutes() {
	}

	public Minutes(long minId, String minute) {
		this.minId = minId;
		this.minute = minute;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "min_id", unique = true, nullable = false)
	public long getMinId() {
		return this.minId;
	}

	public void setMinId(long minId) {
		this.minId = minId;
	}

	@Column(name = "minute", nullable = false, length = 20)
	public String getMinute() {
		return this.minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

}
