package com.lp.admin.controller;

import java.util.Collections;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.appadmin.service.AppAdminService3;
import com.lp.common.service.CommonService;
import com.lp.custom.exception.DataException;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.MasterGrades;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class AddGradesController {

	@Autowired
	private AdminService adminservice;
	@Autowired
	private CommonService reportService;
	@Autowired
	private AppAdminService3 appadminservice3;
	
	static final Logger logger = Logger.getLogger(AddGradesController.class);
	
	@RequestMapping(value = "/AddGrades", method = RequestMethod.GET)
	public ModelAndView AddGrades(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/AdminAddGrades", "Grade", new Grade());
		try{
			List<MasterGrades> mgrades = adminservice.getMasterGrades();
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			long adminRegId = userReg.getRegId();
			School school = reportService.getSchoolIdByRegId(adminRegId);
			List<Grade> schollgrades = adminservice.getSchoolGrades(school.getSchoolId());
			model.addObject("mastergrades", mgrades);
			model.addObject("schoolgrades", schollgrades);
		}
		catch(Exception e){
			logger.error("Error in AddGrades() of AddGradesController" +e);
		}
		return model;
	}

	@RequestMapping(value = "/AdminAddGrades", method = RequestMethod.GET)
	public @ResponseBody
	void AdminAddGrades(HttpServletResponse response, HttpSession session,
			@ModelAttribute("grade") Grade grade, BindingResult result,
			@RequestParam("MasgradeId") long MasgradeId) throws Exception {
		String helloAjax = "";
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			long adminRegId = userReg.getRegId();
			School school = reportService.getSchoolIdByRegId(adminRegId);
			grade.setSchoolId(school.getSchoolId());
			appadminservice3.getMasterGrade(MasgradeId);
			grade.setMasterGrades(appadminservice3.getMasterGrade(MasgradeId));
			grade.setStatus(WebKeys.ACTIVE);
			adminservice.AddGrades(grade);
			helloAjax = "Added";
		}
		catch(Exception e){
			logger.error("Error in AdminAddGrades() of AddGradesController" +e);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);
	}

	@RequestMapping(value = "/AdminRemoveGrades", method = RequestMethod.GET)
	public @ResponseBody
	void AdminRemoveGrade(HttpServletResponse response, HttpSession session,
			@ModelAttribute("grade") Grade grade, BindingResult result,
			@RequestParam("MasgradeId") long MasgradeId) throws Exception {
		String helloAjax = "";
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			long adminRegId = userReg.getRegId();
			String status = "Inactive";
			School school = reportService.getSchoolIdByRegId(adminRegId);
			grade.setMasterGrades(appadminservice3.getMasterGrade(MasgradeId));
			grade.setSchoolId(school.getSchoolId());
			grade.setStatus(status);
			adminservice.RemoveGrades(grade);
			helloAjax = "Removed";
		}
		catch(Exception e){
			logger.error("Error in AdminRemoveGrades() of AddGradesController" +e);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);

	}

	@RequestMapping("/addClasses")
	public @ResponseBody
	void addClasses(HttpSession session, HttpServletResponse response,
			@RequestParam("classIds") String[] classId,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("removeIds") String[] removeIds,
			@ModelAttribute("gradeclasses") GradeClasses gradeclass) {
		try { 
			int i = 0, stat = 0;
			String helloAjax = "";
			try{
				UserRegistration userReg = (UserRegistration) session
						.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				long adminRegId = userReg.getRegId();
				School school = reportService.getSchoolIdByRegId(adminRegId);
				for (i = 0; i < classId.length; i++) {
					long classExists = adminservice.checkGradeClasses(gradeId,
							Long.parseLong(classId[i]), school.getSchoolId());
					String status = WebKeys.LP_STATUS_ACTIVE;
					if (classExists == 0) {					
						stat = adminservice.AddClasses(gradeId,
								Long.parseLong(classId[i]), status, WebKeys.LP_TAB_CREATE);
					}else {					
						stat = adminservice.AddClasses(gradeId,
								Long.parseLong(classId[i]), status, WebKeys.LP_TAB_EDIT);
					}
				}
				for (i = 0; i < removeIds.length; i++) {
					long classExists = adminservice.checkGradeClasses(gradeId,
							Long.parseLong(removeIds[i]), school.getSchoolId());
					if (classExists == 1) {
						String status = WebKeys.ASSIGN_STATUS_INACTIVE;
						stat = adminservice.AddClasses(gradeId,
								Long.parseLong(removeIds[i]), status, WebKeys.LP_TAB_EDIT);
					}
				}
				if (stat == 1)
					helloAjax = "Class Added/Removed Successfully";
				else
					helloAjax = "Class Not Added";
			}
			catch(Exception e){
				logger.error("Error in addClasses() of AddGradesController" +e);
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/promoteGrade", method = RequestMethod.GET)
	public ModelAndView promoteGrade(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Admin/promote_student_grade");
		try{
			long adminRegId = userReg.getRegId();
			School school = reportService.getSchoolIdByRegId(adminRegId);
			List<Grade> schollgrades = adminservice.getSchoolGrades(school.getSchoolId());			
			if(session.getAttribute("promoteStatus") != null){
				model.addObject("promoteStatus", session.getAttribute("promoteStatus").toString());
				session.removeAttribute("promoteStatus");
			}			
			model.addObject("schoolgrades", schollgrades);
		}
		catch(Exception e){
			logger.error("Error in promoteGrade() of AddGradesController" +e);
		}
		return model;
	}
	
	@RequestMapping(value = "/getGradedStudentList", method = RequestMethod.GET)
	public ModelAndView getGradedStudentList( HttpSession session,@RequestParam("gradeId") long gradeId,Model model) throws DataException {
		List<Student> studentList = Collections.emptyList();
		try{
			studentList = adminservice.getGradeStudentList(gradeId);
		}catch(DataException e){
			logger.error("Error in getGradedStudentList() of AddGradesController"+ e);
		}
		model.addAttribute("allPromoteStudents", studentList);		
		return new ModelAndView("Ajax/Admin/promote_students_list");
	
	}
	
	@RequestMapping(value = "/promoteStudentGrade", method = RequestMethod.GET)
	public ModelAndView promoteStudentGrade( HttpSession session,HttpServletRequest request,Model model) throws DataException {
		try{
			long gradeId = Long.parseLong(request.getParameter("gradeId"));
			String promoteGrade = request.getParameter("promoteId");
			long promoteGradeId = 0;
			if(!promoteGrade.equalsIgnoreCase("promote")){
				promoteGradeId = Long.parseLong(promoteGrade);
			}
			String studentIds[] = request.getParameterValues("studentId");
			boolean status = adminservice.promoteStudents(gradeId,promoteGradeId,studentIds);
			if(status){
			 	session.setAttribute("promoteStatus", WebKeys.PROMOTE_SUCCESS); 
			}
		}catch(DataException e){
			logger.error("Error in promoteStudentGrade() of AddGradesController"+ e);
		}
		return new ModelAndView("redirect:/promoteGrade.htm");	
	}
}
