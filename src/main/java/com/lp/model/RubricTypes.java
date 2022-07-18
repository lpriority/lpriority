package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * RubricTypes generated by hbm2java
 */
@Entity
@Table(name = "rubric_types")
public class RubricTypes implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long rubricTypeId;
	@NotNull
	@Size(min = 1, max = 50)
	private String rubricType;

	public RubricTypes() {
	}

	public RubricTypes(long rubricTypeId) {
		this.rubricTypeId = rubricTypeId;
	}

	public RubricTypes(long rubricTypeId, String rubricType) {
		this.rubricTypeId = rubricTypeId;
		this.rubricType = rubricType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "rubric_type_id", unique = true, nullable = false)
	public long getRubricTypeId() {
		return this.rubricTypeId;
	}

	public void setRubricTypeId(long rubricTypeId) {
		this.rubricTypeId = rubricTypeId;
	}

	@Column(name = "rubric_type", length = 50)
	public String getRubricType() {
		return this.rubricType;
	}

	public void setRubricType(String rubricType) {
		this.rubricType = rubricType;
	}

}