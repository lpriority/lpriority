package com.lp.appadmin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lp.model.SchoolLevel;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("schoolLevelDao")
public class SchoolLevelDAOImpl extends CustomHibernateDaoSupport implements SchoolLevelDAO 
{
	static final Logger logger = Logger.getLogger(SchoolLevelDAOImpl.class);

	
	public SchoolLevel getSchoolLevel(long schoolLevelId) {
		SchoolLevel schoolLevels = new SchoolLevel();
		schoolLevels = (SchoolLevel)super.find(SchoolLevel.class, schoolLevelId);
		return schoolLevels;
	}

	
	@SuppressWarnings("unchecked")
	public List<SchoolLevel> getSchoolLevelList() {
		List<SchoolLevel> slList = new ArrayList<SchoolLevel>();
		try {
			slList = (List<SchoolLevel>) super.findAll(SchoolLevel.class);
		} catch (DataAccessException e) {
			logger.error("Error in getSchoolLevelList() of SchoolLevelDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return slList;
	}

	@Override
	public void deleteSchoolLevel(long schoolLevelId) {
		try {
		super.delete(getSchoolLevel(schoolLevelId));
		} catch (HibernateException e) {
			logger.error("Error in deleteSchoolLevel() of SchoolLevelDAOImpl"
					+ e);
			e.printStackTrace();
		}

	}

	@Override
	public void saveSchoolLevel(SchoolLevel schoolLevel) {
		try {
			super.saveOrUpdate(schoolLevel);
		} catch (HibernateException e) {
			logger.error("Error in saveSchoolLevel() of SchoolLevelDAOImpl"
					+ e);
			e.printStackTrace();
		}
	}

}
