package com.lp.appadmin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.District;
import com.lp.model.Grade;
import com.lp.model.School;
import com.lp.model.Security;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;


@Repository("schoolDao")
public class SchoolDAOImpl extends CustomHibernateDaoSupport implements
		SchoolDAO {

	static final Logger logger = Logger.getLogger(SchoolDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public School getSchool(long schoolId) {
		School aschool = new School();
		try {
			aschool = (School) super.find(School.class, schoolId);
		} catch (HibernateException e) {
			logger.error("Error in getSchool() of SchoolDAOImpl" + e);
			e.printStackTrace();
		}
		return aschool;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<School> getSchoolList() {
		List<School> sList = new ArrayList<School>();
		try {
			sList = (List<School>) super.findAll(School.class);
		} catch (DataAccessException e) {
			logger.error("Error in getSchoolList() of SchoolDAOImpl" + e);
			e.printStackTrace();
		}
		return sList;
	}

	@Override
	public void deleteSchool(long schoolId) {
		try {
			School school = getSchool(schoolId);
			super.delete(school);
		} catch (HibernateException e) {
			logger.error("Error in deleteSchool() of SchoolDAOImpl" + e);
			e.printStackTrace();
		}

	}

	@Override
	public void saveSchool(School school) throws DataException {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(school);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			throw new DataException("School name already exists",	e);
		}
	}
	@Override
	public void saveDistrict(District district) {
		try {
			super.saveOrUpdate(district);
			
		} catch (HibernateException e) {
			logger.error("Error in saveDistrict() of SchoolDAOImpl"
					+ e);
			e.printStackTrace();
		}
	}
	@Override
	public void updateDistrict(District district) {
		try {
			super.Update(district);
			
		} catch (HibernateException e) {
			logger.error("Error in updateDistrict() of SchoolDAOImpl"
					+ e);
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<District> getDistrictList() {
		List<District> dList = new ArrayList<District>();
		try {
			dList = (List<District>) getHibernateTemplate().find("from District");
		} catch (DataAccessException e) {
			logger.error("Error in getDistrictList() of SchoolDAOImpl" + e);
			e.printStackTrace();
		}
		return dList;
	}
	@Override
	public District getDistrict(long districtId) {
		District adistrict = new District();
		try {
			adistrict = (District) super.find(District.class, districtId);
		} catch (HibernateException e) {
			logger.error("Error in getDistrict() of SchoolDAOImpl" + e);
			e.printStackTrace();
		}
		return adistrict;
	}
	@Override
	public int deleteDistrict(long districtId) {
		int stat=0;
		try {
			District district = getDistrict(districtId);
			super.delete(district);
			stat=1;
		} catch (HibernateException e) {
			logger.error("Error in deleteSchool() of SchoolDAOImpl" + e);
			e.printStackTrace();
			stat=0;
		}
		return stat;

	}
	@Override
	public void updateSchool(School school) {
		try {
			super.Update(school);
			
		} catch (HibernateException e) {
			logger.error("Error in updateSchool() of SchoolDAOImpl"	+ e);
			e.printStackTrace();
		}
	}

	@Override
	public void saveAdminRegistration(UserRegistration userReg, School school,Security sec) throws DataException {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(userReg);
			session.saveOrUpdate(school);
			session.saveOrUpdate(sec);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveAdminRegistration() of SchoolDAOImpl"+ e);
		}		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateAdminRegistration(UserRegistration userReg)
			throws DataException {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "UPDATE UserRegistration set school.schoolId = :schoolId , firstName = :firstName , lastName=:lastName "
					+ ", emailId=:emailId WHERE regId = :regId";
			Query query = session.createQuery(hql);
			query.setParameter("schoolId", Long.parseLong(userReg.getSchoolId()));
			query.setParameter("firstName", userReg.getFirstName());
			query.setParameter("lastName", userReg.getLastName());
			query.setParameter("emailId", userReg.getEmailId());
			query.setParameter("regId", userReg.getRegId());
			query.executeUpdate();				
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in updateAdminRegistration() of SchoolDAOImpl" + e);
			throw new DataException("Error in updateAdminRegistration() of SchoolDAOImpl",e);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkUserForUpdate(UserRegistration userReg) throws DataException {
		List<UserRegistration> list = new ArrayList<UserRegistration>();
		try{
			list = (List<UserRegistration>) getHibernateTemplate().find("from UserRegistration where "
					+ "(emailId='"+userReg.getEmailId()+"' and regId !="+userReg.getRegId() + ") or (school.schoolId="+userReg.getSchoolId()+" and user.userType='"+WebKeys.LP_USER_TYPE_ADMIN+"'"
							+ " and status = '"+WebKeys.ACTIVE+"')");
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<School> getSchoolList(long districtId) {
		List<School> sList = new ArrayList<School>();
		try {
			sList = (List<School>) getHibernateTemplate().find("from School where district_id="+ districtId);
		} catch (DataAccessException e) {
			logger.error("Error in getSchoolList() of SchoolDAOImpl" + e);
			e.printStackTrace();
		}
		return sList;
		
	}
}
