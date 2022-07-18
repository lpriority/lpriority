package com.lp.common.service;


import java.util.List;

import com.lp.model.AcademicYear;
import com.lp.model.CAASPPScores;
import com.lp.model.GoalSampleIdeas;
import com.lp.model.GoalStrategies;
import com.lp.model.StarScores;
import com.lp.model.Student;
import com.lp.model.StudentCAASPPOwnStrategies;
import com.lp.model.StudentStarStrategies;
import com.lp.model.Teacher;
import com.lp.model.Trimester;

public interface GoalSettingToolService {

	public List<CAASPPScores> getStudentCAASPPScores(long caasppTypesId,long studentId);

	public List<GoalStrategies> getGoalStrategiesByTypeId(long caasppTypesId);
	
	public List<StudentStarStrategies> getStudentStarStrategiesByTrimesterId(long studentId,long gradeId,long trimesterId,long caasppTypesId);
	
	public CAASPPScores getCAASPPScoresByCaaSppScoresId(long caasppScoresId);
	
	public void autoSaveStudStarStrategies(long strategyId,Student student,long trimesterId,long goalCount,long caasppTypesId);
		
	public void autoSaveStudOwnStrategies(Student student,String studOwnStrategyDesc,long goalCount);
	
	public List<StarScores> getStudentStarScores(long goalTypeId,long studentId,long gradeId);
	
	public List<StudentCAASPPOwnStrategies> getStudentOwnStrategiesByTypeId(long studentId,long gradeId);
	
	public StarScores getStudentStarScoresByTypeId(long goalTypeId,long studentId,long gradeId,long trimesterId);
	
	public List<StarScores> getStudentStarTrimesterScores(long goalTypeId,long studentId,long gradeId);
	
	public List<GoalSampleIdeas> getGoalSampleIdeas();
	
	public List<Trimester> getTrimesterList();
	
	public AcademicYear getCurrentAcademicYr();
	 	
	public List<StudentStarStrategies> getStudentStarStrategies(String caasppTypes);
	 
	public List<StarScores> getAllTeachersFromStarScores(long gradeId);
	 
	public List<StarScores> getAllStudentsFromStarScores(long gradeId);
	
	public List<StarScores> getAllStudentsFromStarScores(long teacherId, long gradeId);
	
	public List<StarScores> getStudentStarScoresByStudentId(long studentId, long gradeId);
	
	public List<StudentStarStrategies> getStudentStarStrategies(long studentId, long gradeId);
	
	public List<CAASPPScores> getCAASPPScoresByStudentId(long studentId, long gradeId);
	
	public long getMAXStarScore(long studentId,long gradeId,long caasppTypesId);
	
	public List<StarScores> getAllStudents(long teacherId, long gradeId);
	
	public List<Teacher> getAllTeachers(long gradeId);
	
	public List<Long> getStudentsByCsId(long csId);
	
	public List<Student> getStudentsListByGradeIdandReportId(long gradeId,long reportId,List<Long> studentLst);
}