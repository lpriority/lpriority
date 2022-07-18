
package com.lp.teacher.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicGrades;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkCutOffMarks;
import com.lp.model.BenchmarkResults;
import com.lp.model.FluencyAddedWords;
import com.lp.model.FluencyComments;
import com.lp.model.FluencyMarks;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.GradingTypes;
import com.lp.model.QualityOfResponse;
import com.lp.model.RflpRubric;
import com.lp.model.RflpTest;
import com.lp.model.StudentAssignmentStatus;

public interface GradeAssessmentsDAO {

	public List<StudentAssignmentStatus> getStudentAssessmentTests(long assignmentId);
	public List<AssignmentQuestions> getTestQuestions(long studentAssignmentId);
	public void updateJacMarks(long mark,long assignmentQuestionId);
	public boolean submitJacTemplateTest(long studentAssignmentId,float percentage,long acedamicGradeId);
	public AcademicGrades getAcademicGradeByPercentage(float percentage);
	public boolean gradeStudentAssessments(List<AssignmentQuestions> assQuesList,StudentAssignmentStatus stuAss);
	public AssignmentQuestions getAssignmentQuestions(long assignmentQuestionId);
	public void deleteBenchmarkAllMarks(long studentAssignmentId,long assignmemntQuestionsId);
	public void deleteRetellMarks(long studentAssignmentId,long assignmemntQuestionsId,long readingTypesId,long fluencyMarksId,long gradeTypesId);
	public boolean gradeFluencyTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String errorIdsString[],String errorsString[],long index);
	public List<QualityOfResponse> getQualityOfResponse();
	public boolean gradeRetellFluencyTest(long assignmentQuestionId,long retellScore,long readingTypesId,long gradeTypesId,String comment);
	public BenchmarkResults getBenchmarkResults(long studentAssignmentId);
	public QualityOfResponse getMedianresponse(int medianretell);
	public int updateStudentAssessmentStatus(long studentAssignmentId, float percentageAcquired,AcademicGrades ace);
	public boolean saveBenchmarkResults(BenchmarkResults benResult);
	public BenchmarkCutOffMarks getBenchmarkCutOffMarks(long gradeId,long benchmarkId) throws DataException;
	public boolean updateStudentRTISections(StudentAssignmentStatus studentAssignmentStatus, long sectionId, long rtiGroupId);
	public String getBenchmarkTeacherComment(long studentAssignmentId);
	public List<RflpRubric> getRflpRubricValues();	
	public List<RflpTest> getRFLPTests(long assignmentId);
	public List<FluencyMarks> getBenchmarkTypes(long assignmentQuestionsId,long gradeTypeId);
	public void deleteBenchmarkTypeMarks(long studentAssignmentId,long assignmemntQuestionsId,long readingTypesId,long fluencyMarksId,long gradeTypesId);
	public List<FluencyMarksDetails> getErrorsList(long fluencyMarksId);
	public FluencyMarks getFluencyMarks(long assignmentQuestionId,long readingTypesId,long gradeTypesId);
	public boolean gradeAccuracyTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String[] errorsString,long readingTypesid,List<FluencyMarksDetails> fluencyMarksDetails,long gradeTypesId, List<FluencyAddedWords> FluencyAddedWordsLt,String percentage,String comment, Integer wcpm);
	public List<GradingTypes> getGradeTypes();
	public GradingTypes getGradingType(long gradeTypesId);
	public boolean gradeSelfAndPeerBenchmark(long studentAssignmentId,long gradeTypesId);
	public int updateStudentAssessmentStatus(long studentAssignmentId);
	public boolean gradeFluencyTestWithoutRFLP(long assignmentQuestionId,long wordsRead,long errors,long correctWords,long readingTypesId,long gradeTypesId,String percentage,String comment);
	public boolean deleteFluencyAddedWords(long fluencyMarksId);
	public List<FluencyMarks> getSelfAndPeerFluencyMarks(long assignmentQuestionId,long readingTypesId);
	public List<FluencyAddedWords> getFluencyAddedWords(long fluencyMarksId);
	public void autoSaveAddedWord(long assignmentQuestionId,long readingTypesId,long gradeTypesId,FluencyAddedWords FluencyAddedWordsLt);
	public boolean removeAddedWord(long wordIndex,long fluencyMarksId,int wordType);
	public long checkAddedWordExists(long wordIndex,long fluencyMarksId);
	public void autoSaveErrorWord(FluencyMarksDetails fluMarkDet);
	public void autoSaveErrorComment(long fluencyMarksId,String errComment,long errAddress);
	public long checkErrorWordExists(long fluencyMarksId,long errorAddress);
	public boolean removeErrorWord(long fluencyMarksId,long errAddress);
	public void autoGradeAccuracy(long assignmentQuestionId,long readingTypesId,long gradeTypesId,long wordsRead,long errRead,long totalRead,String percentage);
	public List<FluencyComments>  getFluencyComments();
	public void autoSaveComments(FluencyMarks fluMarks);
	public boolean retestFluencyAndAccuracy(List<FluencyMarks> fluencyMarksId,long studentAssignmentId);
	public void autoGradeFluency(long assignmentQuestionId,long readingTypesId,long gradeTypesId,long errRead);
	public void autoSaveWordCount(long fluencyMArksIdId,long errRead,long wordsRead,long totalRead);
	public boolean gradeFluencyReadingTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,long readingTypesid,long gradeTypesId,String percentage,String comment);
	public List<AssignmentQuestions> getStudentAssignmentQuestions(long studentId,long assignmentId);
	public List<FluencyMarks> getStudSelfAndPeerFluencyMarks(long assignmentId);
	public List<FluencyMarks> getFluencyMarks(long studentAssignmentId);
	public boolean saveWCPM(long assignmentQuestionId, Integer wcpm, long gradingTypeId, long readingTypeId);

}
