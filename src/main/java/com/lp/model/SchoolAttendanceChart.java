package com.lp.model;

import java.util.List;

public class SchoolAttendanceChart {
	private List<SchoolAttendance> schoolAttendanceList;
	private long totalEnrollment;
	private long maleEnrollment;
	private long femaleEnrollment;
	
	public List<SchoolAttendance> getSchoolAttendanceList() {
		return schoolAttendanceList;
	}
	public void setSchoolAttendanceList(List<SchoolAttendance> schoolAttendanceList) {
		this.schoolAttendanceList = schoolAttendanceList;
	}
	public long getTotalEnrollment() {
		return totalEnrollment;
	}
	public void setTotalEnrollment(long totalEnrollment) {
		this.totalEnrollment = totalEnrollment;
	}
	public long getMaleEnrollment() {
		return maleEnrollment;
	}
	public void setMaleEnrollment(long maleEnrollment) {
		this.maleEnrollment = maleEnrollment;
	}
	public long getFemaleEnrollment() {
		return femaleEnrollment;
	}
	public void setFemaleEnrollment(long femaleEnrollment) {
		this.femaleEnrollment = femaleEnrollment;
	}
}
