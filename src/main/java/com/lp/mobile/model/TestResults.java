package com.lp.mobile.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestResults {

	private long studentAssignmentId;
	private String title;
	private String assignmentType;
	private String studentName;
	private Date submitdate;
	private Float percentage;
	private Date testDueDate;
	private String teacherName;
	private String className;
	private String readStatus;
	private List<TestResultFluency> testResultFluency = new ArrayList<>();
	
	public TestResults() {
	}	
	
	public TestResults(long studentAssignmentId, String title,
			String assignmentType, String studentName, Date submitdate,
			Float percentage, Date testDueDate, String teacherName,
			String className, String readStatus,
			List<TestResultFluency> testResultFluency) {
		this.studentAssignmentId = studentAssignmentId;
		this.title = title;
		this.assignmentType = assignmentType;
		this.studentName = studentName;
		this.submitdate = submitdate;
		this.percentage = percentage;
		this.testDueDate = testDueDate;
		this.teacherName = teacherName;
		this.className = className;
		this.readStatus = readStatus;
		this.testResultFluency = testResultFluency;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getStudentAssignmentId() {
		return this.studentAssignmentId;
	}

	public void setStudentAssignmentId(long studentAssignmentId) {
		this.studentAssignmentId = studentAssignmentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Date getSubmitdate() {
		return this.submitdate;
	}

	public void setSubmitdate(Date submitdate) {
		this.submitdate = submitdate;
	}

	public Float getPercentage() {
		return this.percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

	public String getAssignmentType() {
		return this.assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public Date getTestDueDate() {
		return testDueDate;
	}

	public void setTestDueDate(Date testDueDate) {
		this.testDueDate = testDueDate;
	}	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	public List<TestResultFluency> getTestResultFluency() {
		return testResultFluency;
	}

	public void setTestResultFluency(List<TestResultFluency> testResultFluency) {
		this.testResultFluency = testResultFluency;
	}	
	
}