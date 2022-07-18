package com.lp.student.dao;

import java.util.List;
import java.util.Map;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicPerformance;
import com.lp.model.Assignment;
import com.lp.model.FluencyMarks;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;

public interface StudentTestResultsDAO {
	public List<StudentAssignmentStatus> getStudentsTestResults(Student student,String usedFor, String testStatus, String gradedStatus,long classId, long perGroupId);
	public List<AcademicPerformance> getAcademicPerformance();
	public Map<Long,String> getNumericalPercentageScore();
	public List<StudentAssignmentStatus> getStudentsBenchmarkResults(Student student, String usedFor,String testStatus, String gradedStatus,long classId, long assignmentTypeId);
	public List<StudentAssignmentStatus> getHomeworkAssignedDates(long csId,String usedFor,Student student);
	public List<StudentAssignmentStatus> getHomeworkReports(long csId,String usedFor,String assignedDate,Student student);
	public List<FluencyMarks> getStudentAssignments(Student student);
	public List<RflpTest> getRFLPHomeworks(long csId,String usedFor,Student student,long gradeTypesId);
	public List<RflpTest> getPeerRFLPHomeworks(long csId,String usedFor,Student peerStudent,long gradeTypesId);
	public List<StudentAssignmentStatus> getStudentBenchmarkTests(Student student, String usedFor,String testStatus, String gradedStatus,long classId,long assignmentTypeId)  throws DataException;
	public List<StudentAssignmentStatus> getStudentBenchPeerReviewTests(Student student, String usedFor,String testStatus, String gradedStatus,long classId,long assignmentTypeId)  throws DataException;
    public boolean getCompleteSelfTestByStudentId(Assignment assignment,Student student,String testStatus);
    public List<StudentAssignmentStatus> getStudentAllRTITestResults(Student student, String usedFor,String testStatus, String gradedStatus,long csId, long perGroupId);
}
