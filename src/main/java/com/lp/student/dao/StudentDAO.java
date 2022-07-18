package com.lp.student.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.ClassActualSchedule;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserInterests;

public interface StudentDAO {
	public boolean SaveStudent(Student student);
	public long getGradeClassId(long gradeId,long classId);
	public boolean SaveUserInterests(UserInterests userInt);
	public List<Student> saveBulkStudents(List<Student> newStuList);
	public List<ClassActualSchedule> getStudentClassesForRegistration(long gradeId);
	public List<TeacherSubjects> getTeachersForRegistration(long gradeId);
	public String setStatusForClassRegistration(long studentId, long sectionId, long csId,String status,String classStatus,long gradeLevelId,long gradeClassId);
	public List<ClassActualSchedule> getSectionsForRegistration(long teacherId,long studentId,long gradeClassId);
	public List<Student> getAllStudents();
	public Student getStudent(long regId) throws DataException;
	public long getSectionId(long studentId,long gradeClassId);
	public List<RegisterForClass> getStudentClasses(Student student);
	public String sendClassRequest(long studentId,long csId,long gradeClassId, long teacherId);
	public List<Student> getStudentsByHomeRoom(long homeroomId);
	public List<Student> getActiveStudents();
	public List<RegisterForClass> getStudentGradeByYear(AcademicYear academicYear);
	public List<RegisterForClass> getChildGradeByYear(AcademicYear academicYear, Student student);
	public List<Student> updateStudentGradeAndSchool(List<Student> newStuList);
	public List<StudentClass> getStudentScheduledClasses(Student student);
}
