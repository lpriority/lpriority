package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "caaspp_types")
public class CAASPPTypes implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private long caasppTypesId;
	private String caasppType;
	
	public CAASPPTypes() {
	}
	
	public CAASPPTypes(long caasppTypesId) {
		this.caasppTypesId = caasppTypesId;
	}

	public CAASPPTypes(long caasppTypesId, String caasppType) {
		super();
		this.caasppTypesId = caasppTypesId;
		this.caasppType = caasppType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "caaspp_types_id", unique = true, nullable = false)
	public long getCaasppTypesId() {
		return caasppTypesId;
	}

	public void setCaasppTypesId(long caasppTypesId) {
		this.caasppTypesId = caasppTypesId;
	}

	@Column(name = "caaspp_type", length = 45)
	public String getCaasppType() {
		return caasppType;
	}

	public void setCaasppType(String caasppType) {
		this.caasppType = caasppType;
	}

}
