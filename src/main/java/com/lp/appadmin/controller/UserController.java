package com.lp.appadmin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lp.appadmin.service.AppAdminService;
import com.lp.model.AssignmentType;
import com.lp.model.Days;
import com.lp.model.Minutes;
import com.lp.model.SecurityQuestion;
import com.lp.model.User;

@Controller
public class UserController {
	
	static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private AppAdminService appAdminService;

	/* ######### User types Methods starts from here ############# */
	@RequestMapping("/displayUsers")
	public ModelAndView listUserTypes(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userList", appAdminService.getUserTypes());
		return new ModelAndView("AppManager/DisplayUsers", model);
	}

	@RequestMapping("/deleteUser")
	public ModelAndView deleteUserType(@RequestParam int id) {
		appAdminService.deleteUserType(id);
		return new ModelAndView("redirect:/displayUsers.htm");
	}

	@RequestMapping("/saveUser")
	public ModelAndView saveGrade(@ModelAttribute("user") @Valid User user,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("AppManager/AddUsers");

		} else {
			appAdminService.saveUserType(user);
		}
		return new ModelAndView("redirect:/displayUsers.htm");

	}

	@RequestMapping("/addUser")
	public ModelAndView addUser(@ModelAttribute User user) {
		return new ModelAndView("AppManager/AddUsers", "user", new User());
	}

	@RequestMapping("/editUser")
	public ModelAndView editGrade(@RequestParam int id,
			@ModelAttribute User user) {
		return new ModelAndView("AppManager/AddUsers", "user",
				appAdminService.getUserType(id));
	}

	/* ######### Assignment types Methods starts from here ############# */

	@RequestMapping("/displayAssignmentTypes")
	public ModelAndView listAssignmentTypes(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("assignmentTypesList", appAdminService.getAssignmentTypes());
		return new ModelAndView("AppManager/DisplayAssignmentTypes", model);
	}

	@RequestMapping("/deleteAssignmentType")
	public ModelAndView deleteAssignmentType(@RequestParam int id) {
		appAdminService.deleteAssignmentType(id);
		return new ModelAndView("redirect:/displayAssignmentTypes.htm");
	}

	@RequestMapping("/saveAssignmentType")
	public ModelAndView saveAssignmentType(
			@ModelAttribute("assignmentType") @Valid AssignmentType assignmentType,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("AppManager/AddAssignmentTypes");
		} else {
			appAdminService.saveAssignmentType(assignmentType);
		}
		return new ModelAndView("redirect:/displayAssignmentTypes.htm");

	}

	@RequestMapping("/addAssignmentType")
	public ModelAndView addAssignmentType(@ModelAttribute User user) {
		return new ModelAndView("AppManager/AddAssignmentTypes",
				"assignmentType", new AssignmentType());
	}

	@RequestMapping("/editAssignmentType")
	public ModelAndView editAssignmentType(@RequestParam int id,
			@ModelAttribute AssignmentType assignmentType) {
		return new ModelAndView("AppManager/AddAssignmentTypes",
				"assignmentType", appAdminService.getAssignmentType(id));
	}

	/* ######### SecurityQuestion Methods starts from here ############# */

	@RequestMapping("/displaySecurityQuestions")
	public ModelAndView listSecurityQuestions(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("securityQuestionsList",
				appAdminService.getSecurityQuestions());
		return new ModelAndView("AppManager/DisplaySecurityQuestions", model);
	}

	@RequestMapping("/deleteSecurityQuestion")
	public ModelAndView deleteSecurityQuestion(@RequestParam int id) {
		appAdminService.deleteSecurityQuestion(id);
		return new ModelAndView("redirect:/displaySecurityQuestions.htm");
	}

	@RequestMapping("/saveSecurityQuestion")
	public ModelAndView saveSecurityQuestion(
			@ModelAttribute("securityQuestion") @Valid SecurityQuestion securityQuestion,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("AppManager/AddSecurityQuestions");
		} else {
			appAdminService.saveSecurityQuestion(securityQuestion);
		}
		return new ModelAndView("redirect:/displaySecurityQuestions.htm");

	}

	@RequestMapping("/addSecurityQuestion")
	public ModelAndView addSecurityQuestion(
			@ModelAttribute SecurityQuestion securityQuestion) {
		return new ModelAndView("AppManager/AddSecurityQuestions",
				"securityQuestion", new SecurityQuestion());
	}

	@RequestMapping("/editSecurityQuestion")
	public ModelAndView editSecurityQuestion(@RequestParam int id,
			@ModelAttribute SecurityQuestion securityQuestion) {
		return new ModelAndView("AppManager/AddSecurityQuestions",
				"securityQuestion", appAdminService.getSecurityQuestion(id));
	}

	/* ######### Minutes Methods starts from here ############# */

	@RequestMapping("/displayMinutes")
	public ModelAndView listMinutes(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("minutesList", appAdminService.getMinutes());
		return new ModelAndView("AppManager/DisplayMinutes", model);
	}

	@RequestMapping("/deleteMinute")
	public ModelAndView deleteMinute(@RequestParam int id) {
		appAdminService.deleteMinute(id);
		return new ModelAndView("redirect:/displayMinutes.htm");
	}

	@RequestMapping("/saveMinute")
	public ModelAndView saveMinute(
			@ModelAttribute("minute") @Valid Minutes minute,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("AppManager/AddMinute");
		} else {
			appAdminService.saveMinute(minute);
		}
		return new ModelAndView("redirect:/displayMinutes.htm");

	}

	@RequestMapping("/addMinute")
	public ModelAndView addMinute(@ModelAttribute Minutes minute) {
		return new ModelAndView("AppManager/AddMinute", "minute", new Minutes());
	}

	@RequestMapping("/editMinute")
	public ModelAndView editMinute(@RequestParam int id,
			@ModelAttribute Minutes minute) {
		return new ModelAndView("AppManager/AddMinute", "minute",
				appAdminService.getMinute(id));
	}

	/* ######### Days Methods starts from here ############# */

	@RequestMapping("/displayDays")
	public ModelAndView listDays(Map<String, Object> map) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("daysList", appAdminService.getDays());
		return new ModelAndView("AppManager/DisplayDays", model);
	}

	@RequestMapping("/deleteDay")
	public ModelAndView deleteDay(@RequestParam int id) {
		appAdminService.deleteDay(id);
		return new ModelAndView("redirect:/displayDays.htm");
	}

	@RequestMapping("/saveDay")
	public ModelAndView saveDay(@ModelAttribute("day") @Valid Days day,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("AppManager/AddDay");
		} else {
			appAdminService.saveDay(day);
		}
		return new ModelAndView("redirect:/displayDays.htm");

	}

	@RequestMapping("/addDay")
	public ModelAndView addDay(@ModelAttribute Days day) {
		return new ModelAndView("AppManager/AddDay", "day", new Days());
	}

	@RequestMapping("/editDay")
	public ModelAndView editDay(@RequestParam int id, @ModelAttribute Days day) {
		return new ModelAndView("AppManager/AddDay", "day",
				appAdminService.getDay(id));
	}

}
