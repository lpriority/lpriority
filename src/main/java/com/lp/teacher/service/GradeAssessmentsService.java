
package com.lp.teacher.service;

import java.util.List;

import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkResults;
import com.lp.model.FluencyAddedWords;
import com.lp.model.FluencyComments;
import com.lp.model.FluencyMarks;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.GradingTypes;
import com.lp.model.JacQuestionFile;
import com.lp.model.JacTemplate;
import com.lp.model.QualityOfResponse;
import com.lp.model.RflpRubric;
import com.lp.model.RflpTest;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;

public interface GradeAssessmentsService {

	public List<StudentAssignmentStatus> getStudentAssessmentTests(long assignmentId);
	public List<AssignmentQuestions> getTestQuestions(long studentAssignmentId);
	public List<SubQuestions> getSSQuestions(List<AssignmentQuestions> questions);
	public List<JacTemplate> getJacTemplateTitleList(List<AssignmentQuestions> questions);
	public String getJacQuestionFilePath(JacQuestionFile jacQuestionFile);
	public void updateJacMarks(long mark,long assignmentQuestionId);
	public boolean submitJacTemplateTest(long studentAssignmentId);
	public boolean gradeStudentTests(long studentAssignmentId,List<AssignmentQuestions> assQuesList);
	public AssignmentQuestions getAssignmentQuestions(long assignmentQuestionId);
	public void deleteBenchmarkAllMarks(long studentAssignmentId,long assignmemntQuestionsId);
	public void deleteBenchmarkTypeMarks(long studentAssignmentId,long assignmemntQuestionsId,long readingTypesId,long gradeTypesId);
	public List<QualityOfResponse> getQualityOfResponse();
	public boolean gradeRetellFluencyTest(long assignmentQuestionId,long retellScore,long readingTypesId,long gradeTypesId,String comment);
	public BenchmarkResults getBenchmarkResults(long studentAssignmentId);
	public int getMedianValue(int a, int b, int c);
	public boolean gradeBenchmarkTest(StudentAssignmentStatus studentAssignmentStatus,String teacherComment);
	public String getBenchmarkTeacherComment(long studentAssignmentId);
	public BenchmarkResults getBenchmarkResults(StudentAssignmentStatus studentAssignmentStatus);
	public Assignment getAssignment(long assignmentId);
	public List<RflpRubric> getRflpRubricValues();
	public List<RflpTest> getRFLPTests(long assignmentId);
	public List<AssignmentQuestions> getBenchmarkQuestBygradeTyId(List<AssignmentQuestions> questions,long gradeTypeId);
	public List<FluencyMarks> getFluencyMarksList(long assignmentQuestionId);
	public List<FluencyMarksDetails> getErrorsList(long fluencyMarksId);
	public FluencyMarks getFluencyMarks(long assignmentQuestionId,long readingTypesId,long gradeTypesId);
	//public boolean gradeAccuracyTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String errorsString[],long readingTypesId,String errorComments[],List<String> errorWordsList,long gradeTypesId,String percentage,String addedWords[],String teacherComment);
	public boolean gradeFluencyTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String errorIdsString[],String errorsString[],String addedWords[],long readingTypesId,long gradeTypesId,String percentage,String comment,String selfCorrWords[],String prosodyWords[]);
    public List<GradingTypes> getGradeTypes();
    public GradingTypes getGradingType(long gradeTypesId);
    public boolean gradeSelfAndPeerBenchmark(long studentAssignmentId,long gradeTypesId);
    public boolean gradeAccuracyTests(StudentAssignmentStatus studentAssignmentStatus);
    public List<FluencyMarks> getSelfAndPeerFluencyMarks(long assignmentQuestionId,long readingTypesId);
    public List<FluencyAddedWords> getFluencyAddedWords(long fluencyMarksId);
	public void autoSaveAddedWord(long assignmentQuestionId,long readingTypesId,long gradeTypesId,String addedWord,int wordType);
	public void removeAddedWord(long assignmentQuestionId,long readingTypesId,long gradeTypesId,String addedWord,int wordType);
	public void autoSaveErrorWord(FluencyMarksDetails fluMarkDet);
	public void autoSaveErrorComment(long fluencyMarksId,String errComment,long errAddress);
	public void removeErrorWord(long fluencyMarksId,long errorAddress);
	public void autoGradeAccuracy(long assignmentQuestionId,long readingTypesId,long gradeTypesId,long wordsRead,long errRead,long totalRead,String percentage);
	public List<FluencyComments>  getFluencyComments();
	public List<FluencyAddedWords> getTeacherFluencyAddedWords(long assignmentQuestionId,long readingTypesId,long gradesTypeId);
	public void autoSaveComments(FluencyMarks fluMarks);
	public boolean gradeAccuracyTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String errorsString[],long readingTypesId,String errorComments[],String errorWordsList[],long gradeTypesId,String percentage,String addedWords[],String teacherComment,Integer wcpm);
	public void autoGradeFluency(long assignmentQuestionId,long readingTypesId,long gradeTypesId,long errRead);
	public void autoSaveWordCount(long fluencyMArksIdId,long errRead,long wordsRead,long totalRead);
	public boolean gradeSelfAndPeerTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String errorIdsString[],String errorsString[],String addedWords[],long readingTypesId,long gradeTypesId,String percentage,String comment,String selfCorrWords[],String prosodyWords[]);
	public List<AssignmentQuestions> getStudentAssignmentQuestions(long studentId,long assignmentId);
	public List<FluencyMarks> getStudSelfAndPeerFluencyMarks(long assignmentId);
	public boolean retestFluencyAndAccuracy(long studentAssignmentId);
	public boolean saveWCPM(long assignmentQuestionId, Integer wcpm, long gradingTypeId, long readingTypeId);


}
