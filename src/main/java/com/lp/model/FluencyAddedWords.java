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
 * Attendance generated by hbm2java
 */
@Entity
@Table(name = "fluency_added_words")
public class FluencyAddedWords implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long addedWordId;
	@JsonManagedReference
	private FluencyMarks fluencyMarks;
	private long wordIndex;
	private String addedWord;
	private int wordType;
	
	public FluencyAddedWords() {
	}
	
	public FluencyAddedWords(long addedWorId, FluencyMarks fluencyMarks, long wordIndex,String addedWord) {
		this.addedWordId = addedWorId;
		
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "added_word_id", unique = true, nullable = false)
	public long getAddedWordId() {
		return addedWordId;
	}

	public void setAddedWordId(long addedWorId) {
		this.addedWordId = addedWorId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fluency_marks_id")
	public FluencyMarks getFluencyMarks() {
		return fluencyMarks;
	}

	public void setFluencyMarks(FluencyMarks fluencyMarks) {
		this.fluencyMarks = fluencyMarks;
	}

	@Column(name = "word_index", nullable = false)
	public long getWordIndex() {
		return wordIndex;
	}

	public void setWordIndex(long wordIndex) {
		this.wordIndex = wordIndex;
	}
	
	@Column(name = "added_word", nullable = false, length = 100)
	public String getAddedWord() {
		return addedWord;
	}

	public void setAddedWord(String addedWord) {
		this.addedWord = addedWord;
	}
	@Column(name = "word_type", nullable = true, length = 100)
	public int getWordType() {
		return wordType;
	}

	public void setWordType(int wordType) {
		this.wordType = wordType;
	}

}