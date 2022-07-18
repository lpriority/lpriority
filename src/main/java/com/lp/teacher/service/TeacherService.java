package com.lp.teacher.service;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.AdminTeacherReports;
import com.lp.model.Assignment;
import com.lp.model.BenchmarkResults;
import com.lp.model.ClassStatus;
import com.lp.model.FluencyAddedWords;
import com.lp.model.FluencyErrorTypes;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.HomeroomClasses;
import com.lp.model.LessonPaths;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.TeacherPerformances;
import com.lp.model.TeacherReports;
import com.lp.model.User;
import com.lp.model.UserRegistration;

public interface TeacherService {

	public String registerTeacher(UserRegistration user, String[] gradeIds,
			String[] classIds, String[] classLengths, String[] gradeLevels,
			String[] noOfSectionsPerDay, String[] noOfSectionsPerWeek);
	
	public List<Teacher> getTeachers(long gradeId, long classId);
	
	public List<TeacherPerformances> getTeacherPerformances(); 
	
    public TeacherPerformances getTeacherPerformance(long performanceId);
    
    public Teacher getTeacher(long teacherregId);
    
    public boolean saveTeacherPerformances(TeacherReports teacherreports);

    
    public UserRegistration getAdminUserRegistration(School school);
    
    public List<TeacherReports> getTeacherReports(User user,Teacher teacher);
    
    public List<ClassStatus> getTeachersBySchoolId(School school);
    
    public List<AdminTeacherReports> getTeacherSelfReportDates(long teacherId);
  	
    public List<Teacher> getTeachersByGradeId(long gradeId, long schoolId);
    
    public boolean saveAdminTeacherReports(AdminTeacherReports adminteacherreports);
    
    public List<TeacherReports> getTeacherReportsByDate(Teacher teacher,String reportDate);
    
    public Teacher getTeacherbyTeacherId(long teacherId);
    
    public List<Assignment> getBenchmarkDates(Teacher teacher, long csId, String usedFor);
    
    public List<BenchmarkResults> getBenchmarkResults(Teacher teacher, long csId, String usedFor,String dateAssigned,long assignemntId);
    
    public List<String> getAllStudentRTIGroupName(List<BenchmarkResults> benchmarkResults);
    
    public List<Integer> getAllStudentCompositeScore(List<BenchmarkResults> benchmarkResults);
    
    public void setLessonPath(LessonPaths lessonPaths);

	public String checkHomeRoomTeacher(HomeroomClasses hrc) throws DataException;
	
	public List<LessonPaths> getLessonPathByLessonId(long lessonId);
	
	public boolean deleteLessonPath(long lessonId,String lessonPath);
	
	public ClassStatus getClassStatus(long csId);
	
	public List<Student> getStudents(List<BenchmarkResults> fluencyResults);
	
	public List<StudentAssignmentStatus> getStudentAssignmentStatusList(long assignmentId);
	
	public List<List<Integer>> getErrorWordDetails(List<FluencyMarksDetails> benchQuesList,List<StudentAssignmentStatus> stuAssignStatusList,long type);
		
	public List<FluencyMarksDetails> getErrorWordCount(long assignmentId);
	
	public List<FluencyAddedWords> getAddedWordCount(long assignmentId,long wordType);
	
	public List<List<Integer>> getAddedWordDetails(List<FluencyAddedWords> benchQuesList,List<StudentAssignmentStatus> stuAssignStatusList,long type);
	
    public List<FluencyErrorTypes> getFluencyErrorTypes();
    
    public UserRegistration getAppAdminUserRegistration();
    
    public List<Student> getAllStudentsByAssignmentId(long assignmentId);
    
    public List<Assignment> getAccuracyDates(long csId,
			String usedFor);
}

