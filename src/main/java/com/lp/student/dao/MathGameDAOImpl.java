package com.lp.student.dao;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.model.GameLevel;
import com.lp.model.MathGameScores;
import com.lp.model.StudentAssignmentStatus;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;



public class MathGameDAOImpl extends CustomHibernateDaoSupport implements MathGameDAO {
	static final Logger logger = Logger.getLogger(MathGameDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
		
	@Override
	public boolean submitOrUpdateLevel(MathGameScores mathGameScores){
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(mathGameScores);
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			logger.error("Error in submitGameLevel1() of MathGameDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GameLevel getGameLevel(long gameLevelId){
		List<GameLevel> gameLevelList = new ArrayList<GameLevel>();
		try {
			gameLevelList = (List<GameLevel>) getHibernateTemplate()
					.find("From GameLevel where gameLevelId = "
							+ gameLevelId);
		} catch (HibernateException e) {
			logger.error("Error in getGameLevel() of MathGameDAOImpl", e);
		}
		if(gameLevelList.size()>0){
			return gameLevelList.get(0);
		}else{
			return new GameLevel();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public MathGameScores getMathGameScores(long studentAssignmentId, long mathGearId, long gameLevelId){
		List<MathGameScores> mathGameScoresLt = new ArrayList<MathGameScores>();
		try {
			mathGameScoresLt = (List<MathGameScores>) getHibernateTemplate()
					.find("From MathGameScores where gameLevel.gameLevelId="+gameLevelId+" and mathGear.mathGearId="+mathGearId+" and studentAssignmentStatus.studentAssignmentId="+studentAssignmentId);
		} catch (HibernateException e) {
			logger.error("Error in getMathGameScores() of MathGameDAOImpl", e);
		}
		if(mathGameScoresLt.size()>0){
			return mathGameScoresLt.get(0);
		}else{
			return new MathGameScores();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MathGameScores getMathGameScoresById(long mathGameScoreId){
		List<MathGameScores> mathGameScoresLt = new ArrayList<MathGameScores>();
		try {
			mathGameScoresLt = (List<MathGameScores>) getHibernateTemplate()
					.find("From MathGameScores where mathGameScoreId="+mathGameScoreId);
		} catch (HibernateException e) {
			logger.error("Error in getMathGameScores() of MathGameDAOImpl", e);
		}
		if(mathGameScoresLt.size()>0){
			return mathGameScoresLt.get(0);
		}else{
			return new MathGameScores();
		}
	}
	@Override
	public boolean submitGameAssignment(MathGameScores mathGameScores) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			session.saveOrUpdate(mathGameScores);
			session.saveOrUpdate(mathGameScores.getStudentAssignmentStatus());
			tx.commit();
			session.close();
			return true;
		} catch (HibernateException e) {
			logger.error("Error in submitGameAssignment() of MathGameDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public MathGameScores getPendingMathGameScoreByStudentAssignmentId(long studentAssignmentId) {
		List<MathGameScores> mathGameScores = new ArrayList<MathGameScores>();
		try {
			mathGameScores = (List<MathGameScores>) getHibernateTemplate()
					.find("From MathGameScores where studentAssignmentStatus.studentAssignmentId = "+ studentAssignmentId
							+" and status='"+WebKeys.TEST_STATUS_PENDING+"' order by mathGear.mathGearId, gameLevel.gameLevelId");
		} catch (HibernateException e) {
			logger.error("Error in getPendingMathGameScoreByStudentAssignmentId() of MathGameDAOImpl", e);
		}
		if(!mathGameScores.isEmpty()){
			return mathGameScores.get(0);
		}else{
			return new MathGameScores();
		}
	}
	@Override
	public boolean submitGearGameTest(StudentAssignmentStatus sas){
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			sas.setStatus(WebKeys.TEST_STATUS_SUBMITTED);
			sas.setGradedStatus(WebKeys.GRADED_STATUS_GRADED);
			sas.setSubmitdate(new Date());
			session.saveOrUpdate(sas);
			tx.commit();
			session.close();
		return true;
		} catch (HibernateException e) {
			logger.error("Error in submitGearGameTest() of MathGameDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
	}
}