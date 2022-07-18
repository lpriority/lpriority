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
 * SubQuestions generated by hbm2java
 */
@Entity
@Table(name = "k1_auto_assigned_sets")
public class K1AutoAssignedSets implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long k1AutoAssignedSetId;
	@JsonManagedReference
	private ClassStatus classStatus;
	@JsonManagedReference
	private Assignment assignment;
	@JsonManagedReference
	private K1Sets setId;
	@JsonManagedReference
	private K1Sets nextAutoSetId;
	private int recordTime;
	
	public K1AutoAssignedSets() {
	}

	public K1AutoAssignedSets(long k1AutoAssignedSetId, ClassStatus classStatus, Assignment assignment, K1Sets setId,K1Sets nextAutoSetId, int recordTime) {
		this.k1AutoAssignedSetId = k1AutoAssignedSetId;
		this.classStatus = classStatus;
		this.assignment = assignment;
		this.setId = setId;
		this.nextAutoSetId = nextAutoSetId;
		this.recordTime = recordTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "k1_auto_assigned_set_id", unique = true, nullable = false)
	public long getK1AutoAssignedSetId() {
		return this.k1AutoAssignedSetId;
	}

	public void setK1AutoAssignedSetId(long k1AutoAssignedSetId) {
		this.k1AutoAssignedSetId = k1AutoAssignedSetId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cs_id", nullable = false)
	public ClassStatus getClassStatus() {
		return classStatus;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assignment_id", nullable = false)		
	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public void setClassStatus(ClassStatus classStatus) {
		this.classStatus = classStatus;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "set_id", nullable = false)
	public K1Sets getSetId() {
		return setId;
	}

	public void setSetId(K1Sets setId) {
		this.setId = setId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "next_auto_set_id", nullable = true)
	public K1Sets getNextAutoSetId() {
		return nextAutoSetId;
	}

	public void setNextAutoSetId(K1Sets nextAutoSetId) {
		this.nextAutoSetId = nextAutoSetId;
	}
	
	@Column(name = "record_time", nullable = false)
	public int getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(int recordTime) {
		this.recordTime = recordTime;
	}

}