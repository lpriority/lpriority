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
@Table(name = "formative_assessment_rubric")
public class FormativeAssessmentRubric implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long formativeAssessmentRubricId;
	@JsonManagedReference
	private FormativeAssessmentCategory formativeAssessmentCategory;
	private Integer score;
	private String description;

	public FormativeAssessmentRubric() {
		super();
	}

	public FormativeAssessmentRubric(long formativeAssessmentRubricId,
			FormativeAssessmentCategory formativeAssessmentCategory,
			Integer score, String description) {
		super();
		this.formativeAssessmentRubricId = formativeAssessmentRubricId;
		this.formativeAssessmentCategory = formativeAssessmentCategory;
		this.score = score;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "formative_assessment_rubric_id", unique = true, nullable = false)
	public long getFormativeAssessmentRubricId() {
		return formativeAssessmentRubricId;
	}

	public void setFormativeAssessmentRubricId(long formativeAssessmentRubricId) {
		this.formativeAssessmentRubricId = formativeAssessmentRubricId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "formative_assessment_category_id")
	public FormativeAssessmentCategory getFormativeAssessmentCategory() {
		return formativeAssessmentCategory;
	}

	public void setFormativeAssessmentCategory(
			FormativeAssessmentCategory formativeAssessmentCategory) {
		this.formativeAssessmentCategory = formativeAssessmentCategory;
	}

	@Column(name = "score", nullable = false)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "description", nullable = false, length = 45)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
