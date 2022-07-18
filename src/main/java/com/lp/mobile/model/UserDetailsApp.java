package com.lp.mobile.model;

public class UserDetailsApp {

	private String userName;
	private String userEmail;
	private String schoolName;
	private long regId;
	private long schoolId;

	
	public UserDetailsApp() {
	}

	public UserDetailsApp(String userName, String userEmail, String schoolName,long regId,long schoolId) {
		this.userName = userName;
		this.userEmail = userEmail;
		this.schoolName = schoolName;
		this.regId=regId;
		this.schoolId=schoolId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public long getRegId() {
		return regId;
	}
	
	public void setRegId(long regId) {
		this.regId = regId;
	}
	
	public long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

}
