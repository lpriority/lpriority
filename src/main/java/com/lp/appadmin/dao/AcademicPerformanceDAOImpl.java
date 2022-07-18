package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.AcademicPerformance;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("academicPerformDao")
public class AcademicPerformanceDAOImpl extends CustomHibernateDaoSupport
		implements AcademicPerformanceDAO {

	static final Logger logger = Logger.getLogger(AcademicPerformanceDAOImpl.class);


	@Override
	public AcademicPerformance getAcademicPerfom(long academicPerfId) {
		return (AcademicPerformance) super.find(AcademicPerformance.class,
				academicPerfId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AcademicPerformance> getAcademicPerfList() {
		List<AcademicPerformance> apList = null;
		apList = (List<AcademicPerformance>) super
				.findAll(AcademicPerformance.class);
		return apList;
	}

	@Override
	public void deleteAcademicPerformance(long academicPerfId) {
		super.delete(getAcademicPerfom(academicPerfId));
	}

	@Override
	public void saveAcademicPerformance(AcademicPerformance academicPerf) {
		super.saveOrUpdate(academicPerf);
	}

}
