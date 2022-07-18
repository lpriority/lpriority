
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
@Table(name = "stem_concept_sub_category_items")
public class StemConceptSubCategoryItems implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long stemConceptSubCategoryItemId;	
	@JsonManagedReference
	private StemConceptSubCategory stemConceptSubCategory;
	private String itemDesc;
	
	public StemConceptSubCategoryItems() {
	}

	public StemConceptSubCategoryItems(long stemConceptSubCategoryItemId, StemConceptSubCategory stemConceptSubCategory, String itemDesc) 
	{
		this.stemConceptSubCategoryItemId = stemConceptSubCategoryItemId;
		this.stemConceptSubCategory = stemConceptSubCategory;
		this.itemDesc = itemDesc;	
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_concept_sub_category_item_id", unique = true, nullable = false)	
	public long getStemConceptSubCategoryItemId() {
		return stemConceptSubCategoryItemId;
	}

	public void setStemConceptSubCategoryItemId(long stemConceptSubCategoryItemId) {
		this.stemConceptSubCategoryItemId = stemConceptSubCategoryItemId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stem_cpt_sub_categoryid")
	public StemConceptSubCategory getStemConceptSubCategory() {
		return stemConceptSubCategory;
	}

	public void setStemConceptSubCategory(
			StemConceptSubCategory stemConceptSubCategory) {
		this.stemConceptSubCategory = stemConceptSubCategory;
	}
	@Column(name = "item_desc")
	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	
}
