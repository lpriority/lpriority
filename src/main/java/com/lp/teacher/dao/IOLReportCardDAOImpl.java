package com.lp.teacher.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.lp.admin.dao.GradesDAO;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.FileService;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignLegendRubrics;
import com.lp.model.CAASPPScores;
import com.lp.model.CAASPPTypes;
import com.lp.model.FilesLP;
import com.lp.model.Grade;
import com.lp.model.IOLReport;
import com.lp.model.LERubrics;
import com.lp.model.LearningIndicator;
import com.lp.model.LearningIndicatorValues;
import com.lp.model.Legend;
import com.lp.model.LegendCriteria;
import com.lp.model.LegendSubCriteria;
import com.lp.model.MulYrLegend;
import com.lp.model.Student;
import com.lp.model.Trimester;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Repository("iolreportcarddao")
public class IOLReportCardDAOImpl extends CustomHibernateDaoSupport implements
		IOLReportCardDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HttpSession session;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	SchoolAdminService schoolAdminService;
	@Autowired
	HttpServletRequest request;
	@Autowired
	FileService fileService;
	@Autowired
	private GradesDAO gradesDAO;
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LegendCriteria> getLegendCriteria() {
		List<LegendCriteria> legendCriteria = new ArrayList<LegendCriteria>();
		try {
			legendCriteria = (List<LegendCriteria>) getHibernateTemplate()
					.find("from LegendCriteria");
		} catch (Exception e) {
			logger.error("Error in getLegendCriteria() of IOLReportCardDAOImpl"
					+ e);

		}
		return legendCriteria;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LegendSubCriteria> getLegendSubCriteriasByCriteriaId(
			long legendCriteriaId, long masterGradeId) {
		List<LegendSubCriteria> legendSubCriterias = new ArrayList<LegendSubCriteria>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			legendSubCriterias = (List<LegendSubCriteria>) getHibernateTemplate()
					.find("Select ls.legendSubCriteria from LegendSubCriteriaDistrictWise ls where ls.legendSubCriteria.legendCriteria.legendCriteriaId="
							+ legendCriteriaId + " and ls.masterGrades.masterGradesId = "+masterGradeId+" and ls.district.districtId="+userReg.getSchool().getDistrict().getDistrictId()+
							" order by ls.legendSubCriteria.legendCriteria.legendCriteriaId, ls.legendSubCriteria.legendSubCriteriaId");
			
			return legendSubCriterias;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getLegendSubCriteriasByCriteriaId() of IOLReportCardDAOImpl"
					+ e);

		}
		return legendSubCriterias;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LegendSubCriteria> getLiteracyLegendSubCriterias() {
		List<LegendSubCriteria> legendSubCriterias = new ArrayList<LegendSubCriteria>();
		try {
			legendSubCriterias = (List<LegendSubCriteria>) getHibernateTemplate()
					.find("FROM LegendSubCriteria where legendCriteria.legendCriteriaId=6"+
							" order by legendSubCriteriaId");
			return legendSubCriterias;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getLegendSubCriteriasByCriteriaId() of IOLReportCardDAOImpl"
					+ e);

		}
		return legendSubCriterias;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Legend> getLegends() {
		List<Legend> legends = new ArrayList<Legend>();
		try {
			legends = (List<Legend>) getHibernateTemplate().find("from Legend where isDefault='yes' order by legendId desc");
		} catch (Exception e) {
			logger.error("Error in getLegends() of IOLReportCardDAOImpl" + e);

		}
		return legends;

	}

	@Override
	public List<LearningIndicatorValues> CreateIOLReportCard(IOLReport iolReport,List<LearningIndicator> learningIndicators, List<LearningIndicatorValues> learningIndicatorValues) {

		try {
			super.saveOrUpdate(iolReport);
			for(LearningIndicator learningIndicator: learningIndicators){
				super.saveOrUpdate(learningIndicator);	
			}
			
			for(LearningIndicatorValues learningIndicatorValues2: learningIndicatorValues){
				super.saveOrUpdate(learningIndicatorValues2);
			}

		} catch (Exception e) {
			logger.error("Error in CreateIOLReportCard() of IOLReportCardDAOImpl"
					+ e);

		}
		return learningIndicatorValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Legend getLegend(long legendId) {
		List<Legend> legend = new ArrayList<Legend>();
		try {
			legend = (List<Legend>) getHibernateTemplate().find(
					"from Legend where legendId=" + legendId + "");

		} catch (Exception e) {
			logger.error("Error in getLegend() of IOLReportCardDAOImpl" + e);
		}
		if (legend.size() > 0)
			return legend.get(0);
		else
			return new Legend();
	}

	@SuppressWarnings("unchecked")
	@Override
	public LegendSubCriteria getLegendSubCriteria(long legendSubCriteriaId) {
		List<LegendSubCriteria> legendSub = new ArrayList<LegendSubCriteria>();
		try {
			legendSub = (List<LegendSubCriteria>) getHibernateTemplate().find(
					"from LegendSubCriteria where legendSubCriteriaId="
							+ legendSubCriteriaId + "");
		} catch (Exception e) {
			logger.error("Error in getLegendSubCriteria() of IOLReportCardDAOImpl"
					+ e);
		}
		if (legendSub.size() > 0)
			return legendSub.get(0);
		else
			return new LegendSubCriteria();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LearningIndicator getLearningIndicator(long learningIndicatorId) {
		List<LearningIndicator> learInd = new ArrayList<LearningIndicator>();
		try {
			learInd = (List<LearningIndicator>) getHibernateTemplate().find(
					"from LearningIndicator where learningIndicatorId="
							+ learningIndicatorId + "");
		} catch (Exception e) {
			logger.error("Error in getLearningIndicator() of IOLReportCardDAOImpl"
					+ e);
		}
		if (learInd.size() > 0)
			return learInd.get(0);
		else
			return new LearningIndicator();
	}

	@Override
	public boolean saveLearningValues(LearningIndicatorValues learningdVal) {
		try {
			super.saveOrUpdate(learningdVal);
			return true;
		} catch (Exception e) {
			logger.error("Error in saveLearningValues() of IOLReportCardDAOImpl"
					+ e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOLReport> getStudentIOLReportDates(long csId,
			long studentId) {
		List<IOLReport> listLearnIndicator = new ArrayList<IOLReport>();
		try {
			listLearnIndicator = (List<IOLReport>) getHibernateTemplate()
					.find("from IOLReport where student.studentId="
							+ studentId + " and classStatus.csId=" + csId + " and (status='false' or status is null)");
			//and status ='false'
		} catch (Exception e) {
			logger.error("Error in getStudentIOLReportDates() of IOLReportCardDAOImpl"
					+ e);
		}
		return listLearnIndicator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOLReport> getStudentCompletedIOLReportDates(long csId,
			long studentId) {
		List<IOLReport> listIOLReports = new ArrayList<IOLReport>();
		try {
			listIOLReports = (List<IOLReport>) getHibernateTemplate()
					.find("from IOLReport where student.studentId="
							+ studentId + " and classStatus.csId=" + csId
							+ " and status='true'");
		} catch (Exception e) {
			logger.error("Error in getStudentIOLReportDates() of IOLReportCardDAOImpl"
					+ e);
		}
		return listIOLReports;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LearningIndicatorValues> getStudentIOLReportCard(
			long learnIndicatorId) {
		List<LearningIndicatorValues> listLearnIndicatorValues = new ArrayList<LearningIndicatorValues>();
		try {
			listLearnIndicatorValues = (List<LearningIndicatorValues>) getHibernateTemplate()
					.find("from LearningIndicatorValues where learningIndicator.learningIndicatorId="
							+ learnIndicatorId + "");
		} catch (Exception e) {
			logger.error("Error in getStudentIOLReportCard() of IOLReportCardDAOImpl"
					+ e);
		}
		return listLearnIndicatorValues;
	}
	// used by student show report
	@SuppressWarnings("unchecked")
	@Override
	public List<IOLReport> getStudentAllIOLReportDates(long studentId) {
		List<IOLReport> listLearnIndicator = new ArrayList<IOLReport>();
		try {
			listLearnIndicator = (List<IOLReport>) getHibernateTemplate()
					.find("from IOLReport where student.studentId="
							+ studentId + " and status is not null");
		} catch (Exception e) {
			logger.error("Error in getStudentAllIOLReportDates() of IOLReportCardDAOImpl"
					+ e);
		}
		return listLearnIndicator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LearningIndicator> getStudentAllCompletedIOLReportDates(
			long studentId) {
		List<LearningIndicator> listLearnIndicator = new ArrayList<LearningIndicator>();
		try {
			listLearnIndicator = (List<LearningIndicator>) getHibernateTemplate()
					.find("from LearningIndicator where student.studentId="
							+ studentId + " and status='true'");
		} catch (Exception e) {
			logger.error("Error in getStudentAllIOLReportDates() of IOLReportCardDAOImpl"
					+ e);
		}
		return listLearnIndicator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LearningIndicatorValues getLearningIndicatorValues(
			long learningIndicatorValueId) {
		List<LearningIndicatorValues> learIndValues = new ArrayList<LearningIndicatorValues>();
		try {
			learIndValues = (List<LearningIndicatorValues>) getHibernateTemplate()
					.find("from LearningIndicatorValues where learningValuesId="
							+ learningIndicatorValueId + "");
		} catch (Exception e) {
			logger.error("Error in getLearningIndicatorValues() of IOLReportCardDAOImpl"
					+ e);
		}
		if (learIndValues.size() > 0)
			return learIndValues.get(0);
		else
			return new LearningIndicatorValues();
	}

	@Override
	public boolean saveTeacherComment(long  learningdValId, String teacherComment) {
		try {
			String query = "update learning_indicator_values set teacher_comment=? where learning_values_id=?";
			@SuppressWarnings("unused")
			int out = jdbcTemplate.update(query, teacherComment, learningdValId);
		} catch (Exception e) {
			logger.error("Error in saveTeacherComment() of IOLReportCardDAOImpl" + e);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean saveTeacherScore(long  learningdValId, long teacherScore) {
		try {
			String query = "update learning_indicator_values set teacher_score=? where learning_values_id=?";
			@SuppressWarnings("unused")			
			int out = jdbcTemplate.update(query, teacherScore, learningdValId);
		} catch (Exception e) {
			logger.error("Error in saveTeacherScore() of IOLReportCardDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean submitIOLCardtoStudent(long iolReportId) {
		try {
			String query = "update iol_report set status=? where iol_report_id=?";	
			@SuppressWarnings("unused")		
			int out = jdbcTemplate.update(query, WebKeys.LP_TRUE, iolReportId);
		} catch (Exception e) {
			logger.error("Error in saveTeacherScore() of IOLReportCardDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean submitStudentIOLReportCardToTeacher(long iolReportId){
		String isMulYrRpt="yes";
		try{
			String query = "update iol_report set status=?,is_mul_yr_rpt=? where iol_report_id=?";	
			@SuppressWarnings("unused")		
			int out = jdbcTemplate.update(query, WebKeys.LP_FALSE,isMulYrRpt, iolReportId);
		}catch(Exception e)
		{
			logger.error("Error in submitStudentIOLReportCardToTeacher() of IOLReportCardDAOImpl", e);
			return false;
		}
		return true;		
	}
	  	  
	@SuppressWarnings("unchecked")
	@Override
	public List<IOLReport> getStudentInCompletedIOLReport(long csId,
			long studentId) {
		List<IOLReport> listIOLReports = new ArrayList<IOLReport>();
		try {
			listIOLReports = (List<IOLReport>) getHibernateTemplate()
					.find("from IOLReport where student.studentId="
								+ studentId + " and classStatus.csId=" + csId
								+ " and status is null");
		} catch (Exception e) {
			logger.error("Error in getStudentInCompletedIOLReport() of IOLReportCardDAOImpl"
					+ e);
		}
		return listIOLReports;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LegendSubCriteria> getLegendSubCriterias() {
		List<LegendSubCriteria> legendSubCriterias = new ArrayList<LegendSubCriteria>();
		try {
			legendSubCriterias = (List<LegendSubCriteria>) getHibernateTemplate()
					.find("from LegendSubCriteria order by legendCriteria.legendCriteriaId, legendSubCriteriaId");
			return legendSubCriterias;
		} catch (Exception e) {
			logger.error("Error in getLegendSubCriterias() of IOLReportCardDAOImpl"
					+ e);			
		}
		return legendSubCriterias;
	}
	@Override
	public boolean saveStudentNotes(long  learningdValId, String studentNotes) {
		try {
			String query = "update learning_indicator_values set notes=? where learning_values_id=?";
			@SuppressWarnings("unused")
			int out = jdbcTemplate.update(query, studentNotes, learningdValId);
		} catch (Exception e) {
			logger.error("Error in saveStudentNotes() of IOLReportCardDAOImpl" + e);
			return false;
		}
		return true;
	}
		
	@Override
	public boolean saveStudentSelfScore(long  learningdValId, long studentScore) {
		try {
			String query = "update learning_indicator_values set legend_id=?, teacher_score=? where learning_values_id=?";
			@SuppressWarnings("unused")			
			int out = jdbcTemplate.update(query, studentScore, studentScore, learningdValId);
		} catch (Exception e) {
			logger.error("Error in saveStudentSelfScore() of IOLReportCardDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Legend> getRubricValuesBySubCriteriaId(long subCriteriaId, long createBy){
		List<Legend> rubricValues= new ArrayList<Legend>();
		try {
			rubricValues = (List<Legend>) getHibernateTemplate()
					.find("from Legend where legendSubCriteria.legendSubCriteriaId="+subCriteriaId+" and createdBy.regId="+createBy+" order by legendValue");
			return rubricValues;
		} catch (Exception e) {
			logger.error("Error in getRubricValuesBySubCriteriaId() of IOLReportCardDAOImpl"
					+ e);

		}
		return rubricValues;
	}
		
		
	@SuppressWarnings("rawtypes")
	@Override
	public long assignLIRubricToGrade(List<LERubrics> leRubricsList){			
		try {
			Session session = sessionFactory.openSession();		
			Transaction tx = (Transaction) session.beginTransaction();		
			Query query= session.createQuery("update LERubrics set status='"+WebKeys.LP_STATUS_INACTIVE+"' where grade.gradeId="+leRubricsList.get(0).getGrade().getGradeId()
					+" and legendSubCriteria.legendSubCriteriaId="+leRubricsList.get(0).getLegendSubCriteria().getLegendSubCriteriaId() +
					 " and status='"+WebKeys.ACTIVE+"'");
			query.executeUpdate();
			for(LERubrics leRub: leRubricsList){						
				session.saveOrUpdate(leRub);					
			}
			tx.commit();
			session.close();
			return 1;
		} catch (HibernateException e) {
			logger.error("Error in createLIRubric() of AdminDAOImpl" + e);
			e.printStackTrace();
			return 0;
		}

	}
	@Override
	public long checkAlreadyAssignedLIRubric(long subCriteriaId,long legendId,long gradeId){
		
			String query = "select * from le_rubrics where grade_id="
					+ gradeId + " and legend_sub_criteria_id=" + subCriteriaId+" and legend_id="+legendId;
			int check = 0;

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {

				check = 1;

			}
			return check;
		}
	@SuppressWarnings("unchecked")
	@Override
	public List<LERubrics> getRubricValuesByGradeId(long gradeId,long subCriteriaId){
		List<LERubrics> rubricValues= new ArrayList<LERubrics>();
		try {
			rubricValues = (List<LERubrics>) getHibernateTemplate()
					.find("from LERubrics where legendSubCriteria.legendSubCriteriaId="+subCriteriaId+" and grade.gradeId="+gradeId+""
							+ "and status='"+WebKeys.ACTIVE+"' order by legend.legendValue");
			
		} catch (Exception e) {
			logger.error("Error in getRubricValuesBySubCriteriaId() of IOLReportCardDAOImpl"
					+ e);

		}
		return rubricValues;
	}
	
	public String leFileAutoSave(MultipartFile file,Student stud,long learningIndicatorId,long subCriteriaId,long learnIndValuesId){
		String path="";
		try{			
			path=saveFileInFileSystem(file, stud,learningIndicatorId,subCriteriaId,learnIndValuesId);			
		}catch (HibernateException e) {
			logger.error("Error in fileAutoSave() of IOLReportCardDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in fileAutoSave() of IOLReportCardDAOImpl",e);
		}
	return path;
	}
	@Override
	public String saveFileInFileSystem(MultipartFile file,Student stud,long learningIndicatorId,long subCriteriaId,long learnIndValuesId) {
		UserRegistration userReg = schoolAdminService.getUserRegistration(stud.getUserRegistration().getRegId());
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
		String leFileFullPath = uploadFilePath + File.separator+stud.getStudentId()
					+File.separator+learningIndicatorId+File.separator+learnIndValuesId;
		//Create dir if doesn't exist
		File f = new File(leFileFullPath);
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
		leFileFullPath = leFileFullPath+File.separator+file.getOriginalFilename();
  		try {
  			 byte[] bis =  file.getBytes();
  			 FileOutputStream fs = new FileOutputStream(leFileFullPath);
             BufferedOutputStream bs = new BufferedOutputStream(fs);
             bs.write(bis);
             bs.close();
             fs.close();
        } catch (Exception e) {
             e.printStackTrace();
        }	
  		return leFileFullPath;
	}
	@Override
	public List<FilesLP> getLEFileList(Student stud,long learningIndicatorId,long learnIndValuesId){
		File[] fileList = null;
		UserRegistration userReg = schoolAdminService.getUserRegistration(stud.getUserRegistration().getRegId());
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
		String leFileFullPath = uploadFilePath + File.separator+stud.getStudentId()
				+File.separator+learningIndicatorId+File.separator+learnIndValuesId;
		 List<FilesLP> fileLPs=new ArrayList<FilesLP>();
		 fileList=fileService.getFolderFiles(leFileFullPath);
		 		 
		 if(fileList != null){
			for(File pf : fileList){
				FilesLP fl = new FilesLP();
				fl.setFileName(pf.getName());
				fl.setFilePath(pf.getPath());
				fileLPs.add(fl);
			}
		}
   	 
		return fileLPs;		
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getRubricDesc(long criteriaId,long subCriteriaId,long rubricScore, long createdBy){
		List<Legend> rubricValues= new ArrayList<Legend>();
		try {
			rubricValues = (List<Legend>) getHibernateTemplate()
					.find("from Legend where legendSubCriteria.legendSubCriteriaId="+subCriteriaId+" and createdBy.regId="+createdBy+" and legendValue="+rubricScore+" and status='"+WebKeys.ACTIVE+"'");
			
		} catch (Exception e) {
			logger.error("Error in getRubricValuesBySubCriteriaId() of IOLReportCardDAOImpl"
					+ e);

		}
		if(!rubricValues.isEmpty()){
			return rubricValues.get(0).getLegendName();
		}
		else{
			return "";
		}
		
	}
	@Override
	public long editLIRubric(long subCriteriaId,long rubricScore,String rubricDesc, long regId){
		int out=0;
		try {
			String query = "update legend set legend_name=? where legend_sub_criteria_id=? and legend_value=? and created_by=" + regId + " and status='" +WebKeys.ACTIVE+"'";			
			 out= jdbcTemplate.update(query, rubricDesc, subCriteriaId, rubricScore);
		} catch (Exception e) {
			logger.error("Error in saveStudentSelfScore() of IOLReportCardDAOImpl" + e);
			e.printStackTrace();
			return 0;
		}
		return out;
	}
	@Override
	public boolean checkLIRubricExists(long subCriteriaId,long rubricScore, long regId){
		boolean check = false;
		try{
		String query = "select * from legend where legend_sub_criteria_id="+subCriteriaId + 
				" and legend_value="+rubricScore+ " and created_by="+regId+" and status='"+WebKeys.ACTIVE+"'";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
					check = true;
			}
		}catch(Exception e){
			logger.error("Error in saveStudentSelfScore() of IOLReportCardDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
				return check;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<IOLReport> getChildAllIOLReportDates(long studentId) {
		List<IOLReport> listLearnIndicator = new ArrayList<IOLReport>();
		try {
			listLearnIndicator = (List<IOLReport>) getHibernateTemplate()
					.find("from IOLReport where student.studentId="
							+ studentId + " and status='true'");
		} catch (Exception e) {
			logger.error("Error in getChildAllIOLReportDates() of IOLReportCardDAOImpl"
					+ e);
		}
		return listLearnIndicator;
	}
	 @SuppressWarnings("unchecked")
	 @Override
	public List<LearningIndicatorValues> getStudentInCompletedIOLSection(long learningIndicatorId) {
			List<LearningIndicatorValues> listLearnIndicator = new ArrayList<LearningIndicatorValues>();
			try {
				listLearnIndicator = (List<LearningIndicatorValues>) getHibernateTemplate()
						.find("from LearningIndicatorValues where learningIndicator.learningIndicatorId="+learningIndicatorId+" and learningIndicator.status is null order by legendSubCriteria.legendSubCriteriaId");
			} catch (Exception e) {
				logger.error("Error in getStudentInCompletedIOLSection() of IOLReportCardDAOImpl"
						+ e);
			}
			return listLearnIndicator;
		}
	 @SuppressWarnings("unchecked")
		@Override
		public LegendCriteria getLegendCriteria(long legendCriteriaId) {
			List<LegendCriteria> legendCri = new ArrayList<LegendCriteria>();
			try {
				legendCri = (List<LegendCriteria>) getHibernateTemplate().find(
						"from LegendCriteria where legendCriteriaId="
								+ legendCriteriaId + "");
			} catch (Exception e) {
				logger.error("Error in getLegendCriteria() of IOLReportCardDAOImpl"
						+ e);
			}
			if (legendCri.size() > 0)
				return legendCri.get(0);
			else
				return new LegendCriteria();
		}
	 @Override
	  public boolean submitStudentIOLSectionToTeacher(long learIndicatorId,String leScore){
		  try{
			  String query = "update learning_indicator set status=?,le_score=? where learning_indicator_id=?";	
			  @SuppressWarnings("unused")		
			  int out = jdbcTemplate.update(query, WebKeys.LP_FALSE,leScore,learIndicatorId);
		  }catch(Exception e)
		  {
			  logger.error("Error in submitStudentIOLReportCardToTeacher() of IOLReportCardDAOImpl", e);
			  return false;
		  }
		  return true;		
	  }
	 @Override
	 public long getSumOfLegends(long learningIndicatorId){
		 long sumOfLegends=0;
		 try{
				String query = "select sum(legend_id) from  learning_indicator_values where learning_indicator_id="+learningIndicatorId;
				sumOfLegends =  jdbcTemplate.queryForObject(query, new Object[] {}, Long.class)!=null?jdbcTemplate.queryForObject(query, new Object[] {}, Long.class):0;  //jdbcTemplate.queryForLong(query);
					
		 }catch(Exception e){
			logger.error("Error in getSumOfLegends() of IOLReportCardDAOImpl" + e);
			e.printStackTrace();
			return 0;
		}
		return sumOfLegends;
	  }
	 @Override
	 public long getNoOfLegends(long learningIndicatorId){
		 long noOfLegends=0;
		 try{
				String query = "select count(legend_id) from  learning_indicator_values where learning_indicator_id="+learningIndicatorId;
				noOfLegends =  jdbcTemplate.queryForObject(query, new Object[] {}, Long.class)!=null?jdbcTemplate.queryForObject(query, new Object[] {}, Long.class):0;  //jdbcTemplate.queryForLong(query);
					
		 }catch(Exception e){
			logger.error("Error in getSumOfLegends() of IOLReportCardDAOImpl" + e);
			e.printStackTrace();
			return 0;
		}
		return noOfLegends; 
	 }
	 @SuppressWarnings({ "unchecked" })
	 @Override
	 public LearningIndicator getIOLSectionStatus(long csId,long studentId,long legendCriteriaId,String createDate){
		 List<LearningIndicator> legendCri = new ArrayList<LearningIndicator>();
			try {
				legendCri = (List<LearningIndicator>) getHibernateTemplate().find("from LearningIndicator where legendCriteria.legendCriteriaId="+legendCriteriaId + 
						" and classStatus.csId="+csId+" and student.studentId="+studentId+" and createDate='"+createDate+"'");
			} catch (Exception e) {
				logger.error("Error in getLegendCriteria() of IOLReportCardDAOImpl"
						+ e);
			}
			
			if (legendCri.size() > 0)
				return legendCri.get(0);
			else
				return new LearningIndicator();
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<LearningIndicatorValues> getStudentCompletedIOLSection(long learningIndicatorId) {
		 List<LearningIndicatorValues> listLearnIndicator = new ArrayList<LearningIndicatorValues>();
		 try {
			 listLearnIndicator = (List<LearningIndicatorValues>) getHibernateTemplate()
					 .find("from LearningIndicatorValues where learningIndicator.learningIndicatorId="+learningIndicatorId+" and learningIndicator.status is not null order by legendSubCriteria.legendSubCriteriaId");
		 } catch (Exception e) {
			 logger.error("Error in getStudentCompletedIOLSection() of IOLReportCardDAOImpl"
					 + e);
		 }
			return listLearnIndicator;
	 }
  	  
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<LearningIndicator> getStudentCurrentIOLReport(long csId,long studentId,long iolReportId) {
		 List<LearningIndicator> listLearnIndicator = new ArrayList<LearningIndicator>();
		 try {
			 listLearnIndicator = (List<LearningIndicator>) getHibernateTemplate()
					 .find("from LearningIndicator where iolReport.student.studentId="
							 + studentId + " and iolReport.classStatus.csId=" + csId
							 + " and iolReport.iolReportId="+iolReportId+" order by legendCriteria.legendCriteriaId");
		 } catch (Exception e) {
			 logger.error("Error in getStudentCurrentIOLReport() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return listLearnIndicator;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<LearningIndicatorValues> getIncompleteLitracyLearnIndValues(long iolReportId,long legendCriteriaId){
		 List<LearningIndicatorValues> listLearnIndValues = new ArrayList<LearningIndicatorValues>();
		 try {
			 listLearnIndValues = (List<LearningIndicatorValues>) getHibernateTemplate()
					 .find("from LearningIndicatorValues where "+
							 "learningIndicator.legendCriteria.legendCriteriaId="+legendCriteriaId+
							 " and learningIndicator.iolReport.iolReportId="+iolReportId
							 + " order by legendSubCriteria.legendSubCriteriaId");
		 } catch (Exception e) {
			 logger.error("Error in getIncompleteLitracyLearnIndValues() of IOLReportCardDAOImpl"
					 + e);
			 e.printStackTrace();
		 }
		 return listLearnIndValues;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<LearningIndicatorValues> getNewLitracyLearnIndValues(long csId,long studentId,long legendCriteriaId){
		 List<LearningIndicatorValues> listLearnIndValues = new ArrayList<LearningIndicatorValues>();
		 try {
			 listLearnIndValues = (List<LearningIndicatorValues>) getHibernateTemplate()
					 .find("from LearningIndicatorValues where learningIndicator.iolReport.student.studentId="
							 + studentId + " and learningIndicator.iolReport.classStatus.csId=" + csId
							 + " and learningIndicator.status is null and learningIndicator.legendCriteria.legendCriteriaId="+legendCriteriaId+" order by legendSubCriteria.legendSubCriteriaId");
		 } catch (Exception e) {
			 logger.error("Error in getNewLitracyLearnIndValues() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return listLearnIndValues; 
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public LearningIndicatorValues getStudentInCompletedSubCriteria(long learningValuesId){
		 List<LearningIndicatorValues> listLearnIndValues = new ArrayList<LearningIndicatorValues>();
		 try {
			 listLearnIndValues = (List<LearningIndicatorValues>) getHibernateTemplate()
					 .find("from LearningIndicatorValues where submitStatus is null and learningValuesId="+learningValuesId+"");
		 } catch (Exception e) {
			 logger.error("Error in getStudentInCompletedSubCriteria() of IOLReportCardDAOImpl"
					 + e);
		 }
		 if (listLearnIndValues.size() > 0)
			 return listLearnIndValues.get(0);
		 else
			 return new LearningIndicatorValues();			
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public LearningIndicatorValues getStudentCompletedSubCriteria(long learningValuesId) {
		 List<LearningIndicatorValues> listLearnIndicator = new ArrayList<LearningIndicatorValues>();
		 try {
			 listLearnIndicator = (List<LearningIndicatorValues>) getHibernateTemplate()
					 .find("from LearningIndicatorValues where learningValuesId="+learningValuesId+" and submitStatus is not null");
		 } catch (Exception e) {
			 logger.error("Error in getStudentCompletedSubCriteria() of IOLReportCardDAOImpl"
					 + e);
		 }
		 if (listLearnIndicator.size() > 0)
			 return listLearnIndicator.get(0);
		 else
			 return new LearningIndicatorValues();
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public void submitStudentIOLSubCriteriaToTeacher(long learIndicatorId,long learningValuesId){
		 try{
			 String query2 = "update learning_indicator_values set submit_status=?  where learning_values_id=?";
			 @SuppressWarnings("unused")			
			 int out2 = jdbcTemplate.update(query2, WebKeys.LP_FALSE,learningValuesId);
			 List<LearningIndicatorValues> listLearnIndicatorValues = new ArrayList<LearningIndicatorValues>();
			 try {
				 listLearnIndicatorValues = (List<LearningIndicatorValues>) getHibernateTemplate()
						 .find("from LearningIndicatorValues where learningIndicator.learningIndicatorId="+learIndicatorId+" and submitStatus is null");
			 } catch (Exception e) {
				 logger.error("Error in submitStudentIOLSubCriteriaToTeacher() of IOLReportCardDAOImpl"
						 + e);
			 }
			 if (listLearnIndicatorValues.size() == 0){
				 String query = "update learning_indicator set status=?  where learning_indicator_id=?";
				 @SuppressWarnings("unused")			
				 int out = jdbcTemplate.update(query, WebKeys.LP_FALSE,learIndicatorId);
			 }
		 }catch(Exception e)
		 {
			 logger.error("Error in submitStudentIOLReportCardToTeacher() of IOLReportCardDAOImpl", e);			 	
		 }
				
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public IOLReport getIOLReport(long iolReportId){
		 List<IOLReport> iolReport = new ArrayList<IOLReport>();
		 try {
			 iolReport = (List<IOLReport>) getHibernateTemplate().find(
					 "from IOLReport where iolReportId="
							 + iolReportId + "");
		 } catch (Exception e) {
			 logger.error("Error in getIOLReport() of IOLReportCardDAOImpl"
					 + e);
		 }
		 if (iolReport.size() > 0)
			 return iolReport.get(0);
		 else
			 return new IOLReport();
	 }
	 @Override
	 public long getSumOfTeacherLegends(long learningIndicatorId){
		 long sumOfTeacherLegends=0;
		 try{
			 String query = "select sum(teacher_score) from  learning_indicator_values where learning_indicator_id="+learningIndicatorId;
			 sumOfTeacherLegends =  jdbcTemplate.queryForObject(query, new Object[] {}, Long.class)!=null?jdbcTemplate.queryForObject(query, new Object[] {}, Long.class):0;  //jdbcTemplate.queryForLong(query);			
		 }catch(Exception e){
			 logger.error("Error in getSumOfTeacherLegends() of IOLReportCardDAOImpl" + e);
			 e.printStackTrace();
			 return 0;
		}
		return sumOfTeacherLegends;
	  }
	 @Override
	 public long getNoOfTeacherLegends(long learningIndicatorId){
		 long noOfTeacherLegends=0;
		 try{
			 String query = "select count(teacher_score) from  learning_indicator_values where learning_indicator_id="+learningIndicatorId;
				noOfTeacherLegends =  jdbcTemplate.queryForObject(query, new Object[] {}, Long.class)!=null?jdbcTemplate.queryForObject(query, new Object[] {}, Long.class):0;  //jdbcTemplate.queryForLong(query);
					
		 }catch(Exception e){
			logger.error("Error in getNoOfTeacherLegends() of IOLReportCardDAOImpl" + e);
			e.printStackTrace();
			return 0;
		}
		return noOfTeacherLegends; 
	 }
	 @Override
	 public boolean gradeStudentIOLSectionToTeacher(long learIndicatorId,String leScore){
		 try{
			 String query = "update learning_indicator set status=?,le_score=? where learning_indicator_id=?";	
			 @SuppressWarnings("unused")		
			 int out = jdbcTemplate.update(query, WebKeys.LP_TRUE,leScore,learIndicatorId);
		 }catch(Exception e)
		 {
			 logger.error("Error in submitStudentIOLReportCardToTeacher() of IOLReportCardDAOImpl", e);
			 return false;
		 }
		 return true;		
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public void gradeStudentIOLSubCriteriaToTeacher(long learIndicatorId,long learningValuesId){
		 try{
			 String query2 = "update learning_indicator_values set submit_status=?  where learning_values_id=?";			
			 @SuppressWarnings("unused")
			 int out2 = jdbcTemplate.update(query2, WebKeys.LP_TRUE,learningValuesId);
			 List<LearningIndicatorValues> listLearnIndicatorValues = new ArrayList<LearningIndicatorValues>();
			 try {
				 listLearnIndicatorValues = (List<LearningIndicatorValues>) getHibernateTemplate()
						 .find("from LearningIndicatorValues where learningIndicator.learningIndicatorId="+learIndicatorId+" and submitStatus='false'");
			 } catch (Exception e) {
				 logger.error("Error in submitStudentIOLSubCriteriaToTeacher() of IOLReportCardDAOImpl"
						 + e);
			 }
			 if (listLearnIndicatorValues.size() == 0){
				 String query = "update learning_indicator set status=?  where learning_indicator_id=?";
				 @SuppressWarnings("unused")
				 int out = jdbcTemplate.update(query, WebKeys.LP_TRUE,learIndicatorId);
			 }
		 }catch(Exception e)
		 {
			 logger.error("Error in submitStudentIOLReportCardToTeacher() of IOLReportCardDAOImpl", e);			 
		 }				
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean checkIOLReportStatus(long iolReportId){
		 boolean status=false;
		 List<LearningIndicator> listLearnIndicator = new ArrayList<LearningIndicator>();
		 try {
			 listLearnIndicator = (List<LearningIndicator>) getHibernateTemplate()
					 .find("from LearningIndicator where iolReport.iolReportId="+iolReportId+" and status is null");				
			 if (listLearnIndicator.size() == 0)
				 status= true;
											
		 } catch (Exception e) {
			 logger.error("Error in checkIOLReportStatus() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return status;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean checkIOLGradeStatus(long iolReportId){
		 boolean status=false;
		 List<LearningIndicator> listLearnIndicator = new ArrayList<LearningIndicator>();
		 try {
			 listLearnIndicator = (List<LearningIndicator>) getHibernateTemplate().find("from LearningIndicator where iolReport.iolReportId="+iolReportId+" and (status is null or status='false')");			 
			 if (listLearnIndicator.size() == 0)
				 status= true;												
		 } catch (Exception e) {
			 logger.error("Error in checkIOLGradeStatus() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return status;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<LearningIndicator> getStudentIOLReportByIOLReportId(long studentId,long iolReportId){
		 List<LearningIndicator> listLearnIndicator = new ArrayList<LearningIndicator>();
		 try {
			 listLearnIndicator = (List<LearningIndicator>) getHibernateTemplate()
					 .find("from LearningIndicator where iolReport.student.studentId="+studentId+" and iolReport.iolReportId="+iolReportId+" and status is not null order by legendCriteria.legendCriteriaId");
		 } catch (Exception e) {
			 logger.error("Error in getStudentIOLReportByIOLReportId() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return listLearnIndicator;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<MulYrLegend> getMultiYearLegends(){
		 List<MulYrLegend> listMulYrLegends = new ArrayList<MulYrLegend>();
		 try {
			 listMulYrLegends = (List<MulYrLegend>) getHibernateTemplate()
					 .find("from MulYrLegend order by mulYrLegendId desc");
		 } catch (Exception e) {
			 logger.error("Error in getMultiYearLegends() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return listMulYrLegends; 
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<LearningIndicator> getStudentIOLScoresByCriteriaId(long studentId,long legendCriteriaId){
		 List<LearningIndicator> listLearnIndicator = new ArrayList<LearningIndicator>();
		 try {					
			 listLearnIndicator = (List<LearningIndicator>) getHibernateTemplate()
					 .find("from LearningIndicator where iolReport.student.studentId="+studentId+" and iolReport.isMulYearReport='yes' and legendCriteria.legendCriteriaId="+legendCriteriaId+" order by iolReport.classStatus.section.gradeClasses.grade.masterGrades.masterGradesId");
		 } catch (Exception e) {
			 logger.error("Error in getStudentIOLScoresByCriteriaId() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return listLearnIndicator;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public long chkIOLReportExistsByCsIdStudentId(long csId,long studentId){
		 long status=0;
		 List<IOLReport> listIOLReport = new ArrayList<IOLReport>();
		 try {
			 listIOLReport = (List<IOLReport>) getHibernateTemplate()
					 .find("from IOLReport where classStatus.csId="+csId+" and student.studentId="+studentId+"");			 
			 if (listIOLReport.size()>0)
				 status= 1;			 
		 } catch (Exception e) {
			 logger.error("Error in chkIOLReportExistsByCsIdStudentId() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return status;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public IOLReport getTrimesterId(long csId,long studentId){
		 IOLReport iolReport=new IOLReport();
		 List<IOLReport> listIOLReport = new ArrayList<IOLReport>();
		 try {
			 listIOLReport = (List<IOLReport>) getHibernateTemplate()
					 .find("from IOLReport r where r.classStatus.csId="+csId+" and r.student.studentId="+studentId+" and r.trimester.trimesterId in (select max(ior.trimester.trimesterId) from IOLReport ior where ior.classStatus.csId="+csId+" and ior.student.studentId="+studentId+")");
			 if (listIOLReport.size() > 0)
				 iolReport= listIOLReport.get(0);
			 else
				 iolReport=new IOLReport();						
		 } catch (Exception e) {
			 logger.error("Error in getTrimesterId() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return iolReport; 
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public Trimester getTrimesterId(long trimesterId){
		 List<Trimester> trimester = new ArrayList<Trimester>();
		 try {
			 trimester = (List<Trimester>) getHibernateTemplate().find(
					 "from Trimester where trimesterId="
							 + trimesterId + "");
		 } catch (Exception e) {
			 logger.error("Error in getTrimesterId() of IOLReportCardDAOImpl"
					 + e);
		 }
		 if (trimester.size() > 0)
			 return trimester.get(0);
		 else
			 return new Trimester();
	 }
	 
	 @Override
	 public boolean addDefaultRubricsToGrades(List<LERubrics> leRubrics){
		 try{
			 for(LERubrics rubrics : leRubrics){
				 getHibernateTemplate().saveOrUpdate(rubrics);
			 }
		 }catch(DataException e){
			 logger.error("Error in addDefaultRubricsToGrades() of IOLReportCardDAOImpl", e);
		 }
		 return true;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<Legend> getDefaultLegendsByCriteria(long criteriaId) {
		 List<Legend> legends = new ArrayList<Legend>();
		 try {
			 legends = (List<Legend>) getHibernateTemplate().find("from Legend where isDefault='no' and legendSubCriteria.legendCriteria.legendCriteriaId=  "+criteriaId
					 + " and createdBy.regId is null  order by legendId desc");
		 } catch (Exception e) {
			 logger.error("Error in getLegends() of IOLReportCardDAOImpl" + e);
		 }
		 return legends;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<CAASPPScores> getCAASPPScoresByStudentId(long studentId,long caasppTypesId, String className){
		 List<CAASPPScores> listCAASPPScores = new ArrayList<CAASPPScores>();
		 try {
			 listCAASPPScores = (List<CAASPPScores>) getHibernateTemplate()
					 .find("from CAASPPScores where student.studentId="+studentId+" and caasppType.caasppTypesId="+caasppTypesId+""
							 + " order by grade.masterGrades.masterGradesId");
		 } catch (Exception e) {
			 logger.error("Error in getCAASPPScoresByStudentId() of IOLReportCardDAOImpl"
					 + e);
		 }
		 return listCAASPPScores;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<Grade> getSchoolGradesForIOL(long schoolId){
		 List<Grade> agList = new ArrayList<Grade>();
		 try {
			 agList = (List<Grade>) getHibernateTemplate().find(
					 "from Grade where status='"+WebKeys.ACTIVE+"' and school_id="
							 + schoolId+" and masterGrades.masterGradesId between 3 and 8");
		 } catch (DataAccessException e) {
			 logger.error("Error in getSchoolGradesForIOL() of IOLReportCardDAOImpl" + e);
			 e.printStackTrace();
		 }
		 return agList;
	 }
	 @Override
	 public void setTrimesterIdForStudent(long studentId,long csId,long trimesterId){
		 try {
			 String query2 = "update iol_report set is_mul_yr_rpt='no' where student_id=? and cs_id=? and trimester_id=?";
			 @SuppressWarnings("unused")
			 int out2 = jdbcTemplate.update(query2,studentId,csId,trimesterId);
		 } catch(Exception e)
		 {
			 logger.error("Error in setTrimesterIdForStudent() of IOLReportCardDAOImpl", e);			 
		 }
	 }

	 @SuppressWarnings("unchecked")
	 @Override
	 public List<Legend> getSubcriteriaRubrics(long subId) {
		 List<Legend> legendList = new ArrayList<Legend>();
		 UserRegistration userReg = (UserRegistration) session
				 .getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		 try {
			 legendList = (List<Legend>) getHibernateTemplate()
					 .find("from Legend where legendSubCriteria.legendSubCriteriaId="+subId+" and createdBy.regId="+userReg.getRegId()
					 +" and isDefault='"+WebKeys.STEM_NO.toLowerCase()+"' and status='"+WebKeys.ACTIVE+"'");
		 } catch (Exception e) {
			 logger.error("Error in getSubcriteriaRubrics() of IOLReportCardDAOImpl"
						+ e);
		 }
		 return legendList;
	 }

	 @Override
	 public boolean updateIOLRubrics(List<Legend> legendList) {
		 try {
			 Session session = sessionFactory.openSession();
			 Transaction tx = (Transaction) session.beginTransaction();
			 for(Legend legend : legendList) {
				 session.saveOrUpdate(legend);
			 }					
			 tx.commit();
			 session.close();
			 return true;
		 } catch (HibernateException e) {
			 logger.error("Error in updateIOLRubrics() of IOLReportCardDAOImpl" + e);
			 return false;
		 }
	 }	
	 @Override
	 public boolean assignIOLRubricToGrade(AssignLegendRubrics assRubrics){
		 try {
			 Session session = sessionFactory.openSession();
			 Transaction tx = (Transaction) session.beginTransaction();
			 session.saveOrUpdate(assRubrics);
			 tx.commit();
			 session.close();
			 return true;
		 } catch (HibernateException e) {
			 logger.error("Error in assignIOLRubricToGrade() of IOLReportCardDAOImpl" + e);
			 return false;
		 }
	 }

	 @SuppressWarnings("unchecked")
	 @Override
	 public List<Legend> getCriteriaRubrics(long createdBy, long subCriteriaId) {
		 List<Legend> legendList = new ArrayList<Legend>();
		 try {
			 if(createdBy>0){
				 legendList = (List<Legend>) getHibernateTemplate()
						 .find("from Legend where legendSubCriteria.legendSubCriteriaId = "+subCriteriaId
								 +" and isDefault='"+WebKeys.STEM_NO.toLowerCase()+"'"
								 +" and status='"+WebKeys.ACTIVE+"' and createdBy.regId="+createdBy);
			 }else{
				 legendList = (List<Legend>) getHibernateTemplate()
						 .find("from Legend where legendSubCriteria.legendSubCriteriaId = "+subCriteriaId
								 +" and isDefault='"+WebKeys.STEM_NO.toLowerCase()+"'"
								 +" and status='"+WebKeys.ACTIVE+"' and createdBy.regId="+null);
			 }
		 } catch (Exception e) {
			 logger.error("Error in getCriteriaRubrics() of IOLReportCardDAOImpl"+ e);
		 }
		 return legendList;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<Trimester> getAllTrimesters(){
		 List<Trimester> trimesList = new ArrayList<Trimester>();
		 try {
			 trimesList = (List<Trimester>) getHibernateTemplate().find(
					 "from Trimester");
		 } catch (DataAccessException e) {
			 logger.error("Error in getAllTrimesters() of IOLReportCardDAOImpl" + e);
			 e.printStackTrace();
		 }
		 return trimesList;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public boolean checkAlreadyAssignRubric(long gradeId,long trimesterId,long teacherRegId){
		 List<AssignLegendRubrics> assRubrics = new ArrayList<AssignLegendRubrics>();
		 try {
			 assRubrics = (List<AssignLegendRubrics>) getHibernateTemplate().find("from AssignLegendRubrics where trimester.trimesterId="
					 + trimesterId + " and grade.gradeId="+gradeId+" and createdBy.regId="+teacherRegId+"");
		 } catch (Exception e) {
			 logger.error("Error in checkAlreadyAssignRubric() of IOLReportCardDAOImpl"
					 + e);
		 }
		 if (assRubrics.size() > 0)
			 return true;
		 else
			 return false;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public IOLReport getTrimesterByIolReportId(long iolReportId){
		 List<IOLReport> iolReport = new ArrayList<IOLReport>();
		 try {
			 iolReport = (List<IOLReport>) getHibernateTemplate().find(
					 "from IOLReport where iolReportId="+iolReportId+"");			 
		 } catch (DataAccessException e) {
			 logger.error("Error in getTrimesterByIolReportId() of IOLReportCardDAOImpl" + e);
			 e.printStackTrace();
		 }
		 if (iolReport.size() > 0)
			 return iolReport.get(0);
		 else
			 return new IOLReport();
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public long getReferRegId(long gradeId,long trimesterId,long createdBy){
		 List<AssignLegendRubrics> lstAssRubrics = new ArrayList<AssignLegendRubrics>();
		 try {
			 lstAssRubrics = (List<AssignLegendRubrics>) getHibernateTemplate().find(
					 "from AssignLegendRubrics where grade.gradeId="+gradeId+" and createdBy.regId="+createdBy+" and trimester.trimesterId="+trimesterId+"");				
		 } catch (Exception e) {
			 logger.error("Error in getReferRegId() of IOLReportCardDAOImpl" + e);
			 e.printStackTrace();
		 }
		 if (lstAssRubrics.size() > 0){
			 UserRegistration use=lstAssRubrics.get(0).getReferRegId();
			 if(use!=null)
				 return use.getRegId();
			 else
				 return 0;
		 }			
		 else
			 return 0;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<Legend> getRubricValuesByUserId(long referRegId,long subCriteriaId, long gradeId){
		 List<Legend> lstlegendRubrics = new ArrayList<Legend>();
		 UserRegistration  userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		 try {
			 if(referRegId==0){
				 Grade grade = gradesDAO.getSchoolGrade(gradeId);
				 lstlegendRubrics = (List<Legend>) getHibernateTemplate().find(
						 "from Legend where createdBy.regId=7 and district.districtId="+ userReg.getSchool().getDistrict().getDistrictId()+ // regId  implies appmanager
						 " and legendSubCriteria.legendSubCriteriaId="+subCriteriaId+" and isDefault='no'  "
						 +" and " +grade.getMasterGrades().getMasterGradesId() + " between fromGrade and toGrade"
						 + " and status='active' order by legendValue");
				 if(lstlegendRubrics.isEmpty()){
					 lstlegendRubrics = (List<Legend>) getHibernateTemplate().find(
							 "from Legend where createdBy is null and legendSubCriteria.legendSubCriteriaId="+subCriteriaId+" and isDefault='no' and status='active' "
									 + "and district.districtId is null order by legendValue");
				 }
			 }else{
				 lstlegendRubrics = (List<Legend>) getHibernateTemplate().find(
						 "from Legend where createdBy.regId="+referRegId+" and legendSubCriteria.legendSubCriteriaId="+subCriteriaId+" and isDefault='no' "
								 + " and status='active' order by legendValue");
			 }
		 } catch (DataAccessException e) {
			 logger.error("Error in getTrimesterByIolReportId() of IOLReportCardDAOImpl" + e);
			 e.printStackTrace();
		 }
		 return lstlegendRubrics;
	 }	

	 @SuppressWarnings("unchecked")
	 @Override
	 public List<LegendSubCriteria> getLegendSubCriterias(
			 long legendCriteriaId, long masterGradeId, long districtId) {
		 List<LegendSubCriteria> legendSubCriterias = new ArrayList<LegendSubCriteria>();
		 try {
			 legendSubCriterias = (List<LegendSubCriteria>) getHibernateTemplate()
					 .find("Select ls.legendSubCriteria from LegendSubCriteriaDistrictWise ls where ls.legendSubCriteria.legendCriteria.legendCriteriaId="
							 + legendCriteriaId + " and ls.masterGrades.masterGradesId = "+masterGradeId+" and ls.district.districtId="+districtId+
							 " order by ls.legendSubCriteria.legendCriteria.legendCriteriaId, ls.legendSubCriteria.legendSubCriteriaId");
			 return legendSubCriterias;
		 } catch (Exception e) {
			 e.printStackTrace();
			 logger.error("Error in getLegendSubCriteriasByCriteriaId() of IOLReportCardDAOImpl"
					 + e);
			 
		 }
		 return legendSubCriterias;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public CAASPPTypes getCAASPPTypesByCaaSppTypesId(long caasppTypesId){
		 List<CAASPPTypes> listCAASPPTypes = new ArrayList<CAASPPTypes>();
		 try {
			 listCAASPPTypes = (List<CAASPPTypes>) getHibernateTemplate()
					 .find("from CAASPPTypes where caasppTypesId="+caasppTypesId+"");
		 } catch (Exception e) {
			 logger.error("Error in getCAASPPTypesByCaaSppTypesId() of IOLReportCardDAOImpl"
					 + e);
		 }
		 if (listCAASPPTypes.size() > 0)
			 return listCAASPPTypes.get(0);
		 else
			 return new CAASPPTypes();
				
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<LearningIndicatorValues> getWholeClassCriteriaReport(long csId,long trimesterId,long criteriaId) {
		 List<LearningIndicatorValues> listLearnIndicatorValues = new ArrayList<LearningIndicatorValues>();
		 try {
			 
			 listLearnIndicatorValues = (List<LearningIndicatorValues>) getHibernateTemplate()
					 .find("from LearningIndicatorValues where learningIndicator.iolReport.classStatus.csId="+csId+" and learningIndicator.iolReport.trimester.trimesterId="+trimesterId+" and "
					 		+ "learningIndicator.legendCriteria.legendCriteriaId="+criteriaId+" order by learningIndicator.iolReport.student.userRegistration.lastName");
		 } catch (Exception e) {
			 logger.error("Error in getWholeClassCriteriaReport() of IOLReportCardDAOImpl"
					 + e);
		 }
			return listLearnIndicatorValues;
	 }
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<LearningIndicatorValues> getStudentCriteriaReport(long studentId,long trimesterId,long criteriaId){
		 List<LearningIndicatorValues> listLearnIndValues = new ArrayList<LearningIndicatorValues>();
		  try {
				 listLearnIndValues = (List<LearningIndicatorValues>) getHibernateTemplate()
						 .find("from LearningIndicatorValues where learningIndicator.iolReport.student.studentId="
								 + studentId + " and learningIndicator.iolReport.trimester.trimesterId="+trimesterId+" and learningIndicator.legendCriteria.legendCriteriaId="+criteriaId+"order by legendSubCriteria.legendSubCriteriaId");
			
		   	   } catch (Exception e) {
				 logger.error("Error in getStudentCriteriaReport() of IOLReportCardDAOImpl"
						 + e);
			 }
			 return listLearnIndValues; 
	 }
}