package com.lp.admin.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AttendanceDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.Attendance;
import com.lp.model.AttendanceGroupedByStatus;
import com.lp.model.ClassStatus;
import com.lp.model.SchoolAttendance;
import com.lp.model.SchoolAttendanceChart;

public class AttendanceServiceImpl implements AttendanceService {
	
	static final Logger logger = Logger.getLogger(AttendanceServiceImpl.class);
	
	@Autowired
	private AttendanceDAO attendanceDAO;
	
	@Override
	public List<Attendance> getAttandance(long csId, Date date) throws DataException{
		List<Attendance> attendanceList = Collections.emptyList();
		try{
			attendanceList = attendanceDAO.getAttandance(csId, date);
		}catch(DataException e){
			logger.error("Error in getAttandance() of AttendanceServiceImpl"+ e);
			e.printStackTrace();	
			throw new DataException("Error in getAttandance() of AttendanceServiceImpl",e);
		}
		return attendanceList;
	}
	@Override
	public List<AttendanceGroupedByStatus> getAttandance(long csId, Date startDate, Date endDate) throws DataException{
		List<AttendanceGroupedByStatus> attendanceList = Collections.emptyList();
		try{
			attendanceList = attendanceDAO.getAttandance(csId, startDate, endDate);
		}catch(DataException e){
			logger.error("Error in getAttandance() of AttendanceServiceImpl"+ e);
			e.printStackTrace();	
			throw new DataException("Error in getAttandance() of AttendanceServiceImpl",e);
		}
		return attendanceList;
	}
	@Override
	public List<ClassStatus> getScheduledClasses(long gradeClassId) throws DataException{
		List<ClassStatus> classStatusList = new ArrayList<ClassStatus>();
		try{
			classStatusList = attendanceDAO.getScheduledClasses(gradeClassId);
		}catch(DataException e){
			logger.error("Error in getScheduledClasses() of ParentServiceImpl", e);
		}
		return classStatusList;
	}
	@Override
	public SchoolAttendanceChart getSchoolAttandance(long schoolId) throws DataException{
		SchoolAttendanceChart schoolAttendanceChart = new SchoolAttendanceChart();
		List<SchoolAttendance> schoolAttendanceList = new ArrayList<SchoolAttendance>();
		String[] stausName = { "Absent", "ExcusedTardy", "Present", "Tardy", "ExcusedAbsent" };

		try{
			schoolAttendanceChart = attendanceDAO.getSchoolAttandance(schoolId);
			schoolAttendanceList = schoolAttendanceChart.getSchoolAttendanceList();
			for(String st:stausName){
				boolean check = false;
				for(SchoolAttendance sa:schoolAttendanceList){
					if(sa.getStatus().equalsIgnoreCase(st)){
						check = true;
						break;
					}
				}
				if(!check){
					SchoolAttendance sAttendance = new SchoolAttendance();
					sAttendance.setStatus(st);
					sAttendance.setPriorDayCount((long) 0);
					sAttendance.setTodayCount((long) 0);
					sAttendance.setTwoDaysPrior((long) 0);
					schoolAttendanceList.add(sAttendance);
				}
			}
		}catch(DataException e){
			logger.error("Error in getAttandance() of AttendanceServiceImpl"+ e);
			throw new DataException("Error in getAttandance() of AttendanceServiceImpl",e);
		}
		return schoolAttendanceChart;
	}
	@Override
	public List<ClassStatus> getScheduledClassesByTeacher(long gradeClassId, long teacherId) throws DataException{
		List<ClassStatus> classStatusList = new ArrayList<ClassStatus>();
		try{
			classStatusList = attendanceDAO.getScheduledClassesByTeacher(gradeClassId,teacherId);
		}catch(DataException e){
			logger.error("Error in getScheduledClasses() of ParentServiceImpl", e);
		}
		return classStatusList;
	}
	@Override
	public boolean updateReadStatus(long attendanceId){
		return attendanceDAO.updateReadStatus(attendanceId);
	}
}
