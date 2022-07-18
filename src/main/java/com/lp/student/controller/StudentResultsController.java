package com.lp.student.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import com.lp.common.service.CommonService;
import com.lp.common.service.PerformanceTaskService;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.AssignmentType;
import com.lp.model.ClassStatus;
import com.lp.model.FluencyMarks;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.Grade;
import com.lp.model.GradingTypes;
import com.lp.model.JacTemplate;
import com.lp.model.Language;
import com.lp.model.MathGameScores;
import com.lp.model.MathQuizQuestions;
import com.lp.model.QualityOfResponse;
import com.lp.model.RegisterForClass;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentMathAssessMarks;
import com.lp.model.SubQuestions;
import com.lp.model.UserRegistration;
import com.lp.student.service.StudentService;
import com.lp.student.service.StudentTestResultsService;
import com.lp.student.service.TakeAssessmentsService;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.teacher.service.AssignAssessmentsService;
import com.lp.teacher.service.GradeAssessmentsService;
import com.lp.teacher.service.GradeBookService;
import com.lp.teacher.service.MathAssessmentService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class StudentResultsController extends WebApplicationObjectSupport{

	@Autowired
	private HttpSession session;
	@Autowired
	private StudentTestResultsService studentTestResultsService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private GradeAssessmentsService gradeAssessmentsService;
	@Autowired
	private CommonService commonService;
	@Autowired
	AssignAssessmentsService assignAssessmentService;
	@Autowired
	TakeAssessmentsService takeAssessmentsService;
	@Autowired
	private PerformanceTaskService performanceTaskService;
	@Autowired
	private MathAssessmentService mathAssessmentService;
	@Autowired
	private GradeBookService gradeBookService;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDao;

	static final Logger logger = Logger.getLogger(StudentResultsController.class);

	@RequestMapping(value = "/studentTestResults", method = RequestMethod.GET)
	public ModelAndView studentTestResults() {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		Grade grade = new Grade();
		List<RegisterForClass> GradeClasses = Collections.emptyList();
		try {			
			AcademicYear academicYear = null;
			session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
			if(session.getAttribute("academicYear") != null){
				academicYear = (AcademicYear) session.getAttribute("academicYear");
				GradeClasses = studentService.getStudentGradeByYear(academicYear);
				if(GradeClasses.size()>0){
					grade = GradeClasses.get(0).getGradeClasses().getGrade();
				}
			}else{	
				grade = student.getGrade();
				GradeClasses = studentService.getStudentClasses(student);
			}
		} catch (DataException e) {
			logger.error("Error in studentTestResults() of StudentResultsController" + e.getStackTrace());
		}
		ModelAndView model = new ModelAndView("Student/student_test_results", "student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("grade", grade);
		model.addObject("GradeClasses", GradeClasses);
		model.addObject("tab", WebKeys.LP_TAB_RTI_TEST_RESULTS);
		return model;
	}

	@RequestMapping(value = "/studentTestGraded", method = RequestMethod.GET)
	public ModelAndView studentTestGraded(
			@RequestParam("classId") long classId,
			@RequestParam("gradeName") String gradeName,
			@RequestParam("studentId") long studentId,
			@RequestParam("usedFor") String usedFor, Model model) {
		UserRegistration userRegistration = (UserRegistration) session
				.getAttribute(WebKeys.LP_USER_REGISTRATION);
		String studentName = commonService.getFullName(userRegistration
				.getRegId());
		List<StudentAssignmentStatus> studentAssignmentStatusLt = new ArrayList<StudentAssignmentStatus>();
		try {
			studentAssignmentStatusLt = studentTestResultsService.getStudentsTestResults(classId, usedFor, studentId);
		} catch (DataException e) {
			logger.error("Error in studentTestGraded() of StudentResultsController" + e.getStackTrace());
		}
		model.addAttribute("studentAssignmentStatusLt",
				studentAssignmentStatusLt);
		model.addAttribute("gradeName", gradeName);
		model.addAttribute("studentName", studentName);
		model.addAttribute("studentId",studentId);
		return new ModelAndView("Ajax/Student/student_tests_graded");
	}

	@RequestMapping(value = "/assessmentsCompleted", method = RequestMethod.GET)
	public ModelAndView assessmentsCompleted() {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		Grade grade = new Grade();
		List<RegisterForClass> gradeClasses = Collections.emptyList();
		try {
			grade = student.getGrade();
			gradeClasses = studentService.getStudentClasses(student);
		} catch (DataException e) {
			logger.error("Error in assessmentsCompleted() of StudentResultsController" + e.getStackTrace());
		}
		ModelAndView model = new ModelAndView("Student/student_test_results", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("grade", grade);
		model.addObject("GradeClasses", gradeClasses);
		model.addObject("tab", WebKeys.LP_TAB_VIEW_ASSESSMENT_COMPLETED);
		return model;
	}

	// For student progress report page
		@RequestMapping(value = "/studentProgressReports", method = RequestMethod.GET)
		public ModelAndView studentProgressReports(HttpSession session,
				HttpServletRequest request, Model model) {
			Student student = (Student) session
					.getAttribute(WebKeys.STUDENT_OBJECT);
			List<RegisterForClass> studentClasslist = new ArrayList<RegisterForClass>();
			try {
				studentClasslist = studentService.getStudentClasses(student);
			} catch (DataException e) {
				logger.error("Error in studentProgressReports() of StudentResultsController" + e.getStackTrace());
			}
			model.addAttribute("tab", WebKeys.LP_TAB_PROGRESS_REPORTS);
			model.addAttribute("student", student);
			model.addAttribute("studentClassList", studentClasslist);
			//return new ModelAndView("Student/progress_reports");
			return new ModelAndView("CommonJsp/view_progress_report_tabs");
		}
		
		@RequestMapping(value = "/studentProgress", method = RequestMethod.GET)
		public ModelAndView studentProgress(HttpSession session,
				HttpServletRequest request, Model model) {
			Student student = (Student) session
					.getAttribute(WebKeys.STUDENT_OBJECT);
			List<RegisterForClass> studentClasslist = new ArrayList<RegisterForClass>();
			try {
				studentClasslist = studentService.getStudentClasses(student);
			} catch (DataException e) {
				logger.error("Error in studentProgressReports() of StudentResultsController" + e.getStackTrace());
			}
			model.addAttribute("tab", WebKeys.LP_TAB_PROGRESS_REPORTS);
			model.addAttribute("student", student);
			model.addAttribute("studentClassList", studentClasslist);
			return new ModelAndView("Ajax/Student/progress_reports");
		}

	@RequestMapping(value = "/studentBenchmarkResults", method = RequestMethod.GET)
	public ModelAndView studentBenchmarkResults() {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		Grade grade = new Grade();
		List<RegisterForClass> gradeClasses = Collections.emptyList();
		try {
			AcademicYear academicYear = null;
			session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
			if(session.getAttribute("academicYear") != null){
				academicYear = (AcademicYear) session.getAttribute("academicYear");
				gradeClasses = studentService.getStudentGradeByYear(academicYear);
				if(gradeClasses.size()>0){
					grade = gradeClasses.get(0).getGradeClasses().getGrade();
				}
			}else{	
				grade = student.getGrade();
				gradeClasses = studentService.getStudentClasses(student);
			}
		} catch (DataException e) {
			logger.error("Error in studentBenchmarkResults() of StudentResultsController" + e.getStackTrace());
		}
		ModelAndView model = new ModelAndView("Student/student_benchmark_results", "student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("grade", grade);
		model.addObject("GradeClasses", gradeClasses);
		model.addObject("tab", WebKeys.LP_TAB_BENCHMARK_RESULTS);
		model.addObject("page",WebKeys.LP_TAB_BENCHMARK_RESULTS);
		model.addObject("assignmentTypeId", 8);
		return model;
	}

	@RequestMapping(value = "/getStudentBenchmarkResults", method = RequestMethod.GET)
	public ModelAndView getBenchmarkCompletedTests(Model model,
			@RequestParam("classId") long classId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("assignmentTypeId") long assignmentTypeId) {
		List<StudentAssignmentStatus> studentAssignmentStatusLt = new ArrayList<StudentAssignmentStatus>();
		try {
			studentAssignmentStatusLt = studentTestResultsService.getStudentsBenchmarkTestResults(classId, usedFor, assignmentTypeId);
		} catch (DataException e) {
			logger.error("Error in getStudentBenchmarkResults() of StudentResultsController" + e.getStackTrace());
		}
		model.addAttribute("studentAssignmentStatusLt", studentAssignmentStatusLt);
		return new ModelAndView("Ajax/Student/completed_fluency_results");
	}

	@RequestMapping(value = "/getStudentBenchmarkTests", method = RequestMethod.GET)
	public ModelAndView getBenchmarkCompletedTests(
			@RequestParam("classId") long classId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("gradingTypesId") long gradingTypesId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,Model model) {
		List<StudentAssignmentStatus> studentAssignmentStatusLt = new ArrayList<StudentAssignmentStatus>();
		try {
			studentAssignmentStatusLt = studentTestResultsService.getStudentBenchmarkTests(classId, usedFor, gradingTypesId,assignmentTypeId);
		} catch (DataException e) {
			logger.error("Error in getStudentBenchmarkTests() of StudentResultsController" + e.getStackTrace());
		}
		model.addAttribute("studentAssignmentStatusLt", studentAssignmentStatusLt);
		model.addAttribute("gradingTypesId", gradingTypesId);
		model.addAttribute("assignmentTypeId",assignmentTypeId);
		return new ModelAndView("Ajax/Student/completed_benchmark_tests");
	}

	@RequestMapping(value = "/getCompletedTestQuestions", method = RequestMethod.GET)
	public ModelAndView getCompletedTestQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("assignmentId") long assignmentId,
			HttpServletRequest request,HttpSession session) {
		List<AssignmentQuestions> questions = gradeAssessmentsService.getTestQuestions(studentAssignmentId);
		StudentAssignmentStatus studentAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
		studentAssignmentStatus.setAssignmentQuestions(questions);
		ModelAndView model = null;
		String teacherComment = "";
		if (assignmentTypeId == 8) {
			model = new ModelAndView(
					"Ajax/Student/include_student_completed_test_questions",
					"studentAssignmentStatus", studentAssignmentStatus);
			teacherComment = gradeAssessmentsService
					.getBenchmarkTeacherComment(studentAssignmentId);
			model.addObject("teacherComment", teacherComment);

		} else if (assignmentTypeId == 13) {
			model = new ModelAndView("Ajax/Teacher/performance_test",
					"studentAssignmentStatus", studentAssignmentStatus);
		} else {
			model = new ModelAndView(
					"Ajax/Student/include_student_completed_test_questions",
					"studentAssignmentStatus", studentAssignmentStatus);

		}
		model.addObject("testQuestions", questions);
		if (assignmentTypeId == 7) {
			List<SubQuestions> ssQuestions = takeAssessmentsService
					.getComprehensionQuestions(questions);
			model.addObject("ssQuestions", ssQuestions);
		}
		if (assignmentTypeId == 19) {
			List<SubQuestions> compreQuestions = gradeAssessmentsService.getSSQuestions(questions);
			model.addObject("compreQuestions", compreQuestions);
			
			Student student = gradeBookService.getStudentById(studentAssignmentStatus.getStudent().getStudentId()); 
			String uploadFilePath = FileUploadUtil.getUploadFilesPath(student.getUserRegistration(), request);
			String comprehensionFilePath = "";
			comprehensionFilePath = uploadFilePath+File.separator+WebKeys.ASSIGNMENT_TYPE_COMPREHENSION+File.separator+studentAssignmentId;
			model.addObject("comprehensionFilePath", comprehensionFilePath);
			
		}
		if (assignmentTypeId == 14) {
			model = new ModelAndView(
					"Ajax/Student/include_jactemplate_questions",
					"studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("testQuestions", questions);
			List<JacTemplate> jacTitles = gradeAssessmentsService
					.getJacTemplateTitleList(questions);
			model.addObject("jacTitles", jacTitles);
			String jacQuestionFilePath = gradeAssessmentsService
					.getJacQuestionFilePath(jacTitles.get(0)
							.getJacQuestionFile());
			model.addObject("jacQuestionFilePath", jacQuestionFilePath);

		}
		if (assignmentTypeId == 21) {
			model = new ModelAndView("Ajax/Teacher/grade_math_assessment_student_details");
				List<List<MathQuizQuestions>> mathQuizQuestionsLts = new ArrayList<List<MathQuizQuestions>>();
				List<List<StudentMathAssessMarks>> studentMathAssessMarksLts = new ArrayList<List<StudentMathAssessMarks>>();
				studentAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);				
				Assignment assignment = studentAssignmentStatus.getAssignment();
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
				model.addObject("loginFrom", "student");
				model.addObject("sAssignmentStatus",studentAssignmentStatus);

		}
		if (assignmentTypeId == 30) {
			model = new ModelAndView("Ajax/Student/student_mathgame_details");
			List<MathGameScores> studsMathGameScoresLst=mathAssessmentService.getStudentMathGameDetails(studentAssignmentId);
    		model.addObject("studsMathGameScoresLst", studsMathGameScoresLst);
		}
		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("assignmentId", assignmentId);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("usedFor", usedFor);
		model.addObject("assignmentType", gradeAssessmentsService.getAssignment(assignmentId).getAssignmentType());
		return model;
	}

	@RequestMapping(value = "/studentEvaluateHomeworks", method = RequestMethod.GET)
	public ModelAndView studentEvaluateHomeworks() {
		Student student = (Student) session
				.getAttribute(WebKeys.STUDENT_OBJECT);
		List<RegisterForClass> studentClasslist = new ArrayList<RegisterForClass>();
		Grade grade = new Grade();
		try {
			grade = student.getGrade();
			studentClasslist = studentService.getStudentClasses(student);
		} catch (DataException e) {
			logger.error("Error in studentEvaluateHomeworks() of StudentResultsController" + e.getStackTrace());
		}
		ModelAndView model = new ModelAndView("Student/student_evaluate_homework_reports", "student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_HOMEWORKS);
		model.addObject("grade", grade);
		model.addObject("studentClassList", studentClasslist);
		model.addObject("tab", WebKeys.LP_TAB_HOMEWORK_REPORTS);
		return model;
	}

	@RequestMapping(value = "/getHomeworkDates", method = RequestMethod.GET)
	public View getHomeworkAssignedDates(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId, Model model) {
		Student student = (Student) session
				.getAttribute(WebKeys.STUDENT_OBJECT);
		try {
			model.addAttribute("teacherAssignedDates",
					studentTestResultsService.getHomeworkAssignedDates(csId,
							usedFor, student));
		} catch (DataException e) {
			logger.error("Error in getHomeworkDates() of StudentResultsController" + e.getStackTrace());
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/getHomeworkReportsByDate", method = RequestMethod.GET)
	public ModelAndView getHomeworkReports(@RequestParam("csId") long csId,
			@RequestParam("assignedDate") String assignedDate,
			HttpSession session, @RequestParam("usedFor") String usedFor,
			HttpServletResponse response, HttpServletRequest request) {
		List<StudentAssignmentStatus> homeworkReports = new ArrayList<StudentAssignmentStatus>();
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		try {
			homeworkReports = studentTestResultsService.getHomeworkReports(csId, usedFor, assignedDate, student);
		} catch (DataException e) {
			logger.error("Error in getHomeworkReportsByDate() of StudentResultsController" + e.getStackTrace());
		}
		ModelAndView model = new ModelAndView("Ajax/Student/show_homework_reports", "assignment", new StudentAssignmentStatus());
		model.addObject("homeworkReportList", homeworkReports);

		return model;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getStudentsBenchmarkTest", method = RequestMethod.GET)
	public ModelAndView getTestQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("benchmarkId") String benchmarkId,
			@RequestParam("readingTypesId") long readingTypesId,
			@RequestParam("marks") long marks,
			@RequestParam("regrade") String regrade,
			HttpServletRequest request, HttpSession session,
			@RequestParam("butt") String butt,
			@RequestParam("classId") long classId,
			@RequestParam("gradingTypesId") long gradingTypesId,
			@RequestParam("langId") long langId) {
		AssignmentQuestions assignmentQuestions = new AssignmentQuestions();
		FluencyMarks fluencyMarksList = new FluencyMarks();
		List<FluencyMarksDetails> errorsList = Collections.emptyList();
		String path = "";
		List<QualityOfResponse> qualityResponses = Collections.emptyList();
		int correctWords=0;
		long wordsRead=0;
		ModelAndView model = null;
		long csId = 0;

		if (benchmarkId.equals("4")) {
			benchmarkId = String.valueOf(readingTypesId);
		} else {
			benchmarkId = benchmarkId + "." + readingTypesId;
		}

		if (regrade.equalsIgnoreCase("yes")) {
			gradeAssessmentsService.deleteBenchmarkTypeMarks(studentAssignmentId, assignmentQuestionId, readingTypesId, gradingTypesId);
		}

		assignmentQuestions = gradeAssessmentsService
				.getAssignmentQuestions(assignmentQuestionId);
		fluencyMarksList = gradeAssessmentsService.getFluencyMarks(
				assignmentQuestionId, readingTypesId, gradingTypesId);

		if (fluencyMarksList.getReadingTypes().getReadingTypesId() == 1
				|| fluencyMarksList.getReadingTypes().getReadingTypesId() == 2) {
			if (fluencyMarksList.getWordsRead() != null) {
				wordsRead = fluencyMarksList.getWordsRead();
				correctWords = fluencyMarksList.getWordsRead() - fluencyMarksList.getCountOfErrors();
				errorsList = gradeAssessmentsService.getErrorsList(fluencyMarksList.getFluencyMarksId());
				
			}
		}
		if (fluencyMarksList.getReadingTypes().getReadingTypesId() == 2) {
			csId = assignmentQuestions.getStudentAssignmentStatus()
					.getAssignment().getClassStatus().getCsId();
			
			if(regrade.equalsIgnoreCase("no")){
				model = new ModelAndView("Ajax/Teacher/benchmark_fluency_test",	"assignmentQuestions", assignmentQuestions);
	    	}else{
	    		   model = new ModelAndView("Ajax/Teacher/benchmark_fluency_test","assignmentQuestions", assignmentQuestions);
			  
	    	}					
			
			model.addObject("passage", assignmentQuestions.getQuestions().getQuestion());
			
			model.addObject("correctWords", correctWords);
			model.addObject("errorsList", errorsList);
			model.addObject("fluencyMarks", fluencyMarksList);
			model.addObject("gradePage", "student");
			path = FileUploadUtil.getUploadFilesPath(assignmentQuestions.getStudentAssignmentStatus().getStudent().getUserRegistration(), request)
					+ File.separator
					+ WebKeys.STUDENT_BENCH_MARK_TESTS
					+ File.separator
					+ assignmentQuestionId
					+ File.separator
					+ WebKeys.FLUENCY_FILE_NAME;
			model.addObject("filename", path);
			model.addObject("usersFilePath", WebKeys.STUDENT_BENCH_MARK_TESTS
					+ File.separator + assignmentQuestionId + File.separator
					+ WebKeys.FLUENCY_FILE_NAME);
			model.addObject("csId", csId);

		} else {
			qualityResponses = gradeAssessmentsService.getQualityOfResponse();
			model = new ModelAndView("Ajax/Teacher/retell_fluency_test", "assignmentQuestions", assignmentQuestions);
			model.addObject("fluencyMarks", fluencyMarksList);
			model.addObject("qualityResponseList", qualityResponses);
			path = FileUploadUtil.getUploadFilesPath(assignmentQuestions.getStudentAssignmentStatus().getStudent().getUserRegistration(), request)
					+ File.separator
					+ WebKeys.STUDENT_BENCH_MARK_TESTS
					+ File.separator
					+ assignmentQuestionId
					+ File.separator
					+ WebKeys.RETELL_FILE_NAME;
			model.addObject("filename", path);
			model.addObject("usersFilePath", WebKeys.STUDENT_BENCH_MARK_TESTS
					+ File.separator + assignmentQuestionId + File.separator
					+ WebKeys.RETELL_FILE_NAME);

		}

		ArrayList<String> benchmarkqList = new ArrayList(Arrays.asList(assignmentQuestions.getQuestions().getQuestion().replaceAll("[\\r\\n]+", " ").split(" ")));
		String questions = assignmentQuestions.getQuestions().getQuestion().replaceAll("[\\r\\n]+", " ");
		questions = assignmentQuestions.getQuestions().getQuestion().replaceAll(",", "");
		questions = questions.replaceAll("\\?", "");
		questions = questions.replaceAll("\\\"", "");
		questions = questions.replaceAll("\\\'", "");
		questions = questions.replaceAll("!", "");
		ArrayList<String> benchmarkqList1 = new ArrayList(Arrays.asList(questions.split(" ")));
		model.addObject("regrade", regrade);
		model.addObject("studentRegId", assignmentQuestions.getStudentAssignmentStatus().getStudent().getUserRegistration().getRegId());
		model.addObject("benchmarkId", benchmarkId);
		model.addObject("benchmarkqList", benchmarkqList);
		model.addObject("assignQuestions", assignmentQuestions);
		model.addObject("readingTypesId", readingTypesId);
		model.addObject("butt", butt);
		model.addObject("assignmentTitle", assignmentQuestions.getStudentAssignmentStatus().getAssignment().getTitle());
		model.addObject("wordsRead", wordsRead);
		model.addObject("benchmarkqList1", benchmarkqList1);
		model.addObject("gradeTypesId", gradingTypesId);
		model.addObject("langId", langId);
		model.addObject("studentId", assignmentQuestions.getStudentAssignmentStatus().getStudent().getStudentId());
		return model;
	}

	@RequestMapping(value = "/getBenchmarkQuestsByGradingTypesId", method = RequestMethod.GET)
	public ModelAndView getBenchmarkQuestsByGradingTypesId(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("assignmentId") long assignmentId,
			HttpServletRequest request, HttpSession session,
			@RequestParam("gradingTypesId") long gradingTypesId) {
		List<AssignmentQuestions> questions = gradeAssessmentsService.getTestQuestions(studentAssignmentId);
		List<String> fluencyFilenames = new ArrayList<String>();
		List<String> retellFilenames = new ArrayList<String>();
		List<String> accuracyFilenames = new ArrayList<String>();
		StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
		studentAssignmentStatus.setStudentAssignmentId(studentAssignmentId);
		studentAssignmentStatus.setAssignmentQuestions(questions);
		ModelAndView model = null;
		List<AssignmentQuestions> benchQuestions = null;
		
		if(assignmentTypeId==8){
			model = new ModelAndView("Ajax/Student/show_benchmark_questions", "studentAssignmentStatus", studentAssignmentStatus);
		}else{
			model = new ModelAndView("Ajax/Student/show_accuracy_questions", "studentAssignmentStatus", studentAssignmentStatus);
		}

		benchQuestions = gradeAssessmentsService.getBenchmarkQuestBygradeTyId(questions, gradingTypesId);

		for (AssignmentQuestions assignques : questions) {
			assignques.getAssignmentQuestionsId();
			String accuracyPath = FileUploadUtil.getUploadFilesPath(assignques.getStudentAssignmentStatus().getStudent().getUserRegistration(), request)
					+ File.separator
					+ WebKeys.STUDENT_BENCH_MARK_TESTS
					+ File.separator
					+ assignques.getAssignmentQuestionsId()
					+ File.separator + WebKeys.ACCURACY_FILE_NAME;
			accuracyFilenames.add(accuracyPath);
			String path = FileUploadUtil.getUploadFilesPath(assignques.getStudentAssignmentStatus().getStudent().getUserRegistration(), request)
					+ File.separator
					+ WebKeys.STUDENT_BENCH_MARK_TESTS
					+ File.separator
					+ assignques.getAssignmentQuestionsId()
					+ File.separator + WebKeys.FLUENCY_FILE_NAME;
			fluencyFilenames.add(path);
			String retellPath = FileUploadUtil.getUploadFilesPath(assignques.getStudentAssignmentStatus().getStudent().getUserRegistration(), request)
					+ File.separator
					+ WebKeys.STUDENT_BENCH_MARK_TESTS
					+ File.separator
					+ assignques.getAssignmentQuestionsId()
					+ File.separator + WebKeys.RETELL_FILE_NAME;
			retellFilenames.add(retellPath);

		}

		model.addObject("fluencyFilenames", fluencyFilenames);
		model.addObject("retellFilenames", retellFilenames);
		model.addObject("accuracyFilenames", accuracyFilenames);
		model.addObject("testQuestions", benchQuestions);
		model.addObject("passageCount", benchQuestions.size());
		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("assignmentId", assignmentId);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("gradeTypesId", gradingTypesId);
		return model;
	}

	@RequestMapping(value = "/getStudentBenchmarkQuestions", method = RequestMethod.GET)
	public ModelAndView getStudentBenchmarkQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("assignmentId") long assignmentId,
			HttpServletRequest request, HttpSession session) {
		List<String> fluencyFilenames = new ArrayList<String>();
		List<String> retellFilenames = new ArrayList<String>();
		List<String> accuracyFilenames = new ArrayList<String>();
		StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
		studentAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
		ModelAndView model = null;
		Student student = studentAssignmentStatus.getStudent();
		model = new ModelAndView("Ajax/Student/display_grade_types", "studentAssignmentStatus", studentAssignmentStatus);
		List<GradingTypes> gradingTypes = gradeAssessmentsService.getGradeTypes();
		model.addObject("gradingTypes", gradingTypes);
		//model.addObject("lessonId", studentAssignmentStatus.getAssignment().getLesson().getLessonId());
		model.addObject("csId", studentAssignmentStatus.getAssignment().getClassStatus().getCsId());

		model.addObject("fluencyFilenames", fluencyFilenames);
		model.addObject("retellFilenames", retellFilenames);
		model.addObject("accuracyFilenames", accuracyFilenames);

		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("assignmentId", assignmentId);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("studentId", student.getStudentId());
		model.addObject("userTypeId", student.getUserRegistration().getUser().getUserTypeid());
		model.addObject("assignmentType", studentAssignmentStatus.getAssignment().getAssignmentType());
		return model;
	}

	@RequestMapping(value = "/goToStudentRFLPGradingPage", method = RequestMethod.GET)
	public ModelAndView studentSelfRFLPGrading() {
		Student student = (Student) session
				.getAttribute(WebKeys.STUDENT_OBJECT);
		List<RegisterForClass> studentClasslist = new ArrayList<RegisterForClass>();
		Grade grade = new Grade();
		try {
			grade = student.getGrade();
			studentClasslist = studentService.getStudentClasses(student);
		} catch (DataException e) {
			logger.error("Error in studentTestResults() of StudentResultsController" + e.getStackTrace());
		}
		ModelAndView model = new ModelAndView("Student/rflp_self_grading", "student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("grade", grade);
		model.addObject("studentClassList", studentClasslist);
		model.addObject("tab", WebKeys.LP_TAB_RFLP_GRADING);
		model.addObject("gradeTypesId", 2);
		return model;
	}

	@RequestMapping(value = "/getRFLPHomeworks", method = RequestMethod.GET)
	public ModelAndView getRFLPHomeworks(
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,
			@RequestParam("gradeTypesId") long gradeTypesId, Model model) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		List<RflpTest> rflpHomeworksList = new ArrayList<RflpTest>();
		try {
			rflpHomeworksList = studentTestResultsService.getRFLPHomeworks(csId, usedFor, student, gradeTypesId);
		} catch (Exception e) {
			logger.error("Error in getRFLPHomeworks() of StudentResultsController" + e.getStackTrace());
		}
		model.addAttribute("rflpHomeworksList", rflpHomeworksList);
		model.addAttribute("gradeTypesId", gradeTypesId);
		return new ModelAndView("Ajax/Student/show_rflp_homeworks");
	}

	@RequestMapping(value = "/rflpPeerGrading", method = RequestMethod.GET)
	public ModelAndView rflpPeerGrading() {
		Student student = (Student) session
				.getAttribute(WebKeys.STUDENT_OBJECT);
		List<RegisterForClass> studentClasslist = new ArrayList<RegisterForClass>();
		Grade grade = new Grade();
		try {
			grade = student.getGrade();
			studentClasslist = studentService.getStudentClasses(student);
		} catch (DataException e) {
			logger.error("Error in rflpPeerGrading() of StudentResultsController" + e.getStackTrace());
		}
		ModelAndView model = new ModelAndView("Student/rflp_self_grading", "student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_HOMEWORKS);
		model.addObject("grade", grade);
		model.addObject("studentClassList", studentClasslist);
		model.addObject("tab", WebKeys.LP_TAB_RFLP_GRADING);
		model.addObject("gradeTypesId", 3);
		return model;
	}

	@RequestMapping(value = "/GradeSelfAndPeerBenchmark", method = RequestMethod.GET)
	public @ResponseBody
	void gradeTests(HttpSession session, HttpServletResponse response,
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("gradeTypesId") long gradeTypesId, Model model) {
		try {
			String helloAjax = "";
			boolean check = gradeAssessmentsService.gradeSelfAndPeerBenchmark(studentAssignmentId, gradeTypesId);
			if (check)
				helloAjax = WebKeys.TEST_GRADED_SUCCESS;
			else
				helloAjax = WebKeys.TEST_GRADED_FAILURE;
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			logger.error("Error in GradeSelfAndPeerBenchmark() of StudentResultsController" + e.getStackTrace());
		}

	}

	@RequestMapping(value = "/studentAccuracyResults", method = RequestMethod.GET)
	public ModelAndView studentAccuracyResults() {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		Grade grade = new Grade();
		List<RegisterForClass> gradeClasses = Collections.emptyList();
		try {			
			AcademicYear academicYear = null;
			session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
			if(session.getAttribute("academicYear") != null){
				academicYear = (AcademicYear) session.getAttribute("academicYear");
				gradeClasses = studentService.getStudentGradeByYear(academicYear);
				if(gradeClasses.size()>0){
					grade = gradeClasses.get(0).getGradeClasses().getGrade();
				}
			}else{		
				grade = student.getGrade();
				gradeClasses = studentService.getStudentClasses(student);
			}
		} catch (DataException e) {
			logger.error("Error in studentAccuracyResults() of StudentResultsController" + e.getStackTrace());
		}
		ModelAndView model = new ModelAndView("Student/student_benchmark_results", "student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("grade", grade);
		model.addObject("GradeClasses", gradeClasses);
		model.addObject("tab", WebKeys.LP_TAB_BENCHMARK_RESULTS);
		model.addObject("page", WebKeys.LP_TAB_ACCURACY_RESULTS);
		model.addObject("assignmentTypeId", 20);
		return model;
	}

	@RequestMapping(value = "/getAccuracyQuestions", method = RequestMethod.GET)
	public ModelAndView getAccuracyQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("assignmentId") long assignmentId,
			HttpServletRequest request, HttpSession session) {
		List<String> fluencyFilenames = new ArrayList<String>();
		List<String> retellFilenames = new ArrayList<String>();
		List<String> accuracyFilenames = new ArrayList<String>();
		StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
		studentAssignmentStatus = performanceTaskService
				.getStudentAssignmentStatusById(studentAssignmentId);
		ModelAndView model = null;
		Student student = studentAssignmentStatus.getStudent();
		model = new ModelAndView("Ajax/Student/display_grade_types",
				"studentAssignmentStatus", studentAssignmentStatus);
		List<GradingTypes> gradingTypes = gradeAssessmentsService
				.getGradeTypes();
		model.addObject("gradingTypes", gradingTypes);
		model.addObject("lessonId", studentAssignmentStatus.getAssignment()
				.getLesson().getLessonId());
		model.addObject("csId", studentAssignmentStatus.getAssignment()
				.getClassStatus().getCsId());

		model.addObject("fluencyFilenames", fluencyFilenames);
		model.addObject("retellFilenames", retellFilenames);
		model.addObject("accuracyFilenames", accuracyFilenames);

		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("assignmentId", assignmentId);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("studentId", student.getStudentId());
		model.addObject("userTypeId", student.getUserRegistration().getUser()
				.getUserTypeid());
		model.addObject("assignmentType", studentAssignmentStatus
				.getAssignment().getAssignmentType());
		return model;
	}

	@RequestMapping(value = "/getAccuracyTestResults", method = RequestMethod.GET)
	public ModelAndView getGradingTypeDetails(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			HttpServletRequest request) {
		List<AssignmentQuestions> questions = gradeAssessmentsService
				.getTestQuestions(studentAssignmentId);
		StudentAssignmentStatus studentAssignmentStatus = performanceTaskService
				.getStudentAssignmentStatusById(studentAssignmentId);
		ModelAndView model = null;
		String teacherComment = "";
		if(assignmentTypeId==20){
			model = new ModelAndView("Ajax/Student/include_accuracy_reading",
					"studentAssignmentStatus", studentAssignmentStatus);
		}else{
			model = new ModelAndView("Ajax/Teacher/include_grading_types",
					"studentAssignmentStatus", studentAssignmentStatus);
		}
		
		List<AssignmentQuestions> benchQuestions = gradeAssessmentsService
				.getBenchmarkQuestBygradeTyId(questions, 1);
		teacherComment = gradeAssessmentsService
				.getBenchmarkTeacherComment(studentAssignmentId);
		model.addObject("teacherComment", teacherComment);
		model.addObject("benchQuestions", benchQuestions);
		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("assignmentId", assignmentId);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("lessonId", studentAssignmentStatus.getAssignment()
				.getLesson().getLessonId());
		model.addObject("csId", studentAssignmentStatus.getAssignment()
				.getClassStatus().getCsId());
		model.addObject("studentId", studentAssignmentStatus.getStudent()
				.getStudentId());
		model.addObject("userTypeId", studentAssignmentStatus.getStudent()
				.getUserRegistration().getUser().getUserTypeid());

		return model;
	}

	@RequestMapping(value = "/studentRTITestGraded", method = RequestMethod.GET)
	public ModelAndView studentRTITestGraded(@RequestParam("csId") long csId,
			@RequestParam("studentId") long studentId,
			@RequestParam("usedFor") String usedFor, Model model) {
		UserRegistration userRegistration = (UserRegistration) session
				.getAttribute(WebKeys.LP_USER_REGISTRATION);
		String studentName = commonService.getFullName(userRegistration
				.getRegId());
		List<StudentAssignmentStatus> studentAssignmentStatusLt = new ArrayList<StudentAssignmentStatus>();
		try {
			studentAssignmentStatusLt = studentTestResultsService.getStudentAllRTITestResults(csId, usedFor, studentId);
		} catch (Exception e) {
			logger.error("Error in studentRTITestGraded() of StudentResultsController" + e.getStackTrace());
		}
		model.addAttribute("studentAssignmentStatusLt", studentAssignmentStatusLt);
		model.addAttribute("studentName", studentName);
		model.addAttribute("userTypeId", userRegistration.getUser().getUserTypeid());
		model.addAttribute("studentId",studentId);
		return new ModelAndView("Ajax/Teacher/show_student_all_rti_tests");
	}
	
	@RequestMapping(value = "/getStudentGradeClasses", method = RequestMethod.POST)
	public View getStudentGradeClasses(HttpSession session,
			@RequestParam("academicYear") long academicYearId, Model model) {
		List<RegisterForClass> gradeClasses = Collections.emptyList();
		Grade grade = new Grade();				
		try {
			session.setAttribute("selectedYearId", academicYearId);
			AcademicYear academicYear = commonService.getAcademicYearById(academicYearId);
			session.setAttribute("academicYear", academicYear);
			gradeClasses = studentService.getStudentGradeByYear(academicYear);
			if(gradeClasses.size()>0){
				grade = gradeClasses.get(0).getGradeClasses().getGrade();
			}
			model.addAttribute("grade", grade);
			model.addAttribute("gradeClasses", gradeClasses);
			
		} catch (Exception e) {
			logger.error("Error in getStudentGradeClasses() of StudentResultsController" + e.getStackTrace());
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/updateChildYear", method = RequestMethod.POST)
	public View updateChildYear(HttpSession session,
			@RequestParam("academicYear") long academicYearId, Model model) {		
		try {
			session.setAttribute("selectedYearId", academicYearId);
			AcademicYear academicYear = commonService.getAcademicYearById(academicYearId);
			session.setAttribute("academicYear", academicYear);
		} catch (Exception e) {
			logger.error("Error in updateChildYear() of StudentResultsController" + e.getStackTrace());
		}
		return new MappingJackson2JsonView();
	}
	@SuppressWarnings({ "rawtypes","unchecked" })
	@RequestMapping(value = "/getStudentAccuracyTest", method = RequestMethod.GET)
	public ModelAndView getStudentAccuracyQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("benchmarkId") String benchmarkId, @RequestParam("readingTypesId") long readingTypesId,
			@RequestParam("marks") long marks, 
			@RequestParam("regrade") String regrade,HttpServletRequest request,HttpSession session,
			@RequestParam("butt") String butt,
			@RequestParam("classId") long classId,
			@RequestParam("gradeTypesId") long gradeTypesId
			
			) {
		
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		AssignmentQuestions assignmentQuestions = new AssignmentQuestions();
		FluencyMarks fluencyMarks =new FluencyMarks();
		List<FluencyMarksDetails> errorsList=new ArrayList<FluencyMarksDetails>();
		String path="";
		int correctWords=0;
		long wordsRead=0;
		ModelAndView model = null;
		try{
		 if (benchmarkId.equals("4")) {
			  benchmarkId = String.valueOf(readingTypesId);
          } else {
        	  benchmarkId = benchmarkId + "." + readingTypesId;
          }
		 
		 fluencyMarks =gradeAssessmentsService.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
		  if(regrade.equalsIgnoreCase("yes")){
			  gradeAssessmentsDao.deleteFluencyAddedWords(fluencyMarks.getFluencyMarksId());
			  gradeAssessmentsService.deleteBenchmarkTypeMarks(studentAssignmentId, assignmentQuestionId,readingTypesId,gradeTypesId);
			 
		  }
		  assignmentQuestions = gradeAssessmentsService.getAssignmentQuestions(assignmentQuestionId);
		  long csId=assignmentQuestions.getStudentAssignmentStatus().getAssignment().getClassStatus().getCsId();
		      if(fluencyMarks.getReadingTypes().getReadingTypesId()==1){
				  if(fluencyMarks.getWordsRead()!=null){ 
					  wordsRead=fluencyMarks.getWordsRead();
					  correctWords=fluencyMarks.getWordsRead()-fluencyMarks.getCountOfErrors();
					  errorsList=gradeAssessmentsService.getErrorsList(fluencyMarks.getFluencyMarksId());
				  }
			   }  
		      if(fluencyMarks.getReadingTypes().getReadingTypesId()==1){
		    	  if(regrade.equalsIgnoreCase("no")){
		    		  model = new ModelAndView("Ajax/Teacher/benchmark_accuracy_test","assignmentQuestions", assignmentQuestions);  
		    	   }else if(regrade.equalsIgnoreCase("view")){
		    		   model = new ModelAndView("Ajax/Student/benchmark_accuracy_results_page","assignmentQuestions", assignmentQuestions);
		    	   }else{
		    		   model = new ModelAndView("Ajax/Teacher/benchmark_accuracy_test","assignmentQuestions", assignmentQuestions);
				   }
		  
		    	   model.addObject("correctWords",correctWords);
				   model.addObject("fluencyAddedWordsLt",fluencyMarks.getFluencyAddedWordsLt());
			       model.addObject("errorsList",errorsList);
			       model.addObject("fluencyMarks",fluencyMarks);
			       path  = FileUploadUtil.getUploadFilesPath(assignmentQuestions.getStudentAssignmentStatus().getStudent().getUserRegistration(), request)+File.separator+WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+assignmentQuestionId+File.separator+WebKeys.ACCURACY_FILE_NAME;
			       model.addObject("filename",path);
				   model.addObject("usersFilePath", WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+assignmentQuestionId+File.separator+WebKeys.ACCURACY_FILE_NAME);
				   model.addObject("csId",csId);
				   model.addObject("studentId", assignmentQuestions.getStudentAssignmentStatus().getStudent().getStudentId());
		      }
		}catch(Exception e){
			e.printStackTrace();
		} 
		  ArrayList<String> benchmarkqList= new ArrayList(Arrays.asList(assignmentQuestions.getQuestions().getQuestion().replaceAll("[\\r\\n]+", " ").split(" ")));
		  String questions=assignmentQuestions.getQuestions().getQuestion().replaceAll("[\\r\\n]+", " ");
		  questions=assignmentQuestions.getQuestions().getQuestion().replaceAll(",", ""); 
		  questions=questions.replaceAll("\\?", "");	
		  questions=questions.replaceAll("\\\"", "");	
		  questions=questions.replaceAll("\\\'", "");	
		  questions=questions.replaceAll("!", "");	
		  ArrayList<String> benchmarkqList1= new ArrayList(Arrays.asList(questions.split(" ")));

		model.addObject("regrade",regrade);
		model.addObject("studentRegId", assignmentQuestions.getStudentAssignmentStatus().getStudent().getUserRegistration().getRegId());
		model.addObject("benchmarkId", benchmarkId);
		model.addObject("benchmarkqList",benchmarkqList);
		model.addObject("assignQuestions",assignmentQuestions);
		model.addObject("readingTypesId",readingTypesId);
		model.addObject("butt",butt);
		model.addObject("classId",classId);
		model.addObject("assignmentTitle",assignmentQuestions.getStudentAssignmentStatus().getAssignment().getTitle());
		model.addObject("wordsRead",wordsRead);
		model.addObject("benchmarkqList1",benchmarkqList1);
		model.addObject("gradeTypesId",gradeTypesId);
		model.addObject("benchQuestions",questions);
		model.addObject("studentAssignmentId",studentAssignmentId);
		model.addObject("langId",assignmentQuestions.getStudentAssignmentStatus().getAssignment().getBenchmarkDirections().getbenchmarkDirectionsId());
		return model;
	}
	@RequestMapping(value = "/gradeSelfandPeerAccuracyTest", method = RequestMethod.POST)
	public View gradeAccuracyTest(HttpSession session,
			HttpServletResponse response,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,@RequestParam("wordsRead") long wordsRead,
			@RequestParam("errors") long errors,@RequestParam("correctWords") long correctWords,
			@RequestParam("errorIdsStr") String errorIdsStr,
			@RequestParam("readingTypesId") long readingTypesId, Model model,@RequestParam("assignmentTitle") String assignmentTitle,@RequestParam("hwAssignmentId") long hwAssignmentId,
			@RequestParam("errorComments") String[] errorComments,@RequestParam("errorsStr") String errorsStr,@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("addedWordStr") String addedWordStr,@RequestParam("comment") String comment,@RequestParam("dueDate") String dueDate,@RequestParam("studentId") long studentId,@RequestParam("wcpm") int wcpm) {
		try {	
			
			String[] addedWords = null;
			String[] errorWords = null;
			float percentageAcquired = 0.2f;
			String helloAjax = "";
			long s = correctWords + errors;
		    percentageAcquired = (float) correctWords * 100 / s;
		    DecimalFormat dec = new DecimalFormat("##.##");
			String[] error = errorsStr.split(":");
			String[] wordNums=null;
			//String comment="";
			ArrayList<String> errorWordsList = new ArrayList<String>();
			if(errors!=0){
			/*for(int i =0; i<error.length;i++){
				errorWordsList.add(error[i]);
			}*/
			if(errorsStr.contains(":"))
			   errorWords = errorsStr.split(":");
					
			wordNums = errorIdsStr.split(":");
			if(addedWordStr.contains(":"))
				addedWords = addedWordStr.split(":");
			}
			boolean check=gradeAssessmentsService.gradeAccuracyTest(assignmentQuestionId,wordsRead,errors,correctWords,wordNums,readingTypesId,errorComments,errorWords,gradeTypesId,dec.format(percentageAcquired),addedWords,comment,wcpm);
			model.addAttribute("percentageAcquired",dec.format(percentageAcquired));
			if(gradeTypesId==3){
			if (check){
				helloAjax =WebKeys.TEST_GRADED_SUCCESS;
		    }else{
		    model.addAttribute("helloAjax",helloAjax);}
			}
			if(gradeTypesId==2){
			if(dueDate!=""){
			    if (check){
			    	AssignmentQuestions assignmentQuestion= gradeAssessmentsService.getAssignmentQuestions(assignmentQuestionId);
			    	String homeTitle=assignmentQuestion.getStudentAssignmentStatus().getAssignment().getTitle();
					helloAjax =WebKeys.TEST_GRADED_SUCCESS;
					Assignment assignment = new Assignment();
					AssignmentType assignmentType = new AssignmentType();
					StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
					ClassStatus classStatus = new ClassStatus();
					long csId=assignmentQuestion.getStudentAssignmentStatus().getAssignment().getClassStatus().getCsId();
					classStatus.setCsId(csId);
					assignment.setClassStatus(classStatus);
					assignmentType.setAssignmentTypeId(18);
					assignmentType.setUsedFor(WebKeys.LP_USED_FOR_HOMEWORKS);
					assignment.setAssignmentType(assignmentType);
					assignment.setAssignStatus(WebKeys.ACTIVE);
					assignment.setDateAssigned(new Date());
					DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
					assignment.setDateDue(formatter.parse(dueDate));
					if(assignmentQuestion.getStudentAssignmentStatus().getAssignment().getBenchmarkDirections() != null){
						Language lan = assignmentQuestion.getStudentAssignmentStatus().getAssignment().getBenchmarkDirections().getLanguage();
						assignment.setLanguage(lan);
					}
					GradingTypes gt=new GradingTypes();
					gt=gradeAssessmentsService.getGradingType(gradeTypesId);
					long peerReviewBy=0;
					List<AssignmentQuestions> assignmentQuestionst = null;
					long questionId = 0;
					if(assignmentQuestion.getStudentAssignmentStatus() != null){
						assignmentQuestionst = assignAssessmentService.getAssignmentQuestionsByStudentAssignmentId(assignmentQuestion.getStudentAssignmentStatus().getStudentAssignmentId());
						int inc = 0;
						for (AssignmentQuestions ass: assignmentQuestionst) {
							inc=inc+1;
							if(ass.getQuestions().getQuestionId() == assignmentQuestion.getQuestions().getQuestionId())								
							   questionId = inc;
						}
					}
					
		        	if(gradeTypesId==2){
		        		assignment.setCreatedBy(studentId);
			        	if(assignmentQuestionst.size() > 1 && questionId > 0)
			          		assignmentTitle=homeTitle+""+questionId+"_"+gt.getGradingTypes();
			          	else
			          		assignmentTitle=homeTitle+"_"+gt.getGradingTypes();
			        }else if(gradeTypesId==3){
			        	
			        	Student assStudent = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			        	if(assignmentQuestionst.size() > 1 && questionId > 0)
			          		assignmentTitle=homeTitle+""+questionId+"_"+gt.getGradingTypes();
			          	else
			          		assignmentTitle=homeTitle+"_"+gt.getGradingTypes();
				        assignment.setCreatedBy(assStudent.getStudentId());
				        peerReviewBy=assStudent.getStudentId();
				        //studentId=assignmentQuestion.getStudentAssignmentStatus().getStudent().getStudentId();
				    }
		        	assignment.setTitle(assignmentTitle);
					assignment.setUsedFor(WebKeys.LP_USED_FOR_HOMEWORKS);
					
					if(hwAssignmentId > 0)
						assignment.setAssignmentId(hwAssignmentId);
					else
						assignment.setAssignmentId(0);
					studentAssignmentStatus.setAssignment(assignment);
				    Student student = gradeBookService.getStudentById(studentId); 
					studentAssignmentStatus.setStudent(student);
					studentAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
					studentAssignmentStatus.setStatus(WebKeys.TEST_STATUS_PENDING);
					StudentAssignmentStatus stdAssignmentStatus = assignAssessmentService.assignReadingFluencyLearningPracticeHomeWork(assignment, studentAssignmentStatus);
					List<String> contentList = new ArrayList<String>();
					contentList.add(errorsStr);
					if(stdAssignmentStatus.getStatus().equals(WebKeys.TEST_STATUS_SUBMITTED) && stdAssignmentStatus.getGradedStatus().equals(WebKeys.GRADED_STATUS_GRADED)){
						model.addAttribute("assignmentId",stdAssignmentStatus.getAssignment().getAssignmentId());
					}else{
						assignAssessmentService.assignRFLPTest(contentList,dueDate,studentAssignmentStatus,gradeTypesId,peerReviewBy);//assignAssessmentsService.assignRFLPTest(rflpTest);
						model.addAttribute("assignmentId",stdAssignmentStatus.getAssignment().getAssignmentId());
					}
					helloAjax = WebKeys.TEST_GRADED_SUCCESS;
			   
				}else{
					helloAjax = WebKeys.TEST_GRADED_FAILURE;
			    }
			}
			}
		    model.addAttribute("helloAjax",helloAjax);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/saveWCPM", method = RequestMethod.GET)
	public View saveWCPM(HttpSession session,
			HttpServletResponse response,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,@RequestParam("wcpm") int wcpm,
			@RequestParam("gradeTypesId") long gradingTypeId, @RequestParam("readingTypesId") int readingTypeId,
			Model model) {
		try {	
			
			boolean check=gradeAssessmentsService.saveWCPM(assignmentQuestionId, wcpm, gradingTypeId, readingTypeId);
			model.addAttribute("helloAjax", check);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	
}
