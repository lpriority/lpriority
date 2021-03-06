package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * StudentCompositeChartValuesId generated by hbm2java
 */
@Embeddable
public class StudentCompositeLessonScoreId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long studentId;
	private long assignLessonId;

	public StudentCompositeLessonScoreId() {
	}

	public StudentCompositeLessonScoreId(long studentId,
			long assignLessonId) {
		this.studentId = studentId;
		this.assignLessonId = assignLessonId;
	}

	@Column(name = "student_id", nullable = false)
	public long getStudentId() {
		return this.studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	@Column(name = "assign_id", nullable = false, length = 30)
	public long getAssignLessonId() {
		return this.assignLessonId;
	}

	public void setAssignLessonId(long assignLessonId) {
		this.assignLessonId = assignLessonId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof StudentCompositeLessonScoreId))
			return false;
		StudentCompositeLessonScoreId castOther = (StudentCompositeLessonScoreId) other;

		return (this.getStudentId() == castOther.getStudentId())
				&& (this.getAssignLessonId() == castOther.getAssignLessonId());
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (int) this.getStudentId();
		result = 37 * result + (int) this.getAssignLessonId();
		return result;
	}

}
