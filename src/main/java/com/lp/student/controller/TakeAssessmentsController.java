package com.lp.student.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.common.service.PerformanceTaskService;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.AssignedPtasks;
import com.lp.model.AssignmentQuestions;
import com.lp.model.FluencyAudioFileUpload;
import com.lp.model.GradingTypes;
import com.lp.model.JacTemplate;
import com.lp.model.RflpPractice;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.model.UserRegistration;
import com.lp.student.service.StudentService;
import com.lp.student.service.TakeAssessmentsService;
import com.lp.teacher.service.AssignAssessmentsService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class TakeAssessmentsController extends WebApplicationObjectSupport {

	static final Logger logger = Logger.getLogger(TakeAssessmentsController.class);

	@Autowired
	private HttpSession session;
	@Autowired
	private TakeAssessmentsService takeAssessmentsService;	@Autowired
	private PerformanceTaskService performanceTaskService;
	@Autowired
	private StudentService studentService;
	@Autowired
	AndroidPushNotificationsService apns;
	@Autowired
	AssignAssessmentsService assignAssessmentsService;

	@RequestMapping(value = "/goToStudentAssessmentsPage", method = RequestMethod.GET)
	public ModelAndView goToStudentAssessmentsPage() {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		ModelAndView model = new ModelAndView("Student/take_assessments",
				"studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("tab", WebKeys.LP_TAB_VIEW_ASSESSMENTS);
		if(session.getAttribute("studentTests")!=null && session.getAttribute("helloAjax") != null) {
			model.addObject("studentTests", session.getAttribute("studentTests"));
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
		}
		else{
			model.addObject("studentTests", takeAssessmentsService.getStudentTests(student, WebKeys.LP_USED_FOR_ASSESSMENT, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED));
		}
		session.removeAttribute("helloAjax");
		session.removeAttribute("studentTests");
		return model;
	}

	@RequestMapping(value = "/goToStudentHomeworksPage", method = RequestMethod.GET)
	public ModelAndView goToStudentHomeworksPage() {
		ModelAndView model = new ModelAndView("Student/take_assessments",
				"studentAssignmentStatus", new StudentAssignmentStatus());
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);		
		model.addObject("usedFor", WebKeys.LP_USED_FOR_HOMEWORKS);
		model.addObject("tab", WebKeys.LP_TAB_VIEW_CURRENT_HOMEWORK);
		if(session.getAttribute("helloAjax") != null || session.getAttribute("selectedCsId") != null ||
				session.getAttribute("studentClasses")!=null || session.getAttribute("studentTests")!=null){
			model.addObject("helloAjax", session.getAttribute("helloAjax"));			
			model.addObject("selectedCsId", session.getAttribute("selectedCsId"));		
			model.addObject("studentClasses", session.getAttribute("studentClasses"));		
			model.addObject("studentTests", session.getAttribute("studentTests"));			
		}
		else{
			model.addObject("studentClasses", studentService.getStudentClasses(student));		
		}
		session.removeAttribute("helloAjax");
		session.removeAttribute("selectedCsId");
		session.removeAttribute("studentClasses");
		session.removeAttribute("studentTests");
		
		return model;
	}

	@RequestMapping(value = "/goToStudentDueHomeworksPage", method = RequestMethod.GET)
	public ModelAndView goToStudentDueHomeworks() {
		ModelAndView model = new ModelAndView("Student/take_assessments",
				"studentAssignmentStatus", new StudentAssignmentStatus());
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_HOMEWORKS);
		model.addObject("tab", WebKeys.LP_TAB_VIEW_DUE_HOMEWORK);

		if(session.getAttribute("helloAjax") != null || session.getAttribute("selectedCsId") != null ||
				session.getAttribute("studentClasses")!=null || session.getAttribute("studentTests")!=null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());			
			model.addObject("selectedCsId", session.getAttribute("selectedCsId"));		
			model.addObject("studentClasses", session.getAttribute("studentClasses"));		
			model.addObject("studentTests", session.getAttribute("studentTests"));			
		}
		else{
			model.addObject("studentClasses", studentService.getStudentClasses(student));		
		}
		session.removeAttribute("helloAjax");
		session.removeAttribute("selectedCsId");
		session.removeAttribute("studentClasses");
		session.removeAttribute("studentTests");
		return model;
	}

	@RequestMapping(value = "/goToStudentRTIPage", method = RequestMethod.GET)
	public ModelAndView goToStudentRTIPage() {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		ModelAndView model = new ModelAndView("Student/take_assessments", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_VIEW_RTI);
		if(session.getAttribute("studentTests")!=null && session.getAttribute("helloAjax") != null) {
			model.addObject("studentTests", session.getAttribute("studentTests"));
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
		}
		else{
			model.addObject("studentTests", takeAssessmentsService.getStudentTests(student, WebKeys.LP_USED_FOR_RTI, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED));
		}
		session.removeAttribute("helloAjax");
		session.removeAttribute("studentTests");
		return model;
	}

	@RequestMapping(value = "/getStudentTests", method = RequestMethod.GET)
	public ModelAndView getChildTests(@RequestParam("usedFor") String usedFor, @RequestParam("csId") long csId) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/student_tests", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("usedFor", usedFor);
		model.addObject("studentTests", takeAssessmentsService.getStudentTests(student, usedFor, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED));
		return model;
	}
	@RequestMapping(value = "/getStudentHomeworkTests", method = RequestMethod.GET)
	public ModelAndView getChildHomeworks(@RequestParam("usedFor") String usedFor, @RequestParam("csId") long csId, @RequestParam("tab") String tab) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/student_tests", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("usedFor", usedFor);
		if(tab.equals(WebKeys.LP_TAB_VIEW_CURRENT_HOMEWORK)){
			model.addObject("studentTests", takeAssessmentsService.getStudentCurrentHomeworks(student, usedFor, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED, csId));
		}
		else{
			model.addObject("studentTests", takeAssessmentsService.getStudentDueHomeworks(student, usedFor, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED, csId));
		}
		return model;
	}
	
	@RequestMapping(value = "/getTestQuestions", method = RequestMethod.GET)
	public ModelAndView getTestQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("tab") String tab, 
			@RequestParam("testCount") long testCount,
			@ModelAttribute("studentAssignmentStatus") StudentAssignmentStatus studentAssignmentStatus,
			HttpServletRequest request,
			BindingResult result) {
		List<AssignmentQuestions> questions = null;
		List<RflpPractice> rflpPracticeLt = null;
		if(assignmentTypeId == 18){
			rflpPracticeLt = takeAssessmentsService.getRflpTest(studentAssignmentId);
		}else{
			questions = takeAssessmentsService.getTestQuestions(studentAssignmentId);
		}
		studentAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
		studentAssignmentStatus.setAssignmentQuestions(questions);
		ModelAndView model = null;
		if (assignmentTypeId == 8) {
			model = new ModelAndView("Ajax/Student/benchmark_test", "fluencyAudioFileUpload", new FluencyAudioFileUpload());
			model.addObject("studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("questionList", questions);
		}
		else if (assignmentTypeId == 20) {
			model = new ModelAndView("Ajax/Student/accuracy_test", "accuracyAudioFileUpload", new FluencyAudioFileUpload());
			model.addObject("studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("questionList", questions);
		}
		else if(assignmentTypeId == 13) {
			model = new ModelAndView("Ajax/Student/performance_test", "assignedPtasks", new AssignedPtasks());
		}else if(assignmentTypeId == 18) {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			String usersFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
			model = new ModelAndView("Ajax/Student/rflp_test", "rflpTest", new RflpTest());
			model.addObject("rflpPracticeLt", rflpPracticeLt);
			model.addObject("usersFilePath", usersFilePath);
			if(studentAssignmentStatus.getAssignment().getLanguage() != null){
				model.addObject("langId", studentAssignmentStatus.getAssignment().getLanguage().getLanguageId());
			}else{
				model.addObject("langId", 1);
			}
		}else {
			model = new ModelAndView("Ajax/Student/SampleTest", "studentAssignmentStatus", studentAssignmentStatus);
		}
		model.addObject("testQuestions", questions);
		if(assignmentTypeId == 3){
			ArrayList<String> options = new ArrayList<>();
			for(AssignmentQuestions assignmentQuestions: questions){
				options.add(assignmentQuestions.getQuestions().getOption1());
				options.add(assignmentQuestions.getQuestions().getOption2());
				options.add(assignmentQuestions.getQuestions().getOption3());
				if(assignmentQuestions.getQuestions().getOption4()!=null && !assignmentQuestions.getQuestions().getOption4().isEmpty())
					options.add(assignmentQuestions.getQuestions().getOption4());
				if(assignmentQuestions.getQuestions().getOption5()!=null && !assignmentQuestions.getQuestions().getOption5().isEmpty())
					options.add(assignmentQuestions.getQuestions().getOption5());
			}
			model.addObject("mcqOptions",options);
			model.addObject("mcqOptionsTitles", WebKeys.MCQOPTIONS);
		}
		else if (assignmentTypeId == 7) {
			List<SubQuestions> ssQuestions = takeAssessmentsService.getComprehensionQuestions(questions);
			model.addObject("ssQuestions", ssQuestions);
		}
		else if(assignmentTypeId==19){
			List<SubQuestions> compreQuestions = takeAssessmentsService.getComprehensionQuestions(questions);
			model.addObject("compreQuestions", compreQuestions);
		}
		else if (assignmentTypeId == 14) {
			model = new ModelAndView("Ajax/Student/jactemplate_test",
					"studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("testQuestions", questions);
			List<JacTemplate> jacTitles = takeAssessmentsService
					.getJacTemplateTitleList(questions);
			model.addObject("jacTitles", jacTitles);
			String jacQuestionFilePath=takeAssessmentsService.getJacQuestionFilePath(jacTitles.get(0).getJacQuestionFile());
			model.addObject("jacQuestionFilePath",jacQuestionFilePath);

		}
		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("tab", tab);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("usedFor", usedFor);
		model.addObject("testCount", testCount);
		model.addObject("assignmentType",studentAssignmentStatus.getAssignment().getAssignmentType());
		return model;
	}
	//----------------add neww---------------
	@RequestMapping(value = "/getestQuestions", method = RequestMethod.GET)
	public ModelAndView getestQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("tab") String tab, 
			@ModelAttribute("studentAssignmentStatus") StudentAssignmentStatus studentAssignmentStatus,
			BindingResult result) {
		List<AssignmentQuestions> questions = takeAssessmentsService
				.getTestQuestions(studentAssignmentId);
		studentAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
		studentAssignmentStatus.setAssignmentQuestions(questions);
		ModelAndView model = null;
		if (assignmentTypeId == 8) {
			model = new ModelAndView("Student/benchmark_test", "fluencyAudioFileUpload", new FluencyAudioFileUpload());
			model.addObject("studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("questionList", questions);
			
		}else if(assignmentTypeId == 13) {
			model = new ModelAndView("Student/performance_test",
					"assignedPtasks", new AssignedPtasks());
		}else {
			model = new ModelAndView("Student/SampleTest",
					"studentAssignmentStatus", studentAssignmentStatus);
		}
		model.addObject("testQuestions", questions);
		if (assignmentTypeId == 7) {
			List<SubQuestions> ssQuestions = takeAssessmentsService.getComprehensionQuestions(questions);
			model.addObject("ssQuestions", ssQuestions);
		}
		else if(assignmentTypeId==19){
			List<SubQuestions> compreQuestions = takeAssessmentsService.getComprehensionQuestions(questions);
			model.addObject("compreQuestions", compreQuestions);
		}
		if (assignmentTypeId == 14) {
			model = new ModelAndView("Student/jactemplate_test",
					"studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("testQuestions", questions);
			List<JacTemplate> jacTitles = takeAssessmentsService
					.getJacTemplateTitleList(questions);
			model.addObject("jacTitles", jacTitles);
			String jacQuestionFilePath=takeAssessmentsService.getJacQuestionFilePath(jacTitles.get(0).getJacQuestionFile());
			model.addObject("jacQuestionFilePath",jacQuestionFilePath);

		}
		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("tab", tab);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("usedFor", usedFor);
		return model;
	}
	//------------------------end----------------------------
	@RequestMapping(value = "/getChildTestQuestions", method = RequestMethod.GET)
	public ModelAndView getChildTestQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("tab") String tab, 
			@ModelAttribute("studentAssignmentStatus") StudentAssignmentStatus studentAssignmentStatus,
			BindingResult result) {
		List<AssignmentQuestions> questions = takeAssessmentsService
				.getTestQuestions(studentAssignmentId);
		studentAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
		studentAssignmentStatus.setAssignmentQuestions(questions);
		ModelAndView model = null;
		if (assignmentTypeId == 8) {
			model = new ModelAndView("Ajax/Student/benchmark_test", "fluencyAudioFileUpload", new FluencyAudioFileUpload());
			model.addObject("studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("questionList", questions);
			
		}else if(assignmentTypeId == 13) {
			model = new ModelAndView("Ajax/Student/performance_test",
					"assignedPtasks", new AssignedPtasks());
		}else {
			model = new ModelAndView("Ajax/Student/include_child_test_questions",
					"studentAssignmentStatus", studentAssignmentStatus);
		}
		model.addObject("testQuestions", questions);
		if (assignmentTypeId == 7) {
			List<SubQuestions> ssQuestions = takeAssessmentsService.getComprehensionQuestions(questions);
			model.addObject("ssQuestions", ssQuestions);
		}
		else if(assignmentTypeId==19){
			List<SubQuestions> compreQuestions = takeAssessmentsService.getComprehensionQuestions(questions);
			model.addObject("compreQuestions", compreQuestions);
		}
		if (assignmentTypeId == 14) {
			model = new ModelAndView("Ajax/Student/jactemplate_test",
					"studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("testQuestions", questions);
			List<JacTemplate> jacTitles = takeAssessmentsService
					.getJacTemplateTitleList(questions);
			model.addObject("jacTitles", jacTitles);
			String jacQuestionFilePath=takeAssessmentsService.getJacQuestionFilePath(jacTitles.get(0).getJacQuestionFile());
			model.addObject("jacQuestionFilePath",jacQuestionFilePath);

		}
		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("tab", tab);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("usedFor", usedFor);
		return model;
	}
	
	@RequestMapping(value = "/submitTest", method = RequestMethod.POST)
	public @ResponseBody void submitTest(
			@ModelAttribute("studentAssignmentStatus") StudentAssignmentStatus studentAssignmentStatus,BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {
		String tab = "";
		try {
			boolean lateSubmission = false;
			String operation = "";
			
			if(request.getParameter("tab") != null)
				tab = request.getParameter("tab");
			if(request.getParameter("operation") != null)
			  operation = request.getParameter("operation");

			if (tab!=null && tab.equals(WebKeys.LP_TAB_VIEW_DUE_HOMEWORK)) {
				lateSubmission = true;
			}
			
			boolean status = takeAssessmentsService.saveAssignment(studentAssignmentStatus, studentAssignmentStatus.getOperation(), lateSubmission);
			
			long studentAssignmentId = studentAssignmentStatus.getStudentAssignmentId();
			studentAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
			
			logger.info("studentAssignmentStatus [studentID : "+ studentAssignmentStatus.getStudent().getStudentId() + 
					", studentAssignmentId : "+ studentAssignmentStatus.getStudentAssignmentId() + 
					", assignmentTypeId : "+ studentAssignmentStatus.getAssignment().getAssignmentType().getAssignmentType()+"]");			
			
			if (status) {
				if (studentAssignmentStatus.getGradedStatus().equals(
						WebKeys.GRADED_STATUS_GRADED)) {
					apns.sendStudentResultsNotification(studentAssignmentStatus.getAssignment().getUsedFor(), studentAssignmentStatus.getStudent().getStudentId());
					response.getWriter().write( WebKeys.TEST_SUBMITTED_SUCCESS+ " your score is : "+ studentAssignmentStatus.getPercentage());  
					
				} else {
					if(operation.equalsIgnoreCase("save"))
						response.getWriter().write(WebKeys.TEST_SAVED_SUCCESS);
					else if(operation.equalsIgnoreCase("submit"))
						response.getWriter().write(WebKeys.TEST_SUBMITTED_SUCCESS);
					else
						response.getWriter().write(WebKeys.TEST_SUBMITTED_FAILURE);
						
				}
			} else {
				response.getWriter().write(WebKeys.TEST_SUBMITTED_FAILURE);
			}
		} catch (Exception e) {
			logger.error("Error in submitTest() of of TakeAssessmentsController"+ e);
		}
	}
	@RequestMapping(value = "/autoSaveAssignment", method = RequestMethod.GET)
	public @ResponseBody void saveTest(
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("answer") String answer,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			boolean status = takeAssessmentsService.autoSaveAssignment(assignmentQuestionId,answer);
			 if(status)
				 response.getWriter().write("Saved.");  
			 else
				 response.getWriter().write("Not Saved."); 
		} catch (Exception e) {
			 response.getWriter().write("Erro, not saved.");  
			logger.error("Error in autoSaveAssignment() of of TakeAssessmentsController"
					+ e);
		}
	}

	@RequestMapping(value = "/saveJacAnswer", method = RequestMethod.GET)
	public View saveJacAnswer(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("originalAnswer") String originalAnswer,
			@RequestParam("answer") String answer, Model model) {
		takeAssessmentsService.saveJacAnswer(answer, originalAnswer,
				assignmentQuestionId);
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/submitJacTemplateTest", method = RequestMethod.GET)
	public @ResponseBody
	void submitJacTemplateTest(HttpServletResponse response,
			@RequestParam("studentAssignmentId") long studentAssignmentId,@RequestParam("tab") String tab,
			Model model) {
		try {
			String helloAjax = "";
			boolean check = takeAssessmentsService
					.submitJacTemplateTest(studentAssignmentId,tab);
			if (check)
				helloAjax =WebKeys.JAC_SUBMITTED_SUCCESS;
			else
				helloAjax = WebKeys.JAC_SUBMITTED_FAILURE;
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  	@RequestMapping(value = "/saveAccurcayFiles", method = RequestMethod.POST)
	public View saveAccurcayFiles(@ModelAttribute("fluencyAudioFileUpload") FluencyAudioFileUpload fluencyAudioFileUpload,HttpSession session, HttpServletRequest request) {	
	    long assignmentQuestionId = fluencyAudioFileUpload.getAssignmentQuestionId();
		String passageType = fluencyAudioFileUpload.getPassageType();
		String audioData = fluencyAudioFileUpload.getAudioData();
		takeAssessmentsService.saveAccuracyFiles(assignmentQuestionId, audioData, passageType);
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/saveBenchmarkFiles", method = RequestMethod.POST)
	public View saveBenchmarkFiles(@ModelAttribute("fluencyAudioFileUpload") FluencyAudioFileUpload fluencyAudioFileUpload,HttpSession session, HttpServletRequest request, Model model) {	
  	    long assignmentQuestionId = fluencyAudioFileUpload.getAssignmentQuestionId();
		String passageType = fluencyAudioFileUpload.getPassageType();
		boolean status = takeAssessmentsService.saveBenchmarkFiles(assignmentQuestionId, fluencyAudioFileUpload.getAudioData(), passageType);
		model.addAttribute("status", status);
		return new MappingJackson2JsonView();
	}
  	
 	@RequestMapping(value = "/submitBenchmark", method = RequestMethod.POST)
	public View submitBenchmark(@ModelAttribute("fluencyAudioFileUpload") FluencyAudioFileUpload fluencyAudioFileUpload, HttpSession session, HttpServletRequest request, Model model) {		
 		long studentAssignmentId = fluencyAudioFileUpload.getStudentAssignmentId();
 		boolean status = false;
		try{			
			StudentAssignmentStatus stAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
			if(stAssignmentStatus.getAssignment().getAssignmentType().getAssignmentType().equals(WebKeys.ASSIGNMENT_TYPE_FLUENCY_READING) || 
					stAssignmentStatus.getAssignment().getAssignmentType().getAssignmentType().equals(WebKeys.ASSIGNMENT_TYPE_ACCURACY_READING)){
				status = takeAssessmentsService.submitBenchmarkTest(studentAssignmentId);
				model.addAttribute("status", status);
					
			}else{
				logger.info("Fluncy Test Submission Test is not a Fluency Type");
				model.addAttribute("status", status);
			}		
 		}catch(Exception e){
 			logger.error("Error in submitBenchmark() of of TakeAssessmentsController"+ e);
 			model.addAttribute("status", status);
 		}		
		return new MappingJackson2JsonView();
 	}
 	
 	@RequestMapping(value = "/submitRflpTest", method = RequestMethod.POST)
	public View submitRflpTest(@ModelAttribute("rflpTest") RflpTest rflpTest,BindingResult result, HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			boolean status = false;
			String operation = rflpTest.getOperation();
			long studentAssignmentId = Long.parseLong(request.getParameter("studentAssignmentId"));
			String action = rflpTest.getAction();
			if(action.equalsIgnoreCase(WebKeys.TEST_RFLP)){
				for (RflpPractice rflpPractice : rflpTest.getRflpPractice()){
					long rflpPracticeId = rflpPractice.getRflpPracticeId();
					String studentSentence = rflpPractice.getStudentSentence();
			  	    status = takeAssessmentsService.saveRflpTest(rflpPracticeId, studentSentence, operation, studentAssignmentId);
				  }
			}else if(action.equalsIgnoreCase(WebKeys.GRADE_RFLP)){
				String dateDue = request.getParameter("dateDue");
				float percentage = Float.parseFloat(request.getParameter("percentage"));
				DateFormat formatter = new SimpleDateFormat(WebKeys.DB_DATE_FORMATE);
				StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
				studentAssignmentStatus.setStudentAssignmentId(studentAssignmentId);
				studentAssignmentStatus.setPercentage(percentage);
				rflpTest.setStudentAssignmentStatus(studentAssignmentStatus);
				rflpTest.setDateDue(formatter.parse(dateDue));
				GradingTypes gradingTypes = new GradingTypes();
				gradingTypes.setGradingTypesId(rflpTest.getGradingTypesId());
				rflpTest.setGradingTypes(gradingTypes);
				status = takeAssessmentsService.submitRflpTest(rflpTest);
			}
		 if (status){
				if(operation.equalsIgnoreCase(WebKeys.TEST_SAVE)){
				    model.addAttribute("result", WebKeys.TEST_SAVED_SUCCESS);
				}else if(operation.equalsIgnoreCase(WebKeys.TEST_SUBMIT)){
					 StudentAssignmentStatus stAs=assignAssessmentsService.getStudentAssignmentStatus(studentAssignmentId);
					 apns.sendStudentResultsNotification(stAs.getAssignment().getUsedFor(), stAs.getStudent().getStudentId());
					 model.addAttribute("result", WebKeys.TEST_SUBMITTED_SUCCESS);
				}
			}else{
				model.addAttribute("result", WebKeys.TEST_SUBMITTED_FAILURE);
			}
		} catch (Exception e) {
			logger.error("Error in submitRflpTest() of of TakeAssessmentsController"+ e);
			model.addAttribute("result", WebKeys.TEST_SUBMITTED_ERROR);
		}
		return new MappingJackson2JsonView();
	}
 	
 	@RequestMapping(value = "/autoSaveRflpTest", method = RequestMethod.POST)
 	public View autoSaveRflpTest(@ModelAttribute("rflpTest") RflpTest rflpTest,BindingResult result, HttpServletRequest request, HttpServletResponse response,Model model) {
 		
 		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			boolean status = false;
			long rflpTestId = rflpTest.getRflpTestId();
			String operation = rflpTest.getOperation();
			long studentAssignmentId = Long.parseLong(request.getParameter("studentAssignmentId"));
			int index = Integer.parseInt(request.getParameter("index"));
			String action = rflpTest.getAction();
			if(action.equalsIgnoreCase(WebKeys.TEST_RFLP)){
					long rflpPracticeId = rflpTest.getRflpPractice().get(index).getRflpPracticeId();
					String studentSentence = rflpTest.getRflpPractice().get(index).getStudentSentence();
					String audioData =  rflpTest.getRflpPractice().get(index).getAudioData();
					if(audioData.length() > 0){
						byte[] bis = Base64.decodeBase64(audioData.toString());
						String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
						String rflpRecordsFilePath = "";
						FileOutputStream fos = null;
						rflpRecordsFilePath = uploadFilePath+File.separator+WebKeys.RFLP_TEST+File.separator+rflpTestId;
					  	  try{
					  		  File file = new File(rflpRecordsFilePath);
					  		  if(!file.exists()) 
					  			 file.mkdirs();
					  			File f = new File(rflpRecordsFilePath+File.separator+rflpPracticeId+WebKeys.WAV_FORMAT);
					  			if(f.exists()) 
					  				f.delete();
					  			synchronized(bis) {
					  				//status = FileUploadUtil.audioFileUploadToServer(bis, f);
					  				fos = new FileOutputStream(f, true); 
					  				fos.write(bis);
					  			}
						    } catch (Exception e) {
					             e.printStackTrace();
					        } finally{
					        	  fos.close();
					        }
					}
				  	status = takeAssessmentsService.saveRflpTest(rflpPracticeId, studentSentence, operation, studentAssignmentId);
			}
			 if (status){
					if(operation.equalsIgnoreCase(WebKeys.TEST_SAVE)){
					    model.addAttribute("result", WebKeys.TEST_SAVED_SUCCESS);
					}else if(operation.equalsIgnoreCase(WebKeys.TEST_SUBMIT)){
						 model.addAttribute("result", WebKeys.TEST_SUBMITTED_SUCCESS);
					}
				}else{
					model.addAttribute("result", WebKeys.TEST_SUBMITTED_FAILURE);
				}
			} catch (Exception e) {
				logger.error("Error in autoSaveRflpTest() of of TakeAssessmentsController"+ e);
				model.addAttribute("result", WebKeys.TEST_SUBMITTED_ERROR);
			}
 		return new MappingJackson2JsonView();
 	}
	@RequestMapping(value = "/goToWordworkPage", method = RequestMethod.GET)
	public ModelAndView goToWordworkPage() {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		ModelAndView model = new ModelAndView("Student/take_assessments", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_VIEW_WORD_WORK);
		if(session.getAttribute("studentTests")!=null && session.getAttribute("helloAjax") != null) {
			model.addObject("studentTests", session.getAttribute("studentTests"));
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
		}
		else{
			model.addObject("studentTests", takeAssessmentsService.getStudentWordWorks(student, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED));
		}
		session.removeAttribute("helloAjax");
		session.removeAttribute("studentTests");
		return model;
	}
	@RequestMapping(value = "/autoSaveCompreAudio", method = RequestMethod.POST)
 	public View autoSaveComprehensionAudio(@ModelAttribute("studentAssignmentStatus") StudentAssignmentStatus studAssignStatus,BindingResult result, HttpServletRequest request, HttpServletResponse response,Model model) {
 		
 		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.LP_USER_REGISTRATION);
			long studentAssignmentId = Long.parseLong(request.getParameter("studentAssignmentId"));
			int index = Integer.parseInt(request.getParameter("index"));
			long assignmentQuestionsId = studAssignStatus.getAssignmentQuestions().get(index).getAssignmentQuestionsId();
			String audioData =  studAssignStatus.getAssignmentQuestions().get(index).getAudioData();
			if(audioData.length() > 0){
						byte[] bis = Base64.decodeBase64(audioData.toString());
						String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
						String compreRecordsFilePath = "";
						FileOutputStream fos = null;
						compreRecordsFilePath = uploadFilePath+File.separator+WebKeys.ASSIGNMENT_TYPE_COMPREHENSION+File.separator+studentAssignmentId;
					  	try{
					  		  File file = new File(compreRecordsFilePath);
					  		  if(!file.exists()) 
					  			 file.mkdirs();
					  			File f = new File(compreRecordsFilePath+File.separator+assignmentQuestionsId+WebKeys.WAV_FORMAT);
					  			if(f.exists()) 
					  				f.delete();
					  			synchronized(bis) {
					  				fos = new FileOutputStream(f, true); 
					  				fos.write(bis);
					  			}
						    } catch (Exception e) {
					             e.printStackTrace();
					        } finally{
					        	  fos.close();
					        }
					}
				  
	
			} catch (Exception e) {
				logger.error("Error in autoSaveComprehensionAudio() of of TakeAssessmentsController"+ e);

				//model.addAttribute("result", WebKeys.TEST_SUBMITTED_ERROR);
			}
 		return new MappingJackson2JsonView();
 	}
 
}
