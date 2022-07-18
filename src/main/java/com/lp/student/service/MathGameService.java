package com.lp.student.service;

import com.lp.model.MathGameScores;

public interface MathGameService {
	
	public boolean submitGameLevel1(long studentAssignmentId,long noOfAttempts,long noOfIncorrects,String timeTaken, long mathGearId, long gameLevelId);

	public MathGameScores getMathGameScores(long studentAssignmentId,long mathGearId, long gameLevelId);

	public void updateAttempts(long noOfAttempts, long mathGameScoreId, long mathGearId);

	public boolean submitLevel2(String time, long failCount, long studentAssignmentId, long noOfAttempts, long mathGearId, long gameLevelId);

	public boolean submitLevel3(String time, long mathGameScoreId, long mathGearId, long gameLevelId);

	public MathGameScores getPendingMathGameScoreByStudentAssignmentId(long studentAssignmentId);
	
	public boolean submitGearGameTest(long studentAssignmentId);
}
