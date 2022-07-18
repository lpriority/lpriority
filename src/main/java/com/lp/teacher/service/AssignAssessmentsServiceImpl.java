package com.lp.teacher.service;

import java.sql.SQLDataException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AddStudentsToClassDAO;
import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.common.dao.AssessmentDAO;
import com.lp.common.service.CommonService;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignLessons;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.ClassStatus;
import com.lp.model.JacQuestionFile;
import com.lp.model.JacTemplate;
import com.lp.model.PerformancetaskGroups;
import com.lp.model.Questions;
import com.lp.model.RegisterForClass;
import com.lp.model.RflpPractice;
import com.lp.model.RflpTest;
import com.lp.model.RtiGroups;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.SubQuestions;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.AssignAssessmentsDAO;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.teacher.dao.IOLReportCardDAO;
import com.lp.utils.WebKeys;
import com.lp.model.FluencyMarksDetails;

@RemoteProxy(name = "assignAssessmentsService")
public class AssignAssessmentsServiceImpl implements AssignAssessmentsService {

	static final Logger logger = Logger.getLogger(AssignAssessmentsServiceImpl.class);

	@Autowired
	private AssignAssessmentsDAO assignAssessmentsDAO;
	@Autowired
	HttpSession session;
	@Autowired
	private AssessmentDAO assessmentDAO;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDao;
	@Autowired
	private AddStudentsToClassDAO addStudentsToClassDAO;
	@Autowired
	private CommonService commonService;
	@Autowired
	private AdminSchedulerDAO adminSchedulerDao;

	@Override
	@RemoteMethod
	public List<AssignLessons> getTeacherAssignLessons(long csId) {
		Teacher teacherObj = (Teacher) session.getAttribute(WebKeys.TEACHER_OBJECT);
		List<AssignLessons> assignLessons = Collections.emptyList();
		try{
			assignLessons = assignAssessmentsDAO.getTeacherAssignLessons(teacherObj, csId);
		} catch (Exception e) {
			logger.error("Error in  getTeacherAssignLessons() of  AssignAssessmentsServiceImpl" + e);
		}	
		return assignLessons;
	}

	@Override
	public List<Questions> getQuestions(long lessonId, long assignmentTypeId, Teacher teacher, String usedFor,long gradeId) {
		List<Questions> questions = Collections.emptyList();
		try{
			questions = assignAssessmentsDAO.getQuestions(lessonId, assignmentTypeId, teacher, usedFor,gradeId);
		} catch (Exception e) {
			logger.error("Error in  getQuestions() of  AssignAssessmentsServiceImpl" + e);
		}	
		return questions;
	}

	@Override
	public List<SubQuestions> getSSQuestions(List<Questions> questions) {
		List<SubQuestions> ssQuestions = new ArrayList<SubQuestions>();
		Map<Long, String> clsMap = new LinkedHashMap<Long, String>();
		try {
			if (questions.size() > 0) {
				for (Questions q : questions) {
					SubQuestions ssQue = new SubQuestions();
					if (!clsMap.containsKey(q.getSubQuestions()
							.getSubQuestionId())) {
						clsMap.put(q.getSubQuestions().getSubQuestionId(), q
								.getSubQuestions().getSubQuestion());
						ssQue.setSubQuestionId(q.getSubQuestions()
								.getSubQuestionId());
						ssQue.setSubQuestion(q.getSubQuestions()
								.getSubQuestion());
						ssQue.setNumOfOptions(q.getSubQuestions()
								.getNumOfOptions());
						ssQuestions.add(ssQue);
					}
				}

			} else {
				logger.error("Error in getSSQuestions() of  AssignAssessmentsServiceImpl");
			}
		} catch (Exception e) {
			logger.error("Error in  getSSQuestions() of  AssignAssessmentsServiceImpl" + e);
		}
		return ssQuestions;
	}

	@Override
	public List<RtiGroups> getRTIGroups(List<RegisterForClass> list) {
		List<RtiGroups> rtiGroups = new ArrayList<RtiGroups>();
		Map<Long, String> rtiMap = new LinkedHashMap<Long, String>();
		try {
			if (list.size() > 0) {
				for (RegisterForClass rfc : list) {
					RtiGroups rti = new RtiGroups();
					if (rfc.getRtiGroups() != null) {
						if (!rtiMap.containsKey(rfc.getRtiGroups()
								.getRtiGroupId())) {
							rtiMap.put(rfc.getRtiGroups().getRtiGroupId(), rfc
									.getRtiGroups().getRtiGroupName());
							rti.setRtiGroupId(rfc.getRtiGroups()
									.getRtiGroupId());
							rti.setRtiGroupName(rfc.getRtiGroups()
									.getRtiGroupName());
							rtiGroups.add(rti);
						}
					}
				}

			} else {
				logger.error("No rti groups available");
			}
		} catch (Exception e) {
			logger.error("Error in getRTIGroups() of  AssignAssessmentsServiceImpl" + e);
		}
		return rtiGroups;
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Override
	public boolean assignAssessments(Assignment assignment, List studentList,
			ArrayList<Long> questionList, long retestId, List groupList) {
		long assignmentId = 0;
		int count;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<StudentAssignmentStatus> studentAssignmentStatusList = new ArrayList<StudentAssignmentStatus>();
		
		List<Questions> questionsList = new ArrayList<Questions>();
		assignment.setDateAssigned(new java.sql.Date(new java.util.Date().getTime()));
		assignment.setAssignStatus(WebKeys.ASSIGN_STATUS_ACTIVE);
		assignment.setCreatedBy(userReg.getRegId());

		if(!studentList.isEmpty()){
			count = studentList.size();
		}else{
			count = groupList.size();
		}
		
		ArrayList peerStudents = new ArrayList<>();
		peerStudents.addAll(studentList);
		
		try {
			int index = 0;
			int cnt = 0; 
			for (int i = 0; i < count; i++) {
				cnt++;
				if(cnt==count){
					cnt = 0;
				}
				Student student = new Student();
				Student peerReviewBy = new Student();
				PerformancetaskGroups perGroup = new PerformancetaskGroups();
				StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();				
				studentAssignmentStatus.setAssignment(assignment);
				if(!studentList.isEmpty()){
					student.setStudentId(Long.valueOf(studentList.get(i).toString()));
					studentAssignmentStatus.setStudent(student);
					peerReviewBy.setStudentId(Long.valueOf(peerStudents.get(cnt).toString()));
				}else{
					perGroup.setPerformanceGroupId(Long.valueOf(groupList.get(i).toString()));
					studentAssignmentStatus.setPerformanceGroup(perGroup);
				}				
				studentAssignmentStatus.setStatus(WebKeys.TEST_STATUS_PENDING);
				studentAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
				if(assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId()==20){
					studentAssignmentStatus.setPeerGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
					studentAssignmentStatus.setSelfGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
					if(count > 1 && (assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId() == 20)){
						studentAssignmentStatus.setPeerReviewBy(peerReviewBy);
					}
					index++;
				}
				studentAssignmentStatusList.add(studentAssignmentStatus);
			}
			if (assignment.getAssignmentType().getAssignmentTypeId() == 14) {
				for (int j = 0; j < questionList.size(); j++) {				
					List<JacTemplate> jacTemplate = new ArrayList<JacTemplate>();
					List<Questions> question = new ArrayList<Questions>();
					
					jacTemplate = assessmentDAO.getJacTemplateByFileId(Long.valueOf(questionList.get(j)));
					for(JacTemplate jt:jacTemplate){
						question = assignAssessmentsDAO.getJacQuestionsByTitleId(jt.getJacTemplateId());
						for (int s = 0; s < question.size(); s++) {
							questionsList.add(question.get(s));
						}
					}					
				}
			} else {
				for (int j = 0; j < questionList.size(); j++) {
					Questions question = new Questions();
					question.setQuestionId(Long.valueOf(questionList.get(j)));
					questionsList.add(question);
				}
			}
			assignAssessmentsDAO.assignAssessments(assignment, studentAssignmentStatusList, questionsList, retestId);
		} catch (Exception e) {
			logger.error("Error in assignAssessments() of  AssignAssessmentsServiceImpl" + e);
			return false;
		}
		return true;
	}

	@Override
	public List<JacQuestionFile> getJacQuestions(Teacher teacher,
			String usedFor, long lessonId) {
		List<JacQuestionFile> jacQuestionFiles = Collections.emptyList();
		try{
			jacQuestionFiles = assignAssessmentsDAO.getJacQuestions(teacher, usedFor, lessonId);
		} catch (Exception e) {
			logger.error("Error in getJacQuestions() of  AssignAssessmentsServiceImpl" + e);
		}	
		return jacQuestionFiles;

	}

	@Override
	public List<Assignment> getPreviousTestDates(long csId, long assignmentTypeId, String assignFor) throws DataException {
		List<Assignment> assignments = Collections.emptyList();
		try {
			assignments = assignAssessmentsDAO.getPreviousTestDates(csId, assignmentTypeId, assignFor);

		} catch (DataException e) {
			logger.error("Error in getPreviousTestDates() of  AssessmentServiceImpl" + e);
			throw new DataException( "Error in getPreviousTestDates() of AssignAssessmentServiceImpl", e);
		}
		return assignments;
	}

	@Override
	public List<Assignment> getAssignmentByTitle(long csId, String title, String usedFor) throws DataException {
		List<Assignment> assignments = Collections.emptyList();
		try {
			assignments = assignAssessmentsDAO.getAssignmentByTitle(csId,title, usedFor);
		} catch (DataException | SQLDataException e) {
			logger.error("Error in getAssignmentByTitle() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getAssignmentByTitle() of AssignAssessmentServiceImpl",e);
		}
		return assignments;
	}
	@Override
	public long checkBenchmarkTitleExists(long benchmarkId, long studentId, long csId)
			throws DataException {
		long assign=0;
		try {
			assign = assignAssessmentsDAO.checkBenchmarkTitleExists(benchmarkId,studentId,csId);
		} catch (Exception e) {
			logger.error("Error in getAssignmentByTitle() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getAssignmentByTitle() of AssignAssessmentServiceImpl",e);
		}
		return assign;
	}
	
	@Override
	public StudentAssignmentStatus assignReadingFluencyLearningPracticeHomeWork(Assignment assignment,StudentAssignmentStatus studentAssignmentStatus) throws DataException {
		return assignAssessmentsDAO.assignReadingFluencyLearningPracticeHomeWork(assignment,studentAssignmentStatus);
	}
	
	@Override
	public boolean assignRFLPTest(List<String> contentList,String dueDate,StudentAssignmentStatus stdAssignmentStatus, long gradingTypesId,long peerReviewBy){
		RflpTest rflpTest = new RflpTest();
		String temp="";
		String[] error={};
		List<RflpPractice> rflpPracticeLt =  new ArrayList<>();
		DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
		try {
			for (String errorsStr : contentList) {
				 error = errorsStr.split(":");
				ArrayList<String> errorWordsList = new ArrayList<String>();
				ArrayList<Long> errorIdList = new ArrayList<Long>();
				long count= 0;
				for(int i = 0; i < error.length; i++){
					if(error[i].trim().length() > 0){
						if(!errorWordsList.contains(error[i])){
							count = count+1;
							temp=error[i];
							temp.replace("^'", "").replace("'$", "");
							 if (temp.endsWith("\'"))
						      {
								 temp = temp.substring(0, temp.length() - 1);
						      }
							if (temp.length() >= 2 && (temp.charAt(0) == '"' || temp.charAt(0) == '“' ||  temp.charAt(temp.length() - 1) == '”' || temp.charAt(temp.length() - 1) == '"'))
							{
								temp=temp.replaceAll("“", "");
								temp=temp.replaceAll("\"", "");
								temp=temp.replaceAll("”", "");
							}
							errorWordsList.add(temp);
							errorIdList.add(count);
						}
					}
				}
				String str= new String();
				for (int i = 0; i < errorWordsList.size(); i++){
					RflpPractice rflpPractice = new RflpPractice();
					
					if(errorWordsList.get(i).trim().length() > 0){
						if (errorWordsList.get(i).endsWith(",") || errorWordsList.get(i).endsWith("?") || errorWordsList.get(i).endsWith("$")|| errorWordsList.get(i).endsWith("@")){
							str = errorWordsList.get(i).substring(0, errorWordsList.get(i).length() - 1);
							if(errorWordsList.get(i).endsWith("$")){
								rflpPractice.setUnknownWord(WebKeys.LP_TRUE);
							}else if(errorWordsList.get(i).endsWith("@")){
								rflpPractice.setSkippedWord(WebKeys.LP_TRUE);
							}
							errorWordsList.remove(i);
							errorWordsList.add(i,str);
					    }
						rflpPractice.setErrorWord(errorWordsList.get(i));
						rflpPractice.setWordNum(errorIdList.get(i));
						rflpPracticeLt.add(rflpPractice);
					}
				}
			}
			rflpTest.setRflpPractice(rflpPracticeLt);
			rflpTest.setDateDue(formatter.parse(dueDate));
			rflpTest.setStudentAssignmentStatus(stdAssignmentStatus);			
			rflpTest.setGradingTypes(gradeAssessmentsDao.getGradingType(gradingTypesId));
			if(gradingTypesId==3){
			rflpTest.setPeerReviewBy(addStudentsToClassDAO.getStudent(peerReviewBy));}
		} catch (ParseException e) {
			logger.error("Error in assignRFLPTest() of  AssessmentServiceImpl"+ e);
		}
		return assignAssessmentsDAO.assignRFLPTest(rflpTest);
	}
	
	@Override
	public List<AssignmentQuestions> getAssignmentQuestionsByStudentAssignmentId(long studentAssignmentId){
		List<AssignmentQuestions> assignmentQuestions = Collections.emptyList();
		try{
			assignmentQuestions = assignAssessmentsDAO.getAssignmentQuestionsByStudentAssignmentId(studentAssignmentId);
		} catch (Exception e) {
			logger.error("Error in getAssignmentQuestionsByStudentAssignmentId() of  AssessmentServiceImpl"+ e);
		}	
		return assignmentQuestions;
	}
	
	public StudentAssignmentStatus getStudentAssignmentStatus(long studentAssignmentId){
		StudentAssignmentStatus sAssignmentStatus = new StudentAssignmentStatus();
		try{
			sAssignmentStatus = assignAssessmentsDAO.getStudentAssignmentStatus(studentAssignmentId);
		} catch (Exception e) {
			logger.error("Error in getStudentAssignmentStatus(long studentAssignmentId) of  AssessmentServiceImpl"+ e);
		}	
		return sAssignmentStatus;
	}

	@Override
	public Assignment assignmentByTitle(String title, long assignmentTypeId, String usedFor) throws SQLDataException {
		Assignment assignment = new Assignment();
		try{
			assignment = assignAssessmentsDAO.assignmentByTitle(title, assignmentTypeId, usedFor);
		} catch (Exception e) {
			logger.error("Error in assignmentByTitle() of  AssessmentServiceImpl"+ e);
		}	
		return assignment;
	}
	
	public StudentAssignmentStatus getStudentAssignmentStatus(long assignmentId, long studentId){
		StudentAssignmentStatus sAssignmentStatus = new StudentAssignmentStatus();
		try{
			sAssignmentStatus = assignAssessmentsDAO.getStudentAssignmentStatus(assignmentId, studentId);
		} catch (Exception e) {
			logger.error("Error in getStudentAssignmentStatus(long assignmentId, long studentId) of  AssessmentServiceImpl"+ e);
		}	
		return sAssignmentStatus;
	}
	@Override
	public void updateStudentTests(){
		try{
			List<ClassStatus> classStatus = assignAssessmentsDAO.getClasses();		
			for(ClassStatus cStatus : classStatus){
				List<StudentAssignmentStatus> studentAssignmentStatusList = new ArrayList<StudentAssignmentStatus>();
				List<StudentAssignmentStatus> studentList = assignAssessmentsDAO.getStudentTests(cStatus.getCsId());
				List<StudentAssignmentStatus> studentList2 = new ArrayList<StudentAssignmentStatus>();
				studentList2.addAll(studentList);
				int count =0;
				for (int index=0; index<studentList.size()-1;index++) {
					count++;
					StudentAssignmentStatus studentAssignmentStatus = studentList.get(index);
					if(count==studentList.size()){
						count = 0;
					}
					Student student = studentList.get(count).getStudent();
					studentAssignmentStatus.setPeerReviewBy(student);
					studentAssignmentStatusList.add(studentAssignmentStatus);			
				}
				assignAssessmentsDAO.saveStudentTests(studentAssignmentStatusList);
			}
		}
		catch (Exception e) {
			logger.error("Error in updateStudentTests of  AssessmentServiceImpl"+ e);
		}	
	}
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Override
	public boolean assignRTF(Assignment assignment, ArrayList<String> csIds,
			ArrayList<Long> questionList) {
		long assignmentId = 0;
		
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		Date currentDate = new Date();
		try {
		for(int x=0;x<csIds.size();x++){
			Assignment assignmentObj=new Assignment();
			assignmentObj=assignment;
			assignment.setAssignmentId(0);
			int count=0;
			List<Student> studentList=new ArrayList<Student>();
			studentList=commonService.getStudentsBySection(Long.valueOf(csIds.get(x).toString()));
		List<StudentAssignmentStatus> studentAssignmentStatusList = new ArrayList<StudentAssignmentStatus>();
		List<Questions> questionsList = new ArrayList<Questions>();
		assignmentObj.setDateAssigned(currentDate);
		assignmentObj.setAssignStatus(WebKeys.ASSIGN_STATUS_ACTIVE);
		assignmentObj.setCreatedBy(userReg.getRegId());
		assignmentObj.setClassStatus(adminSchedulerDao.getclassStatus(Long.valueOf(csIds.get(x).toString())));
		
		if(!studentList.isEmpty()){
			count = studentList.size();
		}
		
		ArrayList<Student> peerStudents = new ArrayList<Student>();
		peerStudents.addAll(studentList);
		
		
			int index = 0;
			int cnt = 0; 
			for (int i = 0; i < count; i++) {
				cnt++;
				if(cnt==count){
					cnt = 0;
				}
				Student student = new Student();
				Student peerReviewBy = new Student();
				PerformancetaskGroups perGroup = new PerformancetaskGroups();
				StudentAssignmentStatus studentAssignmentStatus = new StudentAssignmentStatus();				
				studentAssignmentStatus.setAssignment(assignmentObj);
				if(!studentList.isEmpty()){
					student.setStudentId(Long.valueOf(studentList.get(i).getStudentId()));
					studentAssignmentStatus.setStudent(student);
					peerReviewBy.setStudentId(Long.valueOf(peerStudents.get(cnt).getStudentId()));
				}	
				studentAssignmentStatus.setStatus(WebKeys.TEST_STATUS_PENDING);
				studentAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
				if(assignmentObj.getAssignmentType().getAssignmentTypeId() == 8 || assignmentObj.getAssignmentType().getAssignmentTypeId()==20){
					studentAssignmentStatus.setPeerGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
					studentAssignmentStatus.setSelfGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
					if(count > 1 && (assignmentObj.getAssignmentType().getAssignmentTypeId() == 8 || assignmentObj.getAssignmentType().getAssignmentTypeId() == 20)){
						studentAssignmentStatus.setPeerReviewBy(peerReviewBy);
					}
					index++;
				}
				studentAssignmentStatusList.add(studentAssignmentStatus);
			}
			
				for (int j = 0; j < questionList.size(); j++) {
					Questions question = new Questions();
					question.setQuestionId(Long.valueOf(questionList.get(j)));
					questionsList.add(question);
				}
			
			boolean sss=assignAssessmentsDAO.assignRTF(assignmentObj, studentAssignmentStatusList, questionsList);
		}
		} catch (Exception e) {
			logger.error("Error in assignRTF() of  AssignAssessmentsServiceImpl" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public long checkBenchmarkTitleExistsForSection(long benchmarkId, long csId)
			throws DataException {
		long assign=0;
		try {
			assign = assignAssessmentsDAO.checkBenchmarkTitleExistsForSection(benchmarkId,csId);
		} catch (Exception e) {
			logger.error("Error in getAssignmentByTitle() of  AssessmentServiceImpl"+ e);
			throw new DataException("Error in getAssignmentByTitle() of AssignAssessmentServiceImpl",e);
		}
		return assign;
	}
	@Override
	public boolean assignRFLPTestFromTeacherGrading(List<FluencyMarksDetails> contentList,String dueDate,StudentAssignmentStatus stdAssignmentStatus, long gradingTypesId,long peerReviewBy){
		RflpTest rflpTest = new RflpTest();
		List<RflpPractice> rflpPracticeLt =  new ArrayList<>();
		DateFormat formatter = new SimpleDateFormat(WebKeys.DATE_FORMATE);
		try {
						
				String str= new String();
				int i=0;
				for (FluencyMarksDetails fluMarDet : contentList){
					RflpPractice rflpPractice = new RflpPractice();
					i++;
					
					if(fluMarDet.getErrorWord().trim().length() > 0){
						str=fluMarDet.getErrorWord();
						if (fluMarDet.getErrorWord().endsWith(",") || fluMarDet.getErrorWord().endsWith("?") || fluMarDet.getErrorWord().endsWith("$")|| fluMarDet.getErrorWord().endsWith("@")){
							str = fluMarDet.getErrorWord().substring(0, fluMarDet.getErrorWord().length() - 1);
							}
							str=str.replaceAll("“", "");
							str=str.replaceAll("\"", "");
							str=str.replaceAll("”", "");
							str=str.replaceAll("'", "");
												
							if(fluMarDet.getUnknownWord()!=null){
								 if(fluMarDet.getUnknownWord().equalsIgnoreCase("true"))
								rflpPractice.setUnknownWord(WebKeys.LP_TRUE);
							}
							if(fluMarDet.getSkippedWord()!=null){
								if(fluMarDet.getSkippedWord().equalsIgnoreCase("true")){
									rflpPractice.setSkippedWord(WebKeys.LP_TRUE);
							}
							}
						
							
						rflpPractice.setErrorWord(str);
						rflpPractice.setWordNum(i);
						rflpPracticeLt.add(rflpPractice);
					}
				}
			
			rflpTest.setRflpPractice(rflpPracticeLt);
			rflpTest.setDateDue(formatter.parse(dueDate));
			rflpTest.setStudentAssignmentStatus(stdAssignmentStatus);			
			rflpTest.setGradingTypes(gradeAssessmentsDao.getGradingType(gradingTypesId));
			if(gradingTypesId==3){
			rflpTest.setPeerReviewBy(addStudentsToClassDAO.getStudent(peerReviewBy));}
		} catch (ParseException e) {
			logger.error("Error in assignRFLPTest() of  AssessmentServiceImpl"+ e);
		}
		return assignAssessmentsDAO.assignRFLPTest(rflpTest);
	}
}
