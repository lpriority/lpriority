package com.lp.model;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "assign_stem_unit")
public class AssignStemUnits implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private long assignStemId;
	@JsonManagedReference
	private ClassStatus classStatus;
	@JsonManagedReference
	private StemUnit stemUnit;
	private Date assignedDate;
	private Date dueDate;
	
	
	public AssignStemUnits() {
	}

	public AssignStemUnits(long assignStemId) {
		this.assignStemId = assignStemId;
	}

	public AssignStemUnits(long assignStemId, ClassStatus classStatus, StemUnit stemUnit,
			Date assignedDate, Date dueDate) {
		this.assignStemId = assignStemId;
		this.classStatus = classStatus;
		this.stemUnit = stemUnit;
		this.assignedDate = assignedDate;
		this.dueDate = dueDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "assign_stem_id", unique = true, nullable = false)
	public long getAssignStemId() {
		return assignStemId;
	}

	public void setAssignStemId(long assignStemId) {
		this.assignStemId = assignStemId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cs_id")
	public ClassStatus getClassStatus() {
		return this.classStatus;
	}

	public void setClassStatus(ClassStatus classStatus) {
		this.classStatus = classStatus;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_unit_id")
	public StemUnit getStemUnit() {
		return stemUnit;
	}

	public void setStemUnit(StemUnit stemUnit) {
		this.stemUnit = stemUnit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "assigned_date", length = 10)
	public Date getAssignedDate() {
		return this.assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "due_date", length = 10)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
