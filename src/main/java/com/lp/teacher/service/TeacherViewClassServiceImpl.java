package com.lp.teacher.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.ClassActualSchedule;
import com.lp.model.Grade;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.teacher.dao.TeacherViewClassDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "teacherViewClassService")
public class TeacherViewClassServiceImpl  implements TeacherViewClassService{
	@Autowired
	HttpSession session;
	@Autowired
	TeacherViewClassDAO teacherViewClassDAO;
	@Autowired
	private AdminSchedulerDAO adminSchedulerDAO;
	static final Logger logger = Logger.getLogger(TeacherViewClassServiceImpl.class);

	@Override
	public boolean saveAttendance(List<Long> studentId,List<String> status, Date date,long sectionId,long teacherId){
		return teacherViewClassDAO.saveAttendance(studentId, status,date,sectionId,teacherId);		
	}
	
	@Override
	public boolean checkTodaysAttendance(long csId,Date date){
		return teacherViewClassDAO.checkTodaysAttendance(csId, date);
	}
	@RemoteMethod
	@Override
	public long getCsIdBySectionId(long sectionId){
		return adminSchedulerDAO.getCsIdBySectionId(sectionId);
	}
	
	@Override
	public List<Student> getStudentsOfRegistration(long csId,long gradeClassId){
		return teacherViewClassDAO.getStudentsOfRegistration(csId, gradeClassId);
	}
	
	@Override
	public boolean setStudentAction(long studentId, long csId, long gradeClassId,long gradeLevelId,String status) {
		return teacherViewClassDAO.setStudentAction(studentId, csId, gradeClassId,gradeLevelId, status);
	}
	
	@Override
	public List<Grade> getTeacherGradesForRequest(long teacherId){
	    return teacherViewClassDAO.getTeacherGradesForRequest(teacherId);	
	}
	
	@Override
	@RemoteMethod
	public List<StudentClass> getTeacherClassesForRequest(long gradeId){
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		return teacherViewClassDAO.getTeacherClassesForRequest(teacherObj.getTeacherId(), gradeId);
	}
	
	@Override
	public String sendRequestForAClass(long teacherId, long gradeId,long classId) {
		return teacherViewClassDAO.sendRequestForAClass(teacherId, gradeId, classId);
	}
	@Override
	@RemoteMethod
	public List<ClassActualSchedule> getMyClassesTimeTable(long teacherId){
		return teacherViewClassDAO.getMyClassesTimeTable(teacherId);
	}
	@Override
	@RemoteMethod
	public List<ClassActualSchedule> getRequestedClassSections(long teacherId, long gradeId, long classId) {
		return teacherViewClassDAO.getRequestedClassSections(teacherId, gradeId, classId);
	}

	@Override
	public List<RegisterForClass> getStudentRoster(long csId) throws DataException {
		List<RegisterForClass> students = new ArrayList<RegisterForClass>();
		try{
			students = teacherViewClassDAO.getStudentRoster(csId);
		}catch(DataException e){
			logger.error("Error in getStudentRoster() of  TeacherViewClassServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentRoster() of TeacherViewClassServiceImpl", e);
		}
		return students;
	}
	@Override
	@RemoteMethod
	public List<ClassActualSchedule> getMyClassesTimeTable(long teacherId, long csId){
		return teacherViewClassDAO.getMyClassesTimeTable(teacherId,csId);
	}
}
