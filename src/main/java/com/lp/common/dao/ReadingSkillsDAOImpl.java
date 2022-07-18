package com.lp.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.Assignment;
import com.lp.model.BenchmarkResults;
import com.lp.model.StudentAssignmentStatus;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;


public class ReadingSkillsDAOImpl extends CustomHibernateDaoSupport implements ReadingSkillsDAO {
	
	@Autowired
	HttpSession session;
	static final Logger logger = Logger.getLogger(ReadingSkillsDAOImpl.class);


	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getTaskForceTitles(long csId, String usedFor) {
		List<Assignment> assignmentList = new ArrayList<Assignment>();
		AcademicYear academicYear = null;
		String assignStatus = WebKeys.ACTIVE;
		if(session.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) session.getAttribute("academicYear");
		try{
			if(session.getAttribute("academicYrFlag")!=null && session.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				assignStatus =  WebKeys.EXPIRED;
			}
			assignmentList = ((List<Assignment>) getHibernateTemplate().find("from Assignment where "
					+ "classStatus.csId="+csId+" and usedFor='"+usedFor+"' and "
					+ "assignStatus='"+assignStatus+"' and "
			 		+ "assignmentType.assignmentType in('"+WebKeys.ASSIGNMENT_TYPE_FLUENCY_READING
			 		+ "','"+WebKeys.ASSIGNMENT_TYPE_COMPREHENSION+"') order by assignmentId"));
		} catch (DataAccessException e) {
			logger.error("Error in getTaskForceTitles() of ReadingSkillsDAOImpl");
			throw new DataException(
					"Error in getTaskForceTitles() of ReadingSkillsDAOImpl", e);
		}
		return assignmentList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getComprehensionResults(long comId) {
		List<StudentAssignmentStatus> StudentAssignmentList = new ArrayList<StudentAssignmentStatus>();
		try {
			StudentAssignmentList = ((List<StudentAssignmentStatus>) getHibernateTemplate().find("from StudentAssignmentStatus where "
					+ "assignment.assignmentId="+comId));
		} catch (DataAccessException e) {
			logger.error("Error in getFluencyResults() of ReadingSkillsDAOImpl");
			throw new DataException(
					"Error in getFluencyResults() of ReadingSkillsDAOImpl", e);
		}
		return StudentAssignmentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BenchmarkResults> getFluencyResults(long fluencyId) {
		List<BenchmarkResults> benchmarkResults = new ArrayList<BenchmarkResults>();
		try {
			benchmarkResults = ((List<BenchmarkResults>) getHibernateTemplate().find("from BenchmarkResults where "
					+ "studentAssignmentStatus.assignment.assignmentId="+fluencyId));
		} catch (DataAccessException e) {
			logger.error("Error in getComprehensionResults() of ReadingSkillsDAOImpl");
			throw new DataException(
					"Error in getComprehensionResults() of ReadingSkillsDAOImpl", e);
		}
		return benchmarkResults;
	}
}