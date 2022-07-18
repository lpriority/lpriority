package com.lp.admin.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.admin.service.AttendanceService;
import com.lp.common.service.CommonService;
import com.lp.custom.exception.DataException;
import com.lp.model.Attendance;
import com.lp.model.AttendanceGroupedByStatus;
import com.lp.model.Grade;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class AttendanceController extends WebApplicationObjectSupport {
	
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CommonService commonService;
	
	static final Logger logger = Logger.getLogger(AttendanceController.class);	
	
	@RequestMapping(value = "/viewDailyAttendance", method = RequestMethod.GET)
	public ModelAndView viewDailyAttendance(HttpSession session) {
		List<Grade> schoolGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Admin/view_attendance");
		model.addObject("tab", WebKeys.TAB_VIEW_DAILY_ATTENDANCE);
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			try{
				schoolGrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolGrades);
			}catch(DataException e){
				logger.error("Error in viewDailyAttendance() of AttendanceController"+ e);
				e.printStackTrace();
				model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
			}
			return  model;
	}	
	@RequestMapping(value = "/viewWeeklyAttendance", method = RequestMethod.GET)
	public ModelAndView viewWeeklyAttendance(HttpSession session) {
		List<Grade> schoolGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Ajax/Admin/sub_view_attendance");
		model.addObject("tab", WebKeys.TAB_VIEW_WEEKLY_ATTENDANCE);
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			schoolGrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
			model.addObject("grList", schoolGrades);
		}catch(DataException e){
			logger.error("Error in viewWeeklyAttendance() of AttendanceController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}	
	@RequestMapping(value = "/viewSchoolAttendance", method = RequestMethod.GET)
	public ModelAndView viewSchoolAttendance(HttpSession session) {
		ModelAndView model = new ModelAndView("Ajax/Admin/sub_view_attendance");
		model.addObject("tab", WebKeys.TAB_VIEW_SCHOOL_ATTENDANCE);
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			try{
			    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			    Calendar calendar = Calendar.getInstance();
				java.util.Date today = calendar.getTime();
			    calendar.add(Calendar.DATE, -1);
			    java.util.Date priorDay = calendar.getTime();
				calendar.add(Calendar.DATE, -1);
				java.util.Date twoDaysPrior = calendar.getTime();
				model.addObject("today", dateFormat.format(today));
				model.addObject("priorDay", dateFormat.format(priorDay));
				model.addObject("twoDaysPrior", dateFormat.format(twoDaysPrior));
				model.addObject("attendanceChart", attendanceService.getSchoolAttandance(userReg.getSchool().getSchoolId()));
			}catch(DataException e){
				logger.error("Error in viewSchoolAttendance() of AttendanceController"+ e);
				model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
			}
			return  model;
	}	
	@RequestMapping(value = "/getScheduledClasses", method = RequestMethod.GET)
	public View getScheduledClasses(@RequestParam("gradeClassId") long gradeClassId,
			Model model, HttpSession session) throws DataException {
		try{
			model.addAttribute("scheduledClasses", attendanceService.getScheduledClasses(gradeClassId));
		}
		catch(DataException e){
			logger.error("Error in getScheduledClasses() of AttendanceController"+ e);
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getDailyAttendance", method = RequestMethod.GET)
	public ModelAndView getDailyAttendance(HttpSession session, @RequestParam("csId") long csId, @RequestParam("date") Date date, @RequestParam("tab") String tab) {
		List<Attendance> attenList = new ArrayList<Attendance>();
		ModelAndView model = new ModelAndView("Ajax/Admin/attendance_div");
		model.addObject("tab", WebKeys.TAB_VIEW_SCHOOL_ATTENDANCE);
		try{
			attenList = attendanceService.getAttandance(csId, date);
			model.addObject("attendance", attenList);
			model.addObject("tab", tab);
		}catch(DataException e){
			logger.error("Error in getDailyAttendance() of AttendanceController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}	
	
	@RequestMapping(value = "/getWeeklyAttendance", method = RequestMethod.GET)
	public ModelAndView getWeeklyAttendance(HttpSession session, @RequestParam("csId") long csId, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate
			, @RequestParam("tab") String tab) {
		List<AttendanceGroupedByStatus> attenList = new ArrayList<AttendanceGroupedByStatus>();
		ModelAndView model = new ModelAndView("Ajax/Admin/attendance_div");
		model.addObject("tab", WebKeys.TAB_VIEW_SCHOOL_ATTENDANCE);
		try{
			attenList = attendanceService.getAttandance(csId, startDate, endDate);
			model.addObject("attendance", attenList);
			model.addObject("tab", tab);
		}catch(DataException e){
			logger.error("Error in getWeeklyAttendance() of AttendanceController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}	
	
	@RequestMapping(value = "/getScheduledClassList", method = RequestMethod.GET)
	public View getScheduledClassList(@RequestParam("gradeId") long gradeId, @RequestParam("classId") long classId,
			Model model, HttpSession session) throws DataException {
		try{
			long gradeClassId = commonService.getGradeClassId(gradeId, classId);
			model.addAttribute("scheduledClasses", attendanceService.getScheduledClasses(gradeClassId));
		}
		catch(DataException e){
			logger.error("Error in getScheduledClassList() of AttendanceController"+ e);
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getScheduledClassListByTeacher", method = RequestMethod.GET)
	public View getClassListByTeacher(@RequestParam("gradeId") long gradeId, @RequestParam("classId") long classId,
			@RequestParam("teacherId") long teacherId,
			Model model, HttpSession session) throws DataException {
		try{
			long gradeClassId = commonService.getGradeClassId(gradeId, classId);
			model.addAttribute("scheduledClasses", attendanceService.getScheduledClassesByTeacher(gradeClassId, teacherId));
		}
		catch(DataException e){
			logger.error("Error in getScheduledClassListByTeacher() of AttendanceController"+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/viewDailyAttend", method = RequestMethod.GET)
	public ModelAndView viewDailyAttend(HttpSession session) {
		List<Grade> schoolGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Ajax/Admin/sub_view_attendance");
		model.addObject("tab", WebKeys.TAB_VIEW_DAILY_ATTENDANCE);
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			try{
				schoolGrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolGrades);
			}catch(DataException e){
				logger.error("Error in viewDailyAttendance() of AttendanceController"+ e);
				e.printStackTrace();
				model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
			}
			return  model; 
	}
	
	
}
