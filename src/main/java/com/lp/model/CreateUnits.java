package com.lp.model;

// default package
// Generated Jun 11, 2014 7:25:34 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Unit generated by hbm2java
 */
public class CreateUnits implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5940790768471811123L;
	/**
	 * 
	 */
	private List<Unit> units = new ArrayList<Unit>();
	private List<Lesson> lessons = new ArrayList<Lesson>();
	private List<Activity> activities = new ArrayList<Activity>();
		

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	
	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	
	public CreateUnits() {
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
}
