package com.lp.common.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.lp.admin.dao.GradesDAO;
import com.lp.common.dao.AssessmentDAO;
import com.lp.common.dao.CurriculumDAO;
import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.AssignmentType;
import com.lp.model.JacQuestionFile;
import com.lp.model.JacTemplate;
import com.lp.model.Lesson;
import com.lp.model.PtaskFiles;
import com.lp.model.Questions;
import com.lp.model.QuestionsList;
import com.lp.model.Rubric;
import com.lp.model.RubricScalings;
import com.lp.model.RubricTypes;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.CommonDAO;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "assessmentService")
public class AssessmentServiceImpl implements AssessmentService {

	@Autowired
	private AssessmentDAO assessmentDAO;	
	@Autowired
	private CommonDAO commonDAO;
	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;	
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	@Autowired
	private GradesDAO gradeDao;
	@Autowired
    private GradeAssessmentsDAO gradeAssessmentsDAO;

	@Autowired
	private CurriculumDAO curriculumDAO;
	
	static final Logger logger = Logger.getLogger(AssessmentServiceImpl.class);
	
	@Override
	@RemoteMethod
	public List<Lesson> getLessonsByUnitId(long unitId) throws DataException {
		List<Lesson> lessons = new ArrayList<Lesson>();
		try{
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			if(teacherObj == null){
				lessons = curriculumDAO.getLessonssbyUnitId(unitId);
			}else{
				UserRegistration adminReg = commonDAO.getAdminByTeacher(teacherObj.getUserRegistration());
				lessons = curriculumDAO.getTeacherLessonssbyUnitId(unitId,teacherObj.getUserRegistration().getRegId(),adminReg.getRegId());
			}			
			
		}catch(DataException e){
			logger.error("Error in getLessonsByUnitId() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getLessonsByUnitId() of AssessmentServiceImpl",e);
		}
		return lessons;
	}
	
	@Override
	@RemoteMethod
	public List<AssignmentType> getAssignments(String assignFor) throws DataException {
		List<AssignmentType> assignments = new ArrayList<AssignmentType>();
		try{
			assignments = assessmentDAO.getAssignmentsByassignFor(assignFor);
			
		}catch(DataException e){
			logger.error("Error in getAssignments() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getAssignments() of AssessmentServiceImpl",e);
		}
		return assignments;
	}

	@Override
	public boolean createAssessments(long assessmentTypeId, long lessonId,String usedFor, List<Questions> questions,long gradeId) throws DataException {
		// TODO Auto-generated method stub
		
		boolean status = false;
		try{
			List<Questions> saveQuestions = new ArrayList<>();
			saveQuestions = createQuestionList(lessonId, assessmentTypeId, questions, usedFor, null, null,gradeId);			
			status = assessmentDAO.createAssessments(saveQuestions);
		}catch(DataException e){
			logger.error("Error in createAssessments() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in createAssessments() of AssessmentServiceImpl",e);
		}
		
		return status;
		
	}

	@Override
	public List<Questions> createQuestionList(long lessonId,long assessmentTypeId, List<Questions> questions, String usedFor,JacTemplate jacTemplate, SubQuestions subQuestions,long gradeId)
			throws DataException {
		List<Questions> saveQuestions = new ArrayList<>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
	try{
		Lesson lesson=new Lesson();
		if(assessmentTypeId!=8 && assessmentTypeId!=19 && assessmentTypeId!=20){
			 lesson = assessmentDAO.getLessonById(lessonId);
		}
		AssignmentType assignmentType = assessmentDAO.getassignmentTypeById(assessmentTypeId);		
		 for(Questions ques: questions){
				GregorianCalendar cal = new GregorianCalendar();
				long millis = cal.getTimeInMillis();
				Timestamp changeDate = new Timestamp(millis);
				ques.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
				ques.setChangeDate(changeDate);
			 
		if((assessmentTypeId == 14 && (ques.getAnswer() == null || ques.getAnswer().equalsIgnoreCase(""))) || 
				(assessmentTypeId == 7 && ques.getAnswer() == null) ||
				(assessmentTypeId == 19 && (ques.getQuestion() == null || ques.getAssignmentType() == null))){
			 continue;
		}else{
					Questions newQuestion = new Questions();
					if(assessmentTypeId!=8 && assessmentTypeId!=19 && assessmentTypeId!=20){
					 newQuestion.setLesson(lesson);
					}
				if(assessmentTypeId == 19){
					AssignmentType assType = assessmentDAO.getassignmentTypeById(ques.getAssignmentType().getAssignmentTypeId());
					newQuestion.setAssignmentType(assType);
				}else{
					newQuestion.setAssignmentType(assignmentType);
				}
					newQuestion.setAnswer(ques.getAnswer());
					newQuestion.setQuestion(ques.getQuestion());
					newQuestion.setBGradeLevel(ques.getBGradeLevel());
					newQuestion.setBTitle(ques.getBTitle());
					newQuestion.setOption1(ques.getOption1());
					newQuestion.setOption2(ques.getOption2());
					newQuestion.setOption3(ques.getOption3());
					newQuestion.setOption4(ques.getOption4());
					newQuestion.setOption5(ques.getOption5());
					newQuestion.setNumOfOptions(ques.getNumOfOptions());
					newQuestion.setPtDirections(ques.getPtDirections());
					newQuestion.setPtName(ques.getPtName());
					newQuestion.setPtSubjectArea(ques.getPtSubjectArea());
					newQuestion.setSubQuestions(subQuestions);
					newQuestion.setCreatedBy((int)userReg.getRegId());
					newQuestion.setUsedFor(usedFor);
					newQuestion.setJacTemplate(jacTemplate);
					newQuestion.setRubrics(ques.getRubrics());
					newQuestion.setPtaskFiles(ques.getPtaskFiles());
					newQuestion.setGrade(gradeDao.getGrade(gradeId));
					newQuestion.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
					newQuestion.setQueStatus("active");
					if(assessmentTypeId == 3){
						newQuestion.setNumOfOptions(ques.getNumOfOptions());
					}
					if(ques.getQuestionId() > 0){
						newQuestion.setQuestionId(ques.getQuestionId());
						newQuestion.setChangeDate(ques.getChangeDate());

					}
				  saveQuestions.add(newQuestion);
				}
			}
		}catch(DataException e){
			logger.error("Error in setQuestionList() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in setQuestionList() of AssessmentServiceImpl",e);
		}
		return saveQuestions;
	}
	
	public boolean createJacTemplate(long lessonId,long assessmentTypeId, QuestionsList questionsList, String usedFor,MultipartFile file,long gradeId) throws DataException{

		try{
			Lesson lesson = assessmentDAO.getLessonById(lessonId);		
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
	
			List<JacTemplate> jacTemps = questionsList.getJacTemplate();
			JacQuestionFile jqf = new JacQuestionFile();
			jqf.setUsedFor(usedFor);
			jqf.setFilename(file.getOriginalFilename());
			jqf.setLesson(lesson);
			jqf.setUserRegistration(userReg);
			// DB Call  get Id from DB  jac_question_file_id
			assessmentDAO.saveJacQuestioFile(jqf);
			
			//Save file in fileSystem
			saveJacTemplateFile(jqf, file,assessmentTypeId);
			
			List<JacTemplate> jacTemplates =new ArrayList<JacTemplate>();
			for(JacTemplate jacTemp: jacTemps ){
				if(jacTemp.getTitleName() != null){
					jacTemp.setJacQuestionFile(jqf);
					jacTemp.setJacTemplateId(null);
					jacTemp.setNoOfQuestions(jacTemp.getQuestionsList().size());
					jacTemplates.add(jacTemp);
				}
			}
			// DB call to save JacTemplate List - return list of JacTemplate Ids
			assessmentDAO.saveBulkJacTemplates(jacTemplates);
			for(JacTemplate jacTemp: jacTemps ){
				if(jacTemp.getTitleName() != null){
					List<Questions> saveQuestions = new ArrayList<>();
					saveQuestions = createQuestionList(lessonId, assessmentTypeId, jacTemp.getQuestionsList(), usedFor, jacTemp, null,gradeId);			
					assessmentDAO.createAssessments(saveQuestions);
				}
			}
		}catch(DataException | SQLDataException e){
			logger.error("Error in CreateSentenceStructure() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in CreateSentenceStructure() of AssessmentServiceImpl",e);
		} 
		return true;	
	}
	@Override
	public boolean saveJacTemplate(JacTemplate jacTemplate) throws SQLDataException{
		return assessmentDAO.saveJacTemplate(jacTemplate);
	}
	
	public SubQuestions createSentenceStructure(long lessonId,long assessmentTypeId, SubQuestions subQuestion, String usedFor,long gradeId) throws DataException{
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			List<Questions> saveQuestions = new ArrayList<>();
			saveQuestions = createQuestionList(lessonId, assessmentTypeId, subQuestion.getQuestionses(), usedFor, null, subQuestion,gradeId);	
			if(assessmentTypeId!=19){
			Lesson lesson = new Lesson();
			lesson.setLessonId(lessonId);
			subQuestion.setLesson(lesson);
			}
			AssignmentType assignmentType = new AssignmentType();
			assignmentType.setAssignmentTypeId(assessmentTypeId);
			assignmentType.setUsedFor(usedFor);
			subQuestion.setAssignmentType(assignmentType);
			subQuestion.setQuestionses(saveQuestions);
			subQuestion.setCreatedBy(userReg.getRegId());
			subQuestion.setUsedFor(usedFor);
			subQuestion.setGrade(gradeDao.getGrade(gradeId));
			subQuestion.setSubQuesStatus("active");
			// DB call to save SubQuestions List 
			subQuestion = assessmentDAO.saveSubQuestion(subQuestion);
		}catch(DataException | SQLDataException e){
			logger.error("Error in CreateSentenceStructure() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in CreateSentenceStructure() of AssessmentServiceImpl",e);
		}
		return subQuestion;	
	}
	
	
	@Override
	public String saveJacTemplateFile(JacQuestionFile jqf, MultipartFile file,long assessmentTypeId) {
		
		BufferedOutputStream bs = null;
		String jacFileFullPath ="";
	    byte[] bis;
	    UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			String usedFor = WebKeys.JAC_TEMPLATE;
			bis = file.getBytes();
			String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
			jacFileFullPath = uploadFilePath +File.separator+ usedFor+File.separator+jqf.getJacQuestionFileId();
			//Create dir if doesn't exist
			File f = new File(jacFileFullPath);
			if (!f.isDirectory()) {
				 f.setExecutable(true, false);
	             f.mkdirs();
	           
	        } 
			jacFileFullPath = jacFileFullPath +File.separator+file.getOriginalFilename();
			FileOutputStream fs = new FileOutputStream(jacFileFullPath);
            bs = new BufferedOutputStream(fs);
            bs.write(bis);
            bs.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		return jacFileFullPath;
	}

	@Override
	public List<RubricTypes> getRubricTypes() throws DataException {
		
		List<RubricTypes> rubricTypes = new ArrayList<RubricTypes>();
		try{
			rubricTypes = assessmentDAO.getRubricTypes();
		}catch(DataException | SQLDataException e){
			logger.error("Error in getRubricTypes() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getRubricTypes() of AssessmentServiceImpl",e);
		}
		
		return rubricTypes;
	}

	@Override
	public List<RubricScalings> getRubricScalings() throws DataException {
		List<RubricScalings> rubricScalings = new ArrayList<RubricScalings>();
		try{
			rubricScalings = assessmentDAO.getRubricScalings();
		}catch(DataException | SQLDataException e){
			logger.error("Error in getRubricScalings() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getRubricScalings() of AssessmentServiceImpl",e);
		}
		
		return rubricScalings;
	}

	@Override
	public boolean createPerformancetask(long assessmentTypeId, long lessonId,
			String usedFor, List<Questions> questions,long gradeId) throws DataException {
		try{
			List<Questions> saveQuestions = new ArrayList<>();
			saveQuestions = createQuestionList(lessonId, assessmentTypeId, questions, usedFor, null, null,gradeId);	
			// DB call to save Questions and Rubric List 
			assessmentDAO.createAssessments(saveQuestions);	
		}catch(DataException  e){
			logger.error("Error in createPerformancetask() of  AssessmentServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in createPerformancetask() of AssessmentServiceImpl",e);
		}
		return true;
	}
	
	public Lesson getLessonById(long lessonId) throws DataException{
		return assessmentDAO.getLessonById(lessonId);
	}

	@Override
	public List<Questions> getQuestionsByAssignmentType(long assignmentTypeId,
			long lessonId, String usedFor,long gradeId) throws DataException {
		List<Questions> questionsList = Collections.emptyList();
		try{
			if(assignmentTypeId!=8 && assignmentTypeId!=20 && assignmentTypeId!=19)
			questionsList = assessmentDAO.getQuestionsByAssignmentType(assignmentTypeId, lessonId, usedFor);
			else
			questionsList = assessmentDAO.getQuestionsByAssignmentType(assignmentTypeId,usedFor,gradeId);	
		}catch(DataException | SQLDataException  e){
			logger.error("Error in getQuestionsByAssignmentType() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getQuestionsByAssignmentType() of AssessmentServiceImpl",e);
		}
		return questionsList;
	}
	
	@Override
	public QuestionsList getQuestionByQuestionId(long questionId) throws SQLDataException{
		return assessmentDAO.getQuestionByQuestionId(questionId);
	}
	
	@Override
	public boolean updateAssessments(Questions question)
			throws DataException {
		boolean status = false;
		try{
			GregorianCalendar cal = new GregorianCalendar();
			long millis = cal.getTimeInMillis();
			Timestamp changeDate = new Timestamp(millis);
			question.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
			question.setChangeDate(changeDate);
			// DB call to Update Questions List 
			status = assessmentDAO.updateAssessments(question);
		}catch(DataException | SQLDataException e){
			logger.error("Error in updateAssessments() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in updateAssessments() of AssessmentServiceImpl",e);
		}
		return status;
	}
	
	@Override
	public boolean removeAssessments(Questions question) throws SQLDataException{
		boolean status = false;
		try{
			// DB call to Update Questions List 
			List<PtaskFiles> ptaskFiles = new ArrayList<PtaskFiles>(); 
			List<Questions> questionLt = new ArrayList<Questions>();
			ptaskFiles = assessmentDAO.getPtaskFilesByUser(question.getQuestionId());
			List<Rubric> rubrics = new ArrayList<Rubric>(); 
			rubrics = performanceTaskDAO.getRubrics(question.getQuestionId());
			QuestionsList questLt = getQuestionByQuestionId(question.getQuestionId());
			question = questLt.getQuestions().get(0);
			question.setRubrics(rubrics);
			question.setPtaskFiles(ptaskFiles);
			questionLt.add(question);
			status = assessmentDAO.removeAssessments(questionLt.get(0));
		}catch(DataException | SQLDataException e){
			logger.error("Error in removeAssessments() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in removeAssessments() of AssessmentServiceImpl",e);
		}
		return status;
	}

	@Override
	public List<SubQuestions> getSubQuestionsByAssignmentType(long assignmentTypeId, long lessonId, String usedFor,long gradeId)
			throws DataException {
		List<SubQuestions> subQuestionsList = Collections.emptyList();
		try{
			subQuestionsList = assessmentDAO.getSubQuestionByAssignmentType(assignmentTypeId, lessonId, usedFor,gradeId);	
		}catch(DataException | SQLDataException  e){
			logger.error("Error in getSubQuestionsByAssignmentType() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getSubQuestionsByAssignmentType() of AssessmentServiceImpl",e);
		}
		return subQuestionsList;
	}

	@Override
	public SubQuestions updateSentenceStructure(SubQuestions subQuestion)
			throws DataException {
		try{
			GregorianCalendar cal = new GregorianCalendar();
			long millis = cal.getTimeInMillis();
			Timestamp changeDate = new Timestamp(millis);			
			Questions qr = null;
			for (Questions question : subQuestion.getQuestionses()) {
				question.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
				question.setChangeDate(changeDate); 			
			}
			adjustingScoresforAutomatically(subQuestion);
			// DB call to Update Questions List 
			subQuestion = assessmentDAO.saveAssignemntQuestions(subQuestion);
		}catch(DataException | SQLDataException e){
			logger.error("Error in updateSentenceStructure() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in updateSentenceStructure() of AssessmentServiceImpl",e);
		}
		return subQuestion;
	}

	@Override
	public List<Questions> getRubricsByQuestionId(List<Questions> questions)
			throws DataException {
		try{
			for(Questions qs:questions){
				List<Rubric> rubrics = new ArrayList<Rubric>(); 
				rubrics = performanceTaskDAO.getRubrics(qs.getQuestionId());
				qs.setRubrics(rubrics);
			}
		}catch(Exception e){
			logger.error("Error in getRubricsByQuestionId() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getRubricsByQuestionId() of AssessmentServiceImpl",e);
		}
		return questions;
	}

	@Override
	public List<Questions> getPtaskFiles(List<Questions> questions)
			throws DataException {
		try{
			for(Questions qs:questions){
				List<PtaskFiles> pf = new ArrayList<PtaskFiles>(); 
				pf = assessmentDAO.getPtaskFilesByUser(qs.getQuestionId());
				qs.setPtaskFiles(pf);
			}
		}catch(DataException | SQLDataException e){
			logger.error("Error in getRubricsByQuestionId() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getRubricsByQuestionId() of AssessmentServiceImpl",e);
		}
		return questions;
	}

	@Override
	public boolean deletePerformancefile(long fileId) throws DataException {
		boolean status = false;
		try{
			status = assessmentDAO.deletePerformancefile(fileId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in deletePerformancefile() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in deletePerformancefile() of AssessmentServiceImpl",e);
		}
		return status;
	}
	
	@Override
	public JacTemplate getJacTemplate(long jacTemplateId) throws SQLDataException{
		return assessmentDAO.getJacTemplate(jacTemplateId);
	}	

	@Override
	public List<JacQuestionFile> getJacFileQuestionsByUsedFor(long lessonId,	String usedFor) throws DataException {
		List<JacQuestionFile> jacFileQuestions = Collections.emptyList();
		try{			
			jacFileQuestions = assessmentDAO.getJacFileQuestionsByUsedFor(lessonId, usedFor);	
		}catch(DataException | SQLDataException  e){
			logger.error("Error in getJacFileQuestions() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getJacFileQuestions() of AssessmentServiceImpl",e);
		}
		return jacFileQuestions;
	}
	
	@Override
	public List<JacQuestionFile> getJacFileQuestionsByLessonId(long lessonId) throws SQLDataException{
		return assessmentDAO.getJacFileQuestionsByLessonId(lessonId);
	}

	@Override
	public List<JacTemplate> getJacTemplateByFileId(long jacQuestionFileId) throws DataException {
		List<JacTemplate> jacTemp = new ArrayList<JacTemplate>();
		try{			
			jacTemp = assessmentDAO.getJacTemplateByFileId(jacQuestionFileId);	
		}catch(DataException | SQLDataException  e){
			logger.error("Error in getJacTemplateByFileId() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getJacTemplateByFileId() of AssessmentServiceImpl",e);
		}
		return jacTemp;
	}
	
	@Override
	public List<Assignment> getTestDatesforRTIResults(long createdBy,long csId,String usedFor) throws SQLDataException{
		List<Assignment> testDates = new ArrayList<Assignment>();
		try{
			testDates = assessmentDAO.getTestDatesforRTIResults(createdBy, csId, usedFor);
		}catch (HibernateException e) {
			logger.error("Error in getTestDatesforRTIResults() of AssessmentServiceImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getTestDatesforRTIResults() of AssessmentServiceImpl",e);			
		}
		return testDates;
	}
	
	@Override
	public List<StudentAssignmentStatus> getRTIResultsByStudent(long assignmentId) throws SQLDataException{
		List<StudentAssignmentStatus> rtiResults = new ArrayList<StudentAssignmentStatus>();
		try{
			rtiResults = assessmentDAO.getRTIResultsByStudent(assignmentId);
		}catch (HibernateException e) {
			logger.error("Error in getRTIResultsByStudent() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getRTIResultsByStudent() of AssessmentDAOImpl",e);
		}
		return rtiResults;
	}
	
	@Override
	public List<AssignmentQuestions> getRTIResultsByQuestion(long assignmentId) throws SQLDataException{
		List<AssignmentQuestions> rtiTestResults = new ArrayList<AssignmentQuestions>();
		try{
			rtiTestResults = assessmentDAO.getRTIResultsByQuestion(assignmentId);
		}catch (HibernateException e) {
			logger.error("Error in getRTIResultsByQuestion() of AssessmentDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getRTIResultsByQuestion() of AssessmentDAOImpl",e);
		}
		return rtiTestResults;
	}
	
	@Override
	public JacQuestionFile getJacQuestionFileByFileId(long jacQuestionFileId) throws SQLDataException{
		return assessmentDAO.getJacQuestionFileByFileId(jacQuestionFileId);
	}
	
	@Override
	public boolean deleteJacTemplete(JacTemplate jacTemplate) throws SQLDataException{
		return assessmentDAO.deleteJacTemplete(jacTemplate);
	}
	
	@Override
	public boolean deleteQuestion(Questions question) throws SQLDataException{
		return assessmentDAO.deleteQuestion(question);
	}
	
	@Override
	public Questions saveQuestion(Questions question) throws SQLDataException{
		return assessmentDAO.saveQuestion(question);
	}
	
	@Override
	public boolean deleteJacTempleteQuestion(long jacQuestionFileId) throws SQLDataException{
		return assessmentDAO.deleteJacTempleteQuestion(jacQuestionFileId);
	}
	
	@Override
	public List<Questions> getQuestionsByLessonId(long lessonId) throws SQLDataException{
		return assessmentDAO.getQuestionsByLessonId(lessonId);
	}
	
	@Override
	public SubQuestions getSubQuestionBySubQuestionId(long subQuestionId) throws SQLDataException{
		return assessmentDAO.getSubQuestionBySubQuestionId(subQuestionId);
	}
	
	@Override
	public boolean deleteSentenceStructure(long subQuestionId) throws SQLDataException{
		return assessmentDAO.deleteSentenceStructure(subQuestionId);
	}

	@Override
	public boolean checkQuestionExists(long questionId){
		boolean status = false;
		try{
			status = assessmentDAO.checkQuestionExists(questionId);
		}catch(DataException e){
			logger.error("Error in checkQuestionExists() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in checkQuestionExists() of AssessmentServiceImpl",e);
		}
		return status;
	}

	@Override
	public boolean checkJacTemplateExists(long jacTemplateId){
		boolean status = false;
		try{
			status = assessmentDAO.checkJacTemplateExists(jacTemplateId);
		}catch(DataException e){
			logger.error("Error in checkJacTemplateExists() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in checkJacTemplateExists() of AssessmentServiceImpl",e);
		}
		return status;
	}

	@Override
	public long checkQuestionAssigned(long questionId){
		return assessmentDAO.checkQuestionAssigned(questionId);
	}
	public boolean updateSubQuestion(Assignment assignment,List<Student> studentList,List<Questions> questionsLt){
		return assessmentDAO.updateSubQuestion(assignment, studentList, questionsLt);
	}
	@Override
	public boolean checkSubQuestionExists(long questionId)throws SQLDataException{
		return assessmentDAO.checkSubQuestionExists(questionId);
	}
	
	@Override
	public List<AssignmentQuestions> getAssignmentByquestionId(long questionId)
			throws DataException {
		List<AssignmentQuestions> AssignmentQuestionsList = Collections.emptyList();
		try{
			AssignmentQuestionsList = assessmentDAO.getAssignmentByquestionId(questionId);	
		}catch(DataException e){
			logger.error("Error in getAssignmentByquestionId() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getAssignmentByquestionId() of AssessmentServiceImpl",e);
		}
		return AssignmentQuestionsList;
	}
	
	@Override
	public boolean updateAssignmentQuestions(Questions question) throws DataException{
		boolean status = false;
		List<AssignmentQuestions> assignmentQuestions = assessmentDAO.getAssignmentByquestionId(question.getQuestionId());
		int maxMarks = 0;
		int secMarks = 0;
		StudentAssignmentStatus currTest = null;
		List<StudentAssignmentStatus> testObjects = new ArrayList<StudentAssignmentStatus>();
		if(!assignmentQuestions.isEmpty()) {
			StudentAssignmentStatus prevTest = assignmentQuestions.get(0).getStudentAssignmentStatus();
			for(AssignmentQuestions studentAssignmentQuestions : assignmentQuestions) {
				currTest = studentAssignmentQuestions.getStudentAssignmentStatus();
				if(prevTest.getStudentAssignmentId() != currTest.getStudentAssignmentId()){					
					maxMarks = prevTest.getAssignmentQuestions().size();
					int prevSecured = Math.round((prevTest.getPercentage()/100)*maxMarks);
					secMarks = secMarks + prevSecured;
					if(secMarks < 0) {
						 secMarks = 0;
					}
					float percentage = (secMarks*100)/maxMarks;					 
					secMarks = 0;
					prevTest.setPercentage(percentage);
					prevTest.setAcademicGrade(gradeAssessmentsDAO.getAcademicGradeByPercentage(prevTest.getPercentage()));
					testObjects.add(prevTest);
					prevTest = currTest;					
				}				
				if(studentAssignmentQuestions.getAnswer().equalsIgnoreCase(question.getAnswer())){
					 studentAssignmentQuestions.setSecMarks(1);	
					 secMarks ++;
				}else{
					secMarks--;
					studentAssignmentQuestions.setSecMarks(0);					
				}
			}
			maxMarks = currTest.getAssignmentQuestions().size();
			int prevSecured = Math.round((currTest.getPercentage()*maxMarks)/100);
			secMarks = secMarks + prevSecured;
			if(secMarks < 0) {
				 secMarks = 0;
			}
			float percentage = (secMarks*100)/maxMarks;
			secMarks = 0;
			currTest.setPercentage(percentage);	
			currTest.setAcademicGrade(gradeAssessmentsDAO.getAcademicGradeByPercentage(currTest.getPercentage()));
			testObjects.add(currTest);
			
			
        }		
		try {
			GregorianCalendar cal = new GregorianCalendar();
			long millis = cal.getTimeInMillis();
			Timestamp changeDate = new Timestamp(millis);
			question.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
			question.setChangeDate(changeDate);
			status = assessmentDAO.updateStudentAssignmentQusetions(question,assignmentQuestions,testObjects);
		} catch (SQLDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}

	private SubQuestions adjustingScoresforAutomatically(SubQuestions subQuestion) throws DataException{	
		
		List<AssignmentQuestions> assignmentQuestions = assessmentDAO.getAssignmentBySubQuestionId(subQuestion.getSubQuestionId());
		int maxMarks = 0;
		int secMarks = 0;
		StudentAssignmentStatus currTest = null;
		Map<Long, Questions> questionMap = new HashMap<Long, Questions>();
 		for(Questions que : subQuestion.getQuestionses()) {
 			questionMap.put(que.getQuestionId(), que);			
		}
		List<StudentAssignmentStatus> testObjects = new ArrayList<StudentAssignmentStatus>();
		if(!assignmentQuestions.isEmpty()) {
			StudentAssignmentStatus prevTest = assignmentQuestions.get(0).getStudentAssignmentStatus();
			for(AssignmentQuestions studentAssignmentQuestions : assignmentQuestions) {
				currTest = studentAssignmentQuestions.getStudentAssignmentStatus();
				if(prevTest.getStudentAssignmentId() != currTest.getStudentAssignmentId()){					
					maxMarks = prevTest.getAssignmentQuestions().size();
					if(secMarks < 0) {
						 secMarks = 0;
					}
					float percentage = (secMarks*100)/maxMarks;					 
					secMarks = 0;
					prevTest.setPercentage(percentage);
					prevTest.setAcademicGrade(gradeAssessmentsDAO.getAcademicGradeByPercentage(prevTest.getPercentage()));
					testObjects.add(prevTest);
					prevTest = currTest;					
				}				
				if(studentAssignmentQuestions.getAnswer().trim().equalsIgnoreCase(questionMap.get(studentAssignmentQuestions.getQuestions().getQuestionId()).getAnswer().trim())){
					 studentAssignmentQuestions.setSecMarks(1);	
					 secMarks ++;
				}else{
					studentAssignmentQuestions.setSecMarks(0);					
				}
			}
			maxMarks = currTest.getAssignmentQuestions().size();
			if(secMarks < 0) {
				 secMarks = 0;
			}
			float percentage = (secMarks*100)/maxMarks;
			secMarks = 0;
			currTest.setPercentage(percentage);	
			currTest.setAcademicGrade(gradeAssessmentsDAO.getAcademicGradeByPercentage(currTest.getPercentage()));
			testObjects.add(currTest);
			
			
        }		
		subQuestion.setAssignmentQuestions(assignmentQuestions);
		subQuestion.setStudentassignmentStatus(testObjects);
		
		return subQuestion;
	}
	
	

}