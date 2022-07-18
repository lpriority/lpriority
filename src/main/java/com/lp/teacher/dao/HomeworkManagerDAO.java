package com.lp.teacher.dao;

import java.util.List;

import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;

public interface HomeworkManagerDAO {
	public List<Assignment> getAssignedHomeworks(Teacher teacher,long csId,String usedFo,long lessonId);
	public List<AssignmentQuestions> getQuestionsByAssignmentId(long assignmentId);
	public List<Assignment> getAllAssignedHomeworks(Teacher teacher,long csId,String usedFo,long lessonId);
	public List<StudentAssignmentStatus> getHomeworkReports(Teacher teacher,long csId,String usedFor,String assignedDate,long title);
	public List<Assignment> getAssignmentTitles(long csId,String usedFor,String assignedDate);
	public List<Assignment> getTestTitles(long csId,String usedFor);
	public List<Assignment> getHomeworkTitles(long csId, String usedFor,String assignedDate);
	public List<Assignment> getRTIResultsTitles(long csId, String usedFor,String assignedDate);
	public List<Assignment> getFluencyTitles(long csId,String assignedDate);
	public List<Assignment> getAccuracyTitles(long csId,String assignedDate);
}
