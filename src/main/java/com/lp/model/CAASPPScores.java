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
@Table(name = "caaspp_scores")
public class CAASPPScores implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long caaspp_scores_id;
	@JsonManagedReference
	private Teacher teacher;
	@JsonManagedReference
	private Student student;
	@JsonManagedReference
	private Grade grade;
	@JsonManagedReference
	private CAASPPTypes caasppType;
	private Float caasppScore;
	@JsonManagedReference
	private StudentClass studentClass;
	

	public CAASPPScores() {
	}	
		
	public CAASPPScores(long caaspp_scores_id, Teacher teacher,
			Student student, Grade grade, CAASPPTypes caasppType,
			Float caasppScore, StudentClass studentClass) {
		super();
		this.caaspp_scores_id = caaspp_scores_id;
		this.teacher = teacher;
		this.student = student;
		this.grade = grade;
		this.caasppType = caasppType;
		this.caasppScore = caasppScore;
		this.studentClass = studentClass;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "caaspp_scores_id", unique = true, nullable = false)
	public long getCaaspp_scores_id() {
		return caaspp_scores_id;
	}

	public void setCaaspp_scores_id(long caaspp_scores_id) {
		this.caaspp_scores_id = caaspp_scores_id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "caaspp_type_id")
	public CAASPPTypes getCaasppType() {
		return caasppType;
	}

	public void setCaasppType(CAASPPTypes caasppType) {
		this.caasppType = caasppType;
	}

	@Column(name = "caaspp_score")
	public Float getCaasppScore() {
		return caasppScore;
	}

	public void setCaasppScore(Float caasppScore) {
		this.caasppScore = caasppScore;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "class_id")
	public StudentClass getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(StudentClass studentClass) {
		this.studentClass = studentClass;
	}
	
	

}
