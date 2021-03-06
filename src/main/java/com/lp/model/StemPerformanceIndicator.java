package com.lp.model;

// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Assignment generated by hbm2java
 */
@Entity
@Table(name = "stem_performance_indicator")
public class StemPerformanceIndicator implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long performanceIndicatorId;
	private MasterGrades masterGradeId;
	private Legend legend;
	private String status;

	public StemPerformanceIndicator() {
		super();
	}

	public StemPerformanceIndicator(long performanceIndicatorId,
			MasterGrades masterGradeId, Legend legend, String status) {
		super();
		this.performanceIndicatorId = performanceIndicatorId;
		this.masterGradeId = masterGradeId;
		this.legend = legend;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "performance_indicator_id", unique = true, nullable = false)
	public long getPerformanceIndicatorId() {
		return performanceIndicatorId;
	}

	public void setPerformanceIndicatorId(long performanceIndicatorId) {
		this.performanceIndicatorId = performanceIndicatorId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "master_grade_id")
	public MasterGrades getMasterGradeId() {
		return masterGradeId;
	}

	public void setMasterGradeId(MasterGrades masterGradeId) {
		this.masterGradeId = masterGradeId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "legend_id")
	public Legend getLegend() {
		return legend;
	}

	public void setLegend(Legend legend) {
		this.legend = legend;
	}
	@Column(name = "status", nullable = false, length = 45)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
