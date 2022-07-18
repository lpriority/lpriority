package com.lp.appadmin.dao;

import java.util.List;



import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lp.model.LPNews;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

public class LPNewsFeedDAOImpl extends CustomHibernateDaoSupport implements LPNewsFeedDAO {
	static final Logger logger = Logger.getLogger(LPNewsFeedDAOImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<LPNews> getLPNews(){
		List<LPNews> lpNews = null;
		try{
			lpNews = (List<LPNews>) getHibernateTemplate().find("FROM LPNews where status='"+WebKeys.ACTIVE+"' order by lpNewsId desc");			
		}
		catch(Exception e){
			logger.error("Error in getLPNews() of LPNewsFeedDAOImpl"+ e);
		}
		return lpNews;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LPNews getLPNewsById(long lpNewsId){
		List<LPNews> lpNews = null;
		LPNews news  = null;
		try{
			lpNews = (List<LPNews>) getHibernateTemplate().find("FROM LPNews WHERE lpNewsId="+lpNewsId);		
			if(!lpNews.isEmpty()){
				news = lpNews.get(0);
			}
		}
		catch(Exception e){
			logger.error("Error in getLPNewsById() of LPNewsFeedDAOImpl"+ e);
		}
		return news;
	}
	@SuppressWarnings("rawtypes")
	public void deleteLPNews(long lpNewsId){
		Transaction tx = null;
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			tx = session.beginTransaction();
			Query q = session.createQuery("update LPNews set status=:status where lpNewsId=:lpNewsId");
			q.setParameter("status", WebKeys.LP_STATUS_INACTIVE);
			q.setParameter("lpNewsId", lpNewsId);
			q.executeUpdate();
			tx.commit();
			session.close();
		}
		catch(Exception e){
			tx.rollback();
			logger.error("Error in deleteLPNews() of LPNewsFeedDAOImpl"+ e);
			e.printStackTrace();
		}
	
	}
	
	@Override
	public void saveLPNews(LPNews lpNews){
		try{
			getHibernateTemplate().saveOrUpdate(lpNews);
		}
		catch(Exception e){
			logger.error("Error in saveLPNews() of LPNewsFeedDAOImpl"+ e);
		}
	}
	

}
