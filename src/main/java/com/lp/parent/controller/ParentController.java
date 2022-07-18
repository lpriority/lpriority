package com.lp.parent.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.appadmin.service.AppAdminService;
import com.lp.appadmin.service.AppAdminService2;
import com.lp.model.Grade;
import com.lp.model.ParentLastseen;
import com.lp.model.SecurityQuestion;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.service.TakeAssessmentsService;
import com.lp.utils.WebKeys;

@Controller
public class ParentController extends WebApplicationObjectSupport{

	@Autowired
	private ParentService parentService;
	@Autowired
	private AppAdminService2 appAdminService2;
	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private HttpSession session;
	@Autowired
	private TakeAssessmentsService takeAssessmentsService;
	
	@RequestMapping(value = "/registerMyChild", method = RequestMethod.GET)
	public ModelAndView registerMyChild(HttpSession session,
		Map<String, Object> map) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		map.put("countryIds", appAdminService2.getCountries());
		map.put("securityQuestions", appAdminService.getSecurityQuestions());
		List<UserRegistration> childEmails = parentService.getUnregisteredChildren(userReg.getRegId());
		List<SecurityQuestion> seclist = appAdminService.getSecurityQuestions();
		ModelAndView model = new ModelAndView("Student/StudentRegistration1", "studentUserReg", new UserRegistration());
		model.addObject("studentlist", childEmails);
		model.addObject("seclist", seclist);
		List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
		model.addObject("schoolgrades", schoolgrades);
		model.addObject(WebKeys.USER_REGISTRATION_OBJ, userReg);
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getChildCurriculum", method = RequestMethod.GET)
	public ModelAndView displayStudentClassFiles(HttpSession session,
			HttpServletRequest request, Model model) {
		ParentLastseen parentLastseen = (ParentLastseen) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ);
		HashSet<String> hs = (HashSet<String>) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB);
		if(!hs.contains(WebKeys.LP_LESSONS)){
			String lastSeenTabs = parentLastseen.getLastSeenFeature();
			if(lastSeenTabs.equalsIgnoreCase("---")){
				lastSeenTabs = "";
			}
			lastSeenTabs = lastSeenTabs + WebKeys.LP_LESSONS + ";" ;
			parentLastseen.setLastSeenFeature(lastSeenTabs);
			hs.add(WebKeys.LP_LESSONS);
			session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ, parentLastseen);
			session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB, hs);
		}
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		model.addAttribute("childs", parentService.getStudentByParent(userReg.getRegId()));
		model.addAttribute("tab", WebKeys.LP_TAB_LESSON);
		model.addAttribute("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
		return new ModelAndView("Student/assigned_curriculum");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ParentChildCurriculum", method = RequestMethod.GET)
	public ModelAndView ParentChildCurriculum(HttpSession session,
			HttpServletRequest request, Model model) {
		ParentLastseen parentLastseen = (ParentLastseen) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ);
		HashSet<String> hs = (HashSet<String>) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB);
		if(!hs.contains(WebKeys.LP_LESSONS)){
			String lastSeenTabs = parentLastseen.getLastSeenFeature();
			if(lastSeenTabs.equalsIgnoreCase("---")){
				lastSeenTabs = "";
			}
			lastSeenTabs = lastSeenTabs + WebKeys.LP_LESSONS + ";" ;
			parentLastseen.setLastSeenFeature(lastSeenTabs);
			hs.add(WebKeys.LP_LESSONS);
			session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ, parentLastseen);
			session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB, hs);
		}
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		model.addAttribute("childs", parentService.getStudentByParent(userReg.getRegId()));
		model.addAttribute("tab", WebKeys.LP_TAB_LESSON);
		model.addAttribute("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
		return new ModelAndView("Ajax/Student/lesson_div");
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getChildCurrentHomeworks", method = RequestMethod.GET)
	public ModelAndView goToStudentHomeworksPage(HttpSession session, Model model) {
	
		ParentLastseen parentLastseen = (ParentLastseen) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ);
		HashSet<String> hs = (HashSet<String>) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB);
		if(!hs.contains(WebKeys.LP_HOMEWORKS)){
			String lastSeenTabs = parentLastseen.getLastSeenFeature();
			if(lastSeenTabs.equalsIgnoreCase("---")){
				lastSeenTabs = "";
			}
			lastSeenTabs = lastSeenTabs + WebKeys.LP_HOMEWORKS + ";" ;
			parentLastseen.setLastSeenFeature(lastSeenTabs);
			hs.add(WebKeys.LP_HOMEWORKS);
			session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ, parentLastseen);
			session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB, hs);
		}
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		model.addAttribute("childs", parentService.getStudentByParent(userReg.getRegId()));
		model.addAttribute("usedFor", WebKeys.LP_USED_FOR_HOMEWORKS);
		model.addAttribute("tab", WebKeys.LP_TAB_VIEW_CURRENT_HOMEWORK);
		return new ModelAndView("Student/take_assessments",
				"studentAssignmentStatus", new StudentAssignmentStatus());
	}
	
	@RequestMapping(value = "/getChildTests", method = RequestMethod.GET)
	public ModelAndView getChildTests(@RequestParam("studentId") long studentId, @RequestParam("usedFor") String usedFor) {
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/child_tests", "studentAssignmentStatus", new StudentAssignmentStatus());
		Student student = new Student();
		student.setStudentId(studentId);
		model.addObject("usedFor", usedFor);
		model.addObject("studentTests", takeAssessmentsService.getStudentTests(student, usedFor, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED));
		return model;
	}
	
	@RequestMapping(value = "/getChildHomeworkTests", method = RequestMethod.GET)
	public ModelAndView getChildHomeworks(@RequestParam("studentId") long studentId, @RequestParam("usedFor") String usedFor, @RequestParam("csId") long csId, @RequestParam("tab") String tab) {
		Student student = new Student();
		student.setStudentId(studentId);
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/student_tests", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("usedFor", usedFor);
		if(tab.equals(WebKeys.LP_TAB_VIEW_CURRENT_HOMEWORK)){
			model.addObject("studentTests", takeAssessmentsService.getStudentCurrentHomeworks(student, usedFor, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED, csId));
		}
		else{
			model.addObject("studentTests", takeAssessmentsService.getStudentDueHomeworks(student, usedFor, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED, csId));
		}
		return model;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/childAssessments", method = RequestMethod.GET)
	public ModelAndView goToStudentAssessmentsPage() {
		ParentLastseen parentLastseen = (ParentLastseen) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ);
		HashSet<String> hs = (HashSet<String>) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB);
		if(!hs.contains(WebKeys.LP_ASSESSMENTS)){
			String lastSeenTabs = parentLastseen.getLastSeenFeature();
			if(lastSeenTabs.equalsIgnoreCase("---")){
				lastSeenTabs = "";
			}
			lastSeenTabs = lastSeenTabs + WebKeys.LP_ASSESSMENTS + ";" ;
			parentLastseen.setLastSeenFeature(lastSeenTabs);
			hs.add(WebKeys.LP_ASSESSMENTS);
			session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ, parentLastseen);
			session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB, hs);
		}
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Student/take_assessments",
				"studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("childs", parentService.getStudentByParent(userReg.getRegId()));
		model.addObject("tab", WebKeys.LP_TAB_VIEW_ASSESSMENTS);
		return model;
	}
	
	@RequestMapping(value = "/showMyChildClass", method = RequestMethod.GET)
	public ModelAndView showMyChildClass() {
		ModelAndView model = new ModelAndView("Student/register_for_class");
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		model.addObject("schoolName", userReg.getSchool().getSchoolName());
		model.addObject("divId", "header");
		return new ModelAndView("Parent/my_child_classes");
	}	
	
	@RequestMapping(value = "/getAvailableClasses", method = RequestMethod.GET)
	public View getAvailableClasses(@RequestParam("gradeId") long gradeId,
			Model model, HttpSession session) throws Exception {
		model.addAttribute("availableClasses", adminService.getGradeClasses(gradeId));
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getAvailableTeachers", method = RequestMethod.GET)
	public View getAvailableTeachers(@RequestParam("gradeClassId") long gradeClassId,
			Model model, HttpSession session) throws Exception {
		model.addAttribute("availableTeachers", parentService.getAvailableTeachers(gradeClassId));
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getAvailableTeacherSections", method = RequestMethod.GET)
	public View getTeacherSections(@RequestParam("teacherId") long teacherId, @RequestParam("gradeClassId") long gradeClassId,
			Model model, HttpSession session) throws Exception {
		model.addAttribute("teacherSections", parentService.getTeacherSections(gradeClassId,teacherId));
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/getClassesByGrade", method = RequestMethod.GET)
	public View getClassesByGrade(@RequestParam("gradeId") long gradeId,
			Model model, HttpSession session) throws Exception {
		model.addAttribute("availableClasses", adminService.getClassesByGrade(gradeId));
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/childSTEMCurriculum", method = RequestMethod.GET)
	public ModelAndView childSTEMCurriculum(HttpSession session,
			HttpServletRequest request, Model model) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		model.addAttribute("childs", parentService.getStudentByParent(userReg.getRegId()));
		model.addAttribute("tab", WebKeys.LP_TAB_STEM);
		model.addAttribute("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
		return new ModelAndView("Ajax/Student/student_stem_curriculum_main");
	}
}
