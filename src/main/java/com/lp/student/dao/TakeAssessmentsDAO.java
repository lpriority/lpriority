package com.lp.student.dao;

import java.util.List;

import com.lp.model.AcademicGrades;
import com.lp.model.AssignmentQuestions;
import com.lp.model.Questions;
import com.lp.model.RflpPractice;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;


public interface TakeAssessmentsDAO { 
	public Student getStudent(UserRegistration userReg) ;
	public List<StudentAssignmentStatus> getStudentTests(Student student, String usedFor, String testStatus, String gradedStatus);
	public List<StudentAssignmentStatus> getStudentCurrentHomeworks(Student student, String usedFor, String testStatus, String gradedStatus, long csId);
	public List<StudentAssignmentStatus> getStudentDueHomeworks(Student student, String usedFor, String testStatus, String gradedStatus, long csId);
	public List<AssignmentQuestions> getTestQuestions(long studentAssignmentId);
	public boolean saveAssignmentQuestionsMarks(AssignmentQuestions assignmentQuestion);
	public boolean saveAssignment(StudentAssignmentStatus studentAssignmentStatus, String operation, boolean lateSubmission);
	public void saveJacAnswer(String answer,long assignmentQuestionId,long secMark);
	public long getMaxMarks(long studentAssignmentId);
	public long getSecuredMarks(long studentAssignmentId);
	public boolean submitJacTemplateTest(long studentAssignmentId,float percentage, AcademicGrades academicGrades);
	public boolean submitBenchmarkTest(long studentAssignmentId);
	public boolean autoSaveAssignment(long assignmentQuestionId, String answer);
	public List<RflpPractice> getRflpTest(long studentAssignmentId);
	public boolean saveRflpTest(long rflpPracticeId, String studentSentence, String operation, long studentAssignmentId);
	public boolean submitRflpTest(RflpTest rflpTest);	
	public List<Questions> getCompQuestionList(long subQuestionsId);
	public List<Questions> getCompQuestionList(long subQuestionsId,List<AssignmentQuestions> assignQuests);
	public List<StudentAssignmentStatus> getStudentWordWorks(Student student, String testStatus, String gradedStatus);
}
