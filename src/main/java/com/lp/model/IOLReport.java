package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "iol_report")
public class IOLReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long iolReportId;
	@JsonManagedReference
	private ClassStatus classStatus;
	@JsonManagedReference
	private Student student;
	private Date reportDate;
	private String status;
	@JsonManagedReference
	private Trimester trimester;
	private String isMulYearReport;
	
	
	public IOLReport() {
	}

	public IOLReport(long iolReportId) {
		this.iolReportId = iolReportId;
	}

	public IOLReport(long iolReportId, ClassStatus classStatus, Student student,Date reportDate,String status,Trimester trimester) {
		this.iolReportId = iolReportId;
		this.classStatus = classStatus;
		this.student = student;
		this.reportDate = reportDate;
	    this.status=status;
	    this.trimester=trimester;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "iol_report_id", unique = true, nullable = false)
	public long getIolReportId() {
		return iolReportId;
	}

	public void setIolReportId(long iolReportId) {
		this.iolReportId = iolReportId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cs_id")
	public ClassStatus getClassStatus() {
		return this.classStatus;
	}

	public void setClassStatus(ClassStatus classStatus) {
		this.classStatus = classStatus;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "report_date", length = 10)
	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	@Column(name = "status", length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trimester_id")
	public Trimester getTrimester() {
		return this.trimester;
	}

	public void setTrimester(Trimester trimester) {
		this.trimester = trimester;
	}
	
	@Column(name = "is_mul_yr_rpt", length = 100)
	public String getIsMulYearReport() {
		return this.isMulYearReport;
	}

	public void setIsMulYearReport(String isMulYearReport) {
		this.isMulYearReport = isMulYearReport;
	}
	
}