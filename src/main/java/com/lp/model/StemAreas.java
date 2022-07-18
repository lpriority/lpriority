package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stem_areas")
public class StemAreas implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long stemAreaId;
	private String stemArea;
	private String isOtherStem;
	
	public StemAreas() {
	}

	public StemAreas(long stemAreaId, String stemArea, String isOtherStem) {
		this.stemAreaId = stemAreaId;
		this.stemArea = stemArea;
		this.isOtherStem = isOtherStem;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_area_id", unique = true, nullable = false)
	public long getStemAreaId() {
		return this.stemAreaId;
	}

	public void setStemAreaId(long stemAreaId) {
		this.stemAreaId = stemAreaId;
	}

	@Column(name = "stem_area",length = 45)
	public String getStemArea() {
		return this.stemArea;
	}
	
	public void setStemArea(String stemArea) {
		this.stemArea = stemArea;
	}
	@Column(name = "is_other_stem",length = 10)
	public String getIsOtherStem() {
		return isOtherStem;
	}

	public void setIsOtherStem(String isOtherStem) {
		this.isOtherStem = isOtherStem;
	}
	
}
