
package com.lp.model;

//default package
//Generated Jun 11, 2014 7:25:34 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
* Unit generated by hbm2java
*/
public class StemCurriculum implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5940790768471811123L;
	/**
	 * 
	 */
	private List<StemUnit> stemUnits = new ArrayList<StemUnit>();
		

	public List<StemUnit> getStemUnits() {
		return stemUnits;
	}

	public void setStemUnits(List<StemUnit> stemUnits) {
		this.stemUnits = stemUnits;
	}


	public StemCurriculum() {
	}

}