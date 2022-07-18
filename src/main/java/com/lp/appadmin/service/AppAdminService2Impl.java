package com.lp.appadmin.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.appadmin.dao.CitizenshipDAO;
import com.lp.appadmin.dao.CommentsDAO;
import com.lp.appadmin.dao.CountryDAO;
import com.lp.appadmin.dao.StatesDAO;
import com.lp.appadmin.dao.TeacherPerformancesDAO;
import com.lp.model.Citizenship;
import com.lp.model.Comments;
import com.lp.model.Country;
import com.lp.model.States;
import com.lp.model.TeacherPerformances;

public class AppAdminService2Impl implements AppAdminService2 {
	
	static final Logger logger = Logger.getLogger(AppAdminService2Impl.class);

	@Autowired
	private CountryDAO countryDAO;

	@Autowired
	private StatesDAO statesDAO;

	@Autowired
	private CitizenshipDAO citizenshipDAO;

	@Autowired
	private CommentsDAO commentsDAO;

	@Autowired
	private TeacherPerformancesDAO teacherPerformancesDAO;

	/* ##### country methods starts from here ##### */

	public void setCountryDao(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	public List<Country> getCountries() {
		return countryDAO.getCountryList();
	}

	public Country getCountry(long countryId) {
		return countryDAO.getCountry(countryId);
	}

	public void deleteCountry(long countryId) {
		countryDAO.deleteCountry(countryId);
	}

	public void saveCountry(Country country) {
		countryDAO.saveCountry(country);
	}

	/* ##### states methods starts from here ##### */

	public void setStatesDao(StatesDAO statesDAO) {
		this.statesDAO = statesDAO;
	}

	public List<States> getStates() {
		return statesDAO.getStatesList();
	}
	public List<States> getStates(long countryId){
		return statesDAO.getStatesList(countryId);
	}

	public List<States> loadStates(long countryId){
		return statesDAO.getStatesList(countryId);
	}
	public States getState(long stateId) {
		return statesDAO.getState(stateId);
	}

	public void deleteState(long stateId) {
		statesDAO.deleteState(stateId);
	}
	
	public long getCountryByState(long stateId) {
		return statesDAO.getCountrybyStateId(stateId);
		
	}

	public void saveState(States state) {
		state.setCountry(getCountry(Long.valueOf(state.getCountryId())));
		statesDAO.saveState(state);
	}

	/* ##### citizenship methods starts from here ##### */

	public void setCitizenshipDao(CitizenshipDAO citizenshipDAO) {
		this.citizenshipDAO = citizenshipDAO;
	}

	public List<Citizenship> getCitizenships() {
		return citizenshipDAO.getCitizenshipList();
	}

	public Citizenship getCitizenship(long citizenshipId) {
		return citizenshipDAO.getCitizenship(citizenshipId);
	}

	public void deleteCitizenship(long citizenshipId) {
		citizenshipDAO.deleteCitizenship(citizenshipId);
	}

	public void saveCitizenship(Citizenship citizenship) {
		citizenshipDAO.saveCitizenship(citizenship);
	}

	/* ##### comments methods starts from here ##### */

	public void setCommentsDao(CommentsDAO commentsDAO) {
		this.commentsDAO = commentsDAO;
	}

	public List<Comments> getComments() {
		return commentsDAO.getCommentsList();
	}

	public Comments getComment(long commentId) {
		return commentsDAO.getComments(commentId);
	}

	public void deleteComment(long commentId) {
		commentsDAO.deleteComments(commentId);
	}

	public void saveComment(Comments comments) {
		commentsDAO.saveComments(comments);
	}

	/* ##### TeacherPerformances methods starts from here ##### */

	public void setTeacherPerformancesDao(
			TeacherPerformancesDAO teacherPerformancesDAO) {
		this.teacherPerformancesDAO = teacherPerformancesDAO;
	}

	public List<TeacherPerformances> getTeacherPerformances() {
		return teacherPerformancesDAO.getTeacherPerformancesList();
	}

	public TeacherPerformances getTeacherPerformances(long teacherPerformancesId) {
		return teacherPerformancesDAO
				.getTeacherPerformance(teacherPerformancesId);
	}

	public void deleteTeacherPerformance(long teacherPerformancesId) {
		teacherPerformancesDAO.deleteTeacherPerformance(teacherPerformancesId);
	}

	public void saveTeacherPerformance(TeacherPerformances teacherPerformances) {
		teacherPerformancesDAO.saveTeacherPerformance(teacherPerformances);
	}
}
