package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "student_phonic_test_marks")
public class StudentPhonicTestMarks implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	@JsonManagedReference
	private StudentAssignmentStatus studentAssignmentStatus;
	@JsonManagedReference
	private PhonicGroups phonicGroups;
	private int maxMarks;
	private int secMarks;
	private String marksString;
	private String comments;
	
	
	public StudentPhonicTestMarks() {
		super();
	}

	public StudentPhonicTestMarks(long id,
			StudentAssignmentStatus studentAssignmentStatus, PhonicGroups phonicGroups,
			int maxMarks, int secMarks, String marksString, String comments) {
		super();
		this.id = id;
		this.studentAssignmentStatus = studentAssignmentStatus;
		this.phonicGroups = phonicGroups;
		this.maxMarks = maxMarks;
		this.secMarks = secMarks;
		this.marksString = marksString;
		this.comments = comments;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id")
	public PhonicGroups getPhonicGroups() {
		return phonicGroups;
	}

	public void setPhonicGroups(PhonicGroups phonicGroups) {
		this.phonicGroups = phonicGroups;
	}
	
	@Column(name = "max_marks", nullable = false, length = 11)
	public int getMaxMarks() {
		return maxMarks;
	}


	public void setMaxMarks(int maxMarks) {
		this.maxMarks = maxMarks;
	}

	@Column(name = "sec_marks", nullable = false, length = 11)
	public int getSecMarks() {
		return secMarks;
	}

	public void setSecMarks(int secMarks) {
		this.secMarks = secMarks;
	}

	@Column(name = "marks_string", nullable = false, length = 50)
	public String getMarksString() {
		return marksString;
	}

	public void setMarksString(String marksString) {
		this.marksString = marksString;
	}

	@Column(name = "comments", nullable = false, length = 200)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


}
