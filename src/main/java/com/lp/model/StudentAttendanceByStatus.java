package com.lp.model;

public class StudentAttendanceByStatus {

	private long totalCount;
	private String status;
	private Long statusCount;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getStatusCount() {
		return statusCount;
	}

	public void setStatusCount(Long statusCount) {
		this.statusCount = statusCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
