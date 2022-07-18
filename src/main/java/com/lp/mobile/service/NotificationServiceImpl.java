package com.lp.mobile.service;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.admin.service.ReadRegReviewResultsService;
import com.lp.common.service.FileService;
import com.lp.common.service.GoalSettingToolService;
import com.lp.common.service.ReadingSkillsService;
import com.lp.custom.exception.DataException;
import com.lp.mobile.dao.NotificationDAO;
import com.lp.mobile.model.ActivityQuizQuestions;
import com.lp.mobile.model.AnnouncementApp;
import com.lp.mobile.model.AttendanceApp;
import com.lp.mobile.model.ChildDashboard;
import com.lp.mobile.model.EventApp;
import com.lp.mobile.model.ScheduledClass;
import com.lp.mobile.model.StarScoresReports;
import com.lp.mobile.model.StudentActivities;
import com.lp.mobile.model.StudentGoalReports;
import com.lp.mobile.model.StudentReadingRegister;
import com.lp.mobile.model.TestResultFluency;
import com.lp.mobile.model.TestResults;
import com.lp.model.AssignmentQuestions;
import com.lp.model.Attendance;
import com.lp.model.CAASPPScores;
import com.lp.model.EventStatus;
import com.lp.model.FilesLP;
import com.lp.model.FluencyMarks;
import com.lp.model.NotificationStatus;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.ReadRegAnswers;
import com.lp.model.ReadRegQuestions;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.SchoolSchedule;
import com.lp.model.StarScores;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentStarStrategies;
import com.lp.model.Trimester;
import com.lp.model.UserRegistration;
import com.lp.parent.service.ParentService;
import com.lp.student.service.StudentReadRegService;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

public class NotificationServiceImpl implements NotificationService {

	static final Logger logger = Logger.getLogger(NotificationServiceImpl.class);
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	@Autowired
	private AdminSchedulerDAO adminSchedulerDAO;
	
	@Autowired
	private FileService fileservice;
	
	@Autowired
	HttpServletRequest request;	
	
	@Autowired
	ParentService parentService;
	
	@Autowired
	ReadingSkillsService readSkillsService;
	
	@Autowired
	GoalSettingToolService goalSettingToolService;
	
	@Autowired
	private StudentReadRegService studentReadRegService;
	@Autowired
	private ReadRegReviewResultsService resultsService;
	
	@Override
	public boolean updateAttendanceStatus(long notificationId){
		return notificationDAO.updateAttendanceStatus(notificationId);
	}

	@Override
	public boolean updateRegisterForClassStatus(long studentId, long csId) {
		return notificationDAO.updateRegisterForClassStatus(studentId, csId);
	}

	@Override
	public boolean updateEventStatus(long notificationId) {
		return notificationDAO.updateEventStatus(notificationId);
	}

	@Override
	public boolean updateNotificationStatusStatus(long notificationId) {
		return notificationDAO.updateNotificationStatusStatus(notificationId);
	}
	
	@Override
	public boolean updateTestResultsStatusStatus(long notificationId) {
		return notificationDAO.updateTestResultsStatusStatus(notificationId);
	}
	
	
	@Override
	public List<ScheduledClass> getTeacherResponseforChildRequests(
			UserRegistration parent) throws DataException {
		List<RegisterForClass> responses = new ArrayList<RegisterForClass>();
		List<ScheduledClass> scheduledClassList = new ArrayList<ScheduledClass>();
		try{
			responses = notificationDAO.getTeacherResponseforChildRequests(parent);
			
			for(RegisterForClass rfc: responses){
				ScheduledClass sc = new ScheduledClass();
				sc.setStudentId(rfc.getStudent().getStudentId());
				sc.setCsId(rfc.getClassStatus().getCsId());
				sc.setChildName(rfc.getStudent().getUserRegistration().getFirstName() + " " +
                        rfc.getStudent().getUserRegistration().getLastName());
				sc.setTeacherName(rfc.getClassStatus().getTeacher().getUserRegistration().getFirstName() + " " +
                        rfc.getClassStatus().getTeacher().getUserRegistration().getLastName());
				sc.setClassName(rfc.getGradeClasses().getStudentClass().getClassName());
				sc.setStatus(rfc.getStatus());
				sc.setReadStatus(rfc.getReadStatus());
				scheduledClassList.add(sc);
			}
			
		}catch(DataException e){
			logger.error("Error in getTeacherResponseforChildRequests() of  NotificationServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getTeacherResponseforChildRequests() of NotificationServiceImpl", e);
		}
		return scheduledClassList;
	}
	
	@Override
	public List<AttendanceApp> getStudentAttendance(UserRegistration parent)
			throws DataException {
		List<Attendance> attendance = new ArrayList<Attendance>();
		List<AttendanceApp> attendanceApp = new ArrayList<AttendanceApp>();
		try{
			attendance = notificationDAO.getStudentAttendance(parent);
			
			for(Attendance at: attendance){
				AttendanceApp ata = new AttendanceApp();
				ata.setAttendanceId(at.getAttendanceId());
				ata.setStudentId(at.getStudent().getStudentId());
				ata.setStudentName(at.getStudent().getUserRegistration().getFirstName() + " " +
                        at.getStudent().getUserRegistration().getLastName());
				ata.setClassName(at.getClassStatus().getSection().getGradeClasses().getStudentClass().getClassName());
				ata.setDate(at.getDate());
				ata.setStatus(at.getStatus());
				ata.setReadStatus(at.getReadStatus());
				attendanceApp.add(ata);
			}
		}catch(DataException e){
			logger.error("Error in getStudentAttendance() of  NotificationServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getStudentAttendance() of NotificationServiceImpl", e);
		}
		return attendanceApp;
	}
	
	@Override
	public List<AnnouncementApp> getCurrentAnnouncementsBySchool(School schoool, Date fromDate, Date toDate) {
		List<NotificationStatus> notificationStatus = new ArrayList<NotificationStatus>();
		List<AnnouncementApp> announcementApp = new ArrayList<AnnouncementApp>();
		try{
			notificationStatus = notificationDAO.getCurrentAnnouncementsBySchool(schoool, fromDate, toDate);
			
			for(NotificationStatus ns: notificationStatus){
				AnnouncementApp ana = new AnnouncementApp();
				ana.setNotificationStatusId(ns.getNotificationStatusId());
				ana.setAnnouncementName(ns.getAnnouncements().getAnnoncementName());
				ana.setReadStatus(ns.getStatus());
				ana.setAnnounceDescription(ns.getAnnouncements().getAnnounceDescription());
				ana.setAnnounceDate(ns.getAnnouncements().getAnnounceDate());
				ana.setUrl(ns.getAnnouncements().getUrlLinks());
				announcementApp.add(ana);
			}
		}catch(DataException e){
			logger.error("Error in getCurrentAnnouncementsBySchool() of  NotificationServiceImpl"+ e);
			e.printStackTrace();
			throw new DataException("Error in getCurrentAnnouncementsBySchool() of NotificationServiceImpl", e);
		}
		return announcementApp;		
	}
	
	@Override
	public List<EventApp> getCurrentEventsBySchool(UserRegistration userRegistration, Date fromDate, Date toDate) {
		List<EventStatus> events =new ArrayList<EventStatus>();
		List<EventApp> eventsApp =new ArrayList<EventApp>();
		try {
			events = notificationDAO.getCurrentEventsBySchool(userRegistration, fromDate, toDate);
			for(EventStatus eve : events){
				EventApp ea = new EventApp();
				ea.setEventId(eve.getEventStatusId());
				ea.setEventName(eve.getEvent().getEventName());
				ea.setReadStatus(eve.getStatus());
				ea.setEventDescription(eve.getEvent().getDescription());
				ea.setAnnouncementDate(eve.getEvent().getAnnouncementDate());
				ea.setLastDate(eve.getEvent().getLastDate());
				ea.setScheduleDate(eve.getEvent().getScheduleDate());
				ea.setEventTime(eve.getEvent().getEventTime());
				ea.setContactPerson(eve.getEvent().getContactPerson());
				ea.setVenue(eve.getEvent().getVenue());
				eventsApp.add(ea);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return eventsApp;
	}
	
	
	@Override
	public SchoolSchedule getSchoolSchedule(School school) {
		SchoolSchedule scsche = new SchoolSchedule();
		try {
			scsche = adminSchedulerDAO.getSchoolSchedule(school);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scsche;
	}
	
	public List<String> getLatestNotifications(UserRegistration parent){
		List<String> unreadNotifications = new ArrayList<String>();
		SchoolSchedule schedule = adminSchedulerDAO.getSchoolSchedule(parent.getSchool());
		
		List<NotificationStatus> notifications = notificationDAO.getUnreadCurrentAnnouncementsBySchool(parent.getSchool(), schedule.getStartDate(), schedule.getEndDate());
		List<EventStatus> schoolEvents = notificationDAO.getUnreadCurrentEventsBySchool(parent.getSchool(), schedule.getStartDate(), schedule.getEndDate());
		List<RegisterForClass> scheduledClasses = notificationDAO.getUnreadTeacherResponseforChildRequests(parent);
		List<Attendance> attendances = notificationDAO.getUnreadStudentAttendance(parent);
		
		List<StudentAssignmentStatus> assessmentTestResults = notificationDAO.getUnreadChildTestResults(parent, WebKeys.LP_USED_FOR_ASSESSMENT);
		List<StudentAssignmentStatus> homeworkTestResults = notificationDAO.getUnreadChildTestResults(parent, WebKeys.LP_USED_FOR_HOMEWORKS);
		List<StudentAssignmentStatus> rtiTestResults = notificationDAO.getUnreadChildTestResults(parent, WebKeys.LP_USED_FOR_RTI);
		
		if(!assessmentTestResults.isEmpty()){
			unreadNotifications.add("New Assessment Results are available.");
		}
		if(!homeworkTestResults.isEmpty()){
			unreadNotifications.add("New Homework Results are available.");
		}
		if(!rtiTestResults.isEmpty()){
			unreadNotifications.add("New RTI Results are available.");
		}
		
		for(NotificationStatus notification : notifications){
			String notificationStr = "School Announcement "+ notification.getAnnouncements().getAnnoncementName();
			unreadNotifications.add(notificationStr);
		}
		
		for(EventStatus event : schoolEvents){
			String notificationStr = "School Event "+ event.getEvent().getEventName();
			unreadNotifications.add(notificationStr);
		}
		
		for(RegisterForClass registerForClass : scheduledClasses){
			String notificationStr = "Your child "+ registerForClass.getStudent().getUserRegistration().getFirstName() +" is registered for a class";
			unreadNotifications.add(notificationStr);
		}
		
		for(Attendance attendance : attendances){
			String notificationStr = "Your child is "+ attendance.getStatus();
			unreadNotifications.add(notificationStr);
		}		
		
		return unreadNotifications;
		
	}
	
	
	@Override
	public List<TestResults> getChildTestResults(UserRegistration userRegistration, String usedFor) {
		List<StudentAssignmentStatus> tests =new ArrayList<StudentAssignmentStatus>();
		List<TestResults> testResults = new ArrayList<TestResults>();
		try {
			tests = notificationDAO.getChildTestResults(userRegistration, usedFor);	
			for(StudentAssignmentStatus sAssignmentStatus : tests){
				TestResults testResult = new TestResults();
				testResult.setPercentage(sAssignmentStatus.getPercentage());
				
				testResult.setStudentName(sAssignmentStatus.getStudent().getUserRegistration().getFirstName() + " " +
						sAssignmentStatus.getStudent().getUserRegistration().getLastName());
				
				testResult.setStudentAssignmentId(sAssignmentStatus.getStudentAssignmentId());
				testResult.setSubmitdate(sAssignmentStatus.getSubmitdate());
				testResult.setTestDueDate(sAssignmentStatus.getAssignment().getDateDue());
				testResult.setTitle(sAssignmentStatus.getAssignment().getTitle());
				testResult.setAssignmentType(sAssignmentStatus.getAssignment().getAssignmentType().getAssignmentType());
				testResult.setTeacherName(sAssignmentStatus.getAssignment().getClassStatus().getTeacher().getUserRegistration().getFirstName() + " " +
						sAssignmentStatus.getAssignment().getClassStatus().getTeacher().getUserRegistration().getLastName());
				
				testResult.setClassName(sAssignmentStatus.getAssignment().getClassStatus().getSection().getGradeClasses().getStudentClass().getClassName());
				testResult.setReadStatus(sAssignmentStatus.getReadStatus());
				
				if(sAssignmentStatus.getAssignment().getAssignmentType().getAssignmentType().equalsIgnoreCase(WebKeys.ASSIGNMENT_TYPE_FLUENCY_READING)){
					List<TestResultFluency> fluencyList = new ArrayList<>();
					String fullLocation = FileUploadUtil.getUploadFilesPath(sAssignmentStatus.getStudent().getUserRegistration(), request);
					String dirLocation = FileUploadUtil.getRelativeUserPath(sAssignmentStatus.getStudent().getUserRegistration(), request);
					logger.info("fullLocation = "+fullLocation);
					logger.info("dirLocation = "+dirLocation);
					for(AssignmentQuestions aq :sAssignmentStatus.getAssignmentQuestions()){
						File[] aFiles = null;
						String fullPath = dirLocation+File.separator+WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+aq.getAssignmentQuestionsId();
						String fullLocationPath = fullLocation+File.separator+WebKeys.STUDENT_BENCH_MARK_TESTS+File.separator+aq.getAssignmentQuestionsId();
						logger.info("fullLocationPath "+fullLocationPath);
						aFiles = fileservice.getFolderFiles(fullLocationPath);
						TestResultFluency trf = new TestResultFluency();
											
						for(FluencyMarks fm: aq.getFluencyMarkResults()){							
							if(fm.getGradingTypes().getGradingTypes().equalsIgnoreCase(WebKeys.TEACHER_GRADING)
								&& fm.getReadingTypes().getReadingTypes().equalsIgnoreCase(WebKeys.READING_TYPE_FLUENCY)){
								trf.setWcpm(fm.getMarks());
								trf.setAccuracy(fm.getAccuracyScore());
							}
							if(fm.getGradingTypes().getGradingTypes().equalsIgnoreCase(WebKeys.TEACHER_GRADING)
									&& fm.getReadingTypes().getReadingTypes().equalsIgnoreCase(WebKeys.READING_TYPE_RETELL)){
								if(fm.getQualityOfResponse()!=null)
									trf.setRetell(fm.getQualityOfResponse().getQorId());								
							}
						}
						if(aFiles != null){
							logger.info("Files found");
							logger.info("fullLocationPath = "+fullLocationPath);
							trf.setFAudioPath(fullPath);
							trf.setFRetellPath(fullPath);
						}
						else{
							logger.info("Files not found");
						}
						fluencyList.add(trf);
					}
					testResult.setTestResultFluency(fluencyList);
				}				
				testResults.add(testResult);
			}			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return testResults;
	}
	
	@Override
	public List<ChildDashboard> getChildDashBoardFiles(UserRegistration userRegistration, String usedFor) {
		
		List<Student> childLst =new ArrayList<Student>();
		List<ChildDashboard> childDashBoardFiles = new ArrayList<ChildDashboard>();
		try {
			//tests = notificationDAO.getChildDashboardFiles(userRegistration, usedFor);	
			childLst=parentService.getStudentByParent(userRegistration.getRegId());
			for(Student student : childLst){
				ChildDashboard dashFile = new ChildDashboard();
				dashFile.setStudentId(student.getStudentId());;
				dashFile.setStudentName(student.getUserRegistration().getFirstName() + " " +
						student.getUserRegistration().getLastName());
								
				File[] studentRRDDFiles = readSkillsService.getStudentRRDashboardFiles(student);
				ArrayList<FilesLP> files = new ArrayList<FilesLP>();
				ArrayList<String> studDashFiles= new ArrayList<String>();
				//Arrays.sort(studentRRDDFiles,Collections.reverseOrder());
				for(File file : studentRRDDFiles){
					if(file.isFile()){
						FilesLP fLp = new FilesLP();
						fLp.setFileName(file.getName());
						fLp.setFilePath(file.getAbsolutePath());
						studDashFiles.add(file.getAbsolutePath());
						//fLp.setFileType(FilenameUtils.getExtension(file.getAbsolutePath()));
						files.add(fLp);
					}					
				}
				dashFile.setDashFiles(studDashFiles);
			
				//model.addObject("studentRRDDFiles", students);
				
						
			childDashBoardFiles.add(dashFile);
			}			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return childDashBoardFiles;
	}	
	@Override
	public List<StudentGoalReports> getChildGoalSettingReports(UserRegistration userRegistration){
		List<Student> childLst =new ArrayList<Student>();
		List<StudentGoalReports> childGoalReports = new ArrayList<StudentGoalReports>();
		List<CAASPPScores> studCaasppReadScore=new ArrayList<CAASPPScores>();
		List<CAASPPScores> studCaasppMathScore=new ArrayList<CAASPPScores>();
		List<StarScoresReports> studReadStarReports=new ArrayList<StarScoresReports>();
		List<StarScoresReports> studMAthStarReports=new ArrayList<StarScoresReports>();
		List<Trimester> trimesterList=new ArrayList<Trimester>();
		
		List<StarScores> studStarScores=new ArrayList<StarScores>();
		List<StudentStarStrategies> starStrategies=new ArrayList<StudentStarStrategies>();
		try {
			//tests = notificationDAO.getChildDashboardFiles(userRegistration, usedFor);	
			childLst=parentService.getStudentByParent(userRegistration.getRegId());
			for(Student student : childLst){
				StudentGoalReports studGoalReport = new StudentGoalReports();
				studGoalReport.setStudentId(student.getStudentId());
				studGoalReport.setStudentName(student.getUserRegistration().getFirstName() + " " +
						student.getUserRegistration().getLastName());
				studGoalReport.setMasterGradeId(student.getGrade().getMasterGrades().getMasterGradesId());
				int caasppTypeId=1;
				studCaasppReadScore=goalSettingToolService.getStudentCAASPPScores(caasppTypeId,student.getStudentId());	
				if(studCaasppReadScore.size()>0){
					studGoalReport.setCaasppReadScore(studCaasppReadScore.get(0).getCaasppScore());
				}
				else{
					studGoalReport.setCaasppReadScore(0.0F);
				}
				caasppTypeId=3;
				studStarScores=notificationDAO.getStarReports(student.getStudentId(),caasppTypeId,student.getGrade().getGradeId());
				trimesterList=goalSettingToolService.getTrimesterList();
				for(Trimester trim:trimesterList){
					StarScoresReports starReadRept=new StarScoresReports();
					List<String> readStarStrategies=new ArrayList<String>();
					starReadRept.setCaasppTypeId(caasppTypeId);
					starReadRept.setTrimesterId(trim.getTrimesterId());
					starStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(student.getStudentId(), student.getGrade().getGradeId(), trim.getTrimesterId(), caasppTypeId);
					for(StudentStarStrategies studStrategy : starStrategies){
						//readStarStrategies.set(i,studStrategy.getGoalStrategies().getGoalStrategiesDesc());
						readStarStrategies.add(studStrategy.getGoalStrategies().getGoalStrategiesDesc());
						
					}
					long st=0;
				if(studStarScores.size()>0){
				for(StarScores starScores : studStarScores){
					if(starScores.getTrimester().getTrimesterId()==trim.getTrimesterId()){
					st++;
					if(starScores.getScore()>0)
						starReadRept.setStarScore(starScores.getScore());
					else
						starReadRept.setStarScore(0.0f);
					}
				}
				if(st==0)
					starReadRept.setStarScore(0.0f);
				}else{
					starReadRept.setStarScore(0.0f);
				}
				
				
				starReadRept.setStarStrategies(readStarStrategies);
				studReadStarReports.add(starReadRept);
				}
				studGoalReport.setStarReadingReports(studReadStarReports);
				caasppTypeId=2;
				studCaasppMathScore=goalSettingToolService.getStudentCAASPPScores(caasppTypeId,student.getStudentId());	
				if(studCaasppMathScore.size()>0){
					studGoalReport.setCaasppMathScore(studCaasppMathScore.get(0).getCaasppScore());
				}
				else{
					studGoalReport.setCaasppMathScore(0.0F);
				}
				caasppTypeId=4;
				//studGoalReport.setCaasppMathScore(studCaasppMathScore.get(0).getCaasppScore());
				studStarScores=notificationDAO.getStarReports(student.getStudentId(),caasppTypeId,student.getGrade().getGradeId());
				for(Trimester trim:trimesterList){
					List<String> mathStarStrategies=new ArrayList<String>();
					StarScoresReports starMathRept=new StarScoresReports();
					starMathRept.setCaasppTypeId(caasppTypeId);
					starMathRept.setTrimesterId(trim.getTrimesterId());
					starStrategies=goalSettingToolService.getStudentStarStrategiesByTrimesterId(student.getStudentId(), student.getGrade().getGradeId(), trim.getTrimesterId(), caasppTypeId);
					for(StudentStarStrategies studStrategy : starStrategies){
						mathStarStrategies.add(studStrategy.getGoalStrategies().getGoalStrategiesDesc());
						
					}
				long st=0;
				if(studStarScores.size()>0){
				for(StarScores starScores : studStarScores){
					if(starScores.getTrimester().getTrimesterId()==trim.getTrimesterId()){
						st++;
						if(starScores.getScore()>0)
							starMathRept.setStarScore(starScores.getScore());
						else
							starMathRept.setStarScore(0.0f);
						}
					}
				if(st==0)
					starMathRept.setStarScore(0.0f);
				}else{
					starMathRept.setStarScore(0.0f);
				}
				starMathRept.setStarStrategies(mathStarStrategies);
				studMAthStarReports.add(starMathRept);
				}
				studGoalReport.setStarMathReports(studMAthStarReports);
				String academeicYear=goalSettingToolService.getCurrentAcademicYr().getAcademicYear().toString();
				studGoalReport.setAcademicYear(academeicYear);
				childGoalReports.add(studGoalReport);
				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childGoalReports;
	}
	@Override
	public List<StudentReadingRegister> getChildReadingRegisterActivities(UserRegistration userRegistration){
		List<Student> childLst =new ArrayList<Student>();
		List<StudentReadingRegister> lstStudReadReg=new ArrayList<StudentReadingRegister>();
		try {
			childLst=parentService.getStudentByParent(userRegistration.getRegId());
			for(Student student : childLst){
				StudentReadingRegister studReadReg=new StudentReadingRegister();
				studReadReg.setStudentId(student.getStudentId());
				studReadReg.setStudentName(student.getUserRegistration().getFirstName() + " " +	student.getUserRegistration().getLastName());
				studReadReg.setGradeId(student.getGrade().getGradeId());
				
				List<ReadRegActivityScore> studentActivities=studentReadRegService.getStudentActivities(student.getStudentId(), "createDate", 
																student.getUserRegistration().getSchool().getAcademicYear().getStartDate(), 
																student.getUserRegistration().getSchool().getAcademicYear().getEndDate());
				List<StudentActivities> lstStudentActivity=new ArrayList<StudentActivities>();
				for(ReadRegActivityScore readRegActScore : studentActivities){
					StudentActivities stuAct=new StudentActivities();
					List<ActivityQuizQuestions> actQuizQuesLst=new ArrayList<ActivityQuizQuestions>();
					stuAct.setActivityId(readRegActScore.getReadRegActivity().getActivityId());
					stuAct.setActivityDesc(readRegActScore.getReadRegActivity().getActitvity());
					stuAct.setBookTitle(readRegActScore.getReadRegMaster().getBookTitle());
					stuAct.setAuthor(readRegActScore.getReadRegMaster().getAuthor());
					stuAct.setNoOfPages(readRegActScore.getReadRegMaster().getNumberOfPages());
					
					if(readRegActScore.getSelfScore()!=null)
					stuAct.setSelfScore(readRegActScore.getSelfScore().getScore());
					if(readRegActScore.getReadRegRubric()!=null)
					stuAct.setTeacherScore(readRegActScore.getReadRegRubric().getScore());
					
					String pointsEarned=""+readRegActScore.getPointsEarned()+"(pages of the book ="+
					   readRegActScore.getReadRegMaster().getReadRegPageRange().getRange()+" x Activity Value ="+readRegActScore.getReadRegActivity().getActivityValue()+
					   " x Rubric value ="+readRegActScore.getReadRegRubric().getScore()+")";
					   stuAct.setPointsEarned(pointsEarned);
					if(readRegActScore.getReadRegActivity().getActitvity().equalsIgnoreCase("review")){
					   stuAct.setReview(readRegActScore.getReadRegReview().getReview());
					   stuAct.setRating(readRegActScore.getReadRegReview().getRating());
					   //stuAct.setRating(4);
					   
					}
					if(readRegActScore.getReadRegActivity().getActitvity().equalsIgnoreCase("retell")){
						stuAct.setSelfDescription(readRegActScore.getSelfScore().getDescription());
						stuAct.setTeacherDescription(readRegActScore.getReadRegRubric().getDescription());
						String dirLocation = FileUploadUtil.getRelativeUserPath(student.getUserRegistration(), request);
						String ReadingRegisterFilePath = WebKeys.READING_REGISTER+File.separator+readRegActScore.getReadRegMaster().getTitleId()+File.separator+WebKeys.READING_TYPE_RETELL+WebKeys.WAV_FORMAT;
						String retellPath=dirLocation+File.separator+WebKeys.READING_REGISTER+File.separator+readRegActScore.getReadRegMaster().getTitleId();
					    stuAct.setRetellPath(retellPath);
					}
                    if(readRegActScore.getReadRegActivity().getActitvity().equalsIgnoreCase("create a quiz")){
                    	List<ReadRegQuestions> readRegQuestions = resultsService.getReadRegQuestions(student.getStudentId(), readRegActScore.getReadRegMaster().getTitleId());
                    	for(ReadRegQuestions readRegQues : readRegQuestions){
                    		ActivityQuizQuestions quizQues=new ActivityQuizQuestions();
                    		quizQues.setQuestion(readRegQues.getQuestion());
                    		quizQues.setOption1(readRegQues.getOption1());
                    		quizQues.setOption2(readRegQues.getOption2());
                    		quizQues.setOption3(readRegQues.getOption3());
                    		quizQues.setOption4(readRegQues.getOption4());
                    		quizQues.setAnswer(readRegQues.getAnswer());
                    		actQuizQuesLst.add(quizQues);                   		
                    	}
                    	stuAct.setSelfDescription(readRegActScore.getSelfScore().getDescription());
						stuAct.setTeacherDescription(readRegActScore.getReadRegRubric().getDescription());
                    	stuAct.setActivityQuizQuestions(actQuizQuesLst);
					
                    }
                    if(readRegActScore.getReadRegActivity().getActitvity().equalsIgnoreCase("take a quiz")){
                    	List<ReadRegAnswers> readRegAnswers = resultsService.getStudentQuiz(student.getStudentId(), readRegActScore.getReadRegMaster().getTitleId());
                    	for(ReadRegAnswers readRegAns : readRegAnswers){
                    		ActivityQuizQuestions quizQuesAns=new ActivityQuizQuestions();
                    		quizQuesAns.setQuestion(readRegAns.getReadRegQuestions().getQuestion());
                    		quizQuesAns.setOption1(readRegAns.getReadRegQuestions().getOption1());
                    		quizQuesAns.setOption2(readRegAns.getReadRegQuestions().getOption2());
                    		quizQuesAns.setOption3(readRegAns.getReadRegQuestions().getOption3());
                    		quizQuesAns.setOption4(readRegAns.getReadRegQuestions().getOption4());
                    		quizQuesAns.setAnswer(readRegAns.getAnswer());
                    		quizQuesAns.setCorrectAnswer(readRegAns.getReadRegQuestions().getAnswer());
                    		actQuizQuesLst.add(quizQuesAns);
                    	}
                    	stuAct.setActivityQuizQuestions(actQuizQuesLst);
                    }
                    if(readRegActScore.getReadRegActivity().getActitvity().equalsIgnoreCase("upload a picture")){
                    	stuAct.setSelfDescription(readRegActScore.getSelfScore().getDescription());
						stuAct.setTeacherDescription(readRegActScore.getReadRegRubric().getDescription());
						String dirLocation = FileUploadUtil.getRelativeUserPath(student.getUserRegistration(), request);
						String ReadingRegisterPicturePath = WebKeys.READING_REGISTER+File.separator+readRegActScore.getReadRegMaster().getTitleId()+File.separator+WebKeys.LP_PICTURE+WebKeys.JPG_FORMAT;
						String picturePath=dirLocation+File.separator+WebKeys.READING_REGISTER+File.separator+readRegActScore.getReadRegMaster().getTitleId();
					    stuAct.setPicturePath(picturePath);
					    
						
                    }
                    lstStudentActivity.add(stuAct);
				}
				studReadReg.setStudentActivities(lstStudentActivity);
				lstStudReadReg.add(studReadReg);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	  return lstStudReadReg;
	}
		
}
