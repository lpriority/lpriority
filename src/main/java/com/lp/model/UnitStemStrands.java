
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
@Table(name = "unit_stem_strands")
public class UnitStemStrands implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long unitStemStrandsId;
	@JsonIgnore
	private UnitStemAreas unitStemAreas;
	@JsonManagedReference
	private StemGradeStrands stemGradeStrands;
	private String narrative;
	@JsonIgnore
	private List<UnitStemConcepts> unitStemConceptsLt = new ArrayList<UnitStemConcepts>();
	
	
	public UnitStemStrands() {
	}

	public UnitStemStrands(long unitStemStrandsId,StemGradeStrands stemGradeStrands,String narrative, UnitStemAreas unitStemAreas) 
	{
		this.unitStemStrandsId = unitStemStrandsId;
		this.stemGradeStrands = stemGradeStrands;
		this.narrative = narrative;
		this.unitStemAreas = unitStemAreas;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "unit_stem_strands_id", unique = true, nullable = false)
	public long getUnitStemStrandsId() {
		return this.unitStemStrandsId;
	}

	public void setUnitStemStrandsId(long unitStemStrandsId) {
		this.unitStemStrandsId = unitStemStrandsId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_grade_strand_id")
	public StemGradeStrands getStemGradeStrands() {
		return stemGradeStrands;
	}

	public void setStemGradeStrands(StemGradeStrands stemGradeStrands) {
		this.stemGradeStrands = stemGradeStrands;
	}
	
	
	@Column(name = "narrative", length = 500)
	public String getNarrative() {
		return this.narrative;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "unitStemStrands")
	@Fetch(value = FetchMode.SUBSELECT)
	public List<UnitStemConcepts> getUnitStemConceptsLt() {
		return unitStemConceptsLt;
	}
	
	public void setUnitStemConceptsLt(List<UnitStemConcepts> unitStemConceptsLt) {
		this.unitStemConceptsLt = unitStemConceptsLt;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_stem_areas_id")
	public UnitStemAreas getUnitStemAreas() {
		return unitStemAreas;
	}

	public void setUnitStemAreas(UnitStemAreas unitStemAreas) {
		this.unitStemAreas = unitStemAreas;
	}	
}
