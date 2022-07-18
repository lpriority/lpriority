package com.lp.appadmin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lp.model.SchoolType;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("schoolTypeDao")
public class SchoolTypeDAOImpl extends CustomHibernateDaoSupport implements SchoolTypeDAO 
{
	static final Logger logger = Logger.getLogger(SchoolTypeDAOImpl.class);

	
	public SchoolType getSchoolType(long schoolTypeId) {
		SchoolType sTypes = new SchoolType();
		sTypes = (SchoolType)super.find(SchoolType.class, schoolTypeId);
		return sTypes;
	}

	
	@SuppressWarnings("unchecked")
	public List<SchoolType> getSchoolTypeList() {
		List<SchoolType> agList = new ArrayList<SchoolType>();
		try {
			agList = (List<SchoolType>) super.findAll(SchoolType.class);
		} catch (DataAccessException e) {
			logger.error("Error in getAcademicGradeList() of SchoolTypeDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return agList;
	}

	@Override
	public void deleteSchoolType(long schoolTypeId) {
		try {
		super.delete(getSchoolType(schoolTypeId));
		} catch (HibernateException e) {
			logger.error("Error in deleteAcademicGrade() of SchoolTypeDAOImpl"
					+ e);
			e.printStackTrace();
		}

	}

	@Override
	public void saveSchoolType(SchoolType schoolType) {
		try {
			super.saveOrUpdate(schoolType);
		} catch (HibernateException e) {
			logger.error("Error in saveAcademicGrade() of SchoolTypeDAOImpl"
					+ e);
			e.printStackTrace();
		}
	}
	
}
