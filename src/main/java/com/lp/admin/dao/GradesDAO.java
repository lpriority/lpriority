package com.lp.admin.dao;

import java.util.List;

import com.lp.model.Grade;

public interface GradesDAO {

	public Grade getSchoolGrade(long gradeId);

	public List<Grade> getGradesList();

	public void deleteGrades(long gradeId);

	public void saveGrades(Grade Grade);

	public List<Grade> getGradesList(long schoolId);

	public void UpdateGrades(Grade grade);

	public int checkgradeExists(Grade grade);

	public Grade getGrade(long gradeId);
	
	public long getMasterGradeIdbyGradeId(long gradeId);
}
