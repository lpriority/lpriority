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

import org.directwebremoting.annotations.RemoteProperty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "stem_grade_strands")
public class StemGradeStrands implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long stemGradeStrandId;
	@JsonManagedReference
	private MasterGrades masterGrades;
	@JsonManagedReference
	private StemAreas stemAreas;
	@JsonManagedReference
	private States states;
	private String stemStrandTitle;
	private String addedDesc;
	@JsonBackReference
	private List<StemStrandConcepts> stemStrandConceptsLt = new ArrayList<StemStrandConcepts>();
	
	public StemGradeStrands() {
	}

	public StemGradeStrands(long stemGradeStrandId) {
		this.stemGradeStrandId = stemGradeStrandId;
	}

	public StemGradeStrands(long stemGradeStrandId, MasterGrades masterGrades,StemAreas stemAreas, States states, String stemStrandTitle,String addedDesc) {
		this.stemGradeStrandId = stemGradeStrandId;
		this.masterGrades = masterGrades;
		this.stemAreas = stemAreas;
		this.stemStrandTitle=stemStrandTitle;
	    this.addedDesc = addedDesc;
	    this.states = states;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_grade_strand_id", unique = true, nullable = false)
	public long getStemGradeStrandId() {
		return stemGradeStrandId;
	}

	public void setStemGradeStrandId(long stemGradeStrandId) {
		this.stemGradeStrandId = stemGradeStrandId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "master_grades_id")
	public MasterGrades getMasterGrades() {
		return this.masterGrades;
	}

	public void setMasterGrades(MasterGrades masterGrades) {
		this.masterGrades = masterGrades;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_area_id")
	public StemAreas getStemAreas() {
		return this.stemAreas;
	}

	public void setStemAreas(StemAreas stemAreas) {
		this.stemAreas = stemAreas;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	public States getStates() {
		return states;
	}

	public void setStates(States states) {
		this.states = states;
	}
	
	@RemoteProperty
	@Column(name = "stem_strand_title", length = 100)
	public String getStemStrandTitle() {
		return this.stemStrandTitle;
	}

	public void setStemStrandTitle(String stemStrandTitle) {
		this.stemStrandTitle = stemStrandTitle;
	}
	
	@Column(name = "added_desc", length = 2000)
	public String getAddedDesc() {
		return this.addedDesc;
	}
	public void setAddedDesc(String addedDesc) {
		this.addedDesc = addedDesc;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stemGradeStrands")
	public List<StemStrandConcepts> getStemStrandConceptsLt() {
		return stemStrandConceptsLt;
	}

	public void setStemStrandConceptsLt(
			List<StemStrandConcepts> stemStrandConceptsLt) {
		this.stemStrandConceptsLt = stemStrandConceptsLt;
	}
}