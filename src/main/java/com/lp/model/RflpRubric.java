package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rflp_rubric")
public class RflpRubric implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long rflpRubricId;
	private long rflpRubricValue;
	private String rflpDesc;
	
	public RflpRubric() {
	}
	
	public RflpRubric(long rflpRubricId, long rflpRubricValue, String rflpDesc) {
		this.rflpRubricId = rflpRubricId;
		this.rflpRubricValue = rflpRubricValue;
		this.rflpDesc = rflpDesc;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "rflp_rubric_id", unique = true, nullable = false)
	public long getRflpRubricId() {
		return rflpRubricId;
	}

	public void setRflpRubricId(long rflpRubricId) {
		this.rflpRubricId = rflpRubricId;
	}

	@Column(name = "rflp_rubric_value", length = 20)
	public long getRflpRubricValue() {
		return rflpRubricValue;
	}

	public void setRflpRubricValue(long rflpRubricValue) {
		this.rflpRubricValue = rflpRubricValue;
	}

	@Column(name = "rflp_desc", length = 200)
	public String getRflpDesc() {
		return rflpDesc;
	}

	public void setRflpDesc(String rflpDesc) {
		this.rflpDesc = rflpDesc;
	}
	
	
}
