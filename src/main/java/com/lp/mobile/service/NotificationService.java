package com.lp.mobile.service;

import java.util.Date;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.mobile.model.AnnouncementApp;
import com.lp.mobile.model.AttendanceApp;
import com.lp.mobile.model.ChildDashboard;
import com.lp.mobile.model.EventApp;
import com.lp.mobile.model.ScheduledClass;
import com.lp.mobile.model.StudentGoalReports;
import com.lp.mobile.model.StudentReadingRegister;
import com.lp.mobile.model.TestResults;
import com.lp.model.School;
import com.lp.model.SchoolSchedule;
import com.lp.model.UserRegistration;


public interface NotificationService {

	boolean updateAttendanceStatus(long notificationId);

	boolean updateRegisterForClassStatus(long studentId, long csId);

	boolean updateEventStatus(long notificationId);

	boolean updateNotificationStatusStatus(long notificationId);
	
	boolean updateTestResultsStatusStatus(long notificationId);

	public List<ScheduledClass> getTeacherResponseforChildRequests(UserRegistration parent) throws DataException;	

	public List<AttendanceApp> getStudentAttendance(UserRegistration parent) throws DataException;	
	
	public List<AnnouncementApp> getCurrentAnnouncementsBySchool(School schoool, Date fromDate, Date toDate);
	
	public List<EventApp> getCurrentEventsBySchool(UserRegistration userReg, Date fromDate, Date toDate);
	
	public SchoolSchedule getSchoolSchedule(School school);
	
	public List<String> getLatestNotifications(UserRegistration userReg);
	
	public List<TestResults> getChildTestResults(UserRegistration userReg, String usedFor);
	
	public List<ChildDashboard> getChildDashBoardFiles(UserRegistration userRegistration, String usedFor);
	
	public List<StudentGoalReports> getChildGoalSettingReports(UserRegistration userRegistration);
	
	public List<StudentReadingRegister> getChildReadingRegisterActivities(UserRegistration userRegistration);

	

	
}
