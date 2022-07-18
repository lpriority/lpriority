package com.lp.admin.service;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.ErrorWordItemDAO;
import com.lp.model.UserRegistration;

public class ErrorWordItemServiceImpl implements ErrorWordItemService {
	
	static final Logger logger = Logger.getLogger(ErrorWordItemServiceImpl.class);
	
	@Autowired
	private ErrorWordItemDAO errorWordItemDAO;
	
	@Override
	public Map<String, Long> getErrorAnalysis(long typeId, long gradeId, UserRegistration userReg) {
		Map<String, Long> result = new TreeMap<String, Long>();
		result = errorWordItemDAO.getErrorAnalysis(typeId, gradeId, userReg);
		return result;
	}
}
