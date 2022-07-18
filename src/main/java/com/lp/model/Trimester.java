package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trimester")
public class Trimester  implements java.io.Serializable{

	
	private static final long serialVersionUID = 1L;
	private long trimesterId;
	private String trimesterName;
	private long orderId;
	
	public Trimester() {
	}

	public Trimester(long trimesterId,String trimesterName,long orderId)
	 {
		this.trimesterId = trimesterId;
		this.trimesterName = trimesterName;
		this.orderId=orderId;
		 
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "trimester_id", unique = true, nullable = false)
	public long getTrimesterId() {
		return this.trimesterId;
	}

	public void setTrimesterId(long trimesterId) {
		this.trimesterId = trimesterId;
	}

	@Column(name = "trimester_name", nullable = false, length = 30)
	public String getTrimesterName() {
		return this.trimesterName;
	}

	public void setTrimesterName(String trimesterName) {
		this.trimesterName = trimesterName;
	}
	@Column(name = "order_id")
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
}
