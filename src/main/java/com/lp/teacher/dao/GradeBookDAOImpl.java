
package com.lp.teacher.dao;

import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.Activity;
import com.lp.model.AssignActivity;
import com.lp.model.AssignLessons;
import com.lp.model.AssignmentQuestions;
import com.lp.model.BenchmarkResults;
import com.lp.model.Compositechart;
import com.lp.model.Lesson;
import com.lp.model.Report;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentAttendanceByStatus;
import com.lp.model.StudentCompositeActivityScore;
import com.lp.model.StudentCompositeLessonScore;
import com.lp.model.StudentCompositeProjectScore;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("gradebookdao")
public class GradeBookDAOImpl extends CustomHibernateDaoSupport implements GradeBookDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object[]> getStudentAssignmentsByCsId(long csId, String usedFor) throws SQLDataException {
		List studentAssignments = Collections.emptyList();
		try {
			studentAssignments = (List<Object[]>) getHibernateTemplate().find("select student.studentId, avg(percentage) "
					+ "From StudentAssignmentStatus where student.studentId != null and assignment.usedFor = '"+ usedFor + "' "
					+ "and assignment.classStatus.csId="+ csId +" GROUP BY student.studentId");
		} catch (HibernateException e) {
			logger.error("Error in getStudentAssignmentsByCsId() of GradeBookDAOImpl", e);
			throw new DataException("Error in getStudentAssignmentsByCsId() of GradeBookDAOImpl",e);
		}
		return studentAssignments;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateStudentGrades(List<StudentAssignmentStatus> studentTestList) throws SQLDataException {
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for(StudentAssignmentStatus sas : studentTestList){
				String hql = "UPDATE StudentAssignmentStatus set percentage = :percentage, academicGrade.acedamicGradeId=:academicGrade WHERE studentAssignmentId = :studentAssignmentId";
				Query query = session.createQuery(hql);
				query.setParameter("percentage", sas.getPercentage());
				query.setParameter("academicGrade", sas.getAcademicGrade().getAcedamicGradeId());
				query.setParameter("studentAssignmentId", sas.getStudentAssignmentId());
				query.executeUpdate();
			}
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in updateStudentGrades() of GradeBookDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in updateStudentGrades() of GradeBookDAOImpl",e);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentQuestions> getAssignmentQuestion(long sasId) throws SQLDataException {
		List<AssignmentQuestions> assignQuestion = new ArrayList<AssignmentQuestions>();
		try {
			assignQuestion = (List<AssignmentQuestions>) getHibernateTemplate().find("From AssignmentQuestions"
					+ " where studentAssignmentStatus.studentAssignmentId = "+ sasId);
		} catch (HibernateException e) {
			logger.error("Error in getAssignmentQuestion() of GradeBookDAOImpl", e);
			throw new DataException("Error in getAssignmentQuestion() of GradeBookDAOImpl",e);
		}
		return assignQuestion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStudentAssignmentsExcludePerformance(long csId,
			String usedFor, String fromDate, String toDate) throws SQLDataException {
		List<Object[]> studentAssignments = Collections.emptyList();
		try {
			studentAssignments = (List<Object[]>) getHibernateTemplate().find("select student.studentId, avg(percentage) "
					+ "From StudentAssignmentStatus where student.studentId != null and assignment.usedFor = '"+ usedFor + "'"
					+ " and assignment.classStatus.csId="+ csId +" and assignment.assignmentType.assignmentType "
					+ "!='Performance Task' and assignment.dateAssigned>='"+fromDate+"' and assignment.dateDue<='"+toDate+"' "
					+ "GROUP BY student.studentId");
		} catch (HibernateException e) {
			logger.error("Error in getStudentAssignmentsExcludePerformance() of GradeBookDAOImpl", e);
			throw new DataException("Error in getStudentAssignmentsExcludePerformance() of GradeBookDAOImpl",e);
		}
		return studentAssignments;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStudentPerformanceAssessments(long csId, String fromDate, String toDate) throws SQLDataException {
		List<Object[]> studentAssignments = Collections.emptyList();
		try {
			studentAssignments = (List<Object[]>) getHibernateTemplate().find("select student.studentId, avg(percentage) From "
					+ "StudentAssignmentStatus where student.studentId != null and assignment.classStatus.csId="+ csId +" and "
					+ "assignment.assignmentType.assignmentType ='Performance Task' and assignment.dateAssigned>='"+fromDate+"'"
					+ " and assignment.dateDue<='"+toDate+"' GROUP BY student.studentId");
		} catch (HibernateException e) {
			logger.error("Error in getStudentPerformanceAssessments() of GradeBookDAOImpl", e);
			throw new DataException("Error in getStudentPerformanceAssessments() of GradeBookDAOImpl",e);
		}
		return studentAssignments;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getClassAttendance(long csId, String fromDate, String toDate) throws SQLDataException {
		List<Object[]> studentAttendance = Collections.emptyList();		
		try {
			studentAttendance = (List<Object[]>) getHibernateTemplate().find("select student.studentId, status, count(status)"
					+ " From Attendance where classStatus.csId="+ csId +" and date >='"+fromDate+"' and date<='"+toDate+"' "
					+ "GROUP BY status,student.studentId");
		} catch (HibernateException e) {
			logger.error("Error in getClassAttendance() of GradeBookDAOImpl", e);
			throw new DataException("Error in getClassAttendance() of GradeBookDAOImpl",e);
		}
		return studentAttendance;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Override
	public List<Object[]> getTestPercentage(long assignmentId) throws DataException {
		List<Object[]> list = Collections.emptyList();
		try {			
			Query query =  getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT studentAssignmentStatus.studentAssignmentId,"
					+ " sum(secMarks), sum(maxMarks) from AssignmentQuestions where "
					+ "studentAssignmentStatus.assignment.assignmentId =:assignmentId and studentAssignmentStatus.status=:status "
					+ "and studentAssignmentStatus.gradedStatus=:gradedStatus and questions.assignmentType.assignmentType=:assignmentType"
					+ " group by studentAssignmentStatus.studentAssignmentId");
			query.setParameter("assignmentId", assignmentId);
			query.setParameter("status", WebKeys.TEST_STATUS_SUBMITTED);
			query.setParameter("gradedStatus", WebKeys.GRADED_STATUS_GRADED);
			query.setParameter("assignmentType", WebKeys.ASSIGNMENT_TYPE_MULTIPLE_CHOICES);
			list = query.list();			
		} catch (HibernateException e) {
			logger.error("Error in getItemAnalysisReport() of GradeBookDAOImpl", e);
			throw new DataException("Error in getItemAnalysisReport() of GradeBookDAOImpl",e);
		}
		return list;
	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<AssignmentQuestions> getItemAnalysisReports(long assignmentId) throws DataException {
		List<AssignmentQuestions> list = new ArrayList<AssignmentQuestions>();
		try {
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from AssignmentQuestions where "
					+ "studentAssignmentStatus.assignment.assignmentId =:assignmentId and studentAssignmentStatus.status=:status "
					+ "and studentAssignmentStatus.gradedStatus=:gradedStatus and questions.assignmentType.assignmentType=:assignmentType "
					+ "order by questions.questionId");
			query.setParameter("assignmentId", assignmentId);
			query.setParameter("status", WebKeys.TEST_STATUS_SUBMITTED);
			query.setParameter("gradedStatus", WebKeys.GRADED_STATUS_GRADED);
			query.setParameter("assignmentType", WebKeys.ASSIGNMENT_TYPE_MULTIPLE_CHOICES);
			list = query.list();
		} catch (HibernateException e) {
			logger.error("Error in getItemAnalysisReport() of GradeBookDAOImpl", e);
			throw new DataException("Error in getItemAnalysisReport() of GradeBookDAOImpl",e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void submitReportChanges(Report report) throws SQLDataException {
		try{
			List<Report> reports = new ArrayList<Report>();
			String releaseDate = new SimpleDateFormat("yyyy-MM-dd").format(report.getReleaseDate());
			reports = (List<Report>) getHibernateTemplate().find("From Report where student.studentId = "+ 
			report.getStudent().getStudentId()+" and classStatus.csId="+report.getClassStatus().getCsId()+""
					+ " and releaseDate='"+releaseDate+"'");
			if(reports.size()>0){
				throw new DataException("This report already submitted.");
			}else{
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(report);
				tx.commit();
				session.close();
			}
		}catch (HibernateException e) {
			logger.error("Error in submitReportChanges() of GradeBookDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in submitReportChanges() of GradeBookDAOImpl",e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Report> getReportDates(long csId, long studentId)
			throws SQLDataException {
		List<Report> reports = new ArrayList<Report>();
		try {
			reports = (List<Report>) getHibernateTemplate().find("From Report where student.studentId "
					+ "= "+ studentId+" and classStatus.csId="+csId+" and releaseDate<=current_date order by releaseDate");
		} catch (HibernateException e) {
			logger.error("Error in getReportDates() of GradeBookDAOImpl", e);
			throw new DataException("Error in getReportDates() of GradeBookDAOImpl",e);
		}
		return reports;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Compositechart> getCompositeChartValues(long csId) throws DataException {
		List<Compositechart> list = new ArrayList<Compositechart> ();
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from Compositechart "
					+ "where classStatus.csId=:csId order by gradeevents.eventId, assignmentType.assignmentType");
			query.setParameter("csId", csId);
			list = query.list();
		} catch (HibernateException e) {
			logger.error("Error in getCompositeChartValues() of GradeBookDAOImpl", e);
			throw new DataException("Error in getCompositeChartValues() of GradeBookDAOImpl",e);
		}
		return list;
	}

	@Override
	public boolean saveCompositeChartValues(List<Compositechart> compositecharts) throws DataException {
		try{
			for(Compositechart compositechart : compositecharts){
				getHibernateTemplate().saveOrUpdate(compositechart);
			}
			
		} catch (HibernateException e) {
			logger.error("Error in saveCompositeChartValues() of GradeBookDAOImpl", e);
			throw new DataException("Error in saveCompositeChartValues() of GradeBookDAOImpl",e);
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BenchmarkResults> getBanchResults(long studentId, long csId) throws SQLDataException {
		List<BenchmarkResults> benchResults = new ArrayList<BenchmarkResults>();
		try {
			benchResults = (List<BenchmarkResults>) getHibernateTemplate().find("From BenchmarkResults where "
					+ "studentAssignmentStatus.student.studentId = "+ studentId+" and"
					+ " studentAssignmentStatus.assignment.assignmentType.assignmentType='Fluency Reading'"
					+ " and studentAssignmentStatus.assignment.classStatus.csId="+ csId+""
					+ " order by studentAssignmentStatus.assignment.dateAssigned");
		} catch (HibernateException e) {
			logger.error("Error in getBanchResults() of GradeBookDAOImpl", e);
			throw new DataException("Error in getBanchResults() of GradeBookDAOImpl",e);
		}
		return benchResults;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentQuestions> getBenchAssignments(long studentId, long csId) throws SQLDataException {
		List<AssignmentQuestions> benchAssignments = new ArrayList<AssignmentQuestions>();
		try {
			benchAssignments = (List<AssignmentQuestions>) getHibernateTemplate().find("From AssignmentQuestions where "
					+ "studentAssignmentStatus.student.studentId = "+ studentId+" and "
					+ "studentAssignmentStatus.assignment.assignmentType.assignmentType='Fluency Reading'"
					+ " and studentAssignmentStatus.assignment.classStatus.csId="+ csId+""
					+ " and studentAssignmentStatus.gradedStatus='graded'");
		} catch (HibernateException e) {
			logger.error("Error in getBenchAssignments() of GradeBookDAOImpl", e);
			throw new DataException("Error in getBenchAssignments() of GradeBookDAOImpl",e);
		}
		return benchAssignments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudentAttendanceByStatus getStudentAttendance(long csId, long studentId) throws SQLDataException {
		List<Object[]> studentAttendance = Collections.emptyList();
		String status = null;
		long totalCount =0;
		StudentAttendanceByStatus  sAttendanceByStatus = new StudentAttendanceByStatus();
 		try {
			studentAttendance = (List<Object[]>) getHibernateTemplate().find("select status, count(status)"
					+ " From Attendance where classStatus.csId="+ csId +" and student.studentId="+studentId+" GROUP BY status");
			
			for (Object[] o : studentAttendance) {
				status = (String) o[0];
				if(status.equalsIgnoreCase(WebKeys.ATTENDANCE_STATUS_PRESENT)){
					sAttendanceByStatus.setStatus("present");
					sAttendanceByStatus.setStatusCount((long) o[1]);
				}
				totalCount+=(long) o[1];					
			}
			sAttendanceByStatus.setTotalCount(totalCount);
		} catch (HibernateException e) {
			logger.error("Error in getStudentAttendance() of GradeBookDAOImpl", e);
			throw new DataException("Error in getStudentAttendance() of GradeBookDAOImpl",e);
		}
		return sAttendanceByStatus;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentTests(long csId, long studentId) throws SQLDataException {
		List<StudentAssignmentStatus> studentTests = Collections.emptyList();		
		try {
			studentTests = (List<StudentAssignmentStatus>) getHibernateTemplate().find("Select * from StudentAssignmentStatus where "
					+ " student.studentId ="+studentId+" and assignment.classStatus.csId="+csId+" order by retestId");
		} catch (HibernateException e) {
			logger.error("Error in getTestPercentage() of GradeBookDAOImpl", e);
			throw new DataException("Error in getTestPercentage() of GradeBookDAOImpl",e);
		}
		return studentTests;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<StudentAssignmentStatus> getTests(long csId, long studentId) throws SQLDataException {
		List<Object[]> tests = Collections.emptyList();		
		List<StudentAssignmentStatus> studentTests = new ArrayList<StudentAssignmentStatus> ();
		List<Long> studentAssignmentIds = new ArrayList<Long> ();
		try {
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("select retestId, max(percentage)"
					+ " From StudentAssignmentStatus where assignment.classStatus.csId=:csId and student.studentId=:studentId"
					+ " and status=:status and gradedStatus=:gradedStatus GROUP BY retestId");
			query.setParameter("csId", csId);
			query.setParameter("studentId", studentId);
			query.setParameter("status", WebKeys.TEST_STATUS_SUBMITTED);
			query.setParameter("gradedStatus", WebKeys.GRADED_STATUS_GRADED);			
			tests = (List<Object[]>) query.list();
			
			for (Object[] o : tests) {
				studentAssignmentIds.add((Long) o[0]);		
			}
			if(!studentAssignmentIds.isEmpty()){
				Query query2=  getHibernateTemplate().getSessionFactory().openSession().createQuery("from StudentAssignmentStatus where "
						+ " studentAssignmentId in (:studentAssignmentIds)");			
				query2.setParameterList("studentAssignmentIds", studentAssignmentIds);
				studentTests = query2.list();
			}
		}catch (HibernateException e) {
			logger.error("Error in getTests() of GradeBookDAOImpl", e);
			throw new DataException("Error in getTests() of GradeBookDAOImpl",e);
		}
		return studentTests;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudentCompositeProjectScore getStudentCompositeProjectScore(long csId, long studentId) throws SQLDataException{
		List<StudentCompositeProjectScore> projectScore = new ArrayList<StudentCompositeProjectScore> ();
		StudentCompositeProjectScore compositeProjectScore = new StudentCompositeProjectScore();
		try {
			projectScore = (List<StudentCompositeProjectScore>) getHibernateTemplate().find("From StudentCompositeProjectScore "
					+ "where classStatus.csId="+ csId +" and student.studentId="+studentId);
		}catch (HibernateException e) {
			logger.error("Error in getStudentCompositeProjectScore() of GradeBookDAOImpl", e);
			throw new DataException("Error in getStudentCompositeProjectScore() of GradeBookDAOImpl",e);
		}
		if(!projectScore.isEmpty()){
			compositeProjectScore = projectScore.get(0);
		}
		return compositeProjectScore;
	}
	
	@Override
	public List<AssignLessons> getAssignedLesson(long csId, long studentId) {
		List<AssignLessons> assignedLessons = new ArrayList<>();
		try {
			String query = "SELECT t1.lesson_id, l.lesson_name, t1.assign_id, t1.assigned_date, t2.score, ag.acedamic_grade_name "
					+ "FROM  assign_lessons t1 inner join lesson l on l.lesson_id =  t1.lesson_id left JOIN  "
					+ "student_composite_lesson_score t2 ON t1.assign_id=t2.assign_lesson_id left join "
					+ "academic_grades ag on t2.academic_grade_id = ag.acedamic_grade_id where t1.cs_id=? and t2.student_id=?";
			SqlRowSet resultSet = jdbcTemplate.queryForRowSet(query,csId,studentId);		
			
			while(resultSet.next()) {
				Lesson lesson = new Lesson();
				lesson.setLessonId(resultSet.getLong(1));
				lesson.setLessonName(resultSet.getString(2));
				AssignLessons assignLessons = new AssignLessons();
				assignLessons.setLesson(lesson);
				assignLessons.setAssignId(resultSet.getLong(3));
				assignLessons.setAssignedDate(resultSet.getDate(4));
				assignLessons.setScore(resultSet.getInt(5));
				assignLessons.setAcademicGradeName(resultSet.getString(6));
				assignedLessons.add(assignLessons);				
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return assignedLessons;
	}

	@Override
	public List<AssignActivity> getAssignedActivities(long csId, long studentId) {
		List<AssignActivity> assignedActivities = new ArrayList<>();
		try {
			String query = "SELECT a.activity_desc, t1.assign_activity_id, t1.assigned_date, t2.score, ag.acedamic_grade_name "
					+ "FROM  assign_activity t1 inner join activity a on a.activity_id =  t1.activity_id left JOIN "
					+ "student_composite_activity_score t2 ON t1.assign_activity_id=t2.assign_activity_id left join "
					+ "academic_grades ag on t2.academic_grade_id = ag.acedamic_grade_id where t1.cs_id=? and t2.student_id=?";
			SqlRowSet resultSet = jdbcTemplate.queryForRowSet(query,csId,studentId);		
			while(resultSet.next()) {
				Activity activity = new Activity();
				activity.setActivityDesc(resultSet.getString(1));
				AssignActivity assignActivity = new AssignActivity();
				assignActivity.setActivity(activity);
				assignActivity.setAssignActivityId(resultSet.getLong(2));
				assignActivity.setAssignedDate(resultSet.getDate(3));
				assignActivity.setScore(resultSet.getInt(4));
				assignActivity.setAcademicGradeName(resultSet.getString(5));
				assignedActivities.add(assignActivity);				
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return assignedActivities;
	}

	@Override
	public boolean saveStudentCompositeChartValues(List<StudentCompositeLessonScore> lessonScores, List<StudentCompositeActivityScore> activityScores, StudentCompositeProjectScore projectScore) 
			throws SQLDataException {
		for(StudentCompositeLessonScore studentCompositeLessonScore : lessonScores){
			getHibernateTemplate().saveOrUpdate(studentCompositeLessonScore);
		}
		for(StudentCompositeActivityScore studentCompositeActivityScore : activityScores){
			getHibernateTemplate().saveOrUpdate(studentCompositeActivityScore);
		}
		getHibernateTemplate().saveOrUpdate(projectScore);
		return true;
	}



	@SuppressWarnings("rawtypes")
	@Override
	public boolean saveParentComments(long reportId, String comment)
			throws SQLDataException {
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "UPDATE Report set parentComments = :parentComments WHERE reportId = :reportId";
			Query query = session.createQuery(hql);
			query.setParameter("parentComments", comment);
			query.setParameter("reportId", reportId);
			query.executeUpdate();
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in saveParentComments() of GradeBookDAOImpl" + e);
			throw new DataException("Error in saveParentComments() of GradeBookDAOImpl",e);
		}
		return true;
	}
}

	
