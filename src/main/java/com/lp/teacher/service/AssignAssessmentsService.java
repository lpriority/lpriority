package com.lp.teacher.service;


import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignLessons;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.JacQuestionFile;
import com.lp.model.Questions;
import com.lp.model.RegisterForClass;
import com.lp.model.RtiGroups;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.model.Teacher;


public interface AssignAssessmentsService {
	public List<AssignLessons> getTeacherAssignLessons(long csId);
	public List<Questions> getQuestions(long lessonId,long assignmentTypeId,Teacher teacher,String usedFor,long gradeId);
	public List<SubQuestions> getSSQuestions(List<Questions> questions);
	@SuppressWarnings("rawtypes")
	public boolean assignAssessments(Assignment assignment, List studentList, ArrayList<Long> questionList, long retestId, List groupList);
	public List<RtiGroups> getRTIGroups(List<RegisterForClass> list);
	public List<JacQuestionFile> getJacQuestions(Teacher teacher,String usedFor,long lessonId);
	public List<Assignment> getPreviousTestDates(long csId,long assignmentTypeId,String assignFor);
	public List<Assignment> getAssignmentByTitle(long csId, String title, String usedFor) throws DataException;
	public long checkBenchmarkTitleExists(long benchmarkId, long studentId, long csId);
	public StudentAssignmentStatus assignReadingFluencyLearningPracticeHomeWork(Assignment assignment,StudentAssignmentStatus studentAssignmentStatus);
	public boolean assignRFLPTest(List<String> contentList,String dueDate,StudentAssignmentStatus stdAssignmentStatus, long gradingTypesId,long peerReviewBy);
	public Assignment assignmentByTitle(String title, long assignmentTypeId, String usedFor) throws SQLDataException;
	public StudentAssignmentStatus getStudentAssignmentStatus(long studentAssignmentId);
	public StudentAssignmentStatus getStudentAssignmentStatus(long assignmentId, long studentId);
	public void updateStudentTests();
	public List<AssignmentQuestions> getAssignmentQuestionsByStudentAssignmentId(long studentAssignmentId);
	public boolean assignRTF(Assignment assignment, ArrayList<String> csIdList, ArrayList<Long> questionList);
	public long checkBenchmarkTitleExistsForSection(long benchmarkId, long csId);
	public boolean assignRFLPTestFromTeacherGrading(List<FluencyMarksDetails> contentList,String dueDate,StudentAssignmentStatus stdAssignmentStatus, long gradingTypesId,long peerReviewBy);
}
			
