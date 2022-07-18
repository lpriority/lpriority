package com.lp.student.dao;

import java.util.List;

import com.lp.model.MathConversionTypes;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentMathAssessMarks;

public interface MathAssessmentTestDAO {
	public List<StudentMathAssessMarks> getMathAssessmentQues(long studentAssignmentId);
	public List<MathConversionTypes> getMathConversionTypes();
	public boolean submitMathTest(StudentAssignmentStatus stAssignmentStatus);
	public boolean autoSaveMathAnswer(StudentMathAssessMarks studMathAns);
	public StudentMathAssessMarks getStudentMathAssessment(long mathAssessMarksId);
}
