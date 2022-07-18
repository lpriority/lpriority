package com.lp.student.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.AssessmentDAO;
import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.common.service.FileService;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicGrades;
import com.lp.model.AssignmentQuestions;
import com.lp.model.JacQuestionFile;
import com.lp.model.JacTemplate;
import com.lp.model.Questions;
import com.lp.model.RflpPractice;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.model.UserRegistration;
import com.lp.student.dao.TakeAssessmentsDAO;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "assessmentsService1")
public class TakeAssessmentsServiceImpl implements TakeAssessmentsService {

	static final Logger logger = Logger.getLogger(TakeAssessmentsServiceImpl.class);
	
	@Autowired
	private TakeAssessmentsDAO takeAssessmentsDAO;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDAO;
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	@Autowired
	private FileService fileservice;	
	@Autowired
	private AssessmentDAO assessmentDAO;
	
	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;	
	
	@Override
	@RemoteMethod
	public Student getStudent(UserRegistration userReg) {
		return takeAssessmentsDAO.getStudent(userReg);
	}

	@Override
	public List<StudentAssignmentStatus> getStudentTests(Student student,
			String usedFor, String testStatus, String gradedStatus) {
		List<StudentAssignmentStatus> stuAssList=new ArrayList<StudentAssignmentStatus>();
		try{
			stuAssList=takeAssessmentsDAO.getStudentTests(student, usedFor, testStatus,gradedStatus);
		}catch(Exception e){
			logger.error("Error in getStudentTests() of TakeAssessmentsServiceImpl", e);
			e.printStackTrace();
		}
		return stuAssList;
	}

	@Override
	public List<StudentAssignmentStatus> getStudentCurrentHomeworks(
			Student student, String usedFor, String testStatus,
			String gradedStatus, long csId) {
		return takeAssessmentsDAO.getStudentCurrentHomeworks(student, usedFor,
				testStatus, gradedStatus, csId);
	}

	@Override
	public List<StudentAssignmentStatus> getStudentDueHomeworks(
			Student student, String usedFor, String testStatus,
			String gradedStatus, long csId) {
		return takeAssessmentsDAO.getStudentDueHomeworks(student, usedFor,
				testStatus, gradedStatus, csId);
	}

	@Override
	public List<AssignmentQuestions> getTestQuestions(long studentAssignmentId) {
		List<AssignmentQuestions> aq = Collections.emptyList();
		File[] pFiles = null;
		aq = takeAssessmentsDAO.getTestQuestions(studentAssignmentId);
		for(AssignmentQuestions assQue : aq){
			if(assQue.getStudentAssignmentStatus().getAssignment().getBenchmarkDirections() != null){
				String dirLocation = FileUploadUtil.getLpCommonFilesPath();
				String fullPerformancePath = dirLocation+File.separator+WebKeys.LP_FLUENCY_AUDIO_DIRECTION_DIR+File.separator+
						assQue.getStudentAssignmentStatus().getAssignment().getBenchmarkDirections().getLanguage().getLanguage();
				pFiles = fileservice.getFolderFiles(fullPerformancePath);
				if(pFiles != null){
					for(File pf : pFiles){
						if(pf.getName().equalsIgnoreCase(WebKeys.LP_FLUENCY_DIRECTION_AUDIO)){ 
							assQue.getQuestions().setBVoicedirectionspath(pf.getPath());
						}
						if(pf.getName().equalsIgnoreCase(WebKeys.LP_RETELL_DIRECTION_AUDIO)){ 
							assQue.getQuestions().setBRetelldirectionspath(pf.getPath());
						}
					}
				}
			}
		}
		return aq;
	}

	@Override
	public List<SubQuestions> getSSQuestions(List<AssignmentQuestions> questions) {
		List<SubQuestions> ssQuestions = new ArrayList<SubQuestions>();
		Map<Long, String> clsMap = new LinkedHashMap<Long, String>();
		try {
			if (questions.size() > 0) {
				for (AssignmentQuestions q : questions) {
					SubQuestions ssQue = new SubQuestions();
					if (!clsMap.containsKey(q.getQuestions().getSubQuestions()
							.getSubQuestionId())) {
						clsMap.put(q.getQuestions().getSubQuestions()
								.getSubQuestionId(), q.getQuestions()
								.getSubQuestions().getSubQuestion());
						ssQue.setSubQuestionId(q.getQuestions()
								.getSubQuestions().getSubQuestionId());
						ssQue.setSubQuestion(q.getQuestions().getSubQuestions()
								.getSubQuestion());
						ssQue.setNumOfOptions(q.getQuestions()
								.getSubQuestions().getNumOfOptions());
						ssQuestions.add(ssQue);
					}
				}

			} else {
				logger.info("Error in getSSQuestions() of  AssignAssessmentsServiceImpl");
			}
		} catch (Exception e) {
			logger.error("Error in  getSSQuestions() of  AssignAssessmentsServiceImpl");
		}
		return ssQuestions;
	}
	
	@Override
	public boolean saveAssignment(
			StudentAssignmentStatus studentAssignmentStatus, String operation,
			boolean lateSubmission) {
		int maxMarks = 0;
		int secMarks = 0;
		boolean status = false;
		Float percentage = (float) 0;
		try{
			for (AssignmentQuestions assignmentQuestion : studentAssignmentStatus
					.getAssignmentQuestions()) {				
				try{
					if (assignmentQuestion.getQuestions().getAnswer() != null 
							&& assignmentQuestion.getAnswer() != null) {	
						maxMarks++;
						assignmentQuestion.setMaxMarks(1);
						if (assignmentQuestion.getAnswer().trim().equalsIgnoreCase(
								assignmentQuestion.getQuestions().getAnswer().trim())) {
							assignmentQuestion.setSecMarks(1);
							secMarks++;
						} else {
							assignmentQuestion.setSecMarks(0);
						}
						takeAssessmentsDAO.saveAssignmentQuestionsMarks(assignmentQuestion);
					}else{
						logger.info("the actual answer and the student's response are both NULL ");
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			Date currentDate = new Date();
			long studentAssignmentId = studentAssignmentStatus.getStudentAssignmentId();
			studentAssignmentStatus = performanceTaskDAO.getStudentAssignmentStatusById(studentAssignmentId);
			long assignmentTypeId = studentAssignmentStatus.getAssignment()
					.getAssignmentType().getAssignmentTypeId();
			studentAssignmentStatus.setSubmitdate(new java.sql.Date(
					new java.util.Date().getTime()));
			if (assignmentTypeId == 3 || assignmentTypeId == 4
					|| assignmentTypeId == 5 || assignmentTypeId == 7 || assignmentTypeId == 14) {
				logger.info("assignmentTypeId : "+assignmentTypeId);
				percentage = (float) secMarks / maxMarks * 100;
				if (lateSubmission) {
					percentage = percentage / 2;
				}
				AcademicGrades academicGrades = gradeAssessmentsDAO.getAcademicGradeByPercentage(percentage);
				studentAssignmentStatus.setAcademicGrade(academicGrades);
				studentAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_GRADED);
				studentAssignmentStatus.setPercentage(percentage);
				studentAssignmentStatus.setGradedDate(new Date());
				studentAssignmentStatus.setReadStatus(WebKeys.UN_READ);
			}
 else if(assignmentTypeId == 19){
		    	boolean chkStat=true;
		     	for (AssignmentQuestions assignmentQuestion : studentAssignmentStatus
						.getAssignmentQuestions()) {				
					if (assignmentQuestion.getQuestions().getAssignmentType().getAssignmentTypeId()==2 || assignmentQuestion.getQuestions().getAssignmentType().getAssignmentTypeId()==1)
						{	
						 chkStat=false;
						 studentAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
						}
					}
		     	if(chkStat){
		    	percentage = (float) secMarks / maxMarks * 100;
				if (lateSubmission) {
					percentage = percentage / 2;
				}
				AcademicGrades academicGrades = gradeAssessmentsDAO.getAcademicGradeByPercentage(percentage);
				studentAssignmentStatus.setAcademicGrade(academicGrades);
				studentAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_GRADED);
				studentAssignmentStatus.setPercentage(percentage);
				studentAssignmentStatus.setGradedDate(new Date());
				studentAssignmentStatus.setReadStatus(WebKeys.UN_READ);
		     	}
			 }	
			else {
			
			 studentAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
			}
			studentAssignmentStatus.setStatus(WebKeys.TEST_STATUS_SUBMITTED);
			studentAssignmentStatus.setSubmitdate(currentDate);
			status = takeAssessmentsDAO.saveAssignment(studentAssignmentStatus, operation, lateSubmission);
			logger.info("status : "+status);
		}
		catch(Exception e){
			status = false;
			logger.error("Error in saveAssignment() of TakeAssessmentsServiceImpl", e);
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public List<JacTemplate> getJacTemplateTitleList(
			List<AssignmentQuestions> questions) throws DataException {
		List<JacTemplate> jacTemplateTitles = new ArrayList<JacTemplate>();
		Map<Long, String> gradeMap = new LinkedHashMap<Long, String>();

		try {
			if (questions != null && questions.size() > 0) {
				for (AssignmentQuestions cs : questions) {
					if (!gradeMap.containsKey(cs.getQuestions()
							.getJacTemplate().getJacTemplateId())) {
						gradeMap.put(cs.getQuestions().getJacTemplate()
								.getJacTemplateId(), cs.getQuestions()
								.getJacTemplate().getTitleName());
						JacTemplate jacTemplate = new JacTemplate();
						 if(cs.getQuestions().getJacTemplate().getJacTemplateId() > 0){
							 jacTemplate = assessmentDAO.getJacTemplate(cs.getQuestions().getJacTemplate().getJacTemplateId());
						 }
						jacTemplateTitles.add(jacTemplate);
						
					}
				}
			} else {
				logger.info("Error in getJacTemplateTitleList() of  StudentServiceImpl");
				throw new DataException(
						"Error in getJacTemplateTitleList() of StudentServiceImpl");
			}
		} catch (DataException | SQLDataException e) {
			logger.error("Error in getJacTemplateTitleList() of  StudentServiceImpl");
			throw new DataException(
					"Error in getJacTemplateTitleList() of StudentServiceImpl",
					e);
		}

		return jacTemplateTitles;
	}

	@Override
	public void saveJacAnswer(String answer, String originalAnswer,
			long assignmentQuestionId) {
		int secMark = 0;
		if (answer.trim().equalsIgnoreCase(originalAnswer.trim()))
			secMark = 1;
		else
			secMark = 0;
		takeAssessmentsDAO.saveJacAnswer(answer, assignmentQuestionId, secMark);
	}

	@Override
	public boolean submitJacTemplateTest(long studentAssignmentId, String tab) {
		long totalSecMarks = 0, totalMaxMarks = 0;
		float percentage = 0;
		boolean status = true;
		totalSecMarks = takeAssessmentsDAO.getSecuredMarks(studentAssignmentId);
		totalMaxMarks = takeAssessmentsDAO.getMaxMarks(studentAssignmentId);
		percentage = (float) totalSecMarks / totalMaxMarks * 100;
		if(tab.equalsIgnoreCase(WebKeys.LP_TAB_VIEW_DUE_HOMEWORK)){
			percentage = percentage/2;
		}
		AcademicGrades academicGrades = gradeAssessmentsDAO.getAcademicGradeByPercentage(percentage);
		status = takeAssessmentsDAO.submitJacTemplateTest(studentAssignmentId,
				percentage,academicGrades);
		return status;
	}

	@Override
	public String getJacQuestionFilePath(JacQuestionFile jacQuestionFile) {

		String uploadFilePath = FileUploadUtil.getUploadFilesPath(
				jacQuestionFile.getUserRegistration(), request);
		String usedFor = WebKeys.JAC_TEMPLATE;
		String jacFileFullPath = uploadFilePath + File.separator + usedFor
				+ File.separator + jacQuestionFile.getJacQuestionFileId()
				+ File.separator + jacQuestionFile.getFilename();
		return jacFileFullPath;
	}

	@Override
	public void saveAccuracyFiles(long assignmentQuestionId, String audioData, String passageType) {
		String fileName = null;
		if (passageType.equals(WebKeys.BENCHMARK_FLUENCY_PASSAGE)) {
			fileName = WebKeys.FLUENCY_FILE_NAME;
		} else if(passageType.equals(WebKeys.BENCHMARK_RETELL_PASSAGE))  {
			fileName = WebKeys.RETELL_FILE_NAME;
		} else{
			fileName = WebKeys.ACCURACY_FILE_NAME;
		}		
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg,
				request);
		String benchFileFullPath = uploadFilePath + File.separator
				+ WebKeys.STUDENT_BENCH_MARK_TESTS + File.separator + assignmentQuestionId;
		File f = new File(benchFileFullPath);
		if (!f.isDirectory()) {
			f.setExecutable(true, false);
			f.mkdirs();
		}
		benchFileFullPath = benchFileFullPath + File.separator + fileName;
		try {
			if(audioData.length() > 0){
				byte[] bis = Base64.decodeBase64(audioData.toString());
				FileOutputStream fos = null;
			  	  try{
			  		  File file = new File(benchFileFullPath);
			  			if(file.exists()) 
			  				file.delete();
			  			synchronized(bis) {
			  				fos = new FileOutputStream(file, true); 
			  				fos.write(bis);
			  			}
				    } catch (Exception e) {
			             e.printStackTrace();
			        } finally{
			        	  fos.close();
			        }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	@Override
	public boolean saveBenchmarkFiles(long assignmentQuestionId,String audioData, String passageType) {
		
			String fileName = null;
			byte[] bis = null;
			boolean status = false;
		try {
				if (passageType.equals(WebKeys.BENCHMARK_FLUENCY_PASSAGE)) {
					fileName = WebKeys.FLUENCY_FILE_NAME;
				} else if(passageType.equals(WebKeys.BENCHMARK_RETELL_PASSAGE))  {
					fileName = WebKeys.RETELL_FILE_NAME;
				} else{
					fileName = WebKeys.ACCURACY_FILE_NAME;
				}		
				UserRegistration userReg = (UserRegistration) session
						.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg,
						request);
				String benchFileFullPath = uploadFilePath + File.separator
						+ WebKeys.STUDENT_BENCH_MARK_TESTS + File.separator + assignmentQuestionId;
				File f = new File(benchFileFullPath);
				if (!f.isDirectory()) {
					f.setExecutable(true, false);
					f.mkdirs();
				}
				benchFileFullPath = benchFileFullPath + File.separator + fileName;
				try {
					bis = Base64.decodeBase64(audioData.toString());
					
					File file = new File(benchFileFullPath);
					if(file.exists()) 
						file.delete();
					else 
						file.createNewFile();
					
		  			FileOutputStream fs = new FileOutputStream(benchFileFullPath);
		            BufferedOutputStream bs = new BufferedOutputStream(fs);
		            bs.write(bis);
		            bs.close();
		            fs.close();
		            status = true;					
				} catch (Exception e) {
					e.printStackTrace();
				}
		} catch (Exception e) {
			e.printStackTrace();		
		}finally{
			return status;
		}
	}
	
	@Override
	public boolean submitBenchmarkTest(long studentAssignmentId){
	return takeAssessmentsDAO.submitBenchmarkTest(studentAssignmentId);	
	}

	@Override
	public boolean autoSaveAssignment(long assignmentQuestionId, String answer) {
		boolean status;
		try{
			status = takeAssessmentsDAO.autoSaveAssignment(assignmentQuestionId, answer);
		}
		catch(Exception e){
			logger.info("there is an error in autoSaveAssignment in TakeAssessmentsServiceImpl");
			status = false;
		}
		return status;
	}
	@Override
	public List<RflpPractice> getRflpTest(long studentAssignmentId){
		return takeAssessmentsDAO.getRflpTest(studentAssignmentId);
	}
	
	@Override
	public boolean saveRflpTest(long rflpPracticeId, String studentSentence, String operation, long studentAssignmentId){
		return takeAssessmentsDAO.saveRflpTest(rflpPracticeId, studentSentence, operation, studentAssignmentId);
	}
	@Override
	public boolean submitRflpTest(RflpTest rflpTest){
		return takeAssessmentsDAO.submitRflpTest(rflpTest);
	}
	@Override
	public List<SubQuestions> getComprehensionQuestions(List<AssignmentQuestions> questions) {
		List<SubQuestions> ssQuestions = new ArrayList<SubQuestions>();
		List<Questions> comQuestions=new ArrayList<Questions>();
		Map<Long, String> clsMap = new LinkedHashMap<Long, String>();
		try {
			if (questions.size() > 0) {
				for (AssignmentQuestions q : questions) {
					SubQuestions ssQue = new SubQuestions();
					if (!clsMap.containsKey(q.getQuestions().getSubQuestions()
							.getSubQuestionId())) {
						clsMap.put(q.getQuestions().getSubQuestions()
								.getSubQuestionId(), q.getQuestions()
								.getSubQuestions().getSubQuestion());
						ssQue = q.getQuestions().getSubQuestions();
						comQuestions=takeAssessmentsDAO.getCompQuestionList(q.getQuestions()
								.getSubQuestions().getSubQuestionId(),questions);						
						ssQue.setQuestionses(comQuestions);
						ssQuestions.add(ssQue);
					}
				}

			} else {
				logger.info("Error in getSSQuestions() of  AssignAssessmentsServiceImpl");
			}
		} catch (Exception e) {
			logger.error("Error in  getSSQuestions() of  AssignAssessmentsServiceImpl");
		}
		return ssQuestions;
	}
	
	@Override
	public List<StudentAssignmentStatus> getStudentWordWorks(Student student,
		 String testStatus, String gradedStatus) {
		List<StudentAssignmentStatus> stList = Collections.emptyList();
		try{
			stList = takeAssessmentsDAO.getStudentWordWorks(student, testStatus, gradedStatus);
		}
		catch(Exception e){
			logger.error("Error in  getStudentWordWorks() of  AssignAssessmentsServiceImpl");
		}
		return stList;
	}
}
