package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.SecurityQuestion;

public interface SecurityQuestionDAO {
	
	public SecurityQuestion getSecurityQuestion(long securityQuestionId);
	
	public List<SecurityQuestion> getSecurityQuestionList();
	
	public List<SecurityQuestion> loadSecurityQuestionList();
	
	public void deleteSecurityQuestion(long securityQuestionId);
	
	public void saveSecurityQuestion(SecurityQuestion securityQuestion);

}
