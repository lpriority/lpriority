package com.lp.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class ChildDashboard {

	private long studentId;
	private String studentName;
	private List<String> dashFiles = new ArrayList<String>();
	
	public ChildDashboard() {
	}	
	
	public ChildDashboard(long studentId, String studentName, List<String> dashFiles) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.dashFiles = dashFiles;
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

	public List<String> getDashFiles() {
		return dashFiles;
	}

	public void setDashFiles(List<String> dashFiles) {
		this.dashFiles = dashFiles;
	}	
	
}