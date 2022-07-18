package com.lp.common.service;

import java.sql.SQLDataException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.AssignmentType;
import com.lp.model.JacQuestionFile;
import com.lp.model.JacTemplate;
import com.lp.model.Lesson;
import com.lp.model.Questions;
import com.lp.model.QuestionsList;
import com.lp.model.RubricScalings;
import com.lp.model.RubricTypes;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;

public interface AssessmentService {

	List<Lesson> getLessonsByUnitId(long unitId) throws DataException;
	List<AssignmentType> getAssignments(String assignFor) throws DataException;
	public boolean createAssessments(long assessmentTypeId,long lessonId,String usedFor, List <Questions> questions,long gradeId) throws DataException;
	public boolean createJacTemplate(long lessonId,long assessmentTypeId, QuestionsList questionsList, String usedFor,MultipartFile file,long gradeId) throws DataException;
	public SubQuestions createSentenceStructure(long lessonId,long assessmentTypeId, SubQuestions subQuestion, String usedFor,long gradeId) throws DataException;
	public List<RubricTypes> getRubricTypes() throws DataException;
	public List<RubricScalings> getRubricScalings() throws DataException;
	boolean createPerformancetask(long assessmentTypeId, long lessonId, String usedFor, List<Questions> questions,long gradeId) throws DataException;
	public Lesson getLessonById(long lessonId) throws DataException;
	public QuestionsList getQuestionByQuestionId(long questionId) throws SQLDataException;
	public List<Questions> getQuestionsByAssignmentType(long assignmentTypeId, long lessonId, String usedFor,long gradeId) throws DataException;
	public boolean updateAssessments(Questions question) throws DataException;
	public boolean removeAssessments(Questions question) throws SQLDataException;
	public List<SubQuestions> getSubQuestionsByAssignmentType(long assignmentTypeId,long lessonId, String usedFor,long gradeId) throws DataException;
	public SubQuestions updateSentenceStructure(SubQuestions subQuestion) throws DataException;
	public List<Questions> getRubricsByQuestionId(List<Questions> questions) throws DataException;
	public List<Questions> getPtaskFiles(List<Questions> questions) throws DataException;
	boolean deletePerformancefile(long fileId) throws DataException;
	public JacTemplate getJacTemplate(long jacQuestionId) throws SQLDataException;
	public List<JacQuestionFile> getJacFileQuestionsByUsedFor(long lessonId, String usedFor) throws DataException;
	public List<JacTemplate> getJacTemplateByFileId(long jacQuestionFileId) throws DataException;
	public List<Assignment> getTestDatesforRTIResults(long createdBy,long csId,String usedFor) throws SQLDataException;
	public List<StudentAssignmentStatus> getRTIResultsByStudent(long assignmentId) throws SQLDataException;
	public List<AssignmentQuestions> getRTIResultsByQuestion(long assignmentId) throws SQLDataException;
	public String saveJacTemplateFile(JacQuestionFile jqf, MultipartFile file,long assessmentTypeId)  throws SQLDataException;
	public List<Questions> createQuestionList(long lessonId,long assessmentTypeId, List<Questions> questions, String usedFor,JacTemplate jacTemplate, SubQuestions subQuestions,long gradeId) throws DataException ;
	public JacQuestionFile getJacQuestionFileByFileId(long jacQuestionFileId) throws SQLDataException;
	public boolean deleteJacTemplete(JacTemplate jacTemplate) throws SQLDataException;
	public boolean deleteQuestion(Questions question) throws SQLDataException;
	public Questions saveQuestion(Questions question) throws SQLDataException;
	public boolean saveJacTemplate(JacTemplate jacTemplate) throws SQLDataException;
	public boolean deleteJacTempleteQuestion(long jacQuestionFileId) throws SQLDataException;
	public List<Questions> getQuestionsByLessonId(long lessonId) throws SQLDataException;
	public SubQuestions getSubQuestionBySubQuestionId(long subQuestionId) throws SQLDataException;
	public boolean deleteSentenceStructure(long subQuestionId) throws SQLDataException;
	public List<JacQuestionFile> getJacFileQuestionsByLessonId(long lessonId) throws SQLDataException;
	public boolean checkQuestionExists(long questionId);
	public boolean checkJacTemplateExists(long jacTemplateId);
	public long checkQuestionAssigned(long questionId);
	public boolean updateSubQuestion(Assignment assignment, List<Student> studentList,List<Questions> questionsLt);
	public boolean checkSubQuestionExists(long questionId)throws SQLDataException;
	public List<AssignmentQuestions> getAssignmentByquestionId(long questionId) throws DataException;
	public boolean updateAssignmentQuestions(Questions question) throws DataException;
	
	

}
