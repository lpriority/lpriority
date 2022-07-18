package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * AssignedTasks generated by hbm2java
 */
@Entity
@Table(name = "assigned_ptasks")
public class AssignedPtasks implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long assignedTaskId;
	@JsonManagedReference
	private StudentAssignmentStatus studentAssignmentStatus;
	@JsonManagedReference
	private Questions performanceTask;
	private String filepath;
	private String writing;
	private String calculations;
	private String uploadarea;
	private String audiofile;
	private String chatcontents;
	private Integer selfAssessmentScore;
	private String teacherComments;
	private Integer teacherScore;
	@Transient
	private RubricValues rubricValues;	
	@Transient
	private List<StudentPtaskEvidence> studentPtaskEvidence;
	public AssignedPtasks() {
	}

	public AssignedPtasks(long assignedTaskId,
			StudentAssignmentStatus studentAssignmentStatus,Questions performanceTask) {
		this.assignedTaskId = assignedTaskId;
		this.studentAssignmentStatus = studentAssignmentStatus;
		this.performanceTask = performanceTask;
	}

	public AssignedPtasks(long assignedTaskId,
			StudentAssignmentStatus studentAssignmentStatus,
			Questions performanceTask, String filepath, String writing,
			Integer selfAssessmentScore, String teacherComments,
			Integer teacherScore, String calculations, String uploadarea,
			String audiofile, String chatcontents) {
		this.assignedTaskId = assignedTaskId;
		this.studentAssignmentStatus = studentAssignmentStatus;
		this.performanceTask = performanceTask;
		this.filepath = filepath;
		this.writing = writing;
		this.selfAssessmentScore = selfAssessmentScore;
		this.teacherComments = teacherComments;
		this.teacherScore = teacherScore;
		this.calculations = calculations;
		this.uploadarea = uploadarea;
		this.audiofile = audiofile;
		this.chatcontents = chatcontents;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "assigned_task_id", unique = true, nullable = false)
	public long getAssignedTaskId() {
		return this.assignedTaskId;
	}

	public void setAssignedTaskId(long assignedTaskId) {
		this.assignedTaskId = assignedTaskId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_assignment_id", nullable = false)
	public StudentAssignmentStatus getStudentAssignmentStatus() {
		return this.studentAssignmentStatus;
	}

	public void setStudentAssignmentStatus(
			StudentAssignmentStatus studentAssignmentStatus) {
		this.studentAssignmentStatus = studentAssignmentStatus;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "performance_task_id", nullable = false)
	public Questions getPerformanceTask() {
		return this.performanceTask;
	}

	public void setPerformanceTask(Questions performanceTask) {
		this.performanceTask = performanceTask;
	}

	@Column(name = "filepath", length = 100)
	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Column(name = "writing", length = 2000)
	public String getWriting() {
		return this.writing;
	}

	public void setWriting(String writing) {
		this.writing = writing;
	}

	@Column(name = "self_assessment_score")
	public Integer getSelfAssessmentScore() {
		return this.selfAssessmentScore;
	}

	public void setSelfAssessmentScore(Integer selfAssessmentScore) {
		this.selfAssessmentScore = selfAssessmentScore;
	}

	@Column(name = "teacher_comments", length = 400)
	public String getTeacherComments() {
		return this.teacherComments;
	}

	public void setTeacherComments(String teacherComments) {
		this.teacherComments = teacherComments;
	}

	@Column(name = "teacher_score")
	public Integer getTeacherScore() {
		return this.teacherScore;
	}

	public void setTeacherScore(Integer teacherScore) {
		this.teacherScore = teacherScore;
	}
	@Column(name = "calculations")
	public String getCalculations() {
		return calculations;
	}

	public void setCalculations(String calculations) {
		this.calculations = calculations;
	}
	
	@Column(name = "uploadarea")
	public String getUploadarea() {
		return uploadarea;
	}

	public void setUploadarea(String uploadarea) {
		this.uploadarea = uploadarea;
	}
	
	@Column(name = "audiofile")
	public String getAudiofile() {
		return audiofile;
	}

	public void setAudiofile(String audiofile) {
		this.audiofile = audiofile;
	}
	
	@Column(name = "chatcontents")
	public String getChatcontents() {
		return chatcontents;
	}

	public void setChatcontents(String chatcontents) {
		this.chatcontents = chatcontents;
	}
	
	@Transient
	public RubricValues getRubricValues() {
		return rubricValues;
	}

	public void setRubricValues(RubricValues rubricValues) {
		this.rubricValues = rubricValues;
	}
	@Transient
	public List<StudentPtaskEvidence> getStudentPtaskEvidence() {
		return studentPtaskEvidence;
	}

	public void setStudentPtaskEvidence(List<StudentPtaskEvidence> studentPtaskEvidence) {
		this.studentPtaskEvidence = studentPtaskEvidence;
	}

}
