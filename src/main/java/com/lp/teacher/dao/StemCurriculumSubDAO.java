package com.lp.teacher.dao;


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


public interface StemCurriculumSubDAO {

	List<LegendCriteria> getStemLegendCriteria();

	List<StemUnitPerformanceIndicator> getStemUnitPerformanceIndicator(long stemUnitId, long lCriteriaId);

	boolean saveStemIndicator(StemUnitPerformanceIndicator stemUnitIndicator);

	List<StemPerformanceIndicator> getStemPerIndicators(long gradeId, long subCriteriaId);

	List<FormativeAssessmentsGradeWise> getFormativeAssessmentsGradeWise(long gradeId);

	List<FormativeAssessmentsUnitWise> getFormativeAssessmentsUnitWise(long stemUnitId);

	boolean saveformativeAssessUnit(FormativeAssessmentsUnitWise forAssessmentsUnitWise);

	List<IpalResources> getIpalResources(long gradeId);

	List<FormativeAssessmentKeywords> getStemFormativeKeywords(long formativeAssessmentId);

	FormativeAssessments getFormativeAssessmentById(long formativeAssessmentId);
	
	public List<FormativeAssessmentsColumnHeaders> getFormativeAssessmentColumnHeaders(long formativeAssessmentId);	
	
	public List<FormativeAssessmentRubric> getFormativeAssessmentRubric(long formativeAssessmentId);
	
	public List<FormativeAssessmentCategory> getFormativeAssessmentCategories(long formativeAssessmentId);

	boolean removeUnitStandards(long strandsId);
	
	List<LegendCriteria> getLegendCriteria();
}