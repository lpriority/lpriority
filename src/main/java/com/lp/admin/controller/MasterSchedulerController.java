package com.lp.admin.controller;
import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.CommonService;
import com.lp.model.Periods;
import com.lp.model.School;
import com.lp.model.SchoolSchedule;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

public class MasterSchedulerController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private CommonService reportService;
	
	static final Logger logger = Logger.getLogger(MasterSchedulerController.class);

	@RequestMapping(value = "/ScheduleTimeTable", method = RequestMethod.GET)
	public ModelAndView getPeriods(Map<String, Object> map,
			HttpSession session, @ModelAttribute("schoolSchedule") SchoolSchedule SchoolSchedule,
			HttpServletResponse response, HttpServletRequest request) {
		UserRegistration userReg=(UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long adminRegId=userReg.getRegId();
		School school=reportService.getSchoolIdByRegId(adminRegId);
		ModelAndView model = new ModelAndView("Admin/ScheduleTimeTable", "schoolSchedule",
				new SchoolSchedule());
		String stat="Add";
		model.addObject("status", stat);
		List<TeacherSubjects> teachers=adminService.getSchoolTeachers(school);
		model.addObject("teachers", teachers);
		List<Periods> periods=adminService.getSchoolPeriods(school);
		model.addObject("periods", periods);
		return model;
	}
}
