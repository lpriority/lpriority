
package com.lp.teacher.service;

import java.io.File;
import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.lp.admin.dao.AddStudentsToClassDAO;
import com.lp.appadmin.dao.AssignmentTypeDAO;
import com.lp.appadmin.dao.GradeEventsDAO;
import com.lp.common.service.FileService;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicGrades;
import com.lp.model.AssignActivity;
import com.lp.model.AssignLessons;
import com.lp.model.AssignmentQuestions;
import com.lp.model.AssignmentType;
import com.lp.model.BenchmarkResults;
import com.lp.model.ClassStatus;
import com.lp.model.CompositeChartValues;
import com.lp.model.Compositechart;
import com.lp.model.Gradeevents;
import com.lp.model.ItemAnalysis;
import com.lp.model.Questions;
import com.lp.model.RegisterForClass;
import com.lp.model.Report;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentAttendance;
import com.lp.model.StudentAttendanceByStatus;
import com.lp.model.StudentCompositeActivityScore;
import com.lp.model.StudentCompositeActivityScoreId;
import com.lp.model.StudentCompositeLessonScore;
import com.lp.model.StudentCompositeLessonScoreId;
import com.lp.model.StudentCompositeProjectScore;
import com.lp.model.StudentCompositeProjectScoreId;
import com.lp.teacher.dao.GradeAssessmentsDAO;
import com.lp.teacher.dao.GradeBookDAO;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "gradeBookService")
@Component("gradeBookService")
public class GradeBookServiceImpl implements GradeBookService {
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDao;
	@Autowired
	private AddStudentsToClassDAO addStudentsToClassDAO;
	@Autowired
	private GradeBookDAO gradeBookDao;
	
	@Autowired
	private FileService fileservice;
	
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;	
	@Autowired
	private AssignmentTypeDAO assignmentTypeDAO;
	@Autowired
	private GradeEventsDAO	gradeEventsDAO;
	@Autowired
	private GradeAssessmentsDAO	gradeAssessmentsDAO;
	
	static final Logger logger = Logger.getLogger(GradeBookServiceImpl.class);
	
	@Override
	public List<RegisterForClass> getGradeStudentList(long csId) throws DataException {
		List<RegisterForClass> studentList = Collections.emptyList();
		try{
			studentList =   addStudentsToClassDAO.getClassStrengthByCsId(csId);
		}catch(DataException e){
			logger.error("Error in getGradeStudentList() of GradeBookServiceImpl"+ e);
			e.printStackTrace();	
			throw new DataException("Error in getGradeStudentList() of GradeBookServiceImpl",e);
		}
		return studentList;
	}

	@Override
	public HashMap<Long,Double> getStudentAssignmentsByCsId(long csId, String usedFor) throws DataException {
		List<Object[]> studentAssignments = new ArrayList<Object[]>();
		HashMap<Long,Double> hm=new HashMap<Long,Double>();  
		try{
			studentAssignments =   gradeBookDao.getStudentAssignmentsByCsId(csId, usedFor);
			for(Object[] o : studentAssignments){
				hm.put((Long) o[0], (Double) o[1]);
			}
		}catch(DataException | SQLDataException e){
			logger.error("Error in getStudentAssignmentsByCsId() of GradeBookServiceImpl"+ e);
			e.printStackTrace();	
			throw new DataException("Error in getStudentAssignmentsByCsId() of GradeBookServiceImpl",e);
		}
		return hm;
	}

	@Override
	public Student getStudentById(long studentId) throws DataException {		
		Student Student = new Student();
		try{
			Student =   addStudentsToClassDAO.getStudent(studentId);
		}catch(DataException e){
			logger.error("Error in getStudentById() of GradeBookServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentById() of GradeBookServiceImpl",e);
		}
		return Student;
	}

	@Override
	public void updateStudentGrades(List<StudentAssignmentStatus> studentTestList) throws DataException {
		try{
			for(StudentAssignmentStatus studentAssignmentStatus : studentTestList) {
			studentAssignmentStatus.setAcademicGrade(gradeAssessmentsDAO.getAcademicGradeByPercentage(studentAssignmentStatus.getPercentage()));
			}		
			gradeBookDao.updateStudentGrades(studentTestList);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getStudentById() of GradeBookServiceImpl"+ e);
			e.printStackTrace();	
			throw new DataException("Error in getStudentById() of GradeBookServiceImpl",e);
		}		
	}

	@Override
	public List<AssignmentQuestions> getAssignmentQuestion(long sasId) throws DataException {
		List<AssignmentQuestions> assignQuestion = new ArrayList<AssignmentQuestions>();
		try{
			assignQuestion = gradeBookDao.getAssignmentQuestion(sasId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getAssignmentQuestion() of GradeBookServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getAssignmentQuestion() of GradeBookServiceImpl",e);
		}
		return assignQuestion;
	}

	@Override
	public HashMap<Long, Double> getStudentAssignmentsExcludePerformance(
			long csId, String usedFor, String fromDate, String toDate) throws DataException {
		List<Object[]> studentAssignments = new ArrayList<Object[]>();
		HashMap<Long,Double> hm=new HashMap<Long,Double>();  
		try{
			studentAssignments =   gradeBookDao.getStudentAssignmentsExcludePerformance(csId, usedFor,fromDate,toDate);
			for(Object[] o : studentAssignments){
				hm.put((Long) o[0], (Double) o[1]);
			}
		}catch(DataException | SQLDataException e){
			logger.error("Error in getStudentAssignmentsByCsId() of GradeBookServiceImpl"+ e);
			e.printStackTrace();	
			throw new DataException("Error in getStudentAssignmentsByCsId() of GradeBookServiceImpl",e);
		}
		return hm;
	}

	@Override
	public HashMap<Long, Double> getStudentPerformanceAssessments(long csId, String fromDate, String toDate)
			throws DataException {
		List<Object[]> studentAssignments = new ArrayList<Object[]>();
		@SuppressWarnings("unused")
		List<Object[]> studentAssignments1 = new ArrayList<Object[]>();
		HashMap<Long,Double> hm=new HashMap<Long,Double>();  
		try{
			studentAssignments =   gradeBookDao.getStudentPerformanceAssessments(csId,fromDate,toDate);
			studentAssignments1 =   gradeBookDao.getClassAttendance(csId,fromDate,toDate);
			for(Object[] ob : studentAssignments){
				hm.put((Long) ob[0], (Double) ob[1]);
			}
		}catch(DataException | SQLDataException e){
			logger.error("Error in getStudentPerformanceAssessments() of GradeBookServiceImpl"+ e);
			e.printStackTrace();	
			throw new DataException("Error in getStudentPerformanceAssessments() of GradeBookServiceImpl",e);
		}
		return hm;
	}
	
	@Override
	public HashMap<Long, StudentAttendance> getClassAttendance(long csId, String fromDate, String toDate)
			throws DataException {
		List<Object[]> studentAttendance = new ArrayList<Object[]>();
		HashMap<Long,StudentAttendance> hMap=new HashMap<Long,StudentAttendance>();  
		try{
			studentAttendance =   gradeBookDao.getClassAttendance(csId,fromDate,toDate);
			
			for(Object[] ob : studentAttendance){
				StudentAttendance sa = new StudentAttendance();
				if(hMap.containsKey((Long) ob[0])){
					sa = hMap.get((Long) ob[0]);
				}else{
					sa.setStudentId((Long) ob[0]);
				}	
				String status = (String) ob[1];
				if(status.equalsIgnoreCase("Present")){
					sa.setPresentCount((Long) ob[2]);
				}
				if(status.equalsIgnoreCase("Absent")){
					sa.setAbsentCount((Long) ob[2]);
				}
				if(status.equalsIgnoreCase("ExcusedAbsent")){
					sa.setExcusedAbsentCount((Long) ob[2]);
				}
				if(status.equalsIgnoreCase("Tardy")){
					sa.setTardyCount((Long) ob[2]);
				}
				if(status.equalsIgnoreCase("ExcusedTardy")){
					sa.setExcusedTardyCount((Long) ob[2]);
				}
				hMap.put((Long) ob[0], sa);
			}
		}catch(DataException | SQLDataException e){
			logger.error("Error in getStudentPerformanceAssessments() of GradeBookServiceImpl"+ e);
			e.printStackTrace();			
			throw new DataException("Error in getStudentPerformanceAssessments() of GradeBookServiceImpl",e);
		}
		return hMap;
	}
	
	@Override
	public ItemAnalysis getItemAnalysisReports(long assignmentId) throws DataException {
		ItemAnalysis itemAnalysis = new ItemAnalysis();
		List<Object[]> percentageList = new ArrayList<Object[]>();
		List<AssignmentQuestions> assignmentQuestions = new ArrayList<AssignmentQuestions>();
		List<Questions> questionList = new ArrayList<Questions>();
		List<Long> optionAChoices = new ArrayList<Long>();
		List<Long> optionBChoices = new ArrayList<Long>();
		List<Long> optionCChoices = new ArrayList<Long>();
		List<Long> optionDChoices = new ArrayList<Long>();
		List<Long> optionEChoices = new ArrayList<Long>();
		List<Long> questionCounts = new ArrayList<Long>();
		List<Long> correctChoices = new ArrayList<Long>();
		List<Long> wrongChoices = new ArrayList<Long>();

		long optionAChoice = 0;
		long optionBChoice = 0;
		long optionCChoice = 0;
		long optionDChoice = 0;
		long optionEChoice = 0;
		long correctChoice = 0;
		long wrongChoice = 0;
		long questionCount = 0;
		Questions question = null;
		float highestPercentage = 0;
		float lowestPercentage = 0;
		long highestScore =  0;
		long lowestScore =  0;
		long maxScore = 0;
		try {
			percentageList = gradeBookDao.getTestPercentage(assignmentId);
			if (!percentageList.isEmpty()) {
				lowestScore = Long.parseLong(percentageList.get(0)[2]
						.toString());
				maxScore = Long.parseLong(percentageList.get(0)[2]
						.toString());
			}
			for (Object[] o : percentageList) {
				if (highestScore < (long) o[1]) {
					highestScore = (long) o[1];
					//highestPercentage = (float) o[2];
				}
				if (lowestScore > (long) o[1]) {
					lowestScore = (long) o[1];
					//lowestPercentage = (float) o[2];
				}
			}
			highestPercentage = (highestScore/(float)maxScore)*(float)100;
			lowestPercentage = (lowestScore/(float)maxScore)*(float)100;
			itemAnalysis.setHighestScore(highestScore);
			itemAnalysis.setLowestScore(lowestScore);
			itemAnalysis.setHighestPercentage(highestPercentage);
			itemAnalysis.setLowestPercentage(lowestPercentage);

			assignmentQuestions = gradeBookDao
					.getItemAnalysisReports(assignmentId);
			if (!assignmentQuestions.isEmpty()) {
				AssignmentQuestions assignmentQuestions2 = assignmentQuestions
						.get(0);
				question = assignmentQuestions2.getQuestions();
				itemAnalysis.setExamDate(new SimpleDateFormat("MM/dd/yyyy")
						.format(assignmentQuestions2
								.getStudentAssignmentStatus().getAssignment()
								.getDateAssigned()));
				itemAnalysis.setInstructor(assignmentQuestions2
						.getStudentAssignmentStatus().getAssignment()
						.getClassStatus().getTeacher().getUserRegistration()
						.getFirstName()
						+ " "
						+ assignmentQuestions2.getStudentAssignmentStatus()
								.getAssignment().getClassStatus().getTeacher()
								.getUserRegistration().getLastName());
				/*itemAnalysis.setLessonName(assignmentQuestions2
						.getStudentAssignmentStatus().getAssignment()
						.getLesson().getLessonName());*/
			}
			int count = 0;
			for (AssignmentQuestions assQuestion : assignmentQuestions) {
				count++;
				if (question.getQuestionId() != assQuestion.getQuestions()
						.getQuestionId()) {
					questionList.add(question);
					optionAChoices.add(optionAChoice);
					optionBChoices.add(optionBChoice);
					optionCChoices.add(optionCChoice);
					optionDChoices.add(optionDChoice);
					optionEChoices.add(optionEChoice);
					correctChoices.add(correctChoice);
					wrongChoices.add(wrongChoice);
					questionCounts.add(questionCount);
					question = assQuestion.getQuestions();
					optionAChoice = 0;
					optionBChoice = 0;
					optionCChoice = 0;
					optionDChoice = 0;
					optionEChoice = 0;
					correctChoice = 0;
					wrongChoice = 0;
					questionCount = 1;
				}
				else{
					questionCount++;
				}
				if (question.getQuestionId() == assQuestion.getQuestions().getQuestionId() && 
						WebKeys.OPTION_A.equals(assQuestion.getAnswer())) {
					optionAChoice++;
				} else if (question.getQuestionId() == assQuestion.getQuestions().getQuestionId() && 
						WebKeys.OPTION_B.equals(assQuestion.getAnswer())) {
					optionBChoice++;
				} else if (question.getQuestionId() == assQuestion.getQuestions().getQuestionId() && 
						WebKeys.OPTION_C.equals(assQuestion.getAnswer())) {
					optionCChoice++;
				} else if (question.getQuestionId() == assQuestion.getQuestions().getQuestionId() && 
						WebKeys.OPTION_D.equals(assQuestion.getAnswer())) {
					optionDChoice++;
				} else if (question.getQuestionId() == assQuestion.getQuestions().getQuestionId() &&
						WebKeys.OPTION_E.equals(assQuestion.getAnswer())) {
					optionEChoice++;
				}
				if (question.getQuestionId() == assQuestion.getQuestions().getQuestionId() &&
						question.getAnswer().equals(assQuestion.getAnswer())) {
					correctChoice++;
				} else if (question.getQuestionId() == assQuestion.getQuestions().getQuestionId() && 
						!question.getAnswer().equals(assQuestion.getAnswer())) {
					wrongChoice++;
				}
				if (assignmentQuestions.size() == count) {
                    questionList.add(question);
                    optionAChoices.add(optionAChoice);
                    optionBChoices.add(optionBChoice);
                    optionCChoices.add(optionCChoice);
                    optionDChoices.add(optionDChoice);
                    optionEChoices.add(optionEChoice);
                    correctChoices.add(correctChoice);
                    wrongChoices.add(wrongChoice);
                    questionCounts.add(questionCount);
				}
			}
			itemAnalysis.setTotalPossibles(questionList.size());
			itemAnalysis.setQuestions(questionList);
			itemAnalysis.setCorrectChoices(correctChoices);
			itemAnalysis.setWrongChoices(wrongChoices);
			itemAnalysis.setOptionAChoices(optionAChoices);
			itemAnalysis.setOptionBChoices(optionBChoices);
			itemAnalysis.setOptionCChoices(optionCChoices);
			itemAnalysis.setOptionDChoices(optionDChoices);
			itemAnalysis.setOptionEChoices(optionEChoices);
			itemAnalysis.setQuestionCounts(questionCounts);
		} catch (DataException e) {
			logger.error("Error in getItemAnalysisReport() of GradeBookServiceImpl" + e);
			e.printStackTrace();
		}
		return itemAnalysis;
	}

	@Override
	public void submitReportChanges(Report report) throws DataException {
		try{
			gradeBookDao.submitReportChanges(report);			
		}catch(DataException | SQLDataException e){
			logger.error("Error in submitReportChanges() of GradeBookServiceImpl"+ e);	
			throw new DataException(e.getMessage());
		}	
	}

	@Override
	public List<Report> getReportDates(long csId, long studentId) throws DataException {
		List<Report> reports = new ArrayList<Report>();
		try{
			reports = gradeBookDao.getReportDates(csId, studentId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getReportDates() of GradeBookServiceImpl"+ e);
			throw new DataException("Error in getReportDates() of GradeBookServiceImpl",e);
		}
		return reports;
	}
	
	@Override
	public CompositeChartValues getCompositeChartValues(Model model, long csId) throws DataException {
		List<Compositechart> compositecharts = new ArrayList<Compositechart>();
		CompositeChartValues compositeChartValues = new CompositeChartValues();
		ClassStatus classStatus = new ClassStatus();
		classStatus.setCsId(csId);
		try {
			compositecharts = gradeBookDao.getCompositeChartValues(csId);
			if (compositecharts.isEmpty()) {
				List<Gradeevents> gradeEvents = gradeEventsDAO.getGradeEventList();
				List<AssignmentType> list = assignmentTypeDAO.getAssignmentTypeList();
				for (Gradeevents gradeevent : gradeEvents) {
					for (AssignmentType assignmentType : list) {
						if(((gradeevent.getEventName().equals(WebKeys.LP_USED_FOR_HOMEWORKS) || gradeevent.getEventName().equals(WebKeys.LP_USED_FOR_ASSESSMENT)) 
								&& assignmentType.getUsedFor().equals(WebKeys.ALL_ASSIGNMENT_TYPE)) || gradeevent.getEventName().equals(assignmentType.getUsedFor()) || 
								(gradeevent.getEventName().equals(WebKeys.LP_USED_FOR_ASSESSMENT) && assignmentType.getAssignmentType().equals(WebKeys.ASSIGNMENT_TYPE_JAC_TEMPLATE))){
							Compositechart compositeChart = new Compositechart();
							compositeChart.setGradeevents(gradeevent);
							compositeChart.setAssignmentType(assignmentType);
							compositeChart.setClassStatus(classStatus);
							compositecharts.add(compositeChart);
						}		
					}
				}
			}
			compositeChartValues.setCompositecharts(compositecharts);
		} catch (DataException e) {
			logger.error("Error in getCompositeChartValues() of GradeBookServiceImpl"+ e);
			throw new DataException("Error in submitReportChanges() of GradeBookServiceImpl", e);
		}
		return compositeChartValues;
	}

	@Override
	public boolean saveCompositeChartValues(CompositeChartValues compositeChartValues) throws DataException {
		boolean flag = false;
		try {
			flag = gradeBookDao.saveCompositeChartValues(compositeChartValues.getCompositecharts());
		} catch (DataException e) {
			logger.error("Error in getCompositeChartValues() of GradeBookServiceImpl"+ e);
			throw new DataException("Error in submitReportChanges() of GradeBookServiceImpl", e);
		}
		return flag;
	}
	@Override
	public List<BenchmarkResults> getBanchResults(long studentId, long csId) throws DataException {
		List<BenchmarkResults> benchResults = new ArrayList<BenchmarkResults>();
		try{
			benchResults = gradeBookDao.getBanchResults(studentId,csId);
		}catch(DataException | SQLDataException e){
			logger.error("Error in getBanchResults() of GradeBookServiceImpl"+ e);
			throw new DataException("Error in getBanchResults() of GradeBookServiceImpl",e);
		}
		return benchResults;
	}

	@Override
	public List<AssignmentQuestions> getBenchAssignments(long studentId, long csId) throws DataException {
		List<AssignmentQuestions> benchAssignments = new ArrayList<AssignmentQuestions>();
		Student student = new Student();
		try{
			student = addStudentsToClassDAO.getStudent(studentId);
			benchAssignments = gradeBookDao.getBenchAssignments(studentId,csId);
			String dirLocation = FileUploadUtil.getUploadFilesPath(student.getUserRegistration(), request);
			for(AssignmentQuestions aq: benchAssignments){
				File[] aFiles = null;
				String fullPath = dirLocation+File.separator+WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+aq.getAssignmentQuestionsId();
				aFiles = fileservice.getFolderFiles(fullPath);
				if(aFiles != null){
					for(File af : aFiles){
						if(af.getName().equalsIgnoreCase(WebKeys.FLUENCY_FILE_NAME))
						aq.setBAudioPath(af.getPath());
						if(af.getName().equalsIgnoreCase(WebKeys.RETELL_FILE_NAME))
							aq.setBRetellpath(af.getPath());
						}
				}
			}
		}catch(DataException | SQLDataException e){
			logger.error("Error in getBenchAssignments() of GradeBookServiceImpl"+ e);
			throw new DataException("Error in getBenchAssignments() of GradeBookServiceImpl",e);
		}
		return benchAssignments;
	}
	
	@Override
	public StudentAttendanceByStatus getStudentAttendance(long csId, long studentId)
			throws SQLDataException {
		StudentAttendanceByStatus studentAttendance = new StudentAttendanceByStatus();		
		try {
			studentAttendance = gradeBookDao.getStudentAttendance(csId, studentId);
		} catch (HibernateException e) {
			logger.error("Error in getStudentAttendance() of GradeBookServiceImpl", e);
			throw new DataException("Error in getStudentAttendance() of GradeBookServiceImpl",e);
		}
		return studentAttendance;
	}
	
	@Override
	public List<StudentAssignmentStatus> getStudentTests(long csId, long studentId) throws SQLDataException {
		List<StudentAssignmentStatus> studentTests = Collections.emptyList();	
		try {
			studentTests = gradeBookDao.getTests(csId, studentId);
		}catch (HibernateException e) {
			logger.error("Error in getStudentTests() of GradeBookServiceImpl", e);
			throw new DataException("Error in getStudentTests() of GradeBookServiceImpl",e);
		}
		return studentTests;
	}
	
	@Override
	public List<AssignLessons> getAssignedLesson(long csId, long studentId) throws SQLDataException {
		List<AssignLessons> assignedLessons = null;
		try {
			assignedLessons = gradeBookDao.getAssignedLesson(csId,studentId) ; //curriculumDAO.getAssignedLesson(csId);
		} catch (HibernateException e) {
			logger.error("Error in getAssignedLesson() of GradeBookServiceImpl", e);
			e.printStackTrace();
			throw new DataException("Error in getAssignedLesson() of GradeBookServiceImpl",e);
			
		}
		return assignedLessons;
	}

	@Override
	public List<AssignActivity> getAssignedActivities(long csId, long studentId) throws SQLDataException {
		List<AssignActivity> assignedActivities = null;
		try {
			assignedActivities = gradeBookDao.getAssignedActivities(csId,studentId); //curriculumDAO.getAssignedActivities(csId); 
			
		} catch (HibernateException e) {
			logger.error("Error in getAssignedActivities() of GradeBookServiceImpl", e);
			e.printStackTrace();
			throw new DataException("Error in getAssignedActivities() of GradeBookServiceImpl",e);
			
		}
		return assignedActivities;
	}

	@Override
	public StudentCompositeProjectScore getStudentCompositeProjectScore(
			long csId, long studentId) throws SQLDataException {
		StudentCompositeProjectScore projectScore = new StudentCompositeProjectScore();
		try {
			projectScore = gradeBookDao.getStudentCompositeProjectScore(csId, studentId);
		}catch (HibernateException e) {
			logger.error("Error in getStudentCompositeProjectScore() of GradeBookServiceImpl", e);
			throw new DataException("Error in getStudentCompositeProjectScore() of GradeBookServiceImpl",e);
		}
		return projectScore;
	}

	@Override
	public boolean saveStudentCompositeChartValues(long studentId, long csId,
			String[] assignLessonIds, String[] lessonScores,
			String[] assignActivityIds, String[] activityScores, int projectScore) throws SQLDataException {
		Student student = new Student();
		student.setStudentId(studentId);
		ClassStatus classStatus = new ClassStatus();
		classStatus.setCsId(csId);
		int percentage = 0;
		List<StudentCompositeActivityScore> studentCompositeActivityScores = new ArrayList<>();
		List<StudentCompositeLessonScore> studentCompositeLessonScores = new ArrayList<>();
		
		if(assignLessonIds!=null){
			for(int lessonCount =0; lessonCount<assignLessonIds.length; lessonCount++){
				StudentCompositeLessonScore lessonScore = new StudentCompositeLessonScore();
				StudentCompositeLessonScoreId compositeLessonScoreId = new StudentCompositeLessonScoreId();
				compositeLessonScoreId.setAssignLessonId(Long.parseLong(assignLessonIds[lessonCount]));
				compositeLessonScoreId.setStudentId(studentId);
				AssignLessons assignLessons = new AssignLessons();
				assignLessons.setAssignId(Long.parseLong(assignLessonIds[lessonCount]));
				lessonScore.setStudent(student);
				percentage = Integer.parseInt(lessonScores[lessonCount]);
				lessonScore.setAssignLessons(assignLessons);
				lessonScore.setScore(percentage);
				AcademicGrades academicGrades=gradeAssessmentsDao.getAcademicGradeByPercentage(percentage);
				lessonScore.setAcademicGrades(academicGrades);
				lessonScore.setId(compositeLessonScoreId);
				studentCompositeLessonScores.add(lessonScore);
			}	
		}
		if(assignActivityIds!=null){
			for(int activityCount =0; activityCount<assignActivityIds.length; activityCount++){
				StudentCompositeActivityScore activityScore = new StudentCompositeActivityScore();
				StudentCompositeActivityScoreId activityScoreId = new StudentCompositeActivityScoreId();
				activityScoreId.setAssignActivityId(Long.parseLong(assignActivityIds[activityCount]));
				activityScoreId.setStudentId(studentId);
				activityScore.setStudent(student);
				percentage = Integer.parseInt(activityScores[activityCount]);
				activityScore.setScore(percentage);
				AcademicGrades academicGrades=gradeAssessmentsDao.getAcademicGradeByPercentage(percentage);
				activityScore.setAcademicGrades(academicGrades);
				AssignActivity assignActivity = new AssignActivity();
				assignActivity.setAssignActivityId(Long.parseLong(assignActivityIds[activityCount]));
				activityScore.setAssignActivity(assignActivity);
				activityScore.setId(activityScoreId);
				studentCompositeActivityScores.add(activityScore);
			}
		}
		StudentCompositeProjectScore studentCompositeProjectScore = new StudentCompositeProjectScore();
		StudentCompositeProjectScoreId studentCompositeProjectScoreId = new StudentCompositeProjectScoreId();
		studentCompositeProjectScoreId.setCsId(csId);
		studentCompositeProjectScoreId.setStudentId(studentId);
		studentCompositeProjectScore.setClassStatus(classStatus);
		studentCompositeProjectScore.setStudent(student);
		studentCompositeProjectScore.setScore(projectScore);
		studentCompositeProjectScore.setId(studentCompositeProjectScoreId);
		return gradeBookDao.saveStudentCompositeChartValues(studentCompositeLessonScores, studentCompositeActivityScores, studentCompositeProjectScore);
	}

	@Override
	public boolean saveParentComments(long reportId, String comment) throws DataException {
		boolean status = false;
		try{
			status = gradeBookDao.saveParentComments(reportId, comment);
		}catch(DataException | SQLDataException e){
			logger.error("Error in saveParentComments() of GradeBookServiceImpl", e);
			throw new DataException("Error in saveParentComments() of GradeBookServiceImpl",e);
		}
		return status;
	}
}
