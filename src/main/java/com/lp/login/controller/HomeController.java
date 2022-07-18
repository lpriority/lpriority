package com.lp.login.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.appadmin.service.AppAdminService;
import com.lp.appadmin.service.AppAdminService2;
import com.lp.appadmin.service.NewsFeedService;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.CommonService;
import com.lp.login.service.ForgotPasswordService;
import com.lp.login.service.UserLoginService;
import com.lp.mail.service.MailServiceImpl;
import com.lp.model.Grade;
import com.lp.model.Invitations;
import com.lp.model.Security;
import com.lp.model.SecurityQuestion;
import com.lp.model.User;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class HomeController {
		
	@Autowired
	private AppAdminService reportService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private AppAdminService2 appAdminService2;
	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private SchoolAdminService schoolAdminService;
	@Autowired
	private ForgotPasswordService reportsService;
	@Autowired
	private CommonService commonservice;
	@Autowired
	private AdminService adminService;
	@Autowired
	private NewsFeedService newsFeedService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView getdata() {
		ModelAndView model = new ModelAndView("login/Home", "userRegistration", new UserRegistration());
		return model;
	}
	
	@RequestMapping(value = "/privacyPolicy", method = RequestMethod.GET)
	public ModelAndView privacyPolicy() {
		ModelAndView model = new ModelAndView("login/privacy_policy");
		return model;
	}
	@RequestMapping(value = "/firstTimeUserInfo", method = RequestMethod.GET)
	public ModelAndView firstTimeUserInfo() {
		List<User> list = reportService.getUserTypeList2();
		ModelAndView model = new ModelAndView("login/first_time_user_info", "userRegistration", new UserRegistration());
		model.addObject("userlists", list);
		return model;
	}

	@RequestMapping(value = "/securityVerification", method = RequestMethod.GET)
	public ModelAndView securityVerification() {
		return new ModelAndView("login/SecurityVerification", WebKeys.USER_REGISTRATION_OBJ,
				new UserRegistration());
	}

	@RequestMapping(value = "/checkVerification", method = RequestMethod.GET)
	public ModelAndView checkVerification(
			@ModelAttribute("userReg") UserRegistration userReg,
			BindingResult result, Map<String, Object> map, HttpSession session) {
			String emailId = userReg.getEmailId();
			Security sec = userLoginService.getSecurityForVerification(emailId);
			UserRegistration user =  commonservice.getNewUserRegistration(emailId);
			if (sec.getVerificationCode() == null) {
				return new ModelAndView("login/ErrorCodeVerification");
			} else if (!sec.getVerificationCode().equals(
					userReg.getVerificationCode())) {
				return new ModelAndView("login/ErrorCodeVerification");
			} else if (!sec.getStatus().equals(WebKeys.ACTIVE)) {
		return new ModelAndView("login/AlreadyRegistered");
			} else {
				map.put("countryIds", appAdminService2.getCountries());
				map.put("securityQuestions", appAdminService.getSecurityQuestions());
				String userType = user.getUser().getUserType().toLowerCase();
				if (userType.equals(WebKeys.LP_USER_TYPE_ADMIN)) {
					return new ModelAndView("Admin/AdminRegistration1",
							"adminReg1", user);
				} else if (userType.equals("teacher")) {
					return new ModelAndView("Teacher/TeacherRegistration1",
							"teacherReg", user);
				} else if (userType.equals("parent")) {
					ModelAndView model = new ModelAndView(
							"Parent/ParentRegistration1", "parentReg1", user);
					session.setAttribute("ParentEmailId", emailId);
					List<SecurityQuestion> seclist = reportsService
							.getQuestionList();
					model.addObject("seclist", seclist);
					return model;
				} else if (userType.equals("student below 13")
						|| userType.equals("student above 13")) {
					String userTypeId = user.getUser().getUserType();
					session.setAttribute("UserType", userTypeId);
					ModelAndView model = new ModelAndView(
							"Student/StudentRegistration1", "studentUserReg", user);
					session.setAttribute("StudentEmailId", emailId);
					List<SecurityQuestion> seclist = reportsService
							.getQuestionList();
					model.addObject("seclist", seclist);
					List<Grade> schoolgrades = adminService
							.getSchoolGrades(user.getSchool().getSchoolId());
					model.addObject("schoolgrades", schoolgrades);
					return model;
				} else if (userType.equalsIgnoreCase("District Admin")) {
					return new ModelAndView(
							"DistrictAdmin/DistrictAdminRegistration1",
							"DistrictAdminReg1", user);
				} else {

					return new ModelAndView("Login/Fail.jsp");
				}
			}
	}

	@RequestMapping(value = "/invitation")
	public ModelAndView invitation() {

		ModelAndView model = new ModelAndView("login/Invitation", "invitation",
				new Invitations());
		model.addObject("userTypesList", reportService.getUserTypeList2());
		return model;
	}

	@RequestMapping(value = "/inviteOthers", method = RequestMethod.POST)
	public ModelAndView inviteOthers(
			@ModelAttribute("invitation") Invitations invitation,
			BindingResult result, HttpSession session) {
			UserRegistration userReg = null;
			if (session.getAttribute("adminReg1") != null) {
				userReg = (UserRegistration) session.getAttribute("adminReg1");
			} else if (session.getAttribute("userRegiss") != null) {
				userReg = (UserRegistration) session.getAttribute("userRegiss");
			} else if (session.getAttribute("ParentRegis") != null) {
				userReg = (UserRegistration) session
						.getAttribute("ParentRegis");
			} else if (session.getAttribute("teacherReg1") != null) {
				userReg = (UserRegistration) session
						.getAttribute("teacherReg1");
			}

			invitation.setUserRegistration(userReg);
			userLoginService.saveInvitations(invitation);
			MailServiceImpl.sendInvitations(invitation.getInviteeEmail(),
					"admin@learningpriority.com", invitation.getMessage(),
					invitation.getUser().getUserTypeid());
			return new ModelAndView("redirect:/skip.htm");
	}

	@RequestMapping(value = "/skip", method = RequestMethod.GET)
	public ModelAndView skip(HttpSession session) {
		return new ModelAndView("redirect:/gotoDashboard.htm");
	}


	@RequestMapping(value = "/FirstTimeUserInfo", method = RequestMethod.POST)
	public ModelAndView FirstTimeUserInformation(HttpServletResponse response,
			HttpSession session, @ModelAttribute("security") Security security,
			BindingResult result, HttpServletRequest request) throws Exception {
		ModelAndView Goto = null;
		boolean registrationStatus = false;
		long userTypeId = 0, uTypeId = 0;
		  String emailId = request.getParameter("homeEmail");
		  long userTypeid = Long.valueOf(request.getParameter("userType"));
		  security = userLoginService.getSecurityForVerification(emailId);
		  UserRegistration userReg = schoolAdminService.getUserReg(emailId);
		  	if ((userReg.getStatus()!=null && userReg.getStatus().equalsIgnoreCase(WebKeys.ACTIVE)) || (security.getStatus()!=null && (security.getStatus().equals(WebKeys.ACTIVE) ||security.getStatus().equals(WebKeys.LP_STATUS_INACTIVE)))){
				Goto = new ModelAndView("redirect:/AlreadyRegistered.htm");
			}else {			
				security.setUserRegistration(userReg);
				long regId = security.getUserRegistration().getRegId();
				if(security.getUserRegistration().getUser() != null){
					uTypeId = security.getUserRegistration().getUser().getUserTypeid();
				}
				 
				if (regId == 0 || uTypeId != userTypeid) {
					Goto = new ModelAndView("redirect:/Fail.htm");
				} else {
					if (security.getUserRegistration().getUser().getUserType().equalsIgnoreCase("Student Below 13")) {
						registrationStatus = commonservice.isParentGotRegistered(security.getUserRegistration());
					}
					if (!registrationStatus && security.getUserRegistration().getUser().getUserType().equalsIgnoreCase("Student Below 13")) {
						Goto = new ModelAndView("redirect:/FailStudentBelow13.htm");
					}else {
						String verificationCode = MailServiceImpl.getUniqueID();
						security.setVerificationCode(verificationCode);
						security.setStatus(WebKeys.ACTIVE);
						security.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
						boolean statusFlag = userLoginService.saveSecurity(security);
						if (statusFlag == true) {
							userTypeId = security.getUserRegistration().getUser().getUserTypeid();
							MailServiceImpl.FirstTimeUserInfoMail(emailId, "admin@learningpriority.com", verificationCode, userTypeId);
							Goto = new ModelAndView("redirect:/EmailVerification.htm");
						} else {
							Goto = new ModelAndView("redirect:/Fail.htm");
					}							
				}
			}
		}
		return Goto;
	}

	@RequestMapping(value = "/contactUs", method = RequestMethod.GET)
	public ModelAndView contactUs(Map<String, Object> map, HttpSession session) {
		ModelAndView model = new ModelAndView("login/ContactUs");
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax"));
			session.removeAttribute("helloAjax");
		}
		return model;
	}
	@RequestMapping(value = "/privacy", method = RequestMethod.GET)
	public ModelAndView privacy(Map<String, Object> map) {

		ModelAndView model = new ModelAndView("login/Privacy");
		return model;
	}

	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public ModelAndView contactUs(HttpSession session) {
		ModelAndView model = new ModelAndView("login/aboutUs");
		return model;
	}
	
	@RequestMapping(value = "/LPPresentation", method = RequestMethod.GET)
	public ModelAndView LPPresentation(HttpSession session) {
		ModelAndView model = new ModelAndView("login/LPPresentation");
		return model;
	}
	@RequestMapping(value = "/LPFluencySuite", method = RequestMethod.GET)
	public ModelAndView LPFluencySuite(HttpSession session) {
		ModelAndView model = new ModelAndView("login/LPFluencySuite");
		return model;
	}
	@RequestMapping(value = "/LearningPriorityGuide", method = RequestMethod.GET)
	public ModelAndView LearningPriorityGuide(HttpSession session) {
		ModelAndView model = new ModelAndView("login/LearningPriorityGuide");
		return model;
	}
	@RequestMapping(value = "/teacherResources", method = RequestMethod.GET)
	public ModelAndView teacherResources(HttpSession session) {
		ModelAndView model = new ModelAndView("login/TeacherResources");
		return model;
	}
	@RequestMapping(value = "/teacherGuide", method = RequestMethod.GET)
	public ModelAndView teacherGuide(HttpSession session) {
		ModelAndView model = new ModelAndView("login/TeacherGuide");
		return model;
	}
	
	@RequestMapping(value = "/studentGuide", method = RequestMethod.GET)
	public ModelAndView studentGuide(HttpSession session) {
		ModelAndView model = new ModelAndView("login/StudentGuide");
		return model;
	}
	
	@RequestMapping(value = "/readingRegister", method = RequestMethod.GET)
	public ModelAndView readingRegister(HttpSession session) {
		ModelAndView model = new ModelAndView("login/ReadingRegister");
		return model;
	}
	
	

	@RequestMapping(value = "/lpTools", method = RequestMethod.GET)
	public ModelAndView lpTools(HttpSession session) {
		ModelAndView model = new ModelAndView("login/LPTools");
		return model;
	}
	
	@RequestMapping(value = "/contactUsSuccess", method = RequestMethod.POST)
	public ModelAndView ContactUsPage(Map<String, Object> map, HttpServletResponse response,
			HttpSession session, HttpServletRequest request) throws Exception {
		String helloAjax = "";
		String to = "admin@learningpriority.com";
		try {
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String from = request.getParameter("email");
			String iam = request.getParameter("iam");
			String comment = request.getParameter("comment");
			MailServiceImpl.contactUsMail(name, phone, iam, comment, to, from);
			helloAjax = "Success";
		} catch (Exception e) {
			helloAjax = "Sending failed";
		}
		ModelAndView model = new ModelAndView("redirect:/contactUs.htm");
		session.setAttribute("helloAjax", helloAjax);
		return model;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/mainView")
	public ModelAndView getMainView() {
		/*
		 * do all your normal stuff here to build your primary NON-ajax view in
		 * the same way you always do
		 */
		return new ModelAndView("MainView");
	}

	/*
	 * this is the conroller's part of the magic; I'm just using a simple GET
	 * but you could just as easily do a POST here, obviously
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/subView")
	public ModelAndView getSubView(Model model) {
		model.addAttribute("user", "Joe Dirt");
		model.addAttribute("time", new Date());
		return new ModelAndView("SubView");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/registrationSuccess")
	public ModelAndView registrationSuccess(HttpSession session) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();	
		ModelAndView model = new ModelAndView("login/RegistrationSuccess");
		model.addObject("springUser", userName);
		return model;
	}
	
	@RequestMapping(value = "/Fail", method = RequestMethod.GET)
	public ModelAndView Fail(HttpSession session) {
		return new ModelAndView("login/Fail");
	}
	
	@RequestMapping(value = "/AlreadyRegistered", method = RequestMethod.GET)
	public ModelAndView AlreadyRegistered(HttpSession session) {
		return new ModelAndView("login/AlreadyRegistered");
	}
	
	@RequestMapping(value = "/FailStudentBelow13", method = RequestMethod.GET)
	public ModelAndView FailStudentBelow13(HttpSession session) {
		return new ModelAndView("login/FailStudentBelow13");
	}
	
	@RequestMapping(value = "/EmailVerification", method = RequestMethod.GET)
	public ModelAndView EmailVerification(HttpSession session) {
		return new ModelAndView("login/EmailVerification");
	}
	@RequestMapping(value = "/lpNews", method = RequestMethod.GET)
	public ModelAndView lpNews(HttpSession session) {
		ModelAndView model = new ModelAndView("login/lpNews");
		model.addObject("lpNews", newsFeedService.getLPNews());
		return model;
	}

}
