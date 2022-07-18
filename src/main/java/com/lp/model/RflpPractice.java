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

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "rflp_practice")
public class RflpPractice implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long rflpPracticeId;
	private long wordNum;
	private String errorWord;
	private String studentSentence;
	private Long writtenRubricScore;
	private Long oralRubricScore;
	private String unknownWord;
	private String skippedWord;
	@Transient
	private String audioData;
	@JsonManagedReference
	private RflpTest rflpTest;
	
	public RflpPractice() {}

	public RflpPractice(long rflpPracticeId, long wordNum,
			String errorWord, String studentSentence, Long writtenRubricScore, String unknownWord,String skippedWord,
			Long oralRubricScore,RflpTest rflpTest) {
		this.rflpPracticeId = rflpPracticeId;
		this.wordNum = wordNum;
		this.errorWord = errorWord;
		this.studentSentence = studentSentence;
		this.writtenRubricScore = writtenRubricScore;
		this.oralRubricScore = oralRubricScore;
		this.unknownWord = unknownWord;
		this.skippedWord = skippedWord;
		this.rflpTest = rflpTest;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "rflp_practice_id", unique = true, nullable = false)
	public long getRflpPracticeId() {
		return rflpPracticeId;
	}

	public void setRflpPracticeId(long rflpPracticeId) {
		this.rflpPracticeId = rflpPracticeId;
	}
 
	@Column(name = "word_num", length = 20)
	public long getWordNum() {
		return wordNum;
	}

	public void setWordNum(long wordNum) {
		this.wordNum = wordNum;
	}
	
	@Column(name = "error_word", length = 100)
	public String getErrorWord() {
		return errorWord;
	}

	public void setErrorWord(String errorWord) {
		this.errorWord = errorWord;
	}
	
	@Column(name = "student_sentence")
	public String getStudentSentence() {
		return studentSentence;
	}

	public void setStudentSentence(String studentSentence) {
		this.studentSentence = studentSentence;
	}

	@Column(name = "written_rubric_score", length = 20, nullable = false)
	public Long getWrittenRubricScore() {
		return writtenRubricScore;
	}

	public void setWrittenRubricScore(Long writtenRubricScore) {
		this.writtenRubricScore = writtenRubricScore;
	}

	@Column(name = "oral_rubric_score", length = 20, nullable = false)
	public Long getOralRubricScore() {
		return oralRubricScore;
	}

	public void setOralRubricScore(Long oralRubricScore) {
		this.oralRubricScore = oralRubricScore;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rflp_test_id", nullable = true)
	public RflpTest getRflpTest() {
		return rflpTest;
	}

	public void setRflpTest(RflpTest rflpTest) {
		this.rflpTest = rflpTest;
	}
	@Transient
	public String getAudioData() {
		return audioData;
	}

	public void setAudioData(String audioData) {
		this.audioData = audioData;
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
