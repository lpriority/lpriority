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
@Table(name = "goal_strategies")
public class GoalStrategies implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private long goalStrategiesId;
	@JsonManagedReference
	private String goalStrategiesDesc ;
	private CAASPPTypes caasppType;
	private long orderId;
	
	
	public GoalStrategies() {
	}

	public GoalStrategies(long goalStrategiesId) {
		this.goalStrategiesId = goalStrategiesId;
	}

	public GoalStrategies(long goalStrategiesId,CAASPPTypes caasppType,String goalStrategiesDesc) {
		this.goalStrategiesId = goalStrategiesId;
		this.caasppType = caasppType;
		this.goalStrategiesDesc=goalStrategiesDesc;
	  
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "goal_strategies_id", unique = true, nullable = false)
	public long getGoalStrategiesId() {
		return this.goalStrategiesId;
	}

	public void setGoalStrategiesId(long goalStrategiesId) {
		this.goalStrategiesId = goalStrategiesId;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "caaspp_types_id")
	public CAASPPTypes getCaasppType() {
		return caasppType;
	}

	public void setCaasppType(CAASPPTypes caasppType) {
		this.caasppType = caasppType;
	}
	
	@Column(name = "goal_strategies_desc", length = 100)
	public String getGoalStrategiesDesc() {
		return this.goalStrategiesDesc;
	}

	public void setGoalStrategiesDesc(String goalStrategiesDesc) {
		this.goalStrategiesDesc = goalStrategiesDesc;
	}
	@Column(name = "order_id")
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
}
