package com.lp.teacher.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.Teacher;

public interface ShowAssignedAssessmentDAO {

	public List<Assignment> getTeacherAssignedDates(Teacher teacher,long csId,String usedFor);
	public List<Assignment> getAssignedAssessmentByDate(Teacher teacher,long csId,String usedFor);
	public int updateAssignments(long assignmentId, String duedate);
	public int deleteAssignments(long assignmentId);
	public List<Assignment> getGroupAssignedDates(Teacher teacher, long csId,String usedFor) throws DataException;
	public List<Assignment> getGroupAssignmentTitles(long csId, String usedFor,	String assignedDate) throws DataException;
}