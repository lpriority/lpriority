package com.lp.admin.dao;

import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
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

import com.lp.common.service.CommonService;
import com.lp.custom.exception.DataException;
import com.lp.mail.service.MailServiceImpl;
import com.lp.model.AcademicYear;
import com.lp.model.ClassStatus;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.GradeLevel;
import com.lp.model.Legend;
import com.lp.model.LegendCriteria;
import com.lp.model.Periods;
import com.lp.model.RegisterForClass;
import com.lp.model.School;
import com.lp.model.SchoolDays;
import com.lp.model.Section;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("adminDao")
public class AdminDAOImpl extends CustomHibernateDaoSupport implements AdminDAO {

	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CommonService commonService;	
	@Autowired
	private MailServiceImpl mailservice;
	@Autowired
	private GradesDAO gradesDao;
	@Autowired
	private CommonService reportService;
	@Autowired
	private HttpSession httpSession;
	
	static final Logger logger = Logger.getLogger(AdminDAOImpl.class);

	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@Override
	public void CreateClasses(StudentClass stuclass) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(stuclass);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in CreateClasses() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
	}

	@Override
	public void setClassStrength(School school) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(school);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in CreateClasses() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
	}

	@Override
	public int checkClassExists(StudentClass stuclass) {
		String query = "select class_id from class where school_id="
				+ stuclass.getSchool().getSchoolId() + " and class_name='"
				+ stuclass.getClassName() + "'";
		int check = 0;
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		while (rs.next()) {
			check = rs.getInt(1);
		}
		return check;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<School> getClassStrengthDetails(School school) {
		List<School> classStrengthlist = new ArrayList<School>();
		try {
			classStrengthlist = (List<School>) getHibernateTemplate().find(
					"from School where school_id=" + school.getSchoolId());
		} catch (DataAccessException e) {
			logger.error("Error in getClassStrengthDetails() of AdminDAOImpl"
					+ e);
			e.printStackTrace();
		}
		return classStrengthlist;
	}

	@Override
	public int checkGradeClasses(long gradeId, long classId, long schoolId) {
		String query = "select class_id from grade_classes where class_id="
				+ classId + " and grade_id=" + gradeId;
		int check = 0;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {

			check = 1;

		}
		return check;
	}

	@Override
	public int AddClasses(long gradeId, long classId, String status, String operation) {
		try {
			GregorianCalendar cal = new GregorianCalendar();
			long millis = cal.getTimeInMillis();
			Timestamp changeDate = new Timestamp(millis);
			
			if(operation == WebKeys.LP_TAB_CREATE){
				String query = "insert into grade_classes(grade_id,class_id,status,create_date,change_date) values(?,?,?,now(),?)";
				jdbcTemplate.update(query, gradeId, classId, status, changeDate);
			}else{
				String query = "update grade_classes set status=? where grade_id=? and class_id=?";
				@SuppressWarnings("unused")
				int out = jdbcTemplate.update(query, status, gradeId, classId );
			}
			
			return 1;
		} catch (Exception ex) {
			return 0;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<StudentClass> getStudentClasses(long gradeId) {

		List<StudentClass> list1 = new ArrayList<StudentClass>();
		try {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("select gc.studentClass from GradeClasses gc where gc.grade.gradeId="
							+ gradeId + " and gc.status='"+WebKeys.ACTIVE+"' and gc.studentClass.className != '"+WebKeys.LP_CLASS_HOMEROME+"'");
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list1= query.list();

		} catch (DataAccessException e) {
			logger.error("Error in getStudentClasses() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<StudentClass> getAddedClasses(long gradeId) {

		List<StudentClass> list1 = new ArrayList<StudentClass>();
		try {

			list1 = (List<StudentClass>) getHibernateTemplate()
					.find("from StudentClass where classId in("
							+ "select sc.classId from GradeClasses as gc inner join gc.studentClass as sc where  gc.grade.gradeId="
							+ gradeId + " and gc.status='"+WebKeys.ACTIVE+"')");

		} catch (DataAccessException e) {
			logger.error("Error in getStudentClasses() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GradeClasses> getGradeClasses(long gradeId) {
		List<GradeClasses> classes = (List<GradeClasses>) getHibernateTemplate()
				.find("from GradeClasses where grade.gradeId=" + gradeId);
		return classes;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<GradeLevel> getStudentGradeLevels(long classId) {
		List<GradeLevel> list1 = new ArrayList<GradeLevel>();
		try {

			list1 = (List<GradeLevel>) getHibernateTemplate().find(
					"from GradeLevel");
		} catch (DataAccessException e) {
			logger.error("Error in getStudentGradeLevels() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public long getGradeClassId(long gradeId, long classId) {
		String query = "select grade_class_id from grade_classes where class_id="
				+ classId + " and grade_id=" + gradeId;
		int gradeclassId = 0;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {

			gradeclassId = rs.getInt(1);

		}
		return gradeclassId;
	}

	@Override
	public boolean CreateSections(long gradeclassId, String sectionName,
			String status, long gradelevelId) {
		try {
			String sql = "insert into section(grade_class_id,grade_level_id,section,status)"
					+ " values(?,?,?,?)";
			jdbcTemplate.update(sql, gradeclassId, gradelevelId, sectionName,
					status);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int checkExistSection(long gradeclassId, long gradelevelId,
			String sectionName) {
		String query = "select * from section where grade_class_id="
				+ gradeclassId + " and section='" + sectionName + "'";
		int check = 0;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {

			check = 1;

		}
		return check;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Section> getAllSections(long gradeId, long classId) {

		long gradeclassId = getGradeClassId(gradeId, classId);
		List<Section> list1 = new ArrayList<Section>();
		try {

			list1 = (List<Section>) getHibernateTemplate().find(
					"from Section where gradeClasses.gradeClassId="
							+ gradeclassId+ " and status='"+WebKeys.ACTIVE+"'");

		} catch (DataAccessException e) {
			logger.error("Error in getAllSections() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Section> getSectionsByGradeLevel(long gradeId, long classId,
			long gradeLevelId) {
		List<Section> list1 = new ArrayList<Section>();
		try {

			list1 = (List<Section>) getHibernateTemplate().find(
					"from Section where gradeClasses.grade.gradeId=" + gradeId
							+ " and gradeClasses.studentClass.classId="
							+ classId + " and gradeLevel.gradeLevelId="
							+ gradeLevelId+" and status='"+WebKeys.ACTIVE+"'");

		} catch (DataAccessException e) {
			logger.error("Error in getAllSections() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public List<Section> getAllSectionsByHomeRoom(long gradeId, long schoolId) {
		String query = "select class_id from class where class_name='"+WebKeys.LP_CLASS_HOMEROME+"' and school_id="
				+ schoolId;
		long classId = 0;
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			classId = rs.getLong(1);
		}
		long gradeclassId = getGradeClassId(gradeId, classId);
		List<Section> sectionsList = new ArrayList<Section>();
		try {
			String query1 = "SELECT section.section_id,section.section, homeroom_classes.teacher_id,homeroom_classes.homeroom_id "
					+ "FROM section left join homeroom_classes on section.section_id=homeroom_classes.section_id where grade_class_id = ?";
			
			SqlRowSet rs1 = jdbcTemplate.queryForRowSet(query1,gradeclassId);
			while(rs1.next()){
				Section sec = new Section();
				sec.setSectionId(Long.parseLong(rs1.getString(1)));
				sec.setSection(rs1.getString(2));
				if(rs1.getString(3) != null){
					sec.setTeacherId(Long.parseLong(rs1.getString(3)));
				}
				if(rs1.getString(4) != null){
					sec.setHomeroomId(Long.parseLong(rs1.getString(4)));
				}
				sectionsList.add(sec);
			}
			/*sectionsList = (List<Section>) getHibernateTemplate().find(
					"from Section where gradeClasses.gradeClassId="
							+ gradeclassId);*/

		} catch (DataAccessException e) {
			logger.error("Error in getAllSections() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return sectionsList;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<UserRegistration> getAllParentEmailIds(School school,
			long userTypeId) {

		List<UserRegistration> list1 = new ArrayList<UserRegistration>();
		try {

			list1 = (List<UserRegistration>) getHibernateTemplate().find(
					"from UserRegistration where firstName is null and school.schoolId="
							+ school.getSchoolId() + " and user.userTypeid="
							+ userTypeId);

		} catch (DataAccessException e) {
			logger.error("Error in getAllParentEmailIds() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<UserRegistration> getAllUserRegistrationsBySchool(School school,long userTypeId) {

		List<UserRegistration> list1 = new ArrayList<UserRegistration>();
		try {

			list1 = (List<UserRegistration>) getHibernateTemplate().find(
					"from UserRegistration where school.schoolId="
							+ school.getSchoolId() + " and user.userTypeid="+userTypeId);

		} catch (DataAccessException e) {
			logger.error("Error in getAllParentEmailIds() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<UserRegistration> getAllStudentEmailIds(School school,
			long userTypeId, long usertypeid) {

		List<UserRegistration> list1 = new ArrayList<UserRegistration>();
		try {

			list1 = (List<UserRegistration>) getHibernateTemplate().find(
					"from UserRegistration where status='"+WebKeys.LP_STATUS_NEW+"' and school.schoolId="
							+ school.getSchoolId()
							+ " and user.userTypeid in (" + userTypeId + ","
							+ usertypeid + ")");

		} catch (DataAccessException e) {
			logger.error("Error in getAllParentEmailIds() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public long checkSecurityExists(UserRegistration userreg) {
		String query = "select * from security where reg_id="
				+ userreg.getRegId() + "";
		int check = 0;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {

			check = 1;

		}
		return check;
	}

	@Override
	public int UpdateSections(long sectionId, String sectionName) {
		String query = "update section set section=? where section_id=?";

		int out = jdbcTemplate.update(query, sectionName, sectionId);
		if (out != 0) {
			return 1;
		} else
			return 0;
	}

	@Override
	public int DeleteSections(long sectionId, String sectionName) {
		
		String query1 = "delete from section where section_id=?";
		int out = jdbcTemplate.update(query1, sectionId);
		if (out != 0) 
			return 1;
		 else
			return 0;
		
	}

	@Override
	public int checkSections(long sectionId) {
		int stat=0,stat1=0,stat2=0;
		String query="select * from homeroom_classes where section_id=?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query,sectionId);
		if(rs.next()){
			stat=1;
		}
		String query1="select * from class_status where section_id=?";
		SqlRowSet rs1 = jdbcTemplate.queryForRowSet(query1,sectionId);
		if(rs1.next()){
			stat1=1;
		}
		String query2="select * from register_for_class where section_id=?";
		SqlRowSet rs2 = jdbcTemplate.queryForRowSet(query2,sectionId);
		if(rs2.next()){
			stat2=1;
		}
		if(stat==1 || stat1==1 || stat2==1)
	    return 1;
		else
	    return 0;
	    
	
	}
	@Override
	public String getClassName(long classId) {

		String query = "select class_name from class where class_id=" + classId
				+ "";
		String classname = "";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {

			classname = rs.getString(1);

		}
		return classname;
	}

	@Override
	public GradeClasses getGradeClass(long grade_class_id) {
		GradeClasses sq = (GradeClasses) super.find(GradeClasses.class,
				grade_class_id);
		return sq;
	}

	@Override
	public boolean saveRegisterForclass(RegisterForClass regisclass) {
		try {
			super.saveOrUpdate(regisclass);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SchoolDays> getSchoolDays(School school) {

		List<SchoolDays> scdaysList = new ArrayList<SchoolDays>();
		try {
			scdaysList = (List<SchoolDays>) getHibernateTemplate().find(
					"from SchoolDays where status='"+WebKeys.ACTIVE+"' and school_id="
							+ school.getSchoolId());
		} catch (DataAccessException e) {
			logger.error("Error in getSchoolDays() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return scdaysList;

	}

	@Override
	public int checkDayExists(SchoolDays schooldays) {
		String query = "select school_days_id from school_days where school_id="
				+ schooldays.getSchool().getSchoolId()
				+ " and day_id="
				+ schooldays.getDays().getDayId() + "";
		int check = 0;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			check = 1;

		}
		return check;
	}

	@Override
	public void UpdateDays(SchoolDays schooldays) {
		String sql = "UPDATE school_days set status = ?,change_date=? where day_id = ? and school_id=?";
		jdbcTemplate.update(sql, new Object[] { schooldays.getStatus(),
				schooldays.getChangeDate(), schooldays.getDays().getDayId(),
				schooldays.getSchool().getSchoolId() });
	}

	@Override
	public void saveSchoolDays(SchoolDays schooldays) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(schooldays);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveGrades() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String SavePeriod(Periods period) {
		String status = "";
		boolean isOverlapped = false;
		List<Periods> list = new ArrayList<Periods>();
		try {	
			try {
				list = (List<Periods>) getHibernateTemplate().find(
						"from Periods where grade.gradeId =" + period.getGrade().getGradeId()+" and periodId !="+period.getPeriodId()+" ORDER BY startTime,endTime asc");
				for (Periods periods : list) {
					if(periods.getPeriodName().equalsIgnoreCase(period.getPeriodName())){
						isOverlapped =  true;
						status = "PeriodName";
					}else if(!isOverlapped){
						isOverlapped = commonService.isTimeBetweenTwoTime(periods.getStartTime(),periods.getEndTime(),period.getStartTime(),period.getEndTime());
						if(isOverlapped)
							status = "PeriodDates";
					} 
					if(isOverlapped && status.length() > 0){
						return status;
					}
				}
			} catch (DataAccessException e) {
				logger.error("Error in SavePeriod() of AdminDAOImpl" + e);
				e.printStackTrace();
			} catch (ParseException pe) {
				logger.error("Error in SavePeriod() of AdminDAOImpl" + pe);
				pe.printStackTrace();
			}
		     if(!isOverlapped){
		    		Session session = sessionFactory.openSession();
					Transaction tx = (Transaction) session.beginTransaction();
					session.saveOrUpdate(period);
					tx.commit();
					session.close();
					status = "Success";

			}
		} catch (DataAccessException e) {
			logger.error("Error in SavePeriod() of AdminDAOImpl" + e);
			e.printStackTrace();
			status = "Error";
		}
		return status;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Periods> getGradePeriods(long gradeId) {

		List<Periods> list1 = new ArrayList<Periods>();
		try {

			list1 = (List<Periods>) getHibernateTemplate().find(
					"from Periods where grade.gradeId=" + gradeId
							+ "ORDER BY startTime,endTime asc");
		} catch (DataAccessException e) {
			logger.error("Error in getGradePeriods() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return list1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String UpdatePeriods(long periodId, String periodname,
			String starttime, String endtime, long gradeId) {
		String status = "";
		boolean isOverlapped = false;
		List<Periods> list = new ArrayList<Periods>();
		try {	
			try {
				list = (List<Periods>) getHibernateTemplate().find(
						"from Periods where grade.gradeId =" + gradeId+" and periodId !="+periodId+" ORDER BY startTime,endTime asc");
				for (Periods periods : list) {
					if(periods.getPeriodName().equalsIgnoreCase(periodname)){
						isOverlapped =  true;
						status = "PeriodName";
					}else if(!isOverlapped){
						isOverlapped = commonService.isTimeBetweenTwoTime(periods.getStartTime(), periods.getEndTime(), starttime, endtime);
						if(isOverlapped)
							status = "PeriodDates";
					} 
					if(isOverlapped && status.length() > 0){
						return status;
					}
				}
			} catch (DataAccessException e) {
				logger.error("Error in UpdatePeriods() of AdminDAOImpl" + e);
				e.printStackTrace();
			} catch (ParseException pe) {
				logger.error("Error in UpdatePeriods() of AdminDAOImpl" + pe);
				pe.printStackTrace();
			}
		     if(!isOverlapped){
				String query1 = "update periods set period_name=?,start_time=?,end_time=? where period_id=? and grade_id=?";
				int out = jdbcTemplate.update(query1, periodname, starttime, endtime, periodId, gradeId);
				if (out != 0){
					status = "Success";

				}
			}
		} catch (DataAccessException e) {
			logger.error("Error in UpdatePeriods() of AdminDAOImpl" + e);
			e.printStackTrace();
			status = "Error";
		}
		return status;
	}

	@Override
	public int checkPeriod(long periodId) {
		String query = "select period_id from class_actual_schedule where period_id="
				+ periodId + "";
		int check = 0;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			check = 1;

		}
		return check;
	}

	@Override
	public int deletePeriod(long periodId) {
		String query = "delete from periods where period_id=?";

		int out = jdbcTemplate.update(query, periodId);
		if (out != 0) {
			return 1;
		} else
			return 0;
	}

	@Override
	public int deleteClassSchedulePeriod(long periodId) {
		String query = "delete from class_actual_schedule where period_id=?";

		int out = jdbcTemplate.update(query, periodId);
		if (out != 0) {
			return 1;
		} else
			return 0;
	}

	@Override
	public int RemoveClass(long gradeId, long classId) {
	   long gradeclassId = reportService.getGradeClassId(gradeId, classId);
	   int status;
	   try{
			jdbcTemplate.update("delete from section where grade_class_id="+gradeclassId);
			jdbcTemplate.update("delete from grade_classes where grade_class_id="+gradeclassId);
			status = 1;
		}catch(Exception e){
			status = 0;
		}
		return status;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Periods getPeriod(long periodId) {
		List<Periods> scsche = (List<Periods>) getHibernateTemplate().find(
				"from Periods where periodId=" + periodId + "");
		if (scsche.size() > 0)
			return scsche.get(0);
		else
			return new Periods();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Section getSection(long sectionId) {
		List<Section> scsche = (List<Section>) getHibernateTemplate().find(
				"from Section where sectionId=" + sectionId + "");
		if (scsche.size() > 0)
			return scsche.get(0);
		else
			return new Section();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<TeacherSubjects> getSchoolTeachers(School school) {

		List<TeacherSubjects> teachers = new ArrayList<TeacherSubjects>();
		try {
			teachers = (List<TeacherSubjects>) getHibernateTemplate()
					.find("from TeacherSubjects where teacherId in(select teacherId from teacher where userRegistration.schoolId="
							+ school.getSchoolId() + ")");
		} catch (DataAccessException e) {
			logger.error("Error in getSchoolTeachers() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return teachers;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Periods> getSchoolPeriods(School school) {
		List<Periods> teachers = new ArrayList<Periods>();
		try {
			teachers = (List<Periods>) getHibernateTemplate().find(
					"from Periods where gradeId in(select gradeId from Grade where schoolId="
							+ school.getSchoolId()
							+ ") order by gradeId,startTime,endTime");
		} catch (DataAccessException e) {
			logger.error("Error in getSchoolPeriods() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return teachers;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentClass> getAllClasses() {
		List<StudentClass> allClassList = new ArrayList<StudentClass>();
		try {
			allClassList = (List<StudentClass>) super
					.findAll(StudentClass.class);
		} catch (DataAccessException e) {
			logger.error("Error in getAllClasses() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return allClassList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GradeClasses> getAllGradeClasses() {
		List<GradeClasses> allClassList = new ArrayList<GradeClasses>();
		try {
			allClassList = (List<GradeClasses>) super
					.findAll(GradeClasses.class);
		} catch (DataAccessException e) {
			logger.error("Error in getAllGradeClasses() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
		return allClassList;
	}

	@Override
	public GradeClasses saveGradeClasses(GradeClasses gradeClasses) {

		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(gradeClasses);
			tx.commit();
			session.close();
			return gradeClasses;
		} catch (HibernateException e) {
			logger.error("Error in saveGrades() of GradesDAOImpl" + e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getSectionIdByGradeClassIdAndGradeLevelId(long gradeClassId, long gradeLevelId) {
		long sectionId = 0;
		String query_for_sectionId = "select section_id from section where grade_class_id="+gradeClassId+" and grade_level_id="+gradeLevelId+" and status='"+WebKeys.ACTIVE+"'";
		SqlRowSet rs_for_sectionId = jdbcTemplate.queryForRowSet(query_for_sectionId);
		 if (rs_for_sectionId.next()){
			 sectionId = rs_for_sectionId.getLong(1);
		 }
		return sectionId;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassStatus> getScheduledClassesByGradeNClass(long gradeId, long classId) throws SQLDataException{
		List<ClassStatus> cList = Collections.emptyList();
		AcademicYear academicYear = null;
		String availStatus = WebKeys.AVAILABLE;
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try{
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				availStatus =  WebKeys.EXPIRED;
			}
			cList = (List<ClassStatus>) getHibernateTemplate().find("From ClassStatus where section.gradeClasses.grade.gradeId="+gradeId + " "
					+ "and section.gradeClasses.studentClass.classId="+classId+ " and availStatus='"+availStatus+"'");
		}
		catch(Exception e){
			logger.error("error while getting the classes");
		}
		return cList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<StudentClass> getGradeClassesByGradeId(long gradeId) throws SQLDataException{
		List<StudentClass> studentClassLt = new ArrayList<StudentClass>();
		Query query = null;
		try {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
				query = session.createQuery(
						"Select cs.section.gradeClasses.studentClass FROM ClassStatus cs WHERE "
						+ "cs.section.gradeClasses.grade.gradeId="
						+ gradeId+" and cs.availStatus='"+WebKeys.AVAILABLE+"'");
						query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						studentClassLt = query.list();
			}catch(Exception e){
				e.printStackTrace();
			}
		return studentClassLt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentClass> getClassesWithOutHomeRoom(long gradeId)
			throws DataException {
		List<StudentClass> list1 = new ArrayList<StudentClass>();
		try {
			list1 = (List<StudentClass>) getHibernateTemplate()
					.find("from StudentClass where classId in("
							+ "select sc.classId from GradeClasses as gc inner join gc.studentClass as sc where  gc.grade.gradeId="
							+ gradeId + " and gc.status='"+WebKeys.ACTIVE+"') and className != '"+WebKeys.LP_CLASS_HOMEROME+"'");

		} catch (DataAccessException e) {
			logger.error("Error in getClassesWithOutHomeRoom() of AdminDAOImpl" + e);
		}
		return list1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GradeClasses> getGradeClassesByGrade(long gradeId) throws DataException {
		List<GradeClasses> classes = (List<GradeClasses>) getHibernateTemplate()
				.find("from GradeClasses where grade.gradeId=" + gradeId + " and studentClass.className != '"+WebKeys.LP_CLASS_HOMEROME+"'");
		return classes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getGradeStudentList(long gradeId) throws DataException {
		List<Student> list1 = new ArrayList<Student>();
		try {
			list1 = (List<Student>) getHibernateTemplate()
					.find("from Student where grade.gradeId="+gradeId+ " and gradeStatus='"+WebKeys.ACTIVE+"'");

		} catch (DataAccessException e) {
			logger.error("Error in getGradeStudentList() of AdminDAOImpl" + e);
		}
		return list1;
	}

	@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	@Override
	public boolean promoteStudents(long gradeId, long promoteGradeId, String[] studentIds) throws DataException {
		try {
			List<Long> stdIds = new ArrayList<Long>();
			List<Long> stdAbove13 = new ArrayList<Long>();
			List<Long> stdRegIds = new ArrayList<Long>();
			List<Long> parentRegIds = new ArrayList<Long>();
			String gradeName = "";
			for(int i=0;i<studentIds.length;i++){
				stdIds.add(Long.parseLong(studentIds[i]));
			}	
			
			List<Student> students = new ArrayList<Student>();			
			Query queryForStudent=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from Student where "
					+ "studentId in (:studentIds)");
			queryForStudent.setParameterList("studentIds", stdIds);
			students = queryForStudent.list();			
			
			for(Student id : students){	
				Calendar cDob = Calendar.getInstance();
				cDob.setTime(id.getDob());
				Calendar cNow = Calendar.getInstance();
				cNow.setTime(new Date());
				
				int age = cNow.get(Calendar.YEAR) - cDob.get(Calendar.YEAR);
				
				if (cNow.get(Calendar.MONTH) < cDob.get(Calendar.MONTH)) {
		            age = age - 1;
		        }
		        if (cNow.get(Calendar.MONTH) == cDob.get(Calendar.MONTH) && cNow.get(Calendar.DATE) < cDob.get(Calendar.DATE)) {
		            age = age - 1;
		        }
		        //Updating user table
				if(age > 13){
					stdAbove13.add(id.getUserRegistration().getRegId());
				}else{
					parentRegIds.add(id.getUserRegistration().getParentRegId());
				}
				stdRegIds.add(id.getUserRegistration().getRegId());
			}
			
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			
			//Updating Student table
			String hql1 = "UPDATE Student set gradeStatus = :gradeStatus, gradeId = :gradeId"
					+ " WHERE studentId IN (:studentId)";
			Query query1 = session.createQuery(hql1);			
			if(promoteGradeId > 0){
				query1.setParameter("gradeStatus", "promoted");
				query1.setParameter("gradeId", promoteGradeId);
			}else{
				query1.setParameter("gradeId", gradeId);
				query1.setParameter("gradeStatus", WebKeys.LP_STATUS_INACTIVE);
			}
			query1.setParameterList("studentId", stdIds);
			query1.executeUpdate();
			
			//Updating register_for_class table
			String hql2 = "UPDATE RegisterForClass set classStatus_1 = :classStatus_1"
					+ " WHERE student.studentId IN (:studentIds)";
			Query query2 = session.createQuery(hql2);			
			
			query2.setParameter("classStatus_1", WebKeys.EXPIRED);
			query2.setParameterList("studentIds", stdIds);
			query2.executeUpdate();
			
			//Updating userType in user_registration table
			if(stdAbove13.size() > 0 && promoteGradeId > 0){
				String hql3 = "UPDATE UserRegistration set user.userTypeid = :userTypeid"
						+ " WHERE regId IN (:regIds)";
				Query query3 = session.createQuery(hql3);
				
				query3.setParameter("userTypeid", (long)5);
				query3.setParameterList("regIds", stdAbove13);
				query3.executeUpdate();
			}
			if(promoteGradeId > 0){
				Grade grd = gradesDao.getGrade(promoteGradeId);
				gradeName = grd.getMasterGrades().getGradeName();
			}
			//Deactivate passed away student in user_registration table
			if(promoteGradeId == 0){
				String hql4 = "UPDATE UserRegistration set status = :status"
						+ " WHERE regId IN (:regIds)";
				Query query4 = session.createQuery(hql4);
				
				query4.setParameter("status", WebKeys.LP_STATUS_INACTIVE);
				query4.setParameterList("regIds", stdRegIds);
				query4.executeUpdate();
			}else{
				List<UserRegistration> parents = new ArrayList<UserRegistration>();	
				HashMap<Long, String> hmap = new HashMap<Long, String>();
				if(parentRegIds.size()>0){							
					Query queryForParent=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from UserRegistration where "
							+ "regId in (:parentRegIds)");
					queryForParent.setParameterList("parentRegIds", parentRegIds);
					parents = queryForParent.list();	
					for(UserRegistration ur : parents){
						hmap.put(ur.getRegId(), ur.getEmailId());
					}
				}
				String from = "admin@learningpriority.com";				
				for(Student stdObj : students){
					if(hmap.containsKey(stdObj.getUserRegistration().getParentRegId())){
						mailservice.promoteMail(hmap.get(stdObj.getUserRegistration().getParentRegId()), from, gradeName, 4, 
								stdObj.getUserRegistration().getFirstName()+ " "+stdObj.getUserRegistration().getLastName());
					}else if(hmap.containsKey(stdObj.getUserRegistration().getParentRegId2())){
						mailservice.promoteMail(hmap.get(stdObj.getUserRegistration().getParentRegId2()), from, gradeName, 4, 
								stdObj.getUserRegistration().getFirstName()+ " "+stdObj.getUserRegistration().getLastName());
					}
					else{
						
						mailservice.promoteMail(stdObj.getUserRegistration().getEmailId(), from, gradeName, 5, "");
					}
				}
			}
			
			tx.commit();
			session.close();
			
		} catch (HibernateException e) {
			logger.error("Error in promoteStudents() of AdminDAOImpl" + e);
			throw new DataException("Error in promoteStudents() of AdminDAOImpl",e);
		}
		return true;
	}
	@Override
	public boolean setParentToStudent(long parentRegId,String semailId) {
		int out=0;
		try{
			
			String query = "update user_registration set parent_reg_id2=? where email_id=?";
    		 out = jdbcTemplate.update(query, parentRegId,semailId);
			
		}catch(Exception e){
			logger.error("Error in setParentToStudent() of AdminDAOImpl" + e);
			e.printStackTrace();
			
		}
		if (out != 0) {
			return true;
		} else
			return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LegendCriteria> getAllLegendCriteriaList(){
		List<LegendCriteria> list1 = new ArrayList<LegendCriteria>();
		try {
			list1 = (List<LegendCriteria>) getHibernateTemplate()
					.find("from LegendCriteria");

		} catch (DataAccessException e) {
			logger.error("Error in getAllLegendCriteria() of AdminDAOImpl" + e);
		}
		return list1;
	}
	
	@Override
	public void createLIRubric(Legend legend) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(legend);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in createLIRubric() of AdminDAOImpl" + e);
			e.printStackTrace();
		}
	}
}
