package com.lp.admin.service;

import java.util.Map;

import com.lp.model.UserRegistration;

public interface ErrorWordItemService {

	public Map<String, Long> getErrorAnalysis(long typeId, long gradeId, UserRegistration userReg);

}
