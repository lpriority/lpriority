package com.lp.student.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicPerformance;
import com.lp.model.ClassStatus;
import com.lp.model.PerformanceGroupStudents;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.student.dao.StudentTestResultsDAO;
import com.lp.teacher.service.GradeBookService;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "studentTestResultsService")
public class StudentTestResultsServiceImpl implements StudentTestResultsService {

	@Autowired
	public StudentTestResultsDAO studentTestResultsDAO;
	
	@Autowired
	public GradeBookService gradeBookService;
	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;	
	@Autowired
	private HttpSession session;
	@Autowired
	public AdminSchedulerDAO adminSchedulerDAO;
	
	static final Logger logger = Logger.getLogger(StudentTestResultsServiceImpl.class);
	
	@RemoteMethod
	public List<StudentAssignmentStatus> getStudentsTestResults(long classId, String usedFor, long studentId)  throws DataException{
		Student student = null;
		if(studentId == 0){
		    student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		}else{
			 student = gradeBookService.getStudentById(studentId);  
		}
		long perGroupId = 0;
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		try{
			PerformanceGroupStudents perGroupStudent = performanceTaskDAO.getPerformanceGroupByStudentId(student.getStudentId());
			if(perGroupStudent.getPerformancetaskGroups() != null)
				perGroupId = perGroupStudent.getPerformancetaskGroups().getPerformanceGroupId();

			studentAssignments = studentTestResultsDAO.getStudentsTestResults(student, usedFor, WebKeys.TEST_STATUS_SUBMITTED, WebKeys.GRADED_STATUS_GRADED, classId, perGroupId);
		}catch(Exception e){
			logger.error("Error in getStudentsTestResults() of StudentTestResultsServiceImpl", e);
		}
		return studentAssignments;
	}
	
	@Override
	public List<AcademicPerformance> getAcademicPerformance() throws DataException{
		List<AcademicPerformance> academicPerformance = Collections.emptyList();
		try{
			academicPerformance = studentTestResultsDAO.getAcademicPerformance();
		}catch(DataException e){
			logger.error("Error in getStudentsTestResults() of StudentTestResultsServiceImpl", e);
		}
		return academicPerformance;
	}

	@Override
	public Map<Long, String> getNumericalPercentageScore() {
		return studentTestResultsDAO.getNumericalPercentageScore();
	}
	@RemoteMethod
	public List<StudentAssignmentStatus> getStudentsBenchmarkTestResults(long classId, String usedFor, long assignmentTypeId)  throws DataException{
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		try{
			
			studentAssignments = studentTestResultsDAO.getStudentsBenchmarkResults(student, usedFor, WebKeys.TEST_STATUS_SUBMITTED, WebKeys.GRADED_STATUS_GRADED, classId, assignmentTypeId);
		}catch(DataException e){
			logger.error("Error in getStudentsTestResults() of StudentTestResultsServiceImpl", e);
		}
		return studentAssignments;
	}
	@Override
	public List<StudentAssignmentStatus> getHomeworkAssignedDates(long csId,String usedFor,Student student){
		List<StudentAssignmentStatus> dateList=new ArrayList<StudentAssignmentStatus>();
		try{
			  dateList=studentTestResultsDAO.getHomeworkAssignedDates(csId,usedFor,student);
		}catch(DataException e){
			logger.error("Error in getHomeworkAssignedDates() of StudentTestResultsServiceImpl", e);
		}
		return dateList;
	}
	@Override
	public List<StudentAssignmentStatus> getHomeworkReports(long csId,String usedFor,String assignedDate,Student student){
		List<StudentAssignmentStatus> homeworkReports=new ArrayList<StudentAssignmentStatus>();
		try{
			homeworkReports= studentTestResultsDAO.getHomeworkReports(csId, usedFor, assignedDate,student);
		}catch(DataException e){
			logger.error("Error in getHomeworkReports() of StudentTestResultsServiceImpl", e);
		}
		return homeworkReports;
	}

	public List<RflpTest> getRFLPHomeworks(long csId,String usedFor,Student student,long gradeTypesId){
		List<RflpTest> rflpTestList=new ArrayList<RflpTest>();
		try{
				if(gradeTypesId == 2){
					rflpTestList=studentTestResultsDAO.getRFLPHomeworks(csId,usedFor,student,gradeTypesId);
				}
				else{
					rflpTestList=studentTestResultsDAO.getPeerRFLPHomeworks(csId,usedFor,student,gradeTypesId);					
				}
		}catch(DataException e){
			logger.error("Error in getRFLPHomeworks() of StudentTestResultsServiceImpl", e);
		}
		return rflpTestList;
	}
	public List<StudentAssignmentStatus> getStudentBenchmarkTests(long classId, String usedFor,long gradingTypesId,long assignmentTypeId)  throws DataException{
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		List<StudentAssignmentStatus> peerReviewTests = new ArrayList<StudentAssignmentStatus>();
		try{
			if(gradingTypesId==2){
				studentAssignments = studentTestResultsDAO.getStudentBenchmarkTests(student, usedFor, WebKeys.TEST_STATUS_SUBMITTED, WebKeys.GRADED_STATUS_NOTGRADED, classId,assignmentTypeId);
			}
			else{
				peerReviewTests = studentTestResultsDAO.getStudentBenchPeerReviewTests(student, usedFor, WebKeys.TEST_STATUS_SUBMITTED, WebKeys.GRADED_STATUS_NOTGRADED, classId,assignmentTypeId);
				for(StudentAssignmentStatus st:peerReviewTests){
					boolean checkCompleSelfTests = studentTestResultsDAO.getCompleteSelfTestByStudentId(st.getAssignment(),student,WebKeys.TEST_STATUS_SUBMITTED);
					if(checkCompleSelfTests)
						studentAssignments.add(st);
				}
			}
		}catch(DataException e){
			logger.error("Error in getStudentsTestResults() of StudentTestResultsServiceImpl", e);
		}
		return studentAssignments;
	}
	@Override
	public ClassStatus getclassStatus(long csId){
		ClassStatus cSt = new ClassStatus();
		try{
			cSt =adminSchedulerDAO.getclassStatus(csId);
		}catch(DataException e){
			logger.error("Error in getclassStatus() of StudentTestResultsServiceImpl", e);
		}
		return cSt;
	}
	@Override
	public List<StudentAssignmentStatus> getStudentAllRTITestResults(long csId, String usedFor, long studentId)  throws DataException{
		Student student = null;
		 student = gradeBookService.getStudentById(studentId);  
	
		long perGroupId = 0;
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		try{
			PerformanceGroupStudents perGroupStudent = performanceTaskDAO.getPerformanceGroupByStudentId(student.getStudentId());
			if(perGroupStudent.getPerformancetaskGroups() != null)
				perGroupId = perGroupStudent.getPerformancetaskGroups().getPerformanceGroupId();

			studentAssignments = studentTestResultsDAO.getStudentAllRTITestResults(student, usedFor, WebKeys.TEST_STATUS_SUBMITTED, WebKeys.GRADED_STATUS_GRADED, csId, perGroupId);
		}catch(Exception e){
			logger.error("Error in getStudentsTestResults() of StudentTestResultsServiceImpl", e);
		}
		return studentAssignments;
	}
	
}
