package com.lp.teacher.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.common.dao.CurriculumDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.Activity;
import com.lp.model.AssignActivity;
import com.lp.model.AssignK1Tests;
import com.lp.model.AssignLessons;
import com.lp.model.Assignment;
import com.lp.model.BenchmarkResults;
import com.lp.model.ClassStatus;
import com.lp.model.CreateUnits;
import com.lp.model.FluencyMarks;
import com.lp.model.Grade;
import com.lp.model.GradeClasses;
import com.lp.model.IOLReport;
import com.lp.model.LearningIndicator;
import com.lp.model.LearningIndicatorValues;
import com.lp.model.LegendCriteria;
import com.lp.model.Lesson;
import com.lp.model.RegisterForClass;
import com.lp.model.SchoolSchedule;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentClass;
import com.lp.model.StudentCompositeActivityScore;
import com.lp.model.StudentCompositeLessonScore;
import com.lp.model.Teacher;
import com.lp.model.Unit;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("assignLessonDAO")
public class CommonDAOImpl extends CustomHibernateDaoSupport implements
		CommonDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CurriculumDAO curriculumDao;	
	@Autowired
	private HttpSession httpSession;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Grade> getTeacherAssignedGrades(Teacher teacher) {
		List<Grade> grades = null;
		Query query = null;
		AcademicYear academicYear = null;
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				query = session.createQuery(
						"SELECT cs.section.gradeClasses.grade FROM ClassStatus cs WHERE cs.teacher.teacherId="
						+ teacher.getTeacherId()
						+ " and cs.availStatus='"+WebKeys.EXPIRED+"' "
						+ " and (cs.startDate >='"+academicYear.getStartDate() +"' and cs.endDate <='"+ academicYear.getEndDate()+"')" 
						+ " order by cs.section.gradeClasses.grade.masterGrades.masterGradesId");
			}else{
				query = session.createQuery(
						"SELECT cs.section.gradeClasses.grade FROM ClassStatus cs WHERE cs.teacher.teacherId="
						+ teacher.getTeacherId()
						+ " and cs.availStatus='"+WebKeys.AVAILABLE+"' "
						+ "order by cs.section.gradeClasses.grade.masterGrades.masterGradesId");
			}
			
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			grades = query.list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return grades;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Teacher getTeacher(UserRegistration userReg) {
		Teacher teacher = null;
		try {
			List<Teacher> teacherList = (List<Teacher>) getHibernateTemplate()
					.find("FROM Teacher where userRegistration.regId= "
							+ userReg.getRegId());
			if (!teacherList.isEmpty()) {
				teacher = teacherList.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return teacher;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<StudentClass> getTeacherClasses(Teacher teacher, long gradeId) {
		List<StudentClass> classes = null;
		AcademicYear academicYear = null;
		Query query = null;
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				query = session.createQuery(
						"Select cs.section.gradeClasses.studentClass FROM ClassStatus cs WHERE cs.teacher.teacherId="
						+ teacher.getTeacherId()
						+ " and (cs.startDate >='"+academicYear.getStartDate() +"' and cs.endDate <='"+ academicYear.getEndDate()+"')"
						+ " and cs.section.gradeClasses.grade.gradeId="
						+ gradeId+" and cs.availStatus='"+WebKeys.EXPIRED+"'");
			}else{
				query = session.createQuery(
						"Select cs.section.gradeClasses.studentClass FROM ClassStatus cs WHERE cs.teacher.teacherId="
						+ teacher.getTeacherId()
						+ " and cs.section.gradeClasses.grade.gradeId="
						+ gradeId+" and cs.availStatus='"+WebKeys.AVAILABLE+"'");
			}
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			classes = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return classes;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> getUnits(long gradeId, long classId, Teacher teacher,
			UserRegistration adminReg) {
		List<Unit> units = null;
		try {
			units = (List<Unit>) getHibernateTemplate().find(
					"FROM Unit where gradeClasses.grade.gradeId=" + gradeId
							+ "" + " and gradeClasses.studentClass.classId="
							+ classId + " and userRegistration.regId in ("
							+ teacher.getUserRegistration().getRegId() + ", "
							+ adminReg.getRegId() + ") order by unitId");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return units;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getActivitiesByLesson(long lessonId) {
		List<Activity> activities = null;
		try {
			activities = (List<Activity>) getHibernateTemplate().find(
					"FROM Activity where lesson.lessonId=" + lessonId);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return activities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassStatus> getTeacherSections(Teacher teacher, long gradeId,
			long classId) {
		List<ClassStatus> classStatus = null;
		AcademicYear academicYear = null;
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try {
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				if(teacher != null){
					classStatus = (List<ClassStatus>) getHibernateTemplate().find(
							"FROM ClassStatus WHERE teacher.teacherId="
									+ teacher.getTeacherId()
									+ " and section.gradeClasses.grade.gradeId="
									+ gradeId
									+ " and (startDate >='"+academicYear.getStartDate() +"' and endDate <='"+ academicYear.getEndDate()+"')" 
									+ " and section.gradeClasses.studentClass.classId="
									+ classId+" and availStatus='"+WebKeys.EXPIRED+"'");
				}else{
					classStatus = (List<ClassStatus>) getHibernateTemplate().find(
							"FROM ClassStatus WHERE section.gradeClasses.grade.gradeId="
									+ gradeId
									+ " and (startDate >='"+academicYear.getStartDate() +"' and endDate <='"+ academicYear.getEndDate()+"')"
									+ " and section.gradeClasses.studentClass.classId="
									+ classId+" and availStatus='"+WebKeys.EXPIRED+"'");
				}
			}else{
				if(teacher != null){
					classStatus = (List<ClassStatus>) getHibernateTemplate().find(
							"FROM ClassStatus WHERE teacher.teacherId="
									+ teacher.getTeacherId()
									+ " and section.gradeClasses.grade.gradeId="
									+ gradeId
									+ " and section.gradeClasses.studentClass.classId="
									+ classId+" and availStatus='"+WebKeys.AVAILABLE+"'");
				}else{
					classStatus = (List<ClassStatus>) getHibernateTemplate().find(
							"FROM ClassStatus WHERE section.gradeClasses.grade.gradeId="
									+ gradeId
									+ " and section.gradeClasses.studentClass.classId="
									+ classId+" and availStatus='"+WebKeys.AVAILABLE+"'");
				}
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return classStatus;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserRegistration getAdminByTeacher(UserRegistration teacherReg) {
		List<UserRegistration> list = (List<UserRegistration>) getHibernateTemplate()
				.find("from UserRegistration where school.schoolId="
						+ teacherReg.getSchool().getSchoolId()
						+ " and user.userTypeid= " + 2);
		return list.get(0);
	}

	@Override
	public ClassStatus getClassStatus(long csId) {
		@SuppressWarnings("unchecked")
		List<ClassStatus> list = (List<ClassStatus>) getHibernateTemplate()
				.find("FROM ClassStatus WHERE csId=" + csId);
		return list.get(0);

	}

	public boolean assignCurriculum(List<AssignLessons> assignLessons,
			List<AssignActivity> assignActivities) throws DataException {
		try {
			int count = 0;
			for (AssignLessons assignLesson : assignLessons) {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(assignLesson);
				for(StudentCompositeLessonScore studLessonScore : assignLesson.getStudentCompositeLessonScores()){
					studLessonScore.getId().setAssignLessonId(assignLesson.getAssignId());
					session.saveOrUpdate(studLessonScore);
					count++;
				}
				if(count == assignLesson.getStudentCompositeLessonScores().size())
					tx.commit();
				else
					tx.rollback();
				count = 0;
				session.close();
			}
			for (AssignActivity assignActivity : assignActivities) {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(assignActivity);
				for(StudentCompositeActivityScore studActivityScore : assignActivity.getStudentCompositeActivityScores()){
					studActivityScore.getId().setAssignActivityId(assignActivity.getAssignActivityId());
					session.saveOrUpdate(studActivityScore);
					count++;
				}
				if(count == assignActivity.getStudentCompositeActivityScores().size())
					tx.commit();
				else
					tx.rollback();
				count = 0;
				session.close();
			}
			
		} catch (Exception e) {
			logger.error("Error in assignCurriculum() of CurriculumDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in assignCurriculum() of CurriculumDAOImpl", e);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CreateUnits getTeacherCurriculum(long gradeId, long classId,
			Teacher teacher, UserRegistration adminReg, SchoolSchedule schedule) {
		CreateUnits curriculum = new CreateUnits();
		List<Unit> units = null;
		try {
			String sql = "FROM Unit where gradeClasses.grade.gradeId=" + gradeId
					+ "" + " and gradeClasses.studentClass.classId="
					+ classId + " and userRegistration.regId in (";
			if(teacher!=null)
			sql+= teacher.getUserRegistration().getRegId() + ", ";			
			sql+= adminReg.getRegId() + ") order by unitId";
			
			units = (List<Unit>) getHibernateTemplate().find(
					sql);
			for (int unitNo = 0; unitNo < units.size(); unitNo++) {
				List<Lesson> lessons = curriculumDao.getUnAssignedLessonssbyUnitId(units
						.get(unitNo).getUnitId(), teacher.getUserRegistration().getRegId(), gradeId, classId, schedule);
				units.get(unitNo).setLessonsList(lessons);
				for (int lessonNo = 0; lessonNo < lessons.size(); lessonNo++) {
					List<Activity> activities = getActivitiesByLesson(lessons
							.get(lessonNo).getLessonId());
					lessons.get(lessonNo).setActivityList(activities);
				}
			}
			curriculum.setUnits(units);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return curriculum;
	}

	@Override
	public long getTeacherSubjectId(long gradeId, long classId, long teacherId) {
		long teacherSubjectId = 0;
		String query = "select teacher_subject_id from teacher_subjects where grade_id="+gradeId+" and class_id = "+classId+" and teacher_id = "+teacherId;
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			teacherSubjectId = rs.getLong(1);
		}
		return teacherSubjectId;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Student getStudent(UserRegistration userReg) {
		Student student = null;
		try {
			List<Student> studentList = (List<Student>) getHibernateTemplate()
					.find("FROM Student where userRegistration.regId= "
							+ userReg.getRegId());
			if (!studentList.isEmpty()) {
				student = studentList.get(0);
			}
		} catch (HibernateException e) {
			logger.error("Error in getStudent() of CommonDAOImpl"
					+ e);
		}
		return student;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Teacher getTeacherByCsId(long csId) {
		Teacher teacher = null;
		List<ClassStatus> classStatusList = null;
		try{
			classStatusList = (List<ClassStatus>) getHibernateTemplate().find("from ClassStatus where csId="+csId);
		}
		catch(Exception e){
			logger.error("Error in getTeacherByCsId() of CommonDAOImpl"
					+ e);
		}
		if(classStatusList!=null){
			teacher =  classStatusList.get(0).getTeacher();
		}
		return teacher;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassStatus> getAdminSections(long gradeId,long classId)  throws DataException {
		List<ClassStatus> classStatus = null;
		try {
			classStatus = (List<ClassStatus>) getHibernateTemplate().find(
					"FROM ClassStatus WHERE"
							+ " section.gradeClasses.grade.gradeId="
							+ gradeId
							+ " and section.gradeClasses.studentClass.classId="
							+ classId+" and availStatus='"+WebKeys.AVAILABLE+"'");
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return classStatus;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GradeClasses getTeacherGradeClassess(long gradeId, long classId){
		 GradeClasses gradeClasses = null;
		 List<GradeClasses> gradeClassesLt = null;
		 try{
			 gradeClassesLt =(List<GradeClasses>) getHibernateTemplate().find("from GradeClasses where"
				+" grade.gradeId="+gradeId	
				+" and studentClass.classId="+classId	
				+" and status='"+WebKeys.ACTIVE+"'");
		  }catch(Exception e){
			  logger.error("Error in getTeacherGradeClassess() of CommonDAOImpl "+ e);
		  }
		  if(gradeClassesLt.size() > 0){
			 gradeClasses =  gradeClassesLt.get(0);
		  }
		return gradeClasses;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BenchmarkResults> getBenchmarkResults(long schoolId, List<Long> teacherList ,List<Long> bencmarkTypes){
		List<BenchmarkResults> benchmarkResults = new ArrayList<BenchmarkResults>();
		try{
			Query queryForParent=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from BenchmarkResults where "
					+ " studentAssignmentStatus.assignment.classStatus.teacher.teacherId in  (:teacherIds)"
					+"and studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId in (:benchmarkIds) "
					+ "and studentAssignmentStatus.assignment.classStatus.availStatus='"+WebKeys.AVAILABLE+"'");
			queryForParent.setParameterList("teacherIds", teacherList);
			queryForParent.setParameterList("benchmarkIds", bencmarkTypes);
			benchmarkResults = queryForParent.list();	
			
		}
		catch(Exception e){
			logger.error("Error in getBenchmarkResults() of CommonDAOImpl "+ e);
		}
		return benchmarkResults;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getStudentRTIGroup(long csId, long studentId){
		List<RegisterForClass> registerForClasses = null;
		try{
			registerForClasses = (List<RegisterForClass>) getHibernateTemplate().find("from RegisterForClass where classStatus.csId="+csId+" and student.studentId="+studentId);
		}
		catch(Exception e){
			logger.error("Error in getStudentRTIGroup() of CommonDAOImpl "+ e);
		}

		if(!registerForClasses.isEmpty()){
			if(registerForClasses.get(0).getRtiGroups()!=null){
				return registerForClasses.get(0).getRtiGroups().getRtiGroupName();
			}
			else{
				return new String("");
			}
		}
		else{
			return new String("");
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignK1Tests> getEarlyLitracyResults(){
		List<AssignK1Tests> assignK1Tests = new ArrayList<AssignK1Tests>();
		try{
			assignK1Tests = (List<AssignK1Tests>) getHibernateTemplate().find("from AssignK1Tests where studentAssignmentStatus.gradedStatus='graded' and"
					+ " studentAssignmentStatus.assignment.assignmentType.assignmentType='"+WebKeys.ASSIGNMENT_TYPE_EARLY_LITERACY_WORD+"'"
					+ " and studentAssignmentStatus.assignment.classStatus.availStatus='"+WebKeys.AVAILABLE+"'"
							+ " order by studentAssignmentStatus.studentAssignmentId");
		}
		catch(Exception e){
			logger.error("Error in getBenchmarkResults() of CommonDAOImpl "+ e);
		}
		return assignK1Tests;
	}
	@SuppressWarnings("unchecked")
	@Override
	public UserRegistration getAdminBySchool(long schoolId){
		UserRegistration userReg = null;
		
		try{
			List<UserRegistration> userRegistrations = (List<UserRegistration>) getHibernateTemplate().find("from UserRegistration where "
					+ "school.schoolId="+schoolId+" and user.userType='"+WebKeys.USER_TYPE_ADMIN+"'");
			if(!userRegistrations.isEmpty()){
				userReg = userRegistrations.get(0);
			}
		}
		catch(Exception e){
			logger.error("Error in getAdminBySchool() of CommonDAOImpl "+ e);
		}
		return userReg;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<FluencyMarks> getFluencyMarks(long csId){
		List<FluencyMarks> fluencyMarks = new ArrayList<FluencyMarks>();
		try{
			fluencyMarks = (List<FluencyMarks>) getHibernateTemplate().find("from FluencyMarks where "
				+ "assignmentQuestions.studentAssignmentStatus.assignment.classStatus.csId in "
				+ "(506,508,511,513,520,521,528,529,530,531,532,533,536,542) and gradingTypes.gradingTypesId=1 "
				+ " and assignmentQuestions.studentAssignmentStatus.gradedStatus='graded'");
		}
		catch(Exception e){
			logger.error("Error in getFluencyMarks() of CommonDAOImpl "+ e);
		}
		return fluencyMarks;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Assignment getAssignmentByAssignmentId(long assignmentId){
		Assignment assignment = null;
		try{
			List<Assignment> assignmentLt = (List<Assignment>) getHibernateTemplate().find("from Assignment where assignmentId="+assignmentId);
			if(!assignmentLt.isEmpty()){
				assignment = assignmentLt.get(0);
			}
		}
		catch(Exception e){
			logger.error("Error in getAssignmentByAssignmentId() of CommonDAOImpl "+ e);
		}
		return assignment;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void migrateIOLData(){
		try{
			
			LearningIndicator learningIndicator = null;
			List<IOLReport> reports = (List<IOLReport>) getHibernateTemplate().find("From IOLReport");
			List<LegendCriteria> criterias  = (List<LegendCriteria>) getHibernateTemplate().find("From LegendCriteria");
			
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();			
			
			for(IOLReport report : reports){
				for(LegendCriteria criteria : criterias){
					learningIndicator = new LearningIndicator();
					learningIndicator.setIolReport(report);
					learningIndicator.setCreateDate(report.getReportDate());
					learningIndicator.setReportDate(report.getReportDate());
					learningIndicator.setLegendCriteria(criteria);
					session.saveOrUpdate(learningIndicator);
					Query query3 = session.createQuery("From LearningIndicatorValues where learningIndicator.learningIndicatorId= "+report.getIolReportId()
							+" and legendSubCriteria.legendCriteria.legendCriteriaId="+criteria.getLegendCriteriaId()
							);
					List<LearningIndicatorValues> indicatorValues = query3.list();
					for(LearningIndicatorValues indicatorValue: indicatorValues){
						indicatorValue.setLearningIndicator(learningIndicator);
						session.saveOrUpdate(indicatorValue);
					}
				}
			}
			tx.commit();
			session.close();
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("Error in migrateIOLData() of CommonDAOImpl "+ e);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<StudentAssignmentStatus> getComprehensionResults(long schoolId, List<Long> teacherList, AcademicYear academicYear){
		List<StudentAssignmentStatus> comprehensionResults = new ArrayList<StudentAssignmentStatus>();
		Query queryForParent = null;
		try{
			if (academicYear.getIsCurrentYear().equalsIgnoreCase("YES")) {
				 queryForParent =  getHibernateTemplate().getSessionFactory().openSession().createQuery("from StudentAssignmentStatus where "
						+ "assignment.classStatus.teacher.teacherId in  (:teacherIds)  and assignment.assignmentType.assignmentTypeId=19 and gradedStatus='graded' "
						+ " and assignment.classStatus.availStatus='"+WebKeys.AVAILABLE+"' and assignment.assignStatus='"+WebKeys.ACTIVE+"'");
			} else {
				 queryForParent =  getHibernateTemplate().getSessionFactory().openSession().createQuery("from StudentAssignmentStatus where "
						+ "assignment.classStatus.teacher.teacherId in  (:teacherIds)  and assignment.assignmentType.assignmentTypeId=19 and gradedStatus='graded' "
						+ " and assignment.dateAssigned between :startDate and :endDate "
						+ " and assignment.classStatus.availStatus='"+WebKeys.EXPIRED+"' and assignment.assignStatus='"+WebKeys.EXPIRED+"'");
				 queryForParent.setParameter("startDate", academicYear.getStartDate());
				 queryForParent.setParameter("endDate", academicYear.getEndDate());
			}
			queryForParent.setParameterList("teacherIds", teacherList);
			comprehensionResults = queryForParent.list();	
			
		}
		catch(Exception e){
			logger.error("Error in getBenchmarkResults() of CommonDAOImpl "+ e);
		}
		return comprehensionResults;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AcademicYear> getSchoolAcademicYears(){
		List<AcademicYear> academicYears = null;
		try{			
			academicYears = (List<AcademicYear>) getHibernateTemplate().find("FROM AcademicYear order by isCurrentYear DESC");
		}
		catch(Exception e){
			logger.error("Exception in getSchoolAcademicYears in CommonService"+e);
		}
		return academicYears;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AcademicYear getAcademicYearById(long academicYearId){
		List<AcademicYear> academicYears = null;
		try{			
			academicYears = (List<AcademicYear>) getHibernateTemplate().find("FROM AcademicYear where academicYearId ="+academicYearId);
		}
		catch(Exception e){
			logger.error("Exception in getSchoolAcademicYears in CommonService"+e);
		}
		if(!academicYears.isEmpty())
			return academicYears.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AcademicYear getCurrentAcademicYear(){
		List<AcademicYear> academicYears = null;
		try{			
			academicYears = (List<AcademicYear>) getHibernateTemplate().find("FROM AcademicYear where isCurrentYear ='"+WebKeys.CURRENT_ACADEMIC_YES+"'");
		}catch(Exception e){
			logger.error("Exception in getCurrentAcademicYear in CommonService"+e);
		}
		if(!academicYears.isEmpty())
			return academicYears.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Student getStudentByRegId(long regId) {
		List<Student> studentLt = new ArrayList<Student>();
		try{			
			studentLt = (List<Student>) getHibernateTemplate().find("FROM Student where userRegistration.regId="+regId);
		}
		catch(Exception e){
			logger.error("Exception in CommonDAOImpl getStudentByRegId"+e);
		}
		if(studentLt.isEmpty())
			return new Student();
		else	
			return studentLt.get(0);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BenchmarkResults> getBenchmarkResults(long schoolId, List<Long> teacherList ,List<Long> bencmarkTypes,AcademicYear academicYr){
		List<BenchmarkResults> benchmarkResults = new ArrayList<BenchmarkResults>();
		try{
			Query queryForParent = null;
			if(academicYr.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)) {
				queryForParent=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from BenchmarkResults where "
						+ " studentAssignmentStatus.assignment.classStatus.teacher.teacherId in  (:teacherIds)"
						+"and studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId in (:benchmarkIds) and"
						+ " studentAssignmentStatus.assignment.assignStatus='"+WebKeys.ACTIVE+"' and studentAssignmentStatus.assignment.dateAssigned between '"+academicYr.getStartDate()+"'"
						+ " and '"+academicYr.getEndDate()+"' and studentAssignmentStatus.submitdate is not null");
			} else {
				queryForParent=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from BenchmarkResults where "
					+ " studentAssignmentStatus.assignment.classStatus.teacher.teacherId in  (:teacherIds)"
					+"and studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId in (:benchmarkIds) and"
					+ " studentAssignmentStatus.assignment.assignStatus='"+WebKeys.EXPIRED+"' and studentAssignmentStatus.assignment.dateAssigned between '"+academicYr.getStartDate()+"'"
					+ " and '"+academicYr.getEndDate()+"'  and studentAssignmentStatus.submitdate is not null");
			}
			queryForParent.setParameterList("teacherIds", teacherList);
			queryForParent.setParameterList("benchmarkIds", bencmarkTypes);
			benchmarkResults = queryForParent.list();	
			
		}
		catch(Exception e){
			logger.error("Error in getBenchmarkResults() of CommonDAOImpl "+ e);
		}
		return benchmarkResults;
	}
}

