package com.lp.common.dao;

import java.sql.SQLDataException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignedPtasks;
import com.lp.model.GroupPerformanceTemp;
import com.lp.model.PerformanceGroupStudents;
import com.lp.model.PerformancetaskGroups;
import com.lp.model.PtaskFiles;
import com.lp.model.RegisterForClass;
import com.lp.model.Rubric;
import com.lp.model.RubricValues;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentPtaskEvidence;


public interface PerformanceTaskDAO {
	
	public void createPerformanceGroup(PerformancetaskGroups performancetaskGroups) throws SQLDataException;

	public List<PerformancetaskGroups> getPerformanceGroups(long csId) throws SQLDataException;

	public boolean addStudentPerformanceGroup(List<PerformanceGroupStudents> perGroupStudents, long csId) throws SQLDataException;

	public PerformancetaskGroups getPerformanceGroupById(long perGroupId) throws SQLDataException;

	public boolean removeStudentPerformanceGroup(PerformanceGroupStudents perGroupStudent, long csId) throws SQLDataException;

	public AssignedPtasks getAssignedPerformance(long studentAssignmentId) throws SQLDataException;

	public List<Rubric> getRubrics(long performanceTaskId) throws SQLDataException;

	public boolean submitPerformanceTest(AssignedPtasks assignedPtasks) throws SQLDataException;

	public List<RubricValues> getRubricValuesByAssignedTaskId(long assignedTaskId) throws SQLDataException;

	public boolean autoSaveAssignedTasks(GroupPerformanceTemp groupPerformanceTemp) throws SQLDataException;

	public boolean autoRubricSave(RubricValues rubricValues) throws SQLDataException;

	public List<PtaskFiles> getPtaskFiles(long questionId) throws SQLDataException;

	public void fileAutoSave(MultipartFile file, AssignedPtasks assignedPtasks, long createdBy) throws SQLDataException;

	public void audioAutoSave(String data, AssignedPtasks assignedPtasks,long createdBy) throws SQLDataException;

	public PerformanceGroupStudents getPerformanceGroupByStudentId(long studentId) throws SQLDataException;

	public List<StudentAssignmentStatus> getGroupTests(long pGroupId, String usedFor, String testStatus, String gradedStatus) throws SQLDataException;

	public List<GroupPerformanceTemp> getGroupPerformanceTemp(long assignedTaskId) throws SQLDataException;

	List<PerformanceGroupStudents> getPerformanceGroupStudents(long performanceGroupId) throws DataException;

	public List<RegisterForClass> getRegisteredPerGroups() throws DataException;

	public boolean autoGroupRubricSave(GroupPerformanceTemp gPerTemp) throws SQLDataException;

	public boolean submitPermission(long assignedTaskId, long pGroupStudentsId, String status) throws SQLDataException;

	public void updateAreaStatus(GroupPerformanceTemp gpTemp) throws SQLDataException;

	public StudentAssignmentStatus getStudentAssignmentStatusById(long studentAssignmentId) throws SQLDataException;

	public boolean submitGradePerformance(AssignedPtasks assignedPtasks) throws SQLDataException;

	public List<StudentAssignmentStatus> getGroupAssessmentTests(long assignmentId) throws SQLDataException;

	public boolean verifyPerformanceGroupNameExists(long csId, String groupName) throws SQLDataException;

	public AssignedPtasks getCompletedPerformanceTask(long studentAssignmentId) throws SQLDataException;
	
	public List<StudentPtaskEvidence> getStudentEvidencesByAssignedTaskId(long assignedTaskId)throws DataException;
	
	public AssignedPtasks getAssignedPtasks(long assignedPtaskId) throws DataException;
	 
	public StudentPtaskEvidence getStudentEvidencesByStudentEvidenceId(long evidenceId);
	
	public long saveStudentEvidence(StudentPtaskEvidence studentevid);
	
	public void updateStudentPtaskEvidence(long assignedPtaskId,long evidenceId,String evidence);
	
	public PerformanceGroupStudents getPerformanceGroupStudentById(long ptaskGroupStudentId)throws SQLDataException;
	
	public void updateGroupStudentPtaskEvidences(long assignedPtaskId,long evidenceId,String evidence,long ptaskGroupStudentId);

}
