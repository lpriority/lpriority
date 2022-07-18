package com.lp.student.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.model.EarlyLiteracyTestsForm;
import com.lp.model.Student;
import com.lp.student.service.EarlyLiteracyTestsService;
import com.lp.teacher.service.AssignPhonicSkillTestService;
import com.lp.utils.WebKeys;

@Controller
public class PhonicSkillTestController {
	
	@Autowired 
	private AssignPhonicSkillTestService assignPhonicSkillTestService;
	@Autowired
	private EarlyLiteracyTestsService earlyLiteracyTestsService;
	
	static final Logger logger = Logger.getLogger(PhonicSkillTestController.class);
	
	@RequestMapping("/goForPhonicSkillTest")
	public ModelAndView goForPhonicSkillTest(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentId") long assignmentId,
			HttpSession session,
			Model model) {
		try{
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			long regId =student.getUserRegistration().getRegId();
		    long studentId = student.getStudentId();
			String phonicSkillTestFilePath = 	WebKeys.PHONIC_TEST+File.separator+
					studentId+File.separator+
					assignmentId;
			model.addAttribute("studentAssignmentId",studentAssignmentId);
			model.addAttribute("regId",regId);
			model.addAttribute("phonicSkillTestFilePath",phonicSkillTestFilePath);
			model.addAttribute("assignmentId",assignmentId);
			model.addAttribute("earlyLiteracyTestsForm",new EarlyLiteracyTestsForm());
		}catch(Exception e){
			logger.error("Error in goForPhonicSkillTest() of PhonicSkillTestController "+ e.getStackTrace());
		}
		return new ModelAndView("Ajax/Student/phonic_skill_test");
	}
	@RequestMapping(value = "/recordPhonicTest", method = RequestMethod.POST)
	public View recordPhonicTest(HttpServletResponse response,@ModelAttribute("earlyLiteracyTestsForm") EarlyLiteracyTestsForm earlyLiteracyTestsForm, BindingResult result,HttpSession session,HttpServletRequest request) {
		try{
			assignPhonicSkillTestService.recordPhonicSkillTest(earlyLiteracyTestsForm);
		}catch(Exception e){
			logger.error("Error in recordPhonicTest() of PhonicSkillTestController "+ e.getStackTrace());
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value ="/updateTestStatus", method = RequestMethod.POST)
	public View updateTestStatus(HttpServletResponse response,@ModelAttribute("earlyLiteracyTestsForm") EarlyLiteracyTestsForm earlyLiteracyTestsForm, BindingResult result,HttpSession session,HttpServletRequest request) {
		try{
			boolean success = earlyLiteracyTestsService.updateTestStatus(earlyLiteracyTestsForm);
			if(success){
				if(earlyLiteracyTestsForm.getStatus().equalsIgnoreCase(WebKeys.TEST_STATUS_SAVED))
					earlyLiteracyTestsForm.setResult(WebKeys.TEST_SAVED_SUCCESS);
			    else if(earlyLiteracyTestsForm.getStatus().equalsIgnoreCase(WebKeys.TEST_STATUS_SUBMITTED))
			    	earlyLiteracyTestsForm.setResult(WebKeys.TEST_SUBMITTED_SUCCESS);	
			}else{
				earlyLiteracyTestsForm.setResult(WebKeys.ERROR_OCCURED);	
			}
		}catch(Exception e){
			logger.error("Error in updateTestStatus() of PhonicSkillTestController "+ e.getStackTrace());
		}
		return new MappingJackson2JsonView();
	}
}
