package com.lp.teacher.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.dao.AssessmentDAO;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.AcademicGrades;
import com.lp.model.Assignment;
import com.lp.model.AssignmentType;
import com.lp.model.ClassStatus;
import com.lp.model.EarlyLiteracyTestsForm;
import com.lp.model.Grade;
import com.lp.model.K1Sets;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.teacher.service.AssignAssessmentsService;
import com.lp.teacher.service.EarlyLiteracyService;
import com.lp.teacher.service.GradeBookService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;


@Controller
public class EarlyLiteracyController {
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private EarlyLiteracyService earlyLiteracyService;
	@Autowired
	private AssessmentDAO assessmentDAO;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDAO;
	@Autowired
	private SchoolAdminService schooladminservice;
	@Autowired
	private AssignAssessmentsService assignAssessmentsService;
	@Autowired
	private GradeBookService gradeBookService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private AndroidPushNotificationsService apns;
	
	@RequestMapping(value = "/earlyLiteracyLetter", method = RequestMethod.GET)
	public ModelAndView earlyLiteracyLetter(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		teacherGrades = curriculumService.getTeacherGrades(teacher);
		model.addAttribute("teacherGrades", teacherGrades);
		model.addAttribute("page", WebKeys.EARLY_LITERACY_LETTER);
		model.addAttribute("divId", WebKeys.CREATE_LETTER);
		model.addAttribute("tab", WebKeys.LP_TAB_EARLY_IDENTIFICATION);
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		return new ModelAndView("Teacher/early_literacy_main_page");
	}
	
	@RequestMapping(value = "/earlyLiteracyWord", method = RequestMethod.GET)
	public ModelAndView earlyLiteracyWord(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		teacherGrades = curriculumService.getTeacherGrades(teacher);
		model.addAttribute("teacherGrades", teacherGrades);
		model.addAttribute("page", WebKeys.EARLY_LITERACY_WORD);
		model.addAttribute("divId", WebKeys.CREATE_WORD);
		model.addAttribute("tab", WebKeys.LP_TAB_EARLY_SIGHT);
		return new ModelAndView("Teacher/early_literacy_main_page");
	}
	
	@RequestMapping(value = "/createTestSets", method = RequestMethod.GET)
	public @ResponseBody void createTestSets(
			@RequestParam("csId") long csId,
			@RequestParam("page") String page,
			@RequestParam("setId") long setId,
			@RequestParam("setName") String setName,
			@RequestParam("sets") String sets,
			@RequestParam("mode") String mode,
			HttpSession session,
			HttpServletResponse response){
	   try{	
		   Date currentDate = new Date();
		   K1Sets k1Sets = new K1Sets();
		   k1Sets.setSetName(setName);
		   k1Sets.setSetType(WebKeys.WORD);
		   k1Sets.setListType(WebKeys.TEACHER_CREATED);
		   k1Sets.setCsId(csId);
		   k1Sets.setSet(sets);
		   k1Sets.setStatus(WebKeys.ACTIVE);
		   if(setId > 0)
			   k1Sets.setSetId(setId);
		   k1Sets.setCreatedDate(currentDate);
		   String result = earlyLiteracyService.createK1Tests(k1Sets);
		   if(result.equalsIgnoreCase(WebKeys.SUCCESS)){
			   if(mode.equalsIgnoreCase(WebKeys.LP_TAB_CREATE))
				   response.getWriter().write(WebKeys.LP_CREATED_SUCCESS);  
			   else if(mode.equalsIgnoreCase(WebKeys.LP_TAB_EDIT))
				   response.getWriter().write(WebKeys.LP_UPDATED_SUCCESS);
		   }else if(result.equalsIgnoreCase(WebKeys.TEST_ALREDAY_ASSGINED)){	
			   response.getWriter().write(WebKeys.TEST_ALREDAY_ASSGINED);  
		   }else if(result.equalsIgnoreCase(WebKeys.LP_FAILED)){
			   response.getWriter().write(WebKeys.LP_FAILED);  
		   }
		   response.setCharacterEncoding("UTF-8");  
		   response.setContentType("text/html");  
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	}
	@RequestMapping(value = "/removeSetBysetId", method = RequestMethod.GET)
	public @ResponseBody void removeSetBysetId(
			@RequestParam("setId") long setId,
			HttpSession session,
			HttpServletResponse response){
		try{
			String result = earlyLiteracyService.removeSetBysetId(setId);
			if(result.equalsIgnoreCase(WebKeys.SUCCESS)){
				 response.getWriter().write(WebKeys.LP_REMOVED_SUCCESS);
			}else if(result.equalsIgnoreCase(WebKeys.TEST_ALREDAY_ASSGINED)){	
				 response.getWriter().write(WebKeys.TEST_ALREDAY_ASSGINED);  
			}else if(result.equalsIgnoreCase(WebKeys.LP_FAILED)){
				 response.getWriter().write(WebKeys.LP_FAILED);  
			}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
		 }catch(Exception e){
			   e.printStackTrace();
		 }
	}
	
	@RequestMapping(value = "/getWordListContent")
	public ModelAndView gradeEarlyLiteracyTest(
			@RequestParam("setId") long setId,
			@RequestParam("csId") long csId,
			@RequestParam("mode") String mode,
			@RequestParam("divId") String divId,
			HttpSession session,
			HttpServletResponse response,
			Model model){
		if(setId > 0){
			K1Sets k1Set = earlyLiteracyService.getK1SetById(setId);
			model.addAttribute("k1Set",k1Set.getSet());
			model.addAttribute("setName",k1Set.getSetName());
		}
		model.addAttribute("setId", setId);
		model.addAttribute("mode", mode);
		model.addAttribute("csId", csId);
		model.addAttribute("divId", divId);
		return new ModelAndView("Ajax/Teacher/word_list_content");
	}
	@RequestMapping(value = {"/assignLetterSet", "/assignWordSet"}, method = RequestMethod.GET)
	public ModelAndView assginEarlyLiteracySets(HttpSession session,HttpServletRequest request,Model model) {
		String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);	
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		if(pattern.contains(WebKeys.ASSGIN_LETTER_SET)){
	    	model.addAttribute("page", WebKeys.EARLY_LITERACY_LETTER);
			model.addAttribute("divId", WebKeys.ASSGIN_LETTER_SET);
			model.addAttribute("setType", WebKeys.LETTER);	
			model.addAttribute("tab", WebKeys.LP_TAB_EARLY_IDENTIFICATION);
	    }else if(pattern.contains(WebKeys.ASSGIN_WORD_SET)){
	    	model.addAttribute("page", WebKeys.EARLY_LITERACY_WORD);
			model.addAttribute("divId", WebKeys.ASSGIN_WORD_SET);
			model.addAttribute("setType", WebKeys.WORD);	
			model.addAttribute("tab", WebKeys.LP_TAB_EARLY_SIGHT);
	    }
	    try{
	    	teacherGrades = curriculumService.getTeacherGrades(teacher);
			model.addAttribute("teacherGrades", teacherGrades);
	    }catch(DataException e){
	    	model.addAttribute("helloAjax",e.getMessage());
	    }
		return new ModelAndView("Teacher/early_literacy_main_page");
	}
	
	@RequestMapping(value = {"/gradeLetterSet", "/gradeWordSet"}, method = RequestMethod.GET)
	public ModelAndView gradeEarlyLiteracySets(HttpSession session,HttpServletRequest request,Model model) {
		String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
	    if(pattern.contains(WebKeys.GRADE_LETTER_SET)){
	    	model.addAttribute("page", WebKeys.EARLY_LITERACY_LETTER);
			model.addAttribute("divId", WebKeys.GRADE_LETTER_SET);
			model.addAttribute("setType", WebKeys.LETTER);
			model.addAttribute("tab", WebKeys.LP_TAB_EARLY_IDENTIFICATION);
	    }else if(pattern.contains(WebKeys.GRADE_WORD_SET)){
	    	model.addAttribute("page", WebKeys.EARLY_LITERACY_WORD);
			model.addAttribute("divId", WebKeys.GRADE_WORD_SET);
			model.addAttribute("setType", WebKeys.WORD);
			model.addAttribute("tab", WebKeys.LP_TAB_EARLY_SIGHT);
	    }
	    try{
	    	teacherGrades = curriculumService.getTeacherGradesByAcademicYr(teacher);
			model.addAttribute("teacherGrades", teacherGrades);
	    }catch(DataException e){
	    	model.addAttribute("helloAjax",e.getMessage());
	    }
		return new ModelAndView("Teacher/early_literacy_main_page");
	}
	
	@RequestMapping(value = "/assignTest", method = RequestMethod.GET)
	public @ResponseBody void  assginTest(
			@RequestParam("setsArray") ArrayList<Long> setIdsArray,
			@RequestParam("dueDate") String dueDateStr,
			@RequestParam("titleId") String titleId,
			@RequestParam("page") String page,
			@RequestParam("testType") String testType,
			@RequestParam("csId") long csId,
			@RequestParam("recordTime") int recordTime,
			@RequestParam("students") ArrayList<Long> students,
			HttpSession session,
			HttpServletResponse response) {
		try {
			DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
			Date currentDate = new Date();
			Date dueDate = new Date();
			dueDate = formatter.parse(dueDateStr);
			AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(page);
			ClassStatus classStatus = new ClassStatus();
			classStatus.setCsId(csId);
			Assignment assignment = new Assignment();
			assignment.setClassStatus(classStatus);
			assignment.setAssignmentType(assignmentType);
			assignment.setDateDue(dueDate);
			assignment.setDateAssigned(currentDate);
			assignment.setAssignStatus(WebKeys.ACTIVE);
			assignment.setUsedFor(WebKeys.RTI);
			assignment.setTitle(titleId);
			assignment.setTestType(testType);
			assignment.setRecordTime(recordTime);
			String status = earlyLiteracyService.assginTest(assignment, setIdsArray,students);
			if(status.equalsIgnoreCase("success")){
				response.getWriter().write("Test Assigned Successfully");  
			}else if(status.equalsIgnoreCase("already assigned")){
				response.getWriter().write("Already test assigned with same title");  
			}else if(status.equalsIgnoreCase("failed")){
				response.getWriter().write("Failed to assign the test");  
			}
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/html");  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/gradeEarlyLiteracyTest")
	public ModelAndView gradeEarlyLiteracyTest(
			@RequestParam("setsArray") ArrayList<String> setsArray,
			@RequestParam("setNameArray") ArrayList<String> setNameArray,
			@RequestParam("setType") String setType,
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("dialogDivId") String dialogDivId,
			@RequestParam("divId") String divId,
			@RequestParam("userType") String userType,
			@RequestParam("regId") long regId,
			@RequestParam("gradedStatus") String gradedStatus,
			@RequestParam("studentId") long studentId,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("page") String page,
			@RequestParam("assignmentTitle") String assignmentTitle,
			@RequestParam("studentName") String studentName,
			@RequestParam("setIdArray") ArrayList<Long> setIdArray,
			HttpServletRequest request,
			HttpSession session,
			Model model) {
		try {
			ArrayList<String> gradedMarksAsList = earlyLiteracyService.getGradedMarksAsList(studentAssignmentId, setIdArray);
			UserRegistration userReg = schooladminservice.getUserRegistration(regId);
			String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);	
			String earlyLiteracyFilePath = "";
			if(setType.equalsIgnoreCase(WebKeys.WORD)){
				earlyLiteracyFilePath = uploadFilePath +File.separator+ WebKeys.EARLY_LITERACY_TEST+File.separator+WebKeys.WORD;
			}else if(setType.equalsIgnoreCase(WebKeys.LETTER)){
				earlyLiteracyFilePath = uploadFilePath +File.separator+ WebKeys.EARLY_LITERACY_TEST+File.separator+WebKeys.LETTER;
			}
			long assignmentTypeId = 18;
			long hwAssignmentId = 0;
			String title = assignmentTitle+"_homework";
			Assignment assignment = assignAssessmentsService.assignmentByTitle(title, assignmentTypeId, WebKeys.LP_USED_FOR_HOMEWORKS);
			if(assignment.getAssignmentId() > 0){
				hwAssignmentId = assignment.getAssignmentId();
				DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
				String dateDue = formatter.format(assignment.getDateDue());
				model.addAttribute("dueDate",dateDue);
			}
			
			model.addAttribute("hwAssignmentId",hwAssignmentId);
			model.addAttribute("earlyLiteracyFilePath",earlyLiteracyFilePath);
			model.addAttribute("currentScore",Collections.frequency(gradedMarksAsList, "1"));
			model.addAttribute("gradedMarksArray",gradedMarksAsList.toArray());
			model.addAttribute("setsArray",setsArray);
			model.addAttribute("studentAssignmentId",studentAssignmentId);
			model.addAttribute("setNameArray",setNameArray);
			model.addAttribute("setType",setType);
			model.addAttribute("divId",divId);
			model.addAttribute("regId",regId);
			model.addAttribute("studentId",studentId);
			model.addAttribute("studentName",studentName);
			model.addAttribute("userType",userType);
			model.addAttribute("dialogDivId",dialogDivId);
			model.addAttribute("assignmentId",assignmentId);
			model.addAttribute("page",page);
			model.addAttribute("gradedStatus",gradedStatus);
			model.addAttribute("setIdArray", setIdArray);
			model.addAttribute("earlyLiteracyTestsForm",new EarlyLiteracyTestsForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Ajax/Teacher/grade_student_assignment");
	}
	
	@RequestMapping(value = "/getAssignedDates", method = RequestMethod.GET)
	public View getAssignedDates(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,
			@RequestParam("page") String assignmentType,
			Model model) {
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherAssignedDates",
				earlyLiteracyService.getAssignedDates(teacher, csId, usedFor,assignmentType));
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getAssignmentsTitles", method = RequestMethod.GET)
	public View getAssignmentsTitles(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,
			@RequestParam("assignedDate") String assignedDate,
			@RequestParam("page") String assignmentType,
			Model model) {
		model.addAttribute("assignmentTitles", earlyLiteracyService.getAssignmentTitles(csId, usedFor, assignedDate, assignmentType));
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/playEarlyLiteracyContent", method = RequestMethod.GET)
	public @ResponseBody void playAudioFile(
			@RequestParam("content") String content,
			@RequestParam("setname") String setname,
			@RequestParam("setType") String setType,
			@RequestParam("userType") String userType,
			@RequestParam("regId") long regId,
			@RequestParam("assignmentId") long assignmentId,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		UserRegistration userReg = schooladminservice.getUserRegistration(regId);
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);	
		String earlyLiteracyFilePath = "";
		if(setType.equalsIgnoreCase(WebKeys.WORD)){
			earlyLiteracyFilePath = uploadFilePath +File.separator+ WebKeys.EARLY_LITERACY_TEST+File.separator+WebKeys.WORD;
		}else if(setType.equalsIgnoreCase(WebKeys.LETTER)){
			earlyLiteracyFilePath = uploadFilePath +File.separator+ WebKeys.EARLY_LITERACY_TEST+File.separator+WebKeys.LETTER;
		}
		String path  = earlyLiteracyFilePath+File.separator+assignmentId+File.separator+setname+File.separator+content+File.separator+content+".wav";
		File f = new File(path);
		if(f.exists()){
		  try {
			  File audioFile = new File(path);
			  AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			  AudioFormat format = audioStream.getFormat();
			  DataLine.Info info = new DataLine.Info(Clip.class, format);
			  try {
				  Clip audioClip = (Clip) AudioSystem.getLine(info);
				  audioClip.open(audioStream);
				  audioClip.start();
				  response.getWriter().write("");
			  } catch (LineUnavailableException e) {
				  e.printStackTrace();
			  }
		  } catch (UnsupportedAudioFileException e) {
			  e.printStackTrace();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		}else{
			try{
				response.getWriter().write("Audio File not existed !!");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html");  
	}
	
	@RequestMapping(value = "/submitGradePercentage", method = RequestMethod.GET)
	public View submitGradePercentage(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,
			@RequestParam("assignedDate") String assignedDate,
			@RequestParam("page") String assignmentType,
			Model model) {
		model.addAttribute("assignmentTitles", earlyLiteracyService.getAssignmentTitles(csId, usedFor, assignedDate, assignmentType));
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/gradeAssessment", method = RequestMethod.GET)
	public @ResponseBody void gradeAssessment(
			@RequestParam("percentage") float percentage,
			@RequestParam("setType") String setType,
			@RequestParam("setArray") ArrayList<String> setArray,
			@RequestParam("contentArray") List<String> contentList,
			@RequestParam("gradMarksArray")  ArrayList<String> gradMarksArray,
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("studentId") long studentId,
			@RequestParam("dueDate") String dueDate,
			@RequestParam("assignmentTitle") String assignmentTitle,
			@RequestParam("hwAssignmentId") long hwAssignmentId,
			@RequestParam("setIdArray") ArrayList<Long> setIdArray,
			HttpSession session,
			HttpServletResponse response) {
		boolean isGradeSuccessful = true;
		boolean isGradeStatusSuccessful = true;
		try{
			for (int j = 0; j < setArray.toArray().length; j++) {
				String setName = setArray.toArray()[j].toString();
				String gradedMarks = gradMarksArray.toArray()[j].toString();
				if(gradedMarks.contains("-1")){
					isGradeSuccessful = false;
				}
				long setId = setIdArray.get(j); //earlyLiteracyService.getSetIdBySetName(setName, setType);
				isGradeStatusSuccessful = earlyLiteracyService.setGradedMarks(setId, studentAssignmentId, gradedMarks);
			}
			AcademicGrades academicGrades = gradeAssessmentsDAO.getAcademicGradeByPercentage(percentage);
			if(isGradeStatusSuccessful && setArray.size() > 0 && gradMarksArray.size() > 0){
				boolean success = true;
				if(isGradeSuccessful){	
					 success = earlyLiteracyService.updateGradeStatus(studentId, studentAssignmentId, percentage,WebKeys.GRADED_STATUS_GRADED, academicGrades.getAcedamicGradeId());
					 if(success){
						 if(!contentList.isEmpty()) {
							 Assignment assignment = new Assignment();
							 AssignmentType assignmentType = new AssignmentType();
							 ClassStatus classStatus = new ClassStatus();
							 StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
							 StudentAssignmentStatus stdAssignmentStatus = new StudentAssignmentStatus();
							 StudentAssignmentStatus sas =  assignAssessmentsService.getStudentAssignmentStatus(studentAssignmentId);
							 classStatus.setCsId(sas.getAssignment().getClassStatus().getCsId());
							 assignment.setClassStatus(classStatus);
							 assignmentType.setAssignmentTypeId(18);
							 assignmentType.setUsedFor(WebKeys.LP_USED_FOR_HOMEWORKS);
							 assignment.setAssignmentType(assignmentType);
							 assignment.setAssignStatus(WebKeys.ACTIVE);
							 assignment.setDateAssigned(new Date());
							 DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
							 assignment.setDateDue(formatter.parse(dueDate));
							 assignment.setTitle(assignmentTitle+"_homework");
							 assignment.setCreatedBy(studentId);	
							 assignment.setUsedFor(WebKeys.LP_USED_FOR_HOMEWORKS);
							 if(hwAssignmentId > 0)
								 assignment.setAssignmentId(0);
							 else
								 assignment.setAssignmentId(hwAssignmentId);
							 stdAssignmentStatus.setAssignment(assignment);
							 Student student = gradeBookService.getStudentById(studentId); 
							 stdAssignmentStatus.setStudent(student);
							 stdAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
							 stdAssignmentStatus.setStatus(WebKeys.TEST_STATUS_PENDING);
							 long gradingTypesId=1;
							 long peerReviewBy=0;
							 studentAssignmentStatus = assignAssessmentsService.assignReadingFluencyLearningPracticeHomeWork(assignment, stdAssignmentStatus);
							 if(studentAssignmentStatus.getStatus().equals(WebKeys.TEST_STATUS_SUBMITTED) && studentAssignmentStatus.getGradedStatus().equals(WebKeys.GRADED_STATUS_GRADED)){
								 boolean status =  false; //assignAssessmentsService.assignRFLPTest(contentList,dueDate,studentAssignmentStatus,gradingTypesId,peerReviewBy);
								 if(status){
									 apns.sendStudentResultsNotification(WebKeys.LP_USED_FOR_RTI, studentId);
									 response.getWriter().write("Graded Successfully : "+studentAssignmentStatus.getAssignment().getAssignmentId());  
								     
								 }
								 else
									 response.getWriter().write("RFLP Assignment failed !! As the test was already given and submitted.");  
							 }else{
								 assignAssessmentsService.assignRFLPTest(contentList,dueDate,studentAssignmentStatus,gradingTypesId,peerReviewBy);
								 apns.sendStudentResultsNotification(WebKeys.LP_USED_FOR_RTI, studentId);
								 response.getWriter().write("Graded Successfully : "+studentAssignmentStatus.getAssignment().getAssignmentId());  
							 }
						 }
						 
					 }else{
						 response.getWriter().write("Grading failed !!");
					 }
				}else{
					success = earlyLiteracyService.updateGradeStatus(studentId, studentAssignmentId, percentage,WebKeys.GRADED_STATUS_NOTGRADED, academicGrades.getAcedamicGradeId());
					if(success){
						response.getWriter().write("Test not fully graded.Saved Successfully !!"); 
					}else{
						response.getWriter().write("Grading failed !!");
					}
				}
				response.setCharacterEncoding("UTF-8");  
			    response.setContentType("text/html");  
			}else{
				response.getWriter().write("Grading failed !!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/autoAssignTest", method = RequestMethod.GET)
	public @ResponseBody void  autoAssignTest(
			@RequestParam("setsArray") ArrayList<Long> setIdsArray,
			@RequestParam("startDate") String startDateStr,
			@RequestParam("titleId") String titleId,
			@RequestParam("page") String page,
			@RequestParam("testType") String testType,
			@RequestParam("csId") long csId,
			@RequestParam("students") ArrayList<Long> students,
			@RequestParam("recordTimeArray") ArrayList<Integer> recordTimeArray,
			HttpSession session,
			HttpServletResponse response) {
		try {
			UserRegistration userRegistration = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
			Date dueDate = new Date();
			Date startDate = new Date();
			startDate = formatter.parse(startDateStr);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 7);
			dueDate = cal.getTime();
			Assignment assignment = new Assignment();
			
			AssignmentType assignmentType = new AssignmentType();
			assignmentType.setAssignmentTypeId(16);
			
			ClassStatus classStatus = new ClassStatus();
			classStatus.setCsId(csId);
			
			assignment.setAssignmentType(assignmentType);
			assignment.setClassStatus(classStatus);
			assignment.setTitle(titleId);
			assignment.setAssignStatus(WebKeys.ACTIVE);
			assignment.setCreatedBy(userRegistration.getRegId());
			assignment.setUsedFor(WebKeys.RTI);
			assignment.setTestType(testType);
			assignment.setDateAssigned(startDate);
			assignment.setDateDue(dueDate);
			assignment.setRecordTime(recordTimeArray.get(0));
			String status = earlyLiteracyService.assignAutomaticTest(assignment,students,setIdsArray, recordTimeArray);
			if(status.equals(WebKeys.ASSIGNED_SUCCESSFULLY)){
				 	response.getWriter().write(WebKeys.ASSIGNED_SUCCESSFULLY); 
			}else if(status.equals(WebKeys.TEST_ALREDAY_ASSGINED)){
				response.getWriter().write(WebKeys.TEST_ALREDAY_ASSGINED);
			}else if(status.equals(WebKeys.FAILED_TO_ASSIGNED)){
				response.getWriter().write(WebKeys.FAILED_TO_ASSIGNED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}