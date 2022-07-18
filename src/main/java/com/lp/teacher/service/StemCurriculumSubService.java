package com.lp.teacher.service;

import java.util.List;

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

public interface StemCurriculumSubService {
	
	public List<LegendCriteria> getStemLegendCriteria();

	public List<StemUnitPerformanceIndicator> getStemUnitPerformanceIndicator(long stemUnitId, long lCriteriaId);

	public boolean saveStemIndicator(StemUnitPerformanceIndicator stemUnitIndicator);

	public List<StemPerformanceIndicator> getStemPerIndicators(long gradeId, long subCriteriaId);

	public List<FormativeAssessmentsGradeWise> getFormativeAssessmentsGradeWise(long gradeId);

	public List<FormativeAssessmentsUnitWise> getFormativeAssessmentsUnitWise(long stemUnitId);

	public boolean saveformativeAssessUnit(FormativeAssessmentsUnitWise forAssessmentsUnitWise);

	public List<IpalResources> getIpalResources(long gradeId);

	public List<FormativeAssessmentKeywords> getStemFormativeKeywords(long formativeAssessmentId);

	public FormativeAssessments getFormativeAssessmentById(long formativeAssessmentId);	
	
	public List<FormativeAssessmentsColumnHeaders> getFormativeAssessmentColumnHeaders(long formativeAssessmentId);	
	
	public List<FormativeAssessmentRubric> getFormativeAssessmentRubric(long formativeAssessmentId);
	
	public List<FormativeAssessmentCategory> getFormativeAssessmentCategories(long formativeAssessmentId);

	public boolean removeUnitStandards(long strandsId);
	
	public List<LegendCriteria> getLegendCriteria();
	
}
