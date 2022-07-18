package com.lp.student.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;

import com.lp.common.service.PerformanceTaskService;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.MathConversionTypes;
import com.lp.model.School;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentMathAssessMarks;
import com.lp.student.service.MathAssessmentTestService;
import com.lp.teacher.service.AssignAssessmentsService;
import com.lp.utils.WebKeys;

@Controller
public class MathAssessmentTestController  extends WebApplicationObjectSupport {
	
	static final Logger logger = Logger.getLogger(MathAssessmentTestController.class);
	
	@Autowired
	HttpServletRequest request;
	@Autowired
	private MathAssessmentTestService testService;
	@Autowired
	private PerformanceTaskService performanceTaskService;
	@Autowired
	AndroidPushNotificationsService apns;
	@Autowired
	AssignAssessmentsService assignAssessmentsService;
	
	@RequestMapping(value = "/getMathAssessmentQues", method = RequestMethod.GET)
	public ModelAndView getMathAssessmentQues(HttpSession session,
			Model model,
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("testCount") long testCount,
			@RequestParam("returnMessage") String returnMessage,
			@ModelAttribute School school) {
		List<StudentMathAssessMarks> mathQuizQuestions = Collections.emptyList();
		List<MathConversionTypes> mathConversionTypes = Collections.emptyList();
		try{
			mathQuizQuestions = testService.getMathAssessmentQues(studentAssignmentId);	
			mathConversionTypes = testService.getMathConversionTypes();
		}
		catch(Exception e){
			logger.error("Error in getMathAssessmentQues() of  MathAssessmentTestController" +e.getStackTrace());
		}
		if(session.getAttribute("status") != null){
			model.addAttribute("status", session.getAttribute("status"));
			session.removeAttribute("status");
		}
		model.addAttribute("mathQuizQuestions", mathQuizQuestions);
		model.addAttribute("mathConversionTypes", mathConversionTypes);
		model.addAttribute("testCount", testCount);
		model.addAttribute("returnMessage", returnMessage);
		StudentAssignmentStatus sAssignmentStatus = new StudentAssignmentStatus();
		try{
			sAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
		}
		catch(Exception e){
			logger.error("Error while calling getStudentAssignmentStatusById() in MathAssessmentTestController" +e.getStackTrace());
		}
		model.addAttribute("sAssignmentStatus",sAssignmentStatus);
		sAssignmentStatus.setMathAssessMarks(mathQuizQuestions);
		return new ModelAndView("Ajax/Student/math_assessment_test", "sAssignmentStatus", sAssignmentStatus);
	}
	
	@RequestMapping(value = "/submitMathAssessmentTest", method = RequestMethod.POST)
	public ModelAndView submitMathAssessmentTest(HttpSession session,
			@ModelAttribute("sAssignmentStatus") StudentAssignmentStatus sAssignmentStatus, HttpServletResponse response,ModelAndView model) {
		String returnMessage = "";
		long testCount = 0;
		try{
			boolean status = testService.submitMathTest(sAssignmentStatus);
			if(status){
				if(request.getParameter("testCount") != null)
					testCount = Long.parseLong(request.getParameter("testCount"));
					returnMessage = WebKeys.TEST_SUBMITTED_SUCCESS;
					apns.sendStudentResultsNotification(WebKeys.LP_USED_FOR_RTI, sAssignmentStatus.getStudent().getStudentId());
			}else{
				returnMessage = WebKeys.TEST_SUBMITTED_FAILED;
			}			         
			if(request.getServerName().equalsIgnoreCase(WebKeys.LOCAL_HOST)){
				model=new ModelAndView("redirect:/getMathAssessmentQues.htm?studentAssignmentId="+sAssignmentStatus.getStudentAssignmentId()+"&testCount="+testCount+"&returnMessage="+returnMessage);
			}else{
				model=new ModelAndView("redirect:https:/getMathAssessmentQues.htm?studentAssignmentId="+sAssignmentStatus.getStudentAssignmentId()+"&testCount="+testCount+"&returnMessage="+returnMessage);
			}
		}
		catch(Exception e){
			logger.error("Error in submitMathAssessmentTest() of  MathAssessmentTestController" +e.getStackTrace());
		}		
		return model;
	}
	@RequestMapping(value = "/autoSaveMathAnswer", method = RequestMethod.GET)
	public @ResponseBody void saveTest(
			@RequestParam("mathAssessMarksId") long mathAssessMarksId,
			@RequestParam("answer") String answer,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			StudentMathAssessMarks studMathMarks=new StudentMathAssessMarks();
			//boolean status = takeAssessmentsService.autoSaveAssignment(assignmentQuestionId,answer);
			studMathMarks=testService.getStudentMathAssessment(mathAssessMarksId);
			studMathMarks.setAnswer(answer);
			boolean status = testService.autoSaveMathAnswer(studMathMarks);
			 if(status)
				 response.getWriter().write("Saved.");  
			 else
				 response.getWriter().write("Not Saved."); 
		} catch (Exception e) {
			 response.getWriter().write("Erro, not saved.");  
			logger.error("Error in autoSaveAssignment() of of TakeAssessmentsController"
					+ e);
		}
	}
}
