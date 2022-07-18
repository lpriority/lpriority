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
 * AcademicPerformance generated by hbm2java
 */
@Entity
@Table(name = "academic_performance")
public class AcademicPerformance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long academicId;
	@NotNull
	@Size(min = 5, max = 20)
	private String academicLevel;
	@NotNull
	@Size(min = 1, max = 1)
	private String academicGrade;
	@NotNull
	@Size(min = 5, max = 260)
	private String academicDescription;

	public AcademicPerformance() {
	}

	public AcademicPerformance(long academicId, String academicLevel,
			String academicGrade, String academicDescription) {
		this.academicId = academicId;
		this.academicLevel = academicLevel;
		this.academicGrade = academicGrade;
		this.academicDescription = academicDescription;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "academic_id", unique = true, nullable = false)
	public long getAcademicId() {
		return this.academicId;
	}

	public void setAcademicId(long academicId) {
		this.academicId = academicId;
	}

	@Column(name = "academic_level", nullable = false, length = 20)
	public String getAcademicLevel() {
		return this.academicLevel;
	}

	public void setAcademicLevel(String academicLevel) {
		this.academicLevel = academicLevel;
	}

	@Column(name = "academic_grade", nullable = false, length = 1)
	public String getAcademicGrade() {
		return this.academicGrade;
	}

	public void setAcademicGrade(String academicGrade) {
		this.academicGrade = academicGrade;
	}

	@Column(name = "academic_description", nullable = false, length = 260)
	public String getAcademicDescription() {
		return this.academicDescription;
	}

	public void setAcademicDescription(String academicDescription) {
		this.academicDescription = academicDescription;
	}
}
