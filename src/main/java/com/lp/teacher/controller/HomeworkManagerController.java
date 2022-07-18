package com.lp.teacher.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.JacQuestionFile;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.student.service.TakeAssessmentsService;
import com.lp.teacher.service.HomeworkManagerService;
import com.lp.utils.WebKeys;

@Controller
public class HomeworkManagerController {

	@Autowired
	private HomeworkManagerService homeworkManagerService;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private TakeAssessmentsService takeAssessmentsService;

	static final Logger logger = Logger
			.getLogger(AssignAssessmentController.class);

	@RequestMapping(value = "/gotoCurrentHomework", method = RequestMethod.GET)
	public ModelAndView goToCurrentHomework(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_HOMEWORKS);
		ModelAndView model = new ModelAndView(
				"Teacher/current_homework", "assignment", assignment);
		
		model.addObject("tab", WebKeys.LP_TAB_CURRENT_HOMEWORK);
		try{
			model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));
		}catch(DataException e){
			model.addObject("helloAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping(value = "/getTeacherCurrentHomeworks", method = RequestMethod.GET)
	public ModelAndView getTeacherCurrentHomeworks(@RequestParam("classId") long classId,
			@RequestParam("gradeId") long gradeId,@RequestParam("csId") long csId,
			Map<String, Object> map,@RequestParam("lessonId") long lessonId,
			HttpSession session, @ModelAttribute("assignment") Assignment assignment,
			@RequestParam("stat") String stat ,
			HttpServletResponse response, HttpServletRequest request) {
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<Assignment> assignedHomeworks = homeworkManagerService.getAssignedHomeworks(teacher,csId, WebKeys.LP_USED_FOR_HOMEWORKS,lessonId,stat);
		ModelAndView model = new ModelAndView("Ajax/Teacher/view_homeworks", "assignment",
				new Assignment());
		model.addObject("homeworkList", assignedHomeworks);
		return model;

	}
	@RequestMapping(value = "/getQuestionsByAssignmentId", method = RequestMethod.GET)
	public ModelAndView getQuestionsByAssignmentId(@RequestParam("assignmentId") long assignmentId,
			HttpSession session, @RequestParam("assignmentTypeId") long assignmentTypeId,@ModelAttribute("assignment") Assignment assignment,
			HttpServletResponse response, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/view_homework_questions", "assignmentquestions",
				new AssignmentQuestions());
			if(assignmentTypeId==14){
			List<JacQuestionFile> jacQuestionFiles = homeworkManagerService.getJacTemplateQuestionsFile(assignmentId);
			model.addObject("hquestionList", jacQuestionFiles);
			String jacQuestionFilePath=takeAssessmentsService.getJacQuestionFilePath(jacQuestionFiles.get(0));
			model.addObject("jacQuestionFilePath",jacQuestionFilePath);
		}else{
			
		List<AssignmentQuestions> homeworkQuestions = homeworkManagerService.getQuestionsByAssignmentId(assignmentId);
		model.addObject("hquestionList", homeworkQuestions);
		if(assignmentTypeId == 3){
			ArrayList<String> options = new ArrayList<>();
			for(AssignmentQuestions assignmentQuestions: homeworkQuestions){
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
		}
	
		model.addObject("assignmentTypeId",assignmentTypeId);
		return model;

	}
	@RequestMapping(value = "/gotoHomeworkManager", method = RequestMethod.GET)
	public ModelAndView goToHomeworkManager(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_HOMEWORKS);
		ModelAndView model = new ModelAndView(
				"Teacher/current_homework", "assignment", assignment);		
		model.addObject("tab", WebKeys.LP_TAB_HOMEWORK_MANAGER);
		try{
			model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));
		}catch(DataException e){
			model.addObject("helloAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping(value = "/gotoHomeworkReports", method = RequestMethod.GET)
	public ModelAndView goToHomeworkReports(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		ModelAndView model = new ModelAndView(
				"Teacher/homework_reports", "assignment", assignment);		
		model.addObject("tab", WebKeys.LP_TAB_HOMEWORK_REPORTS);
		model.addObject("usedFor",WebKeys.LP_USED_FOR_HOMEWORKS);
		try{
			model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));
		}catch(DataException e){
			model.addObject("helloAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping(value = "/getHomeworkReports", method = RequestMethod.GET)
	public ModelAndView getHomeworkReports(@RequestParam("csId") long csId,@RequestParam("assignedDate") String assignedDate,
			HttpSession session,@RequestParam("usedFor") String usedFor,@RequestParam("titleId") long title,
			HttpServletResponse response, HttpServletRequest request) {
	
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<StudentAssignmentStatus> homeworkReports = homeworkManagerService.getHomeworkReports(teacher,csId,usedFor,assignedDate,title);
		ModelAndView model = new ModelAndView("Ajax/Teacher/show_homework_reports", "assignment",
				new StudentAssignmentStatus());
		model.addObject("homeworkReportList", homeworkReports);
		return model;
	
}
	
	@RequestMapping(value = "/getAssignmentTitles", method = RequestMethod.GET)
	public View getTeacherSections(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,@RequestParam("assignedDate") String assignedDate,
			Model model) {		
		model.addAttribute("assignmentTitles", homeworkManagerService.getAssignmentTitles(csId, usedFor,assignedDate));
		return new MappingJackson2JsonView();
	}
	
	
	@RequestMapping(value = "/getTestTitles", method = RequestMethod.GET)
	public View getTestTitles(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,
			Model model) {
		try{
		model.addAttribute("assignmentTitles",
				homeworkManagerService.getTestTitles(csId, usedFor));
		}
		catch(Exception e){
			logger.error("Error in getTestTitles() of HomeworkManagerController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getRTIResultTitles", method = RequestMethod.GET)
	public View getAssessmentTitles(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,@RequestParam("assignedDate") String assignedDate,
			Model model) {
		model.addAttribute("assignmentTitles",
				homeworkManagerService.getRTIResultsTitles(csId, usedFor,assignedDate));
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getFluencyTitles", method = RequestMethod.GET)
	public View getFluencyTitles(HttpSession session,
			@RequestParam("csId") long csId,@RequestParam("assignedDate") String assignedDate,
			Model model) {
		model.addAttribute("assignmentTitles", homeworkManagerService.getFluencyTitles(csId,assignedDate));
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getAccuracyTitles", method = RequestMethod.GET)
	public View getAccuracyTitles(HttpSession session,
			@RequestParam("csId") long csId,@RequestParam("assignedDate") String assignedDate,
			Model model) {
		model.addAttribute("assignmentTitles", homeworkManagerService.getAccuracyTitles(csId,assignedDate));
		return new MappingJackson2JsonView();
	}
}
