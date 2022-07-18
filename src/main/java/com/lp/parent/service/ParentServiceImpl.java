package com.lp.parent.service;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.ParentLastseen;
import com.lp.model.PerformanceGroupStudents;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;
import com.lp.parent.dao.ParentDAO;
import com.lp.student.dao.StudentDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "parentService")
public class ParentServiceImpl implements ParentService {
	@Autowired
	private ParentDAO parentDAO;
	@Autowired
	private StudentDAO studentDAO;
	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;
	
	static final Logger logger = Logger.getLogger(ParentServiceImpl.class);
	
	@Override
	public List<UserRegistration> getchildren(long parentRegId){
		return parentDAO.getchildren(parentRegId);
	}
	
	@Override
	@RemoteMethod
	public long getStudentGradeIdByRegId(long childRegId){
		Student student = studentDAO.getStudent(childRegId);		
		return student.getGrade().getGradeId();		
	}

	@Override
	public List<Student> getStudentByParent(long parentRegId) {
		List<Student> students = new ArrayList<Student>();
		try{
			students = parentDAO.getStudentByParent(parentRegId);
		}catch(DataException e){
			logger.error("Error in getStudentByParent() of  ParentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentByParent() of ParentServiceImpl", e);
		}
		return students;
	}
	@Override
	public List<UserRegistration> getUnregisteredChildren(long parentId) throws DataException {
		List<UserRegistration> userRegistrations = Collections.emptyList();
		try {
			userRegistrations = parentDAO.getUnregisteredChildren(parentId);
		} catch (HibernateException e) {
			logger.error("Error in getUnregisteredChildren() of ParentServiceImpl", e);
			throw new DataException("Error in getUnregisteredChildren() of ParentServiceImpl",e);
		}
		return userRegistrations;
	}
	@RemoteMethod
	public List<StudentAssignmentStatus> getStudentsTestResults(long studentId,long classId, String usedFor)  throws DataException{
		long perGroupId = 0;
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		try{
			PerformanceGroupStudents perGroupStudent = performanceTaskDAO.getPerformanceGroupByStudentId(studentId);
			if(perGroupStudent.getPerformancetaskGroups() != null)
				perGroupId = perGroupStudent.getPerformancetaskGroups().getPerformanceGroupId();

			studentAssignments = parentDAO.getStudentsTestResults(studentId, usedFor, WebKeys.TEST_STATUS_SUBMITTED, WebKeys.GRADED_STATUS_GRADED, classId, perGroupId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getStudentsTestResults() of ParentServiceImpl", e);
		}
		return studentAssignments;
	}
	@RemoteMethod
	public List<StudentAssignmentStatus> getStudentsBenchmarkTestResults(long classId, String usedFor,Student student,long assignmentTypeId)  throws DataException{
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		try{
			
			studentAssignments = parentDAO.getChildsBenchmarkResults(student, usedFor, WebKeys.TEST_STATUS_SUBMITTED, WebKeys.GRADED_STATUS_GRADED, classId,assignmentTypeId);
		}catch(DataException e){
			logger.error("Error in getStudentsTestResults() of ParentServiceImpl", e);
		}
		return studentAssignments;
	}
	public List<StudentAssignmentStatus> getChildHomeworkReportDates(Student student,String usedFor){
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		try{
			
			studentAssignments = parentDAO.getChildHomeworkReportDates(student, usedFor);
		}catch(DataException e){
			logger.error("Error in getStudentsTestResults() of ParentServiceImpl", e);
		}
		return studentAssignments;
	}
	@Override
	public List<StudentAssignmentStatus> getChildHomeworkReports(Student student,String usedFor,String assignedDate){
		List<StudentAssignmentStatus> homeworkReports=new ArrayList<StudentAssignmentStatus>();
		try{
			homeworkReports= parentDAO.getChildHomeworkReports(student, usedFor, assignedDate);
		}catch(DataException e){
			logger.error("Error in getHomeworkReports() of ParentServiceImpl", e);
		}
		return homeworkReports;
	}

	@Override
	public ParentLastseen getParentLastseen(UserRegistration userReg)
			throws DataException {
		ParentLastseen parentLastseen=new ParentLastseen();
		try{
			parentLastseen= parentDAO.getParentLastseen(userReg);
		}catch(DataException e){
			logger.error("Error in getParentLastseen() of ParentServiceImpl", e);
		}
		return parentLastseen;
	}

	@Override
	public void saveParentLastSeen(ParentLastseen parentLastseen)
			throws DataException {
		try{
			parentDAO.saveParentLastSeen(parentLastseen);
		}catch(DataException e){
			logger.error("Error in saveParentLastSeen() of ParentServiceImpl", e);
		}		
	}
	@Override
	public List<Long> getChildRtiTestCorrectResponses(List<StudentAssignmentStatus> rtiResults){
		List<Long> correctResponses=new ArrayList<Long>();
		try{
			for (StudentAssignmentStatus sa : rtiResults) {
				long correct= parentDAO.getChildCorrectResponses(sa.getStudentAssignmentId());
				correctResponses.add(correct);
		    }
			
		}catch(DataException e){
			logger.error("Error in getChildRtiTestCorrectResponses() of ParentServiceImpl", e);
		}
		return correctResponses;
	}

	@Override
	public List<Long> getChildRtiTestWrongResponses(List<StudentAssignmentStatus> rtiResults){
		List<Long> wrongResponses=new ArrayList<Long>();
		try{
			for (StudentAssignmentStatus sa : rtiResults) {
				long wrong= parentDAO.getChildWrongResponses(sa.getStudentAssignmentId());
				wrongResponses.add(wrong);
		    }
			
		}catch(DataException e){
			logger.error("Error in getChildRtiTestWrongResponses() of ParentServiceImpl", e);
		}
		return wrongResponses;
	}
	@Override
	public List<ClassStatus> getAvailableTeachers(long gradeClassId) throws DataException{
		List<ClassStatus> classStatusList = new ArrayList<ClassStatus>();
		try{
			classStatusList = parentDAO.getAvailableTeachers(gradeClassId);
		}catch(DataException e){
			logger.error("Error in getAvailableTeachers() of ParentServiceImpl", e);
		}
		return classStatusList;
	}
	@Override
	public List<ClassStatus> getTeacherSections(long gradeClassId, long teacherId) throws DataException{
		List<ClassStatus> classStatusList = new ArrayList<ClassStatus>();
		try{
			classStatusList = parentDAO.getTeacherSections(gradeClassId,teacherId);
		}catch(DataException e){
			logger.error("Error in getAvailableTeachers() of ParentServiceImpl", e);
		}
		return classStatusList;
	}
	@RemoteMethod
	public List<StudentAssignmentStatus> getChildTestResults(long studentId,long classId, String usedFor)  throws DataException{
		long perGroupId = 0;
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		try{
			PerformanceGroupStudents perGroupStudent = performanceTaskDAO.getPerformanceGroupByStudentId(studentId);
			if(perGroupStudent.getPerformancetaskGroups() != null)
				perGroupId = perGroupStudent.getPerformancetaskGroups().getPerformanceGroupId();

			studentAssignments = parentDAO.getChildTestResults(studentId, usedFor, WebKeys.TEST_STATUS_SUBMITTED, WebKeys.GRADED_STATUS_GRADED, classId, perGroupId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getStudentsTestResults() of ParentServiceImpl", e);
		}
		return studentAssignments;
	}
}

