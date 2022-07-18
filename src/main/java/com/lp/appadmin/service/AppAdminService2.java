package com.lp.appadmin.service;

import com.lp.model.Citizenship;
import com.lp.model.Comments;
import com.lp.model.Country;
import com.lp.model.States;
import com.lp.model.TeacherPerformances;

import java.util.List;

public interface AppAdminService2 {

	/* ##### Country methods starts from here ##### */

	public List<Country> getCountries();
	public Country getCountry(long countryId);
	public void deleteCountry(long countryId);
	public void saveCountry(Country country);
	
	/* ##### States methods starts from here ##### */
	public List<States> getStates(long countryId);
	public List<States> loadStates(long countryId);
	public long getCountryByState(long stateId);
	public List<States> getStates();
	public States getState(long statesId);
	public void deleteState(long statesId);
	public void saveState(States state);
	
	/* ##### Citizenship methods starts from here ##### */
	public List<Citizenship> getCitizenships();
	public Citizenship getCitizenship(long citizenshipId);
	public void deleteCitizenship(long citizenshipId);
	public void saveCitizenship(Citizenship citizenship);
	
	
	/* ##### Comments methods starts from here ##### */
	public List<Comments> getComments();
	public Comments getComment(long commentId);
	public void deleteComment(long commentId);
	public void saveComment(Comments comments);
	
	/* ##### TeacherPerformances methods starts from here ##### */
	public List<TeacherPerformances> getTeacherPerformances();
	public TeacherPerformances getTeacherPerformances(long teacherPerformancesId);
	public void deleteTeacherPerformance(long teacherPerformancesId);
	public void saveTeacherPerformance(TeacherPerformances teacherPerformances);
}
