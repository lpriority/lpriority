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
@Table(name = "legend_sub_criteria_district_wise")
public class LegendSubCriteriaDistrictWise implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long legendSubCriteriaDistrictWiseId;
	@JsonManagedReference
	private MasterGrades masterGrades;
	@JsonManagedReference
	private District district;
	@JsonManagedReference
	private LegendSubCriteria legendSubCriteria;
	private String additionalInfo;

	public LegendSubCriteriaDistrictWise() {
		super();
	}

	public LegendSubCriteriaDistrictWise(long legendSubCriteriaDistrictWiseId,
			MasterGrades masterGrades, District district,
			LegendSubCriteria legendSubCriteria, String additionalInfo) {
		super();
		this.legendSubCriteriaDistrictWiseId = legendSubCriteriaDistrictWiseId;
		this.masterGrades = masterGrades;
		this.district = district;
		this.legendSubCriteria = legendSubCriteria;
		this.additionalInfo = additionalInfo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "legend_sub_criteria_district_wise_id", unique = true, nullable = false)
	public long getLegendSubCriteriaDistrictWiseId() {
		return legendSubCriteriaDistrictWiseId;
	}

	public void setLegendSubCriteriaDistrictWiseId(
			long legendSubCriteriaDistrictWiseId) {
		this.legendSubCriteriaDistrictWiseId = legendSubCriteriaDistrictWiseId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "master_grade_id")
	public MasterGrades getMasterGrades() {
		return masterGrades;
	}

	public void setMasterGrades(MasterGrades masterGrades) {
		this.masterGrades = masterGrades;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "district_id")
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "legend_sub_criteria_id")
	public LegendSubCriteria getLegendSubCriteria() {
		return legendSubCriteria;
	}

	public void setLegendSubCriteria(LegendSubCriteria legendSubCriteria) {
		this.legendSubCriteria = legendSubCriteria;
	}
	@Column(name="additional_info", length=200, nullable=true)
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	
	

}
