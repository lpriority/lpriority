package com.lp.common.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.Attendance;
import com.lp.model.ClassActualSchedule;
import com.lp.model.RegisterForClass;
import com.lp.model.Report;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;


public interface DashboardDAO {
	List<Student> getStudentsByGrade(long gradeId) throws DataException;
	public List<TeacherSubjects> getTeacherRequestsBySchool(School school) throws DataException;
	public List<RegisterForClass> getStudentRequestsforClass(Teacher teacher) throws DataException;
	public List<RegisterForClass> getTeacherResponseforStudentRequest(Student student) throws DataException;
	public boolean getStudentRTITests(Student student) throws DataException;
	public List<Attendance> getStudentAttendance(UserRegistration parent) throws DataException;	
	public List<RegisterForClass> getTeacherResponseforChildRequests(UserRegistration parent) throws DataException;	
	public List<Report> getParentComments(Teacher teacher) throws DataException; 
	
	public List<ClassActualSchedule> getTeacherClasses(Teacher teacher, long dayId) throws DataException;
	public List<ClassActualSchedule> getStudentClasses(Student student, long dayId) throws DataException;
	public List<Assignment> getAssignedTests(Teacher teacher);
	public List<StudentAssignmentStatus> getStudentAssignedTests(Student student);
	public List<StudentAssignmentStatus> getTestsToBeGraded(Teacher teacher);
}
