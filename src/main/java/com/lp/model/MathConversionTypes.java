package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "math_conversion_types")
public class MathConversionTypes {

	private long conversionTypeId;
	private String conversionType;
	private String status;
	
	public MathConversionTypes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MathConversionTypes(long conversionTypeId, String conversionType,
			String status) {
		super();
		this.conversionTypeId = conversionTypeId;
		this.conversionType = conversionType;
		this.status = status;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "conversion_type_id", unique = true, nullable = false)
	public long getConversionTypeId() {
		return conversionTypeId;
	}
	
	public void setConversionTypeId(long conversionTypeId) {
		this.conversionTypeId = conversionTypeId;
	}
	
	@Column(name = "conversion_type", nullable = false, length = 45)
	public String getConversionType() {
		return conversionType;
	}
	
	public void setConversionType(String conversionType) {
		this.conversionType = conversionType;
	}
	
	@Column(name = "status", nullable = false, length = 45)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
