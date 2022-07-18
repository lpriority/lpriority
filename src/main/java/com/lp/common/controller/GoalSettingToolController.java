package com.lp.common.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.dao.GoalSettingToolDAO;
import com.lp.common.service.CommonService;
import com.lp.common.service.FileService;
import com.lp.common.service.GoalSettingToolService;
import com.lp.custom.exception.DataException;
import com.lp.model.CAASPPScores;
import com.lp.model.GoalSampleIdeas;
import com.lp.model.GoalStrategies;
import com.lp.model.Grade;
import com.lp.model.StarScores;
import com.lp.model.Student;
import com.lp.model.StudentCAASPPOwnStrategies;
import com.lp.model.StudentStarStrategies;
import com.lp.model.Teacher;
import com.lp.model.Trimester;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.service.StudentService;
import com.lp.teacher.service.IOLReportCardService;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class GoalSettingToolController {

	@Autowired
	HttpServletRequest request;
	@Autowired
	private GoalSettingToolService goalSettingToolService;
	@Autowired
	private IOLReportCardService iolReportCardService; 
	@Autowired 
	CommonService commonservice;
	@Autowired 
	SchoolAdminService schooladminservice;
	@Autowired
	GoalSettingToolDAO goalSettingToolDao;
	@Autowired
	TeacherService teacherService;
	@Autowired
	AdminSchedulerDAO adminSchedulerdao;
	@Autowired
	ParentService parentService;
	@Autowired
	StudentService studentService;
	@Autowired
	private FileService fileService; 
	

	static final Logger logger = Logger.getLogger(GoalSettingToolController.class);

	@RequestMapping(value = "/goToGoalSettingTool", method = RequestMethod.POST)
	public ModelAndView goToGoalSettingTool(HttpSession session,@RequestParam("studentId") long studentId,@RequestParam("tab") String tab) {
		ModelAndView model=null;
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			model = new ModelAndView("Ajax/CommonJsp/menu_main_page");
			model.addObject("userType",userReg.getUser().getUserType());
			model.addObject("studentId",studentId);
			Student student=iolReportCardService.getStudent(studentId);
			model.addObject("gradeId",student.getGrade().getGradeId());
			if(student.getUserRegistration().getParentRegId() != null){
				long parentRedId = student.getUserRegistration().getParentRegId();
				model.addObject("parentRedId",parentRedId);
			}
			model.addObject("userTypeId",userReg.getUser().getUserTypeid());
			model.addObject("tab",tab);
		}catch(Exception e){
			logger.error("Error in goToGoalSettingTool() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	@RequestMapping(value = "/goToGoalReadingPage", method = RequestMethod.POST)
	public ModelAndView goToGoalReadingPage(HttpSession session,@RequestParam("caasppTypesId") long caasppTypesId,
			@RequestParam("studentId") long studentId,@RequestParam("starCaasppTypeId") long starCaasppTypeId) {		
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/goal_reading_page");
        List<CAASPPScores> studCaaSppScores=new ArrayList<CAASPPScores>();
        List<GoalStrategies> listGoalStrategies=new ArrayList<GoalStrategies>();
        List<StarScores> listStarScores=new ArrayList<StarScores>();
        List<Trimester> trimesterList=new ArrayList<Trimester>();
		try{			
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Student student = iolReportCardService.getStudent(studentId);
			studCaaSppScores=goalSettingToolService.getStudentCAASPPScores(caasppTypesId,student.getStudentId());
			model.addObject("studGoalScores",studCaaSppScores);
			listStarScores=goalSettingToolService.getStudentStarScores(starCaasppTypeId,studentId,student.getGrade().getGradeId());
			model.addObject("studStarReadingScores",listStarScores);
			listGoalStrategies=goalSettingToolService.getGoalStrategiesByTypeId(starCaasppTypeId);
			model.addObject("listGoalStrategies",listGoalStrategies);
			model.addObject("goalTypeId",caasppTypesId);
			model.addObject("starCaasppTypeId",starCaasppTypeId);
			model.addObject("studGradeId",student.getGrade().getMasterGrades().getMasterGradesId());
			model.addObject("userType",userReg.getUser().getUserType());
			trimesterList=goalSettingToolService.getTrimesterList();
			model.addObject("trimesterList",trimesterList);
			model.addObject("student",student);
			long maxStarMathScore=goalSettingToolService.getMAXStarScore(student.getStudentId(), student.getGrade().getGradeId(), starCaasppTypeId);
			maxStarMathScore=maxStarMathScore+1;
			model.addObject("starMax",maxStarMathScore);
			long maxOrderId=goalSettingToolDao.getMaxOrderIdByGradeId(student.getGrade().getGradeId(), starCaasppTypeId);
			model.addObject("maxOrderId",maxOrderId);				
		}catch(Exception e){
			logger.error("Error in goToGoalReadingPage() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	
	@RequestMapping(value = "/goToGoalCAASPPTestPage", method = RequestMethod.POST)
	public ModelAndView goToGoalCAASPPTestPage(HttpSession session,@RequestParam("studentId") long studentId) {
		long caasppScoresId=0;
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/caaspp_test_prep_page");
        List<CAASPPScores> studCAASPPScores=new ArrayList<CAASPPScores>();
       List<StudentCAASPPOwnStrategies> listStudOwnStrategies=new ArrayList<StudentCAASPPOwnStrategies>();
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			int caasppTypesId=1;
			Student student = iolReportCardService.getStudent(studentId);
			model.addObject("gradeId",student.getGrade().getGradeId());
			studCAASPPScores=goalSettingToolService.getStudentCAASPPScores(caasppTypesId,student.getStudentId());
			model.addObject("studGoalReadingScores",studCAASPPScores);
			if(studCAASPPScores.size()>0)
				caasppScoresId=studCAASPPScores.get(0).getCaaspp_scores_id();
			model.addObject("caasppScoresId",caasppScoresId);
			caasppTypesId=2;
			studCAASPPScores=goalSettingToolService.getStudentCAASPPScores(caasppTypesId,student.getStudentId());
			model.addObject("studGoalMathScores",studCAASPPScores);
			listStudOwnStrategies=goalSettingToolService.getStudentOwnStrategiesByTypeId(studentId,student.getGrade().getGradeId());
			model.addObject("listStudOwnStrategies",listStudOwnStrategies);
			model.addObject("studentId",studentId);
			model.addObject("student",student);
			model.addObject("userType",userReg.getUser().getUserType());				
		}catch(Exception e){
			logger.error("Error in goToGoalCAASPPTestPage() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	@RequestMapping(value = "/autoSaveStudStrategies", method = RequestMethod.POST)
	public View autoSaveStudStrategies(HttpSession session,
			@RequestParam("strategyId") long strategyId,
			@RequestParam("studentId") long studentId,
			@RequestParam("goalCount") long goalCount,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("trimesterId") long trimesterId,
			@RequestParam("caasppTypesId") long caasppTypesId,Model model) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		try{
			goalSettingToolService.autoSaveStudStarStrategies(strategyId,student,trimesterId,goalCount,caasppTypesId);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/goToGoalStrategies", method = RequestMethod.POST)
	public ModelAndView goToGoalStrategies(HttpSession session,@RequestParam("starCaasppTypesId") long starCaasppTypesId) {
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/goal_setting_strategies");
        List<GoalStrategies> listGoalStrategies=new ArrayList<GoalStrategies>();
       	try{			
			listGoalStrategies=goalSettingToolService.getGoalStrategiesByTypeId(starCaasppTypesId);
			model.addObject("listGoalStrategies",listGoalStrategies);
			if(starCaasppTypesId==3)
				model.addObject("gtype","Reading");
			else
				model.addObject("gtype","Math");				
		}catch(Exception e){
			logger.error("Error in goToGoalStrategies() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	
	@RequestMapping(value = "/autoSaveStudOwnStrategies", method = RequestMethod.POST)
	public View autoSaveStudStrategies(HttpSession session,
			@RequestParam("studentId") long studentId,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("studOwnStrategyDesc") String studOwnStrategyDesc,
			@RequestParam("goalCount") long goalCount,
			Model model) {
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		try{			
			goalSettingToolService.autoSaveStudOwnStrategies(student,studOwnStrategyDesc,goalCount);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getStarStrategies", method = RequestMethod.POST)
	public ModelAndView getStarStrategies(HttpSession session,@RequestParam("starCaasppTypesId") long starCaasppTypesId,@RequestParam("studentId") long studentId,
			@RequestParam("gradeId") long gradeId,@RequestParam("trimesterId") long trimesterId) {		
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/include_star_strategies");
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
        List<GoalStrategies> listGoalStrategies=new ArrayList<GoalStrategies>();
        List<StudentStarStrategies> listStudGoalStrategies=new ArrayList<StudentStarStrategies>();
       	try{       		
       		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
       		listGoalStrategies=goalSettingToolService.getGoalStrategiesByTypeId(starCaasppTypesId);
			model.addObject("listGoalStrategies",listGoalStrategies);
			listStudGoalStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(studentId,gradeId,trimesterId,starCaasppTypesId);
			model.addObject("listStudGoalStrategies",listStudGoalStrategies);
		    model.addObject("student",student);
		    if(starCaasppTypesId==3){
		    	model.addObject("id",5);
		        model.addObject("gtype","Reading");}
		    else{
		    	model.addObject("id",6);
		        model.addObject("gtype","Math");}
		    model.addObject("starCaasppTypesId",starCaasppTypesId);
		    model.addObject("userType",userReg.getUser().getUserType());
		    model.addObject("trimesterId",trimesterId);
		}catch(Exception e){
			logger.error("Error in getStarStrategies() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	@RequestMapping(value = "/printBoyReport", method = RequestMethod.POST)
	public ModelAndView printBoyReport(HttpSession session,@RequestParam("studentId") long studentId,@RequestParam("tab") String tab) {
		long caasppScoresId=0;
		ModelAndView model=null;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Student student=iolReportCardService.getStudent(studentId);
		if(tab.equalsIgnoreCase("goalReports")){
		 model = new ModelAndView("Ajax/Parent/child_goal_reports_main");
		 if(student.getUserRegistration().getParentRegId() != null){
				long parentRedId = student.getUserRegistration().getParentRegId();
				model.addObject("parentRedId",parentRedId);
			}
			model.addObject("userTypeId",userReg.getUser().getUserTypeid());
		}
		else{
		 model = new ModelAndView("Ajax/CommonJsp/boy_report");
		}
		String studName=student.getUserRegistration().getFirstName()+" "+student.getUserRegistration().getLastName();
		model.addObject("studName",studName);
		model.addObject("student",student);
        List<CAASPPScores> studGoalScores=new ArrayList<CAASPPScores>();
        StarScores starReadingScores=new StarScores();
        StarScores starMathScores=new StarScores();
        List<StudentStarStrategies> listReadingGoalStrategies=new ArrayList<StudentStarStrategies>();
        List<StudentStarStrategies> listMathGoalStrategies=new ArrayList<StudentStarStrategies>();
		try{
			int caasppTypeId=1,trimesterId=4,starTypeId=3;
			studGoalScores=goalSettingToolService.getStudentCAASPPScores(caasppTypeId,student.getStudentId());
			model.addObject("studGoalReadingScores",studGoalScores);
			starReadingScores=goalSettingToolService.getStudentStarScoresByTypeId(starTypeId, student.getStudentId(),student.getGrade().getGradeId(),trimesterId);
			model.addObject("studStarReadingBoyScore",starReadingScores);
		    listReadingGoalStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(student.getStudentId(),student.getGrade().getGradeId(),trimesterId,starTypeId);
			model.addObject("listReadingGoalStrategies",listReadingGoalStrategies);
			if(studGoalScores.size()>0)
				caasppScoresId=studGoalScores.get(0).getCaaspp_scores_id();
			 model.addObject("caasppScoresId",caasppScoresId);
			 caasppTypeId=2;starTypeId=4;
			studGoalScores=goalSettingToolService.getStudentCAASPPScores(caasppTypeId,student.getStudentId());
			model.addObject("studGoalMathScores",studGoalScores);
			starMathScores=goalSettingToolService.getStudentStarScoresByTypeId(starTypeId, student.getStudentId(),student.getGrade().getGradeId(),4);
			model.addObject("studStarMathBoyScore",starMathScores);
			listMathGoalStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(student.getStudentId(),student.getGrade().getGradeId(),trimesterId,starTypeId);
			model.addObject("listMathGoalStrategies",listMathGoalStrategies);
			model.addObject("userTypeId",userReg.getUser().getUserTypeid());
			model.addObject("currentAcademicYr", goalSettingToolService.getCurrentAcademicYr());
			model.addObject("trimesterId",trimesterId);
			model.addObject("tab",tab);
		}catch(Exception e){
			logger.error("Error in printBoyReport() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	@RequestMapping(value = "/printTrimesterReport", method = RequestMethod.POST)
	public ModelAndView printTrimesterReport(HttpSession session,@RequestParam("studentId") long studentId,@RequestParam("trimesterId") long trimesterId,
			@RequestParam("tab") String tab) {		
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/goal_trimester_report");
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Student student=iolReportCardService.getStudent(studentId);
		String studName=student.getUserRegistration().getFirstName()+" "+student.getUserRegistration().getLastName();
		model.addObject("studName",studName);
		model.addObject("student",student);
        List<StarScores> starReadingScores=new ArrayList<StarScores>();
        List<StarScores> lstStarReadScors=new ArrayList<StarScores>();
        List<StarScores> lstStarMathScors=new ArrayList<StarScores>();
        StarScores starReadingBoyScores=new StarScores();
        StarScores starMathBoyScores=new StarScores();
        List<StarScores> starMathScores=new ArrayList<StarScores>();
        List<Trimester> trimesterList=new ArrayList<Trimester>();
        List<StudentStarStrategies> listReadingGoalStrategies=new ArrayList<StudentStarStrategies>();
        List<StudentStarStrategies> listMathGoalStrategies=new ArrayList<StudentStarStrategies>();
		try{
			int caasppTypeId=3;
			long gradeId=student.getGrade().getGradeId();
			starReadingBoyScores=goalSettingToolService.getStudentStarScoresByTypeId(caasppTypeId, studentId,gradeId,4);
			model.addObject("studStarReadingBoyScore",starReadingBoyScores);
			starReadingScores=goalSettingToolService.getStudentStarTrimesterScores(caasppTypeId, studentId,gradeId);
			model.addObject("studStarReadingTrimesterScore",starReadingScores);
			listReadingGoalStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(studentId,gradeId,trimesterId,caasppTypeId);
			model.addObject("listReadingGoalStrategies",listReadingGoalStrategies);
			lstStarReadScors=goalSettingToolService.getStudentStarScores(caasppTypeId, studentId, gradeId);
			model.addObject("lstStarReadScors",lstStarReadScors);
			long maxStarReadScore=goalSettingToolService.getMAXStarScore(studentId, gradeId, caasppTypeId);			
			maxStarReadScore=maxStarReadScore+1;
			model.addObject("maxStarReadScore",maxStarReadScore);
			caasppTypeId=4;
			starMathBoyScores=goalSettingToolService.getStudentStarScoresByTypeId(caasppTypeId, studentId,gradeId,4);
			model.addObject("studStarMathBoyScore",starMathBoyScores);
			starMathScores=goalSettingToolService.getStudentStarTrimesterScores(caasppTypeId, studentId,gradeId);
			model.addObject("studStarMathTrimesterScore",starMathScores);
			listMathGoalStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(studentId,gradeId,trimesterId,caasppTypeId);
			model.addObject("listMathGoalStrategies",listMathGoalStrategies);
			lstStarMathScors=goalSettingToolService.getStudentStarScores(caasppTypeId, studentId, gradeId);
			model.addObject("lstStarMathScors",lstStarMathScors);
			model.addObject("userTypeId",userReg.getUser().getUserTypeid());
			model.addObject("trimesterId",trimesterId);
			model.addObject("tab",tab);
			trimesterList=goalSettingToolService.getTrimesterList();
			model.addObject("trimesterList",trimesterList);
			long maxStarMathScore=goalSettingToolService.getMAXStarScore(studentId, gradeId, caasppTypeId);
			maxStarMathScore=maxStarMathScore+1;
			model.addObject("maxStarMathScore",maxStarMathScore);			
		}catch(Exception e){
			logger.error("Error in printTrimesterReport() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	@RequestMapping(value = "/printGoalReminder", method = RequestMethod.POST)
	public ModelAndView printGoalReminder(HttpSession session,@RequestParam("studentId") long studentId) {
		long caasppScoresId=0;
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/goal_reminder");
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Student student=iolReportCardService.getStudent(studentId);
		String studName=student.getUserRegistration().getFirstName()+" "+student.getUserRegistration().getLastName();
		model.addObject("studName",studName);
        List<CAASPPScores> studGoalScores=new ArrayList<CAASPPScores>();
       	try{
			int caasppTypeId=1;
			studGoalScores=goalSettingToolService.getStudentCAASPPScores(caasppTypeId,student.getStudentId());
			model.addObject("studGoalReadingScores",studGoalScores);
			if(studGoalScores.size()>0)
				caasppScoresId=studGoalScores.get(0).getCaaspp_scores_id();
			model.addObject("caasppScoresId",caasppScoresId);
			caasppTypeId=2;
			studGoalScores=goalSettingToolService.getStudentCAASPPScores(caasppTypeId,student.getStudentId());
			model.addObject("studGoalMathScores",studGoalScores);
			model.addObject("userTypeId",userReg.getUser().getUserTypeid());
			String parentName = commonservice.getFullName(student.getUserRegistration().getParentRegId());
			model.addObject("parentName",parentName);	
		}catch(Exception e){
			logger.error("Error in printGoalReminder() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	@RequestMapping(value = "/goToSampleIdeas", method = RequestMethod.POST)
	public ModelAndView goToSampleIdeas(HttpSession session) {		
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/goal_sample_ideas");
        List<GoalSampleIdeas> listGoalSampleIdeas=new ArrayList<GoalSampleIdeas>();
       	try{
			listGoalSampleIdeas=goalSettingToolService.getGoalSampleIdeas();
			model.addObject("listGoalSampleIdeas",listGoalSampleIdeas);						
		}catch(Exception e){
			logger.error("Error in goToSampleIdeas() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	
	@RequestMapping(value = "/loadTrimesterList", method = RequestMethod.POST)
	public View loadGradeClasess(@RequestParam("gradeId") long gradeId,
			Model model, HttpSession session) {
		try {			
			HashMap<Long, String> reportsMap = new HashMap<Long, String>();
			List<Trimester> trimesterList=new ArrayList<Trimester>();
			trimesterList =goalSettingToolService.getTrimesterList();			
			for (Trimester tr : trimesterList) {
				reportsMap.put(tr.getTrimesterId(),tr.getTrimesterName());						
			}
			model.addAttribute("reportsMap", reportsMap);
		} catch (DataException e) {
			logger.error("Error in loadTrimesterList() of GoalSettingToolController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/getAllStudsGoalReportByReportId", method = RequestMethod.POST)
	public ModelAndView printAllGoalReport(HttpServletRequest request, HttpSession session,@RequestParam("gradeId") long gradeId,
			@RequestParam("trimesterId") long trimesterId, @RequestParam("classId") long classId, @RequestParam("csId") long csId) {
		ModelAndView model=null;
		model = new ModelAndView("Ajax/CommonJsp/goal_reports_main");
		String usersFilePath="signature/BOY_Report/"+gradeId+"";
		Map<Long, String> userSignMap=new HashMap<Long, String>();
		List<CAASPPScores> readCAASPPScoresList = new ArrayList<CAASPPScores>();
		List<CAASPPScores> mathCAASPPScoresList = new ArrayList<CAASPPScores>();
		List<StarScores> readStarScoresList=new ArrayList<StarScores>();
		List<StarScores> mathStarScoresList=new ArrayList<StarScores>();
		Map<String,List<StudentStarStrategies>> readStarStrategiesList = new HashMap<String,List<StudentStarStrategies>>();
		Map<String,List<StudentStarStrategies>> mathStarStrategiesList = new HashMap<String,List<StudentStarStrategies>>();
		List<Long> studentLst=goalSettingToolService.getStudentsByCsId(csId);
		List<Student> studentList = goalSettingToolService.getStudentsListByGradeIdandReportId(gradeId,trimesterId,studentLst);
		model.addObject("studentList",studentList);
		try{			
			int i=0;
			for (Student student : studentList) {
				String s="stud"+i;
				List<CAASPPScores> studGoalScores=new ArrayList<CAASPPScores>();
			    StarScores starReadingScores=new StarScores();
			    StarScores starMathScores=new StarScores();
			    List<StudentStarStrategies> listReadingGoalStrategies=new ArrayList<StudentStarStrategies>();
			    List<StudentStarStrategies> listMathGoalStrategies=new ArrayList<StudentStarStrategies>();
				int caasppTypeId=1,starTypeId=3;
				studGoalScores=goalSettingToolService.getStudentCAASPPScores(caasppTypeId,student.getStudentId());
				if(studGoalScores.size()>0)
					readCAASPPScoresList.add(studGoalScores.get(0));
				else
					readCAASPPScoresList.add(new CAASPPScores());	
				model.addObject("studGoalReadingScores",readCAASPPScoresList);
				starReadingScores=goalSettingToolService.getStudentStarScoresByTypeId(starTypeId, student.getStudentId(),student.getGrade().getGradeId(),trimesterId);
				readStarScoresList.add(starReadingScores);
				model.addObject("studStarReadingBoyScore",readStarScoresList);
			    listReadingGoalStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(student.getStudentId(),student.getGrade().getGradeId(),trimesterId,starTypeId);
				readStarStrategiesList.put(s,listReadingGoalStrategies);
				model.addObject("listReadingGoalStrategies",readStarStrategiesList);
				caasppTypeId=2;starTypeId=4;
				studGoalScores=goalSettingToolService.getStudentCAASPPScores(caasppTypeId,student.getStudentId());
				if(studGoalScores.size()>0)
					mathCAASPPScoresList.add(studGoalScores.get(0));
				else
					mathCAASPPScoresList.add(new CAASPPScores());					
				model.addObject("studGoalMathScores",mathCAASPPScoresList);
				starMathScores=goalSettingToolService.getStudentStarScoresByTypeId(starTypeId, student.getStudentId(),student.getGrade().getGradeId(),4);
				mathStarScoresList.add(starMathScores);
				model.addObject("studStarMathBoyScore",mathStarScoresList);
				listMathGoalStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(student.getStudentId(),student.getGrade().getGradeId(),trimesterId,starTypeId);
				mathStarStrategiesList.put(s,listMathGoalStrategies);
				model.addObject("listMathGoalStrategies",mathStarStrategiesList);
				if(usersFilePath.toString().length() > 0 && student.getUserRegistration().getParentRegId() != null && student.getUserRegistration().getParentRegId() > 0){
					UserRegistration userRe = schooladminservice.getUserRegistration(student.getUserRegistration().getParentRegId());
					String folderPath = FileUploadUtil.getUploadFilesPath(userRe, request);
					String path = folderPath+"/"+usersFilePath+"/"+student.getStudentId()+"/"+trimesterId+"/"+"sign.png";
					File file = new File(path);
					if(file.exists()) {
						if(request.getServerName().equalsIgnoreCase(WebKeys.LOCAL_HOST)){
							path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/loadDirectUserFile.htm?usersFilePath="+path;
						}else{
							path = request.getServerName()+":"+request.getServerPort()+"/loadDirectUserFile.htm?usersFilePath="+path;
						}
						userSignMap.put(student.getStudentId(), path);	
					}else{
						userSignMap.put(student.getStudentId(),"");
						
					}
			   }
				i=i+1;
			}
			model.addObject("gradeId",gradeId);
		    model.addObject("userSignMap",userSignMap);
		    model.addObject("currentAcademicYr", goalSettingToolService.getCurrentAcademicYr());
	    }catch(Exception e){
			logger.error("Error in printAllGoalReport() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	
	@RequestMapping(value = "/getAllStudsTrimesterReports", method = RequestMethod.POST)
	public ModelAndView printAllStudsTrimesterReports(HttpSession session,@RequestParam("gradeId") long gradeId,
			@RequestParam("trimesterId") long trimesterId,@RequestParam("classId") long classId,@RequestParam("csId") long csId) {
		ModelAndView model=null;
	    String usersFilePath="signature/Trimester_Report/"+gradeId+"";
		model = new ModelAndView("Ajax/CommonJsp/print_all_trimester_reports");		
		List<Trimester> trimesterList=new ArrayList<Trimester>();
		Map<String,List<StudentStarStrategies>> readStarStrategiesList = new HashMap<String,List<StudentStarStrategies>>();
		Map<String,List<StudentStarStrategies>> mathStarStrategiesList = new HashMap<String,List<StudentStarStrategies>>();
		Map<String,List<StarScores>> allStarReadScs = new HashMap<String,List<StarScores>>();
		Map<String,List<StarScores>> allStarMathScs = new HashMap<String,List<StarScores>>();
		Map<Long, String> userSignMap=new HashMap<Long, String>();
		Map<String,Long> starMaxReadScores=new HashMap<String, Long>();
		Map<String,Long> starMaxMathScores=new HashMap<String, Long>();		
	    List<StarScores> starReadingScores=new ArrayList<StarScores>();
	    Map<String,List<StarScores>> lstStarReadScors=new HashMap<String,List<StarScores>>();
        Map<String,List<StarScores>> lstStarMathScors=new HashMap<String,List<StarScores>>();
        List<StarScores> starReadingBoyScores=new ArrayList<StarScores>();
        List<StarScores> starMathBoyScores=new ArrayList<StarScores>();
        List<StarScores> starMathScores=new ArrayList<StarScores>();	                
	    List<Long> studentLst=goalSettingToolService.getStudentsByCsId(csId);   		
		List<Student> studentList = goalSettingToolService.getStudentsListByGradeIdandReportId(gradeId,trimesterId,studentLst);
		model.addObject("studentList",studentList);
		try{
			int i=0;
			for (Student student : studentList) {
				String s="stud"+i;		
				StarScores readBoyScore=new StarScores();
			    StarScores mathBoyScore=new StarScores();
			    List<StarScores> starReadTriScore=new ArrayList<StarScores>();
			    List<StarScores> starMathTriScore=new  ArrayList<StarScores>();
			    List<StudentStarStrategies> listReadingGoalStrategies=new ArrayList<StudentStarStrategies>();
			    List<StudentStarStrategies> listMathGoalStrategies=new ArrayList<StudentStarStrategies>();
						
				int starCaasppTypesId=3;
				readBoyScore=goalSettingToolService.getStudentStarScoresByTypeId(starCaasppTypesId, student.getStudentId(),gradeId,4);
				starReadingBoyScores.add(readBoyScore);
				model.addObject("studStarReadingBoyScore",starReadingBoyScores);
				starReadTriScore=goalSettingToolService.getStudentStarTrimesterScores(starCaasppTypesId, student.getStudentId(), gradeId);
				lstStarReadScors.put(s,starReadTriScore);
				model.addObject("studStarReadingTrimesterScore",lstStarReadScors);
				listReadingGoalStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(student.getStudentId(),student.getGrade().getGradeId(),trimesterId,starCaasppTypesId);
				readStarStrategiesList.put(s,listReadingGoalStrategies);
				model.addObject("listReadingGoalStrategies",readStarStrategiesList);
				starReadingScores=goalSettingToolService.getStudentStarScores(starCaasppTypesId, student.getStudentId(), gradeId);
				allStarReadScs.put(s, starReadingScores);
				model.addObject("allStarReadScs",allStarReadScs);
				long maxStarReadScore=goalSettingToolService.getMAXStarScore(student.getStudentId(), gradeId, starCaasppTypesId);
				maxStarReadScore=maxStarReadScore+1;
				starMaxReadScores.put(s,maxStarReadScore);
				model.addObject("starMaxRead",starMaxReadScores);
				
				starCaasppTypesId=4;
				mathBoyScore=goalSettingToolService.getStudentStarScoresByTypeId(starCaasppTypesId, student.getStudentId(),gradeId,4);
				starMathBoyScores.add(mathBoyScore);
				model.addObject("studStarMathBoyScore",starMathBoyScores);
				starMathTriScore=goalSettingToolService.getStudentStarTrimesterScores(starCaasppTypesId, student.getStudentId(),gradeId);
				lstStarMathScors.put(s,starMathTriScore);
				model.addObject("studStarMathTrimesterScore",lstStarMathScors);
				listMathGoalStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(student.getStudentId(),student.getGrade().getGradeId(),trimesterId,starCaasppTypesId);
				mathStarStrategiesList.put(s,listMathGoalStrategies);
				model.addObject("listMathGoalStrategies",mathStarStrategiesList);
				starMathScores=goalSettingToolService.getStudentStarScores(starCaasppTypesId, student.getStudentId(), gradeId);
				allStarMathScs.put(s,starMathScores);
				model.addObject("allStarMathScs",allStarMathScs);
				long maxStarMathScore=goalSettingToolService.getMAXStarScore(student.getStudentId(), gradeId, starCaasppTypesId);
				maxStarMathScore=maxStarMathScore+1;
				starMaxMathScores.put(s,maxStarMathScore);
				model.addObject("starMaxMath",starMaxMathScores);
				logger.info(student.getUserRegistration().getParentRegId());
				if(usersFilePath.toString().length() > 0 && (student.getUserRegistration().getParentRegId()!= null && student.getUserRegistration().getParentRegId() > 0)){
					UserRegistration userRe = schooladminservice.getUserRegistration(student.getUserRegistration().getParentRegId());
					String folderPath = FileUploadUtil.getUploadFilesPath(userRe, request);
					String path = folderPath+"/"+usersFilePath+"/"+student.getStudentId()+"/"+trimesterId+"/"+"sign.png";
					File file = new File(path);
					if(file.exists()) {
						if(request.getServerName().equalsIgnoreCase(WebKeys.LOCAL_HOST)){
							path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/loadDirectUserFile.htm?usersFilePath="+path;
						}else{
							path = request.getServerName()+":"+request.getServerPort()+"/loadDirectUserFile.htm?usersFilePath="+path;
						}
						userSignMap.put(student.getStudentId(), path);
								
					}else{
						userSignMap.put(student.getStudentId(),"");
						
					}
			   }else{
				   userSignMap.put(student.getStudentId(),"");
			   }
				i=i+1;
			}
			model.addObject("userSignMap",userSignMap);
			model.addObject("trimesterId",trimesterId);
			trimesterList=goalSettingToolService.getTrimesterList();
			model.addObject("trimesterList",trimesterList);
		}catch(Exception e){
			logger.error("Error in printAllStudsTrimesterReports() of GoalSettingToolController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	
	@RequestMapping(value = "/masterPrintGoalReminder", method = RequestMethod.POST)
	public ModelAndView masterPrintGoalReminder(HttpSession session,@RequestParam("gradeId") long gradeId,
			@RequestParam("trimesterId") long trimesterId,@RequestParam("classId") long classId,@RequestParam("csId") long csId) {
		long caasppScoresId=0;
		List<String> parentNames=new ArrayList<String>();
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/master_print_goal_reminder");
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<CAASPPScores> readCAASPPScoresList = new ArrayList<CAASPPScores>();
		List<CAASPPScores> mathCAASPPScoresList = new ArrayList<CAASPPScores>();
		
		List<Long> studentLst=goalSettingToolService.getStudentsByCsId(csId);  
		List<Student> studentList = goalSettingToolService.getStudentsListByGradeIdandReportId(gradeId,trimesterId,studentLst);
		model.addObject("studentList",studentList);
		
		for (Student student : studentList) {
			List<CAASPPScores> studGoalScores=new ArrayList<CAASPPScores>();
		   	try{
				int goalTypeId=1;
				List<GoalSampleIdeas> listGoalSampleIdeas=new ArrayList<GoalSampleIdeas>();
				studGoalScores=goalSettingToolService.getStudentCAASPPScores(goalTypeId,student.getStudentId());
				if(studGoalScores.size()>0)
					readCAASPPScoresList.add(studGoalScores.get(0));
				else
					readCAASPPScoresList.add(new CAASPPScores());
				model.addObject("studGoalReadingScores",readCAASPPScoresList);
				if(studGoalScores.size()>0)
					caasppScoresId=studGoalScores.get(0).getCaaspp_scores_id();
				model.addObject("caasppScoresId",caasppScoresId);
				
				goalTypeId=2;
				studGoalScores=goalSettingToolService.getStudentCAASPPScores(goalTypeId,student.getStudentId());
				if(studGoalScores.size()>0)
					mathCAASPPScoresList.add(studGoalScores.get(0));
				else
					mathCAASPPScoresList.add(new CAASPPScores());
				model.addObject("studGoalMathScores",mathCAASPPScoresList);
				model.addObject("userTypeId",userReg.getUser().getUserTypeid());
				String parentName = commonservice.getFullName(student.getUserRegistration().getParentRegId());
				parentNames.add(parentName);
				model.addObject("parentNames",parentNames);
				listGoalSampleIdeas=goalSettingToolService.getGoalSampleIdeas();
				model.addObject("listGoalSampleIdeas",listGoalSampleIdeas);
			}catch(Exception e){
				logger.error("Error in masterPrintGoalReminder() of GoalSettingToolController"+ e);
				e.printStackTrace();
				model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
			}		
		}
		return  model;

	}

	@RequestMapping(value = "/goalSettingsAnalytics", method = RequestMethod.GET)
	public ModelAndView getStemFiveCs(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/goal_settings_reports");
		return model;
	}

	@RequestMapping(value = "/getGoalSettingsReport", method = RequestMethod.GET)
	public ModelAndView getGoalSettingsReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("caasppType") String caasppType) {
		List<StudentStarStrategies> sssLt = new ArrayList<StudentStarStrategies>();
		Map<String,Integer> sssSortedMap = new LinkedHashMap<String,Integer>();
		ModelAndView model = new ModelAndView("Ajax/Admin/goal_settings_reports_data");
		try {
			sssLt = goalSettingToolService.getStudentStarStrategies(caasppType);
			int i = 0;
			for (StudentStarStrategies studentStarStrategies : sssLt) {
				if(studentStarStrategies.getGoalStrategies().getGoalStrategiesId() > 0){
					if(!sssSortedMap.containsKey(studentStarStrategies.getGoalStrategies().getGoalStrategiesDesc())){
						i = 0;
						sssSortedMap.put(studentStarStrategies.getGoalStrategies().getGoalStrategiesDesc(), i+1);
					}else{
						i=i+1;
						sssSortedMap.put(studentStarStrategies.getGoalStrategies().getGoalStrategiesDesc(),  sssSortedMap.get(studentStarStrategies.getGoalStrategies().getGoalStrategiesDesc())+1);
					}
				}
			}
			model.addObject("sssSortedMap", sssSortedMap);
			model.addObject("noOfConQues", sssLt.size());
		} catch (Exception e) {
			logger.error("Error in getGoalSettingsReport() of STEMAnalyticsController"+ e);
			e.printStackTrace();
		}
		return model;
	}	
	@RequestMapping(value = "/goalSettingTeachersDetails", method = RequestMethod.GET)
	public ModelAndView goalSettingTeachersDetails(@RequestParam("gradeId") long gradeId,
			@RequestParam("schoolId") long schoolId, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/goal_setting_teachers_details");
		List<Teacher> teacherLt = teacherService.getTeachersByGradeId(gradeId, schoolId);
		model.addObject("teacherLt",teacherLt);
		return model;
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "/downloadAllToExcel", method = RequestMethod.GET)
	public View getAllStudentsAssessmentTest(@RequestParam("gradeId") long gradeId,
			@RequestParam("teacherId") long teacherId, Model model) {
		 Map<Long, List<Map<String, String>>> starScoresMap = new LinkedHashMap<Long, List<Map<String, String>>>();
		 Map<Long, List<Map<String, String>>> starStrategiesMap = new LinkedHashMap<Long, List<Map<String, String>>>();
		 Map<Long,List<CAASPPScores>> CAASPPScoresMap = new LinkedHashMap<Long,List<CAASPPScores>>();
		 List<StarScores> studentsLt = new ArrayList<StarScores>();;
		 List<Teacher> teacherLst= new ArrayList<Teacher>();
		 List<Map<String, String>> ssLt = null;
		 List<Map<String,String>> sssLt = null;
		 
	     if(teacherId > 0){
	    	 studentsLt = goalSettingToolService.getAllStudents(teacherId, gradeId);
	    	 Teacher teacher= adminSchedulerdao.getTeacher(teacherId);
	    	 teacherLst.add(teacher);
	     }
	     else{
		     teacherLst=  goalSettingToolService.getAllTeachers(gradeId);
		     for (Teacher teacher : teacherLst) {
		    	 List<StarScores> stdLt = goalSettingToolService.getAllStudents(teacher.getTeacherId(), gradeId);
		    	 studentsLt.addAll(stdLt);
			 }
	     }
	     
	     for(StarScores starScores : studentsLt) {
	    	 long studentId = starScores.getStudent().getStudentId();		    	 
	    	 List<StarScores> student_readingLt = goalSettingToolService.getStudentStarScoresByStudentId(studentId, gradeId);
	    	 Map<String, String> read = new LinkedHashMap<String, String>() {{ put("@4", ""); put("@1", ""); put("@2", "");  put("@3", "");}};
    		 Map<String, String> math = new LinkedHashMap<String, String>() {{ put("@4", ""); put("@1", ""); put("@2", "");  put("@3", "");}};          
	    	 ssLt = new ArrayList<Map<String, String>>();
    	 
	    	 for (StarScores ss : student_readingLt) {
	    		 long caasppTypesId = ss.getCaasppType().getCaasppTypesId();
	    		 long trimesterId = ss.getTrimester().getTrimesterId();
	    		 String score = ss.getScore().toString();
	    		 if(trimesterId == 4){
	    			 if(caasppTypesId == 3)
	    				 read.put("@4", score.toString());
	    			 else if(caasppTypesId == 4)
	    				 math.put("@4", score.toString());
	    		 }else if(trimesterId == 1){
	    			 if(caasppTypesId == 3)
	    				 read.put("@1", score.toString());
	    			 else if(caasppTypesId == 4)
	    				 math.put("@1", score.toString());
	    		 }else if(trimesterId == 2){
	    			 if(caasppTypesId == 3)
	    				 read.put("@2", score.toString());
	    			 else if(caasppTypesId == 4)
	    				 math.put("@2", score.toString());
	    		 }else if(trimesterId == 3){
	    			 if(caasppTypesId == 3)
	    				 read.put("@3", score.toString());
	    			 else if(caasppTypesId == 4)
	    				 math.put("@3", score.toString());
	    		 }
	    	 }
	    	 ssLt.add(read);
	    	 ssLt.add(math);
	    	 starScoresMap.put(studentId, ssLt);
	    	 List<StudentStarStrategies> student_sssLt = goalSettingToolService.getStudentStarStrategies(studentId, gradeId);
	    	 if(student_sssLt.size() > 0){
	    		 sssLt = new ArrayList<Map<String,String>>();
	    		 Map<String, String> readingMap = new LinkedHashMap<String, String>() {{ put("@4", ""); put("@1", ""); put("@2", "");  put("@3", "");}};
	    		 Map<String, String> mathMap = new LinkedHashMap<String, String>() {{ put("@4", ""); put("@1", ""); put("@2", "");  put("@3", "");}};
		    	 for (StudentStarStrategies sss : student_sssLt) {
		    		 int trimesterId = (int)sss.getTrimester().getTrimesterId();
		    		 long caasppTypesId = sss.getCaasppTypes().getCaasppTypesId();
		    		 String strategies ="";
		    		 if(caasppTypesId == 3){
			    		 if(readingMap.get("@"+String.valueOf(trimesterId)).length() > 0)
			    			 strategies = readingMap.get("@"+String.valueOf(trimesterId))+", "+Long.toString(sss.getGoalStrategies().getOrderId());
			    		 else
			    			 strategies = Long.toString(sss.getGoalStrategies().getOrderId());
		    		 }else if(caasppTypesId == 4){
		    			 if(mathMap.get("@"+String.valueOf(trimesterId)).length() > 0)
		    				 strategies = mathMap.get("@"+String.valueOf(trimesterId))+", "+Long.toString(sss.getGoalStrategies().getOrderId());
			    		 else
			    			 strategies = Long.toString(sss.getGoalStrategies().getOrderId());
		    		 }			    			 
		    		 if(trimesterId == 4){
			    		  if(caasppTypesId == 3)
			    			  readingMap.put("@4", strategies);
			    		  else if(caasppTypesId == 4)
			    			  mathMap.put("@4", strategies);
		    		 }else if(trimesterId == 1){
		    			 if(caasppTypesId == 3)
		    				 readingMap.put("@1", strategies);
		    			 else if(caasppTypesId == 4)
		    				 mathMap.put("@1", strategies);
		    		 }else if(trimesterId == 2){
		    			 if(caasppTypesId == 3)
		    				 readingMap.put("@2", strategies);
		    			 else if(caasppTypesId == 4)
		    				 mathMap.put("@2", strategies);
		    		 }else if(trimesterId == 3){
		    			 if(caasppTypesId == 3)
		    				 readingMap.put("@3", strategies);
		    			 else if(caasppTypesId == 4)
		    				 mathMap.put("@3", strategies);
		    		 }
		    	 }
		    	 sssLt.add(readingMap);
		    	 sssLt.add(mathMap);
		    	 starStrategiesMap.put(studentId,sssLt);
	    	 }	    	 
	    	 Grade grade = fileService.getGrade(gradeId);
	    	 List<CAASPPScores> CAASPPScoresLt = goalSettingToolService.getCAASPPScoresByStudentId(studentId, grade.getMasterGrades().getMasterGradesId());
	    	 CAASPPScoresMap.put(studentId, CAASPPScoresLt);
		 }
	     model.addAttribute("studentsLt", studentsLt);
	     model.addAttribute("starScoresMap", starScoresMap);
	     model.addAttribute("starStrategiesMap", starStrategiesMap);
	     model.addAttribute("CAASPPScoresMap", CAASPPScoresMap);
	     model.addAttribute("teacherLst", teacherLst);
	     model.addAttribute("teacherId", teacherId);
	     return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/viewChildGoalReports", method = RequestMethod.GET)
	public ModelAndView getChildIOLReportCard(HttpSession session) {
		ModelAndView model = new ModelAndView("Ajax/Parent/child_goal_reports");
		try {
			model.addObject("tab", WebKeys.LP_TAB_GOALREPORTS);
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			List<UserRegistration> childrenRegistrations = new ArrayList<UserRegistration>();
			List<Student> students = new ArrayList<Student>();
			childrenRegistrations = parentService.getchildren(userReg.getRegId());
			for (UserRegistration ur : childrenRegistrations) {
				Student student = studentService.getStudent(ur.getRegId());
				students.add(student);
			}
			model.addObject("students", students);
		} catch (DataException e) {
			logger.error("Error in getChildIOLReportCard() of GoalSettingToolController"
					+ e);
		}
		return model;
	}
}