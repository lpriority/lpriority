package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.GradeLevel;

public interface GradeLevelDAO {
	
	public GradeLevel getGradeLevel(long gradeLevelId);
	
	public List<GradeLevel> getGradeLevelList();
	
	public void deleteGradeLevel(long gradeLevelId);
	
	public void saveGradeLevel(GradeLevel gradeLevel);

}
