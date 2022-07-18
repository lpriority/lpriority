package com.lp.mobile.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentGoalReports {

	private long studentId;
	private String studentName;
	private Float caasppReadScore;;
	private Float caasppMathScore;
	private long masterGradeId;
	private List<StarScoresReports> starReadingReports = new ArrayList<>();
	private List<StarScoresReports> starMathReports = new ArrayList<>();
	private String academicYear;
	
	
	public StudentGoalReports() {
	}	
	
	public StudentGoalReports(long studentId, String studentName,
			Float caasppReadScore, Float caasppMathScore,
			long masterGradeId,
			List<StarScoresReports> starReadingReports,
			List<StarScoresReports> starMathReports,String academicYear) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.caasppReadScore = caasppReadScore;
		this.caasppMathScore = caasppMathScore;
		this.starReadingReports = starReadingReports;
		this.starMathReports=starMathReports;
		this.masterGradeId=masterGradeId;
		this.academicYear=academicYear;
		
	}

	public long getStudentId() {
		return this.studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Float getCaasppReadScore() {
		return this.caasppReadScore;
	}

	public void setCaasppReadScore(Float caasppReadScore) {
		this.caasppReadScore = caasppReadScore;
	}
	
	public Float getCaasppMathScore() {
		return this.caasppMathScore;
	}

	public void setCaasppMathScore(Float caasppMathScore) {
		this.caasppMathScore = caasppMathScore;
	}
	public long getMasterGradeId() {
		return masterGradeId;
	}

	public void setMasterGradeId(long masterGradeId) {
		this.masterGradeId = masterGradeId;
	}
	
	public List<StarScoresReports> getStarReadingReports() {
		return starReadingReports;
	}

	public void setStarReadingReports(List<StarScoresReports> starReadingReports) {
		this.starReadingReports = starReadingReports;
	}	
	public List<StarScoresReports> getStarMathReports() {
		return starMathReports;
	}

	public void setStarMathReports(List<StarScoresReports> starMathReports) {
		this.starMathReports = starMathReports;
	}
	
	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	
}