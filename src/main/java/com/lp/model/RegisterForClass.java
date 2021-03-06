package com.lp.model;

// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * RegisterForClass generated by hbm2java
 */
@Entity
@Table(name = "register_for_class")
public class RegisterForClass implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RegisterForClassId id;
	@JsonManagedReference
	private Student student;
	@JsonManagedReference
	private GradeClasses gradeClasses;
	@JsonManagedReference
	private Section section;
	@JsonManagedReference
	private GradeLevel gradeLevel;
	@JsonManagedReference
	private PerformancetaskGroups performancetaskGroups;
	@JsonManagedReference
	private RtiGroups rtiGroups;
	@JsonManagedReference
	private ClassStatus classStatus;
	private String status;
	private String desktopStatus;
	private String classStatus_1;
	private Date createDate;
	private Date changeDate;
	@JsonManagedReference
	private Teacher teacher;
	private String readStatus;

	public RegisterForClass() {
	}

	public RegisterForClass(RegisterForClassId id, Student student,
			GradeClasses gradeClass, Section section) {
		this.id = id;
		this.student = student;
		this.gradeClasses = gradeClass;
		this.section = section;
	}

	public RegisterForClass(RegisterForClassId id, Student student,
			GradeClasses gradeClass, Section section,
			PerformancetaskGroups performancetaskGroups, RtiGroups rtiGroups,
			ClassStatus classStatus, String status, String desktopStatus,
			String classStatus_1, Date createDate, Date changeDate,
			GradeLevel gradeLevel, String readStatus) {
		this.id = id;
		this.student = student;
		this.gradeClasses = gradeClass;
		this.section = section;
		this.gradeLevel = gradeLevel;
		this.performancetaskGroups = performancetaskGroups;
		this.rtiGroups = rtiGroups;
		this.classStatus = classStatus;
		this.status = status;
		this.desktopStatus = desktopStatus;
		this.classStatus_1 = classStatus_1;
		this.createDate = createDate;
		this.changeDate = changeDate;
		this.readStatus = readStatus;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "gradeClassId", column = @Column(name = "grade_class_id", nullable = false)),
			@AttributeOverride(name = "studentId", column = @Column(name = "student_id", nullable = false)) })
	public RegisterForClassId getId() {
		return this.id;
	}

	public void setId(RegisterForClassId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id", nullable = false, insertable = false, updatable = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_class_id", nullable = false, insertable = false, updatable = false)

	
	public GradeClasses getGradeClasses() {

		return this.gradeClasses;
	}

	public void setGradeClasses(GradeClasses gradeClass) {
		this.gradeClasses = gradeClass;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "section_id", nullable = true)
	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "performance_group_id")
	public PerformancetaskGroups getPerformancetaskGroups() {
		return this.performancetaskGroups;
	}

	public void setPerformancetaskGroups(
			PerformancetaskGroups performancetaskGroups) {
		this.performancetaskGroups = performancetaskGroups;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rti_group_id")
	public RtiGroups getRtiGroups() {
		return this.rtiGroups;
	}

	public void setRtiGroups(RtiGroups rtiGroups) {
		this.rtiGroups = rtiGroups;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cs_id")
	public ClassStatus getClassStatus() {
		return this.classStatus;
	}

	public void setClassStatus(ClassStatus classStatus) {
		this.classStatus = classStatus;
	}
	
	@Column(name = "status", length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "desktop_status", length = 20)
	public String getDesktopStatus() {
		return this.desktopStatus;
	}

	public void setDesktopStatus(String desktopStatus) {
		this.desktopStatus = desktopStatus;
	}

	@Column(name = "class_status", length = 30)
	public String getClassStatus_1() {
		return this.classStatus_1;
	}

	public void setClassStatus_1(String classStatus_1) {
		this.classStatus_1 = classStatus_1;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", length = 10)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "change_date", length = 19)
	public Date getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_level_id", nullable = false, insertable = true, updatable = true)
	public GradeLevel getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(GradeLevel gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Column(name = "read_status", length = 10)
	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	
	
	
}
