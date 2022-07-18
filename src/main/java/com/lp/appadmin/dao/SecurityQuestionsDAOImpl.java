package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.SecurityQuestion;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("securityQuestionDao")
public class SecurityQuestionsDAOImpl extends CustomHibernateDaoSupport
		implements SecurityQuestionDAO {
	
	static final Logger logger = Logger.getLogger(SecurityQuestionsDAOImpl.class);

	@Override
	public SecurityQuestion getSecurityQuestion(long securityQuestionId) {
		SecurityQuestion sq= (SecurityQuestion) super.find(SecurityQuestion.class,securityQuestionId);
		return sq;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityQuestion> getSecurityQuestionList() {
		List<SecurityQuestion> securityQuestionList = null;
		securityQuestionList = (List<SecurityQuestion>)  super.findAll(SecurityQuestion.class);
		return securityQuestionList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityQuestion> loadSecurityQuestionList() {
		List<SecurityQuestion> securityQuestionList = null;
		securityQuestionList = (List<SecurityQuestion>)  super.loadAll(SecurityQuestion.class);
		return securityQuestionList;
	}

	@Override
	public void deleteSecurityQuestion(long securityQuestionId) {
		SecurityQuestion sq= getSecurityQuestion(securityQuestionId);
		super.delete(sq);
	}

	@Override
	public void saveSecurityQuestion(SecurityQuestion securityQuestion) {
		super.saveOrUpdate(securityQuestion);
	}
}
