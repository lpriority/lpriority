
package com.lp.model;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "goal_reports")
public class GoalReports implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long goalReportId;
	private String reportType;
	
	

	public GoalReports() {
	}

	public GoalReports(long goalReportId) {
		this.goalReportId = goalReportId;
	}

	public GoalReports(long goalReportId, String reportType) {
		this.goalReportId = goalReportId;
		this.reportType = reportType;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "goal_report_id", unique = true, nullable = false)
	public long getGoalReportId() {
		return this.goalReportId;
	}

	public void setGoalReportId(long goalReportId) {
		this.goalReportId = goalReportId;
	}

	@Column(name = "report_type", length = 40)
	public String getReportType() {
		return this.reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	
	
}
