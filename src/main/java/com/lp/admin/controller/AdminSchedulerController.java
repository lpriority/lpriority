package com.lp.admin.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminSchedulerService;
import com.lp.appadmin.service.AppAdminService;
import com.lp.model.Minutes;
import com.lp.model.SchoolSchedule;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class AdminSchedulerController {
	@Autowired
	private AdminSchedulerService adminSchedulerservice;
	@Autowired
	private AppAdminService appAdminService;
	
	static final Logger logger = Logger.getLogger(AdminSchedulerController.class);


	@RequestMapping(value = "/AutomaticScheduler", method = RequestMethod.GET)
	public ModelAndView getPeriods(Map<String, Object> map,
			HttpSession session, HttpServletResponse response,
			HttpServletRequest request) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		SchoolSchedule schoolSchedule = adminSchedulerservice
				.getSchoolSchedule(userReg.getSchool());
		ModelAndView model = new ModelAndView("Admin/TeacherScheduleForm",
				"schoolSchedule", schoolSchedule);
		if(session.getAttribute("returnMessage") != null){
			model.addObject("returnMessage", session.getAttribute("returnMessage").toString());
			session.removeAttribute("returnMessage");
		}
		SimpleDateFormat sm = new SimpleDateFormat(WebKeys.DATE_FORMATE);
		if (schoolSchedule.getStartDate() != null) {
			model.addObject("startDate", sm.format(schoolSchedule.getStartDate()));
			model.addObject("endDate", sm.format(schoolSchedule.getEndDate()));
			model.addObject("startTimehours", schoolSchedule.getDayStartTime());
			model.addObject("startTimeMinutes", schoolSchedule.getDayStartTimeMin());
			model.addObject("startTimeMeridian", schoolSchedule.getDayStartTimeMeridian());
			model.addObject("endTimehours", schoolSchedule.getDayEndTime());
			model.addObject("endTimeMinutes", schoolSchedule.getDayEndTimeMin());
			model.addObject("endTimeMeridian",
					schoolSchedule.getDayEndTimeMeridian());
		}
		String stat = "Add";
		model.addObject("status", stat);
		List<Minutes> mins = appAdminService.getMinutes();
		model.addObject("mins", mins);
		return model;
	}

	@RequestMapping(value = "/SaveAutomaticScheduler", method = RequestMethod.POST)
	public  @ResponseBody void SaveAutomaticScheduler(HttpServletResponse response,
			HttpSession session,
			@ModelAttribute("schoolSchedule") SchoolSchedule schoolSchedule,
			BindingResult result) throws Exception {
		try {
			
			boolean status = adminSchedulerservice.scheduleTeachers(schoolSchedule);
			if(status)
				response.getWriter().write( WebKeys.AUTOMATED_SCHEDULER_SUCCESS);  
			else
				response.getWriter().write( WebKeys.AUTOMATED_SCHEDULER_FAILED); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/html");  
	}

}
