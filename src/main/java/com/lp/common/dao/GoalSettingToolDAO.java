package com.lp.common.dao;

import java.util.List;

import com.lp.model.AcademicYear;
import com.lp.model.CAASPPScores;
import com.lp.model.GoalSampleIdeas;
import com.lp.model.GoalStrategies;
import com.lp.model.StarScores;
import com.lp.model.StudentCAASPPOwnStrategies;
import com.lp.model.StudentStarStrategies;
import com.lp.model.Trimester;

public interface GoalSettingToolDAO {
	
	public List<CAASPPScores> getStudentCAASPPScores(long goalTypeId,long studentId);

	public List<GoalStrategies> getGoalStrategiesByTypeId(long goalTypeId);
	
	public List<StudentStarStrategies> getStudentStarStrategiesByTrimesterId(long studentId,long gradeId,long trimesterId,long caasppTypesId);
	
	public CAASPPScores getCAASPPScoresByCaaSppScoresId(long caasppScoresId);
	
	public GoalStrategies getGoalStrategiesById(long goalStrategiesId);
	
	public boolean autoSaveStudStarStrategies(StudentStarStrategies studStarStrategy);
	
    public long checkExistsGoalStrategies(long studentId,long gradeId,long trimesterId,long goalCount,long caasppTypesId);

    public boolean autoSaveStudOwnStrategies(StudentCAASPPOwnStrategies studOwnStrategy);
	
	public List<StarScores> getStudentStarScores(long goalTypeId,long studentId,long gradeId);
	
	public StarScores getStarScoresByStarScoresId(long starScoresId);
	
	public List<StudentCAASPPOwnStrategies> getStudentOwnStrategiesByTypeId(long studentId,long gradeId);
	
	public long checkExistsStudOwnStrategies(long studentId,long gradeId,long goalCount);
	
	public StarScores getStudentStarScoresByTypeId(long goalTypeId,long studentId,long gradeId,long trimesterId);
	
	public List<StarScores> getStudentStarTrimesterScores(long goalTypeId,long studentId,long gradeId);
	
	public List<GoalSampleIdeas> getGoalSampleIdeas();
	
	public List<Trimester> getTrimesterList();
	
	public long getMAXTrimesterId(long studentId,long gradeId,long caasppTypesId);
	
	public List<StudentStarStrategies> getStudentStarStrategiesByTrimesterId(long gradeId,long trimesterId,List<Long> studentLst);
	
	public AcademicYear getCurrentAcademicYr();
	
	public List<StudentCAASPPOwnStrategies> getStudentCAASPPOwnStrategies(long gradeId,long trimesterId,List<Long> studentLst);
	
	public List<StudentStarStrategies> getStudentStarStrategies(String caasppTypes);
	
	public List<StarScores> getAllTeachersFromStarScores(long gradeId);
	
	public List<StarScores> getAllStudentsFromStarScores(long gradeId);
	
	public List<StarScores> getAllStudentsFromStarScores(long teacherId, long gradeId);
	
	public List<StarScores> getStudentStarScoresByStudentId(long studentId,long gradeId);
	 
	public List<StudentStarStrategies> getStudentStarStrategies(long studentId,long gradeId);
	
	public List<CAASPPScores> getCAASPPScoresByStudentId(long studentId, long gradeId);
	
	public long getMAXStarScore(long studentId,long gradeId,long caasppTypesId);

	public long getMaxOrderIdByGradeId(long gradeId, long caasppTypesId);
	
	public List<Long> getStudentsBySection(long csId);
	
	
}
