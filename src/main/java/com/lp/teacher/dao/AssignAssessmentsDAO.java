package com.lp.teacher.dao;

import java.sql.SQLDataException;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignLessons;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.ClassStatus;
import com.lp.model.JacQuestionFile;
import com.lp.model.Questions;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;

public interface AssignAssessmentsDAO {

	public List<AssignLessons> getTeacherAssignLessons(Teacher teacher,long csId);
	public List<Questions> getQuestions(long lessonId,long assignmentTypeId,Teacher teacher,String usedFor,long gradeId);
	public long checkAssignmentExists(long cs_id,Long benchmarkId);
	public long checkStudentAssignmentStatus(long assignmentId, long studentId);
	public Assignment getAssignment(long assignmentId);
	public Student getStudent(long studentId);
	public Questions getQuestion(long quesId);
	public StudentAssignmentStatus getStudentAssignmentStatus(long studentAssignmentId);
	public boolean assignAssessments(Assignment assignment, List<StudentAssignmentStatus> studentAssignmentStatusList, List<Questions> questionsList, long retestId);
	public List<JacQuestionFile> getJacQuestions(Teacher teacher,String usedFor,long lessonId);
	public List<Questions> getJacQuestionsByTitleId(long titleId);
	public List<Assignment> getPreviousTestDates(long csId,long assignmentTypeId,String assignFor);
	public StudentAssignmentStatus getStudentAssignmentStatus(long assignmentId, long studentId);
	public List<StudentAssignmentStatus> getRetestObjects(long assignmentId, long studentId);
	public List<Assignment> getAssignmentByTitle(long csId, String title, String usedFor) throws SQLDataException;
	public long checkBenchmarkTitleExists(long benchmarkId, long studentId, long csId);
	public StudentAssignmentStatus assignReadingFluencyLearningPracticeHomeWork(Assignment assignment,StudentAssignmentStatus studentAssignmentStatus) throws DataException;
	public boolean assignRFLPTest(RflpTest rflpTest);
	public Assignment assignmentByTitle(String title, long assignmentTypeId, String usedFor) throws SQLDataException;
	public List<StudentAssignmentStatus> getStudentTests(long csId) throws DataException;
	public List<ClassStatus> getClasses() throws DataException;
	public boolean saveStudentTests(List<StudentAssignmentStatus> status);
	public List<AssignmentQuestions> getAssignmentQuestionsByStudentAssignmentId(long studentAssignmentId);
	public boolean assignRTF(Assignment assignment,	List<StudentAssignmentStatus> studentAssignmentStatusList,List<Questions> questionsList);
	public long checkBenchmarkTitleExistsForSection(long benchmarkId, long csId);
	
	
}
