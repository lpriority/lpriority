package com.lp.model;

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

@Entity
@Table(name = "rflp_test")
public class RflpTest implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long rflpTestId;
	@JsonManagedReference
	private StudentAssignmentStatus studentAssignmentStatus;
	@JsonBackReference
	private List<RflpPractice> rflpPractice =  new ArrayList<>();
	private float totalAvgScore;
	private String teacherComment;
	private Date dateDue;
	@Transient
	private String operation;
	@Transient
	private String action;
	@JsonManagedReference
	private GradingTypes gradingTypes;
	@JsonManagedReference
	private Student peerReviewBy;
	@Transient
	private long gradingTypesId;

	

	public RflpTest() {}

	public RflpTest(long rflpTestId,
			StudentAssignmentStatus studentAssignmentStatus,
			List<RflpPractice> rflpPractice, float totalAvgScore, String teacherComment,Date dateDue,GradingTypes gradingTypes,Student peerReviewBy) {
		super();
		this.rflpTestId = rflpTestId;
		this.studentAssignmentStatus = studentAssignmentStatus;
		this.rflpPractice = rflpPractice;
		this.totalAvgScore = totalAvgScore;
		this.teacherComment = teacherComment;
		this.dateDue = dateDue;
		this.gradingTypes = gradingTypes;
		this.peerReviewBy = peerReviewBy;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "rflp_test_id", unique = true, nullable = false)
	public long getRflpTestId() {
		return rflpTestId;
	}

	public void setRflpTestId(long rflpTestId) {
		this.rflpTestId = rflpTestId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_assignment_id")
	public StudentAssignmentStatus getStudentAssignmentStatus() {
		return studentAssignmentStatus;
	}

	public void setStudentAssignmentStatus(
			StudentAssignmentStatus studentAssignmentStatus) {
		this.studentAssignmentStatus = studentAssignmentStatus;
	}
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "rflpTest")
	public List<RflpPractice> getRflpPractice() {
		return rflpPractice;
	}

	public void setRflpPractice(List<RflpPractice> rflpPractice) {
		this.rflpPractice = rflpPractice;
	}

	@Column(name = "total_avg_score", length = 11)
	public float getTotalAvgScore() {
		return totalAvgScore;
	}

	public void setTotalAvgScore(float totalAvgScore) {
		this.totalAvgScore = totalAvgScore;
	}

	@Column(name = "teacher_comment", length = 200)
	public String getTeacherComment() {
		return teacherComment;
	}

	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_due", nullable = false, length = 10)
	public Date getDateDue() {
		return this.dateDue;
	}

	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}
	
    @Transient
	public String getOperation() {
		return operation;
	}
    
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Transient
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grading_types_id", nullable = true)
	public GradingTypes getGradingTypes() {
		return gradingTypes;
	}

	public void setGradingTypes(GradingTypes gradingTypes) {
		this.gradingTypes = gradingTypes;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "peer_review_by")
	public Student getPeerReviewBy() {
		return peerReviewBy;
	}

	public void setPeerReviewBy(Student peerReviewBy) {
		this.peerReviewBy = peerReviewBy;
	}

	@Transient
	public long getGradingTypesId() {
		return gradingTypesId;
	}

	public void setGradingTypesId(long gradingTypesId) {
		this.gradingTypesId = gradingTypesId;
	}
}
