package com.lp.teacher.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.model.Grade;
import com.lp.model.Language;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.AssignPhonicSkillTestService;
import com.lp.teacher.service.GradeAssessmentsService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class PhonicTestReportsController {
	
	 @Autowired
	 private CurriculumService curriculumService;
	 @Autowired
	 private CommonService commonService;
	 @Autowired
	 private HttpServletRequest request;
	 @Autowired
     private SchoolAdminService schooladminservice;
	 @Autowired
	 private GradeAssessmentsService gradeAssessmentsService;
	 @Autowired 
	 private AssignPhonicSkillTestService assignPhonicSkillTestService;
	
	
	 static final Logger logger = Logger.getLogger(PhonicTestReportsController.class);

	@RequestMapping("/phonicTestSignleReports")
	public ModelAndView phonicTestSignleReports(HttpSession session) {
		ModelAndView model = new ModelAndView("Teacher/phonic_test_single_reports", "assignment",
				new StudentAssignmentStatus());
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try{
				List<Grade> teacherGrades = new ArrayList<Grade>();
				Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);				
				model.addObject("teacherId", teacher.getTeacherId());
				model.addObject("page", WebKeys.LP_TAB_PHONIC_SINGLE_REPORTS);		
				model.addObject("tab",WebKeys.LP_TAB_PHONIC_SKILL_TEST);
				model.addObject("usedFor",WebKeys.LP_USED_FOR_RTI);
				model.addObject("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));	
				teacherGrades = curriculumService.getTeacherGradesByAcademicYr(teacher);
				model.addObject("teacherGrades", teacherGrades);
		}catch(Exception e){
			logger.error("Error in phonicTestReports() of PhonicTestReportsController "+ e);
		}
		return model;
	}
	@RequestMapping("/phonicTestMultipleReports")
	public ModelAndView phonicTestMultipleReports(HttpSession session) {
		ModelAndView model = new ModelAndView("Teacher/phonic_test_multiple_reports", "assignment",
				new StudentAssignmentStatus());
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try{
				List<Grade> teacherGrades = new ArrayList<Grade>();
				Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);				
				model.addObject("teacherId", teacher.getTeacherId());
				model.addObject("page", WebKeys.LP_TAB_PHONIC_MULTIPLE_REPORTS);		
				model.addObject("tab",WebKeys.LP_TAB_PHONIC_SKILL_TEST);
				model.addObject("usedFor",WebKeys.LP_USED_FOR_RTI);
				model.addObject("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));	
				teacherGrades = curriculumService.getTeacherGradesByAcademicYr(teacher);
				model.addObject("teacherGrades", teacherGrades);
				List<Language> langList=assignPhonicSkillTestService.getLanguages();
				model.addObject("langList",langList);
		}catch(Exception e){
			logger.error("Error in phonicTestReports() of PhonicTestReportsController "+ e);
		}
		return model;
	}
	@RequestMapping("/playPhonicTestAudio")
	public  @ResponseBody void playPhonicTestAudio(
			@RequestParam("studentId") long studentId,
			@RequestParam("groupId") long groupId,
			@RequestParam("userType") String userType,
			@RequestParam("regId") long regId,
			@RequestParam("assignmentId") long assignmentId,
			HttpSession session,
			HttpServletResponse response) {
		try{
			UserRegistration userReg = schooladminservice
					.getUserRegistration(regId);
			String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
			String phonicSkillTestFilePath = 	uploadFilePath + File.separator+   
												WebKeys.PHONIC_TEST+File.separator+
												studentId+File.separator+
												assignmentId;
			String path  = phonicSkillTestFilePath+File.separator+groupId+File.separator+groupId+WebKeys.WAV_FORMAT;
			File f = new File(path);
			if(f.exists()){
				 response.getWriter().write(path);
			  }else{
				 response.getWriter().write("");
			  }
			  response.setCharacterEncoding("UTF-8");  
			  response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in playPhonicTestAudio() of PhonicTestReportsController "+ e);
		}
	}

	@RequestMapping(value = "/getAllStudentsAssessmentTest", method = RequestMethod.GET)
	public ModelAndView getAllStudentsAssessmentTest(HttpSession session,
			@RequestParam("assignmentId") long assignmentId) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/phonic_test_reports_details");
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<StudentAssignmentStatus> studentAssignmentStatusLt = gradeAssessmentsService.getStudentAssessmentTests(assignmentId);
		long langId = 0 ;
		long bpstTypeId = 0;
		if(studentAssignmentStatusLt.size() > 0){
			for (StudentAssignmentStatus studentAssignmentStatus : studentAssignmentStatusLt) {
				langId = studentAssignmentStatus.getStudentPhonicTestMarksList().get(0).getPhonicGroups().getPhonicSections().getLanguage().getLanguageId();
				if(langId == 2){
					bpstTypeId = studentAssignmentStatus.getStudentPhonicTestMarksList().get(0).getPhonicGroups().getBpstGroups().get(0).getBpstTypes().getBpstTypeId();
				}
			}
		}
		model.addObject("langId",langId);
		model.addObject("bpstTypeId",bpstTypeId);
		model.addObject("studentAssignmentStatusLt",studentAssignmentStatusLt);
		model.addObject("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));	
		return model;
	}
}
