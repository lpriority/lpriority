package com.lp.student.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.model.MathConversionTypes;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentMathAssessMarks;
import com.lp.student.dao.MathAssessmentTestDAO;

public class MathAssessmentTestServiceImpl implements MathAssessmentTestService{
	static final Logger logger = Logger.getLogger(MathAssessmentTestServiceImpl.class);
	
	@Autowired
	private MathAssessmentTestDAO testDAO;
	@Override
	public List<StudentMathAssessMarks> getMathAssessmentQues(long studentAssignmentId){
		List<StudentMathAssessMarks> mathQuizQuestions = null;
		try{
			mathQuizQuestions = testDAO.getMathAssessmentQues(studentAssignmentId);			
		}
		catch(Exception e){
			logger.error("Error in getMathAssessment() of  MathAssessmentTestServiceImpl" +e.getStackTrace());
		}
		return mathQuizQuestions;
	}
	@Override
	public List<MathConversionTypes> getMathConversionTypes(){
		List<MathConversionTypes> conversionTypes = null;
		try{
			conversionTypes = testDAO.getMathConversionTypes();			
		}
		catch(Exception e){
			logger.error("Error in getMathConversionTypes() of  MathAssessmentTestServiceImpl" +e.getStackTrace());
		}
		return conversionTypes;		
	}
	@Override
	public boolean submitMathTest(StudentAssignmentStatus stAssignmentStatus){
		boolean flag = false;
		try{
			flag = testDAO.submitMathTest(stAssignmentStatus);			
		}
		catch(Exception e){
			logger.error("Error in submitMathTest() of  MathAssessmentTestServiceImpl" +e.getStackTrace());
		}
		return flag;	
	}
	@Override
	public boolean autoSaveMathAnswer(StudentMathAssessMarks studMathAns){
		boolean stat = false;
		try{
			stat = testDAO.autoSaveMathAnswer(studMathAns);			
		}
		catch(Exception e){
			logger.error("Error in autoSaveMathAnswer() of  MathAssessmentTestServiceImpl" +e.getStackTrace());
			e.printStackTrace();
		}
		return stat;	
	}
	@Override
	public StudentMathAssessMarks getStudentMathAssessment(long mathAssessMarksId){
		StudentMathAssessMarks studMathAssessMarks = null;
		try{
			studMathAssessMarks = testDAO.getStudentMathAssessment(mathAssessMarksId);			
		}
		catch(Exception e){
			logger.error("Error in getStudentMathAssessment() of  MathAssessmentTestServiceImpl" +e.getStackTrace());
			e.printStackTrace();
		}
		return studMathAssessMarks;
	}
}
