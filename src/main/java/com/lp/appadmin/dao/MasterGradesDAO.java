package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.MasterGrades;

public interface MasterGradesDAO {

	public MasterGrades getMasterGrade(long masterGradeId);

	public List<MasterGrades> getMasterGradesList();

	
	public void deleteMasterGrades(long masterGradeId);

	public void saveMasterGrades(MasterGrades masterGrade);
	
		
	//public Hashtable getschoolGrades(int schoolId);

}
