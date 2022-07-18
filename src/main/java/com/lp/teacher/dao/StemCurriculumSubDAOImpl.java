package com.lp.teacher.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lp.admin.dao.GradesDAO;
import com.lp.model.FormativeAssessmentCategory;
import com.lp.model.FormativeAssessmentKeywords;
import com.lp.model.FormativeAssessmentRubric;
import com.lp.model.FormativeAssessments;
import com.lp.model.FormativeAssessmentsColumnHeaders;
import com.lp.model.FormativeAssessmentsGradeWise;
import com.lp.model.FormativeAssessmentsUnitWise;
import com.lp.model.Grade;
import com.lp.model.IpalResources;
import com.lp.model.LegendCriteria;
import com.lp.model.StemPerformanceIndicator;
import com.lp.model.StemUnitPerformanceIndicator;
import com.lp.model.UnitStemConcepts;
import com.lp.model.UnitStemStrands;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("StemCurriculumSubDAO")
public class StemCurriculumSubDAOImpl extends CustomHibernateDaoSupport implements StemCurriculumSubDAO {
	
		@Autowired
		private SessionFactory sessionFactory;
		@Autowired
		HttpSession session;
		@Autowired
		HttpServletRequest request;
		@Autowired
		private GradesDAO gradeDAO;
		
		@SuppressWarnings("unchecked")
		@Override
		public List<LegendCriteria> getStemLegendCriteria() {
			List<LegendCriteria> legendCriteria = new ArrayList<LegendCriteria>();
			try {
				legendCriteria = (List<LegendCriteria>) getHibernateTemplate()
						.find("from LegendCriteria where legendCriteriaName not in ('"+WebKeys.LITERACIES+"', '"+WebKeys.TECHNOLOGY+"')");
			} catch (Exception e) {
				logger.error("Error in getStemLegendCriteria() of StemCurriculumSubDAOImpl"+ e);
			}
			return legendCriteria;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<StemUnitPerformanceIndicator> getStemUnitPerformanceIndicator(long stemUnitId, long lCriteriaId) {
			List<StemUnitPerformanceIndicator> stemUnitPerformanceIndicator = new ArrayList<StemUnitPerformanceIndicator>();
			try {
				stemUnitPerformanceIndicator = (List<StemUnitPerformanceIndicator>) getHibernateTemplate()
						.find("from StemUnitPerformanceIndicator where stemUnit.stemUnitId = "+stemUnitId
								+" and legendSubCriteria.legendCriteria.legendCriteriaId ="+lCriteriaId);
			} catch (Exception e) {
				logger.error("Error in getStemUnitPerformanceIndicator() of StemCurriculumSubDAOImpl"+ e);
			}
			return stemUnitPerformanceIndicator;
		}

		@Override
		public boolean saveStemIndicator(StemUnitPerformanceIndicator stemUnitIndicator) {
			try {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(stemUnitIndicator);						
				tx.commit();
				session.close();
				return true;
			} catch (HibernateException e) {
				logger.error("Error in saveStemIndicator() of StemCurriculumSubDAOImpl" + e);
				return false;
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<StemPerformanceIndicator> getStemPerIndicators(long gradeId, long subCriteriaId) {
			List<StemPerformanceIndicator> stemUnitPerformanceIndicator = new ArrayList<StemPerformanceIndicator>();
			try {
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				Grade grade = gradeDAO.getGrade(gradeId);
				stemUnitPerformanceIndicator = (List<StemPerformanceIndicator>) getHibernateTemplate()
						.find("from StemPerformanceIndicator where masterGradeId = "+grade.getMasterGrades().getMasterGradesId()
								+" and legend.legendSubCriteria.legendSubCriteriaId ="+subCriteriaId
								+" and status='"+WebKeys.ACTIVE+"' and legend.district.districtId="+userReg.getSchool().getDistrict().getDistrictId());
			} catch (Exception e) {
				logger.error("Error in getStemPerIndicators() of StemCurriculumSubDAOImpl"+ e);
			}
			return stemUnitPerformanceIndicator;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<FormativeAssessmentsGradeWise> getFormativeAssessmentsGradeWise(long gradeId) {
			List<FormativeAssessmentsGradeWise> formativeAssGradeWise = new ArrayList<FormativeAssessmentsGradeWise>();
			try {
				Grade grade = gradeDAO.getGrade(gradeId);
				formativeAssGradeWise = (List<FormativeAssessmentsGradeWise>) getHibernateTemplate()
						.find("from FormativeAssessmentsGradeWise where masterGrades.masterGradesId = "+grade.getMasterGrades().getMasterGradesId()
								+" and status='"+WebKeys.ACTIVE+"'");
			} catch (Exception e) {
				logger.error("Error in getFormativeAssessmentsGradeWise() of StemCurriculumSubDAOImpl"+ e);
			}
			return formativeAssGradeWise;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<FormativeAssessmentsUnitWise> getFormativeAssessmentsUnitWise(long stemUnitId) {
			List<FormativeAssessmentsUnitWise> formativeAssUnitWise = new ArrayList<FormativeAssessmentsUnitWise>();
			try {
				formativeAssUnitWise = (List<FormativeAssessmentsUnitWise>) getHibernateTemplate()
						.find("from FormativeAssessmentsUnitWise where stemUnit.stemUnitId = "+stemUnitId);
			} catch (Exception e) {
				logger.error("Error in getFormativeAssessmentsUnitWise() of StemCurriculumSubDAOImpl"+ e);
			}
			return formativeAssUnitWise;
		}

		@Override
		public boolean saveformativeAssessUnit(FormativeAssessmentsUnitWise forAssessmentsUnitWise) {
			try {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(forAssessmentsUnitWise);						
				tx.commit();
				session.close();
				return true;
			} catch (HibernateException e) {
				logger.error("Error in saveformativeAssessUnit() of StemCurriculumSubDAOImpl" + e);
				return false;
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<IpalResources> getIpalResources(long gradeId) {
			List<IpalResources> iPalResources = new ArrayList<IpalResources>();
			try {
				Grade grade = gradeDAO.getGrade(gradeId);
				iPalResources = (List<IpalResources>) getHibernateTemplate()
						.find("from IpalResources where masterGrades.masterGradesId = "+grade.getMasterGrades().getMasterGradesId()
								+" and status = '"+WebKeys.ACTIVE+"'");
			} catch (Exception e) {
				logger.error("Error in getIpalResources() of StemCurriculumSubDAOImpl"+ e);
			}
			return iPalResources;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<FormativeAssessmentKeywords> getStemFormativeKeywords(long formativeAssessmentId) {
			List<FormativeAssessmentKeywords> keywords = new ArrayList<FormativeAssessmentKeywords>();
			try {
				keywords = (List<FormativeAssessmentKeywords>) getHibernateTemplate()
						.find("from FormativeAssessmentKeywords where formativeAssessments.formativeAssessmentsId = "+formativeAssessmentId);
			} catch (Exception e) {
				logger.error("Error in getStemFormativeKeywords() of StemCurriculumSubDAOImpl"+ e);
			}
			return keywords;
		}

		@SuppressWarnings("unchecked")
		@Override
		public FormativeAssessments getFormativeAssessmentById(long formativeAssessmentId) {
			List<FormativeAssessments> fAssessments = new ArrayList<FormativeAssessments>();
			try {
				fAssessments = (List<FormativeAssessments>) getHibernateTemplate()
						.find("from FormativeAssessments where formativeAssessmentsId = "+formativeAssessmentId);
			} catch (Exception e) {
				logger.error("Error in getFormativeAssessmentById() of StemCurriculumSubDAOImpl"+ e);
			}
			if(fAssessments.size()>0){
				return fAssessments.get(0);
			}else{
				return new FormativeAssessments();
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<FormativeAssessmentsColumnHeaders> getFormativeAssessmentColumnHeaders(
				long formativeAssessmentId) {
			List<FormativeAssessmentsColumnHeaders> fAColumnHeaders = new ArrayList<FormativeAssessmentsColumnHeaders>();
			try {
				fAColumnHeaders = (List<FormativeAssessmentsColumnHeaders>) getHibernateTemplate()
						.find("from FormativeAssessmentsColumnHeaders where formativeAssessments.formativeAssessmentsId = "+formativeAssessmentId);
																			
			} catch (Exception e) {
				logger.error("Error in getFormativeAssessmentColumnHeaders() of StemCurriculumSubDAOImpl"+ e);
			}
			return fAColumnHeaders;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<FormativeAssessmentRubric> getFormativeAssessmentRubric(
				long formativeAssessmentId) {
			List<FormativeAssessmentRubric> fARubrics = new ArrayList<FormativeAssessmentRubric>();
			try {
				fARubrics = (List<FormativeAssessmentRubric>) getHibernateTemplate()
						.find("from FormativeAssessmentRubric where formativeAssessmentCategory.formativeAssessments.formativeAssessmentsId = "+formativeAssessmentId);
			} catch (Exception e) {
				logger.error("Error in getFormativeAssessmentRubric() of StemCurriculumSubDAOImpl"+ e);
			}
			return fARubrics;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<FormativeAssessmentCategory> getFormativeAssessmentCategories(
				long formativeAssessmentId) {
			List<FormativeAssessmentCategory> fAssessmentCategories = new ArrayList<FormativeAssessmentCategory>();
			try {
				fAssessmentCategories = (List<FormativeAssessmentCategory>) getHibernateTemplate()
						.find("from FormativeAssessmentCategory where formativeAssessments.formativeAssessmentsId = "+formativeAssessmentId);
			} catch (Exception e) {
				logger.error("Error in getFormativeAssessmentCategories() of StemCurriculumSubDAOImpl"+ e);
			}
			return fAssessmentCategories;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public boolean removeUnitStandards(long strandsId) {
			List<UnitStemStrands> unitStemStrands = Collections.emptyList();
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			try {
				Query query = session.createQuery("from UnitStemStrands where unitStemStrandsId="+ strandsId);
				unitStemStrands= query.list();
			} catch (Exception e) {
				logger.error("Error in removeUnitStandards() of StemCurriculumSubDAOImpl"+ e);
			}
			if(unitStemStrands.size()>0){
				try {
					
					for(UnitStemConcepts usc:unitStemStrands.get(0).getUnitStemConceptsLt()){
						session.delete(usc);	
					}
					session.delete(unitStemStrands.get(0));						
					tx.commit();
					session.close();
					return true;
				} catch (HibernateException e) {
					logger.error("Error in saveformativeAssessUnit() of StemCurriculumSubDAOImpl" + e);
					return false;
				}
			}
			return false;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<LegendCriteria> getLegendCriteria() {
			List<LegendCriteria> legendCriteria = new ArrayList<LegendCriteria>();
			try {
				legendCriteria = (List<LegendCriteria>) getHibernateTemplate()
						.find("from LegendCriteria where legendCriteriaName not in ('"+WebKeys.LITERACIES+"')");
			} catch (Exception e) {
				logger.error("Error in getStemLegendCriteria() of StemCurriculumSubDAOImpl"+ e);
			}
			return legendCriteria;
		}

}