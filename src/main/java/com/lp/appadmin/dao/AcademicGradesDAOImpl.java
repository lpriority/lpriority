package com.lp.appadmin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lp.model.AcademicGrades;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("academicGradesDao")
public class AcademicGradesDAOImpl extends CustomHibernateDaoSupport implements AcademicGradesDAO 
{
	static final Logger logger = Logger.getLogger(AcademicGradesDAOImpl.class);

	
	public AcademicGrades getAcademicGrade(long academicGradeId) {
		AcademicGrades aGrades = new AcademicGrades();
		aGrades = (AcademicGrades)super.find(AcademicGrades.class, academicGradeId);
		return aGrades;
	}

	
	@SuppressWarnings("unchecked")
	public List<AcademicGrades> getAcademicGradeList() {
		List<AcademicGrades> agList = new ArrayList<AcademicGrades>();
		try {
			agList = (List<AcademicGrades>) super.findAll(AcademicGrades.class);
		} catch (DataAccessException e) {
			logger.error("Error in getAcademicGradeList() of AcademicGradesDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return agList;
	}

	@Override
	public void deleteAcademicGrade(long academicGradeId) {
		try {
		super.delete(getAcademicGrade(academicGradeId));
		} catch (HibernateException e) {
			logger.error("Error in deleteAcademicGrade() of AcademicGradesDAOImpl"
					+ e);
			e.printStackTrace();
		}

	}

	@Override
	public void saveAcademicGrade(AcademicGrades academicGrade) {
		try {
			super.saveOrUpdate(academicGrade);
		} catch (HibernateException e) {
			logger.error("Error in saveAcademicGrade() of AcademicGradesDAOImpl"
					+ e);
			e.printStackTrace();
		}
	}

}
