package com.lp.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.appadmin.service.SchoolAdminService;
import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.common.service.CommonService;
import com.lp.common.service.CurriculumService;
import com.lp.common.service.FileService;
import com.lp.common.service.PerformanceTaskService;
import com.lp.custom.exception.DataException;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.AssignedPtasks;
import com.lp.model.Assignment;
import com.lp.model.FilesLP;
import com.lp.model.Grade;
import com.lp.model.GroupPerformanceTemp;
import com.lp.model.PerformanceGroupStudents;
import com.lp.model.PerformancetaskGroups;
import com.lp.model.PtaskFiles;
import com.lp.model.QuestionsList;
import com.lp.model.RegisterForClass;
import com.lp.model.Rubric;
import com.lp.model.RubricValues;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentPtaskEvidence;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.student.service.TakeAssessmentsService;
import com.lp.teacher.service.AssignAssessmentsService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;
/**
 * 
 * @author PRASAD BHVN & SANTHOSH
 *
 */
@Controller
public class PerformanceTaskController extends WebApplicationObjectSupport{
	
	@Autowired
	private AdminService adminservice;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private PerformanceTaskService performanceTaskService;
	@Autowired
	private CommonService commonservice;
	@Autowired
	private TakeAssessmentsService takeAssessmentsService;
	@Autowired
	private SchoolAdminService schoolAdminService;	
	@Autowired
	private FileService fileservice;
	@Autowired
	private	HttpSession session;
	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;
	@Autowired
	private AssignAssessmentsService assignAssessmentsService;
	@Autowired
	AndroidPushNotificationsService apns;
	
	
	static final Logger logger = Logger.getLogger(PerformanceTaskController.class);
	
	//Show Performance Group
	@RequestMapping(value = "/groupPerformance", method = RequestMethod.GET)
	public ModelAndView groupPerformance(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Teacher/create_group_performance", "performancetaskGroups", new PerformancetaskGroups());
		model.addObject("tab", WebKeys.LP_TAB_GROUP_PERFORMANCE);
		model.addObject("subTab", WebKeys.LP_SUB_TAB_CREATE_PERFORMANCE_GROUP);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		try{
			if(teacherObj == null){
				//admin
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			}else{
				//teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
		}catch(DataException e){
			logger.error("Error in groupPerformance() of PerformanceTaskController"
					+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}

		return  model;
	}

	//Create Performance Group
	@RequestMapping(value = "/createGroupPerformance", method = RequestMethod.POST)
	public @ResponseBody void createGroupPerformance(HttpServletRequest request,HttpServletResponse response,HttpSession session,@ModelAttribute("performancetaskGroups") PerformancetaskGroups performancetaskGroups,
			BindingResult result) throws Exception { 

		long csId;
		boolean status = false;
		if(request != null && session != null){
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			try{
				if(userReg != null){
					try{
						csId = new Long(request.getParameter("csId"));
						//Create Performance Group
						status = performanceTaskService.createPerformanceGroup(csId, performancetaskGroups);

						if(status){
							response.getWriter().write(WebKeys.LP_CREATE_PERFORMANCE_GROUP_SUCCESS); 
						}else{
							response.getWriter().write(WebKeys.LP_CREATE_PERFORMANCE_GROUP_FAILED); 
						}
					}catch(DataException e){
						logger.error("Error in createGroupPerformance() of of PerformanceTaskController"+ e);
						e.printStackTrace();
						response.getWriter().write(WebKeys.LP_CREATE_PERFORMANCE_GROUP_ERROR); 
					}
				}

			}catch(DataException e){
				logger.error("Error in createGroupPerformance() of PerformanceTaskController"
						+ e);
				e.printStackTrace();
			}
		}
	}
	
	//groupPerformanceAssign
	@RequestMapping(value = "/groupPerformanceAssign", method = RequestMethod.GET)
	public ModelAndView groupPerformanceAssign(HttpSession session) {
		
		Assignment assignment = new Assignment();
		assignment.setUsedFor(WebKeys.LP_USED_FOR_ASSESSMENT);
		ModelAndView model = new ModelAndView("Teacher/assign_assessments_main", "assignment", assignment);
		model.addObject("tab", WebKeys.LP_TAB_GROUP_PERFORMANCE);
		model.addObject("subTab", WebKeys.LP_SUB_TAB_ASSIGNING_PERFORMANCE_GROUP);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("isGroup",true);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		
		try{
			model.addObject("teacherGrades",
					curriculumService.getTeacherGrades(teacherObj));
			
		}catch(DataException e){
			logger.error("Error in groupPerformanceAssign() of PerformanceTaskController"
					+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		if(session.getAttribute("helloAjax") != null){
			model.addObject("helloAjax", session.getAttribute("helloAjax").toString());
			session.removeAttribute("helloAjax");
		}
		if(session.getAttribute("isError") != null){
			model.addObject("isError", session.getAttribute("isError").toString());
			session.removeAttribute("isError");
		}
		return  model;
	}		
	
	//Split Students To Group
	@RequestMapping(value = "/splitStudentsToGroup", method = RequestMethod.GET)
	public ModelAndView splitStudentsToGroup(HttpSession session) {
		List<Grade> teacherGrades = new ArrayList<Grade>();
		ModelAndView model = new ModelAndView("Teacher/split_student_group", "performancetaskGroups", new PerformancetaskGroups());
		model.addObject("tab", WebKeys.LP_TAB_GROUP_PERFORMANCE);
		model.addObject("subTab", WebKeys.LP_SUB_TAB_SPLITTING_STUDENTS);
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);		
		try{
			if(teacherObj == null){
				//admin
				UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg.getSchool().getSchoolId());
				model.addObject("grList", schoolgrades);
			}else{
				//teacher
				teacherGrades = curriculumService.getTeacherGrades(teacherObj);
				model.addObject("grList", teacherGrades);
			}
		}catch(DataException e){
			logger.error("Error in splitStudentsToGroup() of PerformanceTaskController"
					+ e);
			e.printStackTrace();
			model.addObject("helloAjax",WebKeys.LP_SYSTEM_ERROR);
		}
		return  model;
	}
	
	@RequestMapping(value = "/getStudents", method = RequestMethod.GET)
	public ModelAndView getStudents(@RequestParam("csId") long csId,@RequestParam("perGroupId") long perGroupId,@ModelAttribute("questionsList") QuestionsList questionsList) {
		
		ModelAndView model = new ModelAndView("Ajax/Teacher/split_student_list");
		List<RegisterForClass> studentList = commonservice.getStudentsByCsId(csId);
		PerformancetaskGroups perGroup = performanceTaskService.getPerformanceGroupById(perGroupId);
		model.addObject("studentList",studentList);
		model.addObject("perGroup",perGroup);
		return  model;
	}
	
	@RequestMapping(value = "/addStudentToPerformance", method = RequestMethod.GET)
	public View addStudentToPerformance(@RequestParam("csId") long csId,@RequestParam("studentId") String studentIds,
			@RequestParam("perGroupId") long perGroupId, Model model) throws DataException{
		try{
			boolean status =  performanceTaskService.addStudentPerformanceGroup(studentIds, perGroupId, csId);
			model.addAttribute("status",status);
			if(status){
				model.addAttribute("helloAjax",WebKeys.LP_ADD_STUDENT_SUCCESS);
			}else{
				model.addAttribute("helloAjax",WebKeys.LP_ADD_STUDENT_FAILED);
			}
		}catch(DataException e){
			logger.error("Error in addStudentToPerformance() of PerformanceTaskController"	+ e);
			e.printStackTrace();
			model.addAttribute("helloAjax",WebKeys.LP_ADD_STUDENT_ERROR);
		}
		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/removeStudentPerformance", method = RequestMethod.GET)
	public View removeStudentPerformance(@RequestParam("csId") long csId,@RequestParam("studentId") String studentId,
			@RequestParam("perGroupId") long perGroupId, Model model) 
			throws DataException{
		try{
			boolean status =  performanceTaskService.removeStudentPerformanceGroup(studentId, perGroupId, csId);
			model.addAttribute("status",status);
			if(status){
				model.addAttribute("helloAjax",WebKeys.LP_REMOVE_STUDENT_SUCCESS);
			}else{
				model.addAttribute("helloAjax",WebKeys.LP_REMOVE_STUDENT_FAILED);
			}
		}catch(DataException e){
			logger.error("Error in addStudentToPerformance() of PerformanceTaskController"	+ e);
			e.printStackTrace();
			model.addAttribute("helloAjax",WebKeys.LP_REMOVE_STUDENT_ERROR);
		}
		
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/addOrRemoveStudentToPerformance", method = RequestMethod.GET)
	public View addOrRemoveStudentToPerformance(@RequestParam("csId") long csId,@RequestParam("studentId") String studentIds,
			@RequestParam("perGroupId") long perGroupId,@RequestParam("checkStatus") boolean checkStatus, Model model) throws DataException{
		try{
			boolean status;
			if(checkStatus){
			 status =  performanceTaskService.addStudentPerformanceGroup(studentIds, perGroupId, csId);
			}else{
			 status =  performanceTaskService.removeStudentPerformanceGroup(studentIds, perGroupId, csId);	
			}
			model.addAttribute("status",status);
			if(status){
				model.addAttribute("helloAjax",WebKeys.LP_ADD_STUDENT_SUCCESS);
			}else{
				model.addAttribute("helloAjax",WebKeys.LP_ADD_STUDENT_FAILED);
			}
		}catch(DataException e){
			logger.error("Error in addOrRemoveStudentToPerformance() of PerformanceTaskController"	+ e);
			e.printStackTrace();
			model.addAttribute("helloAjax",WebKeys.LP_ADD_STUDENT_ERROR);
		}
		
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/getPerformanceTest", method = RequestMethod.GET)
	public ModelAndView getTestQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("createdBy") long createdBy,  HttpServletRequest request) {
		AssignedPtasks assignedPtasks = new AssignedPtasks();
		assignedPtasks = performanceTaskService.getAssignedPerformance(studentAssignmentId);
		
		List<RubricValues> rubricValues = new ArrayList<RubricValues>();
		rubricValues = performanceTaskService.getRubricValuesByAssignedTaskId(assignedPtasks.getAssignedTaskId());
		if(rubricValues.size()>0){
			assignedPtasks.setRubricValues(rubricValues.get(0));
		}else{
			assignedPtasks.setRubricValues(new RubricValues());
		}
		List<StudentPtaskEvidence> studentEvidences = new ArrayList<StudentPtaskEvidence>();
		studentEvidences = performanceTaskService.getStudentEvidencesByAssignedTaskId(assignedPtasks.getAssignedTaskId());
		assignedPtasks.setStudentPtaskEvidence(studentEvidences);
		ModelAndView model = null;
		model = new ModelAndView("Ajax/Student/performance_test","assignedPtasks", assignedPtasks);		
		model.addObject("createdBy", createdBy);		
		model = perTestModel(assignedPtasks, request, model);
		
		return model;
	}
			
	@RequestMapping(value = "/submitPerformanceTest", method = RequestMethod.POST)
	public String submitPerformanceTest1(@ModelAttribute("assignedPtasks") AssignedPtasks assignedPtasks,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session,RedirectAttributes redirectAttributes) {
		boolean status = false;
		long pGroupStudentId = 0;
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		if(request.getParameter("pGroupStudentId") != null){
			pGroupStudentId = Long.parseLong(request.getParameter("pGroupStudentId"));
		}
		ModelAndView model = null;		
		if(pGroupStudentId >0){			
			model = new ModelAndView("Student/group_assessments","studentAssignmentStatus", new StudentAssignmentStatus());
			model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
			model.addObject("tab", WebKeys.LP_TAB_VIEW_GROUP_PERFORMANCE);
		}else{
			model = new ModelAndView("Student/take_assessments",
					"studentAssignmentStatus", new StudentAssignmentStatus());
			model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
			model.addObject("tab", WebKeys.LP_TAB_VIEW_ASSESSMENTS);
		}
		
		long studentAssignmentId = assignedPtasks.getStudentAssignmentStatus().getStudentAssignmentId();
		StudentAssignmentStatus studentAssignment = new StudentAssignmentStatus();
		studentAssignment = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
		try{
			if(!studentAssignment.getStatus().equalsIgnoreCase(WebKeys.TEST_STATUS_SUBMITTED)){
				status = performanceTaskService.submitPerformanceTest(assignedPtasks);
			}	
			if(status){
				redirectAttributes.addFlashAttribute("helloAjax", WebKeys.TEST_SUBMITTED_SUCCESS);
			}else{
				redirectAttributes.addFlashAttribute("helloAjax", WebKeys.TEST_SUBMITTED_FAILURE);
			}
			if(pGroupStudentId >0){
				PerformanceGroupStudents pGroupStudent = new PerformanceGroupStudents();
				pGroupStudent = performanceTaskService.getPerformanceGroupByStudentId(student.getStudentId());
				List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
				if(pGroupStudent.getPerformanceGroupStudentsId() != null){
					studentAssignments = performanceTaskService.getGroupTests(pGroupStudent.getPerformancetaskGroups().getPerformanceGroupId(),
							WebKeys.LP_USED_FOR_ASSESSMENT, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED);
				}				
				model.addObject("studentTests", studentAssignments);
			}else{
				model.addObject("studentTests", takeAssessmentsService.getStudentTests(
						student, WebKeys.LP_USED_FOR_ASSESSMENT,WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED));
			}
			
			
		} catch (Exception e) {
			logger.error("Error in submitPerformanceTest() of of TakeAssessmentsController"	+ e);
			redirectAttributes.addFlashAttribute("helloAjax", WebKeys.TEST_SUBMITTED_ERROR);
		}
		if(pGroupStudentId >0){	
			return "redirect:perGroupAssessmentsPage.htm";
		}else{
			return "redirect:goToStudentAssessmentsPage.htm";
		}
	}
		
	@RequestMapping(value = "/autoSave", method = RequestMethod.POST)
	public View autoSave(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {
		
		AssignedPtasks assignedPtasks = new AssignedPtasks();
		GroupPerformanceTemp gpTemp = new GroupPerformanceTemp();
		PerformanceGroupStudents pgStudent = new PerformanceGroupStudents();
		String id = request.getParameter("id");
		String data = request.getParameter("data");
		String taskId = request.getParameter("taskId");
		String chat = request.getParameter("chat");
		
		long perGroupStudentId = Long.parseLong(request.getParameter("perGroupStudentId"));
		pgStudent.setPerformanceGroupStudentsId(perGroupStudentId);		
		
		assignedPtasks.setAssignedTaskId(Long.parseLong(taskId));
		if(id.equalsIgnoreCase(WebKeys.LP_WRITING)){
			assignedPtasks.setWriting(data);
			gpTemp.setWritingAreaStatus("unlocked");
		}else if(id.equalsIgnoreCase(WebKeys.LP_CALCULATIONS)){
			assignedPtasks.setCalculations(data);
			gpTemp.setCalculationAreaStatus("unlocked");
		}else{
			assignedPtasks.setUploadarea(data);
			gpTemp.setImageAreaStatus("unlocked");
		}
		gpTemp.setAssignedPtasks(assignedPtasks);
		gpTemp.setPerformanceGroupStudents(pgStudent);
		if(chat.equalsIgnoreCase("logout")){
			gpTemp.setChatLoginStatus("logout");
		}

		@SuppressWarnings("unused")
		boolean status = performanceTaskService.autoSaveAssignedTasks(gpTemp);
		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/autoRubricSave", method = RequestMethod.POST)
	public View autoRubricSave(@RequestParam("idName") String idName,
			@RequestParam("score") long score, @RequestParam("totalScore") long totalScore, @RequestParam("taskId") long taskId,
			@RequestParam("rubricValId") long rubricValId, @RequestParam("pGroupStudentId") long pGroupStudentId, 
			HttpSession session, Model model) throws DataException{
		
		boolean status = false;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		AssignedPtasks assignedPtasks = new AssignedPtasks();		
		assignedPtasks.setAssignedTaskId(taskId);
		
		GroupPerformanceTemp gpt = new GroupPerformanceTemp();
		PerformanceGroupStudents performanceGroupStudents = new PerformanceGroupStudents();
		performanceGroupStudents.setPerformanceGroupStudentsId(pGroupStudentId);
		gpt.setAssignedPtasks(assignedPtasks);
		gpt.setPerformanceGroupStudents(performanceGroupStudents);
		gpt.setTotal(totalScore);
		
		RubricValues rubricValues = new RubricValues();
		rubricValues.setRubricValuesId(rubricValId);
		rubricValues.setTotalScore((int)totalScore);
		rubricValues.setAssignedPtasks(assignedPtasks);
		rubricValues.setUser(userReg.getUser());
				
		if(idName.equalsIgnoreCase(WebKeys.LP_DIMENTION_ONE)){
			rubricValues.setDimension1Value(score);
			gpt.setDim1Value(score);
		}else if(idName.equalsIgnoreCase(WebKeys.LP_DIMENTION_TWO)){
			rubricValues.setDimension2Value(score);
			gpt.setDim2Value(score);
		}else if(idName.equalsIgnoreCase(WebKeys.LP_DIMENTION_THREE)){
			rubricValues.setDimension3Value(score);
			gpt.setDim3Value(score);
		}else{
			rubricValues.setDimension4Value(score);
			gpt.setDim4Value(score);
		}
		if(pGroupStudentId>0){
			status = performanceTaskService.autoGroupRubricSave(gpt);
			model.addAttribute("rubricValueId",0);
		}else{
			status = performanceTaskService.autoRubricSave(rubricValues);
			model.addAttribute("rubricValueId",rubricValues.getRubricValuesId());
		}
			
		
		model.addAttribute("status",status);
		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/fileAutoSave", method = RequestMethod.POST)
	public View fileAutoSave(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpSession session) {
		try{
			AssignedPtasks assignedPtasks = new AssignedPtasks();
			long assignedPtasksId = Long.parseLong(request.getParameter("assignedFileTaskId"));
			long createdBy = Long.parseLong(request.getParameter("createdBy"));
			assignedPtasks.setAssignedTaskId(assignedPtasksId);
			assignedPtasks.setFilepath(file.getOriginalFilename());
			performanceTaskService.fileAutoSave(file,assignedPtasks,createdBy);
		}catch(Exception e){
			logger.error("Error in fileAutoSave() of PerformanceTaskController"	+ e);
		}		
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/audioSave", method = RequestMethod.POST)
	public View audioSave(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {
		
		AssignedPtasks assignedPtasks = new AssignedPtasks();
		String data = request.getParameter("data");
		long assignedPtasksId = Long.parseLong(request.getParameter("assignedFileTaskId"));
		long createdBy = Long.parseLong(request.getParameter("createdBy"));
		assignedPtasks.setAssignedTaskId(assignedPtasksId);
		assignedPtasks.setAudiofile(WebKeys.AUDIO_FILE_NAME);
		performanceTaskService.audioAutoSave(data,assignedPtasks,createdBy);
		
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/perGroupAssessmentsPage", method = RequestMethod.GET)
	public ModelAndView perGroupAssessmentsPage(HttpSession session) {
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		PerformanceGroupStudents pGroupStudent = new PerformanceGroupStudents();
		pGroupStudent = performanceTaskService.getPerformanceGroupByStudentId(student.getStudentId());
		List<StudentAssignmentStatus> studentAssignments = new ArrayList<StudentAssignmentStatus>();
		if(pGroupStudent.getPerformanceGroupStudentsId() != null){
			studentAssignments = performanceTaskService.getGroupTests(pGroupStudent.getPerformancetaskGroups().getPerformanceGroupId(),
					WebKeys.LP_USED_FOR_ASSESSMENT, WebKeys.TEST_STATUS_PENDING, WebKeys.GRADED_STATUS_NOTGRADED);
		}
		ModelAndView model = new ModelAndView("Student/group_assessments","studentAssignmentStatus", new StudentAssignmentStatus());
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("tab", WebKeys.LP_TAB_VIEW_GROUP_PERFORMANCE);
		model.addObject("studentTests", studentAssignments);
		
		return model;
	}
	
	@RequestMapping(value = "/getGroupPerformanceTest", method = RequestMethod.GET)
	public ModelAndView getGroupPerformanceTest(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("createdBy") long createdBy,  HttpServletRequest request, HttpSession session) {
		
		Student student = (Student) session.getAttribute(WebKeys.STUDENT_OBJECT);
		AssignedPtasks assignedPtasks = new AssignedPtasks();
		assignedPtasks = performanceTaskService.getAssignedPerformance(studentAssignmentId);
		ModelAndView model = null;
		List<StudentPtaskEvidence> studentEvidences = new ArrayList<StudentPtaskEvidence>();
		studentEvidences = performanceTaskService.getStudentEvidencesByAssignedTaskId(assignedPtasks.getAssignedTaskId());
		assignedPtasks.setStudentPtaskEvidence(studentEvidences);
		
		if(assignedPtasks.getAssignedTaskId() == 0){
			model = new ModelAndView("Ajax/Student/group_performance_test","assignedPtasks", assignedPtasks);			
			model.addObject("submitted", true);
			return model;
		}
		
		PerformanceGroupStudents perGroupStudent = new PerformanceGroupStudents();
		perGroupStudent = performanceTaskService.getPerformanceGroupByStudentId(student.getStudentId());
		
		List<GroupPerformanceTemp> gPerTemp = new ArrayList<GroupPerformanceTemp>();
		gPerTemp = performanceTaskService.getGroupPerformanceTemp(assignedPtasks.getAssignedTaskId());		
			
		
		model = new ModelAndView("Ajax/Student/group_performance_test","assignedPtasks", assignedPtasks);			
		model.addObject("createdBy", createdBy);
		model.addObject("gPerTempList", gPerTemp);
		model.addObject("groupStudentId", perGroupStudent.getPerformanceGroupStudentsId());
		model.addObject("pGroupId", perGroupStudent.getPerformancetaskGroups().getPerformanceGroupId());
		model = perTestModel(assignedPtasks, request, model);
		
		
		return model;
	}
	
	private ModelAndView perTestModel(AssignedPtasks assignedPtasks, HttpServletRequest request, ModelAndView model){
		List<Rubric> rubrics = new ArrayList<Rubric>(); 
		List<PtaskFiles> pTaskFiles = new ArrayList<PtaskFiles>();
		File[] pFiles = null;
		File[] aFiles = null;
		File[] audioFiles = null;
		String dirLocation = "";
		List<FilesLP> filesLP = new ArrayList<>();
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		if(assignedPtasks.getAssignedTaskId()!=0){
			rubrics = performanceTaskService.getRubrics(assignedPtasks.getPerformanceTask().getQuestionId());			
			pTaskFiles = performanceTaskService.getPtaskFiles(assignedPtasks.getPerformanceTask().getQuestionId());
			assignedPtasks.getPerformanceTask().setRubrics(rubrics);
			if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_PARENT) ||
					userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_STUDENT_ABOVE_13) ||
						userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_STUDENT_BELOW_13)){
				userReg = schoolAdminService.getUserRegistration(assignedPtasks.getStudentAssignmentStatus().getAssignment().getCreatedBy());
			}
		}	
		if(pTaskFiles.size()>0){
			PtaskFiles ptf = new PtaskFiles(); 
			ptf = pTaskFiles.get(0);
			UserRegistration userReg1 =schoolAdminService.getUserRegistration(ptf.getCreatedBy());
			dirLocation = FileUploadUtil.getUploadFilesPath(userReg1, request);
			String fullPerformancePath = dirLocation+File.separator+WebKeys.PERFORMANCE_TESTS+File.separator+ptf.getQuestions().getQuestionId();
			pFiles = fileservice.getFolderFiles(fullPerformancePath);
			if(pFiles != null){
				for(File pf : pFiles){
					FilesLP fl = new FilesLP();
					fl.setFileName(pf.getName());
					fl.setFilePath(pf.getPath());
					filesLP.add(fl);
				}
			}
		}		
		
		if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER)){
			dirLocation = FileUploadUtil.getUploadFilesPath(userReg, request);
			String fullAdditionalPath = dirLocation+File.separator+WebKeys.ASSIGNMENTS+File.separator+WebKeys.PERFORMANCE_TESTS
					+File.separator+assignedPtasks.getAssignedTaskId();
			aFiles = fileservice.getFolderFiles(fullAdditionalPath+File.separator+WebKeys.ADDITIONAL_MEDIA);
			if(aFiles != null){
				FilesLP fl = new FilesLP();
				fl.setFileName(aFiles[0].getName());
				fl.setFilePath(aFiles[0].getPath());
				model.addObject("fileLP", fl);
			}
			audioFiles = fileservice.getFolderFiles(fullAdditionalPath+File.separator+WebKeys.AUDIO);
			if(audioFiles != null){
				FilesLP fl = new FilesLP();
				fl.setFileName(audioFiles[0].getName());
				fl.setFilePath(audioFiles[0].getPath());
				model.addObject("audioFiles", fl);
			}
		}	
		
		model.addObject("assignedPtasks", assignedPtasks);
		model.addObject("rubrics", rubrics);
		model.addObject("filesLP", filesLP);
		return model;
	}
	
	@RequestMapping(value = "/checkEditorAvailability", method = RequestMethod.POST)
	public View checkEditorAvailability(@RequestParam("idName") String idName,@RequestParam("taskId") long taskId,
			@RequestParam("pGroupStudentId") long pGroupStudentId, HttpServletRequest request,
			HttpSession session, Model model) {
		
		boolean status = false;	
		boolean isUnlocked = false;
		List<GroupPerformanceTemp> gPerTemp = new ArrayList<GroupPerformanceTemp>();
		gPerTemp = performanceTaskService.getGroupPerformanceTemp(taskId);
		GroupPerformanceTemp gpTemp = new GroupPerformanceTemp();
		
		if(idName.equalsIgnoreCase(WebKeys.LP_WRITING)){
			for(GroupPerformanceTemp gpt : gPerTemp){
				if(gpt.getWritingAreaStatus()!=null && !gpt.getWritingAreaStatus().equalsIgnoreCase(WebKeys.LP_UNLOCKED) 
						&& gpt.getPerformanceGroupStudents().getPerformanceGroupStudentsId() != pGroupStudentId){
					status = true;
					break;
				}
				if(gpt.getPerformanceGroupStudents().getPerformanceGroupStudentsId() == pGroupStudentId 
						&& gpt.getWritingAreaStatus().equalsIgnoreCase(WebKeys.LP_UNLOCKED)){
					gpTemp = gpt;
					gpTemp.setWritingAreaStatus(WebKeys.LP_LOCKED);
					gpTemp.setCalculationAreaStatus(WebKeys.LP_UNLOCKED);
					gpTemp.setImageAreaStatus(WebKeys.LP_UNLOCKED);
					model.addAttribute("content",gpt.getAssignedPtasks().getWriting());
					isUnlocked = true;
				}
			}			
		}else if(idName.equalsIgnoreCase(WebKeys.LP_CALCULATIONS)){
			for(GroupPerformanceTemp gpt : gPerTemp){
				if(gpt.getCalculationAreaStatus()!=null && !gpt.getCalculationAreaStatus().equalsIgnoreCase(WebKeys.LP_UNLOCKED) 
						&& gpt.getPerformanceGroupStudents().getPerformanceGroupStudentsId() != pGroupStudentId){
					status = true;
				}
				if(gpt.getPerformanceGroupStudents().getPerformanceGroupStudentsId() == pGroupStudentId 
						&& gpt.getCalculationAreaStatus().equalsIgnoreCase(WebKeys.LP_UNLOCKED) && status != true){
					gpTemp = gpt;
					gpTemp.setCalculationAreaStatus(WebKeys.LP_LOCKED);
					gpTemp.setImageAreaStatus(WebKeys.LP_UNLOCKED);
					gpTemp.setWritingAreaStatus(WebKeys.LP_UNLOCKED);
					model.addAttribute("content",gpt.getAssignedPtasks().getCalculations());
					isUnlocked = true;
				}
			}			
		}else{
			for(GroupPerformanceTemp gpt : gPerTemp){
				if(gpt.getImageAreaStatus()!=null && !gpt.getImageAreaStatus().equalsIgnoreCase(WebKeys.LP_UNLOCKED) 
						&& gpt.getPerformanceGroupStudents().getPerformanceGroupStudentsId() != pGroupStudentId){
					status = true;
				}
				if(gpt.getPerformanceGroupStudents().getPerformanceGroupStudentsId() == pGroupStudentId 
						&& gpt.getImageAreaStatus().equalsIgnoreCase(WebKeys.LP_UNLOCKED) && status != true){
					gpTemp = gpt;
					gpTemp.setImageAreaStatus(WebKeys.LP_LOCKED);
					gpTemp.setWritingAreaStatus(WebKeys.LP_UNLOCKED);
					gpTemp.setCalculationAreaStatus(WebKeys.LP_UNLOCKED);
					model.addAttribute("content",gpt.getAssignedPtasks().getUploadarea());
					isUnlocked = true;
				}
			}
		}	
		try{
			if(isUnlocked){
				performanceTaskService.updateAreaStatus(gpTemp);
			}
		}catch(Exception e){
			logger.error("Error in checkEditorAvailability() of PerformanceTaskController"	+ e);
		}
				
		model.addAttribute("status",status);
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/gradePerformanceTest", method = RequestMethod.GET)
	public ModelAndView gradePerformanceTest(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentTypeId") long assignmentTypeId, @RequestParam("tab") String tab, 
			HttpServletRequest request) {
		AssignedPtasks assignedPtasks = new AssignedPtasks();
		ModelAndView model = new ModelAndView("Ajax/Teacher/grade_performance_assignment","assignedPtasks", assignedPtasks);			
		try{
			assignedPtasks = performanceTaskService.getCompletedPerformanceTask(studentAssignmentId);	
			StudentAssignmentStatus studAss=performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
			List<RubricValues> rubricValues = new ArrayList<RubricValues>();
			rubricValues = performanceTaskService.getRubricValuesByAssignedTaskId(assignedPtasks.getAssignedTaskId());
			assignedPtasks.setRubricValues(new RubricValues());
			for(RubricValues rv: rubricValues){
				if(rv.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER)){
					assignedPtasks.setRubricValues(rv);
				}else{
					model.addObject("studentRubric", rv);		
				}
			}
			List<StudentPtaskEvidence> studentEvidences = new ArrayList<StudentPtaskEvidence>();
			studentEvidences = performanceTaskService.getStudentEvidencesByAssignedTaskId(assignedPtasks.getAssignedTaskId());
			assignedPtasks.setStudentPtaskEvidence(studentEvidences);
			model = perTestModel(assignedPtasks, request, model);
			model.addObject("tab", tab);
			model.addObject("assignmentId",studAss.getAssignment().getAssignmentId());
		}catch(DataException e){
			logger.error("Error in gradePerformanceTest() of PerformanceTaskController"	+ e);
		}
		
		return model;
	}
	
	@RequestMapping(value = "/submitGradePerformance", method = RequestMethod.POST)
	public ModelAndView submitGradePerformance(@ModelAttribute("assignedPtasks") AssignedPtasks assignedPtasks,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		boolean status = false;
		ModelAndView model = new ModelAndView("Teacher/teacher_grade_assessments", "assignment", assignment);
		
		try{
			status = performanceTaskService.submitGradePerformance(assignedPtasks);
		}catch(DataException e){
			logger.error("Error in submitGradePerformance() of PerformanceTaskController"	+ e);
			model.addObject("helloAjax", WebKeys.TEST_GRADED_FAILURE);
		}	
		model.addObject("teacherGrades", curriculumService.getTeacherGrades(teacherObj));
		String tab = request.getParameter(WebKeys.LP_TAB);
		model.addObject("tab", tab);
		model.addObject("usedFor",WebKeys.LP_USED_FOR_ASSESSMENT);
		if(status == true){
			model.addObject("helloAjax", WebKeys.TEST_GRADED_SUCCESS);	
		}else{
			model.addObject("helloAjax", WebKeys.TEST_GRADED_FAILURE);
		}
		return model;
	}
	
	@RequestMapping(value = "/groupGradeAssessments", method = RequestMethod.GET)
	public ModelAndView groupGradeAssessments(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		ModelAndView model = new ModelAndView("Teacher/teacher_grade_assessments", "assignment", assignment);
		List<Grade> grades = new ArrayList<Grade>();
		try{
			grades = curriculumService.getTeacherGrades(teacherObj);
			model.addObject("teacherGrades",grades);
		}catch(DataException e){
			logger.error("Error in groupGradeAssessments() of PerformanceTaskController"	+ e);
		}			
		model.addObject("usedFor", WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("tab", WebKeys.LP_TAB_GROUP_PERFORMANCE);
		model.addObject("subTab", WebKeys.LP_SUB_TAB_GRADE_PERFORMANCE_GROUP);
		model.addObject("usedFor",WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("LP_STEM_TAB",WebKeys.LP_STEM_TAB);
		return model;
	}
	
	@RequestMapping(value = "/getGroupAssessmentTests", method = RequestMethod.GET)
	public ModelAndView getGroupAssessmentTests(HttpSession session,@RequestParam("assignmentId") long assignmentId,
			HttpServletResponse response, HttpServletRequest request) {
	
		ModelAndView model = new ModelAndView("Ajax/Teacher/show_completed_assessment_tests", "assignment",
				new StudentAssignmentStatus());
		try{
			List<StudentAssignmentStatus> assessmentCompletedList = performanceTaskService.getGroupAssessmentTests(assignmentId);
			model.addObject("studentAssessmentTestList", assessmentCompletedList);
		}catch(DataException e){
			logger.error("Error in getGroupAssessmentTests() of PerformanceTaskController"	+ e);
		}	
		
		model.addObject("tab", WebKeys.LP_TAB_GRADE_GROUP);
		return model;	
	}
	
	@RequestMapping(value = "/playAudio", method = RequestMethod.GET)
	public void playAudio(@RequestParam("filePath") String filePath,
			HttpServletRequest request,HttpServletResponse response){
		String path  = filePath;
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
					//response.getWriter().write("");
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
	
	}
	
	@RequestMapping(value = "/tinynceFileSave", method = RequestMethod.POST)
	public View tinynceFileSave(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpSession session, Model model) {
		try{
			AssignedPtasks assignedPtasks = new AssignedPtasks();
			long assignedPtasksId = Long.parseLong(request.getParameter("tinyAssignedFileTaskId"));
			long createdBy = Long.parseLong(request.getParameter("createdByTiny"));
			assignedPtasks.setAssignedTaskId(assignedPtasksId);
			assignedPtasks.setFilepath(file.getOriginalFilename());
			performanceTaskService.tinynceFileSave(file,assignedPtasks,createdBy);
			UserRegistration userReg = schoolAdminService.getUserRegistration(createdBy);
			model.addAttribute("imageURL","loadUserFile.htm?usersFilePath="+WebKeys.ASSIGNMENTS +File.separator+ WebKeys.PERFORMANCE_TESTS
					+File.separator+assignedPtasks.getAssignedTaskId() +File.separator+WebKeys.TINYMCE_IMAGES+File.separator+file.getOriginalFilename()+"&regId="+userReg.getRegId());
		}catch(Exception e){
			logger.error("Error in tinynceFileSave() of PerformanceTaskController"	+ e);
		}		
		
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/saveStudentEvidence", method = RequestMethod.POST)
	public View saveStudentEvidence(@RequestParam("assignedPtaskId") long assignedPtaskId,@RequestParam("evidenceId") long evidenceId,@RequestParam("evidence") String evidence,
			@RequestParam("ptaskGroupStudentId") long ptaskGroupStudentId,HttpServletRequest request,HttpSession session, Model model) {
		try{
			
			StudentPtaskEvidence stuEvid=new StudentPtaskEvidence();
			if(evidenceId==0)
			{
				stuEvid.setAssignedPtasks(performanceTaskService.getAssignedPtasks(assignedPtaskId));
				stuEvid.setEvidenceDesc(evidence);
				if(ptaskGroupStudentId!=0)
				stuEvid.setPerformanceGroupStudents(performanceTaskService.getPerformanceGroupStudentsById(ptaskGroupStudentId));
				evidenceId=performanceTaskService.saveStudentEvidences(stuEvid);
			}else{
				performanceTaskService.updateStudentPtaskEvidence(assignedPtaskId,evidenceId,evidence,ptaskGroupStudentId);
			
			}
			
			
		}catch(Exception e){
			logger.error("Error in saveStudentEvidence() of PerformanceTaskController"	+ e);
		}		
		model.addAttribute("evidenceId",evidenceId);
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/gradePerformanceTasks", method = RequestMethod.GET)
	public @ResponseBody
	void gradePerformanceTest(HttpSession session,
			HttpServletResponse response,
			@RequestParam("percentage") float percentage,
			@RequestParam("teacherScore") int teacherScore,@RequestParam("teacherComment") String teacherComment,
			@RequestParam("assignPTaskId") long assignPTaskId,
			Model model) {
		try {
			String helloAjax="";
			boolean check = false;
			check=performanceTaskService.gradePerformanceTasks(assignPTaskId,percentage,teacherComment,teacherScore);
			if (check){
				AssignedPtasks assignedPTask=performanceTaskDAO.getAssignedPtasks(assignPTaskId);
				StudentAssignmentStatus stAs=assignAssessmentsService.getStudentAssignmentStatus(assignedPTask.getStudentAssignmentStatus().getStudentAssignmentId());
				apns.sendStudentResultsNotification(stAs.getAssignment().getUsedFor(),stAs.getStudent().getStudentId());
				helloAjax =WebKeys.TEST_GRADED_SUCCESS;
			}
			else
				helloAjax = WebKeys.TEST_GRADED_FAILURE;
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
