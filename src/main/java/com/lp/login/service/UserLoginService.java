package com.lp.login.service;

import java.util.List;

import com.lp.model.Address;
import com.lp.model.Invitations;
import com.lp.model.Security;
import com.lp.model.UserRegistration;

public interface UserLoginService {
	public Security getSecurity(String emailId);
	public boolean saveSecurity(Security sec);
	public boolean checkSecurity(Security sec);
	public void saveInvitations(Invitations inv);
	public void saveAddress(Address address);
	public UserRegistration checkUserRegistration(String emailId,String password);
	public String getMD5Conversion(String password);
	public long getSchoolByRegId(UserRegistration userReg);
	public List<Security> getUsersNotRegistered(String userType);
	public List<UserRegistration> getUserNotRegistered(String userType, long schoolId);
	public Security getSecurityForVerification(String emailId);
	
}
