package com.lp.model;



public class StudentAttendance {
	private long studentId;
	private long presentCount;
	private long absentCount;
	private long excusedAbsentCount;
	private long tardyCount;
	private long excusedTardyCount;
	
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public long getPresentCount() {
		return presentCount;
	}
	public void setPresentCount(long presentCount) {
		this.presentCount = presentCount;
	}
	public long getAbsentCount() {
		return absentCount;
	}
	public void setAbsentCount(long absentCount) {
		this.absentCount = absentCount;
	}
	public long getExcusedAbsentCount() {
		return excusedAbsentCount;
	}
	public void setExcusedAbsentCount(long excusedAbsentCount) {
		this.excusedAbsentCount = excusedAbsentCount;
	}
	public long getTardyCount() {
		return tardyCount;
	}
	public void setTardyCount(long tardyCount) {
		this.tardyCount = tardyCount;
	}
	public long getExcusedTardyCount() {
		return excusedTardyCount;
	}
	public void setExcusedTardyCount(long excusedTardyCount) {
		this.excusedTardyCount = excusedTardyCount;
	}
}
