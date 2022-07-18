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

@Entity
@Table(name = "stem_unit_activity")
public class StemUnitActivity implements java.io.Serializable {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private long stemActivityId;
	private String activityDesc;
	private String activityLink;
	private long referActivityId;
	private String fileName;
	@JsonIgnore
	private UnitStemAreas unitStemAreas;
	
	public StemUnitActivity() {
	}

	public StemUnitActivity(long stemActivityId) {
		this.stemActivityId = stemActivityId;
	}

	public StemUnitActivity(long stemActivityId,String activityDesc,String activityLink,long referActivityId, UnitStemAreas unitStemAreas) {
		this.stemActivityId = stemActivityId;
		this.activityDesc=activityDesc;
		this.referActivityId=referActivityId;
		this.unitStemAreas = unitStemAreas;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_activity_id", unique = true, nullable = false)
	public long getStemActivityId() {
		return stemActivityId;
	}

	public void setStemActivityId(long stemActivityId) {
		this.stemActivityId = stemActivityId;
	}
	
	@Column(name = "activity_desc", length = 2000)
	public String getActivityDesc() {
		return activityDesc;
	}

	

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	
	@Column(name = "activity_link", length = 2000)
	public String getActivityLink() {
		return activityLink;
	}

	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}
	
	@Column(name = "refer_activity_id", nullable = true)
	public long getReferActivityId() {
		return referActivityId;
	}

	public void setReferActivityId(long referActivityId) {
		this.referActivityId = referActivityId;
	}
	@Column(name = "file_name", nullable = true)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_stem_area_id")	
	public UnitStemAreas getUnitStemAreas() {
		return unitStemAreas;
	}

	public void setUnitStemAreas(UnitStemAreas unitStemAreas) {
		this.unitStemAreas = unitStemAreas;
	}
	
}