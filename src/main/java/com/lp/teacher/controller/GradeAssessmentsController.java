package com.lp.teacher.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.CurriculumService;
import com.lp.common.service.PerformanceTaskService;
import com.lp.custom.exception.DataException;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.AssignmentType;
import com.lp.model.ClassStatus;
import com.lp.model.FluencyAddedWords;
import com.lp.model.FluencyMarks;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.Grade;
import com.lp.model.GradingTypes;
import com.lp.model.JacTemplate;
import com.lp.model.Language;
import com.lp.model.Lesson;
import com.lp.model.QualityOfResponse;
import com.lp.model.RflpPractice;
import com.lp.model.RflpRubric;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.student.service.TakeAssessmentsService;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.teacher.service.AssignAssessmentsService;
import com.lp.teacher.service.GradeAssessmentsService;
import com.lp.teacher.service.GradeBookService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class GradeAssessmentsController {

	@Autowired
	private TakeAssessmentsService takeAssessmentsService;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private GradeAssessmentsService gradeAssessmentsService;
	@Autowired
	private PerformanceTaskService performanceTaskService;
	@Autowired
	private AdminService adminservice;
	@Autowired
	private GradeBookService gradeBookService;
	@Autowired
	private AssignAssessmentsService assignAssessmentsService;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDao;
	@Autowired
	AndroidPushNotificationsService apns;

	static final Logger logger = Logger
			.getLogger(AssignAssessmentController.class);
	
	@RequestMapping(value = "/gotoGradeAssessments", method = RequestMethod.GET)
	public ModelAndView goToGradeAssessments(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		ModelAndView model = new ModelAndView(
				"Teacher/teacher_grade_assessments", "assignment", assignment);		
		model.addObject("tab", WebKeys.LP_TAB_GRADE_ASSESSMENTS);
		model.addObject("usedFor",WebKeys.LP_USED_FOR_ASSESSMENT);
		model.addObject("LP_STEM_TAB",WebKeys.LP_STEM_TAB);		
		try{
			model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));
		}catch(DataException e){
			model.addObject("helloAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping(value = "/gotoGradeHomeworks", method = RequestMethod.GET)
	public ModelAndView goToGradeHomeworks(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		ModelAndView model = new ModelAndView(
				"Teacher/teacher_grade_assessments", "assignment", assignment);		
		model.addObject("tab", WebKeys.LP_TAB_GRADE_HOMEWORK);
		model.addObject("usedFor",WebKeys.LP_USED_FOR_HOMEWORKS);
		try{
			model.addObject("teacherGrades", curriculumService.getTeacherGrades(teacherObj));
		}catch(DataException e){
			model.addObject("helloAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping(value = "/gotoGradeRti", method = RequestMethod.GET)
	public ModelAndView goToGradeRti(HttpSession session) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		Assignment assignment = new Assignment();
		ModelAndView model = new ModelAndView("Teacher/teacher_grade_assessments", "assignment", assignment);		
		model.addObject("tab", WebKeys.LP_TAB_GRADE_RTI);
		model.addObject("usedFor",WebKeys.LP_USED_FOR_RTI);
		try{
			if (teacherObj == null) {
				// admin
				UserRegistration userReg = (UserRegistration) session
						.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
				List<Grade> schoolgrades = adminservice.getSchoolGrades(userReg
						.getSchool().getSchoolId());
				model.addObject("teacherGrades", schoolgrades);
			} else {
				// teacher
				model.addObject("teacherGrades",curriculumService.getTeacherGrades(teacherObj));	
			}
			
			
		}catch(DataException e){
			model.addObject("helloAjax",e.getMessage());
		}
		return model;
	}
	@RequestMapping(value = "/getStudentAssessmentTests", method = RequestMethod.GET)
	public ModelAndView getHomeworkReports(HttpSession session,@RequestParam("assignmentId") long assignmentId,
			HttpServletResponse response, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("Ajax/Teacher/show_completed_assessment_tests", "assignment",
				new StudentAssignmentStatus());
		List<StudentAssignmentStatus> assessmentCompletedList = gradeAssessmentsService.getStudentAssessmentTests(assignmentId);
		if(!assessmentCompletedList.isEmpty() && assessmentCompletedList.get(0).getAssignment().getAssignmentType().getAssignmentType().equals(WebKeys.ASSIGNMENT_TYPE_RFLP)){
			List<RflpTest> rflpList = gradeAssessmentsService.getRFLPTests(assignmentId);
			model.addObject("rflpTests", rflpList);
		}
		model.addObject("studentAssessmentTestList", assessmentCompletedList);
		return model;
	
	}
	
	@RequestMapping(value = "/getGradingTypeDetails", method = RequestMethod.GET)
	public ModelAndView getGradingTypeDetails(@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("csId") long csId,
			/*@RequestParam("lessonId") long lessonId,*/
			@RequestParam("studentId") long studentId,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("userTypeId") long userTypeId,
			HttpServletRequest request) {
		List<AssignmentQuestions> questions = gradeAssessmentsService.getTestQuestions(studentAssignmentId);
		StudentAssignmentStatus studentAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
		ModelAndView model = null;
		String teacherComment="";
		model = new ModelAndView("Ajax/Teacher/include_grading_types", "studentAssignmentStatus", studentAssignmentStatus);
		 List<AssignmentQuestions> benchQuestions =gradeAssessmentsService.getBenchmarkQuestBygradeTyId(questions,gradeTypesId);
		 teacherComment=gradeAssessmentsService.getBenchmarkTeacherComment(studentAssignmentId);
		 model.addObject("teacherComment",teacherComment);
		/* model.addObject("lessonId",lessonId);*/
		 model.addObject("studentId",studentId);
		 model.addObject("benchQuestions",benchQuestions);
		 model.addObject("passageCount",benchQuestions.size());
		 model.addObject("studentAssignmentId", studentAssignmentId);
		 model.addObject("assignmentId", assignmentId);
		 model.addObject("gradeTypesId",gradeTypesId);
		 model.addObject("userTypeId",userTypeId);
		 model.addObject("assignmentTypeId", studentAssignmentStatus.getAssignment().getAssignmentType().getAssignmentTypeId());
		 if(gradeTypesId==2)
		 model.addObject("gradeStatus",studentAssignmentStatus.getSelfGradedStatus());
		 else if(gradeTypesId==3){
		     model.addObject("gradeStatus",studentAssignmentStatus.getPeerGradedStatus());
		     model.addObject("peerSubmitDate",studentAssignmentStatus.getPeerSubmitDate()!=null?studentAssignmentStatus.getPeerSubmitDate():"");
			 String PeerReviewName= studentAssignmentStatus.getPeerReviewBy()!=null?studentAssignmentStatus.getPeerReviewBy().getUserRegistration().getFirstName()+" "+studentAssignmentStatus.getPeerReviewBy().getUserRegistration().getLastName():"";
			 model.addObject("peerReviewBy",PeerReviewName);
		 }
		 return model;
	}
	@RequestMapping(value = "/getStudentTestQuestions", method = RequestMethod.GET)
	public ModelAndView getTestQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("lessonId") long lessonId,
			@RequestParam("studentId") long studentId,
			HttpServletRequest request,HttpSession session) throws Exception{
		List<AssignmentQuestions> questions = gradeAssessmentsService.getTestQuestions(studentAssignmentId);		
		StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
		studentAssignmentStatus.setStudentAssignmentId(studentAssignmentId);
		studentAssignmentStatus.setAssignmentQuestions(questions);
		Assignment assignment = new Assignment();
		assignment=gradeAssessmentsService.getAssignment(assignmentId);
		studentAssignmentStatus.setAssignment(assignment);
		ModelAndView model = null;
		String teacherComment="";
		if (assignmentTypeId == 8) {
			 model = new ModelAndView("Ajax/Teacher/include_fluency_questions", "studentAssignmentStatus", studentAssignmentStatus);
			 List<AssignmentQuestions> benchQuestions =gradeAssessmentsService.getBenchmarkQuestBygradeTyId(questions,1);
			 teacherComment=gradeAssessmentsService.getBenchmarkTeacherComment(studentAssignmentId);
			 model.addObject("teacherComment",teacherComment);
			 model.addObject("lessonId",lessonId);
			 model.addObject("studentId",studentId);
			 model.addObject("benchQuestions",benchQuestions);
			 model.addObject("passageCount",benchQuestions.size());
			 model.addObject("studentAssignmentId", studentAssignmentId);
			 model.addObject("assignmentId", assignmentId);
			 model.addObject("gradeTypesId",1);
			 model.addObject("assignmentTypeId", studentAssignmentStatus.getAssignment().getAssignmentType().getAssignmentTypeId());
			  
		}
		else if(assignmentTypeId == 20){
			 model = new ModelAndView("Ajax/Teacher/include_student_test_questions", "studentAssignmentStatus", studentAssignmentStatus);
			 model.addObject("studentAssignmentId",studentAssignmentId);
			 model.addObject("lessonId",lessonId);
			 model.addObject("studentId",studentId);
			 List<AssignmentQuestions> benchQuestions =gradeAssessmentsService.getBenchmarkQuestBygradeTyId(questions,1);
			 model.addObject("benchQuestions",benchQuestions);
			 model.addObject("userTypeId",WebKeys.TEACHER_USER_TYPEID);
		}
		else if(assignmentTypeId == 13) {
			model = new ModelAndView("Ajax/Teacher/performance_test",
					"studentAssignmentStatus", studentAssignmentStatus);
		}else {
			model = new ModelAndView("Ajax/Teacher/include_student_test_questions",
					"studentAssignmentStatus", studentAssignmentStatus);
			
		}
		model.addObject("testQuestions", questions);
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
		else if (assignmentTypeId == 7 || assignmentTypeId == 19) {
			List<SubQuestions> ssQuestions = gradeAssessmentsService.getSSQuestions(questions);
			model.addObject("ssQuestions", ssQuestions);
			if(assignmentTypeId==19){
				Student student = gradeBookService.getStudentById(studentId); 
				String uploadFilePath = FileUploadUtil.getUploadFilesPath(student.getUserRegistration(), request);
				String comprehensionFilePath = "";
				comprehensionFilePath = uploadFilePath+File.separator+WebKeys.ASSIGNMENT_TYPE_COMPREHENSION+File.separator+studentAssignmentId;
				model.addObject("comprehensionFilePath", comprehensionFilePath);
			}
		}
		else if (assignmentTypeId == 14) {
			model = new ModelAndView("Ajax/Teacher/include_jactemplate_questions",
					"studentAssignmentStatus", studentAssignmentStatus);
			model.addObject("testQuestions", questions);
			List<JacTemplate> jacTitles = gradeAssessmentsService.getJacTemplateTitleList(questions);
			model.addObject("jacTitles", jacTitles);
			String jacQuestionFilePath=gradeAssessmentsService.getJacQuestionFilePath(jacTitles.get(0).getJacQuestionFile());
			model.addObject("jacQuestionFilePath",jacQuestionFilePath);
		}else if(assignmentTypeId == 18) {
			long gradingTypesId=0;
			String vis="";
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			List<RflpPractice> rflpPracticeLt = takeAssessmentsService.getRflpTest(studentAssignmentId);
			List<RflpRubric> rflpRubricLt = gradeAssessmentsService.getRflpRubricValues();

			model = new ModelAndView("Ajax/Teacher/grade_rflp_test", "rflpTest", new RflpTest());
			model.addObject("userTypeId", userReg.getUser().getUserTypeid());
			Student student = gradeBookService.getStudentById(studentId); 
			String uploadFilePath = FileUploadUtil.getUploadFilesPath(student.getUserRegistration(), request);
			String rflpRecordsFilePath = "";
			rflpRecordsFilePath = uploadFilePath+File.separator+WebKeys.RFLP_TEST;
			model.addObject("rflpRecordsFilePath", rflpRecordsFilePath);
			model.addObject("rflpPracticeLt", rflpPracticeLt);
			model.addObject("rflpRubricLt", rflpRubricLt);
			if(rflpPracticeLt.get(0) != null){
				model.addObject("dateDue", rflpPracticeLt.get(0).getRflpTest().getDateDue());
				gradingTypesId=rflpPracticeLt.get(0).getRflpTest().getGradingTypes().getGradingTypesId();
				model.addObject("gradingTypesId",rflpPracticeLt.get(0).getRflpTest().getGradingTypes().getGradingTypesId());
				float percentage = 0;
				teacherComment = rflpPracticeLt.get(0).getRflpTest().getTeacherComment(); 
				model.addObject("teacherComment", teacherComment);
				long writtenScore = 0;
				float totalWrittenScore = 0;
				for (RflpPractice rflpPractice : rflpPracticeLt) {
					if(rflpPractice.getWrittenRubricScore() != null){
						writtenScore = rflpPractice.getWrittenRubricScore();
						totalWrittenScore = totalWrittenScore+writtenScore;
					}					
				}
				if(totalWrittenScore >= 0){
					int len = rflpPracticeLt.size();
				    DecimalFormat df = new DecimalFormat("##.#");
					model.addObject("writtenAvg", df.format(totalWrittenScore/len));
				}				
				if(rflpPracticeLt.get(0).getRflpTest().getStudentAssignmentStatus().getPercentage() != null){
					percentage = rflpPracticeLt.get(0).getRflpTest().getStudentAssignmentStatus().getPercentage();					
				}
				model.addObject("percentage", percentage);
			}
			long userTypeId=userReg.getUser().getUserTypeid();
			if((userTypeId==3)){
				if(gradingTypesId==2 || gradingTypesId==3)
					vis="hidden";
					else
					vis="visible";
			}
			else
				vis="visible";
			model.addObject("vis",vis);			
		}
		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("assignmentId", assignmentId);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("usedFor", usedFor);
		model.addObject("assignmentType",performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId).getAssignment().getAssignmentType());
		return model;
	}

	@RequestMapping(value = "/updateJacMarks", method = RequestMethod.GET)
	public View saveJacAnswer(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("mark") long mark, Model model) {
		gradeAssessmentsService.updateJacMarks(mark,assignmentQuestionId);
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/gradeJacTemplateTest", method = RequestMethod.GET)
	public @ResponseBody
	void submitJacTemplateTest(HttpSession session,
			HttpServletResponse response,
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			Model model) {
		try {
			String helloAjax = "";
			boolean check = gradeAssessmentsService.submitJacTemplateTest(studentAssignmentId);
			if (check){
				StudentAssignmentStatus stAs=assignAssessmentsService.getStudentAssignmentStatus(studentAssignmentId);
				apns.sendStudentResultsNotification(stAs.getAssignment().getUsedFor(),stAs.getStudent().getStudentId());
				helloAjax =WebKeys.JAC_GRADED_SUCCESS;
			}
			else
				helloAjax = WebKeys.JAC_GRADED_FAILURE;
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/gradeStudentTests", method = RequestMethod.GET)
	public @ResponseBody
	void gradeStudentTests(HttpSession session,
			HttpServletResponse response,
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("questions") long questionIds[],@RequestParam("maxMarks") long maxMarks[],
			@RequestParam("secMarks") long secMarks[],@RequestParam("teacherComments") String teacherComments[],
			Model model) {
		try {
			
			String helloAjax = "";
			List<AssignmentQuestions> assignmentQuestions= new ArrayList<AssignmentQuestions>();
			for(int i=0;i<questionIds.length;i++){
				AssignmentQuestions ass=new AssignmentQuestions();
				ass.setAssignmentQuestionsId(questionIds[i]);
				ass.setMaxMarks((int)(maxMarks[i]));
				ass.setSecMarks((int)secMarks[i]);
				ass.setTeacherComment(teacherComments[i]);
				assignmentQuestions.add(ass);
			}
			boolean check=gradeAssessmentsService.gradeStudentTests(studentAssignmentId,assignmentQuestions);
						
			if (check)
				helloAjax =WebKeys.TEST_GRADED_SUCCESS;
			else
				helloAjax = WebKeys.TEST_GRADED_FAILURE;
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings({ "rawtypes","unchecked" })
	@RequestMapping(value = "/getBenchmarkTest", method = RequestMethod.GET)
	public ModelAndView getBenchmarkTest(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("benchmarkId") String benchmarkId, @RequestParam("readingTypesId") long readingTypesId,
			@RequestParam("marks") long marks, 
			@RequestParam("regrade") String regrade,HttpServletRequest request,HttpSession session,
			@RequestParam("butt") String butt,
			/*@RequestParam("lessonId") long lessonId,*/
			@RequestParam("csId") long csId,
			@RequestParam("studentId") long studentId,
			@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("userTypeId") long userTypeId
			) {
		
		AssignmentQuestions assignmentQuestions = new AssignmentQuestions();
		FluencyMarks fluencyMarks =new FluencyMarks();
		List<FluencyMarksDetails> errorsList=new ArrayList<FluencyMarksDetails>();
		String path="";
		List<QualityOfResponse> qualityResponses=null;
		int correctWords=0;
		long wordsRead=0,assignmentTypeId=0;
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
		  assignmentTypeId=assignmentQuestions.getStudentAssignmentStatus().getAssignment().getAssignmentType().getAssignmentTypeId();
		      if(fluencyMarks.getReadingTypes().getReadingTypesId()==1 || fluencyMarks.getReadingTypes().getReadingTypesId()==2){
				  if(fluencyMarks.getWordsRead()!=null){ 
					  wordsRead=fluencyMarks.getWordsRead();
					  correctWords=fluencyMarks.getWordsRead()-fluencyMarks.getCountOfErrors();
					  errorsList=gradeAssessmentsService.getErrorsList(fluencyMarks.getFluencyMarksId());
				  }
			   }  
		      if(fluencyMarks.getReadingTypes().getReadingTypesId()==1){
		    	  if(regrade.equalsIgnoreCase("no")){
		    		   model = new ModelAndView("Ajax/Teacher/common_accuracy_grade_results","assignmentQuestions", assignmentQuestions);
		    	   }else if(regrade.equalsIgnoreCase("view")){
		    		   model = new ModelAndView("Ajax/Student/benchmark_accuracy_results_page","assignmentQuestions", assignmentQuestions);
		    	   }else{
		    		   model = new ModelAndView("Ajax/Teacher/common_accuracy_grade_results","assignmentQuestions", assignmentQuestions);
				   }
		  
		    	   model.addObject("correctWords",correctWords);
				   model.addObject("fluencyAddedWordsLt",fluencyMarks.getFluencyAddedWordsLt());
			       model.addObject("errorsList",errorsList);
			       model.addObject("fluencyMarks",fluencyMarks);
			       path  = FileUploadUtil.getUploadFilesPath(assignmentQuestions.getStudentAssignmentStatus().getStudent().getUserRegistration(), request)+File.separator+WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+assignmentQuestionId+File.separator+WebKeys.ACCURACY_FILE_NAME;
			       model.addObject("filename",path);
				   model.addObject("usersFilePath", WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+assignmentQuestionId+File.separator+WebKeys.ACCURACY_FILE_NAME);
				   
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
		/*model.addObject("lessonId",lessonId);*/
		model.addObject("csId",csId);
		model.addObject("studentId",studentId);
		model.addObject("assignmentTitle",assignmentQuestions.getStudentAssignmentStatus().getAssignment().getTitle());
		model.addObject("wordsRead",wordsRead);
		model.addObject("benchmarkqList1",benchmarkqList1);
		model.addObject("gradeTypesId",gradeTypesId);
		model.addObject("benchQuestions",questions);
		model.addObject("assignmentTypeId",assignmentTypeId);
		return model;
	
	}
	
	@RequestMapping(value = "/gradeFluencyTest", method = RequestMethod.POST)
	public View gradeFluencyTest(HttpSession session,
			HttpServletResponse response,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,@RequestParam("wordsRead") long wordsRead,@RequestParam("hwAssignmentId") long hwAssignmentId,
			@RequestParam("errors") long errors,@RequestParam("correctWords") long correctWords,@RequestParam("errorsStr") String errorsStr,@RequestParam("addedWordStr") String addedWordStr,
		@RequestParam("errorIdsStr") String errorIdsStr,@RequestParam("assignmentTitle") String assignmentTitle,@RequestParam("dueDate") String dueDate,
			@RequestParam("csId") long csId,@RequestParam("studentId") long studentId,@RequestParam("readingTypesId") long readingTypesId, 
			Model model,@RequestParam("gradeTypesId") long gradeTypesId,@RequestParam("comment") String comment,@RequestParam("addedSelfCorrStr") String addedSelfCorrStr,@RequestParam("addedProsodyStr") String addedProsodyStr) {
		try {
			
			 String helloAjax = "";
			 float percentageAcquired = 0.2f;
			 String percentage = "";
			 String[] wordNums = null; String[] errorWords = null; String[] addedWords = null; String[] selfCorrWords = null; String[] prosodyWords = null;
			 List<FluencyMarksDetails> fluMarkDets=new ArrayList<FluencyMarksDetails>();
			 if(errorIdsStr.contains(":"))
				 wordNums = errorIdsStr.split(":");
			 if(errorsStr.contains(":"))
				 errorWords = errorsStr.split(":");
			 if(addedWordStr.contains(":"))
				 addedWords = addedWordStr.split(":");
			 if(addedSelfCorrStr.contains(":"))
				 selfCorrWords=addedSelfCorrStr.split(":");
			 if(addedProsodyStr.contains(":"))
				 prosodyWords=addedProsodyStr.split(":");
			GradingTypes gt=new GradingTypes();
		
		
			long s = correctWords + errors;
			if(s!=0){
				percentageAcquired = (float) correctWords * 100 / s;
				 DecimalFormat dec = new DecimalFormat("##.##");
				 percentage = dec.format(percentageAcquired);
				 model.addAttribute("percentageAcquired",percentage);
			}
		   
		  	boolean check=gradeAssessmentsService.gradeFluencyTest(assignmentQuestionId,wordsRead,errors,correctWords,wordNums,errorWords,addedWords,readingTypesId,gradeTypesId,percentage,comment,selfCorrWords,prosodyWords);
			if(dueDate!=""){
			    if (check){
			    	AssignmentQuestions assignmentQuestion= gradeAssessmentsService.getAssignmentQuestions(assignmentQuestionId);
			    	String homeTitle=assignmentQuestion.getStudentAssignmentStatus().getAssignment().getTitle();
					helloAjax =WebKeys.TEST_GRADED_SUCCESS;
					Assignment assignment = new Assignment();
					AssignmentType assignmentType = new AssignmentType();
					StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
					ClassStatus classStatus = new ClassStatus();
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
					gt=gradeAssessmentsService.getGradingType(gradeTypesId);
					long peerReviewBy=0;
					List<AssignmentQuestions> assignmentQuestionst = null;
					long questionId = 0;
					if(assignmentQuestion.getStudentAssignmentStatus() != null){
						assignmentQuestionst = assignAssessmentsService.getAssignmentQuestionsByStudentAssignmentId(assignmentQuestion.getStudentAssignmentStatus().getStudentAssignmentId());
						int inc = 0;
						for (AssignmentQuestions ass: assignmentQuestionst) {
							inc=inc+1;
							if(ass.getQuestions().getQuestionId() == assignmentQuestion.getQuestions().getQuestionId())								
							   questionId = inc;
						}
					}
		        	if(gradeTypesId==1){
						UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);						
			          	assignment.setCreatedBy(userReg.getRegId());
			          	if(assignmentQuestionst.size() > 1 && questionId > 0)
			          		assignmentTitle=homeTitle+""+questionId+"_"+gt.getGradingTypes();
			          	else
			          		assignmentTitle=homeTitle+"_"+gt.getGradingTypes();

		          	}else if(gradeTypesId==2){
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
					StudentAssignmentStatus stdAssignmentStatus = assignAssessmentsService.assignReadingFluencyLearningPracticeHomeWork(assignment, studentAssignmentStatus);
					List<String> contentList = new ArrayList<String>();
					contentList.add(errorsStr);
					if(gradeTypesId==1){
						FluencyMarks fluMarks=gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId, readingTypesId, gradeTypesId);
						fluMarkDets=gradeAssessmentsService.getErrorsList(fluMarks.getFluencyMarksId());
					}
					
					if(stdAssignmentStatus.getStatus().equals(WebKeys.TEST_STATUS_SUBMITTED) && stdAssignmentStatus.getGradedStatus().equals(WebKeys.GRADED_STATUS_GRADED)){
						model.addAttribute("assignmentId",stdAssignmentStatus.getAssignment().getAssignmentId());
					}else{
						if(gradeTypesId==1)
							assignAssessmentsService.assignRFLPTestFromTeacherGrading(fluMarkDets,dueDate,studentAssignmentStatus,gradeTypesId,peerReviewBy);
						else
						assignAssessmentsService.assignRFLPTest(contentList,dueDate,studentAssignmentStatus,gradeTypesId,peerReviewBy);//assignAssessmentsService.assignRFLPTest(rflpTest);
						model.addAttribute("assignmentId",stdAssignmentStatus.getAssignment().getAssignmentId());
					}
					helloAjax = WebKeys.TEST_GRADED_SUCCESS;
			   
				}else{
					helloAjax = WebKeys.TEST_GRADED_FAILURE;
			    }
			}
		    model.addAttribute("helloAjax",helloAjax);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/gradeAccuracyTest", method = RequestMethod.POST)
	public View gradeAccuracyTest(HttpSession session,
			HttpServletResponse response,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,@RequestParam("wordsRead") long wordsRead,
			@RequestParam("errors") long errors,@RequestParam("correctWords") long correctWords,
			@RequestParam("errorIdsStr") String errorIdsStr,@RequestParam("assignmentTitle") String assignmentTitle,
			@RequestParam("readingTypesId") long readingTypesId, Model model,
			@RequestParam("errorComments") String[] errorComments,@RequestParam("errorsStr") String errorsStr,@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("addedWordStr") String addedWordStr,@RequestParam("comment") String comment,@RequestParam("dueDate") String dueDate,@RequestParam("csId") long csId,
			@RequestParam("studentId") long studentId,@RequestParam("hwAssignmentId") long hwAssignmentId,@RequestParam("wcpm") Integer wcpm) {
		try {		
			
			String[] addedWords = null;
			String[] errorWords = null;
			float percentageAcquired = 0.2f;
			String helloAjax = "";
			long s = correctWords + errors;
		    percentageAcquired = (float) correctWords * 100 / s;
		    DecimalFormat dec = new DecimalFormat("##.##");
			String[] error = errorsStr.split("&&");
			String[] wordNums=null;
			ArrayList<String> errorWordsList = new ArrayList<String>();
			GradingTypes gt=new GradingTypes();	
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
			if(gradeTypesId==1 || gradeTypesId==3){
			if (check){
				helloAjax =WebKeys.TEST_GRADED_SUCCESS;
		    }else{
		    model.addAttribute("helloAjax",helloAjax);}
			}
			
			if(dueDate!="" && gradeTypesId==2){
			    if (check){
			    	AssignmentQuestions assignmentQuestion= gradeAssessmentsService.getAssignmentQuestions(assignmentQuestionId);
			    	String homeTitle=assignmentQuestion.getStudentAssignmentStatus().getAssignment().getTitle();
					helloAjax =WebKeys.TEST_GRADED_SUCCESS;
					Assignment assignment = new Assignment();
					AssignmentType assignmentType = new AssignmentType();
					StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
					ClassStatus classStatus = new ClassStatus();
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
					gt=gradeAssessmentsService.getGradingType(gradeTypesId);
					long peerReviewBy=0;
					List<AssignmentQuestions> assignmentQuestionst = null;
					long questionId = 0;
					if(assignmentQuestion.getStudentAssignmentStatus() != null){
						assignmentQuestionst = assignAssessmentsService.getAssignmentQuestionsByStudentAssignmentId(assignmentQuestion.getStudentAssignmentStatus().getStudentAssignmentId());
						int inc = 0;
						for (AssignmentQuestions ass: assignmentQuestionst) {
							inc=inc+1;
							if(ass.getQuestions().getQuestionId() == assignmentQuestion.getQuestions().getQuestionId())								
							   questionId = inc;
						}
					}
		        	if(gradeTypesId==1){
						UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);						
			          	assignment.setCreatedBy(userReg.getRegId());
			          	if(assignmentQuestionst.size() > 1 && questionId > 0)
			          		assignmentTitle=homeTitle+""+questionId+"_"+gt.getGradingTypes();
			          	else
			          		assignmentTitle=homeTitle+"_"+gt.getGradingTypes();

		          	}else if(gradeTypesId==2){
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
					StudentAssignmentStatus stdAssignmentStatus = assignAssessmentsService.assignReadingFluencyLearningPracticeHomeWork(assignment, studentAssignmentStatus);
					List<String> contentList = new ArrayList<String>();
					contentList.add(errorsStr);
					if(stdAssignmentStatus.getStatus().equals(WebKeys.TEST_STATUS_SUBMITTED) && stdAssignmentStatus.getGradedStatus().equals(WebKeys.GRADED_STATUS_GRADED)){
						model.addAttribute("assignmentId",stdAssignmentStatus.getAssignment().getAssignmentId());
					}else{
						assignAssessmentsService.assignRFLPTest(contentList,dueDate,studentAssignmentStatus,gradeTypesId,peerReviewBy);//assignAssessmentsService.assignRFLPTest(rflpTest);
						model.addAttribute("assignmentId",stdAssignmentStatus.getAssignment().getAssignmentId());
					}
					helloAjax = WebKeys.TEST_GRADED_SUCCESS;
			   
				}else{
					helloAjax = WebKeys.TEST_GRADED_FAILURE;
			    }
			}
		    model.addAttribute("helloAjax",helloAjax);

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/gradeRetellFluencyTest", method = RequestMethod.GET)
	public @ResponseBody
	void gradeRetellFluencyTest(HttpSession session,
			HttpServletResponse response,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,@RequestParam("retellmarks") long retellmarks,
			@RequestParam("readingTypesId") long readingTypesId,@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("comment") String comment,
			Model model) {
		try {
			String helloAjax = "";
			boolean check=gradeAssessmentsService.gradeRetellFluencyTest(assignmentQuestionId, retellmarks, readingTypesId,gradeTypesId,comment);
		    if (check)
				helloAjax =WebKeys.TEST_GRADED_SUCCESS;
			else
				helloAjax = WebKeys.TEST_GRADED_FAILURE;			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	@RequestMapping(value = "/gradeBenchmarkTests", method = RequestMethod.GET)
	public @ResponseBody
	void gradeBenchmarkTests(HttpSession session,
			HttpServletResponse response,
			@RequestParam("studentAssignmentId") long studentAssignmentId,@RequestParam("teacherComment") String teacherComment,
			Model model) {
		try {
			String helloAjax = "";
			boolean check= false;
			StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
			studentAssignmentStatus = performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId);
			if(studentAssignmentStatus.getAssignment().getAssignmentType().getAssignmentType().equals(WebKeys.ASSIGNMENT_TYPE_FLUENCY_READING)){
				check=gradeAssessmentsService.gradeBenchmarkTest(studentAssignmentStatus,teacherComment);
			}
			else {
				check=gradeAssessmentsService.gradeAccuracyTests(studentAssignmentStatus);
			}
		   
			if (check){
				StudentAssignmentStatus stAs=assignAssessmentsService.getStudentAssignmentStatus(studentAssignmentId);
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
	
	@RequestMapping(value = "/getRFLPQuestions", method = RequestMethod.GET)
	public ModelAndView getRFLPQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("usedFor") String usedFor,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			@RequestParam("assignmentId") long assignmentId,
			@RequestParam("studentId") long studentId,
			HttpServletRequest request,HttpSession session) throws Exception{
			
		String teacherComment="";
		long gradingTypesId=0;
		String vis="";
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<RflpPractice> rflpPracticeLt = takeAssessmentsService.getRflpTest(studentAssignmentId);
		List<RflpRubric> rflpRubricLt = gradeAssessmentsService.getRflpRubricValues();

		ModelAndView model = new ModelAndView("Ajax/Teacher/rflp_grade_book", "rflpTest", new RflpTest());
		model.addObject("userTypeId", userReg.getUser().getUserTypeid());
		Student student = gradeBookService.getStudentById(studentId); 
		String uploadFilePath = FileUploadUtil.getUploadFilesPath(student.getUserRegistration(), request);
		String rflpRecordsFilePath = "";
		rflpRecordsFilePath = uploadFilePath+File.separator+WebKeys.RFLP_TEST;
		model.addObject("rflpRecordsFilePath", rflpRecordsFilePath);
		model.addObject("rflpPracticeLt", rflpPracticeLt);
		model.addObject("rflpRubricLt", rflpRubricLt);

		if(rflpPracticeLt.get(0) != null){
			model.addObject("dateDue", rflpPracticeLt.get(0).getRflpTest().getDateDue());
			gradingTypesId=rflpPracticeLt.get(0).getRflpTest().getGradingTypes().getGradingTypesId();
			model.addObject("gradingTypesId",rflpPracticeLt.get(0).getRflpTest().getGradingTypes().getGradingTypesId());
			float percentage = 0;
			teacherComment = rflpPracticeLt.get(0).getRflpTest().getTeacherComment(); 
			model.addObject("teacherComment", teacherComment);
			long writtenScore = 0;
			float totalWrittenScore = 0;
			for (RflpPractice rflpPractice : rflpPracticeLt) {
				if(rflpPractice.getWrittenRubricScore() != null){
					writtenScore = rflpPractice.getWrittenRubricScore();
					totalWrittenScore = totalWrittenScore+writtenScore;
				}					
			}
			if(totalWrittenScore >= 0){
				int len = rflpPracticeLt.size();
			    DecimalFormat df = new DecimalFormat("##.#");
				model.addObject("writtenAvg", df.format(totalWrittenScore/len));
			}				
			if(rflpPracticeLt.get(0).getRflpTest().getStudentAssignmentStatus().getPercentage() != null){
				percentage = rflpPracticeLt.get(0).getRflpTest().getStudentAssignmentStatus().getPercentage();					
			}
			model.addObject("percentage", percentage);
		}
		long userTypeId=userReg.getUser().getUserTypeid();
		if((userTypeId==3)){
				if(gradingTypesId==2 || gradingTypesId==3)
		        vis="hidden";
				else
				vis="visible";
		}
		else
			vis="visible";
		model.addObject("vis",vis);		
		model.addObject("studentAssignmentId", studentAssignmentId);
		model.addObject("assignmentId", assignmentId);
		model.addObject("assignmentTypeId", assignmentTypeId);
		model.addObject("usedFor", usedFor);
		model.addObject("assignmentType",performanceTaskService.getStudentAssignmentStatusById(studentAssignmentId).getAssignment().getAssignmentType());
		return model;
	}
	

	@SuppressWarnings({ "rawtypes","unchecked" })
	@RequestMapping(value = "/getFluencyTest", method = RequestMethod.GET)
	public ModelAndView getFluencyQuestions(
			@RequestParam("studentAssignmentId") long studentAssignmentId,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("benchmarkId") String benchmarkId, @RequestParam("readingTypesId") long readingTypesId,
			@RequestParam("marks") long marks, 
			@RequestParam("regrade") String regrade,HttpServletRequest request,HttpSession session,
			@RequestParam("butt") String butt,
			/*@RequestParam("lessonId") long lessonId,*/
			@RequestParam("csId") long csId,
			@RequestParam("studentId") long studentId,
			@RequestParam("gradeTypesId") long gradeTypesId) {
	
		AssignmentQuestions assignmentQuestions = new AssignmentQuestions();
		FluencyMarks fluencyMarks =new FluencyMarks();
		List<FluencyMarksDetails> errorsList=new ArrayList<FluencyMarksDetails>();
		String path="";
		List<QualityOfResponse> qualityResponses=null;
		int correctWords=0;
		long wordsRead=0,assignmentTypeId=0;
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
			assignmentTypeId=assignmentQuestions.getStudentAssignmentStatus().getAssignment().getAssignmentType().getAssignmentTypeId();
			if(fluencyMarks.getReadingTypes().getReadingTypesId()==1 || fluencyMarks.getReadingTypes().getReadingTypesId()==2){
				//if(fluencyMarks.getWordsRead()!=null){ 
				if(regrade.equalsIgnoreCase("yes") || regrade.equalsIgnoreCase("new")){
					errorsList=gradeAssessmentsService.getErrorsList(fluencyMarks.getFluencyMarksId());
				}
				if(fluencyMarks.getWordsRead()!=null){ 
					wordsRead=fluencyMarks.getWordsRead();
					correctWords=fluencyMarks.getWordsRead()-fluencyMarks.getCountOfErrors();
					errorsList=gradeAssessmentsService.getErrorsList(fluencyMarks.getFluencyMarksId());
				}
			}  
			 
			if(fluencyMarks.getReadingTypes().getReadingTypesId()==2){
				if(regrade.equalsIgnoreCase("no")){
					model = new ModelAndView("Ajax/Teacher/common_fluency_grade_results","assignmentQuestions", assignmentQuestions);
				}if(regrade.equalsIgnoreCase("view")){
					model = new ModelAndView("Ajax/Student/benchmark_fluency_result_page","assignmentQuestions", assignmentQuestions);
				}
				else{
					model = new ModelAndView("Ajax/Teacher/common_fluency_grade_results","assignmentQuestions", assignmentQuestions);			  
				}
				
				model.addObject("fluencyAddedWordsLt",gradeAssessmentsService.getFluencyAddedWords(fluencyMarks.getFluencyMarksId()));
				model.addObject("passage",assignmentQuestions.getQuestions().getQuestion());
				model.addObject("correctWords",correctWords);
				model.addObject("errorsList",errorsList);
				model.addObject("fluencyMarks",fluencyMarks);
				model.addObject("gradePage","teacher");
				path  = FileUploadUtil.getUploadFilesPath(assignmentQuestions.getStudentAssignmentStatus().getStudent().getUserRegistration(), request)+File.separator+WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+assignmentQuestionId+File.separator+WebKeys.FLUENCY_FILE_NAME;
				model.addObject("filename",path);
				model.addObject("usersFilePath", WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+assignmentQuestionId+File.separator+WebKeys.FLUENCY_FILE_NAME);
			}else{
				qualityResponses=gradeAssessmentsService.getQualityOfResponse(); 
				model = new ModelAndView("Ajax/Teacher/retell_fluency_test","assignmentQuestions", assignmentQuestions);
				model.addObject("fluencyMarks",fluencyMarks);
				model.addObject("qualityResponseList",qualityResponses);
				path  = FileUploadUtil.getUploadFilesPath(assignmentQuestions.getStudentAssignmentStatus().getStudent().getUserRegistration(), request)+File.separator+WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+assignmentQuestionId+File.separator+WebKeys.RETELL_FILE_NAME;
				model.addObject("filename",path);
				model.addObject("usersFilePath", WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+assignmentQuestionId+File.separator+WebKeys.RETELL_FILE_NAME);   
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
		/*model.addObject("lessonId",lessonId);*/
		model.addObject("csId",csId);
		model.addObject("studentId",studentId);
		model.addObject("assignmentTitle",assignmentQuestions.getStudentAssignmentStatus().getAssignment().getTitle());
		model.addObject("wordsRead",wordsRead);
		model.addObject("benchmarkqList1",benchmarkqList1);
		model.addObject("gradeTypesId",gradeTypesId);
		model.addObject("benchQuestions",questions);
		model.addObject("assignmentTypeId",assignmentTypeId);
		model.addObject("studentAssignmentId",assignmentQuestions.getStudentAssignmentStatus().getStudentAssignmentId());
		return model;
	}

	@RequestMapping(value = "/getSelfAndPeerFluencyScores", method = RequestMethod.POST)
	public View getSelfAndPeerFluencyScores(@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("assignmentTypeId") long assignmentTypeId,
			HttpSession session,
			Model model)
			 {
		List<FluencyMarks> fluencyMarks =new ArrayList<FluencyMarks>();
		List<FluencyAddedWords> fluencyTeacherAddedWords =new ArrayList<FluencyAddedWords>();
		try{
			if(assignmentTypeId==8){
			fluencyMarks =gradeAssessmentsService.getSelfAndPeerFluencyMarks(assignmentQuestionId,2);
			fluencyTeacherAddedWords=gradeAssessmentsService.getTeacherFluencyAddedWords(assignmentQuestionId,2,1);
			
			}
		else{ 
			fluencyMarks =gradeAssessmentsService.getSelfAndPeerFluencyMarks(assignmentQuestionId,1);
			fluencyTeacherAddedWords=gradeAssessmentsService.getTeacherFluencyAddedWords(assignmentQuestionId,1,1);
			
		}
		}catch(Exception e){
			e.printStackTrace();			
		} 		
		model.addAttribute("fluencyMarks",fluencyMarks);	
		model.addAttribute("fluencyTeacherAddedWords",fluencyTeacherAddedWords);
		return new MappingJackson2JsonView();	
	}
	@RequestMapping(value = "/autoSaveAddedWord", method = RequestMethod.GET)
	public View autoSaveAddedWord(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("readingTypesId") long readingTypesId,@RequestParam("gradeTypesId") long gradeTypesId,@RequestParam("addedWord") String addedWord,
			@RequestParam("wordsRead") long wordsRead,@RequestParam("errRead") long errRead,
			@RequestParam("totalRead") long totalRead,@RequestParam("wordType") int wordType,
			@RequestParam("regradeStat") String regradeStat,Model model) {
		gradeAssessmentsService.autoSaveAddedWord(assignmentQuestionId,readingTypesId,gradeTypesId,addedWord,wordType);
		float percentageAcquired = 0.2f;
		if(regradeStat.equalsIgnoreCase("no")){
		long s = totalRead + errRead;
		if(s!=0){
			percentageAcquired = (float) totalRead * 100 / s;
		}
		DecimalFormat dec = new DecimalFormat("##.##");
		String percentage = dec.format(percentageAcquired);
		model.addAttribute("percentage",percentage);
		gradeAssessmentsService.autoGradeAccuracy(assignmentQuestionId,readingTypesId,gradeTypesId,wordsRead,errRead,totalRead,percentage);
		}else
		gradeAssessmentsService.autoGradeFluency(assignmentQuestionId,readingTypesId,gradeTypesId,errRead);		
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/removeAddedWord", method = RequestMethod.GET)
	public View removeAddedWord(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("readingTypesId") long readingTypesId,@RequestParam("gradeTypesId") long gradeTypesId,@RequestParam("addedWord") String addedWord,
			@RequestParam("wordsRead") long wordsRead,@RequestParam("errRead") long errRead,@RequestParam("totalRead") long totalRead,
			@RequestParam("wordType") int wordType,@RequestParam("regradeStat") String regradeStat,Model model) {
		gradeAssessmentsService.removeAddedWord(assignmentQuestionId,readingTypesId,gradeTypesId,addedWord,wordType);
		float percentageAcquired = 0.2f;
		if(regradeStat.equalsIgnoreCase("no")){
		long s = totalRead + errRead;
		if(s!=0){
			percentageAcquired = (float) totalRead * 100 / s;
		}
		DecimalFormat dec = new DecimalFormat("##.##");
		String percentage = dec.format(percentageAcquired);
		model.addAttribute("percentage",percentage);
		gradeAssessmentsService.autoGradeAccuracy(assignmentQuestionId,readingTypesId,gradeTypesId,wordsRead,errRead,totalRead,percentage);
		}else{
			gradeAssessmentsService.autoGradeFluency(assignmentQuestionId,readingTypesId,gradeTypesId,errRead);	
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/autoSaveErrorWord", method = RequestMethod.GET)
	public View autoSaveErrorWord(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("readingTypesId") long readingTypesId,@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("errorWord") String errorWord,@RequestParam("errorAddress") long errorAddress,
			@RequestParam("errorType") String errorType,@RequestParam("wordsRead") long wordsRead,@RequestParam("errRead") long errRead,@RequestParam("totalRead") long totalRead,
			@RequestParam("regrade") String regrade,
			Model model) {
		String error = "",percentage="0";
		float percentageAcquired = 0.2f;
		FluencyMarksDetails fluMarkDet=new 	FluencyMarksDetails();
		FluencyMarks fluencyMarks=gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
		fluMarkDet.setFluencyMarks(fluencyMarks);
		long fluencyMarksDetId=gradeAssessmentsDao.checkErrorWordExists(fluencyMarks.getFluencyMarksId(),errorAddress);
		if(fluencyMarksDetId>0){
			fluMarkDet.setFluencyMarksDetailsId(fluencyMarksDetId);
		}
		if(errorType.equalsIgnoreCase("skippedWord")){
			fluMarkDet.setSkippedWord(WebKeys.LP_TRUE);
		}
		if(errorType.equalsIgnoreCase("unKnownWord")){
			fluMarkDet.setUnknownWord(WebKeys.LP_TRUE);
		}
		fluMarkDet.setErrorsAddress(errorAddress);
		if (errorWord.endsWith(",") || errorWord.endsWith("?") || errorWord.endsWith("$")|| errorWord.endsWith("@")|| errorWord.endsWith(".")){
			error = errorWord.substring(0,errorWord.length() - 1);
			fluMarkDet.setErrorWord(error);
		}else{
		fluMarkDet.setErrorWord(errorWord);
		}
		gradeAssessmentsService.autoSaveErrorWord(fluMarkDet);
		if(regrade.equalsIgnoreCase("no")){
			
		long s = totalRead + errRead;
		if(s!=0){
			percentageAcquired = (float) totalRead * 100 / s;
			
		}
		DecimalFormat dec = new DecimalFormat("##.##");
		percentage = dec.format(percentageAcquired);
		model.addAttribute("percentage",percentage);
				
		gradeAssessmentsService.autoGradeAccuracy(assignmentQuestionId,readingTypesId,gradeTypesId,wordsRead,errRead,totalRead,percentage);
		}else{
		
		gradeAssessmentsService.autoGradeFluency(assignmentQuestionId,readingTypesId,gradeTypesId,errRead);
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/autoSaveSkippedSentence", method = RequestMethod.GET)
	public View autoSaveSkippedSentence(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("readingTypesId") long readingTypesId,@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("errorWord") String errorWord[],@RequestParam("errorAddress") long errorAddress[],
			@RequestParam("errorType") String errorType,@RequestParam("wordsRead") long wordsRead,@RequestParam("errRead") long errRead,
			@RequestParam("totalRead") long totalRead,@RequestParam("regrade") String regrade,Model model) {
		
		List<FluencyMarksDetails> lstFluencyMarksDetails =new ArrayList<FluencyMarksDetails>();
		String error="";
		FluencyMarks fluencyMarks=gradeAssessmentsDao.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
		for(int i=0;i<errorAddress.length;i++){
		FluencyMarksDetails fluMarkDet=new 	FluencyMarksDetails();
		fluMarkDet.setFluencyMarks(fluencyMarks);
		long fluencyMarksDetId=gradeAssessmentsDao.checkErrorWordExists(fluencyMarks.getFluencyMarksId(),errorAddress[i]);
		if(fluencyMarksDetId>0){
			fluMarkDet.setFluencyMarksDetailsId(fluencyMarksDetId);
		}
		if(errorType.equalsIgnoreCase("skippedWord")){
			fluMarkDet.setSkippedWord(WebKeys.LP_TRUE);
		}
		if(errorType.equalsIgnoreCase("unKnownWord")){
			fluMarkDet.setUnknownWord(WebKeys.LP_TRUE);
		}
		fluMarkDet.setErrorsAddress(errorAddress[i]);
		if (errorWord[i].endsWith(",") || errorWord[i].endsWith("?") || errorWord[i].endsWith("$")|| errorWord[i].endsWith("@")|| errorWord[i].endsWith(".")){
			error = errorWord[i].substring(0,errorWord[i].length() - 1);
			fluMarkDet.setErrorWord(error);
		}else{
		 fluMarkDet.setErrorWord(errorWord[i]);
		}
		gradeAssessmentsService.autoSaveErrorWord(fluMarkDet);
		}
		if(regrade.equalsIgnoreCase("no")){
		float percentageAcquired = 0.2f;
		long s = totalRead + errRead;
		if(s!=0){
			percentageAcquired = (float) totalRead * 100 / s;
			
		}
		DecimalFormat dec = new DecimalFormat("##.##");
		String percentage = dec.format(percentageAcquired);
		model.addAttribute("percentage",percentage);
		gradeAssessmentsService.autoGradeAccuracy(assignmentQuestionId,readingTypesId,gradeTypesId,wordsRead,errRead,totalRead,percentage);
		}else{
			gradeAssessmentsService.autoGradeFluency(assignmentQuestionId,readingTypesId,gradeTypesId,errRead);
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/autoSaveErrorComment", method = RequestMethod.GET)
	public View autoSaveErrorComment(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("readingTypesId") long readingTypesId,@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("errComment") String errComment,@RequestParam("errorAddress") long errorAddress,Model model) {
		try{
			FluencyMarks fluMarks=gradeAssessmentsService.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
			gradeAssessmentsService.autoSaveErrorComment(fluMarks.getFluencyMarksId(),errComment,errorAddress);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/removeErrorWord", method = RequestMethod.GET)
	public View removeErrorWord(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("readingTypesId") long readingTypesId,@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("errorWord") String errorWord,@RequestParam("errorAddress") long errorAddress,@RequestParam("wordsRead") long wordsRead,
			@RequestParam("errRead") long errRead,@RequestParam("totalRead") long totalRead,@RequestParam("regradeStat") String regradeStat,Model model) {
		try{
			String percentage="0";
			float percentageAcquired = 0.2f;
			FluencyMarks fluMarks=gradeAssessmentsService.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
			gradeAssessmentsService.removeErrorWord(fluMarks.getFluencyMarksId(),errorAddress);
			if(regradeStat.equalsIgnoreCase("no")){
		 	long s = totalRead + errRead;
			
			if(s!=0){
				percentageAcquired = (float) totalRead * 100 / s;
			}
			DecimalFormat dec = new DecimalFormat("##.##");
			percentage = dec.format(percentageAcquired);
			model.addAttribute("percentage",percentage);
			gradeAssessmentsService.autoGradeAccuracy(assignmentQuestionId,readingTypesId,gradeTypesId,wordsRead,errRead,totalRead,percentage);
			}else{
				gradeAssessmentsService.autoGradeFluency(assignmentQuestionId,readingTypesId,gradeTypesId,errRead);	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/autoSaveComments", method = RequestMethod.GET)
	public View autoSaveComments(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("readingTypesId") long readingTypesId,@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("comment") String comment,Model model) {
		try{
			FluencyMarks fluMarks=gradeAssessmentsService.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
			fluMarks.setComment(comment);
			gradeAssessmentsService.autoSaveComments(fluMarks);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/retestFluencyAndAccuracy", method = RequestMethod.GET)
	public View retestFluencyAndAccuracy(HttpSession session,
			@RequestParam("studentAssignmentId") long studentAssignmentId,@RequestParam("readingTypesId") long readingTypesId,
			@RequestParam("gradeTypesId") long gradeTypesId,@RequestParam("assignmentQuestionsId") long assignmentQuestionsId,
			Model model) {
		try{
			boolean stat=gradeAssessmentsService.retestFluencyAndAccuracy(studentAssignmentId);
			model.addAttribute("stat",stat);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/autoSaveWordCount", method = RequestMethod.GET)
	public View autoSaveWordCount(HttpSession session,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,
			@RequestParam("readingTypesId") long readingTypesId,@RequestParam("gradeTypesId") long gradeTypesId,
			@RequestParam("wordsRead") long wordsRead,@RequestParam("errRead") long errRead,@RequestParam("totalRead") long totalRead,Model model) {
		try{
			FluencyMarks fluMarks=gradeAssessmentsService.getFluencyMarks(assignmentQuestionId,readingTypesId,gradeTypesId);
			gradeAssessmentsService.autoSaveWordCount(fluMarks.getFluencyMarksId(),errRead,wordsRead,totalRead);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	@RequestMapping(value = "/gradeSelfAndPeerTest", method = RequestMethod.POST)
	public View gradeSelfAndPeerTest(HttpSession session,
			HttpServletResponse response,
			@RequestParam("assignmentQuestionId") long assignmentQuestionId,@RequestParam("wordsRead") long wordsRead,@RequestParam("hwAssignmentId") long hwAssignmentId,
			@RequestParam("errors") long errors,@RequestParam("correctWords") long correctWords,@RequestParam("errorsStr") String errorsStr,@RequestParam("addedWordStr") String addedWordStr,
		@RequestParam("errorIdsStr") String errorIdsStr,@RequestParam("assignmentTitle") String assignmentTitle,@RequestParam("dueDate") String dueDate,
			@RequestParam("csId") long csId,@RequestParam("studentId") long studentId,@RequestParam("readingTypesId") long readingTypesId, 
			Model model,@RequestParam("gradeTypesId") long gradeTypesId,@RequestParam("comment") String comment,@RequestParam("addedSelfCorrStr") String addedSelfCorrStr,@RequestParam("addedProsodyStr") String addedProsodyStr) {
		try {
			 String helloAjax = "";
			 float percentageAcquired = 0.2f;
			 String percentage = "";
			 String[] wordNums = null; String[] errorWords = null; String[] addedWords = null; String[] selfCorrWords = null; String[] prosodyWords = null;
			 if(errorIdsStr.contains(":"))
				 wordNums = errorIdsStr.split(":");
			 if(errorsStr.contains(":"))
				 errorWords = errorsStr.split(":");
			 if(addedWordStr.contains(":"))
				 addedWords = addedWordStr.split(":");
			 if(addedSelfCorrStr.contains(":"))
				 selfCorrWords=addedSelfCorrStr.split(":");
			 if(addedProsodyStr.contains(":"))
				 prosodyWords=addedProsodyStr.split(":");
			GradingTypes gt=new GradingTypes();
		
		
			long s = correctWords + errors;
			if(s!=0){
				percentageAcquired = (float) correctWords * 100 / s;
				 DecimalFormat dec = new DecimalFormat("##.##");
				 percentage = dec.format(percentageAcquired);
				 model.addAttribute("percentageAcquired",percentage);
			}
		   
		  	boolean check=gradeAssessmentsService.gradeSelfAndPeerTest(assignmentQuestionId,wordsRead,errors,correctWords,wordNums,errorWords,addedWords,readingTypesId,gradeTypesId,percentage,comment,selfCorrWords,prosodyWords);
			if(dueDate!=""){
			    if (check){
			    	AssignmentQuestions assignmentQuestion= gradeAssessmentsService.getAssignmentQuestions(assignmentQuestionId);
			    	String homeTitle=assignmentQuestion.getStudentAssignmentStatus().getAssignment().getTitle();
					helloAjax =WebKeys.TEST_GRADED_SUCCESS;
					Assignment assignment = new Assignment();
					AssignmentType assignmentType = new AssignmentType();
					StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();
					ClassStatus classStatus = new ClassStatus();
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
					gt=gradeAssessmentsService.getGradingType(gradeTypesId);
					long peerReviewBy=0;
					List<AssignmentQuestions> assignmentQuestionst = null;
					long questionId = 0;
					if(assignmentQuestion.getStudentAssignmentStatus() != null){
						assignmentQuestionst = assignAssessmentsService.getAssignmentQuestionsByStudentAssignmentId(assignmentQuestion.getStudentAssignmentStatus().getStudentAssignmentId());
						int inc = 0;
						for (AssignmentQuestions ass: assignmentQuestionst) {
							inc=inc+1;
							if(ass.getQuestions().getQuestionId() == assignmentQuestion.getQuestions().getQuestionId())								
							   questionId = inc;
						}
					}
		        	if(gradeTypesId==1){
						UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);						
			          	assignment.setCreatedBy(userReg.getRegId());
			          	if(assignmentQuestionst.size() > 1 && questionId > 0)
			          		assignmentTitle=homeTitle+""+questionId+"_"+gt.getGradingTypes();
			          	else
			          		assignmentTitle=homeTitle+"_"+gt.getGradingTypes();

		          	}else if(gradeTypesId==2){
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
					StudentAssignmentStatus stdAssignmentStatus = assignAssessmentsService.assignReadingFluencyLearningPracticeHomeWork(assignment, studentAssignmentStatus);
					List<String> contentList = new ArrayList<String>();
					contentList.add(errorsStr);
					if(stdAssignmentStatus.getStatus().equals(WebKeys.TEST_STATUS_SUBMITTED) && stdAssignmentStatus.getGradedStatus().equals(WebKeys.GRADED_STATUS_GRADED)){
						model.addAttribute("assignmentId",stdAssignmentStatus.getAssignment().getAssignmentId());
					}else{
						assignAssessmentsService.assignRFLPTest(contentList,dueDate,studentAssignmentStatus,gradeTypesId,peerReviewBy);//assignAssessmentsService.assignRFLPTest(rflpTest);
						model.addAttribute("assignmentId",stdAssignmentStatus.getAssignment().getAssignmentId());
					}
					helloAjax = WebKeys.TEST_GRADED_SUCCESS;
			   
				}else{
					helloAjax = WebKeys.TEST_GRADED_FAILURE;
			    }
			}
		    model.addAttribute("helloAjax",helloAjax);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
}
