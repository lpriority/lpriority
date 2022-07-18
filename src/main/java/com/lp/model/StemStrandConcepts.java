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
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "stem_strand_concepts")
public class StemStrandConcepts implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long stemStrandConceptId;
	@JsonManagedReference
	private StemGradeStrands stemGradeStrands;
	private String stemConcept;
	private String stemConceptDesc;
	@JsonBackReference
	private List<StemStrandConceptDetails> stemStrCptDetails = new ArrayList<StemStrandConceptDetails>();
	
	
	public StemStrandConcepts() {
	}

	public StemStrandConcepts(long stemStrandConceptId) {
		this.stemStrandConceptId = stemStrandConceptId;
	}

	public StemStrandConcepts(long stemStrandConceptId, StemGradeStrands stemGradeStrands,String stemConcept,String stemConceptDesc) {
		this.stemStrandConceptId = stemStrandConceptId;
		this.stemGradeStrands = stemGradeStrands;
		this.stemConcept=stemConcept;
		this.stemConceptDesc=stemConceptDesc;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_str_cpt_id", unique = true, nullable = false)
	public long getStemStrandConceptId() {
		return stemStrandConceptId;
	}

	public void setStemStrandConceptId(long stemStrandConceptId) {
		this.stemStrandConceptId = stemStrandConceptId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_grade_strand_id")
	public StemGradeStrands getStemGradeStrands() {
		return this.stemGradeStrands;
	}

	public void setStemGradeStrands(StemGradeStrands stemGradeStrands) {
		this.stemGradeStrands = stemGradeStrands;
	}
	
	@Column(name = "stem_concept", length = 2000)
	public String getStemConcept() {
		return this.stemConcept;
	}

	public void setStemConcept(String stemConcept) {
		this.stemConcept = stemConcept;
	}
	
	@Column(name = "stem_concept_desc", length = 2000)
	public String getStemConceptDesc() {
		return this.stemConceptDesc;
	}

	public void setStemConceptDesc(String stemConceptDesc) {
		this.stemConceptDesc = stemConceptDesc;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "stemStrandConcepts")
	public List<StemStrandConceptDetails> getStemStrandConceptDetails() {
		return stemStrCptDetails;
	}

	public void setStemStrandConceptDetails(List<StemStrandConceptDetails> stemStrCptDetails) {
		this.stemStrCptDetails = stemStrCptDetails;
	}
	
	
}