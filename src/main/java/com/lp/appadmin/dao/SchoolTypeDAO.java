package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.SchoolType;

public interface SchoolTypeDAO {
	public SchoolType getSchoolType(long schoolTypeId);
	public List<SchoolType> getSchoolTypeList();
	public void deleteSchoolType(long schoolTypeId);
	public void saveSchoolType(SchoolType schoolType);

}
