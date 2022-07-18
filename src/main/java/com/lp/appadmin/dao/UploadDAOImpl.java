package com.lp.appadmin.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.model.CAASPPScores;
import com.lp.model.CAASPPTypes;
import com.lp.model.ColumnHeaders;
import com.lp.model.District;
import com.lp.model.FormativeAssessmentCategory;
import com.lp.model.FormativeAssessmentKeywords;
import com.lp.model.FormativeAssessmentRubric;
import com.lp.model.FormativeAssessments;
import com.lp.model.FormativeAssessmentsColumnHeaders;
import com.lp.model.FormativeAssessmentsGradeWise;
import com.lp.model.Legend;
import com.lp.model.MasterGrades;
import com.lp.model.School;
import com.lp.model.StarScores;
import com.lp.model.StemAreas;
import com.lp.model.StemConceptSubCategory;
import com.lp.model.StemConceptSubCategoryItems;
import com.lp.model.StemGradeStrands;
import com.lp.model.StemPerformanceIndicator;
import com.lp.model.StemStrandConceptDetails;
import com.lp.model.StemStrandConcepts;
import com.lp.model.Trimester;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Repository("uploadDao")
public class UploadDAOImpl extends CustomHibernateDaoSupport implements UploadDAO {
	static final Logger logger = Logger.getLogger(UploadDAOImpl.class);
	FileOutputStream out;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
		try {
			String commonFilesPath = FileUploadUtil.getLpCommonFilesPath();
			String fullPath = commonFilesPath+File.separator+WebKeys.EXCEL_UPLOAD;
			File file = new File(fullPath);
			if(!file.exists())
				file.mkdir();
			fullPath = fullPath+File.separator+WebKeys.FORMATIVE_ASSESSMENT_LOG;
			out = new FileOutputStream(fullPath,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean saveLegendFromFile(List<Legend> legendList, List<MasterGrades> masterGrades, String check) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for(Legend legend : legendList) {
				session.saveOrUpdate(legend);
			}
			if(!check.equalsIgnoreCase(WebKeys.LP_TAB_UPLOAD_IOLRUBRIC)){
				for(MasterGrades mg: masterGrades){
					for(Legend legend : legendList) {
						StemPerformanceIndicator spi = new StemPerformanceIndicator();
						spi.setMasterGradeId(mg);
						spi.setLegend(legend);
						spi.setStatus(WebKeys.ACTIVE);
						session.saveOrUpdate(spi);
					}
				}
			}			
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			logger.error("Error in saveLegendFromFile() of UploadDAOImpl" + e);
			return false;
		}
	}
	public long saveFormativeAssessment(FormativeAssessments fa) throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		long formativeAssessmentsId = 0;
		String query = "select formative_assessments_id from formative_assessments where title='"+fa.getTitle()+"'";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while (rs.next()) {
			formativeAssessmentsId = rs.getInt(1);
			fa.setFormativeAssessmentsId(formativeAssessmentsId);
	    }
		try {
			session.saveOrUpdate(fa);
			tx.commit();
			session.close();
			return fa.getFormativeAssessmentsId();
		} catch (HibernateException e) {
			tx.rollback();
			session.close();
			logger.error("Error in saveFormativeAssessment() of UploadDAOImpl" + e);
			String logError = "Unable to save Title:  '"+fa.getTitle()+"'.\r\n\n";
			out.write(logError.getBytes());
			return 0;
		}
	}
	
	public boolean saveFormativeAssessmentCategory(FormativeAssessmentCategory fac) throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		long formativeAssessmentCategoryId = 0;
		String query = "select formative_assessment_category_id from formative_assessment_categories where category=? and formative_assessment_id="+fac.getFormativeAssessments().getFormativeAssessmentsId();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,fac.getCategory());
		while (rs.next()) {
			formativeAssessmentCategoryId = rs.getInt(1);
			fac.setFormativeAssessmentSubCateId(formativeAssessmentCategoryId);
	    }
		try {
			session.saveOrUpdate(fac);
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			tx.rollback();
			session.close();
			String logError = "Unable to save Category: '"+fac.getCategory()+"'.\r\n\n";
			out.write(logError.getBytes());
			logger.error("Error in saveFormativeAssessmentCategory() of UploadDAOImpl" + e);
			return false;
		}
	}
	
	@Override
	public boolean saveFormativeAssessmentsGradeWise(FormativeAssessmentsGradeWise fag) throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		long formativeAssessmentsGradeWiseId = 0;
		String query = "select formative_assessments_grade_wise_id from formative_assessments_grade_wise where master_grades_id="+fag.getMasterGrades().getMasterGradesId()+" and formative_assessment_id="+fag.getFormativeAssessments().getFormativeAssessmentsId();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while (rs.next()) {
			formativeAssessmentsGradeWiseId = rs.getInt(1);
			fag.setFormativeAssessmentsGradeWiseId(formativeAssessmentsGradeWiseId);
	    }
		try {
			session.saveOrUpdate(fag);
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			tx.rollback();
			session.close();
			logger.error("Error in saveFormativeAssessmentsGradeWise() of UploadDAOImpl" + e);
			return false;
		}
	}
	
	@Override
	public boolean saveFormativeAssessmentKeywords(FormativeAssessmentKeywords fak) throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		long formativeAssessmentKeywordsId = 0;
		String query = "select formative_assessment_keywords_id from formative_assessment_keywords where keyword='"+fak.getKeyword()+"' and formative_assessment_id="+fak.getFormativeAssessments().getFormativeAssessmentsId();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while (rs.next()) {
			formativeAssessmentKeywordsId = rs.getInt(1);
			fak.setFormativeAssessmentKeywordsId(formativeAssessmentKeywordsId);
	    }
		try {
			session.saveOrUpdate(fak);
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			tx.rollback();
			session.close();
			String logError = "Unable to save keyword: '"+fak.getKeyword()+"'.\r\n\n";
			out.write(logError.getBytes());
			logger.error("Error in saveFormativeAssessmentKeywords() of UploadDAOImpl" + e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ColumnHeaders getColumnHeaderByColumn(String columnName) throws Exception {
		List<ColumnHeaders> columnHeadersLt = new ArrayList<ColumnHeaders>();
		try {
			columnHeadersLt = (List<ColumnHeaders>) getHibernateTemplate().find("from ColumnHeaders where columnName='"+ columnName.trim() +"'");
		} catch (Exception e) {
			logger.error("Error in getColumnHeaderByColumn() of StemCurriculumDAOImpl" + e);
		}
		if(columnHeadersLt.size() > 0){
			return columnHeadersLt.get(0);
		}else{
			String logError = "Rubric Column: \""+columnName+"\" not matched with categories.\r\n\n";
			out.write(logError.getBytes());
			return new ColumnHeaders();
		}
	}

	@Override
	public boolean saveFormativeAssessmentsColumnHeaders(FormativeAssessmentsColumnHeaders fach) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		long formativeAssessmentsColumnHeadersId = 0;
		String query = "select formative_assessment_column_headers_id from formative_assessment_column_headers where column_header_id="+fach.getColumnHeaders().getColumnHeaderId()+" and formative_assessment_id="+fach.getFormativeAssessments().getFormativeAssessmentsId();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while (rs.next()) {
			formativeAssessmentsColumnHeadersId = rs.getInt(1);
			fach.setFormativeAssessmentsColumnHeadersId(formativeAssessmentsColumnHeadersId);
	    }
		try {
			session.saveOrUpdate(fach);
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			tx.rollback();
			session.close();
			String logError = "Unable to save Rubric Column: \""+fach.getColumnHeaders().getColumnName()+"\".\r\n\n";
			out.write(logError.getBytes());
			logger.error("Error in saveFormativeAssessmentsColumnHeaders() of UploadDAOImpl" + e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FormativeAssessmentCategory getFormativeAssessmentCategoryByCategory(long formativeAssessmentsId, String category) throws Exception{
		List<FormativeAssessmentCategory> facLt = new ArrayList<FormativeAssessmentCategory>();
		try {
			facLt = (List<FormativeAssessmentCategory>) getHibernateTemplate().find("from FormativeAssessmentCategory where formativeAssessments.formativeAssessmentsId="+formativeAssessmentsId+" and category='"+category+"'");

		} catch (Exception e) {
			logger.error("Error in getFormativeAssessmentCategoryByCategory() of StemCurriculumDAOImpl" + e);
		}
		if(facLt.size() > 0){
			return facLt.get(0);
		}else{
			String logError = "Unable to save '"+category+"' rubric data: Please verify 'forrmative_rubric' sheet.\r\n\n";
			out.write(logError.getBytes());
			return new FormativeAssessmentCategory();
		}
	}
	
	public boolean saveFormativeAssessmentRubric(FormativeAssessmentRubric far) throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		long formativeAssessmentRubricId = 0;
		String query = "select formative_assessment_rubric_id from formative_assessment_rubric where score="+far.getScore()+" and formative_assessment_category_id="+far.getFormativeAssessmentCategory().getFormativeAssessmentSubCateId();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while (rs.next()) {
			formativeAssessmentRubricId = rs.getInt(1);
			far.setFormativeAssessmentRubricId(formativeAssessmentRubricId);
	    }
		try {
			session.saveOrUpdate(far);
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			tx.rollback();
			session.close();
			logger.error("Error in saveFormativeAssessmentRubric() of UploadDAOImpl" + e);
			return false;
		} 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StemAreas> getAllStemAreas() {
		List<StemAreas> stemAreasLt = new ArrayList<StemAreas>();
		try {
			stemAreasLt = (List<StemAreas>) getHibernateTemplate().find("from StemAreas");
		} catch (Exception e) {
			logger.error("Error in getAllStemAreas() of UploadDAOImpl");
		}
		return stemAreasLt;
	}
	
	@Override
	public boolean uploadStrandsFile(List<StemGradeStrands> gradeStrandsLt) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for(StemGradeStrands sgs: gradeStrandsLt){
				session.saveOrUpdate(sgs);
				for(StemStrandConcepts ssc : sgs.getStemStrandConceptsLt()) {					
					session.saveOrUpdate(ssc);
					for(StemStrandConceptDetails sscd: ssc.getStemStrandConceptDetails()){
						session.saveOrUpdate(sscd);
						for(StemConceptSubCategory subCat: sscd.getStemConceptSubCategory()){
							session.saveOrUpdate(subCat);
							for(StemConceptSubCategoryItems catItems: subCat.getStemConceptSubCategoryItems()){
								session.saveOrUpdate(catItems);
							}
						}
					}
				}
			}
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			logger.error("Error in uploadStrandsFile() of UploadDAOImpl "+e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<School> getAllSchoolStates() {
		List<School> schoolList = new ArrayList<School>();
		try {
			schoolList = (List<School>) getHibernateTemplate().find("from School group by states.stateId");
		} catch (Exception e) {
			logger.error("Error in getAllSchoolStates() of UploadDAOImpl");
		}
		return schoolList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<District> getDistricts() {
		List<District> districts = new ArrayList<District>();
		try {
			districts = (List<District>) getHibernateTemplate().find("from District");
		} catch (Exception e) {
			logger.error("Error in getDistricts() of UploadDAOImpl");
		}
		return districts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Trimester> getTrimesters() {
		List<Trimester> trimesters = new ArrayList<Trimester>();
		try {
			trimesters = (List<Trimester>) getHibernateTemplate().find("from Trimester");
		} catch (Exception e) {
			logger.error("Error in getTrimesters() of UploadDAOImpl");
		}
		return trimesters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CAASPPTypes> getCAASPPTypes() {
		List<CAASPPTypes> cAASPPTypes = new ArrayList<CAASPPTypes>();
		try {
			cAASPPTypes = (List<CAASPPTypes>) getHibernateTemplate().find("from CAASPPTypes where caasppType in('Star Reading','Star Math')");
		} catch (Exception e) {
			logger.error("Error in getCAASPPTypes() of UploadDAOImpl");
		}
		return cAASPPTypes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<School> getAllSchools() {
		List<School> schoolList = new ArrayList<School>();
		try {
			schoolList = (List<School>) getHibernateTemplate().find("from School");
		} catch (Exception e) {
			logger.error("Error in getAllSchools() of UploadDAOImpl");
		}
		return schoolList;
	}

	@Override
	public boolean saveStarUploadFile(List<StarScores> starScores) {		
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for(StarScores ss: starScores){
				session.saveOrUpdate(ss);
			}			
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {			
			logger.error("Error in saveStarUploadFile() of UploadDAOImpl" + e);
			return false;
		} 
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CAASPPTypes> getCaasppElaMathTypes() {
		List<CAASPPTypes> cAASPPTypes = new ArrayList<CAASPPTypes>();
		try {
			cAASPPTypes = (List<CAASPPTypes>) getHibernateTemplate().find("from CAASPPTypes where caasppType "
					+ "in('"+WebKeys.CAASPP_TYPE_ELA+"','"+WebKeys.CAASPP_TYPE_MATH+"')");
		} catch (Exception e) {
			logger.error("Error in getCaasppElaMathTypes() of UploadDAOImpl");
		}
		return cAASPPTypes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CAASPPScores> getAllCaasppScores() {
		List<CAASPPScores> cAASPPScores = new ArrayList<CAASPPScores>();
		try {
			cAASPPScores = (List<CAASPPScores>) getHibernateTemplate().find("from CAASPPScores");
		} catch (Exception e) {
			logger.error("Error in getAllCaasppScores() of UploadDAOImpl");
		}
		return cAASPPScores;
	}

	@Override
	public boolean saveCAASPPUploadFile(List<CAASPPScores> cAASPPScores) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for(CAASPPScores cas: cAASPPScores){
				session.saveOrUpdate(cas);
			}			
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {			
			logger.error("Error in saveCAASPPUploadFile() of UploadDAOImpl" + e);
			return false;
		} 
	}
}
