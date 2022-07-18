package com.lp.teacher.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lp.model.AcademicGrades;
import com.lp.model.Assignment;
import com.lp.model.GameLevel;
import com.lp.model.MathConversionTypes;
import com.lp.model.MathGameScores;
import com.lp.model.MathGear;
import com.lp.model.MathQuiz;
import com.lp.model.MathQuizQuestions;
import com.lp.model.Student;
import com.lp.model.StudentAssignmentStatus;
import com.lp.model.StudentMathAssessMarks;
import com.lp.teacher.service.GradeBookService;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("mathAssessmentDAO")
public class MathAssessmentDAOImpl extends CustomHibernateDaoSupport implements MathAssessmentDAO {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;	
	@Autowired
	private GradeBookService gradeBookService;
	@Autowired
	private GradeAssessmentsDAO gradeAssessmentsDAO;
	@Autowired
	private CommonDAO commonDAO;
	@Autowired
	private HttpSession httpSession;

	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@Override
      public String saveQuizQuestion(String fraction, long csId, long quizId, String mode, ArrayList<String> answersArray,ArrayList<String> blankArray){
		    String status = "";
	    	Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			MathQuiz mathQuiz = new MathQuiz();
				mathQuiz.setMathQuizId(quizId);
			mathQuiz.setFraction(fraction);
			mathQuiz.setCsId(csId);
			mathQuiz.setStatus(WebKeys.ACTIVE);
			if(mathQuiz != null){
				if(mode.equalsIgnoreCase("edit")){
					MathQuizQuestions  mathQuizQuestion = null;
					List<MathQuizQuestions> mathQuizQuestionsLt = getMathQuizQuestionsByQuizId(quizId);
					for (MathQuizQuestions mathQuizQuestions : mathQuizQuestionsLt) {
						if(mathQuizQuestions.getIsBlank().equalsIgnoreCase(WebKeys.LP_TRUE)){
							mathQuizQuestion = mathQuizQuestions;
							break;
						}
					}
					
					if(mathQuizQuestion != null){
						long quiz_questions_id = 0;
						String query_for_quiz_questions_id = "select quiz_question_id from student_math_assess_marks where quiz_question_id="+mathQuizQuestion.getQuizQuestionsId();
						SqlRowSet rs_for_quiz_questions_id = jdbcTemplate.queryForRowSet(query_for_quiz_questions_id);
						 if (rs_for_quiz_questions_id.next()) {
							 quiz_questions_id = rs_for_quiz_questions_id.getLong(1);
						 }
						 if(quiz_questions_id > 0){
							 status = WebKeys.TEST_ALREDAY_ASSGINED;
							 session.close();
							 return status;
						 }
					}
				}else if(mode.equalsIgnoreCase("create")){
					session.saveOrUpdate(mathQuiz);
					tx.commit();
					session.close();
				}
				status = saveQuestions(mode, mathQuiz, answersArray, blankArray);
			}
		return status;
	}
	
	public String saveQuestions(String mode, MathQuiz mathQuiz, ArrayList<String> answersArray,ArrayList<String> blankArray){
		String status = "";
		boolean token = false;
    	Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try{
			if(mode.equalsIgnoreCase("edit")){
				List<MathQuizQuestions> mathQuizQuestionsLt = getMathQuizQuestionsByQuizId(mathQuiz.getMathQuizId());
				for (MathQuizQuestions mathQuizQuestion : mathQuizQuestionsLt) {
					String conType = "";
					String actualAns = "";
					String conversionType = mathQuizQuestion.getMathConversionTypes().getConversionType().replaceAll("\\s","");
					for (String answer : answersArray) {
						if(answer.contains(":")){
							String[] parts = answer.split(":");
							conType = parts[0];
							actualAns =  parts[1];
						}else{
							conType = answer;
							actualAns = "";
						}
						if(conType.equalsIgnoreCase(conversionType)){
							token = true;
							break;
						}
					}
					if(token){
						mathQuizQuestion.setActualAnswer(actualAns);
						if(blankArray.indexOf(conType) > -1)
							mathQuizQuestion.setIsBlank(WebKeys.LP_TRUE);
						else
							mathQuizQuestion.setIsBlank(WebKeys.LP_FALSE);
						session.saveOrUpdate(mathQuizQuestion);
						token = false;
					}
				}
			}else if(mode.equalsIgnoreCase("create")){	
				 List<MathConversionTypes> mathConversionTypesLt = getMathConversionTypes();
					for (MathConversionTypes mathConversionTypes : mathConversionTypesLt) {
						String conType = "";
						String actualAns = "";
						for (String answer : answersArray) {
							if(answer.contains(":")){
								String[] parts = answer.split(":");
								conType = parts[0];
								actualAns =  parts[1];
							}else{
								conType = answer;
								actualAns = "";
							}
							String conversionType = mathConversionTypes.getConversionType().replaceAll("\\s","");
							if(conType.equalsIgnoreCase(conversionType)){
								token = true;
								break;
							}
						}
						if(token){
							MathQuizQuestions mathQuizQuestions = new MathQuizQuestions();
							mathQuizQuestions.setMathQuiz(mathQuiz);
							mathQuizQuestions.setMathConversionTypes(mathConversionTypes);
							mathQuizQuestions.setActualAnswer(actualAns);
							if(blankArray.indexOf(conType) > -1)
								mathQuizQuestions.setIsBlank(WebKeys.LP_TRUE);
							else
								mathQuizQuestions.setIsBlank(WebKeys.LP_FALSE);
							session.saveOrUpdate(mathQuizQuestions);
							token = false;
						}
					}
			    }

			tx.commit();
		    status = WebKeys.SUCCESS;
			}catch(Exception e){
				status = WebKeys.LP_FAILED;
			}finally{
				session.close();
			} 
		return status;
	}
	@SuppressWarnings("unchecked")
	public List<MathConversionTypes> getMathConversionTypes(){
		List<MathConversionTypes> mathConversionTypesLt = new ArrayList<MathConversionTypes>();
		try {
			mathConversionTypesLt = (List<MathConversionTypes>) getHibernateTemplate().find(
					"from MathConversionTypes where status='"+WebKeys.ACTIVE+"'");
		} catch (DataAccessException e) {
			logger.error("Error in getMathConversionTypes() of MathAssessmentDAOImpl"+ e);
		}
		return mathConversionTypesLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MathQuiz> getMathQuizByCsId(long csId){
		List<MathQuiz> mathQuizQuestionsLt = new ArrayList<MathQuiz>();
		try {
			mathQuizQuestionsLt = (List<MathQuiz>) getHibernateTemplate().find(
					"from MathQuiz where csId ="+csId+" and status='"+WebKeys.ACTIVE+"' ORDER BY csId DESC");
		} catch (DataAccessException e) {
			logger.error("Error in getMathQuizQuestions() of MathAssessmentDAOImpl"+ e);
		}
		return mathQuizQuestionsLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MathQuiz> getAllMathQuizs(long csId){
		List<MathQuiz> mathQuizQuestionsLt = new ArrayList<MathQuiz>();
		try {
			mathQuizQuestionsLt = (List<MathQuiz>) getHibernateTemplate().find(
					"from MathQuiz where (csId ="+csId+" OR csId = 0) and status='"+WebKeys.ACTIVE+"' ORDER BY csId DESC");
		} catch (DataAccessException e) {
			logger.error("Error in getAllMathQuizs() of MathAssessmentDAOImpl"+ e);
		}
		return mathQuizQuestionsLt;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public MathQuiz getMathQuizByQuizId(long mathQuizId){
		List<MathQuiz> mathQuizQuestionsLt = new ArrayList<MathQuiz>();
		try {
			mathQuizQuestionsLt = (List<MathQuiz>) getHibernateTemplate().find(
					"from MathQuiz where mathQuizId ="+mathQuizId+" and status='"+WebKeys.ACTIVE+"'");
		} catch (DataAccessException e) {
			logger.error("Error in getMathQuizQuestions() of MathAssessmentDAOImpl"+ e);
		}
		return mathQuizQuestionsLt.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MathQuizQuestions> getMathQuizQuestionsByQuizId(long mathQuizId){
		List<MathQuizQuestions> mathQuizQuestionsLt = new ArrayList<MathQuizQuestions>();
		try {
			mathQuizQuestionsLt = (List<MathQuizQuestions>) getHibernateTemplate().find(
					"from MathQuizQuestions where mathQuiz.mathQuizId ="+mathQuizId +" order by quizQuestionsId");//mathConversionTypes.conversionTypeId
		} catch (DataAccessException e) {
			logger.error("Error in getMathQuizQuestionsByQuizId() of MathAssessmentDAOImpl"+ e);
		}
		return mathQuizQuestionsLt;
	}
	
	@Override
	public String deleteMathQuizQuestion(long mathQuizId){
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		String status = null;
		try {
			MathQuizQuestions  mathQuizQuestion = null;
			List<MathQuizQuestions> mathQuizQuestionsLt = getMathQuizQuestionsByQuizId(mathQuizId);
			for (MathQuizQuestions mathQuizQuestions : mathQuizQuestionsLt) {
				if(mathQuizQuestions.getIsBlank().equalsIgnoreCase(WebKeys.LP_TRUE)){
					mathQuizQuestion = mathQuizQuestions;
					break;
				}
			}
			
			if(mathQuizQuestion != null){
				long quiz_questions_id = 0;
				String query_for_quiz_questions_id = "select quiz_question_id from student_math_assess_marks where quiz_question_id="+mathQuizQuestion.getQuizQuestionsId();
				SqlRowSet rs_for_quiz_questions_id = jdbcTemplate.queryForRowSet(query_for_quiz_questions_id);
				 if (rs_for_quiz_questions_id.next()) {
					 quiz_questions_id = rs_for_quiz_questions_id.getLong(1);
				 }
				 if(quiz_questions_id > 0){
					 status = WebKeys.TEST_ALREDAY_ASSGINED;
					 session.close();
					 return status;
				 }else{
					 for(MathQuizQuestions mathQuizQuestions : mathQuizQuestionsLt) {
						session.delete(mathQuizQuestions);
					 }
				 }
			}
			MathQuiz  mathQuiz =  new MathQuiz();
			mathQuiz.setMathQuizId(mathQuizId);
			session.delete(mathQuiz);
			tx.commit();
			session.close();
			 status = WebKeys.SUCCESS;
		} catch (DataAccessException e) {
			logger.error("Error in deleteMathQuizQuestions() of MathAssessmentDAOImpl"+ e);
			status = WebKeys.LP_FAILED;
		}
		 return status;
	}
	
	@Override
	public String assignQuizTest(Assignment assignment,ArrayList<Long> quizIdArray,ArrayList<Long> students){
		long assignment_id = 0;
		String status = "";
    	Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try {
			String query_for_assignment_id = "select assignment_id from assignment where cs_id="+assignment.getClassStatus().getCsId()+" AND title = '"+assignment.getTitle()+"' AND used_for= '"+assignment.getUsedFor()+"'";
			SqlRowSet rs_for_assignment_id = jdbcTemplate.queryForRowSet(query_for_assignment_id);
			if (rs_for_assignment_id.next()) {
				 assignment_id = rs_for_assignment_id.getLong(1);
			 }
		
			if(assignment_id > 0){ 
				 status = WebKeys.TEST_ALREDAY_ASSGINED;
		    }else{
				session.saveOrUpdate(assignment);
				for (Long studentId : students) {
					StudentAssignmentStatus stdAssignmentStatus = new StudentAssignmentStatus();
					stdAssignmentStatus.setAssignment(assignment);
					Student student = gradeBookService.getStudentById(studentId); 
					stdAssignmentStatus.setStudent(student);
					stdAssignmentStatus.setGradedStatus(WebKeys.GRADED_STATUS_NOTGRADED);
					stdAssignmentStatus.setStatus(WebKeys.TEST_STATUS_PENDING);
					session.saveOrUpdate(stdAssignmentStatus);
					if(stdAssignmentStatus.getStudentAssignmentId() > 0){
						for (Long quizId : quizIdArray) {
							List<MathQuizQuestions> mathQuizQuestionsLt = getMathQuizQuestionsByQuizId(quizId);
							for (MathQuizQuestions mathQuizQuestions : mathQuizQuestionsLt) {
								if(mathQuizQuestions.getIsBlank().equalsIgnoreCase(WebKeys.LP_TRUE)){
									StudentMathAssessMarks studentMathAssessMarks = new StudentMathAssessMarks();
									studentMathAssessMarks.setMathQuizQuestions(mathQuizQuestions);
									studentMathAssessMarks.setStudentAssignmentStatus(stdAssignmentStatus);
									session.saveOrUpdate(studentMathAssessMarks);
								}
							}
						}
					}
				}
				status = WebKeys.ASSIGNED_SUCCESSFULLY;
		    }
		} catch (DataAccessException e) {
			logger.error("Error in assignQuizTest() of MathAssessmentDAOImpl"+ e);
			status = WebKeys.FAILED_TO_ASSIGNED;
		} finally{
			tx.commit();
			session.close();
		}
		return status;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentMathAssessMarks> getStudentMathAssessMarksByStudentAssignmentId(long studentAssignmentId,long quizId){
		List<StudentMathAssessMarks> studentMathAssessMarksLt = new ArrayList<StudentMathAssessMarks>();
		try {
			studentMathAssessMarksLt = (List<StudentMathAssessMarks>) getHibernateTemplate().find("from StudentMathAssessMarks where studentAssignmentStatus.studentAssignmentId ="+studentAssignmentId+" and mathQuizQuestions.mathQuiz.mathQuizId="+quizId+" ORDER BY mathQuizQuestions.quizQuestionsId ASC");
		} catch (DataAccessException e) {
			logger.error("Error in getStudentMathAssessMarksByStudentAssignmentId() of MathAssessmentDAOImpl"+ e);
		}
		return studentMathAssessMarksLt;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public String udpateQuestionMark(long studentMathAssessMarksId, int mark, int correct, int wrong, long studentAssignmentId){
		String status = null;
		try {
			Session session = getSessionFactory().getCurrentSession();
			String hql1 = "UPDATE StudentMathAssessMarks set mark = :mark"
					+ " WHERE studentMathAssessMarksId=:studentMathAssessMarksId";
			Query query1 = session.createQuery(hql1);	
			query1.setParameter("mark", mark);
			query1.setParameter("studentMathAssessMarksId", studentMathAssessMarksId);
			query1.executeUpdate();				
			float percentage = Float.valueOf(correct*100/(correct+wrong));
			AcademicGrades academicGrades = gradeAssessmentsDAO.getAcademicGradeByPercentage(percentage);
			String hql2 = "UPDATE StudentAssignmentStatus set percentage = :percentage, academicGrade.acedamicGradeId= :acedamicGradeId"
					+ " WHERE studentAssignmentId=:studentAssignmentId";
			Query query2 = session.createQuery(hql2);	
			query2.setParameter("percentage", percentage);
			query2.setParameter("acedamicGradeId", academicGrades.getAcedamicGradeId());
			query2.setParameter("studentAssignmentId", studentAssignmentId);					
			query2.executeUpdate();
			httpSession.setAttribute("percentage", percentage);
			httpSession.setAttribute("acedamicGrade", academicGrades.getAcedamicGradeName());
			status = WebKeys.SUCCESS; 
		} catch (DataAccessException e) {
			logger.error("Error in udpateQuestionMark() of MathAssessmentDAOImpl"+ e);
			status = WebKeys.LP_FAILED;
		}
		return status;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentAssessmentTests(long assignmentId, long assignmentTypeId){
		List<StudentAssignmentStatus> assignmentList = null;
		try {			
			assignmentList = (List<StudentAssignmentStatus>) getHibernateTemplate().find(
					"from StudentAssignmentStatus where assignment.assignStatus='"+WebKeys.ACTIVE+"'"
							+" and assignment.assignmentId="+assignmentId+" and assignment.assignmentType.assignmentTypeId="+assignmentTypeId+" and performanceGroup.performanceGroupId = Null and student.gradeStatus='"+WebKeys.ACTIVE+"'");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return assignmentList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getMathAssignedDates(long csId,String usedFor,String assignmentType){
		List<Assignment> assignmentDates = null;	
		try{
		     assignmentDates =(List<Assignment>) getHibernateTemplate().find("from Assignment where classStatus.csId="+csId+" and "
		 		+ "usedFor='"+usedFor+"' and assignmentType.assignmentType='"+assignmentType+"' and assignStatus='"+WebKeys.ACTIVE+"' group by dateAssigned");
				} catch (HibernateException ex) {
				ex.printStackTrace();
		 }
		return assignmentDates;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentAssessmentTestsByPercentage(long assignmentId, long assignmentTypeId){
		List<StudentAssignmentStatus> studentAssignmentStatusLt = null;
		try {
			studentAssignmentStatusLt = (List<StudentAssignmentStatus>) getHibernateTemplate().find(
					"from StudentAssignmentStatus where assignment.assignStatus='"+WebKeys.ACTIVE+"'"
							+" and assignment.assignmentId="+assignmentId+" and assignment.assignmentType.assignmentTypeId="+assignmentTypeId+" and performanceGroup.performanceGroupId = Null and student.gradeStatus='"+WebKeys.ACTIVE+"' and percentage != null order by percentage DESC");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return studentAssignmentStatusLt;
	}
	@Override
	public List<MathQuiz> getMathQuizsByAssignmentId(long assignmentId){
		List<MathQuiz> MathQuizLt = new ArrayList<MathQuiz>();
		try {
			Assignment assignment = commonDAO.getAssignmentByAssignmentId(assignmentId);
			String quizIds = assignment.getMathQuizIds();
			String[] quizIdsArr = quizIds.split(":");
			for (String quizIdStr : quizIdsArr) {
				long quizId = Long.parseLong(quizIdStr);
				MathQuiz mathQuiz = getMathQuizByQuizId(quizId);
				MathQuizLt.add(mathQuiz);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return MathQuizLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentMathAssessMarks> getStudentMathAssessMarksByQuizId(long quizId,long assignmentId){
		List<StudentMathAssessMarks> studentMathAssessMarksLt = new ArrayList<StudentMathAssessMarks>();
		try {
			studentMathAssessMarksLt = (List<StudentMathAssessMarks>) getHibernateTemplate().find("from StudentMathAssessMarks where mathQuizQuestions.mathQuiz.mathQuizId="+quizId+" and studentAssignmentStatus.assignment.assignmentId="+assignmentId+" and studentAssignmentStatus.status='"+WebKeys.TEST_STATUS_SUBMITTED+"' and studentAssignmentStatus.gradedStatus='"+WebKeys.GRADED_STATUS_GRADED+"' ORDER BY studentAssignmentStatus.student.studentId ASC");
		} catch (DataAccessException e) {
			logger.error("Error in getStudentMathAssessMarksByQuizId() of MathAssessmentDAOImpl"+ e);
		}
		return studentMathAssessMarksLt;
	}

	@Override
	public boolean assignGameToStudents(List<StudentAssignmentStatus> studentAssignmentList,
			Assignment assignment, List<MathGameScores> gameScores) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();	
			session.saveOrUpdate(assignment);
			for(StudentAssignmentStatus sas: studentAssignmentList){
				session.saveOrUpdate(sas);
			}
			for(MathGameScores mathGameScore: gameScores){
				session.saveOrUpdate(mathGameScore);
			}
			tx.commit();
			session.close();
			return  true;
		} catch (HibernateException e) {
			logger.error("Error in assignGameToStudents() of MathAssessmentDAOImpl" + e);
			return false;
		}
	}
	
	public boolean assignGameByAdmin(List<StudentAssignmentStatus> studentAssignmentList, List<Assignment> assignmentLt, List<MathGameScores> gameScores) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();	
			for(Assignment assignment: assignmentLt){
				session.saveOrUpdate(assignment);
			}
			for(StudentAssignmentStatus sas: studentAssignmentList){
				session.saveOrUpdate(sas);
			}
			for(MathGameScores mathGameScore: gameScores){
				session.saveOrUpdate(mathGameScore);
			}
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			logger.error("Error in assignGameByAdmin() of MathAssessmentDAOImpl" + e);
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<GameLevel> getGameLevels() {
		List<GameLevel> gameLevels = new ArrayList<GameLevel>();
		try {
			gameLevels = (List<GameLevel>) getHibernateTemplate().find("from GameLevel");
		} catch (DataAccessException e) {
			logger.error("Error in getGameLevels() of MathAssessmentDAOImpl"+ e);
		}
		return gameLevels;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MathGear> getMathGears() {
		List<MathGear> mathGears = new ArrayList<MathGear>();
		try {
			mathGears = (List<MathGear>) getHibernateTemplate().find("from MathGear");
		} catch (DataAccessException e) {
			logger.error("Error in getMathGear() of MathAssessmentDAOImpl"+ e);
		}
		return mathGears;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentAssignmentStatus> getStudentMathGameAssessmentTests(long assignmentId, long assignmentTypeId){
		List<StudentAssignmentStatus> studentAssignmentStatusLt = null;
		try {
			studentAssignmentStatusLt = (List<StudentAssignmentStatus>) getHibernateTemplate().find(
					"from StudentAssignmentStatus where assignment.assignStatus='"+WebKeys.ACTIVE+"'"
							+" and assignment.assignmentId="+assignmentId+" and assignment.assignmentType.assignmentTypeId="+assignmentTypeId+" and performanceGroup.performanceGroupId = Null and student.gradeStatus='"+WebKeys.ACTIVE+"'");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return studentAssignmentStatusLt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MathGameScores> getStudentMathGameScores(long assignmentId){
		List<MathGameScores> studMathGameScoresLst = null;
		try {
			studMathGameScoresLst = (List<MathGameScores>) getHibernateTemplate().find(
					"from MathGameScores where studentAssignmentStatus.assignment.assignmentId="+assignmentId+" order by mathGear.mathGearId, gameLevel.gameLevelId");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return studMathGameScoresLst;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MathGameScores> getStudentMathGameDetails(long studentAssignmentId){
		List<MathGameScores> studMathGameScoresLst = null;
		try {
			studMathGameScoresLst = (List<MathGameScores>) getHibernateTemplate().find(
					"from MathGameScores where studentAssignmentStatus.studentAssignmentId="+studentAssignmentId+" order by gameLevel.gameLevelId");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return studMathGameScoresLst;
	}
}
