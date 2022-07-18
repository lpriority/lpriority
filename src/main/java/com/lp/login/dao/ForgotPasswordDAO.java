package com.lp.login.dao;

import com.lp.model.Security;
import com.lp.model.SecurityQuestion;
import java.util.List;

public interface ForgotPasswordDAO {
	
	public SecurityQuestion getQuestionId(long QuestionId);
	
	public List<SecurityQuestion> getQuestionList();
	
	public boolean checkSecurityforUser(Security sec);
	public int UpdatePasswords(long regId, String password);

	
	}
