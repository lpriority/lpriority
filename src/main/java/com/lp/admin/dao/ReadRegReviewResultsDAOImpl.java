package com.lp.admin.dao;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lp.common.dao.FileDAO;
import com.lp.model.AcademicYear;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.ReadRegAnswers;
import com.lp.model.ReadRegMaster;
import com.lp.model.ReadRegQuestions;
import com.lp.model.ReadRegReview;
import com.lp.model.ReadRegRubric;
import com.lp.model.Student;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;
import java.util.Date;

@Repository("studentReadRegTempDAO")
public class ReadRegReviewResultsDAOImpl extends CustomHibernateDaoSupport implements ReadRegReviewResultsDAO {
	static final Logger logger = Logger.getLogger(ReadRegReviewResultsDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private FileDAO fileDAO;
	@Autowired
	private HttpServletRequest request;
	
	public Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public List<Student> getRRStudents(long gradeId, long teacherId){
		List<Student> students = new ArrayList<>();
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT rras.student FROM ReadRegActivityScore rras WHERE"
					+ " rras.grade.gradeId=:gradeId "
					+ "and rras.teacher.teacherId=:teacherId order by rras.student.userRegistration.firstName asc");
			query.setParameter("gradeId", gradeId);
			query.setParameter("teacherId", teacherId);
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			students = query.list();
		}
		catch(Exception e){
			logger.error("Error in getRRStudents() of ReadRegReviewResultsDAOImpl" + e);
		}
		return students;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public List<Student> getRRStudentsByYear(long gradeId, AcademicYear academicYear, long teacherId, String gradedStatus){
		List<Student> students = new ArrayList<>();
		String startDate=academicYear.getStartDate().toString();
		String endDate =academicYear.getEndDate().toString();
		System.out.println("startDate="+startDate);
		System.out.println("endDate="+endDate);
		try{
			Query query = null;
			if(gradedStatus.equals("graded")) {
				query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT rras.student FROM ReadRegActivityScore rras "
						+ "WHERE rras.grade.gradeId=:gradeId "
						+ "and rras.createDate>='"+endDate+"' and rras.createDate<='"+startDate+"' "
						+ "and rras.pointsEarned is not null "
						+ "and rras.approveStatus != :approveStatus "
						+ "and rras.teacher.teacherId=:teacherId order by rras.student.userRegistration.firstName asc");
			} else {
				query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT rras.student FROM ReadRegActivityScore rras "
						+ "WHERE rras.grade.gradeId=:gradeId "
						+ "and rras.createDate>='"+endDate+"' and rras.createDate<='"+startDate+"' "
						+ "and rras.pointsEarned is null "
						+ "and rras.approveStatus != :approveStatus "
						+ "and rras.teacher.teacherId=:teacherId order by rras.student.userRegistration.firstName asc");
			}
			query.setParameter("gradeId", gradeId);
			query.setParameter("teacherId", teacherId);
//			query.setDate("startDate", academicYear.getStartDate());
//			query.setDate("endDate", academicYear.getEndDate());
			query.setString("approveStatus", "returned");
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			students = query.list();
			System.out.println("size="+students.size());
		}
		catch(Exception e){
			logger.error("Error in getRRStudents() of ReadRegReviewResultsDAOImpl" + e);
		}
		return students;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public List<ReadRegActivityScore> getGradedActivities(long gradeId,String sortBy, long teacherId, String startDate, String endDate){
		List<ReadRegActivityScore> list=new ArrayList<ReadRegActivityScore>();
		try{
			if(teacherId == 0){
				list = (List<ReadRegActivityScore>) getHibernateTemplate()
	    				.find("from ReadRegActivityScore where grade.gradeId= " + gradeId+" and "
	    						+ "createDate>='"+endDate+"' and createDate<='"+startDate+"' and approveStatus='approved'   order by "+sortBy);	
			}
			else{
				list = (List<ReadRegActivityScore>) getHibernateTemplate() 
    				.find("from ReadRegActivityScore where grade.gradeId= " + gradeId+" and createDate>='"
    						+endDate+"' and createDate<='"+startDate+"' and teacher.teacherId="+teacherId+" "
    								+ "and approveStatus='approved' order by "+sortBy);
			}
		}catch(Exception e){
			logger.error("Error in getGradedActivities() of ReadRegReviewResultsDAOImpl", e);
			e.printStackTrace();
		}
		return list;
		
		/*List<ReadRegActivityScore> studentActivities = new ArrayList<ReadRegActivityScore> ();
		try {
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from ReadRegActivityScore where grade.gradeId= :gradeId and pointsEarned is not null "
					+ "order by "+sortBy);
			query.setParameter("gradeId", gradeId);
			studentActivities = query.list();
		} catch (HibernateException e) {
			logger.error("Error in getGradedActivities() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();			
		}
		return studentActivities;*/
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public List<Student> getRRStudents(long gradeId) {
		List<Student> students = new ArrayList<>();
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT rras.student FROM ReadRegActivityScore rras WHERE"
					+ " and rras.grade.gradeId=:gradeId order by rras.student.userRegistration.firstName");
			query.setParameter("gradeId", gradeId);
			query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			students = query.list();
		}
		catch(Exception e){
			logger.error("Error in getRRStudents() of ReadRegReviewResultsDAOImpl" + e);
		}
		return students;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public List<Student> getRRStudentsByGrade(long gradeId, AcademicYear academicYear, String gradedStatus) {
		List<Student> students = new ArrayList<>();
		String startDate =academicYear.getStartDate().toString();
		String endDate= academicYear.getEndDate().toString();
		try{
			Query query = null;
			if(gradedStatus.equals("graded")){
				query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT rras.student FROM ReadRegActivityScore rras "
						+ "WHERE rras.createDate>='"+endDate+"' and createDate<='"+startDate+"' "
					+ "and rras.grade.gradeId=:gradeId "
					+ "and rras.pointsEarned is not null "
					+ "and rras.approveStatus != :approveStatus "
					+ "order by rras.student.userRegistration.firstName");
			} else {
				query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT rras.student FROM ReadRegActivityScore rras "
						+ "WHERE rras.createDate>='"+endDate+"' and createDate<='"+startDate+"' "
						+ "and rras.grade.gradeId=:gradeId "
						+ "and rras.pointsEarned is null "
						+ "and rras.approveStatus != :approveStatus "
						+ "order by rras.student.userRegistration.firstName");
			}
			query.setParameter("gradeId", gradeId);
//			query.setDate("startDate", academicYear.getStartDate());
//			query.setDate("endDate", academicYear.getEndDate());
			query.setParameter("approveStatus", "returned");
			//query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			students = query.list();
		}
		catch(Exception e){
			logger.error("Error in getRRStudents() of ReadRegReviewResultsDAOImpl" + e);
		}
		return students;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ReadRegActivityScore> getActivitiesToBeReviewed(long studentId, AcademicYear academicYear, String sortBy){
		List<ReadRegActivityScore> studentActivities = new ArrayList<ReadRegActivityScore> ();
		try {
			String startDate=academicYear.getStartDate().toString();
			String endDate=academicYear.getEndDate().toString();
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from ReadRegActivityScore where student.studentId= :studentId "
					+ " and createDate>='"+endDate+"' and createDate<='"+startDate+"'"
					+ " and pointsEarned is null "
					+ " and readRegMaster.approved != :status1 and approveStatus != :status2 order by  "+sortBy);				
			query.setParameter("studentId", studentId);
//			query.setDate("startDate", academicYear.getStartDate());
//			query.setDate("endDate", academicYear.getEndDate());
			query.setParameter("status1", "returned");
			query.setParameter("status2", "returned");
			studentActivities = query.list();
		} catch (HibernateException e) {
			logger.error("Error in getActivitiesToBeReviewed() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();			
		}
		return studentActivities;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ReadRegActivityScore> getUnGradedActivities(long gradeId,String sortBy, AcademicYear academicYear){
		List<ReadRegActivityScore> studentActivities = new ArrayList<ReadRegActivityScore> ();
		try {
			String startDate=academicYear.getStartDate().toString();
			String endDate=academicYear.getEndDate().toString();
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from ReadRegActivityScore where "
					+ "grade.gradeId= :gradeId and readRegMaster.approved!=:status1 and approveStatus!=:status2"
					+ " and createDate>='"+endDate+"' and createDate<='"+startDate+"' "
					+ "and pointsEarned is null "
					+ "order by "+sortBy);
			query.setParameter("gradeId", gradeId);
			query.setParameter("status1",WebKeys.LP_REJECTED );
			query.setParameter("status2", WebKeys.LP_REJECTED);
//			query.setDate("startDate", academicYear.getStartDate());
//			query.setDate("endDate", academicYear.getEndDate());
			studentActivities = query.list();
		} catch (HibernateException e) {
			logger.error("Error in getUnGradedActivities() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();			
		}
		return studentActivities;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public ReadRegRubric getReadRegRubricById(long scoreId){
		ReadRegRubric readRegRubric = new ReadRegRubric();
		try{
			List<ReadRegRubric> readRegRubrics = (List<ReadRegRubric>) getHibernateTemplate().find("FROM ReadRegRubric where readRegRubricId="+scoreId);
			if(!readRegRubrics.isEmpty())
				readRegRubric = readRegRubrics.get(0);
		}
		catch (HibernateException e) {
			logger.error("Error in getReadRegRubricById() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegRubric;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public boolean saveScore(long readRegActivityScoreId, long scoreId, long pointsEarned,String accApproveStat,String teacherComment){
		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();	
						
			if(accApproveStat.equalsIgnoreCase(WebKeys.LP_REJECTED)){
			String hql1 = "UPDATE ReadRegActivityScore set pointsEarned = :pointsEarned, approveStatus= :accApproveStat,teacherComment= :teacherComment"
					+ " WHERE readRegActivityScoreId =:readRegActivityScoreId";
			Query query = session.createQuery(hql1);					
			query.setParameter("pointsEarned", pointsEarned);
			query.setParameter("accApproveStat",accApproveStat);
			query.setParameter("teacherComment", teacherComment);
			query.setParameter("readRegActivityScoreId", readRegActivityScoreId);
			query.executeUpdate();	
			}else{
				String hql2 = "UPDATE ReadRegActivityScore set pointsEarned = :pointsEarned, readRegRubric.readRegRubricId = :readRegRubricId,approveStatus= :accApproveStat,teacherComment= :teacherComment"
						+ " WHERE readRegActivityScoreId =:readRegActivityScoreId";
				Query query2 = session.createQuery(hql2);					
				query2.setParameter("pointsEarned", pointsEarned);
				query2.setParameter("readRegRubricId", scoreId);
				query2.setParameter("accApproveStat",accApproveStat);
				query2.setParameter("teacherComment", teacherComment);
				query2.setParameter("readRegActivityScoreId", readRegActivityScoreId);
				query2.executeUpdate();
			}
			tx.commit();
			session.close();
		}
		catch (HibernateException e) {
			logger.error("Error in saveScore() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public List<ReadRegQuestions> getReadRegQuestions(long studentId, long titleId){
		List<ReadRegQuestions> readRegQuestions = new ArrayList<ReadRegQuestions> ();
		try {
			readRegQuestions = (List<ReadRegQuestions>) getHibernateTemplate().find("from ReadRegQuestions where student.studentId= "+studentId + " and readRegMaster.titleId="+titleId);
		} catch (HibernateException e) {
			logger.error("Error in getReadRegQuestions() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegQuestions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegAnswers> getStudentQuiz(long studentId, long titleId){
		List<ReadRegAnswers> readRegAnswers = new ArrayList<ReadRegAnswers> ();
		try {
			readRegAnswers = (List<ReadRegAnswers>) getHibernateTemplate().find("from ReadRegAnswers where currentStudent.studentId= "+studentId + " and readRegQuestions.readRegMaster.titleId="+titleId);
		} catch (HibernateException e) {
			logger.error("Error in getStudentQuiz() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegAnswers;
		
	}

  	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegMaster> getAllAddedBooksByGrade(long gradeId, long teacherId){
		List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
		try {
			if(teacherId > 0)
				readRegMasterLt = (List<ReadRegMaster>)getHibernateTemplate().find("from ReadRegMaster where grade.gradeId="+gradeId+" and  teacher.teacherId="+teacherId+" order by approved desc");
			else
				readRegMasterLt = (List<ReadRegMaster>)getHibernateTemplate().find("from ReadRegMaster where grade.gradeId="+gradeId+" order by approved desc");
		} catch (HibernateException e) {
			logger.error("Error in getAllAddedBooksByGrade() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();			
		}
		return readRegMasterLt;
	}
  	
  	public boolean updateAprrovalStatus(ReadRegMaster readRegMaster) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(readRegMaster);
			tx.commit();	
			return true;
		} catch (HibernateException e) {
			logger.error("Error in updateAprrovalStatus() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();	
			return false;
		}		
  	}
  	
  	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ReadRegActivityScore> getUnGradedActivities(long gradeId,String sortBy,long teacherId, AcademicYear academicYear){
		List<ReadRegActivityScore> studentActivities = new ArrayList<ReadRegActivityScore> ();
		try {
			String startDate=academicYear.getStartDate().toString();
			String endDate=academicYear.getEndDate().toString();
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("from ReadRegActivityScore where "
					+ "grade.gradeId= :gradeId and teacher.teacherId=:teacherId "
					+ "and pointsEarned is NULL and readRegMaster.approved!=:status1 and approveStatus!=:status2 "
					+ "and createDate>='"+endDate+"' and createDate<='"+startDate+"' "
					+ "order by "+sortBy);
			query.setParameter("gradeId", gradeId);
			query.setParameter("teacherId", teacherId);
			//query.setDate("startDate", academicYear.getStartDate());
			//query.setDate("endDate", academicYear.getEndDate());
			query.setParameter("status1",WebKeys.LP_REJECTED );
			query.setParameter("status2", WebKeys.LP_REJECTED);
			studentActivities = query.list();
		} catch (HibernateException e) {
			logger.error("Error in getUnGradedActivities() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();			
		}
		return studentActivities;
	}
  	public boolean setActivityApprovalStatus(ReadRegMaster readRegMaster){
  		try{
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();			
			String hql1 = "UPDATE ReadRegActivityScore set approveStatus= :accApproveStat"
					+ " WHERE readRegMaster.titleId =:titleId";
			Query query = session.createQuery(hql1);					
			query.setParameter("accApproveStat", readRegMaster.getApproved());
			query.setParameter("titleId", readRegMaster.getTitleId());
			query.executeUpdate();		
			tx.commit();
			session.close();
		}
		catch (HibernateException e) {
			logger.error("Error in setActivityApprovalStatus() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
		return true;
  	}
  	public List<Student> getRRStudents(long gradeId,String approveStatus){
  		
  			List<Student> students = new ArrayList<Student>();
  			try{
  				Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT rras.student FROM ReadRegActivityScore rras WHERE"
  						+ " rras.grade.gradeId=:gradeId and rras.approveStatus='"+approveStatus+"'");
  				query.setParameter("gradeId", gradeId);
  				query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
  				students = query.list();
  			}
  			catch(Exception e){
  				logger.error("Error in getRRStudents() of ReadRegReviewResultsDAOImpl" + e);
  			}
  			return students;
  		
      }
  	public List<Student> getRRStudents(long gradeId,long teacherId,String approveStatus){
  		
			List<Student> students = new ArrayList<Student>();
			try{
				Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery("SELECT rras.student FROM ReadRegActivityScore rras WHERE"
						+ " rras.grade.gradeId=:gradeId and rras.teacher.teacherId=:teacherId and rras.approveStatus='"+approveStatus+"'");
				query.setParameter("gradeId", gradeId);
				query.setParameter("teacherId", teacherId);
				query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				students = query.list();
			}
			catch(Exception e){
				logger.error("Error in getRRStudents() of ReadRegReviewResultsDAOImpl" + e);
			}
			return students;
		
  }
  	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegMaster> getAllAddedBooksByGrade(long gradeId, AcademicYear academicYear, long teacherId,long pageId,long rows,String sortBy, String sortOrder, String bookName){
		List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
		try{
			Query tmp = null;	
			if(teacherId==0){
				if(sortBy.equalsIgnoreCase("numberOfPages")) {
				tmp = getSession().createQuery("from ReadRegMaster where grade.gradeId="+gradeId+""
						+ " and createDate between :startDate and :endDate and bookTitle like :bookTitle order by "+sortBy+" "+sortOrder+"");
				}else{
					tmp = getSession().createQuery("from ReadRegMaster where grade.gradeId="+gradeId+" "
							+ " and createDate between :startDate and :endDate and bookTitle like :bookTitle order by TRIM(lower("+sortBy+")) "+sortOrder);
				}
			}
			else{
				if(sortBy.equalsIgnoreCase("numberOfPages")){
				tmp = getSession().createQuery("from ReadRegMaster where grade.gradeId="+gradeId+" "
						+ " and createDate between :startDate and :endDate and  teacher.teacherId="+teacherId+"and bookTitle like :bookTitle order by "+sortBy+" "+sortOrder+"");
				}else{
					tmp = getSession().createQuery("from ReadRegMaster where grade.gradeId="+gradeId+" "
							+ " and createDate between :startDate and :endDate and  teacher.teacherId="+teacherId+"and bookTitle like :bookTitle order by TRIM(lower("+sortBy+")) "+sortOrder);
				}
			}
		tmp.setDate("startDate", academicYear.getStartDate());
		tmp.setDate("endDate", academicYear.getEndDate());	
		tmp.setParameter("bookTitle", "%"+bookName+"%");			
		tmp.setFirstResult((int) pageId);
		tmp.setMaxResults((int) rows);
		readRegMasterLt=(List<ReadRegMaster>)tmp.list();
		}catch(Exception e){
			logger.error("Error in getAllAddedBooksByGrade() of ReadRegReviewResultsDAOImpl" + e);
		}
		return readRegMasterLt;
}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReadRegMaster> getReadRegBooksByGrade(long gradeId, long teacherId, AcademicYear academicYear,long pageId,long rows){
		List<ReadRegMaster> readRegMasterLt = new ArrayList<ReadRegMaster> ();
		try{
			Query tmp = null;	
			String startDate=academicYear.getStartDate().toString();
			String endDate=academicYear.getEndDate().toString();
					
			if(teacherId==0)
				tmp = getSession().createQuery("from ReadRegMaster where grade.gradeId="+gradeId+" "
						+ " and createDate between :startDate and :endDate  order by approved desc");
			else	
				tmp = getSession().createQuery("from ReadRegMaster where grade.gradeId="+gradeId+" and teacher.teacherId="+teacherId+""
						+ " and (createDate>='"+endDate+"' and createDate<='"+startDate+"')  order by approved desc");
		
			tmp.setFirstResult((int) pageId);
			tmp.setMaxResults((int) rows);
			readRegMasterLt=(List<ReadRegMaster>)tmp.list();
			System.out.println("size="+readRegMasterLt.size());
		}catch(Exception e){
			logger.error("Error in getAllAddedBooksByGrade() of ReadRegReviewResultsDAOImpl" + e);
		}
		return readRegMasterLt;	
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean returnGradedActivity(long readRegActivityScoreId){
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();	
		try{
		String hql = "UPDATE ReadRegActivityScore set approveStatus= :accApproveStat,pointsEarned = :pointsEarned, readRegRubric.readRegRubricId = :readRegRubricId,teacherComment= :teacherComment  WHERE readRegActivityScoreId =:readRegActivityScoreId";
		Query query2 = session.createQuery(hql);					
		query2.setParameter("accApproveStat",WebKeys.LP_WAITING);
		query2.setParameter("pointsEarned",null);
		query2.setParameter("readRegRubricId",null);
		query2.setParameter("teacherComment", null);
		query2.setParameter("readRegActivityScoreId", readRegActivityScoreId);
		query2.executeUpdate();
		tx.commit();
		session.close();
		}catch(Exception e){
			logger.error("Error in returnGradedActivity() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean mergeReadRegDulicateBooks(Long[] titleIds, ReadRegMaster approvedBook) {		
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		
		// Approved Review Objects
		
		String hql1 = "FROM ReadRegReview WHERE readRegMaster.titleId =:titleId";
		Query query1 = session.createQuery(hql1);			
		query1.setParameter("titleId", approvedBook.getTitleId());
		List<ReadRegReview> readRegReviews = query1.list();
		
		
		Map<String, ReadRegReview> approvedReviewsMap = new HashMap<String, ReadRegReview>();
		for(ReadRegReview readRegReview: readRegReviews) {
			approvedReviewsMap.put(readRegReview.getReadRegMaster().getTitleId()+"-"+readRegReview.getStudent().getStudentId(),
					readRegReview);
		}
		
		// Approved ReadRegActivities
		Query query2 = session.createQuery("FROM ReadRegActivityScore"
				+ " WHERE readRegMaster.titleId=:titleId ORDER BY student.studentId");
		query2.setParameter("titleId", approvedBook.getTitleId());
		List<ReadRegActivityScore> approvedBookActivities = query2.list();		
		
		Map<String, ReadRegActivityScore> approvedActivitiesMap = new HashMap<String, ReadRegActivityScore>();
		for(ReadRegActivityScore readRegActivityScore: approvedBookActivities) {
			approvedActivitiesMap.put(readRegActivityScore.getReadRegMaster().getTitleId()+"-"+readRegActivityScore.getStudent().getStudentId() + "-" + readRegActivityScore.getReadRegActivity().getActivityId() ,
					readRegActivityScore);
		}
		
		// Actities to be merged
		Query query3 = session.createQuery("FROM ReadRegActivityScore"
				+ " WHERE readRegMaster.titleId IN (:readRedTitleIdArrayLt) ORDER BY student.studentId");
		query3.setParameterList("readRedTitleIdArrayLt", titleIds);
		List<ReadRegActivityScore> ReadRegActivityLt = query3.list();
		try{
			for(ReadRegActivityScore reActivityScore: ReadRegActivityLt) {			
				if(approvedActivitiesMap.containsKey(approvedBook.getTitleId()+"-"+reActivityScore.getStudent().getStudentId() 
						+ "-" + reActivityScore.getReadRegActivity().getActivityId())) {
					String hql7 = "DELETE FROM ReadRegActivityScore WHERE readRegActivityScoreId =:readRegActivityScoreId";
					Query query7 = session.createQuery(hql7);
					query7.setParameter("readRegActivityScoreId", reActivityScore.getReadRegActivityScoreId());
					query7.executeUpdate();
					if(reActivityScore.getReadRegActivity().getActivityId() == 2) {
						String hql8 = "DELETE FROM ReadRegQuestions WHERE readRegMaster.titleId =:titleId and student.studentId =:studentId and grade.gradeId =:gradeId";
						Query query8 = session.createQuery(hql8);
						query8.setParameter("titleId", reActivityScore.getReadRegMaster().getTitleId());
						query8.setParameter("studentId", reActivityScore.getStudent().getStudentId());
						query8.setParameter("gradeId", reActivityScore.getGrade().getGradeId());
						query8.executeUpdate();
					} else if (reActivityScore.getReadRegActivity().getActivityId() == 4) {
						String hql9 = "DELETE FROM ReadRegAnswers WHERE readRegQuestions.readRegMaster.titleId =:titleId and currentStudent.studentId =:studentId and grade.gradeId =:gradeId";
						Query query9 = session.createQuery(hql9);
						query9.setParameter("titleId", reActivityScore.getReadRegMaster().getTitleId());
						query9.setParameter("studentId", reActivityScore.getStudent().getStudentId());
						query9.setParameter("gradeId", reActivityScore.getGrade().getGradeId());
						query9.executeUpdate();
					}
					String hql10 = "DELETE FROM ReadRegReview WHERE readRegMaster.titleId =:titleId and student.studentId=:studentId";
					Query query10 = session.createQuery(hql10);
					query10.setParameter("titleId", reActivityScore.getReadRegMaster().getTitleId());
					query10.setParameter("studentId", reActivityScore.getStudent().getStudentId());
					query10.executeUpdate();
				} else if(approvedReviewsMap.containsKey(approvedBook.getTitleId()+"-"+reActivityScore.getReadRegReview().getStudent().getStudentId())) {
					ReadRegReview review = approvedReviewsMap.get(approvedBook.getTitleId()+"-"+reActivityScore.getReadRegReview().getStudent().getStudentId());
					String hql4 = "UPDATE ReadRegActivityScore set readRegMaster.titleId = :oldestTitleId, readRegReview.reviewId=:reviewId  WHERE readRegActivityScoreId =:readRegActivityScoreId";
					Query query4 = session.createQuery(hql4);
					query4.setParameter("oldestTitleId", approvedBook.getTitleId());
					query4.setParameter("reviewId", review.getReviewId());
					query4.setParameter("readRegActivityScoreId", reActivityScore.getReadRegActivityScoreId());
					query4.executeUpdate();
					if(reActivityScore.getReadRegActivity().getActivityId() == 2) {
						String hql5 = "UPDATE ReadRegQuestions set readRegMaster.titleId = :oldestTitleId WHERE readRegMaster.titleId =:titleId and student.studentId =:studentId and grade.gradeId =:gradeId ";
						Query query5 = session.createQuery(hql5);
						query5.setParameter("oldestTitleId", approvedBook.getTitleId());
						query5.setParameter("titleId", reActivityScore.getReadRegMaster().getTitleId());
						query5.setParameter("studentId", reActivityScore.getStudent().getStudentId());
						query5.setParameter("gradeId", reActivityScore.getGrade().getGradeId());
						query5.executeUpdate();
					} else if(reActivityScore.getReadRegActivity().getActivityId() == 3) {
						String uploadFilePath = FileUploadUtil.getUploadFilesPath(reActivityScore.getStudent().getUserRegistration(), request);
						String ReadingRegisterFilePath =  uploadFilePath + File.separator+ 
													WebKeys.READING_REGISTER+File.separator+
													reActivityScore.getReadRegMaster().getTitleId();
						fileDAO.renameDir(ReadingRegisterFilePath, String.valueOf(reActivityScore.getReadRegMaster().getTitleId()), String.valueOf(approvedBook.getTitleId()));
					}  else if(reActivityScore.getReadRegActivity().getActivityId() == 5) {
						String uploadFilePath = FileUploadUtil.getUploadFilesPath(reActivityScore.getStudent().getUserRegistration(), request);
						String ReadingRegisterFilePath =  uploadFilePath + File.separator+ 
													WebKeys.READING_REGISTER+File.separator+
													reActivityScore.getReadRegMaster().getTitleId();
						fileDAO.renameDir(ReadingRegisterFilePath, String.valueOf(reActivityScore.getReadRegMaster().getTitleId()), String.valueOf(approvedBook.getTitleId()));
					}
					String hql10 = "DELETE FROM ReadRegReview WHERE readRegMaster.titleId =:titleId and student.studentId=:studentId";
					Query query10 = session.createQuery(hql10);
					query10.setParameter("titleId", reActivityScore.getReadRegMaster().getTitleId());
					query10.setParameter("studentId", reActivityScore.getStudent().getStudentId());
					query10.executeUpdate();
				} else {
					String hql4 = "UPDATE ReadRegActivityScore set readRegMaster.titleId = :oldestTitleId WHERE readRegActivityScoreId =:readRegActivityScoreId";
					Query query4 = session.createQuery(hql4);
					query4.setParameter("oldestTitleId", approvedBook.getTitleId());
					query4.setParameter("readRegActivityScoreId", reActivityScore.getReadRegActivityScoreId());
					query4.executeUpdate();
					if(reActivityScore.getReadRegActivity().getActivityId() == 2) {
						String hql5 = "UPDATE ReadRegQuestions set readRegMaster.titleId = :oldestTitleId WHERE readRegMaster.titleId =:titleId and student.studentId =:studentId and grade.gradeId =:gradeId ";
						Query query5 = session.createQuery(hql5);
						query5.setParameter("oldestTitleId", approvedBook.getTitleId());
						query5.setParameter("titleId", reActivityScore.getReadRegMaster().getTitleId());
						query5.setParameter("studentId", reActivityScore.getStudent().getStudentId());
						query5.setParameter("gradeId", reActivityScore.getGrade().getGradeId());
						query5.executeUpdate();
					} else if(reActivityScore.getReadRegActivity().getActivityId() == 3) {
						String uploadFilePath = FileUploadUtil.getUploadFilesPath(reActivityScore.getStudent().getUserRegistration(), request);
						String ReadingRegisterFilePath =  uploadFilePath + File.separator+ 
													WebKeys.READING_REGISTER+File.separator+
													reActivityScore.getReadRegMaster().getTitleId();
						fileDAO.renameDir(ReadingRegisterFilePath, String.valueOf(reActivityScore.getReadRegMaster().getTitleId()), String.valueOf(approvedBook.getTitleId()));
					} else if(reActivityScore.getReadRegActivity().getActivityId() == 5) {
						String uploadFilePath = FileUploadUtil.getUploadFilesPath(reActivityScore.getStudent().getUserRegistration(), request);
						String ReadingRegisterFilePath =  uploadFilePath + File.separator+ 
													WebKeys.READING_REGISTER+File.separator+
													reActivityScore.getReadRegMaster().getTitleId();
						fileDAO.renameDir(ReadingRegisterFilePath, String.valueOf(reActivityScore.getReadRegMaster().getTitleId()), String.valueOf(approvedBook.getTitleId()));
					}
					String hql6 = "UPDATE ReadRegReview set readRegMaster.titleId = :oldestTitleId WHERE readRegMaster.titleId =:titleId and student.studentId =:studentId and grade.gradeId =:gradeId";
					Query query6 = session.createQuery(hql6);
					query6.setParameter("oldestTitleId", approvedBook.getTitleId());
					query6.setParameter("titleId", reActivityScore.getReadRegMaster().getTitleId());
					query6.setParameter("studentId", reActivityScore.getStudent().getStudentId());
					query6.setParameter("gradeId", reActivityScore.getGrade().getGradeId());
					query6.executeUpdate();	
				}		
			}	
			if(ReadRegActivityLt.isEmpty()) {
				String hql10 = "DELETE FROM ReadRegReview WHERE readRegMaster.titleId IN (:readRedTitleIdArrayLt)";
				Query query10 = session.createQuery(hql10);
				query10.setParameterList("readRedTitleIdArrayLt", titleIds);
				query10.executeUpdate();
			}
			String hql11= "DELETE FROM ReadRegMaster WHERE titleId IN (:readRedTitleIdArrayLt)";
			Query query11 = session.createQuery(hql11);			
			query11.setParameterList("readRedTitleIdArrayLt", titleIds);
			query11.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			logger.error("Error in mergeReadRegDulicateBooks() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public ReadRegMaster getApprovedBook(Long[] readRedTitleIdArrayLt) {
	List<ReadRegMaster> readRegMasters = new ArrayList<ReadRegMaster>();
	ReadRegMaster approvedBook = null;
		try{
			Query query = getHibernateTemplate().getSessionFactory().openSession().createQuery(" FROM ReadRegMaster"
					+ " WHERE titleId IN (:readRedTitleIdArrayLt) ORDER BY approved ASC, titleId ASC");
			query.setParameterList("readRedTitleIdArrayLt", readRedTitleIdArrayLt);
			readRegMasters = query.list();
			if(!readRegMasters.isEmpty())
			approvedBook = readRegMasters.get(0);
		} catch (Exception e){
			logger.error("Error in readRegMasters() of ReadRegReviewResultsDAOImpl" + e);
			e.printStackTrace();
			
		}
		return approvedBook;
	}
}
