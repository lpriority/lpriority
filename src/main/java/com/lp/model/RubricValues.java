package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

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

/**
 * RubricValues generated by hbm2java
 */
@Entity
@Table(name = "rubric_values")
public class RubricValues implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long rubricValuesId;
	@JsonManagedReference
	private User user;
	@JsonManagedReference
	private AssignedPtasks assignedPtasks;
	private long dimension1Value;
	private long dimension2Value;
	private long dimension3Value;
	private long dimension4Value;
	private Integer totalScore;

	public RubricValues() {
	}

	public RubricValues(long rubricValuesId, User user,
			AssignedPtasks assignedPtasks, long dimension1Value, long dimension2Value,
			long dimension3Value, long dimension4Value) {
		this.rubricValuesId = rubricValuesId;
		this.user = user;
		this.assignedPtasks = assignedPtasks;
		this.dimension1Value = dimension1Value;
		this.dimension2Value = dimension2Value;
		this.dimension3Value = dimension3Value;
		this.dimension4Value = dimension4Value;
	}

	public RubricValues(long rubricValuesId, User user,
			AssignedPtasks assignedPtasks, long dimension1Value, long dimension2Value,
			long dimension3Value, long dimension4Value, Integer totalScore) {
		this.rubricValuesId = rubricValuesId;
		this.user = user;
		this.assignedPtasks = assignedPtasks;
		this.dimension1Value = dimension1Value;
		this.dimension2Value = dimension2Value;
		this.dimension3Value = dimension3Value;
		this.dimension4Value = dimension4Value;
		this.totalScore = totalScore;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "rubric_values_id", unique = true, nullable = false)
	public long getRubricValuesId() {
		return this.rubricValuesId;
	}

	public void setRubricValuesId(long rubricValuesId) {
		this.rubricValuesId = rubricValuesId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_type_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assigned_task_id", nullable = false)
	public AssignedPtasks getAssignedPtasks() {
		return this.assignedPtasks;
	}

	public void setAssignedPtasks(AssignedPtasks assignedPtasks) {
		this.assignedPtasks = assignedPtasks;
	}

	@Column(name = "dimension1_value", nullable = false, length = 45)
	public long getDimension1Value() {
		return this.dimension1Value;
	}

	public void setDimension1Value(long dimension1Value) {
		this.dimension1Value = dimension1Value;
	}

	@Column(name = "dimension2_value", nullable = false, length = 45)
	public long getDimension2Value() {
		return this.dimension2Value;
	}

	public void setDimension2Value(long dimension2Value) {
		this.dimension2Value = dimension2Value;
	}

	@Column(name = "dimension3_value", nullable = false, length = 45)
	public long getDimension3Value() {
		return this.dimension3Value;
	}

	public void setDimension3Value(long dimension3Value) {
		this.dimension3Value = dimension3Value;
	}

	@Column(name = "dimension4_value", nullable = false, length = 45)
	public long getDimension4Value() {
		return this.dimension4Value;
	}

	public void setDimension4Value(long dimension4Value) {
		this.dimension4Value = dimension4Value;
	}

	@Column(name = "total_score")
	public Integer getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

}