
package com.lp.teacher.service;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.custom.exception.DataException;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.AcademicGrades;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkCutOffMarks;
import com.lp.model.BenchmarkResults;
import com.lp.model.FluencyAddedWords;
import com.lp.model.FluencyComments;
import com.lp.model.FluencyMarks;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.GradingTypes;
import com.lp.model.JacQuestionFile;
import com.lp.model.JacTemplate;
import com.lp.model.QualityOfResponse;
import com.lp.model.Questions;
import com.lp.model.RflpRubric;
import com.lp.model.RflpTest;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.student.dao.TakeAssessmentsDAO;
import com.lp.teacher.dao.AssignAssessmentsDAO;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "gradeAssessmentsService")
public class GradeAssessmentsServiceImpl implements GradeAssessmentsService {
	
	
	@Autowired
	private TakeAssessmentsDAO takeAssessmentDao;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDao;
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	@Autowired
	AssignAssessmentsDAO assignAssessmentsDao;
	@Autowired
	AndroidPushNotificationsService apns;
	
	static final Logger logger = Logger.getLogger(GradeAssessmentsServiceImpl.class);
	@Override
	@RemoteMethod
	public List<StudentAssignmentStatus> getStudentAssessmentTests(long assignmentId){
	 try{	
		return gradeAssessmentsDao.getStudentAssessmentTests(assignmentId);
	}catch(DataException e){
		logger.error("Error in getStudentAssessmentTests() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in getStudentAssessmentTests() of GradeAssessmentsServiceImpl", e);
	}
	}
	@Override
	public List<AssignmentQuestions> getTestQuestions(long studentAssignmentId) {
		try{
		return gradeAssessmentsDao.getTestQuestions(studentAssignmentId);
	}catch(DataException e){
		logger.error("Error in getTestQuestions() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in getTestQuestions() of GradeAssessmentsServiceImpl", e);
	}
	}
	@Override
	public List<SubQuestions> getSSQuestions(List<AssignmentQuestions> questions) {
		List<SubQuestions> ssQuestions = new ArrayList<SubQuestions>();
		List<Questions> comQuestions=new ArrayList<Questions>();
		Map<Long, String> clsMap = new LinkedHashMap<Long, String>();
		try {
			if (questions.size() > 0) {
				for (AssignmentQuestions q : questions) {
					SubQuestions ssQue = null;
					if (!clsMap.containsKey(q.getQuestions().getSubQuestions()
							.getSubQuestionId())) {
						clsMap.put(q.getQuestions().getSubQuestions()
								.getSubQuestionId(), q.getQuestions()
								.getSubQuestions().getSubQuestion());
						ssQue = q.getQuestions().getSubQuestions();
						comQuestions=takeAssessmentDao.getCompQuestionList(q.getQuestions()
								.getSubQuestions().getSubQuestionId(),questions);
						ssQue.setNumOfOptions(comQuestions.size());
						ssQuestions.add(ssQue);
					}
				}

			} else {
				logger.error("Error in getSSQuestions() of  AssignAssessmentsServiceImpl");
			}
		} catch (Exception e) {
			logger.error("Error in  getSSQuestions() of  AssignAssessmentsServiceImpl");
		}
		return ssQuestions;
	}
	@Override
	public List<JacTemplate> getJacTemplateTitleList(List<AssignmentQuestions> questions)
			throws DataException {
		List<JacTemplate> jacTemplateTitles = new ArrayList<JacTemplate>();
		Map <Long, String> gradeMap =   new LinkedHashMap <Long,String>();
		
		try {
			if (questions!=null && questions.size() > 0) {
				for (AssignmentQuestions cs : questions) {
					if(!gradeMap.containsKey(cs.getQuestions().getJacTemplate().getJacTemplateId())){
						gradeMap.put(cs.getQuestions().getJacTemplate().getJacTemplateId(), cs.getQuestions().getJacTemplate().getTitleName());
						jacTemplateTitles.add(cs.getQuestions().getJacTemplate());
					}
				}
			} else {
				logger.error("Error in getJacTemplateTitleList() of  StudentServiceImpl");
				throw new DataException(
						"Error in getJacTemplateTitleList() of StudentServiceImpl");
			}
		} catch (DataException e) {
			logger.error("Error in getJacTemplateTitleList() of  StudentServiceImpl");
			throw new DataException(
					"Error in getJacTemplateTitleList() of StudentServiceImpl", e);
		}

		return jacTemplateTitles;
	}
	@Override
	public String getJacQuestionFilePath(JacQuestionFile jacQuestionFile){
		
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(jacQuestionFile.getUserRegistration(), request);
		String usedFor = WebKeys.JAC_TEMPLATE;
		String jacFileFullPath = uploadFilePath +File.separator+ usedFor+File.separator+jacQuestionFile.getJacQuestionFileId()+File.separator+jacQuestionFile.getFilename();
		return jacFileFullPath;
	}
	public void updateJacMarks(long mark,long assignmentQuestionId){
		try{
		gradeAssessmentsDao.updateJacMarks(mark,assignmentQuestionId);
	}catch(DataException e){
		logger.error("Error in updateJacMarks() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in updateJacMarks() of GradeAssessmentsServiceImpl", e);
	}
	}
	
	@Override
	public boolean submitJacTemplateTest(long studentAssignmentId){
		long totalSecMarks = 0,totalMaxMarks=0;
		float percentage=0;
		boolean status=true;
		try{
		totalSecMarks=takeAssessmentDao.getSecuredMarks(studentAssignmentId);
		totalMaxMarks=takeAssessmentDao.getMaxMarks(studentAssignmentId);
		percentage=(float)totalSecMarks/totalMaxMarks*100;
		AcademicGrades academicGrades=gradeAssessmentsDao.getAcademicGradeByPercentage(percentage);
		status=gradeAssessmentsDao.submitJacTemplateTest(studentAssignmentId,percentage,academicGrades.getAcedamicGradeId());
		}catch(DataException e){
			logger.error("Error in submitJacTemplateTest() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in submitJacTemplateTest() of GradeAssessmentsServiceImpl", e);
		}
		return status;
	}
	@Override
	public boolean gradeStudentTests(long studentAssignmentId,List<AssignmentQuestions> assQuesList){
		long totalMaxMarks=0,totalSecMarks=0;
		DecimalFormat df = new DecimalFormat("#.##");
		float percentage=0.2f;
		boolean status=false;
		try{
		for(int i=0;i<assQuesList.size();i++){
			totalMaxMarks=totalMaxMarks+assQuesList.get(i).getMaxMarks();
			totalSecMarks=totalSecMarks+assQuesList.get(i).getSecMarks();
		}
		
		percentage=(float)totalSecMarks/totalMaxMarks*100;
		String per=df.format(percentage);
		percentage = Float.parseFloat(per);
		AcademicGrades academicGrades=gradeAssessmentsDao.getAcademicGradeByPercentage(percentage);
		StudentAssignmentStatus stuAss=new StudentAssignmentStatus();
		stuAss.setStudentAssignmentId(studentAssignmentId);
		stuAss.setPercentage(percentage);
		stuAss.setAcademicGrade(academicGrades);
		status=gradeAssessmentsDao.gradeStudentAssessments(assQuesList,stuAss);
		if(status){
			StudentAssignmentStatus stAs=assignAssessmentsDao.getStudentAssignmentStatus(studentAssignmentId);
			apns.sendStudentResultsNotification(stAs.getAssignment().getUsedFor(),stAs.getStudent().getStudentId());
		}
		}catch(Exception e){
			logger.error("Error in gradeStudentTests() of  GradeAssessmentsServiceImpl",e);
			return false;
		}
		return status;
	}
	@Override
	public AssignmentQuestions getAssignmentQuestions(long assignmentQuestionId){
		try{
		return gradeAssessmentsDao.getAssignmentQuestions(assignmentQuestionId);
		}catch(DataException e){
			logger.error("Error in getAssignmentQuestions() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getAssignmentQuestions() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public void deleteBenchmarkAllMarks(long studentAssignmentId,long assignmemntQuestionsId){
		
		gradeAssessmentsDao.deleteBenchmarkAllMarks(studentAssignmentId,assignmemntQuestionsId);
			
	}
	public void deleteBenchmarkTypeMarks(long studentAssignmentId,long assignmemntQuestionsId,long readingTypesId,long gradeTypesId){
		
		FluencyMarks fluency=gradeAssessmentsDao.getFluencyMarks(assignmemntQuestionsId, readingTypesId,gradeTypesId);
		if(readingTypesId==2 || readingTypesId==1)
		gradeAssessmentsDao.deleteBenchmarkTypeMarks(studentAssignmentId,assignmemntQuestionsId,readingTypesId,fluency.getFluencyMarksId(),gradeTypesId);
		else
		gradeAssessmentsDao.deleteRetellMarks(studentAssignmentId, assignmemntQuestionsId,readingTypesId,fluency.getFluencyMarksId(),gradeTypesId);	
		
	}
	
	@Override
	public List<QualityOfResponse> getQualityOfResponse(){
		try{
		return gradeAssessmentsDao.getQualityOfResponse();
	}catch(DataException e){
		logger.error("Error in getQualityOfResponse() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in getQualityOfResponse() of GradeAssessmentsServiceImpl", e);
	}
	}
	@Override
	public boolean gradeRetellFluencyTest(long assignmentQuestionId,long retellScore,long readingTypesId,long gradeTypesId,String comment){
		try{
		return gradeAssessmentsDao.gradeRetellFluencyTest(assignmentQuestionId,retellScore,readingTypesId,gradeTypesId,comment);
	}catch(DataException e){
		logger.error("Error in gradeRetellFluencyTest() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in gradeRetellFluencyTest() of GradeAssessmentsServiceImpl", e);
	}
	}
	@Override
	public BenchmarkResults getBenchmarkResults(long studentAssignmentId){
	try{
		return gradeAssessmentsDao.getBenchmarkResults(studentAssignmentId);
	}catch(DataException e){
		logger.error("Error in getBenchmarkResults() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in getBenchmarkResults() of GradeAssessmentsServiceImpl", e);
	}
	}
	@Override
	 public int getMedianValue(int a, int b, int c) {
	        if (a > b) {
	            if (b > c && b != 0) {
	                return b;
	            } else if (a > c && c != 0) {
	                return c;
	            } else {
	                return a;
	            }
	        } else {
	            if (a > c && a != 0) {
	                return a;
	            } else if (b > c && c != 0) {
	                return c;
	            } else {
	                return b;
	            }
	        }

	    }
	@Override
	public boolean gradeBenchmarkTest(StudentAssignmentStatus studentAssignmentStatus, String teacherComment){
		try{ 
			
			ArrayList<Integer> fluency = new ArrayList<Integer>(3);
			ArrayList<Integer> ferrors = new ArrayList<Integer>(3);
			ArrayList<Integer> qualityresponse = new ArrayList<Integer>(3);
		 
			int k=0;
			float percentageAcquired = 0.2f;
			List<AssignmentQuestions> assignQuestionsList=gradeAssessmentsDao.getTestQuestions(studentAssignmentStatus.getStudentAssignmentId());
			for(AssignmentQuestions assignmentQuestion:assignQuestionsList){
				List<FluencyMarks> fluencyMarksList =gradeAssessmentsDao.getBenchmarkTypes(assignmentQuestion.getAssignmentQuestionsId(),1); 
				for(FluencyMarks fluencyMarks:fluencyMarksList){
					if(fluencyMarks.getReadingTypes().getReadingTypesId()==2){
						fluency.add(fluencyMarks.getMarks()==null?0:fluencyMarks.getMarks()); 
						ferrors.add(fluencyMarks.getCountOfErrors()==null?0:fluencyMarks.getCountOfErrors());
					}
					else if(fluencyMarks.getReadingTypes().getReadingTypesId()==3){
						qualityresponse.add(fluencyMarks.getQualityOfResponse()==null?0:fluencyMarks.getQualityOfResponse().getQorId());
					}
			
				}	
				k++;
			}
			for (int i = 0; i < 3 - k; i++) {
				fluency.add(0);
				ferrors.add(0);
				qualityresponse.add(0);
			}
			int medianfluency = 0;
			int medianferrors = 0;
			int a = Integer.parseInt(fluency.get(0).toString());
			int b = Integer.parseInt(fluency.get(1).toString());
			int c = Integer.parseInt(fluency.get(2).toString());
			if (a > b) {
				if (b > c && b != 0) {
					medianfluency = b;
					medianferrors = Integer.parseInt(ferrors.get(1).toString());
				} else if (a > c && c != 0) {
					medianfluency = c;
					medianferrors = Integer.parseInt(ferrors.get(2).toString());
				} else {
					medianfluency = a;
					medianferrors = Integer.parseInt(ferrors.get(0).toString());
				}
			} else {
				if (a > c && a != 0) {
					medianfluency = a;
					medianferrors = Integer.parseInt(ferrors.get(0).toString());
				} else if (b > c && c != 0) {
					medianfluency = c;
					medianferrors = Integer.parseInt(ferrors.get(2).toString());
				} else {
					medianfluency = b;
					medianferrors = Integer.parseInt(ferrors.get(1).toString());
				}
			}	        
         
			int medianretell=0,medianQualResponse=0;
			a = Integer.parseInt(qualityresponse.get(0).toString());
			b = Integer.parseInt(qualityresponse.get(1).toString());
			c = Integer.parseInt(qualityresponse.get(2).toString());
			if(a==0 && b==0 && c==0)   {
				medianQualResponse=0; 
			}else {
				if (a > b) {
					if (b > c && b != 0) {
						medianQualResponse = Integer.parseInt(qualityresponse.get(1).toString());
					} else if (a > c && c != 0) {
						medianQualResponse = Integer.parseInt(qualityresponse.get(2).toString());
					} else {
						medianQualResponse = Integer.parseInt(qualityresponse.get(0).toString());
					}
				} else {
					if (a > c && a != 0) {
						medianQualResponse = Integer.parseInt(qualityresponse.get(0).toString());
					} else if (b > c && c != 0) {
						medianQualResponse = Integer.parseInt(qualityresponse.get(2).toString());
					} else {
						medianQualResponse = Integer.parseInt(qualityresponse.get(1).toString());
					}
				}
			}
			
			QualityOfResponse medianresponse =gradeAssessmentsDao.getMedianresponse(medianQualResponse);

			int s = medianfluency + medianferrors;
			if(s!=0){
				percentageAcquired = (float) medianfluency * 100 / s;
			}
			BenchmarkResults benResult=gradeAssessmentsDao.getBenchmarkResults(studentAssignmentStatus.getStudentAssignmentId());
			AcademicGrades academicGrade=gradeAssessmentsDao.getAcademicGradeByPercentage(percentageAcquired);
			@SuppressWarnings("unused")
			int status = gradeAssessmentsDao.updateStudentAssessmentStatus(studentAssignmentStatus.getStudentAssignmentId(), percentageAcquired,academicGrade);
			long sectionId=studentAssignmentStatus.getAssignment().getClassStatus().getSection().getSectionId();

			if (benResult.getStudentAssignmentStatus()==null) {
				benResult.setStudentAssignmentStatus(studentAssignmentStatus);
				benResult.setMedianFluencyScore(medianfluency);
				benResult.setQualityOfResponse(medianresponse);
				benResult.setTeacherNotes(teacherComment);
				benResult.setAccuracy(percentageAcquired);
				gradeAssessmentsDao.saveBenchmarkResults(benResult);
			}
			else {		 		 
				benResult.setMedianFluencyScore(medianfluency);
				benResult.setQualityOfResponse(medianresponse);
				benResult.setTeacherNotes(teacherComment);
				benResult.setAccuracy(percentageAcquired);
				gradeAssessmentsDao.saveBenchmarkResults(benResult);				
			}
			long fluencyCutOff=90,retellCutOff=20;
			long benchmarkId=studentAssignmentStatus.getAssignment().getBenchmarkCategories().getBenchmarkCategoryId();
			long gradeId=studentAssignmentStatus.getStudent().getGrade().getGradeId();
			if (benchmarkId == 1 || benchmarkId == 2 || benchmarkId == 3) {
				BenchmarkCutOffMarks benCutOff= gradeAssessmentsDao.getBenchmarkCutOffMarks(gradeId, benchmarkId);
				if(benCutOff!=null){
					fluencyCutOff =benCutOff.getFluencyCutOff();
					retellCutOff =benCutOff.getRetellCutOff();
				}
				int rtiGroupId = 0;
				if (medianfluency < fluencyCutOff) {
					rtiGroupId = 1;
				} else if (medianfluency >= fluencyCutOff && medianretell < retellCutOff) {
					rtiGroupId = 2;
				} else if (medianfluency >= fluencyCutOff && medianretell >= retellCutOff) {
					rtiGroupId = 3;
				}
				@SuppressWarnings("unused")
				boolean check=gradeAssessmentsDao.updateStudentRTISections(studentAssignmentStatus, sectionId, rtiGroupId);           
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public String getBenchmarkTeacherComment(long studentAssignmentId){
		try{
			return gradeAssessmentsDao.getBenchmarkTeacherComment(studentAssignmentId);	
		}catch(DataException e){
			logger.error("Error in getBenchmarkTeacherComment() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getBenchmarkTeacherComment() of GradeAssessmentsServiceImpl", e);
		}
	}
	
	@Override
	public BenchmarkResults getBenchmarkResults(StudentAssignmentStatus studentAssignmentStatus){
		try{
			BenchmarkResults benchRes = new BenchmarkResults();
			benchRes = gradeAssessmentsDao.getBenchmarkResults(studentAssignmentStatus.getStudentAssignmentId());
			return benchRes;
		}catch(DataException e){
			logger.error("Error in getBenchmarkResults() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getBenchmarkResults() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public Assignment getAssignment(long assignmentId){
		try{
			return assignAssessmentsDao.getAssignment(assignmentId);
		}catch(DataException e){
			logger.error("Error in getAssignment() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getAssignment() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	@RemoteMethod
	public List<RflpRubric> getRflpRubricValues(){
		try{
			return gradeAssessmentsDao.getRflpRubricValues();
		}catch(DataException e){
			logger.error("Error in getRflpRubricValues() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getRflpRubricValues() of GradeAssessmentsServiceImpl", e);
		}
	}
	
	@Override
	@RemoteMethod
	public List<RflpTest> getRFLPTests(long assignmentId){
	 try{	
		return gradeAssessmentsDao.getRFLPTests(assignmentId);
	}catch(DataException e){
		logger.error("Error in getStudentAssessmentTests() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in getStudentAssessmentTests() of GradeAssessmentsServiceImpl", e);
	}
	}
	@Override
	public List<AssignmentQuestions> getBenchmarkQuestBygradeTyId(List<AssignmentQuestions> questions,long gradeTypeId){
		List<AssignmentQuestions> assignmentQuestions=new ArrayList<AssignmentQuestions>();
		List<FluencyMarks> fluencyMarks=new ArrayList<FluencyMarks>();
		
		try{
			for(AssignmentQuestions assignmentQuestion:questions){
				fluencyMarks=gradeAssessmentsDao.getBenchmarkTypes(assignmentQuestion.getAssignmentQuestionsId(),gradeTypeId);
				assignmentQuestion.setFluencyMarks(fluencyMarks);
				assignmentQuestions.add(assignmentQuestion);
			}
			return assignmentQuestions;
		}catch(DataException e){
			logger.error("Error in getBenchmarkTypes() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getBenchmarkTypes() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public List<FluencyMarks> getFluencyMarksList(long assignmentQuestionId){
		return gradeAssessmentsDao.getBenchmarkTypes(assignmentQuestionId,1);
	}
	@Override
	public List<FluencyMarksDetails> getErrorsList(long fluencyMarksId){
		return gradeAssessmentsDao.getErrorsList(fluencyMarksId);
	}
	@Override
	public FluencyMarks getFluencyMarks(long assignmentQuestionId,long readingTypesId,long gradeTypesId){
		return gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
	}
	
	@Override
	public boolean gradeFluencyTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String errorIdsString[],String errorsString[], String addedWords[], long readingTypesId,long gradeTypesId,String percentageAcquired,String comment,String selfCorrWords[],String prosodyWords[]){
	List<FluencyMarksDetails> fluMarksDetails=new ArrayList<FluencyMarksDetails>();
	List<FluencyAddedWords> FluencyAddedWordsLt =new ArrayList<FluencyAddedWords>();
	try{
		/*FluencyMarks fluencyMarks = gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
		if(addedWords != null){
			gradeAssessmentsDao.deleteFluencyAddedWords(fluencyMarks.getFluencyMarksId());
			for(int i=0;i<addedWords.length;i++){
				if(addedWords[i].contains("$")){
					String str[] = addedWords[i].split("\\$");
					FluencyAddedWords fluencyAddedWords = new FluencyAddedWords();
					fluencyAddedWords.setWordIndex(Long.parseLong(str[0]));
					fluencyAddedWords.setAddedWord(str[1]);
					fluencyAddedWords.setWordType(1);
					fluencyAddedWords.setFluencyMarks(fluencyMarks);
					FluencyAddedWordsLt.add(fluencyAddedWords);
				}
			}
		}
		if(selfCorrWords != null){
			for(int i=0;i<selfCorrWords.length;i++){
				if(selfCorrWords[i].contains("$")){
					String str1[] = selfCorrWords[i].split("\\$");
					FluencyAddedWords fluencyAddedWords = new FluencyAddedWords();
					fluencyAddedWords.setWordIndex(Long.parseLong(str1[0]));
					fluencyAddedWords.setAddedWord(str1[1]);
					fluencyAddedWords.setWordType(2);
					fluencyAddedWords.setFluencyMarks(fluencyMarks);
					FluencyAddedWordsLt.add(fluencyAddedWords);
				}
			}
		}
		if(prosodyWords != null){
			for(int i=0;i<prosodyWords.length;i++){
				if(prosodyWords[i].contains("$")){
					String str2[] = prosodyWords[i].split("\\$");
					FluencyAddedWords fluencyAddedWords = new FluencyAddedWords();
					fluencyAddedWords.setWordIndex(Long.parseLong(str2[0]));
					fluencyAddedWords.setAddedWord(str2[1]);
					fluencyAddedWords.setWordType(3);
					fluencyAddedWords.setFluencyMarks(fluencyMarks);
					FluencyAddedWordsLt.add(fluencyAddedWords);
				}
			}
		}
  
		if(errorsString != null){
			for(int i=0;i<errorsString.length;i++){
				FluencyMarksDetails fluMarkDet=new FluencyMarksDetails();
				fluMarkDet.setFluencyMarks(fluencyMarks);
				fluMarkDet.setErrorsAddress(Long.parseLong(errorIdsString[i]));
				String error = "";
				if (errorsString[i].endsWith(",") || errorsString[i].endsWith("?") || errorsString[i].endsWith("$")|| errorsString[i].endsWith("@")){
					error = errorsString[i].substring(0,errorsString[i].length() - 1);
					if(errorsString[i].endsWith("$")){
						fluMarkDet.setUnknownWord(WebKeys.LP_TRUE);
					}else if(errorsString[i].endsWith("@")){
						fluMarkDet.setSkippedWord(WebKeys.LP_TRUE);
					}else{
						error = errorsString[i];
					}
				}else{
					error = errorsString[i];
				}
				fluMarkDet.setErrorWord(error);
				fluMarksDetails.add(fluMarkDet);
			}
		}*/
		return gradeAssessmentsDao.gradeFluencyReadingTest(assignmentQuestionId,wordsRead,errors,correctWords,readingTypesId,gradeTypesId, percentageAcquired,comment);
		//return gradeAssessmentsDao.gradeAccuracyTest(assignmentQuestionId,wordsRead,errors,correctWords,errorIdsString,readingTypesId,fluMarksDetails,gradeTypesId, FluencyAddedWordsLt,percentageAcquired,comment);
		/*else{
			 return gradeAssessmentsDao.gradeFluencyTestWithoutRFLP(assignmentQuestionId,wordsRead,errors,correctWords,readingTypesId,gradeTypesId,percentageAcquired,comment);
		}*/
		
		
	}catch(DataException e){
		logger.error("Error in gradeFluencyTest() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in gradeFluencyTest() of GradeAssessmentsServiceImpl", e);
	}
	}
	
	@Override
	public boolean gradeAccuracyTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String[] errorsString,long readingTypesId,String errorComments[],String errorWords[],long gradeTypesId,String percentage,String addedWords[],String teacherComment,Integer wcpm){
	List<FluencyMarksDetails> fluMarksDetails=new ArrayList<FluencyMarksDetails>();
	List<FluencyAddedWords> FluencyAddedWordsLt =new ArrayList<FluencyAddedWords>();
	 float percentageAcquired = 0.2f;
	 String comment="";
	try{
		FluencyMarks fluencyMarks = gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
		if(addedWords != null){
			gradeAssessmentsDao.deleteFluencyAddedWords(fluencyMarks.getFluencyMarksId());
			for(int i=0;i<addedWords.length;i++){
				if(addedWords[i].contains("$")){
					String str[] = addedWords[i].split("\\$");
					FluencyAddedWords fluencyAddedWords = new FluencyAddedWords();
					fluencyAddedWords.setWordIndex(Long.parseLong(str[0]));
					fluencyAddedWords.setAddedWord(str[1]);
					fluencyAddedWords.setWordType(1);
					fluencyAddedWords.setFluencyMarks(fluencyMarks);
					FluencyAddedWordsLt.add(fluencyAddedWords);
				}
			}
		}
		
		int k=errorComments.length;
		
		if(errorWords!=null && !errorWords.toString().isEmpty()){
		//if(errorWords.length>0 || k>0){
			for(int i=0;i<errorWords.length;i++){
				FluencyMarksDetails fluMarkDet=new 	FluencyMarksDetails();
				fluMarkDet.setFluencyMarks(gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId));
				fluMarkDet.setErrorsAddress(Long.parseLong(errorsString[i]));
				@SuppressWarnings("unused")
				String error = "";
				if (errorWords[i].endsWith(",") || errorWords[i].endsWith("?") || errorWords[i].endsWith("$")|| errorWords[i].endsWith("@")){
					error = errorWords[i].substring(0,errorWords[i].length() - 1);
					 if(errorWords[i].endsWith("@")){
						fluMarkDet.setSkippedWord(WebKeys.LP_TRUE);
					}else if(errorWords[i].endsWith("$")){
						fluMarkDet.setUnknownWord(WebKeys.LP_TRUE);
					}
				}else{
					error = errorWords[i];
					fluMarkDet.setComments(errorComments[k-1]);
					k--;
				}
				fluMarkDet.setErrorWord(error);
				fluMarksDetails.add(fluMarkDet);
			}
		}
		 long s = correctWords + errors;
		 if(s!=0){
			 percentageAcquired = (float) correctWords * 100 / s;
		 }
	      DecimalFormat dec = new DecimalFormat("##.##");
	      //if(gradeTypesId==1)
	    	comment=teacherComment;
	      
	     
		return gradeAssessmentsDao.gradeAccuracyTest(assignmentQuestionId,wordsRead,errors,correctWords,errorsString,readingTypesId,fluMarksDetails,gradeTypesId, FluencyAddedWordsLt,dec.format(percentageAcquired),comment,wcpm);
	}catch(DataException e){
		logger.error("Error in gradeFluencyTest() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in gradeFluencyTest() of GradeAssessmentsServiceImpl", e);
	}
	}
	@Override
	public List<GradingTypes> getGradeTypes(){
		try{
			return gradeAssessmentsDao.getGradeTypes();
		}catch(DataException e){
			logger.error("Error in getGradeTypes() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getGradeTypes() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	 public GradingTypes getGradingType(long gradeTypesId){
		try{
			return gradeAssessmentsDao.getGradingType(gradeTypesId);
		}catch(DataException e){
			logger.error("Error in getGradingType() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in getGradingType() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	 public boolean gradeSelfAndPeerBenchmark(long studentAssignmentId,long gradeTypesId){
		try{
			return gradeAssessmentsDao.gradeSelfAndPeerBenchmark(studentAssignmentId,gradeTypesId);
		}catch(DataException e){
			logger.error("Error in gradeSelfAndPeerBenchmark() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in gradeSelfAndPeerBenchmark() of GradeAssessmentsServiceImpl", e);
		}
	}
//	@Override
//	public boolean gradeAccuracyTest(
//			StudentAssignmentStatus studentAssignmentStatus,
//			String teacherComment) {
//		try {	
//			 AcademicGrades academicGrade=gradeAssessmentsDao.getAcademicGradeByPercentage(studentAssignmentStatus.getPercentage());
//			int status = gradeAssessmentsDao.updateStudentAssessmentStatus(
//					studentAssignmentStatus.getStudentAssignmentId());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
	@Override
	public boolean gradeAccuracyTests(StudentAssignmentStatus studentAssignmentStatus){
		try{ 
			
		 ArrayList<Integer> fluency = new ArrayList<Integer>(3);
		 ArrayList<Integer> ferrors = new ArrayList<Integer>(3);
		 int k=0;
		 float percentageAcquired = 0.2f;
		List<AssignmentQuestions> assignQuestionsList=gradeAssessmentsDao.getTestQuestions(studentAssignmentStatus.getStudentAssignmentId());
		for(AssignmentQuestions assignmentQuestion:assignQuestionsList){
			List<FluencyMarks> fluencyMarksList =gradeAssessmentsDao.getBenchmarkTypes(assignmentQuestion.getAssignmentQuestionsId(),1); 
		 for(FluencyMarks fluencyMarks:fluencyMarksList){
			if(fluencyMarks.getReadingTypes().getReadingTypesId()==1){
			 fluency.add(fluencyMarks.getMarks()); 
		     ferrors.add(fluencyMarks.getCountOfErrors());
		    }
					
		 }	
             k++;
		}
		for (int i = 0; i < 3 - k; i++) {
            fluency.add(0);
            ferrors.add(0);
           
        }
		 int medianfluency = 0;
         int medianferrors = 0;
         int a = Integer.parseInt(fluency.get(0).toString());
         int b = Integer.parseInt(fluency.get(1).toString());
         int c = Integer.parseInt(fluency.get(2).toString());
         if (a > b) {
             if (b > c && b != 0) {
                 medianfluency = b;
                 medianferrors = Integer.parseInt(ferrors.get(1).toString());
             } else if (a > c && c != 0) {
                 medianfluency = c;
                 medianferrors = Integer.parseInt(ferrors.get(2).toString());
             } else {
                 medianfluency = a;
                 medianferrors = Integer.parseInt(ferrors.get(0).toString());
             }
         } else {
             if (a > c && a != 0) {
                 medianfluency = a;
                 medianferrors = Integer.parseInt(ferrors.get(0).toString());
             } else if (b > c && c != 0) {
                 medianfluency = c;
                 medianferrors = Integer.parseInt(ferrors.get(2).toString());
             } else {
                 medianfluency = b;
                 medianferrors = Integer.parseInt(ferrors.get(1).toString());
             }
         }        
                        
         int s = medianfluency + medianferrors;
         if(s!=0){
        	 percentageAcquired = (float) medianfluency * 100 / s;
         }
         AcademicGrades academicGrade=gradeAssessmentsDao.getAcademicGradeByPercentage(percentageAcquired);
         @SuppressWarnings("unused")
		int status = gradeAssessmentsDao.updateStudentAssessmentStatus(studentAssignmentStatus.getStudentAssignmentId(), percentageAcquired,academicGrade);
                   
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public List<FluencyMarks> getSelfAndPeerFluencyMarks(long assignmentQuestionId,long readingTypesId){
		List<FluencyMarks> list=gradeAssessmentsDao.getSelfAndPeerFluencyMarks(assignmentQuestionId,readingTypesId);
		for(FluencyMarks fluencyMarks:list){
	        List<FluencyMarksDetails> fluencyMarksDetails = getErrorsList(fluencyMarks.getFluencyMarksId());
	        fluencyMarks.setFluencyMarksDetails(fluencyMarksDetails);
	    }
      for(FluencyMarks fluencyMarks:list){
    	   List<FluencyAddedWords> fluencyAddWords = gradeAssessmentsDao.getFluencyAddedWords(fluencyMarks.getFluencyMarksId());
	        fluencyMarks.setFluencyAddedWordsLt(fluencyAddWords);
      }
      return list;
	}
	@Override
	public List<FluencyAddedWords> getFluencyAddedWords(long fluencyMarksId){
		List<FluencyAddedWords> FluencyAddedWordsLt =new ArrayList<FluencyAddedWords>();
		try{
			FluencyAddedWordsLt=gradeAssessmentsDao.getFluencyAddedWords(fluencyMarksId);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return FluencyAddedWordsLt;
	}
	@Override
	public void autoSaveAddedWord(long assignmentQuestionId,long readingTypesId,long gradeTypesId,String addedWord,int wordType){
		FluencyAddedWords fluencyAddedWords = new FluencyAddedWords();
		try{
			  FluencyMarks fluencyMarks = gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
				if(addedWord != null){
						if(addedWord.contains("$")){
							String str[]= addedWord.split("\\$");
						    long addedWordId=gradeAssessmentsDao.checkAddedWordExists(Long.parseLong(str[0]),fluencyMarks.getFluencyMarksId());
						    if(addedWordId>0){
						    	fluencyAddedWords.setAddedWordId(addedWordId);}
							fluencyAddedWords.setWordIndex(Long.parseLong(str[0]));
							fluencyAddedWords.setAddedWord(str[1]);
							fluencyAddedWords.setWordType(wordType);
							fluencyAddedWords.setFluencyMarks(fluencyMarks);
							
						}
					}
				
		gradeAssessmentsDao.autoSaveAddedWord(assignmentQuestionId,readingTypesId,gradeTypesId,fluencyAddedWords);
	}catch(DataException e){
		logger.error("Error in updateJacMarks() of  GradeAssessmentsServiceImpl");
		throw new DataException(
				"Error in updateJacMarks() of GradeAssessmentsServiceImpl", e);
	}
	}
	@Override
	public void removeAddedWord(long assignmentQuestionId,long readingTypesId,long gradeTypesId,String addedWord,int wordType){
		try{
			FluencyMarks fluencyMarks = gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
			if(addedWord.contains("$")){
				String str[]= addedWord.split("\\$");
				gradeAssessmentsDao.removeAddedWord(Long.parseLong(str[0]),fluencyMarks.getFluencyMarksId(),wordType);
			}
		}catch(DataException e){
			logger.error("Error in updateJacMarks() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in updateJacMarks() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public void autoSaveErrorWord(FluencyMarksDetails fluMarkDet){
		try{			
			gradeAssessmentsDao.autoSaveErrorWord(fluMarkDet);			
		}catch(DataException e){
			logger.error("Error in autoSaveErrorWord() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in autoSaveErrorWord() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public void autoSaveErrorComment(long fluencyMarksId,String errComment,long errAddress){
		try{
			gradeAssessmentsDao.autoSaveErrorComment(fluencyMarksId,errComment,errAddress);			 
		}catch(DataException e){
			logger.error("Error in autoSaveErrorComment() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in autoSaveErrorComment() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public void removeErrorWord(long fluencyMarksId,long errAddress){
		try{			
			@SuppressWarnings("unused")
			boolean stat =gradeAssessmentsDao.removeErrorWord(fluencyMarksId,errAddress);		 
		}catch(DataException e){
			logger.error("Error in autoSaveErrorWord() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in autoSaveErrorWord() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public void autoGradeAccuracy(long assignmentQuestionId,long readingTypesId,long gradeTypesId,long wordsRead,long errRead,long totalRead,String percentageAcquired){
		try{			 
			gradeAssessmentsDao.autoGradeAccuracy(assignmentQuestionId,readingTypesId,gradeTypesId,wordsRead,errRead,totalRead,percentageAcquired);
		}catch(DataException e){
			logger.error("Error in autoGradeAccuracy() of  GradeAssessmentsServiceImpl");
			throw new DataException(
			"Error in autoGradeAccuracy() of GradeAssessmentsServiceImpl", e);
		}
	}
	
	@Override
	@RemoteMethod
	public List<FluencyComments>  getFluencyComments(){
		return gradeAssessmentsDao.getFluencyComments();
	}
	@Override
	public List<FluencyAddedWords> getTeacherFluencyAddedWords(long assignmentQuestionId,long readingTypesId,long gradeTypesId){
		 List<FluencyAddedWords> fluencyAddWords=new ArrayList<FluencyAddedWords>();
		try{
		FluencyMarks list=gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId, readingTypesId, gradeTypesId);
		fluencyAddWords = gradeAssessmentsDao.getFluencyAddedWords(list.getFluencyMarksId());
		}catch(DataException e){
			logger.error("Error in getTeacherFluencyAddedWords() of  GradeAssessmentsServiceImpl");
			throw new DataException(
			"Error in getTeacherFluencyAddedWords() of GradeAssessmentsServiceImpl", e);
		}
		return fluencyAddWords;
	    
	}
	@Override
	public void autoSaveComments(FluencyMarks fluMarks){
		try{			
			
			gradeAssessmentsDao.autoSaveComments(fluMarks);		 
		}catch(DataException e){
			logger.error("Error in autoSaveComments() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in autoSaveComments() of GradeAssessmentsServiceImpl", e);
		}
	}
	public void autoGradeFluency(long assignmentQuestionId,long readingTypesId,long gradeTypesId,long errRead){
		try{			 
			gradeAssessmentsDao.autoGradeFluency(assignmentQuestionId,readingTypesId,gradeTypesId,errRead);
		}catch(DataException e){
			logger.error("Error in autoGradeAccuracy() of  GradeAssessmentsServiceImpl");
			throw new DataException(
			"Error in autoGradeAccuracy() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public void autoSaveWordCount(long fluencyMArksIdId,long errRead,long wordsRead,long totalRead){
		try{			 
			gradeAssessmentsDao.autoSaveWordCount(fluencyMArksIdId,errRead,wordsRead,totalRead);
		}catch(DataException e){
			logger.error("Error in autoSaveWordCount() of  GradeAssessmentsServiceImpl");
			throw new DataException(
			"Error in autoSaveWordCount() of GradeAssessmentsServiceImpl", e);
		}
	}
	@Override
	public boolean gradeSelfAndPeerTest(long assignmentQuestionId,long wordsRead,long errors,long correctWords,String errorIdsString[],String errorsString[], String addedWords[], long readingTypesId,long gradeTypesId,String percentageAcquired,String comment,String selfCorrWords[],String prosodyWords[]){
	List<FluencyMarksDetails> fluMarksDetails=new ArrayList<FluencyMarksDetails>();
	List<FluencyAddedWords> FluencyAddedWordsLt =new ArrayList<FluencyAddedWords>();
	try{
		FluencyMarks fluencyMarks = gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
		if(addedWords != null){
			gradeAssessmentsDao.deleteFluencyAddedWords(fluencyMarks.getFluencyMarksId());
			for(int i=0;i<addedWords.length;i++){
				if(addedWords[i].contains("$")){
					String str[] = addedWords[i].split("\\$");
					FluencyAddedWords fluencyAddedWords = new FluencyAddedWords();
					fluencyAddedWords.setWordIndex(Long.parseLong(str[0]));
					fluencyAddedWords.setAddedWord(str[1]);
					fluencyAddedWords.setWordType(1);
					fluencyAddedWords.setFluencyMarks(fluencyMarks);
					FluencyAddedWordsLt.add(fluencyAddedWords);
				}
			}
		}
		if(selfCorrWords != null){
			for(int i=0;i<selfCorrWords.length;i++){
				if(selfCorrWords[i].contains("$")){
					String str1[] = selfCorrWords[i].split("\\$");
					FluencyAddedWords fluencyAddedWords = new FluencyAddedWords();
					fluencyAddedWords.setWordIndex(Long.parseLong(str1[0]));
					fluencyAddedWords.setAddedWord(str1[1]);
					fluencyAddedWords.setWordType(2);
					fluencyAddedWords.setFluencyMarks(fluencyMarks);
					FluencyAddedWordsLt.add(fluencyAddedWords);
				}
			}
		}
		if(prosodyWords != null){
			for(int i=0;i<prosodyWords.length;i++){
				if(prosodyWords[i].contains("$")){
					String str2[] = prosodyWords[i].split("\\$");
					FluencyAddedWords fluencyAddedWords = new FluencyAddedWords();
					fluencyAddedWords.setWordIndex(Long.parseLong(str2[0]));
					fluencyAddedWords.setAddedWord(str2[1]);
					fluencyAddedWords.setWordType(3);
					fluencyAddedWords.setFluencyMarks(fluencyMarks);
					FluencyAddedWordsLt.add(fluencyAddedWords);
				}
			}
		}
  
		if(errorsString != null){
			for(int i=0;i<errorsString.length;i++){
				FluencyMarksDetails fluMarkDet=new FluencyMarksDetails();
				fluMarkDet.setFluencyMarks(fluencyMarks);
				fluMarkDet.setErrorsAddress(Long.parseLong(errorIdsString[i]));
				String error = "";
				if (errorsString[i].endsWith(",") || errorsString[i].endsWith("?") || errorsString[i].endsWith("$")|| errorsString[i].endsWith("@")){
					error = errorsString[i].substring(0,errorsString[i].length() - 1);
					if(errorsString[i].endsWith("$")){
						fluMarkDet.setUnknownWord(WebKeys.LP_TRUE);
					}else if(errorsString[i].endsWith("@")){
						fluMarkDet.setSkippedWord(WebKeys.LP_TRUE);
					}else{
						error = errorsString[i];
					}
				}else{
					error = errorsString[i];
				}
				fluMarkDet.setErrorWord(error);
				fluMarksDetails.add(fluMarkDet);
			}
		}
		return gradeAssessmentsDao.gradeAccuracyTest(assignmentQuestionId,wordsRead,errors,correctWords,errorIdsString,readingTypesId,fluMarksDetails,gradeTypesId, FluencyAddedWordsLt,percentageAcquired,comment, 0);
		}catch(DataException e){
			logger.error("Error in gradeFluencyTest() of  GradeAssessmentsServiceImpl");
			throw new DataException(
					"Error in gradeFluencyTest() of GradeAssessmentsServiceImpl", e);
		}
	}
	public List<AssignmentQuestions> getStudentAssignmentQuestions(long studentId,long assignmentId){
		 List<AssignmentQuestions> assQuesLst=new ArrayList<AssignmentQuestions>();
		try{
			assQuesLst=gradeAssessmentsDao.getStudentAssignmentQuestions(studentId,assignmentId);
		 }catch(DataException e){
				logger.error("Error in getStudentAssignmentQuestions() of  GradeAssessmentsServiceImpl");
				throw new DataException(
				"Error in getStudentAssignmentQuestions() of GradeAssessmentsServiceImpl", e);
			}
			return assQuesLst;
	}
	@Override
	public List<FluencyMarks> getStudSelfAndPeerFluencyMarks(long assignmentId){
		List<FluencyMarks> list= new ArrayList<FluencyMarks>();
	 try{
		  list=gradeAssessmentsDao.getStudSelfAndPeerFluencyMarks(assignmentId);
	 }catch(Exception e){
		 logger.error("Error in getStudentAssignmentQuestions() of  GradeAssessmentsServiceImpl");
			throw new DataException(
			"Error in getStudentAssignmentQuestions() of GradeAssessmentsServiceImpl", e);
	 }
		
      return list;
	}
	@Override
	public boolean retestFluencyAndAccuracy(long studentAssignmentId) {
		// TODO Auto-generated method stub
		List<FluencyMarks> list= new ArrayList<FluencyMarks>();
		 list=gradeAssessmentsDao.getFluencyMarks(studentAssignmentId);
		return gradeAssessmentsDao.retestFluencyAndAccuracy(list, studentAssignmentId);
	}
	
	@Override
	public boolean saveWCPM(long assignmentQuestionId, Integer wcpm, long gradingTypeId, long readingTypeId) {
		return gradeAssessmentsDao.saveWCPM(assignmentQuestionId, wcpm, gradingTypeId, readingTypeId);
		
	}
	
}
