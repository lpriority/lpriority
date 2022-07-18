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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * SubInterest generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "fluency_marks_details")
public class FluencyMarksDetails implements java.io.Serializable {
	
	

	private Long fluencyMarksDetailsId;
	@JsonIgnore
	@JsonManagedReference
	private FluencyMarks fluencyMarks;
	private Long errorsAddress;
	private String errorWord;
	private String comments;
	private String unknownWord;
	private String skippedWord;
	@Transient
	private Long errorCount;
	
	@Transient
	public Long getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}

	public FluencyMarksDetails() {
	}

	public FluencyMarksDetails(long fluencyMarksDetailsId, FluencyMarks fluencyMarks, Long errorsAddress, String comments, String unknownWord,String skippedWord) {
		this.fluencyMarksDetailsId = fluencyMarksDetailsId;
		this.fluencyMarks = fluencyMarks;
		this.errorsAddress = errorsAddress;
		this.comments = comments;
		this.unknownWord = unknownWord;
		this.skippedWord = skippedWord;
	}

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "fluency_marks_details_id", unique = true, nullable = false)
	public Long getFluencyMarksDetailsId() {
		return fluencyMarksDetailsId;
	}

	public void setFluencyMarksDetailsId(Long fluencyMarksDetailsId) {
		this.fluencyMarksDetailsId = fluencyMarksDetailsId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fluency_marks_id", nullable = false)
	public FluencyMarks getFluencyMarks() {
		return fluencyMarks;
	}

	public void setFluencyMarks(FluencyMarks fluencyMarks) {
		this.fluencyMarks = fluencyMarks;
	}
	@Column(name = "errors_address")
	public Long getErrorsAddress() {
		return errorsAddress;
	}

	public void setErrorsAddress(Long errorsAddress) {
		this.errorsAddress = errorsAddress;
	}
	@Column(name = "error_word")
	public String getErrorWord() {
		return errorWord;
	}

	public void setErrorWord(String errorWord) {
		this.errorWord = errorWord;
	}
	@Column(name = "comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Column(name = "unknown_word", nullable = true)
	public String getUnknownWord() {
		return unknownWord;
	}
	public void setUnknownWord(String unknownWord) {
		this.unknownWord = unknownWord;
	}
	
	@Column(name = "skipped_word", nullable = true)
	public String getSkippedWord() {
		return skippedWord;
	}

	public void setSkippedWord(String skippedWord) {
		this.skippedWord = skippedWord;
	}
	
	
}
