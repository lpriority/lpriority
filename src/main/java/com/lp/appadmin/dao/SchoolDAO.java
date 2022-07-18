package com.lp.appadmin.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.District;
import com.lp.model.School;
import com.lp.model.Security;
import com.lp.model.UserRegistration;

public interface SchoolDAO {
	
	public School getSchool(long schoolId);
	
	public List<School> getSchoolList();
	
	public void deleteSchool(long schoolId);
	
	public void saveSchool(School school);
	
	public void saveDistrict(District district);
	
	public List<District> getDistrictList();
	
	public District getDistrict(long districtId);

	public void updateDistrict(District district);
	
	public int deleteDistrict(long districtId);
	
	public void updateSchool(School school);

	public void saveAdminRegistration(UserRegistration userReg, School school,Security sec) throws DataException;

	public boolean updateAdminRegistration(UserRegistration userRegistration) throws DataException;

	public boolean checkUserForUpdate(UserRegistration userRegistration) throws DataException;
	
	public List<School> getSchoolList(long districtId);
	
}
