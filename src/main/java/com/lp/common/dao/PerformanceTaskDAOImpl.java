package com.lp.common.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.lp.appadmin.service.SchoolAdminService;
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
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Repository("Performance")
public class PerformanceTaskDAOImpl extends CustomHibernateDaoSupport implements PerformanceTaskDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	@Autowired
	private SchoolAdminService schoolAdminService;	
		
	@Override
	public void createPerformanceGroup(PerformancetaskGroups performancetaskGroups)
			throws SQLDataException {
		
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(performancetaskGroups);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in createPerformanceGroup() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in createPerformanceGroup() of PerformanceTaskDAOImpl",e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PerformancetaskGroups> getPerformanceGroups(long csId)	throws SQLDataException {
		
		List<PerformancetaskGroups> performancetaskGroups = Collections.emptyList();
		try{
			performancetaskGroups = (List<PerformancetaskGroups>) getHibernateTemplate().find("from PerformancetaskGroups where classStatus.csId="+csId+"");
		}catch (HibernateException e) {
			logger.error("Error in getPerformanceGroups() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getPerformanceGroups() of PerformanceTaskDAOImpl",e);
		}
		return performancetaskGroups;
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean addStudentPerformanceGroup(List<PerformanceGroupStudents> perGroupStudents, long csId)
			throws SQLDataException {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for(PerformanceGroupStudents pgs : perGroupStudents) {
				session.saveOrUpdate(pgs);
				
				String hql = "UPDATE RegisterForClass set performancetaskGroups.performanceGroupId = :performanceGroupId "
						+ "WHERE classStatus.csId = :csId and student.studentId = :studentId";
				Query query = session.createQuery(hql);
				query.setParameter("performanceGroupId", pgs.getPerformancetaskGroups().getPerformanceGroupId());
				query.setParameter("csId", csId);
				query.setParameter("studentId", pgs.getStudent().getStudentId());
				query.executeUpdate();
			}		
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in addStudentPerformanceGroup() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in addStudentPerformanceGroup() of PerformanceTaskDAOImpl",e);
		}
		return true;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public PerformancetaskGroups getPerformanceGroupById(long perGroupId)
			throws SQLDataException {
		List<PerformancetaskGroups> performancetaskGroups = Collections.emptyList();
		try{
			performancetaskGroups = (List<PerformancetaskGroups>) getHibernateTemplate().find("from PerformancetaskGroups where performanceGroupId="+perGroupId+"");
		}catch (HibernateException e) {
			logger.error("Error in getPerformanceGroupById() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getPerformanceGroupById() of PerformanceTaskDAOImpl",e);
		}
		if(performancetaskGroups.size()>0){
			return performancetaskGroups.get(0);
		}else{
			return new PerformancetaskGroups();
		}		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean removeStudentPerformanceGroup(
			PerformanceGroupStudents perGroupStudent, long csId)
			throws SQLDataException {		
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "UPDATE RegisterForClass set performancetaskGroups.performanceGroupId = :performanceGroupId "
					+ "WHERE classStatus.csId = :csId and student.studentId = :studentId";
			Query query = session.createQuery(hql);
			query.setParameter("performanceGroupId", null);
			query.setParameter("csId", csId);
			query.setParameter("studentId", perGroupStudent.getStudent().getStudentId());
			query.executeUpdate();
			
			hql = "DELETE from PerformanceGroupStudents WHERE performancetaskGroups.performanceGroupId = :performanceGroupId and student.studentId = :studentId";
			query = session.createQuery(hql);
			query.setParameter("performanceGroupId", perGroupStudent.getPerformancetaskGroups().getPerformanceGroupId());
			query.setParameter("studentId", perGroupStudent.getStudent().getStudentId());
		    query.executeUpdate();		
			
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in removeStudentPerformanceGroup() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in removeStudentPerformanceGroup() of PerformanceTaskDAOImpl",e);
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AssignedPtasks getAssignedPerformance(long studentAssignmentId) throws SQLDataException {
		List<AssignedPtasks> assignedPtasks = new ArrayList<AssignedPtasks>();
		try {
			assignedPtasks = (List<AssignedPtasks>) getHibernateTemplate()
					.find("From AssignedPtasks where studentAssignmentStatus.studentAssignmentId="
							+ studentAssignmentId+" and studentAssignmentStatus.status != 'submitted'");			
			
		} catch (HibernateException e) {
			logger.error("Error in getAssignedPerformance() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getAssignedPerformance() of PerformanceTaskDAOImpl",e);
		}
		if(assignedPtasks.size()>0){
			return assignedPtasks.get(0);
		}else{
			return new AssignedPtasks();
		}
		
	} 

	@SuppressWarnings("unchecked")
	@Override
	public List<Rubric> getRubrics(long performanceTaskId) throws SQLDataException {
		List<Rubric> rubrics = new ArrayList<Rubric>();
		try {
			rubrics = (List<Rubric>) getHibernateTemplate()
					.find("From Rubric where questions.questionId="
							+ performanceTaskId);
		} catch (HibernateException e) {
			logger.error("Error in getRubrics() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getRubrics() of PerformanceTaskDAOImpl",e);
		}
		return rubrics;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean submitPerformanceTest(AssignedPtasks assignedPtasks)
			throws SQLDataException {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();	
			String hql = "UPDATE StudentAssignmentStatus set status = :status, submitdate=current_date WHERE studentAssignmentId = :studentAssignmentId";
			Query query = session.createQuery(hql);
			query.setParameter("status","submitted");
			query.setParameter("studentAssignmentId",assignedPtasks.getStudentAssignmentStatus().getStudentAssignmentId());
			query.executeUpdate();
			
			hql = "UPDATE AssignedPtasks set selfAssessmentScore = :selfAssessmentScore WHERE assignedTaskId = :assignedTaskId";
			query = session.createQuery(hql);
			query.setParameter("selfAssessmentScore",assignedPtasks.getRubricValues().getTotalScore());
			query.setParameter("assignedTaskId",assignedPtasks.getAssignedTaskId());
			query.executeUpdate();	
			
			if(assignedPtasks.getRubricValues().getRubricValuesId() == 0){
				session.saveOrUpdate(assignedPtasks.getRubricValues());
			}			
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in submitPerformanceTest() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in submitPerformanceTest() of PerformanceTaskDAOImpl",e);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RubricValues> getRubricValuesByAssignedTaskId(long assignedTaskId)
			throws SQLDataException {
		List<RubricValues> rubricValues = new ArrayList<RubricValues>();
		try {
			rubricValues = (List<RubricValues>) getHibernateTemplate()
					.find("From RubricValues where assignedPtasks.assignedTaskId="
							+ assignedTaskId);
		} catch (HibernateException e) {
			logger.error("Error in getRubricValuesByAssignedTaskId() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getRubricValuesByAssignedTaskId() of PerformanceTaskDAOImpl",e);
		}
		return rubricValues;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean autoSaveAssignedTasks(GroupPerformanceTemp groupPerformanceTemp)
			throws SQLDataException {
		try{
			AssignedPtasks assignedPtasks = groupPerformanceTemp.getAssignedPtasks();
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "";
			Query query;
			if(assignedPtasks.getWriting() != null){
				hql = "UPDATE AssignedPtasks set writing = :writing WHERE assignedTaskId = :assignedTaskId and selfAssessmentScore is null";
				query = session.createQuery(hql);
				query.setParameter("writing", assignedPtasks.getWriting());
			}else if(assignedPtasks.getCalculations() != null){
				hql = "UPDATE AssignedPtasks set calculations = :calculations WHERE assignedTaskId = :assignedTaskId and selfAssessmentScore is null";
				query = session.createQuery(hql);
				query.setParameter("calculations", assignedPtasks.getCalculations());
			}else{
				hql = "UPDATE AssignedPtasks set uploadarea = :uploadarea WHERE assignedTaskId = :assignedTaskId  and selfAssessmentScore is null";
				query = session.createQuery(hql);
				query.setParameter("uploadarea", assignedPtasks.getUploadarea());
			}			
			
			query.setParameter("assignedTaskId", assignedPtasks.getAssignedTaskId());
			query.executeUpdate();
			
			if(groupPerformanceTemp.getPerformanceGroupStudents().getPerformanceGroupStudentsId() > 0){
				String unlocked = "unlocked";
				String logout = "";
				if(groupPerformanceTemp.getChatLoginStatus() != null && groupPerformanceTemp.getChatLoginStatus().equalsIgnoreCase("logout")){
					logout = ",chatLoginStatus = :chatLoginStatus ";
				}
				hql = logout+"WHERE performanceGroupStudents.performanceGroupStudentsId = :performanceGroupStudentsId and assignedPtasks.assignedTaskId = :assignedTaskId";
				if(groupPerformanceTemp.getWritingAreaStatus() != null){
					hql = "UPDATE GroupPerformanceTemp set writingAreaStatus = :writingAreaStatus "+hql;
					query = session.createQuery(hql);
					query.setParameter("writingAreaStatus", unlocked);
				}else if(groupPerformanceTemp.getCalculationAreaStatus() != null){
					hql = "UPDATE GroupPerformanceTemp set calculationAreaStatus = :calculationAreaStatus "+hql;
					query = session.createQuery(hql);
					query.setParameter("calculationAreaStatus", unlocked);
				}else{
					hql = "UPDATE GroupPerformanceTemp set imageAreaStatus = :imageAreaStatus "+hql;
					query = session.createQuery(hql);
					query.setParameter("imageAreaStatus", unlocked);
				}
				query.setParameter("performanceGroupStudentsId", groupPerformanceTemp.getPerformanceGroupStudents().getPerformanceGroupStudentsId());
				query.setParameter("assignedTaskId", assignedPtasks.getAssignedTaskId());
				if(logout!=""){
					query.setParameter("chatLoginStatus", groupPerformanceTemp.getChatLoginStatus());
				}
				query.executeUpdate();
			}
			
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in autoSaveAssignedTasks() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in autoSaveAssignedTasks() of PerformanceTaskDAOImpl",e);
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean autoRubricSave(RubricValues rubricValues)
			throws SQLDataException {
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			if(rubricValues.getRubricValuesId()>0){
				String hql = "";
				Query query;
				if(rubricValues.getDimension1Value() > 0){
					hql = "UPDATE RubricValues set dimension1Value = :dimension1Value,totalScore = :totalScore WHERE rubricValuesId = :rubricValuesId";
					query = session.createQuery(hql);
					query.setParameter("dimension1Value", rubricValues.getDimension1Value());
				}else if(rubricValues.getDimension2Value() > 0){
					hql = "UPDATE RubricValues set dimension2Value = :dimension2Value,totalScore = :totalScore WHERE rubricValuesId = :rubricValuesId";
					query = session.createQuery(hql);
					query.setParameter("dimension2Value", rubricValues.getDimension2Value());
				}else if(rubricValues.getDimension3Value() > 0){
					hql = "UPDATE RubricValues set dimension3Value = :dimension3Value,totalScore = :totalScore WHERE rubricValuesId = :rubricValuesId";
					query = session.createQuery(hql);
					query.setParameter("dimension3Value", rubricValues.getDimension3Value());
				}else{
					hql = "UPDATE RubricValues set dimension4Value = :dimension4Value,totalScore = :totalScore WHERE rubricValuesId = :rubricValuesId";
					query = session.createQuery(hql);
					query.setParameter("dimension4Value", rubricValues.getDimension4Value());
				}			
				
				query.setParameter("totalScore", rubricValues.getTotalScore());
				query.setParameter("rubricValuesId", rubricValues.getRubricValuesId());
				query.executeUpdate();
			}else{
				session.saveOrUpdate(rubricValues);
			}
			
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in autoRubricSave() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in autoRubricSave() of PerformanceTaskDAOImpl",e);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PtaskFiles> getPtaskFiles(long questionId)
			throws SQLDataException {
		List<PtaskFiles> pTaskFiles = new ArrayList<PtaskFiles>();
		try {
			pTaskFiles = (List<PtaskFiles>) getHibernateTemplate().find("From PtaskFiles where questions.questionId="+ questionId);
		} catch (HibernateException e) {
			logger.error("Error in getPtaskFiles() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getPtaskFiles() of PerformanceTaskDAOImpl",e);
		}
		return pTaskFiles;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void fileAutoSave(MultipartFile file, AssignedPtasks assignedPtasks, long createdBy)
			throws SQLDataException {
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "UPDATE AssignedPtasks set filepath = :filepath WHERE assignedTaskId = :assignedTaskId";
			Query query = session.createQuery(hql);	
			query.setParameter("filepath", assignedPtasks.getFilepath());
			query.setParameter("assignedTaskId", assignedPtasks.getAssignedTaskId());
			query.executeUpdate();
			saveFileInFileSystem(file, assignedPtasks.getAssignedTaskId(), createdBy);
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in fileAutoSave() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in fileAutoSave() of PerformanceTaskDAOImpl",e);
		}
		
	}

	private void saveFileInFileSystem(MultipartFile file, long assignmentPtaskId, long createdBy) {
		UserRegistration userReg = schoolAdminService.getUserRegistration(createdBy);
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
		String pTaskFileFullPath = uploadFilePath + File.separator+ WebKeys.ASSIGNMENTS +File.separator+ WebKeys.PERFORMANCE_TESTS
					+File.separator+assignmentPtaskId +File.separator+WebKeys.ADDITIONAL_MEDIA;
		//Create dir if doesn't exist
		File f = new File(pTaskFileFullPath);
		if (!f.isDirectory()) {
			 f.setExecutable(true, false);
             f.mkdirs();
        } 
		File[] files = f.listFiles();
		if(files.length > 0){
			for(File subFile:files){
				subFile.delete();
			}
		}
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

	@SuppressWarnings("rawtypes")
	@Override
	public void audioAutoSave(String data, AssignedPtasks assignedPtasks,
			long createdBy) throws SQLDataException {
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "UPDATE AssignedPtasks set audiofile = :audiofile WHERE assignedTaskId = :assignedTaskId";
			Query query = session.createQuery(hql);	
			query.setParameter("audiofile", assignedPtasks.getAudiofile());
			query.setParameter("assignedTaskId", assignedPtasks.getAssignedTaskId());
			query.executeUpdate();
			saveAudioInFileSystem(data, assignedPtasks.getAssignedTaskId(), createdBy);
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in audioAutoSave() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in audioAutoSave() of PerformanceTaskDAOImpl",e);
		}
		
	}

	private void saveAudioInFileSystem(String data, long assignedPtaskId,
			long createdBy) {
		UserRegistration userReg = schoolAdminService.getUserRegistration(createdBy);
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
		String pTaskFileFullPath = uploadFilePath + File.separator+ WebKeys.ASSIGNMENTS +File.separator+ WebKeys.PERFORMANCE_TESTS
					+File.separator+assignedPtaskId +File.separator+WebKeys.AUDIO;
		//Create dir if doesn't exist
		File f = new File(pTaskFileFullPath);
		if (!f.isDirectory()) {
			 f.setExecutable(true, false);
             f.mkdirs();
        } 
		File[] files = f.listFiles();
		if(files.length > 0){
			for(File subFile:files){
				subFile.delete();
			}
		}
		pTaskFileFullPath = pTaskFileFullPath+File.separator+WebKeys.AUDIO_FILE_NAME;
  		try {
  			 byte[] bis = Base64.decodeBase64(data);
  			 FileOutputStream fs = new FileOutputStream(pTaskFileFullPath);
             BufferedOutputStream bs = new BufferedOutputStream(fs);
             bs.write(bis);
             bs.close();
             fs.close();
        } catch (Exception e) {
        	logger.error("Error in saveAudioInFileSystem() of PerformanceTaskDAOImpl" + e);
             e.printStackTrace();
        }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public PerformanceGroupStudents getPerformanceGroupByStudentId(long studentId)
			throws SQLDataException {
		List<PerformanceGroupStudents> pGroupStudent = new ArrayList<PerformanceGroupStudents>();
		try {
			pGroupStudent = (List<PerformanceGroupStudents>) getHibernateTemplate()
					.find("From PerformanceGroupStudents where student.studentId="+ studentId);
		} catch (HibernateException e) {
			logger.error("Error in getPerformanceGroupByStudentId() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getPerformanceGroupByStudentId() of PerformanceTaskDAOImpl",e);
		}
		if(pGroupStudent.size()>0){
			return pGroupStudent.get(0);
		}else{
			return new PerformanceGroupStudents();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getGroupTests(long pGroupId, String usedFor, String testStatus, String gradedStatus)
			throws SQLDataException {
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		try {
			studentAssignments = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where performanceGroup.performanceGroupId = "
							+ pGroupId
							+ " and status = '"
							+ testStatus
							+ "' and gradedStatus = '"
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ WebKeys.ASSIGN_STATUS_ACTIVE
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "'");
		} catch (HibernateException e) {
			logger.error("Error in getGroupTests() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getGroupTests() of PerformanceTaskDAOImpl",e);
		}
		return studentAssignments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupPerformanceTemp> getGroupPerformanceTemp(long assignedTaskId) throws SQLDataException {
		List<GroupPerformanceTemp> gPerStudent = new ArrayList<GroupPerformanceTemp>();
		try {
			gPerStudent = (List<GroupPerformanceTemp>) getHibernateTemplate()
					.find("From GroupPerformanceTemp where assignedPtasks.assignedTaskId="+ assignedTaskId);
		} catch (HibernateException e) {
			logger.error("Error in getGroupPerformanceTemp() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getGroupPerformanceTemp() of PerformanceTaskDAOImpl",e);
		}
		return gPerStudent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PerformanceGroupStudents> getPerformanceGroupStudents(
			long performanceGroupId) throws DataException {
		// TODO Auto-generated method stub
		List<PerformanceGroupStudents> pgStudents = new ArrayList<PerformanceGroupStudents>();
		try {
			pgStudents = (List<PerformanceGroupStudents>) getHibernateTemplate().find(
					"from PerformanceGroupStudents where performancetaskGroups.performanceGroupId=" + performanceGroupId);
		} catch (DataAccessException e) {
			logger.error("Error in getPerformanceGroupStudents() of WebChatDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException("Error in getPerformanceGroupStudents() of PerformanceTaskDAOImpl",e);
		}
		if(pgStudents.size()>0){
				return pgStudents;
		}
		else{
			logger.error("Performance group student  doesn't exist getPerformanceGroupStudents() of PerformanceTaskDAOImpl");
			throw new DataException("Error in getPerformanceGroupStudents() of PerformanceTaskDAOImpl");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getRegisteredPerGroups() throws DataException {
		List<RegisterForClass> rfc = new ArrayList<RegisterForClass>();
		try {
			rfc = (List<RegisterForClass>) getHibernateTemplate()
					.find("From RegisterForClass where performancetaskGroups.performanceGroupId!=null or performancetaskGroups.performanceGroupId!=''");
		} catch (HibernateException e) {
			logger.error("Error in getGroupPerformanceTemp() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getGroupPerformanceTemp() of PerformanceTaskDAOImpl",e);
		}
		return rfc;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean autoGroupRubricSave(GroupPerformanceTemp gPerTemp)
			throws SQLDataException {
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "WHERE performanceGroupStudents.performanceGroupStudentsId = :performanceGroupStudentsId and assignedPtasks.assignedTaskId = :assignedTaskId";
			Query query;
			if(gPerTemp.getDim1Value() != null){
				hql = "UPDATE GroupPerformanceTemp set dim1Value = :dim1Value,total = :total "+hql;
				query = session.createQuery(hql);
				query.setParameter("dim1Value", gPerTemp.getDim1Value());
			}else if(gPerTemp.getDim2Value() != null){
				hql = "UPDATE GroupPerformanceTemp set dim2Value = :dim2Value,total = :total "+hql;
				query = session.createQuery(hql);
				query.setParameter("dim2Value", gPerTemp.getDim2Value());
			}else if(gPerTemp.getDim3Value() != null){
				hql = "UPDATE GroupPerformanceTemp set dim3Value = :dim3Value,total = :total "+hql;
				query = session.createQuery(hql);
				query.setParameter("dim3Value", gPerTemp.getDim3Value());
			}else{
				hql = "UPDATE GroupPerformanceTemp set dim4Value = :dim4Value,total = :total "+hql;
				query = session.createQuery(hql);
				query.setParameter("dim4Value", gPerTemp.getDim4Value());
			}	
			
				
			query.setParameter("total", gPerTemp.getTotal());
			query.setParameter("performanceGroupStudentsId", gPerTemp.getPerformanceGroupStudents().getPerformanceGroupStudentsId());
			query.setParameter("assignedTaskId", gPerTemp.getAssignedPtasks().getAssignedTaskId());
			query.executeUpdate();			
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in autoRubricSave() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in autoRubricSave() of PerformanceTaskDAOImpl",e);
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean submitPermission(long assignedTaskId, long pGroupStudentsId, String status)
			throws SQLDataException {
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "UPDATE GroupPerformanceTemp set permissionStatus = :permissionStatus WHERE "
					+ "performanceGroupStudents.performanceGroupStudentsId = :performanceGroupStudentsId and assignedPtasks.assignedTaskId = :assignedTaskId";
			Query query = session.createQuery(hql);
			query.setParameter("permissionStatus", status);
			query.setParameter("performanceGroupStudentsId", pGroupStudentsId);
			query.setParameter("assignedTaskId", assignedTaskId);
			query.executeUpdate();			
			tx.commit();
			session.close();
			
		}catch (HibernateException e) {
			logger.error("Error in submitPermission() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in submitPermission() of PerformanceTaskDAOImpl",e);
		}
		return true;
	}

	@Override
	public void updateAreaStatus(GroupPerformanceTemp gpTemp) throws SQLDataException {
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(gpTemp);			
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in updateAreaStatus() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in updateAreaStatus() of PerformanceTaskDAOImpl",e);
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public StudentAssignmentStatus getStudentAssignmentStatusById(long studentAssignmentId)  throws SQLDataException {
		List<StudentAssignmentStatus> StudentAssignmentList = Collections.emptyList();
		try {
			StudentAssignmentList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where studentAssignmentId = "
							+ studentAssignmentId);
		} catch (HibernateException e) {
			logger.error("Error in getStudentAssignmentStatusById() of PerformanceTaskDAOImpl", e);
		}
		if(StudentAssignmentList.size()>0){
			return StudentAssignmentList.get(0);
		}else{
			return new StudentAssignmentStatus();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean submitGradePerformance(AssignedPtasks assignedPtasks)
			throws SQLDataException {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			
			String hql = "UPDATE StudentAssignmentStatus set percentage = :percentage, gradedStatus=:gradedStatus, academicGrade.acedamicGradeId=:acedamicGradeId WHERE studentAssignmentId = :studentAssignmentId";
			Query query = session.createQuery(hql);
			query.setParameter("percentage",assignedPtasks.getStudentAssignmentStatus().getPercentage());
			query.setParameter("gradedStatus",WebKeys.GRADED_STATUS_GRADED);
			query.setParameter("acedamicGradeId",assignedPtasks.getStudentAssignmentStatus().getAcademicGrade().getAcedamicGradeId());
			query.setParameter("studentAssignmentId",assignedPtasks.getStudentAssignmentStatus().getStudentAssignmentId());
			query.executeUpdate();
			
			hql = "UPDATE AssignedPtasks set teacherComments = :teacherComments, teacherScore=:teacherScore WHERE assignedTaskId = :assignedTaskId";
			query = session.createQuery(hql);
			query.setParameter("teacherComments",assignedPtasks.getTeacherComments());
			query.setParameter("teacherScore",assignedPtasks.getTeacherScore());
			query.setParameter("assignedTaskId",assignedPtasks.getAssignedTaskId());
			query.executeUpdate();
			
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in submitGradePerformance() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in submitGradePerformance() of PerformanceTaskDAOImpl",e);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getGroupAssessmentTests(long assignmentId) throws SQLDataException {
		List<StudentAssignmentStatus> assignmentList = new ArrayList<StudentAssignmentStatus>();
		try {	
			assignmentList = (List<StudentAssignmentStatus>) getHibernateTemplate().find(
					"from StudentAssignmentStatus where assignment.assignStatus='"+WebKeys.ACTIVE+"' and "
					+ "assignment.assignmentId="+assignmentId+" and performanceGroup.performanceGroupId <> Null");
			
		}catch (HibernateException e) {
			logger.error("Error in getGroupAssessmentTests() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getGroupAssessmentTests() of PerformanceTaskDAOImpl",e);
		}
		return assignmentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyPerformanceGroupNameExists(long csId, String groupName)
			throws SQLDataException {
		List<PerformancetaskGroups> performancetaskGroups = new ArrayList<PerformancetaskGroups>();
		try {			
			performancetaskGroups = (List<PerformancetaskGroups>) getHibernateTemplate().find(
					"from PerformancetaskGroups where classStatus.csId="+csId+" and groupName='"+groupName+"'");		
		}catch (HibernateException e) {
			logger.error("Error in verifyPerformanceGroupNameExists() of PerformanceTaskDAOImpl" + e);
			throw new DataException("Error in verifyPerformanceGroupNameExists() of PerformanceTaskDAOImpl",e);
		}
		if(performancetaskGroups.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AssignedPtasks getCompletedPerformanceTask(long studentAssignmentId)
			throws SQLDataException {
		List<AssignedPtasks> assignedPtasks = new ArrayList<AssignedPtasks>();
		try {
			assignedPtasks = (List<AssignedPtasks>) getHibernateTemplate()
					.find("From AssignedPtasks where studentAssignmentStatus.studentAssignmentId="
							+ studentAssignmentId+" and studentAssignmentStatus.status = 'submitted'");			
			
		} catch (HibernateException e) {
			logger.error("Error in getCompletedPerformanceTask() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getCompletedPerformanceTask() of PerformanceTaskDAOImpl",e);
		}
		if(assignedPtasks.size()>0){
			return assignedPtasks.get(0);
		}else{
			return new AssignedPtasks();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentPtaskEvidence> getStudentEvidencesByAssignedTaskId(long assignedTaskId)throws DataException {
		List<StudentPtaskEvidence> studentEvidences = new ArrayList<StudentPtaskEvidence>();
		try {
			studentEvidences = (List<StudentPtaskEvidence>) getHibernateTemplate()
					.find("From StudentPtaskEvidence where assignedPtasks.assignedTaskId="
							+ assignedTaskId);
		} catch (HibernateException e) {
			logger.error("Error in getStudentEvidencesByAssignedTaskId() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getStudentEvidencesByAssignedTaskId() of PerformanceTaskDAOImpl",e);
		}
		return studentEvidences;
	}
	@SuppressWarnings("unchecked")
	@Override
	public AssignedPtasks getAssignedPtasks(long assignedPtaskId) throws DataException {
		List<AssignedPtasks> assignedPtasks = new ArrayList<AssignedPtasks>();
		try {
			assignedPtasks = (List<AssignedPtasks>) getHibernateTemplate()
					.find("From AssignedPtasks where assignedTaskId="+assignedPtaskId+"");			
			
		} catch (HibernateException e) {
			logger.error("Error in getAssignedPtasks() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getAssignedPtasks() of PerformanceTaskDAOImpl",e);
		}
		if(assignedPtasks.size()>0){
			return assignedPtasks.get(0);
		}else{
			return new AssignedPtasks();
		}
		
	} 
	@SuppressWarnings("unchecked")
	@Override
	 public StudentPtaskEvidence getStudentEvidencesByStudentEvidenceId(long evidenceId){
		 List<StudentPtaskEvidence> studentEvidences = new ArrayList<StudentPtaskEvidence>();
			try {
				studentEvidences = (List<StudentPtaskEvidence>) getHibernateTemplate()
						.find("From StudentPtaskEvidence where studentPtaskEvidenceId="+evidenceId+"");			
				
			} catch (HibernateException e) {
				logger.error("Error in getStudentEvidencesByStudentEvidenceId() of PerformanceTaskDAOImpl", e);
				throw new DataException("Error in getStudentEvidencesByStudentEvidenceId() of PerformanceTaskDAOImpl",e);
			}
			if(studentEvidences.size()>0){
				return studentEvidences.get(0);
			}else{
				return new StudentPtaskEvidence();
			}
	 }
	@Override
	public long saveStudentEvidence(StudentPtaskEvidence studentevid) {
	try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(studentevid);			
			tx.commit();
			session.close();
			
		} catch (HibernateException e) {
			logger.error("Error in saveStudentEvidence() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
		}
	return studentevid.getStudentPtaskEvidenceId();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void updateStudentPtaskEvidence(long assignedPtaskId,long evidenceId,String evidence){
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try{
		String hql = "UPDATE StudentPtaskEvidence set evidenceDesc = :evidence WHERE studentPtaskEvidenceId = :evidenceId and assignedPtasks.assignedTaskId= :assignedPtaskId";
		Query query = session.createQuery(hql);
		query.setParameter("evidence",evidence);
		query.setParameter("evidenceId",evidenceId);
		query.setParameter("assignedPtaskId",assignedPtaskId);
		query.executeUpdate();
				
		tx.commit();
		session.close();
		}catch(HibernateException e){
			logger.error("Error in updateStudentPtaskEvidence() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public PerformanceGroupStudents getPerformanceGroupStudentById(long ptaskGroupStudentId)throws SQLDataException {
		List<PerformanceGroupStudents> pGroupStudent = new ArrayList<PerformanceGroupStudents>();
		try {
			pGroupStudent = (List<PerformanceGroupStudents>) getHibernateTemplate()
					.find("From PerformanceGroupStudents where performanceGroupStudentsId="+ ptaskGroupStudentId);
		} catch (HibernateException e) {
			logger.error("Error in getPerformanceGroupByStudentId() of PerformanceTaskDAOImpl", e);
			throw new DataException("Error in getPerformanceGroupByStudentId() of PerformanceTaskDAOImpl",e);
		}
		if(pGroupStudent.size()>0){
			return pGroupStudent.get(0);
		}else{
			return new PerformanceGroupStudents();
		}
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void updateGroupStudentPtaskEvidences(long assignedPtaskId,long evidenceId,String evidence,long ptaskGroupStudentId){
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try{
		String hql = "UPDATE StudentPtaskEvidence set evidenceDesc = :evidence WHERE studentPtaskEvidenceId = :evidenceId and assignedPtasks.assignedTaskId= :assignedPtaskId "
				+ "and performanceGroupStudents.performanceGroupStudentsId=:ptaskGroupStuId ";
		Query query = session.createQuery(hql);
		query.setParameter("evidence",evidence);
		query.setParameter("evidenceId",evidenceId);
		query.setParameter("assignedPtaskId",assignedPtaskId);
		query.setParameter("ptaskGroupStuId",ptaskGroupStudentId);
		query.executeUpdate();
				
		tx.commit();
		session.close();
		}catch(HibernateException e){
			logger.error("Error in updateStudentPtaskEvidence() of PerformanceTaskDAOImpl" + e);
			e.printStackTrace();
		}
	}
}
