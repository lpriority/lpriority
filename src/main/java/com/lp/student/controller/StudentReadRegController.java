package com.lp.student.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.ReadRegReviewResultsService;
import com.lp.common.service.CommonService;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.QuizQuestionsList;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.ReadRegAnswers;
import com.lp.model.ReadRegMaster;
import com.lp.model.ReadRegQuestions;
import com.lp.model.ReadRegQuiz;
import com.lp.model.ReadRegReview;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.student.dao.StudentReadRegDAO;
import com.lp.student.service.StudentReadRegService;
import com.lp.teacher.service.StemCurriculumService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;


@Controller
public class StudentReadRegController {
	static final Logger logger = Logger.getLogger(StudentReadRegController.class);
	@Autowired
	private StudentReadRegService studentReadRegService;
	@Autowired
	private StudentReadRegDAO studentReadRegDAO;
	@Autowired
	private CommonService commonService;
	@Autowired
	private StemCurriculumService stemCurriculumService;
	@Autowired
	private ReadRegReviewResultsService resultsService;

	@RequestMapping(value = "/studentReadingRegister", method = RequestMethod.GET)
	public ModelAndView studentReadingRegister(HttpSession session, Model model,HttpServletRequest request) {
		try{
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			long bookCount = studentReadRegService.getBookCount(student.getGrade().getMasterGrades().getMasterGradesId(), "");			
			List<ReadRegMaster> readRegMasterLt = studentReadRegService.getBooks(student.getGrade().getMasterGrades().getMasterGradesId(), WebKeys.RRFIRSTPAGE-1, WebKeys.RRROWCOUNT,  WebKeys.RR_COLUMN_CREATEDATE, WebKeys.SORTING_ORDER_DESC, "");
			List<ReadRegMaster> studentBooks = studentReadRegService.getStudentBooks(student.getUserRegistration().getRegId());
			model.addAttribute("firstPage", 1);
			long numberOfPages = bookCount/WebKeys.RRROWCOUNT;			
			if(bookCount%WebKeys.RRROWCOUNT >0)
				numberOfPages = numberOfPages+1;
			
			model.addAttribute("lastPage", numberOfPages>WebKeys.RRDISPLAYPAGES ? WebKeys.RRDISPLAYPAGES : numberOfPages);
			
			model.addAttribute("maxPages", numberOfPages);
			model.addAttribute("pageId", WebKeys.RRFIRSTPAGE);
			model.addAttribute("sortBy", WebKeys.RR_COLUMN_CREATEDATE);
			model.addAttribute("sortingOrder", WebKeys.SORTING_ORDER_DESC);
			model.addAttribute("readRegMasterLt", readRegMasterLt);
			boolean isNew = true;
			if(studentBooks!=null && !studentBooks.isEmpty()){
				isNew = false;
			}
			model.addAttribute("pageId", 1);
			model.addAttribute("isNew", isNew);
			model.addAttribute("divId", WebKeys.LP_TAB_READING_REGISTER);
			model.addAttribute("user", "student");
			model.addAttribute("gradeId",student.getGrade().getGradeId());

		}catch(DataException e){
			logger.error("Error in studentReadingRegister() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Student/common_reading_register");
	}
	
	@RequestMapping(value = "/gotoPage", method = RequestMethod.GET)
	public ModelAndView gotoPage(HttpSession session, Model model,HttpServletRequest request,
			@RequestParam("pageId") long pageId, @RequestParam("sortBy") String sortBy, @RequestParam("sortingOrder") String sortingOrder, @RequestParam("searchBy") String searchBy) {
		try{
			long masterGradeId = 1;
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			if(student != null){
				masterGradeId = student.getGrade().getMasterGrades().getMasterGradesId();
			} else if (userRegistration.getSchool().getSchoolLevel().getSchoolLevelName().equals(WebKeys.SCHOOL_LEVEL_HIGH_SCHOOL)) {
				masterGradeId = 9;
			}
			long bookCount = studentReadRegService.getBookCount(masterGradeId, searchBy);
			model.addAttribute("bookBankChecked", true);
			model.addAttribute("pageId", pageId);
			model.addAttribute("sortBy", sortBy);
			model.addAttribute("sortingOrder", sortingOrder);
			long numberOfPages = bookCount/WebKeys.RRROWCOUNT;
			
			if(bookCount%WebKeys.RRROWCOUNT >0)
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
			List<ReadRegMaster> readRegMasterLt = studentReadRegService.getBooks(masterGradeId, pageId, WebKeys.RRROWCOUNT, sortBy, sortingOrder, searchBy);
			model.addAttribute("readRegMasterLt", readRegMasterLt);
			model.addAttribute("pageId", pageId);
			model.addAttribute("divId", WebKeys.LP_TAB_READING_REGISTER);
			if(student != null) {
				model.addAttribute("user", "student");
				model.addAttribute("gradeId",student.getGrade().getGradeId());
			}
			
		}catch(DataException e){
			logger.error("Error in studentReadingRegister() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/reading_register_view");
	}
	
	@RequestMapping(value = "/studentRR", method = RequestMethod.GET)
	public ModelAndView studentRR(HttpSession session, Model model,HttpServletRequest request,
			@RequestParam("pageId") long pageId, @RequestParam("sortBy") String sortBy, @RequestParam("sortOrder") String sortOrder) {
		try{
			long startTime = System.currentTimeMillis();
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			
			List<ReadRegMaster> readRegMasterLt = studentReadRegService.getAllAddedBooks(student.getGrade().getMasterGrades().getMasterGradesId());
			List<ReadRegMaster> studentBooks = studentReadRegService.getStudentBooks(student.getUserRegistration().getRegId());
			model.addAttribute("readRegMasterLt", readRegMasterLt);
			boolean isNew = true;
			if(studentBooks!=null && !studentBooks.isEmpty()){
				isNew = false;
			}
			model.addAttribute("isNew", isNew);
			model.addAttribute("divId", WebKeys.LP_TAB_READING_REGISTER);
			model.addAttribute("user", "student");
			model.addAttribute("gradeId",student.getGrade().getGradeId());
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			logger.info(elapsedTime);
		}catch(DataException e){
			logger.error("Error in studentReadingRegister() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Student/common_reading_register");
	}
	
	@RequestMapping(value = "/studentReading", method = RequestMethod.GET)
	public ModelAndView studentReading(HttpSession session, Model model,HttpServletRequest request) {
		try{
			long startTime = System.currentTimeMillis();
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			long bookCount = studentReadRegService.getBookCount(student.getGrade().getMasterGrades().getMasterGradesId(), "");
			List<ReadRegMaster> readRegMasterLt = studentReadRegService.getBooks(student.getGrade().getMasterGrades().getMasterGradesId(), WebKeys.RRFIRSTPAGE -1, WebKeys.RRROWCOUNT,  WebKeys.RR_COLUMN_CREATEDATE, WebKeys.SORTING_ORDER_DESC, "");
			List<ReadRegMaster> studentBooks = studentReadRegService.getStudentBooks(student.getUserRegistration().getRegId());
			long numberOfPages = bookCount/WebKeys.RRROWCOUNT;
			
			if(bookCount%WebKeys.RRROWCOUNT >0)
				numberOfPages = numberOfPages+1;
			model.addAttribute("maxPages", numberOfPages);
			model.addAttribute("firstPage", 1);
			model.addAttribute("lastPage", numberOfPages>WebKeys.RRDISPLAYPAGES ? WebKeys.RRDISPLAYPAGES : numberOfPages);
			model.addAttribute("pageId", WebKeys.RRFIRSTPAGE);
			model.addAttribute("sortBy", WebKeys.RR_COLUMN_CREATEDATE);
			model.addAttribute("sortingOrder", WebKeys.SORTING_ORDER_DESC);
			model.addAttribute("readRegMasterLt", readRegMasterLt);
			boolean isNew = true;
			if(studentBooks!=null && !studentBooks.isEmpty()){
				isNew = false;
			}
			model.addAttribute("isNew", isNew);
			model.addAttribute("divId", WebKeys.LP_TAB_READING_REGISTER);
			model.addAttribute("user", "student");
			model.addAttribute("gradeId",student.getGrade().getGradeId());
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			logger.info(elapsedTime);
		}catch(DataException e){
			logger.error("Error in studentReadingRegister() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/reading_register_view");
	}
	
	@RequestMapping(value = "/getBookActivities", method = RequestMethod.GET)
	public ModelAndView getBookActivities(HttpSession session, Model model,HttpServletRequest request, @RequestParam("titleId") long titleId,
			 @RequestParam("status") long status) {
		try{
			long startTime = System.currentTimeMillis();
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			ReadRegMaster ReadRegMaster = studentReadRegService.getBookDetails(titleId);
			model.addAttribute("readRegMaster", ReadRegMaster);
			if(student != null) {
				ReadRegReview readRegReview = studentReadRegService.getReadRegReviewByStudentId(titleId, student.getStudentId());
				if(readRegReview != null){
					model.addAttribute("userReivewId", readRegReview.getReviewId());
				}
				else{
					model.addAttribute("userReivewId", 0);
				}
			}
			model.addAttribute("status", status);
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			logger.info(elapsedTime);
		}catch(DataException e){
			logger.error("Error in studentReadingRegister() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/rr_div");
	}
	
	@RequestMapping(value = "/openBookDialog", method = RequestMethod.GET)
	public ModelAndView openBookDialog(
			@RequestParam("titleId") long titleId,
			@RequestParam("user") String user,
			@RequestParam("gradeId") long gradeId,
			@ModelAttribute ReadRegMaster readRegMaster,
			BindingResult result,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		try{
			
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			if(titleId > 0){
				model.addAttribute("mode", WebKeys.LP_TAB_EDIT);
				readRegMaster = studentReadRegService.getReadRegMasterByTitleId(titleId);
				if(student == null)
					student = commonService.getStudentByRegId(readRegMaster.getUserRegistration().getRegId());
				ReadRegReview readRegReview = studentReadRegService.getReadRegReviewByStudentId(readRegMaster.getTitleId(), student.getStudentId());
				if(readRegReview != null) {
					model.addAttribute("rating", readRegReview.getRating());
				}else {
					model.addAttribute("rating", "");
				}
				
					
			}else{
				model.addAttribute("mode", WebKeys.LP_TAB_CREATE);
				model.addAttribute("rating", "");
			}
			model.addAttribute("masterGradesId",stemCurriculumService.getGrade(gradeId).getMasterGrades().getMasterGradesId());
			model.addAttribute("titleId", titleId);
			model.addAttribute("user", user);
			model.addAttribute("gradeId",gradeId);
			model.addAttribute("page",0);
		}catch(DataException e){
			logger.error("Error in openBookDialog() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/add_or_edit_book","readRegMaster", readRegMaster);
	}
	
	@RequestMapping(value = "/saveOrUpdateBook", method = RequestMethod.POST)
	public View saveOrUpdateBook(
			@ModelAttribute ReadRegMaster readRegMaster,
			HttpSession session, 
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{
			String mode = "";
			String status = "";
			UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			Date date = new Date();
			if(readRegMaster.getTitleId()> 0)
				mode = WebKeys.LP_TAB_EDIT;
			else
				mode = WebKeys.LP_TAB_CREATE;
			if(student != null) { 
				if(readRegMaster.getApproved() != null || readRegMaster.getApproved() != "") {
					
					readRegMaster.setApproved(WebKeys.LP_WAITING);
					readRegMaster.setUserRegistration(userReg);
					readRegMaster.setCreateDate(date);
					readRegMaster.setTeacher(studentReadRegDAO.getTeacher(student.getStudentId()));
					status = studentReadRegService.saveOrUpdateBook(readRegMaster);
				}else {
					
					ReadRegMaster readReg = studentReadRegService.getReadRegMasterByTitleId(readRegMaster.getTitleId());
					readReg.setAuthor(readRegMaster.getAuthor());
					readReg.setBookTitle(readRegMaster.getBookTitle());
					readReg.setRating(readRegMaster.getRating());
					readReg.setNumberOfPages(readRegMaster.getNumberOfPages());
					readReg.setCreateDate(readRegMaster.getCreateDate());
					status = studentReadRegService.saveOrUpdateBook(readReg);
				}
			}else {
				
				ReadRegMaster readReg = studentReadRegService.getReadRegMasterByTitleId(readRegMaster.getTitleId());
				readReg.setAuthor(readRegMaster.getAuthor());
				readReg.setBookTitle(readRegMaster.getBookTitle());
				readReg.setRating(readRegMaster.getRating());
				readReg.setNumberOfPages(readRegMaster.getNumberOfPages());
				readReg.setCreateDate(readRegMaster.getCreateDate());
				status = studentReadRegService.saveOrUpdateBook(readReg);
			}			
			
			if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
				if(mode.equalsIgnoreCase(WebKeys.LP_TAB_CREATE)){
					model.addAttribute("status", WebKeys.LP_CREATED_SUCCESS);
				}else if(mode.equalsIgnoreCase(WebKeys.LP_TAB_EDIT)){
					model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS);
				}			
			}else if(status.equalsIgnoreCase(WebKeys.LP_BOOK_ALREADY_EXISTED)){
				model.addAttribute("status", WebKeys.LP_BOOK_ALREADY_EXISTED);
			}else{
				model.addAttribute("status", WebKeys.ERROR_OCCURED);				
			}
			model.addAttribute("titleId", readRegMaster.getTitleId());	
		}catch(Exception e){
			logger.error("Error in saveOrUpdateBook() of studentReadingRegister"+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/openReviewDialog", method = RequestMethod.GET)
	public ModelAndView openReviewDialog(
			@RequestParam("titleId") long titleId,
			@RequestParam("reviewId") long reviewId,
			@RequestParam("index") long index,
			@RequestParam("page") long page,
			@ModelAttribute ReadRegReview readRegReview,
			BindingResult result,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		try{
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			ReadRegReview revie=studentReadRegService.getReadRegReviewByStudentId(titleId, student.getStudentId());
			if(revie!=null){
				reviewId=revie.getReviewId();
			}
			
			if(reviewId > 0){
				model.addAttribute("mode", WebKeys.LP_TAB_EDIT);
				readRegReview = studentReadRegService.getReadRegReviewByReviewId(reviewId);
			}else{
				model.addAttribute("mode", WebKeys.LP_TAB_CREATE);
				model.addAttribute(WebKeys.INDEX, index);
			}
			model.addAttribute("rubrics", studentReadRegService.getReadRegRubric());
			model.addAttribute("reviewId", reviewId);
			model.addAttribute("titleId", titleId);
			if(student.getGrade().getMasterGrades().getMasterGradesId() >= 3 && student.getGrade().getMasterGrades().getMasterGradesId() < 13){
				model.addAttribute("showRubric", WebKeys.LP_YES);
			}
			else{
				model.addAttribute("showRubric", WebKeys.LP_NO);
			}
			ReadRegActivityScore activityScore = studentReadRegService.getStudentActivity(student.getStudentId(), titleId, WebKeys.READ_REG_REVIEW_ACTIVITY_ID);
			if(activityScore == null){
				activityScore = new ReadRegActivityScore();
			}
			model.addAttribute("activityScore", activityScore);
			model.addAttribute("page",page);
			model.addAttribute(WebKeys.INDEX, index);
		}catch(DataException e){
			logger.error("Error in openReviewDialog() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/add_or_edit_review","readRegReview", readRegReview);
	}
	
	@RequestMapping(value = "/saveOrUpdateReview", method = RequestMethod.POST)
	public View saveOrUpdateReview(
			@ModelAttribute ReadRegReview readRegReview,
			HttpSession session, 
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{
			String mode = "";
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			readRegReview.setStudent(student);
			Date currentDate = new Date();
			readRegReview.setReviewDate(currentDate);
			if(readRegReview.getReviewId()> 0)
				mode = WebKeys.LP_TAB_EDIT;
			else
				mode = WebKeys.LP_TAB_CREATE;
			String status = studentReadRegService.saveOrUpdateReview(readRegReview, mode);
			if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
				if(mode.equalsIgnoreCase(WebKeys.LP_TAB_CREATE)){
					model.addAttribute("status", WebKeys.LP_CREATED_SUCCESS);
				}else if(mode.equalsIgnoreCase(WebKeys.LP_TAB_EDIT)){
					model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS);
				}				
			}else if(status.equalsIgnoreCase(WebKeys.LP_BOOK_ALREADY_EXISTED)){
				model.addAttribute("status", WebKeys.LP_BOOK_ALREADY_EXISTED);
			}else{
				model.addAttribute("status", WebKeys.ERROR_OCCURED);				
			}
			model.addAttribute("reviewId", readRegReview.getReviewId());	
		}catch(Exception e){
			logger.error("Error in saveOrUpdateReview() of studentReadingRegister"+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/openRetellDialog", method = RequestMethod.GET)
	public ModelAndView openRetellDialog(
			@RequestParam("titleId") long titleId,
			@RequestParam("page") long page,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		try{
			String ReadingRegisterFilePath = WebKeys.READING_REGISTER+File.separator+titleId+File.separator+WebKeys.READING_TYPE_RETELL+WebKeys.WAV_FORMAT;
			model.addAttribute("rubrics", studentReadRegService.getReadRegRubric());
			model.addAttribute("usersFilePath", ReadingRegisterFilePath);
			model.addAttribute("titleId", titleId);
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			if(student.getGrade().getMasterGrades().getMasterGradesId() >= 3 && student.getGrade().getMasterGrades().getMasterGradesId() < 13){
				model.addAttribute("showRubric", WebKeys.LP_YES);
			}
			else{
				model.addAttribute("showRubric", WebKeys.LP_NO);
			}
			ReadRegActivityScore activityScore = studentReadRegService.getStudentActivity(student.getStudentId(), titleId, WebKeys.READ_REG_RETELL_ACTIVITY_ID);
			if(activityScore == null){
				activityScore = new ReadRegActivityScore();
			}
			model.addAttribute("activityScore", activityScore);
			model.addAttribute("page",page);
			if(activityScore.getReadRegActivityScoreId() > 0){
				model.addAttribute("mode", WebKeys.LP_TAB_EDIT);
			}else{
				model.addAttribute("mode", WebKeys.LP_TAB_CREATE);
			}
			int rating= studentReadRegService.getStudentRating(titleId,student.getStudentId());
			model.addAttribute("rating",rating);
		}catch(DataException e){
			logger.error("Error in openRetellDialog() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/add_or_edit_retell");
	}
	
	@RequestMapping(value = "/uploadRRRecordRetell", method = RequestMethod.POST)
	public View uploadRRRecordRetell(HttpServletResponse response, HttpSession session,HttpServletRequest request, Model model) {
		try{			
			String audioData = request.getParameter("audioData").toString();
			long titleId =  Long.parseLong(request.getParameter("titleId"));
			byte[] bis = Base64.decodeBase64(audioData);
			UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
			FileOutputStream fos = null;
			String ReadingRegisterFilePath = "";
			ReadingRegisterFilePath =   uploadFilePath + File.separator+ 
										WebKeys.READING_REGISTER+File.separator+
										titleId;
			File file = new File(ReadingRegisterFilePath);
			if(file.exists()) { 
				file.delete();
			}else if(!file.isDirectory()) {
				file.setExecutable(true, false);
				file.mkdirs();
	        } 
			try{
		  		 File f = new File(ReadingRegisterFilePath+File.separator+WebKeys.READING_TYPE_RETELL+WebKeys.WAV_FORMAT);  
				  if(f.exists()) 
		  				f.delete();
		  			synchronized(bis) {
		  				fos = new FileOutputStream(f, true); 
		  				fos.write(bis);
		  			}
	         }catch (Exception e) {
	             e.printStackTrace();
	         }			
		}catch(Exception e){
			logger.error("Error in uploadRRRecordRetell() of studentReadingRegister "+ e.getStackTrace());
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/openPictureDialog", method = RequestMethod.GET)
	public ModelAndView openPictureDialog(
			@RequestParam("titleId") long titleId,
			@RequestParam("page") long page,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		try{
			String ReadingRegisterFilePath = WebKeys.READING_REGISTER+File.separator+titleId+File.separator+WebKeys.LP_PICTURE+WebKeys.JPG_FORMAT;			
			model.addAttribute("usersFilePath", ReadingRegisterFilePath);
			model.addAttribute("rubrics", studentReadRegService.getReadRegRubric());
			model.addAttribute("titleId", titleId);
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			if(student.getGrade().getMasterGrades().getMasterGradesId() >= 3 && student.getGrade().getMasterGrades().getMasterGradesId() < 13){
				model.addAttribute("showRubric", WebKeys.LP_YES);
			}
			else{
				model.addAttribute("showRubric", WebKeys.LP_NO);
			}
			ReadRegActivityScore activityScore = studentReadRegService.getStudentActivity(student.getStudentId(), titleId, WebKeys.READ_REG_UPLOAd_PIC_ACTIVITY_ID);
			if(activityScore == null){
				activityScore = new ReadRegActivityScore();
			}
			if(activityScore.getReadRegActivityScoreId() > 0){
				model.addAttribute("mode", WebKeys.LP_TAB_EDIT);
			}else{
				model.addAttribute("mode", WebKeys.LP_TAB_CREATE);
			}
			model.addAttribute("activityScore", activityScore);
			model.addAttribute("page",page);
			int rating= studentReadRegService.getStudentRating(titleId,student.getStudentId());
			model.addAttribute("rating",rating);
		}catch(DataException e){
			logger.error("Error in openPictureDialog() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/add_or_edit_picture");
	}
	
	@RequestMapping(value = "/uploadRRPictureData", method = RequestMethod.POST)
	public View uploadRRPictureData(HttpServletResponse response, HttpSession session,HttpServletRequest request, Model model) {
		try{			
			String[] imageData = request.getParameter("dataURL").split(",");
			long titleId =  Long.parseLong(request.getParameter("titleId"));
			byte[] bis = Base64.decodeBase64(imageData[1]);
			UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
			FileOutputStream fos = null;
			String ReadingRegisterFilePath = "";
			ReadingRegisterFilePath =   uploadFilePath + File.separator+ 
										WebKeys.READING_REGISTER+File.separator+
										titleId;
			File file = new File(ReadingRegisterFilePath);
			if(file.exists()) { 
				file.delete();
			}else if(!file.isDirectory()) {
				file.setExecutable(true, false);
				file.mkdirs();
	        } 
			try{
		  		 File f = new File(ReadingRegisterFilePath+File.separator+WebKeys.LP_PICTURE+WebKeys.JPG_FORMAT);  
				  if(f.exists()) 
		  				f.delete();
		  			synchronized(bis) {
		  				fos = new FileOutputStream(f, true); 
		  				fos.write(bis);
		  			}
	         }catch (Exception e) {
	             e.printStackTrace();
	         }	
			String RRFilePath = WebKeys.READING_REGISTER+File.separator+titleId+File.separator+WebKeys.LP_PICTURE+WebKeys.JPG_FORMAT;			
			model.addAttribute("usersFilePath", RRFilePath);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in uploadRRPictureData() of studentReadingRegister "+ e.getStackTrace());
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/uploadRRPicture", headers="content-type=multipart/form-data", method=RequestMethod.POST )
	public @ResponseBody View uploadRRPicture(
			HttpSession session,
			HttpServletRequest request,
			Model model) throws IOException{
		try{	
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
			MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
			long titleId =  Long.parseLong(request.getParameter("titleId"));
			try{
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
				if(map != null) {
					@SuppressWarnings("rawtypes")
					Iterator iterator = map.keySet().iterator();
					String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
					while(iterator.hasNext()) {
						String str = (String) iterator.next();
						List<MultipartFile> fileList =  map.get(str);
						File newDir = new File(uploadFilePath+File.separator + WebKeys.READING_REGISTER+File.separator+titleId);
						if(!newDir.isDirectory()){
							newDir.mkdirs();
						}
						for(MultipartFile mpf : fileList) {
							File localFile = new File(uploadFilePath+File.separator +WebKeys.READING_REGISTER+File.separator+titleId+File.separator+WebKeys.LP_PICTURE+WebKeys.JPG_FORMAT);
							if(!localFile.exists()){
								localFile.createNewFile();
							}
							OutputStream out;
							out = new FileOutputStream(localFile);							
							out.write(mpf.getBytes());
							out.close();							
						}
					}			
				}	  			
				String ReadingRegisterFilePath = WebKeys.READING_REGISTER+File.separator+titleId+File.separator+WebKeys.LP_PICTURE+WebKeys.JPG_FORMAT;			
				model.addAttribute("usersFilePath", ReadingRegisterFilePath);
				model.addAttribute("status", WebKeys.PROFILE_PIC_UPLOAD_SUCCESS);	
			}catch(Exception e){
				logger.error("error while uploading failed");
				model.addAttribute("status", WebKeys.PROFILE_PIC_UPLOAD_FAILED);	
			} 
		}catch(Exception e){
			logger.error("Error in uploadRRPicture of AdminClassFilesController" + e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/openCreateQuizDialog", method = RequestMethod.GET)
	public ModelAndView openCreateQuizDialog(
			@RequestParam("titleId") long titleId,
			@RequestParam("page") long page,
			@ModelAttribute ReadRegQuestions quizQuestion,
			BindingResult result,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		List<ReadRegQuestions> regQuestions = null;
		ReadRegQuestions reQuestion = new ReadRegQuestions();
		try{
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			List<ReadRegQuiz> readRegQuizLt = studentReadRegService.getAllQuizQuestionList();
			regQuestions = studentReadRegService.getStudentAllCreatedQuestionsList(titleId, student.getStudentId());		
			model.addAttribute("readRegQuizLt", readRegQuizLt);
			model.addAttribute("titleId", titleId);
			long questionNum = 0;
			if(regQuestions.isEmpty()){
				questionNum = 1;
			}
			else if(regQuestions.size() <WebKeys.READ_REG_QUIZ_QUESTIONS_COUNT){
				questionNum = regQuestions.size()+1;
			}
			else{
				questionNum = regQuestions.size();
			}
			model.addAttribute("questionNum", questionNum);
			
			if(questionNum == WebKeys.READ_REG_QUIZ_QUESTIONS_COUNT){
				model.addAttribute("rubrics", studentReadRegService.getReadRegRubric());
				reQuestion = studentReadRegService.getStudentQuestion(titleId, student.getStudentId(), questionNum);	
				model.addAttribute("reQuestion", reQuestion);
				ReadRegActivityScore activityScore = studentReadRegService.getStudentActivity(student.getStudentId(), titleId, WebKeys.READ_REG_CREATE_QUIZ_ACTIVITY_ID);
				if(activityScore == null){
					activityScore = new ReadRegActivityScore();
				}
				model.addAttribute("activityScore", activityScore);
			}
			model.addAttribute("masGradeId",student.getGrade().getMasterGrades().getMasterGradesId());
			model.addAttribute("page",page);
			int rating= studentReadRegService.getStudentRating(titleId,student.getStudentId());
			model.addAttribute("rating",rating);
						
		}catch(DataException e){
			logger.error("Error in openCreateQuizDialog() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/add_or_edit_create_questions","quizQuestion", reQuestion );
	}
	
	
	@RequestMapping(value = "/saveCreateQuestions", method = RequestMethod.POST)
	public ModelAndView saveCreateQuestions(@ModelAttribute ReadRegQuestions quizQuestion, HttpServletResponse response, HttpSession session,HttpServletRequest request, Model model) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		String status="";
		int rating = Integer.parseInt(request.getParameter("rating"));
		try{			
			quizQuestion.setStudent(student);
			status = studentReadRegService.saveCreateQuestions(quizQuestion,rating);
			if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
				model.addAttribute("status", WebKeys.LP_CREATED_SUCCESS);	
			}else{
				model.addAttribute("status", WebKeys.ERROR_OCCURED);				
			}
		}catch(Exception e){
			logger.error("Error in saveCreateQuestions() of studentReadingRegister "+ e.getStackTrace());
		}
		long page =  Long.parseLong(request.getParameter("page"));
		long questionNum = quizQuestion.getReadRegQuiz().getQuestionNum()+1 ;
		ReadRegQuestions regQuestion = studentReadRegService.getStudentQuestion(quizQuestion.getReadRegMaster().getTitleId(), student.getStudentId(), questionNum);		
		model.addAttribute("titleId", quizQuestion.getReadRegMaster().getTitleId());
		model.addAttribute("questionNum", questionNum);
		model.addAttribute("masGradeId",student.getGrade().getMasterGrades().getMasterGradesId());
		model.addAttribute("page",page);
		model.addAttribute("rating",rating);
		if(questionNum == WebKeys.READ_REG_QUIZ_QUESTIONS_COUNT){
			model.addAttribute("rubrics", studentReadRegService.getReadRegRubric());
		}
		return new ModelAndView("Ajax/Student/add_or_edit_create_questions","quizQuestion", regQuestion);
	}
	
	@RequestMapping(value = "/updateQuizQuestions", method = RequestMethod.POST)
	public View updateQuizQuestions(
			@ModelAttribute QuizQuestionsList quizQuestionsList, 
			HttpServletResponse response, 
			HttpSession session,
			HttpServletRequest request, 
			Model model) {
		try{			
			int count =  Integer.parseInt(request.getParameter("count"));
			long titleId =  quizQuestionsList.getTitleId();
			ReadRegQuestions readRegQuestions = new ReadRegQuestions();
			if(count > -1){
				Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
				List<ReadRegQuiz> readRegQuizLt = studentReadRegService.getAllQuizQuestionList();
				ReadRegMaster readRegMaster = studentReadRegService.getReadRegMasterByTitleId(titleId);
				quizQuestionsList.getReadRegQuestionsLt().get(count).setReadRegMaster(readRegMaster);
				quizQuestionsList.getReadRegQuestionsLt().get(count).setReadRegQuiz(readRegQuizLt.get(count));
				quizQuestionsList.getReadRegQuestionsLt().get(count).setStudent(student);
				readRegQuestions = quizQuestionsList.getReadRegQuestionsLt().get(count);
				String status = studentReadRegService.updateReadRegQuestion(readRegQuestions);
				if(status.equalsIgnoreCase(WebKeys.SUCCESS))
					model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS);								
				else
					model.addAttribute("status", WebKeys.ERROR_OCCURED);				
			}
		}catch(Exception e){
			logger.error("Error in updateQuizQuestions() of studentReadingRegister "+ e.getStackTrace());
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/openTakeQuizDialog", method = RequestMethod.GET)
	public ModelAndView openTakeQuizDialog(
			@RequestParam("titleId") long titleId,
			@ModelAttribute ReadRegMaster readRegMaster,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		try{
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			if(titleId > 0){
				List<Student> studentLt = studentReadRegService.getAllQuizQuestionsGroupByTitleId(titleId, student.getStudentId());
				model.addAttribute("studentLt", studentLt);
			}
			model.addAttribute("titleId", titleId);
		}catch(DataException e){
			logger.error("Error in openTakeQuizDialog() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/read_reg_take_quiz","readRegMaster", readRegMaster);
	}
	
	@RequestMapping(value = "/getStudentAllQuizQuestionByTitleId", method = RequestMethod.GET)
	public ModelAndView getStudentAllQuizQuestionByTitleId(
			@RequestParam("titleId") long titleId,
			@RequestParam("studentId") long createdStudentId,
			@ModelAttribute QuizQuestionsList quizQuestionsList,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		try{
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			if(titleId > 0){
				List<ReadRegQuestions> readRegQuestionsLt = studentReadRegService.getStudentAllQuizQuestionByTitleId(titleId, createdStudentId);
				quizQuestionsList.setReadRegQuestionsLt(readRegQuestionsLt);
				long currentStudentId = student.getStudentId();
				List<ReadRegAnswers> readRegAnswersLt =  studentReadRegService.getStudentAnswerByTitleId(titleId, currentStudentId, createdStudentId);
				model.addAttribute("readRegAnswersLt", readRegAnswersLt);
			}
			model.addAttribute("quizQuestionsList", quizQuestionsList);
			List<Integer> numArray = new ArrayList<>(Arrays.asList(0,2,3,4,5,6,7,8,9));
			Collections.shuffle(numArray);
			model.addAttribute("numArray", numArray.subList(0, 5));
			model.addAttribute("titleId", titleId);
			int rating= studentReadRegService.getStudentRating(titleId,student.getStudentId());
			model.addAttribute("rating",rating);
		}catch(DataException e){
			logger.error("Error in getStudentAllQuizQuestionByTitleId() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/read_reg_show_quiz","quizQuestionsList", quizQuestionsList);
	}
	
	@RequestMapping(value = "/submitQuizAnswers", method = RequestMethod.POST)
	public View submitQuizAnswers(
			@ModelAttribute QuizQuestionsList quizQuestionsList, 
			HttpServletResponse response, 
			HttpSession session,
			HttpServletRequest request, 
			Model model) {
		try{	
			String answer="";
			List<ReadRegAnswers> readRegAnswersLt = new ArrayList<ReadRegAnswers>();
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			for (int i = 0; i < quizQuestionsList.getReadRegAnswersLt().size(); i++) {	
				if(quizQuestionsList.getReadRegAnswersLt().get(i).getReadRegQuestions() != null){
					quizQuestionsList.getReadRegAnswersLt().get(i).setCurrentStudent(student);
					quizQuestionsList.getReadRegAnswersLt().get(i).setTestDate(new Date());	
					answer=quizQuestionsList.getReadRegAnswersLt().get(i).getReadRegQuestions().getAnswer();
					if(quizQuestionsList.getReadRegAnswersLt().get(i).getAnswer().equalsIgnoreCase(answer))
						quizQuestionsList.getReadRegAnswersLt().get(i).setMark(1);
					readRegAnswersLt.add(quizQuestionsList.getReadRegAnswersLt().get(i));
				}
			}	
			int rating = Integer.parseInt(request.getParameter("rating"));
			long titleId = Long.parseLong(request.getParameter("titleId"));
			String status = studentReadRegService.submitQuizAnswers(readRegAnswersLt,rating,titleId);
			if(status.equalsIgnoreCase(WebKeys.SUCCESS))
				model.addAttribute("status", WebKeys.TEST_STATUS_SUBMITTED);								
			else
				model.addAttribute("status", WebKeys.ERROR_OCCURED);	
			model.addAttribute("readRegAnswersLt", readRegAnswersLt);
		}catch(Exception e){
			logger.error("Error in submitQuizAnswers() of studentReadingRegister "+ e.getStackTrace());
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/saveOrUpdateRetell", method = RequestMethod.POST)
	public View saveOrUpdateRetell(
			HttpSession session, 
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{
			long rubricScore = Long.parseLong(request.getParameter("rubricScore"));
			long titleId = Long.parseLong(request.getParameter("titleId"));
			int rating = Integer.parseInt(request.getParameter("rating"));
			String mode = request.getParameter("mode");
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);			
			String status = studentReadRegService.saveOrUpdateActivityScore(student, titleId, rubricScore, WebKeys.READ_REG_RETELL_ACTIVITY_ID, mode,rating);
			if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
				model.addAttribute("status", WebKeys.LP_SAVED_OR_UPDATED_SUCCESS);			
			}else if(status.equalsIgnoreCase(WebKeys.LP_BOOK_ALREADY_EXISTED)){
				model.addAttribute("status", WebKeys.LP_BOOK_ALREADY_EXISTED);
			}else{
				model.addAttribute("status", WebKeys.ERROR_OCCURED);				
			}
		}catch(Exception e){
			logger.error("Error in saveOrUpdateRetell() of studentReadingRegister"+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/saveOrUpdatePicture", method = RequestMethod.POST)
	public View saveOrUpdatePicture(
			HttpSession session, 
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{
			long rubricScore = Long.parseLong(request.getParameter("rubricScore"));
			long titleId = Long.parseLong(request.getParameter("titleId"));
			String mode = request.getParameter("mode");
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);	
			int rating = Integer.parseInt(request.getParameter("rating"));
			String status = studentReadRegService.saveOrUpdateActivityScore(student, titleId, rubricScore, WebKeys.READ_REG_UPLOAd_PIC_ACTIVITY_ID, mode,rating);
			if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
				model.addAttribute("status", WebKeys.LP_SAVED_OR_UPDATED_SUCCESS);			
			}else if(status.equalsIgnoreCase(WebKeys.LP_BOOK_ALREADY_EXISTED)){
				model.addAttribute("status", WebKeys.LP_BOOK_ALREADY_EXISTED);
			}else{
				model.addAttribute("status", WebKeys.ERROR_OCCURED);				
			}
		}catch(Exception e){
			logger.error("Error in saveOrUpdatePicture() of studentReadingRegister"+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/goToQuestion", method = RequestMethod.POST)
	public ModelAndView goToQuestion(
			HttpSession session,
			Model model,
			@RequestParam("page") long page,
			HttpServletRequest request) {
		ReadRegQuestions regQuestion = null;
		try{
			long questionNum = Long.parseLong(request.getParameter("questionNum"));
			long titleId = Long.parseLong(request.getParameter("titleId"));
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			regQuestion = studentReadRegService.getStudentQuestion(titleId, student.getStudentId(), questionNum);	
			if(regQuestion == null){
				regQuestion = new ReadRegQuestions();
			}
			model.addAttribute("regQuestion", regQuestion);
			model.addAttribute("titleId", titleId);
			model.addAttribute("questionNum", questionNum);
			//if(questionNum == WebKeys.READ_REG_QUIZ_QUESTIONS_COUNT){
				model.addAttribute("rubrics", studentReadRegService.getReadRegRubric());
				ReadRegActivityScore activityScore = studentReadRegService.getStudentActivity(student.getStudentId(), titleId, WebKeys.READ_REG_CREATE_QUIZ_ACTIVITY_ID);
				if(activityScore == null){
					activityScore = new ReadRegActivityScore();
				}
				model.addAttribute("activityScore", activityScore);
				int rating= studentReadRegService.getStudentRating(titleId,student.getStudentId());
				model.addAttribute("rating",rating);
				model.addAttribute("page", page);
				model.addAttribute("masGradeId",student.getGrade().getMasterGrades().getMasterGradesId());
			///}
		}catch(DataException e){
			logger.error("Error in goToQuestion() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/add_or_edit_create_questions","quizQuestion", regQuestion);
	}
	
	@RequestMapping(value = "/personalReadingRegister", method = RequestMethod.GET)
	public ModelAndView personalReadingRegister(HttpSession session, Model model, HttpServletRequest request) {
		
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String startDate = commonService.getCurrentAcademicYear().getStartDate().toString();
		String endDate = commonService.getCurrentAcademicYear().getEndDate().toString();
		try{
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			List<ReadRegActivityScore> studentActivities = studentReadRegService.getStudentActivities(student.getStudentId(), "createDate", startDate, endDate);
			model.addAttribute("studentActivities", studentActivities);	
			model.addAttribute("divId", WebKeys.LP_TAB_PERSONAL_READING_REGISTER);
		}catch(DataException e){
			logger.error("Error in personalReadingRegister() of StudentReadRegTempController"+ e);
		}
		return new ModelAndView("Ajax/Student/personal_reading_register");
	}
		
	
	@RequestMapping(value = "/sortActivities", method = RequestMethod.POST)
	public ModelAndView sortByActivity(HttpSession session, Model model,HttpServletRequest request, @RequestParam("studentId") long studentId, @RequestParam("sortBy") String sortBy,@RequestParam("gradeId") long gradeId) {
		List<ReadRegActivityScore> studentActivities = new ArrayList<ReadRegActivityScore>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String startDate = commonService.getCurrentAcademicYear().getStartDate().toString();
		String endDate = commonService.getCurrentAcademicYear().getEndDate().toString();
		try{
			if(studentId==0) {
			Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			studentActivities = resultsService.getGradedActivities(gradeId,sortBy,teacher.getTeacherId(), startDate, endDate);
			}
			else {
				studentActivities = studentReadRegService.getStudentActivities(studentId, sortBy, startDate, endDate);
			}
			model.addAttribute("studentActivities", studentActivities);			
		}catch(DataException e){
			logger.error("Error in sortActivities() of StudentReadRegTempController"+ e);
		}
		return new ModelAndView("Ajax/Student/personal_reading_register_div");
	}
	
	@RequestMapping(value = "/parentSortActivities", method = RequestMethod.POST)
	public ModelAndView parentSortActivities(HttpSession session, Model model,HttpServletRequest request, @RequestParam("studentId") long studentId, @RequestParam("sortBy") String sortBy) {
		try{
			List<ReadRegActivityScore> studentActivities = studentReadRegService.getParentChildActivities(studentId, sortBy);
			model.addAttribute("studentActivities", studentActivities);			
		}catch(DataException e){
			logger.error("Error in sortActivities() of StudentReadRegTempController"+ e);
		}
		return new ModelAndView("Ajax/Student/personal_reading_register_div");
	}
	
	@RequestMapping(value = "/teachersaveOrUpdateBook", method = RequestMethod.POST)
		public @ResponseBody void teachersaveOrUpdateBook(HttpSession session,@ModelAttribute ReadRegMaster readRegMaster,HttpServletResponse response,HttpServletRequest request) {
			try {
				Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
				UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				Date date = new Date();
				readRegMaster.setApproved(WebKeys.LP_APPROVED);
				readRegMaster.setUserRegistration(userReg);
				readRegMaster.setCreateDate(date);
				readRegMaster.setTeacher(teacher);
				readRegMaster.setRating(readRegMaster.getRating());
				readRegMaster.setBookTitle(readRegMaster.getBookTitle().trim().replaceAll(" +", " "));
				readRegMaster.setAuthor(readRegMaster.getAuthor().trim().replaceAll(" +", " "));
				boolean status = studentReadRegService.TeacherSaveBook(readRegMaster);	
		
				if(status) 
					response.getWriter().write(WebKeys.LP_CREATED_SUCCESS);
				else 
					response.getWriter().write(WebKeys.LP_SYSTEM_ERROR);
			} catch (IOException e) {				
				e.printStackTrace();
			}  
		}
	
	@RequestMapping(value = "/checkActivityExists", method = RequestMethod.POST)
	public View checkActivityExists(@RequestParam("titleId") long titleId,
		Model model, HttpSession session) throws Exception {
		try {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		boolean status=studentReadRegService.checkActivityExists(titleId,student.getStudentId());
		model.addAttribute("status", status);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/openReviewPage", method = RequestMethod.GET)
	public ModelAndView openReviewPage(
			@RequestParam("titleId") long titleId,
			@RequestParam("reviewId") long reviewId,
			@RequestParam("index") long index,
			@RequestParam("page") long page,
			@ModelAttribute ReadRegReview readRegReview,
			BindingResult result,
			HttpSession session,
			Model model,
			HttpServletRequest request) {
		try{
			model.addAttribute("mode", WebKeys.LP_TAB_EDIT);
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			readRegReview=studentReadRegService.getReadRegReviewByStudentId(titleId, student.getStudentId());
			//readRegReview = studentReadRegService.getReadRegReviewByReviewId(reviewId);
			model.addAttribute("rubrics", studentReadRegService.getReadRegRubric());
			model.addAttribute("reviewId", readRegReview.getReviewId());
			model.addAttribute("titleId", titleId);
			if(student.getGrade().getMasterGrades().getMasterGradesId() >= 3 && student.getGrade().getMasterGrades().getMasterGradesId() != 13){
				model.addAttribute("showRubric", WebKeys.LP_YES);
			}
			else{
				model.addAttribute("showRubric", WebKeys.LP_NO);
			}
			ReadRegActivityScore activityScore = studentReadRegService.getStudentActivity(student.getStudentId(), titleId, WebKeys.READ_REG_REVIEW_ACTIVITY_ID);
			if(activityScore == null){
				activityScore = new ReadRegActivityScore();
			}
			model.addAttribute("activityScore", activityScore);
			model.addAttribute("page",page);
			model.addAttribute(WebKeys.INDEX, index);
		}catch(DataException e){
			logger.error("Error in openReviewDialog() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/add_or_edit_review","readRegReview", readRegReview);
	}
	@RequestMapping(value = "/checkBookExists", method = RequestMethod.POST)
	public View checkBookExists(@RequestParam("bookTitle") String bookTitle,@RequestParam("masterGradesId") long masterGradesId,
		Model model, HttpSession session) throws Exception {
		try {
		boolean status=studentReadRegService.checkBookExists(bookTitle,masterGradesId);
		model.addAttribute("status", status);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/returnedBooks", method = RequestMethod.GET)
	public ModelAndView studentReturnBooks(HttpSession session, Model model,HttpServletRequest request) {
		try{
			AcademicYear academicYear=commonService.getCurrentAcademicYear();
			UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			List<ReadRegMaster> readRegMasterLt = studentReadRegService.getRejectedBooksByRegId(student.getUserRegistration().getRegId(),academicYear );
			model.addAttribute("readRegMasterLt", readRegMasterLt);
			boolean isNew = true;
			for (ReadRegMaster readRegMaster : readRegMasterLt) {
				if(readRegMaster.getReadRegReviewLt().size() > 0) {
					for (ReadRegReview readRegReview : readRegMaster.getReadRegReviewLt()) {
						if(readRegReview.getStudent().getUserRegistration().getRegId() == userReg.getRegId()) {
							isNew = false;
							break;
						}
					}
				}
			}
			model.addAttribute("isNew", isNew);
			model.addAttribute("divId", WebKeys.LP_TAB_RETURN_BOOKS);
			model.addAttribute("user", "student");
			model.addAttribute("student",student);
			model.addAttribute("gradeId",student.getGrade().getGradeId());
		}catch(DataException e){
			logger.error("Error in returnedBooks() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Ajax/Student/returned_books");
	}
	@RequestMapping(value = "/reSubmitBook", method = RequestMethod.POST)
	public View reSubmitBook(@RequestParam("titleId") long titleId,
		Model model, HttpSession session) throws Exception {
		try {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		boolean status=studentReadRegService.reSubmitBook(titleId,student.getStudentId());
		model.addAttribute("status", status);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/homeReturnedBooks", method = RequestMethod.GET)
	public ModelAndView homeReturnedBooks(HttpSession session, Model model,HttpServletRequest request) {
		try{
			UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			List<ReadRegMaster> readRegMasterLt = studentReadRegService.getRejectedBooksByRegId(student.getUserRegistration().getRegId(), student.getUserRegistration().getSchool().getAcademicYear());
			model.addAttribute("readRegMasterLt", readRegMasterLt);
			boolean isNew = true;
			for (ReadRegMaster readRegMaster : readRegMasterLt) {
				if(readRegMaster.getReadRegReviewLt().size() > 0) {
					for (ReadRegReview readRegReview : readRegMaster.getReadRegReviewLt()) {
						if(readRegReview.getStudent().getUserRegistration().getRegId() == userReg.getRegId()) {
							isNew = false;
							break;
						}
					}
				}
			}
			model.addAttribute("isNew", isNew);
			model.addAttribute("divId", WebKeys.LP_TAB_RETURN_BOOKS);
			model.addAttribute("user", "student");
			model.addAttribute("student",student);
			model.addAttribute("gradeId",student.getGrade().getGradeId());
		}catch(DataException e){
			logger.error("Error in returnedBooks() of studentReadingRegister"+ e);
		}
		return new ModelAndView("Student/common_reading_register");
	}
}



