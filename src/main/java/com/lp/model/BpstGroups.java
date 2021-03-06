
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

/**
* States generated by hbm2java
*/
@Entity
@Table(name = "bpst_groups")
public class BpstGroups implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long bpstGroupsId;
	@JsonManagedReference
	private BpstTypes bpstTypes;
	@JsonManagedReference
	private PhonicGroups phonicGroups;
	
	public BpstGroups() {
	}

	public BpstGroups(long bpstGroupsId) {
		this.bpstGroupsId = bpstGroupsId;
	}

	public BpstGroups(long bpstGroupsId, BpstTypes bpstTypes, PhonicGroups phonicGroups) {
		this.bpstGroupsId = bpstGroupsId;
		this.bpstTypes = bpstTypes;
		this.phonicGroups = phonicGroups;
		
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "bpst_groups_id", unique = true, nullable = false)
	public long getBpstGroupsId() {
		return bpstGroupsId;
	}

	public void setBpstGroupsId(long bpstGroupsId) {
		this.bpstGroupsId = bpstGroupsId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bpst_type_id")
	public BpstTypes getBpstTypes() {
		return this.bpstTypes;
	}

	public void setBpstTypes(BpstTypes bpstTypes) {
		this.bpstTypes = bpstTypes;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "phonic_group_id")
	public PhonicGroups getPhonicGroups() {
		return this.phonicGroups;
	}

	public void setPhonicGroups(PhonicGroups phonicGroups) {
		this.phonicGroups = phonicGroups;
	}

	
}
