package com.lp.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.Grade;
import com.lp.model.QuestionsList;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;
/**
 * 
 * @author PRASAD BHVN & SANTHOSH
 *
 */
@Controller
public class HomeWorkController extends WebApplicationObjectSupport{
	
	@Autowired
	private AdminService adminservice;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private HttpServletRequest request;
	
	static final Logger logger = Logger.getLogger(HomeWorkController.class);
	// Home Works
	@RequestMapping(value = "/homeworks", method = RequestMethod.GET)
	public ModelAndView homeworksView(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		//redirecting to assessment_main view
		ModelAndView model = new ModelAndView("assessments/assessment_main", "questionsList", new QuestionsList());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_HOMEWORKS);
		model.addObject("tab", WebKeys.LP_TAB_CREATE_HOMEWORK);
		if(	request.getParameter("gradeIdHidden") != null &&
			request.getParameter("classIdHidden") != null &&
			request.getParameter("unitIdHidden") != null  &&
			request.getParameter("lessonIdHidden") != null &&
			request.getParameter("currentTab") != null){
			model.addObject("gradeIdHidden", Long.parseLong(request.getParameter("gradeIdHidden")));
			model.addObject("classIdHidden", Long.parseLong(request.getParameter("classIdHidden")));
			model.addObject("unitIdHidden", Long.parseLong(request.getParameter("unitIdHidden")));
			model.addObject("lessonIdHidden", Long.parseLong(request.getParameter("lessonIdHidden")));
			model.addObject("currentTab", request.getParameter("currentTab"));
		}
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
			logger.error("Error in homeworksView() of HomeWorkController"
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
	
	//Edit Homework
	@RequestMapping(value = "/editHomework", method = RequestMethod.GET)
	public ModelAndView editHomework(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("assessments/edit_assessment_main", "questionsList", new QuestionsList());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_HOMEWORKS);
		model.addObject("tab", WebKeys.LP_TAB_EDIT_HOMEWORK);
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
			logger.error("Error in editHomework() of AssessmentController"+ e);
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
}
