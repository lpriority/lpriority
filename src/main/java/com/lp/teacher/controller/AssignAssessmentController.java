package com.lp.teacher.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.AssessmentService;
import com.lp.common.service.BenchmarkCutOffMarksService;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.common.service.PerformanceTaskService;
import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.AssignmentType;
import com.lp.model.BenchmarkCategories;
import com.lp.model.BenchmarkDirections;
import com.lp.model.Grade;
import com.lp.model.JacQuestionFile;
import com.lp.model.PerformancetaskGroups;
import com.lp.model.Questions;
import com.lp.model.RegisterForClass;
import com.lp.model.RtiGroups;
import com.lp.model.SubQuestions;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.AssignAssessmentsService;
import com.lp.utils.WebKeys;

@Controller
public class AssignAssessmentController {

	@Autowired
	private AssignAssessmentsService assignAssessmentsService;
	@Autowired
	private AssessmentService AssessmentService;
	@Autowired
	private CommonService commonservice;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private PerformanceTaskService performanceTaskService;
	@Autowired
	private AdminService adminservice;
	@Autowired
	private BenchmarkCutOffMarksService benchmarkCutOffMarksService;
	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private SessionFactory sessionFactory;

	static final Logger logger = Logger.getLogger(AssignAssessmentController.class);

	@RequestMapping(value = "/assignAssessments", method = RequestMethod.GET)
	public ModelAndView goToAssignAssessment(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_ASSESSMENT);

		ModelAndView model = new ModelAndView("Teacher/assign_assessments_main", "assignment", assignment);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("tab", WebKeys.LP_TAB_ASSIGN_ASSESSMENTS);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		try{
			model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));
		}catch(Exception e){
			model.addObject("hellowAjax",e.getMessage());
		}		
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}		
		if(session.getAttribute("subTab") != null || session.getAttribute("isGroup") != null){
			model.addObject("subTab", session.getAttribute("subTab"));
			model.addObject("isGroup", session.getAttribute("isGroup"));
		}
		//change isGroup to false for assign assessments
		model.addObject("isGroup", false);
				
		session.removeAttribute("subTab");
		session.removeAttribute("isGroup");		
		return model;
	}

	@RequestMapping(value = "/assignHomeworks", method = RequestMethod.GET)
	public ModelAndView goToAssignHomework(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_HOMEWORKS);
		ModelAndView model = new ModelAndView(
				"Teacher/assign_assessments_main", "assignment", assignment);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_HOMEWORKS);
		model.addObject("tab", WebKeys.LP_TAB_ASSIGN_HOMEWORK);
		try{
			model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));
		}catch(DataException e){
			model.addObject("helloAjax",e.getMessage());
		}
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		if(session.getAttribute("subTab") != null || session.getAttribute("isGroup") != null){
			model.addObject("subTab", session.getAttribute("subTab"));
			model.addObject("isGroup", session.getAttribute("isGroup"));
		}
		session.removeAttribute("subTab");
		session.removeAttribute("isGroup");
		return model;
	}

	@RequestMapping(value = "/assignRti", method = RequestMethod.GET)
	public ModelAndView goToAssignRti(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_RTI);
		ModelAndView model = new ModelAndView(
				"Teacher/assign_assessments_main", "assignment", assignment);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_ASSIGN_RTI);
		try {
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session
						.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
						.getSchool().getSchoolId());
				model.addObject("teacherGrades", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("teacherGrades", teacherGrades);
			}
		} catch (DataException e) {
			logger.error("Error in goToAssignRti() of AssignAssessmentController"+ e);
		}
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		if(session.getAttribute("subTab") != null || session.getAttribute("isGroup") != null){
			model.addObject("subTab", session.getAttribute("subTab"));
			model.addObject("isGroup", false);
		}
		session.removeAttribute("subTab");
		session.removeAttribute("isGroup");
		return model;
	}

	@RequestMapping(value = "/getTeacherAssignLessons", method = RequestMethod.GET)
	public View getTeacherSections(HttpSession session,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("csId") long csId, Model model) {
		model.addAttribute("teacherAssignLessons", assignAssessmentsService.getTeacherAssignLessons(csId));
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/getStudentsList", method = RequestMethod.GET)
	public View getStudentsList(@RequestParam("csId") long csId,
			@RequestParam("usedFor") String usedFor,
			Map<String, Object> map, HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			Model model) {
		List<RtiGroups> rtiList = new ArrayList<> ();
		List<RegisterForClass> studentList = commonservice
				.getStudentsByCsId(csId);
		List<PerformancetaskGroups> perGroupList = performanceTaskService.getPerformanceGroups(csId,WebKeys.LP_YES);
		if(usedFor.equals(WebKeys.LP_USED_FOR_RTI)){
			rtiList = assignAssessmentsService.getRTIGroups(studentList);
			}
		model.addAttribute("studentList", studentList);
		model.addAttribute("rtiList", rtiList);
		model.addAttribute("perGroupList", perGroupList);
		return new MappingJackson2JsonView();

	}
	@RequestMapping(value = "/getStudentsLists", method = RequestMethod.GET)
	public View getStudentsLists(@RequestParam("sectionId") long sectionId,			
			Map<String, Object> map, HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			Model model) {
		long csId=commonservice.getCsIdBySectionId(sectionId);
		List<RegisterForClass> studentList = commonservice
				.getStudentsByCsId(csId);
		model.addAttribute("studentList", studentList);
		return new MappingJackson2JsonView();

	}

	@RequestMapping(value = "/getAssignmentTypes", method = RequestMethod.GET)
	public View getAssignmentTypes(@RequestParam("csId") long csId,
			@RequestParam("usedFor") String usedFor, Map<String, Object> map,
			HttpSession session, Model model) {
		List<AssignmentType> assignmentTypes = AssessmentService
				.getAssignments(usedFor);
		model.addAttribute("assignmentTypes", assignmentTypes);
		return new MappingJackson2JsonView();

	}

	@RequestMapping(value = "/getQuestions", method = RequestMethod.GET)
	public ModelAndView getQuestions(HttpSession session,
			@RequestParam("lessonId") long lessonId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("usedFor") String usedFor,
			@ModelAttribute("assignment") Assignment assignment,@RequestParam("gradeId") long gradeId,
			@RequestParam("tab") String tab) {
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		ModelAndView model = new ModelAndView("Ajax/Teacher/view_questions");
		if (assignmentTypeId == 14) {
			List<JacQuestionFile> QuesList = assignAssessmentsService
					.getJacQuestions(teacher, usedFor, lessonId);
			model.addObject("quesList", QuesList);
		} else {
			
			List<Questions> QuesList = assignAssessmentsService.getQuestions(
					lessonId, assignmentTypeId, teacher, usedFor,gradeId);
			ArrayList<String> options = new ArrayList<>();
			if(assignmentTypeId == 3){
				for(Questions questions: QuesList){
					options.add(questions.getOption1());
					options.add(questions.getOption2());
					options.add(questions.getOption3());
					if(questions.getOption4()!=null && !questions.getOption4().isEmpty())
						options.add(questions.getOption4());
					if(questions.getOption5()!=null && !questions.getOption5().isEmpty())
						options.add(questions.getOption5());
					
				}
				model.addObject("mcqOptions",options);
			}
			else if (assignmentTypeId == 7 || assignmentTypeId == 19) {
				List<SubQuestions> ssQuestions = Collections.emptyList();
				ssQuestions = assessmentService.getSubQuestionsByAssignmentType(assignmentTypeId,lessonId,usedFor,gradeId);
				model.addObject("ssQuestions", ssQuestions);
			}
			else if(assignmentTypeId == 8 || assignmentTypeId==20) {				
				model.addObject("benchmarkCategories", benchmarkCutOffMarksService.getMainBenchmarkTestTypes());
				model.addObject("benchmarkDirections", benchmarkCutOffMarksService.getBenchmarkDirections());
			}
			model.addObject("quesList", QuesList);
		}
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("usedFor", usedFor);
		model.addObject("tab",tab);
		return model;
	}

	@RequestMapping(value = "/assignAssessmentsToStudents", method = RequestMethod.POST)
	public @ResponseBody void assignAssessmentsToStudents(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("assignment") Assignment assignment,
			BindingResult result)  throws Exception{
		String assignType = request.getParameter("type");
		List<String> students = new ArrayList<String>();
		List<String> groups = new ArrayList<String>();		 
		String rtiGroup = null;
		String[] subQuestions = null;
		ArrayList<Long> questions = new ArrayList<>();
		if(assignment.getAssignmentType().getAssignmentTypeId() == 7 || assignment.getAssignmentType().getAssignmentTypeId() == 19){
			subQuestions = request.getParameterValues("ssQuestions");
			for(int count=0;count<subQuestions.length; count++){
				long subQuestionId =  Long.parseLong(subQuestions[count]);
				int numOfOptions = Integer.parseInt( request.getParameter("questions"+subQuestionId));
				for(int count2=0;count2<numOfOptions;count2++){
					long questionId =  Long.parseLong(request.getParameter(subQuestionId+":"+count2));
					questions.add(questionId);
				}
			}		
		}
		else{
			String[] tempQ = request.getParameterValues("questions");
			for(int cnt=0; cnt<tempQ.length; cnt++){
				questions.add(Long.parseLong(tempQ[cnt]));
			}
		}

		long retestId = 0;
		String isGroup = request.getParameter("isGroup");
		if (assignType.equals("groups")) {
			rtiGroup = request.getParameter("rtiGroupId");
			students = commonservice.getStudentsByCsIdAndRtiGroup(assignment
					.getClassStatus().getCsId(), Long.valueOf(rtiGroup));
		}else if(isGroup.equals(WebKeys.LP_TRUE)){
			groups = Arrays.asList(request.getParameterValues("perGroupId"));
		}else {
			students = Arrays.asList(request.getParameterValues("studentId"));
		}
		if (!assignment.getUsedFor().equals(WebKeys.LP_USED_FOR_RTI)
				&& request.getParameter("reTestId") != null
				&& !request.getParameter("reTestId").equals("")) {
			retestId = Long.parseLong(request.getParameter("reTestId"));
		}
		Assignment assignmentObj = new Assignment();
		assignmentObj.setUsedFor(assignment.getUsedFor());
		String tab = request.getParameter("tab");
		String subTab = request.getParameter("subTab");
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId() == 20){
			BenchmarkDirections benchmarkDirections = new BenchmarkDirections();
			benchmarkDirections.setbenchmarkDirectionsId(assignment.getBenchmarkDirectionsId());
			assignment.setBenchmarkDirections(benchmarkDirections);
		}
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8){
			BenchmarkCategories benchmarkCategories = new BenchmarkCategories();
			benchmarkCategories.setBenchmarkCategoryId(assignment.getBenchmarkId());
			assignment.setBenchmarkCategories(benchmarkCategories);
		}
		
		session.setAttribute("usedFor", assignment.getUsedFor());
		session.setAttribute("tab", tab);
		session.setAttribute("subTab", subTab);
		session.setAttribute("isGroup", isGroup);
		try {

			boolean status = false;
			status = assignAssessmentsService.assignAssessments(assignment, students, questions, retestId, groups);
			if (status) {
				response.getWriter().write( WebKeys.LP_ASSIGN_ASSESSMENT_SUCESS);  
			} else {
				response.getWriter().write( WebKeys.LP_ASSIGN_ASSESSMENT_ERROR);  
			}
		} catch (DataException e) {
			logger.error("Error in assignAssessmentsToStudents() of of AssignAssessmentController"+ e);
			response.getWriter().write( WebKeys.LP_ASSIGN_ASSESSMENT_ERROR); 
		}		
	}

	@RequestMapping(value = "/getPreviousTestDates", method = RequestMethod.GET)
	public View getPreviousTestDates(@RequestParam("csId") long csId,
			@RequestParam("usedFor") String usedFor, Map<String, Object> map,
			HttpSession session, Model model,
			@RequestParam("assignmentTypeId") long assignmentTypeId) {
		List<Assignment> previousDates = assignAssessmentsService.getPreviousTestDates(csId, assignmentTypeId, usedFor);
		model.addAttribute("previousDates", previousDates);
		return new MappingJackson2JsonView();

	}
	
	@RequestMapping(value = "/validateTitle", method = RequestMethod.GET)
	public View validateTitle(@RequestParam("csId") long csId,
			@RequestParam("title") String title,@RequestParam("usedFor") String usedFor, HttpSession session, Model model) {
		List<Assignment> assignmentList = assignAssessmentsService.getAssignmentByTitle(csId, title, usedFor);
		if(assignmentList.size()>0){
			model.addAttribute("status", false);
		}else{
			model.addAttribute("status", true);
		}
		return new MappingJackson2JsonView();

	}
	@RequestMapping(value = "/checkBenchTitleExists", method = RequestMethod.POST)
	public @ResponseBody
	void checkBenchTitleExists(HttpSession session, HttpServletResponse response,
			Model model,@RequestParam("csId") long csId,
			@RequestParam("benchmarkId") long benchmarkId,@RequestParam("usedFor") String usedFor,@RequestParam("students") long[] students) {
		 String helloAjax="false";
		try {
	    int count=students.length;
		model.addAttribute("status", true);
		for(int i=0;i<count;i++)
		{
			long s=assignAssessmentsService.checkBenchmarkTitleExists(benchmarkId, students[i], csId);
			if(s==1)
			{
				helloAjax="true";
				break;
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);
		
	} catch (Exception e) {
		e.printStackTrace();
		
	}
		
	}
	@RequestMapping(value = "/getStudentsListsByCsId", method = RequestMethod.GET)
	public View getStudentsListsByCsId(@RequestParam("csId") long csId,			
			Map<String, Object> map, HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			Model model) {
		List<RegisterForClass> studentList = commonservice
				.getStudentsByCsId(csId);
		model.addAttribute("studentList", studentList);
		return new MappingJackson2JsonView();

	}
	
	@RequestMapping(value = "/updateStudentTests", method = RequestMethod.GET)
	public ModelAndView updateStudentTests() {
		assignAssessmentsService.updateStudentTests();		
		return new ModelAndView("redirect:/assignRti.htm");

	}
	@RequestMapping(value = "/assignRtf", method = RequestMethod.GET)
	public ModelAndView goToAssignRTF(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_RTI);
		ModelAndView model = new ModelAndView(
				"Admin/assign_rtf", "assignment", assignment);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_ASSIGN_RTF);
		try {
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session
						.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
						.getSchool().getSchoolId());
				model.addObject("teacherGrades", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("teacherGrades", teacherGrades);
			}
		} catch (DataException e) {
			logger.error("Error in goToAssignRti() of AssignAssessmentController"+ e);
		}
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		if(session.getAttribute("subTab") != null || session.getAttribute("isGroup") != null){
			model.addObject("subTab", session.getAttribute("subTab"));
			model.addObject("isGroup", false);
		}
		session.removeAttribute("subTab");
		session.removeAttribute("isGroup");
		return model;
	}
	@RequestMapping(value = "/assignRTFToStudents", method = RequestMethod.POST)
	public @ResponseBody void assignRTFToStudents(
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("assignment") Assignment assignment,
			BindingResult result)  throws Exception{
		//String assignType = request.getParameter("type");
		HttpSession session = request.getSession(true);
		ArrayList<String> csIds=new ArrayList<>();
		String[] subQuestions = null;
		ArrayList<Long> questions = new ArrayList<>();
		if(assignment.getAssignmentType().getAssignmentTypeId() == 19){
			subQuestions = request.getParameterValues("ssQuestions");
			for(int count=0;count<subQuestions.length; count++){
				long subQuestionId =  Long.parseLong(subQuestions[count]);
				int numOfOptions = Integer.parseInt( request.getParameter("questions"+subQuestionId));
				for(int count2=0;count2<numOfOptions;count2++){
					long questionId =  Long.parseLong(request.getParameter(subQuestionId+":"+count2));
					questions.add(questionId);
				}
			}		
		}
		else{
			String[] tempQ = request.getParameterValues("questions");
			for(int cnt=0; cnt<tempQ.length; cnt++){
				questions.add(Long.parseLong(tempQ[cnt]));
			}
		}
		String[] lstStudent = request.getParameterValues("csId");
		for(int ct=0; ct<lstStudent.length; ct++){
			csIds.add(lstStudent[ct]);
		}
	    		
		Assignment assignmentObj = new Assignment();
		assignmentObj.setUsedFor(assignment.getUsedFor());
		String tab = request.getParameter("tab");
		String subTab = request.getParameter("subTab");
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId() == 20){
			BenchmarkDirections benchmarkDirections = new BenchmarkDirections();
			benchmarkDirections.setbenchmarkDirectionsId(assignment.getBenchmarkDirectionsId());
			assignment.setBenchmarkDirections(benchmarkDirections);
		}
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8) {
			BenchmarkCategories benchmarkCategories = new BenchmarkCategories();
			benchmarkCategories.setBenchmarkCategoryId(assignment.getBenchmarkId());
			assignment.setBenchmarkCategories(benchmarkCategories);
		}
		
		session.setAttribute("usedFor", assignment.getUsedFor());
		session.setAttribute("tab", tab);
		session.setAttribute("subTab", subTab);
		//session.setAttribute("isGroup", isGroup);
		try {

			boolean status = false;
			status = assignAssessmentsService.assignRTF(assignment, csIds, questions);
			if (status) {
				response.getWriter().write( WebKeys.LP_ASSIGN_ASSESSMENT_SUCESS);  
			} else {
				response.getWriter().write( WebKeys.LP_ASSIGN_ASSESSMENT_ERROR);  
			}
		} catch (DataException e) {
			logger.error("Error in assignRTFToStudents() of of AssignAssessmentController"+ e);
			response.getWriter().write( WebKeys.LP_ASSIGN_ASSESSMENT_ERROR); 
		}		
	}
	@RequestMapping(value = "/checkBenchTitleExistForSection", method = RequestMethod.POST)
	public @ResponseBody
	void checkBenchTitleExistsForSection(HttpSession session, HttpServletResponse response,
			Model model,@RequestParam("csIds") long[] csIds,
			@RequestParam("benchmarkId") long benchmarkId,@RequestParam("usedFor") String usedFor) {
		 String helloAjax="false";
		try {
	    int count=csIds.length;
		model.addAttribute("status", true);
		for(int i=0;i<count;i++)
		{
			long s=assignAssessmentsService.checkBenchmarkTitleExistsForSection(benchmarkId, csIds[i]);
			if(s==1)
			{
				helloAjax="true";
				break;
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);
		
	} catch (Exception e) {
		e.printStackTrace();
		
	}
		
	}
	
}
