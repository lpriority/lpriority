package com.lp.login.dao;

import java.util.List;

import com.lp.model.Security;

public interface SecurityDAO {
	public Security getSecurity(long regId);
	public Security getSecurity(String emailId);
	public boolean saveSecurity(Security sec);
	public boolean checkSecurity(Security sec);
	public List<Security> getUsersList(String userType);
	public List<Security> saveBulkSecuriy(List<Security> security);
	public Security getSecurityVerification(String emailId);
	
}
