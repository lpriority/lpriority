package com.lp.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.appadmin.service.AppAdminService;
import com.lp.appadmin.service.AppAdminService2;
import com.lp.appadmin.service.AppAdminService4;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.CommonService;
import com.lp.login.service.UserLoginService;
import com.lp.model.Address;
import com.lp.model.Interest;
import com.lp.model.School;
import com.lp.model.Security;
import com.lp.model.StudentClass;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class AdminRegController {
	
	static final Logger logger = Logger.getLogger(AdminRegController.class);

	@Autowired
	private AppAdminService2 appAdminService2;
	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private SchoolAdminService schoolAdminService;
	@Autowired
	private AppAdminService4 appAdminService4;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private AdminService adminService;

	@Autowired
	private CommonService commonService;
	@Autowired
	@Qualifier("adminReg1FormValidator")
	private Validator adminReg1Validator;
	@Autowired
	@Qualifier("adminReg2FormValidator")
	private Validator adminReg2Validator;

	@Autowired
	@Qualifier("adminReg3FormValidator")
	private Validator adminReg3Validator;

	@InitBinder("adminReg1")
	private void initAdminReg1Binder(WebDataBinder binder) {
		binder.setValidator(adminReg1Validator);
	}

	@InitBinder("adminReg2")
	private void initAdminReg2Binder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
		binder.setValidator(adminReg2Validator);
	}

	@InitBinder("adminReg3")
	private void initAdminReg3Binder(WebDataBinder binder) {
		binder.setValidator(adminReg3Validator);
	}

	@RequestMapping(value = "/adminRegForm1Validate", method = RequestMethod.POST)
	public ModelAndView adminRegForm1Validate(
			@ModelAttribute("adminReg1") @Valid UserRegistration adminReg1,
			BindingResult result, HttpSession session, Map<String, Object> map) {
		if (result.hasErrors()) {
			map.put("countryIds", appAdminService2.getCountries());
			if (adminReg1.getCountryId() != null
					&& !adminReg1.getCountryId().equals("")
					&& !adminReg1.getCountryId().equals("select")) {
				map.put("stateIds", appAdminService2.getStates(Long
						.valueOf(adminReg1.getCountryId())));
				map.put("countryId", adminReg1.getCountryId());
			}
			if (adminReg1.getStateId() != null
					&& !adminReg1.getStateId().equals("")
					&& !adminReg1.getStateId().equals("select")) {
				map.put("stateId", Long.valueOf(adminReg1.getStateId()));
			}
			if (adminReg1.getSecurityQuestionId() != null
					&& !adminReg1.getSecurityQuestionId().equals("")
					&& !adminReg1.getSecurityQuestionId().equals("select")) {
				map.put("secQueId", Long.valueOf(adminReg1.getSecurityQuestionId()));
			}
			map.put("securityQuestions", appAdminService.getSecurityQuestions());
			
			return new ModelAndView("Admin/AdminRegistration1", "adminReg1",
					adminReg1);
		} else {
			session.setAttribute("adminReg1", adminReg1);
			map.put("countryIds", appAdminService2.getCountries());
			map.put("schoolTypeIds", schoolAdminService.getSchoolTypeList());
			map.put("schoolLevelIds", schoolAdminService.getSchoolLevelList());
			Interest sports = appAdminService4.getInterest("sports offered");
			map.put("sportId", sports);
			map.put("sportsOffered",
					appAdminService4.getSubInterests(sports.getInterestId()));
			Interest extraCurricular = appAdminService4
					.getInterest("Extra-Curricular Activities Offered");
			map.put("extraCurricularId", extraCurricular);
			map.put("extraCurricularList", appAdminService4
					.getSubInterests(extraCurricular.getInterestId()));
			School school = schoolAdminService.getSchool(commonService.getNewUserRegistration(adminReg1.getEmailId()).getSchool()
 					.getSchoolId());
			return new ModelAndView("Admin/AdminRegistration2", "adminReg2",
					school);
		}
	}

	@RequestMapping(value = "/adminRegForm2Validate", method = RequestMethod.POST)
	public ModelAndView adminRegForm2Validate(
			@ModelAttribute("adminReg2") @Valid School adminReg2,
			BindingResult result, HttpSession session, Map<String, Object> map) {
		if (result.hasErrors()) {
			map.put("countryIds", appAdminService2.getCountries());
			if (adminReg2.getCountryId() != null
					&& !adminReg2.getCountryId().equals("")
					&& !adminReg2.getCountryId().equals("select")) {
				map.put("stateIds", appAdminService2.getStates(Long
						.valueOf(adminReg2.getCountryId())));
				map.put("countryId", adminReg2.getCountryId());
			}
			if (adminReg2.getStateId() != null
					&& !adminReg2.getStateId().equals("")
					&& !adminReg2.getStateId().equals("select")) {
				map.put("stateId", Long.valueOf(adminReg2.getStateId()));
			}
			
			map.put("schoolTypeIds", schoolAdminService.getSchoolTypeList());
			map.put("schoolLevelIds", schoolAdminService.getSchoolLevelList());
			if (adminReg2.getSchoolTypeId() != null
					&& !adminReg2.getSchoolTypeId().equals("")
					&& !adminReg2.getSchoolTypeId().equals("select")) {
				map.put("schoolTypeId", Long.valueOf(adminReg2.getSchoolTypeId()));
			}
			if (adminReg2.getSchoolLevelId() != null
					&& !adminReg2.getSchoolLevelId().equals("")
					&& !adminReg2.getSchoolLevelId().equals("select")) {
				map.put("schoolLevelId", Long.valueOf(adminReg2.getSchoolLevelId()));
			}
			Interest sports = appAdminService4.getInterest("sports offered");
			map.put("sportId", sports);
			map.put("sportsOffered",
			appAdminService4.getSubInterests(sports.getInterestId()));
			Interest extraCurricular = appAdminService4
					.getInterest("Extra-Curricular Activities Offered");
			map.put("extraCurricularId", extraCurricular);
		    map.put("extraCurricularList", appAdminService4
					.getSubInterests(extraCurricular.getInterestId()));
			return new ModelAndView("Admin/AdminRegistration2", "adminReg2",
					adminReg2);
		} else {
			UserRegistration adminReg3 = new UserRegistration();
			session.setAttribute("adminReg2", adminReg2);
			Interest interestAreas = appAdminService4
					.getInterest("areas Of interest");
			map.put("interestAreasId", interestAreas);
			map.put("interestAreasList", appAdminService4
					.getSubInterests(interestAreas.getInterestId()));
			return new ModelAndView("Admin/AdminRegistration3", "adminReg3",
					adminReg3);
		}
	}

	@RequestMapping(value = "/adminRegForm3Validate", method = RequestMethod.POST)
	public ModelAndView adminRegForm3Validate(
			@ModelAttribute("adminReg3") @Valid UserRegistration adminReg3,
			BindingResult result, HttpSession session, Map<String, Object> map) {
		if (result.hasErrors()) {
			Interest interestAreas = appAdminService4
					.getInterest("areas Of interest");
			map.put("interestAreasId", interestAreas);
			map.put("interestAreasList", appAdminService4
					.getSubInterests(interestAreas.getInterestId()));
			return new ModelAndView("Admin/AdminRegistration3", "adminReg3",
					adminReg3);
		} else {
			School school = new School();
			UserRegistration userReg = new UserRegistration();
			if (session.getAttribute("adminReg2") != null
					&& session.getAttribute("adminReg1") != null) {
				school = (School) session.getAttribute("adminReg2");
				userReg = (UserRegistration) session.getAttribute("adminReg1");
			}
			String md5Password = userLoginService.getMD5Conversion(userReg
					.getPassword());
			userReg.setPassword(md5Password);
			userReg.setQualification(adminReg3.getQualification());
			userReg.setPhonenumber(adminReg3.getPhonenumber());
			userReg.setSchool(school);
			boolean status = true;
			userReg.setAddress(new Address());
			userReg.getAddress().setAddress(
					userReg.getAddress1() + "##@##" + userReg.getAddress2());
			userReg.getAddress().setCity(userReg.getCity());
			userReg.getAddress()
					.setStates(
							appAdminService2.getState(Long.valueOf(userReg
									.getStateId())));
			userReg.getAddress().setZipcode(
					Integer.valueOf(userReg.getZipcode()));
			userReg.setUser(schoolAdminService.getUserType(WebKeys.LP_USER_TYPE_ADMIN));
			userLoginService.saveAddress(userReg.getAddress());
			userReg.setStatus(WebKeys.ACTIVE);
			
			Security sec = userLoginService.getSecurityForVerification(userReg.getEmailId());
			sec.setSecurityQuestion(appAdminService.getSecurityQuestion(Long
					.valueOf(userReg.getSecurityQuestionId())));
			sec.setAnswer(userReg.getAnswer());
			sec.setStatus(WebKeys.LP_STATUS_INACTIVE);
			// Saving Admin details
			schoolAdminService.saveAdminRegistration(userReg,school,sec);
			
			// Creating Home Room Class for the school
			StudentClass stuclass = new StudentClass();
			stuclass.setSchool(school);
			stuclass.setClassName(WebKeys.LP_CLASS_HOMEROME);
			int check = adminService.CheckExistClass(stuclass);
			if (check == 0) {
				adminService.CreateClasses(stuclass);
			}
			if (status) {
				session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
				return new ModelAndView("login/RegistrationSuccess");
			} else {
				return new ModelAndView("login/Fail");
			}
		}
	}
}
