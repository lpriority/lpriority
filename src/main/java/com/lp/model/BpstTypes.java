
package com.lp.model;

//default package
//Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* Country generated by hbm2java
*/
@Entity
@Table(name = "bpst_types")
public class BpstTypes implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long bpstTypeId;
	private String bpstType;
	private String desc;
	

	public BpstTypes() {
	}

	public BpstTypes(long bpstTypeId) {
		this.bpstTypeId = bpstTypeId;
	}

	public BpstTypes(long bpstTypeId, String bpstType, String desc) {
		this.bpstTypeId = bpstTypeId;
		this.bpstType = bpstType;
		this.desc = desc;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "bpst_type_id", unique = true, nullable = false)
	public long getBpstTypeId() {
		return this.bpstTypeId;
	}

	public void setBpstTypeId(long bpstTypeId) {
		this.bpstTypeId = bpstTypeId;
	}

	@Column(name = "bpst_type", length = 40)
	public String getBpstType() {
		return this.bpstType;
	}

	public void setBpstType(String bpstType) {
		this.bpstType = bpstType;
	}

	@Column(name = "desc")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
