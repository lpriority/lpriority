package com.lp.mobile.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentReadingRegister {

	private long studentId;
	private String studentName;
	private long gradeId;
	private List<StudentActivities> studActivities = new ArrayList<>();
	private String academicYear;
	
	
	public StudentReadingRegister() {
	}	
	
	public StudentReadingRegister(long studentId, String studentName,
			long gradeId,List<StudentActivities> studActivities,
			String academicYear) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.gradeId = gradeId;
		this.studActivities=studActivities;
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

	public long getGradeId() {
		return gradeId;
	}

	public void setGradeId(long gradeId) {
		this.gradeId = gradeId;
	}
	
	public List<StudentActivities> getStudentActivities() {
		return studActivities;
	}

	public void setStudentActivities(List<StudentActivities> studActivities) {
		this.studActivities = studActivities;
	}	
		
	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	
}