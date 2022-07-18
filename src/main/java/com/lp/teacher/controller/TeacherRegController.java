package com.lp.teacher.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.WebKeys;

@Controller
public class TeacherRegController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private AppAdminService3 adminService3;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/teacherRegVal1", method = RequestMethod.POST)
	public ModelAndView teacherRegVal1(
			@ModelAttribute("teacherReg") UserRegistration teacherReg,
			BindingResult result, HttpSession session, Map<String, Object> map) {		
			UserRegistration dbUserObject = commonService.getNewUserRegistration(teacherReg.getEmailId());
			teacherReg.setRegId(dbUserObject.getRegId());
			session.setAttribute("teacherReg1", teacherReg);
			map.put("grList", adminService.getSchoolGrades(dbUserObject.getSchool().getSchoolId()));
			return new ModelAndView("Teacher/TeacherRegistration2",
					"teacherReg2", new TeacherSubjects());
	}

	@RequestMapping(value = "/getClasses", method = RequestMethod.GET)
	public ModelAndView getClasses(
			@ModelAttribute("teacherReg2") TeacherSubjects teacherReg2,
			Map<String, Object> map, HttpSession session,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("count") int count, Model model, BindingResult result) {
		session.setAttribute("gradeId", gradeId);
		if(session.getAttribute(WebKeys.USER_REGISTRATION_OBJ)!=null){
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			model.addAttribute("classes", adminService.getGradeClasses(userRegistration.getSchool().getSchoolId(), gradeId));
		}
		else{
			model.addAttribute("classes", adminService.getGradeClasses(1, gradeId));
		}
		long mastergradeId=adminService.getMasterGradeIdbyGradeId(gradeId);
		model.addAttribute("gradeName", adminService3.getMasterGrade(mastergradeId).getGradeName());
		model.addAttribute("count", "classId" + gradeId);
		return new ModelAndView("Ajax/Teacher/TeacherRegistration2_1");
	}

	@RequestMapping(value = "/teacherRegVal2", method = RequestMethod.POST)
	public @ResponseBody
	void teacherRegVal2(HttpSession session, HttpServletResponse response,
			Map<String, Object> map, Model model,
			@RequestParam("classIds") String[] classIds,
			@RequestParam("gradeIds") String[] gradeIds,
			@RequestParam("classesLengths") String[] classesLengths) {
		try {
			String helloAjax = "teacherRegistration3.htm";
			session.setAttribute("gradeIds", gradeIds);
			session.setAttribute("classIds", classIds);
			session.setAttribute("classLengths", classesLengths);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/teacherRegistration3", method = RequestMethod.GET)
	public ModelAndView teacherRegistration3(HttpSession session, Model model) {
		String[] gradeIds = null;
		String[] classIds = null;
		if (session.getAttribute("gradeIds") != null) {
			gradeIds = (String[]) session.getAttribute("gradeIds");
			classIds = (String[]) session.getAttribute("classIds");
		}
		model.addAttribute("gradeIds", gradeIds);
		model.addAttribute("classIds", classIds);
		return new ModelAndView("Teacher/TeacherRegistration3");
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test(HttpSession session, Model model) {
		return new ModelAndView("NewFile");
	}

	@RequestMapping(value = "/teacherRegVal3", method = RequestMethod.POST)
	public ModelAndView teacherRegVal3(HttpSession session, HttpServletResponse response,
			Map<String, Object> map, HttpServletRequest request ) {
		String[] gradeLevels = request.getParameterValues("gradeLevels");
		String[] noOfSectionsPerDay = request.getParameterValues("noOfSectionsPerDay");
		String[] noOfSectionsPerWeek = request.getParameterValues("noOfSectionsPerWeek");
		ModelAndView model = new ModelAndView("login/RegistrationSuccess");
		try {
			UserRegistration teacherReg = (UserRegistration) session
					.getAttribute("teacherReg1");
			String userName = teacherReg.getEmailId();
			
			model.addObject("springUser", userName);
		
			
			if(session.getAttribute(WebKeys.USER_REGISTRATION_OBJ) == null){
				session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, teacherReg);
			}
			String[] gradeIds = (String[]) session.getAttribute("gradeIds");
			String[] classIds = (String[]) session.getAttribute("classIds");
			String[] classLengths = (String[]) session
					.getAttribute("classLengths");
			teacherService.registerTeacher(teacherReg, gradeIds, classIds,
					classLengths, gradeLevels, noOfSectionsPerDay,
					noOfSectionsPerWeek);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
