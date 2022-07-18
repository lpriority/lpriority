package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.TeacherPerformances;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("teacherPerformancesDao")
public class TeacherPerformancesDAOImpl extends CustomHibernateDaoSupport implements TeacherPerformancesDAO{
	
	static final Logger logger = Logger.getLogger(TeacherPerformancesDAOImpl.class);

	@Override
	public TeacherPerformances getTeacherPerformance(long teacherPerformancesId) {
		TeacherPerformances teaPerformances= (TeacherPerformances) super.find(TeacherPerformances.class, teacherPerformancesId);
		return teaPerformances;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherPerformances> getTeacherPerformancesList(){
		List<TeacherPerformances> teacherPerformancesList = null;
		teacherPerformancesList= (List<TeacherPerformances>) super.findAll(TeacherPerformances.class);
		return teacherPerformancesList;
	}

	@Override
	public void deleteTeacherPerformance(long teacherPerformancesId) {
		  TeacherPerformances tp1= getTeacherPerformance(teacherPerformancesId);
		  super.delete(tp1);
	}

	@Override
	public void saveTeacherPerformance(TeacherPerformances teacherPerformances){
		super.saveOrUpdate(teacherPerformances);
	}
}
