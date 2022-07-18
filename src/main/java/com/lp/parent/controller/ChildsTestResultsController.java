package com.lp.parent.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;
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

import com.lp.custom.exception.DataException;
import com.lp.model.Grade;
import com.lp.model.ParentLastseen;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.service.StudentService;
import com.lp.teacher.service.GradeBookService;
import com.lp.utils.WebKeys;

@Controller
public class ChildsTestResultsController {
	@Autowired
	private HttpSession session;
	@Autowired
	private StudentService studentService;
	@Autowired
	private GradeBookService gradeBookService;
	@Autowired
	private ParentService parentService;
	@Autowired
	ServletContext context;
	static final Logger logger = Logger.getLogger(ChildsTestResultsController.class);

	@RequestMapping(value = "/childsAssessmentCompleted", method = RequestMethod.GET)
	public ModelAndView displayParentClassFiles(HttpSession session,
			HttpServletRequest request) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<UserRegistration> childrenRegistrations = new ArrayList<UserRegistration>();
		List<Student> students = new ArrayList<Student>();
		childrenRegistrations = parentService.getchildren(userReg.getRegId());
		for (UserRegistration ur : childrenRegistrations) {
			Student student = studentService.getStudent(ur.getRegId());
			students.add(student);
		}
		ModelAndView model = new ModelAndView(
				"Parent/child_assessments_completed");
		model.addObject("students", students);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("tab", WebKeys.LP_TAB_VIEW_ASSESSMENT_COMPLETED);
		return model;
	}

	@RequestMapping(value = "/childTestGraded", method = RequestMethod.GET)
	public ModelAndView studentTestGraded(
			@RequestParam("classId") long classId,
			@RequestParam("usedFor") String usedFor, Model model,
			HttpSession session, @RequestParam("studentId") long studentId) {
		List<StudentAssignmentStatus> studentAssignmentStatusLt = new ArrayList<StudentAssignmentStatus>();
		try {
			studentAssignmentStatusLt = parentService.getStudentsTestResults(
					studentId, classId, usedFor);
		} catch (DataException e) {
			logger.error("Error in studentTestGraded() of StudentResultsController"
					+ e);
		}
		model.addAttribute("studentAssignmentStatusLt",
				studentAssignmentStatusLt);
		return new ModelAndView("Ajax/Student/student_tests_graded");
	}

	@RequestMapping(value = "/childBenchmarkResults", method = RequestMethod.GET)
	public ModelAndView studentBenchmarkResults() {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<UserRegistration> childrenRegistrations = new ArrayList<UserRegistration>();
		List<Student> students = new ArrayList<Student>();
		childrenRegistrations = parentService.getchildren(userReg.getRegId());
		for (UserRegistration ur : childrenRegistrations) {
			Student student = studentService.getStudent(ur.getRegId());
			students.add(student);
		}
		ModelAndView model = new ModelAndView("Parent/child_benchmark_results",
				"student_test_results", new StudentAssignmentStatus());
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		model.addObject("students", students);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_BENCHMARK_RESULTS);
		model.addObject("page",WebKeys.LP_TAB_BENCHMARK_RESULTS);
		model.addObject("assignmentTypeId", 8);
		return model;

	}

	@RequestMapping(value = "/getChildBenchmarkTests", method = RequestMethod.GET)
	public ModelAndView getBenchmarkCompletedTests(
			@RequestParam("classId") long classId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("studentId") long studentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId, Model model) {
		List<StudentAssignmentStatus> studentAssignmentStatusLt = new ArrayList<StudentAssignmentStatus>();
		try {
			Student student = new Student();
			student.setStudentId(studentId);
			studentAssignmentStatusLt = parentService
					.getStudentsBenchmarkTestResults(classId, usedFor, student,
							assignmentTypeId);
		} catch (DataException e) {
			logger.error("Error in studentTestGraded() of StudentResultsController"
					+ e);
		}
		model.addAttribute("studentAssignmentStatusLt",
				studentAssignmentStatusLt);
		return new ModelAndView("Ajax/Student/completed_fluency_results");
	}

	@RequestMapping(value = "/childHomeworkReports", method = RequestMethod.GET)
	public ModelAndView childHomeworkReports() {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<UserRegistration> childrenRegistrations = new ArrayList<UserRegistration>();
		List<Student> students = new ArrayList<Student>();
		childrenRegistrations = parentService.getchildren(userReg.getRegId());
		for (UserRegistration ur : childrenRegistrations) {
			Student student = studentService.getStudent(ur.getRegId());
			students.add(student);
		}
		ModelAndView model = new ModelAndView("Parent/child_homework_reports", "student_test_results", new StudentAssignmentStatus());
		model.addObject("students", students);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_HOMEWORKS);
		model.addObject("tab", WebKeys.LP_TAB_HOMEWORK_REPORTS);
		return model;

	}

	@RequestMapping(value = "/getStudentGradeAndHomeworkDates", method = RequestMethod.GET)
	public View getStudentGradeAndHomeworkDates(
			@RequestParam("studentId") long studentId, Model model,
			HttpSession session, @RequestParam("usedFor") String usedFor) {

		try {
			Student student = gradeBookService.getStudentById(studentId);
			Grade grade = new Grade();
			grade = student.getGrade();
			List<StudentAssignmentStatus> homeworkDates = parentService
					.getChildHomeworkReportDates(student, usedFor);
			model.addAttribute("grade", grade);
			model.addAttribute("homeworkDates", homeworkDates);
		} catch (DataException e) {
			logger.error("Error in getStudentGradeAndHomeworkDates() of ChildsTestResultsController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/getChildHomeworkReportsByDate", method = RequestMethod.GET)
	public ModelAndView getHomeworkReports(
			@RequestParam("studentId") long studentId,
			@RequestParam("assignedDate") String assignedDate,
			HttpSession session, @RequestParam("usedFor") String usedFor,
			HttpServletResponse response, HttpServletRequest request) {
		List<StudentAssignmentStatus> homeworkReports = new ArrayList<StudentAssignmentStatus>();
		try {
			Student student = gradeBookService.getStudentById(studentId);
			homeworkReports = parentService.getChildHomeworkReports(student,
					usedFor, assignedDate);
		} catch (DataException e) {
			logger.error("Error in getHomeworkReports() of StudentResultsController"
					+ e);
		}
		ModelAndView model = new ModelAndView(
				"Ajax/Parent/show_child_homework_reports", "assignment",
				new StudentAssignmentStatus());
		model.addObject("homeworkReports", homeworkReports);

		return model;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/childRtiTestResults", method = RequestMethod.GET)
	public ModelAndView getChildRtiTestResults() {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		if (userReg.getUser().getUserType()
				.equalsIgnoreCase(WebKeys.USER_TYPE_PARENT)) {
			ParentLastseen parentLastseen = (ParentLastseen) session
					.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ);
			HashSet<String> hs = (HashSet<String>) session
					.getAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB);
			if (!hs.contains(WebKeys.LP_TAB_RTI_TEST_RESULTS)) {
				String lastSeenTabs = parentLastseen.getLastSeenFeature();
				if (lastSeenTabs.equalsIgnoreCase("---")) {
					lastSeenTabs = "";
				}
				lastSeenTabs = lastSeenTabs + WebKeys.LP_TAB_RTI_TEST_RESULTS
						+ ";";
				parentLastseen.setLastSeenFeature(lastSeenTabs);
				hs.add(WebKeys.LP_TAB_RTI_TEST_RESULTS);
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_OBJ,
						parentLastseen);
				session.setAttribute(WebKeys.LP_PARENT_LAST_SEEN_TAB, hs);
			}
		}
		List<UserRegistration> childrenRegistrations = new ArrayList<UserRegistration>();
		List<Student> students = new ArrayList<Student>();
		childrenRegistrations = parentService.getchildren(userReg.getRegId());
		for (UserRegistration ur : childrenRegistrations) {
			Student student = studentService.getStudent(ur.getRegId());
			students.add(student);
		}
		ModelAndView model = new ModelAndView("Parent/child_rti_test_results",
				"student_test_results", new StudentAssignmentStatus());
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		model.addObject("students", students);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_RTI_TEST_RESULTS);
		return model;

	}

	@RequestMapping(value = "/childRtiResults", method = RequestMethod.GET)
	public ModelAndView getChildRtiResults() {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<UserRegistration> childrenRegistrations = new ArrayList<UserRegistration>();
		List<Student> students = new ArrayList<Student>();
		childrenRegistrations = parentService.getchildren(userReg.getRegId());
		for (UserRegistration ur : childrenRegistrations) {
			Student student = studentService.getStudent(ur.getRegId());
			students.add(student);
		}
		ModelAndView model = new ModelAndView("Parent/child_rti_results",
				"student_test_results", new StudentAssignmentStatus());
		model.addObject("students", students);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_RTI_RESULTS);
		return model;

	}

	@RequestMapping(value = "/getChildRtiResults", method = RequestMethod.GET)
	public ModelAndView getChildRtiResults(
			@RequestParam("studentId") long studentId, HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("classId") long classId,
			HttpServletResponse response, HttpServletRequest request) {
		List<StudentAssignmentStatus> rtiResults = new ArrayList<StudentAssignmentStatus>();
		List<Long> correctResponses = Collections.emptyList();
		List<Long> wrongResponses = Collections.emptyList();
		try {
			rtiResults = parentService.getChildTestResults(studentId, classId, usedFor);
			correctResponses = parentService.getChildRtiTestCorrectResponses(rtiResults);
			wrongResponses = parentService.getChildRtiTestWrongResponses(rtiResults);

		} catch (DataException e) {
			logger.error("Error in getChildRtiResults() of StudentResultsController"
					+ e);
		}
		ModelAndView model = new ModelAndView(
				"Ajax/Parent/show_child_rti_results", "assignment",
				new StudentAssignmentStatus());
		model.addObject("rtiResults", rtiResults);
		model.addObject("correctResponses", correctResponses);
		model.addObject("wrongResponses", wrongResponses);

		return model;

	}

	@RequestMapping(value = "/childAccuracyResults", method = RequestMethod.GET)
	public ModelAndView studentAccuracyResults() {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<UserRegistration> childrenRegistrations = new ArrayList<UserRegistration>();
		List<Student> students = new ArrayList<Student>();
		childrenRegistrations = parentService.getchildren(userReg.getRegId());
		for (UserRegistration ur : childrenRegistrations) {
			Student student = studentService.getStudent(ur.getRegId());
			students.add(student);
		}
		ModelAndView model = new ModelAndView("Parent/child_benchmark_results", "student_test_results", new StudentAssignmentStatus());
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		model.addObject("students", students);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_BENCHMARK_RESULTS);
		model.addObject("page", WebKeys.LP_TAB_ACCURACY_RESULTS);
		model.addObject("assignmentTypeId", 20);
		return model;

	}

}
