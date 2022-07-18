package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.JacQuestionFile;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.teacher.dao.HomeworkManagerDAO;
import com.lp.utils.WebKeys;

public class HomeworkManagerServiceImpl implements HomeworkManagerService {

	@Autowired
	private HomeworkManagerDAO homeworkManagerDao;
	static final Logger logger = Logger.getLogger(HomeworkManagerServiceImpl.class);
	
	@Override
	public List<Assignment> getAssignedHomeworks(Teacher teacher, long csId,
			String usedFor, long lessonId, String stat) {
		if (stat.equalsIgnoreCase(WebKeys.LP_TAB_CURRENT_HOMEWORK))
			return homeworkManagerDao.getAssignedHomeworks(teacher, csId,
					usedFor, lessonId);
		else
			return homeworkManagerDao.getAllAssignedHomeworks(teacher, csId,
					usedFor, lessonId);

	}

	@Override
	public List<AssignmentQuestions> getQuestionsByAssignmentId(
			long assignmentId) {
		List<AssignmentQuestions> assignmentQues = new ArrayList<AssignmentQuestions>();
		List<AssignmentQuestions> assignmentQuesFilter = new ArrayList<AssignmentQuestions>();
		try{
			assignmentQues = homeworkManagerDao.getQuestionsByAssignmentId(assignmentId);
			HashSet<Long> key=new HashSet<Long>();
			for(AssignmentQuestions as :assignmentQues){
				if(!key.contains(as.getQuestions().getQuestionId())){
					assignmentQuesFilter.add(as);
					key.add(as.getQuestions().getQuestionId());
				}
			}
		}
		catch(Exception e){
			logger.error("Error in getHomeworkReports() of  HomeworkManagerServiceImpl");
			throw new DataException("Error in getHomeworkReports() of HomeworkManagerServiceImpl",e);
		}
		
		return assignmentQuesFilter;
	}

	@Override
	public List<JacQuestionFile> getJacTemplateQuestionsFile(long assignmentId)
			throws DataException {
		List<JacQuestionFile> jacquestionFiles = new ArrayList<JacQuestionFile>();
		Map<Long, String> gradeMap = new LinkedHashMap<Long, String>();

		try {
			List<AssignmentQuestions> teacherAssingedGrades = getQuestionsByAssignmentId(assignmentId);
			if (teacherAssingedGrades.size() > 0) {
				for (AssignmentQuestions as : teacherAssingedGrades) {
					if (!gradeMap.containsKey(as.getQuestions()
							.getJacTemplate().getJacQuestionFile()
							.getJacQuestionFileId())) {
						gradeMap.put(as.getQuestions().getJacTemplate()
								.getJacQuestionFile().getJacQuestionFileId(),
								as.getQuestions().getJacTemplate()
										.getJacQuestionFile().getFilename());
						jacquestionFiles.add(as.getQuestions().getJacTemplate()
								.getJacQuestionFile());
					}
				}
			} else {
				logger.error("Error in getJacTemplateQuestionsFile() of  HomeworkManagerServiceImpl");
				throw new DataException(
						"Error in getJacTemplateQuestionsFile() of HomeworkManagerServiceImpl");
			}
		} catch (DataException e) {
			logger.error("Error in getJacTemplateQuestionsFile() of  HomeworkManagerServiceImpl");
			throw new DataException(
					"Error in getJacTemplateQuestionsFile() of HomeworkManagerServiceImpl",
					e);
		}

		return jacquestionFiles;
	}

	@Override
	public List<StudentAssignmentStatus> getHomeworkReports(Teacher teacher,
			long csId, String usedFor, String assignedDate, long title) {
		List<StudentAssignmentStatus> homeworkReports = new ArrayList<StudentAssignmentStatus>();
		try{
		homeworkReports= homeworkManagerDao.getHomeworkReports(teacher, csId, usedFor,
				assignedDate, title);
		}catch(DataException e){
			logger.error("Error in getHomeworkReports() of  HomeworkManagerServiceImpl");
			throw new DataException(
					"Error in getHomeworkReports() of HomeworkManagerServiceImpl",
					e);
		}
		return homeworkReports;
	}

	@Override
	public List<Assignment> getAssignmentTitles(long csId, String usedFor,
			String assignedDate) {
		List<Assignment> assignmentTitles = new ArrayList<Assignment>();
		try{
			assignmentTitles= homeworkManagerDao.getAssignmentTitles(csId, usedFor,	assignedDate);
		}catch(Exception e){
			logger.error("Error in getAssignmentTitles() of  HomeworkManagerServiceImpl");
			throw new DataException("Error in getAssignmentTitles() of HomeworkManagerServiceImpl",e);
		}
		return assignmentTitles;
	}

	@Override
	public List<Assignment> getTestTitles(long csId, String usedFor) {
		List<Assignment> list = null;
		try {
			list = homeworkManagerDao.getTestTitles(csId, usedFor);
		} catch (Exception e) {
			logger.error("error while getting test titles");
		}
		return list;
	}

	@Override
	public Object getHomeworkTitles(long csId, String usedFor,String assignedDate) {
		List<Assignment> assignmentTitles = new ArrayList<Assignment>();
		try{
		assignmentTitles= homeworkManagerDao.getHomeworkTitles(csId, usedFor, assignedDate);
		}catch(DataException e){
			logger.error("Error in getAssignmentTitles() of  HomeworkManagerServiceImpl");
			throw new DataException(
					"Error in getAssignmentTitles() of HomeworkManagerServiceImpl",
					e);
		}
		return assignmentTitles;
	}
	@Override
	public List<Assignment> getRTIResultsTitles(long csId, String usedFor,
			String assignedDate) {
		List<Assignment> assignmentTitles = new ArrayList<Assignment>();
		try{
		assignmentTitles= homeworkManagerDao.getRTIResultsTitles(csId, usedFor,
				assignedDate);
		}catch(DataException e){
			logger.error("Error in getAssignmentTitles() of  HomeworkManagerServiceImpl");
			throw new DataException(
					"Error in getAssignmentTitles() of HomeworkManagerServiceImpl",
					e);
		}
		return assignmentTitles;
	}
	@Override
	public List<Assignment> getFluencyTitles(long csId,String assignedDate) {
		List<Assignment> assignmentTitles = new ArrayList<Assignment>();
		try{
		assignmentTitles= homeworkManagerDao.getFluencyTitles(csId,assignedDate);
		}catch(DataException e){
			logger.error("Error in getAssignmentTitles() of  HomeworkManagerServiceImpl");
			throw new DataException(
					"Error in getAssignmentTitles() of HomeworkManagerServiceImpl",
					e);
		}
		return assignmentTitles;
	}
	@Override
	public List<Assignment> getAccuracyTitles(long csId,String assignedDate) {
		List<Assignment> assignmentTitles = new ArrayList<Assignment>();
		try{
		assignmentTitles= homeworkManagerDao.getAccuracyTitles(csId, assignedDate);
		}catch(DataException e){
			logger.error("Error in getAssignmentTitles() of  HomeworkManagerServiceImpl");
			throw new DataException(
					"Error in getAssignmentTitles() of HomeworkManagerServiceImpl",
					e);
		}
		return assignmentTitles;
	}
}
