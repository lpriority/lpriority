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
@Table(name = "student_star_strategies")
public class StudentStarStrategies implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private long studStarStrategId;
	
	@JsonManagedReference
	private Student student;
	@JsonManagedReference
	private Grade grade;
	@JsonManagedReference
	private Trimester trimester;
	@JsonManagedReference
	private GoalStrategies goalStrategies;
	@JsonManagedReference
	private CAASPPTypes caasppTypes;
	private long goalCount;
	
	
	public StudentStarStrategies() {
	}

	public StudentStarStrategies(long studStarStrategId) {
		this.studStarStrategId = studStarStrategId;
	}

	public StudentStarStrategies(long studStarStrategId,Student student,Grade grade,Trimester trimester,GoalStrategies goalStrategies,long goalCount) {
		this.studStarStrategId = studStarStrategId;
		this.student = student;
		this.grade=grade;
		this.trimester=trimester;
		this.goalStrategies = goalStrategies;
			  
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stud_star_strategy_id", unique = true, nullable = false)
	public long getStudStarStrategId() {
		return this.studStarStrategId;
	}

	public void setStudStarStrategId(long studStarStrategId) {
		this.studStarStrategId = studStarStrategId;
	}

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id")
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trimester_id")
	public Trimester getTrimester() {
		return this.trimester;
	}

	public void setTrimester(Trimester trimester) {
		this.trimester = trimester;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "caaspp_types_id")
	public CAASPPTypes getCaasppTypes() {
		return this.caasppTypes;
	}

	public void setCaasppTypes(CAASPPTypes caasppTypes) {
		this.caasppTypes = caasppTypes;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "goal_strategies_id")
	public GoalStrategies getGoalStrategies() {
		return this.goalStrategies;
	}

	public void setGoalStrategies(GoalStrategies goalStrategies) {
		this.goalStrategies = goalStrategies;
	}
	@Column(name = "goal_count", length = 100)
	public long getGoalCount() {
		return this.goalCount;
	}

	public void setGoalCount(long goalCount) {
		this.goalCount = goalCount;
	}
	
}
