package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "read_reg_activity")
public class ReadRegActivity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long activityId;
	private String actitvity;
	private int activityValue;
	
	public ReadRegActivity() {
		super();
	}
	
	public ReadRegActivity(long activityId, String actitvity, int activityValue) {
		super();
		this.activityId = activityId;
		this.actitvity = actitvity;
		this.activityValue = activityValue;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "activity_id", nullable = false)
	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	
	@Column(name = "actitvity", length = 45)
	public String getActitvity() {
		return actitvity;
	}

	public void setActitvity(String actitvity) {
		this.actitvity = actitvity;
	}
	
	@Column(name = "activity_value", length = 11)
	public int getActivityValue() {
		return activityValue;
	}

	public void setActivityValue(int activityValue) {
		this.activityValue = activityValue;
	}	
}
