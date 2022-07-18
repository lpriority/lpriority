package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.TeacherPerformances;

public interface TeacherPerformancesDAO {
	
	public TeacherPerformances getTeacherPerformance(long teacherPerformancesId);
	
	public List<TeacherPerformances> getTeacherPerformancesList();
	
	public void deleteTeacherPerformance(long teacherPerformancesId);
	
	public void saveTeacherPerformance(TeacherPerformances teacherPerformances);

}
