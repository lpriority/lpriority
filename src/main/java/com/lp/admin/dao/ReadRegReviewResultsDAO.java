package com.lp.admin.dao;

import java.util.Date;
import java.util.List;

import com.lp.model.AcademicYear;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.ReadRegAnswers;
import com.lp.model.ReadRegMaster;
import com.lp.model.ReadRegQuestions;
import com.lp.model.ReadRegRubric;
import com.lp.model.Student;

public interface ReadRegReviewResultsDAO {
	public List<ReadRegActivityScore> getActivitiesToBeReviewed(long studentId, AcademicYear academicYear, String sortBy);
	public List<Student> getRRStudents(long gradeId, long teacherId);
	public List<Student> getRRStudents(long gradeId);
	public List<Student> getRRStudentsByGrade(long gradeId, AcademicYear academicYear, String gradedStatus);
	public List<Student> getRRStudentsByYear(long gradeId, AcademicYear academicYear, long teacherId, String gradedStatus);
	public List<ReadRegActivityScore> getGradedActivities(long gradeId,String sortBy, long teacherId, String startDate, String endDate);
	public List<ReadRegActivityScore> getUnGradedActivities(long gradeId,String sortBy, AcademicYear academicYear);
	public List<ReadRegActivityScore> getUnGradedActivities(long gradeId,String sortBy,long teacherId, AcademicYear academicYear);
	public ReadRegRubric getReadRegRubricById(long scoreId);
	public boolean saveScore(long readRegActivityScoreId, long scoreId,long pointsEarned,String actApproveStat,String teacherComment);
	public List<ReadRegQuestions> getReadRegQuestions(long studentId, long titleId);
	public List<ReadRegAnswers> getStudentQuiz(long studentId, long titleId);
	public List<ReadRegMaster> getAllAddedBooksByGrade(long gradeId, long teacherId);
	public boolean updateAprrovalStatus(ReadRegMaster readRegMaster);
	public boolean setActivityApprovalStatus(ReadRegMaster readRegMaster);
	public List<Student> getRRStudents(long gradeId,String approveStatus);
	public List<Student> getRRStudents(long gradeId,long teacherId,String approveStatus);
	public List<ReadRegMaster> getAllAddedBooksByGrade(long gradeId, AcademicYear academicYear, long teacherId,long pageId,long rows,String sortBy, String sortOrder, String bookName);
	public List<ReadRegMaster> getReadRegBooksByGrade(long gradeId, long teacherId, AcademicYear academicYear, long pageId,long rows);
	public boolean returnGradedActivity(long readRegActivityScoreId);
	public boolean mergeReadRegDulicateBooks(Long[] titleIds, ReadRegMaster approvedBook);
	public ReadRegMaster getApprovedBook(Long[] readRedTitleIdArrayLt);
}
