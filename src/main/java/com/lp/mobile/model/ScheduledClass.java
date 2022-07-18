package com.lp.mobile.model;

public class ScheduledClass {
	
	private long studentId;
	private long csId;
	private String childName;
	private String teacherName;
	private String className;
	private String Status;
	private String readStatus;	
	
	public ScheduledClass() {
	}
	
	public ScheduledClass(long studentId, long csId, String childName,
			String teacherName, String className, String status) {
		this.studentId = studentId;
		this.csId = csId;
		this.childName = childName;
		this.teacherName = teacherName;
		this.className = className;
		Status = status;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getCsId() {
		return csId;
	}

	public void setCsId(long csId) {
		this.csId = csId;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}	
}
