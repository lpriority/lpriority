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

@Entity
@Table(name = "ipal_resources")
public class IpalResources implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private long ipalResourcesId;
	private MasterGrades masterGrades;
	private String resourcesLink;
	private String status;
	
	public IpalResources() {
		super();
	}
	
	public IpalResources(long ipalResourcesId, MasterGrades masterGrades,
			String resourcesLink, String status) {
		super();
		this.ipalResourcesId = ipalResourcesId;
		this.masterGrades = masterGrades;
		this.resourcesLink = resourcesLink;
		this.status = status; 
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ipal_resources_id", unique = true, nullable = false)
	public long getIpalResourcesId() {
		return ipalResourcesId;
	}
	public void setIpalResourcesId(long ipalResourcesId) {
		this.ipalResourcesId = ipalResourcesId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "master_grade_id")
	public MasterGrades getMasterGrades() {
		return masterGrades;
	}
	public void setMasterGrades(MasterGrades masterGrades) {
		this.masterGrades = masterGrades;
	}
	@Column(name = "resource_link")
	public String getResourcesLink() {
		return resourcesLink;
	}
	public void setResourcesLink(String resourcesLink) {
		this.resourcesLink = resourcesLink;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
