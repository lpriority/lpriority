package com.lp.login.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lp.appadmin.dao.SchoolDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.model.School;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("registerUserDao")
public class RegisterUserDAOImpl extends CustomHibernateDaoSupport implements
		RegisterUserDAO {

	@Autowired
	private UserRegistrationDAO userRegDao;
	@Autowired
	private SchoolDAO schoolDao;
	@Autowired 
	private AddressDAO addressDao;

	@Override
	public boolean saveAdmin(UserRegistration userReg, School school) {
		try {
			addressDao.saveAddress(userReg.getAddress());
			userRegDao.saveUserRegistration(userReg);
			schoolDao.saveSchool(school);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
