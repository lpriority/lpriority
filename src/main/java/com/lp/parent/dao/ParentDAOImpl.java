package com.lp.parent.dao;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.AcademicYear;
import com.lp.model.ClassStatus;
import com.lp.model.ParentLastseen;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("parentDao")
public class ParentDAOImpl extends CustomHibernateDaoSupport implements ParentDAO {	
	@Autowired
	private SessionFactory sessionFactory;
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private HttpSession httpSession;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRegistration> getchildren(long parentRegId){
		List<UserRegistration> childList = (List<UserRegistration>) getHibernateTemplate().find("FROM UserRegistration where (parentRegId ="+parentRegId+" or parentRegId2="+parentRegId+") and status='"+WebKeys.LP_STATUS_ACTIVE+"'");
	return childList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentByParent(long parentRegId) throws DataException {
		List<Student> students = new ArrayList<Student>();
			try{
				students = (List<Student>) getHibernateTemplate().find("FROM Student where (userRegistration.parentRegId ="+parentRegId+" or userRegistration.parentRegId2="+parentRegId+") and userRegistration.status='"+WebKeys.LP_STATUS_ACTIVE+"'");
//				students = (List<Student>) getHibernateTemplate().find("FROM Student where userRegistration.regId in "
//						+ "(Select regId FROM UserRegistration where parent_reg_id ="+parentRegId+" or parent_reg_id2="+parentRegId+" and status='"+WebKeys.ACTIVE+"')");
			}catch(DataException e){
				logger.error("Error in getStudentByParent() of  ParentDAOImpl"+ e);
				e.printStackTrace();
				throw new DataException("Error in getStudentByParent() of ParentDAOImpl", e);
			}
			return students;
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<UserRegistration> getUnregisteredChildren(long parentId) throws DataException {
		List<UserRegistration> userRegistrations = Collections.emptyList();
		try {
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery(
					"From UserRegistration where parentRegId=:parentRegId and user.userType=:userType and status=:status");
			query.setParameter("parentRegId", parentId);
			query.setParameter("userType", WebKeys.LP_USER_TYPE_STUDENT_BELOW_13);
			query.setParameter("status", WebKeys.LP_STATUS_NEW);
			userRegistrations = query.list();
		} catch (HibernateException e) {
			logger.error("Error in getUnregisteredChildren() of ParentDAOImpl", e);
			throw new DataException("Error in getUnregisteredChildren() of ParentDAOImpl",e);
		}
		return userRegistrations;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentsTestResults(long studentId, String usedFor,String testStatus, String gradedStatus,long classId, long perGroupId) {
		List<StudentAssignmentStatus> studentTestList = null;
		try {
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where (student.studentId = "
							+ studentId
							+ " or performanceGroup.performanceGroupId = "
							+ perGroupId
							+ ") and status = '"
							+ testStatus
							+ "' and assignment.classStatus.csId= "
							+ classId
							+ " and gradedStatus = '"
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ WebKeys.ASSIGN_STATUS_ACTIVE
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "'");
		} catch (HibernateException e) {
			logger.error("Error in getStudentsTestResults() of ParentDAOImpl", e);
		}
		return studentTestList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getChildsBenchmarkResults(Student student, String usedFor,String testStatus, String gradedStatus,long classId,long assignmentTypeId) {
		List<StudentAssignmentStatus> studentTestList = null;
		AcademicYear academicYear = null;
		String assignStatus = WebKeys.ACTIVE;
		String query = "";
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try{
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				assignStatus =  WebKeys.EXPIRED;
				query =  " and assignment.dateAssigned between '"+academicYear.getStartDate()+"' and '"+academicYear.getEndDate()+"'";
			}
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "
							+ student.getStudentId()
							+ " and status = '"
							+ testStatus
							+ "' and assignment.classStatus.csId= "
							+ classId
							+ " and assignment.assignStatus='"
							+ assignStatus
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "'"
							+ " and gradedStatus='" + gradedStatus + "'"
							+ " and assignment.assignmentType.assignmentTypeId="+assignmentTypeId
							+ query);
		} catch (HibernateException e) {
			logger.error("Error in getStudentsBenchmarkResults() of ParentDAOImpl", e);
		}
		return studentTestList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getChildHomeworkReportDates(Student student,String usedFor){
		List<StudentAssignmentStatus> studentTestList = null;
		try {
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "
							+ student.getStudentId()
							+ " and gradedStatus = '"
							+ WebKeys.GRADED_STATUS_GRADED
							+ "'"
							+ " and assignment.assignStatus='"
							+ WebKeys.ASSIGN_STATUS_ACTIVE
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "'"
									+ " and student.grade.gradeId="+student.getGrade().getGradeId()+" group by assignment.dateAssigned");
		} catch (HibernateException e) {
			logger.error("Error in getChildHomeworkReportDates() of ParentDAOImpl", e);
		}
		return studentTestList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getChildHomeworkReports(Student student,String usedFor,String assignedDate){
		List<StudentAssignmentStatus> studentTestList = null;
		try {
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "
							+ student.getStudentId()
							+ " and gradedStatus = '"
							+ WebKeys.GRADED_STATUS_GRADED
							+ "'"
							+ " and assignment.assignStatus='"
							+ WebKeys.ASSIGN_STATUS_ACTIVE
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "'"
									+ " and student.grade.gradeId="+student.getGrade().getGradeId()+" and assignment.dateAssigned='"+assignedDate+"'");
		} catch (HibernateException e) {
			logger.error("Error in getChildHomeworkReports() of ParentDAOImpl", e);
		}
		
		return studentTestList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ParentLastseen getParentLastseen(UserRegistration userReg)
			throws DataException {
		List<ParentLastseen> parentLastseen = new ArrayList<ParentLastseen>();
		try{
			parentLastseen = (List<ParentLastseen>) getHibernateTemplate().find("FROM ParentLastseen where parentId.regId="+userReg.getRegId());
		}catch(DataException e){
			logger.error("Error in getParentLastseen() of  ParentDAOImpl"+ e);
			throw new DataException("Error in getParentLastseen() of ParentDAOImpl", e);
		}
		if(parentLastseen.size()>0){
			return parentLastseen.get(0);
		}else{
			return new ParentLastseen();
		}
	}

	@Override
	public void saveParentLastSeen(ParentLastseen parentLastseen)
			throws DataException {
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(parentLastseen);						
			tx.commit();
			session.close();
		}catch (HibernateException e) {
			logger.error("Error in saveParentLastSeen() of AssessmentDAOImpl" + e);
			throw new DataException("Error in saveParentLastSeen() of AssessmentDAOImpl",e);
		}
		
	}
	public Long getChildCorrectResponses(long studentAssignmentId){
		long correct=0;
		try{
		String query = "select count(*) from assignment_questions where"
                    + " student_assignment_id="+studentAssignmentId+" and sec_marks!=0";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {

			correct=rs.getInt(1);

		}
		}catch(Exception e){
			logger.error("Error in getChildCorrectResponses() of ParentDAOImpl", e);
		}
		return correct;
	}
	public Long getChildWrongResponses(long studentAssignmentId){
		long wrong=0;
		try{
		String query = "select count(*) from assignment_questions where"
                    + " student_assignment_id="+studentAssignmentId+" and sec_marks=0";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {

			wrong=rs.getInt(1);

		}}catch(Exception e){
			logger.error("Error in getChildWrongResponses() of ParentDAOImpl", e);
		}
		return wrong;
	}

	public List<ParentLastseen> getAllParentLastseen(long csId)	throws DataException {
		List<ParentLastseen> parentLastseen = new ArrayList<ParentLastseen>();
		try{
			String query = "SELECT u.first_name, u.last_name, u1.email_Id, p.last_logged_in, p.last_logged_out, p.last_seen_feature "
					+ "FROM register_for_class r join student s  on r.student_id =  s.student_id join user_registration u "
					+ "on s.reg_id = u.reg_id left JOIN parent_lastseen p ON p.parent_id=u.parent_reg_id left join user_registration u1 "
					+ "on p.parent_id = u1.reg_id where r.cs_id = ? and r.status = '"+WebKeys.ACCEPTED+"' and r.class_status = '"+WebKeys.ALIVE+"'"
					+ " order by  u.first_name ";
			
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query,csId);
			while(rs.next()){
				ParentLastseen pls = new ParentLastseen();
				pls.setChildName(rs.getString(1) + " " +rs.getString(2));
				pls.setParentEmail(rs.getString(3));
				pls.setLastLoggedIn(rs.getDate(4));
				pls.setLastLoggedOut(rs.getDate(5));
				pls.setLastSeenFeature(rs.getString(6));
				parentLastseen.add(pls);
			}
		}catch(Exception e){
			logger.error("Error in getAllParentLastseen() of ParentDAOImpl", e);
		}
		return parentLastseen;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ClassStatus> getAvailableTeachers(long gradeClassId) throws DataException{
		List<ClassStatus> classStatusList = new ArrayList<ClassStatus>();
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("FROM ClassStatus where "
					+ "section.gradeClasses.gradeClassId=:gradeClassId and availStatus=:availStatus group by teacher.teacherId");
			query.setParameter("gradeClassId", gradeClassId);
			query.setParameter("availStatus",WebKeys.AVAILABLE);
			classStatusList = query.list();
		}catch(DataException e){
			logger.error("Error in getAvailableTeachers() of ParentServiceImpl", e);
		}
		return classStatusList;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ClassStatus> getTeacherSections(long gradeClassId, long teacherId) throws DataException{
		List<ClassStatus> classStatusList = new ArrayList<ClassStatus>();
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("FROM ClassStatus where "
					+ "section.gradeClasses.gradeClassId=:gradeClassId and availStatus=:availStatus and teacher.teacherId=:teacherId");
			query.setParameter("gradeClassId", gradeClassId);
			query.setParameter("availStatus",WebKeys.AVAILABLE);
			query.setParameter("teacherId", teacherId);
			classStatusList = query.list();
		}catch(DataException e){
			logger.error("Error in getAvailableTeachers() of ParentServiceImpl", e);
		}
		return classStatusList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getChildTestResults(long studentId, String usedFor,String testStatus, String gradedStatus,long classId, long perGroupId) {
		List<StudentAssignmentStatus> studentTestList = null;
		AcademicYear academicYear = null;
		String assignStatus = WebKeys.ACTIVE;
		String query = "";
		if(httpSession.getAttribute("academicYear") != null)
			academicYear = (AcademicYear) httpSession.getAttribute("academicYear");
		try{
			if(httpSession.getAttribute("academicYrFlag")!=null && httpSession.getAttribute("academicYrFlag").toString().equalsIgnoreCase(WebKeys.LP_SHOW) 
					&& academicYear != null && !academicYear.getIsCurrentYear().equalsIgnoreCase(WebKeys.LP_YES)){
				assignStatus =  WebKeys.EXPIRED;
				query =  " and assignment.dateAssigned between '"+academicYear.getStartDate()+"' and '"+academicYear.getEndDate()+"'";
			}
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "
							+ studentId
							+ "and status = '"
							+ testStatus
							+ "' and assignment.classStatus.csId= "
							+ classId
							+ " and gradedStatus = '"
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ assignStatus
							+ "'"
							+ " and assignment.usedFor='" + usedFor + "' and assignment.assignmentType.assignmentTypeId!=8"
							+ query);
		} catch (HibernateException e) {
			logger.error("Error in getStudentsTestResults() of ParentDAOImpl", e);
		}
		return studentTestList;
	}
}
