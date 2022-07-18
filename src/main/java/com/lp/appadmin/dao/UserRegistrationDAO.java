package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.Security;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.User;
import com.lp.model.UserRegistration;

public interface UserRegistrationDAO {

	public UserRegistration getUserRegistration(long userRegistrationId);

	public List<UserRegistration> getUserRegistrationList();

	public List<UserRegistration> getUserRegistrationList(User userType);

	public List<UserRegistration> getUserRegistrationListBySchool(long schoolId, long userTypeId);
	
	public List<UserRegistration> getAllUserRegistrationsBySchoolId(long schoolId);

	public int deleteUserRegistration(long userRegistrationId);

	public void saveUserRegistration(UserRegistration userRegistration);

	public List<UserRegistration> saveBulkUserRegistration(
			List<UserRegistration> userRegistrationList);

	public List<Teacher> saveBulkTeacherRegistration(
			List<UserRegistration> userRegistrationList,
			List<Teacher> teachersList, List<Security> securityList);

	public List<Student> saveBulkStudentRegistration(
			List<UserRegistration> userRegistrationList,
			List<Student> studentList, List<Security> securityList, List<UserRegistration> usersToBeUpdated);

	public boolean saveBulkParentRegistration(List<Security> securityList);

	@SuppressWarnings("rawtypes")
	public List getFullName(long userRegistrationId);

	public long parentregId(String emailId);

	public long getregId(String emailId);

	public UserRegistration getUserRegistration(String emailId);

	public UserRegistration checkUserRegistration(String emailId,
			String password);

	public String getMD5Conversion(String password);

	public long getSchoolByRegId(long regId);

	public List<UserRegistration> getStudentsBySchoolId(long schoolId);

	public long getUserTypeId(String user);

	public long getUserType(String emailId);

	public List<UserRegistration> getNotRegisteredUserList(String userType, long schoolId);
	
	public boolean checkUserRegistration(UserRegistration userRegistration);
	
	public UserRegistration getLoginUserRegistration(String emailId);
	
	public UserRegistration getNewUserRegistration(String emailId);
	
	public UserRegistration getActiveUserRegistration(String emailId);
	
	public UserRegistration getNewORActiveUserRegistration(String emailId);
	
	public UserRegistration getUserRegistrationBySchool(String emailId, long schoolId);
	
	public long parentregId2(String emailId);
	
	public UserRegistration getLoginByGoogleSSO(String ssoUserName);
	
	
}
