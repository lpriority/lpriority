package com.lp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "read_reg_quiz")
public class ReadRegQuiz implements java.io.Serializable{
	
private static final long serialVersionUID = 1L;	
private long questionNum;
private String quest;
@Transient
private ReadRegQuestions readRegQuestions;

public ReadRegQuiz() {
	super();
}

public ReadRegQuiz(long questionNum, String quest) {
	this.questionNum = questionNum;
	this.quest = quest;
}
@Id
@Column(name = "question_num", nullable = false)
public long getQuestionNum() {
	return questionNum;
}

public void setQuestionNum(long questionNum) {
	this.questionNum = questionNum;
}

@Column(name = "quest", length = 10)
public String getQuest() {
	return quest;
}

public void setQuest(String quest) {
	this.quest = quest;
}

@Transient
public ReadRegQuestions getReadRegQuestions() {
	return readRegQuestions;
}

public void setReadRegQuestions(ReadRegQuestions readRegQuestions) {
	this.readRegQuestions = readRegQuestions;
}

}
