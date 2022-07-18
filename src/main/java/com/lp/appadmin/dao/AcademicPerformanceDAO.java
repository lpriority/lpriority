package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.AcademicPerformance;

public interface AcademicPerformanceDAO {
	
    public AcademicPerformance getAcademicPerfom(long academicPerfId);
	
	public List<AcademicPerformance> getAcademicPerfList();
	
	public void deleteAcademicPerformance(long academicPerfId);
	
	public void saveAcademicPerformance(AcademicPerformance academicPerf);

}
