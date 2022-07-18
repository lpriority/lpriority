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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "student_math_assess_marks")
public class StudentMathAssessMarks {

	private long studentMathAssessMarksId;
	@JsonManagedReference
	private StudentAssignmentStatus studentAssignmentStatus;
	@JsonManagedReference
	private MathQuizQuestions mathQuizQuestions;
	private String answer;
	private Integer mark;
	@Transient
	private String actualAnswer;
	public StudentMathAssessMarks() {
		super();
	}
	public StudentMathAssessMarks(long studentMathAssessMarksId,
			StudentAssignmentStatus studentAssignmentStatus,  MathQuizQuestions mathQuizQuestions, String answer,
			Integer mark) {
		super();
		this.studentMathAssessMarksId = studentMathAssessMarksId;
		this.studentAssignmentStatus = studentAssignmentStatus;
		this.mathQuizQuestions = mathQuizQuestions;
		this.answer = answer;
		this.mark = mark;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "student_math_assess_marks_id", unique = true, nullable = false)
	public long getStudentMathAssessMarksId() {
		return studentMathAssessMarksId;
	}
	
	public void setStudentMathAssessMarksId(long studentMathAssessMarksId) {
		this.studentMathAssessMarksId = studentMathAssessMarksId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_assignment_id", nullable = false)
	public StudentAssignmentStatus getStudentAssignmentStatus() {
		return studentAssignmentStatus;
	}
	
	public void setStudentAssignmentStatus(
			StudentAssignmentStatus studentAssignmentStatus) {
		this.studentAssignmentStatus = studentAssignmentStatus;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "quiz_question_id", nullable = false)
	public MathQuizQuestions getMathQuizQuestions() {
		return mathQuizQuestions;
	}
	
	public void setMathQuizQuestions(MathQuizQuestions mathQuizQuestions) {
		this.mathQuizQuestions = mathQuizQuestions;
	}
	
	@Column(name = "answer", length = 45)
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Column(name = "mark", length = 12)
	public Integer getMark() {
		return mark;
	}
	
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	@Transient
	public String getActualAnswer() {
		return actualAnswer;
	}
	public void setActualAnswer(String actualAnswer) {
		this.actualAnswer = actualAnswer;
	}
	
	
}
