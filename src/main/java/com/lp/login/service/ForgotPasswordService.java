package com.lp.login.service;

import java.util.List;



import com.lp.model.Security;
import com.lp.model.SecurityQuestion;

public interface ForgotPasswordService {
	public List<SecurityQuestion> getQuestionList();
	public SecurityQuestion getQuestionId(long QuestionId);
	public boolean checkSecurityforUser(Security security);
	public int UpdatePassword(long regId, String password);
	
}
 
