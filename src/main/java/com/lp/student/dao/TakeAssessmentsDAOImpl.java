package com.lp.student.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.model.AcademicGrades;
import com.lp.model.AssignmentQuestions;
import com.lp.model.Questions;
import com.lp.model.RflpPractice;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("takeAssessmentsDAO")
public class TakeAssessmentsDAOImpl extends CustomHibernateDaoSupport implements
		TakeAssessmentsDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	static final Logger logger = Logger.getLogger(TakeAssessmentsDAOImpl.class);
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@SuppressWarnings("unchecked")
	@Override
	public Student getStudent(UserRegistration userReg) {
		Student student = null;
		try {
			List<Student> studentList = (List<Student>) getHibernateTemplate()
					.find("From Student where userRegistration.regId="
							+ userReg.getRegId());
			if (!studentList.isEmpty()) {
				student = studentList.get(0);
			}
		} catch (HibernateException e) {
			logger.error("Error in getStudent() of AssessmentDAOImpl", e);
		}
		return student;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentTests(Student student,
			String usedFor, String testStatus, String gradedStatus) {
		List<StudentAssignmentStatus> studentTestList = null;
		try {
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "
							+ student.getStudentId()
							+ " and ( status = '"+WebKeys.TEST_STATUS_SAVED+"' OR status = '"+testStatus+"' )" 
							+ " and gradedStatus = '"
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ WebKeys.ASSIGN_STATUS_ACTIVE
							+ "'"
							+ " and assignment.usedFor='"+usedFor+"' ");	
		} catch (HibernateException e) {
			logger.error("Error in getStudentTests() of AssessmentDAOImpl", e);
		}
		return studentTestList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentCurrentHomeworks(
			Student student, String usedFor, String testStatus,
			String gradedStatus, long csId) {
		List<StudentAssignmentStatus> studentTestList = null;
		try {
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "+ student.getStudentId() + " and status = '" + testStatus
							+ "' and gradedStatus = '" + gradedStatus + "' and assignment.assignStatus='"+ WebKeys.ASSIGN_STATUS_ACTIVE+"'"
							+ " and assignment.usedFor='"+ usedFor+ "'"+ " and assignment.dateDue>=current_date and assignment.classStatus.csId="+csId);
		} catch (HibernateException e) {
			logger.error("Error in getStudentTests() of AssessmentDAOImpl", e);
		}
		return studentTestList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentDueHomeworks(
			Student student, String usedFor, String testStatus,
			String gradedStatus, long csId) {
		List<StudentAssignmentStatus> studentTestList = null;
		try {
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "
							+ student.getStudentId()
							+ " and status = '"
							+ testStatus
							+ "' and gradedStatus = '"
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ WebKeys.ASSIGN_STATUS_ACTIVE
							+ "'"
							+ " and assignment.usedFor='"
							+ usedFor
							+ "'"
							+ " and assignment.dateDue<current_date"
							+ " and assignment.classStatus.csId="+csId);
		} catch (HibernateException e) {
			logger.error("Error in getStudentTests() of AssessmentDAOImpl", e);
		}
		return studentTestList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentQuestions> getTestQuestions(long studentAssignmentId) {
		List<AssignmentQuestions> questions = null;
		try {
			questions = (List<AssignmentQuestions>) getHibernateTemplate()
					.find("From AssignmentQuestions where studentAssignmentStatus.studentAssignmentId="
							+ studentAssignmentId);
		} catch (Exception e) {
			logger.error("Error in getTestQuestions() of AssessmentDAOImpl", e);
		}
		return questions;
	}

	@Override
	public boolean saveAssignmentQuestionsMarks(AssignmentQuestions assignmentQuestion) {
		boolean status = false;
		try {
			 getHibernateTemplate().saveOrUpdate(assignmentQuestion);
			 status = true;
		} catch (Exception e) {
			logger.error("Error in saveAssignmentQuestionsMarks() of AssessmentDAOImpl", e);
			status = false;
		}finally{
		}
		return status;
	}

	
	@Override
	public boolean saveAssignment(
			StudentAssignmentStatus studentAssignmentStatus, String operation,
			boolean lateSubmission) {
		boolean status = false;
		try {			
			getHibernateTemplate().saveOrUpdate(studentAssignmentStatus);
			logger.info("studentAssignmentStatus is saved successfully ");
			logger.info("saved completed operation" + operation );
			status = true;
		} catch (Exception e) {
			logger.error("Error in saveAssignment() of AssessmentDAOImpl", e);
			status = false;
		} finally {
		}
		return status;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void saveJacAnswer(String answer, long assignmentQuestionId,
			long secMark) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			String hql = "UPDATE AssignmentQuestions set secMarks = :secMark, maxMarks=:maxMark, answer=:answer"
					+ " WHERE assignmentQuestionsId = :assignmentQuestionsId";
			Query query = session.createQuery(hql);
			query.setParameter("secMark", (int) secMark);
			query.setParameter("maxMark", 1);
			query.setParameter("answer", answer);
			query.setParameter("assignmentQuestionsId", assignmentQuestionId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public long getSecuredMarks(long studentAssignmentId) {
		long totalSecMarks = 0;
		try {
			String query = "select sum(sec_marks) from assignment_questions where student_assignment_id="
					+ studentAssignmentId + "";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				totalSecMarks = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalSecMarks;
	}

	@Override
	public long getMaxMarks(long studentAssignmentId) {
		long totalMaxMarks = 0;
		try {
			String query = "select sum(max_marks) from assignment_questions where student_assignment_id="
					+ studentAssignmentId + "";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			if (rs.next()) {
				totalMaxMarks = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalMaxMarks;
	}

	@SuppressWarnings("rawtypes")
	public boolean submitJacTemplateTest(long studentAssignmentId,
			float percentage, AcademicGrades academicGrades) {
		Session session = getSessionFactory().getCurrentSession();
		try {
			String hql = "UPDATE StudentAssignmentStatus set status = :status, percentage=:percentage,submitdate=current_date, gradedStatus=:gradedStatus,"
					+ " academicGrade.acedamicGradeId=:acedamicGradeId, gradedDate=:gradedDate,readStatus=:readStatus WHERE studentAssignmentId = :studentAssignmentId";
			Query query = session.createQuery(hql);
			query.setParameter("status", "submitted");
			query.setParameter("percentage", percentage);
			query.setParameter("gradedStatus", WebKeys.GRADED_STATUS_GRADED);
			query.setParameter("acedamicGradeId", academicGrades.getAcedamicGradeId());
			query.setParameter("gradedDate", new Date());
			query.setParameter("readStatus",WebKeys.UN_READ);
			query.setParameter("studentAssignmentId", studentAssignmentId);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean submitBenchmarkTest(long studentAssignmentId){
		Session session = getSessionFactory().getCurrentSession();
		boolean status = false;
		try {
			logger.info("submit Fluency Test dao"+studentAssignmentId);
			String hql = "UPDATE StudentAssignmentStatus set status = :status,submitdate=current_date WHERE studentAssignmentId = :studentAssignmentId";
			Query query = session.createQuery(hql);
			query.setParameter("status", "submitted");
			query.setParameter("studentAssignmentId", studentAssignmentId);
			query.executeUpdate();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean autoSaveAssignment(long assignmentQuestionId, String answer){
		Session session = getSessionFactory().getCurrentSession();
		boolean status = false;
		try {
			String hql = "UPDATE AssignmentQuestions set answer = :answer WHERE assignmentQuestionsId = :assignmentQuestionsId";
			Query query = session.createQuery(hql);
			query.setParameter("answer", answer);
			query.setParameter("assignmentQuestionsId", assignmentQuestionId);
			query.executeUpdate();
			status = true;
		} catch (Exception e) {
			logger.info("there is an error in autoSaveAssignment in TakeAssessmentsServiceDAOImpl");
			status = false;
			e.printStackTrace();
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RflpPractice> getRflpTest(long studentAssignmentId){
		List<RflpPractice> rflpPracticeLt=null;
		try{
		 rflpPracticeLt = (List<RflpPractice>) getHibernateTemplate().find("From RflpPractice where rflpTest.studentAssignmentStatus.studentAssignmentId="+studentAssignmentId+" ORDER BY wordNum asc");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return rflpPracticeLt;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean saveRflpTest(long rflpPracticeId, String studentSentence, String operation, long studentAssignmentId){
		Session session = getSessionFactory().getCurrentSession();
		boolean status = false;
		try {
			String hql = "UPDATE RflpPractice set studentSentence = :studentSentence WHERE rflpPracticeId = :rflpPracticeId";
			Query query = session.createQuery(hql);
			query.setParameter("rflpPracticeId", rflpPracticeId);
			query.setParameter("studentSentence", studentSentence);
			query.executeUpdate();
			
			if(operation.equalsIgnoreCase(WebKeys.TEST_SUBMIT)){
				String hql1 = "UPDATE StudentAssignmentStatus set status = :status, submitdate=current_date  WHERE studentAssignmentId = :studentAssignmentId";
				Query query1 = session.createQuery(hql1);
				query1.setParameter("status", WebKeys.TEST_STATUS_SUBMITTED);
				query1.setParameter("studentAssignmentId", studentAssignmentId);
				query1.executeUpdate();
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean submitRflpTest(RflpTest rflpTest){
		Session session = getSessionFactory().getCurrentSession();
		String operation = rflpTest.getOperation();
		long studentAssignmentId = rflpTest.getStudentAssignmentStatus().getStudentAssignmentId();
		float percentage = rflpTest.getStudentAssignmentStatus().getPercentage();
		boolean status = false;
		try {
			session.saveOrUpdate(rflpTest);
			List<RflpPractice> rflpPracticeLt =  rflpTest.getRflpPractice();
			for (RflpPractice rflpPractice : rflpPracticeLt) {
				String hql = "UPDATE RflpPractice set writtenRubricScore = :writtenRubricScore, oralRubricScore = :oralRubricScore WHERE rflpPracticeId = :rflpPracticeId";
				Query query = session.createQuery(hql);
				query.setParameter("rflpPracticeId", rflpPractice.getRflpPracticeId());
				query.setParameter("writtenRubricScore", rflpPractice.getWrittenRubricScore());
				query.setParameter("oralRubricScore", rflpPractice.getOralRubricScore());
				query.executeUpdate();
			}
			String hql = "";
			Query query = null;
			if(operation.equalsIgnoreCase(WebKeys.TEST_SUBMIT)){
				hql = "UPDATE StudentAssignmentStatus set gradedStatus = :gradedStatus, percentage = :percentage, gradedDate=:gradedDate,readStatus=:readStatus WHERE studentAssignmentId = :studentAssignmentId";							
				query = session.createQuery(hql);
				query.setParameter("gradedStatus", WebKeys.GRADED_STATUS_GRADED);
				query.setParameter("gradedDate", new Date());
				query.setParameter("readStatus", WebKeys.UN_READ);
			}else{
				hql = "UPDATE StudentAssignmentStatus set percentage = :percentage WHERE studentAssignmentId = :studentAssignmentId";
				query = session.createQuery(hql);
			}
			query.setParameter("percentage", percentage);
			query.setParameter("studentAssignmentId", studentAssignmentId);				
			query.executeUpdate();
			
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Questions> getCompQuestionList(long subQuestionsId){
		List<Questions> questions = null;
		try {
			questions = (List<Questions>) getHibernateTemplate()
					.find("From Questions where subQuestions.subQuestionId="
							+ subQuestionsId);
		} catch (Exception e) {
			logger.error("Error in getCompQuestionList() of AssessmentDAOImpl", e);
		}
		return questions;
	}
	@Override
	public List<Questions> getCompQuestionList(long subQuestionsId,List<AssignmentQuestions> assignQuests){
		List<Questions> questions = new ArrayList<Questions>();
		try {
			for (AssignmentQuestions q : assignQuests) {
				if (q.getQuestions().getSubQuestions().getSubQuestionId()==subQuestionsId)
						questions.add(q.getQuestions());
					
				}
			
		} catch (Exception e) {
			logger.error("Error in getCompQuestionList() of AssessmentDAOImpl", e);
		}
		return questions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentWordWorks(Student student, String testStatus, String gradedStatus) {
		List<StudentAssignmentStatus> studentTestList = Collections.emptyList();
		try{
			studentTestList = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where student.studentId = "
							+ student.getStudentId()
							+ " and ( status = '"+WebKeys.TEST_STATUS_SAVED+"' OR status = '"+testStatus+"' )" 
							+ " and gradedStatus = '"
							+ gradedStatus
							+ "'"
							+ " and assignment.assignStatus='"
							+ WebKeys.ASSIGN_STATUS_ACTIVE
							+ "' and assignment.assignmentType.assignmentType='"+WebKeys.ASSIGNMENT_TYPE_RFLP+"'"
							+ " and assignment.usedFor='"+WebKeys.LP_USED_FOR_HOMEWORKS+"' ");
		}
		catch(Exception e){
			logger.error("Error in  getStudentWordWorks() of  AssignAssessmentsServiceImpl");
		}
		return studentTestList;
	}
}
