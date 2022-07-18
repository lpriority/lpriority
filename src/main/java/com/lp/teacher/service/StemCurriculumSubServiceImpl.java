package com.lp.teacher.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.model.FormativeAssessmentCategory;
import com.lp.model.FormativeAssessmentKeywords;
import com.lp.model.FormativeAssessmentRubric;
import com.lp.model.FormativeAssessments;
import com.lp.model.FormativeAssessmentsColumnHeaders;
import com.lp.model.FormativeAssessmentsGradeWise;
import com.lp.model.FormativeAssessmentsUnitWise;
import com.lp.model.IpalResources;
import com.lp.model.LegendCriteria;
import com.lp.model.StemPerformanceIndicator;
import com.lp.model.StemUnitPerformanceIndicator;
import com.lp.teacher.dao.StemCurriculumSubDAO;

@RemoteProxy(name = "stemCurriculumSubService")
public class StemCurriculumSubServiceImpl implements StemCurriculumSubService {
	
	static final Logger logger = Logger.getLogger(StemCurriculumSubServiceImpl.class);
	
	@Autowired
	HttpSession session;
	@Autowired
	private StemCurriculumSubDAO stemCurriculumSubDAO;

	
	
	@Override
	public List<LegendCriteria> getStemLegendCriteria() {
		return stemCurriculumSubDAO.getStemLegendCriteria();
	}


	@Override
	public List<StemUnitPerformanceIndicator> getStemUnitPerformanceIndicator(
			long stemUnitId, long lCriteriaId) {
		return stemCurriculumSubDAO.getStemUnitPerformanceIndicator(stemUnitId, lCriteriaId);
	}


	@Override
	public boolean saveStemIndicator(StemUnitPerformanceIndicator stemUnitIndicator) {
		return stemCurriculumSubDAO.saveStemIndicator(stemUnitIndicator);
	}


	@Override
	public List<StemPerformanceIndicator> getStemPerIndicators(long gradeId, long subCriteriaId) {
		return stemCurriculumSubDAO.getStemPerIndicators(gradeId,subCriteriaId);
	}


	@Override
	public List<FormativeAssessmentsGradeWise> getFormativeAssessmentsGradeWise(long gradeId) {
		return stemCurriculumSubDAO.getFormativeAssessmentsGradeWise(gradeId);
	}


	@Override
	public List<FormativeAssessmentsUnitWise> getFormativeAssessmentsUnitWise(long stemUnitId) {
		return stemCurriculumSubDAO.getFormativeAssessmentsUnitWise(stemUnitId);
	}


	@Override
	public boolean saveformativeAssessUnit(FormativeAssessmentsUnitWise forAssessmentsUnitWise) {
		return stemCurriculumSubDAO.saveformativeAssessUnit(forAssessmentsUnitWise);
	}


	@Override
	public List<IpalResources> getIpalResources(long gradeId) {
		return stemCurriculumSubDAO.getIpalResources(gradeId);
	}


	@Override
	public List<FormativeAssessmentKeywords> getStemFormativeKeywords(long formativeAssessmentId) {
		return stemCurriculumSubDAO.getStemFormativeKeywords(formativeAssessmentId);
	}


	@Override
	public FormativeAssessments getFormativeAssessmentById(long formativeAssessmentId) {
		return stemCurriculumSubDAO.getFormativeAssessmentById(formativeAssessmentId);
	}


	@Override
	public List<FormativeAssessmentsColumnHeaders> getFormativeAssessmentColumnHeaders(
			long formativeAssessmentId) {
		return stemCurriculumSubDAO.getFormativeAssessmentColumnHeaders(formativeAssessmentId);
	}


	@Override
	public List<FormativeAssessmentRubric> getFormativeAssessmentRubric(
			long formativeAssessmentId) {
		return stemCurriculumSubDAO.getFormativeAssessmentRubric(formativeAssessmentId);
	}


	@Override
	public List<FormativeAssessmentCategory> getFormativeAssessmentCategories(
			long formativeAssessmentId) {
		return stemCurriculumSubDAO.getFormativeAssessmentCategories(formativeAssessmentId);
	}


	@Override
	public boolean removeUnitStandards(long strandsId) {
		return stemCurriculumSubDAO.removeUnitStandards(strandsId);
	}
	
	@Override
	public List<LegendCriteria> getLegendCriteria() {
		return stemCurriculumSubDAO.getLegendCriteria();
	}
	
}
