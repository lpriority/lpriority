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
@Table(name = "formative_assessment_categories")
public class FormativeAssessmentCategory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long formativeAssessmentSubCateId;
	private String category;
	@JsonManagedReference
	private FormativeAssessments formativeAssessments;

	public FormativeAssessmentCategory() {
		super();
	}

	public FormativeAssessmentCategory(long formativeAssessmentSubCateId,
			String category, FormativeAssessments formativeAssessments) {
		super();
		this.formativeAssessmentSubCateId = formativeAssessmentSubCateId;
		this.category = category;
		this.formativeAssessments = formativeAssessments;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "formative_assessment_category_id", unique = true, nullable = false)
	public long getFormativeAssessmentSubCateId() {
		return formativeAssessmentSubCateId;
	}

	public void setFormativeAssessmentSubCateId(
			long formativeAssessmentSubCateId) {
		this.formativeAssessmentSubCateId = formativeAssessmentSubCateId;
	}


	@Column(name = "category", nullable = false, length = 45)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "formative_assessment_id")
	public FormativeAssessments getFormativeAssessments() {
		return formativeAssessments;
	}

	public void setFormativeAssessments(FormativeAssessments formativeAssessments) {
		this.formativeAssessments = formativeAssessments;
	}
	
	

}
