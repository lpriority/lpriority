package com.lp.parent.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;
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

import com.lp.common.service.DashboardService;
import com.lp.custom.exception.DataException;
import com.lp.model.ParentLastseen;
import com.lp.model.Student;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.service.StudentService;
import com.lp.utils.WebKeys;

@Controller
public class ParentFilesController extends WebApplicationObjectSupport{

	
	@Autowired
	private StudentService studentService;
	@Autowired
	private ParentService parentService;
	@Autowired
	ServletContext context; 
	@Autowired
	private DashboardService dashboardService;
	
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/displayParentClassFiles", method = RequestMethod.GET)
	public ModelAndView displayParentClassFiles(HttpSession session, HttpServletRequest request, Model model) {
		UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			ParentLastseen parentLastseen = (ParentLastseen) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ);
			HashSet<String> hs = (HashSet<String>) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB);
			if(!hs.contains(WebKeys.LP_FILE_TRANSFER)){
				String lastSeenTabs = parentLastseen.getLastSeenFeature();
				if(lastSeenTabs.equalsIgnoreCase("---")){
					lastSeenTabs = "";
				}
				lastSeenTabs = lastSeenTabs + WebKeys.LP_FILE_TRANSFER + ";" ;
				parentLastseen.setLastSeenFeature(lastSeenTabs);
				hs.add(WebKeys.LP_FILE_TRANSFER);
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ, parentLastseen);
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB, hs);
			}
			List<UserRegistration>	childrenRegistrations = new ArrayList<UserRegistration>();
			List<UserRegistration>	chRegistrations = new ArrayList<UserRegistration>();
			childrenRegistrations = parentService.getchildren(userRegistration.getRegId());	
			List<Student> studentLst=new ArrayList<Student>();
			
			for(UserRegistration ur:childrenRegistrations){
				Student student = studentService.getStudent(ur.getRegId());
				studentLst.add(student);
				ur.setGradeId(String.valueOf(student.getGrade().getGradeId()));
				chRegistrations.add(ur);
			}
			String title = null;
			String fileType = request.getParameter("fileType");		
			title = WebKeys.PUBLIC_FILES_PAGE_TITLE;
			model.addAttribute("fileType", fileType);
			model.addAttribute("title", title);		
			model.addAttribute("childrenList",chRegistrations);
			model.addAttribute("studentLst",studentLst);
		}catch(DataException e){
			logger.error("Error in displayParentClassFiles() of ParentFilesController"+ e);
			e.printStackTrace();
		}
		return new ModelAndView("Parent/parent_files");
	}
	
	@RequestMapping(value = "/getChildClasses", method = RequestMethod.GET)
	public ModelAndView getChildClasses(@RequestParam("studentId") long studentId, HttpSession session) throws Exception {
		Student student = new Student();
		student.setStudentId(studentId);
		ModelAndView model = new ModelAndView("Ajax/Parent/my_child_classes_div");
		try{
			model.addObject("childClasses", dashboardService.getStudentClasses(student));	
		}catch(DataException e){
			logger.error("Error in getChildClasses() of ParentFilesController"+ e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/getChildHomeworks", method = RequestMethod.GET)
	public ModelAndView getChildHomeworks(@RequestParam("studentId") long studentId, HttpSession session) throws Exception {
		Student student = new Student();
		student.setStudentId(studentId);
		ModelAndView model = new ModelAndView("Ajax/Parent/my_child_homeworks_div");
		try{
			model.addObject("childTests", dashboardService.getStudentAssignedTests(student));
		}catch(DataException e){
			logger.error("Error in getChildHomeworks() of ParentFilesController"+ e);
			e.printStackTrace();
		}	
		return model;
	}
	@RequestMapping(value = "/getChildAssessments", method = RequestMethod.GET)
	public ModelAndView getChildAssessments(@RequestParam("studentId") long studentId, HttpSession session) throws Exception {
		Student student = new Student();
		student.setStudentId(studentId);
		ModelAndView model = new ModelAndView("Ajax/Parent/my_child_assessments_div");
		try{
			model.addObject("childTests", dashboardService.getStudentAssignedTests(student));
		}catch(DataException e){
			logger.error("Error in getChildAssessments() of ParentFilesController"+ e);
			e.printStackTrace();
		}	
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/parentViewProgressReport", method = RequestMethod.GET)
	public ModelAndView parentViewProgressReport(HttpSession session) throws Exception {
		ModelAndView model = new ModelAndView("CommonJsp/view_progress_report_tabs");
		try{
			ParentLastseen parentLastseen = (ParentLastseen) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ);
			HashSet<String> hs = (HashSet<String>) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB);
			if(!hs.contains(WebKeys.LP_PROGRESS_REPORTS)){
				String lastSeenTabs = parentLastseen.getLastSeenFeature();
				if(lastSeenTabs.equalsIgnoreCase("---")){
					lastSeenTabs = "";
				}
				lastSeenTabs = lastSeenTabs + WebKeys.LP_PROGRESS_REPORTS + ";" ;
				parentLastseen.setLastSeenFeature(lastSeenTabs);
				hs.add(WebKeys.LP_PROGRESS_REPORTS);
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ, parentLastseen);
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB, hs);
			}
			model.addObject("tab", WebKeys.LP_TAB_PROGRESS_REPORTS);
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			
			List<UserRegistration>	childrenRegistrations = new ArrayList<UserRegistration>();
			List<Student> students = new ArrayList<Student>();
			childrenRegistrations = parentService.getchildren(userReg.getRegId());	
			for(UserRegistration ur:childrenRegistrations){
				Student student = studentService.getStudent(ur.getRegId());
				students.add(student);
			}
			model.addObject("students", students);
		}catch(DataException e){
			logger.error("Error in getChildHomeworks() of ParentFilesController"+ e);
			e.printStackTrace();
		}
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/parentViewProgress", method = RequestMethod.GET)
	public ModelAndView parentViewProgress(HttpSession session) throws Exception {
		ModelAndView model = new ModelAndView("Ajax/Parent/progress_reports_parent");
		try{
			ParentLastseen parentLastseen = (ParentLastseen) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ);
			HashSet<String> hs = (HashSet<String>) session.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB);
			if(!hs.contains(WebKeys.LP_PROGRESS_REPORTS)){
				String lastSeenTabs = parentLastseen.getLastSeenFeature();
				if(lastSeenTabs.equalsIgnoreCase("---")){
					lastSeenTabs = "";
				}
				lastSeenTabs = lastSeenTabs + WebKeys.LP_PROGRESS_REPORTS + ";" ;
				parentLastseen.setLastSeenFeature(lastSeenTabs);
				hs.add(WebKeys.LP_PROGRESS_REPORTS);
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ, parentLastseen);
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB, hs);
			}
			model.addObject("tab", WebKeys.LP_TAB_PROGRESS_REPORTS);
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			
			List<UserRegistration>	childrenRegistrations = new ArrayList<UserRegistration>();
			List<Student> students = new ArrayList<Student>();
			childrenRegistrations = parentService.getchildren(userReg.getRegId());	
			for(UserRegistration ur:childrenRegistrations){
				Student student = studentService.getStudent(ur.getRegId());
				students.add(student);
			}
			model.addObject("students", students);
		}catch(DataException e){
			logger.error("Error in getChildHomeworks() of ParentFilesController"+ e);
			e.printStackTrace();
		}
		return model;
	}
	
}
