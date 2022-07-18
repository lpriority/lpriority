package com.lp.common.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicGrades;
import com.lp.model.AssignedPtasks;
import com.lp.model.ClassStatus;
import com.lp.model.GroupPerformanceTemp;
import com.lp.model.PerformanceGroupStudents;
import com.lp.model.PerformancetaskGroups;
import com.lp.model.PtaskFiles;
import com.lp.model.RegisterForClass;
import com.lp.model.Rubric;
import com.lp.model.RubricValues;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentPtaskEvidence;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;


@RemoteProxy(name = "performanceService")
public class PerformanceTaskServiceImpl implements PerformanceTaskService {

	@Autowired
	private AdminSchedulerDAO adminSchedulerDAO;
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDao;
	@Autowired
	private UserRegistrationDAO userRegistrationDAO;	
	
	static final Logger logger = Logger.getLogger(PerformanceTaskServiceImpl.class);
	
	@Override
	public boolean createPerformanceGroup(long csId, PerformancetaskGroups performancetaskGroups) throws DataException {
		try{
			ClassStatus cs = adminSchedulerDAO.getclassStatus(csId);
			performancetaskGroups.setClassStatus(cs);
			performanceTaskDAO.createPerformanceGroup(performancetaskGroups);
		}catch(DataException | SQLDataException e){
			logger.error("Error in createPerformanceGroup() of  PerformanceTaskServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in createPerformanceGroup() of PerformanceTaskServiceImpl",e);
		}
		return true;
	}
	
	@Override
	@RemoteMethod
	public List<PerformancetaskGroups> getPerformanceGroups(long csId, String withStudents) throws DataException {
		List<PerformancetaskGroups> pGroups = new ArrayList<PerformancetaskGroups>();
		List<PerformancetaskGroups> pGroupsTemp = new ArrayList<PerformancetaskGroups>();
		HashSet<Long> set = new HashSet<>();
		try{
			pGroupsTemp = performanceTaskDAO.getPerformanceGroups(csId);
			if(!withStudents.equalsIgnoreCase(WebKeys.LP_YES)){
				return pGroupsTemp;
			}
			List<RegisterForClass> rForClass = Collections.emptyList();
			rForClass = performanceTaskDAO.getRegisteredPerGroups();
			
			for(PerformancetaskGroups pg : pGroupsTemp){
				for(RegisterForClass rfc : rForClass){
					if(pg.getPerformanceGroupId() == rfc.getPerformancetaskGroups().getPerformanceGroupId() 
							&& !set.contains(pg.getPerformanceGroupId()) ){
						pGroups.add(pg);
						set.add(pg.getPerformanceGroupId());
					}
				}
			}
			
		}catch(DataException | SQLDataException e){
			logger.error("Error in getPerformanceGroups() of  PerformanceTaskServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getPerformanceGroups() of PerformanceTaskServiceImpl",e);
		}
		return pGroups;
	}

	@Override
	public boolean addStudentPerformanceGroup(String studentIds, long perGroupId, long csId) throws DataException {
		
		boolean status = false;
		String[] sIds = studentIds.split(",");
		List<PerformanceGroupStudents> perGroupStudents = new ArrayList<>();
		for(String sId: sIds){
			Student student = new Student();
			PerformancetaskGroups ptGroup = new PerformancetaskGroups();
			PerformanceGroupStudents perGroupStudent = new PerformanceGroupStudents();
			student.setStudentId(Long.parseLong(sId));
			ptGroup.setPerformanceGroupId(perGroupId);
			perGroupStudent.setPerformancetaskGroups(ptGroup);
			perGroupStudent.setStudent(student);
			perGroupStudents.add(perGroupStudent);
		}
		
		try {
			status = performanceTaskDAO.addStudentPerformanceGroup(perGroupStudents, csId);
		} catch (SQLDataException e) {
			logger.error("Error in addStudentPerformanceGroup() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in addStudentPerformanceGroup() of PerformanceTaskServiceImpl",e);
		}
		return status;
	}

	@Override
	public PerformancetaskGroups getPerformanceGroupById(long perGroupId)
			throws DataException {
		PerformancetaskGroups perGroup = new PerformancetaskGroups();
		try {
			perGroup = performanceTaskDAO.getPerformanceGroupById(perGroupId);
		} catch (SQLDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return perGroup;
	}

	@Override
	public boolean removeStudentPerformanceGroup(String studentId,
			long perGroupId, long csId) throws DataException {
		boolean status = false;
		Student student = new Student();
		PerformancetaskGroups ptGroup = new PerformancetaskGroups();
		PerformanceGroupStudents perGroupStudent = new PerformanceGroupStudents();
		
		student.setStudentId(Long.parseLong(studentId));
		ptGroup.setPerformanceGroupId(perGroupId);
		perGroupStudent.setPerformancetaskGroups(ptGroup);
		perGroupStudent.setStudent(student);		
		
		try {
			status = performanceTaskDAO.removeStudentPerformanceGroup(perGroupStudent, csId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in removeStudentPerformanceGroup() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in removeStudentPerformanceGroup() of PerformanceTaskServiceImpl",e);
		}
		return status;
	}
	
	public AssignedPtasks getAssignedPerformance(long studentAssignmentId) throws DataException{
		
		AssignedPtasks assignedPtasks = new AssignedPtasks();
		
		try {
			assignedPtasks = performanceTaskDAO.getAssignedPerformance(studentAssignmentId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getAssignedPerformance() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getAssignedPerformance() of PerformanceTaskServiceImpl",e);
		}	
		
		return assignedPtasks;
	} 

	@Override
	public List<Rubric> getRubrics(long performanceTaskId) throws DataException {
		List<Rubric> rubrics = new ArrayList<Rubric>();
		
		try {
			rubrics = performanceTaskDAO.getRubrics(performanceTaskId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getRubrics() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getRubrics() of PerformanceTaskServiceImpl",e);
		}			
		return rubrics;
	}

	@Override
	public boolean submitPerformanceTest(AssignedPtasks assignedPtasks)
			throws DataException {
		boolean status = false;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			assignedPtasks.getRubricValues().setUser(userReg.getUser());
			assignedPtasks.getRubricValues().setAssignedPtasks(assignedPtasks);
			status = performanceTaskDAO.submitPerformanceTest(assignedPtasks);
		}catch(DataException | SQLDataException e){
			logger.error("Error in submitPerformanceTest() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in submitPerformanceTest() of PerformanceTaskServiceImpl",e);
		}					
		return status;
	}

	@Override
	public List<RubricValues> getRubricValuesByAssignedTaskId(long assignedTaskId)
			throws DataException {
		List<RubricValues> rubricValues = new ArrayList<RubricValues>();
		
		try {
			rubricValues = performanceTaskDAO.getRubricValuesByAssignedTaskId(assignedTaskId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getRubricValuesByAssignedTaskId() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getRubricValuesByAssignedTaskId() of PerformanceTaskServiceImpl",e);
		}			
		return rubricValues;
	}
	

	@Override
	public boolean autoSaveAssignedTasks(GroupPerformanceTemp groupPerformanceTemp)
			throws DataException {
		boolean status = false;
		try {
			status = performanceTaskDAO.autoSaveAssignedTasks(groupPerformanceTemp);
		}catch(DataException | SQLDataException e){
			logger.error("Error in autoSaveAssignedTasks() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in autoSaveAssignedTasks() of PerformanceTaskServiceImpl",e);
		}					
		return status;
	}

	@Override
	public boolean autoRubricSave(RubricValues rubricValues)
			throws DataException {
		boolean status = false;
		try {
			status = performanceTaskDAO.autoRubricSave(rubricValues);
		}catch(DataException | SQLDataException e){
			logger.error("Error in autoRubricSave() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in autoRubricSave() of PerformanceTaskServiceImpl",e);
		}					
		return status;
	}

	@Override
	public List<PtaskFiles> getPtaskFiles(long questionId) throws DataException {
		List<PtaskFiles> pTaskFiles = Collections.emptyList();
		
		try {
			pTaskFiles = performanceTaskDAO.getPtaskFiles(questionId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getPtaskFiles() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getPtaskFiles() of PerformanceTaskServiceImpl",e);
		}			
		return pTaskFiles;
	}

	@Override
	public void fileAutoSave(MultipartFile file, AssignedPtasks assignedPtasks, long createdBy)
			throws DataException {
		try {
			performanceTaskDAO.fileAutoSave(file, assignedPtasks, createdBy);
		}catch(DataException | SQLDataException e){
			logger.error("Error in fileAutoSave() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in fileAutoSave() of PerformanceTaskServiceImpl",e);
		}
	}

	@Override
	public void audioAutoSave(String data, AssignedPtasks assignedPtasks,
			long createdBy) throws DataException {
		try {
			performanceTaskDAO.audioAutoSave(data, assignedPtasks, createdBy);
		}catch(DataException | SQLDataException e){
			logger.error("Error in audioAutoSave() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in audioAutoSave() of PerformanceTaskServiceImpl",e);
		}
		
	}

	@Override
	public PerformanceGroupStudents getPerformanceGroupByStudentId(long studentId)
			throws DataException {
		PerformanceGroupStudents pGroupStudent = new PerformanceGroupStudents();
		
		try {
			pGroupStudent = performanceTaskDAO.getPerformanceGroupByStudentId(studentId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getPerformanceGroupByStudentId() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getPerformanceGroupByStudentId() of PerformanceTaskServiceImpl",e);
		}			
		return pGroupStudent;
	}

	@Override
	public List<StudentAssignmentStatus> getGroupTests(long pGroupId, String usedFor, String testStatus, String gradedStatus) throws DataException {
		
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		try {
			studentAssignments = performanceTaskDAO.getGroupTests(pGroupId, usedFor, testStatus, gradedStatus);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getGroupTests() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getGroupTests() of PerformanceTaskServiceImpl",e);
		}			
		return studentAssignments;
	}

	@Override
	@RemoteMethod
	public List<GroupPerformanceTemp> getGroupPerformanceTemp(long assignedTaskId) throws DataException {
		List<GroupPerformanceTemp> gPerStudent = new ArrayList<GroupPerformanceTemp>();
		
		try {
			gPerStudent = performanceTaskDAO.getGroupPerformanceTemp(assignedTaskId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getGroupPerformanceTemp() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getGroupPerformanceTemp() of PerformanceTaskServiceImpl",e);
		}			
		return gPerStudent;
	}

	@Override
	public boolean autoGroupRubricSave(GroupPerformanceTemp gPerTemp)
			throws DataException {
		boolean status = false;
		try {
			status = performanceTaskDAO.autoGroupRubricSave(gPerTemp);
		}catch(DataException | SQLDataException e){
			logger.error("Error in autoGroupRubricSave() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in autoGroupRubricSave() of PerformanceTaskServiceImpl",e);
		}					
		return status;
	}
	
	@Override
	@RemoteMethod
	public boolean submitPermission(long assignedTaskId, long pGroupStudentsId, String status)
			throws DataException {
		boolean locaStatus = false;
		try {			
			locaStatus = performanceTaskDAO.submitPermission(assignedTaskId,pGroupStudentsId, status);
		}catch(DataException | SQLDataException e){
			logger.error("Error in submitPermission() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in submitPermission() of PerformanceTaskServiceImpl",e);
		}					
		return locaStatus;
	}

	@Override
	public void updateAreaStatus(GroupPerformanceTemp gpTemp) throws DataException {
		try {			
			performanceTaskDAO.updateAreaStatus(gpTemp);
		}catch(DataException | SQLDataException e){
			logger.error("Error in updateAreaStatus() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in updateAreaStatus() of PerformanceTaskServiceImpl",e);
		}
	}

	@Override
	@RemoteMethod
	public StudentAssignmentStatus getStudentAssignmentStatusById(
			long studentAssignmentId) throws DataException {
		StudentAssignmentStatus studentAssignment = new StudentAssignmentStatus();
		try {				
			studentAssignment = performanceTaskDAO.getStudentAssignmentStatusById(studentAssignmentId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getStudentAssignmentStatusById() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getStudentAssignmentStatusById() of PerformanceTaskServiceImpl",e);
		}
		return studentAssignment;
	}

	@Override
	public boolean submitGradePerformance(AssignedPtasks assignedPtasks)
			throws DataException {
		boolean status = false;
		AcademicGrades academicGrade = new AcademicGrades();
		try {
			academicGrade =  gradeAssessmentsDao.getAcademicGradeByPercentage(assignedPtasks.getStudentAssignmentStatus().getPercentage());
			assignedPtasks.getStudentAssignmentStatus().setAcademicGrade(academicGrade);
			status = performanceTaskDAO.submitGradePerformance(assignedPtasks);
		}catch(DataException | SQLDataException e){
			logger.error("Error in submitGradePerformance() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in submitGradePerformance() of PerformanceTaskServiceImpl",e);
		}					
		return status;
	}
	 
	@Override
	public List<StudentAssignmentStatus> getGroupAssessmentTests(long assignmentId) throws DataException {
		List<StudentAssignmentStatus> groupAssignments = new ArrayList<StudentAssignmentStatus>();		
		try {
			groupAssignments = performanceTaskDAO.getGroupAssessmentTests(assignmentId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getGroupAssessmentTests() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getGroupAssessmentTests() of PerformanceTaskServiceImpl",e);
		}			
		return groupAssignments;
	}

	@Override
	public void tinynceFileSave(MultipartFile file,	AssignedPtasks assignedPtasks, long createdBy) throws DataException {
		UserRegistration userReg = userRegistrationDAO.getUserRegistration(createdBy);
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
		String pTaskFileFullPath = uploadFilePath + File.separator+ WebKeys.ASSIGNMENTS +File.separator+ WebKeys.PERFORMANCE_TESTS
					+File.separator+assignedPtasks.getAssignedTaskId() +File.separator+WebKeys.TINYMCE_IMAGES;
		//Create dir if doesn't exist
		File f = new File(pTaskFileFullPath);
		if (!f.isDirectory()) {
			 f.setExecutable(true, false);
             f.mkdirs();
        } 
		File[] files = f.listFiles();
		boolean fileExists = false;
		if(files.length > 0){
			for(File subFile:files){
				String name = subFile.getName();
				if(name == file.getOriginalFilename()){
					fileExists = true;
					break;
				}
				//subFile.delete();
			}
		}
		if(!fileExists){
			pTaskFileFullPath = pTaskFileFullPath+File.separator+file.getOriginalFilename();
	  		try {
	  			 byte[] bis =  file.getBytes();
	  			 FileOutputStream fs = new FileOutputStream(pTaskFileFullPath);
	             BufferedOutputStream bs = new BufferedOutputStream(fs);
	             bs.write(bis);
	             bs.close();
	             fs.close();
	        } catch (Exception e) {
	             e.printStackTrace();
	        }
		}
	}
	
	@Override
	@RemoteMethod
	public boolean verifyGroupName(long csId, String groupName) throws DataException {
		boolean status = false;
		try {				
			status = performanceTaskDAO.verifyPerformanceGroupNameExists(csId,groupName);
		}catch(DataException | SQLDataException e){
			logger.error("Error in verifyGroupName() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in verifyGroupName() of PerformanceTaskServiceImpl",e);
		}
		return status;
	}

	@Override
	public AssignedPtasks getCompletedPerformanceTask(long studentAssignmentId)
			throws DataException {
AssignedPtasks assignedPtasks = new AssignedPtasks();
		
		try {
			assignedPtasks = performanceTaskDAO.getCompletedPerformanceTask(studentAssignmentId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getCompletedPerformanceTask() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getCompletedPerformanceTask() of PerformanceTaskServiceImpl",e);
		}	
		
		return assignedPtasks;
	}
	@Override
	@RemoteMethod
	public List<StudentPtaskEvidence> getStudentEvidencesByAssignedTaskId(long assignedTaskId)
			throws DataException {
		List<StudentPtaskEvidence> studentEvidences = new ArrayList<StudentPtaskEvidence>();
		
		try {
			studentEvidences = performanceTaskDAO.getStudentEvidencesByAssignedTaskId(assignedTaskId);
		}catch(Exception e){
			logger.error("Error in getRubricValuesByAssignedTaskId() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getStudentEvidencesByAssignedTaskId() of PerformanceTaskServiceImpl",e);
		}			
		return studentEvidences;
	}
	 public AssignedPtasks getAssignedPtasks(long assignedPtaskId) throws DataException{
		
		AssignedPtasks assignedPtasks = new AssignedPtasks();
		
		try {
			assignedPtasks = performanceTaskDAO.getAssignedPtasks(assignedPtaskId);
			
		}catch(DataException e){
			logger.error("Error in getAssignedPtasks() of  PerformanceTaskServiceImpl"+ e);
			throw new DataException("Error in getAssignedPtasks() of PerformanceTaskServiceImpl",e);
		}	
		
		return assignedPtasks;
	}
	 public StudentPtaskEvidence getStudentEvidencesByStudentEvidenceId(long evidenceId){
		 StudentPtaskEvidence studentEvidence = new StudentPtaskEvidence();
			
			try {
				studentEvidence = performanceTaskDAO.getStudentEvidencesByStudentEvidenceId(evidenceId);
				
			}catch(DataException e){
				logger.error("Error in getStudentEvidencesByStudentEvidenceId() of  PerformanceTaskServiceImpl"+ e);
				throw new DataException("Error in getStudentEvidencesByStudentEvidenceId() of PerformanceTaskServiceImpl",e);
			}	
			
			return studentEvidence;
	 }
	 public long saveStudentEvidences(StudentPtaskEvidence stuEvid){
		
		 try {
				return performanceTaskDAO.saveStudentEvidence(stuEvid);
				
			}catch(DataException e){
				logger.error("Error in saveStudentEvidences() of  PerformanceTaskServiceImpl"+ e);
				throw new DataException("Error in saveStudentEvidences() of PerformanceTaskServiceImpl",e);
			}	
	 }
	 public void updateStudentPtaskEvidence(long assignedPtaskId,long evidenceId,String evidence,long ptaskGroupStudentId){
		 try {
			    if(ptaskGroupStudentId==0)
				performanceTaskDAO.updateStudentPtaskEvidence(assignedPtaskId,evidenceId,evidence);
			    else
			    performanceTaskDAO.updateGroupStudentPtaskEvidences(assignedPtaskId,evidenceId,evidence,ptaskGroupStudentId);
				
			}catch(DataException e){
				logger.error("Error in updateStudentPtaskEvidence() of  PerformanceTaskServiceImpl"+ e);
				throw new DataException("Error in updateStudentPtaskEvidence() of PerformanceTaskServiceImpl",e);
			}	
	 }
	 public PerformanceGroupStudents getPerformanceGroupStudentsById(long ptaskGroupStudentId)throws SQLException{
		 try {
			 return performanceTaskDAO.getPerformanceGroupStudentById(ptaskGroupStudentId);
				
			}catch(DataException e){
				logger.error("Error in getPerformanceGroupStudentsById() of  PerformanceTaskServiceImpl"+ e);
				throw new DataException("Error in getPerformanceGroupStudentsById() of PerformanceTaskServiceImpl",e);
			}	 
	 }
		@Override
		 public boolean gradePerformanceTasks(long assignPTaskId,float percentage,String teacherComment,int teacherScore){
			boolean status = false;
			AcademicGrades academicGrade = new AcademicGrades();
			AssignedPtasks assignedPTask=new AssignedPtasks();
			try {
				academicGrade =  gradeAssessmentsDao.getAcademicGradeByPercentage(percentage);
				assignedPTask=performanceTaskDAO.getAssignedPtasks(assignPTaskId);
				assignedPTask.setTeacherComments(teacherComment);
				assignedPTask.setTeacherScore(teacherScore);
				assignedPTask.getStudentAssignmentStatus().setAcademicGrade(academicGrade);
				assignedPTask.getStudentAssignmentStatus().setPercentage(percentage);
				status = performanceTaskDAO.submitGradePerformance(assignedPTask);
			}catch(DataException | SQLDataException e){
				logger.error("Error in submitGradePerformance() of  PerformanceTaskServiceImpl"+ e);
				throw new DataException("Error in submitGradePerformance() of PerformanceTaskServiceImpl",e);
			}					
			return status;
		}
	
	}