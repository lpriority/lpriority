package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "read_reg_rubric")
public class ReadRegRubric implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long readRegRubricId;
	private long score;
	private String description;
	
	public ReadRegRubric() {
		super();
	}

	public ReadRegRubric(long readRegRubricId, long score, String description) {
		this.readRegRubricId = readRegRubricId;
		this.score = score;
		this.description = description;
	}
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "read_reg_rubric_id", unique = true, nullable = false)	
	public long getReadRegRubricId() {
		return readRegRubricId;
	}

	public void setReadRegRubricId(long readRegRubricId) {
		this.readRegRubricId = readRegRubricId;
	}

	@Column(name = "score", nullable = false)
	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}
	
	@Column(name = "description", length = 100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
