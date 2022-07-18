package com.lp.teacher.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.Section;
import com.lp.model.Teacher;
import com.lp.teacher.service.ShowAssignedAssessmentService;
import com.lp.utils.WebKeys;

@Controller
public class ShowAssignedAssessmentController {

	@Autowired
	private ShowAssignedAssessmentService showAssignAssessmentService;
	@Autowired
	private CurriculumService curriculumService;

	static final Logger logger = Logger.getLogger(ShowAssignedAssessmentController.class);

	@RequestMapping(value = "/showAssignedAssessments", method = RequestMethod.GET)
	public ModelAndView goToShowAssignedAssessments(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_ASSESSMENT);
		ModelAndView model = new ModelAndView(
				"Teacher/show_assigned_assessments", "assignment", assignment);		
		model.addObject("tab", WebKeys.LP_TAB_SHOW_ASSIGNED);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		try{
			model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));
		}catch(DataException e){
			model.addObject("hellowAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping(value = "/showAssignedRtis", method = RequestMethod.GET)
	public ModelAndView goToShowAssignedRTIs(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_RTI);
		ModelAndView model = new ModelAndView(
				"Teacher/show_assigned_assessments", "assignment", assignment);		
		model.addObject("tab", WebKeys.LP_TAB_SHOW_ASSIGNED_RTI);
		try{
			model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));
		}catch(DataException e){
			model.addObject("hellowAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping(value = "/getTeacherAssignedDates", method = RequestMethod.GET)
	public View getTeacherSections(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId, Model model) {
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherAssignedDates",
				showAssignAssessmentService.getTeacherAssignedDates(teacher, csId, usedFor));
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getTeacherAssignedAssessments", method = RequestMethod.GET)
	public ModelAndView getSections(@RequestParam("classId") long classId,
			@RequestParam("gradeId") long gradeId,@RequestParam("csId") long csId,
			Map<String, Object> map,
			HttpSession session, @ModelAttribute("section") Section section,@RequestParam("tab") String tab,
			HttpServletResponse response, HttpServletRequest request) {
		List<Assignment> assignedAssessments=null;
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		if(tab.equalsIgnoreCase("showAssigned"))
		assignedAssessments = showAssignAssessmentService.getAssignedAssessmentByDate(teacher,csId, WebKeys.LP_USED_FOR_ASSESSMENT);
		else
	    assignedAssessments = showAssignAssessmentService.getAssignedAssessmentByDate(teacher,csId, WebKeys.LP_USED_FOR_RTI);
		ModelAndView model = new ModelAndView("Ajax/Teacher/view_assignments", "assignment",
				new Assignment());
		model.addObject("assessmentList", assignedAssessments);
		model.addObject("tab",tab);
		return model;

	}
	
	@RequestMapping(value = "/UpdateAssignment", method = RequestMethod.GET)
	public @ResponseBody
	void UpdateSections(HttpSession session, HttpServletResponse response,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("enddate") Date duedate,
			@ModelAttribute("assignment") Assignment assignment) {
		try {

			String helloAjax = "";
			int stat = 0;
			 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			    String sqlDate = sdf.format(duedate);
			stat = showAssignAssessmentService.updateAssignments(assignmentId, sqlDate);
			if (stat > 0) {
				helloAjax = "Assignment Updated Successfully";
			} else {
				helloAjax = "Assignment Not Updated Successfully";
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
}
	
	@RequestMapping(value = "/DeleteAssignments", method = RequestMethod.GET)
	public @ResponseBody void deleteAssignments(@RequestParam("assignmentId") long assignmentId,@RequestParam("csId") long csId,
			HttpSession session, @ModelAttribute("assignment") Assignment assignment,
			HttpServletResponse response, HttpServletRequest request) {
		int stat = 0;
		String status = "";
		try {
			stat = showAssignAssessmentService.deleteAssignments(assignmentId);
			if (stat > 0) {
				status = "Assignment Deleted Successfully";
			} else {
				status = "Assignment Not Deleted";
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getGroupAssignedDates", method = RequestMethod.GET)
	public View getGroupAssignedDates(HttpSession session,@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId, Model model) {
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherAssignedDates",showAssignAssessmentService.getGroupAssignedDates(teacher, csId, usedFor));
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getGroupAssignmentTitles", method = RequestMethod.GET)
	public View getTeacherSections(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,@RequestParam("assignedDate") String assignedDate,
			Model model) {
		model.addAttribute("assignmentTitles",showAssignAssessmentService.getGroupAssignmentTitles(csId, usedFor,assignedDate));
		return new MappingJackson2JsonView();
	}
}
