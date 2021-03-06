package com.lp.model;

// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

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

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Assignment generated by hbm2java
 */
@Entity
@Table(name = "k1_sets")
public class K1Sets implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long setId;
	private String set;
	private String setType;
	private String setName;
	private Long csId;
	private String listType;
	@JsonManagedReference
	private MasterGrades masterGrades;
	private String partType;
	private Date createdDate;
	private String status;
	
	public K1Sets() {
	}

	public K1Sets(long setId, String set,String setType,String setName,Long csId,String listType,Date createdDate,
			MasterGrades masterGrade, String status) {
		this.setId = setId;
		this.set = set;
		this.setType = setType;
		this.setName = setName;
		this.csId = csId;
		this.listType = listType;
		this.createdDate = createdDate;
		this.masterGrades = masterGrade;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "k1_set_id", unique = true, nullable = false)
	public long getSetId() {
		return this.setId;
	}

	public void setSetId(long setId) {
		this.setId = setId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "master_grade_id")
	public MasterGrades getMasterGrades() {
		return this.masterGrades;
	}

	public void setMasterGrades(MasterGrades masterGrades) {
		this.masterGrades = masterGrades;
	}

	@Column(name = "sets", nullable = false, length = 100)
	public String getSet() {
		return this.set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	@Column(name = "set_type", length = 10)
	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}
	
	@Column(name = "part_type", length = 10)
	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	@Column(name = "set_name", length = 10)
	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}
	
	@Column(name = "cs_id", nullable = true)
	public Long getCsId() {
		return csId;
	}

	public void setCsId(Long csId) {
		this.csId = csId;
	}
	
	@Column(name = "list_type", length = 45)
	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}
	
	@Column(name = "created_date")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	

}
