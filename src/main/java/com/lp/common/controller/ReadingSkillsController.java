package com.lp.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.common.service.FileService;
import com.lp.common.service.ReadingSkillsService;
import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.FilesLP;
import com.lp.model.Grade;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.service.StudentService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

//@Author : Lalitha DT: 7/20/2016


@Controller
public class ReadingSkillsController {
	 @Autowired
	 HttpServletRequest request;
	 @Autowired
	 private AdminService adminservice;
	 @Autowired
	 private CurriculumService curriculumService;
	 @Autowired
	 private ReadingSkillsService readSkillsService;
	 @Autowired
	 private HttpSession session;
	 @Autowired
	 private ParentService parentService;
	 @Autowired
	 private CurriculumService cuService;
	 @Autowired 
	 private AdminService aService;
	 @Autowired
	 private CommonService cService;
	 @Autowired
	 private StudentService studentService;
	 @Autowired
	 private FileService fileService;
	 
	 static final Logger logger = Logger.getLogger(ReadingSkillsController.class);
	 
	 //RSDD stands for Reading Skills Development Dashboard
	 
	@RequestMapping(value = "/renameFiles", method = RequestMethod.GET)
	public ModelAndView moveRSDDFilesToStudentsFolder() {
		ModelAndView model = new ModelAndView("redirect:/index.htm");
		try{
			readSkillsService.renameFiles();
		}
		catch(Exception e){
			logger.error("error inside renameFiles() of ReadingSkillsController ");
		}
		return model;
	}
	@RequestMapping(value = "/expireCurrentFiles", method = RequestMethod.GET)
	public ModelAndView expireCurrentFiles() {
		ModelAndView model = new ModelAndView("redirect:/index.htm");
		try{
			readSkillsService.expireCurrentFiles();
		}
		catch(Exception e){
			logger.error("error inside renameFiles() of ReadingSkillsController ");
		}
		return model;
	}

	
	@RequestMapping(value="/goToStudentRSDD", method = RequestMethod.GET)
	public ModelAndView goToStudentRSDD(){
		ModelAndView model = new ModelAndView("Student/st_rsddfiles_page");
		try{
			Student student  = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			File[] studentRRDDFiles =  null;
			if(student.getStudentId() == 7202 || student.getStudentId() == 7203 || student.getStudentId() == 7204 || 
					student.getStudentId() == 7205 || student.getStudentId() == 7206 || student.getStudentId() == 7207 || 
					student.getStudentId() == 7208 || student.getStudentId() == 7209 || student.getStudentId() == 7210 || 
					student.getStudentId() == 7211 || student.getStudentId() == 7212 || student.getStudentId() == 7213 || 
					student.getStudentId() == 7214 || student.getStudentId() == 7215 || student.getStudentId() == 7216 || 
					student.getStudentId() == 7217 || student.getStudentId() == 7218 || student.getStudentId() == 7219 || 
					student.getStudentId() == 7220 || student.getStudentId() == 7221 || student.getStudentId() == 7222 || 
					student.getStudentId() == 7223 || student.getStudentId() == 7224 || student.getStudentId() == 7225 || 
					student.getStudentId() == 7226 || student.getStudentId() == 7227 || student.getStudentId() == 7228 || 
					student.getStudentId() == 7229 || student.getStudentId() == 7230 || student.getStudentId() == 7231)
			{
				studentRRDDFiles = readSkillsService.getSpecialDashboardFiles();
			}
			else{
				studentRRDDFiles = readSkillsService.getStudentRRDashboardFiles(student);
			}
			ArrayList<FilesLP> files = new ArrayList<FilesLP>();
			for(File file : studentRRDDFiles){
				if(file.isFile()){
					FilesLP fLp = new FilesLP();
					fLp.setFileName(file.getName());
					fLp.setFilePath(file.getAbsolutePath());
					fLp.setFileType(FilenameUtils.getExtension(file.getAbsolutePath()));
					files.add(fLp);
				}					
			}
			model.addObject("studentRRDDFiles", files);
		}
		catch(Exception e){
			logger.error("error inside goToStudentRSDD() of ReadingSkillsController ");
		}
		return model;		
	}
	@RequestMapping(value="/goToParentRSDD", method = RequestMethod.GET)
	public ModelAndView parentRRDDFiles(){
		ModelAndView model = new ModelAndView("Parent/pa_rsddfiles_page");
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			session.setAttribute("children", parentService.getStudentByParent(userReg.getRegId()));
		}
		catch(Exception e){
			logger.error("error inside goToParentRSDD() of ReadingSkillsController ");
		}	
		return model;
		
	}
	
	@RequestMapping(value="/goToAdminRSDD", method = RequestMethod.GET)
	public ModelAndView adminRRDDFiles(){
		ModelAndView model = new ModelAndView("Admin/ad_rsddfiles_page");
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			model.addObject("schoolGrades", aService.getSchoolGrades(userReg.getSchool().getSchoolId()));
		}
		catch(Exception e){
			logger.error("error inside goToAdminRSDD() of ReadingSkillsController ");
		}		
		return model;		
	}
	
	@RequestMapping(value="/goToTeacherRSDD", method = RequestMethod.GET)
	public ModelAndView teacherRRDDFiles(){
		ModelAndView model = new ModelAndView("Teacher/te_rsddfiles_page");
		try{
			Teacher teacher  = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			model.addObject("teacherGrades", cuService.getTeacherGrades(teacher));
		}
		catch(Exception e){
			logger.error("error inside goToTeacherRSDD() of ReadingSkillsController ");
		}	
		return model;		
	}
	
	@RequestMapping(value="/getAdminRSDashboards", method = RequestMethod.GET)
	public ModelAndView getAdminRSDashboards(@RequestParam("csId") long csId){
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/ad_te_include_rsddfiles");
		try{
			List<Student> students = cService.getStudentsBySection(csId);
			for(Student student : students){
				File[] studentRRDDFiles = readSkillsService.getStudentRRDashboardFiles(student);
				ArrayList<FilesLP> files = new ArrayList<FilesLP>();
				Arrays.sort(studentRRDDFiles,Collections.reverseOrder());
				for(File file : studentRRDDFiles){
					if(file.isFile()){
						FilesLP fLp = new FilesLP();
						fLp.setFileName(file.getName());
						fLp.setFilePath(file.getAbsolutePath());
						fLp.setFileType(FilenameUtils.getExtension(file.getAbsolutePath()));
						files.add(fLp);
					}					
				}
				student.setRsFiles(files);
			}
			model.addObject("studentRRDDFiles", students);
		}
		catch(Exception e){
			logger.error("error inside getAdminRSDashboards() of ReadingSkillsController ");
		}
		return model;	
	}
	@RequestMapping(value="/getParentRSDashboards", method = RequestMethod.GET)
	public ModelAndView getParentRSDashboards(@RequestParam("studentRegId") long studentRegId){
		ModelAndView model = new ModelAndView("Ajax/Student/st_rsddfiles_page");
		try{
			Student student = studentService.getStudent(studentRegId);
			File[] studentRRDDFiles = readSkillsService.getStudentRRDashboardFiles(student);
			ArrayList<FilesLP> files = new ArrayList<FilesLP>();
			for(File file : studentRRDDFiles){
				if(file.isFile()){
					FilesLP fLp = new FilesLP();
					fLp.setFileName(file.getName());
					fLp.setFilePath(file.getAbsolutePath());
					fLp.setFileType(FilenameUtils.getExtension(file.getAbsolutePath()));
					files.add(fLp);
				}					
			}
			model.addObject("studentRRDDFiles", files);
		}
		catch(Exception e){
			logger.error("error inside getParentRSDashboards() of ReadingSkillsController ");
		}
		return model;		
	}
	
	@RequestMapping(value="/moveNewFilesToRSDashboard", method = RequestMethod.GET)
	public ModelAndView moveNewFilesToRSDashboard(){
		String commonPath = FileUploadUtil.getLpCommonFilesPath();
		String zipFilePath =  commonPath + File.separator + "RSDashboardBackup2016";
		String destinationFilePath = FileUploadUtil.getReadingSkillsPath();
		try {
			fileService.moveFiles(zipFilePath, destinationFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return new ModelAndView("redirect:/index.htm");
	}	
	
	@RequestMapping(value = "/taskForceResults", method = RequestMethod.GET)
	public ModelAndView taskForceResults(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("CommonJsp/taskforce_results");
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_BENCHMARK_RESULTS);
		model.addObject("page", WebKeys.LP_TAB_TASKFORCE_RESULTS);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);		
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try {
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session
						.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGradesByAcademicYr(userReg
						.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGradesByAcademicYr(teacherObj);
				model.addObject("grList", teacherGrades);
				model.addObject("teacherObj", teacherObj);
			}
		} catch (DataException e) {
			logger.error("Error in taskForceResults() of ReadingSkillsController" + e);
			e.printStackTrace();
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}
		if (session.getAttribute("isError") != null) {
			model.addObject("isErro", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		if (session.getAttribute("helloAjax") != null) {
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		return model;
	}

	@RequestMapping(value = "/getTaskForceTitles", method = RequestMethod.POST)
	public View getTaskForceTitles(HttpSession session,
			@RequestParam("csId") long csId, @RequestParam("usedFor") String usedFor, 
			Model model) {
		try{
			HashMap<String, List<Assignment>> titles = new HashMap<String, List<Assignment>>();
			titles = readSkillsService.getTaskForceTitles(csId, usedFor);
			model.addAttribute("fluency", titles.get(WebKeys.ASSIGNMENT_TYPE_FLUENCY_READING));
			model.addAttribute("com", titles.get(WebKeys.ASSIGNMENT_TYPE_COMPREHENSION));
		}catch(Exception e){
			logger.error("Error in getTaskForceTitles() of ReadingSkillsController" + e);
		}		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getTaskForceResults", method = RequestMethod.POST)
	public ModelAndView getTaskForceResults(HttpSession session,
			@RequestParam("fluencyId") long fluencyId, @RequestParam("comId") long comId) {
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/taskforce_results_sub");
		try{
			model.addObject("studentList", readSkillsService.getTaskForceResults(fluencyId, comId));
		}catch(Exception e){
			logger.error("Error in taskForceResults() of ReadingSkillsController" + e);
		}		
		return model;
	}
}
