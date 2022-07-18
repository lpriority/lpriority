package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.AcademicGrades;

public interface AcademicGradesDAO {
	public AcademicGrades getAcademicGrade(long academicGradeId);
	public List<AcademicGrades> getAcademicGradeList();
	public void deleteAcademicGrade(long academicGradeId);
	public void saveAcademicGrade(AcademicGrades academicGrade);

}
