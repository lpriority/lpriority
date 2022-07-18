package com.lp.parent.service;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.ParentLastseen;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;

public interface ParentService {
	public List<UserRegistration> getchildren(long parentRegId);
	public List<Student> getStudentByParent(long parentRegId);
	public long getStudentGradeIdByRegId(long childRegId) throws DataException;
	public List<UserRegistration> getUnregisteredChildren(long parentId) throws DataException;
	public List<StudentAssignmentStatus> getStudentsTestResults(long studentId,long classId, String usedFor)  throws DataException;
	public List<StudentAssignmentStatus> getStudentsBenchmarkTestResults(long classId, String usedFor,Student student,long assignmentTypeId)  throws DataException;
	public List<StudentAssignmentStatus> getChildHomeworkReportDates(Student student,String usedFor);
	public List<StudentAssignmentStatus> getChildHomeworkReports(Student student,String usedFor,String assignedDate);
	public ParentLastseen getParentLastseen(UserRegistration userReg) throws DataException;
	public void saveParentLastSeen(ParentLastseen parentLastseen) throws DataException;
	public List<Long> getChildRtiTestCorrectResponses(List<StudentAssignmentStatus> rtiResults);
	public List<Long> getChildRtiTestWrongResponses(List<StudentAssignmentStatus> rtiResults);
	public List<ClassStatus> getAvailableTeachers(long gradeClassId) throws DataException;
	public List<ClassStatus> getTeacherSections(long gradeClassId, long teacherId) throws DataException;
	public List<StudentAssignmentStatus> getChildTestResults(long studentId,long classId, String usedFor);

}
