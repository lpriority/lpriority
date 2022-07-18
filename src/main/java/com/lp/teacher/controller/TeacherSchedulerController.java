package com.lp.teacher.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminSchedulerService;
import com.lp.admin.service.AdminService;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.appadmin.service.AppAdminService;
import com.lp.common.service.CommonService;
import com.lp.custom.exception.DataException;
import com.lp.mail.service.MailServiceImpl;
import com.lp.model.ClassActualSchedule;
import com.lp.model.ClassStatus;
import com.lp.model.Days;
import com.lp.model.Grade;
import com.lp.model.HomeroomClasses;
import com.lp.model.HomeroomClassesForm;
import com.lp.model.Periods;
import com.lp.model.SchoolDays;
import com.lp.model.Section;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;
import com.lp.student.service.StudentService;
import com.lp.teacher.service.TeacherSchedulerService;
import com.lp.teacher.service.TeacherService;
import com.lp.teacher.service.TeacherViewClassService;
import com.lp.utils.WebKeys;

@Controller
public class TeacherSchedulerController extends WebApplicationObjectSupport{

	@Autowired
	private AdminService adminService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private TeacherSchedulerService teacherSchedulerService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private AdminSchedulerService adminSchedulerService;	
	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private UserRegistrationDAO userRegistrationDAO;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherViewClassService teacherViewClassService;
	@Value("${mail.username}")
	String fromAddress;
	
	static final Logger logger = Logger.getLogger(TeacherSchedulerController.class);
	@RequestMapping("/masterScheduler")
	public ModelAndView masterScheduler(HttpSession session, Model model) {
	List<Teacher> teachersLt = new ArrayList<Teacher>();
		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
			model.addAttribute("schoolgrades", schoolgrades);
			List<ClassStatus>  classStatusLt =  teacherService.getTeachersBySchoolId(userReg.getSchool());
			for (ClassStatus classStatus : classStatusLt) {
				teachersLt.add(classStatus.getTeacher());
			}
			model.addAttribute("teachersLt", teachersLt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Admin/MasterScheduler");
	}
	
	@RequestMapping("/getStudentList")
	public ModelAndView getStudentList(
			@RequestParam("uniqueId") long uniqueId, 
			@RequestParam("type") String type, 
			Model model) {
		List<Student> studentLt = null;
	 try{	
		if(type.equalsIgnoreCase("class")){
			studentLt = commonService.getStudentsBySection(uniqueId);
			model.addAttribute("csId",  uniqueId);
		}else if(type.equalsIgnoreCase("homeroom")){
			studentLt = studentService.getStudentsByHomeRoom(uniqueId);
		}
		model.addAttribute("studentLt", studentLt);
	 }catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getStudentList() of TeacherSchedulerController"+ e);
	 }
		return new ModelAndView("Ajax/Admin/StudentsDetailsPage");
	}
	
	@RequestMapping(value ="/getEmailWindow", method = RequestMethod.GET)
	public ModelAndView getEmailWindow(@RequestParam("studentEmailId") String studentEmailId, Model model) {
		UserRegistration parentUser,parentUser2 =  null;
		String parentEmailId=null,parentEmailId2=null;
		long userTypeid;
		userTypeid = commonService.getActiveUserRegistration(studentEmailId).getUser().getUserTypeid();
		if(userTypeid == 5 || userTypeid==6){
		long parentRegid = commonService.getParentRegId(studentEmailId);
		long parentRegid2=commonService.getParentRegId2(studentEmailId);
			if(parentRegid != 0){
				parentUser = userRegistrationDAO.getUserRegistration(parentRegid);
				parentEmailId = parentUser.getEmailId();
				model.addAttribute("parentEmailId", parentEmailId);
			}else{
				model.addAttribute("parentEmailId", "");
			}
			if(parentRegid2!=0){
				parentUser2 = userRegistrationDAO.getUserRegistration(parentRegid2);
				parentEmailId2 = parentUser2.getEmailId();
				model.addAttribute("parentEmailId2", parentEmailId2);
			}else{
				model.addAttribute("parentEmailId2", "");
			}
		}
		model.addAttribute("studentEmailId", studentEmailId);
		model.addAttribute("userTypeid", userTypeid);
//		if(parentEmailId != null){
//			model.addAttribute("parentEmailId", parentEmailId);
//		}else{
//			model.addAttribute("parentEmailId", "");
//		}
		return new ModelAndView("Ajax/Admin/SendMail");
	}
	
	@RequestMapping(value ="/sendGroupMail", method = RequestMethod.GET)
	public ModelAndView sendGroupMail(@RequestParam("subject") String subject,@RequestParam("body") String body,@RequestParam("studentEmailId") String studentEmailId, Model model,HttpServletRequest request) {
		String parentEmailId=request.getParameter("parentEmailId");
		String parentEmailId2=request.getParameter("parentEmailId2");
		ArrayList<String> toList = new ArrayList<String>();
		if(studentEmailId != null && studentEmailId.length() > 0)
			toList.add(studentEmailId);
		if(parentEmailId != null && parentEmailId.length() > 0)
			toList.add(parentEmailId);
		if(parentEmailId2 != null && parentEmailId2.length() > 0)
			toList.add(parentEmailId2);
		if(toList.size() > 0 && fromAddress != null){
			String[] to = toList.toArray(new String[toList.size()]);
			boolean status = MailServiceImpl.sendGroupMail(to,null,null, fromAddress, subject, body);
			model.addAttribute("status", status);
		}
		return new ModelAndView("Ajax/Admin/SendMail");
	}
	
	@RequestMapping("/teacherScheduler")
	public ModelAndView getScheduler(HttpSession session, Model model) {
		UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> gradeLt = adminService.getSchoolGrades(userRegistration.getSchool().getSchoolId());
		model.addAttribute("gradeLt", gradeLt);
		model.addAttribute("divId", "planSchedule");
		return new ModelAndView("Admin/scheduler_home_page");
	}
	
	@RequestMapping(value = "/getPlannerData", method = RequestMethod.GET)
	public ModelAndView getPlannerData(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("teacherId") long teacherId,
			@RequestParam("divId") String divId,
			HttpSession session,Model model) {
		
		List<SchoolDays> schoolDaysLt = null;
		List<Section> sectionsLt = null;
		List<Periods> periodsLt = null;
		List<Grade> gradeLt = null;
		List<ClassActualSchedule> casLt = null;
		int noOfPeriods;
		String startDate = "";
		String endDate = "";
		Date stDate = new Date();
		Date enDate = new Date();
		final String OLD_FORMAT = "yyyy-mm-dd";
		final String NEW_FORMAT = "mm/dd/yyyy";

		UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		if(userRegistration != null){
			schoolDaysLt = adminService.getSchoolDays(userRegistration.getSchool());
		}
		sectionsLt = teacherSchedulerService.getSections(gradeId, classId);
		periodsLt = adminSchedulerService.getSchoolPeriods(gradeId);
		noOfPeriods = periodsLt.size();
		gradeLt = adminService.getSchoolGrades(userRegistration.getSchool().getSchoolId());
		casLt = teacherSchedulerService.getCSIds(gradeId, classId, teacherId, userRegistration.getSchool().getSchoolId());
		if(casLt.size() > 0){
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
				stDate = formatter.parse(casLt.get(0).getClassStatus().getStartDate().toString());
				enDate = formatter.parse(casLt.get(0).getClassStatus().getEndDate().toString());
				formatter.applyPattern(NEW_FORMAT);
				startDate = formatter.format(stDate);
				endDate = formatter.format(enDate);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("schoolDaysLt", schoolDaysLt);
		model.addAttribute("sectionsLt", sectionsLt);
		model.addAttribute("periodsLt", periodsLt);
		model.addAttribute("noOfPeriods", noOfPeriods);
		model.addAttribute("gradeLt", gradeLt);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("classId", classId);
		model.addAttribute("teacherId", teacherId);
		model.addAttribute("casLt", casLt);
		if(divId.equalsIgnoreCase("planSchedule")){
			model.addAttribute("page", "planSchedule");
		}else{
			model.addAttribute("page", "viewTeacherRequests");	
		}
		model.addAttribute("divId", "plannerData");
		return new ModelAndView("Admin/scheduler_home_page");

	}
	@RequestMapping("/getGradeLevelTeachers")
	public ModelAndView getHomeRoomTeachers(@RequestParam("gradeId") long gradeId, HttpSession session, Model model) {
		UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Teacher> teachersList=teacherService.getTeachersByGradeId(gradeId,userRegistration
				.getSchool().getSchoolId());
		List<Section> sectionsList = adminService.getAllSectionsByHomeRoom(gradeId, userRegistration
				.getSchool().getSchoolId());
		model.addAttribute("teachersList", teachersList);
		model.addAttribute("sectionsList", sectionsList);
		return new ModelAndView("Ajax/Admin/set_home_room_teachers", "homeroomClassesForm", new HomeroomClassesForm());
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/planHomeRoom")
	public ModelAndView planHomeRoom1(@ModelAttribute("homeroomClassesForm")HomeroomClassesForm homeroomClassesForm,HttpSession session,  Model model) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminService.getSchoolGrades(userReg
				.getSchool().getSchoolId());	
		model.addAttribute("divId", "planHomeRoom");
		model.addAttribute("schoolgrades", schoolgrades);		
		return new ModelAndView("Admin/plan_home_room");
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/saveHomeRoomTeacher")
	public ModelAndView saveHomeRoomTeacher(@ModelAttribute("homeroomClassesForm")HomeroomClassesForm homeroomClassesForm,
			BindingResult result,@RequestParam("gradeId") long gradeId, HttpSession session, Model model) {			
		UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);		
		Periods periods = adminSchedulerService.getHomeRoomPeriod(userRegistration.getSchool(),gradeId);
		for(HomeroomClasses hrClasses:homeroomClassesForm.getHomeroomClassesList()){
			hrClasses.setSection(adminService.getSection(hrClasses.getSection().getSectionId()));
			hrClasses.setTeacher(adminSchedulerService.getTeacher(hrClasses.getTeacher().getTeacherId()));
			hrClasses.setPeriods(periods);
			if(hrClasses.getTeacher().getTeacherId() > 0){
				adminSchedulerService.SetHomeroomClassForTeacher(hrClasses);
			}			
		}		
		List<Grade> schoolgrades = adminService.getSchoolGrades(userRegistration
				.getSchool().getSchoolId());		
		model.addAttribute("schoolgrades", schoolgrades);		
		return new ModelAndView("Admin/plan_home_room");		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/viewTeacherRequest")
	public ModelAndView viewTeacherRequest(HttpSession session,  Model model) {
		UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		 List<TeacherSubjects> teacherRequestLt = teacherSchedulerService.getTeachersRequests(userRegistration.getSchool().getSchoolId());
		 model.addAttribute("teacherRequestLt", teacherRequestLt);
		 model.addAttribute("divId", "viewTeacherRequests");
		 return new ModelAndView("Admin/scheduler_home_page");
	}

	@RequestMapping(method=RequestMethod.GET,value="/acceptTeacherRequest")
	public  ModelAndView  acceptTeacherRequest(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("teacherId") long teacherId,
			@RequestParam("status") String status,
			@RequestParam("teacherName") String teacherName,
			HttpSession session,Model model) {
			model.addAttribute("gradeId", gradeId);
			model.addAttribute("classId", classId); 
			model.addAttribute("teacherId", teacherId);
			model.addAttribute("divId", "viewTeacherRequests");
		return new ModelAndView("redirect:/getPlannerData.htm");
	}

	@RequestMapping(method=RequestMethod.GET,value="/declineTeacherRequest")
	public  @ResponseBody  void declineTeacherRequest(
			HttpServletResponse response,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("teacherId") long teacherId,
			@RequestParam("status") String status,
			@RequestParam("teacherName") String teacherName,
			HttpSession session,Model model) {
		boolean success =  teacherSchedulerService.setTeacherReplyAction(gradeId,classId,teacherId,status);
		try {
		if(success){
			if(status.equalsIgnoreCase(WebKeys.DECLINED)){
				response.getWriter().write(teacherName+ " request is Declined!!");  
			}else if(status.equalsIgnoreCase(WebKeys.WAITING)){
				response.getWriter().write(teacherName+ " request reset to waiting state!!");  
			}
		}
		}catch(Exception e){
			e.printStackTrace();	
		}
	}
	
	@RequestMapping("/checkHomeRoomTeacher")
	public View checkHomeRoomTeacher(
			@RequestParam("teacher") long teacher,
			@RequestParam("sectionId") long  sectionId, 
			@RequestParam("gradeId") long  gradeId, 
			@RequestParam("homeRoomId") long  homeRoomId,
			HttpSession session, Model model) {
		String status = "";
		HomeroomClasses hrc = new HomeroomClasses();
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Section sec = new Section();
			sec.setSectionId(sectionId);
			Teacher teach = new Teacher();
			teach.setTeacherId(teacher);
			Periods period = adminSchedulerService.getHomeRoomPeriod(userRegistration.getSchool(),gradeId); 
			hrc.setHomeroomId(homeRoomId);
			hrc.setSection(sec);
			hrc.setPeriods(period);
			hrc.setTeacher(teach);
			status=teacherService.checkHomeRoomTeacher(hrc);
		}catch(DataException e){
			logger.error("Error in checkHomeRoomTeacher() of TeacherSchedulerController"+ e);
		}
		model.addAttribute("status",status);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping("showScheduleByTeacher")
	public  ModelAndView showScheduleByTeacher(
			@RequestParam("teacherId") long teacherId,
			HttpSession session,Model model) {
		List<ClassActualSchedule> casLt = null;
		List<SchoolDays> schoolDaysLt = null;
		try{
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			if(userRegistration != null){
				schoolDaysLt = adminService.getSchoolDays(userRegistration.getSchool());
				casLt = teacherViewClassService.getMyClassesTimeTable(teacherId);
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
			model.addAttribute("showBy", "teacher");
			model.addAttribute("casLt", casLt);
			model.addAttribute("schoolDaysLt", schoolDaysLt);
		}catch(DataException e){
			logger.error("Error in showScheduleByTeacher() of TeacherSchedulerController "+ e);
		}
		return new ModelAndView("Ajax/Admin/show_schedule");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("showScheduleByGrade")
	public  ModelAndView showScheduleByGrade(
			@RequestParam("gradeId") long gradeId,
			HttpSession session,Model model) {
		List<Periods> periodLt = new ArrayList<Periods>();
		Map<String,Map <String,String>> hrmMap = new HashMap<String,Map <String,String>>();
		Map<String,String> csMap = new HashMap<String,String>();
		Map [] casMap = null;
		try{
			UserRegistration user = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			periodLt = adminService.getGradePeriods(gradeId);
			List<Teacher> teachersList = teacherService.getTeachersByGradeId(gradeId,user.getSchool().getSchoolId());
			for (Periods period : periodLt) {
				String stime = period.getStartTime();
				String etime =  period.getEndTime();
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
				period.setStartTime(stime);
				period.setEndTime(etime);
				List <Long> csIds  = new ArrayList<Long>();
				List <Long> teacherIds  = new ArrayList<Long>();
				for (Teacher teacher : teachersList) {
					int clsCnt =0;
					long teacherRegId = teacher.getUserRegistration().getRegId();
					if(period.getPeriodName().equalsIgnoreCase(WebKeys.LP_CLASS_HOMEROME)){  
						Map<String,String> hrm = new LinkedHashMap<String,String>();
						HomeroomClasses homeroom =  commonService.getHomeRoomByTeacher(teacherRegId, period.getPeriodId());
						if(homeroom.getSection() != null){
							if(homeroom.getSection().getSectionId() > 0){
								hrm.put("section",homeroom.getSection().getSection());
								hrm.put("button","<a href='#' onClick=\"getStudentList("+homeroom.getSection().getSectionId()+",\'class\')\" class='subButtons subButtonsWhite medium' style='text-decoration: none;padding:.1em 1em .2em;'>Class Roster</a>");
								hrmMap.put(user.getRegId()+""+period.getPeriodId(), hrm);
							}
						}
					}else{
						casMap = new LinkedHashMap [teachersList.size()];
						casMap[clsCnt] = new LinkedHashMap<String,Map <String,String>>();
						List <ClassActualSchedule> casLt = commonService.getClassSchedule(teacherRegId,period.getPeriodId());
						String str="<table width='100%'><tr>";
						for(ClassActualSchedule clsActualSch:casLt){
							if(clsActualSch.getPeriods().getPeriodId() == period.getPeriodId() && clsActualSch.getClassStatus().getTeacher().getUserRegistration().getRegId() == teacherRegId){
								if(!csIds.contains(clsActualSch.getClassStatus().getCsId())){
									String daysStr="";
									if(!teacherIds.contains(clsActualSch.getClassStatus().getTeacher().getTeacherId())){
										str+="<td align='center' colspan="+casLt.size()+" style='color:#002064;text-transform: uppercase;'><b>"+clsActualSch.getClassStatus().getSection().getGradeClasses().getStudentClass().getClassName()+"</b></td></tr><tr>";
										teacherIds.add(clsActualSch.getClassStatus().getTeacher().getTeacherId());
									}
									str+="<td><table width='100%'>";
									csIds.add(clsActualSch.getClassStatus().getCsId());
									List<Long> daysLt = commonService.getDayIdsByCsId(clsActualSch.getClassStatus().getCsId(), period.getPeriodId());
									for(Long day: daysLt){         
										Days days = appAdminService.getDay(day);
										daysStr += "<span class='"+days.getDay()+"' >"+days.getDay()+"</span> ";
									}
									
									str+="<tr><td align='center' style='font-weight:bold;'>"+clsActualSch.getClassStatus().getSection().getSection()+"</td></tr>";
									str+="<tr><td align='center' style='padding-bottom: 8px;'>"+daysStr+"</td></tr>";
									str+="<tr><td align='center'>"+"<a href='#' onClick=\"getStudentList("+clsActualSch.getClassStatus().getCsId()+",\'class\')\" class='subButtons subButtonsWhite medium' style='text-decoration: none;padding:.1em 1em .2em;font-size:12px;font-weight:bold;color:#002064;'>Class Roster</a>"+"</td></tr>";
									str+="</table></td>";
								}
							}
						}
						str+="</tr></table>";
						csMap.put(teacherRegId+""+period.getPeriodId(),str);
						str="";
						clsCnt++;
				}
			 }
			}
			model.addAttribute("hrmMap", hrmMap);
			model.addAttribute("csMap", csMap);
			model.addAttribute("showBy", "grade");
			model.addAttribute("periodLt", periodLt);
			model.addAttribute("teachersList", teachersList);
		}catch(DataException e){
			logger.error("Error in showScheduleByGrade() of TeacherSchedulerController "+ e);
		}
		return new ModelAndView("Ajax/Admin/show_schedule");
	}
}
