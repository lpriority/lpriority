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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "stem_str_cpt_details")
public class StemStrandConceptDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long stemStrCptDetId;
	private StemStrandConcepts stemStrandConcepts;
	private String conceptDetDesc;
	@JsonBackReference
	private List<StemConceptSubCategory> stemCptSubCateg = new ArrayList<StemConceptSubCategory>();
	
	
	public StemStrandConceptDetails() {
	}

	public StemStrandConceptDetails(long stemStrCptDetId) {
		this.stemStrCptDetId = stemStrCptDetId;
	}

	public StemStrandConceptDetails(long stemStrCptDetId, StemStrandConcepts stemStrandConcepts,String conceptDetDesc) {
		this.stemStrCptDetId = stemStrCptDetId;
		this.stemStrandConcepts = stemStrandConcepts;
		this.conceptDetDesc=conceptDetDesc;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_str_cpt_detailsid", unique = true, nullable = false)
	public long getStemStrCptDetId() {
		return stemStrCptDetId;
	}

	public void setStemStrCptDetId(long stemStrCptDetId) {
		this.stemStrCptDetId = stemStrCptDetId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_str_cpt_id")
	public StemStrandConcepts getStemStrandConcepts() {
		return this.stemStrandConcepts;
	}

	public void setStemStrandConcepts(StemStrandConcepts stemStrandConcepts) {
		this.stemStrandConcepts = stemStrandConcepts;
	}
	
	@Column(name = "concept_det_desc", length = 2000)
	public String getConceptDetDesc() {
		return this.conceptDetDesc;
	}

	public void setConceptDetDesc(String conceptDetDesc) {
		this.conceptDetDesc = conceptDetDesc;
	}
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "stemStrandConceptDetails")
	public List<StemConceptSubCategory> getStemConceptSubCategory() {
		return stemCptSubCateg;
	}

	public void setStemConceptSubCategory(List<StemConceptSubCategory> stemCptSubCateg) {
		this.stemCptSubCateg = stemCptSubCateg;
	}
	
}