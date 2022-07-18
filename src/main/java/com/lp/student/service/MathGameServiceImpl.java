package com.lp.student.service;


import java.sql.SQLDataException;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.model.MathGameScores;
import com.lp.model.MathGear;
import com.lp.model.StudentAssignmentStatus;
import com.lp.student.dao.MathGameDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "mathGameService")
public class MathGameServiceImpl implements MathGameService {
	
	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;	
	@Autowired
	private MathGameDAO mathGameDAO;

	static final Logger logger = Logger.getLogger(MathGameServiceImpl.class);
	
	@Override
	public boolean submitGameLevel1(long studentAssignmentId,long noOfAttempts,long noOfIncorrects,String timeTaken, long mathGearId, long gameLevelId) {
		MathGameScores mathGameScores=new MathGameScores();
		boolean status=false;
		try{
			MathGear mathGear = new MathGear();
			mathGear.setMathGearId(mathGearId);
			mathGameScores=mathGameDAO.getMathGameScores(studentAssignmentId, mathGearId, gameLevelId);
			mathGameScores.setNoOfAttempts(noOfAttempts);
			mathGameScores.setNoOfInCorrects(noOfIncorrects);
			mathGameScores.setTimeTaken(timeTaken);
			mathGameScores.setStatus(WebKeys.TEST_STATUS_SUBMITTED);
			mathGameScores.setMathGear(mathGear);
			status=mathGameDAO.submitOrUpdateLevel(mathGameScores);						
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return status;
	}

	public MathGameScores getMathGameScores(long studentAssignmentId,long mathGearId, long gameLevelId){
		return mathGameDAO.getMathGameScores(studentAssignmentId, mathGearId, gameLevelId);
	}

	@Override
	public void updateAttempts(long noOfAttempts, long mathGameScoreId, long mathGearId) {
		MathGameScores mathGameScores=new MathGameScores();
		try{
			MathGear mathGear = new MathGear();
			mathGear.setMathGearId(mathGearId);
			mathGameScores=mathGameDAO.getMathGameScoresById(mathGameScoreId);
			mathGameScores.setMathGameScoreId(mathGameScoreId);
			mathGameScores.setNoOfAttempts(noOfAttempts);
			mathGameScores.setMathGear(mathGear);
			mathGameDAO.submitOrUpdateLevel(mathGameScores);
		}catch(Exception e){
			logger.error("Error in updateAttempts() of MathGameServiceImpl"	+ e);
		}
	}

	@Override
	public boolean submitLevel2(String time, long failCount, long studentAssignmentId, long noOfAttempts, long mathGearId, long gameLevelId) {
		MathGameScores mathGameScores=new MathGameScores();
		boolean status = false;
		try{
			MathGear mathGear = new MathGear();
			mathGear.setMathGearId(mathGearId);
			mathGameScores=mathGameDAO.getMathGameScores(studentAssignmentId, mathGearId, gameLevelId);
			mathGameScores.setNoOfInCorrects(failCount);
			mathGameScores.setTimeTaken(time);
			mathGameScores.setNoOfAttempts(noOfAttempts);
			mathGameScores.setStatus(WebKeys.TEST_STATUS_SUBMITTED);
			mathGameScores.setMathGear(mathGear);
			status = mathGameDAO.submitOrUpdateLevel(mathGameScores);
		}catch(Exception e){
			logger.error("Error in submitLevel2() of MathGameServiceImpl"	+ e);
		}
		return status;
	}

	@Override
	public boolean submitLevel3(String time, long mathGameScoreId, long mathGearId, long gameLevelId) {
		MathGameScores mathGameScores=new MathGameScores();
		boolean status = false;
		try{
			MathGear mathGear = new MathGear();
			mathGear.setMathGearId(mathGearId);
			mathGameScores=mathGameDAO.getMathGameScoresById(mathGameScoreId);
			mathGameScores.setMathGameScoreId(mathGameScoreId);
			mathGameScores.setTimeTaken(time);
			mathGameScores.setStatus(WebKeys.TEST_STATUS_SUBMITTED);
			/*if(mathGearId == 4 && gameLevelId == 3){
				mathGameScores.getStudentAssignmentStatus().setStatus(WebKeys.TEST_STATUS_SUBMITTED);
				mathGameScores.getStudentAssignmentStatus().setGradedStatus(WebKeys.GRADED_STATUS_GRADED);
				mathGameScores.getStudentAssignmentStatus().setSubmitdate(new Date());
			}*/			
			mathGameScores.setMathGear(mathGear);
			status = mathGameDAO.submitGameAssignment(mathGameScores);
		}catch(Exception e){
			logger.error("Error in submitLevel3() of MathGameServiceImpl"	+ e);
		}
		return status;
	}

	@Override
	public MathGameScores getPendingMathGameScoreByStudentAssignmentId(long studentAssignmentId) {
		return mathGameDAO.getPendingMathGameScoreByStudentAssignmentId(studentAssignmentId);
	}
	
	@RemoteMethod
	@Override	
	public boolean submitGearGameTest(long studentAssignmentId){
		StudentAssignmentStatus sas = new StudentAssignmentStatus();
		try {
			sas = performanceTaskDAO.getStudentAssignmentStatusById(studentAssignmentId);
		} catch (SQLDataException e) {
			e.printStackTrace();
		}
		return mathGameDAO.submitGearGameTest(sas);		
	}
}