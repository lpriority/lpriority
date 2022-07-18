package com.lp.common.dao;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.Activity;
import com.lp.model.AssignActivity;
import com.lp.model.AssignLessons;
import com.lp.model.Assignment;
import com.lp.model.Lesson;
import com.lp.model.LessonPaths;
import com.lp.model.SchoolSchedule;
import com.lp.model.StudentClass;
import com.lp.model.Unit;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.CommonDAO;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("Curriculum")
public class CurriculumDAOImpl extends CustomHibernateDaoSupport implements
		CurriculumDAO {

	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	HttpSession session;
	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@Override
	public boolean createUnit(Unit unit) throws DataException {
		// TODO Auto-generated method stub
		try {
			long unit_id = 0;
			long adminRegId = 0;
			if(!unit.getUserRegistration().getUser().getUserType().equals(WebKeys.USER_TYPE_ADMIN)){
				adminRegId = commonDAO.getAdminBySchool(unit.getUserRegistration().getSchool().getSchoolId()).getRegId();	
			}			
			String query_for_unit_id = "select unit_id from unit where grade_class_id= "+unit.getGradeClasses().getGradeClassId()+" AND unit_name = '"+unit.getUnitName()+"' "
					+ "AND created_by in ("+unit.getUserRegistration().getRegId()+", "+adminRegId+")";
			SqlRowSet rs_for_unit_id = jdbcTemplate.queryForRowSet(query_for_unit_id);
			
			if (rs_for_unit_id.next()) {
				 unit_id = rs_for_unit_id.getLong(1);
			}
			
			if(unit_id > 0){ 
				return false;
		    }else{
		    	Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
					session.saveOrUpdate(unit);
				tx.commit();
				session.close();
				return true;
		    }
		} catch (HibernateException e) {
			logger.error("Error in createUnit() of CurriculumDAOImpl" + e);
			e.printStackTrace();
			throw new DataException(
					"Error in createUnit() of CurriculumDAOImpl", e);
		}
	}
	@Override
	public boolean updateUnit(Unit unit) throws DataException {
		// TODO Auto-generated method stub
		try {
			long unit_id = 0;
			String query_for_unit_id = "select unit_id from unit where unit_id= "+unit.getUnitId();
			SqlRowSet rs_for_unit_id = jdbcTemplate.queryForRowSet(query_for_unit_id);			
			if (rs_for_unit_id.next()) {
				 unit_id = rs_for_unit_id.getLong(1);
			}			
			long existUnit_id = 0;
			String query= "select unit_id from unit where grade_class_id= "+unit.getGradeClasses().getGradeClassId()+" AND unit_name = '"+unit.getUnitName()+"'";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				existUnit_id = rs.getLong(1);
			}			
			if(existUnit_id > 0){ 
				return false;
		    }			
			else if(unit_id > 0){ 
				String update_query = "update unit set unit_name = '"+unit.getUnitName()+"' where unit_id= "+unit.getUnitId();
				jdbcTemplate.update(update_query);
				return true;
		    }else{
				return false;
		    }
		} catch (HibernateException e) {
			logger.error("Error in updateUnit() of CurriculumDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in updateUnit() of CurriculumDAOImpl", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getUnitsbygradeClassId(long gradeClassId)
			throws DataException {
		List<Unit> units = new ArrayList<Unit>();
		try {
			units = (List<Unit>) getHibernateTemplate()
					.find("from Unit where gradeClasses.gradeClassId="+ gradeClassId);
		} catch (DataAccessException e) {
			logger.error("Error in getUnitsbygradeClassId() of CurriculumDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException(
					"Error in getUnitsbygradeClassId() of CurriculumDAOImpl", e);
		}
		return units;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lesson> getLessonssbyUnitId(long unitId) throws DataException {
		List<Lesson> lessons = new ArrayList<Lesson>();
		try {
			lessons = (List<Lesson>) getHibernateTemplate().find(
					"from Lesson where unit.unitId=" + unitId
							+ " order by lessonId");
		} catch (DataAccessException e) {
			logger.error("Error in getLessonssbyUnitId() of CurriculumDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException(
					"Error in getLessonssbyUnitId() of CurriculumDAOImpl", e);
		}
		return lessons;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Unit getUnitbyUnitId(long unitId) throws DataException {
		List<Unit> units = new ArrayList<Unit>();
		try {
			units = (List<Unit>) getHibernateTemplate().find(
					"from Unit where unitId=" + unitId);
		} catch (DataAccessException e) {
			logger.error("Error in getUnitbyUnitId() of CurriculumDAOImpl" + e);
			e.printStackTrace();
			throw new DataException(
					"Error in getUnitbyUnitId() of CurriculumDAOImpl", e);
		}
		if (units.size() > 0)
			return units.get(0);
		else {
			logger.error("Unit doesn't exist getUnitbyUnitId() of CurriculumDAOImpl");
			throw new DataException(
					"Unit doesn't exist getUnitbyUnitId() of CurriculumDAOImpl");
		}
	}

	@Override
	public boolean createLesson(Lesson lesson) throws DataException {
		try {
			long lesson_id = 0;
			String query_for_lesson_id = "select lesson_id from lesson where unit_id= "+lesson.getUnit().getUnitId()+" AND lesson_name = '"+lesson.getLessonName()+"'";
			SqlRowSet rs_for_lesson_id = jdbcTemplate.queryForRowSet(query_for_lesson_id);
			
			if (rs_for_lesson_id.next()) {
				lesson_id = rs_for_lesson_id.getLong(1);
			}
			if(lesson_id > 0){ 
				return false;
		    }else{
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
					session.saveOrUpdate(lesson);
				tx.commit();
				session.close();
				for (int i = 0; i < lesson.getActivityList().size(); i++) {
					lesson.getActivityList().get(i).setLesson(lesson);
					saveActivity(lesson.getActivityList().get(i));
				}
		    }
		} catch (HibernateException e) {
			logger.error("Error in createLesson() of CurriculumDAOImpl" + e);
			e.printStackTrace();
			throw new DataException(
					"Error in createLesson() of CurriculumDAOImpl", e);
		}
		return true;
	}
	
	@Override
	public boolean updateLesson(Lesson lesson) throws DataException {
		try {
			long lesson_id = 0;
			String query_for_lesson_id = "select lesson_id from lesson where lesson_id= "+lesson.getLessonId();
			SqlRowSet rs_for_lesson_id = jdbcTemplate.queryForRowSet(query_for_lesson_id);
			
			if (rs_for_lesson_id.next()) {
				lesson_id = rs_for_lesson_id.getLong(1);
			}
			if(lesson_id > 0){ 
				  String update_query = "update lesson set lesson_name = '"+lesson.getLessonName()+"', lesson_desc = '"+lesson.getLessonDesc()+"' where lesson_id="+lesson.getLessonId();
				  jdbcTemplate.update(update_query);
				return true;
		    }else{
		    	return false;
		    }
		} catch (HibernateException e) {
			logger.error("Error in updateLesson() of CurriculumDAOImpl" + e);
			e.printStackTrace();
			throw new DataException(
					"Error in updateLesson() of CurriculumDAOImpl", e);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Lesson> getLessonsbygradeClassId(long gradeClassId)
			throws DataException {
		List<Lesson> lessons = new ArrayList<Lesson>();
		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.LP_USER_TYPE_TEACHER)){
				UserRegistration adminReg = commonDAO.getAdminByTeacher(userReg);
				lessons = (List<Lesson>) getHibernateTemplate().find("from Lesson where unit.gradeClasses.gradeClassId="+ gradeClassId
						+" and userRegistration.regId="+adminReg.getRegId()+" or userRegistration.regId="+userReg.getRegId());
			}else{
				lessons = (List<Lesson>) getHibernateTemplate().find("from Lesson where unit.gradeClasses.gradeClassId="+ gradeClassId
						+" and userRegistration.regId="+userReg.getRegId());
			}			
		} catch (DataAccessException e) {
			logger.error("Error in getLessonsbygradeClassId() of CurriculumDAOImpl"+ e);
			throw new DataException("Error in getLessonsbygradeClassId() of CurriculumDAOImpl",	e);
		}
		return lessons;
	}

	@Override
	public Lesson getLessonObj(long lessonId) {
		Lesson lesson = new Lesson();
		lesson = (Lesson) super.find(Lesson.class, lessonId);
		List<Activity> activityLt = getActivityByLesson(lessonId);
		lesson.setActivityList(activityLt);
		return lesson;
	}

	@Override
	public boolean saveActivity(Activity activity) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(activity);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveActivity() of CurriculumDAOImpl" + e);
			throw new DataException("Error in saveActivity() of CurriculumDAOImpl", e);
		}
		return true;		
	}
	
	@Override
	public Activity updateActivity(Activity activity) throws DataException {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(activity);
			tx.commit();
			session.close();
			} catch (HibernateException e) {
				logger.error("Error in updateActivity() of AssessmentDAOImpl" + e);
				e.printStackTrace();
				throw new DataException("Error in updateActivity() of AssessmentDAOImpl",e);
			}
			return activity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignLessons> getAssignedLesson(long csId) {
		List<AssignLessons> assignedLessons = null;
		try {
			assignedLessons = (List<AssignLessons>) getHibernateTemplate()
					.find("FROM AssignLessons where classStatus.csId = " + csId
							+ " order by lesson.unit.unitNo, lesson.lessonNo");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return assignedLessons;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignActivity> getAssignedActivities(long csId) {
		List<AssignActivity> assignedActivities = null;
		try {
			assignedActivities = (List<AssignActivity>) getHibernateTemplate()
					.find("FROM AssignActivity where classStatus.csId=" + csId);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return assignedActivities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getAssignedAssignments(long csId) {
		List<Assignment> assignedAssignments = null;
		try {
			assignedAssignments = (List<Assignment>) getHibernateTemplate()
					.find("FROM Assignment WHERE classStatus.csId=" + csId);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return assignedAssignments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LessonPaths> getLessonPaths(long regId) {
		List<LessonPaths> lessonPaths = null;
		try {
			lessonPaths = (List<LessonPaths>) getHibernateTemplate().find(
					"FROM LessonPaths where uploadedBy=" + regId);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return lessonPaths;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lesson> getLessonssbyUnitIdUserId(long unitId) throws DataException {
		List<Lesson> lessons = new ArrayList<Lesson>();
		try {
			lessons = (List<Lesson>) getHibernateTemplate().find(
					"from Lesson where unit.unitId=" + unitId + " order by lessonId");
			for (int i = 0; i < lessons.size(); i++) {
				List<Activity> activityLt = getActivityByLesson(lessons.get(i).getLessonId());
				lessons.get(i).setActivityList(activityLt);
			}

		} catch (DataAccessException e) {
			logger.error("Error in getLessonssbyUnitIdUserId() of CurriculumDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException("Error in getLessonssbyUnitIdUserId() of CurriculumDAOImpl", e);
		}
		return lessons;
	}

	@Override
	public boolean removeUnit(long unitId) throws DataException {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			Unit unit = getUnitbyUnitId(unitId);
			session.delete(unit);					
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in removeUnit() of CurriculumDAOImpl" + e);
			throw new DataException(WebKeys.LP_UNABLE_REMOVE_UNIT, e);
		}
		return true;
	}
	
	@Override
	public boolean removeLesson(long lessonId)	throws DataException {
		boolean status = false;
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			if(lessonId > 0){
				removeLessonActivities(lessonId);
				Lesson lesson = getLessonObj(lessonId);
				session.delete(lesson);
				tx.commit();
				session.close();
				status = true;
			}	
		} catch (HibernateException e) {
			status = false;
			logger.error("Error in removeLesson() of CurriculumDAOImpl" + e);
		}
		return status;
	}

	public boolean removeLessonActivities(long lessonId)	throws DataException {
		boolean status = false;
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			if(lessonId > 0){
				List<Activity> activityLt = getActivityByLesson(lessonId);
				for (Activity activity : activityLt) {
					session.delete(activity);
					status = true;
				}
			}	
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in removeLesson() of CurriculumDAOImpl" + e);
			status = true;
		}
		return status;
	}
	
	@Override
	public boolean removeActivity(long activityId){
		try {
		 jdbcTemplate.update("delete from activity where activity_id="+activityId);
		 return true;
		} catch (HibernateException e) {
			logger.error("Error in removeActivity() of CurriculumDAOImpl" + e);
			 return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getActivityByLesson(long lessonId) throws DataException {
		List<Activity> activity = new ArrayList<Activity>();
		try {
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			activity = (List<Activity>) getHibernateTemplate().find("from Activity where lesson.lessonId=" + lessonId 
					+ "  and userRegistration.regId="+userReg.getRegId());
		} catch (DataAccessException e) {
			logger.error("Error in getActivityByLesson() of CurriculumDAOImpl"
					+ e);
			e.printStackTrace();
		//	throw new DataException("Error in getActivityByLesson() of CurriculumDAOImpl", e);
		}
		if(activity.size() > 0){
			return activity;
		}else{
			return new ArrayList<Activity>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lesson> getTeacherLessonssbyUnitId(long unitId,
			long teacherRegId, long adminRegId) throws DataException {
		List<Lesson> lessons = new ArrayList<Lesson>();
		try {
			lessons = (List<Lesson>) getHibernateTemplate().find(
					"from Lesson where unit.unitId=" + unitId+ " and (userRegistration.regId = "+ teacherRegId+" or "
							+ "userRegistration.regId = "+ adminRegId+") order by lessonId");
		} catch (DataAccessException e) {
			logger.error("Error in getTeacherLessonssbyUnitId() of CurriculumDAOImpl"+ e);
			throw new DataException(
					"Error in getTeacherLessonssbyUnitId() of CurriculumDAOImpl", e);
		}
		return lessons;
	}

	@Override
	public boolean saveActivities(List<Activity> activities) throws DataException {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for (Activity activity : activities) {
				 if(activity.getActivityId() > 0){
				  String update_query = "update activity set activity_desc='"+activity.getActivityDesc()+"' where activity_id="+activity.getActivityId()+" and lesson_id="+activity.getLesson().getLessonId();
				  jdbcTemplate.update(update_query);
				 }else{
					 session.saveOrUpdate(activity);
				 }
			}
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveActivities() of CurriculumDAOImpl" + e);
			e.printStackTrace();
			throw new DataException(
					"Error in saveActivities() of CurriculumDAOImpl", e);
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkLessonExists(long lessonId){
		boolean status = false;
		try{
			List<Assignment> as = Collections.emptyList();
			as = (List<Assignment>) getHibernateTemplate().find("from Assignment where lesson.lessonId ="+lessonId);
			List<AssignLessons> asl = Collections.emptyList();
			asl = (List<AssignLessons>) getHibernateTemplate().find("from AssignLessons where lesson.lessonId ="+lessonId);
			if(as.size()>0 || asl.size()>0){
				status = true;
			}
		}catch (HibernateException e) {
			logger.error("Error in checkLessonExists() of CurriculumDAOImpl" + e);
			throw new DataException("Error in checkLessonExists() of CurriculumDAOImpl",e);
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkUnitExists(long unitId){
		boolean status = false;
		try{
			List<Lesson> ass=Collections.emptyList();
			ass = (List<Lesson>) getHibernateTemplate().find("from Lesson where unit.unitId ="+unitId);
			for (Lesson lesson : ass) {
			status=checkLessonExists(lesson.getLessonId());
			}
		}catch (HibernateException e) {
			logger.error("Error in checkLessonExists() of CurriculumDAOImpl" + e);
			throw new DataException("Error in checkLessonExists() of CurriculumDAOImpl",e);
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getTeacherUnits(long gradeClassId, long createdBy)
			throws DataException {
		List<Unit> units = new ArrayList<Unit>();
		try {
			units = (List<Unit>) getHibernateTemplate()
					.find("from Unit where gradeClasses.gradeClassId="+ gradeClassId+" and userRegistration.regId= "+createdBy);
		} catch (DataAccessException e) {
			logger.error("Error in getUnitsbygradeClassId() of CurriculumDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException(
					"Error in getUnitsbygradeClassId() of CurriculumDAOImpl", e);
		}
		return units;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Lesson> getUnAssignedLessonssbyUnitId(long unitId, long regId, long gradeId, long classId, SchoolSchedule schedule) throws DataException {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		try {
			lessons = (List<Lesson>) getHibernateTemplate().find(
					"from Lesson where unit.unitId=" + unitId
							+ " and lessonId not in (Select lesson.lessonId from AssignLessons where classStatus.teacher.userRegistration.regId="+regId+" and"
							+ " classStatus.section.gradeClasses.grade.gradeId="+gradeId+" and classStatus.section.gradeClasses.studentClass.classId="+classId+" "
						    + "and assignedDate between '"+schedule.getStartDate()+"' and '"+schedule.getEndDate()+"') order by lessonId ");
		} catch (DataAccessException e) {
			logger.error("Error in getLessonssbyUnitId() of CurriculumDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException(
					"Error in getLessonssbyUnitId() of CurriculumDAOImpl", e);
		}
		return lessons;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SchoolSchedule getSchoolSchedule(long schoolId) throws DataException{
		SchoolSchedule schedule = new SchoolSchedule();
		List<SchoolSchedule> schoolSchedules = null;
		
		try {
			schoolSchedules = (List<SchoolSchedule>) getHibernateTemplate().find(
					"from SchoolSchedule where school.schoolId= "+schoolId);
		} catch (DataAccessException e) {
			logger.error("Error in getSchoolSchedule() of CurriculumDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException(
					"Error in getSchoolSchedule() of CurriculumDAOImpl", e);
		}
		schedule = schoolSchedules.get(0);
		return schedule;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<StudentClass> getTeacherSubjects(long gradeId,long teacherId) throws DataException, SQLDataException
	{
	  List<StudentClass> teacherClassesLt = new ArrayList<StudentClass>();
		Query query = null;
		try {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
				query = session.createQuery(
						"Select s.studentClass FROM TeacherSubjects s WHERE s.grade.gradeId="
						+ gradeId+" and s.teacher.teacherId="+teacherId+"");
						teacherClassesLt = query.list();
			}catch(Exception e){
				e.printStackTrace();
			}
		return teacherClassesLt;
	}
	
	

}
