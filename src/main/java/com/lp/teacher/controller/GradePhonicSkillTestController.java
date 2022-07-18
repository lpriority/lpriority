package com.lp.teacher.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lp.common.service.CurriculumService;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.Grade;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.teacher.dao.AssignAssessmentsDAO;
import com.lp.teacher.service.GradeAssessmentsService;
import com.lp.teacher.service.GradePhonicSkillTestService;
import com.lp.utils.WebKeys;

@Controller
public class GradePhonicSkillTestController {
	
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	@Autowired 
	ServletContext servletContext;
	@Autowired
	private GradePhonicSkillTestService gradePhonicSkillTestService;
	@Autowired
	AndroidPushNotificationsService apns;
	@Autowired
	private AssignAssessmentsDAO assignAssessmentsDao;
	
	static final Logger logger = Logger.getLogger(GradePhonicSkillTestController.class);
	
	@RequestMapping("/gradePhonicSkill")
	public ModelAndView gradePhonicSkill(HttpSession session) {
		ModelAndView model = new ModelAndView("Teacher/grade_phonic_skill_test");
		try{
				List<Grade> teacherGrades = new ArrayList<Grade>();
				Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);				

				model.addObject("teacherId", teacher.getTeacherId());				
				model.addObject("tab",WebKeys.LP_TAB_PHONIC_SKILL_TEST);
				model.addObject("page",WebKeys.LP_TAB_GRADE_PHONIC);
				model.addObject("usedFor",WebKeys.LP_USED_FOR_RTI);
				teacherGrades = curriculumService.getTeacherGrades(teacher);
				model.addObject("teacherGrades", teacherGrades);
		}catch(Exception e){
			logger.error("Error in gradePhonicSkill() of GradePhonicSkillTestController "+ e);
			model.addObject("hellowAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping("/insertStudentsTestDetails")
	public ModelAndView insertStudentsTestDetails(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("userType") String userType,
			@RequestParam("regId") long regId,
			@RequestParam("studentId") long studentId,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("status") String status,
			@RequestParam("gradedStatus") String gradedStatus,
			@RequestParam("page") String page,
			@RequestParam("lastSavedSet") long lastSavedSet) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/grade_phonic_student_details");
		try{
				List<Grade> teacherGrades = new ArrayList<Grade>();
				Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
				teacherGrades = curriculumService.getTeacherGrades(teacher);
				String phonicSkillTestFilePath = 	WebKeys.PHONIC_TEST+File.separator+
													studentId+File.separator+
													assignmentId;
				
				model.addObject("studentAssignmentId", studentAssignmentId);
				model.addObject("phonicSkillTestFilePath", phonicSkillTestFilePath);
				model.addObject("teacherId", teacher.getTeacherId());
				model.addObject("teacherGrades", teacherGrades);
				model.addObject("status", status);
				model.addObject("gradedStatus", gradedStatus);
				model.addObject("tab",WebKeys.GRADE_PHONIC_SKILL_TEST);
				model.addObject("page", WebKeys.PHONIC_SKILL_TEST);
				model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
				model.addObject("studentRegId", regId);
				model.addObject("lastSavedSet", lastSavedSet);
		}catch(Exception e){
			logger.error("Error in insertStudentsTestDetails() of GradePhonicSkillTestController "+ e);
		}
		return model;
	}
	
	@RequestMapping("/submitStudentPhonicTestMarks")
	public @ResponseBody void submitStudentPhonicTestMarks(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("groupId") long groupId,
			@RequestParam("lastSavedSetId") long lastSavedSetId,
			@RequestParam("secmarks") int secmarks,
			@RequestParam("totalMarks") int totalMarks,
			@RequestParam("marksStr") String marksStr,
			@RequestParam("commentStr") String commentStr,
			HttpSession session,
			HttpServletResponse response) {
		try{
			boolean success = true;
			success = gradePhonicSkillTestService.submitStudentPhonicTestMarks(studentAssignmentId, groupId, lastSavedSetId, totalMarks, secmarks, marksStr, commentStr);
			 if(success){
				 StudentAssignmentStatus stAs=assignAssessmentsDao.getStudentAssignmentStatus(studentAssignmentId);
				 apns.sendStudentResultsNotification(WebKeys.LP_USED_FOR_RTI, stAs.getStudent().getStudentId());
				 response.getWriter().write(WebKeys.GRADED_SUCCESSFULLY);  
			 }else{
				response.getWriter().write(WebKeys.GRADING_FAILED);
			 }
			 	response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in submitStudentPhonicTestMarks() of GradePhonicSkillTestController "+ e);
		}
	}
	
}
