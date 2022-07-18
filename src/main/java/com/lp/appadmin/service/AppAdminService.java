package com.lp.appadmin.service;

import java.io.InputStream;
import java.util.List;

import com.lp.model.AssignmentType;
import com.lp.model.Days;
import com.lp.model.Minutes;
import com.lp.model.SecurityQuestion;
import com.lp.model.User;

public interface AppAdminService {

	/* ##### User methods starts from here ##### */

	public List<User> getUserTypes();

	public User getUserType(long userTypeIdId);

	public void deleteUserType(long userTypeId);

	public void saveUserType(User user);

	public List<User> getUserTypeList2();
	
	public User getUserType(String userType);
	
	/* ##### AssignmentType methods starts from here ##### */

	public List<AssignmentType> getAssignmentTypes();

	public AssignmentType getAssignmentType(long assignmentTypeIdId);

	public void deleteAssignmentType(long assignmentTypeId);

	public void saveAssignmentType(AssignmentType assignmentType);

	/* ##### SecurityQuestion methods starts from here ##### */

	public List<SecurityQuestion> getSecurityQuestions();
	
	public List<SecurityQuestion> loadSecurityQuestions();

	public SecurityQuestion getSecurityQuestion(long securityQuestionId);

	public void deleteSecurityQuestion(long securityQuestionId);

	public void saveSecurityQuestion(SecurityQuestion securityQuestionId);

	/* ##### Minutes methods starts from here ##### */

	public List<Minutes> getMinutes();

	public Minutes getMinute(long minuteId);

	public void deleteMinute(long minuteId);

	public void saveMinute(Minutes minute);

	/* ##### Days methods starts from here ##### */

	public List<Days> getDays();

	public Days getDay(long dayId);

	public void deleteDay(long dayId);

	public void saveDay(Days day);
	
	public boolean bulkRegisterUsers(InputStream inputStream, long userTypeId) throws Exception;
	
	
	public long massScheduleStudents();
	
	public void updateStudentGradeAndSchool(InputStream xmlFile) throws Exception;	
}
