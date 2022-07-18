package com.lp.teacher.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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

public interface IOLReportCardDAO {
	
	public List<LegendCriteria> getLegendCriteria();
	
	public List<LegendSubCriteria> getLegendSubCriteriasByCriteriaId(long legendCriteriaId, long masterGradeId);
	
	public List<Legend> getLegends();
	
	public List<LearningIndicatorValues> CreateIOLReportCard(IOLReport iolReport,List<LearningIndicator> learningIndicators, List<LearningIndicatorValues> learningIndicatorValues);
	
	public Legend getLegend(long legendId);
	
	public LegendSubCriteria getLegendSubCriteria(long legendSubCriteriaId);
	
	public LearningIndicator getLearningIndicator(long learningIndicatorId);
	
	public boolean saveLearningValues(LearningIndicatorValues learningdVal);
	
	public List<IOLReport> getStudentIOLReportDates(long csId, long studentId);
	
	public List<LearningIndicatorValues> getStudentIOLReportCard(long learnIndicatorId);
	
	public List<IOLReport> getStudentAllIOLReportDates(long studentId);
	
	public LearningIndicatorValues getLearningIndicatorValues(long learningIndicatorValueId);
	
	public List<IOLReport> getStudentCompletedIOLReportDates(long csId, long studentId);
	
	public List<LearningIndicator> getStudentAllCompletedIOLReportDates(long studentId);
	
	public boolean saveTeacherComment(long learningdValId, String teacherComment);
	
	public boolean saveTeacherScore(long learningdValId, long teacherScore);
	
	public boolean submitIOLCardtoStudent(long learningIndicatorId);
	
	public boolean submitStudentIOLReportCardToTeacher(long learIndicatorId);	
	
	public List<IOLReport> getStudentInCompletedIOLReport(long csId, long studentId);
	
	public List<LegendSubCriteria> getLegendSubCriterias();
	
	public boolean saveStudentNotes(long learnIndiValueId, String studentNotes);
	
	public boolean saveStudentSelfScore(long learnIndiValueId, long legend);
	
	public List<Legend> getRubricValuesBySubCriteriaId(long subCriteriaId, long createBy);
	
	public long assignLIRubricToGrade(List<LERubrics> leRubrics);
	
	public long checkAlreadyAssignedLIRubric(long subCriteriaId,long legendId,long gradeId);
	
	public List<LERubrics> getRubricValuesByGradeId(long gradeId,long subCriteriaId);
	
	public String leFileAutoSave(MultipartFile file,Student stud,long learningIndicatorId,long subCriteriaId,long learnIndValuesId);
	
	public String saveFileInFileSystem(MultipartFile file, Student createdBy,long learningIndicatorId,long subCriteriaId,long learnIndValuesId);
	
	public List<FilesLP> getLEFileList(Student stud,long learningIndicatorId,long learnIndValuesId);
	
	public String getRubricDesc(long criteriaId,long subCriteriaId,long rubricScore, long createBy);
	
	public long editLIRubric(long subCriteriaId,long rubricScore,String rubricDesc, long createdBy);
	
	public boolean checkLIRubricExists(long subCriteriaId,long rubricScore, long createdBy);
	
	public List<IOLReport> getChildAllIOLReportDates(long studentId);
	 
	public List<LearningIndicatorValues> getStudentInCompletedIOLSection(long learningIndicatorId);
	
	public LegendCriteria getLegendCriteria(long legendCriteriaId);
	 
	public boolean submitStudentIOLSectionToTeacher(long learIndicatorId,String leScore);
	
	public long getSumOfLegends(long learningIndicatorId);
	 
	public long getNoOfLegends(long learningIndicatorId);
	 
	public LearningIndicator getIOLSectionStatus(long csId,long studentId,long legendCriteriaId,String createDate);
	 
	public List<LearningIndicatorValues> getStudentCompletedIOLSection(long learningIndicatorId);
	
	public List<LearningIndicator> getStudentCurrentIOLReport(long csId, long studentId,long iolReportId);
	 
	public List<LearningIndicatorValues> getIncompleteLitracyLearnIndValues(long iolReportId,long legendCriteriaId);
	 
	public List<LearningIndicatorValues> getNewLitracyLearnIndValues(long csId,long studentId,long legendCriteriaId);
	 
	public LearningIndicatorValues getStudentInCompletedSubCriteria(long learningValuesId);
	 
	public LearningIndicatorValues getStudentCompletedSubCriteria(long learningvaluesId);
	 
	public void submitStudentIOLSubCriteriaToTeacher(long learIndicatorId,long learningValuesId);
	 
	public IOLReport getIOLReport(long iolReportId);
	 
	public long getSumOfTeacherLegends(long learningIndicatorId);
	 
	public long getNoOfTeacherLegends(long learningIndicatorId);
	 
	public boolean gradeStudentIOLSectionToTeacher(long learIndicatorId,String leScore);
	 
	public void gradeStudentIOLSubCriteriaToTeacher(long learIndicatorId,long learningValuesId);
	 
	public boolean checkIOLReportStatus(long iolReportId);
	 
	public boolean checkIOLGradeStatus(long iolReportId);
	 
	public List<LearningIndicator> getStudentIOLReportByIOLReportId(long studentId,long iolReportId);
	
	public List<MulYrLegend> getMultiYearLegends();
	 
	public List<LearningIndicator> getStudentIOLScoresByCriteriaId(long studentId,long legendCriteriaId);
	 
	public long chkIOLReportExistsByCsIdStudentId(long csId,long studentId);
	 
	public IOLReport getTrimesterId(long csId,long studentId);
	 
	public Trimester getTrimesterId(long trimesterId);
	
	public boolean addDefaultRubricsToGrades(List<LERubrics> leRubrics);
	 
	public List<Legend> getDefaultLegendsByCriteria(long criteriaId);
	 
	public List<CAASPPScores> getCAASPPScoresByStudentId(long studentId,long caasppTypesId,  String className);
	 
	public List<Grade> getSchoolGradesForIOL(long schoolId);
	 
	public void setTrimesterIdForStudent(long studentId,long csId,long trimesterId);

	public List<Legend> getSubcriteriaRubrics(long subId);

	public boolean updateIOLRubrics(List<Legend> legendList);
	
	public boolean assignIOLRubricToGrade(AssignLegendRubrics assRubrics);

	public List<Legend> getCriteriaRubrics(long createdBy, long subCriteriaId);
	
	public List<Trimester> getAllTrimesters();
	
	public boolean checkAlreadyAssignRubric(long gradeId,long trimesterId,long teacherRegId);
	
	public IOLReport getTrimesterByIolReportId(long iolReportId);
	
	public long getReferRegId(long gradeId,long trimesterId,long createdBy);
	
	public List<Legend> getRubricValuesByUserId(long referRegId,long subCriteriaId, long gradeId);
	
	public List<LegendSubCriteria> getLiteracyLegendSubCriterias();
	
	public List<LegendSubCriteria> getLegendSubCriterias(long legendCriteriaId, long masterGradeId, long districtId);
	
	public CAASPPTypes getCAASPPTypesByCaaSppTypesId(long caasppTypesId);
	
	public List<LearningIndicatorValues> getWholeClassCriteriaReport(long csId,long trimesterId,long criteriaId);
	
	public List<LearningIndicatorValues> getStudentCriteriaReport(long studentId,long trimesterId,long criteriaId);
}