package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.User;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("userDao")
public class UserDAOImpl extends CustomHibernateDaoSupport implements UserDAO{
	
	static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	
	@Override
	public User getUserType(long userTypeId) {
		 User aGrades = (User) super.find(User.class, userTypeId);
		 return aGrades;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserTypeList() {
		List<User> userList = null;
		userList = (List<User>) super.findAll(User.class);
		return userList;
	}

	@Override
	public void deleteUserType(long userTypeId) {
		User user = getUserType(userTypeId);
		super.delete(user);
	}

	@Override
	public void saveUserType(User user) {
		super.saveOrUpdate(user);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserTypeList2() {
		List<User> userTypeList = null;
		userTypeList = (List<User>) getHibernateTemplate().find("from User where lcase(userType) not in ('admin', 'appmanager') ");
		return userTypeList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User getUserType(String userType){
		List<User> userTypeObj = (List<User>) getHibernateTemplate().find("from User where lcase(userType) like '" + userType+"'");
		if(userTypeObj.size()>0)
			return userTypeObj.get(0);
		else 
			return new User();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserTypesForAnnouncements() {
		List<User> userTypeList = null;
		userTypeList = (List<User>) getHibernateTemplate().find("from User where lcase(userType) in ('"+WebKeys.LP_USER_TYPE_TEACHER+"', '"+WebKeys.LP_USER_TYPE_PARENT+"', '"+WebKeys.LP_USER_TYPE_STUDENT_ABOVE_13+"') ");
		return userTypeList;
	}
}
