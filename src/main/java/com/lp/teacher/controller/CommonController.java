package com.lp.teacher.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.ClassStatus;
import com.lp.model.CreateUnits;
import com.lp.model.Grade;
import com.lp.model.ParentLastseen;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.TeacherCommonService;
import com.lp.utils.WebKeys;

@Controller
public class CommonController extends WebApplicationObjectSupport {
	@Autowired
	private TeacherCommonService teacherCommonService;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private CommonService commonservice;
	@Autowired
	private AdminService adminservice;

	@RequestMapping(value = "/getTeacherGrades", method = RequestMethod.GET)
	public View getTeacherGrades(@RequestParam("academicYear") long academicYearId,
			Model model, HttpSession session) throws Exception {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		
		session.setAttribute("selectedYearId", academicYearId);
		AcademicYear academicYear = commonservice.getAcademicYearById(academicYearId);
		session.setAttribute("academicYear", academicYear);
		if (teacherObj == null) {
			// admin
			UserRegistration userReg = (UserRegistration) session
					.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			List<Grade> schoolgrades = adminservice.getSchoolGradesByAcademicYr(userReg
					.getSchool().getSchoolId());
			model.addAttribute("grList", schoolgrades);
		} else {
			// teacher
			teacherGrades = curriculumService.getTeacherGradesByAcademicYr(teacherObj);
			model.addAttribute("grList", teacherGrades);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getTeacherClasses", method = RequestMethod.GET)
	public View getTeacherClasses(@RequestParam("gradeId") long gradeId,
			Model model, HttpSession session) throws Exception {
		model.addAttribute("teacherClasses",
				curriculumService.getAssingedTeacherClasses(gradeId));
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/getUnitsByTeacherNAdmin", method = RequestMethod.GET)
	public ModelAndView getUnitsByTeacherNAdmin(
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId, HttpSession session)
			throws Exception {		
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		CreateUnits curriculum = teacherCommonService.getTeacherCurriculum(
				gradeId, classId, teacherObj);
	    ModelAndView model = new ModelAndView("Ajax/Teacher/include_curriculum");
		model.addObject("createUnits", curriculum);
		model.addObject("unitCount", curriculum.getUnits().size()-1);
		return model;
	}
	
	@RequestMapping(value = "/assignCurriculum", method = RequestMethod.POST)
	public @ResponseBody void  assignCurriculum(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@ModelAttribute CreateUnits createUnits,
			BindingResult result) throws Exception {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		if (userReg != null) {
			try {
				long csId = Long.parseLong(request.getParameter("sectionId"));
				Date dueDate = null;
				try {
					dueDate = new SimpleDateFormat("MM/dd/yyyy").parse(request
							.getParameter("dueDate"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				boolean status = teacherCommonService.assignCurriculum(csId,
						createUnits, dueDate);
				if (status) {
					response.getWriter().write( WebKeys.LP_ASSIGN_LESSONS_SUCCESS);  
				} else {
					response.getWriter().write( WebKeys.LP_ASSIGN_LESSONS_FAILED);  
				}
			} catch (DataException e) {
				logger.error("Error in assignCurriculum() of of CommonController"+ e);
				e.printStackTrace();
				response.getWriter().write( WebKeys.LP_ASSIGN_LESSONS_FAILED);  
				}
		}
		
	}

	@RequestMapping(value = "/getTeacherSections", method = RequestMethod.GET)
	public View getTeacherSections(HttpSession session, Teacher teacher,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId, Model model) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherSections",
				curriculumService.getTeacherSections(teacherObj, gradeId, classId));
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/goToAssignLessonsPage", method = RequestMethod.GET)
	public ModelAndView goToAssignLessonsPage(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		ModelAndView model = new ModelAndView("Teacher/assign_lessons",
				"createunits", new CreateUnits());
		model.addObject("tab", WebKeys.LP_TAB_ASSIGN_LESSONS);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			if (teacherObj == null) {
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
						.getSchool().getSchoolId());
				model.addObject("teacherGrades", schoolgrades);
			} else {
				// teacher
				List<Grade> teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("teacherGrades", teacherGrades);				
			}
		}catch(DataException e){
			model.addObject("hellowAjax",e.getMessage());
		}
		
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/getUserSections", method = RequestMethod.GET)
	public View getUserSections(HttpSession session, Teacher teacher,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId, Model model) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<ClassStatus> cs = new ArrayList<ClassStatus>();
		try{
			if(teacherObj!=null){
				cs = curriculumService.getTeacherSections(teacherObj, gradeId, classId);
			}else{
				cs = curriculumService.getAdminSections(gradeId, classId);
			}
		}catch(Exception e){
			
		}
		model.addAttribute("userSections", cs);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/viewParentLastSeen", method = RequestMethod.GET)
	public ModelAndView viewParentLastSeen(HttpSession session)
			throws Exception {			
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Teacher/parent_lastseen");
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
		teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("grList", teacherGrades);
		}catch(DataException e){
			logger.error("Error in viewParentLastSeen() of CommonController" + e);
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}	
	
	@RequestMapping(value = "/getParentLastSeen", method = RequestMethod.GET)
	public ModelAndView getParentLastSeen(@RequestParam("csId") long csId,HttpSession session) {
		List<ParentLastseen> parentLastseen = new ArrayList<ParentLastseen>();
		ModelAndView model = new ModelAndView("Ajax/Teacher/parent_lastseen_sub");
		try{
			parentLastseen = commonservice.getParentLastSeenWithStudent(csId);  
		}catch(DataException e){
			logger.error("Error in getParentLastSeen() of CommonController"+ e);
		}
		model.addObject("parentLastseen", parentLastseen);
		return model;
	}
	@RequestMapping(value = "/getTeacherSubjects", method = RequestMethod.GET)
	public View getTeacherSubjects(@RequestParam("gradeId") long gradeId,
			Model model, HttpSession session) throws Exception {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		long teacherId=teacherObj.getTeacherId();
		model.addAttribute("teacherClasses",curriculumService.getTeacherSubjects(gradeId, teacherId));
		return new MappingJackson2JsonView();
	}
}
