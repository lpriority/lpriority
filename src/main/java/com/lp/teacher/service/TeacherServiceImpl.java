package com.lp.teacher.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.admin.dao.GradesDAO;
import com.lp.admin.dao.StudentClassesDAO;
import com.lp.appadmin.dao.GradeLevelDAO;
import com.lp.appadmin.dao.UserDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.appadmin.service.AppAdminService;
import com.lp.appadmin.service.AppAdminService2;
import com.lp.custom.exception.DataException;
import com.lp.login.service.UserLoginService;
import com.lp.model.Address;
import com.lp.model.AdminTeacherReports;
import com.lp.model.Assignment;
import com.lp.model.BenchmarkResults;
import com.lp.model.ClassStatus;
import com.lp.model.FluencyAddedWords;
import com.lp.model.FluencyErrorTypes;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.Grade;
import com.lp.model.GradeLevel;
import com.lp.model.HomeroomClasses;
import com.lp.model.LessonPaths;
import com.lp.model.School;
import com.lp.model.Security;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.TeacherPerformances;
import com.lp.model.TeacherReports;
import com.lp.model.TeacherSubjects;
import com.lp.model.User;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.TeacherDAO;
import com.lp.teacher.dao.TeacherSubjectsDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "teacherService")
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private UserRegistrationDAO userRegistrationDAO;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AppAdminService appAdminService;
	@Autowired
	private AppAdminService2 appAdminService2;
	@Autowired
	private TeacherSubjectsDAO teacherSubDao;
	@Autowired
	private TeacherDAO teacherDao;	
	@Autowired
	private GradeLevelDAO gradeLevelDAO;
	@Autowired
	private GradesDAO gradeDao;
	@Autowired
	private StudentClassesDAO stuClassDao;
	@Autowired
	private AdminSchedulerDAO adminSchedulerDao;

	static final Logger logger = Logger.getLogger(TeacherServiceImpl.class);

	@Override
	public String registerTeacher(UserRegistration userReg, String[] gradeIds,
			String[] classIds, String[] classLengths, String[] gradeLevels,
			String[] noOfSectionsPerDay, String[] noOfSectionsPerWeek) {
		boolean status = true;
		String md5Password = userLoginService.getMD5Conversion(userReg
				.getPassword());
		userReg.setPassword(md5Password);
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		userReg.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		userReg.setChangeDate(changeDate);
		long classLength = 0;
		long classId = 0;
		long gradeId = 0;
		long gradeLevelId = 0;
		int noOfSectionDay = 0;
		int noOfSectionWeek = 0;
		Teacher teacher = new Teacher();
		userReg.setUser(userDAO.getUserType("teacher"));
		userReg.setAddress(new Address());
		userReg.getAddress().setAddress(
				userReg.getAddress1() + "##@##" + userReg.getAddress2());
		userReg.getAddress().setCity(userReg.getCity());
		userReg.getAddress().setStates(
				appAdminService2.getState(Long.valueOf(userReg.getStateId())));
		userReg.getAddress().setZipcode(Integer.valueOf(userReg.getZipcode()));
		userLoginService.saveAddress(userReg.getAddress());
		School school = userRegistrationDAO.getNewUserRegistration(
				userReg.getEmailId()).getSchool();
		userReg.setSchool(school);
		userReg.setStatus(WebKeys.ACTIVE);
		userRegistrationDAO.saveUserRegistration(userReg);
		teacher.setUserRegistration(userReg);
		teacher.setStatus(WebKeys.ACTIVE);
		teacherDao.saveTeacher(teacher);
		int count = -1;
		for (int i = 0; i < gradeIds.length; i++) {
			Grade grade = new Grade();
			gradeId = Long.parseLong(gradeIds[i].split(":")[0]);
			grade = gradeDao.getGrade(gradeId);
			classLength = Integer.parseInt(classLengths[i]);
			for (int j = 0; j < classLength; j++) {
				count++;
				TeacherSubjects teacherSub = new TeacherSubjects();
				StudentClass stuClass = new StudentClass();
				GradeLevel gradeLevel = new GradeLevel();
				classId = Long.parseLong(classIds[count].split(":")[0]);
				gradeLevelId = Long.parseLong(gradeLevels[count]);
				noOfSectionDay = Integer.parseInt(noOfSectionsPerDay[count]);
				noOfSectionWeek = Integer.parseInt(noOfSectionsPerWeek[count]);
				teacherSub = new TeacherSubjects();
				teacherSub.setTeacher(teacher);
				teacherSub.setGrade(grade);
				stuClass = stuClassDao.getClass(classId);
				teacherSub.setStudentClass(stuClass);
				gradeLevel = gradeLevelDAO.getGradeLevel(gradeLevelId);
				teacherSub.setGradeLevel(gradeLevel);
				teacherSub.setNoSectionsPerDay(noOfSectionDay);
				teacherSub.setNoSectionsPerWeek(noOfSectionWeek);
				teacherSubDao.saveTeacherSubject(teacherSub);
			}
		}
		Security sec = userLoginService.getSecurity(userReg.getEmailId());
		sec.setUserRegistration(userReg);
		sec.setSecurityQuestion(appAdminService.getSecurityQuestion(Long
				.valueOf(userReg.getSecurityQuestionId())));
		sec.setAnswer(userReg.getAnswer());
		sec.setStatus(WebKeys.LP_STATUS_INACTIVE);
		if (sec.getVerificationCode() == null)
			sec.setVerificationCode(WebKeys.DEFAULT_VERIFICATION_CODE);
		userLoginService.saveSecurity(sec);
		if (status) {
			return "login/RegistrationSuccess.jsp";
		} else {
			return "login/Fail.jsp";
		}
	}

	public List<Teacher> getTeachers(long gradeId, long classId) {
		return teacherDao.getTeachers(gradeId, classId);
	}

	@Override
	public List<TeacherPerformances> getTeacherPerformances() {
		return teacherDao.getTeacherPerformances();

	}

	@Override
	public TeacherPerformances getTeacherPerformance(long performanceId) {
		return teacherDao.getTeacherPerformance(performanceId);
	}

	@Override
	public Teacher getTeacher(long teacherregId) {
		return teacherDao.getTeacher(teacherregId);
	}

	@Override
	public boolean saveTeacherPerformances(TeacherReports teacherreports) {
		return teacherDao.saveTeacherPerformances(teacherreports);
	}

	@Override
	public UserRegistration getAdminUserRegistration(School school) {
		return teacherDao.getAdminUserRegistration(school);
	}

	@Override
	public List<TeacherReports> getTeacherReports(User user, Teacher teacher) {
		return teacherDao.getTeacherReports(user, teacher);
	}

	@Override
	public List<ClassStatus> getTeachersBySchoolId(School school) {
		List<ClassStatus> cStatus = new ArrayList<ClassStatus>();
		List<ClassStatus> classStatus = new ArrayList<ClassStatus>();
		try {
			cStatus = teacherDao.getTeachersBySchoolId(school);
			HashSet<Long> key = new HashSet<Long>();
			for (ClassStatus cs : cStatus) {
				if (!key.contains(cs.getTeacher().getTeacherId())) {
					classStatus.add(cs);
					key.add(cs.getTeacher().getTeacherId());
				}
			}
		} catch (DataException e) {
			logger.error("Error in getTeachersBySchoolId() of TeacherServiceImpl"
					+ e);
		}

		return classStatus;
	}

	@Override
	@RemoteMethod
	public List<AdminTeacherReports> getTeacherSelfReportDates(long teacherId) {

		return teacherDao.getTeacherSelfReportDates(teacherId);
	}

	@Override
	@RemoteMethod
	public List<Teacher> getTeachersByGradeId(long gradeId, long schoolId) {
		List<Teacher> teachersList = new ArrayList<Teacher>();
		List<TeacherSubjects> teacherSubjectsList = teacherSubDao
				.getTeachersByGrade(gradeId, schoolId);

		HashSet<Long> key = new HashSet<Long>();
		for (TeacherSubjects teacherSubject : teacherSubjectsList) {
			if (!key.contains(teacherSubject.getTeacher().getTeacherId())) {
				teachersList.add(teacherSubject.getTeacher());
				key.add(teacherSubject.getTeacher().getTeacherId());
			}
		}
		return teachersList;
	}

	@Override
	public boolean saveAdminTeacherReports(
			AdminTeacherReports adminteacherreports) {
		String date = new java.sql.Date(new java.util.Date().getTime())
				.toString();
		adminteacherreports.setDate(date);
		boolean status = false;
		try {
			status = teacherDao.getSelfEvaluation(adminteacherreports
					.getTeacher().getTeacherId(), adminteacherreports.getUser()
					.getUserTypeid(), date);
			if (!status) {
				status = teacherDao
						.saveAdminTeacherReports(adminteacherreports);
			} else {
				throw new DataException(WebKeys.ALREADY_REPORT_CREATED);
			}
		} catch (DataException e) {
			if (e.getMessage() != "")
				throw new DataException(e.getMessage());
			else
				throw new DataException("Error in saveAdminTeacherReports()", e);
		}
		return status;
	}

	@Override
	public List<TeacherReports> getTeacherReportsByDate(Teacher teacher,
			String reportDate) {
		return teacherDao.getTeacherReportsByDate(teacher, reportDate);
	}

	@Override
	public Teacher getTeacherbyTeacherId(long teacherId) {
		return teacherDao.getTeacherbyTeacherId(teacherId);
	}

	@Override
	public List<Assignment> getBenchmarkDates(Teacher teacher, long csId,
			String usedFor) {
		List<Assignment> assignments = Collections.emptyList();
		try {
			assignments = teacherDao.getBenchmarkDates(teacher, csId, usedFor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assignments;
	}

	@Override
	public List<BenchmarkResults> getBenchmarkResults(Teacher teacher,
			long csId, String usedFor, String dateAssigned, long assignmentId) {
		List<BenchmarkResults> assignments = Collections.emptyList();
		try {
			assignments = teacherDao.getBenchmarkResults(teacher, csId,
					usedFor, dateAssigned, assignmentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assignments;
	}

	@Override
	public List<String> getAllStudentRTIGroupName(
			List<BenchmarkResults> benchmarkResults) {
		List<String> rtiGroupNames = new ArrayList<String>();
		try {
			for (BenchmarkResults br : benchmarkResults) {
				String as = new String();
				as = teacherDao.getRTIGroupName(br.getStudentAssignmentStatus()
						.getStudent().getStudentId(), br
						.getStudentAssignmentStatus().getAssignment()
						.getClassStatus().getCsId());
				rtiGroupNames.add(as);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtiGroupNames;
	}

	public List<Integer> getAllStudentCompositeScore(
			List<BenchmarkResults> benchmarkResults) {
		List<Integer> compositeScores = new ArrayList<Integer>();
		try {
			for (BenchmarkResults br : benchmarkResults) {
				Integer as = 0;
				as = (int) (br.getMedianFluencyScore() + br
						.getQualityOfResponse().getQorId());
				compositeScores.add(as);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return compositeScores;
	}

	@Override
	public void setLessonPath(LessonPaths lessonPaths) {
		try {
			teacherDao.setLessonPath(lessonPaths);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String checkHomeRoomTeacher(HomeroomClasses hrc)
			throws DataException {
		String status = "";
		try {
			status = teacherDao.checkHomeRoomTeacher(hrc);
		} catch (DataException e) {
			logger.error("Error in checkHomeRoomTeacher() of TeacherServiceImpl"
					+ e);
		}
		return status;
	}

	@Override
	public List<LessonPaths> getLessonPathByLessonId(long lessonId) {
		return teacherDao.getLessonPathByLessonId(lessonId);
	}

	@Override
	public boolean deleteLessonPath(long lessonId, String lessonPath) {
		return teacherDao.deleteLessonPath(lessonId, lessonPath);
	}

	@Override
	public ClassStatus getClassStatus(long csId) {
		return adminSchedulerDao.getclassStatus(csId);
	}

	@Override
	public List<Student> getStudents(List<BenchmarkResults> fluencyResults) {
		List<Student> studentList = new ArrayList<Student>();
		try {
			for (BenchmarkResults br : fluencyResults) {
				Student stud = br.getStudentAssignmentStatus().getStudent();
				studentList.add(stud);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public List<StudentAssignmentStatus> getStudentAssignmentStatusList(
			long assignmentId) {
		List<StudentAssignmentStatus> studentAssignStatList = new ArrayList<StudentAssignmentStatus>();
		try {
			studentAssignStatList = teacherDao
					.getStudentAssignmentStatusList(assignmentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentAssignStatList;
	}


	@Override
	public List<List<Integer>> getErrorWordDetails(List<FluencyMarksDetails> errorAddressList,
			List<StudentAssignmentStatus> stuAssignStatusList,long type) {
		ArrayList<List<Integer>> allStudErrorList = new ArrayList<>();
		List<String> fluencyMarkDets = new ArrayList<String>();
		if(type==1){
		for (StudentAssignmentStatus studAss : stuAssignStatusList) {
			ArrayList<Integer> studErrorsList = new ArrayList<Integer>();
			fluencyMarkDets = teacherDao.getErrorAddressList(studAss.getStudentAssignmentId());
			for (FluencyMarksDetails fm : errorAddressList) {				
				if (fluencyMarkDets.contains(fm.getErrorWord())) {
					if (fm.getSkippedWord() != null)
						studErrorsList.add(0);
					else 
					studErrorsList.add(1);
				} else
					studErrorsList.add(0);
			}
			allStudErrorList.add(studErrorsList);
    	}
		}
		else if(type==2){
			for (StudentAssignmentStatus studAss : stuAssignStatusList) {
				ArrayList<Integer> studErrorsList = new ArrayList<Integer>();
				fluencyMarkDets = teacherDao.getSkippedWordList(studAss.getStudentAssignmentId());
				for (FluencyMarksDetails fm : errorAddressList) {				
					if (fluencyMarkDets.contains(fm.getErrorWord())) {
						if (fm.getSkippedWord() != null)
							studErrorsList.add(2);
						else 
						studErrorsList.add(0);
					} else
						studErrorsList.add(0);
				}
				allStudErrorList.add(studErrorsList);
	    	}
	}else if(type==5){
		for (StudentAssignmentStatus studAss : stuAssignStatusList) {
			ArrayList<Integer> studErrorsList = new ArrayList<Integer>();
			fluencyMarkDets = teacherDao.getUnknownWordList(studAss.getStudentAssignmentId());
			for (FluencyMarksDetails fm : errorAddressList) {				
				if (fluencyMarkDets.contains(fm.getErrorWord())) 
					studErrorsList.add(5);
					else 
					studErrorsList.add(0);
				
			}
			allStudErrorList.add(studErrorsList);
    	}
}
		return allStudErrorList;
	}
	@Override
	public List<List<Integer>> getAddedWordDetails(List<FluencyAddedWords> fluencyAddedWordList,List<StudentAssignmentStatus> stuAssignStatusList,long wordType){
		ArrayList<List<Integer>> allStudErrorList = new ArrayList<>();
		List<String> fluencyAddedDets = new ArrayList<String>();
		if(wordType==1){
		for (StudentAssignmentStatus studAss : stuAssignStatusList) {
			ArrayList<Integer> studErrorsList = new ArrayList<Integer>();
			fluencyAddedDets = teacherDao.getAddedWordList(studAss.getStudentAssignmentId(),wordType);
			for (FluencyAddedWords fm : fluencyAddedWordList) {				
				if (fluencyAddedDets.contains(fm.getAddedWord())) {
					 studErrorsList.add(4);
				} else
					studErrorsList.add(0);
			}
			allStudErrorList.add(studErrorsList);
    	}
		}else if(wordType==2){
			for (StudentAssignmentStatus studAss : stuAssignStatusList) {
				ArrayList<Integer> studErrorsList = new ArrayList<Integer>();
				fluencyAddedDets = teacherDao.getAddedWordList(studAss.getStudentAssignmentId(),wordType);
				for (FluencyAddedWords fm : fluencyAddedWordList) {				
					if (fluencyAddedDets.contains(fm.getAddedWord())) {
						studErrorsList.add(3);
					} else
						studErrorsList.add(0);
				}
				allStudErrorList.add(studErrorsList);
	    	}
	}
		return allStudErrorList;
	}

	@Override
	public List<FluencyMarksDetails> getErrorWordCount(long assignmentId) {
		List<FluencyMarksDetails> errorWordList=new ArrayList<FluencyMarksDetails>();
		try{
	     errorWordList = teacherDao.getErrorWordCount(assignmentId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return errorWordList;	
	}
	@Override
	public List<FluencyAddedWords> getAddedWordCount(long assignmentId,long wordType) {
		List<FluencyAddedWords> addedWordList=new ArrayList<FluencyAddedWords>();
		try{
	     addedWordList = teacherDao.getAddedWordCount(assignmentId,wordType);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return addedWordList;	
	}
	@Override
	public List<FluencyErrorTypes> getFluencyErrorTypes(){
		List<FluencyErrorTypes> errorTypes=new ArrayList<FluencyErrorTypes>();
		try{
			errorTypes = teacherDao.getFluencyErrorTypes();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return errorTypes;	
	}
	
	@Override
	public UserRegistration getAppAdminUserRegistration() {
		return teacherDao.getAppAdminUserRegistration();
	}
	@Override
	public List<Student> getAllStudentsByAssignmentId(long assignemntId){
		List<Student> allStudents=new ArrayList<Student>();
		try{
			allStudents = teacherDao.getAllStudentsByAssignmentId(assignemntId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return allStudents;	
	}
	@Override
	public List<Assignment> getAccuracyDates(long csId,
			String usedFor) {
		List<Assignment> assignments = Collections.emptyList();
		try {
			assignments = teacherDao.getAccuracyDates(csId, usedFor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assignments;
	}

}
