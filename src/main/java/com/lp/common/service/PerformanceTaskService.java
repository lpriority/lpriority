package com.lp.common.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignedPtasks;
import com.lp.model.GroupPerformanceTemp;
import com.lp.model.PerformanceGroupStudents;
import com.lp.model.PerformancetaskGroups;
import com.lp.model.PtaskFiles;
import com.lp.model.Rubric;
import com.lp.model.RubricValues;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentPtaskEvidence;

public interface PerformanceTaskService {

	public boolean createPerformanceGroup(long csId, PerformancetaskGroups performancetaskGroups) throws DataException;

	public List<PerformancetaskGroups> getPerformanceGroups(long csId, String withStudents) throws DataException;

	public boolean addStudentPerformanceGroup(String studentIds, long perGroupId, long csId) throws DataException;

	public PerformancetaskGroups getPerformanceGroupById(long perGroupId) throws DataException;

	public boolean removeStudentPerformanceGroup(String studentId,	long perGroupId, long csId) throws DataException;
	
	public AssignedPtasks getAssignedPerformance(long studentAssignmentId) throws DataException;
	
	public List<Rubric> getRubrics(long performanceTaskId) throws DataException;

	public boolean submitPerformanceTest(AssignedPtasks assignedPtasks) throws DataException;

	public List<RubricValues> getRubricValuesByAssignedTaskId(long assignedTaskId) throws DataException;
	
	public boolean autoSaveAssignedTasks(GroupPerformanceTemp groupPerformanceTemp) throws DataException;

	public boolean autoRubricSave(RubricValues rubricValues) throws DataException;

	public List<PtaskFiles> getPtaskFiles(long questionId) throws DataException;

	public void fileAutoSave(MultipartFile file, AssignedPtasks assignedPtasks, long createdBy) throws DataException;

	public void audioAutoSave(String data, AssignedPtasks assignedPtasks,long createdBy) throws DataException;

	public PerformanceGroupStudents getPerformanceGroupByStudentId(long studentId) throws DataException;
	
	public List<StudentAssignmentStatus> getGroupTests(long pGroupId, String usedFor, String testStatus, String gradedStatus) throws DataException;

	public List<GroupPerformanceTemp> getGroupPerformanceTemp(long assignedTaskId) throws DataException;

	public boolean autoGroupRubricSave(GroupPerformanceTemp gPerTemp) throws DataException;

	boolean submitPermission(long assignedTaskId, long pGroupStudentsId, String status) throws DataException;

	public void updateAreaStatus(GroupPerformanceTemp gpTemp) throws DataException;
	
	public StudentAssignmentStatus getStudentAssignmentStatusById(long studentAssignmentId) throws DataException;

	public boolean submitGradePerformance(AssignedPtasks assignedPtasks) throws DataException;

	public List<StudentAssignmentStatus> getGroupAssessmentTests(long assignmentId) throws DataException;

	public void tinynceFileSave(MultipartFile file,	AssignedPtasks assignedPtasks, long createdBy) throws DataException;

	public boolean verifyGroupName(long csId, String groupName) throws DataException;

	public AssignedPtasks getCompletedPerformanceTask(long studentAssignmentId) throws DataException;
	
    public List<StudentPtaskEvidence> getStudentEvidencesByAssignedTaskId(long assignedTaskId) throws DataException;
    
    public AssignedPtasks getAssignedPtasks(long assignedPtaskId);
    
    public StudentPtaskEvidence getStudentEvidencesByStudentEvidenceId(long evidenceId);
    
    public long saveStudentEvidences(StudentPtaskEvidence stuEvid);
    
    public void updateStudentPtaskEvidence(long assignedPtaskId,long evidenceId,String evidence,long ptaskGroupStudentId);
    
    public PerformanceGroupStudents getPerformanceGroupStudentsById(long ptaskGroupStudentId)throws SQLException;
    
    public boolean gradePerformanceTasks(long assignPTaskId,float percentage,String teacherComment,int teacherScore);
	
	
}
