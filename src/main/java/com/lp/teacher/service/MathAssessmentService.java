package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.List;

import com.lp.model.Assignment;
import com.lp.model.GameLevel;
import com.lp.model.MathGameScores;
import com.lp.model.MathGear;
import com.lp.model.MathQuiz;
import com.lp.model.MathQuizQuestions;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentMathAssessMarks;

public interface MathAssessmentService {	
	public String saveQuizQuestion(String fraction, long csId, long quizId, String mode, ArrayList<String> answersArray,ArrayList<String> blankArray);
	public List<MathQuiz> getMathQuizByCsId(long csId);
	public List<MathQuiz> getAllMathQuizs(long csId);
	public List<MathQuizQuestions> getMathQuizQuestionsByQuizId(long mathQuizId);
	public String deleteMathQuizQuestion(long mathQuizId);
	public MathQuiz getMathQuizByQuizId(long mathQuizId);
	public String  assignQuizTest(Assignment assignment,ArrayList<Long> quizIdArray,ArrayList<Long> students);
	public List<StudentMathAssessMarks> getStudentMathAssessMarksByStudentAssignmentId(long studentAssignmentId, long quizId);
	public String udpateQuestionMark(long studentMathAssessMarksId, int mark, int correct, int wrong, long studentAssignmentId);
	public List<StudentAssignmentStatus> getStudentAssessmentTests(long assignmentId, long assignmentTypeId);
	public List<Assignment> getMathAssignedDates(long csId,String usedFor,String assignmentType);
	public List<StudentAssignmentStatus> getStudentAssessmentTestsByPercentage(long assignmentId, long assignmentTypeId);
	public List<MathQuiz> getMathQuizsByAssignmentId(long assignmentId);
	public List<StudentMathAssessMarks> getStudentMathAssessMarksByQuizId(long quizId,long assignmentId);
	public boolean assignGameToStudents(long csId, String dueDateStr,String title, String instructions, ArrayList<Long> students);
	public boolean assignGameByAdmin(ArrayList<Long> csIds, String dueDateStr,String title, String instructions);
	public List<GameLevel> getGameLevels();
	public List<MathGear> getMathGears();
	public List<StudentAssignmentStatus> getStudentMathGameAssessmentTests(long assignmentId, long assignmentTypeId);
	public List<MathGameScores> getStudentMathGameScores(long assignmentId);
	public List<MathGameScores> getStudentMathGameDetails(long studentAssignmentId);
}
