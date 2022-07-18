package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "read_reg_activity_score")
public class ReadRegActivityScore implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long readRegActivityScoreId;
	@JsonManagedReference
	private Student student;
	@JsonBackReference
	private ReadRegMaster readRegMaster;
	@JsonManagedReference
	private Teacher teacher;
	@JsonManagedReference
	private ReadRegActivity readRegActivity;
	@JsonManagedReference
	private ReadRegRubric readRegRubric;
	@JsonManagedReference
	private ReadRegRubric selfScore;
	private Date createDate;
	private Long pointsEarned;
	@JsonManagedReference
	private Grade grade;	
	@JsonBackReference
	ReadRegReview readRegReview;
	private String approveStatus;
	private String teacherComment;
	
	
	public ReadRegActivityScore() {
	}
	
	public ReadRegActivityScore(long readRegActivityScoreId, Student student, ReadRegMaster readRegMaster,
			Teacher teacher, ReadRegActivity readRegActivity, ReadRegRubric readRegRubric, ReadRegRubric selfScore,
			Date createDate, Long pointsEarned, Grade grade, ReadRegReview readRegReview,String approveStatus) {
		this.readRegActivityScoreId = readRegActivityScoreId;
		this.student = student;
		this.readRegMaster = readRegMaster;
		this.teacher = teacher;
		this.readRegActivity = readRegActivity;
		this.readRegRubric = readRegRubric;
		this.selfScore = selfScore;
		this.createDate = createDate;
		this.pointsEarned = pointsEarned;
		this.grade = grade;
		this.readRegReview = readRegReview;
		this.approveStatus=approveStatus;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "read_reg_activity_score_id", nullable = false, length = 20)
	public long getReadRegActivityScoreId() {
		return readRegActivityScoreId;
	}

	public void setReadRegActivityScoreId(long readRegActivityScoreId) {
		this.readRegActivityScoreId = readRegActivityScoreId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "title_id")
	public ReadRegMaster getReadRegMaster() {
		return readRegMaster;
	}

	public void setReadRegMaster(ReadRegMaster readRegMaster) {
		this.readRegMaster = readRegMaster;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_id")
	public ReadRegActivity getReadRegActivity() {
		return readRegActivity;
	}

	public void setReadRegActivity(ReadRegActivity readRegActivity) {
		this.readRegActivity = readRegActivity;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_score")
	public ReadRegRubric getReadRegRubric() {
		return readRegRubric;
	}

	public void setReadRegRubric(ReadRegRubric readRegRubric) {
		this.readRegRubric = readRegRubric;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "self_score")
	public ReadRegRubric getSelfScore() {
		return selfScore;
	}

	public void setSelfScore(ReadRegRubric selfScore) {
		this.selfScore = selfScore;
	}

	@Column(name = "create_date", nullable = false, length = 20)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "points_earned", nullable = true, length = 11)
	public Long getPointsEarned() {
		return pointsEarned;
	}
	
	public void setPointsEarned(Long pointsEarned) {
		this.pointsEarned = pointsEarned;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "review_id")
	public ReadRegReview getReadRegReview() {
		return readRegReview;
	}

	public void setReadRegReview(ReadRegReview readRegReview) {
		this.readRegReview = readRegReview;
	}
	
	@Column(name = "approve_status")
	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	@Column(name = "teacher_comment")
	public String getTeacherComment() {
		return teacherComment;
	}

	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}


	
}
