package com.lp.student.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lp.model.AcademicYear;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.ReadRegAnswers;
import com.lp.model.ReadRegMaster;
import com.lp.model.ReadRegQuestions;
import com.lp.model.ReadRegQuiz;
import com.lp.model.ReadRegReview;
import com.lp.model.ReadRegRubric;
import com.lp.model.Student;

public interface StudentReadRegService {
	public String saveOrUpdateBook(ReadRegMaster readRegMaster);
	public List<ReadRegMaster> getAllAddedBooks(long masterGradesId);
	public ReadRegMaster getBookDetails(long bookId);
	public List<ReadRegMaster> getStudentBooks(long regId);
	public ReadRegMaster getReadRegMasterByTitleId(long titleId);
	public ReadRegReview getReadRegReviewByReviewId(long reviewId);
	public ReadRegReview getReadRegReviewByStudentId(long titleId, long studentId);
	public String saveOrUpdateReview(ReadRegReview readRegReview, String mode);
	public ReadRegQuestions getStudentQuestion(long titleId, long studentId, long quetionNum);
	public List<ReadRegQuiz> getAllQuizQuestionList();
	public String saveCreateQuestions(ReadRegQuestions regQuestions,int rating);	
	public String updateReadRegQuestion(ReadRegQuestions readRegQuestions);
	public List<Student> getAllQuizQuestionsGroupByTitleId(long titleId, long studentId);
	public List<ReadRegQuestions> getStudentAllQuizQuestionByTitleId(long titleId, long studentId);
	public List<ReadRegAnswers> getStudentAnswerByTitleId(long titleId, long currentStudentId, long createdStudentId);
	public String submitQuizAnswers(List<ReadRegAnswers> readRegAnswersLt,int rating,long titleId);
	public List<ReadRegRubric> getReadRegRubric(); 
	public ReadRegActivityScore getStudentActivity(long studentId, long titleId, long activityId);
	public String saveOrUpdateActivityScore(Student student, long titleId, long rubricScore, long activityIdm, String mode,int rating);
	public List<ReadRegQuestions> getStudentAllCreatedQuestionsList(long titleId, long studentId);
	public ReadRegActivityScore getStudentActivity(long readRegActivityScoreId);
	public List<ReadRegActivityScore> getStudentActivities(long studentId, String sortBy, String startDate, String endDate);
	public List<ReadRegActivityScore> getParentChildActivities(long studentId, String sortBy);
	public long getStudentTotalPointsEarned(long studentId, long gradeId);
	public boolean deleteBook(ReadRegMaster readRegMaster);
	public boolean TeacherSaveBook(ReadRegMaster readRegMaster);
	public boolean checkActivityExists(long titleId,long studentId);
	public boolean checkBookExists(String bookTitle,long masterGradesId);
	public List<ReadRegMaster> getRejectedBooksByRegId(long regId, AcademicYear academicYear);
	public boolean reSubmitBook(long titleId,long studentId);
	public int getStudentRating(long titleId,long studentId);
	
	public List<ReadRegMaster> getBooks(long masterGradesId, long pageId, long rows, String sortBy, String sortOrder, String bookName);
	public long getBookCount(long masterGradesId, String bookName);
	public long getApprovalBookCount(long gradeId, long teacherId,String bookName, AcademicYear academicYear);
	
	public String getRRPath(ReadRegActivityScore rActivityScore,  HttpServletRequest request);

	
}
