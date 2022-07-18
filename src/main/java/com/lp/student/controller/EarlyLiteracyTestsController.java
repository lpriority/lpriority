package com.lp.student.controller;

import java.util.ArrayList;

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
import com.lp.student.service.EarlyLiteracyTestsService;


@Controller
public class EarlyLiteracyTestsController {

	static final Logger logger = Logger.getLogger(EarlyLiteracyTestsController.class);

	@Autowired
	EarlyLiteracyTestsService earlyLiteracyTestsService;
	
	@RequestMapping(value = "/earlyLiteracyTests", method = RequestMethod.GET)
	public ModelAndView earlyLiteracyTests(HttpSession session,Model model) {
		return new ModelAndView("Student/early_literacy_tests");
	}
	@RequestMapping(value = "/goForAssignment")
	public ModelAndView goForAssignment(
			@RequestParam("setsArray") ArrayList<String> setsArray,
			@RequestParam("setNameArray") ArrayList<String> setNameArray,
			@RequestParam("setType") String setType,
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("dialogDivId") String dialogDivId,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("recordTime") int recordTime,
			@RequestParam("eltTypeId") long eltTypeId,
			HttpSession session,
			Model model) {
		try{
			model.addAttribute("setsArray",setsArray);
			model.addAttribute("studentAssignmentId",studentAssignmentId);
			model.addAttribute("setNameArray",setNameArray);
			model.addAttribute("setType",setType);
			model.addAttribute("dialogDivId",dialogDivId);
			model.addAttribute("assignmentId",assignmentId);
			model.addAttribute("recordTime",recordTime);
			model.addAttribute("eltTypeId", eltTypeId);
			model.addAttribute("earlyLiteracyTestsForm",new EarlyLiteracyTestsForm());
		}
		catch(Exception e){
			logger.error("Error in goForAssignment() of EarlyLiteracyTestsController" + e.getStackTrace());
		}
		return new ModelAndView("Ajax/Student/assignment_reading_test");
	}
	
	@RequestMapping(value = "/recordAssessment", method = RequestMethod.POST)
	public View  recordAssessment(HttpServletResponse response,@ModelAttribute("earlyLiteracyTestsForm") EarlyLiteracyTestsForm earlyLiteracyTestsForm, BindingResult result,HttpSession session,HttpServletRequest request) {
		try{
			earlyLiteracyTestsService.saveAudiorRecord(earlyLiteracyTestsForm.getSetName(),earlyLiteracyTestsForm.getAudioData(),earlyLiteracyTestsForm.getContent(),earlyLiteracyTestsForm.getSetType(),earlyLiteracyTestsForm.getAssignmentId());
		}
		catch(Exception e){
			logger.error("Error in saveAudiorRecord() of EarlyLiteracyTestsController" + e.getStackTrace());
		}
		boolean success = false;
		try{
			if(earlyLiteracyTestsForm.isLastValue()){
				if(earlyLiteracyTestsForm.getEltTypeId() == 0){						
					success = earlyLiteracyTestsService.updateTestStatus(earlyLiteracyTestsForm);						
				}
				else{
					success = earlyLiteracyTestsService.submitAndAssignNextELTest(earlyLiteracyTestsForm);						
				}
			}
		}			
		catch(Exception e){
			logger.error("Error in second block code recordAssessment() of EarlyLiteracyTestsController" + e.getStackTrace());
		}
		if(success){
			earlyLiteracyTestsForm.setResult("Test Completed Successfully !!");	
		}else{
			earlyLiteracyTestsForm.setResult("Error Occured !!");	
		}
		return new MappingJackson2JsonView();
	}
}
