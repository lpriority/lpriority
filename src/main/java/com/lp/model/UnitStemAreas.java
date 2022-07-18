
package com.lp.model;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "unit_stem_areas")
public class UnitStemAreas implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long unitStemAreasId;
	@JsonIgnore
	private StemUnit stemUnit;
	@JsonManagedReference
	private StemAreas stemAreas;
	@JsonManagedReference
	List<UnitStemStrands>  unitStemStrandsLt = new ArrayList<UnitStemStrands>();	
	@JsonManagedReference
	List<UnitStemContentQuestions> unitStemContentQuestionsLt = new ArrayList<UnitStemContentQuestions>();
	@JsonManagedReference
	List<StemUnitActivity> stemUnitActivitiesLt = new ArrayList<StemUnitActivity>();
	
	public UnitStemAreas() {
	}

	public UnitStemAreas(long unitStemAreasId,StemUnit stemUnit,StemAreas stemAreas,StemGradeStrands stemGradeStrands,String narrative) 
	{
		this.unitStemAreasId = unitStemAreasId;
		this.stemUnit = stemUnit;
		this.stemAreas=stemAreas;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "unit_stem_areas_id", unique = true, nullable = false)
	public long getUnitStemAreasId() {
		return this.unitStemAreasId;
	}

	public void setUnitStemAreasId(long unitStemAreasId) {
		this.unitStemAreasId = unitStemAreasId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_unit_id")
	public StemUnit getStemUnit() {
		return this.stemUnit;
	}

	public void setStemUnit(StemUnit stemUnit) {
		this.stemUnit = stemUnit;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_area_id")
	public StemAreas getStemAreas() {
		return stemAreas;
	}

	public void setStemAreas(StemAreas stemAreas) {
		this.stemAreas = stemAreas;
	}
	
	@OneToMany(mappedBy = "unitStemAreas", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<UnitStemStrands> getUnitStemStrandsLt() {
		return unitStemStrandsLt;
	}

	public void setUnitStemStrandsLt(List<UnitStemStrands> unitStemStrandsLt) {
		this.unitStemStrandsLt = unitStemStrandsLt;
	}

	@OneToMany(mappedBy = "unitStemAreas", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<UnitStemContentQuestions> getUnitStemContentQuestionsLt() {
		return unitStemContentQuestionsLt;
	}

	public void setUnitStemContentQuestionsLt(List<UnitStemContentQuestions> unitStemContentQuestionsLt) {
		this.unitStemContentQuestionsLt = unitStemContentQuestionsLt;
	}

	@OneToMany(mappedBy = "unitStemAreas", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<StemUnitActivity> getStemUnitActivitiesLt() {
		return stemUnitActivitiesLt;
	}

	public void setStemUnitActivitiesLt(List<StemUnitActivity> stemUnitActivitiesLt) {
		this.stemUnitActivitiesLt = stemUnitActivitiesLt;
	}
	
	
}
