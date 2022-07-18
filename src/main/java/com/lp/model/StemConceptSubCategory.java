
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
@Table(name = "stem_concept_sub_category")
public class StemConceptSubCategory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long stemCptSubCategId;
	@JsonManagedReference
	private StemStrandConceptDetails stemStrandConceptDetails;
	private String subCategoryDesc;
	@JsonBackReference
	private List<StemConceptSubCategoryItems> stemConceptSubCategoryItems = new ArrayList<StemConceptSubCategoryItems>();
	
	
	public StemConceptSubCategory() {
	}

	public StemConceptSubCategory(long stemCptSubCategId,StemStrandConceptDetails stemStrandConceptDetails,String subCategoryDesc) 
	{
		this.stemCptSubCategId = stemCptSubCategId;
		this.stemStrandConceptDetails = stemStrandConceptDetails;
		this.subCategoryDesc = subCategoryDesc;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_cpt_sub_categoryid", unique = true, nullable = false)
	public long getStemCptSubCategId() {
		return stemCptSubCategId;
	}

	public void setStemCptSubCategId(long stemCptSubCategId) {
		this.stemCptSubCategId = stemCptSubCategId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_str_cpt_detailsid")
	public StemStrandConceptDetails getStemStrandConceptDetails() {
		return stemStrandConceptDetails;
	}

	public void setStemStrandConceptDetails(StemStrandConceptDetails stemStrandConceptDetails) {
		this.stemStrandConceptDetails = stemStrandConceptDetails;
	}
	
	@Column(name = "sub_category_desc", length = 500)
	public String getSubCategoryDesc() {
		return subCategoryDesc;
	}

	public void setSubCategoryDesc(String subCategoryDesc) {
		this.subCategoryDesc = subCategoryDesc;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "stemConceptSubCategory")
	public List<StemConceptSubCategoryItems> getStemConceptSubCategoryItems() {
		return stemConceptSubCategoryItems;
	}

	public void setStemConceptSubCategoryItems(
			List<StemConceptSubCategoryItems> stemConceptSubCategoryItems) {
		this.stemConceptSubCategoryItems = stemConceptSubCategoryItems;
	}
	
}
