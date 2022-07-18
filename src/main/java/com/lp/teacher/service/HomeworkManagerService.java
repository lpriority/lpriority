package com.lp.teacher.service;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.JacQuestionFile;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;

public interface HomeworkManagerService {

	public List<Assignment> getAssignedHomeworks(Teacher teacher,long csId,String usedFor,long lessonId,String stat);
	public List<AssignmentQuestions>getQuestionsByAssignmentId(long assignmentId);
	List<JacQuestionFile> getJacTemplateQuestionsFile(long assignmentId)throws DataException;
	public List<StudentAssignmentStatus> getHomeworkReports(Teacher teacher,long csId,String usedFor,String assignedDate,long title);
	public List<Assignment> getAssignmentTitles(long csId,String usedFor,String assignedDate);
	public List<Assignment> getTestTitles(long csId,String usedFor);
	public Object getHomeworkTitles(long csId, String lpUsedForHomeworks,String sqlDate);
	public List<Assignment> getRTIResultsTitles(long csId, String usedFor,String assignedDate);
	public List<Assignment> getFluencyTitles(long csId,String assignedDate);
	public List<Assignment> getAccuracyTitles(long csId,String assignedDate);
}
