package com.lp.student.dao;

import com.lp.model.GameLevel;
import com.lp.model.MathGameScores;
import com.lp.model.StudentAssignmentStatus;

public interface MathGameDAO {

	public boolean submitOrUpdateLevel(MathGameScores mathGameScores);
	public GameLevel getGameLevel(long gameLevelId);
	public MathGameScores getMathGameScores(long studentAssignmentId,long mathGearId, long gameLevelId);
	public MathGameScores getMathGameScoresById(long mathGameScoreId);
	public boolean submitGameAssignment(MathGameScores mathGameScores);
	public MathGameScores getPendingMathGameScoreByStudentAssignmentId(long studentAssignmentId);
	public boolean submitGearGameTest(StudentAssignmentStatus sas);
}
