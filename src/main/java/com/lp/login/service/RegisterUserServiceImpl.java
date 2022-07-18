package com.lp.login.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.lp.login.dao.RegisterUserDAO;
import com.lp.model.School;
import com.lp.model.UserRegistration;

public class RegisterUserServiceImpl implements RegisterUserService {
	@Autowired
	private RegisterUserDAO regUserDAO;

	@Override
	public boolean saveAdmin(UserRegistration userReg, School school) {
		return regUserDAO.saveAdmin(userReg, school);
	}
}
