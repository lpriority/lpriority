package com.lp.admin.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lp.model.ClassActualSchedule;
import com.lp.model.ClassStatus;
import com.lp.model.HomeroomClasses;
import com.lp.model.Periods;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.SchoolDays;
import com.lp.model.SchoolSchedule;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("adminSchedulerDAO")
public class AdminSchedulerDAOImpl extends CustomHibernateDaoSupport implements
		AdminSchedulerDAO {
	
	static final Logger logger = Logger.getLogger(AdminSchedulerDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@Override
	public boolean checkPeriodsBySchoolId(School school) {
		boolean check = false;
		try {
			String query = "select count(*) from periods p, grade g where p.grade_id=g.grade_id and g.school_id="
					+ school.getSchoolId() + "";
			int count = 0;
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0)
				check = true;
		} catch (HibernateException e) {
			logger.error("Error in checkPeriodsBySchoolId() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return check;
	}

	@Override
	public boolean deletePeriods(School school) {
		int out = 0;
		try {
			String query = "delete from periods where grade_id in (select grade_id from grade where school_id=?)";
			out = jdbcTemplate.update(query, school.getSchoolId());
		} catch (Exception e) {
			logger.error("Error in deletePeriods() of AdminSchedulerDAOImpl" + e);
			e.printStackTrace();
		}
		if (out != 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean makeClassesExpired(School school) {
		try {
			Query q = getHibernateTemplate()
					.getSessionFactory()
					.openSession()
					.createQuery(
							"UPDATE RegisterForClass SET classStatus_1 = :classStatus WHERE "
									+ "classStatus.csId in (select csId from ClassStatus where "
									+ "teacher.userRegistration.school.schoolId = :schoolId)");
			q.setParameter("classStatus", WebKeys.EXPIRED);
			q.setParameter("schoolId", school.getSchoolId());
			q.executeUpdate();

			Query s = getHibernateTemplate()
					.getSessionFactory()
					.openSession()
					.createQuery(
							"UPDATE ClassStatus SET availStatus = :availstatus WHERE teacher.teacherId  in (select teacherId from Teacher where "
									+ "userRegistration.school.schoolId = :schoolId)");
			s.setParameter("availstatus", WebKeys.EXPIRED);
			s.setParameter("schoolId", school.getSchoolId());
			s.executeUpdate();
			Query m = getHibernateTemplate()
					.getSessionFactory()
					.openSession()
					.createQuery(
							"delete from HomeroomClasses where teacher.teacherId in (select teacherId from Teacher where "
									+ "userRegistration.school.schoolId = :schoolId)");

			m.setParameter("schoolId", school.getSchoolId());
			m.executeUpdate();
			return true;
		} catch (HibernateException e) {
			logger.error("Error in makeClassesExpired() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
			return false;

		}
	}

	@Override
	public boolean addSchoolSchedule(SchoolSchedule schoolschedule) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(schoolschedule);
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			logger.error("Error in addSchoolSchedule() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateSchoolSchedule(SchoolSchedule schoolschedule) {
		try {
			super.Update(schoolschedule);
			return true;
		} catch (HibernateException e) {
			logger.error("Error in updateSchoolSchedule() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public SchoolSchedule getSchoolSchedule(School school) {
		List<SchoolSchedule> scsche = Collections.emptyList();
		try {
			scsche = (List<SchoolSchedule>) getHibernateTemplate().find("from SchoolSchedule where school.schoolId = "
					+ school.getSchoolId() + "");
		} catch (Exception e) {
			logger.error("Error in getSchoolSchedule() of AdminSchedulerDAOImpl" + e);
		}
		if (scsche.size() > 0)
			return scsche.get(0);
		else
			return new SchoolSchedule();
	}

	@Override
	public boolean checkSchoolSchedule(School school) {
		boolean check = false;

		try {
			String query = "select * from school_schedule where school_id="
					+ school.getSchoolId() + "";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				check = false;
			}
		} catch (Exception e) {
			logger.error("Error in checkSchoolSchedule() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}

		return check;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<TeacherSubjects> getTeacherSubjects(School school) {
		List<TeacherSubjects> list1 = new ArrayList<TeacherSubjects>();
		try {

			list1 = (List<TeacherSubjects>) getHibernateTemplate().find(
					"from TeacherSubjects where teacher.userRegistration.school.schoolId="
							+ school.getSchoolId());

		} catch (DataAccessException e) {
			logger.error("Error in getTeacherSubjects() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<SchoolDays> getSchoolDays(School school) {

		List<SchoolDays> list1 = new ArrayList<SchoolDays>();
		try {

			list1 = (List<SchoolDays>) getHibernateTemplate().find(
					"from SchoolDays where school.schoolId="
							+ school.getSchoolId() + " order by days.dayId");

		} catch (DataAccessException e) {
			logger.error("Error in getSchoolDays() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public long getClassIdbyClassName(School school, String className) {
		long classId = 0;
		try {
			String query = "select class_id from class where class_name='"
					+ className + "' and school_id=" + school.getSchoolId();
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				classId = rs.getInt(1);
			}

		} catch (HibernateException e) {
			logger.error("Error in getClassIdbyClassName() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return classId;
	}

	@Override
	public boolean checkHomeSectionAvailabilityforTeacher(long sectionId) {
		int count = 0;
		try {
			String query = "select * from homeroom_classes where  section_id="
					+ sectionId + " and teacher_id is not null";

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				count++;
			}
		} catch (Exception e) {
			logger.error("Error in checkHomeSectionAvailabilityforTeacher() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		if (count == 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean checkTeacherAvailabilityforHomeRoom(long teacherId) {
		int count = 0;
		try {
			String query = "select * from homeroom_classes where teacher_id="
					+ teacherId + "";

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				count++;
			}
		} catch (Exception e) {
			logger.error("Error in checkTeacherAvailabilityforHomeRoom() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		if (count == 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Periods getHomeRoomPeriod(School school, long gradeId) {
		List<Periods> list =null;
		try {
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("From Periods where periodName=:periodName and grade.gradeId=:gradeId");
			query.setParameter("periodName", WebKeys.LP_CLASS_HOMEROME);
			query.setParameter("gradeId", gradeId);
			list = query.list();
		}catch (HibernateException e) {
			logger.error("Error in getHomeRoomPeriod() of AdminSchedulerDAOImpl"+ e);
			e.printStackTrace();
		}
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}
		else 			
		return null;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Teacher getTeacher(long teacherId) {
		List<Teacher> scsche = new ArrayList<Teacher>();
		try {
			scsche = (List<Teacher>) getHibernateTemplate().find(
					"from Teacher where teacherId=" + teacherId + "");
		} catch (Exception e) {
			logger.error("Error in getTeacher() of AdminSchedulerDAOImpl" + e);
		}
		if (scsche.size() > 0)
			return scsche.get(0);
		else
			return new Teacher();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean SetHomeroomClassForTeacher(HomeroomClasses homeroomclass) {
		try {
			super.saveOrUpdate(homeroomclass);
			return true;
		} catch (Exception e) {
			logger.error("Error in SetHomeroomClassForTeacher() of AdminSchedulerDAOImpl"
					+ e);
			return false;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public boolean SetHomeroomClassForTeacher(HomeroomClasses homeroomclass,
			List<Student> studentList) {
		try {
			super.saveOrUpdate(homeroomclass);
			for (Student student : studentList) {
				getHibernateTemplate().saveOrUpdate(student);
			}
			return true;
		} catch (Exception e) {
			logger.error("Error in SetHomeroomClassForTeacher() of AdminSchedulerDAOImpl"
					+ e);
			return false;
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Student> getStudentsBySchoolId(long gradeId) {

		List<Student> list1 = new ArrayList<Student>();
		try {

			list1 = (List<Student>) getHibernateTemplate().find(
					"from Student where grade.gradeId=" + gradeId
							+ " and homeroomClasses.homeroomId is null");

		} catch (DataAccessException e) {
			logger.error("Error in getStudentsBySchoolId() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public java.util.ArrayList getStudentCountfromChild(long sectionId) {
		java.util.ArrayList al = null;
		try {
			int boys = 0, girls = 0;
			al = new java.util.ArrayList();
			String query = "select reg_id from Student where  homeroom_id="
					+ sectionId + "";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				if (getStudentGenderByRegId(rs.getLong(1)).equalsIgnoreCase(
						WebKeys.LP_MALE)) {
					boys = boys + 1;
				} else {
					girls = girls + 1;
				}
			}
			al.add(boys);
			al.add(girls);
		} catch (Exception ex) {
			logger.error("Error in getStudentCountfromChild() of AdminSchedulerDAOImpl"
					+ ex);
			ex.printStackTrace();
			ex.getMessage();
		}
		return al;
	}

	@Override
	public String getStudentGenderByRegId(long reg_id) {
		String gender = "";
		try {
			String query = "select gender from student where reg_id=" + reg_id
					+ "";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				gender = rs.getString(1);
			}

		} catch (Exception e) {
			logger.error("Error in getStudentGenderByRegId() of AdminSchedulerDAOImpl"
					+ e);
			e.getMessage();
		}
		return gender;
	}

	@Override
	public void UpdatechildHomeRoom(long gradeId, long regId, long sectionId) {
		try {
			String query = "update student set homeroom_id=? where reg_id=? and grade_id=?";

			jdbcTemplate.update(query, sectionId, regId, gradeId);
		} catch (Exception e) {
			logger.error("Error in UpdatechildHomeRoom() of AdminSchedulerDAOImpl"
					+ e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Periods> getSchoolPeriods(long gradeId) {
		List<Periods> list1 = new ArrayList<Periods>();
		try {

			list1 = (List<Periods>) getHibernateTemplate()
					.find("from Periods where grade.gradeId="
							+ gradeId
							+ " and periodName!='"+WebKeys.LP_CLASS_HOMEROME+"' ORDER BY startTime,endTime asc");

		} catch (DataAccessException e) {
			logger.error("Error in getSchoolPeriods() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public void saveSchoolSchedule(SchoolSchedule schoolSchedule) {
		getHibernateTemplate().saveOrUpdate(schoolSchedule);
	}

	@Override
	public boolean checkSectionAvailabilityforTeacher(long sectionId,
			long teacherId) {
		int count = 0;
		try {
			String query = "select * from class_status where  section_id="+ sectionId + " and avail_status='"+WebKeys.AVAILABLE+"' AND teacher_id is not null";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				count++;
			}
		} catch (Exception e) {
			logger.error("Error in checkSectionAvailabilityforTeacher() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		if (count == 0)
			return true;
		else
			return false;
	}

	@Override
	public long getNoofDaysBySectionId(long sectionId) {
		long noOfDays = 0;
		try {
			String query = "select count(distinct cas.day_id) from class_status cs,class_actual_schedule cas,periods p where cas.cs_id =cs.cs_id and "
					+ "cs.section_id=" 
					+ sectionId
					+ " and p.period_id=cas.period_id and p.period_name!='"+WebKeys.LP_CLASS_HOMEROME+"'" +" and avail_status='"+WebKeys.AVAILABLE+"'";

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				noOfDays = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("Error in getNoofDaysBySectionId() of AdminSchedulerDAOImpl"
					+ e);
		}
		return noOfDays;
	}

	@Override
	public long getNoofSectionsByDay(long dayId, long sectionId) {
		long noOfSections = 0;
		try {
			String query = "select count(*)  from class_status cs,"
					+ "class_actual_schedule cas,periods p  where cas.cs_id =cs.cs_id and cas.day_id="
					+ dayId + " and cs.section_id=" + sectionId
					+ " and p.period_id=cas.period_id and "
					+ "p.period_name!='"+WebKeys.LP_CLASS_HOMEROME+"'" +" and avail_status='"+WebKeys.AVAILABLE+"'";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				noOfSections = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("Error in getNoofSectionsByDay() of AdminSchedulerDAOImpl"
					+ e);
		}
		return noOfSections;
	}

	@Override
	public long getNoofSectionsByTeacherRegId(long dayId, long teacherId) {
		long noOfSections = 0;
		try {
			String query = "select count(*)  from class_status cs,"
					+ "class_actual_schedule cas,periods p  where cas.cs_id =cs.cs_id and cas.day_id="
					+ dayId
					+ " and cs.teacher_id="
					+ teacherId
					+ " and p.period_id=cas.period_id and p.period_name!='"+WebKeys.LP_CLASS_HOMEROME+"'" +" and avail_status='"+WebKeys.AVAILABLE+"'";

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				noOfSections = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("Error in getNoofSectionsByTeacherRegId() of AdminSchedulerDAOImpl"
					+ e);
		}
		return noOfSections;
	}

	@Override
	public boolean checkTeacherAvailability(long teacherId, long day,
			long period) {
		int count = 0, periodId = 0, dayId = 0;
		try {
			String query = "select period_id, day_id from class_actual_schedule"
					+ " where cs_id in(select cs_id from class_status where teacher_id="
					+ teacherId + " and avail_status='"+WebKeys.AVAILABLE+"')";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				periodId = rs.getInt(1);
				dayId = rs.getInt(2);
				if (dayId == day && period == periodId) {
					count++;
					break;
				}
			}
		} catch (Exception e) {
			logger.error("Error in checkTeacherAvailability() of AdminSchedulerDAOImpl"
					+ e);
		}
		if (count == 0)
			return true;
		else
			return false;
	}

	@Override
	public long getGradeLevelId(long sectionId) {
		long sectId = 0;
		try {
			String query = "select grade_level_id from section where section_id="
					+ sectionId + "";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				sectId = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("Error in getGradeLevelId() of AdminSchedulerDAOImpl"
					+ e);
		}
		return sectId;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void UpdateClassStatus(java.util.Date startDate,
			java.util.Date endDate, School school) {
		try {
			Query s = getHibernateTemplate()
					.getSessionFactory()
					.openSession()
					.createQuery(
							"Update ClassStatus set startDate=:startdate,endDate=:enddate where availStatus='"+WebKeys.AVAILABLE+"' and teacher.teacherId in (select teacherId from Teacher where "
									+ "userRegistration.school.schoolId = :schoolId)");
			s.setParameter("startdate", startDate);
			s.setParameter("enddate", endDate);
			s.setParameter("schoolId", school.getSchoolId());
			s.executeUpdate();
		} catch (Exception e) {
			logger.error("Error in UpdateClassStatus() of AdminSchedulerDAOImpl"
					+ e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public ClassStatus getClassStatus(long teacherRegId, long sectionId) {

		List<ClassStatus> list1 = new ArrayList<ClassStatus>();
		ClassStatus classStatus = null;
		try {
			list1 = (List<ClassStatus>) getHibernateTemplate().find(
					"from ClassStatus where section.sectionId=" + sectionId
							+ " and teacher.teacherId=" + teacherRegId
							+ " and availStatus='"+WebKeys.AVAILABLE+"'");

		} catch (DataAccessException e) {
			logger.error("Error in getClassStatus() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		if (!list1.isEmpty()) {
			classStatus = list1.get(0);
		}
		return classStatus;
	}

	public long getcsId(long gradeclassId) {
		long csId = 0;
		try {
			String query = "select cs_id from register_for_class where grade_class_id="
					+ gradeclassId + "";

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				csId = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("Error in getcsId() of AdminSchedulerDAOImpl" + e);
		}
		return csId;
	}

	@SuppressWarnings({ "unchecked" })
	public List<RegisterForClass> getStudentsbyGradeLevelId(long gradeclassId,
			long gradeLevelId) {
		List<RegisterForClass> list1 = new ArrayList<RegisterForClass>();
		try {

			list1 = (List<RegisterForClass>) getHibernateTemplate()
					.find("from RegisterForClass  where gradeClasses.gradeClassId="
							+ gradeclassId
							+ " and gradeLevel.gradeLevelId="
							+ gradeLevelId
							+ " and status='"+WebKeys.LP_STATUS_NEW+"' and classStatus_1='"+WebKeys.ALIVE+"'");

		} catch (DataAccessException e) {
			logger.error("Error in getStudentsbyGradeLevelId() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}

	public boolean checkStudentAvailabilityByTime(long studentId, long periodId,
			long dayId) {
		int count = 0;
		try {
			String query = "select * from register_for_class r,class_actual_schedule cas"
					+ " where  r.cs_id=cas.cs_id and cas.period_id=" + periodId + " and cas.day_id="
					+ dayId + " and r.student_id=" + studentId + " and r.status='"+WebKeys.ACCEPTED+"'"+" and r.class_status='"+WebKeys.ALIVE+"'";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				count++;
			}
		} catch (Exception e) {
			logger.error("Error in checkStudentAvailabilityByTime() of AdminSchedulerDAOImpl" + e);
		}
		if (count == 0)
			return true;
		else
			return false;
	}

	public boolean SaveClassStatus(ClassStatus cs) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(cs);
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			logger.error("Error in SaveClassStatus() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
			return false;
		}
	}

	public long getCsIdBySectionId(long sectionId) {
		long csId = 0;
		try {
			String query = "select cs_id from class_status where section_id="
					+ sectionId + " and avail_status='"+WebKeys.AVAILABLE+"'";

			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				csId = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("Error in getCsIdBySectionId() of AdminSchedulerDAOImpl"
					+ e);
		}
		return csId;
	}

	@SuppressWarnings({ "unchecked" })
	public List<RegisterForClass> getStudentsByTeacherGradelevel(
			long gradeclassId, long gradeLevelId, long teacherId) {
		List<RegisterForClass> list1 = new ArrayList<RegisterForClass>();
		try {

			list1 = (List<RegisterForClass>) getHibernateTemplate().find("from RegisterForClass  where gradeClasses.gradeClassId=" 
					+ gradeclassId + " and gradeLevel.gradeLevelId=" + gradeLevelId + " and status='"+WebKeys.LP_STATUS_NEW+"' and classStatus_1='"+WebKeys.ALIVE+"' "
					+ "and teacher.teacherId=" + teacherId);
		} catch (DataAccessException e) {
			logger.error("Error in getStudentsByTeacherGradelevel() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "unchecked" })
	public List<RegisterForClass> getStudentsByTeacher(long gradeclassId,
			long teacherRegId) {
		List<RegisterForClass> list1 = new ArrayList<RegisterForClass>();
		try {

			list1 = (List<RegisterForClass>) getHibernateTemplate()
					.find("from RegisterForClass  where gradeClasses.gradeClassId="
							+ gradeclassId
							+ " and status='"+WebKeys.LP_STATUS_NEW+"' and classStatus_1='"+WebKeys.ALIVE+"' and teacher.teacherId="
							+ teacherRegId + "");

		} catch (DataAccessException e) {
			logger.error("Error in getStudentsByTeacher() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "unchecked" })
	public List<RegisterForClass> getStudentsbygradeclassId(long gradeclassId) {
		List<RegisterForClass> list1 = new ArrayList<RegisterForClass>();
		try {

			list1 = (List<RegisterForClass>) getHibernateTemplate()
					.find("from RegisterForClass  where gradeClasses.gradeClassId="
							+ gradeclassId
							+ " and status='"+WebKeys.LP_STATUS_NEW+"' and classStatus_1='"+WebKeys.ALIVE+"'");

		} catch (DataAccessException e) {
			logger.error("Error in getStudentsbygradeclassId() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings("rawtypes")
	public void UpdateRegisterForClass(long sectionId, long cs_id,
			long gradeclassId, long studentId) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			Query s = session.createQuery(
					"Update RegisterForClass set status='"+WebKeys.ACCEPTED+"',desktopStatus='"+WebKeys.ALIVE+"',"
							+ "section.sectionId=" + sectionId + ", classStatus.csId=" + cs_id
							+ " where gradeClasses.gradeClassId=" + gradeclassId + " and student.studentId="
							+ studentId + " and status='"+WebKeys.LP_STATUS_NEW+"' and classStatus_1='"+WebKeys.ALIVE+"'");

			s.executeUpdate();
			tx.commit();
			session.close();			
		} catch (Exception e) {
			logger.error("Error in UpdateRegisterForClass() of AdminSchedulerDAOImpl"
					+ e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public List<RegisterForClass> getStudentsByCsId(long cs_id) {
		List<RegisterForClass> list1 = new ArrayList<RegisterForClass>();
		try {

			list1 = (List<RegisterForClass>) getHibernateTemplate().find(
					"from RegisterForClass  where classStatus.csId=" + cs_id+" and classStatus_1='"+WebKeys.ALIVE+"' and status='"+WebKeys.ACCEPTED+"'");

		} catch (DataAccessException e) {
			logger.error("Error in getStudentsByCsId() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public ClassStatus getclassStatus(long csId) {
		List<ClassStatus> scsche = Collections.emptyList();
		try {
			scsche = (List<ClassStatus>) getHibernateTemplate().find("from ClassStatus where csId=" + csId + "");
		} catch (Exception e) {
			logger.error("Error in getclassStatus() of AdminSchedulerDAOImpl" + e);
		}
		if (scsche.size() > 0)
			return scsche.get(0);
		else
			return new ClassStatus();
	}

	public boolean SaveClassActualSchedule(ClassActualSchedule clasactschedule) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(clasactschedule.getClassStatus());
			session.saveOrUpdate(clasactschedule);			
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			logger.error("Error in SaveClassActualSchedule() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public HomeroomClasses getHomeRoomByTeacher(long teacherId, long periodId) {
		List<HomeroomClasses> homeroomLt = new ArrayList<HomeroomClasses>();	
		HomeroomClasses  homeroom = new HomeroomClasses();
		try {
			homeroomLt = (List<HomeroomClasses>) getHibernateTemplate().find("from HomeroomClasses where teacher.teacherId="+teacherId+" and periods.periodId="+periodId);			
			if(homeroomLt.size()>0){
				homeroom = (HomeroomClasses) homeroomLt.get(0);
			}
		} catch (HibernateException e) {
			e.getMessage();
			logger.error("Error in getHomeRoomByTeacher() of AdminSchedulerDAOImpl"
					+ e);
		}
		return homeroom;
	}

	@Override
	public String getSectionName(long sectionId) {
		String sectionName = "";
		try {
			String query = "select section from section where section_id="
					+ sectionId;
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				sectionName = rs.getString(1);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error in getSectionName() of AdminSchedulerDAOImpl"
					+ ex);
		}
		return sectionName;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ClassActualSchedule> getClassSchedule(long regId, long periodId) {
		List<ClassActualSchedule> casLt = new ArrayList<ClassActualSchedule>();
		try {

			casLt = (List<ClassActualSchedule>) getHibernateTemplate().find(
					"from  ClassActualSchedule where classStatus.teacher.userRegistration.regId ="
							+ regId + " and periods.periodId=" + periodId
							+ " and classStatus.availStatus='"+WebKeys.AVAILABLE+"'");

		} catch (DataAccessException e) {
			logger.error("Error in getClassSchedule() of AdminSchedulerDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return casLt;
	}

	@Override
	public String getGradethvalue(long gradeId) {

		if (gradeId == 0) {
			return "";
		} else if (gradeId == 1) {
			return "1st";
		} else if (gradeId == 2) {
			return "2nd";
		} else if (gradeId == 3) {
			return "3rd";
		} else {
			return gradeId + "th";
		}
	}

	@Override
	public List<Long> getDayIdsByCsId(long csId, long periodId) {
		List<Long> days = new ArrayList<Long>();
		try {
			String query = "select day_id from class_actual_schedule where cs_id="
					+ csId + " and period_id=" + periodId+" order by day_id";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				days.add(rs.getLong(1));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error in getDayIdsByCsId() of AdminSchedulerDAOImpl"
					+ ex);
		}
		return days;

	}
	@Override
	public long getGradeLevelIdByCsId(long csId) {
		long gradeLevelId = 0;
		try {
			String query = "select grade_level_id from section s, class_status cs where s.section_id=cs.section_id and cs.cs_id="
					+ csId + " ";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				gradeLevelId = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("Error in getGradeLevelId() of AdminSchedulerDAOImpl"
					+ e);
		}
		return gradeLevelId;
	}

}
