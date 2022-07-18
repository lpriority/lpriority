package com.lp.appadmin.service;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.appadmin.dao.AcademicGradesDAO;
import com.lp.appadmin.dao.AcademicPerformanceDAO;
import com.lp.appadmin.dao.GradeLevelDAO;
import com.lp.appadmin.dao.MasterGradesDAO;
import com.lp.appadmin.dao.GradeEventsDAO;
import com.lp.model.AcademicGrades;
import com.lp.model.AcademicPerformance;
import com.lp.model.GradeLevel;
import com.lp.model.Gradeevents;
import com.lp.model.MasterGrades;
@RemoteProxy(name = "appAdmin3Service")
public class AppAdminService3Impl implements AppAdminService3 {
	@Autowired
	private AcademicGradesDAO academicGradesDao;
	@Autowired
	private AcademicPerformanceDAO academicPerformanceDao;

	@Autowired
	private MasterGradesDAO masterGradesDao;

	@Autowired
	private GradeEventsDAO gradeEventsDao;

	@Autowired
	private GradeLevelDAO gradeLevelDao;
	static final Logger logger = Logger.getLogger(AppAdminService3Impl.class);


	public void setAcademicGradesDao(AcademicGradesDAO academicGradesDao) {
		this.academicGradesDao = academicGradesDao;
	}

	public void setAcademicPerformanceDAO(
			AcademicPerformanceDAO academicPerformanceDao) {
		this.academicPerformanceDao = academicPerformanceDao;
	}

	public void setMasterGradesDAO(MasterGradesDAO masterGradesDao) {
		this.masterGradesDao = masterGradesDao;
	}

	public void setGradeeventsDAO(GradeEventsDAO gradeEventsDao) {
		this.gradeEventsDao = gradeEventsDao;
	}

	public void setGradeLevelDao(GradeLevelDAO gradeLevelDao) {
		this.gradeLevelDao = gradeLevelDao;
	}

	/* ##### AcademicGrades methods goes here ##### */
	public List<AcademicGrades> getAcademicGrades() {
		return academicGradesDao.getAcademicGradeList();
	}

	public AcademicGrades getAcademicGrade(long academicGradeId) {
		return academicGradesDao.getAcademicGrade(academicGradeId);
	}

	@RemoteMethod
	public void deleteAcademicGrade(long academicGradeId) {
		academicGradesDao.deleteAcademicGrade(academicGradeId);
	}

	public void saveAcademicGrade(AcademicGrades academicGrade) {
		academicGrade.setAcademicPerformance(getAcademicPerformance(Long.valueOf(academicGrade.getAcademicId())));
		academicGradesDao.saveAcademicGrade(academicGrade);
	}

	/* ##### AcademicPerformance methods goes here ##### */

	public List<AcademicPerformance> getAcademicPerformances() {
		return academicPerformanceDao.getAcademicPerfList();
	}

	public AcademicPerformance getAcademicPerformance(long academicPerformanceId) {
		return academicPerformanceDao.getAcademicPerfom(academicPerformanceId);
	}

	public void deleteAcademicPerformance(long academicPerformanceId) {
		academicPerformanceDao.deleteAcademicPerformance(academicPerformanceId);
	}

	public void saveAcademicPerformance(AcademicPerformance academicPerformanc) {
		academicPerformanceDao.saveAcademicPerformance(academicPerformanc);
	}

	/* ##### GradeLevel methods goes here ##### */
	public List<GradeLevel> getGradeLevel() {
		return gradeLevelDao.getGradeLevelList();
	}

	public GradeLevel getGradeLevel(long gradeLevelId) {
		return gradeLevelDao.getGradeLevel(gradeLevelId);
	}

	public void deleteGradeLevel(long gradeLevelId) {
		gradeLevelDao.deleteGradeLevel(gradeLevelId);
	}

	public void saveGradeLevel(GradeLevel gradeLevel) {
		gradeLevelDao.saveGradeLevel(gradeLevel);
	}

	/* ##### Gradeevents methods goes here ##### */
	public List<Gradeevents> getGradeEvents() {
		return gradeEventsDao.getGradeEventList();
	}

	public Gradeevents getGradeEvent(long gradeEventId) {
		return gradeEventsDao.getGradeEvent(gradeEventId);
	}

	public void deleteGradeEvent(long gradeEventId) {
		gradeEventsDao.deleteGradeEvent(gradeEventId);
	}

	public void saveGradeEvent(Gradeevents gradeEvent) {
		gradeEventsDao.saveGradeEvent(gradeEvent);
	}

	/* ##### MasterGrades methods goes here ##### */
	public List<MasterGrades> getMasterGrades() {
		return masterGradesDao.getMasterGradesList();
	}
	public MasterGrades getMasterGrade(long masterGradeId) {
		return masterGradesDao.getMasterGrade(masterGradeId);
	}

	public void deleteMasterGrade(long masterGradeId) {
		masterGradesDao.deleteMasterGrades(masterGradeId);
	}

	public void saveMasterGrade(MasterGrades masterGrade) {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		Timestamp changeDate = new Timestamp(millis);
		masterGrade.setCreateDate(new java.sql.Date(new java.util.Date()
				.getTime()));
		masterGrade.setChangeDate(changeDate);
		masterGradesDao.saveMasterGrades(masterGrade);
	}

}
