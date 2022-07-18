package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.User;

public interface UserDAO {
	
	public User getUserType(long userTypeId);
		
	public List<User> getUserTypeList();
	
	public void deleteUserType(long userTypeId);
	
	public void saveUserType(User user);
	
	public List<User> getUserTypeList2();
	
	public User getUserType(String userType);
	
	public List<User> getUserTypesForAnnouncements();

}
