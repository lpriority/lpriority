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

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "star_scores")
public class StarScores implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long starScoresId;
	@JsonManagedReference
	private Student student;
	@JsonManagedReference
	private Grade grade;
	@JsonManagedReference
	private Trimester trimester;
	@JsonManagedReference
	private Teacher teacher;
	private Float score;
	@JsonManagedReference
	private CAASPPTypes caasppType;
	private Date testDate;	

	public StarScores() {
		
	}		

	public StarScores(long starScoresId, Student student, Grade grade,
			Trimester trimester, Teacher teacher, Float score,
			CAASPPTypes caasppType, Date testDate) {
		super();
		this.starScoresId = starScoresId;
		this.student = student;
		this.grade = grade;
		this.trimester = trimester;
		this.teacher = teacher;
		this.score = score;
		this.caasppType = caasppType;
		this.testDate = testDate;
	}



	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "star_scores_id", unique = true, nullable = false)
	public long getStarScoresId() {
		return starScoresId;
	}

	public void setStarScoresId(long starScoresId) {
		this.starScoresId = starScoresId;
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
	@JoinColumn(name = "trimester_id")
	public Trimester getTrimester() {
		return trimester;
	}

	public void setTrimester(Trimester trimester) {
		this.trimester = trimester;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	

	@Column(name = "score")
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "caaspp_types_id")
	public CAASPPTypes getCaasppType() {
		return caasppType;
	}

	public void setCaasppType(CAASPPTypes caasppType) {
		this.caasppType = caasppType;
	}
	
	@Column(name = "test_date")
	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

}
