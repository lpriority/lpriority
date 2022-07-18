 
package com.lp.model;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "stem_questions")
public class StemQuestions implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long stemQuestionId;
	private String stemQuestion ;
	@JsonBackReference
	private StemUnit stemUnit;
	@JsonIgnore
	private StemQuestionsType stemQuestionsType;
		
	@Transient
	private List<Lesson> lessonsList = new ArrayList<Lesson>(0);
	
	public StemQuestions() {
	}

	public StemQuestions(Long stemQuestionId,StemUnit stemUnit,StemQuestionsType stemQuestionsType,String stemQuestion)
	{
		this.stemQuestionId = stemQuestionId;
		this.stemUnit = stemUnit;
		this.stemQuestionsType=stemQuestionsType;
		this.stemQuestion = stemQuestion;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_question_id", unique = true, nullable = false)
	public Long getStemQuestionId() {
		return this.stemQuestionId;
	}

	public void setStemQuestionId(Long stemQuestionId) {
		this.stemQuestionId = stemQuestionId;
	}

	@Column(name = "stem_question", length = 45)
	public String getStemQuestion() {
		return this.stemQuestion;
	}

	public void setStemQuestion(String stemQuestion) {
		this.stemQuestion = stemQuestion;
	}
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stem_unit_id")
	public StemUnit getStemUnit() {
		return stemUnit;
	}

	public void setStemUnit(StemUnit stemUnit) {
		this.stemUnit = stemUnit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stem_ques_typeid")
	public StemQuestionsType getStemQuestionsType() {
		return stemQuestionsType;
	}

	public void setStemQuestionsType(StemQuestionsType stemQuestionsType) {
		this.stemQuestionsType = stemQuestionsType;
	}
	
	
}
