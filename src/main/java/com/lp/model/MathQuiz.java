package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "math_quiz")
public class MathQuiz {

	private long mathQuizId;
	private String fraction;
	private long csId;
	private String status;
	
	@JsonBackReference
	private List<MathQuizQuestions> mathQuizQuestions = new ArrayList<MathQuizQuestions>(0);
	public MathQuiz() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MathQuiz(long mathQuizId, String fraction, long csId, String status,List<MathQuizQuestions> mathQuizQuestions ) {
		super();
		this.mathQuizId = mathQuizId;
		this.fraction = fraction;
		this.csId = csId;
		this.status = status;
		this.mathQuizQuestions = mathQuizQuestions;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "math_quiz_id", unique = true, nullable = false)
	public long getMathQuizId() {
		return mathQuizId;
	}
	
	public void setMathQuizId(long mathQuizId) {
		this.mathQuizId = mathQuizId;
	}
	
	@Column(name = "fraction", nullable = false, length = 45)
	public String getFraction() {
		return fraction;
	}
	
	public void setFraction(String fraction) {
		this.fraction = fraction;
	}
	
	@Column(name = "cs_id", nullable = false, length = 20)
	public long getCsId() {
		return csId;
	}
	
	public void setCsId(long csId) {
		this.csId = csId;
	}
	
	@Column(name = "status", nullable = false, length = 45)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "mathQuiz")
	public List<MathQuizQuestions> getMathQuizQuestions() {		 
		 Collections.sort(mathQuizQuestions, new Comparator<MathQuizQuestions>(){
			   public int compare(MathQuizQuestions o1, MathQuizQuestions o2){
			      return (int) (o1.getQuizQuestionsId() - o2.getQuizQuestionsId());
			   }
			});


		return mathQuizQuestions;
	}
	public void setMathQuizQuestions(List<MathQuizQuestions> mathQuizQuestions) {
		this.mathQuizQuestions = mathQuizQuestions;
	}
	
}
