package com.lp.model;

public class AttendanceGroupedByStatus {
	private long studentId;
	private String studentName;
	private long presentDays;
	private long absentDays;
	private long excusedAbsentDays;
	private long tardyDays;
	private long excusedTardyDays;
	private String teacherName;
	
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public long getStudentId() {
		return studentId;
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
	public long getPresentDays() {
		return presentDays;
	}
	public void setPresentDays(long presentDays) {
		this.presentDays = presentDays;
	}
	public long getAbsentDays() {
		return absentDays;
	}
	public void setAbsentDays(long absentDays) {
		this.absentDays = absentDays;
	}
	public long getExcusedAbsentDays() {
		return excusedAbsentDays;
	}
	public void setExcusedAbsentDays(long excusedAbsentDays) {
		this.excusedAbsentDays = excusedAbsentDays;
	}
	public long getTardyDays() {
		return tardyDays;
	}
	public void setTardyDays(long tardyDays) {
		this.tardyDays = tardyDays;
	}
	public long getExcusedTardyDays() {
		return excusedTardyDays;
	}
	public void setExcusedTardyDays(long excusedTardyDays) {
		this.excusedTardyDays = excusedTardyDays;
	}
}
