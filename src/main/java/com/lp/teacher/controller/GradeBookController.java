package com.lp.teacher.controller;

import java.sql.SQLDataException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.appadmin.service.AppAdminService;
import com.lp.appadmin.service.AppAdminService2;
import com.lp.appadmin.service.AppAdminService3;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicGrades;
import com.lp.model.AssignmentQuestions;
import com.lp.model.Citizenship;
import com.lp.model.Comments;
import com.lp.model.CompositeChartValues;
import com.lp.model.Grade;
import com.lp.model.ItemAnalysis;
import com.lp.model.JacTemplate;
import com.lp.model.RegisterForClass;
import com.lp.model.Report;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentAttendance;
import com.lp.model.Teacher;
import com.lp.student.service.TakeAssessmentsService;
import com.lp.teacher.service.GradeAssessmentsService;
import com.lp.teacher.service.GradeBookService;
import com.lp.utils.WebKeys;

@Controller
public class GradeBookController {

	@Autowired
	private TakeAssessmentsService takeAssessmentsService;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private GradeAssessmentsService gradeAssessmentsService;
	@Autowired
	private GradeBookService gradeBookService;
	@Autowired
	private AppAdminService2 appAdminService2;
	@Autowired
	private AppAdminService3 appAdminService3;
	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private CommonService commonservice;
	@Autowired
	private HttpServletRequest request;

	static final Logger logger = Logger.getLogger(GradeBookController.class);	
	@RequestMapping(value = "/gradeBook", method = RequestMethod.GET)
	public ModelAndView gradeBook(HttpSession session, HttpServletRequest request) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Teacher/grade_book_main", "studentAssignmentStatus", new StudentAssignmentStatus());		
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
			String page = request.getParameter("page");
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("grList", teacherGrades);
			if(page != null && page.equalsIgnoreCase(WebKeys.LP_TAB_IOLREPORTCARD)){
				model.addObject("tab", WebKeys.LP_TAB_IOLREPORTCARD);
				model.addObject("page", WebKeys.LP_TAB_IOLREPORTCARD);
				model.addObject("teacherObj", teacherObj);
				model.addObject("stat", "create");
			}else if(page != null && page.equalsIgnoreCase(WebKeys.LP_TAB_ITEM_ANALYSIS_REPORTS)){
				model.addObject("tab", WebKeys.LP_TAB_ITEM_ANALYSIS_REPORTS);
				model.addObject("page", WebKeys.LP_TAB_ITEM_ANALYSIS_REPORTS);
			}else{
				model.addObject("tab", WebKeys.LP_TAB_GRADES);
			}
		}catch(DataException e){
			logger.error("Error in gradeBook() of GradeBookController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;		
	}	
	
	@RequestMapping(value = "/getGradeStudentList", method = RequestMethod.GET)
	public ModelAndView getGradeStudentList( HttpSession session,@RequestParam("csId") long csId,
			@RequestParam("usedFor") String usedFor, Model model) throws DataException {
		List<RegisterForClass> studentList = Collections.emptyList();
		HashMap<Long,Double> hm=new HashMap<Long,Double>();
		try{
			studentList = gradeBookService.getGradeStudentList(csId);
			hm = gradeBookService.getStudentAssignmentsByCsId(csId,usedFor);
			
		}catch(DataException e){
			logger.error("Error in getGradeStudentList() of GradeBookController"+ e);
			e.printStackTrace();
		}
		model.addAttribute("studentList", studentList);
		model.addAttribute("studentPercentage", hm);		
		return new ModelAndView("Ajax2/Teacher/grade_student_list");
	
	}
	
	@RequestMapping(value = "/getStudentGrades", method = RequestMethod.GET)
	public ModelAndView getStudentGrades( HttpSession session,@RequestParam("studentId") long studentId,
			@RequestParam("usedFor") String usedFor,Model model) throws DataException {
		List<StudentAssignmentStatus> studentTestList = Collections.emptyList();
		try{
			Student student = gradeBookService.getStudentById(studentId);
			studentTestList = takeAssessmentsService.getStudentTests(student, usedFor, 
					WebKeys.TEST_STATUS_SUBMITTED, WebKeys.GRADED_STATUS_GRADED);
		}catch(DataException e){
			logger.error("Error in getStudentGrades() of GradeBookController" + e);
			e.printStackTrace();
		}
		model.addAttribute("studentTestList", studentTestList);
		model.addAttribute("usedFor",usedFor);
		return new ModelAndView("Ajax2/Teacher/edit_student_grade");
	}
	
	@RequestMapping(value = "/updateStudentGrades", method = RequestMethod.POST)
	public View updateStudentGrades( HttpSession session,HttpServletRequest request, Model model) throws DataException {
		try{
			List<StudentAssignmentStatus> studentTestList = new ArrayList<>();
			long count = Long.parseLong(request.getParameter("totalCount"));
			for(int i=0;i<count;i++){
				long sasId = Long.parseLong(request.getParameter("sasId"+i));
				float percentage = Float.parseFloat(request.getParameter("percent"+i));
				StudentAssignmentStatus studentAssignment= new StudentAssignmentStatus();
				studentAssignment.setStudentAssignmentId(sasId);
				studentAssignment.setPercentage(percentage);
				studentTestList.add(studentAssignment);
			}
			gradeBookService.updateStudentGrades(studentTestList);
		}catch(Exception e){
			logger.error("Error in updateStudentGrades() of GradeBookController" + e);
		}		
		return new MappingJackson2JsonView();
	}	
	
	@RequestMapping(value = "/getAssignmentQuestion", method = RequestMethod.GET)
	public ModelAndView getAssignmentQuestion(  HttpSession session,@RequestParam("sasId") long sasId,@RequestParam("assignmentId") long assignmentId,@RequestParam("usedFor") String usedFor,@RequestParam("assignmentTypeId") long assignmentTypeId) throws DataException {
		List<AssignmentQuestions> assignQuestions = new ArrayList<AssignmentQuestions>();
		StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
		studentAssignmentStatus.setStudentAssignmentId(sasId);
		assignQuestions = gradeBookService.getAssignmentQuestion(sasId);
		studentAssignmentStatus.setAssignmentQuestions(assignQuestions);
		ModelAndView model = new ModelAndView("Ajax2/Teacher/show_student_questions","studentAssignmentStatus",studentAssignmentStatus);
		try{	
			if (assignmentTypeId == 14) {
				List<JacTemplate> jacTitles = gradeAssessmentsService.getJacTemplateTitleList(assignQuestions);
				model.addObject("jacTitles", jacTitles);
				String jacQuestionFilePath=gradeAssessmentsService.getJacQuestionFilePath(jacTitles.get(0).getJacQuestionFile());
				model.addObject("jacQuestionFilePath",jacQuestionFilePath);
			}
		
		}catch(Exception e){
			logger.error("Error in getAssignmentQuestion() of GradeBookController"	+ e);
		}
		 if(assignmentTypeId == 3){
				ArrayList<String> options = new ArrayList<>();
				for(AssignmentQuestions assignmentQuestions: assignQuestions){
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
		model.addObject("testQuestions", assignQuestions);
		model.addObject("assignmentId", assignmentId);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("usedFor", usedFor);
		model.addObject("assignmentType",appAdminService.getAssignmentType(assignmentTypeId));
		return model;
	}
	
	@RequestMapping(value = "/progressReports", method = RequestMethod.GET)
	public ModelAndView progressReports(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Ajax2/Teacher/reports_main", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("tab", WebKeys.LP_TAB_REPORTS);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("grList", teacherGrades);
		}catch(DataException e){
			logger.error("Error in progressReports() of GradeBookController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	
	@RequestMapping(value = "/getReportStudentList", method = RequestMethod.GET)
	public ModelAndView getReportStudentList( HttpSession session,@RequestParam("csId") long csId,
			@RequestParam("fromId") Date fromId, @RequestParam("toId") Date toId, Model model) throws DataException {
		List<RegisterForClass> studentList = Collections.emptyList();
		HashMap<Long,Double> homeworkAverages=new HashMap<Long,Double>();
		HashMap<Long,Double> assessmentAverages=new HashMap<Long,Double>();
		HashMap<Long,Double> performanceAverages=new HashMap<Long,Double>();
		HashMap<Long,StudentAttendance> studentAttendance=new HashMap<Long,StudentAttendance>();
		List<Citizenship> citizen = new ArrayList<Citizenship>();
		List<Comments> comments = new ArrayList<Comments>();
		List<AcademicGrades> academicGrades = new ArrayList<AcademicGrades>();
		try{
			String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(fromId);
			String toDate = new SimpleDateFormat("yyyy-MM-dd").format(toId);
			studentList = gradeBookService.getGradeStudentList(csId);
			homeworkAverages = gradeBookService.getStudentAssignmentsExcludePerformance(csId,WebKeys.LP_USED_FOR_HOMEWORKS,fromDate,toDate);
			assessmentAverages = gradeBookService.getStudentAssignmentsExcludePerformance(csId,WebKeys.LP_USED_FOR_ASSESSMENT,fromDate,toDate);
			performanceAverages = gradeBookService.getStudentPerformanceAssessments(csId,fromDate,toDate);
			studentAttendance = gradeBookService.getClassAttendance(csId, fromDate, toDate);
			citizen = appAdminService2.getCitizenships();
			comments = appAdminService2.getComments();
			academicGrades = appAdminService3.getAcademicGrades();
			
		}catch(DataException e){
			logger.error("Error in getGradeStudentList() of GradeBookController"
					+ e);
			e.printStackTrace();
		}
		model.addAttribute("studentList", studentList);
		model.addAttribute("homeworkAverages", homeworkAverages);
		model.addAttribute("assessmentAverages", assessmentAverages);
		model.addAttribute("performanceAverages", performanceAverages);
		model.addAttribute("studentAttendance", studentAttendance);
		model.addAttribute("citizen", citizen);
		model.addAttribute("comments", comments);
		model.addAttribute("academicGrades", academicGrades);
		return new ModelAndView("Ajax2/Teacher/report_student_list", "report", new Report());	
	}
	
	@RequestMapping(value = "/reportSubmit", method = RequestMethod.POST)
	public View reportSubmit(@ModelAttribute("report") Report report, HttpServletRequest request,
			HttpServletResponse response, HttpSession session,Model model) {
		try{
			gradeBookService.submitReportChanges(report);
			model.addAttribute("status", WebKeys.UPDATED_SUCCESSFULLY);
		}catch(DataException e){
			logger.error("Error in reportSubmit() of GradeBookController"+ e);
			model.addAttribute("status", e.getMessage());
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/itemAnalysisReport", method = RequestMethod.GET)
		public ModelAndView itemAnalysisReport(HttpSession session) {
			List<Grade> teacherGrades = new ArrayList<Grade>();
			ModelAndView model = new ModelAndView("Ajax2/Teacher/item_analysis_report", "studentAssignmentStatus", new StudentAssignmentStatus());
			model.addObject("tab", WebKeys.LP_TAB_ITEM_ANALYSIS_REPORTS);
			Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
			try{
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}catch(DataException e){
				logger.error("Error in itemAnalysisReport() of GradeBookController"+ e);
				e.printStackTrace();
				model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
			}
			return  model;
		}	
		
	@RequestMapping(value = "/getItemAnalysisReports", method = RequestMethod.GET)
	public ModelAndView getItemAnalysisReports( HttpSession session, @RequestParam("assignmentId") long assignmentId, Model model) {
		ItemAnalysis itemAnalysis = null;
		try{
			itemAnalysis = gradeBookService.getItemAnalysisReports(assignmentId);
		}catch(Exception e){
			logger.error("Error in getItemAnalysisReports() of GradeBookController" + e);
			e.printStackTrace();
		}
		model.addAttribute("itemAnalysisReports", itemAnalysis);		
		return new ModelAndView("Ajax2/Teacher/sub_item_analysis_report");
	}
	@RequestMapping(value = "/previousReports", method = RequestMethod.GET)
	public ModelAndView previousReports(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Ajax2/Teacher/previous_reports_main");
		model.addObject("tab", WebKeys.LP_TAB_PREVIOUS_REPORTS);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("grList", teacherGrades);
		}catch(DataException e){
			logger.error("Error in previousReports() of GradeBookController" + e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
		
	@RequestMapping(value = "/getStudentsByCsId", method = RequestMethod.GET)
	public View getStudentsByCsId(@RequestParam("csId") long csId, Model model,HttpSession session) {
		List<RegisterForClass> studentList = new ArrayList<RegisterForClass>();
		try{
			studentList = commonservice.getStudentsByCsId(csId);  
		}catch(DataException e){
			logger.error("Error in getStudentsByCsId() of GradeBookController"+ e);
		}
		model.addAttribute("studentList", studentList);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getReportDates", method = RequestMethod.GET)
	public View getReportDates(@RequestParam("csId") long csId,@RequestParam("studentId") long studentId, Model model,HttpSession session) {
		List<Report> reports = new ArrayList<Report>();
		try{
			reports = gradeBookService.getReportDates(csId,studentId);
			session.setAttribute("reportList", reports);
		}catch(DataException e){
			logger.error("Error in getReportDates() of GradeBookController"+ e);
		}
		model.addAttribute("size", reports.size());
		model.addAttribute("reports", reports);
		return new MappingJackson2JsonView();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPreviousReports", method = RequestMethod.GET)
	public ModelAndView getPreviousReports( HttpSession session,@RequestParam("reportId") long reportId,
			Model model) throws DataException {
		Report studentReport = new Report();
		List<Report> reports = new ArrayList<Report>();
		try{
			reports = (List<Report>) session.getAttribute("reportList");
			for(Report rp : reports){
				if(rp.getReportId() == reportId){
					studentReport = rp;
				}
			}
		}catch(DataException e){
			logger.error("Error in getPreviousReports() of GradeBookController" + e);
			e.printStackTrace();
		}
		model.addAttribute("studentReport", studentReport);			
		return new ModelAndView("Ajax2/Teacher/previous_reports_sub");		
	}
	
	@RequestMapping(value = "/compositeChart", method = RequestMethod.GET)
	public ModelAndView compositeChart(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Ajax2/Teacher/add_composite_chart_values",
			"studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("tab", WebKeys.LP_TAB_COMPOSITE);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try {
			
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("grList", teacherGrades);
			model.addObject(WebKeys.TEACHER_OBJECT, teacherObj);
			
		} catch (DataException e) {
			logger.error("Error in itemAnalysisReport() of GradeBookController"
					+ e);
			e.printStackTrace();
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}
		return model;
	}
	
	@RequestMapping(value = "/getCompositeChartValues", method = RequestMethod.GET)
	public ModelAndView getCompositeChartValues( HttpSession session, @RequestParam("csId") long csId, Model model) throws DataException {
		CompositeChartValues compositeChartValues = new CompositeChartValues();
		try{
			compositeChartValues = gradeBookService.getCompositeChartValues(model, csId);
		}catch(DataException e){
			logger.error("Error in getItemAnalysisReports() of GradeBookController" + e);
			e.printStackTrace();
		}
		model.addAttribute("count", compositeChartValues.getCompositecharts().size() -1 );
		model.addAttribute("compositeChartValues", compositeChartValues);
		return new ModelAndView("Ajax2/Teacher/include_composite_chart", "compositeChartValues", compositeChartValues);		
	}	
	
	@RequestMapping(value = "/saveCompositeChartValues", method = RequestMethod.POST)
	public View saveCompositeChartValues(HttpSession session,
	@ModelAttribute("compositeChartValues") CompositeChartValues compositeChartValues, BindingResult result, Model model) throws Exception {
	try{	
	gradeBookService.saveCompositeChartValues(compositeChartValues);
	model.addAttribute("returnMessage", WebKeys.COMPOSITE_CHART_CREATED_SUCCESS);	
	} catch (DataException e) {
	logger.error("Error in saveCompositeChartValues() of GradeBookController" + e);
	e.printStackTrace();
	model.addAttribute("helloAjax", WebKeys.LP_SYSTEM_ERROR);
	}
	return new MappingJackson2JsonView();
	} 
	
	@RequestMapping(value = "/getStudentCompositeChart", method = RequestMethod.GET)
	public ModelAndView getStudentCompositeChart(HttpSession session, @RequestParam("csId") long csId,
			@RequestParam("studentId") long studentId, Model model) throws DataException {
		CompositeChartValues compositeChartValues = new CompositeChartValues();
		try{
			compositeChartValues = gradeBookService.getCompositeChartValues(model, csId);
			model.addAttribute("compositeChartValues", compositeChartValues);
			model.addAttribute("assignedLessons", gradeBookService.getAssignedLesson(csId, studentId));
			model.addAttribute("assignedActivities", gradeBookService.getAssignedActivities(csId, studentId));
			model.addAttribute("studentAttendance", gradeBookService.getStudentAttendance(csId, studentId));
			model.addAttribute("studentTestResults", gradeBookService.getStudentTests(csId, studentId));		
			model.addAttribute("projectScores", gradeBookService.getStudentCompositeProjectScore(csId, studentId));
			model.addAttribute("count", compositeChartValues.getCompositecharts().size() -1 );
			model.addAttribute("studentId", studentId);
			model.addAttribute("csId", csId);
		}catch(DataException e){
			logger.error("Error in getStudentCompositeChart() of GradeBookController" + e);
			e.printStackTrace();
		}
		catch(SQLDataException e){
			logger.error("Error in getStudentCompositeChart() of GradeBookController" + e);
			e.printStackTrace();
		}			
		return new ModelAndView("Ajax2/CommonJsp/student_composite_chart", "compositeChartValues", compositeChartValues);		
	}
	
	@RequestMapping(value = "/saveStudentCompositeChartValues", method = RequestMethod.POST)
	public ModelAndView saveStudentCompositeChartValues() throws DataException{
		long studentId = Long.valueOf(request.getParameter("studentId"));
		long csId = Long.valueOf(request.getParameter("csId"));
		try{
			String[] assignLessonIds = request.getParameterValues("assignLessonIds");
			String[] lessonScores = request.getParameterValues("lessonScores");
			String[] assignActivityIds = request.getParameterValues("assignActivityIds");
			String[] activityScores = request.getParameterValues("activityScores");
			int projectScore =  Integer.parseInt(request.getParameter("projectScore"));
			gradeBookService.saveStudentCompositeChartValues(studentId, csId, assignLessonIds, lessonScores, assignActivityIds, activityScores, projectScore);
		}
		catch(DataException e){
			logger.error("Error in saveStudentCompositeChartValues() of GradeBookController" + e);
			e.printStackTrace();
		} catch (SQLDataException e) {
			logger.error("Error in saveStudentCompositeChartValues() of GradeBookController" + e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/getStudentRoster.htm?csId="+csId);
	}
	
	@RequestMapping(value = "/getCompositeChartByStudent", method = RequestMethod.GET)
	public ModelAndView getCompositeChartByStudent(HttpSession session, @RequestParam("csId") long csId, Model model) throws DataException {
		CompositeChartValues compositeChartValues = new CompositeChartValues();
		try{
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			compositeChartValues = gradeBookService.getCompositeChartValues(model, csId);
			model.addAttribute("compositeChartValues", compositeChartValues);
			model.addAttribute("assignedLessons", gradeBookService.getAssignedLesson(csId, student.getStudentId()));
			model.addAttribute("assignedActivities", gradeBookService.getAssignedActivities(csId, student.getStudentId()));
			model.addAttribute("studentAttendance", gradeBookService.getStudentAttendance(csId, student.getStudentId()));
			model.addAttribute("studentTestResults", gradeBookService.getStudentTests(csId, student.getStudentId()));		
			model.addAttribute("projectScores", gradeBookService.getStudentCompositeProjectScore(csId, student.getStudentId()));
			model.addAttribute("count", compositeChartValues.getCompositecharts().size() -1 );
			model.addAttribute("studentId", student.getStudentId());
			model.addAttribute("csId", csId);
		}catch(DataException e){
			logger.error("Error in getCompositeChartByStudent() of GradeBookController" + e);
			e.printStackTrace();
		}
		catch(SQLDataException e){
			logger.error("Error in getCompositeChartByStudent() of GradeBookController" + e);
			e.printStackTrace();
		}			
		return new ModelAndView("Ajax2/CommonJsp/student_composite_chart", "compositeChartValues", compositeChartValues);		
	}
	
	@RequestMapping(value = "/saveParentComments", method = RequestMethod.GET)
	public View saveParentComments(@RequestParam("comment") String comment,@RequestParam("reportId") long reportId,
			Model model,HttpSession session) {
		boolean status = false;
		try{
			status = gradeBookService.saveParentComments(reportId,comment);
		}catch(DataException e){
			logger.error("Error in saveParentComments() of GradeBookController"+ e);
		}
		model.addAttribute("status", status);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/gradeBooks", method = RequestMethod.GET)
	public ModelAndView gradeBooks(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Ajax2/Teacher/grade_book_main_sub", "studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("tab", WebKeys.LP_TAB_GRADES);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("grList", teacherGrades);
		}catch(DataException e){
			logger.error("Error in gradeBook() of GradeBookController"+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}	
}
