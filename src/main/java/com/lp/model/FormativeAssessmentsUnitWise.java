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
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "formative_assessments_unit_wise")
public class FormativeAssessmentsUnitWise implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long formativeAssessmentsUnitWiseId;
	@JsonIgnore
	private StemUnit stemUnit;
	@JsonManagedReference
	private FormativeAssessments formativeAssessments;
	private String status;

	public FormativeAssessmentsUnitWise() {
		super();
	}

	public FormativeAssessmentsUnitWise(long formativeAssessmentsUnitWiseId,
			StemUnit stemUnit, FormativeAssessments formativeAssessments) {
		super();
		this.formativeAssessmentsUnitWiseId = formativeAssessmentsUnitWiseId;
		this.stemUnit = stemUnit;
		this.formativeAssessments = formativeAssessments;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "formative_assessments_unit_wise_id", unique = true, nullable = false)
	public long getFormativeAssessmentsUnitWiseId() {
		return formativeAssessmentsUnitWiseId;
	}

	public void setFormativeAssessmentsUnitWiseId(
			long formativeAssessmentsUnitWiseId) {
		this.formativeAssessmentsUnitWiseId = formativeAssessmentsUnitWiseId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_unit_id")
	public StemUnit getStemUnit() {
		return stemUnit;
	}

	public void setStemUnit(StemUnit stemUnit) {
		this.stemUnit = stemUnit;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
