package com.lp.student.service;

import java.util.List;
import java.util.Map;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicPerformance;
import com.lp.model.ClassStatus;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;

public interface StudentTestResultsService {	
	public List<StudentAssignmentStatus> getStudentsTestResults(long classId, String usedFor, long studentId) throws DataException;
	public List<AcademicPerformance> getAcademicPerformance() throws DataException;
	public Map<Long,String> getNumericalPercentageScore();
	public List<StudentAssignmentStatus> getStudentsBenchmarkTestResults(long classId, String usedFor, long assignmentTypeId)  throws DataException;
	public List<StudentAssignmentStatus> getHomeworkAssignedDates(long csId,String usedFor,Student student);
	public List<StudentAssignmentStatus> getHomeworkReports(long csId,String usedFor,String assignedDate,Student student);
	public List<RflpTest> getRFLPHomeworks(long csId,String usedFor,Student student,long gradeTypesId);
	public List<StudentAssignmentStatus> getStudentBenchmarkTests(long classId, String usedFor,long gradeTypesId,long assignmentTypeId)  throws DataException;
	public ClassStatus getclassStatus(long csId);
	public List<StudentAssignmentStatus> getStudentAllRTITestResults(long csId, String usedFor, long studentId) throws DataException;
}
