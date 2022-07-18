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
@Table(name = "formative_assessments_grade_wise")
public class FormativeAssessmentsGradeWise implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long formativeAssessmentsGradeWiseId;
	@JsonManagedReference
	private MasterGrades masterGrades;
	@JsonManagedReference
	private FormativeAssessments formativeAssessments;
	private String status;

	public FormativeAssessmentsGradeWise() {
		super();
	}

	public FormativeAssessmentsGradeWise(long formativeAssessmentsGradeWiseId,
			MasterGrades masterGrades,
			FormativeAssessments formativeAssessments, String status) {
		super();
		this.formativeAssessmentsGradeWiseId = formativeAssessmentsGradeWiseId;
		this.masterGrades = masterGrades;
		this.formativeAssessments = formativeAssessments;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "formative_assessments_grade_wise_id", unique = true, nullable = false)
	public long getFormativeAssessmentsGradeWiseId() {
		return formativeAssessmentsGradeWiseId;
	}

	public void setFormativeAssessmentsGradeWiseId(
			long formativeAssessmentsGradeWiseId) {
		this.formativeAssessmentsGradeWiseId = formativeAssessmentsGradeWiseId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "master_grades_id")
	public MasterGrades getMasterGrades() {
		return masterGrades;
	}

	public void setMasterGrades(MasterGrades masterGrades) {
		this.masterGrades = masterGrades;
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

	@Column(name = "status", nullable = false, length = 45)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
