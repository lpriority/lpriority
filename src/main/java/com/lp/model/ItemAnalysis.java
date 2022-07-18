package com.lp.model;

import java.util.List;

public class ItemAnalysis {
	//private String lessonName;
	private String examDate;
	private long highestScore;
	private long lowestScore;
	private float highestPercentage;
	private float lowestPercentage;
	private long totalPossibles;
	private String instructor;
	private List<Questions> questions;
	private List<Long> optionAChoices;
	private List<Long> optionBChoices;
	private List<Long> optionCChoices;
	private List<Long> optionDChoices;
	private List<Long> optionEChoices;
	private List<Long> correctChoices;
	private List<Long> wrongChoices;
	private List<Long> questionCounts;

	public List<Long> getQuestionCounts() {
		return questionCounts;
	}

	public void setQuestionCounts(List<Long> questionCounts) {
		this.questionCounts = questionCounts;
	}

	public List<Long> getOptionAChoices() {
		return optionAChoices;
	}

	public void setOptionAChoices(List<Long> optionAChoices) {
		this.optionAChoices = optionAChoices;
	}

	public List<Long> getOptionBChoices() {
		return optionBChoices;
	}

	public void setOptionBChoices(List<Long> optionBChoices) {
		this.optionBChoices = optionBChoices;
	}

	public List<Long> getOptionCChoices() {
		return optionCChoices;
	}

	public void setOptionCChoices(List<Long> optionCChoices) {
		this.optionCChoices = optionCChoices;
	}

	public List<Long> getOptionDChoices() {
		return optionDChoices;
	}

	public void setOptionDChoices(List<Long> optionDChoices) {
		this.optionDChoices = optionDChoices;
	}

	public List<Long> getOptionEChoices() {
		return optionEChoices;
	}

	public void setOptionEChoices(List<Long> optionEChoices) {
		this.optionEChoices = optionEChoices;
	}

	public List<Long> getCorrectChoices() {
		return correctChoices;
	}

	public void setCorrectChoices(List<Long> correctChoices) {
		this.correctChoices = correctChoices;
	}

	public List<Long> getWrongChoices() {
		return wrongChoices;
	}

	public void setWrongChoices(List<Long> wrongChoices) {
		this.wrongChoices = wrongChoices;
	}

	
	/*public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}*/

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public long getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(long highestScore) {
		this.highestScore = highestScore;
	}

	public long getLowestScore() {
		return lowestScore;
	}

	public void setLowestScore(long lowestScore) {
		this.lowestScore = lowestScore;
	}

	public float getHighestPercentage() {
		return highestPercentage;
	}

	public void setHighestPercentage(float highestPercentage) {
		this.highestPercentage = highestPercentage;
	}

	public float getLowestPercentage() {
		return lowestPercentage;
	}

	public void setLowestPercentage(float lowestPercentage) {
		this.lowestPercentage = lowestPercentage;
	}

	public long getTotalPossibles() {
		return totalPossibles;
	}

	public void setTotalPossibles(long totalPossibles) {
		this.totalPossibles = totalPossibles;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

}
