package com.lp.admin.controller;


import java.io.File;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.admin.service.ReadRegReviewResultsService;
import com.lp.appadmin.service.AppAdminService3;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.Assignment;
import com.lp.model.BenchmarkResults;
import com.lp.model.District;
import com.lp.model.Grade;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.ReadRegAnswers;
import com.lp.model.ReadRegMaster;
import com.lp.model.ReadRegQuestions;
import com.lp.model.ReadRegReview;
import com.lp.model.RflpTest;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.dao.StudentReadRegDAO;
import com.lp.student.service.StudentReadRegService;
import com.lp.student.service.StudentService;
import com.lp.teacher.service.IOLReportCardService;
import com.lp.utils.WebKeys;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.lp.model.AcademicYear;


@Controller
public class ReadRegReviewResultsController {
	static final Logger logger = Logger.getLogger(ReadRegReviewResultsController.class);
	@Autowired
	private ReadRegReviewResultsService resultsService;
	@Autowired
	private ParentService parentService;
	@Autowired
	private CurriculumService curriculumService;	
	@Autowired
	private AdminService adminService;
	@Autowired 
	private HttpSession session;
	@Autowired
	private IOLReportCardService iolReportCardService;
	@Autowired
	private StudentReadRegService studentReadRegService;
	@Autowired
	private StudentReadRegDAO studentReadRegDAO;
	@Autowired
	private SchoolAdminService schoolAdminService;
	@Autowired
	private AppAdminService3 appadminservice3;
	@Autowired
	private CommonService commonservice;
	
	
	@RequestMapping(value = "/childReadingRegister", method = RequestMethod.GET)
	public ModelAndView childReadingRegister(HttpSession session, Model model,HttpServletRequest request) {
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			model.addAttribute("childs", parentService.getStudentByParent(userReg.getRegId()));
			
		}catch(DataException e){
			logger.error("Error in childReadingRegister() of StudentReadRegTempController"+ e);
		}
		return new ModelAndView("Parent/child_reading_register");
	}
	

	@RequestMapping(value = "/getRRStudents", method = RequestMethod.GET)
	public View getTeacherClasses(@RequestParam("gradeId") long gradeId, 
		Model model, HttpSession session) throws Exception {
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("students", resultsService.getRRStudents(gradeId, teacher.getTeacherId()));
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getStudentReadingRegister", method = RequestMethod.GET)
	public ModelAndView getStudentReadingRegister(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/admin_student_reading_register", "Grade", new Grade());
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
			if(teacherObj == null){
				//admin
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			}else{
				//teacher
				List<Grade>  teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
			model.addObject("divId", WebKeys.LP_TAB_PRR);
		}
		catch(Exception e){
			logger.error("Error in getStudentReadingRegister() of ReadRegReviewResultsController" +e);
		}
		return model;
	}
	
	@RequestMapping(value = "/getTeacherReadingRegister", method = RequestMethod.GET)
	public ModelAndView getTeacherReadingRegister(HttpSession session) {
		ModelAndView model = new ModelAndView("Student/common_reading_register", "Grade", new Grade());
		UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long masterGradeId = 1;
		try{	
			/*
			 * if
			 * (userRegistration.getSchool().getSchoolLevel().getSchoolLevelName().equals(
			 * WebKeys.SCHOOL_LEVEL_HIGH_SCHOOL)) { masterGradeId = 9; }
			 */
			long bookCount = studentReadRegService.getBookCount(masterGradeId, "");			
			List<ReadRegMaster> readRegMasterLt = studentReadRegService.getBooks(masterGradeId, WebKeys.RRFIRSTPAGE-1, WebKeys.RRROWCOUNT,  WebKeys.RR_COLUMN_CREATEDATE, WebKeys.SORTING_ORDER_DESC, "");
			model.addObject("firstPage", 1);
			long numberOfPages = bookCount/WebKeys.RRROWCOUNT;			
			if(bookCount%WebKeys.RRROWCOUNT >0)
				numberOfPages = numberOfPages+1;
			
			model.addObject("lastPage", numberOfPages>WebKeys.RRDISPLAYPAGES ? WebKeys.RRDISPLAYPAGES : numberOfPages);
			
			model.addObject("maxPages", numberOfPages);
			model.addObject("pageId", WebKeys.RRFIRSTPAGE);
			model.addObject("sortBy", WebKeys.RR_COLUMN_CREATEDATE);
			model.addObject("sortingOrder", WebKeys.SORTING_ORDER_DESC);
			model.addObject("readRegMasterLt", readRegMasterLt);
			
			model.addObject("pageId", 1);
			model.addObject("divId", WebKeys.LP_TAB_READING_REGISTER);
			model.addObject("user", "student");
			model.addObject("isNew", false);
			model.addObject("divId", WebKeys.LP_TAB_READING_REGISTER);
		}
		catch(Exception e){
			logger.error("Error in getStudentReadingRegister() of ReadRegReviewResultsController" +e);
		}
		return model;
	}
	
	@RequestMapping(value = "/bookApproval", method = RequestMethod.GET)
	public ModelAndView bookApproval(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/admin_book_approval", "Grade", new Grade());
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
			if(teacherObj == null){
				//admin
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			}else{
				//teacher
				List<Grade>  teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}

			model.addObject("sortBy", "approved");
			model.addObject("sortingOrder", WebKeys.SORTING_ORDER_DESC);
			model.addObject("divId", WebKeys.LP_TAB_BOOK_APPROVAL);
		}
		catch(Exception e){
			logger.error("Error in getStudentReadingRegister() of ReadRegReviewResultsController" +e);
		}
		return model;
	}
	
	/*@RequestMapping(value = "/getBooksToApprove", method = RequestMethod.GET)
	public View getBooksToApprove(long gradeId, Model model, HttpSession session){
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<ReadRegMaster> readRegMasterLt = null;
		long bookCount=0;
		try{
			
			if(teacherObj == null){
				//admin
				model.addAttribute("user", "admin");
				readRegMasterLt = resultsService.getAllAddedBooksByGrade(gradeId, 0);
				bookCount = studentReadRegService.getApprovalBookCount(gradeId, 0);
			}else{
				//teacher
				readRegMasterLt = resultsService.getAllAddedBooksByGrade(gradeId, teacherObj.getTeacherId());
				bookCount = studentReadRegService.getApprovalBookCount(gradeId, teacherObj.getTeacherId());
				model.addAttribute("user", "teacher");
			}
			model.addAttribute("readRegMasterLt", readRegMasterLt);
			model.addAttribute("firstPage", 1);
			model.addAttribute("lastPage", bookCount/10>10 ? 10 : bookCount/10);
			model.addAttribute("pageId", WebKeys.RRFIRSTPAGE);
		}
		catch(Exception e){
			logger.error("Error in getStudentReadingRegister() of ReadRegReviewResultsController" +e);
		}		
		return new MappingJackson2JsonView();
	}*/
	
	@RequestMapping(value = "/updateAprrovalStatus", method = RequestMethod.POST)
	public @ResponseBody void updateAprrovalStatus(long titleId, String status, Model model, HttpSession session, HttpServletResponse response){
		try{
			ReadRegMaster readRegMaster = studentReadRegService.getReadRegMasterByTitleId(titleId);
			if(status.equalsIgnoreCase(WebKeys.LP_APPROVED)) 
				readRegMaster.setApproved(WebKeys.LP_APPROVED);
			else if(status.equalsIgnoreCase(WebKeys.LP_REJECTED)) 
				readRegMaster.setApproved(WebKeys.LP_REJECTED);
			boolean resp = resultsService.updateAprrovalStatus(readRegMaster);
			if(resp) {
				if(status.equalsIgnoreCase(WebKeys.LP_APPROVED)) 
					response.getWriter().write(WebKeys.LP_APPROVED);
				else if(status.equalsIgnoreCase(WebKeys.LP_REJECTED)) 
					response.getWriter().write(WebKeys.LP_REJECTED);
			}else {
				response.getWriter().write("Error occured !!");  
			}
		}
		catch(Exception e){
			logger.error("Error in getStudentReadingRegister() of ReadRegReviewResultsController" +e);
		}		
	}
	
	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
	public @ResponseBody void deleteBook(long titleId, Model model, HttpSession session, HttpServletResponse response) {
		try {
			ReadRegMaster readRegMaster = studentReadRegService.getReadRegMasterByTitleId(titleId);
			boolean status = studentReadRegService.deleteBook(readRegMaster);	
	
			if(status) 
				response.getWriter().write(WebKeys.DELETED_SUCCESSFULLY);
			else 
				response.getWriter().write(WebKeys.LP_SYSTEM_ERROR);
		} catch (IOException e) {				
			e.printStackTrace();
		}  
	}
	
	@RequestMapping(value = "/getRRStudentsByGrade", method = RequestMethod.GET)
	public View admingetRRStudents(@RequestParam("gradeId") long gradeId,
			@RequestParam("gradedStatus") String gradedStatus,
		Model model, HttpSession session) throws Exception {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		AcademicYear academicYear=commonservice.getCurrentAcademicYear();
		if(userReg.getUser().getUserTypeid()==3){
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			//model.addAttribute("students", resultsService.getRRStudentsByYear(gradeId,userReg.getSchool().getAcademicYear(), teacherObj.getTeacherId(), gradedStatus));
			model.addAttribute("students", resultsService.getRRStudentsByYear(gradeId,academicYear, teacherObj.getTeacherId(), gradedStatus));
		}else
		model.addAttribute("students", resultsService.getRRStudentsByGrade(gradeId,academicYear, gradedStatus));
		return new MappingJackson2JsonView();
	}
	
	/*@RequestMapping(value = "/getGradedActivities", method = RequestMethod.POST)
	public ModelAndView getGradeSortActvities(Model model,@RequestParam("gradeId") long gradeId, @RequestParam("sortBy") String sortBy) {
		try{
			List<ReadRegActivityScore> studentActivities = resultsService.getGradedActivities(gradeId,sortBy);
			model.addAttribute("studentActivities", studentActivities);		
			model.addAttribute("classView", WebKeys.LP_YES);
		}catch(DataException e){
			logger.error("Error in getGradeSortActvities() of ReadRegReviewResultsController"+ e);
		}
		return new ModelAndView("Ajax/Student/personal_reading_register_div");
	}*/
	
	@RequestMapping(value = "/getGradedActivities", method = RequestMethod.POST)
	public ModelAndView getGradedActivities(HttpSession session,@RequestParam("gradeId") long gradeId,@RequestParam("sortBy") String sortBy,
			HttpServletResponse response, HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView("Ajax/Student/personal_reading_register_div");
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
//		startDate = userReg.getSchool().getAcademicYear().getStartDate();
//		endDate = userReg.getSchool().getAcademicYear().getEndDate();
		String startDate = commonservice.getCurrentAcademicYear().getStartDate().toString();
		String endDate = commonservice.getCurrentAcademicYear().getEndDate().toString();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		long teacherId = 0;
		if(teacher!=null)
			teacherId=teacher.getTeacherId();
		List<ReadRegActivityScore> studentActivities = resultsService.getGradedActivities(gradeId,sortBy,teacherId, startDate, endDate);
		model.addObject("studentActivities", studentActivities);
		model.addObject("classView",WebKeys.LP_YES);
		return model;
	
	}
	
	
	@RequestMapping(value = "/getRRReview", method = RequestMethod.GET)
	public ModelAndView getRRReview(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/reading_register_review", "Grade", new Grade());
		model.addObject("divId", WebKeys.LP_TAB_RR_REVIEW);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			if(teacherObj == null){
				//admin
				List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			}else{
				//teacher
				List<Grade>  teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
		}
		catch(Exception e){
			logger.error("Error in getRRReview() of ReadRegReviewResultsController" +e);
		}
		return model;
	}
	
	
	@RequestMapping(value = "/getActivitiesToBeReviewedByStudent", method = RequestMethod.GET)
	public ModelAndView getActivitiesToBeReviewedByStudent(HttpSession session, Model model,HttpServletRequest request, @RequestParam("studentId") long studentId, @RequestParam("sortBy") String sortBy,@RequestParam("gradeId") long gradeId) {
		try{
			List<ReadRegActivityScore> studentActivities =new ArrayList<ReadRegActivityScore>(); 
			//Student studentObj = studentService.getStudent(studentId);
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			AcademicYear academicYear=commonservice.getCurrentAcademicYear();
			if(studentId==0) {
				studentActivities = resultsService.getUnGradedActivities(gradeId,sortBy, academicYear);
			}else {
			  studentActivities = resultsService.getActivitiesToBeReviewed(studentId,academicYear,sortBy);
			}
			model.addAttribute("studentActivities", studentActivities);		
		}catch(DataException e){
			logger.error("Error in getActivitiesToBeReviewed() of ReadRegReviewResultsController"+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/reading_register_div");
	}
	
	
	@RequestMapping(value = "/getActivitiesToBeReviewedByGrade", method = RequestMethod.GET)
	public ModelAndView getActivitiesToBeReviewedByGrade(HttpSession session, Model model,HttpServletRequest request, 
			@RequestParam("gradeId") long gradeId, @RequestParam("sortBy") String sortBy) {
		List<ReadRegActivityScore> studentActivities=new ArrayList<ReadRegActivityScore>();
		AcademicYear academicYear=commonservice.getCurrentAcademicYear();
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		if(userReg.getUser().getUserTypeid()==3){
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			 studentActivities = resultsService.getUnGradedActivities(gradeId,sortBy,teacherObj.getTeacherId(),academicYear );
		}else{
			  studentActivities = resultsService.getUnGradedActivities(gradeId,sortBy, academicYear);
		}
			model.addAttribute("studentActivities", studentActivities);	
			model.addAttribute("classView", WebKeys.LP_YES);
		}catch(DataException e){
			logger.error("Error in getGradeSortActvities() of ReadRegReviewResultsController"+ e);
		}
		return new ModelAndView("Ajax/CommonJsp/reading_register_div");
	}
	
	
	@RequestMapping(value = "/openActivityDialog", method = RequestMethod.GET)
	public ModelAndView openActivityDialog(
			@RequestParam("readRegActivityScoreId") long readRegActivityScoreId,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		ModelAndView modelAndView = null; 
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			long userTypeId=userReg.getUser().getUserTypeid();
			model.addAttribute("userTypeId", userTypeId);
			model.addAttribute("rubrics", studentReadRegService.getReadRegRubric());
			ReadRegActivityScore activityScore = studentReadRegService.getStudentActivity(readRegActivityScoreId);
			model.addAttribute("bookApproveStatus",activityScore.getReadRegMaster().getApproved());
			if(activityScore.getGrade().getMasterGrades().getMasterGradesId() >= 3 && activityScore.getGrade().getMasterGrades().getMasterGradesId() < 13){
				model.addAttribute("showRubric", WebKeys.LP_YES);
			}
			else{
				model.addAttribute("showRubric", WebKeys.LP_NO);
			}
			model.addAttribute("activityScore", activityScore);
			if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_REVIEW_ACTIVITY_ID){
				ReadRegReview readRegReview = studentReadRegService.getReadRegReviewByStudentId(activityScore.getReadRegMaster().getTitleId(), activityScore.getStudent().getStudentId());
				model.addAttribute("readRegReview", readRegReview);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/grade_rr_review");
			}else if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_RETELL_ACTIVITY_ID){
				String ReadingRegisterFilePath = WebKeys.READING_REGISTER+File.separator+activityScore.getReadRegMaster().getTitleId()+File.separator+WebKeys.READING_TYPE_RETELL+WebKeys.WAV_FORMAT;
				model.addAttribute("usersFilePath", ReadingRegisterFilePath);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/grade_rr_retell");
			}else if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_UPLOAd_PIC_ACTIVITY_ID){
				String ReadingRegisterFilePath = WebKeys.READING_REGISTER+File.separator+activityScore.getReadRegMaster().getTitleId()+File.separator+WebKeys.LP_PICTURE+WebKeys.JPG_FORMAT;			
				model.addAttribute("usersFilePath", ReadingRegisterFilePath);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/grade_rr_picture");
			}else if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_CREATE_QUIZ_ACTIVITY_ID){
				List<ReadRegQuestions> readRegQuestions = resultsService.getReadRegQuestions(activityScore.getStudent().getStudentId(), activityScore.getReadRegMaster().getTitleId());
				model.addAttribute("readRegQuestions", readRegQuestions);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/grade_rr_create_a_quiz");
			}else if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_TAKE_QUIZ_ACTIVITY_ID){
				List<ReadRegAnswers> readRegAnswers = resultsService.getStudentQuiz(activityScore.getStudent().getStudentId(), activityScore.getReadRegMaster().getTitleId());
				model.addAttribute("readRegAnswers", readRegAnswers);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/grade_rr_take_a_quiz");
			}
						
		}catch(DataException e){
			logger.error("Error in openActivityDialog() of ReadRegReviewResultsController"+ e);
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/saveScore", method = RequestMethod.POST)
	public View saveScore(HttpSession session,
			Model model,
			HttpServletRequest request) {
		try{
			boolean stat=false;
			String teacherComment="";
			String bookApproveStat=request.getParameter("bookApproveStatus");
			String actApproveStat=request.getParameter("activityAppStat");
			long bookTitleId=Long.parseLong(request.getParameter("bookTitleId"));
			String approveId=request.getParameter("approveId");
			teacherComment=request.getParameter("teacherComment");
			ReadRegMaster readRegMas=studentReadRegService.getReadRegMasterByTitleId(bookTitleId);
			if(bookApproveStat.equalsIgnoreCase(WebKeys.LP_REJECTED))
			    readRegMas.setApproved(WebKeys.LP_REJECTED);
			else
				readRegMas.setApproved(WebKeys.LP_APPROVED);	
			
			System.out.println("bookApproveStat"+bookApproveStat+"actApproveStat="+actApproveStat);
			
			if(bookApproveStat.equalsIgnoreCase(WebKeys.LP_REJECTED)){
				readRegMas.setTeacherComment(teacherComment);
			}
			else{
			
			long readRegActivityScoreId = Long.parseLong(request.getParameter("readRegActivityScoreId"));
			long scoreId = Long.parseLong(request.getParameter("scoreId"));
			long score = Long.parseLong(request.getParameter("score"));
			long pageRange = Long.parseLong(request.getParameter("pageRange"));
			long activityValue = Long.parseLong(request.getParameter("activityValue"));
			stat  = resultsService.saveScore(scoreId, score, readRegActivityScoreId, pageRange, activityValue,actApproveStat,teacherComment,approveId);
			model.addAttribute("stat",stat);
			}
			boolean status = resultsService.updateAprrovalStatus(readRegMas);
			model.addAttribute("status", status);
			
		}catch(DataException e){
			logger.error("Error in saveScore() of ReadRegReviewResultsController"+ e);
		}
		return new MappingJackson2JsonView();
	}	
	
	@RequestMapping(value = "/openActivityScoreDialog", method = RequestMethod.GET)
	public ModelAndView openActivityScoreDialog(
			@RequestParam("readRegActivityScoreId") long readRegActivityScoreId,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		ModelAndView modelAndView = null; 
		try{
			ReadRegActivityScore activityScore = studentReadRegService.getStudentActivity(readRegActivityScoreId);
			model.addAttribute("activityScore", activityScore);
			if(activityScore.getGrade().getMasterGrades().getMasterGradesId() >= 3 && activityScore.getGrade().getMasterGrades().getMasterGradesId() < 13){
				model.addAttribute("showRubric", WebKeys.LP_YES);
			}
			else{
				model.addAttribute("showRubric", WebKeys.LP_NO);
			}
			if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_REVIEW_ACTIVITY_ID){
				ReadRegReview readRegReview = studentReadRegService.getReadRegReviewByStudentId(activityScore.getReadRegMaster().getTitleId(), activityScore.getStudent().getStudentId());
				model.addAttribute("readRegReview", readRegReview);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/rr_review_score");
			}else if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_RETELL_ACTIVITY_ID){
				// String ReadingRegisterFilePath = WebKeys.READING_REGISTER+File.separator+activityScore.getReadRegMaster().getTitleId()+File.separator+WebKeys.READING_TYPE_RETELL+WebKeys.WAV_FORMAT;
				//String ReadingRegisterFilePath = studentReadRegService.getRRPath(activityScore, request);
				String ReadingRegisterFilePath = WebKeys.READING_REGISTER+File.separator+activityScore.getReadRegMaster().getTitleId()+File.separator+WebKeys.READING_TYPE_RETELL+WebKeys.WAV_FORMAT;
				model.addAttribute("usersFilePath", ReadingRegisterFilePath);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/rr_retell_score");
			}else if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_UPLOAd_PIC_ACTIVITY_ID){
				// String ReadingRegisterFilePath = WebKeys.READING_REGISTER+File.separator+activityScore.getReadRegMaster().getTitleId()+File.separator+WebKeys.LP_PICTURE+WebKeys.JPG_FORMAT;
				String ReadingRegisterFilePath = studentReadRegService.getRRPath(activityScore, request);
				model.addAttribute("usersFilePath", ReadingRegisterFilePath);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/rr_picture_score");
			}else if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_CREATE_QUIZ_ACTIVITY_ID){
				List<ReadRegQuestions> readRegQuestions = resultsService.getReadRegQuestions(activityScore.getStudent().getStudentId(), activityScore.getReadRegMaster().getTitleId());
				model.addAttribute("readRegQuestions", readRegQuestions);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/rr_create_a_quiz_score");
			}else if(activityScore.getReadRegActivity().getActivityId() == WebKeys.READ_REG_TAKE_QUIZ_ACTIVITY_ID){
				List<ReadRegAnswers> readRegAnswers = resultsService.getStudentQuiz(activityScore.getStudent().getStudentId(), activityScore.getReadRegMaster().getTitleId());
				model.addAttribute("readRegAnswers", readRegAnswers);
				modelAndView =  new ModelAndView("Ajax/CommonJsp/rr_take_a_quiz_score");
			}
			
		}catch(DataException e){
			logger.error("Error in openActivityScoreDialog() of ReadRegReviewResultsController"+ e);
		}
		return modelAndView;
	}
	@RequestMapping(value = "/getBookApproveStatus", method = RequestMethod.POST)
	public @ResponseBody void getBookApproveStatus(long bookTitleId, Model model, HttpSession session, HttpServletResponse response) {
		try {
			ReadRegMaster readRegMaster = studentReadRegService.getReadRegMasterByTitleId(bookTitleId);
			if(readRegMaster.getApproved().equalsIgnoreCase(WebKeys.LP_REJECTED)) 
				response.getWriter().write(WebKeys.LP_REJECTED);
			else 
				response.getWriter().write(WebKeys.LP_APPROVED);
		} catch (IOException e) {				
			e.printStackTrace();
		}  
	}
	@RequestMapping(value = "/getStudentRRReports", method = RequestMethod.GET)
	public ModelAndView getRRReports(HttpSession session) {
		ModelAndView model = new ModelAndView("Teacher/student_rr_reports_main", "Grade", new Grade());
		model.addObject("divId", WebKeys.LP_TAB_RR_REPORTS);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			if(teacherObj == null){
				//admin
				List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			}else{
				//teacher
				List<Grade>  teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
			model.addObject("userType",userReg.getUser().getUserTypeid());
			
		}
		catch(Exception e){
			logger.error("Error in getRRReview() of ReadRegReviewResultsController" +e);
		}
		return model;
	}
	
	@RequestMapping(value = "/exportStudentRRReports", method = RequestMethod.GET)
	public ModelAndView exportStudentRRReports(HttpServletResponse response, HttpServletRequest request) throws ParseException {
		ModelAndView model=null;
		long studentId=0;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		/*Map<String,List<ReadRegActivityScore>> classRRReports = new HashMap<String,List<ReadRegActivityScore>>();*/
		Map<String,Map<String,List<ReadRegActivityScore>>> gradeRRReports=new HashMap<String,Map<String,List<ReadRegActivityScore>>>();
		List<Long> studTotPoints= new ArrayList<Long>();
		List<ReadRegActivityScore> listReadRegActivityScores=new ArrayList<ReadRegActivityScore>();
		long gradeId = Long.parseLong(request.getParameter("gradeId"));
		String reportsType=request.getParameter("reportsType");
		if(userReg.getUser().getUserTypeid()==3){
			studentId = Long.parseLong(request.getParameter("studentId"));
			try{
				if(studentId==0){
					Map<String,List<ReadRegActivityScore>> classRRReports = new HashMap<String,List<ReadRegActivityScore>>();
					List<Student> studentLst=resultsService.getRRStudents(gradeId,teacherObj.getTeacherId(),WebKeys.LP_APPROVED);
					for (Student student : studentLst) {
						String s="stud"+student.getStudentId();
						long allBookTotPE=studentReadRegDAO.getStudentTotalPointsEarned(student.getStudentId(), gradeId);
						studTotPoints.add(allBookTotPE);
						listReadRegActivityScores=studentReadRegDAO.getReadRegActivityScoresByStudentId(student.getStudentId(),gradeId);
						classRRReports.put(s,listReadRegActivityScores);
					}
					if(reportsType.equalsIgnoreCase("excel")){
					response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentRRReports.xls\"");
					model = new ModelAndView("studentRRReportsExcelView", "listClassRRReports", classRRReports);
					}
					else{
					response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentRRReports.pdf\"");	
					model = new ModelAndView("studentRRReportsPDFView", "listClassRRReports", classRRReports);
					}
					//model = new ModelAndView("studentRRReportsExcelView", "listClassRRReports", classRRReports);
					long mastergradeId=adminService.getMasterGradeIdbyGradeId(gradeId);
					model.addObject("gradeName", appadminservice3.getMasterGrade(mastergradeId).getGradeName());
					model.addObject("allStudsTotPointsEarned",studTotPoints);
					model.addObject("reportType","classWise");
					model.addObject("studentLst",studentLst);
								
				}else{
		
					List<ReadRegActivityScore> readRegActivityScores=studentReadRegDAO.getReadRegActivityScoresByStudentId(studentId,gradeId);
					long allBookTotPE=studentReadRegDAO.getStudentTotalPointsEarned(studentId, gradeId);
					if(reportsType.equalsIgnoreCase("excel")){
					response.setHeader("Content-Disposition", "attachment; filename=\"StudentRRReports.xls\"");
					model = new ModelAndView("studentRRReportsExcelView", "readRegActivityScores", readRegActivityScores);
					}
					else{
					response.setHeader("Content-Disposition", "attachment; filename=\"StudentRRReports.pdf\"");	
					model = new ModelAndView("studentRRReportsPDFView", "readRegActivityScores", readRegActivityScores);
					}
					Student student=iolReportCardService.getStudent(studentId);
					String studentName=student.getUserRegistration().getFirstName()+" "+student.getUserRegistration().getLastName();
					model.addObject("studentName",studentName);
					model.addObject("allBookTotPE",allBookTotPE);
					model.addObject("reportType","studentWise");
							
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			if(gradeId==0){
				String fromId=request.getParameter("fromId");		
				String toId=request.getParameter("toId");
				
				long date=new SimpleDateFormat("MM/dd/yyyy").parse(fromId).getTime();
				java.sql.Date fromDate=new java.sql.Date(date);
				long date1=new SimpleDateFormat("MM/dd/yyyy").parse(toId).getTime();
				java.sql.Date toDate=new java.sql.Date(date1);
			
				//System.out.println("fromDate="+fromDate+"toDate="+toDate);
				List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
				for(Grade grade : schoolgrades){
					Map<String,List<ReadRegActivityScore>> classRRReports = new HashMap<String,List<ReadRegActivityScore>>();
					String grad="grade"+grade.getGradeId();
					List<Student> studentLst=resultsService.getRRStudents(grade.getGradeId(),WebKeys.LP_APPROVED);
					for (Student student : studentLst) {
						String s="stud"+student.getStudentId();
						long allBookTotPE=studentReadRegDAO.getStudentTotalPointsEarned(student.getStudentId(), gradeId,fromDate,toDate);
						studTotPoints.add(allBookTotPE);
						listReadRegActivityScores=studentReadRegDAO.getReadRegActivityScoresByStudentId(student.getStudentId(),grade.getGradeId(),fromDate,toDate);
						classRRReports.put(s,listReadRegActivityScores);
					}
					gradeRRReports.put(grad,classRRReports);
					
				}
				if(reportsType.equalsIgnoreCase("excel")){
					response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentRRReportsByGradeWise.xls\"");
					model = new ModelAndView("studentRRReportsExcelView", "listGradeRRReports", gradeRRReports);
				}else{
					response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentRRReportsByGradeWise.pdf\"");
					model = new ModelAndView("studentRRReportsPDFView", "listGradeRRReports", gradeRRReports);
				}
					model.addObject("allStudsTotPointsEarned",studTotPoints);
					model.addObject("reportType","gradeWise");
					model.addObject("gradeList",schoolgrades);
					model.addObject("schoolName",userReg.getSchool().getSchoolName());
					System.out.println("----------------------------------------------------=");
				
				
			}else{
				String fromId=request.getParameter("fromId");		
				String toId=request.getParameter("toId");
				long date=new SimpleDateFormat("MM/dd/yyyy").parse(fromId).getTime();
				java.sql.Date fromDate=new java.sql.Date(date);
				long date1=new SimpleDateFormat("MM/dd/yyyy").parse(toId).getTime();
				java.sql.Date toDate=new java.sql.Date(date1);
							
				List<Student> studentLst=resultsService.getRRStudents(gradeId,WebKeys.LP_APPROVED);
				Map<String,List<ReadRegActivityScore>> classRRReports = new HashMap<String,List<ReadRegActivityScore>>();
				for (Student student : studentLst) {
					String s="stud"+student.getStudentId();
					long allBookTotPE=studentReadRegDAO.getStudentTotalPointsEarned(student.getStudentId(), gradeId,fromDate,toDate);
					studTotPoints.add(allBookTotPE);
					listReadRegActivityScores=studentReadRegDAO.getReadRegActivityScoresByStudentId(student.getStudentId(),gradeId,fromDate,toDate);
					classRRReports.put(s,listReadRegActivityScores);
				}
				if(reportsType.equalsIgnoreCase("excel")){
				response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentRRReportsByGradeWise.xls\"");
				model = new ModelAndView("studentRRReportsExcelView", "listClassRRReports", classRRReports);
				}else{
					response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentRRReportsByGradeWise.pdf\"");
					model = new ModelAndView("studentRRReportsPDFView", "listClassRRReports", classRRReports);
				}
				model.addObject("allStudsTotPointsEarned",studTotPoints);
				model.addObject("reportType","classWise");
				model.addObject("studentLst",studentLst);
				long mastergradeId=adminService.getMasterGradeIdbyGradeId(gradeId);
				model.addObject("gradeName", appadminservice3.getMasterGrade(mastergradeId).getGradeName());
				
			}
		}
		model.addObject("userTypeId",userReg.getUser().getUserTypeid());
		return model; 
  }
	@RequestMapping(value = "/getRRReportsByDistrictWise", method = RequestMethod.GET)
	public ModelAndView getRRReportsByDistrictWise(HttpSession session) {
		ModelAndView model = new ModelAndView("AppManager/district_wise_rr_reports_main");
		model.addObject("divId", WebKeys.LP_TAB_RR_REPORTS);
		try{
			String userType="";
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			userType = userReg.getUser().getUserType().toLowerCase();
			List<District> districtLst =schoolAdminService.getDistricts();
			model.addObject("districtLst", districtLst);
			model.addObject("userTypeId",userReg.getUser().getUserTypeid());
			
		}
		catch(Exception e){
			logger.error("Error in getRRReview() of ReadRegReviewResultsController" +e);
		}
		return model;
	}
	@RequestMapping(value = "/exportDistrictWiseRRReports", method = RequestMethod.GET)
	public ModelAndView exportDistrictWiseRRReports(HttpServletResponse response, HttpServletRequest request) {
		ModelAndView model=null;
		long studentId=0;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		/*Map<String,List<ReadRegActivityScore>> classRRReports = new HashMap<String,List<ReadRegActivityScore>>();*/
		Map<String,Map<String,List<ReadRegActivityScore>>> gradeRRReports=new HashMap<String,Map<String,List<ReadRegActivityScore>>>();
		Map<String,Map<String,Map<String,List<ReadRegActivityScore>>>> districtRRReports=new HashMap<String,Map<String,Map<String,List<ReadRegActivityScore>>>>();
		List<Long> studTotPoints= new ArrayList<Long>();
		List<ReadRegActivityScore> listReadRegActivityScores=new ArrayList<ReadRegActivityScore>();
		long districtId = Long.parseLong(request.getParameter("districtId"));
		String reportsType=request.getParameter("reportsType");
			
			if(districtId!=0){
			    List<School> schoolLst=schoolAdminService.getSchoolList(districtId);
			    for(School school : schoolLst){
			    	String schol="school"+school.getSchoolId();
			    	List<Grade> schoolgrades = adminService.getSchoolGrades(school.getSchoolId());
			    	for(Grade grade : schoolgrades){
					Map<String,List<ReadRegActivityScore>> classRRReports = new HashMap<String,List<ReadRegActivityScore>>();
					String grad="grade"+grade.getGradeId();
					List<Student> studentLst=resultsService.getRRStudents(grade.getGradeId(),WebKeys.LP_APPROVED);
					for (Student student : studentLst) {
							String s="stud"+student.getStudentId();
							long allBookTotPE=studentReadRegDAO.getStudentTotalPointsEarned(student.getStudentId(), grade.getGradeId());
							studTotPoints.add(allBookTotPE);
							listReadRegActivityScores=studentReadRegDAO.getReadRegActivityScoresByStudentId(student.getStudentId(),grade.getGradeId());
							classRRReports.put(s,listReadRegActivityScores);
						}
					gradeRRReports.put(grad,classRRReports);
					
			    	}
			    	
			    	districtRRReports.put(schol,gradeRRReports);
			    }
			    if(reportsType.equalsIgnoreCase("excel")){
					response.setHeader("Content-Disposition", "attachment; filename=\"AllSchoolsRRReportsByDistrictWise.xls\"");
					model = new ModelAndView("districtRRReportsExcelView", "listDistrictRRReports", districtRRReports);
			    }else{
			    	response.setHeader("Content-Disposition", "attachment; filename=\"AllSchoolsRRReportsByDistrictWise.pdf\"");
					model = new ModelAndView("districtRRReportsPDFView", "listDistrictRRReports", districtRRReports);
			    }
					model.addObject("allStudsTotPointsEarned",studTotPoints);
					model.addObject("reportType","districtWise");
					model.addObject("schoolLst",schoolLst);
					model.addObject("districtName",schoolAdminService.getDistrict(districtId).getDistrictName());
					System.out.println("----------------------------------------------------=");
				
				
			}
		
		return model; 
  }
	@RequestMapping(value = "/getBooksToApprove", method = RequestMethod.GET)
	public ModelAndView getBooksToApprove(long gradeId, long academicYearId, Model model, HttpSession session){
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<ReadRegMaster> readRegMasterLt = null;
		long bookCount=0;
		session.setAttribute("selectedYearId", academicYearId);
		AcademicYear academicYear = commonservice.getAcademicYearById(academicYearId);
		System.out.println(academicYear.getAcademicYear());
		session.setAttribute("academicYear", academicYear);
		try{
			
			if(teacherObj == null){
				//admin
				model.addAttribute("user", "admin");
				readRegMasterLt = resultsService.getReadRegBooksByGrade(gradeId, 0, academicYear,WebKeys.RRFIRSTPAGE-1, WebKeys.RRROWCOUNT1);
				bookCount = studentReadRegService.getApprovalBookCount(gradeId, 0,"",academicYear);
			}else{
				//teacher
				readRegMasterLt = resultsService.getReadRegBooksByGrade(gradeId, teacherObj.getTeacherId(),academicYear,WebKeys.RRFIRSTPAGE-1, WebKeys.RRROWCOUNT1);
				bookCount = studentReadRegService.getApprovalBookCount(gradeId, teacherObj.getTeacherId(),"",academicYear);
				model.addAttribute("user", "teacher");
			}
			model.addAttribute("readRegMasterLt", readRegMasterLt);
			model.addAttribute("firstPage", 1);
			long numberOfPages = bookCount/WebKeys.RRROWCOUNT1;
			
			if(bookCount%WebKeys.RRROWCOUNT1 >0)
				numberOfPages = numberOfPages+1;
			model.addAttribute("lastPage", numberOfPages>WebKeys.RRDISPLAYPAGES ? WebKeys.RRDISPLAYPAGES : numberOfPages);
			
			model.addAttribute("maxPages", numberOfPages);
			model.addAttribute("pageId", WebKeys.RRFIRSTPAGE);
			model.addAttribute("sortBy", "approved");
			model.addAttribute("sortingOrder", WebKeys.SORTING_ORDER_DESC);
		}
		catch(Exception e){
			logger.error("Error in getStudentReadingRegister() of ReadRegReviewResultsController" +e);
		}		
		return new ModelAndView("Ajax/Teacher/view_books_for_approval");
	}
	
	
	
	@RequestMapping(value = "/gotoPages", method = RequestMethod.GET)
	public ModelAndView gotoPage(HttpSession session, Model model,HttpServletRequest request,
			@RequestParam("pageId") long pageId,@RequestParam("gradeId") long gradeId, @RequestParam("academicYearId") long academicYearId,
			@RequestParam("sortBy") String sortBy,@RequestParam("sortingOrder") String sortingOrder, @RequestParam("searchBy") String searchBy) {
		try{
			long bookCount=0;
			List<ReadRegMaster> readRegMasterLt =null;
			model.addAttribute("sortBy", sortBy);
			model.addAttribute("sortingOrder", sortingOrder);
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			session.setAttribute("selectedYearId", academicYearId);
			AcademicYear academicYear = commonservice.getAcademicYearById(academicYearId);
			session.setAttribute("academicYear", academicYear);
			if(teacherObj == null){
				model.addAttribute("user", "admin");
				readRegMasterLt = resultsService.getAllAddedBooksByGrade(gradeId, academicYear, 0,pageId, WebKeys.RRROWCOUNT1,sortBy, sortingOrder, searchBy);
				bookCount = studentReadRegService.getApprovalBookCount(gradeId, 0,searchBy, academicYear);
			}else{
				readRegMasterLt  = resultsService.getAllAddedBooksByGrade(gradeId, academicYear, teacherObj.getTeacherId(),pageId, WebKeys.RRROWCOUNT1,sortBy, sortingOrder, searchBy);
				bookCount = studentReadRegService.getApprovalBookCount(gradeId, teacherObj.getTeacherId(),searchBy,academicYear);
				model.addAttribute("user", "teacher");
			}
						
			long numberOfPages = bookCount/WebKeys.RRROWCOUNT1;
			if(bookCount%WebKeys.RRROWCOUNT1 >0)
				numberOfPages = numberOfPages+1;
			model.addAttribute("maxPages", numberOfPages);
								
			if(numberOfPages < pageId && bookCount%WebKeys.RRDISPLAYPAGES !=0 ) {
				model.addAttribute("firstPage", numberOfPages%WebKeys.RRDISPLAYPAGES);
				model.addAttribute("lastPage", numberOfPages);
				pageId = bookCount/WebKeys.RRDISPLAYPAGES +1;
				
			}
			else if(numberOfPages >= pageId && pageId%WebKeys.RRDISPLAYPAGES ==0 ) {
				model.addAttribute("firstPage", pageId <= WebKeys.RRDISPLAYPAGES ? 1 : (numberOfPages > (pageId/WebKeys.RRDISPLAYPAGES-1)*WebKeys.RRDISPLAYPAGES+1 ? (pageId/WebKeys.RRDISPLAYPAGES-1)*WebKeys.RRDISPLAYPAGES+1 :(numberOfPages)));
				model.addAttribute("lastPage", pageId <= WebKeys.RRDISPLAYPAGES && numberOfPages >WebKeys.RRDISPLAYPAGES ? WebKeys.RRDISPLAYPAGES : (numberOfPages >= (pageId/WebKeys.RRDISPLAYPAGES)*WebKeys.RRDISPLAYPAGES ? (pageId/WebKeys.RRDISPLAYPAGES)*WebKeys.RRDISPLAYPAGES : numberOfPages));
			}
				
			else {
				model.addAttribute("firstPage", pageId <= WebKeys.RRDISPLAYPAGES ? 1 : (numberOfPages > (pageId/WebKeys.RRDISPLAYPAGES)*WebKeys.RRDISPLAYPAGES+1 ? (pageId/WebKeys.RRDISPLAYPAGES)*WebKeys.RRDISPLAYPAGES+1 :(numberOfPages)));
				model.addAttribute("lastPage", pageId <= WebKeys.RRDISPLAYPAGES && numberOfPages >WebKeys.RRDISPLAYPAGES ? WebKeys.RRDISPLAYPAGES : (numberOfPages > (pageId/WebKeys.RRDISPLAYPAGES+1)*WebKeys.RRDISPLAYPAGES ? (pageId/WebKeys.RRDISPLAYPAGES+1)*WebKeys.RRDISPLAYPAGES : numberOfPages));
			}
			model.addAttribute("searchBy", searchBy);	
			model.addAttribute("readRegMasterLt", readRegMasterLt);
			model.addAttribute("pageId", pageId);
			model.addAttribute("divId", WebKeys.LP_TAB_READING_REGISTER);
			model.addAttribute("gradeId",gradeId);
		}catch(DataException e){
			logger.error("Error in studentReadingRegister() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Teacher/view_books_for_approval");
	}
	@RequestMapping(value = "/returnGradedActivity", method = RequestMethod.GET)
	public View returnGradedActivity(HttpSession session,
			Model model,HttpServletRequest request,@RequestParam("readRegActScoreId") long readRegActivityScoreId) {
		try{
			
			boolean status = resultsService.returnGradedActivity(readRegActivityScoreId);
			model.addAttribute("status", status);
			
		}catch(DataException e){
			logger.error("Error in saveScore() of ReadRegReviewResultsController"+ e);
		}
		return new MappingJackson2JsonView();
	}	
	
	@RequestMapping(value = "/mergeBooks", method = RequestMethod.GET)
	public View mergeBooks(HttpSession session, 
				@RequestParam("readRedTitleIdArray") Long[] readRedTitleIdArray,
				Model model,
				HttpServletRequest request) {
		try{
			
			boolean status = resultsService.mergeReadRegDulicateBooks(readRedTitleIdArray);
			if(status) {
				model.addAttribute("returnMessage", WebKeys.LP_TAB_RR_BOOKS_MERGED_SUCCESSFULLY);
			} else {
				model.addAttribute("returnMessage", WebKeys.FAILED);
			}
				
		}catch(DataException e){
			logger.error("Error in mergeBooks() of ReadRegReviewController"+ e);
		}
		return new MappingJackson2JsonView();
	}
	
}