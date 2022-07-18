package com.lp.login.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lp.login.service.ForgotPasswordService;
import com.lp.login.service.UserLoginService;
import com.lp.mail.service.MailServiceImpl;
import com.lp.model.Security;
import com.lp.model.SecurityQuestion;

@Controller
public class ForgotPasswordController {

	@Autowired
	private UserLoginService userLoginService;


	@Autowired
	private ForgotPasswordService reportService;
	static final Logger logger = Logger
			.getLogger(ForgotPasswordController.class);

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public ModelAndView forgotPassword() {
		List<SecurityQuestion> list = reportService.getQuestionList();
		ModelAndView model = new ModelAndView("login/ForgotPassword",
				"security", new Security());
		model.addObject("lists", list);
		return model;
	}

	@RequestMapping(value = "/checkForgotPassword", method = RequestMethod.POST)
	public ModelAndView checkForgotPassword(@ModelAttribute("security") Security security) {
		ModelAndView model = null;
		try {
				long regId = 0;
				boolean flag = reportService.checkSecurityforUser(security);
				if (flag == true) {
					model = new ModelAndView("login/forgotPasswordSuccess");
					regId = security.getUserRegistration().getRegId();
					String email = security.getEmailId();
					String changePassword = MailServiceImpl.getUniqueID();
					String md5Password = userLoginService.getMD5Conversion(changePassword);
					@SuppressWarnings("unused")
					int m = reportService.UpdatePassword(regId, md5Password);
					MailServiceImpl.sendMails(email,"admin@learningpriority.com", changePassword);			
					
				} else {
					model = new ModelAndView("login/ForgotPasswordFail");
				}
		} catch (Exception e) {
			logger.error("Error in checkForgotPassword() of ForgotPasswordController"
					+ e);
			e.printStackTrace();
		}
		return model;
	}
}

