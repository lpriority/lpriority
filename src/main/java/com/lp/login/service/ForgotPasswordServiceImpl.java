package com.lp.login.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.login.dao.ForgotPasswordDAO;
import com.lp.model.Security;
import com.lp.model.SecurityQuestion;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
	@Autowired
	private ForgotPasswordDAO forgotPasswordDao;
	@Autowired
	private UserRegistrationDAO userregistrationdao;
	
	public void setAcademicGradesDao(ForgotPasswordDAO forgotPasswordDao) {
		this.forgotPasswordDao = forgotPasswordDao;
	}

	@Override
	public List<SecurityQuestion> getQuestionList() {
		List<SecurityQuestion> qList =  forgotPasswordDao.getQuestionList();
		return qList;
	}

	@Override
	public SecurityQuestion getQuestionId(long questionId) {
		return forgotPasswordDao.getQuestionId(questionId);
	}
	@Override
	public boolean checkSecurityforUser(Security security){
		String emailId=security.getEmailId();
		security.setUserRegistration(userregistrationdao.getActiveUserRegistration(emailId));
	   	return forgotPasswordDao.checkSecurityforUser(security);
	}
	@Override
	public int UpdatePassword(long regId, String password){
		return forgotPasswordDao.UpdatePasswords(regId,password);
	}
	
}
