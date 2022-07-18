package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "goal_sample_ideas")
public class GoalSampleIdeas implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long goalSampleId;
	private String ideaDesc;
	
	public GoalSampleIdeas() {
	}

	public GoalSampleIdeas(long goalSampleId, String ideaDesc) {
		this.goalSampleId = goalSampleId;
		this.ideaDesc = ideaDesc;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "goal_sample_id", unique = true, nullable = false)
	public long getGoalSampleId() {
		return this.goalSampleId;
	}

	public void setGoalSampleId(long goalSampleId) {
		this.goalSampleId = goalSampleId;
	}

	@Column(name = "idea_desc", nullable = false, length = 10000)
	public String getIdeaDesc() {
		return this.ideaDesc;
	}

	public void setIdeaDesc(String ideaDesc) {
		this.ideaDesc = ideaDesc;
	}

}
