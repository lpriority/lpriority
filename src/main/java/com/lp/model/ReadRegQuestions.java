package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "read_reg_questions")
public class ReadRegQuestions implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long readRegQuestionsId;
	@JsonManagedReference
	private Student student;
	@JsonBackReference
	private ReadRegMaster readRegMaster;
	@JsonManagedReference
	private ReadRegQuiz readRegQuiz;
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String answer;
	private Date createDate;
	@Transient
	private long rubricScore;
	@JsonBackReference
	List<ReadRegAnswers> readRegAnswers = new ArrayList<ReadRegAnswers>();
	
	@JsonManagedReference
	private Grade grade;
	
	public ReadRegQuestions() {
	}
	
	public ReadRegQuestions(long readRegQuestionsId, Student student, ReadRegMaster readRegMaster,
			ReadRegQuiz readRegQuiz, String question, String option1, String option2, String option3, String option4,
			String answer, Date createDate, Grade grade) {
		this.readRegQuestionsId = readRegQuestionsId;
		this.student = student;
		this.readRegMaster = readRegMaster;
		this.readRegQuiz = readRegQuiz;
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.answer = answer;
		this.createDate = createDate;
		this.grade = grade;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "read_reg_questions_id", unique = true, nullable = false)
	public long getReadRegQuestionsId() {
		return readRegQuestionsId;
	}

	public void setReadRegQuestionsId(long readRegQuestionsId) {
		this.readRegQuestionsId = readRegQuestionsId;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "title_id")
	public ReadRegMaster getReadRegMaster() {
		return readRegMaster;
	}

	public void setReadRegMaster(ReadRegMaster readRegMaster) {
		this.readRegMaster = readRegMaster;
	}	

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_num")
	public ReadRegQuiz getReadRegQuiz() {
		return readRegQuiz;
	}

	public void setReadRegQuiz(ReadRegQuiz readRegQuiz) {
		this.readRegQuiz = readRegQuiz;
	}
	
	@Column(name = "question", nullable = false, length = 100)
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	@Column(name = "option1", nullable = false, length = 15)
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	
	@Column(name = "option2", nullable = false, length = 15)
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	
	@Column(name = "option3", nullable = false, length = 15)
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	
	@Column(name = "option4", nullable = false, length = 15)
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	
	@Column(name = "answer", nullable = false, length = 15)
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Transient
	public long getRubricScore() {
		return rubricScore;
	}

	public void setRubricScore(long rubricScore) {
		this.rubricScore = rubricScore;
	}	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	@OneToMany(mappedBy = "readRegQuestions", fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<ReadRegAnswers> getReadRegAnswers() {
		return readRegAnswers;
	}

	public void setReadRegAnswers(List<ReadRegAnswers> readRegAnswers) {
		this.readRegAnswers = readRegAnswers;
	}
	
}
