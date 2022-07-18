package com.lp.teacher.controller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.admin.service.AdminService;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.Attendance;
import com.lp.model.ClassActualSchedule;
import com.lp.model.Grade;
import com.lp.model.SchoolDays;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.TeacherViewClassService;
import com.lp.utils.WebKeys;

@Controller
public class TeacherViewClassController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private TeacherViewClassService teacherViewClassService;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private CommonService reportService;
	@Autowired
	private AdminSchedulerDAO adminSchedulerdao;
	@Autowired
	private AndroidPushNotificationsService apns;
	
	static final Logger logger = Logger.getLogger(TeacherViewClassController.class);
	
	@RequestMapping(value = "/displayRoster", method = RequestMethod.GET)
	public ModelAndView displayClassMainPage(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);		
		model.addAttribute("divId", "Roster");
		model.addAttribute("teacherId", teacher.getTeacherId());
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacher);
			model.addAttribute("teacherGrades", teacherGrades);
			if(teacherGrades.size() == 0)
			model.addAttribute("hellowAjax","Grades not available !!");
		}catch(DataException e){
			logger.error("Error in displayClassMainPage() of TeacherViewClassController "+ e);
		}
		return new ModelAndView("Teacher/view_class_main_page");
	}
	@RequestMapping(value = "/getStudentDetails", method = RequestMethod.GET)
	public ModelAndView getStudentDetails(@RequestParam("divId") String divId,@RequestParam("gradeId") long gradeId,@RequestParam("classId") long classId,@RequestParam("csId") long csId,@RequestParam("page") String page,@RequestParam("dateToUpdate") String dateToUpdate, HttpSession session,Model model) {
		List<Student> studentLt = new ArrayList<Student>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		String jspName ="";
		Date updateDate = new Date();
		DateFormat db_formatter = new SimpleDateFormat(WebKeys.DB_DATE_FORMATE);
		DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
		String date="";
		try{
		try {
			if(dateToUpdate.equals("")){
				date = db_formatter.format(updateDate);
			}else{
				updateDate = formatter.parse(dateToUpdate);
				date = db_formatter.format(updateDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(divId.equalsIgnoreCase(WebKeys.ROSTER)){
			studentLt = commonService.getStudentsBySection(csId);
			model.addAttribute("studentLt", studentLt);
			model.addAttribute("csId", csId);
			jspName = "Ajax2/Teacher/view_class_students_details";
		}else if(page != null && page.equalsIgnoreCase(WebKeys.TAKE_ATTENDANCE)){
			boolean attendanceTaken =  teacherViewClassService.checkTodaysAttendance(csId, updateDate);
			if(attendanceTaken){
				List<Attendance> attendanceLt = new ArrayList<Attendance>();
				attendanceLt = commonService.getStudentsAttendance(csId, date);
				model.addAttribute("attendanceLt", attendanceLt);
				model.addAttribute("noOfStudents", attendanceLt.size());
			}else{
				studentLt = commonService.getStudentsBySection(csId);
				model.addAttribute("studentLt", studentLt);
				model.addAttribute("noOfStudents", studentLt.size());
			}
			model.addAttribute("dateToUpdate",  formatter.format(updateDate));
			model.addAttribute("csId", csId);
			jspName = "Ajax2/Teacher/students_attendance_page";
		}else if(page != null && page.equalsIgnoreCase(WebKeys.UPDATE_ATTENDANCE)){
			List<Attendance> attendanceLt = new ArrayList<Attendance>();
			attendanceLt = commonService.getStudentsAttendance(csId,date);
			model.addAttribute("attendanceLt", attendanceLt);
			model.addAttribute("noOfStudents", attendanceLt.size());
			model.addAttribute("dateToUpdate",  formatter.format(updateDate));
			model.addAttribute("csId", csId);
			jspName = "Ajax2/Teacher/students_attendance_page";
		}else if(divId.equalsIgnoreCase(WebKeys.REGISTRATION)){
			long gradeClassId = reportService.getGradeClassId(gradeId, classId);
			long gradeLevelId = adminSchedulerdao.getGradeLevelIdByCsId(csId);
			studentLt = teacherViewClassService.getStudentsOfRegistration(csId,gradeClassId);
			model.addAttribute("studentLt", studentLt);
			model.addAttribute("csId", csId);
			model.addAttribute("gradeClassId", gradeClassId);
			model.addAttribute("gradeLevelId", gradeLevelId);
			model.addAttribute("csId", csId);
			jspName = "Ajax2/Teacher/view_class_students_details";
		}
		model.addAttribute("divId", divId);
		model.addAttribute("page", page);
		model.addAttribute("teacherId", teacher.getTeacherId());
		}catch(Exception e){
			logger.error("Error in getStudentDetails() of TeacherViewClassController "+ e);
		}
		return new ModelAndView(jspName);
	}
	@RequestMapping(value = "/takeAttendancePage", method = RequestMethod.GET)
	public ModelAndView takeAttendance(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);		
		model.addAttribute("divId", "Attendance");
		model.addAttribute("page", "takeAttendance");
		model.addAttribute("teacherId", teacher.getTeacherId());
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacher);
			model.addAttribute("teacherGrades", teacherGrades);
			if(teacherGrades.size() == 0)
				model.addAttribute("hellowAjax","Grades not available !!");
		}catch(DataException e){
			logger.error("Error in takeAttendance() of TeacherViewClassController "+ e);
		}
		return new ModelAndView("Ajax2/Teacher/view_class_header_page");
	}
	@RequestMapping(value = "/updateAttendancePage", method = RequestMethod.GET)
	public ModelAndView updateAttendance(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("divId", "Attendance");
		model.addAttribute("page", "updateAttendance");
		model.addAttribute("teacherId", teacher.getTeacherId());
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacher);
			model.addAttribute("teacherGrades", teacherGrades);
			if(teacherGrades.size() == 0)
				model.addAttribute("hellowAjax","Grades not available !!");
		}catch(DataException e){
			logger.error("Error in updateAttendance() of TeacherViewClassController "+ e);
		}
		return new ModelAndView("Ajax2/Teacher/view_class_header_page");
	}
	@RequestMapping(value = "/saveAttendance", method = RequestMethod.GET)
	public @ResponseBody void saveAttendance(HttpServletResponse response,HttpSession session,@RequestParam("status") List<String> status,@RequestParam("studentId") List<Long> studentId,@RequestParam("csId") long csId,String date,String page,boolean savable)throws Exception { 
		boolean takeattendance = false;
		Date updateDate = new Date();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
		if(date != null && date !="")
			updateDate = formatter.parse(date);
		try {
			if(page.equalsIgnoreCase(WebKeys.UPDATE_ATTENDANCE) || ( page.equalsIgnoreCase(WebKeys.TAKE_ATTENDANCE)&& savable)){
				takeattendance = teacherViewClassService.saveAttendance(studentId,status,updateDate,csId,teacher.getTeacherId());
				  if (takeattendance) {
					  response.getWriter().write("Updated Successfully");  
					  
		          } else {
		        	  response.getWriter().write("Update Failed");  
		          }
			}else{
				boolean attendanceTaken =  teacherViewClassService.checkTodaysAttendance(csId,updateDate);
				if(attendanceTaken){
					 response.getWriter().write("Yes"); 
				}else{
				 takeattendance = teacherViewClassService.saveAttendance(studentId,status,updateDate,csId,teacher.getTeacherId());
				  if (takeattendance) {
					  response.getWriter().write("Saved Successfully");  
					  
		          } else {
		        	  response.getWriter().write("Update Failed");  
		          }
				}
			}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
		} catch (Exception e) {
			logger.error("Error in saveAttendance() of TeacherViewClassController "+ e);
		}
	}
	@RequestMapping(value = "/classRegistration", method = RequestMethod.GET)
	public ModelAndView classRegistration(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("divId", "Registration");
		model.addAttribute("teacherId", teacher.getTeacherId());
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacher);
			model.addAttribute("teacherGrades", teacherGrades);
			if(teacherGrades.size() == 0)
				model.addAttribute("hellowAjax","Grades not available !!");
		}catch(DataException e){
			logger.error("Error in classRegistration() of TeacherViewClassController "+ e);
		}
		return new ModelAndView("Ajax2/Teacher/view_class_header_page");
	}
	@RequestMapping(value = "/setStudentAction", method = RequestMethod.GET)
	public @ResponseBody void setStudentAction(
			HttpServletResponse response,
			@RequestParam("studentId") long studentId,
			@RequestParam("status") String status,
			@RequestParam("studentName") String studentName,
			@RequestParam("gradeClassId") long gradeClassId,
			@RequestParam("gradeLevelId") long gradeLevelId,
			@RequestParam("csId") long csId,
			Model model) {
		try {
		boolean success = teacherViewClassService.setStudentAction(studentId, csId, gradeClassId,gradeLevelId,status);
		if(success){
			if(status.equalsIgnoreCase(WebKeys.ACCEPTED)){
				apns.sendScheduleClassNotification("Scheduled", studentId);
				response.getWriter().write(studentName+ " request Accepted!!");
			}else if(status.equalsIgnoreCase(WebKeys.DECLINED)){
				response.getWriter().write(studentName+ " request Declined!!");  
			}else if(status.equalsIgnoreCase(WebKeys.WAITING)){
				response.getWriter().write(studentName+ " request reset to waiting state!!");  
			}
		}
		}catch(Exception e){
			logger.error("Error in setStudentAction() of TeacherViewClassController "+ e);
		}
	}
	@RequestMapping(value = "/requestForClass", method = RequestMethod.GET)
	public ModelAndView requestForClass(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		teacherGrades = teacherViewClassService.getTeacherGradesForRequest(teacher.getTeacherId());
		model.addAttribute("teacherGrades", teacherGrades);
		model.addAttribute("divId", "RequestForClass");
		return new ModelAndView("Ajax2/Teacher/view_class_header_page");
	}
	@RequestMapping(value = "/sendRequestForAClass", method = RequestMethod.GET)
	public @ResponseBody void sendRequestForAClass(HttpServletResponse response,@RequestParam("gradeId") long gradeId, @RequestParam("classId") long classId,HttpSession session) {
		String status = "";
		try {
			Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			status = teacherViewClassService.sendRequestForAClass(teacher.getTeacherId(), gradeId, classId);
			if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
				response.getWriter().write("Request sent successfully !!");  
			}else if(status.equalsIgnoreCase(WebKeys.WAITING)){
				response.getWriter().write("Request is in progress !!");  
			}else if(status.equalsIgnoreCase(WebKeys.DECLINED)){
				response.getWriter().write("Request declined !!");  
			}else if(status.equalsIgnoreCase(WebKeys.ACCEPTED)){
				response.getWriter().write("Class already added !!");  
			}
		}catch(Exception e){
			logger.error("Error in sendRequestForAClass() of TeacherViewClassController "+ e);
		}
	}
	@RequestMapping(value = "/showMyClassesTimeTable", method = RequestMethod.GET)
	public ModelAndView showMyClassesTimeTable(HttpSession session,Model model) {
		UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.LP_USER_REGISTRATION);
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		List<ClassActualSchedule> casLt = null;
		List<SchoolDays> schoolDaysLt = null;
		try{
			if(userRegistration != null){
				schoolDaysLt = adminService.getSchoolDays(userRegistration.getSchool());
				casLt = teacherViewClassService.getMyClassesTimeTable(teacher.getTeacherId());
			}
			for (ClassActualSchedule cas : casLt) {
				String stime = cas.getPeriods().getStartTime();
				String etime =  cas.getPeriods().getEndTime();
				Date sdate = new Date();
				Date edate = new Date();
				DateFormat formatter = new SimpleDateFormat(WebKeys.TIME_FORMATE);
				try {
					sdate = formatter.parse(stime);
					stime = formatter.format(sdate);
					edate = formatter.parse(etime);
					etime = formatter.format(edate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			    cas.getPeriods().setStartTime(stime);
				cas.getPeriods().setEndTime(etime);
				
			}
			model.addAttribute("casLt", casLt);
			model.addAttribute("schoolDaysLt", schoolDaysLt);
			model.addAttribute("divId", "showMyClasses");
		}catch(DataException e){
			logger.error("Error in showMyClassesTimeTable() of TeacherViewClassController "+ e);
		}
		return new ModelAndView("Ajax2/Teacher/show_my_classes");
	}
	
	@RequestMapping(value = "/gradeBookUpdateAttendancePage", method = RequestMethod.GET)
	public ModelAndView gradeBookUpdateAttendancePage(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);		
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("divId", "Attendance");
		model.addAttribute("page", "updateAttendance");
		model.addAttribute("gradebook", WebKeys.GRADEBOOK);
		model.addAttribute("tab", WebKeys.LP_TAB_GRADEBOOK_ATTENDANCE);
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacher);
			model.addAttribute("teacherGrades", teacherGrades);
			if(teacherGrades.size() == 0)
				model.addAttribute("hellowAjax","Grades not available !!");
		}catch(DataException e){
			logger.error("Error in gradeBookUpdateAttendancePage() of TeacherViewClassController "+ e);
		}
		return new ModelAndView("Ajax2/Teacher/view_class_header_page");
	}
	
	@RequestMapping(value = "/getStudentRoster", method = RequestMethod.GET)
	public ModelAndView getStudentRoster(HttpSession session, @RequestParam("csId") long csId) {
		ModelAndView model = new ModelAndView("Ajax2/Teacher/student_roster");
		try{
			model.addObject("studentRoster", teacherViewClassService.getStudentRoster(csId));
		}
		catch(Exception e){
			logger.error("Error in getStudentRoster() of  TeacherViewClassController"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentRoster() of TeacherViewClassController", e);
		}
		return model;
	}
	
	@RequestMapping(value = "/classRoster", method = RequestMethod.GET)
	public ModelAndView classRoster(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);		
		model.addAttribute("divId", "Roster");
		model.addAttribute("teacherId", teacher.getTeacherId());
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacher);
			model.addAttribute("teacherGrades", teacherGrades);
			if(teacherGrades.size() == 0)
			model.addAttribute("hellowAjax","Grades not available !!");
		}catch(DataException e){
			logger.error("Error in displayClassMainPage() of TeacherViewClassController "+ e);
		}
		return new ModelAndView("Ajax2/Teacher/view_class_header_page");
	}
	
}
