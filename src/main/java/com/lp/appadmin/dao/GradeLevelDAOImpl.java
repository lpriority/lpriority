package com.lp.appadmin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lp.model.GradeLevel;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("gradeLevelDAO")
public class GradeLevelDAOImpl extends CustomHibernateDaoSupport implements
		GradeLevelDAO {

	static final Logger logger = Logger.getLogger(GradeLevelDAOImpl.class);

	@Override
	public GradeLevel getGradeLevel(long gradeLevelId) {
		GradeLevel gl = (GradeLevel) super.find(GradeLevel.class, gradeLevelId);
		return gl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GradeLevel> getGradeLevelList() {
		List<GradeLevel> glList = new ArrayList<GradeLevel>();
		try {
			glList = (List<GradeLevel>) super.findAll(GradeLevel.class);
		} catch (DataAccessException e) {
			logger.error("Error in getGradeLevelList() of GradeLevelDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return glList;
	}

	@Override
	public void deleteGradeLevel(long gradeLevelId) {
		try {
			super.delete(getGradeLevel(gradeLevelId));
		} catch (HibernateException e) {
			logger.error("Error in deleteGradeLevel() of GradeLevelDAOImpl" + e);
			e.printStackTrace();
		}

	}

	@Override
	public void saveGradeLevel(GradeLevel gradeLevel) {
		try {
			super.saveOrUpdate(gradeLevel);
		} catch (HibernateException e) {
			logger.error("Error in saveGradeLevel() of GradeLevelDAOImpl" + e);
			e.printStackTrace();
		}
	}

}
