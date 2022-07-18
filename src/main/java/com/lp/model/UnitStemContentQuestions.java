
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "unit_stem_content_quests")
public class UnitStemContentQuestions implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long unitStemContentQuesId;
	private String contentQuestion;
	@JsonIgnore
	private UnitStemAreas unitStemAreas;
	

	public UnitStemContentQuestions() {
	}

	public UnitStemContentQuestions(long unitStemContentQuesId,String contentQuestion, UnitStemAreas unitStemAreas) 
	{
		this.unitStemContentQuesId = unitStemContentQuesId;
		this.contentQuestion = contentQuestion;
		this.unitStemAreas = unitStemAreas;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "unit_stem_cont_quesid", unique = true, nullable = false)
	public long getUnitStemContentQuesId() {
		return unitStemContentQuesId;
	}

	public void setUnitStemContentQuesId(long unitStemContentQuesId) {
		this.unitStemContentQuesId = unitStemContentQuesId;
	}
	@Column(name = "content_question", length = 500)
	public String getContentQuestion() {
		return contentQuestion;
	}

	public void setContentQuestion(String contentQuestion) {
		this.contentQuestion = contentQuestion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_stem_area_id")
	public UnitStemAreas getUnitStemAreas() {
		return unitStemAreas;
	}

	public void setUnitStemAreas(UnitStemAreas unitStemAreas) {
		this.unitStemAreas = unitStemAreas;
	}

	
}
