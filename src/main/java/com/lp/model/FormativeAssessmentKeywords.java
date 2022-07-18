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
@Table(name = "formative_assessment_keywords")
public class FormativeAssessmentKeywords implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long formativeAssessmentKeywordsId;
	@JsonManagedReference
	private FormativeAssessments formativeAssessments;
	private String keyword;

	public FormativeAssessmentKeywords() {
		super();
	}

	public FormativeAssessmentKeywords(long formativeAssessmentKeywordsId,
			FormativeAssessments formativeAssessments, String keyword) {
		super();
		this.formativeAssessmentKeywordsId = formativeAssessmentKeywordsId;
		this.formativeAssessments = formativeAssessments;
		this.keyword = keyword;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "formative_assessment_keywords_id", unique = true, nullable = false)
	public long getFormativeAssessmentKeywordsId() {
		return formativeAssessmentKeywordsId;
	}

	public void setFormativeAssessmentKeywordsId(
			long formativeAssessmentKeywordsId) {
		this.formativeAssessmentKeywordsId = formativeAssessmentKeywordsId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "formative_assessment_id")
	public FormativeAssessments getFormativeAssessments() {
		return formativeAssessments;
	}

	public void setFormativeAssessments(
			FormativeAssessments formativeAssessments) {
		this.formativeAssessments = formativeAssessments;
	}

	@Column(name = "keyword", nullable = false, length = 45)
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
