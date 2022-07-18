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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.common.dao.AssessmentDAO;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.common.service.PerformanceTaskService;
import com.lp.custom.exception.DataException;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.Assignment;
import com.lp.model.AssignmentType;
import com.lp.model.ClassStatus;
import com.lp.model.Grade;
import com.lp.model.MathGameScores;
import com.lp.model.MathQuiz;
import com.lp.model.MathQuizQuestions;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentMathAssessMarks;
import com.lp.model.Teacher;
import com.lp.teacher.dao.AssignAssessmentsDAO;
import com.lp.teacher.service.MathAssessmentService;
import com.lp.utils.WebKeys;


@Controller
public class MathAssessmentController extends WebApplicationObjectSupport{
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private MathAssessmentService mathAssessmentService;
	@Autowired
	private PerformanceTaskService performanceTaskService;
	@Autowired
	private AssessmentDAO assessmentDAO;
	@Autowired
	private AssignAssessmentsDAO assignAssessmentsDao;
	@Autowired
	AndroidPushNotificationsService apns;
	
	static final Logger logger = Logger.getLogger(MathAssessmentController.class);
	
	@RequestMapping(value = "/createMathAssessment", method = RequestMethod.GET)
	public ModelAndView createMathAssessment(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		teacherGrades = curriculumService.getTeacherGrades(teacher);
		model.addAttribute("teacherGrades", teacherGrades);
		model.addAttribute("page", WebKeys.CREATE_MATH_ASSESMENT);
		model.addAttribute("tab", WebKeys.MATH_ASSESMENT);
		return new ModelAndView("Teacher/display_math_assessments");
	}
	
	@RequestMapping(value = "/openQuizQuestion", method = RequestMethod.GET)
	public ModelAndView openQuizQuestion(
			@RequestParam("quizId") long quizId,
			@RequestParam("mode") String mode,
			HttpSession session,
			Model model) {
		model.addAttribute("quizId", quizId);
		model.addAttribute("mode", mode);
		return new ModelAndView("Ajax/Teacher/create_quiz_questions");
	}
	
	@RequestMapping(value = "/saveQuizQuestion", method = RequestMethod.GET)
	public @ResponseBody void saveQuizQuestion(
			@RequestParam("fraction") String fraction,
			@RequestParam("csId") long csId,
			@RequestParam("mode") String mode,
			@RequestParam("quizId") long quizId,
			@RequestParam("answersArray") ArrayList<String> answersArray,
			@RequestParam("blankArray") ArrayList<String> blankArray,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{	
			String status = mathAssessmentService.saveQuizQuestion(fraction, csId, quizId, mode, answersArray, blankArray);
			if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
				if(mode.equalsIgnoreCase("edit"))
					response.getWriter().write(WebKeys.LP_UPDATED_SUCCESS); 
				else	
					response.getWriter().write(WebKeys.LP_CREATED_SUCCESS); 
			}else if(status.equalsIgnoreCase(WebKeys.ALREADY_EXISTED)){
				response.getWriter().write(WebKeys.ALREADY_EXISTED); 
			}else if(status.equalsIgnoreCase(WebKeys.TEST_ALREDAY_ASSGINED)){
				response.getWriter().write(WebKeys.TEST_ALREDAY_ASSGINED); 
			}else if(status.equalsIgnoreCase(WebKeys.LP_FAILED)){
				response.getWriter().write(WebKeys.LP_FAILED); 	
			}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
	   }catch(Exception e){
		   logger.error("Error in saveQuizQuestion() of MathAssessmentController "+ e);
	   }
	}
	
	@RequestMapping(value = "/removeQuizQuestion", method = RequestMethod.GET)
	public @ResponseBody void removeQuizQuestion(
			@RequestParam("quizId") long quizId,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{	
			String status = mathAssessmentService.deleteMathQuizQuestion(quizId);
			if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
				response.getWriter().write(WebKeys.LP_REMOVED_SUCCESS); 
			}else if(status.equalsIgnoreCase(WebKeys.LP_FAILED)){	
				response.getWriter().write(WebKeys.LP_FAILED); 
			}else if(status.equalsIgnoreCase(WebKeys.TEST_ALREDAY_ASSGINED)){
				response.getWriter().write(WebKeys.TEST_ALREDAY_ASSGINED); 
			}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
	   }catch(Exception e){
		   logger.error("Error in removeQuizQuestion() of MathAssessmentController "+ e);
	   }
	}
	
	@RequestMapping(value = "/assignMathAssessment", method = RequestMethod.GET)
	public ModelAndView assignMathAssessment(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		teacherGrades = curriculumService.getTeacherGrades(teacher);
		model.addAttribute("teacherGrades", teacherGrades);
		model.addAttribute("page", WebKeys.ASSIGN_MATH_ASSESMENT);
		model.addAttribute("tab", WebKeys.MATH_ASSESMENT);
		return new ModelAndView("Teacher/assign_math_assessment");
	}
	
	@RequestMapping(value = "/assignQuizTest", method = RequestMethod.GET)
	public @ResponseBody void assignQuizTest(
			@RequestParam("csId") long csId,
			@RequestParam("dueDate") String dueDateStr,
			@RequestParam("titleId") String titleId,
			@RequestParam("quizeTime") int quizeTime,
			@RequestParam("quizIdArray")  ArrayList<Long> quizIdArray,
			@RequestParam("students")  ArrayList<Long> students,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{	
				DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
				Date currentDate = new Date();
				Date dueDate = new Date();
				dueDate = formatter.parse(dueDateStr);
				AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(WebKeys.LP_MATH_ASSESMENT);
				String mathQuizIds = "";
				long len = quizIdArray.size();
				for (int i = 0; i < len; i++) {
					if(len-1 > i)
						mathQuizIds += quizIdArray.get(i).toString() +":";
					else
						mathQuizIds += quizIdArray.get(i).toString();	
				}
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
				assignment.setDateDue(dueDate);
				assignment.setRecordTime(quizeTime);
				assignment.setMathQuizIds(mathQuizIds);
				String status = mathAssessmentService.assignQuizTest(assignment,quizIdArray,students);
				if(status.equalsIgnoreCase(WebKeys.ASSIGNED_SUCCESSFULLY)){
					response.getWriter().write(WebKeys.ASSIGNED_SUCCESSFULLY); 
				}else if(status.equalsIgnoreCase(WebKeys.TEST_ALREDAY_ASSGINED)){	
					response.getWriter().write(WebKeys.TEST_ALREDAY_ASSGINED); 
				}else if(status.equalsIgnoreCase(WebKeys.FAILED_TO_ASSIGNED)){
					response.getWriter().write(WebKeys.FAILED_TO_ASSIGNED); 
				}
			 response.setCharacterEncoding("UTF-8");  
		     response.setContentType("text/html");  
	   }catch(Exception e){
			logger.error("Error in assignQuizTest() of MathAssessmentController "+ e);
	   }
	}
	
	@RequestMapping(value = "/gradeMathAssessment", method = RequestMethod.GET)
	public ModelAndView gradeMathAssessment(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		teacherGrades = curriculumService.getTeacherGrades(teacher);
		model.addAttribute("teacherGrades", teacherGrades);
		AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(WebKeys.LP_MATH_ASSESMENT);
		model.addAttribute("assignmentTypeId", assignmentType.getAssignmentTypeId());
		model.addAttribute("page", WebKeys.GRADE_MATH_ASSESMENT);
		model.addAttribute("tab", WebKeys.MATH_ASSESMENT);
		model.addAttribute("usedFor",WebKeys.RTI);
		return new ModelAndView("Teacher/grade_math_assessment");
	}
	
	@RequestMapping("/getStudentsTestDetails")
	public ModelAndView getStudentsTestDetails(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("userType") String userType,
			@RequestParam("regId") long regId,
			@RequestParam("studentId") long studentId,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("status") String status,
			@RequestParam("gradedStatus") String gradedStatus,
			@RequestParam("page") String page,
			HttpSession session) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/grade_math_assessment_student_details");
		try{
				List<List<MathQuizQuestions>> mathQuizQuestionsLts = new ArrayList<List<MathQuizQuestions>>();
				List<List<StudentMathAssessMarks>> studentMathAssessMarksLts = new ArrayList<List<StudentMathAssessMarks>>();
				StudentAssignmentStatus sAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
				Assignment assignment = sAssignmentStatus.getAssignment();
				String quizIds = assignment.getMathQuizIds();
				String[] quizIdsArr = quizIds.split(":");
				for (String quizIdStr : quizIdsArr) {
					long quizId = Long.parseLong(quizIdStr);
					List<MathQuizQuestions> mathQuizQuestionsLt = mathAssessmentService.getMathQuizQuestionsByQuizId(quizId);
					mathQuizQuestionsLts.add(mathQuizQuestionsLt);
					List<StudentMathAssessMarks> studentMathAssessMarksLt = mathAssessmentService.getStudentMathAssessMarksByStudentAssignmentId(studentAssignmentId,quizId);
					studentMathAssessMarksLts.add(studentMathAssessMarksLt);
				}
				model.addObject("studentMathAssessMarksLts", studentMathAssessMarksLts);
				model.addObject("mathQuizQuestionsLts", mathQuizQuestionsLts);
				model.addObject("len", quizIdsArr.length);
				model.addObject("studentAssignmentId", studentAssignmentId);
				model.addObject("status", status);
				model.addObject("gradedStatus", gradedStatus);
				model.addObject("tab",WebKeys.MATH_ASSESMENT);
				model.addObject("page", WebKeys.GRADE_MATH_ASSESMENT);
				model.addObject("usedFor", WebKeys.MATH_ASSESMENT);
				model.addObject("studentRegId", regId);
				model.addObject("loginFrom", "teacher");
				model.addObject("sAssignmentStatus", sAssignmentStatus);
		}catch(Exception e){
			logger.error("Error in getStudentsTestDetails() of MathAssessmentController "+ e);
		}
		return model;
	}
	
	@RequestMapping(value = "/udpateMarkStatus", method = RequestMethod.GET)
	public View udpateMarkStatus(
			@RequestParam("studentMathAssessMarksId") long studentMathAssessMarksId,
			@RequestParam("mark") int mark,
			@RequestParam("correct") int correct,
			@RequestParam("wrong") int wrong,
			@RequestParam("studentAssignmentId") long studentAssignmentId,			
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response, Model model) throws DataException {
		try{	
			String status = mathAssessmentService.udpateQuestionMark(studentMathAssessMarksId, mark, correct, wrong, studentAssignmentId);
			if(status.equalsIgnoreCase(WebKeys.SUCCESS)){
				StudentAssignmentStatus stAs=assignAssessmentsDao.getStudentAssignmentStatus(studentAssignmentId);
				apns.sendStudentResultsNotification(WebKeys.LP_USED_FOR_RTI, stAs.getStudent().getStudentId());
				model.addAttribute("status", WebKeys.LP_UPDATED_SUCCESS );
				
			}else if(status.equalsIgnoreCase(WebKeys.LP_FAILED)){	
				model.addAttribute("status", WebKeys.LP_UNABLE_TO_UPADATE ); 
			}		     
			model.addAttribute("percentage", session.getAttribute("percentage"));
			model.addAttribute("acedamicGrade", session.getAttribute("acedamicGrade"));
			session.removeAttribute("acedamicGrade");
			session.removeAttribute("percentage");		     
	   }catch(Exception e){
			logger.error("Error in udpateMarkStatus() of MathAssessmentController "+ e);
	   }
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getMathAssignedDates", method = RequestMethod.GET)
	public View getMathAssignedDates(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,
			@RequestParam("page") String assignmentType,
			Model model) {
		model.addAttribute("teacherAssignedDates",mathAssessmentService.getMathAssignedDates(csId, usedFor,assignmentType));
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/analysisMathAssessment", method = RequestMethod.GET)
	public ModelAndView analysisMathAssessment(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		teacherGrades = curriculumService.getTeacherGrades(teacher);
		model.addAttribute("teacherGrades", teacherGrades);
		AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(WebKeys.LP_MATH_ASSESMENT);
		model.addAttribute("assignmentTypeId", assignmentType.getAssignmentTypeId());
		model.addAttribute("page", WebKeys.ANALYSIS_MATH_ASSESMENT);
		model.addAttribute("tab", WebKeys.MATH_ASSESMENT);
		model.addAttribute("usedFor",WebKeys.RTI);
		return new ModelAndView("Teacher/analysis_math_assessment");
	}
	
	@RequestMapping(value = "/getResultByMathQuizId", method = RequestMethod.GET)
	public ModelAndView getResultByMathQuizId(HttpSession session,
			@RequestParam("mathQuizId") long mathQuizId,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			Model model) {
		MathQuiz mathQuiz = mathAssessmentService.getMathQuizByQuizId(mathQuizId);
		List<MathQuizQuestions> mathQuizQuestionsLt =  mathAssessmentService.getMathQuizQuestionsByQuizId(mathQuizId);
		List<StudentMathAssessMarks> studentMathAssessMarksLt = mathAssessmentService.getStudentMathAssessMarksByQuizId(mathQuizId,assignmentId);
		List<StudentAssignmentStatus> studentAssignmentStatusLt = mathAssessmentService.getStudentAssessmentTestsByPercentage(assignmentId,assignmentTypeId);
		model.addAttribute("mathQuiz", mathQuiz);
		model.addAttribute("mathQuizQuestionsLt", mathQuizQuestionsLt);
		model.addAttribute("studentMathAssessMarksLts", studentMathAssessMarksLt);
		model.addAttribute("studentAssignmentStatusLt", studentAssignmentStatusLt);
		return new ModelAndView("Ajax/Teacher/analysis_math_assessment_student_details");
	}
	
	@RequestMapping(value = "/getStudentDetailsByPercentage", method = RequestMethod.GET)
	public ModelAndView getStudentDetailsByPercentage(HttpSession session,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			Model model) {
		Assignment assignment = commonService.getAssignmentByAssignmentId(assignmentId);
		List<MathQuiz> mathQuizLt = new ArrayList<MathQuiz>();
		List<List<MathQuizQuestions>> mathQuizQuestionsLts = new ArrayList<List<MathQuizQuestions>>();
		List<List<StudentMathAssessMarks>> studentMathAssessMarksLts = new ArrayList<List<StudentMathAssessMarks>>();
		String quizIds = assignment.getMathQuizIds();
		String[] quizIdsArr = quizIds.split(":");
		for (String quizIdStr : quizIdsArr) {
			long quizId = Long.parseLong(quizIdStr);
			MathQuiz mathQuiz = mathAssessmentService.getMathQuizByQuizId(quizId);
			mathQuizLt.add(mathQuiz);
			List<MathQuizQuestions> mathQuizQuestionsLt = mathAssessmentService.getMathQuizQuestionsByQuizId(quizId);
			mathQuizQuestionsLts.add(mathQuizQuestionsLt);
			List<StudentMathAssessMarks> studentMathAssessMarksLt = mathAssessmentService.getStudentMathAssessMarksByQuizId(quizId,assignmentId);
			studentMathAssessMarksLts.add(studentMathAssessMarksLt);
		}
		List<StudentAssignmentStatus> studentAssignmentStatusLt = mathAssessmentService.getStudentAssessmentTestsByPercentage(assignmentId,assignmentTypeId);
		model.addAttribute("mathQuizQuestionsLts", mathQuizQuestionsLts);
		model.addAttribute("studentAssignmentStatusLt", studentAssignmentStatusLt);
		model.addAttribute("studentMathAssessMarksLts", studentMathAssessMarksLts);
		model.addAttribute("mathQuizLt", mathQuizLt);
		return new ModelAndView("Ajax/Teacher/analysis_math_assessment_by_percentage");
	}
	
	@RequestMapping(value = "/assignMathGame", method = RequestMethod.GET)
	public ModelAndView assignMathGame(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		teacherGrades = curriculumService.getTeacherGrades(teacher);
		model.addAttribute("teacherGrades", teacherGrades);
		model.addAttribute("page", WebKeys.ASSIGN_GEAR_GAME);
		model.addAttribute("tab", WebKeys.MATH_ASSESMENT);
		model.addAttribute("usedFor", WebKeys.LP_USED_FOR_RTI);		
		return new ModelAndView("Teacher/assign_math_game");
	}
	
	@RequestMapping(value = "/assignGameToStudents", method = RequestMethod.POST)
	public @ResponseBody void assignGameToStudents(
			@RequestParam("csId") long csId,
			@RequestParam("dueDate") String dueDateStr,
			@RequestParam("titleId") String titleId,
			@RequestParam("instructId") String instructions,
			@RequestParam("students")  ArrayList<Long> students,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{					
			boolean status = mathAssessmentService.assignGameToStudents(csId,dueDateStr,titleId,instructions,students);
			if(status){
				response.getWriter().write(WebKeys.ASSIGNED_SUCCESSFULLY); 
			}else{
				response.getWriter().write(WebKeys.FAILED_TO_ASSIGNED); 
			}
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/html");  
	   }catch(Exception e){
			logger.error("Error in assignGameToStudents() of MathAssessmentController "+ e);
	   }
	}
	@RequestMapping(value = "/reviewMathGame", method = RequestMethod.GET)
	public ModelAndView reviewMathGame(HttpSession session,Model model) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherId", teacher.getTeacherId());
		model.addAttribute("teacherName", commonService.getFullName(teacher.getUserRegistration().getRegId()));
		teacherGrades = curriculumService.getTeacherGrades(teacher);
		model.addAttribute("teacherGrades", teacherGrades);
		AssignmentType assignmentType = assessmentDAO.getAssignmentTypeByAssignmentType(WebKeys.GEAR_GAME);
		model.addAttribute("assignmentTypeId", assignmentType.getAssignmentTypeId());
		model.addAttribute("page", WebKeys.GEAR_GAME);
		model.addAttribute("tab", WebKeys.MATH_ASSESMENT);
		model.addAttribute("usedFor", WebKeys.LP_USED_FOR_RTI);		
		return new ModelAndView("Teacher/review_math_game");
	}
	
	@RequestMapping(value = "/getMathGameResults", method = RequestMethod.GET)
	public ModelAndView getMathGameResults(HttpSession session,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			Model model) {
		List<MathGameScores> studsMathGameScoresLst=mathAssessmentService.getStudentMathGameScores(assignmentId);
		List<StudentAssignmentStatus> studentAssignmentStatusLt = mathAssessmentService.getStudentMathGameAssessmentTests(assignmentId,assignmentTypeId);
		model.addAttribute("studentAssignmentStatusLt", studentAssignmentStatusLt);
		model.addAttribute("studsMathGameScoresLst", studsMathGameScoresLst);
		return new ModelAndView("Ajax/Teacher/review_student_mathgame_details");
	}
	
	
}
