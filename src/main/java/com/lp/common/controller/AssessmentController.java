package com.lp.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.dao.GradesDAO;
import com.lp.admin.service.AdminService;
import com.lp.common.dao.AssessmentDAO;
import com.lp.common.service.AssessmentService;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.common.service.PerformanceTaskService;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignmentQuestions;
import com.lp.model.AssignmentType;
import com.lp.model.Grade;
import com.lp.model.JacQuestionFile;
import com.lp.model.JacTemplate;
import com.lp.model.Lesson;
import com.lp.model.PtaskFiles;
import com.lp.model.Questions;
import com.lp.model.QuestionsList;
import com.lp.model.Rubric;
import com.lp.model.RubricScalings;
import com.lp.model.RubricTypes;
import com.lp.model.SubQuestions;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;
/**
 * 
 * @author PRASAD BHVN & SANTHOSH
 *
 */
@Controller
public class AssessmentController extends WebApplicationObjectSupport{
	
	@Autowired
	private AdminService adminservice;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private AssessmentDAO assessmentDAO;
	@Autowired
	private PerformanceTaskService performanceTaskService;
	@Autowired
	private HttpSession session;
	@Autowired
	private CommonService commonService;
	@Autowired
	private GradesDAO gradesDao;
	
	static final Logger logger = Logger.getLogger(AssessmentController.class);
	
	
	// Assessments
		@RequestMapping(value = "/assessments", method = RequestMethod.GET)
		public ModelAndView assessmentView(HttpSession session) {
			List<Grade> teacherGrades = new ArrayList<Grade>();
			ModelAndView model = new ModelAndView("assessments/assessment_main", "questionsList", new QuestionsList());
			model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
			model.addObject("tab", WebKeys.LP_TAB_CURRICULUM);
			model.addObject("subTab", WebKeys.LP_SUB_TAB_CREATE_ASSESSMENTS);
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
			try{
				if(teacherObj == null){
					//admin
					UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
					List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
					model.addObject("grList", schoolgrades);
				}else{
					//teacher
					teacherGrades = curriculumService.getTeacherGrades(teacherObj);
					model.addObject("grList", teacherGrades);
				}
			}catch(DataException e){
				logger.error("Error in assessmentView() of AssessmentController"
						+ e);
				e.printStackTrace();
				model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
			}
			if(session.getAttribute("isError") != null){
				model.addObject("isErro", session.getAttribute("isError").toString());
				session.removeAttribute("isError");
			}
			if(session.getAttribute("helloAjax") != null){
				model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
				session.removeAttribute("helloAjax");
			}	
			return  model;
		}
		
		@RequestMapping(value = "/createAssessments",method=RequestMethod.POST)
		public @ResponseBody void createAssessments(@RequestParam("files") MultipartFile file, HttpServletRequest request,HttpServletResponse response,HttpSession session,@ModelAttribute("questionsList") QuestionsList questionsList) throws Exception { 
			
			long assessmentTypeId;
			long lessonId=-1;
			long gradeId;
			boolean status = false;
			String usedFor = null;
			if(request != null && session != null){
	        	UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
	  	        usedFor = request.getParameter("usedFor");
	  	        try{
		        	if(userReg != null){
		        		try{
		    				//Create Assessments (Admin && Teacher)
		        			gradeId =  Long.parseLong(request.getParameter("gradeId"));
		    				List<Questions> questions = questionsList.getQuestions();
		    				assessmentTypeId =  Long.parseLong(request.getParameter("assignmentTypeId"));
		    				if(assessmentTypeId!=8 && assessmentTypeId!=20 && assessmentTypeId!=19)
		    				lessonId =Long.parseLong(request.getParameter("lessonId"));
		    				if(assessmentTypeId == 14){
		    					//Create Jac Template
		    					status = assessmentService.createJacTemplate(lessonId, assessmentTypeId, questionsList, usedFor, file,gradeId);
		    				}else if(assessmentTypeId == 7 || assessmentTypeId == 19){
		    					//Create Sentence Structure or comprehension types
		    					assessmentService.createSentenceStructure(lessonId, assessmentTypeId, questionsList.getSubQuestions().get(0), usedFor,gradeId);
		    					status = true;
		    				}else if(assessmentTypeId == 13){
		    					//Create PerformanceTask
		    					questions = getPerformanceQuestions(questionsList, request, userReg);
		    					status = assessmentService.createPerformancetask(assessmentTypeId, lessonId, usedFor, questions,gradeId);
		    				}else{
		    					status = assessmentService.createAssessments(assessmentTypeId, lessonId, usedFor, questions,gradeId);
		    				}		    				
		    				if (status) {
		    					response.getWriter().write(WebKeys.LP_CREATED_SUCCESS);
							} else {
								// Edit units failed
								response.getWriter().write(WebKeys.LP_EDIT_UNIT_FAILED);
							}
		    			}catch(DataException e){
		    				status = false;
		    				response.getWriter().write(WebKeys.LP_SYSTEM_ERROR);
		    				logger.error("Error in createAssessments() of AssessmentController"+ e);
		    			}
		    		}
		        	
		        }catch(DataException e){
		        	status = false;
		        	logger.error("Error in createAssessments() of AssessmentController"+ e);
		        	response.getWriter().write(WebKeys.LP_CREATE_ASSESSMENTS_FAILED);
		        }
				
	        }else{
	        	// request and session objects should not null
	        	response.getWriter().write(WebKeys.LP_SYSTEM_ERROR);
	        }
		}
		
		@RequestMapping(method = RequestMethod.GET, value="/assessmentTypeView")
		protected ModelAndView assessmentTypeView(
				@RequestParam("noOfQuestions") long noOfQuestions,
				@RequestParam("assignmentTypeId") long assignmentTypeId, 
				@RequestParam("noOfOptions") long noOfOptions, 
				@RequestParam("assignmentValue") String assignmentValue, 
				@RequestParam("usedFor") String usedFor, 
				@RequestParam("tab") String tab, 
				@RequestParam("lessonId") long lessonId, 
				@RequestParam("questionId") long questionId, 
				@ModelAttribute("questionsList") QuestionsList questionsList,
				@RequestParam("gradeId") long gradeId, 
				BindingResult result) throws SQLDataException{
			ModelAndView model = new ModelAndView("assessments/essays_type");
			if(assignmentTypeId == 1 || assignmentTypeId == 2 || assignmentTypeId == 6){
				model = new ModelAndView("Ajax/assessments/essays_type", "questionsList", new QuestionsList());
			}
			else if(assignmentTypeId == 3){
				String answer = "";
				if(questionId > 0){
					questionsList =	assessmentService.getQuestionByQuestionId(questionId);
					answer = questionsList.getQuestions().get(0).getAnswer();
				}
				model = new ModelAndView("Ajax/assessments/multiplechoice_type","questionsList", questionsList);
				model.addObject("answer", answer);
				if(questionId > 0)
					model.addObject("mode", "Edit");
				else
					model.addObject("mode", "Create");
			}
			else if(assignmentTypeId == 4){
				model = new ModelAndView("Ajax/assessments/fillblanks_type");
			}
			else if(assignmentTypeId == 5){
				model = new ModelAndView("Ajax/assessments/trueorflase_type");
			}
			else if(assignmentTypeId == 7 || assignmentTypeId == 19){
				List<SubQuestions> subQuestionsLt = new ArrayList<SubQuestions>();
				List<Questions> questionsLt = new ArrayList<Questions>();
				if(questionId > 0){
					SubQuestions subQuestion =	assessmentService.getSubQuestionBySubQuestionId(questionId);
					subQuestionsLt.add(subQuestion);
					questionsLt = subQuestion.getQuestionses();
					questionsList.setSubQuestions(subQuestionsLt);
				}
				model = new ModelAndView("Ajax/assessments/sentence_type","questionsList", questionsList);
				if(assignmentTypeId == 19){
					model.addObject("comprehensionTypes", commonService.getComprehensionTypes());			
				}
				model.addObject("questionsLt", questionsLt);
				if(questionId > 0)
					model.addObject("mode", "Edit");
				else
					model.addObject("mode", "Create");
			}
			else if(assignmentTypeId == 8 || assignmentTypeId==20){
				model = new ModelAndView("Ajax/assessments/benchmark_type");
				if(questionId > 0){
					UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.LP_USER_REGISTRATION);
					questionsList =	assessmentService.getQuestionByQuestionId(questionId);
					
					model.addObject("mode", "Edit");
					model.addObject("regId", userReg.getRegId());
				}else{
					model.addObject("mode", "Create");
				}
			}
			
			else if(assignmentTypeId == 13){
				List<Questions> questionLt = new ArrayList<Questions>();
				if(questionId > 0){
					List<PtaskFiles> ptaskFiles = new ArrayList<PtaskFiles>(); 
					ptaskFiles = assessmentDAO.getPtaskFilesByUser(questionId);
					
					List<Rubric> rubrics = new ArrayList<Rubric>(); 
					rubrics = performanceTaskService.getRubrics(questionId);
					Questions question = new Questions();
					questionsList =	assessmentService.getQuestionByQuestionId(questionId);
					question = questionsList.getQuestions().get(0);
					question.setRubrics(rubrics);
					question.setPtaskFiles(ptaskFiles);
					questionLt.add(question);
					questionsList.setQuestions(questionLt);
					if(questionId > 0)
						model.addObject("mode", "Edit");
					else
						model.addObject("mode", "Create");
				}
				model = new ModelAndView("Ajax/assessments/performance_type","questionsList", questionsList);
				List<RubricTypes> rubTypes = assessmentService.getRubricTypes();
				model.addObject("rubricType", rubTypes);
				model.addObject("rubricScalings", assessmentService.getRubricScalings());
				model.addObject("questionLt", questionLt);
				if(questionId > 0)
					model.addObject("mode", "Edit");
				else
					model.addObject("mode", "Create");
			}
			else if(assignmentTypeId == 14){
				List<JacTemplate> jacTemplateLt = new ArrayList<JacTemplate>();
				JacQuestionFile jacQuestionFile = new JacQuestionFile();
				if(questionId > 0){
					jacTemplateLt = assessmentService.getJacTemplateByFileId(questionId);
					questionsList.setJacTemplate(jacTemplateLt);
					jacQuestionFile = assessmentService.getJacQuestionFileByFileId(questionId);
				}
				model = new ModelAndView("Ajax/assessments/jactemplate_type","questionsList", questionsList);
				model.addObject("jacTemplateSize", jacTemplateLt.size());
				model.addObject("jacQuestionFile", jacQuestionFile);
				if(questionId > 0)
					model.addObject("mode", "Edit");
				else
					model.addObject("mode", "Create");
				
			}
			model.addObject("questionId", questionId);
			model.addObject("questionsList", questionsList);
			model.addObject("assignmentValue", assignmentValue);
			model.addObject("noOfQuestions", noOfQuestions-1);
			model.addObject("usedFor", usedFor);
			model.addObject("tab", tab);
			model.addObject("lessonId", lessonId);
			model.addObject("assignmentTypeId", assignmentTypeId);
			model.addObject("gradeId",gradeId);
			return model;
		}	
		
		@RequestMapping(method = RequestMethod.POST, value="/jacTemplateView")
		public ModelAndView jacTemplateView(@RequestParam("assignmentTypeId") long assignmentTypeId,@RequestParam("titleNamesArray") String titleNames,@RequestParam("noOfQuestionsArray") String noOfQuestions){
			
			ModelAndView model = new ModelAndView("assessments/jactemplate_type_sub","questionsList",new QuestionsList());
			String[] titleNamesArray = titleNames.split(",");
			String[] noOfQuestionsArray = noOfQuestions.split(",");
			List<JacTemplate> jacList = new ArrayList<>();
			for(int count=0; count< noOfQuestionsArray.length; count++){
				JacTemplate jacTemplate = new JacTemplate();
				String titleName = titleNamesArray[count];
				String noOfTitle = noOfQuestionsArray[count];
				long noOfQues = Long.parseLong(noOfTitle);
				jacTemplate.setTitleName(titleName);
				jacTemplate.setNoOfQuestions(noOfQues-1);
				jacList.add(jacTemplate);
			}
			model.addObject("jacList", jacList);
			return model;
		}	
		
		@RequestMapping(value = "/audioRecord", method = RequestMethod.GET)
		public ModelAndView audioRecord(HttpSession session) {
			
			ModelAndView model = new ModelAndView("assessments/audio_record");
					
			return  model;
		}
	
		@RequestMapping(value = "/showRubric", method = RequestMethod.GET)
		public ModelAndView showRubric(@RequestParam("rubType") long rubType,@RequestParam("rubScore") long rubScore,@RequestParam("qNumber") long qNumber,@RequestParam("mode") String mode,@ModelAttribute("questionsList") QuestionsList questionsList) {
			
			
			ModelAndView model = new ModelAndView("Ajax/assessments/performance_type_sub");
			model.addObject("questionsList", questionsList);
			model.addObject("rubType", rubType);
			model.addObject("rubScore", rubScore);
			model.addObject("qNumber", qNumber);
			model.addObject("mode", mode);
					
			return  model;
		}
		
		private List<Questions> getPerformanceQuestions(QuestionsList questionList,HttpServletRequest request,UserRegistration userReg){
				List<Questions> questions = questionList.getQuestions();
				int temp  = 0;
				for(Questions ques : questions){
					if(!request.getParameter("noOfFiles").isEmpty()){
						int questionNum = Integer.parseInt(request.getParameter("noOfFiles"));
						List<MultipartFile> perfomranceFiles = questionList.getFiles();
						List<MultipartFile> sublist = perfomranceFiles.subList(temp, temp+questionNum);
						List<PtaskFiles> pfiles = getPerformanceFiles(questionNum,ques,sublist,userReg);
						ques.setPtaskFiles(pfiles);
						temp = temp+questionNum;
					}else{
						temp++;
					}
				}
				return questions;	
		}
		
		private List<PtaskFiles> getPerformanceFiles(long questionNum,Questions question, List<MultipartFile> sublist,UserRegistration userReg){
			
			List<PtaskFiles> ptaskFilesList  = new ArrayList<PtaskFiles>();
			for(MultipartFile performanceFile: sublist){
					PtaskFiles ptaskFile = new PtaskFiles();
					ptaskFile.setFilename(performanceFile.getOriginalFilename());
					ptaskFile.setCreatedBy(userReg.getRegId());
					ptaskFile.setQuestions(question);
					ptaskFile.setPfile(performanceFile);
					ptaskFilesList.add(ptaskFile);
			}
			
			return ptaskFilesList;
		}
		
		//editAssessments
		@RequestMapping(value = "/editAssessments", method = RequestMethod.GET)
		public ModelAndView editAssessments(HttpSession session) {
			List<Grade> teacherGrades = new ArrayList<Grade>();
			ModelAndView model = new ModelAndView("assessments/edit_assessment_main", "questionsList", new QuestionsList());
			model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
			model.addObject("tab", WebKeys.LP_TAB_EDIT);
			model.addObject("subTab", WebKeys.LP_SUB_TAB_EDIT_ASSESSMENTS);
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			try{
				if(teacherObj == null){
					//admin
					UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
					List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
					model.addObject("grList", schoolgrades);
				}else{
					//teacher
					teacherGrades = curriculumService.getTeacherGrades(teacherObj);
					model.addObject("grList", teacherGrades);
				}
			}catch(DataException e){
				logger.error("Error in editAssessments() of AssessmentController"
						+ e);
				e.printStackTrace();
				model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
			}
			if(session.getAttribute("helloAjax") != null){
				model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
				session.removeAttribute("helloAjax");
			}
			if(session.getAttribute("isError") != null){
				model.addObject("isError", session.getAttribute("isError").toString());
				session.removeAttribute("isError");
			}
			return  model;
		}
		
		@RequestMapping(method = RequestMethod.GET, value="/editAssessmentTypeView")
		protected ModelAndView editAssessmentTypeView(@RequestParam("assignmentTypeId") long assignmentTypeId,@RequestParam("lessonId") long lessonId,
				@RequestParam("usedFor") String usedFor,@ModelAttribute("questionsList") QuestionsList questionsList,BindingResult result,
				HttpServletRequest request,@RequestParam("gradeId") long gradeId){
			ModelAndView model = new ModelAndView("Ajax/assessments/edit_essays_type");
			List<Questions> questions = Collections.emptyList();
			if(assignmentTypeId == 1 || assignmentTypeId == 2 || assignmentTypeId == 6){
				questions = assessmentService.getQuestionsByAssignmentType(assignmentTypeId,lessonId,usedFor,gradeId);
				questionsList.setQuestions(questions);
				model = new ModelAndView("Ajax/assessments/edit_essays_type","questionsList",questionsList);
			}
			else if(assignmentTypeId == 3){
				questions = assessmentService.getQuestionsByAssignmentType(assignmentTypeId,lessonId,usedFor,gradeId);
				questionsList.setQuestions(questions);
				model = new ModelAndView("Ajax/assessments/edit_multiplechoice_type","questionsList",questionsList);
			}
			else if(assignmentTypeId == 4){
				questions = assessmentService.getQuestionsByAssignmentType(assignmentTypeId,lessonId,usedFor,gradeId);
				questionsList.setQuestions(questions);
				model = new ModelAndView("Ajax/assessments/edit_fillblanks_type","questionsList",questionsList);
			}
			else if(assignmentTypeId == 5){
				questions = assessmentService.getQuestionsByAssignmentType(assignmentTypeId,lessonId,usedFor,gradeId);
				questionsList.setQuestions(questions);
				model = new ModelAndView("Ajax/assessments/edit_trueorflase_type","questionsList",questionsList);
			}
			else if(assignmentTypeId == 7 || assignmentTypeId == 19){
				List<SubQuestions> sQuestions = Collections.emptyList();
				sQuestions = assessmentService.getSubQuestionsByAssignmentType(assignmentTypeId,lessonId,usedFor,gradeId);	
				questionsList.setSubQuestions(sQuestions);
				model = new ModelAndView("Ajax/assessments/edit_sentence_type","questionsList",questionsList);
				model.addObject("quesCount", sQuestions.size());
				model.addObject("numOfOptions", sQuestions.size()-1);
			}
			else if(assignmentTypeId == 8 || assignmentTypeId==20){
				questions = assessmentService.getQuestionsByAssignmentType(assignmentTypeId,lessonId,usedFor,gradeId);
				questionsList.setQuestions(questions);
				model = new ModelAndView("Ajax/assessments/edit_benchmark_type","questionsList",questionsList);
			}
			else if(assignmentTypeId == 13){
				questions = assessmentService.getQuestionsByAssignmentType(assignmentTypeId,lessonId,usedFor,gradeId);
				questions = assessmentService.getRubricsByQuestionId(questions);
				questionsList.setQuestions(questions);
				questions = assessmentService.getPtaskFiles(questions);
				model = new ModelAndView("Ajax/assessments/edit_performance_type","questionsList",questionsList);
				List<RubricTypes> rubTypes = assessmentService.getRubricTypes();
				model.addObject("rubricType", rubTypes);
				model.addObject("rubricScalings", assessmentService.getRubricScalings());
			}
			else if(assignmentTypeId == 14){
				List<JacQuestionFile> jacFileQuestions = Collections.emptyList();
				jacFileQuestions = assessmentService.getJacFileQuestionsByUsedFor(lessonId,usedFor);
				model = new ModelAndView("Ajax/assessments/edit_jactemplate_type","questionsList",questionsList);
				model.addObject("jacFileQuestions", jacFileQuestions);			
				model.addObject("jacCount", jacFileQuestions.size());
			}
			model.addObject("assignmentTypeId", assignmentTypeId);
			model.addObject("questionsList", questionsList);
			model.addObject("questions", questions);
			model.addObject("count", questions.size());
			model.addObject("noOfQuestions", questions.size()-1);
			model.addObject("helloAjax",WebKeys.LP_NO_QUESTIONS);
			model.addObject("gradeId",gradeId);
			return model;
		}
		//Update Assessments
		@RequestMapping(value = "/updateAssessments", method = RequestMethod.POST)
		public @ResponseBody View updateAssessments(@RequestParam("files") MultipartFile file, HttpServletRequest request,HttpServletResponse response,HttpSession session,@ModelAttribute("questionsList") QuestionsList questionsList,Model model) throws Exception { 
			long assignmentTypeId;
			long lessonId;
			long questionId = 0;
			long gradeId;
			boolean status = false;
			boolean isError = false;
			Lesson lesson = new Lesson();
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
  	        String usedFor = request.getParameter("usedFor");
  	        String tab = request.getParameter("tab");
  	        session.setAttribute("usedFor", usedFor);
  	        session.setAttribute("tab", tab);
  	        int id;
			try{
				gradeId = Long.parseLong(request.getParameter("gradeId"));
				List<Questions> questions = questionsList.getQuestions();
				ArrayList<Long> questionIdLt = new ArrayList<>();
				Questions editQuestion = null;
				assignmentTypeId = Long.parseLong(request.getParameter("assignmentTypeId"));
				id =  Integer.parseInt(request.getParameter("id"));
				if(assignmentTypeId!=8 && assignmentTypeId!=20 && assignmentTypeId!=19){
				lessonId = Long.parseLong(request.getParameter("lessonId"));
				lesson.setLessonId(lessonId);
				if(assignmentTypeId==14)
					lesson = assessmentDAO.getLessonById(lessonId);	
				
				}
				AssignmentType assignmentType = new AssignmentType();
				assignmentType.setAssignmentTypeId(assignmentTypeId);
				assignmentType.setUsedFor(usedFor);
				
				if(questionsList.getSubQuestions().size() > 0){
					SubQuestions subQuestion = questionsList.getSubQuestions().get(0);
					subQuestion.setCreatedBy(userReg.getRegId());
					subQuestion.setAssignmentType(assignmentType);
					if(assignmentTypeId != 19)
					subQuestion.setLesson(lesson);
					subQuestion.setUsedFor(usedFor);
					subQuestion.setGrade(gradesDao.getGrade(gradeId));
					subQuestion.setSubQuesStatus(WebKeys.ACTIVE);
					if(subQuestion.getQuestionses().size() > 0){
						for (Questions question : subQuestion.getQuestionses()) {
							if((assignmentTypeId != 19 || (assignmentTypeId == 19 && question.getQuestion() != null))){
								if(assignmentTypeId != 19)
								  question.setLesson(lesson);
								if(assignmentTypeId == 19){
									AssignmentType assType = assessmentDAO.getassignmentTypeById(question.getAssignmentType().getAssignmentTypeId());
									question.setAssignmentType(assType);
								}else{
									question.setAssignmentType(assignmentType);
								}
								question.setCreatedBy((int) userReg.getRegId());
								question.setUsedFor(usedFor);
								question.setSubQuestions(questionsList.getSubQuestions().get(0));
								question.setGrade(gradesDao.getGrade(gradeId));
								questionIdLt.add(question.getQuestionId());
								
							}
						}
					}
				}
				if(questions.size() > 0){
					editQuestion = questions.get(id);
					if(assignmentTypeId!=8 && assignmentTypeId!=20 && assignmentTypeId!=19){
					editQuestion.setLesson(lesson);}
					if(request.getParameter("questionId") != null || !request.getParameter("questionId").equalsIgnoreCase(""))
					   questionId = Long.parseLong(request.getParameter("questionId"));
					editQuestion.setAssignmentType(assignmentType);
					editQuestion.setCreatedBy((int) userReg.getRegId());
					editQuestion.setUsedFor(usedFor);
					editQuestion.setGrade(gradesDao.getGrade(gradeId));
					editQuestion.setQueStatus(WebKeys.ACTIVE);
					if(questionId > 0)
						editQuestion.setQuestionId(questionId);
				}
				if(assignmentTypeId == 7 || assignmentTypeId == 19){
					SubQuestions subQuestions = questionsList.getSubQuestions().get(0);
					subQuestions = assessmentService.updateSentenceStructure(subQuestions);
					if(subQuestions.getQuestionses() != null && subQuestions.getQuestionses().size() > 0){
						model.addAttribute("questionId",subQuestions.getQuestionses().get(id).getQuestionId());
					}
					status = true;
				}else if(assignmentTypeId == 13){
					questions = getPerformanceQuestions(questionsList, request, userReg);
					long rubricTypeId = editQuestion.getRubrics().get(0).getRubricTypes().getRubricTypeId();
					long rubricScalingId = editQuestion.getRubrics().get(0).getRubricScalings().getRubricScalingId();
					for(Rubric r:editQuestion.getRubrics()){
						RubricTypes rubricTypes = new  RubricTypes();
						RubricScalings rubricScalings = new RubricScalings();
						rubricTypes.setRubricTypeId(rubricTypeId);
						rubricScalings.setRubricScalingId(rubricScalingId);
                        r.setRubricTypes(rubricTypes);
                        r.setRubricScalings(rubricScalings);
					}
					status = assessmentService.updateAssessments(editQuestion);
					List<PtaskFiles> ptaskFiles = new ArrayList<PtaskFiles>(); 
					ptaskFiles = assessmentDAO.getPtaskFilesByUser(questionId);
					model.addAttribute("ptaskFiles",ptaskFiles);
				}else if(assignmentTypeId == 14){
					String path = "";
				    long jacQuestionFileId = Long.parseLong(request.getParameter("jacQuestionFileId"));
					//lesson = assessmentDAO.getLessonById(lessonId);		
					JacQuestionFile jqf = new JacQuestionFile();
					jqf.setUsedFor(usedFor);
					jqf.setLesson(lesson);
					jqf.setUserRegistration(userReg);
					jqf.setJacQuestionFileId(jacQuestionFileId);
					String jacUploadedFileName = file.getOriginalFilename();
					String jacFileName = request.getParameter("jacFileName");
					if(jacUploadedFileName.length() > 0){
						jqf.setFilename(jacUploadedFileName);
						model.addAttribute("filename",jacUploadedFileName);
					}else if(jacFileName.length() > 0){
						jqf.setFilename(jacFileName);
						model.addAttribute("filename",jacFileName);
					}else{
						jqf.setFilename("");
						model.addAttribute("filename","");
					}
					assessmentDAO.saveJacQuestioFile(jqf);
					if(jacUploadedFileName.length() > 0)
					  path = assessmentService.saveJacTemplateFile(jqf, file,assignmentTypeId);
					if(path.length() > 0)
						status = true;
					else
						status = false;
				
				}else if(assignmentTypeId == 5 || assignmentTypeId == 3 || assignmentTypeId == 4){
					status = assessmentService.updateAssignmentQuestions(editQuestion);
				}else{
					status = assessmentService.updateAssessments(editQuestion);
				}
				
			}catch(Exception e){
				status = false;
				model.addAttribute("status",WebKeys.LP_FAILED);
				logger.error("Error in updateAssessments() of of AssessmentController"+ e);
				session.setAttribute("isError", isError);
			}
			if (status) {
				model.addAttribute("status",WebKeys.LP_UPDATED_SUCCESS);
			} else {
				model.addAttribute("status",WebKeys.LP_FAILED);
			}
			return new MappingJackson2JsonView();
		}
		
		//Update Assessments
				@RequestMapping(value = "/removeAssessments", method = RequestMethod.POST)
				public @ResponseBody void removeAssessments(@RequestParam("files") MultipartFile file, HttpServletRequest request,HttpServletResponse response,HttpSession session,@ModelAttribute("questionsList") QuestionsList questionsList) throws Exception { 
				    long assessmentTypeId;
					boolean status = false,jacStatus=false,chkStatus=false;
		  	        String usedFor = request.getParameter("usedFor");
		  	        String tab = request.getParameter("tab");
		  	        session.setAttribute("usedFor", usedFor);
		  	        session.setAttribute("tab", tab);
		  	        int id;
					try{
						List<Questions> questions = questionsList.getQuestions();
						assessmentTypeId = Long.parseLong(request.getParameter("assignmentTypeId"));
						id =  Integer.parseInt(request.getParameter("id"));
						if(assessmentTypeId == 14){
							jacStatus=assessmentService.checkJacTemplateExists(id);
							if(!jacStatus){
							 status = assessmentService.deleteJacTempleteQuestion(id);
							 }
							
						}else if(assessmentTypeId == 7 || assessmentTypeId == 19){
							if(assessmentTypeId==19){
								status = assessmentService.deleteSentenceStructure(id);
							}else{
							chkStatus=assessmentService.checkSubQuestionExists(id);
							if(!chkStatus){
							status = assessmentService.deleteSentenceStructure(id);}
						}
						}else{
							if(assessmentTypeId == 8 || assessmentTypeId == 20){
								status = assessmentService.removeAssessments(questions.get(id));}
							else{
							jacStatus=assessmentService.checkQuestionExists(questions.get(id).getQuestionId());
							if(!jacStatus){
							status = assessmentService.removeAssessments(questions.get(id));
							}}
						}
						if (status) {
							response.getWriter().write(WebKeys.LP_REMOVED_SUCCESS);
						} else {
							if(jacStatus)
							response.getWriter().write(WebKeys.LP_DELETE);
							else
								response.getWriter().write(WebKeys.LP_REMOVED_FAILED);	
						}
					}catch(DataException e){
						logger.error("Error in removeAssessments() of of AssessmentController"+ e);
						response.getWriter().write(WebKeys.LP_REMOVED_FAILED);
					}
					
				}
				@RequestMapping(value = "/assessmentsFileDownload",method = RequestMethod.GET)
			    public void assessmentsFileDownload(HttpServletRequest request,
			            HttpServletResponse response, HttpSession session,@RequestParam("questionId") long questionId,@RequestParam("fileName") String fileName,@RequestParam("type") String type) throws IOException { 
					FileInputStream fileIn = null ;
					ServletOutputStream out = null;
					UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
					String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
					String pTaskFileFullPath = "";
					if(type.equalsIgnoreCase(WebKeys.PERFORMANCE_TESTS))
					     pTaskFileFullPath = uploadFilePath +File.separator+ WebKeys.PERFORMANCE_TESTS+File.separator+questionId+File.separator+fileName;
					else if(type.equalsIgnoreCase(WebKeys.JAC_TEMPLATE))
						 pTaskFileFullPath = uploadFilePath +File.separator+ WebKeys.JAC_TEMPLATE+File.separator+questionId+File.separator+fileName;
					try {		       
						File file = new File(pTaskFileFullPath);
					    fileIn = new FileInputStream(file);
					    response.setContentType("application/octet-stream");
					    response.setHeader("Content-Disposition", "attachment;filename="+file.getName());		        
					    out = response.getOutputStream();
					    byte[] outputByte = new byte[(int)file.length()];
					    while(fileIn.read(outputByte, 0, (int)file.length()) != -1){
					    	out.write(outputByte, 0, (int)file.length());
					    }
					}catch (Exception e){
						logger.error("Error in assessmentsFileDownload() of AssessmentController"+ e);
						e.printStackTrace();
					}finally{
						try {
							if (null != fileIn)
								fileIn.close();
							if (null != fileIn)
								out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}		 
					}
			    }
		@RequestMapping(value = "/deletePerformancefile", method = RequestMethod.GET)
		public View deletePerformancefile(@RequestParam("fileId") long fileId,@RequestParam("questionId") long questionId,Model model) {
			boolean status = false;
			try{
				status = assessmentService.deletePerformancefile(fileId);
				List<PtaskFiles> ptaskFiles = new ArrayList<PtaskFiles>(); 
				ptaskFiles = assessmentDAO.getPtaskFilesByUser(questionId);
				model.addAttribute("ptaskFiles",ptaskFiles);
			}catch(Exception e){
				logger.error("Error in deletePerformancefile() of AssessmentController"	+ e);
			}	
			model.addAttribute("status",status);
			return new MappingJackson2JsonView();
		}		
	
		@RequestMapping(value = "/getJacQuestions", method = RequestMethod.GET)
		public ModelAndView getJacQuestions(@RequestParam("jacFileId") long jacQuestionFileId,@ModelAttribute("questionsList") QuestionsList questionsList) {
			
			ModelAndView model = new ModelAndView("assessments/edit_jactemplate_sub","questionsList",questionsList);
			List<JacTemplate> jacTemp = new ArrayList<JacTemplate>();
			try{
				jacTemp = assessmentService.getJacTemplateByFileId(jacQuestionFileId);
				questionsList.setJacTemplate(jacTemp);
				model.addObject("questionsList", questionsList);
			}catch(DataException e){
				logger.error("Error in getJacQuestions() of of AssessmentController"+ e);
			}
			return  model;
		}
		
		@RequestMapping(value = "/saveJacTemplate", method = RequestMethod.GET)
		public View saveJacTemplate(
				@RequestParam("jacQuestionFileId") long jacQuestionFileId,
				@RequestParam("titleName") String titleName,
				@RequestParam("jacTemplateId") long jacTemplateId,
				HttpServletRequest request,
				Model model) {
			try{
				 JacTemplate jacTemplate = new JacTemplate();
				 if(jacTemplateId > 0){
					 jacTemplate = assessmentService.getJacTemplate(jacTemplateId);
				 }
				 JacQuestionFile jacQuestionFile = assessmentService.getJacQuestionFileByFileId(jacQuestionFileId);
				 jacTemplate.setJacQuestionFile(jacQuestionFile);
				 jacTemplate.setTitleName(titleName);
				boolean status =  assessmentService.saveJacTemplate(jacTemplate);
				model.addAttribute("jacTemplateId",jacTemplate.getJacTemplateId());
				if(status){
				   model.addAttribute("status",WebKeys.LP_UPDATED_SUCCESS);
				}else{
					model.addAttribute("status",WebKeys.LP_FAILED);
				}
			}catch(Exception e){
				logger.error("Error in saveJacTemplate() of AssessmentController"	+ e);
				model.addAttribute("status",WebKeys.LP_FAILED);
			}	

			return new MappingJackson2JsonView();
		}
		
		@RequestMapping(value = "/saveQuestion", method = RequestMethod.GET)
		public View saveQuestion(
				@RequestParam("assignmentTypeId") long assignmentTypeId,
				@RequestParam("lessonId") long lessonId,
				@RequestParam("answer") String answer,
				@RequestParam("usedFor") String usedFor,
				@RequestParam("jacTemplateId") long jacTemplateId,
				@RequestParam("questionId") long questionId,
				Model model) {
			try{
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				JacTemplate jacTemplate = assessmentService.getJacTemplate(jacTemplateId);
				Questions question = new Questions();
				if(questionId > 0)
					question.setQuestionId(questionId);
				question.setAnswer(answer);
				AssignmentType assignmentType = new AssignmentType();
				assignmentType.setAssignmentTypeId(assignmentTypeId);
				assignmentType.setUsedFor(usedFor);
				question.setAssignmentType(assignmentType);
				Lesson lesson = assessmentDAO.getLessonById(lessonId);
				question.setLesson(lesson);
				question.setCreatedBy((int) userReg.getRegId());
				question.setUsedFor(usedFor);
				jacTemplate.setNoOfQuestions(jacTemplate.getNoOfQuestions()+1);
				question.setJacTemplate(jacTemplate);
				assessmentService.updateAssignmentQuestions(question);
				//question = assessmentService.saveQuestion(question);
				model.addAttribute("questionId",question.getQuestionId());
				model.addAttribute("status",WebKeys.LP_UPDATED_SUCCESS);
			}catch(Exception e){
				logger.error("Error in saveQuestion() of AssessmentController"	+ e);
				model.addAttribute("status",WebKeys.LP_FAILED);
			}	

			return new MappingJackson2JsonView();
		}
		
		@RequestMapping(value = "/deleteJacTemplate", method = RequestMethod.GET)
		public View deleteJacTemplate(@RequestParam("jacTemplateId") long jacTemplateId,Model model) {
			boolean delTitle = false,stat=false;
			try{
				JacTemplate jacTemplate =assessmentService.getJacTemplate(jacTemplateId);
				List<Questions> questionsLt = jacTemplate.getQuestionsList();
				for (Questions questions : questionsLt) {
					stat=assessmentService.checkQuestionExists(questions.getQuestionId());
					if(stat)
						break;
				}
				if(!stat){
					delTitle = assessmentService.deleteJacTemplete(jacTemplate);
					if (delTitle) {
						model.addAttribute("delTitStatus",WebKeys.LP_REMOVED_SUCCESS);
					} else {
						model.addAttribute("delTitStatus",WebKeys.LP_FAILED);
					}
				}else{
					model.addAttribute("delTitStatus",WebKeys.LP_DELETE);
				}
				
			}catch(Exception e){
				logger.error("Error in deleteJacTemplate() of AssessmentController"	+ e);
			}	
			return new MappingJackson2JsonView();
		}
		
		@RequestMapping(value = "/deleteQuestion", method = RequestMethod.GET)
		public View deleteQuestion(@RequestParam("questionId") long questionId,Model model) {
			boolean delStatus = false;
			try{
				QuestionsList question = assessmentService.getQuestionByQuestionId(questionId);
				delStatus = assessmentService.deleteQuestion(question.getQuestions().get(0));
					if (delStatus) {
						model.addAttribute("status",WebKeys.LP_REMOVED_SUCCESS);
					} else {
						model.addAttribute("status",WebKeys.LP_FAILED);
					}
			}
			catch(Exception e){
				
				logger.error("Error in deleteQuestion() of AssessmentController"	+ e);
			}
			
			return new MappingJackson2JsonView();
		}
		
		//Update Assessments
				@RequestMapping(value = "/checkAlreadyAssigned", method = RequestMethod.POST)
				public @ResponseBody View checkAlreadyAssigned(@RequestParam("files") MultipartFile file, HttpServletRequest request,HttpServletResponse response,HttpSession session,@ModelAttribute("questionsList") QuestionsList questionsList,Model model) throws Exception { 
					long questionId = 0;
					long assignmentId = 0;
					boolean isAlreadyAssigned = false;
					try{
						if(questionsList.getSubQuestions().size() > 0){
							SubQuestions subQuestion = questionsList.getSubQuestions().get(0);
							if(subQuestion.getQuestionses().size() > 0){
								questionId = subQuestion.getQuestionses().get(0).getQuestionId();
								if(questionId > 0)
									assignmentId = assessmentService.checkQuestionAssigned(questionId);
								if(assignmentId > 0)
									isAlreadyAssigned = true;
								model.addAttribute("isAlreadyAssigned",isAlreadyAssigned);
								model.addAttribute("assignmentId",assignmentId);
							}
						}
					}catch(Exception e){
						
					}
					return new MappingJackson2JsonView();
				}
		   	
}
