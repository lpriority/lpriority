package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * HomeroomClasses generated by hbm2java
 */
@Entity
@Table(name = "homeroom_classes")
public class HomeroomClasses implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long homeroomId;
	@JsonManagedReference
	private Periods periods;
	@JsonManagedReference
	private Section section;
	@JsonManagedReference
	private Teacher teacher;

	public HomeroomClasses() {
	}

	public HomeroomClasses(long homeroomId, Teacher teacher) {
		this.homeroomId = homeroomId;
		this.teacher = teacher;
	}

	public HomeroomClasses(long homeroomId, Periods periods, Section section,
			Teacher teacher) {
		this.homeroomId = homeroomId;
		this.periods = periods;
		this.section = section;
		this.teacher = teacher;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "homeroom_id", unique = true, nullable = false)
	public long getHomeroomId() {
		return this.homeroomId;
	}

	public void setHomeroomId(long homeroomId) {
		this.homeroomId = homeroomId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "period_id")
	public Periods getPeriods() {
		return this.periods;
	}

	public void setPeriods(Periods periods) {
		this.periods = periods;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "section_id")
	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id", nullable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
