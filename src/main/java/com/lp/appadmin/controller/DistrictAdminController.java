package com.lp.appadmin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lp.appadmin.service.AppAdminService;
import com.lp.appadmin.service.AppAdminService2;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.CommonService;
import com.lp.login.service.UserLoginService;
import com.lp.mail.service.MailServiceImpl;
import com.lp.model.Address;
import com.lp.model.Security;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class DistrictAdminController {
	@Autowired
	private SchoolAdminService schoolAdminService;
	@Autowired
	private AppAdminService2 appAdminService2;
	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private CommonService reportService;

	static final Logger logger = Logger.getLogger(DistrictAdminController.class);

	/* ##### UserRegistration methods go here ###### */

	@RequestMapping("/displayDistrictUsers")
	public ModelAndView listUserRegistrations(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("urList",
					schoolAdminService.getUserRegistrations("District Admin"));
		} catch (Exception e) {
			logger.error("Error in displayUserRegistrations() of AcademicPerformances Controller"
					+ e);
			e.printStackTrace();
		}
		return new ModelAndView("AppManager/DisplayDistrictUserRegistrations",
				model);
	}

	@RequestMapping("/deleteDistrictUserRegistration")
	public @ResponseBody
	void deleteDistrictAdmin(HttpSession session, HttpServletResponse response,
			@RequestParam("regId") long regId) {
		try {

			String helloAjax = "";
			int stat = 0;
			stat = schoolAdminService.deleteUserRegistration(regId);
			if (stat > 0) {
				helloAjax = "District Administrator deleted Successfully";
			} else {
				helloAjax = "District Administrator Not deleted Successfully";
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/SaveDistrictAdmin", method = RequestMethod.GET)
	public @ResponseBody
	void SaveDistrictAdmin(
			HttpServletResponse response,
			HttpSession session,
			@ModelAttribute("userRegistration") UserRegistration userRegistration,
			BindingResult result, @RequestParam("emailId") String emailId,
			@RequestParam("districtId") long districtId) throws Exception {
		String helloAjax = "";
		try {

			userRegistration.setEmailId(emailId);
			userRegistration.setDistrict(schoolAdminService
					.getDistrict(districtId));
			userRegistration.setdistrictId(districtId);
			userRegistration.setUser(appAdminService
					.getUserType("District Admin"));
			userRegistration.setStatus(WebKeys.LP_STATUS_NEW);
			schoolAdminService.saveUserRegistration(userRegistration);
			Security sec = new Security();
			sec.setUserRegistration(userRegistration);
			if (userLoginService.checkSecurity(sec)) {
				sec.setVerificationCode("adminadmin");
				sec.setStatus(WebKeys.ACTIVE);
				userLoginService.saveSecurity(sec);
				MailServiceImpl.FirstTimeUserInfoMail(emailId,
						"admin@learningpriority.com", "adminadmin",
						userRegistration.getUser().getUserTypeid());
			}
			helloAjax = "DistrictAdmin Added Successfully";

		} catch (Exception e) {
			logger.error("Error in SaveDistrictAdmin() of DistrictAdminController"
					+ e);
			e.printStackTrace();
			helloAjax = "District Not Added Successfully";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(helloAjax);

	}

	@RequestMapping("/addDistrictUserRegistration")
	public ModelAndView addDistrictUserRegistration(
			@ModelAttribute UserRegistration userRegistration,
			Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		map.put("districtList", schoolAdminService.getDistricts());
		try {
			mav = new ModelAndView("AppManager/AddDistrictUserRegistration",
					"userRegistration", new UserRegistration());
		} catch (Exception e) {
			logger.error("Error in addDistrictUserRegistration() of SchoolAdminController"
					+ e);
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/SaveDistrictAdminDetails", method = RequestMethod.GET)
	public @ResponseBody
	void SaveDistrictAdminDetails(HttpSession session,
			HttpServletResponse response, @RequestParam("title") String title,
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("address1") String address1,
			@RequestParam("address2") String address2,
			@RequestParam("countryId") long countryId,
			@RequestParam("stateId") long stateId,
			@RequestParam("city") String city,
			@RequestParam("zipcode") String zipcode,
			@RequestParam("emailId") String emailId,
			@RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("securityid") long securityid,
			@RequestParam("securityanswer") String securityanswer,
			@RequestParam("qualification") String qualification,
			@RequestParam("phoneNumber") String phonenumber,
			@ModelAttribute("DistrictAdminReg1") UserRegistration userRe) {
		try {
			String helloAjax = "";
			long regid = reportService.getNewUserRegistration(emailId).getRegId();
			UserRegistration userReg1 = schoolAdminService
					.getUserRegistration(regid);
			userReg1.setPassword(password);
			String md5Password = userLoginService.getMD5Conversion(userReg1
					.getPassword());
			userReg1.setPassword(md5Password);
			userReg1.setTitle(title);
			userReg1.setFirstName(firstname);
			userReg1.setLastName(lastname);
			userReg1.setCity(city);
			userReg1.setAddress1(address1);
			userReg1.setAddress2(address2);
			userReg1.setStateId(Long.toString(stateId));
			userReg1.setZipcode(zipcode);
			userReg1.setAddress(new Address());
			userReg1.getAddress().setAddress(
					userReg1.getAddress1() + "##@##" + userReg1.getAddress2());
			userReg1.getAddress().setCity(userReg1.getCity());
			userReg1.getAddress().setStates(
					appAdminService2.getState(Long.valueOf(userReg1
							.getStateId())));
			userReg1.getAddress().setZipcode(
					Integer.valueOf(userReg1.getZipcode()));
			userLoginService.saveAddress(userReg1.getAddress());
			userReg1.setQualification(qualification);
			userReg1.setPhonenumber(phonenumber);
			userReg1.setStatus(WebKeys.ACTIVE);
			schoolAdminService.UpdateUserRegistration(userReg1);
			boolean status = false;
			Security sec = userLoginService.getSecurity(emailId);
			sec.setStatus(WebKeys.LP_STATUS_INACTIVE);
			sec.setSecurityQuestion(appAdminService
					.getSecurityQuestion(securityid));
			sec.setAnswer(securityanswer);
			status = userLoginService.saveSecurity(sec);
			if (status == true) {
				helloAjax = "RegistrationSuccess.htm";
			} else {
				helloAjax = "Fail.htm";
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
