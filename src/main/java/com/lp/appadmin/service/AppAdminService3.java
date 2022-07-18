package com.lp.appadmin.service;

import java.util.List;

import com.lp.model.AcademicGrades;
import com.lp.model.AcademicPerformance;
import com.lp.model.GradeLevel;
import com.lp.model.Gradeevents;
import com.lp.model.MasterGrades;

public interface AppAdminService3 {

	/* ##### AcademicGrades methods goes here ##### */
	public List<AcademicGrades> getAcademicGrades();

	public AcademicGrades getAcademicGrade(long academicGradeId);

	public void deleteAcademicGrade(long academicGradeId);

	public void saveAcademicGrade(AcademicGrades academicGrade);

	/* ##### AcademicPerformance methods goes here ##### */

	public List<AcademicPerformance> getAcademicPerformances();

	public AcademicPerformance getAcademicPerformance(long academicPerformanceId);

	public void deleteAcademicPerformance(long academicPerformanceId);

	public void saveAcademicPerformance(AcademicPerformance academicPerformanc);

	/* ##### GradeLevel methods goes here ##### */
	public List<GradeLevel> getGradeLevel();

	public GradeLevel getGradeLevel(long gradeLevelId);

	public void deleteGradeLevel(long gradeLevelId);

	public void saveGradeLevel(GradeLevel gradeLevel);

	/* ##### Gradeevents methods goes here ##### */
	public List<Gradeevents> getGradeEvents();

	public Gradeevents getGradeEvent(long gradeEventId);

	public void deleteGradeEvent(long gradeEventId);

	public void saveGradeEvent(Gradeevents gradeEvent);

	/* ##### MasterGrades methods goes here ##### */
	public List<MasterGrades> getMasterGrades();

	public MasterGrades getMasterGrade(long masterGradeId);

	public void deleteMasterGrade(long masterGradeId);

	public void saveMasterGrade(MasterGrades masterGrade);

}
