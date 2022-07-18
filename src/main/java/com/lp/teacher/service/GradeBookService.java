
package com.lp.teacher.service;

import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.List;

import org.springframework.ui.Model;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignActivity;
import com.lp.model.AssignLessons;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkResults;
import com.lp.model.CompositeChartValues;
import com.lp.model.ItemAnalysis;
import com.lp.model.RegisterForClass;
import com.lp.model.Report;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentAttendance;
import com.lp.model.StudentAttendanceByStatus;
import com.lp.model.StudentCompositeProjectScore;

public interface GradeBookService {
	public List<RegisterForClass> getGradeStudentList(long csId) throws DataException;
	public HashMap<Long,Double> getStudentAssignmentsByCsId(long csId, String usedFor) throws DataException;
	public Student getStudentById(long studentId) throws DataException;
	public void updateStudentGrades(List<StudentAssignmentStatus> studentTestList) throws DataException;
	public List<AssignmentQuestions> getAssignmentQuestion(long sasId) throws DataException;
	public HashMap<Long, Double> getStudentAssignmentsExcludePerformance(long csId, String usedFor, String fromDate, String toDate) throws DataException;
	public HashMap<Long, Double> getStudentPerformanceAssessments(long csId, String fromDate, String toDate) throws DataException;
	public HashMap<Long, StudentAttendance> getClassAttendance(long csId, String fromDate, String toDate) throws DataException;
 	public ItemAnalysis getItemAnalysisReports(long assignmentId) throws DataException;
	public void submitReportChanges(Report report) throws DataException;
	public List<Report> getReportDates(long csId, long studentId) throws DataException;
	public CompositeChartValues getCompositeChartValues(Model model, long csId) throws DataException;	
	public boolean saveCompositeChartValues(CompositeChartValues compositeChartValues) throws DataException;
	public List<BenchmarkResults> getBanchResults(long studentId, long csId) throws DataException;
	public List<AssignmentQuestions> getBenchAssignments(long studentId, long csId) throws DataException;
	public StudentAttendanceByStatus getStudentAttendance(long csId, long studentId) throws SQLDataException;
	public List<StudentAssignmentStatus> getStudentTests(long csId, long studentId) throws SQLDataException;
	public List<AssignLessons> getAssignedLesson(long csId, long studentId) throws SQLDataException;
	public List<AssignActivity> getAssignedActivities(long csId, long studentId) throws SQLDataException;
	public StudentCompositeProjectScore getStudentCompositeProjectScore(long csId, long studentId) throws SQLDataException;	
	public boolean saveStudentCompositeChartValues(long studentId, long csId, String[] assignLessonIds, String[] lessonScores, 
		String[] assignActivityIds,String[] activityScores, int projectScore) throws SQLDataException;
	public boolean saveParentComments(long reportId, String comment) throws DataException;
}
