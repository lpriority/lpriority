package com.lp.login.dao;

import com.lp.model.School;
import com.lp.model.UserRegistration;

public interface RegisterUserDAO {
	public boolean saveAdmin(UserRegistration userReg, School school);
}
