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
@Table(name = "formative_assessments")
public class FormativeAssessments implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long formativeAssessmentsId;
	private String title;
	private String description;
	private String instructions;
	@JsonManagedReference
	private AssignmentType assignmentType;

	public FormativeAssessments() {
		super();
	}

	public FormativeAssessments(long formativeAssessmentsId, String title,
			String description, String instructions,
			AssignmentType assignmentType) {
		super();
		this.formativeAssessmentsId = formativeAssessmentsId;
		this.title = title;
		this.description = description;
		this.instructions = instructions;
		this.assignmentType = assignmentType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "formative_assessments_id", unique = true, nullable = false)
	public long getFormativeAssessmentsId() {
		return formativeAssessmentsId;
	}

	public void setFormativeAssessmentsId(long formativeAssessmentsId) {
		this.formativeAssessmentsId = formativeAssessmentsId;
	}

	@Column(name = "title", nullable = false, length = 45)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", nullable = false, length = 45)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "instructions", nullable = false, length = 45)
	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assessment_type")
	public AssignmentType getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(AssignmentType assignmentType) {
		this.assignmentType = assignmentType;
	}

}
