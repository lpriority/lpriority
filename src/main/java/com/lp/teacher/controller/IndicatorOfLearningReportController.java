package com.lp.teacher.controller;

import java.text.DecimalFormat;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.custom.exception.DataException;
import com.lp.model.FilesLP;
import com.lp.model.Grade;
import com.lp.model.IOLReport;
import com.lp.model.LearningIndicator;
import com.lp.model.LearningIndicatorValues;
import com.lp.model.Legend;
import com.lp.model.LegendCriteria;
import com.lp.model.LegendSubCriteria;
import com.lp.model.RegisterForClass;
import com.lp.model.StemUnitActList;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentStarStrategies;
import com.lp.model.Teacher;
import com.lp.model.Trimester;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.service.StudentService;
import com.lp.teacher.service.IOLReportCardService;
import com.lp.teacher.service.StemCurriculumSubService;
import com.lp.teacher.service.TeacherService;
import com.lp.utils.WebKeys;

@Controller
public class IndicatorOfLearningReportController {

	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private ParentService parentService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private IOLReportCardService iolReportCardService;
	@Autowired
	private StemCurriculumSubService stemCurriculumSubService;
	@Autowired
	private CommonService commonservice;

	static final Logger logger = Logger.getLogger(IndicatorOfLearningReportController.class);

	@RequestMapping(value = "/RIO21stIOLReportCard", method = RequestMethod.GET)
	public ModelAndView createILReportCard(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView(
				"Ajax2/Teacher/indicator_learning_report_card");
		model.addObject("tab", WebKeys.LP_TAB_IOLREPORTCARD);
		model.addObject("page", WebKeys.LP_TAB_IOLREPORTCARD);
		Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");
		try {
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("grList", teacherGrades);
			model.addObject("teacherObj", teacherObj);
			model.addObject("stat", "create");
		} catch (DataException e) {
			logger.error("Error in showIOLReportCard() of IndicatorOfLearningReportController"
					+ e);
			e.printStackTrace();
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}
		return model;
	}
   
	@RequestMapping(value = "/StudentSubmitIOLReportCard", method = RequestMethod.POST)
	public @ResponseBody
	void submitTest(
			@RequestParam("iolReportId") long iolReportId,@RequestParam("studentId") long studentId,
			@RequestParam("csId") long csId,@RequestParam("trimesterId") long trimesterId,
			HttpSession session, HttpServletResponse response) {

		try {

			boolean status = iolReportCardService.submitStudentIOLReportCardToTeacher(iolReportId,studentId,csId,trimesterId);
			if (status == true)
				response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write("Report Card Created SuccessFully");

		} catch (Exception e) {
			logger.error("Error in submitTest() of of IndicatorOfLearningReportController"
					+ e);
		}
	}

	@RequestMapping(value = "/TeacherSubmitIOLReportCard", method = RequestMethod.POST)
	public @ResponseBody
	void teacherSubmitIOLReport(
			@RequestParam("iolReportId") long iolReportId,
			HttpSession session, HttpServletResponse response) {

		try {

			boolean status = iolReportCardService.TeacherCreateIOLReportCard(iolReportId);
			if (status == true)
				session.setAttribute("reportSuccess",
						"Report Card Created SuccessFully");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write("success");

		} catch (Exception e) {
			logger.error("Error in teacherSubmitIOLReport() of of IndicatorOfLearningReportController"
					+ e);
			session.setAttribute("reportSuccess", "Failure");
		}

	}

	@RequestMapping(value = "/ShowLPReportCard", method = RequestMethod.GET)
	public ModelAndView showIOLReportCard(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView(
				"Ajax2/Teacher/teacher_view_iolreport_card");
		model.addObject("tab", WebKeys.LP_TAB_SHOWREPORTS);
		Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");
		try {
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("grList", teacherGrades);
			model.addObject("teacherObj", teacherObj);
		} catch (DataException e) {
			logger.error("Error in showIOLReportCard() of IndicatorOfLearningReportController"
					+ e);
			e.printStackTrace();
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}
		return model;
	}

	@RequestMapping(value = "/getStudentIOLReportDates", method = RequestMethod.GET)
	public ModelAndView getStudentIOLReportCardDates(
			@RequestParam("csId") long csId,
			@RequestParam("studentId") long studentId,
			@RequestParam("stat") String stat,@RequestParam("tab") String tab, HttpSession session) {
		ModelAndView model=null;
		List<IOLReport> reportDates = new ArrayList<IOLReport>();
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		if(studentId==0){
			List<LegendCriteria> legendCriterias = iolReportCardService.getLegendCriteria();
			model = new ModelAndView("Ajax/Teacher/whole_class_grade_main_page");	
			model.addObject("legendCriterias",legendCriterias);
		}
		else{
		model = new ModelAndView("Ajax/Teacher/show_iol_reportcard");
		String userType = userReg.getUser().getUserType();
		model.addObject("csId", csId);
		model.addObject("userType", userType);
		try {
			if (stat.equalsIgnoreCase("create")) {
				reportDates = iolReportCardService.getStudentIOLReportDates(csId, studentId);
			} else {
				reportDates = iolReportCardService.getStudentCompletedIOLReportDates(csId, studentId);
			}
			model.addObject("reportDates", reportDates);
			model.addObject("csId", csId);
			model.addObject("studentId", studentId);
			model.addObject("stat", stat);
			model.addObject("tab", tab);
		} catch (DataException e) {
			logger.error("Error in getStudentIOLReportDates() of IndicatorOfLearningReportController"
					+ e);
		}
		}
		return model;
	}

	@RequestMapping(value = "/viewStudentIOLReportCards", method = RequestMethod.GET)
	public ModelAndView getIOLReportCardDates(HttpSession session) {
		List<IOLReport> reportDates = new ArrayList<IOLReport>();
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView(
				"Ajax/Student/show_student_iol_reportDates");
		String userType = userReg.getUser().getUserType();
		long studentId = 0;
		model.addObject("userType", userType);
		try {
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			studentId = student.getStudentId();
			reportDates = iolReportCardService.getStudentAllIOLReportDates(studentId);
			model.addObject("reportDates", reportDates);
			model.addObject("studentId", studentId);
			model.addObject("tab", WebKeys.LP_TAB_SHOWREPORTS);
			model.addObject("status", false);
		} catch (DataException e) {
			logger.error("Error in getIOLReportCardDates() of IndicatorOfLearningReportController"
					+ e);
		}
		return model;
	}

	@RequestMapping(value = "/showStudentIOLReportCard", method = RequestMethod.GET)
	public ModelAndView viewStudentIOLReportCard(HttpSession session,
			@RequestParam("studentId") long studentId,
			@RequestParam("iolReportId") long iolReportId,
			@RequestParam("reportDate") String reportDate,
			@RequestParam("status") String status,@RequestParam("tab") String tab) {
		String studentName = "", gradeLevel = "", stat = "show";
		ModelAndView model = new ModelAndView(
				"Ajax/Teacher/create_iol_report_card");
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String schoolImage = userReg.getSchool().getLogoLink();
		List<Legend> legends = iolReportCardService.getLegends();
		model.addObject("userType", userReg.getUser().getUserType());
		model.addObject("userType", userReg.getUser().getUserType());
		try {
			List<LearningIndicatorValues> litracyLearnIndValues=null;
			List<LearningIndicator> learnInds = null;
			learnInds = iolReportCardService.getStudentIOLReportByIOLReportId(studentId,iolReportId);
			litracyLearnIndValues=iolReportCardService.getLitracyLearnIndValues(iolReportId,6);
					
			String upStatus = "show";
			List<LegendCriteria> legendCriterias = iolReportCardService.getLegendCriteria();
			Student student = iolReportCardService.getStudent(studentId);
			studentName = student.getUserRegistration().getFirstName() + " "
					+ student.getUserRegistration().getLastName();
			long gradeId = student.getGrade().getGradeId();
			Grade grade = iolReportCardService.getGrade(gradeId);
			gradeLevel = grade.getMasterGrades().getGradeName();
			model.addObject("listLegCriteria", legendCriterias);
			model.addObject("studentName", studentName);
			model.addObject("studentId", student.getStudentId());
			model.addObject("gradeLevel", gradeLevel);
			model.addObject("legends", legends);
			model.addObject("schoolImage", schoolImage);
			model.addObject("reportDate", reportDate);
			model.addObject("litracyLearnIndValues",litracyLearnIndValues);
			model.addObject("stat", stat);
			model.addObject("upStatus", upStatus);
			model.addObject("learnInds",learnInds);
			if (userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER)) {
				Teacher teacherObj = (Teacher) session
						.getAttribute("teacherObj");
				model.addObject("teacher", teacherObj.getUserRegistration());
			} else {
				IOLReport iolReport = iolReportCardService.getIOLReport(iolReportId);
				 Teacher teacherObj = iolReport.getClassStatus().getTeacher();
				 model.addObject("teacher", teacherObj.getUserRegistration());
				 model.addObject("teacherId",teacherObj.getTeacherId());
			}
			model.addObject("trimesterId",iolReportCardService.getTrimesterByIolReportId(iolReportId).getTrimester().getTrimesterId());
			model.addObject("tab",tab);
		} catch (DataException e) {
			logger.error("Error in createStudentIOLReportCard() of IndicatorOfLearningReportController"
					+ e);
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}
		return model;
	}
	

	
	@RequestMapping(value = "/viewChildIOLReportCards", method = RequestMethod.GET)
	public ModelAndView getChildIOLReportCard(HttpSession session) {
		ModelAndView model = new ModelAndView("Ajax/Parent/child_iol_report_cards");
		try {
			model.addObject("tab", WebKeys.LP_TAB_SHOWREPORTS);
			UserRegistration userReg = (UserRegistration) session
					.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			List<UserRegistration> childrenRegistrations = new ArrayList<UserRegistration>();
			List<Student> students = new ArrayList<Student>();
			childrenRegistrations = parentService.getchildren(userReg
					.getRegId());
			for (UserRegistration ur : childrenRegistrations) {
				Student student = studentService.getStudent(ur.getRegId());
				students.add(student);
			}
			model.addObject("students", students);
		} catch (DataException e) {
			logger.error("Error in getChildIOLReportCard() of IndicatorOfLearningReportController"
					+ e);
		}
		return model;
	}

	@RequestMapping(value = "/getChildIOLReportDates", method = RequestMethod.GET)
	public ModelAndView getChildIOLReportCardDates(
			@RequestParam("studentId") long studentId,@RequestParam("tab") String tab, HttpSession session) {
		List<IOLReport> reportDates = new ArrayList<IOLReport>();
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView(
				"Ajax/Student/show_student_iol_reports");
		String userType = userReg.getUser().getUserType();
		model.addObject("userType", userType);
		try {

		    reportDates = iolReportCardService.getChildAllIOLReportDates(studentId);
					
			model.addObject("reportDates", reportDates);
			model.addObject("studentId", studentId);
			model.addObject("stat", "show");
			model.addObject("tab",tab);
		} catch (Exception e) {
			logger.error("Error in getChildIOLReportCardDates() of IndicatorOfLearningReportController"
					+ e);
		}

		return model;
	}

	@RequestMapping(value = "/StudentSelfIOLReportCard", method = RequestMethod.GET)
	public ModelAndView studentCreateIOLReportCard(HttpSession session) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		Grade grade = new Grade();
		String tab="studCreate";
		try {
			grade = student.getGrade();
			
		} catch (DataException e) {
			logger.error("Error in studentTestResults() of StudentResultsController"
					+ e);
		}
		ModelAndView model = new ModelAndView(
				"Student/student_create_iol_report", "studentAssignmentStatus",
				new StudentAssignmentStatus());
		model.addObject("grade", grade);
		model.addObject("tab",tab);
		model.addObject("studentClasses",
				studentService.getStudentClasses(student));
		return model;
	}
	
	@RequestMapping(value = "/checkIOLReportExists", method = RequestMethod.GET)
	public View checkIOLReportExists(@RequestParam("csId") long csId,
			HttpServletRequest request, HttpSession session,Model model) {
		try {
			Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
			long trimesterId=0;
			String status="";
			long reportExists=iolReportCardService.chkIOLReportExistsByCsIdStudentId(csId,student.getStudentId());
			IOLReport iolReport=new IOLReport();
		    if(reportExists==1){
		    	iolReport=iolReportCardService.getTrimesterId(csId,student.getStudentId());
		    	trimesterId=iolReport.getTrimester().getTrimesterId();
		    	status=iolReport.getStatus();
			}
            model.addAttribute("trimesterId",trimesterId);
            model.addAttribute("reportExists",reportExists);
            model.addAttribute("status",status);
		} catch (Exception e) {
			logger.error("Error in checkIOLReportExists() of IndicatorOfLearningReportController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/CreateSelfIOLReportCard", method = RequestMethod.GET)
	public ModelAndView getStudentIOLReport(@RequestParam("csId") long csId,@RequestParam("trimesterId") long trimesterId,
			@RequestParam("tab") String tab,HttpSession session) {
		ModelAndView model = null;
		String studentName = "", gradeLevel = "", upStatus = "upload";
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		String schoolImage = userReg.getSchool().getLogoLink();
		//List<Legend> legends = iolReportCardService.getLegends();
		Teacher teacher = iolReportCardService.getclassStatus(csId)
				.getTeacher();
		List<IOLReport> iolReports = null;
		List<LearningIndicatorValues> litracyLearnIndValues=null;
		List<LearningIndicator> learnInds = null;
		long gradeId = student.getGrade().getGradeId();
		Grade grade = iolReportCardService.getGrade(gradeId);
		try {
			iolReports = iolReportCardService.getStudentInCompletedIOLReport(csId,student.getStudentId());
			if (iolReports.size()>0) {
				learnInds = iolReportCardService.getStudentCurrentIOLReport(csId, student.getStudentId(),iolReports.get(0).getIolReportId());
				litracyLearnIndValues=iolReportCardService.getLitracyLearnIndValues(iolReports.get(0).getIolReportId(),6);
											
			}else{
				learnInds = iolReportCardService.saveStudentIOLReportCard(csId, student.getStudentId(),trimesterId,grade);
				litracyLearnIndValues=iolReportCardService.getLitracyLearnIndValues(learnInds.get(0).getIolReport().getIolReportId(),6);
			}
			//String createDate=learnInds.get(0).getCreateDate().toString();
			//List<LegendCriteria> legendCriterias = iolReportCardService.getLegendCriteria();
			studentName = student.getUserRegistration().getFirstName() + " "
					+ student.getUserRegistration().getLastName();
			
			gradeLevel = grade.getMasterGrades().getGradeName();
			model = new ModelAndView("Ajax/Student/student_self_iol_report1");
			//model.addObject("listLegCriteria", legendCriterias);
			model.addObject("studentName", studentName);
			model.addObject("studentId", student.getStudentId());
			model.addObject("gradeLevel", gradeLevel);
			//model.addObject("legends", legends);
			model.addObject("schoolImage", schoolImage);
			model.addObject("learnInds", learnInds);
			model.addObject("csId", csId);
			model.addObject("gradeId", gradeId);
			model.addObject("upStatus", upStatus);
			model.addObject("litracyLearnIndValues",litracyLearnIndValues);
			model.addObject("trimesterId",learnInds.get(0).getIolReport().getTrimester().getTrimesterId());
			model.addObject("teacherRegId",teacher.getUserRegistration().getRegId());
			model.addObject("tab",tab);
			
		} catch (DataException e) {
			logger.error("Error in getLegendCriterias() of IndicatorOfLearningReportController"
					+ e);
		}
		model.addObject("teacher", teacher);
		return model;
	}

//	@RequestMapping(value = "/StudentSelfSubmitIOLReport", method = RequestMethod.POST)
//	public @ResponseBody
//	void studentSelfSubmitReport(
//			@RequestParam("learningIndicatorId") long learningIndicatorId,
//			HttpSession session, HttpServletResponse response, Model model) {
//
//		try {
//			boolean status = iolReportCardService
//					.submitStudentIOLReportCardToTeacher(learningIndicatorId);
//			if (status) {
//				model.addAttribute("status", status);
//			}
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html");
//			response.getWriter().write("Report Card Created SuccessFully");
//
//		} catch (Exception e) {
//			logger.error("Error in submitTest() of of IndicatorOfLearningReportController"
//					+ e);
//		}
//
//	}

	@RequestMapping(value = "/saveTeacherComment", method = RequestMethod.POST)
	public @ResponseBody
	void saveTeacherComment(@RequestParam("learnValId") long learnValId,
			@RequestParam("teacherNote") String teacherNote,
			HttpSession session, HttpServletResponse response) {
		try {
			boolean status = iolReportCardService.saveTeacherComment(
					learnValId, teacherNote);
			if (status == true)
				session.setAttribute("flag", "changes saved");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write("success");
		} catch (Exception e) {
			logger.error("Error in saveTeacherComment() of of IndicatorOfLearningReportController"
					+ e);
		}
	}

	@RequestMapping(value = "/saveTeacherScore", method = RequestMethod.POST)
	public @ResponseBody
	void saveTeacherScore(@RequestParam("learnValId") long learnValId,
			@RequestParam("legend") long legend, HttpSession session,
			HttpServletResponse response) {
		try {
			boolean status = iolReportCardService.saveTeacherScore(learnValId,
					legend);
			if (status == true)
				session.setAttribute("flag", "changes Saved");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write("success");

		} catch (Exception e) {
			logger.error("Error in saveTeacherScore() of of IndicatorOfLearningReportController"
					+ e);
			// session.setAttribute("reportSuccess", "Failure");
		}
	}

	@RequestMapping(value = "/saveStudentNotes", method = RequestMethod.POST)
	public @ResponseBody
	void saveStudentNotes(
			@RequestParam("learnIndiValueId") long learnIndiValueId,
			@RequestParam("studentNotes") String studentNotes,
			HttpSession session, HttpServletResponse response) {
		try {
			boolean status = iolReportCardService.saveStudentNotes(
					learnIndiValueId, studentNotes);
			if (status == true)
				session.setAttribute("flag", "changes saved");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write("success");
		} catch (Exception e) {
			logger.error("Error in saveStudentNotes() of of IndicatorOfLearningReportController"
					+ e);
			// session.setAttribute("reportSuccess", "Failure");
		}
	}

	@RequestMapping(value = "/saveStudentSelfScore", method = RequestMethod.POST)
	public @ResponseBody
	void saveStudentSelfScore(
			@RequestParam("learnIndiValueId") long learnIndiValueId,
			@RequestParam("legend") long legend, HttpSession session,
			HttpServletResponse response) {
		try {
			boolean status = iolReportCardService.saveStudentSelfScore(
					learnIndiValueId, legend);
			if (status == true)
				session.setAttribute("flag", "changes saved");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write("success");
		} catch (Exception e) {
			logger.error("Error in saveStudentNotes() of of IndicatorOfLearningReportController"
					+ e);
			// session.setAttribute("reportSuccess", "Failure");
		}
	}
  
	@RequestMapping(value = "/getRubricByGradeId", method = RequestMethod.POST)
	public ModelAndView getRubricByGradeId(
			@RequestParam("gradeId") long gradeId,@RequestParam("subCriteriaId") long subCriteriaId,@RequestParam("indx") long indx,
			@RequestParam("editStatus") String editStatus,
			@RequestParam("type") String type,@RequestParam("trimesterId") long trimesterId,@RequestParam("teacherRegId") long teacherRegId,
			HttpSession session) {
		ModelAndView model = null;
		//List<LERubrics> leRubrics = new ArrayList<LERubrics>();
		List<Legend> lstLegend=new ArrayList<Legend>();
		try {
			
			long referRegId=iolReportCardService.getReferRegId(gradeId,trimesterId,teacherRegId);
			
			//leRubrics = iolReportCardService.getRubricValuesByGradeId(gradeId,subCriteriaId);
			lstLegend=iolReportCardService.getRubricValuesByUserId(referRegId,subCriteriaId,gradeId);
			LegendSubCriteria subCriteria = iolReportCardService.getLegendSubCriteria(subCriteriaId);

			model = new ModelAndView("Ajax/Student/show_le_rubrics");
			model.addObject("leRubrics", lstLegend);
			model.addObject("subCriteria",subCriteria.getlegendSubCriteriaName());
			model.addObject("indx",indx);
			model.addObject("editStatus",editStatus);
			model.addObject("type",type);
		} catch (DataException e) {
			logger.error("Error in getLegendCriterias() of IndicatorOfLearningReportController"
					+ e);
		}

		return model;
	}
 
	@RequestMapping(value = "/getLEFileBySubCriteriaId", method = RequestMethod.POST)
	public ModelAndView getTestQuestions(
			@RequestParam("subCriteriaId") long subCriteriaId,
			@RequestParam("studentId") long studentId,
			@RequestParam("index") long index,
			@RequestParam("learningIndicatorId") long learningIndicatorId,
			@RequestParam("learnIndiValueId") long learnIndiValueId,
			@RequestParam("stat") String stat, HttpServletRequest request) {
		
		
		List<FilesLP> fileList = new ArrayList<FilesLP>();
		String status = "";
		ModelAndView model = null;
		try{
		LearningIndicatorValues learnIndValues = iolReportCardService
				.getLearningIndicatorValues(learnIndiValueId);
		model = new ModelAndView("Ajax/Student/le_file_upload",
				"learnIndValues", learnIndValues);
		model.addObject("createdBy", studentId);
		model.addObject("learningIndicatorId", learningIndicatorId);
		model.addObject("subCriteriaId", subCriteriaId);
		model.addObject("learnIndiValueId", learnIndiValueId);
		model.addObject("index", index);
		if (stat.equalsIgnoreCase("upload"))
			status = "visible";
		else
			status = "hidden";
		model.addObject("status", status);
		model.addObject("upStatus", stat);
		fileList = iolReportCardService.getLEFileList(studentId,learningIndicatorId, learnIndiValueId);
		model.addObject("fileList", fileList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/leFileSave", method = RequestMethod.POST)
	public View leFileAutoSave(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpSession session,Model model) {
		try {
			long createdBy = Long.parseLong(request.getParameter("createdBy"));
			long learningIndicatorId = Long.parseLong(request.getParameter("learningIndicatorId"));
			long subCriteriaId = Long.parseLong(request.getParameter("subCriteriaId"));
			long learnIndValueId = Long.parseLong(request.getParameter("learnIndiValueId"));
			@SuppressWarnings("unused")
			String path = iolReportCardService.leFileAutoSave(file, createdBy, learningIndicatorId, subCriteriaId, learnIndValueId);
            model.addAttribute("learningIndicatorId",learningIndicatorId);
		} catch (Exception e) {
			logger.error("Error in leFileAutoSave() of IndicatorOfLearningReportController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	 @RequestMapping(value = "/getRubricDesc", method = RequestMethod.POST)
		public View getRubricDesc(@RequestParam("criteriaId") long criteriaId,
				@RequestParam("subCriteriaId") long subCriteriaId,
				@RequestParam("rubricScore") long rubricScore,
				HttpServletRequest request, HttpSession session, Model model) {

			try {
				UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				String rubricDesc = iolReportCardService.getRubricDesc(criteriaId,
						subCriteriaId, rubricScore, userRegistration.getRegId());
				model.addAttribute("rubricDesc", rubricDesc);

			} catch (Exception e) {
				logger.error("Error in getRubricDesc() of IndicatorOfLearningReportController"
						+ e);
			}
			return new MappingJackson2JsonView();
		}
	
 
	@RequestMapping(value = "/getLearningIndicatorValuesByCriteria", method = RequestMethod.POST)
	public ModelAndView getLearningIndicatorValuesByCriteria(@RequestParam("learningIndicatorId") long learningIndicatorId,
			//@RequestParam("csId") long csId,
			@RequestParam("studentId") long studentId,
			@RequestParam("upStatus") String upStatus,@RequestParam("trimesterId") long trimesterId,@RequestParam("teacherRegId") long teacherRegId,
			@RequestParam("gradeType") String gradeType,HttpSession session) {
		
		
		ModelAndView model = null;
		String studentName = "", gradeLevel = "";
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		 Student student = iolReportCardService.getStudent(studentId);
		List<Legend> legends = iolReportCardService.getLegends();
		List<LearningIndicatorValues> learnIndValues = null;
		List<LegendSubCriteria> legendSubCriterias = null;
		try {
			LearningIndicator learnInd=iolReportCardService.getLearningIndicator(learningIndicatorId);
			if(learnInd.getStatus()==null || learnInd.getStatus()==""){
			 learnIndValues = iolReportCardService.getStudentInCompletedIOLSection(learningIndicatorId);}
			else{
			  learnIndValues = iolReportCardService.getStudentCompletedIOLSection(learningIndicatorId);
			}
			studentName = student.getUserRegistration().getFirstName() + " "
					+ student.getUserRegistration().getLastName();
			long gradeId = student.getGrade().getGradeId();
			Grade grade = iolReportCardService.getGrade(gradeId);
			gradeLevel = grade.getMasterGrades().getGradeName();
			legendSubCriterias=iolReportCardService.getLegendSubCriteriasByCriteriaId(learnInd.getLegendCriteria().getLegendCriteriaId(),grade.getMasterGrades().getMasterGradesId());
			LegendCriteria lgCriteria=iolReportCardService.getLegendCriteria(learnInd.getLegendCriteria().getLegendCriteriaId());
			if (userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER)) {
				model = new ModelAndView("Ajax/Teacher/teacher_iol_section");
				upStatus="show";
				Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");
				model.addObject("teacher", teacherObj.getUserRegistration());
			}else{
				model = new ModelAndView("Ajax/Student/student_iol_section");
			}
			model.addObject("studentName", studentName);
			model.addObject("studentId", studentId);
			model.addObject("gradeLevel", gradeLevel);
			model.addObject("legends", legends);
			model.addObject("learnIndValues", learnIndValues);
			model.addObject("gradeId", gradeId);
			model.addObject("upStatus", upStatus);
			model.addObject("legendSubCriterias",legendSubCriterias);
			model.addObject("legendCriteriaName",lgCriteria.getLegendCriteriaName());
			model.addObject("legendCriteriaId",lgCriteria.getLegendCriteriaId());
			model.addObject("learningIndicatorId",learningIndicatorId);
			model.addObject("secStatus",learnInd.getStatus());
			model.addObject("trimesterId",trimesterId);
			model.addObject("teacherRegId",teacherRegId);
			model.addObject("gradeType",gradeType);
		} catch (DataException e) {
			logger.error("Error in getLegendCriterias() of IndicatorOfLearningReportController"
					+ e);
		}
		return model;
	}
	
	@RequestMapping(value = "/getLearningIndicatorValuesBySubcriteria", method = RequestMethod.POST)
	public ModelAndView getLearningIndicatorValuesBySubcriteria(@RequestParam("learningValuesId") long learningValuesId,
			@RequestParam("studentId") long studentId,
			@RequestParam("upStatus") String upStatus,
			@RequestParam("trimesterId") long trimesterId,
			@RequestParam("teacherRegId") long teacherRegId,
			HttpSession session) {
		ModelAndView model = null;
		String studentName = "", gradeLevel = "";
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		//String schoolImage = userReg.getSchool().getLogoLink();
		List<Legend> legends = iolReportCardService.getLegends();
		 Student student = iolReportCardService.getStudent(studentId);
		//Teacher teacher = iolReportCardService.getclassStatus(csId)
		//		.getTeacher();
		LearningIndicatorValues learnIndValues = null;
		try {
			LearningIndicatorValues learnIndValue=iolReportCardService.getLearningIndicatorValues(learningValuesId);
			if(learnIndValue.getSubmitStatus()==null || learnIndValue.getSubmitStatus()==""){
				learnIndValues = iolReportCardService.getStudentInCompletedSubCriteria(learningValuesId);
			 }
			else{
			  learnIndValues = iolReportCardService.getStudentCompletedSubCriteria(learningValuesId);
			}
			studentName = student.getUserRegistration().getFirstName() + " "
					+ student.getUserRegistration().getLastName();
			long gradeId = student.getGrade().getGradeId();
			Grade grade = iolReportCardService.getGrade(gradeId);
			gradeLevel = grade.getMasterGrades().getGradeName();
			if (userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER)) {
				model = new ModelAndView("Ajax/Teacher/teacher_iol_Litracy");
				Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");
				model.addObject("teacher", teacherObj.getUserRegistration());
			}else{
				model = new ModelAndView("Ajax/Student/student_iol_Litracy");
			}
			model.addObject("studentName", studentName);
			model.addObject("studentId", student.getStudentId());
			model.addObject("gradeLevel", gradeLevel);
			model.addObject("legends", legends);
			//model.addObject("schoolImage", schoolImage);
			model.addObject("learnIndValues", learnIndValues);
			//model.addObject("csId", csId);
			model.addObject("gradeId", gradeId);
			model.addObject("upStatus", upStatus);
			model.addObject("learnIndValues",learnIndValues);
			model.addObject("iolSectionStatus",learnIndValues.getLearningIndicator().getStatus());
		    model.addObject("learningIndicatorId",learnIndValues.getLearningIndicator().getLearningIndicatorId());
		    model.addObject("trimesterId",trimesterId);
			model.addObject("teacherRegId",teacherRegId);
		} catch (DataException e) {
			logger.error("Error in getLearningIndicatorValuesBySubcriteria() of IndicatorOfLearningReportController"
					+ e);
		}
		//model.addObject("teacher", teacher);
		return model;
	}
	
	@RequestMapping(value = "/submitStudentIOLSection", method = RequestMethod.POST)
	public View submitStudentIOLSection(@RequestParam("learningIndicatorId") long learningIndicatorId,
			HttpSession session, HttpServletResponse response, Model model) {
		try {
			float avgLegend = 0.1f;
    		long sumOfLegend=iolReportCardService.getSumOfLegends(learningIndicatorId);
			long noOfLegend=iolReportCardService.getNoOfLegends(learningIndicatorId);
			avgLegend=(float)sumOfLegend/(float)noOfLegend;
		    DecimalFormat dec = new DecimalFormat("##.#");
			@SuppressWarnings("unused")
			boolean status = iolReportCardService.submitStudentIOLSectionToTeacher(learningIndicatorId,dec.format(avgLegend));
            model.addAttribute("leScore",dec.format(avgLegend));
		} catch (Exception e) {
			logger.error("Error in submitTest() of of IndicatorOfLearningReportController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/submitStudentIOLLitracy", method = RequestMethod.POST)
	public View submitStudentIOLLitracy(@RequestParam("learningIndicatorId") long learningIndicatorId,
			HttpSession session,@RequestParam("learningValuesId") long learningValuesId,
			HttpServletResponse response, Model model) {

		try {
     	    iolReportCardService.submitStudentIOLSubCriteriaToTeacher(learningIndicatorId,learningValuesId);
        } catch (Exception e) {
			logger.error("Error in submitStudentIOLLitracy() of of IndicatorOfLearningReportController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/gradeStudentIOLSection", method = RequestMethod.POST)
	public View gradeStudentIOLSection(@RequestParam("learningIndicatorId") long learningIndicatorId,
			HttpSession session, HttpServletResponse response, Model model) {
		try {
			float avgLegend = 0.1f;
     		long sumOfLegend=iolReportCardService.getSumOfTeacherLegends(learningIndicatorId);
			long noOfLegend=iolReportCardService.getNoOfTeacherLegends(learningIndicatorId);
			avgLegend=(float)sumOfLegend/(float)noOfLegend;
		    DecimalFormat dec = new DecimalFormat("##.#");
			@SuppressWarnings("unused")
			boolean status = iolReportCardService.gradeStudentIOLSectionToTeacher(learningIndicatorId,dec.format(avgLegend));
            model.addAttribute("leScore",dec.format(avgLegend));
		} catch (Exception e) {
			logger.error("Error in gradeStudentIOLSection() of of IndicatorOfLearningReportController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/gradeStudentIOLLitracy", method = RequestMethod.POST)
	public View gradeStudentIOLLitracy(@RequestParam("learningIndicatorId") long learningIndicatorId,
			HttpSession session,@RequestParam("learningValuesId") long learningValuesId,
			HttpServletResponse response, Model model) {

		try {
     	    iolReportCardService.gradeStudentIOLSubCriteriaToTeacher(learningIndicatorId,learningValuesId);
        } catch (Exception e) {
			logger.error("Error in submitStudentIOLLitracy() of of IndicatorOfLearningReportController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/checkIOLStatus", method = RequestMethod.POST)
	public View checkIOLStatus(@RequestParam("iolReportId") long iolReportId,
			HttpSession session,HttpServletResponse response, Model model) {
            boolean iolStatus=false;
            try {
            	iolStatus= iolReportCardService.checkIOLReportStatus(iolReportId);
            	model.addAttribute("iolStatus",iolStatus);
        } catch (Exception e) {
			logger.error("Error in submitStudentIOLLitracy() of of IndicatorOfLearningReportController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/checkIOLGradeStatus", method = RequestMethod.POST)
	public View checkIOLGradeStatus(@RequestParam("iolReportId") long iolReportId,
			HttpSession session,HttpServletResponse response, Model model) {
            boolean iolStatus=false;
            try {
            	iolStatus= iolReportCardService.checkIOLGradeStatus(iolReportId);
            	model.addAttribute("iolStatus",iolStatus);
        } catch (Exception e) {
			logger.error("Error in submitStudentIOLLitracy() of of IndicatorOfLearningReportController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/createStudentIOLReportCard", method = RequestMethod.GET)
	public ModelAndView createStudentIOLReportCard(HttpSession session,
			@RequestParam("studentId") long studentId,
			@RequestParam("iolReportId") long iolReportId,
			@RequestParam("reportDate") String reportDate,
			@RequestParam("stat") String stat,
			@RequestParam("csId") long csId,
			@RequestParam("tab") String tab
			) {
		String studentName = "", gradeLevel = "";
		ModelAndView model = new ModelAndView(
				"Ajax/Teacher/create_iol_report_card");
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String schoolImage = userReg.getSchool().getLogoLink();
		List<Legend> legends = iolReportCardService.getLegends();
		model.addObject("userType", userReg.getUser().getUserType());
		try {
			List<LearningIndicatorValues> litracyLearnIndValues=null;
			List<LearningIndicator> learnInds = null;
			learnInds = iolReportCardService.getStudentCurrentIOLReport(csId, studentId,iolReportId);
			litracyLearnIndValues=iolReportCardService.getLitracyLearnIndValues(iolReportId,6);
					
			String upStatus = "show";
			List<LegendCriteria> legendCriterias = iolReportCardService.getLegendCriteria();
			Student student = iolReportCardService.getStudent(studentId);
			studentName = student.getUserRegistration().getFirstName() + " "
					+ student.getUserRegistration().getLastName();
			long gradeId = student.getGrade().getGradeId();
			Grade grade = iolReportCardService.getGrade(gradeId);
			gradeLevel = grade.getMasterGrades().getGradeName();
			model.addObject("listLegCriteria", legendCriterias);
			model.addObject("studentName", studentName);
			model.addObject("studentId", student.getStudentId());
			model.addObject("gradeLevel", gradeLevel);
			model.addObject("legends", legends);
			model.addObject("schoolImage", schoolImage);
			model.addObject("reportDate", reportDate);
			model.addObject("litracyLearnIndValues",litracyLearnIndValues);
			model.addObject("stat", stat);
			model.addObject("upStatus", upStatus);
			model.addObject("learnInds",learnInds);
			model.addObject("csId",csId);
			model.addObject("trimesterId",iolReportCardService.getTrimesterId(csId, studentId).getTrimester().getTrimesterId());
			if (userReg.getUser().getUserType()
					.equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER)) {
				Teacher teacherObj = (Teacher) session
						.getAttribute("teacherObj");
				model.addObject("teacher", teacherObj.getUserRegistration());
				model.addObject("teacherId",teacherObj.getTeacherId());
			} else {
				IOLReport iolReport = iolReportCardService.getIOLReport(iolReportId);
				 Teacher teacherObj = iolReport.getClassStatus().getTeacher();
				 model.addObject("teacher", teacherObj.getUserRegistration());
			}
			model.addObject("tab",tab);
		} catch (DataException e) {
			logger.error("Error in createStudentIOLReportCard() of IndicatorOfLearningReportController"
					+ e);
			model.addObject("helloAjax", WebKeys.LP_SYSTEM_ERROR);
		}
		return model;
	}
	
	@RequestMapping(value = "/addDefaultRubricsToGrades", method = RequestMethod.GET)
	public ModelAndView addDefaultRubricsToGrades(HttpSession session,HttpServletResponse response, Model model) {
        try {
            	iolReportCardService.addDefaultRubricsToGrades();
        } catch (Exception e) {
			logger.error("Error in addDefaultRubricsToGrades() of of IndicatorOfLearningReportController"
					+ e);
		}
		return new ModelAndView("redirect:/index.htm");
	}
	
	@RequestMapping(value = "/editIOLRubrics", method = RequestMethod.GET)
	public ModelAndView editIOLRubrics(HttpSession session) {					
		ModelAndView model = new ModelAndView("CommonJsp/edit_iol_rubrics");
		model.addObject("tab", WebKeys.LP_TAB_IOLREPORTCARD);
		model.addObject("page", WebKeys.LP_TAB_EDIT_IOLRUBRIC);
		Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");
		List<LegendCriteria> legendCriterias = new ArrayList<LegendCriteria>();
		try{
			legendCriterias = stemCurriculumSubService.getStemLegendCriteria();		
		}catch(Exception e){
    		logger.error("Error in editIOLRubrics() of UploadController"+e);
		}		
		model.addObject("legendCriterias", legendCriterias);
		model.addObject("teacherObj", teacherObj);
	    return model; 
	}
	
	@RequestMapping(value = "/getSubCriteriasByLegend", method = RequestMethod.POST)
	public View getSubCriteriasByLegend(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("lCriteriaId") long lCriteriaId, Model model) {
		try {
			List<LegendSubCriteria> legendSubCriteria = new ArrayList<LegendSubCriteria>();
			legendSubCriteria = iolReportCardService.getLegendSubCriteriasByCriteriaId(lCriteriaId,6);
			model.addAttribute("legendSubCriteria", legendSubCriteria);	
		} catch (Exception e) {
			logger.error("Error in getSubCriteriasByLegend() of IndicatorOfLearningReportController"
					+ e);
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getSubcriteriaRubrics", method = RequestMethod.POST)
	public ModelAndView getSubcriteriaRubrics(HttpSession session, @RequestParam("subId") long subId) {	
		StemUnitActList stemUnitActList = new StemUnitActList();
		List<Legend> legendList = new ArrayList<Legend>();
		ModelAndView model = new ModelAndView("Ajax/CommonJsp/edit_iol_sub_rebrics","stemUnitActList",stemUnitActList);		
		try{
			legendList = iolReportCardService.getSubcriteriaRubrics(subId);	
			stemUnitActList.setLegendList(legendList);
			model.addObject("legendList", legendList);
		}catch(Exception e){
    		logger.error("Error in getSubcriteriaRubrics() of IndicatorOfLearningReportController"+e);
		}		
		model.addObject("count", legendList.size());
	    return model; 
	}
	
	@RequestMapping(value="/updateIOLRubrics", method=RequestMethod.POST)
	public View updateIOLRubrics(@ModelAttribute("stemUnitActList") StemUnitActList stemUnitActList ,Model model,HttpServletRequest request){
	   	boolean status = false;	  
	    try {
	    	List<Legend> legendList = new ArrayList<Legend>();
	    	legendList = stemUnitActList.getLegendList();	    	
	    	status = iolReportCardService.updateIOLRubrics(legendList);
	        if(status){
	           	model.addAttribute("status", WebKeys.UPLOAD_IOL_SUCCESS);
	        } else {
	           	model.addAttribute("status", WebKeys.UPLOAD_IOL_FAILURE);
	        }
	    } catch (Exception e) {
	    	model.addAttribute("status", WebKeys.UPLOAD_IOL_FAILURE);
	    	logger.error("Error in updateIOLRubrics() of IndicatorOfLearningReportController"+e);	    		
	    }
	    
	    return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/assignIOLRubrics", method = RequestMethod.GET)
	public ModelAndView assignIOLRubrics(HttpSession session) {					
		ModelAndView model = new ModelAndView("CommonJsp/assign_iol_rubrics");
		model.addObject("tab", WebKeys.LP_TAB_IOLREPORTCARD);
		model.addObject("page", WebKeys.LP_TAB_ASSIGN_IOLRUBRIC);
		Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");
		List<Grade> teacherGrades = new ArrayList<Grade>();
		List<Trimester> trimesterList=new ArrayList<Trimester>();
		UserRegistration appadminRegis = teacherService.getAppAdminUserRegistration();
		try{
			teacherGrades = curriculumService.getTeacherGrades(teacherObj);
			trimesterList= iolReportCardService.getAllTrimesters();
			model.addObject("grList", teacherGrades);	
			model.addObject("trimesterList",trimesterList);
		}catch(Exception e){
    		logger.error("Error in assignIOLRubrics() of IndicatorOfLearningReportController"+e);
		}		
		model.addObject("adminRegId", appadminRegis.getRegId());
		model.addObject("teacherRegId", teacherObj.getUserRegistration().getRegId());
	    return model; 
	}
	
	@RequestMapping(value = "/assignIOLRubricToGrade", method = RequestMethod.POST)
	public View assignIOLRubricToGrade(@RequestParam("userRegId") long userRegId,
			HttpSession session,@RequestParam("gradeId") long gradeId,@RequestParam("trimesterId") long trimesterId,
			HttpServletResponse response, Model model) {

		try {
			boolean stat=false;
			Teacher teacherObj = (Teacher) session.getAttribute("teacherObj");
			stat=iolReportCardService.checkAlreadyAssignRubric(gradeId,trimesterId,teacherObj.getUserRegistration().getRegId());
			if(stat){
				 model.addAttribute("status", "Already Assigned");
			}else{
     	    boolean stat1=iolReportCardService.assignIOLRubricToGrade(userRegId,gradeId,teacherObj.getUserRegistration().getRegId(),trimesterId);
     	    if(stat1==true)
     	    model.addAttribute("status", "Rubric Assigned Successfully");
     	    else
     	    model.addAttribute("status", "Failure");
			}
        } catch (Exception e) {
			logger.error("Error in assignIOLRubricToGrade() of of IndicatorOfLearningReportController"
					+ e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/getLegCriterias", method = RequestMethod.POST)
	public ModelAndView getLegCriterias(HttpSession session) {			
		ModelAndView model = new ModelAndView("Ajax/Teacher/view_legend_criteria");
		try {
			List<LegendCriteria> legendCriteria = new ArrayList<LegendCriteria>();
			legendCriteria = stemCurriculumSubService.getStemLegendCriteria();
			model.addObject("legendCriteria", legendCriteria);
		} catch (Exception e) {
    		logger.error("Error in getLegCriterias() of IndicatorOfLearningReportController"+e);
		}		
	    return model; 
	}
	
	@RequestMapping(value = "/getLegSubCriterias", method = RequestMethod.POST)
	public ModelAndView getLegSubCriterias(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("lCriteriaId") long lCriteriaId) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/legend_sub_criterias");
		try {
			List<LegendSubCriteria> legendSubCriteria = new ArrayList<LegendSubCriteria>();
			legendSubCriteria = iolReportCardService.getLegendSubCriteriasByCriteriaId(lCriteriaId,6);
			model.addObject("legendSubCriteria", legendSubCriteria);
		} catch (Exception e) {
			logger.error("Error in getLegSubCriterias() of IndicatorOfLearningReportController"
					+ e);
		}
		return model;
	}
	
	@RequestMapping(value = "/getCriteriaRubrics", method = RequestMethod.POST)
	public ModelAndView getCriteriaRubrics(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("createdBy") long createdBy, @RequestParam("subCriteriaId") long subCriteriaId) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/legend_criteria_rubrics");
		try {
			List<Legend> legendrList = new ArrayList<Legend>();
			legendrList = iolReportCardService.getCriteriaRubrics(createdBy,subCriteriaId);
			model.addObject("legendrList",legendrList);		
		} catch (Exception e) {
			logger.error("Error in getCriteriaRubrics() of IndicatorOfLearningReportController"+ e);
		}
		return model;
	}
	
	@RequestMapping(value = "/teacherGoalSettingExcelDownload", method = RequestMethod.GET)
	public ModelAndView goalSettingExcelDownload(HttpSession session) {
		List<Grade> grades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Ajax2/Teacher/teacher_goal_setting_excel_download");
		model.addObject("usedFor", WebKeys.LP_USED_FOR_RTI);
		session.setAttribute("academicYrFlag", WebKeys.LP_SHOW);
		try {
			    UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				Teacher teacher = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);		
				grades = curriculumService.getTeacherGrades(teacher);
				model.addObject("teacherId", teacher.getTeacherId());
				model.addObject("teacherName", teacher.getUserRegistration().getFirstName()+" "+teacher.getUserRegistration().getLastName());
				model.addObject("tab", WebKeys.LP_TAB_IOLREPORTCARD); 
				model.addObject("page", WebKeys.LP_TAB_TEACHER_GOAL_SETTING_EXCEL_DOWNLOAD);
				model.addObject("grades", grades);
			    model.addObject("schoolId",userRegistration.getSchool().getSchoolId());
		} catch (Exception e) {
			logger.error("error while getting goal setting reports");
			model.addObject("errorMessage",	WebKeys.TEACHER_GRADES_ERROR_MESSAGE);
		}
		return model;
	}
	@RequestMapping(value = "/getSubCriteriasByCriteriaIdsss", method = RequestMethod.POST)
	public View getSubCriteriasByCriteriaId(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("legendCriteriaId") long legCriteriaId, Model model,
			@RequestParam("gradeId") long gradeId) {
		try {
			List<LegendSubCriteria> legendSubCriterias = new ArrayList<LegendSubCriteria>();
			Grade grade=iolReportCardService.getGrade(gradeId);
			legendSubCriterias=iolReportCardService.getLegendSubCriteriasByCriteriaId(legCriteriaId,grade.getMasterGrades().getMasterGradesId());
			model.addAttribute("legendSubCriterias", legendSubCriterias);	
			
		} catch (Exception e) {
			logger.error("Error in getSubCriteriasByLegend() of IndicatorOfLearningReportController"
					+ e);
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getTrimesters", method = RequestMethod.GET)
	public View getTeacherClasses(Model model, HttpSession session) throws Exception {
		model.addAttribute("trimesters",
				iolReportCardService.getAllTrimesters());
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getWholeClassLIValuesByCriteria", method = RequestMethod.POST)
	public ModelAndView getWholeClassLIValuesByCriteria(@RequestParam("criteriaId") long criteriaId,
			@RequestParam("csId") long csId,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("trimesterId") long trimesterId,
			HttpSession session) {
		ModelAndView model = null;
		String studentName = "", gradeLevel = "";
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		 //Student student = iolReportCardService.getStudent(studentId);
		List<Legend> legends = iolReportCardService.getLegends();
		List<LearningIndicatorValues> learnIndValues = null;
		List<LegendSubCriteria> legendSubCriterias = null;
		model = new ModelAndView("Ajax/Teacher/whole_class_criteria_reports");
		try {
			List<RegisterForClass> studentList = commonservice
					.getStudentsByCsId(csId);
			model.addObject("studentList", studentList);
			Map<String,List<LearningIndicatorValues>> learIndValuesList = new HashMap<String,List<LearningIndicatorValues>>();
			Map<String,Long> learningIndicatorIds= new HashMap<String,Long>(); 
			for (RegisterForClass reg : studentList) {
				String s="stud:"+reg.getStudent().getStudentId();
				learnIndValues=iolReportCardService.getStudentCriteriaReport(reg.getStudent().getStudentId(),trimesterId,criteriaId);
				learIndValuesList.put(s,learnIndValues);
				if(learnIndValues.size()>0)
				learningIndicatorIds.put(s,learnIndValues.get(0).getLearningIndicator().getLearningIndicatorId());
				else
				learningIndicatorIds.put(s,null);	
			}
			    Grade grade=iolReportCardService.getGrade(gradeId);
				legendSubCriterias=iolReportCardService.getLegendSubCriteriasByCriteriaId(criteriaId,grade.getMasterGrades().getMasterGradesId());
				model.addObject("legendSubCriterias", legendSubCriterias);	
				gradeLevel = grade.getMasterGrades().getGradeName();
			LegendCriteria lgCriteria=iolReportCardService.getLegendCriteria(criteriaId);
			model.addObject("gradeLevel", gradeLevel);
			model.addObject("legends", legends);
			model.addObject("learnIndValues", learIndValuesList);
			model.addObject("learningIndicatorIds", learningIndicatorIds);
			model.addObject("gradeId", gradeId);
			model.addObject("legendSubCriterias",legendSubCriterias);
			model.addObject("legendCriteriaName",lgCriteria.getLegendCriteriaName());
			model.addObject("legendCriteriaId",criteriaId);
			model.addObject("trimesterId",trimesterId);
			model.addObject("teacherRegId",userReg.getRegId());
		} catch (Exception e) {
			logger.error("Error in getWholeClassLIValuesByCriteria() of IndicatorOfLearningReportController"
					+ e);
		}
		return model;
	}
	@RequestMapping(value = "/getRubricsBySubcriteriaId", method = RequestMethod.POST)
	public ModelAndView getRubricsBySubCriteriaId(
			@RequestParam("gradeId") long gradeId,@RequestParam("subCriteriaId") long subCriteriaId,
			@RequestParam("trimesterId") long trimesterId,@RequestParam("teacherRegId") long teacherRegId,
			HttpSession session) {
		ModelAndView model = null;
		//List<LERubrics> leRubrics = new ArrayList<LERubrics>();
		List<Legend> lstLegend=new ArrayList<Legend>();
		try {
			
			long referRegId=iolReportCardService.getReferRegId(gradeId,trimesterId,teacherRegId);
			
			//leRubrics = iolReportCardService.getRubricValuesByGradeId(gradeId,subCriteriaId);
			lstLegend=iolReportCardService.getRubricValuesByUserId(referRegId,subCriteriaId,gradeId);
			LegendSubCriteria subCriteria = iolReportCardService.getLegendSubCriteria(subCriteriaId);

			model = new ModelAndView("Ajax/Student/show_le_rubrics");
			model.addObject("leRubrics", lstLegend);
			model.addObject("subCriteria",subCriteria.getlegendSubCriteriaName());
			model.addObject("editStatus","disabled");
			
		} catch (DataException e) {
			logger.error("Error in getRubricsBySubCriteriaId() of IndicatorOfLearningReportController"
					+ e);
		}

		return model;
	}
}