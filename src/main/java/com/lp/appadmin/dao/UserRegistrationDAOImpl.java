package com.lp.appadmin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.Security;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.User;
import com.lp.model.UserRegistration;
import com.lp.student.dao.RegisterForClassDAO;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;


@Repository("userRegistrationDao")
public class UserRegistrationDAOImpl extends CustomHibernateDaoSupport
		implements UserRegistrationDAO {
	
	static final Logger logger = Logger.getLogger(UserRegistrationDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RegisterForClassDAO registerForClassDAO;
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserRegistration getUserRegistration(long userRegistrationId) {
		List<UserRegistration> userRegistrationList = new ArrayList<UserRegistration>();
		userRegistrationList = (List<UserRegistration>) getHibernateTemplate().find("from UserRegistration where regId= "+userRegistrationId);
		if(userRegistrationList.size()>0)
			return userRegistrationList.get(0);
		else
			return new UserRegistration();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRegistration> getUserRegistrationList() {
		List<UserRegistration> userRegistrationList = null;
		userRegistrationList = (List<UserRegistration>) super
				.findAll(UserRegistration.class);
		return userRegistrationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRegistration> getUserRegistrationList(User userType) {
		List<UserRegistration> userRegistrationList = null;
		userRegistrationList = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where user.userTypeid= "
						+ userType.getUserTypeid() +" and status !='inactive'");
		return userRegistrationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRegistration> getUserRegistrationListBySchool(
			long schoolId, long userTypeId) {
		List<UserRegistration> userRegistrationList = null;
		userRegistrationList = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where user_typeid= " + userTypeId
						+ " and school_id=" + schoolId
						+ " and status!='inactive'");
		return userRegistrationList;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<UserRegistration> getAllUserRegistrationsBySchoolId(long schoolId) {
		List<UserRegistration> userRegLt = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where school.schoolId="+schoolId+" and status!='inactive'");
		if (userRegLt.size() > 0)
			return userRegLt;
		else
			return userRegLt;
	}
	
	@Override
	public int deleteUserRegistration(long userRegistrationId) {
		String query = "update user_registration set status=? where reg_id=?";
		int out = jdbcTemplate.update(query, WebKeys.LP_STATUS_INACTIVE, userRegistrationId);
		if (out != 0) {
			return 1;
		} else
			return 0;

	}

	@Override
	public void saveUserRegistration(UserRegistration userRegistration) {
		try{		
			getHibernateTemplate().saveOrUpdate(userRegistration);	
		} catch (HibernateException e) {
			logger.error("Error in saveUserRegistration() of UserRegistrationDAOImpl" + e);
		}
	}

	@Override
	public List<UserRegistration> getFullName(long userRegistrationId) {
		@SuppressWarnings("unchecked")
		List<UserRegistration> list = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where reg_id="
						+ userRegistrationId);
		return list;
	}

	@Override
	public long parentregId(String emailId) {
		try {
			String query = "select parent_reg_id from user_registration where email_id='"
					+ emailId + "' and status='"+WebKeys.ACTIVE+"'";
			long parentId = 0;

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				parentId = rs.getLong(1);
			}

			return parentId;
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}

	
	@Override
	public long getregId(String emailId) {
		try {
			String query = "select reg_id from user_registration where email_id='"
					+ emailId + "'";
			long regId = 0;

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				regId = rs.getLong(1);
			}

			return regId;
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public UserRegistration getUserRegistration(String emailId) {
		List<UserRegistration> userReg = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where emailId = '" + emailId + "'");
		if (userReg.size() > 0)
			return userReg.get(0);
		else
			return new UserRegistration();

	}

	@SuppressWarnings("unchecked")
	@Override
	public UserRegistration checkUserRegistration(String emailId,
			String password) {
		List<UserRegistration> userRegList = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where emailId = '" + emailId + "'"
						+ " and password='" + password + "'" + " and status='"
						+ WebKeys.ACTIVE + "'");
		UserRegistration userReg = null;
		if (userRegList.size() > 0) {
			userReg = userRegList.get(0);
		} else {
			return null;
		}
		return userReg;
	}

	@Override
	public String getMD5Conversion(String password) {
		String hashedPassword ="";
		try {
	    	Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			hashedPassword = passwordEncoder.encodePassword(password,"");
	    } catch (DataException ex) {
	        throw new DataException("Could not generate hash from String", ex);
	    }
		 return hashedPassword;
	}

	@Override
	public long getSchoolByRegId(long regId) {
		long schoolId = 0;
		schoolId =  jdbcTemplate.queryForObject("select school_id from user_registration where reg_id="+ regId, new Object[] {}, Long.class); //jdbcTemplate.queryForLong("select school_id from user_registration where reg_id="+ regId);
		return schoolId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRegistration> getStudentsBySchoolId(long schoolId) {
		{
			List<UserRegistration> list = (List<UserRegistration>) getHibernateTemplate()
					.find("from UserRegistration where school.schoolId="
							+ schoolId + " and user.userTypeid in (5,6)");
			return list;
		}
	}

	@Override
	public long getUserTypeId(String user) {
		try {
			String query = "select user_typeid from user where user_type='"
					+ user + "'";
			long usertypeid = 0;

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				usertypeid = rs.getLong(1);
			}

			return usertypeid;
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}

	@Override
	public long getUserType(String emailId) {
		try {
			String query = "select user_typeid from user_registration where email_id='"
					+ emailId + "'";
			long usertypeid = 0;

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				usertypeid = rs.getLong(1);
			}

			return usertypeid;
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRegistration> getNotRegisteredUserList(String userType, long schoolId) {
		long usertypeid = getUserTypeId(userType);
		List<UserRegistration> userRegistrationList = null;
		userRegistrationList = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where user.userTypeid= "
						+ usertypeid + " and status='"+WebKeys.LP_STATUS_NEW+"' and school.schoolId="+schoolId);
		return userRegistrationList;
	}

	@Override
	public List<UserRegistration> saveBulkUserRegistration(
			List<UserRegistration> userRegistrationList) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		long i = 0;
		for (UserRegistration ur : userRegistrationList) {
			session.save(ur.getAddress());
			session.save(ur);

			i++;
			if (i % 10 == 0) { // 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		session.close();
		return userRegistrationList;
	}

	@Override
	public List<Teacher> saveBulkTeacherRegistration(
			List<UserRegistration> userRegistrationList,
			List<Teacher> teachersList, List<Security> securityList) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			long i = 0;
			for (int objCount = 0; objCount < userRegistrationList.size(); objCount++) {
				session.save(userRegistrationList.get(objCount).getAddress());
				session.save(userRegistrationList.get(objCount));
				session.save(teachersList.get(objCount));
				session.save(securityList.get(objCount));
				i++;
				if (i % 5 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return teachersList;
	}

	@Override
	public List<Student> saveBulkStudentRegistration(
			List<UserRegistration> userRegistrationList,
			List<Student> studentList, List<Security> securityList,
			List<UserRegistration> usersToBeUpdated) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			long i = 0;
			if(!userRegistrationList.isEmpty()){
				for (int objCount = 0; objCount < userRegistrationList.size(); objCount++) {
					session.save(userRegistrationList.get(objCount).getAddress());
					session.save(userRegistrationList.get(objCount));
					session.save(studentList.get(objCount));
					session.save(securityList.get(objCount));
					i++;
					if (i % 5 == 0) { // 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
				}
			}
			if(!usersToBeUpdated.isEmpty()){
				for(UserRegistration registration :usersToBeUpdated){
					session.saveOrUpdate(registration);
					registerForClassDAO.expireAllClasses(registration);
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return studentList;
	}
	
	@Override
	public boolean saveBulkParentRegistration(List<Security> securityList){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			long i = 0;
			for (int objCount = 0; objCount < securityList.size(); objCount++) {
				session.save(securityList.get(objCount).getUserRegistration().getAddress());
				session.save(securityList.get(objCount).getUserRegistration());
				session.save(securityList.get(objCount));
				i++;
				if (i % 5 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkUserRegistration(UserRegistration userReg) {
		List<UserRegistration> list = new ArrayList<UserRegistration>();
		try{
			list = (List<UserRegistration>) getHibernateTemplate().find("from UserRegistration where "
					+ "emailId='"+userReg.getEmailId()+"' or (school.schoolId="+userReg.getSchoolId()+" and user.userType='"+WebKeys.LP_USER_TYPE_ADMIN+"'"
							+ " and status != 'inactive')");
		}
		catch(Exception e){
			logger.error("Error inside checkUserRegistration method in UserRegistrationDAOImpl");
		}
		if(list.size()>0){
			return false;
		}
		else{
			return true;
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public UserRegistration getLoginUserRegistration(String emailId) {
		
		
		List<UserRegistration> userReg = null;
		Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from UserRegistration where emailId =:emailId and status =:status");
		query.setParameter("emailId", emailId);
		query.setParameter("status", WebKeys.ACTIVE);
		userReg = query.list();
		if (userReg.size() > 0)
			return userReg.get(0);
		else
			return new UserRegistration();

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public UserRegistration getLoginByGoogleSSO(String ssoUserName) {		
		List<UserRegistration> userReg = null;
		Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from UserRegistration where ssoUserName =:ssoUserName and status =:status");
		query.setParameter("ssoUserName", ssoUserName);
		query.setParameter("status", WebKeys.ACTIVE);
		userReg = query.list();
		if (userReg.size() > 0)
			return userReg.get(0);
		else
			return new UserRegistration();

	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public UserRegistration getNewUserRegistration(String emailId) {
		List<UserRegistration> userReg = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where emailId = '" + emailId + "' and status = '"+WebKeys.LP_STATUS_NEW+"'");
		if (userReg.size() > 0)
			return userReg.get(0);
		else
			return new UserRegistration();

	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public UserRegistration getActiveUserRegistration(String emailId) {
		List<UserRegistration> userReg = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where emailId = '" + emailId + "' and status = '"+WebKeys.ACTIVE+"'");
		if (userReg.size() > 0)
			return userReg.get(0);
		else
			return new UserRegistration();

	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public UserRegistration getNewORActiveUserRegistration(String emailId) {
		List<UserRegistration> userReg = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where emailId = '" + emailId + "' and status in('"+WebKeys.ACTIVE+"', '"+WebKeys.LP_STATUS_NEW+"')");
		if (userReg.size() > 0)
			return userReg.get(0);
		else
			return new UserRegistration();

	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public UserRegistration getUserRegistrationBySchool(String emailId, long schoolId) {
		List<UserRegistration> userReg = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where emailId = '" + emailId + "' and school.schoolId="+schoolId);
		if (userReg.size() > 0)
			return userReg.get(0);
		else
			return new UserRegistration();

	}
	@Override
	public long parentregId2(String emailId) {
		try {
			String query = "select parent_reg_id2 from user_registration where email_id='"
					+ emailId + "' and status='"+WebKeys.ACTIVE+"'";
			long parentId = 0;

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				parentId = rs.getLong(1);
			}

			return parentId;
		} catch (final EmptyResultDataAccessException e) {
			return 0;
		}
	}
}
