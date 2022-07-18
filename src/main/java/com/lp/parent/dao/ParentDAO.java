package com.lp.parent.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.ParentLastseen;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;


public interface ParentDAO {
	public List<UserRegistration> getchildren(long parentRegId);
	public List<Student> getStudentByParent(long parentRegId) throws DataException;
	public List<UserRegistration> getUnregisteredChildren(long parentId) throws DataException;
	public List<StudentAssignmentStatus> getStudentsTestResults(long studentId, String usedFor,String testStatus, String gradedStatus,long classId, long perGroupId);
	public List<StudentAssignmentStatus> getChildsBenchmarkResults(Student student, String usedFor,String testStatus, String gradedStatus,long classId,long assignmentTypeId);
	public List<StudentAssignmentStatus> getChildHomeworkReportDates(Student student,String usedFor);
	public List<StudentAssignmentStatus> getChildHomeworkReports(Student student,String usedFor,String assignedDate);
	public ParentLastseen getParentLastseen(UserRegistration userReg) throws DataException;
	public void saveParentLastSeen(ParentLastseen parentLastseen) throws DataException;
	public Long getChildCorrectResponses(long studentAssignmentId);
	public Long getChildWrongResponses(long studentAssignmentId);
	public List<ParentLastseen> getAllParentLastseen(long csId)	throws DataException;
	public  List<ClassStatus> getAvailableTeachers(long gradeClassId) throws DataException;
	public List<ClassStatus> getTeacherSections(long gradeClassId, long teacherId) throws DataException;
	public List<StudentAssignmentStatus> getChildTestResults(long studentId, String usedFor,String testStatus, String gradedStatus,long classId, long perGroupId);
}
