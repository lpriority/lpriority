package com.lp.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lp.admin.dao.AdminDAO;
import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.appadmin.dao.AssignmentTypeDAO;
import com.lp.appadmin.dao.SchoolDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.custom.exception.DataException;
import com.lp.login.dao.SecurityDAO;
import com.lp.model.AcademicYear;
import com.lp.model.AssignK1Tests;
import com.lp.model.Assignment;
import com.lp.model.AssignmentType;
import com.lp.model.Attendance;
import com.lp.model.BenchmarkResults;
import com.lp.model.ClassActualSchedule;
import com.lp.model.FluencyMarks;
import com.lp.model.HomeroomClasses;
import com.lp.model.ParentLastseen;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.Security;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;
import com.lp.parent.dao.ParentDAO;
import com.lp.teacher.dao.CommonDAO;
import com.lp.teacher.dao.TeacherDAO;
import com.lp.teacher.dao.TeacherViewClassDAO;
import com.lp.utils.WebKeys;

@Service
public class CommonService {
	
	@Autowired
	private UserRegistration user;
	@Autowired
	private UserRegistrationDAO userRegistrationDAO;
	@Autowired
	private SchoolDAO schoolDAO;
	@Autowired
	private SecurityDAO securityDAO;
	@Autowired
	private AdminDAO AdminDAO;
	@Autowired
	private TeacherDAO teacherDAO;
	@Autowired
	TeacherViewClassDAO teacherViewClassDAO;
	@Autowired
	CommonDAO commonDAO;
	@Autowired
	private ParentDAO parentDao;

	@Autowired
	private AdminSchedulerDAO adminSchedulerDAO;
	
	@Autowired
	private AssignmentTypeDAO assignmentTypeDAO;
	
	static final Logger logger = Logger.getLogger(AssessmentServiceImpl.class);


	public void setUserRegistrationDao(UserRegistrationDAO userRegistrationDAO) {
		this.userRegistrationDAO = userRegistrationDAO;
	}

	public School getSchoolIdByRegId(long regId) {
		School school = userRegistrationDAO.getUserRegistration(regId)
				.getSchool();

		return school;
	}

	public String getDate(long regId) {
		String[] strDays = new String[] { "Sunday", "Monday", "Tuesday",
				"Wednesday", "Thursday", "Friday", "Saturday" };
		String months[] = { "January", "February", "March", "April", "May",
				"June", "July", "Auguest", "September", "October", "November",
				"December" };
		Calendar cal = new GregorianCalendar();
		return strDays[cal.get(Calendar.DAY_OF_WEEK) - 1] + ", "
				+ months[(cal.get(Calendar.MONTH))] + " "
				+ cal.get(Calendar.DAY_OF_MONTH) + ", "
				+ cal.get(Calendar.YEAR) + " | " + getFullName(regId);
	}

	public String getFullName(long regId) {
		String fullname = "";
		user = userRegistrationDAO.getUserRegistration(regId);
		if (user.getTitle() != null) {
			fullname = user.getTitle() + " " + user.getFirstName() + " "
					+ user.getLastName();
		} else {
			fullname = user.getFirstName() + " " + user.getLastName();
		}

		return fullname;
	}

	public String getSchoolName(long schoolId) {

		return schoolDAO.getSchool(schoolId).getSchoolName();

	}

	public long getParentRegId(String emailId) {

		return userRegistrationDAO.parentregId(emailId);
	}
	public long getParentRegId2(String emailId) {

		return userRegistrationDAO.parentregId2(emailId);
	}

	public long getregId(String emailId) {
		return userRegistrationDAO.getregId(emailId);
	}

	public boolean isParentGotRegistered(UserRegistration userR) {
		boolean flag = false;
		Security sec = null;
			try {
				sec = securityDAO.getSecurity(userR.getParentRegId());
				if(sec.getStatus()!=null && sec.getStatus().equals(WebKeys.LP_STATUS_INACTIVE)){
					flag = true;
				}
				else
					flag = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
	}

	public long getGradeClassId(long gradeId, long classId) {
		long gradeclassId = AdminDAO.getGradeClassId(gradeId, classId);
		return gradeclassId;
	}

	public String getClassName(long classId) {
		return AdminDAO.getClassName(classId);
	}

	public long getUserTypeId(String user) {
		return userRegistrationDAO.getUserTypeId(user);
	}

	public long getUserType(String emailId) {
		return userRegistrationDAO.getUserType(emailId);
	}

	public HomeroomClasses getHomeRoomByTeacher(long teacherId, long periodId) {
		return adminSchedulerDAO.getHomeRoomByTeacher(teacherId, periodId);
	}

	public String getSectionName(long sectionId) {
		return adminSchedulerDAO.getSectionName(sectionId);
	}

	public List<ClassActualSchedule> getClassSchedule(long regId, long periodId) {
		return adminSchedulerDAO.getClassSchedule(regId, periodId);

	}

	public String getGradethvalue(long gradeId) {
		return adminSchedulerDAO.getGradethvalue(gradeId);
	}

	public List<Long> getDayIdsByCsId(long csId, long periodId) {
		return adminSchedulerDAO.getDayIdsByCsId(csId, periodId);
	}

	public List<Student> getStudentsBySection(long csId) {
		return teacherDAO.getStudentsBySection(csId);
	}

	public List<Attendance> getStudentsAttendance(long csId, String dateToUpdate) {
		return teacherViewClassDAO.getStudentsAttendance(csId, dateToUpdate);
	}

	public List<RegisterForClass> getStudentsByCsId(long csId) {
		return teacherDAO.getStudentsByCsId(csId);
	}

	public List<String> getStudentsByCsIdAndRtiGroup(long csId, long rtiGroupId) {
		List<String> studentList = new ArrayList<String>();
		List<RegisterForClass> list = teacherDAO.getStudentsByCsIdAndRtiGroup(
				csId, rtiGroupId);
		for (RegisterForClass regForClass : list) {
			studentList.add(String.valueOf(regForClass.getStudent().getStudentId()));
		}
		return studentList;
	}
	
	public long getTeacherSubjectId(long gradeId, long classId, long teacherId){
		return commonDAO.getTeacherSubjectId(gradeId, classId, teacherId);
	}
	public Student getStudent(UserRegistration userReg){
		return commonDAO.getStudent(userReg);
	}

	public List<ParentLastseen> getParentLastSeenWithStudent(long csId) throws DataException {
		List<ParentLastseen> pls = Collections.emptyList();
		try{
			pls = parentDao.getAllParentLastseen(csId);
			
		}catch(DataException e){
			logger.error("Error in getParentLastSeenWithStudent() of CommonService"+ e);
		}
		return pls;
	}
	public boolean isTimeBetweenTwoTime(String startTime, String endTime, String newStartTime, String newEndTime) throws ParseException {
		boolean isOverlapped = false;
		Date startTimeDate;
		Date endTimeDate;
		Date startNewStartTime;
		Date endNewEndTime;
		try{
		
		if(startTime != null && startTime != ""){
			 startTimeDate = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(startTime);
			 startTime = new SimpleDateFormat(WebKeys.SYSTEM_TIME_FORMATE).format(startTimeDate);
		}
		
		if(endTime != null && endTime != ""){
			 endTimeDate = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(endTime);
			 endTime = new SimpleDateFormat(WebKeys.SYSTEM_TIME_FORMATE).format(endTimeDate);
		}
		
		if(newStartTime != null && newStartTime != ""){
			 startNewStartTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(newStartTime);
			 newStartTime = new SimpleDateFormat(WebKeys.SYSTEM_TIME_FORMATE).format(startNewStartTime);
		}
		
		if(newEndTime != null && newEndTime != ""){
			endNewEndTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(newEndTime);
			newEndTime = new SimpleDateFormat(WebKeys.SYSTEM_TIME_FORMATE).format(endNewEndTime);
		}
		
		if(startTime.equalsIgnoreCase(newStartTime) || endTime.equalsIgnoreCase(newEndTime)){
			isOverlapped = true;
		}
		if(!isOverlapped && newStartTime != null && newStartTime != ""){
			startTimeDate = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(startTime);
			endTimeDate = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(endTime);
		    Date checkNewTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(newStartTime);
		    isOverlapped = checkNewTime.after(startTimeDate) && checkNewTime.before(endTimeDate);
		}
		if(!isOverlapped && newEndTime != null && newEndTime != ""){
			startTimeDate = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(startTime);
			endTimeDate = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(endTime);
		    Date checkNewTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(newEndTime);
		    isOverlapped = checkNewTime.after(startTimeDate) && checkNewTime.before(endTimeDate);
		}
		if(!isOverlapped && startTime != null && startTime != ""){
			startNewStartTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(newStartTime);
			endNewEndTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(newEndTime);
		    Date checkNewTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(startTime);
		    isOverlapped = checkNewTime.after(startNewStartTime) && checkNewTime.before(endNewEndTime);
		}
		if(!isOverlapped && endTime != null && endTime != ""){
			startNewStartTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(newStartTime);
			endNewEndTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(newEndTime);
		    Date checkNewTime = new SimpleDateFormat(WebKeys.COMPLETE_TIME_FORMATE).parse(endTime);
		    isOverlapped = checkNewTime.after(startNewStartTime) && checkNewTime.before(endNewEndTime);
		}
		}catch(Exception e){
			logger.error("Error in isTimeBetweenTwoTime() of CommonService"+ e);
		}
        return isOverlapped;
	}

	public UserRegistration getNewUserRegistration(String emailId) {
		return userRegistrationDAO.getNewUserRegistration(emailId);

	}
	
	public UserRegistration getActiveUserRegistration(String emailId) {
		return userRegistrationDAO.getActiveUserRegistration(emailId);
	}
	
	public UserRegistration getNewORActiveUserRegistration(String emailId) {
		return userRegistrationDAO.getNewORActiveUserRegistration(emailId);
	}
	
	public UserRegistration getUserRegistrationBySchool(String emailId, long schoolId) {
		return userRegistrationDAO.getUserRegistrationBySchool(emailId, schoolId);
	}
	
	public long getCsIdBySectionId(long sectionId){
		return adminSchedulerDAO.getCsIdBySectionId(sectionId);
	}
	public List<AssignmentType> getComprehensionTypes(){
		return assignmentTypeDAO.getComprehensionTypes();
	}
	public List<BenchmarkResults> getBenchmarkResults(long schoolId,String[] teacherIds, String[] benchmarkIds){
		List<BenchmarkResults> benchmarkResults = null;
		try{
			List<Long> teacherList = new ArrayList<>();
			List<Long> bencmarkTypes = new ArrayList<>();
			for(int i =0 ; i<teacherIds.length; i++){
				teacherList.add(Long.parseLong(teacherIds[i]));
			}
			for(int i =0 ; i<benchmarkIds.length; i++){
				bencmarkTypes.add(Long.parseLong(benchmarkIds[i]));
			}
			benchmarkResults = commonDAO.getBenchmarkResults(schoolId,teacherList ,bencmarkTypes);
		}
		catch(Exception e){
			logger.error("Exception in CommonService"+e);
		}
		return benchmarkResults;
	}
	public String getStudentRTIGroup(long csId, long studentId){
		return commonDAO.getStudentRTIGroup(csId, studentId);
	}
	public List<AssignK1Tests> getEarlyLitracyTestResults(){
		List<AssignK1Tests> assignK1Tests = null;
		try{
			assignK1Tests = commonDAO.getEarlyLitracyResults();
		}
		catch(Exception e){
			logger.error("Exception in CommonService"+e);
		}
		return assignK1Tests;
	}
	public List<FluencyMarks> getFluencyMarks(long csId){
		List<FluencyMarks> fluencyMarks = new ArrayList<FluencyMarks>();
		try{
			fluencyMarks = commonDAO.getFluencyMarks(csId);
		}
		catch(Exception e){
			logger.error("Exception in CommonService"+e);
		}
		return fluencyMarks;
	}
		
	public Assignment getAssignmentByAssignmentId(long assignmentId){
		return commonDAO.getAssignmentByAssignmentId(assignmentId);
	}
	
	public void migrateIOLData(){
		commonDAO.migrateIOLData();
	}
	public List<StudentAssignmentStatus> getComprehensionResults(long schoolId,String[] teacherIds, AcademicYear academicYear){
		List<StudentAssignmentStatus> comprehensionResults = null;
		try{
			List<Long> teacherList = new ArrayList<>();
			for(int i =0 ; i<teacherIds.length; i++){
				teacherList.add(Long.parseLong(teacherIds[i]));
			}
			
			comprehensionResults = commonDAO.getComprehensionResults(schoolId,teacherList, academicYear);
		}
		catch(Exception e){
			logger.error("Exception in CommonService"+e);
		}
		return comprehensionResults;
	}
	
	public List<AcademicYear> getSchoolAcademicYears(){
		List<AcademicYear> academicYears = null;
		try{			
			academicYears = commonDAO.getSchoolAcademicYears();
		}
		catch(Exception e){
			logger.error("Exception in CommonService"+e);
		}
		return academicYears;
	}
	
	public AcademicYear getAcademicYearById(long academicYearId){
		AcademicYear academicYear = null;
		try{			
			academicYear = commonDAO.getAcademicYearById(academicYearId);
		}
		catch(Exception e){
			logger.error("Exception in CommonService"+e);
		}
		return academicYear;
	}
	
	public AcademicYear getCurrentAcademicYear(){
		AcademicYear academicYear = null;
		try{			
			academicYear = commonDAO.getCurrentAcademicYear();
		}
		catch(Exception e){
			logger.error("Exception in CommonService"+e);
		}
		return academicYear;
	}
	
	public Student getStudentByRegId(long regId) {
		Student student = null;
		try{			
			student = commonDAO.getStudentByRegId(regId);
		}
		catch(Exception e){
			logger.error("Exception in CommonService getStudentByRegId"+e);
		}
		return student;
	}
	public List<BenchmarkResults> getBenchmarkResults(long schoolId,String[] teacherIds, String[] benchmarkIds,long academicYearId){
		List<BenchmarkResults> benchmarkResults = null;
		try{
			List<Long> teacherList = new ArrayList<>();
			List<Long> bencmarkTypes = new ArrayList<>();
			for(int i =0 ; i<teacherIds.length; i++){
				teacherList.add(Long.parseLong(teacherIds[i]));
			}
			for(int i =0 ; i<benchmarkIds.length; i++){
				bencmarkTypes.add(Long.parseLong(benchmarkIds[i]));
			}
			AcademicYear acadeYr=commonDAO.getAcademicYearById(academicYearId);
			benchmarkResults = commonDAO.getBenchmarkResults(schoolId,teacherList ,bencmarkTypes,acadeYr);
		}
		catch(Exception e){
			logger.error("Exception in CommonService"+e);
		}
		return benchmarkResults;
	}
}



