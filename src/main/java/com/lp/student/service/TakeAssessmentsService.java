package com.lp.student.service;

import java.util.List;

import com.lp.model.AssignmentQuestions;
import com.lp.model.JacQuestionFile;
import com.lp.model.JacTemplate;
import com.lp.model.RflpPractice;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.model.UserRegistration;

public interface TakeAssessmentsService {
	public Student getStudent(UserRegistration userReg);
	public List<StudentAssignmentStatus> getStudentTests(Student student, String usedFor, String testStatus, String gradedStatus);
	public List<StudentAssignmentStatus> getStudentCurrentHomeworks(Student student, String usedFor, String testStatus, String gradedStatus, long csId);
	public List<StudentAssignmentStatus> getStudentDueHomeworks(Student student, String usedFor, String testStatus, String gradedStatus, long csId);
	public List<AssignmentQuestions> getTestQuestions(long studentAssignmentId);
	public List<SubQuestions> getSSQuestions(List<AssignmentQuestions> questions);
	public boolean saveAssignment(StudentAssignmentStatus studentAssignmentStatus, String operation, boolean lateSubmission);
	public List<JacTemplate> getJacTemplateTitleList(List<AssignmentQuestions> questions);
	public void saveJacAnswer(String answer,String originalAnswer,long assignmentQuestionId);
	public boolean submitJacTemplateTest(long studentAssignmentId, String tab);
	public String getJacQuestionFilePath(JacQuestionFile jacQuestionFile);
	public void saveAccuracyFiles(long assignmentQuestionId,String audioData, String passageType);
	public boolean saveBenchmarkFiles(long assignmentQuestionId, String audioData, String passageType);
	public boolean submitBenchmarkTest(long studentAssignmentId);
	public boolean autoSaveAssignment(long assignmentQuestionId, String answer);
	public List<RflpPractice> getRflpTest(long studentAssignmentId);
	public boolean saveRflpTest(long rflpPracticeId, String studentSentence, String operation, long studentAssignmentId);
	public boolean submitRflpTest(RflpTest rflpTest);
	public List<SubQuestions> getComprehensionQuestions(List<AssignmentQuestions> questions) ;
	public List<StudentAssignmentStatus> getStudentWordWorks(Student student, String testStatus, String gradedStatus);
	
	
}
