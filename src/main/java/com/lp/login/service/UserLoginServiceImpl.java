package com.lp.login.service;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lp.appadmin.dao.UserDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.login.dao.AddressDAO;
import com.lp.login.dao.InvitationsDAO;
import com.lp.login.dao.SecurityDAO;
import com.lp.model.Address;
import com.lp.model.Invitations;
import com.lp.model.Security;
import com.lp.model.UserRegistration;

public class UserLoginServiceImpl implements UserLoginService {
	@Autowired
	private SecurityDAO secDAO;
	@Autowired
	private UserRegistrationDAO userRegDAO;
	@Autowired
	private InvitationsDAO invitationsDao;
	@Autowired
	private AddressDAO addressDao;
	@Autowired
	private UserDAO userDao;

	@Override
	public Security getSecurity(String emailId) {
		return secDAO.getSecurity(emailId);
	}

	@Override
	public boolean saveSecurity(Security sec) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		sec.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		sec.setChangeDate(changeDate);
		return secDAO.saveSecurity(sec);
	}

	@Override
	public void saveInvitations(Invitations inv) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		inv.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		inv.setChangeDate(changeDate);
		inv.setUser(userDao.getUserType(inv.getUserTypeid()));
		invitationsDao.saveInvitations(inv);
	}

	@Override
	public void saveAddress(Address address) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		address.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
		address.setChangeDate(changeDate);
		addressDao.saveAddress(address);
	}

	@Override
	public UserRegistration checkUserRegistration(String emailId,
			String password) {
		return userRegDAO.checkUserRegistration(emailId, password);
	}

	@Override
	public String getMD5Conversion(String password) {
		return userRegDAO.getMD5Conversion(password);
	}

	@Override
	public boolean checkSecurity(Security sec) {
		return secDAO.checkSecurity(sec);
	}

	@Override
	public long getSchoolByRegId(UserRegistration userReg) {
		long schoolId = userRegDAO.getSchoolByRegId(userReg.getRegId());
		return schoolId;
	}

	@Override
	public List<Security> getUsersNotRegistered(String userType) {
		return secDAO.getUsersList(userType);
	}
	@Override
	public List<UserRegistration> getUserNotRegistered(String userType, long schoolId) {
		return userRegDAO.getNotRegisteredUserList(userType, schoolId);
	}
	
	public Security getSecurityForVerification(String emailId){
		return secDAO.getSecurityVerification(emailId);
	}
}
