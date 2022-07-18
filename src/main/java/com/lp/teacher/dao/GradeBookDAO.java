
package com.lp.teacher.dao;

import java.sql.SQLDataException;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignActivity;
import com.lp.model.AssignLessons;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkResults;
import com.lp.model.Compositechart;
import com.lp.model.Report;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentAttendanceByStatus;
import com.lp.model.StudentCompositeActivityScore;
import com.lp.model.StudentCompositeLessonScore;
import com.lp.model.StudentCompositeProjectScore;

public interface GradeBookDAO {

	public List<Object[]> getStudentAssignmentsByCsId(long csId, String usedFor) throws SQLDataException;

	public void updateStudentGrades(List<StudentAssignmentStatus> studentTestList) throws SQLDataException;

	public List<AssignmentQuestions> getAssignmentQuestion(long sasId) throws SQLDataException;

	public List<Object[]> getStudentAssignmentsExcludePerformance(long csId, String usedFor, String fromDate, String toDate) throws SQLDataException;

	public List<Object[]> getStudentPerformanceAssessments(long csId, String fromDate, String toDate) throws SQLDataException;

	public List<Object[]> getClassAttendance(long csId, String fromDate, String toDate) throws SQLDataException;
   	
	public List<Object[]> getTestPercentage(long assignmentId) throws DataException;
	
   	public List<AssignmentQuestions> getItemAnalysisReports(long assignmentId) throws DataException;

	public void submitReportChanges(Report report) throws SQLDataException;

	public List<Report> getReportDates(long csId, long studentId) throws SQLDataException;

	public List<Compositechart> getCompositeChartValues(long csId) throws DataException;
	
	public boolean saveCompositeChartValues(List<Compositechart> compositecharts) throws DataException;
	
	public List<BenchmarkResults> getBanchResults(long studentId, long csId) throws SQLDataException;

	public List<AssignmentQuestions> getBenchAssignments(long studentId, long csId) throws SQLDataException;
	
	public StudentAttendanceByStatus getStudentAttendance(long csId, long studentId) throws SQLDataException;
	
	public List<StudentAssignmentStatus> getStudentTests(long csId, long studentId) throws SQLDataException;

	public List<StudentAssignmentStatus> getTests(long csId, long studentId) throws SQLDataException;	
	
	public StudentCompositeProjectScore getStudentCompositeProjectScore(long csId, long studentId) throws SQLDataException;	
	
	public List<AssignLessons> getAssignedLesson(long csId, long studentId);
	
	public List<AssignActivity> getAssignedActivities(long csId, long studentId);
	
	public boolean saveStudentCompositeChartValues(List<StudentCompositeLessonScore> lessonScores, List<StudentCompositeActivityScore> activityScores, StudentCompositeProjectScore projectScore)
		throws SQLDataException;

	public boolean saveParentComments(long reportId, String comment) throws SQLDataException;	
}
