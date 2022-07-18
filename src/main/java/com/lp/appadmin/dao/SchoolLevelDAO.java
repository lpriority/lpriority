package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.SchoolLevel;

public interface SchoolLevelDAO {
	public SchoolLevel getSchoolLevel(long schoolLevelId);
	public List<SchoolLevel> getSchoolLevelList();
	public void deleteSchoolLevel(long schoolLevelId);
	public void saveSchoolLevel(SchoolLevel schoolLevel);
}
