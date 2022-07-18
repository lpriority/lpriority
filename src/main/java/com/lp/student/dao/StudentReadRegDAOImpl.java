package com.lp.student.dao;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.model.AcademicYear;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.ReadRegAnswers;
import com.lp.model.ReadRegMaster;
import com.lp.model.ReadRegPageRange;
import com.lp.model.ReadRegQuestions;
import com.lp.model.ReadRegQuiz;
import com.lp.model.ReadRegReview;
import com.lp.model.ReadRegRubric;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("studentReadRegDAO")
public class StudentReadRegDAOImpl extends CustomHibernateDaoSupport implements StudentReadRegDAO {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	static final Logger logger = Logger.getLogger(StudentReadRegDAOImpl.class);
	
	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	
	@Override
	public String saveOrUpdateBook(ReadRegMaster readRegMaster, ReadRegReview readRegReview){
		try {
			/*int check = 0;
			if(readRegMaster.getTitleId() == 0){
				String query = "select title_id from read_reg_master where book_title=?";				
				SqlRowSet rs = jdbcTemplate.queryForRowSet(query,readRegMaster.getBookTitle());
				while (rs.next()) 
				 check = rs.getInt(1);
			}*/
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			/*if(check == 0)
			{*/
				ReadRegPageRange pageRange = getPageRange(readRegMaster.getNumberOfPages());
				readRegMaster.setReadRegPageRange(pageRange);
				readRegMaster.setBookTitle(readRegMaster.getBookTitle().trim().replaceAll(" +", " "));
				readRegMaster.setAuthor(readRegMaster.getAuthor().trim().replaceAll(" +", " "));
				session.saveOrUpdate(readRegMaster);				
			//}
			/*else{
				readRegMaster.setTitleId(check);
			}	*/	
			readRegReview.setReadRegMaster(readRegMaster);
			session.saveOrUpdate(readRegReview);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveOrUpdateBook() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();
			return WebKeys.ERROR_OCCURED;
		}
		return WebKeys.SUCCESS;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegMaster> getAllAddedBooks(long masterGradesId){
		long startTime = System.currentTimeMillis();
		List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
		try {
			if(masterGradesId > 8 && masterGradesId != 13)
				readRegMasterLt = (List<ReadRegMaster>)getHibernateTemplate().find("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and grade.masterGrades.masterGradesId between 9 and 12"
						+ " order by createDate " );
			else
				readRegMasterLt = (List<ReadRegMaster>)getHibernateTemplate().find("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and "
						+ "(grade.masterGrades.masterGradesId <= 8 or grade.masterGrades.masterGradesId=13) order by createDate " );
		} catch (HibernateException e) {
			logger.error("Error in getAllAddedBooks() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		logger.info("Time taken by getAllAddedBooks of StudentReadRegDAOImpl"+elapsedTime);
		return readRegMasterLt;
	}
	@Override	
	public long getBookCount(long masterGradesId, String bookName) {
		long bookCount = 0;
		Query query = null;
		try {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			if(masterGradesId > 8 && masterGradesId != 13)
				query =  session.createQuery("Select count(*) from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' "
						+ " and bookTitle like :bookTitle and grade.masterGrades.masterGradesId between 9 and 12"
						+ " order by createDate ");
			else
				query = session.createQuery("Select count(*) from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and "
						+ "(grade.masterGrades.masterGradesId <= 8 or grade.masterGrades.masterGradesId=13) and bookTitle like :bookTitle order by createDate " );
			query.setParameter("bookTitle", "%"+bookName+"%");
			bookCount = (long) query.uniqueResult();
			
		} catch (HibernateException e) {
			logger.error("Error in getAllAddedBooks() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return bookCount;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegMaster getBookDetails(long bookId){
		List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
		ReadRegMaster readRegMaster = null;
		try {
			readRegMasterLt = (List<ReadRegMaster>)getHibernateTemplate().find("from ReadRegMaster where  titleId= "+bookId );
		} catch (HibernateException e) {
			logger.error("Error in getStudentBooks() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		if(!readRegMasterLt.isEmpty())
			readRegMaster= readRegMasterLt.get(0);
		return readRegMaster;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegMaster> getStudentBooks(long regId){
		List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
		try {
			readRegMasterLt = (List<ReadRegMaster>)getHibernateTemplate().find("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and "
						+ " userRegistration.regId="+regId );
		} catch (HibernateException e) {
			logger.error("Error in getStudentBooks() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegMasterLt;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegMaster getReadRegMasterByTitleId(long titleId){
		List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
		try {
			readRegMasterLt = (List<ReadRegMaster>)getHibernateTemplate().find("from ReadRegMaster where titleId="+titleId);
		} catch (HibernateException e) {
			logger.error("Error in getAllAddedBooks() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegMasterLt.get(0);
	}
	
	@Override
	public String saveOrUpdateReview(ReadRegReview readRegReview, ReadRegActivityScore activityScore){
		try {	
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(readRegReview);
			activityScore.setReadRegReview(readRegReview);
			session.saveOrUpdate(activityScore);
			tx.commit();
			session.close();
		} catch (Exception e) {
			logger.error("Error in saveOrUpdateReview() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();
			return WebKeys.ERROR_OCCURED;
		}
		return WebKeys.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegReview getReadRegReviewByReviewId(long reviewId){
		List<ReadRegReview> readRegReviewLt = new ArrayList<ReadRegReview> ();
		try {
			readRegReviewLt = (List<ReadRegReview>)getHibernateTemplate().find("from ReadRegReview where reviewId="+reviewId);
		} catch (HibernateException e) {
			logger.error("Error in getReadRegReviewByReviewId() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegReviewLt.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegReview getReadRegReviewByStudentId(long titleId, long studentId){
		List<ReadRegReview> readRegReviewLt = new ArrayList<ReadRegReview> ();
		try {
			readRegReviewLt = (List<ReadRegReview>)getHibernateTemplate().find("from ReadRegReview where readRegMaster.titleId="+titleId+" and student.studentId="+studentId);
		} catch (HibernateException e) {
			logger.error("Error in getReadRegReviewByReviewId() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		if(!readRegReviewLt.isEmpty())
			return readRegReviewLt.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegQuestions getStudentQuestion(long titleId, long studentId, long quetionNum){
		List<ReadRegQuestions> readRegQuestionsLt = new ArrayList<ReadRegQuestions> ();
		ReadRegQuestions reQuestion = new ReadRegQuestions();
		try {
			readRegQuestionsLt = (List<ReadRegQuestions>)getHibernateTemplate().find("from ReadRegQuestions where readRegMaster.titleId="+titleId+" and student.studentId="+studentId
					+" and readRegQuiz.questionNum="+quetionNum);
		} catch (HibernateException e) {
			logger.error("Error in getStudentAllCreatedQuestionsList() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		if(!readRegQuestionsLt.isEmpty()){
			reQuestion = readRegQuestionsLt.get(0);
		}
		return reQuestion;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegQuiz> getAllQuizQuestionList(){
		List<ReadRegQuiz> readRegQuizLt = new ArrayList<ReadRegQuiz> ();
		try {
			readRegQuizLt = (List<ReadRegQuiz>)getHibernateTemplate().find("from ReadRegQuiz");
		} catch (HibernateException e) {
			logger.error("Error in getAllQuizQuestionList() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegQuizLt;
	}
	
	@Override
	public String saveCreateQuestions(ReadRegQuestions regQuestions, ReadRegActivityScore activityScore){
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try {	
			if(activityScore != null){
				session.saveOrUpdate(activityScore);
				if(activityScore.getReadRegReview()!=null)
					session.saveOrUpdate(activityScore.getReadRegReview());
			}
			session.saveOrUpdate(regQuestions);
			
		} catch (Exception e) {
			logger.error("Error in saveCreateQuestions() of StudentReadRegDAOImpl" + e);
			return WebKeys.ERROR_OCCURED;
		}
		tx.commit();
		session.close();
		return WebKeys.SUCCESS;
	}
	
	public String updateReadRegQuestion(ReadRegQuestions readRegQuestions){
		try {	
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(readRegQuestions);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in updateReadRegQuestion() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();
			return WebKeys.ERROR_OCCURED;
		}
		return WebKeys.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getAllQuizQuestionsGroupByTitleId(long titleId, long studentId){
		List<ReadRegActivityScore> readRegActivitysLt = new ArrayList<ReadRegActivityScore> ();
		List<Student> studentLt = new ArrayList<Student>();
		try {
			readRegActivitysLt = (List<ReadRegActivityScore>)getHibernateTemplate().find("from ReadRegActivityScore where readRegMaster.titleId="+titleId+" "
					+ "and student.studentId !="+studentId+" and readRegActivity.activityId=2 and approveStatus='"+WebKeys.LP_APPROVED+"'");		
			for (ReadRegActivityScore readRegActivityScore : readRegActivitysLt) {
				studentLt.add(readRegActivityScore.getStudent());
			}
		} catch (HibernateException e) {
			logger.error("Error in getAllQuizQuestionsGroupByTitleId() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return studentLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegQuestions> getStudentAllQuizQuestionByTitleId(long titleId, long studentId){
		List<ReadRegQuestions> readRegQuestionsLt = new ArrayList<ReadRegQuestions> ();
		try {
			readRegQuestionsLt = (List<ReadRegQuestions>)getHibernateTemplate().find("from ReadRegQuestions where readRegMaster.titleId="+titleId+" and student.studentId="+studentId+" order by readRegMaster.titleId, student.studentId");
		} catch (HibernateException e) {
			logger.error("Error in getStudentAllQuizQuestionByTitleId() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegQuestionsLt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegAnswers> getStudentAnswerByTitleId(long titleId, long currentStudentId, long createdStudentId){
		List<ReadRegAnswers> readRegAnswersLt = new ArrayList<ReadRegAnswers> ();
		try { 
			readRegAnswersLt = (List<ReadRegAnswers>)getHibernateTemplate().find("from ReadRegAnswers where readRegQuestions.readRegMaster.titleId="+titleId+" and currentStudent.studentId="+currentStudentId+" and readRegQuestions.student.studentId="+createdStudentId+" order by readRegAnswersId");
		} catch (HibernateException e) {
			logger.error("Error in getStudentAnswerByTitleId() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegAnswersLt;
	}
	
	public String submitQuizAnswers(List<ReadRegAnswers> readRegAnswersLt, ReadRegActivityScore readRegActivityScore){
		long score=0;
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try {
			if(readRegActivityScore.getReadRegReview()!=null)
				session.saveOrUpdate(readRegActivityScore.getReadRegReview());
			for (ReadRegAnswers readRegAnswers : readRegAnswersLt) {	
				session.saveOrUpdate(readRegAnswers);
				score=score+readRegAnswers.getMark();
			}
			session.saveOrUpdate(readRegActivityScore);			
		} catch (Exception e) {
			logger.error("Error in submitQuizAnswers() of StudentReadRegDAOImpl");
			 e.printStackTrace();
			return WebKeys.ERROR_OCCURED;
		}
		tx.commit();
		session.close();
		return WebKeys.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegPageRange getPageRange(long pages){
		ReadRegPageRange pageRange = new ReadRegPageRange();
		try { 
			List<ReadRegPageRange> pageRanges = (List<ReadRegPageRange>)getHibernateTemplate().find("from ReadRegPageRange where "+pages+" between pagesFrom and pagesTo");
			if(!pageRanges.isEmpty()){
				pageRange = pageRanges.get(0);
			}
		} catch (HibernateException e) {
			logger.error("Error in getPageRange() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return pageRange;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegRubric> getReadRegRubric(){
		List<ReadRegRubric> readRegRubrics = new ArrayList<ReadRegRubric>();
		try{
			readRegRubrics = (List<ReadRegRubric>) getHibernateTemplate().find("FROM ReadRegRubric");
		}
		catch (HibernateException e) {
			logger.error("Error in getPageRange() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegRubrics;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegActivityScore getStudentActivity(long studentId, long titleId, long activityId){
		ReadRegActivityScore activityScore = null;
		try{
			List<ReadRegActivityScore> activityScores = (List<ReadRegActivityScore>) getHibernateTemplate().find("FROM ReadRegActivityScore WHERE student.studentId="+studentId + " AND "
					+ "readRegMaster.titleId="+titleId+" AND readRegActivity.activityId="+activityId);
			if(!activityScores.isEmpty()){
				activityScore = activityScores.get(0);
			}
		}
		catch (HibernateException e) {
			logger.error("Error in getPageRange() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return activityScore;
	}
	
	@Override
	public String saveOrUpdateActivityScore(ReadRegActivityScore activityScore, ReadRegReview readRegReview){
		try {	
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			if(readRegReview!=null){
				session.saveOrUpdate(readRegReview);
			}
			session.saveOrUpdate(activityScore);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in saveOrUpdateActivityScore() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();
			return WebKeys.ERROR_OCCURED;
		}
		return WebKeys.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegQuestions> getStudentAllCreatedQuestionsList(long titleId, long studentId){
		List<ReadRegQuestions> readRegQuestionsLt = new ArrayList<ReadRegQuestions> ();
		try {
			readRegQuestionsLt = (List<ReadRegQuestions>)getHibernateTemplate().find("from ReadRegQuestions where readRegMaster.titleId="+titleId+" and student.studentId="+studentId);
		} catch (HibernateException e) {
			logger.error("Error in getStudentAllCreatedQuestionsList() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegQuestionsLt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegRubric getReadRegRubric(long score){
		ReadRegRubric readRegRubric = new ReadRegRubric();
		try{
			List<ReadRegRubric> readRegRubrics = (List<ReadRegRubric>) getHibernateTemplate().find("FROM ReadRegRubric where score="+score);
			if(!readRegRubrics.isEmpty())
				readRegRubric = readRegRubrics.get(0);
		}
		catch (HibernateException e) {
			logger.error("Error in getReadRegRubric() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegRubric;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegActivityScore getStudentActivity(long readRegActivityScoreId){
		ReadRegActivityScore activityScore = null;
		try{
			List<ReadRegActivityScore> activityScores = (List<ReadRegActivityScore>) getHibernateTemplate().find("FROM ReadRegActivityScore WHERE readRegActivityScoreId="+readRegActivityScoreId);
			if(!activityScores.isEmpty()){
				activityScore = activityScores.get(0);
			}
		}
		catch (HibernateException e) {
			logger.error("Error in getStudentActivity(long readRegActivityScoreId) of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return activityScore;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ReadRegActivityScore> getStudentActivities(long studentId, String sortBy, String startDate, String endDate){
		List<ReadRegActivityScore> studentActivities = new ArrayList<ReadRegActivityScore> ();
		try{
			studentActivities = (List<ReadRegActivityScore>) getHibernateTemplate()
    				.find("from ReadRegActivityScore where student.studentId= " + studentId+" "
    						+ "and createDate>='"+endDate+"' and createDate<='"+startDate+"' and approveStatus='approved' order by "+sortBy);
		}catch(Exception e){
			logger.error("Error in getStudentActivities() of StudentReadRegDAOImpl", e);
			e.printStackTrace();
		}
		return studentActivities;
		/*try {
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from ReadRegActivityScore where student.studentId= :studentId and pointsEarned is not null order by  "+sortBy);
			query.setParameter("studentId", studentId);
			studentActivities = query.list();
		} catch (HibernateException e) {
			logger.error("Error in getStudentActivities() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}*/
		//return studentActivities;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ReadRegActivityScore> getParentChildActivities(long studentId, String sortBy){
		List<ReadRegActivityScore> studentActivities = new ArrayList<ReadRegActivityScore> ();
		try{
			studentActivities = (List<ReadRegActivityScore>) getHibernateTemplate()
	    				.find("from ReadRegActivityScore where student.studentId= " + studentId+" and approveStatus='approved' order by "+sortBy);
		}catch(Exception e){
			logger.error("Error in getStudentActivities() of StudentReadRegDAOImpl", e);
			e.printStackTrace();
		}
		return studentActivities;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Teacher getTeacher(long studentId){
		System.out.println("studentId"+studentId);
		Teacher teacher = null;
		try {		
			List<RegisterForClass> studentClasses = (List<RegisterForClass>) getHibernateTemplate().find("FROM RegisterForClass WHERE student.studentId="+studentId+" "
					+ "AND classStatus_1='"+WebKeys.ALIVE+"' AND status='"+WebKeys.ACCEPTED+"' ORDER BY  rtiGroups.rtiGroupId DESC");
			if(!studentClasses.isEmpty()){
				teacher = studentClasses.get(0).getClassStatus().getTeacher();
			}
		} catch (HibernateException e) {
			logger.error("Error in getStudentActivities() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		System.out.println("teacher"+teacher.getUserRegistration().getEmailId());
		return teacher;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long getStudentTotalPointsEarned(long studentId, long gradeId){
		long totalPointsEarned = 0;
		List scores = Collections.emptyList();
		try{
			scores = (List<Object>) getHibernateTemplate()
					.find("select sum(pointsEarned) From ReadRegActivityScore where student.studentId = "+studentId
							+ "and grade.gradeId = "+ gradeId+ " and approveStatus='"+WebKeys.LP_APPROVED+"'");
			if(scores.get(0) != null)
				totalPointsEarned = Long.parseLong(scores.get(0).toString());
		}
		catch(Exception e){
			logger.error("Error in getStudentTotalPointsEarned() of StudentReadRegDAOImpl" + e);	
		}
		return totalPointsEarned;
	}
		
	@Override
	public boolean deleteBook(ReadRegMaster readRegMaster) {
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try {
			
			for (ReadRegActivityScore readRegActivityScore : readRegMaster.getReadRegActivityScoreLt()) {
				session.delete(readRegActivityScore);				
			}			
			for (ReadRegQuestions readRegQuestions : readRegMaster.getReadRegQuestionsLt()) {
				for(ReadRegAnswers readRegAnswers : readRegQuestions.getReadRegAnswers()){
					session.delete(readRegAnswers);
				}
				session.delete(readRegQuestions);				
			}			
			for (ReadRegReview readRegReview : readRegMaster.getReadRegReviewLt()) {
				session.delete(readRegReview);
			}	
			session.delete(readRegMaster);
			tx.commit();
			session.close();
			
		}catch(Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean deleteReadRegActivityScore(long titleId){
		Session session = getSessionFactory().getCurrentSession();
		try{
		String hql1 = "delete from read_reg_activity_score where title_id=:titleId";
		Query query1 = session.createQuery(hql1);
		query1.setParameter("titleId",titleId);
		query1.executeUpdate();
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	@Override
	public boolean deleteReadRegQuestions(long titleId){
		Session session = getSessionFactory().getCurrentSession();
		try{
		String hql1 = "delete from ReadRegQuestions WHERE readRegMaster.titleId=:titleId";
		Query query1 = session.createQuery(hql1);
		query1.setParameter("titleId",titleId);
		query1.executeUpdate();
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	@Override
	public boolean TeacherSaveBook(ReadRegMaster readRegMaster) {
		boolean status= true;
		int check = 0;
		try{
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		session.saveOrUpdate(readRegMaster);
		/*if(check == 0)
		{
			ReadRegPageRange pageRange = getPageRange(readRegMaster.getNumberOfPages());
			readRegMaster.setReadRegPageRange(pageRange);
			session.saveOrUpdate(readRegMaster);				
		}
		else{
			readRegMaster.setTitleId(check);
		}*/
		tx.commit();
		session.close();
		}catch(Exception e){
		logger.error("Error in addStudentGradeClass() of MyProfileDAOImpl"+ e);
		e.printStackTrace();
		status = false;
		}
		return status;
		}
	@Override
	public boolean checkActivityExists(long titleId,long studentId){
		boolean status=false;
		try{
			@SuppressWarnings("unchecked")
			List<ReadRegActivityScore> activityScores = (List<ReadRegActivityScore>) getHibernateTemplate().find("FROM ReadRegActivityScore WHERE student.studentId="+studentId + " AND "
					+ "readRegMaster.titleId="+titleId);
			if(activityScores.size()>0){
				status=true;
			}
			return status;
		}catch(Exception e){
			e.printStackTrace();
			return status;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkBookExists(String bookTitle,long masterGradesId){
		boolean status=false;
		List<ReadRegMaster> readRegMaster=new ArrayList<ReadRegMaster>();
		try{
			
			if(masterGradesId > 8 && masterGradesId != 13)
				readRegMaster = (List<ReadRegMaster>) getHibernateTemplate().find("FROM ReadRegMaster WHERE bookTitle='"+bookTitle.trim()+"' and grade.masterGrades.masterGradesId between 9 and 12");
					
			else
				readRegMaster = (List<ReadRegMaster>) getHibernateTemplate().find("FROM ReadRegMaster WHERE bookTitle='"+bookTitle.trim()+"' and"
			 		+ "(grade.masterGrades.masterGradesId <= 8 or grade.masterGrades.masterGradesId=13)");
							
			if(readRegMaster.size()>0){
				status=true;
			}
			return status;
		}catch(Exception e){
			e.printStackTrace();
			return status;
		}
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<ReadRegMaster> getRejectedBooksByRegId(long regId,  AcademicYear academicYear){
		/*List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
		try {
			readRegMasterLt = (List<ReadRegMaster>)getHibernateTemplate().find("from ReadRegMaster where approved='"+WebKeys.LP_REJECTED+"' and userRegistration.regId="+regId+" order by createDate");
			
		} catch (HibernateException e) {
			logger.error("Error in getRejectedBooksByStudentId() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegMasterLt;*/
		List<ReadRegMaster> returnedBooks = null;
		//AcademicYear academicYear = null;
		Query query = null;
		String startDate=academicYear.getStartDate().toString();
		String endDate=academicYear.getEndDate().toString();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		query = session.createQuery(
				"Select rs.readRegMaster FROM ReadRegActivityScore rs WHERE rs.approveStatus='"+WebKeys.LP_REJECTED+"' "
				        + "and rs.createDate>='"+endDate+"' and rs.createDate<='"+startDate+"'"
						+ " and rs.student.userRegistration.regId="+regId+"");
		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		returnedBooks = query.list();
		System.out.println("returnsize="+returnedBooks.size()+"regId="+regId);
		return returnedBooks;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean reSubmitBook(long titleId,long studentId){
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();			
			String hql1 = "UPDATE ReadRegMaster set  approved= :status"
					+ " WHERE titleId =:titleId and approved='"+WebKeys.LP_REJECTED+"'";
			Query query = session.createQuery(hql1);					
			query.setParameter("status", WebKeys.LP_WAITING);
			query.setParameter("titleId", titleId);
			query.executeUpdate();		
			String hql2 = "UPDATE ReadRegActivityScore set  approveStatus= :status"
					+ " WHERE readRegMaster.titleId =:titleId and student.studentId="+studentId+" and approveStatus='"+WebKeys.LP_REJECTED+"'";
			Query query1 = session.createQuery(hql2);					
			query1.setParameter("status", WebKeys.LP_WAITING);
			query1.setParameter("titleId", titleId);
			query1.executeUpdate();	
			tx.commit();
			session.close();
			
		}
		catch (HibernateException e) {
			logger.error("Error in reSubmitBook() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegActivityScore> getStudentsFromReadRegActivityScore(long gradeId){
		
	    	List<ReadRegActivityScore> studActivityScores = new ArrayList<ReadRegActivityScore>();
	    	try {
	    		studActivityScores = (List<ReadRegActivityScore>) getHibernateTemplate()
	    				.find("from ReadRegActivityScore where grade.gradeId="+gradeId+" order by student.studentId, readRegActivity.activityId asc");
	    	} catch (DataAccessException e) {
	    		logger.error("Error in getAllStudentsFromStarScores() of GoalSettingToolDAOImpl"+ e);
	    		e.printStackTrace();
	    	}
	    	return studActivityScores;
	    }
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegActivityScore> getReadRegActivityScoresByStudentId(long studentId, long gradeId){
		List<ReadRegActivityScore> studActivityScores = new ArrayList<ReadRegActivityScore>();
    	try {
    		studActivityScores = (List<ReadRegActivityScore>) getHibernateTemplate()
    				.find("from ReadRegActivityScore where grade.gradeId="+gradeId+" and student.studentId="+studentId+" and approveStatus='"+WebKeys.LP_APPROVED+"' order by readRegMaster.titleId");
    		/*readRegMaster.approved='approved'*/
    	} catch (Exception e) {
    		logger.error("Error in getReadRegActivityScoresByStudentId() of GoalSettingToolDAOImpl"+ e);
    		e.printStackTrace();
    	}
    	return studActivityScores;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getStudentRating(long titleId,long studentId){
		int rating = 0;
		try{
			String query = "select rating from read_reg_review where student_id="+studentId+" and title_id="+titleId+"";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				rating = rs.getInt(1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return rating;
	}
	@Override
	public long getActivityValue(long activityId){
		int activityValue = 0;
		try{
			String query = "select activity_value from read_reg_activity where activity_id="+activityId+"";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
			while (rs.next()) {
				activityValue = rs.getInt(1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return activityValue;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegMaster> getBooks(long masterGradesId, long pageId, long rows, String sortBy, String sortOrder, String bookName){
		List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
		try {
			Query tmp = null;		
			if(sortBy.equalsIgnoreCase("numberOfPages")) {
				if(masterGradesId > 8 && masterGradesId != 13)
					tmp = getSession().createQuery("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and grade.masterGrades.masterGradesId between 9 and 12 "
							+ "and bookTitle like :bookTitle order by "+sortBy+" "+sortOrder);
				else
					tmp = getSession().createQuery("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and "
				 		+ "(grade.masterGrades.masterGradesId <= 8 or grade.masterGrades.masterGradesId=13) and bookTitle like :bookTitle order by "+sortBy+" "+sortOrder);
			}
			else {				if(masterGradesId > 8 && masterGradesId != 13)
					tmp = getSession().createQuery("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and grade.masterGrades.masterGradesId between 9 and 12 "
							+ "order by '"+sortBy+"' "+sortOrder);
				else
					tmp = getSession().createQuery("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and "
				 		+ "(grade.masterGrades.masterGradesId <= 8 or grade.masterGrades.masterGradesId=13)"
				 		+ " order by TRIM(lower("+sortBy+")) "+sortOrder);
			}
			//tmp.setParameter("bookTitle", "%"+bookName+"%");			    
			tmp.setFirstResult((int) pageId);
			tmp.setMaxResults((int) rows);

			readRegMasterLt = (List<ReadRegMaster>)tmp.list();
		} catch (HibernateException e) {
			logger.error("Error in getAllAddedBooks() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegMasterLt;
	}	
	
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ReadRegMaster> getBooks(long masterGradesId, long pageId, long rows, String sortBy, String sortOrder, String bookName){
//		List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
//		try {
//			Query tmp = null;		
//			if(sortBy.equalsIgnoreCase("numberOfPages")) {
//				if(masterGradesId > 8 && masterGradesId != 13)
//					tmp = getSession().createQuery("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and grade.masterGrades.masterGradesId between 9 and 12 "
//							+ "and bookTitle like :bookTitle order by "+sortBy+" "+sortOrder);
//				else
//					tmp = getSession().createQuery("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and "
//				 		+ "(grade.masterGrades.masterGradesId <= 8 or grade.masterGrades.masterGradesId=13) and bookTitle like :bookTitle order by "+sortBy+" "+sortOrder);
//			}
//			else {				if(masterGradesId > 8 && masterGradesId != 13)
//					tmp = getSession().createQuery("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and grade.masterGrades.masterGradesId between 9 and 12 "
//							+ "and bookTitle like :bookTitle order by "+sortBy+" "+sortOrder);
//				else
//					tmp = getSession().createQuery("from ReadRegMaster where approved='"+WebKeys.LP_APPROVED+"' and "
//				 		+ "(grade.masterGrades.masterGradesId <= 8 or grade.masterGrades.masterGradesId=13)"
//				 		+ " and bookTitle like :bookTitle order by TRIM(lower("+sortBy+")) "+sortOrder);
//			}
//			tmp.setParameter("bookTitle", "%"+bookName+"%");			    
//			tmp.setFirstResult((int) pageId);
//			tmp.setMaxResults((int) rows);
//
//			readRegMasterLt = (List<ReadRegMaster>)tmp.list();
//		} catch (HibernateException e) {
//			logger.error("Error in getAllAddedBooks() of StudentReadRegDAOImpl" + e);
//			e.printStackTrace();			
//		}
//		return readRegMasterLt;
//	}	

	@Override	
	public long getApprovalBookCount(long gradeId, long teacherId,String bookName, AcademicYear academicYear) {
			
		long bookCount = 0;
		Query query = null;
		try {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			
			if(teacherId>0){
				query =  session.createQuery("Select count(*) from ReadRegMaster where grade.gradeId="+gradeId+" "
						+ "and createDate between :startDate and :endDate "
						+ "and teacher.teacherId="+teacherId+" and bookTitle like :bookTitle");
			}
			else{
			query =  session.createQuery("Select count(*) from ReadRegMaster where grade.gradeId="+gradeId+" "
					+ "and createDate between :startDate and :endDate "
					+ "and bookTitle like :bookTitle");
			}
			//bookCount = ((Long) session.createQuery("Select count(*) from ReadRegMaster where grade.gradeId="+gradeId+"").uniqueResult());
				
			query.setParameter("bookTitle", "%"+bookName+"%");
			query.setDate("startDate", academicYear.getStartDate());
			query.setDate("endDate", academicYear.getEndDate());
			bookCount = (long) query.uniqueResult();
		} catch (HibernateException e) {
			logger.error("Error in getAllAddedBooks() of StudentReadRegDAOImpl" + e);
			e.printStackTrace();			
		}
		return bookCount;
	}
	@SuppressWarnings("unchecked")
	@Override	
	public long getStudentTotalPointsEarned(long studentId, long gradeId,Date fromDate,Date toDate){
		long totalPointsEarned = 0;
		List scores = Collections.emptyList();
		try{
			scores = (List<Object>) getHibernateTemplate()
					.find("select sum(pointsEarned) From ReadRegActivityScore where student.studentId = "+studentId
							+ "and grade.gradeId = "+ gradeId+ " and approveStatus='"+WebKeys.LP_APPROVED+"' and createDate>='"+fromDate+"' "+" and createDate<='"+toDate+"'");
			if(scores.get(0) != null)
				totalPointsEarned = Long.parseLong(scores.get(0).toString());
		}
		catch(Exception e){
			logger.error("Error in getStudentTotalPointsEarned() of StudentReadRegDAOImpl" + e);	
		}
		return totalPointsEarned;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegActivityScore> getReadRegActivityScoresByStudentId(long studentId, long gradeId,Date fromDate,Date toDate){
		List<ReadRegActivityScore> studActivityScores = new ArrayList<ReadRegActivityScore>();
    	try {
    		studActivityScores = (List<ReadRegActivityScore>) getHibernateTemplate()
    		     .find("from ReadRegActivityScore where grade.gradeId="+gradeId+" and student.studentId="+studentId+" and approveStatus='"+WebKeys.LP_APPROVED+"' and createDate between '"+fromDate+"' "+" and '"+toDate+"' order by readRegMaster.titleId");
    		/*readRegMaster.approved='approved'*/
    	} catch (Exception e) {
    		logger.error("Error in getReadRegActivityScoresByStudentId() of GoalSettingToolDAOImpl"+ e);
    		e.printStackTrace();
    	}
    	return studActivityScores;
	}
	
	@Override
	public AcademicYear getRRAcademicYearByActivityDate(String actDate) {
		AcademicYear academicYear = null;
		try {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery("FROM AcademicYear where :activityDate between startDate and endDate");
			query.setString("activityDate", actDate);
			List<AcademicYear> academicYears = query.list();
			System.out.println("size"+academicYears.size());
			if(!academicYears.isEmpty()) {
				academicYear = academicYears.get(0);
			}
		} catch (Exception e) {
			logger.info("Error in of getRRAcademicYearByActivityDate StudentReadRegDAOImpl "+ e);
		}
		return academicYear;
	}
}
