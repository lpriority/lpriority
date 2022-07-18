package com.lp.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class StudentActivities {
	private long activityId;
	private String activityDesc;
	private String bookTitle; 
	private String author;
	private long noOfPages;
	private String pointsEarned;
	private long selfScore;
	private long teacherScore;
	private String picturePath;
	private String retellPath;
	private int rating;
	private String review;
	private String selfDescription;
	private String teacherDescription;
	private List<ActivityQuizQuestions> activityQuestions = new ArrayList<>();
		
	
	public StudentActivities() {
	}	
	
	public StudentActivities(long activityId,String activityDesc,String bookTitle,String author,long noOfPages,
			String pointsEarned,long selfScore,long teacherScore,String picturePath,
			String retellPath,int rating,String review,
			List<ActivityQuizQuestions> activityQuestions
			) {
		this.activityId=activityId;
		this.activityDesc=activityDesc;
		this.bookTitle = bookTitle;
		this.author = author;
		this.noOfPages = noOfPages;
		this.pointsEarned=pointsEarned;
		this.selfScore=selfScore;
		this.teacherScore=teacherScore;
		this.picturePath=picturePath;
		this.retellPath=retellPath;
		this.rating=rating;
		this.review=review;
		this.activityQuestions=activityQuestions;
		
		
	}
	
	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public long getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(long noOfPages) {
		this.noOfPages = noOfPages;
	}

	public String getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(String pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public long getSelfScore() {
		return selfScore;
	}

	public void setSelfScore(long selfScore) {
		this.selfScore = selfScore;
	}

	public long getTeacherScore() {
		return teacherScore;
	}

	public void setTeacherScore(long teacherScore) {
		this.teacherScore = teacherScore;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getRetellPath() {
		return retellPath;
	}

	public void setRetellPath(String retellPath) {
		this.retellPath = retellPath;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	public List<ActivityQuizQuestions> getActivityQuizQuestions() {
		return activityQuestions;
	}

	public void setActivityQuizQuestions(List<ActivityQuizQuestions> activityQuestions) {
		this.activityQuestions = activityQuestions;
	}	
	public String getSelfDescription() {
		return selfDescription;
	}

	public void setSelfDescription(String selfDescription) {
		this.selfDescription = selfDescription;
	}

	public String getTeacherDescription() {
		return teacherDescription;
	}

	public void setTeacherDescription(String teacherDescription) {
		this.teacherDescription = teacherDescription;
	}
}
