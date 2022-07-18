package com.lp.admin.dao;

import java.sql.Date;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.Attendance;
import com.lp.model.AttendanceGroupedByStatus;
import com.lp.model.ClassStatus;
import com.lp.model.SchoolAttendanceChart;

public interface AttendanceDAO {
	public List<Attendance> getAttandance(long csId, Date date) throws DataException;
	public List<AttendanceGroupedByStatus> getAttandance(long csId, Date startDate, Date endDate) throws DataException;
	public List<ClassStatus> getScheduledClasses(long gradeClassId) throws DataException;
	public SchoolAttendanceChart getSchoolAttandance(long schoolId) throws DataException;
	public List<ClassStatus> getScheduledClassesByTeacher(long gradeClassId, long teacherId) throws DataException;
	public boolean updateReadStatus(long attendanceId);
}
