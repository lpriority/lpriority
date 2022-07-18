package com.lp.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.lp.common.dao.DashboardDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.Assignment;
import com.lp.model.Attendance;
import com.lp.model.ClassActualSchedule;
import com.lp.model.ClassStatus;
import com.lp.model.RegisterForClass;
import com.lp.model.Report;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.TeacherDAO;

public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private DashboardDAO dashboardDAO;	
	@Autowired
	private TeacherDAO teacherDAO;
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	static final Logger logger = Logger.getLogger(DashboardServiceImpl.class);
	@Override
	public List<Student> getStudentsByGrade(long gradeId) throws DataException {
		List<Student> students = Collections.emptyList();
		try{
			students =  dashboardDAO.getStudentsByGrade(gradeId);			
		}catch(DataException e){
			logger.error("Error in getStudents() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudents() of DashboardServiceImpl", e);
		}
		return students;
	}	
	
	@Override
	public List<Teacher> getTeachersBySchoolId(School school){
		List<Teacher> uniqueTeachers = new ArrayList<Teacher>();
		List<ClassStatus> teacherList = new ArrayList<ClassStatus>();
		Map <Long, String> teacherMap =   new LinkedHashMap <Long,String>();
		try{
			teacherList = teacherDAO.getTeachersBySchoolId(school);
			for(ClassStatus classStatus : teacherList){
				if(!teacherMap.containsKey(classStatus.getTeacher().getTeacherId())){
					teacherMap.put(classStatus.getTeacher().getTeacherId(), classStatus.getTeacher().getUserRegistration().getEmailId());
					uniqueTeachers.add(classStatus.getTeacher());
				}
			}
		}catch(DataAccessException e) {
			logger.error("Error in getTeachersBySchoolId() of DashboardServiceImpl" + e);
			e.printStackTrace();
		}
		return uniqueTeachers;
	}

	@Override
	public List<TeacherSubjects> getTeacherRequestsBySchool(School school) throws DataException {
		List<TeacherSubjects> teacherRequests = new ArrayList<TeacherSubjects>();
		try{	
			teacherRequests =  dashboardDAO.getTeacherRequestsBySchool(school);					
		}catch(DataException e){
			logger.error("Error in getTeacherRequestsBySchool() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getTeacherRequestsBySchool() of DashboardServiceImpl", e);
		}
		return teacherRequests;
	}

	@Override
	public List<RegisterForClass> getStudentRequestsforClass(Teacher teacher) throws DataException {
		List<RegisterForClass> studentRequests = new ArrayList<RegisterForClass>();
		try{	
			studentRequests =  dashboardDAO.getStudentRequestsforClass(teacher);					
		}catch(DataException e){
			logger.error("Error in getStudentRequestsByTeacher() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentRequestsByTeacher() of DashboardServiceImpl", e);
		}
		return studentRequests;
	}

	@Override
	public List<RegisterForClass> getTeacherResponseforStudentRequests(Student student) throws DataException {
		List<RegisterForClass> teacherResponse = new ArrayList<RegisterForClass>();
		try{	
			teacherResponse =  dashboardDAO.getTeacherResponseforStudentRequest(student);					
		}catch(DataException e){
			logger.error("Error in getTeacherResponseforStudentRequests() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getTeacherResponseforStudentRequests() of DashboardServiceImpl", e);
		}
		return teacherResponse;
	}

	@Override
	public boolean getStudentRTITests(Student student) throws DataException {
		boolean flag = false;
		try{
			flag = dashboardDAO.getStudentRTITests(student);
		}catch(DataException e){
			logger.error("Error in getStudentRTITests() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentRTITests() of DashboardServiceImpl", e);
		}
		return flag;
	}

	@Override
	public List<Attendance> getStudentAttendance(UserRegistration parent)
			throws DataException {
		List<Attendance> attendance = new ArrayList<Attendance>();
		try{
			attendance = dashboardDAO.getStudentAttendance(parent);
		}catch(DataException e){
			logger.error("Error in getStudentAttendance() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentAttendance() of DashboardServiceImpl", e);
		}
		return attendance;
	}

	@Override
	public List<RegisterForClass> getTeacherResponseforChildRequests(
			UserRegistration parent) throws DataException {
		List<RegisterForClass> responses = new ArrayList<RegisterForClass>();
		try{
			responses = dashboardDAO.getTeacherResponseforChildRequests(parent);
		}catch(DataException e){
			logger.error("Error in getTeacherResponseforChildRequests() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getTeacherResponseforChildRequests() of DashboardServiceImpl", e);
		}
		return responses;
	}

	@Override
	public List<Report> getParentComments(Teacher teacher) throws DataException {
		List<Report> parentComments = new ArrayList<Report>();
		try{
			parentComments = dashboardDAO.getParentComments(teacher);
		}catch(DataException e){
			logger.error("Error in getParentComments() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getParentComments() of DashboardServiceImpl", e);
		}
		return parentComments;
	}

	@Override
	public List<ClassActualSchedule> getTeacherClasses(Teacher teacher)
			throws DataException {
		long dayId = getdayOfWeek();
		List<ClassActualSchedule> teacherClasses = new ArrayList<ClassActualSchedule>();
		try{
			teacherClasses = dashboardDAO.getTeacherClasses(teacher, dayId);
		}catch(DataException e){
			logger.error("Error in getTeacherClasses() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getTeacherClasses() of DashboardServiceImpl", e);
		}
		return teacherClasses;
	}

	@Override
	public List<ClassActualSchedule> getStudentClasses(Student student) throws DataException {
		List<ClassActualSchedule> studentClasses = new ArrayList<ClassActualSchedule>();
		try{
			long dayId = getdayOfWeek();
			studentClasses = dashboardDAO.getStudentClasses(student, dayId);
		}catch(DataException e){
			logger.error("Error in getStudentClasses() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentClasses() of DashboardServiceImpl", e);
		}
		return studentClasses;
	}

	@Override
	public List<Assignment> getAssignedTests(Teacher teacher) {
		List<Assignment> assignments = new ArrayList<Assignment>();
		try{
			assignments = dashboardDAO.getAssignedTests(teacher);
		}catch(DataException e){
			logger.error("Error in getAssignedTests() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getAssignedTests() of DashboardServiceImpl", e);
		}
		return assignments;
	}

	@Override
	public List<Assignment> getStudentAssignedTests(Student student) {
		List<Assignment> assignments = new ArrayList<Assignment>();
		List<StudentAssignmentStatus> studentTests = new ArrayList<StudentAssignmentStatus>();
		try{
			studentTests = dashboardDAO.getStudentAssignedTests(student);
			for(StudentAssignmentStatus studentAssignmentStatus : studentTests){
				assignments.add(studentAssignmentStatus.getAssignment());
			}
		}catch(DataException e){
			logger.error("Error in getStudentAssignedTests() of  DashboardServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentAssignedTests() of DashboardServiceImpl", e);
		}
		return assignments;
	}	
	
	public long getdayOfWeek() {
		java.util.Date date1 = new java.util.Date();
	    int day_of_week = date1.getDay();
	    if (day_of_week == 0) {
	        day_of_week = 7;
	    }
	    return day_of_week;
    }
	@Override
	public List<StudentAssignmentStatus> getTestsToBeGraded(Teacher teacher){
		List<StudentAssignmentStatus> studentAssignmentStatus = Collections.emptyList();
		try{
			studentAssignmentStatus = dashboardDAO.getTestsToBeGraded(teacher);
		}
		catch(Exception e){
			logger.error("Error in getTestsToBeGraded() of  DashboardServiceImpl"+ e);
			throw new DataException("Error in getTestsToBeGraded() of DashboardServiceImpl", e);
		}
		return studentAssignmentStatus;
	}

	@Override
	public List<Teacher> getTeachersByYear(School school, AcademicYear academicYear) throws DataException {
		// TODO Auto-generated method stub
		return teacherDAO.getTeachersByYear(school, academicYear);
	}
}