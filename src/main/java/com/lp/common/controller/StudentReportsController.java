package com.lp.common.controller;

import java.io.File;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.admin.service.AdminService;
import com.lp.common.service.AdminStudentReportsService;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignmentQuestions;
import com.lp.model.Grade;
import com.lp.model.JacTemplate;
import com.lp.model.RegisterForClass;
import com.lp.model.Report;
import com.lp.model.RflpPractice;
import com.lp.model.RflpRubric;
import com.lp.model.RflpTest;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.student.service.TakeAssessmentsService;
import com.lp.teacher.service.GradeAssessmentsService;
import com.lp.teacher.service.HomeworkManagerService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class StudentReportsController {

	@Autowired
	private HomeworkManagerService homeworkManagerService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminStudentReportsService adminStudentReportsService;
	@Autowired
	private GradeAssessmentsService gradeAssessmentsService;
	@Autowired
	private TakeAssessmentsService takeAssessmentsService;
	@Autowired
	private AdminSchedulerDAO adminSchedulerdao;

	static final Logger logger = Logger.getLogger(StudentReportsController.class);
	
	@RequestMapping(value = "/adminStudentReports", method = RequestMethod.GET)
	public ModelAndView goStudentsReports(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
		ModelAndView model = new ModelAndView("Admin/student_reports", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("grList", schoolgrades);
		return  model;
	}


	@RequestMapping(value = "/getStudentReportsList", method = RequestMethod.GET)
	public ModelAndView getReportStudentList( HttpSession session,@RequestParam("gradeId") long gradeId,@RequestParam("classId") long classId,
			@RequestParam("csId") long csId,@RequestParam("fromId") Date fromId, @RequestParam("toId") Date toId, Model model) throws DataException {
		List<RegisterForClass> studentList = Collections.emptyList();
		HashMap<Long,Double> assessmentAverages=new HashMap<Long,Double>();
		HashMap<Long,Double> homeworkAverages=new HashMap<Long,Double>();	
		try{
			String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(fromId);
			String toDate = new SimpleDateFormat("yyyy-MM-dd").format(toId);
			studentList = adminStudentReportsService.getStudentList(csId);
			homeworkAverages = adminStudentReportsService.getStudentAssignmentsByCsId(csId,WebKeys.LP_USED_FOR_HOMEWORKS,fromDate,toDate);
			assessmentAverages=adminStudentReportsService.getStudentAssignmentsByCsId(csId,WebKeys.LP_USED_FOR_ASSESSMENT,fromDate,toDate);
			if(csId > 0){
				long teacherId = adminSchedulerdao.getclassStatus(csId).getTeacher().getTeacherId();
				Teacher teacher = adminSchedulerdao.getTeacher(teacherId);
				String teacherTitle = teacher.getUserRegistration().getTitle();
				String teacherFirstName = teacher.getUserRegistration().getFirstName();
				String teacherLastName = teacher.getUserRegistration().getLastName();
				String teacherFullName = teacherTitle+". "+teacherFirstName +" "+teacherLastName;
				model.addAttribute("sectionTeacher", teacherFullName);
			}	
			
		}catch(DataException e){
			logger.error("Error in getReportStudentList() of StudentReportsController"
					+ e);
			e.printStackTrace();
		}
		model.addAttribute("studentList", studentList);
		model.addAttribute("homeworkAverages", homeworkAverages);
		model.addAttribute("assessmentAverages", assessmentAverages);
		return new ModelAndView("Ajax/Admin/student_reports_list", "report", new Report());	
	}
	@RequestMapping(value = "/adminStudentHomeworkReports", method = RequestMethod.GET)
	public ModelAndView goStudentHomeworkReports(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());
		ModelAndView model = new ModelAndView("Admin/student_homework_reports", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("grList", schoolgrades);
		model.addObject("usedFor",WebKeys.LP_USED_FOR_HOMEWORKS);
			
		return  model;
	}
	@RequestMapping(value = "/getSectionTeachers", method = RequestMethod.GET)
	public View getSectionTeachers(HttpSession session,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("classId") long classId,
			@RequestParam("sectionId") long sectionId, Model model) {
		try{
		model.addAttribute("sectionTeachers",adminStudentReportsService.getSectionTeachers(sectionId));
		}catch(DataException e){
			logger.error("Error in getSectionTeachers() of StudentReportsController"
					+ e);
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getHomeworkTitles", method = RequestMethod.GET)
	public View getHomeworkTitles(HttpSession session,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("csId") long csId,@RequestParam("fromId") Date fromId,
			Model model) {
		try{
		 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  String sqlDate = sdf.format(fromId);
		 model.addAttribute("homeworkTitles",homeworkManagerService.getHomeworkTitles(csId, WebKeys.LP_USED_FOR_HOMEWORKS,sqlDate));
		}catch(DataException e){
			logger.error("Error in getSectionTeachers() of StudentReportsController"
					+ e);
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getStudentHomeworkReportsList", method = RequestMethod.GET)
	public ModelAndView getHomeworkReports(HttpSession session,@RequestParam("assignmentId") long assignmentId,
			HttpServletResponse response, HttpServletRequest request) {
		List<StudentAssignmentStatus> assessmentCompletedList=null;
	   try{
		 assessmentCompletedList = gradeAssessmentsService.getStudentAssessmentTests(assignmentId);
	   }catch(DataException e){
			logger.error("Error in getSectionTeachers() of StudentReportsController"
					+ e);
			e.printStackTrace();
		}
		ModelAndView model = new ModelAndView("Ajax/Admin/show_homework_tests", "assignment",
				new StudentAssignmentStatus());
		model.addObject("studentAssessmentTestList", assessmentCompletedList);
		return model;
	
}
	@RequestMapping(value = "/getStudentHomeworkQuestions", method = RequestMethod.GET)
	public ModelAndView getTestQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("assignmentId") long assignmentId,
			HttpServletRequest request,
			HttpSession session) {
		List<AssignmentQuestions> questions = gradeAssessmentsService
				.getTestQuestions(studentAssignmentId);
		
		StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
		studentAssignmentStatus.setStudentAssignmentId(studentAssignmentId);
		studentAssignmentStatus.setAssignmentQuestions(questions);
		ModelAndView model = null;
		String teacherComment="";
		if (assignmentTypeId == 8) {
			model = new ModelAndView("Ajax/Student/include_student_completed_test_questions",
					"studentAssignmentStatus", studentAssignmentStatus);
			 teacherComment=gradeAssessmentsService.getBenchmarkTeacherComment(studentAssignmentId);
			 model.addObject("teacherComment",teacherComment);
			  
		}else if (assignmentTypeId == 7) {
			model = new ModelAndView("Ajax/Student/include_student_completed_test_questions","studentAssignmentStatus", studentAssignmentStatus);
			List<SubQuestions> ssQuestions = gradeAssessmentsService.getSSQuestions(questions);
			model.addObject("ssQuestions", ssQuestions);
		}else if (assignmentTypeId == 14) {
			model = new ModelAndView("Ajax/Student/include_jactemplate_questions",
					"studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("testQuestions", questions);
			List<JacTemplate> jacTitles = gradeAssessmentsService.getJacTemplateTitleList(questions);
			model.addObject("jacTitles", jacTitles);
			String jacQuestionFilePath=gradeAssessmentsService.getJacQuestionFilePath(jacTitles.get(0).getJacQuestionFile());
			model.addObject("jacQuestionFilePath",jacQuestionFilePath);

		}else if(assignmentTypeId == 18) {
			List<RflpPractice> rflpPracticeLt = takeAssessmentsService.getRflpTest(studentAssignmentId);
			List<RflpRubric> rflpRubricLt = gradeAssessmentsService.getRflpRubricValues();
			model = new ModelAndView("Ajax/Student/rflp_test_report", "rflpTest", new RflpTest());
			model.addObject("rflpPracticeLt", rflpPracticeLt);
			model.addObject("rflpRubricLt", rflpRubricLt);
			if(rflpPracticeLt.get(0) != null){
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				model.addObject("gradingTypesId", rflpPracticeLt.get(0).getRflpTest().getGradingTypes().getGradingTypesId());
				if(userReg.getUser().getUserTypeid() == 4){
					userReg = rflpPracticeLt.get(0).getRflpTest().getStudentAssignmentStatus().getStudent().getUserRegistration();
				}
				String uploadFilePath = FileUploadUtil.getUploadFilesPath(userReg, request);
				String rflpRecordsFilePath = "";
				rflpRecordsFilePath = uploadFilePath+File.separator+WebKeys.RFLP_TEST;
				model.addObject("rflpRecordsFilePath", rflpRecordsFilePath);
				model.addObject("dateDue", rflpPracticeLt.get(0).getRflpTest().getDateDue());
				float totalAvgScore = rflpPracticeLt.get(0).getRflpTest().getStudentAssignmentStatus().getPercentage();
				float totalOralScore = 0;
				float totalWrittenScore = 0;
				for (RflpPractice rflpPractice : rflpPracticeLt) {
					long oralScore = rflpPractice.getOralRubricScore();
					long writtenScore = rflpPractice.getWrittenRubricScore();
					totalOralScore = totalOralScore+oralScore;
					totalWrittenScore = totalWrittenScore+writtenScore;
				}
			    int len = rflpPracticeLt.size();
			    teacherComment = rflpPracticeLt.get(0).getRflpTest().getTeacherComment(); 
			    DecimalFormat df = new DecimalFormat("##.#");
				model.addObject("oralAvg", df.format(totalOralScore/len));
				model.addObject("writtenAvg", df.format(totalWrittenScore/len));						
				model.addObject("totalAvgScore", totalAvgScore);
				model.addObject("teacherComment", teacherComment);
			}
		}else {
			model = new ModelAndView("Ajax/Student/include_student_completed_test_questions","studentAssignmentStatus", studentAssignmentStatus);
			if(assignmentTypeId == 3){
				ArrayList<String> options = new ArrayList<>();
				for(AssignmentQuestions assignmentQuestions: questions){
					options.add(assignmentQuestions.getQuestions().getOption1());
					options.add(assignmentQuestions.getQuestions().getOption2());
					options.add(assignmentQuestions.getQuestions().getOption3());
					if(assignmentQuestions.getQuestions().getOption4()!=null && !assignmentQuestions.getQuestions().getOption4().isEmpty())
						options.add(assignmentQuestions.getQuestions().getOption4());
					if(assignmentQuestions.getQuestions().getOption5()!=null && !assignmentQuestions.getQuestions().getOption5().isEmpty())
						options.add(assignmentQuestions.getQuestions().getOption5());
				}
				model.addObject("mcqOptions",options);
				model.addObject("mcqOptionsTitles", WebKeys.MCQOPTIONS);
			}
		}
		model.addObject("testQuestions", questions);
		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("assignmentId", assignmentId);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("usedFor", usedFor);
		model.addObject("assignmentType",gradeAssessmentsService.getAssignment(assignmentId).getAssignmentType());
		return model;
	}
	
}
