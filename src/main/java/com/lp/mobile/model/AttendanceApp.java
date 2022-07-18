package com.lp.mobile.model;

import java.util.Date;

public class AttendanceApp {
	
	private long studentId;
	private long attendanceId;
	private String studentName;
	private String className;
	private Date date;
	private String Status;
	private String readStatus;	
	
	public AttendanceApp() {
	}

	public AttendanceApp(long studentId, long attendanceId, String studentName,
			String className, Date date, String status, String readStatus) {
		super();
		this.studentId = studentId;
		this.attendanceId = attendanceId;
		this.studentName = studentName;
		this.className = className;
		this.date = date;
		Status = status;
		this.readStatus = readStatus;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
