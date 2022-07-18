package com.lp.teacher.dao;

import java.util.Date;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.Attendance;
import com.lp.model.ClassActualSchedule;
import com.lp.model.Grade;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.StudentClass;

public interface TeacherViewClassDAO {
	public List<Attendance> getStudentsAttendance(long csId,String dateToUpdate);
	public boolean saveAttendance(List<Long> studentId,List<String> status, Date date,long sectionId,long teacherId);
	public boolean checkTodaysAttendance(long sectionId,Date date);
	public List<Student> getStudentsOfRegistration(long csId, long gradeClassId);
	public boolean setStudentAction(long studentId, long csId, long gradeClassId,long gradeLevelId,String status);
	public List<Grade> getTeacherGradesForRequest(long teacherId);
	public List<StudentClass> getTeacherClassesForRequest(long teacherId,long gradeId);
	public String sendRequestForAClass(long teacherId, long gradeId,long classId);
	public List<ClassActualSchedule> getMyClassesTimeTable(long teacherId);
	public List<ClassActualSchedule> getRequestedClassSections(long teacherId, long gradeId, long classId);
	public List<RegisterForClass> getStudentRoster(long csId) throws DataException;
	public List<ClassActualSchedule> getMyClassesTimeTable(long teacherId, long csId);
}
