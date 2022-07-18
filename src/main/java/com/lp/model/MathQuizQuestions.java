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

@Entity
@Table(name = "math_quiz_questions")
public class MathQuizQuestions {

	private long quizQuestionsId;
	private MathQuiz mathQuiz;
	private MathConversionTypes mathConversionTypes;
	private String actualAnswer;
	private String isBlank;
	
	
	
	public MathQuizQuestions() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MathQuizQuestions(long quizQuestionsId,MathQuiz mathQuiz,
			 MathConversionTypes mathConversionTypes, String actualAnswer, String isBlank) {
		super();
		this.quizQuestionsId = quizQuestionsId;
		this.mathQuiz = mathQuiz;
		this.mathConversionTypes = mathConversionTypes;
		this.actualAnswer = actualAnswer;
		this.isBlank = isBlank;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "quiz_questions_id", unique = true, nullable = false)
	public long getQuizQuestionsId() {
		return quizQuestionsId;
	}
	
	public void setQuizQuestionsId(long quizQuestionsId) {
		this.quizQuestionsId = quizQuestionsId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "math_quiz_id", nullable = false)
	public MathQuiz getMathQuiz() {
		return mathQuiz;
	}
	
	public void setMathQuiz(MathQuiz mathQuiz) {
		this.mathQuiz = mathQuiz;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "conversion_type_id", nullable = false)
	public MathConversionTypes getMathConversionTypes() {
		return mathConversionTypes;
	}
	
	public void setMathConversionTypes(MathConversionTypes mathConversionTypes) {
		this.mathConversionTypes = mathConversionTypes;
	}
	
	@Column(name = "actual_answer", nullable = false, length = 45)
	public String getActualAnswer() {
		return actualAnswer;
	}
	
	public void setActualAnswer(String actualAnswer) {
		this.actualAnswer = actualAnswer;
	}
	
	@Column(name = "is_blank", nullable = false, length = 5)
	public String getIsBlank() {
		return isBlank;
	}
	
	public void setIsBlank(String isBlank) {
		this.isBlank = isBlank;
	}
	
	
}
