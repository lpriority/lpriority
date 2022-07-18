package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stem_questions_type")
public class StemQuestionsType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long stemQuesTypeId;
	private String stemQuesType;
	public StemQuestionsType() {
	}

	public StemQuestionsType(long stemQuesTypeId, String stemQuesType) {
		this.stemQuesTypeId = stemQuesTypeId;
		this.stemQuesType = stemQuesType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_ques_typeid", unique = true, nullable = false)
	public long getStemQuesTypeId() {
		return this.stemQuesTypeId;
	}

	public void setStemQuesTypeId(long stemQuesTypeId) {
		this.stemQuesTypeId = stemQuesTypeId;
	}

	@Column(name = "stem_ques_type",length = 500)
	public String getStemQuesType() {
		return this.stemQuesType;
	}

	public void setStemQuesType(String stemQuesType) {
		this.stemQuesType = stemQuesType;
	}
}
