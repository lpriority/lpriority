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
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "read_reg_master")
public class ReadRegMaster implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private long titleId;
	private String bookTitle;
	private String author;
	private int numberOfPages;
	@JsonManagedReference
	private Teacher teacher;
	@JsonManagedReference
	private UserRegistration userRegistration;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	@Transient
	private String mode;
	private ReadRegPageRange readRegPageRange;
	@JsonManagedReference
	private Grade grade;
	private String approved;
	@Transient
	private int rating;
	private String teacherComment;
	
	@JsonManagedReference
	List<ReadRegReview> readRegReviewLt = new ArrayList<ReadRegReview>();
	@JsonManagedReference
	List<ReadRegQuestions> readRegQuestionsLt = new ArrayList<ReadRegQuestions>();
	@JsonManagedReference
	List<ReadRegActivityScore> readRegActivityScoreLt = new ArrayList<ReadRegActivityScore>();
	
	public ReadRegMaster() {
		super();
	}		
	
	public ReadRegMaster(long titleId, String bookTitle, String author, int numberOfPages, Teacher teacher,
			UserRegistration userRegistration, Date createDate, String mode, ReadRegPageRange readRegPageRange,
			Grade grade, String approved, int rating, List<ReadRegReview> readRegReviewLt,
			List<ReadRegQuestions> readRegQuestionsLt, List<ReadRegActivityScore> readRegActivityScoreLt) {
		super();
		this.titleId = titleId;
		this.bookTitle = bookTitle;
		this.author = author;
		this.numberOfPages = numberOfPages;
		this.teacher = teacher;
		this.userRegistration = userRegistration;
		this.createDate = createDate;
		this.mode = mode;
		this.readRegPageRange = readRegPageRange;
		this.grade = grade;
		this.approved = approved;
		this.rating = rating;
		this.readRegReviewLt = readRegReviewLt;
		this.readRegQuestionsLt = readRegQuestionsLt;
		this.readRegActivityScoreLt = readRegActivityScoreLt;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "title_id", nullable = false, length = 20)
	public long getTitleId() {
		return titleId;
	}

	public void setTitleId(long titleId) {
		this.titleId = titleId;
	}
	
	@Column(name = "book_title", length = 90)
	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	
	@Column(name = "author", length = 45)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Column(name = "number_of_pages")
	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	@JoinColumn(name = "reg_id")
	public UserRegistration getUserRegistration() {
		return userRegistration;
	}

	public void setUserRegistration(UserRegistration userRegistration) {
		this.userRegistration = userRegistration;
	}
	
	@Transient
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@OneToMany(mappedBy = "readRegMaster", fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<ReadRegReview> getReadRegReviewLt() {
		return readRegReviewLt;
	}

	public void setReadRegReviewLt(List<ReadRegReview> readRegReviewLt) {
		this.readRegReviewLt = readRegReviewLt;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "page_range")
	public ReadRegPageRange getReadRegPageRange() {
		return readRegPageRange;
	}

	public void setReadRegPageRange(ReadRegPageRange readRegPageRange) {
		this.readRegPageRange = readRegPageRange;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	@Column(name = "approved")
	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	@Transient
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}	
	
	@OneToMany(mappedBy = "readRegMaster", fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<ReadRegQuestions> getReadRegQuestionsLt() {
		return readRegQuestionsLt;
	}

	public void setReadRegQuestionsLt(List<ReadRegQuestions> readRegQuestionsLt) {
		this.readRegQuestionsLt = readRegQuestionsLt;
	}
	
	@OneToMany(mappedBy = "readRegMaster", fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<ReadRegActivityScore> getReadRegActivityScoreLt() {
		return readRegActivityScoreLt;
	}

	public void setReadRegActivityScoreLt(List<ReadRegActivityScore> readRegActivityScoreLt) {
		this.readRegActivityScoreLt = readRegActivityScoreLt;
	}
	@Column(name = "teacher_comment")
	public String getTeacherComment() {
		return teacherComment;
	}

	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}
	
}
