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

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * GradeClasses generated by hbm2java
 */
@Entity
@Table(name = "grade_classes")
public class GradeClasses implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long gradeClassId;
	@JsonManagedReference
	private Grade grade;
	@JsonManagedReference
	private StudentClass studentClass;
	private String status;
	private Date createDate;
	private Date changeDate;

	public GradeClasses() {
	}

	public GradeClasses(long gradeClassId, Grade grade,
			StudentClass studentClass, String status, Date createDate,
			Date changeDate) {
		this.gradeClassId = gradeClassId;
		this.grade = grade;
		this.studentClass = studentClass;
		this.status = status;
		this.createDate = createDate;
		this.changeDate = changeDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "grade_class_id", unique = true, nullable = false)
	public long getGradeClassId() {
		return this.gradeClassId;
	}

	public void setGradeClassId(long gradeClassId) {
		this.gradeClassId = gradeClassId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id", nullable = false)
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "class_id", nullable = false)
	public StudentClass getStudentClass() {
		return this.studentClass;
	}

	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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
