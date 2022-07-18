
package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stem_strategies")
public class StemStrategies implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long stemStrategiesId;
	private String strategiesDesc;
	private String status;
	
	public StemStrategies() {
	}

	public StemStrategies(long stemStrategiesId, String strategiesDesc,String status) {
		this.stemStrategiesId = stemStrategiesId;
		this.strategiesDesc = strategiesDesc;
		this.status=status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stem_strategies_id", unique = true, nullable = false)
	public long getStemStrategiesId() {
		return stemStrategiesId;
	}

	public void setStemStrategiesId(long stemStrategiesId) {
		this.stemStrategiesId = stemStrategiesId;
	}

	@Column(name = "strategies_desc",length = 450,nullable = true)
	public String getStrategiesDesc() {
		return strategiesDesc;
	}

	public void setStrategiesDesc(String strategiesDesc) {
		this.strategiesDesc = strategiesDesc;
	}
	
	@Column(name = "status",length = 45,nullable = true)
	public String getStatus() {
		return status;
	}
    public void setStatus(String status) {
		this.status = status;
	}
}
