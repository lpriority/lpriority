package com.lp.model;

public class SchoolAttendance {
	private String status;
	private Long todayCount;
	private Long priorDayCount;
	private Long twoDaysPrior;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getTodayCount() {
		return todayCount;
	}
	public void setTodayCount(Long todayCount) {
		this.todayCount = todayCount;
	}
	public Long getPriorDayCount() {
		return priorDayCount;
	}
	public void setPriorDayCount(Long priorDayCount) {
		this.priorDayCount = priorDayCount;
	}
	public Long getTwoDaysPrior() {
		return twoDaysPrior;
	}
	public void setTwoDaysPrior(Long twoDaysPrior) {
		this.twoDaysPrior = twoDaysPrior;
	}
}
