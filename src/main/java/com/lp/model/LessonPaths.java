package com.lp.model;
// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1

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

/**
 * LessonPaths generated by hbm2java
 */
@Entity
@Table(name = "lesson_paths")
public class LessonPaths implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long lessonPathId;
	@JsonManagedReference
	private Lesson lesson;
	private String lessonPath;
	private long uploadedBy;

	public LessonPaths() {
	}

	public LessonPaths(long lessonPathId, Lesson lesson, String lessonPath,
			long uploadedBy) {
		this.lessonPathId = lessonPathId;
		this.lesson = lesson;
		this.lessonPath = lessonPath;
		this.uploadedBy = uploadedBy;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "lesson_path_id", unique = true, nullable = false)
	public long getLessonPathId() {
		return this.lessonPathId;
	}

	public void setLessonPathId(long lessonPathId) {
		this.lessonPathId = lessonPathId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lesson_id", nullable = false)
	public Lesson getLesson() {
		return this.lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	@Column(name = "lesson_path", nullable = false, length = 300)
	public String getLessonPath() {
		return this.lessonPath;
	}

	public void setLessonPath(String lessonPath) {
		this.lessonPath = lessonPath;
	}

	@Column(name = "uploaded_by", nullable = false)
	public long getUploadedBy() {
		return this.uploadedBy;
	}

	public void setUploadedBy(long uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

}
