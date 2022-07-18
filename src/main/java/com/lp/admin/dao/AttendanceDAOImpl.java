package com.lp.admin.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.Attendance;
import com.lp.model.AttendanceGroupedByStatus;
import com.lp.model.ClassStatus;
import com.lp.model.SchoolAttendance;
import com.lp.model.SchoolAttendanceChart;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;
@Repository("attendanceDAO")
public class AttendanceDAOImpl extends CustomHibernateDaoSupport implements AttendanceDAO {
	
	static final Logger logger = Logger.getLogger(AttendanceDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private HttpSession httpSession;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Attendance> getAttandance(long csId, Date date) throws DataException{
		List<Attendance> attendanceList = Collections.emptyList();
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("FROM Attendance where classStatus.csId=:csId"
					+ " and date=:date order by student.userRegistration.lastName");
			query.setParameter("csId", csId);
			query.setParameter("date",date);
			attendanceList = query.list();
		}catch(DataException e){
			logger.error("Error in getAttandance() of AttendanceServiceImpl"+ e);
			e.printStackTrace();	
			throw new DataException("Error in getAttandance() of AttendanceServiceImpl",e);
		}
		return attendanceList;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<AttendanceGroupedByStatus> getAttandance(long csId, Date startDate, Date endDate) throws DataException{
		List<AttendanceGroupedByStatus> attendanceList = new ArrayList<>();
		List<Object[]> list = Collections.emptyList();
		AttendanceGroupedByStatus attendanceGroupedByStatus;
		try{			
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT student.studentId,"
					+ "student.userRegistration.firstName, student.userRegistration.lastName, classStatus.teacher.userRegistration.title,"
					+ "classStatus.teacher.userRegistration.firstName, classStatus.teacher.userRegistration.lastName,  "
					+ "SUM(CASE WHEN status='"+WebKeys.ATTENDANCE_STATUS_PRESENT+"' THEN 1 ELSE 0 END) AS Present, "
					+ "SUM(CASE WHEN status='"+WebKeys.ATTENDANCE_STATUS_ABSENT+"' THEN 1 ELSE 0 END) AS Absent,"
					+ "SUM(CASE WHEN status='"+WebKeys.ATTENDANCE_STATUS_EXCUSED_ABSENT+"' THEN 1 ELSE 0 END) AS ExcusedAbsent,"
					+ "SUM(CASE WHEN status='"+WebKeys.ATTENDANCE_STATUS_TARDY+"' THEN 1 ELSE 0 END) AS Tardy,"
					+ "SUM(CASE WHEN status='"+WebKeys.ATTENDANCE_STATUS_EXCUSED_TARDY+"' THEN 1 ELSE 0 END) AS ExcusedTardy "
					+ "FROM Attendance where classStatus.csId=? and date between ? and ? GROUP BY student.studentId order by student.userRegistration.lastName");
			query.setParameter(0, csId);
			query.setParameter(1, startDate);
			query.setParameter(2, endDate);
			list = query.list();
			for(Object[] o : list){
				attendanceGroupedByStatus = new AttendanceGroupedByStatus();
				attendanceGroupedByStatus.setStudentId((long) o[0]);
				attendanceGroupedByStatus.setStudentName((String) o[1]+" "+(String) o[2]);
				String title = (String) o[3];
				if(title == null){
					title = "";
				}
				attendanceGroupedByStatus.setTeacherName(title+" "+(String) o[4]+" "+(String) o[5]);
				attendanceGroupedByStatus.setPresentDays((long) o[6]);
				attendanceGroupedByStatus.setAbsentDays((long) o[7]);
				attendanceGroupedByStatus.setExcusedAbsentDays((long) o[8]);
				attendanceGroupedByStatus.setTardyDays((long) o[9]);
				attendanceGroupedByStatus.setExcusedTardyDays((long) o[10]);
				attendanceList.add(attendanceGroupedByStatus);
			}
		}catch(DataException e){
			logger.error("Error in getAttandance() of AttendanceServiceImpl"+ e);
			e.printStackTrace();	
			throw new DataException("Error in getAttandance() of AttendanceServiceImpl",e);
		}
		return attendanceList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ClassStatus> getScheduledClasses(long gradeClassId) throws DataException{
		List<ClassStatus> classStatusList = new ArrayList<ClassStatus>();
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("FROM ClassStatus where "
					+ "section.gradeClasses.gradeClassId=:gradeClassId and availStatus=:availStatus");
			query.setParameter("gradeClassId", gradeClassId);
			query.setParameter("availStatus",WebKeys.AVAILABLE);
			classStatusList = query.list();
		}catch(DataException e){
			logger.error("Error in getAvailableTeachers() of ParentServiceImpl", e);
		}
		return classStatusList;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public SchoolAttendanceChart getSchoolAttandance(long schoolId) throws DataException{
		SchoolAttendanceChart schoolAttendanceChart = new SchoolAttendanceChart();
		List<SchoolAttendance> schoolAttendances = new ArrayList<>();
		SchoolAttendance schoolAttendance;
		List<Object[]> attendance;
		List<Object[]> studentCounts;
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT status, "
					+ "SUM(CASE WHEN date = current_date THEN 1 ELSE 0 END) AS today, "
					+ "SUM(CASE WHEN date = CURDATE() - 1 THEN 1 ELSE 0 END) AS priorDay,"
					+ "SUM(CASE WHEN date = CURDATE() - 2 THEN 1 ELSE 0 END) AS twoDaysPrior "
					+ "FROM Attendance where classStatus.teacher.userRegistration.school.schoolId=:schoolId "
					+ "and classStatus.availStatus=:availStatus GROUP BY status");
			query.setParameter("schoolId", schoolId);
			query.setParameter("availStatus", WebKeys.AVAILABLE);
			attendance = query.list();
			for(Object[] o: attendance){
				schoolAttendance = new SchoolAttendance();
				schoolAttendance.setStatus((String) o[0]);
				schoolAttendance.setTodayCount((long) o[1]);
				schoolAttendance.setPriorDayCount((long) o[2]);
				schoolAttendance.setTwoDaysPrior((long) o[3]);	
				schoolAttendances.add(schoolAttendance);
			}	
			
			query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT  "
					+ "SUM(CASE WHEN gender = 'male'  THEN 1 ELSE 0 END) AS maleCount, "
					+ "SUM(CASE WHEN gender = 'female' THEN 1 ELSE 0 END) AS femaleCount "
					+ "FROM Student where userRegistration.school.schoolId=:schoolId"
					+ " and userRegistration.status=:status");
			query.setParameter("schoolId", schoolId);
			query.setParameter("status",WebKeys.ACTIVE);
			studentCounts = query.list();
			for(Object[] o: studentCounts){
				schoolAttendanceChart.setMaleEnrollment((long) o[0]);
				schoolAttendanceChart.setFemaleEnrollment((long) o[1]);
				schoolAttendanceChart.setTotalEnrollment((long) o[1]+(long) o[0]);
				schoolAttendanceChart.setSchoolAttendanceList(schoolAttendances);
			}		
		}catch(Exception e){
			logger.error("Error in getAttandance() of AttendanceServiceImpl"+ e);
			throw new DataException("Error in getAttandance() of AttendanceServiceImpl",e);
		}
		return schoolAttendanceChart;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ClassStatus> getScheduledClassesByTeacher(long gradeClassId, long teacherId) throws DataException{
		List<ClassStatus> classStatusList = new ArrayList<ClassStatus>();
		AcademicYear academicYear = null;
		String availStatus = WebKeys.AVAILABLE;
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try{
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				availStatus =  WebKeys.EXPIRED;
			}
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("FROM ClassStatus where "
					+ "section.gradeClasses.gradeClassId=:gradeClassId and teacher.teacherId="+teacherId+" and availStatus=:availStatus");
			query.setParameter("gradeClassId", gradeClassId);
			query.setParameter("availStatus",availStatus);
			classStatusList = query.list();
		}catch(DataException e){
			logger.error("Error in getAvailableTeachers() of ParentServiceImpl", e);
		}
		return classStatusList;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateReadStatus(long attendanceId){
		Session session = null;
		int rowsUpdated = 0;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			//Updating register_for_class table
			String hql2 = "UPDATE Attendance set readStatus = :readStatus"
					+ " WHERE attendanceId = :attendanceId";
			Query query2 = session.createQuery(hql2);			
			query2.setParameter("readStatus", WebKeys.LP_YES.toLowerCase());
			query2.setParameter("attendanceId", attendanceId);
			rowsUpdated = query2.executeUpdate();
			tx.commit();
		}
		catch(Exception e){
			tx.rollback();
			logger.error("Error in updateReadStatus() of AttendanceDAOImpl", e);
		}		
		finally{
			session.close();
		}
		if(rowsUpdated > 0)
			return true;
		else
			return false;
	}
}
