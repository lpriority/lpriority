package com.lp.appadmin.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.appadmin.dao.SchoolDAO;
import com.lp.appadmin.dao.SchoolLevelDAO;
import com.lp.appadmin.dao.SchoolTypeDAO;
import com.lp.appadmin.dao.StatesDAO;
import com.lp.appadmin.dao.UserDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.District;
import com.lp.model.School;
import com.lp.model.SchoolLevel;
import com.lp.model.SchoolType;
import com.lp.model.Security;
import com.lp.model.States;
import com.lp.model.User;
import com.lp.model.UserRegistration;

@RemoteProxy(name = "regService")
public class SchoolAdminServiceImpl implements SchoolAdminService {

	@Autowired
	private SchoolDAO schoolDAO;

	@Autowired
	private UserRegistrationDAO userRegistrationDAO;

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private StatesDAO statesDAO;
	@Autowired
	private SchoolTypeDAO schoolTypeDAO;
	@Autowired
	private SchoolLevelDAO schoolLevelDAO;
	@Autowired
	private HttpSession session;

	static final Logger logger = Logger.getLogger(SchoolAdminServiceImpl.class);
	/* ########### School methods go here ########## */
	public void setSchoolDao(SchoolDAO schoolDAO) {
		this.schoolDAO = schoolDAO;
	}

	@Override
	public List<School> getSchools() {
		List<School> schoolList =schoolDAO.getSchoolList();
		/*for(int i=0;i<schoolList.size();i++)
		{
			schoolList.get(i).setCountryId(countryId);
		}*/
		return schoolList;
	}

	@Override
	public School getSchool(long schoolId) {
		return schoolDAO.getSchool(schoolId);
	}

	@Override
	public void deleteSchool(long schoolId) {
		schoolDAO.deleteSchool(schoolId);
	}

	@Override
	public void saveSchool(School school) throws DataException {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		school.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		school.setChangeDate(changeDate);
		school.setRegistrationDate(new java.sql.Date(new java.util.Date()
				.getTime()));
		String countryId = school.getCountryId();
		String stateId = school.getStateId();
		String schoolLevelId = school.getSchoolLevelId();
		String schoolTypeId = school.getSchoolTypeId();
		if (countryId != null && stateId != null && schoolLevelId != null
				&& schoolTypeId != null) {
			school.setStates(statesDAO.getState(Long.valueOf(stateId)));
			school.setSchoolLevel(schoolLevelDAO.getSchoolLevel(Long
					.valueOf(schoolLevelId)));
			school.setSchoolType(schoolTypeDAO.getSchoolType(Long
					.valueOf(schoolTypeId)));
		}
		try{
			schoolDAO.saveSchool(school);
		}catch(DataException e){
			throw new DataException(e.getMessage(),	e);
		}
	}

	/* ########### UserRegistration Type methods go here ########## */

	public void setUserRegistrationDao(UserRegistrationDAO userRegistrationDao) {
		this.userRegistrationDAO = userRegistrationDao;
	}

	@Override
	public List<UserRegistration> getUserRegistrations() {
		return userRegistrationDAO.getUserRegistrationList();
	}

	@Override
	public UserRegistration getUserRegistration(long userRegistrationId) {
		return userRegistrationDAO.getUserRegistration(userRegistrationId);
	}
	
	@Override
	public UserRegistration getUserRegistration(String emailId){
		return userRegistrationDAO.getLoginUserRegistration(emailId);
	}

	@Override
	public int deleteUserRegistration(long userRegistrationId) {
		return userRegistrationDAO.deleteUserRegistration(userRegistrationId);
	}

	@Override
	public boolean saveUserRegistration(UserRegistration userRegistration) {
		boolean flag = false;
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		String schoolId = userRegistration.getSchoolId();
		if (schoolId != null) {
			userRegistration.setSchool(getSchool(Long.valueOf(schoolId)));
		}
		userRegistration.setCreateDate(new java.sql.Date(new java.util.Date()
				.getTime()));
		userRegistration.setChangeDate(changeDate);
		if(session.getAttribute("adminReg1") != null){
			flag = true;
		}else{
			flag = userRegistrationDAO.checkUserRegistration(userRegistration);
		}
		
		if(flag){			
			userRegistrationDAO.saveUserRegistration(userRegistration);
			return true;
		}
		else{
			return false;
		}
	}
	@Override
	public void UpdateUserRegistration(UserRegistration userRegistration) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		userRegistration.setCreateDate(new java.sql.Date(new java.util.Date()
				.getTime()));
		userRegistration.setChangeDate(changeDate);
		userRegistrationDAO.saveUserRegistration(userRegistration);
	}

	@Override
	public List<UserRegistration> getUserRegistrations(String userType) {
		return userRegistrationDAO
				.getUserRegistrationList(getUserType(userType));
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public User getUserType(String userType) {
		return userDAO.getUserType(userType);
	}

	public void setStatesDAO(StatesDAO statesDAO) {
		this.statesDAO = statesDAO;
	}

	@Override
	@RemoteMethod
	public List<States> getStates(long countryId) {
		return statesDAO.getStatesList(countryId);
	}

	@Override
	public List<SchoolType> getSchoolTypeList() {
		return schoolTypeDAO.getSchoolTypeList();
	}

	@Override
	public SchoolType getSchoolType(long schoolTypeId) {
		return schoolTypeDAO.getSchoolType(schoolTypeId);
	}

	@Override
	public void deleteSchoolType(long schoolTypeId) {
		schoolTypeDAO.deleteSchoolType(schoolTypeId);
	}

	@Override
	public void saveSchoolType(SchoolType schoolType) {
		schoolTypeDAO.saveSchoolType(schoolType);
	}

	@Override
	public List<SchoolLevel> getSchoolLevelList() {
		return schoolLevelDAO.getSchoolLevelList();
	}

	@Override
	public SchoolLevel getSchoolLevel(long schoolLevelId) {
		return schoolLevelDAO.getSchoolLevel(schoolLevelId);
	}

	@Override
	public void deleteSchoolLevel(long schoolLevelId) {
		schoolLevelDAO.deleteSchoolLevel(schoolLevelId);
	}

	@Override
	public void saveSchoolLevel(SchoolLevel schoolLevel) {
		schoolLevelDAO.saveSchoolLevel(schoolLevel);
	}
	@Override
	public void saveDistrict(District district) {
		
		schoolDAO.saveDistrict(district);
		
	}
	@Override
	public void updateDistrict(District district) {
		schoolDAO.updateDistrict(district);
		
	}
	@Override
	public List<District> getDistricts() {
		List<District> districtList =schoolDAO.getDistrictList();
		/*for(int i=0;i<schoolList.size();i++)
		{
			schoolList.get(i).setCountryId(countryId);
		}*/
		return districtList;
	}
	@Override
	public District getDistrict(long districtId) {
		return schoolDAO.getDistrict(districtId);
	}
	@Override
	public int deleteDistrict(long districtId) {
		return schoolDAO.deleteDistrict(districtId);
	}
	@Override
	public void updateSchool(School school) {
		schoolDAO.updateSchool(school);		
	}

	@Override
	public void saveAdminRegistration(UserRegistration userReg, School school,Security sec) throws DataException {
		try {		
			GregorianCalendar cal = new GregorianCalendar();
			long millis = cal.getTimeInMillis();
			Timestamp changeDate = new Timestamp(millis);
			String schoolId = userReg.getSchoolId();
			if (schoolId != null) {
				userReg.setSchool(getSchool(Long.valueOf(schoolId)));
			}
			userReg.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
			userReg.setChangeDate(changeDate);			
			
			school.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
			school.setChangeDate(changeDate);
			school.setRegistrationDate(new java.sql.Date(new java.util.Date().getTime()));
			String countryId = school.getCountryId();
			String stateId = school.getStateId();
			String schoolLevelId = school.getSchoolLevelId();
			String schoolTypeId = school.getSchoolTypeId();
			if (countryId != null && stateId != null && schoolLevelId != null && schoolTypeId != null) {
				school.setStates(statesDAO.getState(Long.valueOf(stateId)));
				school.setSchoolLevel(schoolLevelDAO.getSchoolLevel(Long.valueOf(schoolLevelId)));
				school.setSchoolType(schoolTypeDAO.getSchoolType(Long.valueOf(schoolTypeId)));
			}								
			sec.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
			sec.setChangeDate(changeDate);						
			schoolDAO.saveAdminRegistration(userReg,school,sec);			
		} catch (DataException e) {
			logger.error("Error in saveAdminRegistration() of  SchoolAdminServiceImpl" + e);
			throw new DataException("Error in saveAdminRegistration() of SchoolAdminServiceImpl", e);
		}		
	}

	@Override
	public boolean updateAdminRegistration(UserRegistration userRegistration) throws DataException {
		boolean status = false;
		try {		
			status = schoolDAO.checkUserForUpdate(userRegistration);
			if(status){
				status = schoolDAO.updateAdminRegistration(userRegistration);
			}			
		} catch (DataException e) {
			logger.error("Error in updateAdminRegistration() of  SchoolAdminServiceImpl" + e);
			throw new DataException("Error in updateAdminRegistration() of SchoolAdminServiceImpl", e);
		}	
		return status;
	}
	@Override
	public UserRegistration getUserReg(String emailId) {
		return userRegistrationDAO.getUserRegistration(emailId);
	}
	
	@Override
	public List<School> getSchoolList(long districtId){
		List<School> schoolLst=new ArrayList<School>();
		try{
			schoolLst=schoolDAO.getSchoolList(districtId);
		}catch(Exception e){
			
		}
		return schoolLst;
	}
}
