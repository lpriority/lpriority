package com.lp.model;

public class FluencyAudioFileUpload {
	private long assignmentQuestionId;
	private long studentAssignmentId;
	private String audioData;
	private String passageType;
	
	public FluencyAudioFileUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FluencyAudioFileUpload(long assignmentQuestionId, long studentAssignmentId,
			String audioData, String passageType) {
		super();
		this.assignmentQuestionId = assignmentQuestionId;
		this.studentAssignmentId = studentAssignmentId;
		this.audioData = audioData;
		this.passageType = passageType;
	}
	
	public long getAssignmentQuestionId() {
		return assignmentQuestionId;
	}
	public void setAssignmentQuestionId(long assignmentQuestionId) {
		this.assignmentQuestionId = assignmentQuestionId;
	}
	public String getAudioData() {
		return audioData;
	}
	public void setAudioData(String audioData) {
		this.audioData = audioData;
	}
	public String getPassageType() {
		return passageType;
	}
	public void setPassageType(String passageType) {
		this.passageType = passageType;
	}

	public long getStudentAssignmentId() {
		return studentAssignmentId;
	}

	public void setStudentAssignmentId(long studentAssignmentId) {
		this.studentAssignmentId = studentAssignmentId;
	}
}
