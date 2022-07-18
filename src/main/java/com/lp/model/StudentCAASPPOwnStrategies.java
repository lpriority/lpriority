package com.lp.model;

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


@Entity
@Table(name = "stud_caaspp_own_stategies")
public class StudentCAASPPOwnStrategies implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private long studentOwnStrategId;
	
	@JsonManagedReference
	private Student student;
	@JsonManagedReference
	private Grade grade;
    String studentOwnStrategDesc;
	long goalCount;
	
	public StudentCAASPPOwnStrategies() {
	}

	public StudentCAASPPOwnStrategies(long studentOwnStrategId) {
		this.studentOwnStrategId = studentOwnStrategId;
	}

	public StudentCAASPPOwnStrategies(long studentOwnStrategId,Student student,Grade grade,String studentOwnStrategDesc) {
		this.studentOwnStrategId = studentOwnStrategId;
		this.student =student;
		this.grade=grade;
		this.studentOwnStrategDesc=studentOwnStrategDesc;
	  
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stud_own_strategies_id", unique = true, nullable = false)
	public long getStudentOwnStrategId() {
		return this.studentOwnStrategId;
	}

	public void setStudentOwnStrategId(long studentOwnStrategId) {
		this.studentOwnStrategId = studentOwnStrategId;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	@Column(name = "stud_own_strategies_desc", length = 2000)
	public String getStudentOwnStrategDesc() {
		return this.studentOwnStrategDesc;
	}

	public void setStudentOwnStrategDesc(String studentOwnStrategDesc) {
		this.studentOwnStrategDesc = studentOwnStrategDesc;
	}
	
	@Column(name = "goal_count", length = 100)
	public long getGoalCount() {
		return this.goalCount;
	}

	public void setGoalCount(long goalCount) {
		this.goalCount = goalCount;
	}
}
