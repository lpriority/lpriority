package com.lp.model;

import java.util.List;

// default package
// Generated May 29, 2014 7:37:08 AM by Hibernate Tools 3.4.0.CR1



public class HomeroomClassesForm implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	private List<HomeroomClasses> homeroomClassesList;
	private Long gradeId;

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public List<HomeroomClasses> getHomeroomClassesList() {
		return homeroomClassesList;
	}

	public void setHomeroomClassesList(List<HomeroomClasses> homeroomClassesList) {
		this.homeroomClassesList = homeroomClassesList;
	}
}
