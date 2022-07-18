package com.lp.common.controller;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.AssessmentService;
import com.lp.common.service.BenchmarkCutOffMarksService;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkCutOffMarks;
import com.lp.model.BenchmarkResults;
import com.lp.model.Grade;
import com.lp.model.QuestionsList;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.service.StudentService;
import com.lp.teacher.service.GradeBookService;
import com.lp.teacher.service.IOLReportCardService;
import com.lp.utils.WebKeys;

/**
 * 
 * @author PRASAD BHVN & SANTHOSH
 * 
 */
@Controller
public class RTIController extends WebApplicationObjectSupport {

	@Autowired
	private AdminService adminservice;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private CommonService commonservice;
	@Autowired
	private GradeBookService gradeBookService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ParentService parentService;
	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private BenchmarkCutOffMarksService benchmarkCutOffMarksService;
	@Autowired
	IOLReportCardService iolReportCardService;

	static final Logger logger = Logger.getLogger(RTIController.class);

	// RTI
	@RequestMapping(value = "/rti", method = RequestMethod.GET)
	public ModelAndView rtiView(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		// redirecting to assessment_main view
		ModelAndView model = new ModelAndView("assessments/assessment_main",
				"questionsList", new QuestionsList());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_PREPARE_RTI);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try {
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session
						.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
						.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
		} catch (DataException e) {
			logger.error("Error in rtiView() of RTIController" + e);
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

	@RequestMapping(value = "/viewProgressMonitor", method = RequestMethod.GET)
	public ModelAndView viewProgressMonitor(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("CommonJsp/progress_monitor");
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_PROGRESS_MONITORING);
		model.addObject("userType", userReg.getUser().getUserType());
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try {
			if (userReg.getUser().getUserType()
					.equalsIgnoreCase(WebKeys.USER_TYPE_ADMIN)) {
				// admin
				List<Grade> schoolgrades = adminservice.getSchoolGradesByAcademicYr(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			} else if (userReg.getUser().getUserType()
					.equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER)) {
				// teacher
				teacherGrades = curriculumService.getTeacherGradesByAcademicYr(teacherObj);
				model.addObject("grList", teacherGrades);
			} else if (userReg.getUser().getUserType()
					.equalsIgnoreCase(WebKeys.LP_USER_TYPE_PARENT)) {
				// Parent
				List<UserRegistration> childrenRegistrations = new ArrayList<UserRegistration>();
				List<Student> students = new ArrayList<Student>();
				childrenRegistrations = parentService.getchildren(userReg.getRegId());
				for (UserRegistration ur : childrenRegistrations) {
					Student student = studentService.getStudent(ur.getRegId());
					students.add(student);
				}
				model.addObject("students", students);
			} else {
				// Student
				Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
				Grade grade = new Grade();
				List<RegisterForClass> gradeClasses = Collections.emptyList();
				AcademicYear academicYear = null;
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
				model.addObject("grade", grade);
				model.addObject("GradeClasses", gradeClasses);
			}
		} catch (DataException e) {
			logger.error("Error in viewProgressMonitor() of RTIController" + e);
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}

		return model;
	}

	@RequestMapping(value = "/getStudentsProgres", method = RequestMethod.GET)
	public ModelAndView getStudentsProgres(@RequestParam("csId") long csId,
			@RequestParam("studentId") long studentId, HttpSession session) {
		List<RegisterForClass> studentList = new ArrayList<RegisterForClass>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/progress_monitor_sub");
		String userType = userReg.getUser().getUserType();
		model.addObject("csId", csId);
		model.addObject("userType", userType);
		Student student=new Student();
		try {
			if (userType.equalsIgnoreCase(WebKeys.USER_TYPE_ADMIN) || userType.equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER)) {
				// Admin && Teacher
				studentList = commonservice.getStudentsByCsId(csId);
				model.addObject("studentList", studentList);
			} else if (userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_PARENT)) {
				// Parent
				student = gradeBookService.getStudentById(studentId);
				model.addObject("student", student);
				String studentName=student.getUserRegistration().getFirstName()+" "+student.getUserRegistration().getLastName();
				model.addObject("studentName",studentName);
			} else {
				// Student
				student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
				model.addObject("student", student);
				String studentName=student.getUserRegistration().getFirstName()+" "+student.getUserRegistration().getLastName();
				model.addObject("studentName",studentName);
			}
			

		} catch (DataException e) {
			logger.error("Error in getStudentsProgres() of RTIController" + e);
		}

		return model;
	}

	@RequestMapping(value = "/getBanchResults", method = RequestMethod.GET)
	public View getBanchResults(@RequestParam("studentId") long studentId,
			@RequestParam("csId") long csId,Model model, HttpSession session) {
		List<BenchmarkResults> benchResults = new ArrayList<BenchmarkResults>();
		List<AssignmentQuestions> benchAssignments = new ArrayList<AssignmentQuestions>();
		List<BenchmarkCutOffMarks> benchCutOffMarks = new ArrayList<BenchmarkCutOffMarks>();
		try {
			benchResults = gradeBookService.getBanchResults(studentId,csId);
			benchAssignments = gradeBookService.getBenchAssignments(studentId,csId);
			long gradeId=iolReportCardService.getStudent(studentId).getGrade().getGradeId();
			benchCutOffMarks = benchmarkCutOffMarksService.getBenchmarkCutOffMarks(gradeId);
		} catch (DataException e) {
			logger.error("Error in getBanchResults() of GradeBookController"
					+ e);
		}
		model.addAttribute("size", benchResults.size());
		model.addAttribute("benchResults", benchResults);
		model.addAttribute("benchAssignments", benchAssignments);
		model.addAttribute("benchCutOffMarks", benchCutOffMarks);
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/loadGradeClasess", method = RequestMethod.GET)
	public View loadGradeClasess(@RequestParam("studentId") long studentId,
			Model model, HttpSession session) {

		try {
			Student student = gradeBookService.getStudentById(studentId);
			Grade grade = new Grade();
			List<RegisterForClass> gradeClasses = Collections.emptyList();
			HashMap<Long, String> classMap = new HashMap<Long, String>();
			
			AcademicYear academicYear = null;
			if(session.getAttribute("academicYear") != null){
				academicYear = (AcademicYear) session.getAttribute("academicYear");
				gradeClasses = studentService.getChildGradeByYear(academicYear,student);
				if(gradeClasses.size()>0){
					grade = gradeClasses.get(0).getGradeClasses().getGrade();
				}
			}else{		
				grade = student.getGrade();
				gradeClasses = studentService.getStudentClasses(student);				
			}
			for (RegisterForClass rfc : gradeClasses) {
				classMap.put(rfc.getClassStatus().getCsId(), rfc
						.getGradeClasses().getStudentClass().getClassName());
			}
			
			model.addAttribute("grade", grade);
			model.addAttribute("classMap", classMap);
		} catch (DataException e) {
			logger.error("Error in loadGradeClasess() of GradeBookController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/loadClasess", method = RequestMethod.GET)
	public View loadClasess(@RequestParam("studentId") long studentId,
			Model model, HttpSession session) {
		try {
			Student student = gradeBookService.getStudentById(studentId);
			Grade grade = new Grade();
			List<RegisterForClass> gradeClasses = Collections.emptyList();
			HashMap<Long, String> classMap = new HashMap<Long, String>();
			grade = student.getGrade();
			gradeClasses = studentService.getStudentClasses(student);
			for (RegisterForClass rfc : gradeClasses) {
				classMap.put(rfc.getGradeClasses().getStudentClass().getClassId(), rfc.getGradeClasses().getStudentClass().getClassName());
			}
			model.addAttribute("grade", grade);
			model.addAttribute("gradeName", grade.getMasterGrades()
					.getGradeName());
			model.addAttribute("classMap", classMap);
		} catch (DataException e) {
			logger.error("Error in loadClasess() of GradeBookController" + e);
		}
		return new MappingJackson2JsonView();
	}

	// Edit RTI
	@RequestMapping(value = "/editRti", method = RequestMethod.GET)
	public ModelAndView editRti(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("assessments/edit_assessment_main", "questionsList", new QuestionsList());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("tab", WebKeys.LP_TAB_EDIT);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try {
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session
						.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
						.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			} else {
				// teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
		} catch (DataException e) {
			logger.error("Error in editRti() of AssessmentController" + e);
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}
		if (session.getAttribute("helloAjax") != null) {
			model.addObject("helloAjax", session.getAttribute("helloAjax")
					.toString());
			session.removeAttribute("helloAjax");
		}
		if (session.getAttribute("isError") != null) {
			model.addObject("isError", session.getAttribute("isError")
					.toString());
			session.removeAttribute("isError");
		}
		return model;
	}

	@RequestMapping(value = "/getTestDatesforRTIResults", method = RequestMethod.GET)
	public View getTestDatesforRTIResults(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId, Model model) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {
			model.addAttribute(
					"teacherAssignedDates",
					assessmentService.getTestDatesforRTIResults(
							userReg.getRegId(), csId, usedFor));
		} catch (SQLDataException | QuerySyntaxException e1) {
			logger.error("error while getting test dates for rti test results");
			model.addAttribute("errorMessage",
					WebKeys.TEACHER_TEST_DATES_ERROR_MESSAGE);
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/getRTIResults", method = RequestMethod.GET)
	public ModelAndView getRTIResults(HttpSession session,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("type") String type) {
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/rti_results_div");
		try {
			if (type.equals("student")) {
				model.addObject("testResultsByStudent",
						assessmentService.getRTIResultsByStudent(assignmentId));
			} else {
				model.addObject("testResultsByQuestion",
						assessmentService.getRTIResultsByQuestion(assignmentId));
			}
			model.addObject("type", type);
		} catch (Exception e) {
			logger.error("error while getting rti results set");
			model.addObject("errorMessage",
					WebKeys.TEACHER_GRADES_ERROR_MESSAGE);
		}
		return model;
	}

	@RequestMapping(value = "/studentSelfGrading", method = RequestMethod.GET)
	public ModelAndView studentSelfGrading(HttpSession session) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		Grade grade = new Grade();
		List<RegisterForClass> gradeClasses = Collections.emptyList();
		try {

			grade = student.getGrade();
			gradeClasses = studentService.getStudentClasses(student);
		} catch (DataException e) {
			logger.error("Error in studentSelfGrading() of RTIController" + e);
		}

		ModelAndView model = new ModelAndView("Student/student_self_grading",
				"student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("grade", grade);
		model.addObject("GradeClasses", gradeClasses);
		model.addObject("tab", WebKeys.LP_TAB_BENCHMARK_SELF_GRADING);
		model.addObject("gradingTypesId", 2);
		model.addObject("assignmentTypeId",8);
		return model;
	}

	@RequestMapping(value = "/studentPeerReview", method = RequestMethod.GET)
	public ModelAndView studentPeerReview(HttpSession session) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		Grade grade = new Grade();
		List<RegisterForClass> gradeClasses = Collections.emptyList();
		try {

			grade = student.getGrade();
			gradeClasses = studentService.getStudentClasses(student);
		} catch (DataException e) {
			logger.error("Error in studentSelfGrading() of RTIController" + e);
		}

		ModelAndView model = new ModelAndView("Student/student_self_grading",
				"student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("grade", grade);
		model.addObject("GradeClasses", gradeClasses);
		model.addObject("tab", WebKeys.LP_TAB_BENCHMARK_PEER_REVIEW);
		model.addObject("gradingTypesId", 3);
		model.addObject("assignmentTypeId",8);
		return model;
	}

	@RequestMapping(value = "/RTIResultsPage", method = RequestMethod.GET)
	public ModelAndView goToGradeRti(HttpSession session) {
		ModelAndView model = new ModelAndView("CommonJsp/student_rti_results");
		model.addObject("tab", WebKeys.LP_TAB_RTI_RESULTS);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try {
			UserRegistration userRegistration = (UserRegistration) session
					.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			if (teacherObj != null) {
				model.addObject("grades",
						curriculumService.getTeacherGradesByAcademicYr(teacherObj));
			} else {
				model.addObject("grades", adminservice
						.getSchoolGradesByAcademicYr(userRegistration.getSchool()
								.getSchoolId()));
			}

		} catch (Exception e) {
			logger.error("error while getting rti results page");
			model.addObject("errorMessage",
					WebKeys.TEACHER_GRADES_ERROR_MESSAGE);
		}
		return model;
	}
	
	@RequestMapping(value="/changeAcademicYear", method = RequestMethod.GET)
	public View changeAcademicYear(HttpSession session, @RequestParam("selectedYearId") long selectedYearId, HttpServletRequest request) {
		session.setAttribute("selectedYearId", selectedYearId);
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/accuracySelfGrading", method = RequestMethod.GET)
	public ModelAndView accuracySelfGrading(HttpSession session) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		Grade grade = new Grade();
		List<RegisterForClass> gradeClasses = Collections.emptyList();
		try {

			grade = student.getGrade();
			gradeClasses = studentService.getStudentClasses(student);
		} catch (DataException e) {
			logger.error("Error in accuracySelfGrading() of RTIController" + e);
		}

		ModelAndView model = new ModelAndView("Student/accuracy_self_grading",
				"student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("grade", grade);
		model.addObject("GradeClasses", gradeClasses);
		model.addObject("tab", WebKeys.LP_TAB_ACCURACY_SELF_GRADING);
		model.addObject("page", WebKeys.LP_TAB_ACCURACY_SELF_GRADING);
		model.addObject("gradingTypesId", 2);
		model.addObject("assignmentTypeId",20);
		return model;
	}
	@RequestMapping(value = "/accuracyPeerGrading", method = RequestMethod.GET)
	public ModelAndView accuracyPeerGrading(HttpSession session) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		Grade grade = new Grade();
		List<RegisterForClass> gradeClasses = Collections.emptyList();
		try {

			grade = student.getGrade();
			gradeClasses = studentService.getStudentClasses(student);
		} catch (DataException e) {
			logger.error("Error in accuracyPeerGrading() of RTIController" + e);
		}

		ModelAndView model = new ModelAndView("Student/accuracy_self_grading",
				"student_test_results", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		model.addObject("grade", grade);
		model.addObject("GradeClasses", gradeClasses);
		model.addObject("tab", WebKeys.LP_TAB_ACCURACY_SELF_GRADING);
		model.addObject("page", WebKeys.LP_TAB_ACCURACY_PEER_REVIEW);
		model.addObject("gradingTypesId", 3);
		model.addObject("assignmentTypeId",20);
		return model;
	}
	
}
