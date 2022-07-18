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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "read_reg_review")
public class ReadRegReview implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long reviewId;
	@JsonManagedReference
	private Student student;
	@JsonBackReference
	private ReadRegMaster readRegMaster;
	private int rating;
	private String review;
	private Date reviewDate;
	@Transient
	private long rubricScore;
	@JsonManagedReference
	private Grade grade;
	
	@JsonManagedReference
	List<ReadRegActivityScore> readRegActivityScoreLt = new ArrayList<ReadRegActivityScore>();
	  
	public ReadRegReview() {
		super();
	}

	public ReadRegReview(long reviewId, Student student, ReadRegMaster readRegMaster, int rating, String review, Date reviewDate, Grade grade) {
		this.reviewId = reviewId;
		this.student = student;
		this.readRegMaster = readRegMaster;
		this.rating = rating;
		this.review = review;
		this.reviewDate = reviewDate;
		this.grade = grade;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "review_id", nullable = false, length = 20)
	public long getReviewId() {
		return reviewId;
	}
	
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id")
	public ReadRegMaster getReadRegMaster() {
		return readRegMaster;
	}

	public void setReadRegMaster(ReadRegMaster readRegMaster) {
		this.readRegMaster = readRegMaster;
	}
	
	@Column(name = "rating", length = 11)
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	@Column(name = "review")
	public String getReview() {
		return review;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	@Column(name = "review_date")
	public Date getReviewDate() {
		return reviewDate;
	}
	
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Transient
	public long getRubricScore() {
		return rubricScore;
	}

	public void setRubricScore(long rubricScore) {
		this.rubricScore = rubricScore;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	@OneToMany(mappedBy = "readRegReview", fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<ReadRegActivityScore> getReadRegActivityScoreLt() {
		return readRegActivityScoreLt;
	}

	public void setReadRegActivityScoreLt(List<ReadRegActivityScore> readRegActivityScoreLt) {
		this.readRegActivityScoreLt = readRegActivityScoreLt;
	}
	
	
}
