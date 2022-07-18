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
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "unit_stem_concepts")
public class UnitStemConcepts implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long unitStemConceptsId;
	@JsonIgnore
	private UnitStemStrands unitStemStrands;
	@JsonManagedReference
	private StemStrandConcepts stemStrandConcepts;
		
	public UnitStemConcepts() {
	}

	public UnitStemConcepts(long unitStemConceptsId,UnitStemStrands unitStemStrands,StemStrandConcepts stemStrandConcepts) 
	{
		this.unitStemConceptsId = unitStemConceptsId;
		this.unitStemStrands = unitStemStrands;
		this.stemStrandConcepts = stemStrandConcepts;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "unit_stem_concepts_id", unique = true, nullable = false)
	public long getUnitStemConceptsId() {
		return unitStemConceptsId;
	}

	public void setUnitStemConceptsId(long unitStemConceptsId) {
		this.unitStemConceptsId = unitStemConceptsId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_stem_strands_id")
	public UnitStemStrands getUnitStemStrands() {
		return unitStemStrands;
	}

	public void setUnitStemStrands(UnitStemStrands unitStemStrands) {
		this.unitStemStrands = unitStemStrands;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_str_cpt_id")
	public StemStrandConcepts getStemStrandConcepts() {
		return stemStrandConcepts;
	}

	public void setStemStrandConcepts(StemStrandConcepts stemStrandConcepts) {
		this.stemStrandConcepts = stemStrandConcepts;
	}
	
	
}
