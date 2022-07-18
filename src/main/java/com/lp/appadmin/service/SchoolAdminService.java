package com.lp.appadmin.service;

import com.lp.custom.exception.DataException;
import com.lp.model.District;
import com.lp.model.SchoolLevel;
import com.lp.model.SchoolType;
import com.lp.model.Security;
import com.lp.model.States;
import com.lp.model.User;
import com.lp.model.UserRegistration;
import com.lp.model.School;

import java.util.List;

public interface SchoolAdminService {

	/* ##### School methods starts from here ##### */

	public List<School> getSchools();

	public School getSchool(long schoolId);

	public void deleteSchool(long schoolId);

	public void saveSchool(School school);

	/* ##### UserRegistration methods starts from here ##### */

	public List<UserRegistration> getUserRegistrations();

	public UserRegistration getUserRegistration(long userRegistrationId);
	
	public UserRegistration getUserRegistration(String emailId);

	public int deleteUserRegistration(long userRegistrationId);

	public boolean saveUserRegistration(UserRegistration userRegistration);

	public User getUserType(String userType);

	public List<UserRegistration> getUserRegistrations(String userType);

	/* getStates by country Id */

	public List<States> getStates(long countryId);

	// methods for SchoolType

	public List<SchoolType> getSchoolTypeList();
	
	public SchoolType getSchoolType(long schoolTypeId);

	public void deleteSchoolType(long schoolTypeId);

	public void saveSchoolType(SchoolType schoolType);

	// methods for SchoolLevel

	public List<SchoolLevel> getSchoolLevelList();
	
	public SchoolLevel getSchoolLevel(long schoolLevelId);

	public void deleteSchoolLevel(long schoolLevelId);

	public void saveSchoolLevel(SchoolLevel schoolLevel);
	
	public void UpdateUserRegistration(UserRegistration userRegistration);
	
	public void saveDistrict(District district);
	
	public List<District> getDistricts();
	
	public District getDistrict(long districtId);
	
	public void updateDistrict(District district);

	public int deleteDistrict(long districtId);
	
	public void updateSchool(School	school);

	public void saveAdminRegistration(UserRegistration userReg, School school, Security sec) throws DataException;

	public boolean updateAdminRegistration(UserRegistration userRegistration) throws DataException;
	
	public UserRegistration getUserReg(String emailId);
	
	public List<School> getSchoolList(long districtId);
	
}

