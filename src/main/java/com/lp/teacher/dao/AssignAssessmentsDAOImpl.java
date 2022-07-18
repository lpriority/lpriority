package com.lp.teacher.dao;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
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

import com.lp.common.dao.PerformanceTaskDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignLessons;
import com.lp.model.AssignedPtasks;
import com.lp.model.Assignment;
import com.lp.model.AssignmentQuestions;
import com.lp.model.AsstypeReadtypeRelation;
import com.lp.model.ClassStatus;
import com.lp.model.FluencyMarks;
import com.lp.model.GradingTypes;
import com.lp.model.GroupPerformanceTemp;
import com.lp.model.GroupPerformanceTempId;
import com.lp.model.JacQuestionFile;
import com.lp.model.PerformanceGroupStudents;
import com.lp.model.Questions;
import com.lp.model.ReadingTypes;
import com.lp.model.RflpPractice;
import com.lp.model.RflpTest;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.Teacher;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("assignassessmentdao")
public class AssignAssessmentsDAOImpl extends CustomHibernateDaoSupport
		implements AssignAssessmentsDAO {
	
	static final Logger logger = Logger.getLogger(AssignAssessmentsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PerformanceTaskDAO performanceTaskDAO;
	@Autowired
	private CommonDAO commonDAO;	
	@Autowired
	private HttpSession session;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssignLessons> getTeacherAssignLessons(Teacher teacher, long csId) {
		List<AssignLessons> assignLessons = Collections.emptyList();
		try {
			if(teacher != null){
				assignLessons = (List<AssignLessons>) getHibernateTemplate().find("from AssignLessons where classStatus.csId=" + csId
						+ " and " + "classStatus.teacher.teacherId="+ teacher.getTeacherId() + "");
			}else{
				assignLessons = (List<AssignLessons>) getHibernateTemplate().find("from AssignLessons where classStatus.csId=" + csId);
			}
		} catch (HibernateException e) {
			logger.error("Error in getTeacherAssignLessons() of  AssignAssessmentsDAOImpl"+ e);
		}
		return assignLessons;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Questions> getQuestions(long lessonId, long assignmentTypeId, Teacher teacher, String usedFor,long gradeId) {
		List<Questions> questions = Collections.emptyList();
		UserRegistration adminReg = new UserRegistration();
		if(teacher != null){
			adminReg = commonDAO.getAdminByTeacher(teacher.getUserRegistration());
		}else{
			adminReg = (UserRegistration) session.getAttribute(WebKeys.LP_USER_REGISTRATION);
		}		
		try {
			if(teacher != null){
				if(assignmentTypeId==8 || assignmentTypeId==20 || assignmentTypeId==19){
					questions = (List<Questions>) getHibernateTemplate().find(
							"from Questions where assignmentType.assignmentTypeId="
									+ assignmentTypeId + " and (createdBy="+ teacher.getUserRegistration().getRegId()
									+ " or createdBy="+ adminReg.getRegId()+") and grade.gradeId="+gradeId+" and " 
									+ "usedFor='" + usedFor+ "' and queStatus='active' order by questionId");
				}else{
				questions = (List<Questions>) getHibernateTemplate().find(
						"from Questions where assignmentType.assignmentTypeId="
								+ assignmentTypeId + " and (createdBy="+ teacher.getUserRegistration().getRegId()
								+ " or createdBy="+ adminReg.getRegId()+") and " 
								+ "usedFor='" + usedFor+ "' and lesson.lessonId=" + lessonId+ " order by questionId");}
			}else{
				if(assignmentTypeId==8 || assignmentTypeId==20 || assignmentTypeId==19){
				questions = (List<Questions>) getHibernateTemplate().find(
						"from Questions where assignmentType.assignmentTypeId="
								+ assignmentTypeId + " and createdBy="+ adminReg.getRegId()+" and " 
								+ "usedFor='" + usedFor+ "' and grade.gradeId="+gradeId+" and queStatus='active' order by questionId");
				}else{
					questions = (List<Questions>) getHibernateTemplate().find(
							"from Questions where assignmentType.assignmentTypeId="
									+ assignmentTypeId + " and createdBy="+ adminReg.getRegId()+" and " 
									+ "usedFor='" + usedFor+ "' and lesson.lessonId=" + lessonId+ " order by questionId");
				}
			}
		} catch (HibernateException e) {
			logger.error("Error in getQuestions() of  AssignAssessmentsDAOImpl"+ e);
		}
		return questions;
	}

	public long checkAssignmentExists(long cs_id, Long benchmarkId) {
		String query = null;
		long assignmentId = 0;
		if (benchmarkId == 1 || benchmarkId == 2 || benchmarkId == 3) {
			query = "select * from assignment where cs_id=" + cs_id
					+ " and benchmark_id = " + benchmarkId +" and assign_status='"+WebKeys.ACTIVE+"'";
		}
		

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			assignmentId = rs.getLong("assignment_id");
		}
		return assignmentId;
	}

	@SuppressWarnings("unchecked")
	public long checkStudentAssignmentStatus(long assignmentId, long studentId) {
		List<StudentAssignmentStatus> list = (List<StudentAssignmentStatus>) getHibernateTemplate().find("From StudentAssignmentStatus where "
				+ "assignment.assignmentId="+assignmentId+
				" and student.studentId="+studentId);
		if(!list.isEmpty()){
			return list.get(0).getStudentAssignmentId();
		}
		else
		return 0;
	}

	@Override
	public Assignment getAssignment(long assignmentId) {
		Assignment assignment = new Assignment();
		assignment = (Assignment) super.find(Assignment.class, assignmentId);
		return assignment;
	}

	@Override
	public Student getStudent(long studentId) {
		Student student = new Student();
		student = (Student) super.find(Student.class, studentId);
		return student;
	}

	@Override
	public Questions getQuestion(long quesId) {
		Questions question = new Questions();
		question = (Questions) super.find(Questions.class, quesId);
		return question;
	}

	@Override
	public StudentAssignmentStatus getStudentAssignmentStatus(
			long studentAssignmentId) {
		StudentAssignmentStatus studentassignmentstatus = new StudentAssignmentStatus();
		studentassignmentstatus = (StudentAssignmentStatus) super.find(
				StudentAssignmentStatus.class, studentAssignmentId);
		return studentassignmentstatus;
	}

	@Override
	public boolean assignAssessments(Assignment assignment,
			List<StudentAssignmentStatus> studentAssignmentStatusList,
			List<Questions> questionsList, long restestId) {
		Session session = sessionFactory.getCurrentSession();
		long studentAssignmentId = 0;
		long assignmentId = 0;
		try{
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId() == 20 || assignment.getAssignmentType().getAssignmentTypeId() == 19)
		assignment.setLesson(null);
			if(assignment.getAssignmentType().getAssignmentTypeId() == 8 
				&& assignment.getBenchmarkCategories().getIsFluencyTest() != null
				&& assignment.getBenchmarkCategories().getIsFluencyTest() == 1){
			assignmentId = checkAssignmentExists(assignment.getClassStatus().getCsId(), assignment.getBenchmarkCategories().getBenchmarkCategoryId());
		}
		if(assignmentId == 0){
			session.saveOrUpdate(assignment);
		}
		else {
			assignment.setAssignmentId(assignmentId);
		}
		List<ReadingTypes> readingTypes = null;
		List<GradingTypes> gradingTypes = null;		
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId() == 20){
			readingTypes = getReadingTypes(assignment.getAssignmentType().getAssignmentTypeId());			
		}
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId()==20){ 
			gradingTypes = getGradingTypes();	
			if(studentAssignmentStatusList.size() == 1){
				gradingTypes.remove(2); // Temporary code for removing peer grading if the test is assigned for a single student
			}
		}
		/*else if(assignment.getAssignmentType().getAssignmentTypeId() == 20){
			gradingTypes = new ArrayList<>();
			GradingTypes gradingTypes2 = new GradingTypes();
			gradingTypes2.setGradingTypesId(1);
			gradingTypes.add(gradingTypes2);
		}*/
		for (StudentAssignmentStatus studentAssignmentStatus : studentAssignmentStatusList) {			
			if(studentAssignmentStatus.getStudent() != null && (assignment.getAssignmentType().getAssignmentTypeId() == 8 && 
					assignment.getBenchmarkCategories().getIsFluencyTest() !=null && assignment.getBenchmarkCategories().getIsFluencyTest() == 1)){
				studentAssignmentId = checkStudentAssignmentStatus(assignment.getAssignmentId(),
						 studentAssignmentStatus.getStudent().getStudentId());
			}else if(studentAssignmentStatus.getPerformanceGroup() != null){
				studentAssignmentId = checkGroupAssignmentStatus(assignment.getAssignmentId(), studentAssignmentStatus.getPerformanceGroup().getPerformanceGroupId());					
			}
			if (studentAssignmentId == 0) {
				studentAssignmentStatus.setAssignment(assignment);
				session.saveOrUpdate(studentAssignmentStatus);
				studentAssignmentStatus.setRetestId(studentAssignmentStatus.getStudentAssignmentId());
				session.saveOrUpdate(studentAssignmentStatus);
				if (!assignment.getUsedFor().equals(WebKeys.LP_USED_FOR_RTI)
						&& restestId != 0) {
					List<StudentAssignmentStatus> retestObjects = getRetestObjects(restestId,studentAssignmentStatus.getStudent().getStudentId());
					if (!retestObjects.isEmpty()) {
						for(StudentAssignmentStatus studentAssStatus : retestObjects)
						{
							studentAssStatus.setRetestId(studentAssignmentStatus.getStudentAssignmentId());
							session.saveOrUpdate(studentAssStatus);
						}
					}
				}
				for (Questions question : questionsList) {
					AssignedPtasks assignedPtasks = new AssignedPtasks();
					AssignmentQuestions assignmentQuestion = new AssignmentQuestions();
					if(assignment.getAssignmentType().getAssignmentTypeId() == 13){
						assignedPtasks.setStudentAssignmentStatus(studentAssignmentStatus);
						assignedPtasks.setPerformanceTask(question);
						session.saveOrUpdate(assignedPtasks);
						if(studentAssignmentStatus.getPerformanceGroup() != null){
							List<PerformanceGroupStudents> perGroupStudents = Collections.emptyList(); 
							perGroupStudents = performanceTaskDAO.getPerformanceGroupStudents(studentAssignmentStatus
										.getPerformanceGroup().getPerformanceGroupId());						
							for(PerformanceGroupStudents pgs : perGroupStudents){
								GroupPerformanceTemp gpt = new GroupPerformanceTemp();
								GroupPerformanceTempId gptId = new GroupPerformanceTempId();
								gptId.setAssignedTaskId(assignedPtasks.getAssignedTaskId());
								gptId.setPerformanceGroupStudentsId(pgs.getPerformanceGroupStudentsId());
								gpt.setId(gptId);
								gpt.setAssignedPtasks(assignedPtasks);
								gpt.setPerformanceGroupStudents(pgs);
								session.saveOrUpdate(gpt);
							}
						}
					}else{
						assignmentQuestion.setStudentAssignmentStatus(studentAssignmentStatus);
						assignmentQuestion.setQuestions(question);
						session.saveOrUpdate(assignmentQuestion);
					}
					if(studentAssignmentStatus.getAssignment().getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId() == 20){
						for(GradingTypes gradingTypes2 : gradingTypes){
							for(ReadingTypes readingTypes2 : readingTypes){
								FluencyMarks fluencyMarks = new FluencyMarks();
								fluencyMarks.setAssignmentQuestions(assignmentQuestion);
								fluencyMarks.setReadingTypes(readingTypes2);
								fluencyMarks.setGradingTypes(gradingTypes2);						
								session.saveOrUpdate(fluencyMarks);										
							}
						}											
					}		
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<JacQuestionFile> getJacQuestions(Teacher teacher,
			String usedFor, long lessonId) {
		UserRegistration adminReg = commonDAO.getAdminByTeacher(teacher.getUserRegistration());
		List<JacQuestionFile> jacquestions = (List<JacQuestionFile>) getHibernateTemplate()
				.find("from JacQuestionFile where (userRegistration.regId="+ teacher.getUserRegistration().getRegId()
						+ " or userRegistration.regId="+ adminReg.getRegId()+") and usedFor='" + usedFor + "' and lesson.lessonId="
						+ lessonId + "");
		return jacquestions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Questions> getJacQuestionsByTitleId(long titleId) {
		List<Questions> jacquestions = (List<Questions>) getHibernateTemplate()
				.find("from Questions where jacTemplate.jacTemplateId="
						+ titleId + "");
		return jacquestions;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getPreviousTestDates(long csId,
			long assignmentTypeId, String assignFor) {
		List<Assignment> assignments = new ArrayList<Assignment>();
		try {
			assignments = (List<Assignment>) getHibernateTemplate().find(
					"from Assignment where classStatus.csId=" + csId
							+ " and assignmentType.assignmentTypeId="
							+ assignmentTypeId + " and " + "usedFor='"
							+ assignFor + "'");

		} catch (DataAccessException e) {
			logger.error("Error in getAssignmentsByassignFor() of AssessmentDAOImpl"
					+ e);
			e.printStackTrace();
			throw new DataException(
					"Error in getAssignmentsByassignFor() of AssessmentDAOImpl",
					e);
		}

		return assignments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudentAssignmentStatus getStudentAssignmentStatus(
			long assignmentId, long studentId) {
		StudentAssignmentStatus studentAssignmentStatus = null;
		try {
			List<StudentAssignmentStatus> list = (List<StudentAssignmentStatus>) getHibernateTemplate()
					.find("From StudentAssignmentStatus where assignment.assignmentId="
							+ assignmentId
							+ " and student.studentId= "
							+ studentId);
			if (!list.isEmpty()) {
				studentAssignmentStatus = list.get(0);
			}
		} catch (HibernateException e) {
			logger.error("Error in getAssignmentsByassignFor() of AssessmentDAOImpl"
					+ e);
		}
		return studentAssignmentStatus;
	}
	
	private long checkGroupAssignmentStatus(long assignmentId, long groupId) {
		String query = "select * from student_assignment_status where assignment_id="
				+ assignmentId + " and performance_group_id=" + groupId + "";
		long groupAassignmentId = 0;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			groupAassignmentId = rs.getLong("student_assignment_id");
		}
		return groupAassignmentId;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getRetestObjects(long assignmentId, long studentId) {
		List<StudentAssignmentStatus> studentAssignmentStatus = new ArrayList<StudentAssignmentStatus>();;
		try {
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From StudentAssignmentStatus where retestId in(select studentAssignmentId from StudentAssignmentStatus where assignment.assignmentId="								
							+ assignmentId+" and student.studentId="+studentId+")");
			studentAssignmentStatus = query.list();
		} catch (HibernateException e) {
			logger.error("Error in getRetestObjects() of AssessmentDAOImpl"
					+ e);
		}
		return studentAssignmentStatus;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getAssignmentByTitle(long csId, String title, String usedFor)
			throws SQLDataException {
		List<Assignment> assignments = new ArrayList<Assignment>();
		try {
			assignments = (List<Assignment>) getHibernateTemplate().find(
					"from Assignment where classStatus.csId=" + csId + " and title='"+ title+"' and usedFor='"+ usedFor+"' and assignStatus='"+WebKeys.ACTIVE+"'");
		} catch (DataAccessException e) {
			logger.error("Error in getAssignmentByTitle() of AssessmentDAOImpl"+ e);
			throw new DataException("Error in getAssignmentByTitle() of AssessmentDAOImpl",e);
		}
		return assignments;
	}
	@SuppressWarnings("unchecked")
	public long checkBenchmarkTitleExists(long benchmarkId, long studentId, long csId) {
		List<StudentAssignmentStatus> list = (List<StudentAssignmentStatus>) getHibernateTemplate().find("From StudentAssignmentStatus where "
				+ "assignment.benchmarkCategories.benchmarkCategoryId="+benchmarkId+
				" and assignment.classStatus.csId="+csId+" and student.studentId="+studentId+" and assignment.assignStatus='"+WebKeys.ACTIVE+"'");
		if(!list.isEmpty()){
			return 1;
			
		}
		else
		return 0;
	}
	
	@Override
	public StudentAssignmentStatus assignReadingFluencyLearningPracticeHomeWork(Assignment assignment,StudentAssignmentStatus studentAssignmentStatus) throws DataException{
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		long assignment_id = 0;
		System.out.println("title="+assignment.getTitle());
		if(assignment.getAssignmentId() ==  0){
			String query_for_assignment_id = "select assignment_id from assignment where cs_id= ? AND title = ? AND used_for= ?";
			SqlRowSet rs_for_assignment_id = jdbcTemplate.queryForRowSet(query_for_assignment_id, assignment.getClassStatus().getCsId(), assignment.getTitle(), assignment.getUsedFor());
			if (rs_for_assignment_id.next())
				assignment_id = rs_for_assignment_id.getLong(1);

			if(assignment_id > 0)
				assignment.setAssignmentId(assignment_id);
		}
		if(assignment != null){
			assignment.setLesson(null);
			session.saveOrUpdate(assignment);
			tx.commit();
			session.close();
		}
		
		long student_assignment_id = 0;
		String query_for_checkAssignmentId = "select student_assignment_id from student_assignment_status where student_id= "+studentAssignmentStatus.getStudent().getStudentId()+" AND assignment_id= "+assignment.getAssignmentId();
		SqlRowSet rs_for_checkAssignmentId = jdbcTemplate.queryForRowSet(query_for_checkAssignmentId);
		 if (rs_for_checkAssignmentId.next()){
			 student_assignment_id = rs_for_checkAssignmentId.getLong(1);
			 studentAssignmentStatus.setStudentAssignmentId(student_assignment_id);
		 }
		 if(student_assignment_id == 0){
			Session session1 = sessionFactory.openSession();
			Transaction tx1 = (Transaction) session1.beginTransaction();
			studentAssignmentStatus.setAssignment(assignment);
			session1.saveOrUpdate(studentAssignmentStatus);
			tx1.commit();
			session1.close();
		}
		return studentAssignmentStatus;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean assignRFLPTest(RflpTest rflpTest){
		Session session = null;
		Transaction tx = null;
		try {
		List<RflpTest> list = (List<RflpTest>) getHibernateTemplate().find("From RflpTest where studentAssignmentStatus.studentAssignmentId="+rflpTest.getStudentAssignmentStatus().getStudentAssignmentId());	
		for (RflpTest rflp : list) {
			rflpTest.setRflpTestId(rflp.getRflpTestId());
			session = sessionFactory.openSession();
			tx = (Transaction) session.beginTransaction();
			List<RflpPractice> rflpPracticeLt = rflp.getRflpPractice();
			for (RflpPractice rflpPractice : rflpPracticeLt) {
				session.delete(rflpPractice);
			}
			tx.commit();
			session.close();
		}
		session = sessionFactory.openSession();
		tx = (Transaction) session.beginTransaction();
		session.saveOrUpdate(rflpTest);
		tx.commit();
		session.close();
		List<RflpPractice> rflpPracticeLt = rflpTest.getRflpPractice();
		session = sessionFactory.openSession();
		tx = (Transaction) session.beginTransaction();
		for (RflpPractice rflpPractice : rflpPracticeLt) {
			rflpPractice.setRflpTest(rflpTest);
			session.save(rflpPractice);
		}
		tx.commit();
		session.close();
		}catch(Exception e){
			logger.error("Error in assignRFLPTest() of AssessmentDAOImpl"+ e);
            return false;
		}
	return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentQuestions> getAssignmentQuestionsByStudentAssignmentId(long studentAssignmentId){
		List<AssignmentQuestions> assignmentQuestionsLt = new ArrayList<AssignmentQuestions>();
		if(studentAssignmentId > 0){
			assignmentQuestionsLt = (List<AssignmentQuestions>) getHibernateTemplate().find("From AssignmentQuestions where studentAssignmentStatus.studentAssignmentId="+studentAssignmentId);	
		if(assignmentQuestionsLt.size() > 0)
		   return assignmentQuestionsLt;
		}
		return assignmentQuestionsLt;
	}
	
	@SuppressWarnings("unchecked")
	private List<ReadingTypes> getReadingTypes(long assignmentTypeId){
		List<ReadingTypes> readingTypes = new ArrayList<ReadingTypes>();
		try{
			List<AsstypeReadtypeRelation> asstypeReadtypeRelations =  ((List<AsstypeReadtypeRelation>) 
					getHibernateTemplate().find("From AsstypeReadtypeRelation where assignmentType.assignmentTypeId="+assignmentTypeId));
			
			for(AsstypeReadtypeRelation asstypeReadtypeRelation : asstypeReadtypeRelations){
				readingTypes.add(asstypeReadtypeRelation.getReadingTypes());
			}
		}
		catch(Exception e){
			logger.error("Error in getReadingTypes() of AssessmentsDAOImpl"+e);
		}
		return readingTypes;
	}
	@SuppressWarnings("unchecked")
	
	private List<GradingTypes> getGradingTypes(){
		List<GradingTypes> gradingTypes = new ArrayList<GradingTypes>();
		try{
			gradingTypes = (List<GradingTypes>) getHibernateTemplate().find("From GradingTypes order by gradingTypesId");
		}
		catch(Exception e){
			logger.error("Error in getGradingTypes() of AssessmentsDAOImpl"+e);
		}
		return gradingTypes;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Assignment assignmentByTitle(String title, long assignmentTypeId, String usedFor) throws SQLDataException{
			List<Assignment> assignments = new ArrayList<Assignment>();
			try {
				Session session = getHibernateTemplate().getSessionFactory().openSession();
				Query query = session.createQuery("from Assignment where assignmentType.assignmentTypeId =:assignmentTypeId and title=:title and usedFor= :usedFor and assignStatus='"+WebKeys.ACTIVE+"'");
				query.setParameter("assignmentTypeId", assignmentTypeId);
				query.setParameter("title", title);
				query.setParameter("usedFor", usedFor);
				assignments = query.list();
			} catch (DataAccessException e) {
				logger.error("Error in getAssignmentByTitle() of AssessmentDAOImpl"+ e);
				throw new DataException("Error in getAssignmentByTitle() of AssessmentDAOImpl",e);
			}
			if(assignments.size() > 0)
				return assignments.get(0);
			else
				return new Assignment();	
		}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentTests(long csId) throws DataException{
		List<StudentAssignmentStatus> assignmentStatus = new ArrayList<StudentAssignmentStatus>();
		assignmentStatus = (List<StudentAssignmentStatus>) getHibernateTemplate().find("From StudentAssignmentStatus where assignment.assignStatus='"+WebKeys.ACTIVE+"'"
				+ " and assignment.assignmentType.assignmentTypeId=8 and assignment.classStatus.csId="+csId+" and peerGradedStatus!='graded'");
		return assignmentStatus;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassStatus> getClasses() throws DataException{
		List<ClassStatus> classStatus = new ArrayList<ClassStatus>();
		classStatus = (List<ClassStatus>) getHibernateTemplate().find("From ClassStatus where availStatus='"+WebKeys.AVAILABLE+"'");
		return classStatus;	
	}
	@Override
	public boolean saveStudentTests(List<StudentAssignmentStatus> status){
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction)  session.beginTransaction();
		for(StudentAssignmentStatus status2 : status){
			session.saveOrUpdate(status2);
		}
		tx.commit();
		session.close();
		return true;
	}
	@Override
	public boolean assignRTF(Assignment assignment,
			List<StudentAssignmentStatus> studentAssignmentStatusList,
			List<Questions> questionsList) {
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction)  session.beginTransaction();
		long studentAssignmentId = 0;
		long assignmentId = 0;
		try{
			
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId() == 20 || assignment.getAssignmentType().getAssignmentTypeId() == 19)
		assignment.setLesson(null);
			if(assignment.getAssignmentType().getAssignmentTypeId() == 8 
				&& assignment.getBenchmarkCategories().getIsFluencyTest() != null
				&& assignment.getBenchmarkCategories().getIsFluencyTest() == 1){
			assignmentId = checkAssignmentExists(assignment.getClassStatus().getCsId(), assignment.getBenchmarkCategories().getBenchmarkCategoryId());
		}
		if(assignmentId == 0){
			session.saveOrUpdate(assignment);
		}
		else {
			assignment.setAssignmentId(assignmentId);
		}
		List<ReadingTypes> readingTypes = null;
		List<GradingTypes> gradingTypes = null;		
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId() == 20){
			readingTypes = getReadingTypes(assignment.getAssignmentType().getAssignmentTypeId());			
		}
		if(assignment.getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId()==20){ 
			gradingTypes = getGradingTypes();	
			if(studentAssignmentStatusList.size() == 1){
				gradingTypes.remove(2); // Temporary code for removing peer grading if the test is assigned for a single student
			}
		}
		/*else if(assignment.getAssignmentType().getAssignmentTypeId() == 20){
			gradingTypes = new ArrayList<>();
			GradingTypes gradingTypes2 = new GradingTypes();
			gradingTypes2.setGradingTypesId(1);
			gradingTypes.add(gradingTypes2);
		}*/
		for (StudentAssignmentStatus studentAssignmentStatus : studentAssignmentStatusList) {			
			if(studentAssignmentStatus.getStudent() != null && (assignment.getAssignmentType().getAssignmentTypeId() == 8 && 
					assignment.getBenchmarkCategories().getIsFluencyTest() !=null && assignment.getBenchmarkCategories().getIsFluencyTest() == 1)){
				studentAssignmentId = checkStudentAssignmentStatus(assignment.getAssignmentId(),
						 studentAssignmentStatus.getStudent().getStudentId());
			}else if(studentAssignmentStatus.getPerformanceGroup() != null){
				studentAssignmentId = checkGroupAssignmentStatus(assignment.getAssignmentId(), studentAssignmentStatus.getPerformanceGroup().getPerformanceGroupId());					
			}
			if (studentAssignmentId == 0) {
				studentAssignmentStatus.setAssignment(assignment);
				session.saveOrUpdate(studentAssignmentStatus);
				session.saveOrUpdate(studentAssignmentStatus);
				
				for (Questions question : questionsList) {
					AssignmentQuestions assignmentQuestion = new AssignmentQuestions();
					assignmentQuestion.setStudentAssignmentStatus(studentAssignmentStatus);
					assignmentQuestion.setQuestions(question);
					session.saveOrUpdate(assignmentQuestion);
					
					if(studentAssignmentStatus.getAssignment().getAssignmentType().getAssignmentTypeId() == 8 || assignment.getAssignmentType().getAssignmentTypeId() == 20){
						for(GradingTypes gradingTypes2 : gradingTypes){
							for(ReadingTypes readingTypes2 : readingTypes){
								FluencyMarks fluencyMarks = new FluencyMarks();
								fluencyMarks.setAssignmentQuestions(assignmentQuestion);
								fluencyMarks.setReadingTypes(readingTypes2);
								fluencyMarks.setGradingTypes(gradingTypes2);						
								session.saveOrUpdate(fluencyMarks);										
							}
						}											
					}		
				}
			}
		}
		tx.commit();
		session.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public long checkBenchmarkTitleExistsForSection(long benchmarkId, long csId) {
		List<StudentAssignmentStatus> list = (List<StudentAssignmentStatus>) getHibernateTemplate().find("From StudentAssignmentStatus where "
				+ "assignment.benchmarkCategories.benchmarkCategoryId="+benchmarkId+
				" and assignment.classStatus.csId="+csId+" and assignment.assignStatus='"+WebKeys.ACTIVE+"'");
		if(!list.isEmpty()){
			return 1;
			
		}
		else
		return 0;
	}
}
