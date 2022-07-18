package com.lp.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.lp.admin.service.AdminService;
import com.lp.appadmin.dao.UserDAO;
import com.lp.appadmin.service.AppAdminService;
import com.lp.appadmin.service.AppAdminService2;
import com.lp.appadmin.service.AppAdminService3;
import com.lp.appadmin.service.AppAdminService4;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.login.service.ForgotPasswordService;
import com.lp.login.service.UserLoginService;
import com.lp.model.Address;
import com.lp.model.Days;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.Interest;
import com.lp.model.Legend;
import com.lp.model.LegendCriteria;
import com.lp.model.Minutes;
import com.lp.model.Periods;
import com.lp.model.RegisterForClass;
import com.lp.model.RegisterForClassId;
import com.lp.model.School;
import com.lp.model.SchoolDays;
import com.lp.model.Section;
import com.lp.model.Security;
import com.lp.model.SecurityQuestion;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.User;
import com.lp.model.UserInterests;
import com.lp.model.UserRegistration;
import com.lp.student.service.StudentService;
import com.lp.utils.WebKeys;

@Controller
public class AdminIconsController extends WebApplicationObjectSupport{

	static final Logger logger = Logger.getLogger(AdminIconsController.class);

	@Autowired
	private AdminService adminservice;
	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private CommonService reportService;
	@Autowired
	private AppAdminService3 appadminservice3;
	@Autowired
	private AppAdminService2 appAdminService2;
	@Autowired
	private ForgotPasswordService reportsService;
	@Autowired
	private UserLoginService userloginservice;
	@Autowired
	private SchoolAdminService schooladminservice;
	@Autowired
	private StudentService studentservice;
	@Autowired
	private UserDAO userdao;
	@Autowired
	private AppAdminService4 appadminservice4;
	@Autowired
	private AdminService adminService;
	@Autowired
	private SchoolAdminService schoolAdminService;	
	@Autowired
	private CurriculumService curriculumService;

	@RequestMapping(value = "/AdminAddClasses", method = RequestMethod.GET)
	public ModelAndView adminAddClasses(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
				.getSchool().getSchoolId());
		ModelAndView model = new ModelAndView("Admin/AdminAddClasses",
				"gradeClasses", new GradeClasses());
		model.addObject("sgList", schoolgrades);
		return model;
	}

	@RequestMapping(value = "/GetClassses", method = RequestMethod.GET)
	public ModelAndView getClasses(@RequestParam("gradeId") long gradeId,
			Map<String, Object> map, HttpSession session,
			@ModelAttribute("GradeClasses") GradeClasses gradeclass,
			HttpServletResponse response, HttpServletRequest request) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<StudentClass> gradeclasses = adminservice.getAddedClasses(gradeId);
		ModelAndView model = new ModelAndView("Ajax/Admin/ShowClasses",
				"gradeclass", new GradeClasses());
		model.addObject("gclassList", gradeclasses);
		session.setAttribute("gradeId", gradeId);
		List<StudentClass> stuclass = adminservice.getStudentClass(userReg
				.getSchool());
		model.addObject("sList", stuclass);
		return model;

	}

	@RequestMapping(value = "/RemoveClassses", method = RequestMethod.GET)
	public @ResponseBody
	void removeClass(HttpSession session, HttpServletResponse response,
			@RequestParam("classId") long classId,
			@RequestParam("gradeId") long gradeId,
			@ModelAttribute("GradeClasses") GradeClasses gradeclass) {
		try {

			String helloAjax = "";
			int stat = adminservice.RemoveClass(gradeId, classId);
			if (stat > 0) {
				helloAjax = "Class Removed Successfully";
			} else {
				helloAjax = "Unable to remove the Class";
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/CreateSections", method = RequestMethod.GET)
	public ModelAndView createSections(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
				.getSchool().getSchoolId());
		ModelAndView model = new ModelAndView("Admin/EditSections",
				"gradeClasses", new GradeClasses());
		model.addObject("grList", schoolgrades);
		return model;
	}

	@RequestMapping(value = "/addSections", method = RequestMethod.GET)
	public @ResponseBody
	void saveSections(HttpSession session, HttpServletResponse response,
			@RequestParam("classId") long classId,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("gradeLevelId") long gradeLevelId,
			@RequestParam("sectionName") String sectionName,
			@ModelAttribute("section") Section section) {
		try {
			String helloAjax = "";
			long gradeclassId = reportService.getGradeClassId(gradeId, classId);
			boolean stat = false;
			long check = adminservice.checkExistSection(gradeclassId, gradeLevelId, sectionName);
			if (check == 0) {
				stat = adminservice.CreateSections(gradeclassId, sectionName, WebKeys.ACTIVE, gradeLevelId);
			}
			if (stat) {
				helloAjax = "Section Added Successfully";
			} else {
				helloAjax = "Section is already exists";
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/EditSections", method = RequestMethod.GET)
	public ModelAndView EditSections(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
				.getSchool().getSchoolId());
		ModelAndView model = new ModelAndView("Admin/EditSections",
				"gradeClasses", new GradeClasses());
		model.addObject("grList", schoolgrades);
		return model;
	}

	@RequestMapping(value = "/UpdateSections", method = RequestMethod.GET)
	public @ResponseBody
	void UpdateSections(HttpSession session, HttpServletResponse response,
			@RequestParam("sectionId") long sectionId,
			@RequestParam("sectionname") String sectioname,
			@ModelAttribute("section") Section section) {
		try {

			String helloAjax = "";
			int stat = 0;
			stat = adminservice.UpdateSections(sectionId, sectioname);
			if (stat > 0) {
				helloAjax = "Section Updated Successfully";
			} else {
				helloAjax = "Section Not Updated Successfully";
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/CheckSections", method = RequestMethod.GET)
	public @ResponseBody
	void CheckSection(HttpSession session,HttpServletResponse response, @RequestParam("sectionId") long sectionId,
			@ModelAttribute("section") Section section,
			HttpServletRequest request) throws Exception {
		int stat = 0;
		String status="0";
		stat = adminservice.checkSections(sectionId);
		if(stat == 1){
			status = "1";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(status);

	}
	@RequestMapping(value = "/DeleteSections", method = RequestMethod.GET)
	public ModelAndView DeleteSections(@RequestParam("classId") long classId,
			@RequestParam("sectionId") long sectionId,
			@RequestParam("sectionname") String sectionname,
			@RequestParam("gradeId") long gradeId, Map<String, Object> map,
			HttpSession session, @ModelAttribute("section") Section section,
			HttpServletResponse response, HttpServletRequest request) {
		@SuppressWarnings("unused")
		int stat = 0;
		stat = adminservice.DeleteSections(sectionId, sectionname);
		List<Section> sections = adminservice.getAllSections(gradeId, classId);
		ModelAndView model = new ModelAndView("Ajax/Admin/GetSection", "section",
				new Section());
		model.addObject("secList", sections);
		return model;

	}
	@RequestMapping(value = "/GetSections", method = RequestMethod.GET)
	public ModelAndView getSections(@RequestParam("classId") long classId,
			@RequestParam("gradeId") long gradeId, Map<String, Object> map,
			HttpSession session, @ModelAttribute("section") Section section,
			HttpServletResponse response, HttpServletRequest request) {
		List<Section> sections = adminservice.getAllSections(gradeId, classId);
		ModelAndView model = new ModelAndView("Ajax/Admin/GetSection", "section",
				new Section());
		model.addObject("secList", sections);
		return model;

	}

	@RequestMapping(value = "/ParentRegistration", method = RequestMethod.GET)
	public ModelAndView ParentRegistration(HttpSession session,
			Map<String, Object> map,
			@ModelAttribute("parentReg1") UserRegistration userReg1) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		map.put("countryIds", appAdminService2.getCountries());
		map.put("securityQuestions", appAdminService.getSecurityQuestions());
		long adminRegId = userReg.getRegId();
		School school = reportService.getSchoolIdByRegId(adminRegId);
		userReg1.setUser(userdao.getUserType("Parent"));
		long userTypeId = userReg1.getUser().getUserTypeid();
		List<UserRegistration> pemailids = adminservice.getAllParentEmailIds(
				school, userTypeId);
		List<SecurityQuestion> seclist = reportsService.getQuestionList();
		String ss = "All";
		session.setAttribute("ParentEmailId", ss);
		ModelAndView model = new ModelAndView("Parent/ParentRegistration1",
				"parentReg1", new UserRegistration());
		model.addObject("parentlist", pemailids);
		model.addObject("seclist", seclist);
		return model;
	}

	@RequestMapping(value = "/SaveParentDetails", method = RequestMethod.POST)
	public ModelAndView saveParentDetails(
			HttpSession session,
			HttpServletResponse response,
			@ModelAttribute("parentReg1") UserRegistration userReg,
			HttpServletRequest request) {
		ModelAndView model = null;
		try {
			
			 UserRegistration parentObj = reportService.getNewUserRegistration(userReg.getEmailId());	
			 userReg.setRegId(parentObj.getRegId());
			 userReg.setSchool(parentObj.getSchool());
			userReg.setUser(userdao.getUserType(WebKeys.LP_USER_TYPE_PARENT));
			userReg.setAddress(new Address());
			userReg.getAddress().setAddress(userReg.getAddress1() + "##@##" + userReg.getAddress2());
			userReg.getAddress().setCity(userReg.getCity());
			userReg.getAddress().setStates(appAdminService2.getState(Long.valueOf(userReg.getStateId())));
			userReg.getAddress().setZipcode(Integer.valueOf(userReg.getZipcode()));
			userloginservice.saveAddress(userReg.getAddress());
			userReg.setStatus(WebKeys.ACTIVE);
			String md5Password = userloginservice.getMD5Conversion(userReg.getPassword());
			userReg.setPassword(md5Password);
			schooladminservice.UpdateUserRegistration(userReg);
			long statusFlag = 0;
			boolean status = false;
			statusFlag = adminservice.checkSecurityExists(userReg);
			SecurityQuestion secQuestion = new SecurityQuestion();
			secQuestion.setSecurityQuestionId(Long.valueOf(userReg.getSecurityQuestionId()));
			if (statusFlag > 0) {
				Security sec = userloginservice.getSecurity(userReg.getEmailId());
				sec.setStatus(WebKeys.LP_STATUS_INACTIVE);
				
				sec.setSecurityQuestion(secQuestion);
				sec.setAnswer(userReg.getAnswer());
				status = userloginservice.saveSecurity(sec);
			} else {
				Security security1 = new Security();
				security1.setUserRegistration(userReg);
				security1.setVerificationCode("adminadmin");
				security1.setSecurityQuestion(secQuestion);
				security1.setStatus(WebKeys.LP_STATUS_INACTIVE);
				security1.setAnswer(userReg.getAnswer());
				status = userloginservice.saveSecurity(security1);
			}
			session.setAttribute("ParentRegis", userReg);
			if (status) {
				model = new ModelAndView("login/RegistrationSuccess");
				model.addObject("springUser", userReg.getEmailId());
				return model;
			} else {
				model = new ModelAndView("redirect:/Fail.htm");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}	

	@RequestMapping(value = "/SaveStudentDetails1", method = RequestMethod.POST)
	public ModelAndView
	saveStudentDetails1(HttpSession session, HttpServletResponse response,
			@ModelAttribute("studentUserReg") UserRegistration userReg1) {
		List<GradeClasses> classList = null;
		try {
						
			UserRegistration userRegistration = schoolAdminService.getUserReg(userReg1.getEmailId());
			userReg1.setEmailId(userRegistration.getEmailId());
			session.setAttribute("schoolId", userRegistration.getSchool().getSchoolId());
			session.setAttribute("gradeId", userReg1.getGradeId());
			userReg1.setRegId(userRegistration.getRegId());
			userReg1.setSchool(userRegistration.getSchool());			
			String md5Password = userloginservice.getMD5Conversion(userReg1
					.getPassword());
			userReg1.setPassword(md5Password);
			
			userReg1.setAddress(new Address());
			userReg1.getAddress().setAddress(userReg1.getAddress1() + "##@##" + userReg1.getAddress2());
			userReg1.getAddress().setCity(userReg1.getCity());
			userReg1.getAddress().setStates(appAdminService2.getState(Long.valueOf(userReg1.getStateId())));
			userReg1.getAddress().setZipcode(Integer.valueOf(userReg1.getZipcode()));
			userReg1.setUser(userRegistration.getUser());
			classList = adminservice.getGradeClasses(userRegistration.getSchool().getSchoolId(),
					Long.parseLong(userReg1.getGradeId()));
			long statusFlag = 0;
			statusFlag = adminservice.checkSecurityExists(userReg1);
			Security sec = new Security();
			if (statusFlag > 0) {
				sec = userloginservice.getSecurityForVerification(userReg1.getEmailId());
				sec.setStatus(WebKeys.LP_STATUS_INACTIVE);
				sec.setSecurityQuestion(appAdminService.getSecurityQuestion(Long.parseLong(userReg1.getSecurityQuestionId())));
				sec.setAnswer(userReg1.getAnswer());
				session.setAttribute("security", sec);
			} 
			sec.setUserRegistration(userReg1);
			sec.setVerificationCode("adminadmin");
			sec.setSecurityQuestion(appAdminService.getSecurityQuestion(Long.parseLong(userReg1.getSecurityQuestionId())));
			sec.setStatus(WebKeys.LP_STATUS_INACTIVE);
			sec.getSecurityQuestion().setSecurityQuestionId(Long.parseLong(userReg1.getSecurityQuestionId()));
			sec.setAnswer(userReg1.getAnswer());
			session.setAttribute("security", sec);
			session.setAttribute("userRegiss", userReg1);
			session.setAttribute("UserType", userRegistration.getUser().getUserType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView model = new ModelAndView("Student/StudentRegistration2");		
		model.addObject("classList", classList);
		return model;	
	}

	@RequestMapping(value = "/GoStudentRegistration2", method = RequestMethod.GET)
	public ModelAndView StudentRegistration2(HttpSession session) {

		ModelAndView model = new ModelAndView("Student/StudentRegistration2");
		long gradeId = Long.parseLong(session.getAttribute("gradeId").toString());
		long schoolId = Long.parseLong(session.getAttribute("schoolId").toString());
		List<GradeClasses> classList = adminservice.getGradeClasses(schoolId,
				gradeId);
		model.addObject("classList", classList);
		return model;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	@RequestMapping(value = "/SaveStudentDetails2", method = RequestMethod.POST)
	public ModelAndView
	SaveStudentDetails2(HttpSession session, HttpServletResponse response, HttpServletRequest request) {

		try {
			String[] classId = request.getParameterValues("classId");
			String gender = request.getParameter("gender") ;
			int date = Integer.parseInt(request.getParameter("date")) ;
			int month=  Integer.parseInt(request.getParameter("month")) ;
			int year= Integer.parseInt(request.getParameter("year")) ;
			String other1 = request.getParameter("other1") ;
			String other2 = request.getParameter("other2") ;
			 String other3 = request.getParameter("other3");
			 String other4 = request.getParameter("other4") ;
			
			String[] classnames;
			Student student = new Student();
			UserRegistration userRegis1 = (UserRegistration) session
					.getAttribute("userRegiss");
			student.setUserRegistration(userRegis1);
			student.setGender(gender);
			String dob = year + "-" + month + "-" + date;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date utilDate = new java.util.Date(year - 1900,
						month - 1, date);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				student.setDob(sqlDate);
				long gradeId = Long.parseLong(session.getAttribute("gradeId").toString());
				Grade grade = new Grade();
				grade.setGradeId(gradeId);
				student.setGrade(grade);

			} catch (Exception e) {
				e.printStackTrace();
			}

			classnames = new String[classId.length];
			for (int i = 0; i < classId.length; i++) {
				String classname = reportService.getClassName(Long.parseLong(classId[i]));
				classnames[i] = classname;
			}
			session.setAttribute("other1", other1);
			session.setAttribute("other2", other2);
			session.setAttribute("other3", other3);
			session.setAttribute("other4", other4);
			session.setAttribute("classIds", classId);
			session.setAttribute("classNames", classnames);
			session.setAttribute("students", student);
			
		} catch (Exception e) {
			e.printStackTrace();
		}return new ModelAndView("Student/StudentRegistration3");
	}

	@RequestMapping(value = "/GoStudentRegistration3", method = RequestMethod.GET)
	public ModelAndView StudentRegistration3(HttpSession session) {

		ModelAndView model = new ModelAndView("Student/StudentRegistration3");
		return model;
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@RequestMapping(value = "/SaveStudentDetails3", method = RequestMethod.POST)
	public ModelAndView SaveStudentDetails3(HttpSession session, HttpServletResponse response,
			HttpServletRequest request) {
		String[] classId = request.getParameterValues("classIds");
		String[] gradelevels = request.getParameterValues("gradeLevels"); 
		Student student = (Student) session.getAttribute("students");
		UserRegistration userRe = (UserRegistration) session.getAttribute("userRegiss");
		userRe.setStatus(WebKeys.ACTIVE);
		student.setUserRegistration(userRe);
		Security security = (Security) session.getAttribute("security");
		String helloAjax = "";
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();	
		ModelAndView model = null;
		
		try {
			String textother = "", textother2 = "", textother3 = "", textother4 = "";
			userloginservice.saveAddress(userRe.getAddress());
			schooladminservice.UpdateUserRegistration(userRe);
			boolean status = userloginservice.saveSecurity(security);
			student.setGradeStatus(WebKeys.ACTIVE);
			studentservice.SaveStudent(student);
			boolean stat = false, stat1 = false;
			textother = (String) session.getAttribute("other1");
			textother2 = (String) session.getAttribute("other2");
			textother3 = (String) session.getAttribute("other3");
			textother4 = (String) session.getAttribute("other4");

			Hashtable text_others = new Hashtable();
			if (textother != null && textother.equalsIgnoreCase("") == false) {
				text_others.put("+textother+", textother);
			}
			if (textother2 != null &&  textother2.equalsIgnoreCase("") == false) {
				text_others.put("+textother2+", textother2);
			}
			if (textother3 != null &&  textother3.equalsIgnoreCase("") == false) {
				text_others.put("+textother3+", textother3);
			}
			if (textother4 != null &&  textother4.equalsIgnoreCase("") == false) {
				text_others.put("+textother4+", textother4);
			}
			String code = "current subject areas";
			Interest inter = appadminservice4.getInterest(code);
			long interestId = inter.getInterestId();
			Enumeration enu_others = null;

			java.util.Iterator itr = null;
			if (text_others != null) {
				enu_others = text_others.elements();
			}
			while (enu_others.hasMoreElements()) {
				String val;
				UserInterests userInt = new UserInterests();
				userInt.setUserRegistration(userRe);
				userInt.setInterest(inter);
				val = (String) enu_others.nextElement();
				userInt.setOtherUserInterest(val);
				stat1 = studentservice.SaveUserInterests(userInt);
			}

			for (int i = 0; i < classId.length; i++) {
				RegisterForClass regisclass = new RegisterForClass();
				RegisterForClassId regisclassId = new RegisterForClassId();
				regisclassId.setStudentId(student.getStudentId());
				long gradeId = student.getGrade().getGradeId();
				long grade_class_id = reportService.getGradeClassId(gradeId,
						Long.parseLong(classId[i]));
				regisclassId.setGradeClassId(grade_class_id);
				regisclass.setId(regisclassId);
				regisclass.setGradeLevel(appadminservice3
						.getGradeLevel(Long.parseLong(gradelevels[i])));
				regisclass.setStudent(student);
				regisclass.setGradeClasses(adminservice
						.getGradeClass(grade_class_id));
				regisclass.setClassStatus_1(WebKeys.ALIVE);
				regisclass.setStatus(WebKeys.LP_STATUS_NEW);
				stat = adminservice.saveRegisterForclass(regisclass);

			}
			if (stat == true) { 
				model = new ModelAndView("login/RegistrationSuccess");
				model.addObject("springUser", userName);
			} else {
				model = new ModelAndView("login/Fail");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return model;
	}

	@RequestMapping(value = "/goToTeacherRegistration", method = RequestMethod.GET)
	public ModelAndView goToTeacherRegistration(HttpSession session,
			Map<String, Object> map) {
		UserRegistration adminReg = null;
		ModelAndView model = new ModelAndView("Teacher/TeacherRegistration1", "teacherReg", new UserRegistration());
		adminReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		model.addObject("teacherMails", userloginservice.getUserNotRegistered("teacher", adminReg.getSchool().getSchoolId()));
		map.put("countryIds", appAdminService2.getCountries());
		map.put("securityQuestions", appAdminService.getSecurityQuestions());
		return model;
	}

	@RequestMapping(value = "/StudentRegistration", method = RequestMethod.GET)
	public ModelAndView StudentRegistration(HttpSession session,
			Map<String, Object> map,
			@ModelAttribute("StudentReg1") UserRegistration userReg1) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		map.put("countryIds", appAdminService2.getCountries());
		map.put("securityQuestions", appAdminService.getSecurityQuestions());
		long adminRegId = userReg.getRegId();
		School school = reportService.getSchoolIdByRegId(adminRegId);
		userReg1.setUser(userdao.getUserType("Student Above 13"));
		User us = userdao.getUserType("Student Below 13");
		long usertypeid = us.getUserTypeid();
		long userTypeId = userReg1.getUser().getUserTypeid();
		List<UserRegistration> studentemailids = adminservice
				.getAllStudentEmailIds(school, userTypeId, usertypeid);
		List<SecurityQuestion> seclist = reportsService.getQuestionList();
		String ss = "All";
		String userTy = "Admin";
		session.setAttribute("UserType", userTy);
		session.setAttribute("StudentEmailId", ss);
		ModelAndView model = new ModelAndView("Student/StudentRegistration1",
				"studentUserReg", new UserRegistration());
		model.addObject("studentlist", studentemailids);
		model.addObject("seclist", seclist);
		List<Grade> schoolgrades = adminservice.getSchoolGrades(school
				.getSchoolId());
		model.addObject("schoolgrades", schoolgrades);
		model.addObject(WebKeys.USER_REGISTRATION_OBJ, userReg);
		return model;
	}

	@RequestMapping(value = "/AddSchoolDays", method = RequestMethod.GET)
	public ModelAndView getdata(HttpSession session) {
		List<Days> days = appAdminService.getDays();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long adminRegId = userReg.getRegId();
		School school = reportService.getSchoolIdByRegId(adminRegId);
		List<SchoolDays> schooldays = adminservice.getSchoolDays(school);
		ModelAndView model = new ModelAndView("Admin/AddSchoolDays",
				"SchoolDays", new SchoolDays());
		model.addObject("days", days);
		model.addObject("schooldays", schooldays);
		return model;
	}

	@RequestMapping(value = "/AdminAddDays", method = RequestMethod.GET)
	public @ResponseBody
	void AdminAddGrade(HttpServletResponse response, HttpSession session,
			@ModelAttribute("SchoolDays") SchoolDays schooldays,
			BindingResult result, @RequestParam("dayId") long DayId)
			throws Exception {
		String helloAjax = "";
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long adminRegId = userReg.getRegId();
		School school = reportService.getSchoolIdByRegId(adminRegId);
		schooldays.setSchool(school);

		appAdminService.getDay(DayId);
		schooldays.setDays(appAdminService.getDay(DayId));
		schooldays.setStatus(WebKeys.ACTIVE);
		int s = adminservice.AddSchoolDays(schooldays);
		if (s == 0)
			helloAjax = "Not Added";
		else
			helloAjax = "Added";

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);

	}

	@RequestMapping(value = "/AdminRemoveDays", method = RequestMethod.GET)
	public @ResponseBody
	void AdminRemoveGrade(HttpServletResponse response, HttpSession session,
			@ModelAttribute("SchoolDays") SchoolDays schooldays,
			BindingResult result, @RequestParam("DayId") long DayId)
			throws Exception {
		String helloAjax = "";
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long adminRegId = userReg.getRegId();
		String status = "Inactive";

		School school = reportService.getSchoolIdByRegId(adminRegId);
		schooldays.setDays(appAdminService.getDay(DayId));
		schooldays.setSchool(school);
		schooldays.setStatus(status);
		adminservice.RemoveDays(schooldays);
		helloAjax = "Removed";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);

	}
	@RequestMapping(value = "/SetPeriods", method = RequestMethod.GET)
	public ModelAndView getGradeMinutes(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
				.getSchool().getSchoolId());
		ModelAndView model = new ModelAndView("Admin/AdminSetPeriods",
				"Periods", new Periods());
		model.addObject("sgList", schoolgrades);
		List<Minutes> mins = appAdminService.getMinutes();
		model.addObject("mins", mins);
		return model;
	}

	@RequestMapping(value = "/AdminAddPeriod", method = RequestMethod.GET)
	public @ResponseBody
	void fetchFlowDowns(HttpServletResponse response, HttpSession session,
			@ModelAttribute("Periods") Periods period, BindingResult bindingResult,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("period") String periodname,
			@RequestParam("starttime") String starttime,
			@RequestParam("starttimemin") String starttimemin,
			@RequestParam("starttimemeridian") String starttimemeridian,
			@RequestParam("endtime") String endtime,
			@RequestParam("endtimemin") String endtimemin,
			@RequestParam("endtimemeridian") String endtimemeridian)
			throws Exception {
		String stime = "";
		String etime = "";
		String result = "";
		starttime = starttime.trim();
		starttimemin = starttimemin.trim();
		starttimemeridian = starttimemeridian.trim();
		endtime = endtime.trim();
		endtimemin = endtimemin.trim();
		endtimemeridian = endtimemeridian.trim();
		try {
			if (starttimemeridian.equals("PM") && (!starttime.equals("12"))) {
				int start = Integer.parseInt(starttime) + new Integer(12);

				stime = start + ":" + starttimemin + ":" + "00";
			} else if (starttimemeridian.equals("PM")
					&& (starttime.equals("12"))) {
				int start = new Integer(12);

				stime = start + ":" + starttimemin + ":" + "00";
			} else {

				stime = starttime + ":" + starttimemin + ":" + "00";
			}

			if (endtimemeridian.equals("PM") && (!endtime.equals("12"))) {
				int end = Integer.parseInt(endtime) + new Integer(12);
				etime = end + ":" + endtimemin + ":" + "00";
			} else if (endtimemeridian.equals("PM") && (endtime.equals("12"))) {
				int end = new Integer(12);

				etime = end + ":" + endtimemin + ":" + "00";
			} else {
				etime = endtime + ":" + endtimemin + ":" + "00";
			}
			period.setPeriodName(periodname);
			period.setStartTime(stime);
			period.setEndTime(etime);
			period.setGrade(adminservice.getGrade(gradeId));
			String status = "";
			status = adminservice.SavePeriod(period);
			if (status.equalsIgnoreCase("Success"))
				result = "Saved Successfully !!";
			else if (status.equalsIgnoreCase("PeriodName"))
				result = "Overlapping with existed Period Name";
			else if (status.equalsIgnoreCase("PeriodDates"))
				result = "Overlapping with existed Period Timings";
			else if (status.equalsIgnoreCase("Error"))
				result = "Period not Saved..Error occured";

		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(result);

	}
	@RequestMapping(value = "/GetPeriods", method = RequestMethod.GET)
	public ModelAndView getPeriods(@RequestParam("gradeId") long gradeId,
			Map<String, Object> map, HttpSession session,
			@ModelAttribute("Periods") Periods periods,
			HttpServletResponse response, HttpServletRequest request) {
		List<Periods> period = adminservice.getGradePeriods(gradeId);
		ModelAndView model = new ModelAndView("Ajax/Admin/GetPeriods", "Periods",
				new Periods());
		model.addObject("PeriodList", period);
		session.setAttribute("gradeId", gradeId);
		List<Minutes> mins = appAdminService.getMinutes();
		model.addObject("mins", mins);
		return model;

	}

	@RequestMapping(value = "/UpdatePeriods", method = RequestMethod.GET)
	public @ResponseBody
	void EditPeriods(HttpServletResponse response, HttpSession session,
			@ModelAttribute("Periods") Periods period, BindingResult bingingResult,
			@RequestParam("periodId") long periodId,
			@RequestParam("period") String periodname,
			@RequestParam("starttime") String starttime,
			@RequestParam("starttimemin") String starttimemin,
			@RequestParam("starttimemeridian") String starttimemeridian,
			@RequestParam("endtime") String endtime,
			@RequestParam("endtimemin") String endtimemin,
			@RequestParam("endtimemeridian") String endtimemeridian)
			throws Exception {
		String stime = "";
		String etime = "";
		String result = "";
		starttime = starttime.trim();
		starttimemin = starttimemin.trim();
		starttimemeridian = starttimemeridian.trim();
		endtime = endtime.trim();
		endtimemin = endtimemin.trim();
		endtimemeridian = endtimemeridian.trim();
		try {
			if (starttimemeridian.equals("PM") && (!starttime.equals("12"))) {
				int start = Integer.parseInt(starttime) + new Integer(12);

				stime = start + ":" + starttimemin + ":" + "00";
			} else if (starttimemeridian.equals("PM")
					&& (starttime.equals("12"))) {
				int start = new Integer(12);

				stime = start + ":" + starttimemin + ":" + "00";
			} else {

				stime = starttime + ":" + starttimemin + ":" + "00";
			}

			if (endtimemeridian.equals("PM") && (!endtime.equals("12"))) {
				int end = Integer.parseInt(endtime) + new Integer(12);
				etime = end + ":" + endtimemin + ":" + "00";
			} else if (endtimemeridian.equals("PM") && (endtime.equals("12"))) {
				int end = new Integer(12);

				etime = end + ":" + endtimemin + ":" + "00";
			} else {
				etime = endtime + ":" + endtimemin + ":" + "00";
			}

			long gradeId = (Long) session.getAttribute("gradeId");

			String status = "";
			status = adminservice.UpdatePeriods(periodId, periodname, stime,etime, gradeId);
			if (status.equalsIgnoreCase("Success"))
				result = "Updated Successfully !!";
			else if (status.equalsIgnoreCase("PeriodName"))
				result = "Overlapping with existed Period Name";
			else if (status.equalsIgnoreCase("PeriodDates"))
				result = "Overlapping with existed Period Timings";
			else if (status.equalsIgnoreCase("Error"))
				result = "Period not updated..Error occured";

		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(result);

	}

	@RequestMapping(value = "/CheckPeriods", method = RequestMethod.GET)
	public @ResponseBody
	void CheckPeriod(HttpServletResponse response, HttpSession session,
			@ModelAttribute("Periods") Periods periods, BindingResult result,
			@RequestParam("periodId") long periodId) throws Exception {
		String status = "0";
		int helloAjax = adminservice.checkPeriod(periodId);
		if(helloAjax == 1){
			status = "1";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(status);

	}

	@RequestMapping(value = "/DeletePeriods", method = RequestMethod.GET)
	public ModelAndView DeletePeriod(@RequestParam("periodId") long periodId,
			Map<String, Object> map, HttpSession session,
			@ModelAttribute("Periods") Periods periods,
			HttpServletResponse response, HttpServletRequest request) {
		long gradeId = (Long) session.getAttribute("gradeId");
		int stat = adminservice.deletePeriod(periodId);
		logger.info("status of deletePeriod in adminIconsController"+stat);
		List<Periods> period = adminservice.getGradePeriods(gradeId);
		ModelAndView model = new ModelAndView("Ajax/Admin/GetPeriods", "Periods",
				new Periods());
		model.addObject("PeriodList", period);
		List<Minutes> mins = appAdminService.getMinutes();
		model.addObject("mins", mins);
		return model;

	}

	@RequestMapping(value = "/DeleteClassSchedulePeriods", method = RequestMethod.GET)
	public ModelAndView DeleteClassSchedulePeriods(
			@RequestParam("periodId") long periodId, Map<String, Object> map,
			HttpSession session, @ModelAttribute("Periods") Periods periods,
			HttpServletResponse response, HttpServletRequest request) {
		long gradeId = (Long) session.getAttribute("gradeId");
		int stat = adminservice.deleteClassSchedulePeriod(periodId);
		int st = adminservice.deletePeriod(periodId);
		logger.info("stat of deleteClassSchedulePeriod in adminIconsController"+stat);
		logger.info("st of deletePeriod in DeleteClassSchedulePeriods in adminIconsController"+st);
		List<Periods> period = adminservice.getGradePeriods(gradeId);
		ModelAndView model = new ModelAndView("Ajax/Admin/GetPeriods", "Periods",
				new Periods());
		model.addObject("PeriodList", period);
		List<Minutes> mins = appAdminService.getMinutes();
		model.addObject("mins", mins);
		return model;

	}

	@RequestMapping(value = "/AddSchoolInformation", method = RequestMethod.GET)
	public ModelAndView AddSchoolInformation(HttpSession session,
			Map<String, Object> map) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long adminRegId = userReg.getRegId();
		String startDate = "";
		String endDate = "";
		School school = reportService.getSchoolIdByRegId(adminRegId);
		map.put("countryIds", appAdminService2.getCountries());
		long countryId = appAdminService2.getCountryByState(Long.valueOf(school.getStates().getStateId()));
		map.put("countryId", countryId);
		map.put("stateIds", appAdminService2.getStates(countryId));				
		map.put("stateId", school.getStates().getStateId());
		map.put("schoolLevelId", school.getSchoolLevel().getSchoolLevelId());
		map.put("schoolTypeId", school.getSchoolType().getSchoolTypeId());
		map.put("schoolTypeIds", schooladminservice.getSchoolTypeList());
		map.put("schoolLevelIds", schooladminservice.getSchoolLevelList());
		map.put("districtIds", schooladminservice.getDistricts());
		map.put("districtId", school.getDistrict()!=null?school.getDistrict().getDistrictId():0);
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		if(school.getPromotStartDate() != null){
			startDate = formatter.format(school.getPromotStartDate());
		}
		if(school.getPromotEndDate() != null){
			endDate = formatter.format(school.getPromotEndDate());			
		}		
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		ModelAndView model = new ModelAndView("Admin/AdminSchoolInformation",
				"School", school);
		model.addObject("tab", WebKeys.LP_TAB_UPDATE_SCHOOL_INFO);
		model.addObject("schoolinfo", school);
		return model;
	}

	@RequestMapping(value = "/SaveSchoolInfo", method = RequestMethod.GET)
	public @ResponseBody
	void SaveSchoolInfo(HttpServletResponse response, HttpSession session,
			@ModelAttribute("School") School school, BindingResult result,
			@RequestParam("schoolName") String schoolName,
			@RequestParam("schoolAbbr") String schoolAbbr,
			@RequestParam("countryId") String countryId,
			@RequestParam("stateId") long stateId,
			@RequestParam("city") String city,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("noOfStudents") int noOfStudents,
			@RequestParam("faxNumber") String faxNumber,
			@RequestParam("schoolId") long schoolId,
			@RequestParam("schoolLevelId") long schoolLevelId,
			@RequestParam("schoolTypeId") long schoolTypeId,
			@RequestParam("promotStartDate") String promotStartDate,
			@RequestParam("promotEndDate") String promotEndDate,
			@RequestParam("districtId") long districtId) throws Exception {

		String helloAjax = "";
		Date startDate;
		Date endDate;
		try {			
			school = schooladminservice.getSchool(schoolId);
			school.setSchoolName(schoolName);
			school.setSchoolAbbr(schoolAbbr);
			school.setCountryId(countryId);
			school.setStates(appAdminService2.getState(stateId));
			school.setCity(city);
			school.setPhoneNumber(phoneNumber);
			school.setFaxNumber(faxNumber);
			school.setNoOfStudents(noOfStudents);
			school.setSchoolLevel(schooladminservice.getSchoolLevel(schoolLevelId));
			school.setSchoolType(schooladminservice.getSchoolType(schoolTypeId));
			
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			if(!promotStartDate.equalsIgnoreCase("")){
				startDate = formatter.parse(promotStartDate);
				school.setPromotStartDate(startDate);
			}
			if(!promotEndDate.equalsIgnoreCase("")){
				endDate = formatter.parse(promotEndDate);
				school.setPromotEndDate(endDate);
			}
			if(districtId>0){
				school.setDistrict(schooladminservice.getDistrict(districtId));
			}			
			schooladminservice.updateSchool(school);

			helloAjax = "School Information Updated Successfully";
		} catch (Exception e) {
			helloAjax = "School Information Not Updated Successfully";
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);

	}
	@RequestMapping(value = "/AddStudentsToClass", method = RequestMethod.GET)
	public ModelAndView addStudentsToClass(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
		ModelAndView model = new ModelAndView("Admin/addStudentsToClass", "gradeClasses", new GradeClasses());
		model.addObject("grList", schoolgrades);
			
		return  model;
	}
	
	@RequestMapping(value = "/getClassesWithOutHomeRoom", method = RequestMethod.GET)
	public View getClassesWithOutHomeRoom(@RequestParam("gradeId") long gradeId, Model model) throws DataException{
		List<StudentClass> classes = Collections.emptyList();
		try{
			classes =  adminservice.getClassesWithOutHomeRoom(gradeId);
			model.addAttribute("classes",classes);			
		}catch(DataException e){
			logger.error("Error in getClassesWithOutHomeRoom() of AdminIconsController"	+ e);
		}		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getStudentSchoolGrades", method = RequestMethod.GET)
	public View getAssignmentsTitles(HttpSession session,
			@RequestParam("stdRegId") long stdRegId,
			Model model) {
		UserRegistration userReg = schoolAdminService.getUserRegistration(stdRegId);
		List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
		model.addAttribute("schoolgrades",schoolgrades);
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/goToCreateLIRubric", method = RequestMethod.GET)
	public ModelAndView goToCreateLIRubric(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/create_le_rubric","legend",new Legend());
		List<LegendCriteria> legendCriteriaLst=adminservice.getAllLegendCriteriaList();
		model.addObject("legendCriteriaLst",legendCriteriaLst);
		model.addObject("tab", WebKeys.LP_TAB_CREATE_LE_RUBRIC);
		return model; 
	}
	@RequestMapping(value = "/goToAssignLIRubric", method = RequestMethod.GET)
	public ModelAndView goToAssignLIRubric(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/assign_le_rubric","legend",new Legend());
		List<LegendCriteria> legendCriteriaLst=adminservice.getAllLegendCriteriaList();
		model.addObject("legendCriteriaLst",legendCriteriaLst);
		model.addObject("tab", WebKeys.LP_TAB_ASSIGN_LE_RUBRIC);
		return model; 
	}
	@RequestMapping(value = "/goToEditLIRubric", method = RequestMethod.GET)
	public ModelAndView goToEditLIRubric(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/create_le_rubric","legend",new Legend());
		List<LegendCriteria> legendCriteriaLst=adminservice.getAllLegendCriteriaList();
		model.addObject("legendCriteriaLst",legendCriteriaLst);
		model.addObject("tab", WebKeys.LP_TAB_EDIT_LE_RUBRIC);
		return model; 
	}
	@RequestMapping(value = "/california5by5Dashboard", method = RequestMethod.GET)
	public ModelAndView california5by5Dashboard(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/california_5by5_Dashboard");
		return model; 
	}
	
	@RequestMapping(value = "/goalSettingDownload", method = RequestMethod.GET)
	public ModelAndView goalSettingDownload(HttpSession session) {
		List<Grade> grades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("CommonJsp/goal_setting_tabs");
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try {
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			grades = adminservice.getSchoolGradesByAcademicYr(userRegistration.getSchool().getSchoolId());
			model.addObject("grades", grades);
			model.addObject("page","goalSettingDownload");
		   } catch (Exception e) {
			logger.error("error while getting goal setting reports");
			model.addObject("errorMessage",
					WebKeys.TEACHER_GRADES_ERROR_MESSAGE);
		}
		return model;
	}
	
	@RequestMapping(value = "/goalSetting", method = RequestMethod.GET)
	public ModelAndView goalSetting(HttpSession session) {
		List<Grade> grades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Ajax2/CommonJsp/goal_setting_download");
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try {
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			grades = adminservice.getSchoolGradesByAcademicYr(userRegistration.getSchool().getSchoolId());
			model.addObject("grades", grades);
			model.addObject("page","goalSettingDownload");
		   } catch (Exception e) {
			logger.error("error while getting goal setting reports");
			model.addObject("errorMessage",
					WebKeys.TEACHER_GRADES_ERROR_MESSAGE);
		}
		return model;
	}
	
	@RequestMapping(value = "/goalSettingExcelDownload", method = RequestMethod.GET)
	public ModelAndView goalSettingExcelDownload(HttpSession session) {
		List<Grade> grades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Ajax2/CommonJsp/goal_setting_excel_download");
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try {
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			if(userRegistration.getUser().getUserTypeid() == 3){
				Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);		
				grades = curriculumService.getTeacherGrades(teacher);
				model.addObject("teacherId", teacher.getTeacherId());
				model.addObject("teacherName", teacher.getUserRegistration().getFirstName()+" "+teacher.getUserRegistration().getLastName());
			}else{
				grades = adminservice.getSchoolGradesByAcademicYr(userRegistration.getSchool().getSchoolId());
			}
			model.addObject("grades", grades);
			model.addObject("page","goalSettingExcelDownload");
			model.addObject("schoolId",userRegistration.getSchool().getSchoolId());
		} catch (Exception e) {
			logger.error("error while getting goal setting reports");
			model.addObject("errorMessage",	WebKeys.TEACHER_GRADES_ERROR_MESSAGE);
		}
		return model;
	}
	
	@RequestMapping(value = "/addSchoolInfo", method = RequestMethod.GET)
	public ModelAndView addSchoolInfo(HttpSession session,
			Map<String, Object> map) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long adminRegId = userReg.getRegId();
		String startDate = "";
		String endDate = "";
		School school = reportService.getSchoolIdByRegId(adminRegId);
		map.put("countryIds", appAdminService2.getCountries());
		long countryId = appAdminService2.getCountryByState(Long.valueOf(school.getStates().getStateId()));
		map.put("countryId", countryId);
		map.put("stateIds", appAdminService2.getStates(countryId));				
		map.put("stateId", school.getStates().getStateId());
		map.put("schoolLevelId", school.getSchoolLevel().getSchoolLevelId());
		map.put("schoolTypeId", school.getSchoolType().getSchoolTypeId());
		map.put("schoolTypeIds", schooladminservice.getSchoolTypeList());
		map.put("schoolLevelIds", schooladminservice.getSchoolLevelList());
		map.put("districtIds", schooladminservice.getDistricts());
		map.put("districtId", school.getDistrict()!=null?school.getDistrict().getDistrictId():0);
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		if(school.getPromotStartDate() != null){
			startDate = formatter.format(school.getPromotStartDate());
		}
		if(school.getPromotEndDate() != null){
			endDate = formatter.format(school.getPromotEndDate());			
		}		
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		ModelAndView model = new ModelAndView("Ajax/Admin/add_school_info",
				"School", school);
		model.addObject("tab", WebKeys.LP_TAB_UPDATE_SCHOOL_INFO);
		model.addObject("schoolinfo", school);
		return model;
	}
}
