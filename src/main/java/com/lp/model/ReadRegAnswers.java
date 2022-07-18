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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "read_reg_answers")
public class ReadRegAnswers implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long readRegAnswersId;
	@JsonManagedReference
	private ReadRegQuestions readRegQuestions;
	private String answer;
	private int mark;
	private Date testDate;
	@JsonManagedReference
	@JsonIgnore
	private Student currentStudent;
	@JsonManagedReference
	private Grade grade;
	
	public ReadRegAnswers() {
	}

	public ReadRegAnswers(long readRegAnswersId, ReadRegQuestions readRegQuestions, String answer, int mark,
			Date testDate, Student currentStudent, Grade grade) {
		this.readRegAnswersId = readRegAnswersId;
		this.readRegQuestions = readRegQuestions;
		this.answer = answer;
		this.mark = mark;
		this.testDate = testDate;
		this.currentStudent = currentStudent;
		this.grade = grade;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "read_reg_answers_id", nullable = false, length = 20)
	public long getReadRegAnswersId() {
		return readRegAnswersId;
	}
	
	public void setReadRegAnswersId(long readRegAnswersId) {
		this.readRegAnswersId = readRegAnswersId;
	}
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "read_reg_questions_id")		
	public ReadRegQuestions getReadRegQuestions() {
		return readRegQuestions;
	}

	public void setReadRegQuestions(ReadRegQuestions readRegQuestions) {
		this.readRegQuestions = readRegQuestions;
	}

	@Column(name = "answer", length = 10)
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Column(name = "mark")
	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
	
	@Column(name = "test_date")
	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "current_student_id")
	public Student getCurrentStudent() {
		return currentStudent;
	}

	public void setCurrentStudent(Student currentStudent) {
		this.currentStudent = currentStudent;
	}
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}	

}
