package com.lp.login.service;

import com.lp.model.UserRegistration;
import com.lp.model.School;

public interface RegisterUserService {
	public boolean saveAdmin(UserRegistration userReg, School school);

}
