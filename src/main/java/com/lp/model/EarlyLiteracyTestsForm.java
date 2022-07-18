package com.lp.model;
import javax.persistence.Transient;


public class EarlyLiteracyTestsForm implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private String audioData;
	@Transient
	private String content;
	@Transient
	private String setName;
	@Transient
	private boolean lastValue;
	@Transient
	private long studentAssignmentId;
	@Transient
	private String setType;
	@Transient
	private String result;
	@Transient
	private long assignmentId;
	@Transient
	private String title;
	@Transient
	private long groupId;
	@Transient
	private String status;
	@Transient 
	private long eltTypeId;
	
	public String getAudioData() {
		return audioData;
	}

	public void setAudioData(String audioData) {
		this.audioData = audioData;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public long getStudentAssignmentId() {
		return studentAssignmentId;
	}

	public void setStudentAssignmentId(long studentAssignmentId) {
		this.studentAssignmentId = studentAssignmentId;
	}

	public boolean isLastValue() {
		return lastValue;
	}

	public void setLastValue(boolean lastValue) {
		this.lastValue = lastValue;
	}

	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getEltTypeId() {
		return eltTypeId;
	}

	public void setEltTypeId(long eltTypeId) {
		this.eltTypeId = eltTypeId;
	}
	
}
