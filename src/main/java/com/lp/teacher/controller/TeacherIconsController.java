package com.lp.teacher.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.lp.appadmin.service.AppAdminService;
import com.lp.common.service.CommonService;
import com.lp.custom.exception.DataException;
import com.lp.model.AdminTeacherReports;
import com.lp.model.ClassStatus;
import com.lp.model.School;
import com.lp.model.Teacher;
import com.lp.model.TeacherPerformances;
import com.lp.model.TeacherReports;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.WebKeys;

@Controller
public class TeacherIconsController {

	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value = "/TeacherSelfEvaluation", method = RequestMethod.GET)
	public ModelAndView adminCreateTeacherReports(HttpSession session) {
		ModelAndView model;
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String userType = userReg.getUser().getUserType();
		if (userType.equalsIgnoreCase(WebKeys.LP_USER_TYPE_ADMIN)) {
			List<TeacherPerformances> teacherPerformance = teacherService
					.getTeacherPerformances();
			List<ClassStatus> teacherList = teacherService
					.getTeachersBySchoolId(userReg.getSchool());
			model = new ModelAndView("Teacher/teacher_self_evaluation",
					"TeacherPerformances", new TeacherPerformances());
			model.addObject("TPList", teacherPerformance);
			model.addObject("TeacherList", teacherList);

		} else {

			List<TeacherPerformances> teacherPerformance = teacherService
					.getTeacherPerformances();
			model = new ModelAndView("Teacher/teacher_self_evaluation",
					"TeacherPerformances", new TeacherPerformances());
			model.addObject("TPList", teacherPerformance);
		}
		model.addObject("userType", userReg.getUser().getUserType()
				.toLowerCase());
		return model;
	}

	@RequestMapping(value = "/TeacherReportCard", method = RequestMethod.GET)
	public ModelAndView teacherReportCard(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Teacher teacher = teacherService.getTeacher(userReg.getRegId());
		// Teacher teacher=teacherService.getTeacher(7);
		School school = userReg.getSchool();
		// School school=teacher.getUserRegistration().getSchool();
		UserRegistration userregis1 = teacherService
				.getAdminUserRegistration(school);
		List<TeacherReports> teacherreports = teacherService.getTeacherReports(
				userregis1.getUser(), teacher);
		ModelAndView model = new ModelAndView("Teacher/admin_reports",
				"AdminTeacherReports", new AdminTeacherReports());
		model.addObject("TRList", teacherreports);
		String teachername = commonService.getFullName(teacher
				.getUserRegistration().getRegId());
		model.addObject("TeacherName", teachername);
		model.addObject("rcount", teacherreports.size());
		return model;
	}

	@RequestMapping(value = "/AdminViewTeacherSelfEvaluation", method = RequestMethod.GET)
	public ModelAndView adminViewTeacherEvaluation(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<ClassStatus> teacherList = teacherService
				.getTeachersBySchoolId(userReg.getSchool());
		ModelAndView model = new ModelAndView(
				"Teacher/admin_view_teacher_selfevaluation",
				"TeacherPerformances", new TeacherPerformances());
		model.addObject("TeacherList", teacherList);
		return model;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getTeacherReportDates", method = RequestMethod.GET)
	public View getTeacherReportDates(
			@RequestParam("teacherId") long teacherId, Model model,
			HttpSession session) throws Exception {
		List<AdminTeacherReports> teacherReportdates = teacherService
				.getTeacherSelfReportDates(teacherId);
		Map reportdates = new HashMap();
		for (int count = 0; count < teacherReportdates.size(); count++) {
			reportdates.put(teacherReportdates.get(count).getDate(),
					teacherReportdates.get(count).getDate());
		}
		
		model.addAttribute("teacherReportDates", reportdates);
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/saveTeacherSelfEvaluationReports", method = RequestMethod.POST)
	public @ResponseBody
	void SaveStudentDetails3(
			HttpSession session,
			HttpServletResponse response,
			Model model,
			@RequestParam("performanceids") long[] performanceids,
			@RequestParam("performances") String[] performances,
			@RequestParam("expression") String[] expression,
			@ModelAttribute("TeacherPerformances") TeacherPerformances teacherperformances,
			@RequestParam("teacherRegId") long teacherRegId) {

		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long regId = userReg.getRegId();
		// long regId=7;
		String userType = userReg.getUser().getUserType();
		String helloAjax = "";
		boolean stat = false, stat1 = false;
		AdminTeacherReports adminteacherreports = new AdminTeacherReports();
		try {
			if (userType.equalsIgnoreCase(WebKeys.LP_USER_TYPE_ADMIN)) {
				adminteacherreports.setTeacher(teacherService
						.getTeacher(teacherRegId));
				adminteacherreports.setUser(appAdminService
						.getUserType("Admin"));

			} else {
				adminteacherreports
						.setTeacher(teacherService.getTeacher(regId));
				adminteacherreports.setUser(appAdminService
						.getUserType("Teacher"));
			}
			try{
				stat = teacherService.saveAdminTeacherReports(adminteacherreports);			
			
				if (stat == true) {
					for (int i = 0; i < performanceids.length; i++) {
						TeacherReports teacherreports = new TeacherReports();
						teacherreports.setAdminTeacherReports(adminteacherreports);
						teacherreports.setTeacherPerformances(teacherService
								.getTeacherPerformance(performanceids[i]));
						teacherreports.setChoosenOption(performances[i]);
						teacherreports.setComments(expression[i]);
						stat1 = teacherService
								.saveTeacherPerformances(teacherreports);
	
					}
				}
				if (stat1 == true) {
					helloAjax = "Teacher Reports Created Successfully";
				} else {
					helloAjax = "Failure";
				}
			}catch(DataException e){
				helloAjax = e.getMessage();
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/getTeacherSelfReports", method = RequestMethod.GET)
	public ModelAndView getTeacherSelfReports(
			@RequestParam("teacherId") long teacherId,
			@RequestParam("reportDate") String reportDate,
			Map<String, Object> map,
			HttpSession session,
			@ModelAttribute("AdminTeacherReports") AdminTeacherReports adminTeacherReports,
			HttpServletResponse response, HttpServletRequest request) {
		Teacher teacher = teacherService.getTeacherbyTeacherId(teacherId);
		List<TeacherReports> teacherreports = teacherService
				.getTeacherReportsByDate(teacher, reportDate);
		ModelAndView model = new ModelAndView(
				"Ajax/Teacher/show_teacher_selfreports", "TeacherReports",
				new TeacherReports());
		model.addObject("TRList", teacherreports);
		return model;

	}

	@RequestMapping(value = "/sendmail", method = RequestMethod.GET)
	public ModelAndView sendMail(HttpSession session) {
		ModelAndView model = new ModelAndView("Teacher/send",
				"AdminTeacherReports", new AdminTeacherReports());
		return model;
	}


}
