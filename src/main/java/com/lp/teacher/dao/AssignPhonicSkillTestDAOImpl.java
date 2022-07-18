package com.lp.teacher.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.lp.model.Assignment;
import com.lp.model.BpstGroups;
import com.lp.model.BpstTypes;
import com.lp.model.EarlyLiteracyTestsForm;
import com.lp.model.Language;
import com.lp.model.PhonicGroups;
import com.lp.model.PhonicSections;
import com.lp.model.Student;
import com.lp.model.StudentPhonicTestMarks;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

public class AssignPhonicSkillTestDAOImpl extends CustomHibernateDaoSupport implements AssignPhonicSkillTestDAO {

	@Autowired
	HttpServletRequest request;
	@Autowired 
	ServletContext servletContext;
	@Autowired
	HttpSession session;
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PhonicGroups> getAllPhonicGroups(long langId) {
		List<PhonicGroups> phonicGroupsLt = new ArrayList<PhonicGroups>();
		try {
			phonicGroupsLt = (List<PhonicGroups>) getHibernateTemplate().find("from PhonicGroups where status='"+WebKeys.ACTIVE+"' and phonicSections.language.languageId="+langId+" order by phonicSections.phonicSectionId, groupOrder");
		} catch (DataAccessException e) {
			logger.error("Error in getAllPhonicGroups() of AssignPhonicSkillTestDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return phonicGroupsLt;
	}
	
	@SuppressWarnings("unchecked")
	public List<PhonicGroups> getAllBpstTypePhonicGroups(long bpstTypeId){
		List<PhonicGroups> phonicGroupsLt = new ArrayList<PhonicGroups>();
		List<BpstGroups> BpstGroupsLt = new ArrayList<BpstGroups>();
		try {
			BpstGroupsLt = (List<BpstGroups>) getHibernateTemplate().find("from BpstGroups where bpstTypes.bpstTypeId="+bpstTypeId);
			for (BpstGroups bpstGroups : BpstGroupsLt) {
				phonicGroupsLt.add(bpstGroups.getPhonicGroups());
			}
		} catch (DataAccessException e) {
			logger.error("Error in getAllPhonicGroups() of AssignPhonicSkillTestDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return phonicGroupsLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PhonicGroups> getAssignedStudentPhonicGroups(long studentAssignmentId) {
		List<PhonicGroups> phonicGroupsLt = new ArrayList<PhonicGroups>();
		try {
			List<StudentPhonicTestMarks> studentPhonicTestMarksLt = new ArrayList<StudentPhonicTestMarks>();
			try{
				studentPhonicTestMarksLt =(List<StudentPhonicTestMarks>) getHibernateTemplate().find("from StudentPhonicTestMarks where studentAssignmentStatus.studentAssignmentId="+studentAssignmentId+" order by phonicGroups.phonicSections.phonicSectionId,phonicGroups.groupOrder");
				if(!studentPhonicTestMarksLt.isEmpty()){
					for(StudentPhonicTestMarks studentPhonicTestMarks: studentPhonicTestMarksLt){
						phonicGroupsLt.add(studentPhonicTestMarks.getPhonicGroups());
					}
				}
			} catch (Exception e) {
				logger.error("Error in getStudentPhonicTestMarksByGroupId() of GradePhonicSkillTestDAOImpl"+ e);
				e.printStackTrace();
			}
		} catch (DataAccessException e) {
			logger.error("Error in getAssignedStudentPhonicGroups() of AssignPhonicSkillTestDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return phonicGroupsLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PhonicSections> getAllPhonicSections() {
		List<PhonicSections> PhonicSectionsLt  = new ArrayList<PhonicSections>();
		try {
			PhonicSectionsLt = (List<PhonicSections>) getHibernateTemplate().find(
					"from PhonicSections order by phonicSectionId");
		} catch (DataAccessException e) {
			logger.error("Error in getAllPhonicSections() of AssignPhonicSkillTestDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return PhonicSectionsLt;
	}
   public boolean checkTestAlreadyAssgined(Assignment assignment){
	 long assignmentId = 0;
	 boolean isAssigned = false;
		try{
			DateFormat formatter = new SimpleDateFormat(WebKeys.DB_DATE_FORMATE);
			String check_assignmentId = "select assignment_id from assignment where "
					+ "assignment_type_id = "+assignment.getAssignmentType().getAssignmentTypeId()
					+" and cs_id = "+assignment.getClassStatus().getCsId()
					+" and title = '"+assignment.getTitle()+"'"
					+" and assign_status = '"+assignment.getAssignStatus()+"'"
					+" and date_assigned = '"+formatter.format(assignment.getDateAssigned())+"'"
					+" and date_due = '"+formatter.format(assignment.getDateDue())+"'";
			
			SqlRowSet rs_check_assignmentId = jdbcTemplate.queryForRowSet(check_assignmentId);
			 if (rs_check_assignmentId.next())
				 assignmentId = rs_check_assignmentId.getLong(1);
			 if(assignmentId > 0){
				 isAssigned = true;
			 }
		 }catch(Exception e){
			    isAssigned = false;
				logger.error("Error in checkTestAlreadyAssgined() of AssignPhonicSkillTestDAOImpl"+ e);
		 }
	   return isAssigned;
	 }
	
	@Override
	public boolean assignPhonicSkillsTest(Assignment assignment, ArrayList<Long> groupIdArray,ArrayList<Long> students,long langId) {

		boolean success = false;
		long assignmentId = 0;
		long studentAssignmentId = 0;
		try{
				String insert_for_assignment = "insert into assignment (cs_id,assignment_type_id,title,assign_status,date_assigned,date_due,used_for) values(?,?,?,?,?,?,?)";
			    jdbcTemplate.update(
			    		insert_for_assignment,
			    		assignment.getClassStatus().getCsId(),
			    		assignment.getAssignmentType().getAssignmentTypeId(),
			    		assignment.getTitle(),
			    		assignment.getAssignStatus(),
			    		assignment.getDateAssigned(),
			    		assignment.getDateDue(),
			    		assignment.getUsedFor()
			    		); 
				assignmentId = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);	
				if(assignmentId > 0){				 
					for (Long studentId : students) {
						String insert_for_student_assignment_status = "insert into student_assignment_status (assignment_id,student_id,status,graded_status) values(?,?,?,?)";
						jdbcTemplate.update(insert_for_student_assignment_status,assignmentId,studentId,WebKeys.TEST_STATUS_PENDING,WebKeys.GRADED_STATUS_NOTGRADED); 
						studentAssignmentId = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
						if(langId==1){
							for (Long groupId : groupIdArray) {
								if(groupId != null){
									String insert_for_student_phonic_test_marks = "insert into student_phonic_test_marks (group_id,student_assignment_id) values(?,?)";
									jdbcTemplate.update(insert_for_student_phonic_test_marks,groupId,studentAssignmentId); 
								}
							}
						}else{
							for (Long bpstTypeId : groupIdArray) {
								List<BpstGroups> bpstGroupsLt=getBpstGroupsByBpstTypeId(bpstTypeId);
								for (BpstGroups bg : bpstGroupsLt) {
									long groupId=bg.getPhonicGroups().getGroupId();
									String insert_for_student_phonic_test_marks = "insert into student_phonic_test_marks (group_id,student_assignment_id) values(?,?)";
									jdbcTemplate.update(insert_for_student_phonic_test_marks,groupId,studentAssignmentId);
								}
							}
						}
						success = true;
					}
				}
		}catch(Exception e){
			success = false;
			logger.error("Error in assignPhonicSkillsTest() of AssignPhonicSkillTestDAOImpl"+ e);
		}
		return success;
	}
	
	@Override
	public void recordPhonicSkillTest(EarlyLiteracyTestsForm earlyLiteracyTestsForm){
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		    long studentId = student.getStudentId();
			byte[] bis = Base64.decodeBase64(earlyLiteracyTestsForm.getAudioData());
			String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
			FileOutputStream fos = null;
			String phonicSkillTestFilePath = "";
			phonicSkillTestFilePath =   uploadFilePath + File.separator+ 
										WebKeys.PHONIC_TEST+File.separator+
										studentId+File.separator+
										earlyLiteracyTestsForm.getAssignmentId()+File.separator+
										earlyLiteracyTestsForm.getGroupId();
			File file = new File(phonicSkillTestFilePath);
			if(file.exists()) { 
				file.delete();
			}else if(!file.isDirectory()) {
				file.setExecutable(true, false);
				file.mkdirs();
	        } 
			try{
		  		 File f = new File(phonicSkillTestFilePath+File.separator+earlyLiteracyTestsForm.getGroupId()+WebKeys.WAV_FORMAT);  
				  if(f.exists()) 
		  				f.delete();
		  			synchronized(bis) {
		  				fos = new FileOutputStream(f, true); 
		  				fos.write(bis);
		  			}
	         }catch (Exception e) {
	             e.printStackTrace();
	         }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
	@SuppressWarnings("unchecked")
	@Override
	public List<Language> getLanguages(){
		List<Language> languageList = new ArrayList<Language>();
		try {
			languageList = (List<Language>) getHibernateTemplate().find(
					"from Language");
		} catch (DataAccessException e) {
			logger.error("Error in getLanguages() of AssignPhonicSkillTestDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return languageList;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PhonicGroups> getPhonicGroupsByLanguage(long langId) {
		List<PhonicGroups> phonicGroupsLt = new ArrayList<PhonicGroups>();
		try {
			phonicGroupsLt = (List<PhonicGroups>) getHibernateTemplate().find(
					"from PhonicGroups where status='"+WebKeys.ACTIVE+"' and phonicSections.language.languageId="+langId+" order by phonicSections.phonicSectionId, groupOrder");
		} catch (DataAccessException e) {
			logger.error("Error in getAllPhonicGroups() of AssignPhonicSkillTestDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return phonicGroupsLt;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<PhonicSections> getPhonicSectionsByLanguage(long langId) {
		List<PhonicSections> PhonicSectionsLt  = new ArrayList<PhonicSections>();
		try {
			PhonicSectionsLt = (List<PhonicSections>) getHibernateTemplate().find(
					"from PhonicSections where language.languageId="+langId+" order by phonicSectionId");
		} catch (DataAccessException e) {
			logger.error("Error in getAllPhonicSections() of AssignPhonicSkillTestDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return PhonicSectionsLt;
	}
	@SuppressWarnings("unchecked")
	@Override
	  public List<BpstTypes> getBpstTypes(){
		List<BpstTypes> bpstTypesList  = new ArrayList<BpstTypes>();
		try {
			bpstTypesList = (List<BpstTypes>) getHibernateTemplate().find(
					"from BpstTypes");
		} catch (DataAccessException e) {
			logger.error("Error in getBpstTypes() of AssignPhonicSkillTestDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return bpstTypesList;
	  }
	@SuppressWarnings("unchecked")
	@Override
	public List<BpstGroups> getBpstGroupsByBpstTypeId(long bpstTypeId){
		List<BpstGroups> bpstGroupsList = new ArrayList<BpstGroups>();
		try {
			bpstGroupsList = (List<BpstGroups>) getHibernateTemplate().find(
					"from BpstGroups where bpstTypes.bpstTypeId="+bpstTypeId+"");
		} catch (DataAccessException e) {
			logger.error("Error in getBpstGroupsByBpstTypeId() of AssignPhonicSkillTestDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return bpstGroupsList;
	}

}
