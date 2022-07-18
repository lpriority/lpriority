package com.lp.admin.dao;

import java.util.Map;

import com.lp.model.UserRegistration;

public interface ErrorWordItemDAO {

	Map<String, Long> getErrorAnalysis(long typeId, long gradeId, UserRegistration userReg);
	
}
