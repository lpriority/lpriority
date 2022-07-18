package com.lp.teacher.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.lp.common.dao.AssessmentDAO;
import com.lp.common.service.CurriculumService;
import com.lp.model.Assignment;
import com.lp.model.AssignmentType;
import com.lp.model.BpstGroups;
import com.lp.model.BpstTypes;
import com.lp.model.ClassStatus;
import com.lp.model.Grade;
import com.lp.model.Language;
import com.lp.model.PhonicGroups;
import com.lp.model.PhonicSections;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.teacher.service.AssignPhonicSkillTestService;
import com.lp.utils.WebKeys;

@Controller
public class AssignPhonicSkillTestController {
	
	@Autowired 
	private AssignPhonicSkillTestService assignPhonicSkillTestService;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private AssessmentDAO assessmentDAO;
	
	static final Logger logger = Logger.getLogger(AssignPhonicSkillTestController.class);
	
	@RequestMapping("/phonicSkillTest")
	public ModelAndView phonicSkillTest(HttpSession session) {
		ModelAndView model = new ModelAndView("Teacher/assign_phonic_skill_test", "assignment",
				new StudentAssignmentStatus());
		try{	
				model.addObject("tab",WebKeys.LP_TAB_PHONIC_SKILL_TEST);
				model.addObject("page",WebKeys.LP_TAB_ASSIGN_PHONIC);
				model.addObject("usedFor",WebKeys.LP_USED_FOR_RTI);
				Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
				model.addObject("teacherId", teacher.getTeacherId());
				List<Grade> teacherGrades = new ArrayList<Grade>();
				
				teacherGrades = curriculumService.getTeacherGrades(teacher);		
				List<Language> langList=assignPhonicSkillTestService.getLanguages();
				
				model.addObject("teacherGrades", teacherGrades);
				model.addObject("langList",langList);
		}catch(Exception e){
			logger.error("Error in phonicSkillTest() of AssignPhonicSkillTestController "+ e);
			model.addObject("hellowAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping(value = "/getPhonicTestGroups", method = RequestMethod.GET)
	public ModelAndView getPhonicTestGroups(@RequestParam("csId") String csId,
			@RequestParam("langId") long langId,HttpSession session,HttpServletRequest request) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/assign_phonic_sub", "assignment",
				new StudentAssignmentStatus());
		try{	
				if(langId==1){
				List<PhonicGroups> phonicGroupsLt  =  assignPhonicSkillTestService.getPhonicGroupsByLanguage(langId);
				List<PhonicSections> PhonicSectionsLt  =  assignPhonicSkillTestService.getPhonicSectionsByLanguage(langId);	
				model.addObject("phonicGroupsLt", phonicGroupsLt);
				model.addObject("PhonicSectionsLt", PhonicSectionsLt);
				model.addObject("langId",langId);
		}else{
			List<BpstTypes> bpstTypes=assignPhonicSkillTestService.getBpstTypes();
			model.addObject("bpstTypesList", bpstTypes);
			model.addObject("langId",langId);
		}
				
		}catch(Exception e){
			logger.error("Error in getPhonicTestGroups() of AssignPhonicSkillTestController "+ e);
			model.addObject("hellowAjax",e.getMessage());
		}
		return model;
	}
	
	@RequestMapping("/assignPhonicSkillsTest")
	public @ResponseBody void assignPhonicSkillsTest(
			@RequestParam("groupIdArray") ArrayList<Long> groupIdArray,
			@RequestParam("dueDate") String dueDateStr,
			@RequestParam("titleId") String titleId,
			@RequestParam("csId") long csId,
			@RequestParam("students") ArrayList<Long> students,
			@RequestParam("langId") long langId,
			HttpSession session,
			HttpServletResponse response) {
		try{
			DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
			Date currentDate = new Date();
			Date dueDate = new Date();
			dueDate = formatter.parse(dueDateStr);
			AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(WebKeys.PHONIC_SKILL_TEST);
			ClassStatus classStatus = new ClassStatus();
			classStatus.setCsId(csId);
			Assignment assignment = new Assignment();
			assignment.setClassStatus(classStatus);
			assignment.setAssignmentType(assignmentType);
			assignment.setDateDue(dueDate);
			assignment.setTitle(titleId);
			assignment.setDateAssigned(currentDate);
			assignment.setAssignStatus(WebKeys.ACTIVE);
			assignment.setUsedFor(WebKeys.RTI);
			
			if(!assignPhonicSkillTestService.checkTestAlreadyAssgined(assignment)){
				boolean success = assignPhonicSkillTestService.assignPhonicSkillsTest(assignment, groupIdArray,students,langId);
				if(success){
				 response.getWriter().write(WebKeys.ASSIGNED_SUCCESSFULLY);  
				}else{
				 response.getWriter().write(WebKeys.FAILED_TO_ASSIGNED);  
				}
			}else{
				 response.getWriter().write(WebKeys.TEST_ALREDAY_ASSGINED);  
			}
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/html");  
		}catch(Exception e){
			logger.error("Error in assignPhonicSkillsTest() of AssignPhonicSkillTestController "+ e);
		}

	}
	@RequestMapping(value = "/getBpstGroups", method = RequestMethod.GET)
	public ModelAndView getBpstGroups(@RequestParam("bpstTypeId") long bpstTypeId,
			HttpSession session,HttpServletRequest request) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/show_bpst_groups", "assignment",
				new StudentAssignmentStatus());
		try{	
				List<BpstGroups> bpstGroupsLt  =  assignPhonicSkillTestService.getBpstGroupsByBpstTypeId(bpstTypeId);
				List<PhonicSections> PhonicSectionsLt  =  assignPhonicSkillTestService.getPhonicSections(bpstGroupsLt);	
				model.addObject("bpstGroupsLt", bpstGroupsLt);
				model.addObject("PhonicSectionsLt", PhonicSectionsLt);
					
		}catch(Exception e){
			logger.error("Error in getBpstGroups() of AssignPhonicSkillTestController "+ e);
			model.addObject("hellowAjax",e.getMessage());
		}
		return model;
	}
	
}
