
package com.lp.teacher.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.Activity;
import com.lp.model.AssignActivity;
import com.lp.model.AssignK1Tests;
import com.lp.model.AssignLessons;
import com.lp.model.Assignment;
import com.lp.model.BenchmarkResults;
import com.lp.model.ClassStatus;
import com.lp.model.CreateUnits;
import com.lp.model.FluencyMarks;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.SchoolSchedule;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.Unit;
import com.lp.model.UserRegistration;


public interface CommonDAO {
	public List<Grade> getTeacherAssignedGrades(Teacher teacher);
	public Teacher getTeacher(UserRegistration userReg);
	public List<StudentClass> getTeacherClasses(Teacher teacher, long gradeId);
	public List<Unit> getUnits(long gradeId, long classId, Teacher teacher, UserRegistration adminReg);
	public List<Activity> getActivitiesByLesson(long lessonId);
	public List<ClassStatus> getTeacherSections(Teacher teacher, long gradeId, long classId);
	public UserRegistration getAdminByTeacher(UserRegistration teacherReg);
	public ClassStatus getClassStatus(long csId); 	
	public boolean assignCurriculum(List <AssignLessons> assignLesson, List <AssignActivity> assignActivities) throws DataException;
	public CreateUnits getTeacherCurriculum(long gradeId, long classId, Teacher teacher, UserRegistration adminReg, SchoolSchedule schedule);
	public long getTeacherSubjectId(long gradeId, long classId, long teacherId);
	public Student getStudent(UserRegistration userReg);	
	public Teacher getTeacherByCsId(long csId);
	List<ClassStatus> getAdminSections(long gradeId, long classId) throws DataException;
	public GradeClasses getTeacherGradeClassess(long gradeId, long classId);
	public List<BenchmarkResults> getBenchmarkResults(long schoolId, List<Long> teacherList ,List<Long> bencmarkTypes );
	public String getStudentRTIGroup(long csId, long studentId);
	public List<AssignK1Tests> getEarlyLitracyResults();
	public UserRegistration getAdminBySchool(long schoolId);
	public List<FluencyMarks> getFluencyMarks(long csId);
	public Assignment getAssignmentByAssignmentId(long assignmentId);
	public void migrateIOLData();
	public List<StudentAssignmentStatus> getComprehensionResults(long schoolId, List<Long> teacherList, AcademicYear academicYear);
	public List<AcademicYear> getSchoolAcademicYears();
	public AcademicYear getAcademicYearById(long academicYearId);
	public AcademicYear getCurrentAcademicYear();
	public Student getStudentByRegId(long regId);
	public List<BenchmarkResults> getBenchmarkResults(long schoolId, List<Long> teacherList ,List<Long> bencmarkTypes,AcademicYear academicYear);
}
