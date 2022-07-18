package com.lp.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.admin.service.ErrorWordItemService;
import com.lp.model.FluencyErrorTypes;
import com.lp.model.Grade;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.WebKeys;

@Controller
public class ErrorWordItemController {
	
	@Autowired
	private AdminService adminservice;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private ErrorWordItemService errorWordItemService;
	
	static final Logger logger = Logger.getLogger(ErrorWordItemController.class);
	
	@RequestMapping(value = "/errorWordItem", method = RequestMethod.GET)
	public ModelAndView errorWordItem(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Admin/error_word_analysis");
		model.addObject("tab", WebKeys.LP_TAB_ERROR_WORD_ITEM);
		try{
			List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
			List<FluencyErrorTypes> errorTypes = teacherService.getFluencyErrorTypes();
			model.addObject("grList", schoolgrades);
			model.addObject("errorTypes", errorTypes);
		}catch(Exception e){
			logger.error("Error in errorWordItem() of  ErrorWordItemController"+ e);
		}
		return model;
	}	
	
	@RequestMapping(value = "/getErrorAnalysis", method = RequestMethod.POST)
	public ModelAndView getErrorAnalysis(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("typeId") long typeId,@RequestParam("gradeId") long gradeId) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Ajax/Admin/error_word_display");
		try{
			Map<String, Long> errorWordsList = new TreeMap<String, Long>();
			errorWordsList = errorWordItemService.getErrorAnalysis(typeId, gradeId, userReg);
			model.addObject("errorWordsList", errorWordsList);
		}catch(Exception e){
			logger.error("Error in getErrorAnalysis() of  ErrorWordItemController"+ e);
		}
		return model;
	}	
}


