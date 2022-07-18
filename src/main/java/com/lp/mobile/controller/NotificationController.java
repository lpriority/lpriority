package com.lp.mobile.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.appadmin.service.SchoolAdminService;
import com.lp.mobile.model.AnnouncementApp;
import com.lp.mobile.model.AttendanceApp;
import com.lp.mobile.model.ChildDashboard;
import com.lp.mobile.model.EventApp;
import com.lp.mobile.model.ScheduledClass;
import com.lp.mobile.model.StudentGoalReports;
import com.lp.mobile.model.StudentReadingRegister;
import com.lp.mobile.model.TestResults;
import com.lp.mobile.service.NotificationService;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.SchoolSchedule;
import com.lp.model.UserRegistration;
import com.lp.student.service.StudentReadRegService;
import com.lp.utils.WebKeys;

@RestController
public class NotificationController {
	@Autowired
	private SchoolAdminService schoolAdminService;
	@Autowired
	private NotificationService notificationService;	
	
	
	
	@RequestMapping(value = "/updateReadStatus", method = RequestMethod.GET)
	public View updateReadStatus(Model model, HttpSession session, @RequestParam("notificationId") long notificationId,
			@RequestParam("type") String type, @RequestParam("csId") long csId) throws Exception {
		boolean status = false;
		try{
			if(type.equalsIgnoreCase("attendance")){
				status = notificationService.updateAttendanceStatus(notificationId);
			}else if(type.equalsIgnoreCase("registerForClass")){
				// notificationId represents studentId
				status = notificationService.updateRegisterForClassStatus(notificationId, csId);
			}else if(type.equalsIgnoreCase("event")){
				status = notificationService.updateEventStatus(notificationId);
			}else if(type.equalsIgnoreCase("notificationStatus")){
				status = notificationService.updateNotificationStatusStatus(notificationId);
			}
			else if(type.equalsIgnoreCase("testResults")){
				// notificationId represents studentAssignmentId
				status = notificationService.updateTestResultsStatusStatus(notificationId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("status", status);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getParentUnreadNotifications", method = RequestMethod.GET)
	public View getLatestNotifications(Model model, HttpSession session) throws Exception {		
		try{
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			UserRegistration userReg = null;		
			userReg = schoolAdminService.getUserRegistration(userName);
			session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);		
			List<String> unreadNotifications = notificationService.getLatestNotifications(userReg);
			model.addAttribute("unreadNotifications", unreadNotifications);
			model.addAttribute("length", unreadNotifications.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
		
	}
	@RequestMapping(value = "/getScheduledClassesForMobile", method = RequestMethod.GET)
	public View getScheduledClassesForMobile(Model model, HttpSession session) throws Exception {		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		long notificationLength = (long) 0;
		try{
			if(userReg!=null){
				List<ScheduledClass> teacherResponsesforClassRequests = notificationService.getTeacherResponseforChildRequests(userReg);
				model.addAttribute("teacherResponsesforClassRequests", teacherResponsesforClassRequests );
				notificationLength = teacherResponsesforClassRequests.size();
				model.addAttribute("notificationLength", notificationLength );
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getStudentAttendanceForMobile", method = RequestMethod.GET)
	public View getStudentAttendanceForMobile(Model model, HttpSession session) throws Exception {		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		long notificationLength = (long) 0;
		try{
			if(userReg!=null){
				List<AttendanceApp> attendanceList = notificationService.getStudentAttendance(userReg);
				model.addAttribute("attendanceList", attendanceList);	
				notificationLength = attendanceList.size();
				model.addAttribute("notificationLength", notificationLength);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getAnnouncementsForMobile", method = RequestMethod.GET)
	public View getAnnouncementsForMobile(Model model, HttpSession session) throws Exception {		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		long notificationLength = (long) 0;
		SchoolSchedule schedule = notificationService.getSchoolSchedule(userReg.getSchool());
		try{
			if(userReg!=null){
				List<AnnouncementApp> notificationStatusLt = notificationService.getCurrentAnnouncementsBySchool(userReg.getSchool(), schedule.getStartDate(), schedule.getEndDate());
				model.addAttribute("notificationStatusLt", notificationStatusLt);
				notificationLength = notificationStatusLt.size();
				model.addAttribute("notificationLength", notificationLength);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getEventsForMobile", method = RequestMethod.GET)
	public View getEventsForMobile(Model model, HttpSession session) throws Exception {		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		SchoolSchedule schedule = notificationService.getSchoolSchedule(userReg.getSchool());
		try{
			if(userReg!=null){
				List<EventApp> events = new ArrayList<EventApp>();
				events = notificationService.getCurrentEventsBySchool(userReg, schedule.getStartDate(), schedule.getEndDate());
				model.addAttribute("events", events);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getAssessmentTestResultsForMobile", method = RequestMethod.GET)
	public View getAssessmentTestResultsForMobile(Model model, HttpSession session) throws Exception {		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		try{
			if(userReg!=null){
				List<TestResults> assessmentTestResults = notificationService.getChildTestResults(userReg, WebKeys.LP_USED_FOR_ASSESSMENT);
				if(!assessmentTestResults.isEmpty()){
					model.addAttribute("assessmentTestResults", assessmentTestResults);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getHomeworkTestResultsForMobile", method = RequestMethod.GET)
	public View getHomeworkTestResultsForMobile(Model model, HttpSession session) throws Exception {		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		try{
			if(userReg!=null){
				List<TestResults> homeworkTestResults = notificationService.getChildTestResults(userReg, WebKeys.LP_USED_FOR_HOMEWORKS);
				if(!homeworkTestResults.isEmpty()){
					model.addAttribute("homeworkTestResults", homeworkTestResults);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getRtiTestResultsForMobile", method = RequestMethod.GET)
	public View getRtiTestResultsForMobile(Model model, HttpSession session) throws Exception {		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		try{
			if(userReg!=null){
				List<TestResults> rtiTestResults = notificationService.getChildTestResults(userReg, WebKeys.LP_USED_FOR_RTI);
				if(!rtiTestResults.isEmpty()){
					model.addAttribute("rtiTestResults", rtiTestResults);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getChildDashBoardFiles", method = RequestMethod.GET)
	public View getDashboardFiles(Model model, HttpSession session) throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		try{
			if(userReg!=null){
				List<ChildDashboard> childdashboardFilesLst = notificationService.getChildDashBoardFiles(userReg, WebKeys.LP_USED_FOR_RTI);
				if(!childdashboardFilesLst.isEmpty()){
					model.addAttribute("childDashBoardFiles", childdashboardFilesLst);
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getChildGoalSettingReports", method = RequestMethod.GET)
	public View getChildGoalSettingReports(Model model, HttpSession session) throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		try{
			if(userReg!=null){
				List<StudentGoalReports> childGoalReports = notificationService.getChildGoalSettingReports(userReg);
				if(!childGoalReports.isEmpty()){
					model.addAttribute("childGoalReports", childGoalReports);
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getChildReadingRegister", method = RequestMethod.GET)
	public View getChildReadingRegister(Model model, HttpSession session) throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRegistration userReg = null;		
		userReg = schoolAdminService.getUserRegistration(userName);
		session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
		List<StudentReadingRegister> childActivityList=notificationService.getChildReadingRegisterActivities(userReg);
		try{
			if(userReg!=null){
					if(!childActivityList.isEmpty()){
					model.addAttribute("childsActivityList", childActivityList);
				}
			}
									
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	
}
