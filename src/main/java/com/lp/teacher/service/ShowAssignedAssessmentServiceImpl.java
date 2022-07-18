package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.Teacher;
import com.lp.teacher.dao.ShowAssignedAssessmentDAO;

public class ShowAssignedAssessmentServiceImpl implements ShowAssignedAssessmentService {
	@Autowired
	private ShowAssignedAssessmentDAO showAssignedAssessmentDao;
	static final Logger logger = Logger.getLogger(ShowAssignedAssessmentServiceImpl.class);

	@Override
	public List<Assignment> getTeacherAssignedDates(Teacher teacher,long csId,String usedFor){
		return showAssignedAssessmentDao.getTeacherAssignedDates(teacher, csId,usedFor);
	}
	@Override
	public List<Assignment> getAssignedAssessmentByDate(Teacher teacher,long csId,String usedFor){
		
		return showAssignedAssessmentDao.getAssignedAssessmentByDate(teacher,csId,usedFor);
	}
	@Override
	public int updateAssignments(long assignmentId, String duedate){
		return showAssignedAssessmentDao.updateAssignments(assignmentId,duedate);
	}
	@Override
	public int deleteAssignments(long assignmentId){
		return showAssignedAssessmentDao.deleteAssignments(assignmentId);
	}


	@Override
	public List<Assignment> getGroupAssignedDates(Teacher teacher, long csId,
			String usedFor) throws DataException {
		List<Assignment> assignments = Collections.emptyList();
		try{
			assignments = showAssignedAssessmentDao.getGroupAssignedDates(teacher, csId,usedFor);
		}catch(DataException e){
			logger.error("Error in getGroupAssignedDates() of ShowAssignedAssessmentServiceImpl" + e);
			throw new DataException("Error in getGroupAssignedDates() of ShowAssignedAssessmentServiceImpl",e);
		}
		 
		return assignments;
	}


	@Override
	public List<Assignment> getGroupAssignmentTitles(long csId, String usedFor,
			String assignedDate) throws DataException {
		List<Assignment> assignmentTitles = new ArrayList<Assignment>();
		try{
			assignmentTitles= showAssignedAssessmentDao.getGroupAssignmentTitles(csId, usedFor,	assignedDate);
		}catch(DataException e){
			logger.error("Error in getGroupAssignmentTitles() of  HomeworkManagerServiceImpl");
			throw new DataException(
					"Error in getGroupAssignmentTitles() of HomeworkManagerServiceImpl",e);
		}
		return assignmentTitles;
	}
}


