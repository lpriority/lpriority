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
@Table(name = "unit_stem_strategies")
public class UnitStemStrategies implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long unitStemStrategiesId;
	@JsonIgnore
	private StemUnit stemUnit;
	@JsonManagedReference
	private StemStrategies stemStrategies;
	
	public UnitStemStrategies() {
	}

	public UnitStemStrategies(Long unitStemStrategiesId,StemUnit stemUnit,StemStrategies stemStrategies)
	{
		this.unitStemStrategiesId = unitStemStrategiesId;
		this.stemUnit = stemUnit;
		this.stemStrategies=stemStrategies;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "unit_stem_strategies_id", unique = true, nullable = false)
	public Long getUnitStemStrategiesId() {
		return unitStemStrategiesId;
	}

	public void setUnitStemStrategiesId(Long unitStemStrategiesId) {
		this.unitStemStrategiesId = unitStemStrategiesId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_unit_id")
	public StemUnit getStemUnit() {
		return stemUnit;
	}

	public void setStemUnit(StemUnit stemUnit) {
		this.stemUnit = stemUnit;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_strategies_id")
	public StemStrategies getStemStrategies() {
		return stemStrategies;
	}

	public void setStemStrategies(StemStrategies stemStrategies) {
		this.stemStrategies = stemStrategies;
	}

	
	
}
