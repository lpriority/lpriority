package com.lp.teacher.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.AssessmentDAO;
import com.lp.common.service.CommonService;
import com.lp.model.Assignment;
import com.lp.model.AssignmentType;
import com.lp.model.ClassStatus;
import com.lp.model.GameLevel;
import com.lp.model.MathGameScores;
import com.lp.model.MathGear;
import com.lp.model.MathQuiz;
import com.lp.model.MathQuizQuestions;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentMathAssessMarks;
import com.lp.teacher.dao.MathAssessmentDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "mathAssessmentService")
public class MathAssessmentServiceImpl implements MathAssessmentService {
	@Autowired
	private MathAssessmentDAO mathAssessmentDAO;
	@Autowired
	private AssessmentDAO assessmentDAO;
	@Autowired
	private CommonService commonservice;
	
	@Override
	public String saveQuizQuestion(String fraction, long csId, long quizId, String mode, ArrayList<String> answersArray,ArrayList<String> blankArray) {
		return mathAssessmentDAO.saveQuizQuestion(fraction, csId, quizId, mode, answersArray, blankArray);
	}
	
	@Override
	@RemoteMethod
	public List<MathQuiz> getMathQuizByCsId(long csId){
		return mathAssessmentDAO.getMathQuizByCsId(csId);
	}
	
	@Override
	@RemoteMethod
	public List<MathQuiz> getAllMathQuizs(long csId){
		return mathAssessmentDAO.getAllMathQuizs(csId);
	}
	
	@Override
	public MathQuiz getMathQuizByQuizId(long mathQuizId){
		return mathAssessmentDAO.getMathQuizByQuizId(mathQuizId);
	}
	
	@Override
	@RemoteMethod
	public List<MathQuizQuestions> getMathQuizQuestionsByQuizId(long mathQuizId){
		return mathAssessmentDAO.getMathQuizQuestionsByQuizId(mathQuizId);
	}
	
	@Override
	public String deleteMathQuizQuestion(long mathQuizId){
		return mathAssessmentDAO.deleteMathQuizQuestion(mathQuizId);
	}
	
	@Override
	public String assignQuizTest(Assignment assignment,ArrayList<Long> quizIdArray,ArrayList<Long> students){
		return mathAssessmentDAO.assignQuizTest(assignment, quizIdArray, students);
	}
	
	@Override
	public List<StudentMathAssessMarks> getStudentMathAssessMarksByStudentAssignmentId(long studentAssignmentId, long quizId){
		return mathAssessmentDAO.getStudentMathAssessMarksByStudentAssignmentId(studentAssignmentId, quizId);
	}
	
	@Override
	public String udpateQuestionMark(long studentMathAssessMarksId, int mark, int correct, int wrong, long studentAssignmentId){
		return mathAssessmentDAO.udpateQuestionMark(studentMathAssessMarksId, mark, correct, wrong, studentAssignmentId);
	}
	
	@Override
	@RemoteMethod
	public List<StudentAssignmentStatus> getStudentAssessmentTests(long assignmentId, long assignmentTypeId){
		return mathAssessmentDAO.getStudentAssessmentTests(assignmentId, assignmentTypeId);
	}
	
	@Override
	@RemoteMethod
	public List<Assignment> getMathAssignedDates(long csId,String usedFor,String assignmentType){
		return mathAssessmentDAO.getMathAssignedDates(csId, usedFor, assignmentType);
	}
	
	@Override
	@RemoteMethod
	public List<StudentAssignmentStatus> getStudentAssessmentTestsByPercentage(long assignmentId, long assignmentTypeId){
		return mathAssessmentDAO.getStudentAssessmentTestsByPercentage(assignmentId, assignmentTypeId);
	}
	
	@Override
	@RemoteMethod
	public List<MathQuiz> getMathQuizsByAssignmentId(long assignmentId){
		return mathAssessmentDAO.getMathQuizsByAssignmentId(assignmentId);
	}
	
	@Override
	public List<StudentMathAssessMarks> getStudentMathAssessMarksByQuizId(long quizId,long assignmentId){
		return mathAssessmentDAO.getStudentMathAssessMarksByQuizId(quizId,assignmentId);
	}

	@Override
	public boolean assignGameToStudents(long csId, String dueDateStr,
			String title, String instructions, ArrayList<Long> students) {
		boolean status = false;
		try{
			List<MathGameScores> gameScores = new ArrayList<MathGameScores>();
			DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
			Date currentDate = new Date();
			Date dueDate = new Date();
			dueDate = formatter.parse(dueDateStr);
			AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(WebKeys.GEAR_GAME);						
			ClassStatus classStatus = new ClassStatus();
			classStatus.setCsId(csId);
			Assignment assignment = new Assignment();
			assignment.setClassStatus(classStatus);
			assignment.setAssignmentType(assignmentType);
			assignment.setDateDue(dueDate);
			assignment.setDateAssigned(currentDate);
			assignment.setAssignStatus(WebKeys.ACTIVE);
			assignment.setUsedFor(WebKeys.RTI);
			assignment.setTitle(title);
			assignment.setInstructions(instructions);	
			List<StudentAssignmentStatus> studentAssignmentList = new ArrayList<StudentAssignmentStatus>();
			List<GameLevel> gameLevels = mathAssessmentDAO.getGameLevels();
			List<MathGear> mathGears = mathAssessmentDAO.getMathGears();
			for (Long studentId : students){
				StudentAssignmentStatus stdAssignmentStatus = new StudentAssignmentStatus();
				stdAssignmentStatus.setAssignment(assignment);
				Student student = new Student();
				student.setStudentId(studentId);
				stdAssignmentStatus.setStudent(student);
				stdAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
				stdAssignmentStatus.setStatus(WebKeys.TEST_STATUS_PENDING);
				studentAssignmentList.add(stdAssignmentStatus);
				for (MathGear mathGear : mathGears) {
					for(GameLevel gameLevel : gameLevels){
						MathGameScores gameScore = new MathGameScores();
						gameScore.setGameLevel(gameLevel);
						gameScore.setMathGear(mathGear);
						gameScore.setStudentAssignmentStatus(stdAssignmentStatus);
						gameScore.setStatus(WebKeys.TEST_STATUS_PENDING);
						gameScores.add(gameScore);					
					}
				}				
			}
			status = mathAssessmentDAO.assignGameToStudents(studentAssignmentList,assignment, gameScores);
		}catch(Exception e){
			
		}
		return status;
	}

	@Override
	public List<GameLevel> getGameLevels() {
		return mathAssessmentDAO.getGameLevels();
	}
	
	@Override
	public List<MathGear> getMathGears(){
		return mathAssessmentDAO.getMathGears();
	}
	
	@Override
	@RemoteMethod
	public List<StudentAssignmentStatus> getStudentMathGameAssessmentTests(long assignmentId, long assignmentTypeId){
		return mathAssessmentDAO.getStudentMathGameAssessmentTests(assignmentId, assignmentTypeId);
	}
	@Override
	public List<MathGameScores> getStudentMathGameScores(long assignmentId){
		List<MathGameScores> studMathGameScoresLst=new ArrayList<MathGameScores>();
		try{
			studMathGameScoresLst=mathAssessmentDAO.getStudentMathGameScores(assignmentId);
		}catch(Exception e){
			
		}
		return studMathGameScoresLst;
	}
	@Override
	public List<MathGameScores> getStudentMathGameDetails(long studentAssignmentId){
		List<MathGameScores> studMathGameScoresLst=new ArrayList<MathGameScores>();
		try{
			studMathGameScoresLst=mathAssessmentDAO.getStudentMathGameDetails(studentAssignmentId);
		}catch(Exception e){
			
		}
		return studMathGameScoresLst;
	}
	
	public boolean assignGameByAdmin(ArrayList<Long> csIds, String dueDateStr,String title, String instructions){
		boolean status = false;
		try{
			List<MathGameScores> gameScores = new ArrayList<MathGameScores>();
			DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
			Date currentDate = new Date();
			Date dueDate = new Date();
			dueDate = formatter.parse(dueDateStr);
			List<StudentAssignmentStatus> studentAssignmentList = new ArrayList<StudentAssignmentStatus>();
			List<Assignment> assignmentLt = new ArrayList<Assignment>();
			AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(WebKeys.GEAR_GAME);		
			List<GameLevel> gameLevels = mathAssessmentDAO.getGameLevels();
			List<MathGear> mathGears = mathAssessmentDAO.getMathGears();	
					
			for (Long csId : csIds) {
				ClassStatus classStatus = new ClassStatus();
				classStatus.setCsId(csId);
				Assignment assignment = new Assignment();
				assignment.setAssignmentType(assignmentType);
				assignment.setDateDue(dueDate);
				assignment.setDateAssigned(currentDate);
				assignment.setAssignStatus(WebKeys.ACTIVE);
				assignment.setUsedFor(WebKeys.RTI);
				assignment.setTitle(title);
				assignment.setInstructions(instructions);	
				assignment.setClassStatus(classStatus);		
				assignmentLt.add(assignment);
				List<Student> studentLt = commonservice.getStudentsBySection(csId);
				for (Student student : studentLt){
					StudentAssignmentStatus stdAssignmentStatus = new StudentAssignmentStatus();
					stdAssignmentStatus.setAssignment(assignment);
					stdAssignmentStatus.setStudent(student);
					stdAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
					stdAssignmentStatus.setStatus(WebKeys.TEST_STATUS_PENDING);
					studentAssignmentList.add(stdAssignmentStatus);
					for (MathGear mathGear : mathGears) {
						for(GameLevel gameLevel : gameLevels){
							MathGameScores gameScore = new MathGameScores();
							gameScore.setGameLevel(gameLevel);
							gameScore.setMathGear(mathGear);
							gameScore.setStudentAssignmentStatus(stdAssignmentStatus);
							gameScore.setStatus(WebKeys.TEST_STATUS_PENDING);
							gameScores.add(gameScore);					
						}
					}				
				}
			}
			status = mathAssessmentDAO.assignGameByAdmin(studentAssignmentList, assignmentLt, gameScores);
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
   	
}