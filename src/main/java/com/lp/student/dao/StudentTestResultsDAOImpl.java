package com.lp.student.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicPerformance;
import com.lp.model.AcademicYear;
import com.lp.model.Assignment;
import com.lp.model.FluencyMarks;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("studentTestResultsDAO")
public class StudentTestResultsDAOImpl extends CustomHibernateDaoSupport implements StudentTestResultsDAO {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private HttpSession httpSession;

	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentsTestResults(Student student, String usedFor,String testStatus, String gradedStatus,long classId, long perGroupId) {
		List<StudentAssignmentStatus> studentTestList = null;
		AcademicYear academicYear = null;
		String assignStatus = WebKeys.ACTIVE;
		String query = ""; 
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try{
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				assignStatus =  WebKeys.EXPIRED;
				query =  " and assignment.dateAssigned between '"+academicYear.getStartDate()+"' and '"+academicYear.getEndDate()+"'";
			}
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where (student.studentId = "
							+ student.getStudentId()
							+ " or performanceGroup.performanceGroupId = "
							+ perGroupId
							+ ") and status = '"
							+ testStatus
							+ "' and assignment.classStatus.section.gradeClasses.studentClass.classId= "
							+ classId
							+ " and gradedStatus = '"
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ assignStatus
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "' and assignment.assignmentType.assignmentTypeId not in (8,20)"
							+query);
		} catch (HibernateException e) {
			logger.error("Error in getStudentsTestResults() of StudentTestResultsDAOImpl", e);
		}
		return studentTestList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AcademicPerformance> getAcademicPerformance() {
		List<AcademicPerformance> academicPerformanceList = null;
		try {
			academicPerformanceList =(List<AcademicPerformance>) getHibernateTemplate().find("From AcademicPerformance"); 
		} catch (HibernateException e) {
			logger.error("Error in getAcademicPerformance() of StudentTestResultsDAOImpl", e);
		}
		return academicPerformanceList;
	}

	@Override
	public Map<Long,String> getNumericalPercentageScore(){
		long academic_id = 0;
		long score_from = 0;
		long score_to = 0;
		Map<Long,String> numPerGradeMap = new HashMap<Long,String>();
		numPerGradeMap.clear();
		String query_for_getNumPerGrade = "select academic_id,min(score_from),max(score_to) from academic_grades group by academic_id";
		SqlRowSet rs_for_getNumPerGrade = jdbcTemplate.queryForRowSet(query_for_getNumPerGrade);
		 while(rs_for_getNumPerGrade.next()){
			 academic_id = rs_for_getNumPerGrade.getLong(1);
		 	 score_from = rs_for_getNumPerGrade.getLong(2);
			 score_to = rs_for_getNumPerGrade.getLong(3);
			 numPerGradeMap.put(academic_id, score_from+"-"+score_to);
		 }
		return numPerGradeMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentsBenchmarkResults(Student student, String usedFor,String testStatus, String gradedStatus,long classId, long assignmentTypeId) {
		List<StudentAssignmentStatus> studentTestList = null;
		AcademicYear academicYear = null;
		String assignStatus = WebKeys.ACTIVE;
		String query = "";
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try{
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				assignStatus =  WebKeys.EXPIRED;
				query =  " and assignment.dateAssigned between '"+academicYear.getStartDate()+"' and '"+academicYear.getEndDate()+"'";
			}
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "
							+ student.getStudentId()
							+ " and status = '"
							+ testStatus
							+ "' and assignment.classStatus.section.gradeClasses.studentClass.classId= "
							+ classId
							+ " and gradedStatus = '"
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ assignStatus
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "'"
							+ " and assignment.assignmentType.assignmentTypeId="+assignmentTypeId
							+ query) ;
		} catch (HibernateException e) {
			logger.error("Error in getStudentsBenchmarkResults() of StudentTestResultsDAOImpl", e);
		}
		
		return studentTestList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getHomeworkAssignedDates(long csId,String usedFor,Student student){
		List<StudentAssignmentStatus> assignmentDates = null;	
		try{
	     assignmentDates =(List<StudentAssignmentStatus>) getHibernateTemplate().find("from StudentAssignmentStatus where assignment.classStatus.csId=" + csId
					+ " and assignment.usedFor='"+usedFor+"' and assignment.assignStatus='"+WebKeys.ACTIVE+"'"
					+" and status='submitted' and student.studentId="+student.getStudentId()+" group by assignment.dateAssigned");
			} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return assignmentDates;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getHomeworkReports(long csId,String usedFor,String assignedDate,Student student){
		List<StudentAssignmentStatus> assignmentList = null;
		try {
			
			assignmentList = (List<StudentAssignmentStatus>) getHibernateTemplate().find(
					"from StudentAssignmentStatus where assignment.classStatus.csId=" + csId
							+ " and assignment.usedFor='"+usedFor+"' and assignment.assignStatus='"+WebKeys.ACTIVE+"'"
							+" and assignment.dateAssigned='"+assignedDate+"' and status='submitted' and gradedStatus='graded'"
							+ " and student.studentId="+student.getStudentId()+"");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return assignmentList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FluencyMarks> getStudentAssignments(Student student){
		List<FluencyMarks> fluencyMarksList = null;
		try {
			
					
			fluencyMarksList = (List<FluencyMarks>) getHibernateTemplate()
					.find("From FluencyMarks where peerReviewBy.studentId="+student.getStudentId()+ "");
		} catch (HibernateException e) {
			logger.error("Error in getStudentAssignments() of StudentTestResultsDAOImpl", e);
		}
		return fluencyMarksList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RflpTest> getRFLPHomeworks(long csId,String usedFor,Student student,long gradeTypesId){
		List<RflpTest> assignmentDates = null;	
		try{
	     assignmentDates =(List<RflpTest>) getHibernateTemplate().find("from RflpTest where studentAssignmentStatus.assignment.classStatus.csId=" + csId
					+ " and studentAssignmentStatus.assignment.usedFor='"+usedFor+"' and studentAssignmentStatus.assignment.assignStatus='"+WebKeys.ACTIVE+"'"
					+" and studentAssignmentStatus.status='submitted' and studentAssignmentStatus.gradedStatus='not graded' and gradingTypes.gradingTypesId="+gradeTypesId+" and studentAssignmentStatus.student.studentId="+student.getStudentId()+"");
			} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return assignmentDates;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RflpTest> getPeerRFLPHomeworks(long csId,String usedFor,Student peerStudent,long gradeTypesId){
		List<RflpTest> assignmentDates = null;	
		try{
	     assignmentDates =(List<RflpTest>) getHibernateTemplate().find("from RflpTest where studentAssignmentStatus.assignment.classStatus.csId=" + csId
					+ " and studentAssignmentStatus.assignment.usedFor='"+usedFor+"' and studentAssignmentStatus.assignment.assignStatus='"+WebKeys.ACTIVE+"'"
					+" and studentAssignmentStatus.status='submitted' and studentAssignmentStatus.gradedStatus='not graded' and gradingTypes.gradingTypesId="+gradeTypesId+" and peerReviewBy.studentId="+peerStudent.getStudentId()+"");
			} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return assignmentDates;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentBenchmarkTests(Student student, String usedFor,String testStatus, String gradedStatus,long classId,long assignmentTypeId)  throws DataException{
		List<StudentAssignmentStatus> studentTestList = null;
		try {
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "
							+ student.getStudentId()
							+ " and status = '"
							+ testStatus
							+ "' and assignment.classStatus.section.gradeClasses.studentClass.classId= "
							+ classId
							+ " and selfGradedStatus = '" 
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ WebKeys.ASSIGN_STATUS_ACTIVE
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "'"
									+ " and assignment.assignmentType.assignmentTypeId="+assignmentTypeId);
		} catch (HibernateException e) {
			logger.error("Error in getStudentsBenchmarkResults() of StudentTestResultsDAOImpl", e);
		}
		
		return studentTestList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentBenchPeerReviewTests(Student student, String usedFor,String testStatus, String gradedStatus,long classId,long assignmentTypeId)  throws DataException{
		List<StudentAssignmentStatus> studentTestList = null;
		try {
						
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where status = '"
							+ testStatus
							+ "' and assignment.classStatus.section.gradeClasses.studentClass.classId= "
							+ classId
							+ " and peerGradedStatus = '" 
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ WebKeys.ASSIGN_STATUS_ACTIVE
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "'"
									+ " and peerReviewBy="+student.getStudentId()+" and assignment.assignmentType.assignmentTypeId="+assignmentTypeId);
		} catch (HibernateException e) {
			logger.error("Error in getStudentsBenchmarkResults() of StudentTestResultsDAOImpl", e);
		}
		
		return studentTestList;
	}
	@SuppressWarnings("unchecked")
	@Override
	 public boolean getCompleteSelfTestByStudentId(Assignment assignment,Student student,String testStatus){
		 List<StudentAssignmentStatus> studentTestList = null;
			try {
							
				studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
						.find("From StudentAssignmentStatus where status = '"
								+ testStatus
								+ "' and assignment.assignmentId= "
								+ assignment.getAssignmentId()
								+ " and student.studentId= "+student.getStudentId()+"");
			} catch (HibernateException e) {
				logger.error("Error in getStudentsBenchmarkResults() of StudentTestResultsDAOImpl", e);
			}
			if(studentTestList.size()>0)
			return true;
			else
			return false;
	 }
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentAllRTITestResults(Student student, String usedFor,String testStatus, String gradedStatus,long csId, long perGroupId) {
		List<StudentAssignmentStatus> studentTestList = null;
		AcademicYear academicYear = null;
		String assignStatus = WebKeys.ASSIGN_STATUS_ACTIVE;
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try {
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				assignStatus =  WebKeys.EXPIRED;
			}
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where (student.studentId = "
							+ student.getStudentId()
							+ " or performanceGroup.performanceGroupId = "
							+ perGroupId
							+ ") and status = '"
							+ testStatus
							+ "' and assignment.classStatus.csId= "
							+ csId
							+ " and gradedStatus = '"
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ assignStatus
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "' and assignment.assignmentType.assignmentTypeId not in(21,30)");
		} catch (HibernateException e) {
			logger.error("Error in getStudentsTestResults() of StudentTestResultsDAOImpl", e);
		}
		return studentTestList;
	}
}
