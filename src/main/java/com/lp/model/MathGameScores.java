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
@Table(name = "math_game_scores")
public class MathGameScores implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private long mathGameScoreId;
	@JsonManagedReference
	private StudentAssignmentStatus studentAssignmentStatus;
	@JsonManagedReference
	private GameLevel gameLevel;
	private String timeTaken;
	private long noOfAttempts;
	private long noOfCorrects;
	private long noOfInCorrects;
	private String status;
	@JsonManagedReference
	private MathGear mathGear;
	
	
	
	public MathGameScores() {
	}

	public MathGameScores(long mathGameScoreId) {
		this.mathGameScoreId = mathGameScoreId;
	}

	
	public MathGameScores(long mathGameScoreId,
			StudentAssignmentStatus studentAssignmentStatus,
			GameLevel gameLevel, String timeTaken, long noOfAttempts,
			long noOfCorrects, long noOfInCorrects, String status,
			MathGear mathGear) {
		super();
		this.mathGameScoreId = mathGameScoreId;
		this.studentAssignmentStatus = studentAssignmentStatus;
		this.gameLevel = gameLevel;
		this.timeTaken = timeTaken;
		this.noOfAttempts = noOfAttempts;
		this.noOfCorrects = noOfCorrects;
		this.noOfInCorrects = noOfInCorrects;
		this.status = status;
		this.mathGear = mathGear;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "math_game_score_id", unique = true, nullable = false)
	public long getMathGameScoreId() {
		return this.mathGameScoreId;
	}

	public void setMathGameScoreId(long mathGameScoreId) {
		this.mathGameScoreId = mathGameScoreId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_assignment_id")
	public StudentAssignmentStatus getStudentAssignmentStatus() {
		return this.studentAssignmentStatus;
	}

	public void setStudentAssignmentStatus(StudentAssignmentStatus studentAssignmentStatus) {
		this.studentAssignmentStatus = studentAssignmentStatus;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "game_level_id")
	public GameLevel getGameLevel() {
		return this.gameLevel;
	}

	public void setGameLevel(GameLevel gameLevel) {
		this.gameLevel = gameLevel;
	}

	@Column(name = "no_of_attempts")
	public long getNoOfAttempts() {
		return this.noOfAttempts;
	}

	public void setNoOfAttempts(long noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}
	
	@Column(name = "no_of_corrects")
	public long getNoOfCorrects() {
		return this.noOfCorrects;
	}

	public void setNoOfCorrects(long noOfCorrects) {
		this.noOfCorrects = noOfCorrects;
	}
	
	@Column(name = "no_of_incorrects")
	public long getNoOfInCorrects() {
		return this.noOfInCorrects;
	}

	public void setNoOfInCorrects(long noOfInCorrects) {
		this.noOfInCorrects = noOfInCorrects;
	}
	
	@Column(name = "time_taken")
	public String getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "math_gear_id")
	public MathGear getMathGear() {
		return mathGear;
	}

	public void setMathGear(MathGear mathGear) {
		this.mathGear = mathGear;
	}
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "math_gear_id")
	public MathGear getMathGear() {
		return this.mathGear;
	}

	public void setMathGear(MathGear mathGear) {
		this.mathGear = mathGear;
	}*/
	
	
}