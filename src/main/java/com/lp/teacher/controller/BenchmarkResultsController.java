package com.lp.teacher.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.lp.admin.service.AdminService;
import com.lp.common.service.CurriculumService;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkResults;
import com.lp.model.ClassStatus;
import com.lp.model.FluencyAddedWords;
import com.lp.model.FluencyErrorTypes;
import com.lp.model.FluencyMarks;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.Grade;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.GradeAssessmentsService;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.WebKeys;

@Controller
public class BenchmarkResultsController {

	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private GradeAssessmentsService gradeAssessmentService;
	@Autowired 
	private HttpSession session;
	@Autowired
	private AdminService adminService;

	static final Logger logger = Logger.getLogger(AssignAssessmentController.class);

	@RequestMapping(value = "/benchmarkResults", method = RequestMethod.GET)
	public ModelAndView goToBenchmarkResults(HttpSession session) {

		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_RTI);
		ModelAndView model = new ModelAndView("Teacher/rti_results",
				"assignment", assignment);
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try {
			model.addObject("tab", WebKeys.LP_TAB_BENCHMARK_RESULTS);
			model.addObject("page", WebKeys.LP_TAB_BENCHMARK_RESULTS);
			model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
			model.addObject("teacherGrades",
					curriculumService.getTeacherGradesByAcademicYr(teacherObj));
		} catch (Exception e) {
			logger.error("Error in benchmarkResults() of BenchmarkResultsController "
					+ e);
			model.addObject("hellowAjax", e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/getBenchmarkDates", method = RequestMethod.GET)
	public View getBenchmarkDates(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId, Model model) {
		Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherAssignedDates",
				teacherService.getBenchmarkDates(teacher, csId, usedFor));
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/showBenchmarkResults", method = RequestMethod.GET)
	public ModelAndView goToGradeRti(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,
			@RequestParam("dateAssigned") String dateAssigned,
			@RequestParam("titleId") long assignmentId) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<String> rtiGroupNames = new ArrayList<String>();
		List<Integer> compositeScores = new ArrayList<Integer>();
		ClassStatus clsSt = (ClassStatus) teacherService.getClassStatus(csId);
		ModelAndView model = new ModelAndView("Ajax/Teacher/benchmark_results",
				"BenchmarkResults", new BenchmarkResults());
		List<BenchmarkResults> benchmarkResults = teacherService
				.getBenchmarkResults(teacherObj, csId, usedFor, dateAssigned,
						assignmentId);
		model.addObject("benchmarkResults", benchmarkResults);
		rtiGroupNames = teacherService
				.getAllStudentRTIGroupName(benchmarkResults);
		compositeScores = teacherService
				.getAllStudentCompositeScore(benchmarkResults);
		model.addObject("rtiGroupNames", rtiGroupNames);
		model.addObject("compositeScores", compositeScores);
		model.addObject("teacher", teacherObj.getUserRegistration());
		model.addObject("tab", WebKeys.LP_TAB_GRADE_RTI);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("GradeLevel", clsSt.getSection().getGradeClasses()
				.getGrade().getMasterGrades().getGradeName());
		model.addObject("ClsStatus", clsSt);
		model.addObject("AssignmentId", assignmentId);
		return model;
	}

	@RequestMapping(value = "/exportFluencyReadingResultsss", method = RequestMethod.GET)
	public ModelAndView getEarlyLitracyExcel(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,
			@RequestParam("dateAssigned") String dateAssigned,
			@RequestParam("title") long assignmentId) {

		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<BenchmarkResults> fluencyResults = teacherService
				.getBenchmarkResults(teacherObj, csId, usedFor, dateAssigned,
						assignmentId);
		return new ModelAndView("fluencyResultsExcelView", "fluencyResults",
				fluencyResults);

	}

	@RequestMapping(value = "/AllStudentFluencyErrorWordList", method = RequestMethod.GET)
	public ModelAndView getAllStudentFluencyErrorWordList(HttpSession session,
			@RequestParam("assignmentId") long assignmentId,@RequestParam("type") long type) {
		List<StudentAssignmentStatus> stuAssignStatusList = new ArrayList<StudentAssignmentStatus>();
		List<List<Integer>> errorsList = new ArrayList<List<Integer>>();
		ModelAndView model = new ModelAndView("Ajax/Teacher/student_errorWord_results");
		List<FluencyErrorTypes> listErrorTypes=new ArrayList<FluencyErrorTypes>();
		listErrorTypes=teacherService.getFluencyErrorTypes();
		try {
			stuAssignStatusList = teacherService.getStudentAssignmentStatusList(assignmentId);
			if(type==1 || type==2 || type==5){
			List<FluencyMarksDetails> totErrors = new ArrayList<FluencyMarksDetails>();
			totErrors = teacherService.getErrorWordCount(assignmentId);
			errorsList = teacherService.getErrorWordDetails(totErrors, stuAssignStatusList,type);
			model.addObject("totErrors", totErrors);
			}else if(type==4){
				List<FluencyAddedWords> totErrors = new ArrayList<FluencyAddedWords>();
				totErrors = teacherService.getAddedWordCount(assignmentId,1);
				errorsList = teacherService.getAddedWordDetails(totErrors, stuAssignStatusList,1);
				model.addObject("totErrors", totErrors);
			}else if(type==3){
				List<FluencyAddedWords> totErrors = new ArrayList<FluencyAddedWords>();
				totErrors = teacherService.getAddedWordCount(assignmentId,2);
				errorsList = teacherService.getAddedWordDetails(totErrors, stuAssignStatusList,2);
				model.addObject("totErrors", totErrors);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("errorsList", errorsList);
		model.addObject("studentList", stuAssignStatusList);
		model.addObject("assignmentId",assignmentId);
		model.addObject("type",type);
		model.addObject("errorTypes",listErrorTypes);
		return model;

	}
	@RequestMapping(value = "/selfAndPeerReviewResults", method = RequestMethod.GET)
	public ModelAndView selfAndPeerReviewResults(HttpSession session) {
		Teacher teacherObj=null;
		ModelAndView model =null;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_RTI);
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try {
			if(userReg.getUser().getUserTypeid()==3){
				teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
				model = new ModelAndView("Teacher/self_peer_review_results",
						"assignment", assignment);
				model.addObject("teacherGrades",curriculumService.getTeacherGradesByAcademicYr(teacherObj));
				model.addObject("tab", WebKeys.LP_TAB_BENCHMARK_RESULTS);
				model.addObject("page", WebKeys.LP_PAGE_SELF_PEER_RESULTS);	
			}else{
				model = new ModelAndView("Ajax/Admin/fluency_results");
				model.addObject("teacherGrades",adminService.getSchoolGrades(userReg.getSchool().getSchoolId()));
				model.addObject("tab", WebKeys.LP_PAGE_SELF_PEER_RESULTS);
			}
			
			model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
			model.addObject("userTypeId",userReg.getUser().getUserTypeid());
		} catch (Exception e) {
			logger.error("Error in selfAndPeerReviewResults() of BenchmarkResultsController "
					+ e);
			model.addObject("hellowAjax", e.getMessage());
		}
		return model;
	}
	
	@RequestMapping(value = "/getAccuracyDates", method = RequestMethod.GET)
	public View getAccuracyDates(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId, Model model) {
		//Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addAttribute("teacherAssignedDates",
				teacherService.getAccuracyDates(csId, usedFor));
		return new MappingJackson2JsonView();
	}

	/*@RequestMapping(value = "/exportSelfAndPeerReports", method = RequestMethod.GET)
	public ModelAndView exportSelfAndPeerReports(HttpServletResponse response, HttpServletRequest request) {
		ModelAndView model=null;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Map<String,List<FluencyMarks>> studSelfAndPeerResults = new HashMap<String,List<FluencyMarks>>();
		long assignmentId = Long.parseLong(request.getParameter("titleId"));
		List<Student> studentLst=teacherService.getAllStudentsByAssignmentId(assignmentId);
		List<AssignmentQuestions> assignQuestLst= new ArrayList<AssignmentQuestions>();
		List<FluencyMarks> fluencyMarksLst= new ArrayList<FluencyMarks>();
		for (Student student : studentLst) {
			String s="stud"+student.getStudentId();
			assignQuestLst=gradeAssessmentService.getStudentAssignmentQuestions(student.getStudentId(),assignmentId);
			fluencyMarksLst=gradeAssessmentService.getStudSelfAndPeerFluencyMarks(assignQuestLst.get(0).getAssignmentQuestionsId(), 1);
			studSelfAndPeerResults.put(s, fluencyMarksLst);
		}
		response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentSelfAndPeerReports.xls\"");
		model = new ModelAndView("studSelfAndPeerReportsExcel", "listStudSelfAndPeerReports", studSelfAndPeerResults);
		model.addObject("studentLst",studentLst);
		model.addObject("userTypeId",userReg.getUser().getUserTypeid());
		return model; 
  }*/
	
	@RequestMapping(value = "/exportSelfAndPeerReports", method = RequestMethod.GET)
	public ModelAndView exportSelfAndPeerReports(HttpServletResponse response, HttpServletRequest request) {
		ModelAndView model=null;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long assignmentId = Long.parseLong(request.getParameter("titleId"));
		List<FluencyMarks> fluencyMarksLst= new ArrayList<FluencyMarks>();
		fluencyMarksLst=gradeAssessmentService.getStudSelfAndPeerFluencyMarks(assignmentId);
		response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentSelfAndPeerReports.xls\"");
		model = new ModelAndView("studSelfAndPeerReportsExcel", "listStudSelfAndPeerReports", fluencyMarksLst);
		model.addObject("userTypeId",userReg.getUser().getUserTypeid());
		return model; 
  }

}