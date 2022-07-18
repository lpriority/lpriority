package com.lp.student.service;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.GradesDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.ClassActualSchedule;
import com.lp.model.Grade;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserInterests;
import com.lp.model.UserRegistration;
import com.lp.student.dao.StudentDAO;
import com.lp.teacher.dao.CommonDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "studentService")
public class StudentServiceImpl implements StudentService {
	
	static final Logger logger = Logger.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private StudentDAO StudentDAO;
	@Autowired
	private GradesDAO gradesdao;
	@Autowired
	private CommonDAO commonDAO;
	@Autowired
	private HttpSession session;

	@Override
	public boolean SaveStudent(Student stud) {
	GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		stud.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		stud.setChangeDate(changeDate);
		return StudentDAO.SaveStudent(stud);
	}
	@RemoteMethod
	@Override
	public long getGradeClassId(long gradeId,long classId){
		return StudentDAO.getGradeClassId(gradeId,classId);
	}

	@Override
	public boolean SaveUserInterests(UserInterests userint) {
	GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		userint.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		userint.setChangeDate(changeDate);
		return StudentDAO.SaveUserInterests(userint);
	}

	@Override
	public List<ClassActualSchedule> getStudentClassesForRegistration(long gradeId){
		return StudentDAO.getStudentClassesForRegistration(gradeId);
	}
	@Override
	public List<TeacherSubjects> getTeachersForRegistration(long gradeId){
		return StudentDAO.getTeachersForRegistration(gradeId);
	}
	@Override
	public String setStatusForClassRegistration(long studentId,long sectionId, long csId,String status,String classStatus,long gradeLevelId,long gradeClassId){
		return StudentDAO.setStatusForClassRegistration(studentId, sectionId, csId, status, classStatus, gradeLevelId, gradeClassId);
	}
	
	@RemoteMethod
	@Override
	public List<ClassActualSchedule> getSectionsForRegistration(long teacherId,long studentId,long gradeClassId){
		return StudentDAO.getSectionsForRegistration(teacherId, studentId, gradeClassId);
	}
	
	
	@Override	
	public Student getStudent(long regId) throws DataException{
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		if (userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.USER_TYPE_PARENT)) {
			session.removeAttribute( "academicYrFlag");
		}
		return StudentDAO.getStudent(regId);
	}

	@Override
	public List<RegisterForClass> getStudentClasses(Student student) {
		session.removeAttribute( "academicYrFlag");
		return StudentDAO.getStudentClasses(student);
	}

	@Override
	public Grade getGrade(long gradeId) {
		return gradesdao.getGrade(gradeId);
	}

	@Override
	public Teacher getTeacherByCsId(long csId) {
		return commonDAO.getTeacherByCsId(csId);
	}	
	@Override
	public String  sendClassRequest(long studentId,long csId,long gradeClassId, long teacherId){
		return StudentDAO.sendClassRequest(studentId, csId, gradeClassId, teacherId);
	}

	@Override
	public List<Student> getStudentsByHomeRoom(long homeroomId) {
		return StudentDAO.getStudentsByHomeRoom(homeroomId);
	}

	@Override
	public List<RegisterForClass> getStudentGradeByYear(AcademicYear academicYear) {
		return StudentDAO.getStudentGradeByYear(academicYear);
	}

	@Override
	public List<RegisterForClass> getChildGradeByYear(AcademicYear academicYear, Student student) {
		return StudentDAO.getChildGradeByYear(academicYear,student);
	}
	@Override
	public List<StudentClass> getStudentScheduledClasses(Student student) {
		session.removeAttribute( "academicYrFlag");
		return StudentDAO.getStudentScheduledClasses(student);
	}
}

