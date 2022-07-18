package com.lp.appadmin.dao;

import java.util.List;

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
import com.lp.model.StemGradeStrands;
import com.lp.model.Trimester;

public interface UploadDAO {

	public boolean saveLegendFromFile(List<Legend> legendList, List<MasterGrades> masterGrades, String Check) throws Exception;
	public long saveFormativeAssessment(FormativeAssessments fa) throws Exception;
	public boolean saveFormativeAssessmentCategory(FormativeAssessmentCategory fac) throws Exception;
	public boolean saveFormativeAssessmentsGradeWise(FormativeAssessmentsGradeWise fag) throws Exception;
	public boolean saveFormativeAssessmentKeywords(FormativeAssessmentKeywords fak) throws Exception;
	public ColumnHeaders getColumnHeaderByColumn(String columnName) throws Exception;
	public boolean saveFormativeAssessmentsColumnHeaders(FormativeAssessmentsColumnHeaders fach) throws Exception;
	public FormativeAssessmentCategory getFormativeAssessmentCategoryByCategory(long formativeAssessmentsId, String category) throws Exception;
	public boolean saveFormativeAssessmentRubric(FormativeAssessmentRubric far) throws Exception;
	public List<StemAreas> getAllStemAreas();
	public boolean uploadStrandsFile(List<StemGradeStrands> gradeStrandsLt);
	public List<School> getAllSchoolStates();
	public List<District> getDistricts();
	public List<Trimester> getTrimesters();
	public List<CAASPPTypes> getCAASPPTypes();
	public List<School> getAllSchools();
	public boolean saveStarUploadFile(List<StarScores> starScores);
	public List<CAASPPTypes> getCaasppElaMathTypes();
	public List<CAASPPScores> getAllCaasppScores();
	public boolean saveCAASPPUploadFile(List<CAASPPScores> cAASPPScores);
}
