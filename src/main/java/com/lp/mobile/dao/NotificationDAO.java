package com.lp.mobile.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.mobile.model.StarScoresReports;
import com.lp.model.Attendance;
import com.lp.model.EventStatus;
import com.lp.model.NotificationStatus;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.StarScores;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentStarStrategies;
import com.lp.model.UserRegistration;


public interface NotificationDAO {

	boolean updateAttendanceStatus(long notificationId);

	boolean updateRegisterForClassStatus(long studentId, long csId);

	boolean updateEventStatus(long notificationId);

	boolean updateNotificationStatusStatus(long notificationId);
	
	boolean updateTestResultsStatusStatus(long notificationId);
	
	public List<RegisterForClass> getTeacherResponseforChildRequests(UserRegistration parent) throws DataException;
	
	public List<Attendance> getStudentAttendance(UserRegistration parent) throws DataException;
	
	public List<NotificationStatus> getCurrentAnnouncementsBySchool(School schoool, Date fromDate, Date toDate);
	
	public List<EventStatus> getCurrentEventsBySchool(UserRegistration userRegistration, Date fromDate, Date toDate);
		
	public List<RegisterForClass> getUnreadTeacherResponseforChildRequests(UserRegistration parent) throws DataException;
	
	public List<Attendance> getUnreadStudentAttendance(UserRegistration parent) throws DataException;
	
	public List<NotificationStatus> getUnreadCurrentAnnouncementsBySchool(School schoool, Date fromDate, Date toDate);
	
	public List<EventStatus> getUnreadCurrentEventsBySchool(School schoool, Date fromDate, Date toDate);
	
	public List<StudentAssignmentStatus> getChildTestResults(UserRegistration userReg, String usedFor);
	
	public List<StudentAssignmentStatus> getUnreadChildTestResults(UserRegistration userReg, String usedFor);
	
	public List<StarScores> getStarReports(long studentId,long caasppTypeId,long gradeId);
	
}
