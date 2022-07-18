package com.lp.student.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
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

import com.lp.admin.service.AdminService;
import com.lp.common.service.DashboardService;
import com.lp.model.ClassActualSchedule;
import com.lp.model.SchoolDays;
import com.lp.model.Student;
import com.lp.model.UserRegistration;
import com.lp.student.service.StudentService;
import com.lp.teacher.service.TeacherViewClassService;
import com.lp.utils.WebKeys;

@Controller
public class RegisterForClassController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherViewClassService teacherViewClassService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private DashboardService dashboardService;
	
	static final Logger logger = Logger.getLogger(RegisterForClassController.class);


	@RequestMapping("/registerForClass")
	public ModelAndView registerForClass(HttpSession session, Model model) {
		List<SchoolDays> schoolDaysLt = null;
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		try{
			model.addAttribute("schoolDaysLt", schoolDaysLt);
			model.addAttribute("schoolName", student.getUserRegistration().getSchool().getSchoolName());
			model.addAttribute("gradeId", student.getGrade().getGradeId());
			model.addAttribute("divId", "header");
		}catch(Exception e){
			logger.error("Error in registerForClass() of RegisterForClassController "+ e.getStackTrace());
		}
		return new ModelAndView("Student/register_for_class");
	}

	@RequestMapping(value = "/getDetailsOfSections", method = RequestMethod.GET)
	public ModelAndView getDetailsOfSections(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("teacherId") long teacherId,
			@RequestParam("sectionId") long sectionId, HttpSession session,
			Model model) {
		UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.LP_USER_REGISTRATION);
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		List<ClassActualSchedule> casLt = Collections.emptyList();
		List<SchoolDays> schoolDaysLt = Collections.emptyList();
		long csId = 0;
		long gradeLevelId = 0;
		long gradeClassId = 0;
		try{
			if (userRegistration != null) {
				schoolDaysLt = adminService.getSchoolDays(userRegistration.getSchool());
				casLt = teacherViewClassService.getMyClassesTimeTable(teacherId);
			}
			for (ClassActualSchedule cas : casLt) {
				String stime = cas.getPeriods().getStartTime();
				String etime = cas.getPeriods().getEndTime();
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
				if (cas.getClassStatus().getSection().getSectionId() == sectionId) {
					csId = cas.getClassStatus().getCsId();
					gradeLevelId = cas.getClassStatus().getSection().getGradeLevel().getGradeLevelId();
					gradeClassId = cas.getClassStatus().getSection().getGradeClasses().getGradeClassId();
				}
			}
			model.addAttribute("casLt", casLt);
			model.addAttribute("schoolDaysLt", schoolDaysLt);
			model.addAttribute("divId", "details");
			model.addAttribute("gradeId", gradeId);
			model.addAttribute("classId", classId);
			model.addAttribute("teacherId", teacherId);
			model.addAttribute("sectionId", sectionId);
			model.addAttribute("csId", csId);
			model.addAttribute("gradeLevelId", gradeLevelId);
			model.addAttribute("gradeClassId", gradeClassId);
			model.addAttribute("studentId", student.getStudentId());
			model.addAttribute("schoolName", student.getUserRegistration().getSchool().getSchoolName());
		}catch(Exception e){
			logger.error("Error in getDetailsOfSections() of RegisterForClassController "+ e.getStackTrace());
		}
		return new ModelAndView("Student/register_for_class");
	}

	@RequestMapping("/setStatusForClassRegistration")
	public @ResponseBody
	void setStatusForClassRegistration(HttpSession session,
			HttpServletResponse response,
			@RequestParam("studentId") long studentId,
			@RequestParam("sectionId") long sectionId,
			@RequestParam("csId") long csId,
			@RequestParam("status") String status,
			@RequestParam("classStatus") String classStatus,
			@RequestParam("gradeLevelId") long gradeLevelId,
			@RequestParam("gradeClassId") long gradeClassId) {
		try {
			String sucess = studentService.setStatusForClassRegistration(studentId, sectionId, csId, status, classStatus, gradeLevelId, gradeClassId);
			response.getWriter().write(sucess);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
		} catch (Exception e) {
			logger.error("Error in setStatusForClassRegistration() of RegisterForClassController "+ e.getStackTrace());
		}
	}

	@RequestMapping(value = "/getClassDetails", method = RequestMethod.GET)
	public ModelAndView getClassDetails(@RequestParam("csId") long csId,
			@RequestParam("teacherId") long teacherId, HttpSession session,
			Model model) {
		UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.LP_USER_REGISTRATION);
		List<ClassActualSchedule> casLt = Collections.emptyList();
		List<SchoolDays> schoolDaysLt = Collections.emptyList();
		try{
			if (userRegistration != null) {
				schoolDaysLt = adminService.getSchoolDays(userRegistration.getSchool());
				casLt = teacherViewClassService.getMyClassesTimeTable(teacherId, csId);
			}
			for (ClassActualSchedule cas : casLt) {
				String stime = cas.getPeriods().getStartTime();
				String etime = cas.getPeriods().getEndTime();
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
			model.addAttribute("csId", csId);
		}
		catch (Exception e) {
			logger.error("Error in getClassDetails() of RegisterForClassController "+ e.getStackTrace());
		}
		return new ModelAndView("Ajax/CommonJsp/available_class_details");
	}

	@RequestMapping("/sendClassRequest")
	public @ResponseBody
	void setStatusForClassRegistration(HttpSession session,
			HttpServletResponse response,
			@RequestParam("studentId") long studentId,
			@RequestParam("csId") long csId,
			@RequestParam("gradeClassId") long gradeClassId,
			@RequestParam("teacherId") long teacherId) {
		try {
			String sucess = studentService.sendClassRequest(studentId, csId, gradeClassId, teacherId);
			response.getWriter().write(sucess);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
		} catch (Exception e) {
			logger.error("Error in sendClassRequest() of RegisterForClassController "+ e.getStackTrace());
		}
	}
	@RequestMapping("/goToMyClasses")
	public ModelAndView myClasses(HttpSession session, Model model) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		try{
			model.addAttribute("classes", dashboardService.getStudentClasses(student));
		}catch(Exception e){
			logger.error("Error in myClasses() of RegisterForClassController "+ e.getStackTrace());
		}
		return new ModelAndView("Student/my_classes");
	}
}
