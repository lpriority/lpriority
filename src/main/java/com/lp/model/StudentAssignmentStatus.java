package com.lp.model;

// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lp.utils.WebKeys;

/**
 * StudentAssignmentStatus generated by hbm2java
 */
@Entity
@Table(name = "student_assignment_status")
public class StudentAssignmentStatus implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long studentAssignmentId;
	@JsonManagedReference
	private Assignment assignment;
	@JsonManagedReference
	private Student student;
	@JsonManagedReference
	private PerformancetaskGroups performanceGroup;
	private String status;
	private Date submitdate;
	private Float percentage;
	private String gradedStatus;
	private String selfGradedStatus;
	private String peerGradedStatus;
	private Long benchmarkAssignmentId;
	private Long retestId;
	private Long lastSavedSet;
	private Date peerSubmitDate;
	private Date testAssignDate;
	private Date testDueDate;
	@JsonManagedReference
	private AcademicGrades academicGrade;
	@Transient
	private boolean testSelected;
	@Transient
	private String operation;
	@Transient 
	private long answersBYWrongResponse;	
	@Transient 
	private long answersByRightResponse;
	@JsonManagedReference
	private Student peerReviewBy;
	@JsonBackReference
	private List<StudentPhonicTestMarks> studentPhonicTestMarksList = new ArrayList<>();
	@JsonBackReference
	private List<AssignmentQuestions> assignmentQuestions = new ArrayList<AssignmentQuestions>(0);
	@Transient
	private List<StudentMathAssessMarks> mathAssessMarks = new ArrayList<StudentMathAssessMarks>(0);
	
	
	private Date gradedDate;
	private String readStatus;

	public StudentAssignmentStatus() {
	}

	public StudentAssignmentStatus(long studentAssignmentId,
			Assignment assignment, Student student, String status,
			Long benchmarkAssignmentId, Long retestId, Date gradedDate) {
		this.studentAssignmentId = studentAssignmentId;
		this.assignment = assignment;
		this.student = student;
		this.status = status;
		this.benchmarkAssignmentId = benchmarkAssignmentId;
		this.retestId = retestId;
		this.gradedDate = gradedDate;
	}

	public StudentAssignmentStatus(long studentAssignmentId,
			Assignment assignment, Student student, String status,
			Date submitdate, Float percentage, String gradedStatus,
			Long benchmarkAssignmentId, Long retestId,
			PerformancetaskGroups performanceGroup,
			AcademicGrades academicGrade,
			Long lastSavedSet, Date peerSubmitDate, 
			Date testAssignDate, Date testDueDate, Date gradedDate,
			String readStatus) {
		this.studentAssignmentId = studentAssignmentId;
		this.assignment = assignment;
		this.student = student;
		this.status = status;
		this.submitdate = submitdate;
		this.percentage = percentage;
		this.gradedStatus = gradedStatus;
		this.benchmarkAssignmentId = benchmarkAssignmentId;
		this.retestId = retestId;
		this.performanceGroup = performanceGroup;
		this.academicGrade = academicGrade;
		this.lastSavedSet = lastSavedSet;
		this.peerSubmitDate = peerSubmitDate;
		this.testAssignDate = testAssignDate;
		this.testDueDate = testDueDate;
		this.gradedDate = gradedDate;
		this.readStatus = readStatus;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "student_assignment_id", unique = true, nullable = false)
	public long getStudentAssignmentId() {
		return this.studentAssignmentId;
	}

	public void setStudentAssignmentId(long studentAssignmentId) {
		this.studentAssignmentId = studentAssignmentId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assignment_id", nullable = false)
	public Assignment getAssignment() {
		return this.assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studentAssignmentStatus")
	public List<StudentPhonicTestMarks> getStudentPhonicTestMarksList() {
		return studentPhonicTestMarksList;
	}

	public void setStudentPhonicTestMarksList(
			List<StudentPhonicTestMarks> studentPhonicTestMarksList) {
		this.studentPhonicTestMarksList = studentPhonicTestMarksList;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "submitdate", length = 10)
	public Date getSubmitdate() {
		return this.submitdate;
	}

	public void setSubmitdate(Date submitdate) {
		this.submitdate = submitdate;
	}

	@Column(name = "percentage", precision = 12, scale = 0)
	public Float getPercentage() {
		return this.percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

	@Column(name = "graded_status", length = 30)
	public String getGradedStatus() {
		return this.gradedStatus;
	}

	public void setGradedStatus(String gradedStatus) {
		this.gradedStatus = gradedStatus;
	}
	@Column(name = "benchmark_assignment_id", nullable = false)
	public Long getBenchmarkAssignmentId() {
		return benchmarkAssignmentId;
	}

	public void setBenchmarkAssignmentId(Long benchmarkAssignmentId) {
		this.benchmarkAssignmentId = benchmarkAssignmentId;
	}
	@Column(name = "retest_id", nullable = false)
	public Long getRetestId() {
		return retestId;
	}

	public void setRetestId(Long retestId) {
		this.retestId = retestId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studentAssignmentStatus")
	public List<AssignmentQuestions> getAssignmentQuestions() {
		return this.assignmentQuestions;
	}

	public void setAssignmentQuestions(
			List<AssignmentQuestions> assignmentQuestionses) {
		this.assignmentQuestions = assignmentQuestionses;
	}
	@Transient
	public boolean isTestSelected() {
		return testSelected;
	}

	public void setTestSelected(boolean testSelected) {
		this.testSelected = testSelected;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "performance_group_id")
	public PerformancetaskGroups getPerformanceGroup() {
		return performanceGroup;
	}

	public void setPerformanceGroup(PerformancetaskGroups performanceGroup) {
		this.performanceGroup = performanceGroup;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "academic_grade_id")
	public AcademicGrades getAcademicGrade() {
		return academicGrade;
	}

	public void setAcademicGrade(AcademicGrades academicGrade) {
		this.academicGrade = academicGrade;
	}
	
	@Column(name = "last_saved_set", nullable = true)
	public Long getLastSavedSet() {
		return lastSavedSet;
	}

	public void setLastSavedSet(Long lastSavedSet) {
		this.lastSavedSet = lastSavedSet;
	}

	@Transient
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Transient
	public long getAnswersBYWrongResponse() {
		return answersBYWrongResponse;
	}

	public void setAnswersBYWrongResponse(long answersBYWrongResponse) {
		this.answersBYWrongResponse = answersBYWrongResponse;
	}
	@Transient
	public long getAnswersByRightResponse() {
		return answersByRightResponse;
	}

	public void setAnswersByRightResponse(long answersByRightResponse) {
		this.answersByRightResponse = answersByRightResponse;
	}
	@Column(name = "self_graded_status", length = 30)
	public String getSelfGradedStatus() {
		return this.selfGradedStatus;
	}

	public void setSelfGradedStatus(String selfGradedStatus) {
		this.selfGradedStatus = selfGradedStatus;
	}
	@Column(name = "peer_graded_status", length = 30)
	public String getPeerGradedStatus() {
		return this.peerGradedStatus;
	}

	public void setPeerGradedStatus(String peerGradedStatus) {
		this.peerGradedStatus = peerGradedStatus;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "peer_review_by")
	public Student getPeerReviewBy() {
		return peerReviewBy;
	}

	public void setPeerReviewBy(Student peerReviewBy) {
		this.peerReviewBy = peerReviewBy;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "peer_submitdate", length = 10)
	public Date getPeerSubmitDate() {
		return this.peerSubmitDate;
	}

	public void setPeerSubmitDate(Date peerSubmitDate) {
		this.peerSubmitDate = peerSubmitDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "test_assign_date", length = 10)
	public Date getTestAssignDate() {
		return testAssignDate;
	}

	public void setTestAssignDate(Date testAssignDate) {
		this.testAssignDate = testAssignDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "test_due_date", length = 10)
	public Date getTestDueDate() {
		return testDueDate;
	}

	public void setTestDueDate(Date testDueDate) {
		this.testDueDate = testDueDate;
	}

	@Transient
	public List<StudentMathAssessMarks> getMathAssessMarks() {
		return mathAssessMarks;
	}

	public void setMathAssessMarks(List<StudentMathAssessMarks> mathAssessMarks) {
		this.mathAssessMarks = mathAssessMarks;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "graded_date")
	public Date getGradedDate() {
		return gradedDate;
	}

	public void setGradedDate(Date gradedDate) {
		this.gradedDate = gradedDate;
	}
	
	@Column(name = "read_status")
	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		if(readStatus == null ){
			this.readStatus = WebKeys.UN_READ;
		}else{
			this.readStatus = readStatus;
		}
	}	
}