package com.lp.teacher.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lp.model.CAASPPScores;
import com.lp.model.ClassStatus;
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

public interface IOLReportCardService {
	
	public List<LegendCriteria> getLegendCriteria();
	
	public Student getStudent(long studentId);
	
	public Grade getGrade(long gradeId);
	
	public List<Legend> getLegends();
	  
	public List<IOLReport> getStudentIOLReportDates(long csId, long studentId);
    
	public List<LearningIndicatorValues> getStudentIOLReportCard(long learnIndicatorId);
    
	public List<IOLReport> getStudentAllIOLReportDates(long studentId);
    
	public LearningIndicator getLearningIndicator(long learningIndicatorId);
	
	public ClassStatus getclassStatus(long csId); 
	
	public boolean TeacherCreateIOLReportCard(long learIndicatorId);
	
	public List<IOLReport> getStudentCompletedIOLReportDates(long csId, long studentId);
	
	public List<LearningIndicator> getStudentAllCompletedIOLReportDates(long studentId);
	
	public boolean saveTeacherScore(long learnValId, long legend);
	
	public boolean saveTeacherComment(long learnValId, String teacherNotes);
	
	public List<LearningIndicator> saveStudentIOLReportCard(long csId, long studentId,long trimesterId, Grade grade);
	
	public boolean submitStudentIOLReportCardToTeacher(long learIndicatorId,long studentId,long csId,long trimesterId);
		
	public boolean saveStudentNotes(long learnIndiValueId, String studentNotes);
	
	public boolean saveStudentSelfScore(long learnIndiValueId, long legend);
	
	public List<IOLReport> getStudentInCompletedIOLReport(long csId, long studentId);
	
	public long assignLIRubricToGrade(long subCriteriaId,List<Long> legendIds,long gradeId);
	
	public List<LERubrics> getRubricValuesByGradeId(long gradeId,long subCriteriaId);
	
	public LegendSubCriteria getLegendSubCriteria(long legendSubCriteriaId);
	
	public String leFileAutoSave(MultipartFile file,long createdBy,long learningIndicatorId,long subCriteriaId,long learnIndValuesId);
	
	public LearningIndicatorValues getLearningIndicatorValues(long learnIndiValueId);
	
	public List<FilesLP> getLEFileList(long studentId,long learningIndicatorId,long learnIndValuesId);
	
	public String getRubricDesc(long criteriaId,long subCriteriaId,long rubricScore, long createdBy);
	
	public boolean checkLIRubricExists(long subCriteriaId,long rubricScore, long createdBy);
	
	public List<IOLReport> getChildAllIOLReportDates(long studentId);
	
	public List<LegendSubCriteria> getLegendSubCriteriasByCriteriaId(long legendCriteriaId, long masterGradeId);
	
	public List<LearningIndicatorValues> getStudentInCompletedIOLSection(long learningIndicatorId);
	
	public LegendCriteria getLegendCriteria(long legendCriteriaId);
	
	public boolean submitStudentIOLSectionToTeacher(long learIndicatorId,String leScore);
	
	public long getSumOfLegends(long learningIndicatorId);
	
	public long getNoOfLegends(long learningIndicatorId);
	
	public LearningIndicator getIOLSectionStatus(long csId,long studentId,long legendCriteriaId,String createDate);
	
	public List<LearningIndicatorValues> getStudentCompletedIOLSection(long learningIndicatorId);
	
	public List<LearningIndicator> getStudentCurrentIOLReport(long csId, long studentId,long iolReportId);
	
	public List<LearningIndicatorValues> getLitracyLearnIndValues(long iolReportId,long legendCriteriaId);
	
	public List<LearningIndicatorValues> getNewLitracyLearnIndValues(long csId,long studentId,long legendCriteriaId);
	
	public LearningIndicatorValues getStudentInCompletedSubCriteria(long learningValuesId);
	
	public LearningIndicatorValues getStudentCompletedSubCriteria(long learningvaluesId);
	
	public void submitStudentIOLSubCriteriaToTeacher(long learningIndicatorId,long learningValuesId);
	
	public IOLReport getIOLReport(long iolReportId);
	
    public long getSumOfTeacherLegends(long learningIndicatorId);
	
	public long getNoOfTeacherLegends(long learningIndicatorId);
	
	 public boolean gradeStudentIOLSectionToTeacher(long learIndicatorId,String leScore);
	 
	 public void gradeStudentIOLSubCriteriaToTeacher(long learningIndicatorId,long learningValuesId);
	 
	 public boolean checkIOLReportStatus(long iolReportId);
	 
	 public boolean checkIOLGradeStatus(long iolReportId);
	 
	 public List<LearningIndicator> getStudentIOLReportByIOLReportId(long studentId,long iolReportId);
	 
	 public List<MulYrLegend> getMultiYearLegends();
	 
	 public List<LearningIndicator> getStudentIOLScoresByCriteriaId(long studentId,long legendCriteriaId);
	 
	 public long chkIOLReportExistsByCsIdStudentId(long csId,long studentId);
	 
	 public IOLReport getTrimesterId(long csId,long studentId);
	 
	 public boolean addDefaultRubricsToGrades();
	 
	 public List<CAASPPScores> getCAASPPScoresByStudentId(long studentId,long caasppTypesId, String className);
	 
	 public List<Grade> getSchoolGradesForIOL(long schoolId);

	public List<Legend> getSubcriteriaRubrics(long subId);

	public boolean updateIOLRubrics(List<Legend> legendList);
	
	public boolean assignIOLRubricToGrade(long userRegId,long gradeId,long createdBy,long trimesterId);

	public List<Legend> getCriteriaRubrics(long createdBy, long subCriteriaId);
	
	public List<Trimester> getAllTrimesters();
	
	public boolean checkAlreadyAssignRubric(long gradeId,long trimesterId,long teacherRegId);
	
	public IOLReport getTrimesterByIolReportId(long iolReportId);
	
	public long getReferRegId(long gradeId,long trimesterId,long createdBy);
	
	public List<Legend> getRubricValuesByUserId(long referRegId,long subCriteriaId, long gradeId);
	
	public List<LearningIndicatorValues> getWholeClassCriteriaReport(long csId,long trimesterId,long criteriaId);
	
	public List<LearningIndicatorValues> getStudentCriteriaReport(long studentId,long trimesterId,long criteriaId);
}