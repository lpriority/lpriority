package com.lp.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.common.dao.AssessmentDAO;
import com.lp.model.Assignment;
import com.lp.model.AssignmentType;
import com.lp.model.Grade;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.AssignAssessmentsService;
import com.lp.teacher.service.MathAssessmentService;
import com.lp.utils.WebKeys;

@Controller
public class GearGameController extends WebApplicationObjectSupport{
	
	static final Logger logger = Logger.getLogger(GearGameController.class);
	@Autowired
	private AdminService adminservice;
	@Autowired
	private AssignAssessmentsService assignAssessmentsService;
	@Autowired
	private MathAssessmentService mathAssessmentService;
	@Autowired
	private AssessmentDAO assessmentDAO;
	
	@RequestMapping(value = "/assignGearGameByAdmin", method = RequestMethod.GET)
	public ModelAndView createMathAssessment(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
		ModelAndView model = new ModelAndView("Admin/assign_math_game");
		model.addObject("grList", schoolgrades);
		model.addObject("tab", "assignGearGame");
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);	
		return model;
	}
	
	@RequestMapping(value = "/validateTitles", method = RequestMethod.GET)
	public View validateTitle(@RequestParam("csIds") ArrayList<Long> csIds,
			@RequestParam("title") String title,@RequestParam("usedFor") String usedFor, HttpSession session, Model model) {
		boolean status = true;
		for (Long csId : csIds) {
			List<Assignment> assignmentList = assignAssessmentsService.getAssignmentByTitle(csId, title, usedFor);
			if(assignmentList.size()>0){
				status = false;
				break;
			}
		}
		model.addAttribute("status", status);
		return new MappingJackson2JsonView();

	}
	
	@RequestMapping(value = "/assignGameByAdmin", method = RequestMethod.POST)
	public @ResponseBody void assignGameByAdmin(
			@RequestParam("csIds") ArrayList<Long> csIds,
			@RequestParam("dueDate") String dueDateStr,
			@RequestParam("title") String title,
			@RequestParam("instructId") String instructions,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{	
			boolean status = mathAssessmentService.assignGameByAdmin(csIds, dueDateStr, title, instructions);
			if(status)
				response.getWriter().write(WebKeys.ASSIGNED_SUCCESSFULLY); 
			else
				response.getWriter().write(WebKeys.FAILED_TO_ASSIGNED); 
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/html");  
	   }catch(Exception e){
			logger.error("Error in assignGameToStudents() of MathAssessmentController "+ e);
	   }
	}
	
	@RequestMapping(value = "/reviewMathGameByAdmin", method = RequestMethod.GET)
	public ModelAndView reviewMathGameByAdmin(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
		ModelAndView model = new ModelAndView("Admin/review_math_game");
		AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(WebKeys.GEAR_GAME);
		model.addObject("assignmentTypeId", assignmentType.getAssignmentTypeId());
		model.addObject("grList", schoolgrades);
		model.addObject("tab", "reviewMathGame");
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);		
		return model;
	}
}
