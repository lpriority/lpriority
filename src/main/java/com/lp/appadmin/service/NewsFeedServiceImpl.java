package com.lp.appadmin.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.appadmin.dao.LPNewsFeedDAO;
import com.lp.model.LPNews;

public class NewsFeedServiceImpl implements NewsFeedService {
	static final Logger logger = Logger.getLogger(NewsFeedServiceImpl.class);
	@Autowired
	private LPNewsFeedDAO newsFeedDAO;
	
	@Override
	public List<LPNews> getLPNews(){
		List<LPNews> lpNews = null;
		try{
			lpNews = newsFeedDAO.getLPNews();
		}
		catch(Exception e){
			logger.error("Error in getLPNews() of NewsFeedServiceImpl"+ e);
		}
		return lpNews;
	}
	
	@Override
	public LPNews getLPNewsById(long lpNewsId){
		LPNews news  = new LPNews();
		try{
			news = newsFeedDAO.getLPNewsById(lpNewsId);
		}
		catch(Exception e){
			logger.error("Error in getLPNewsById() of NewsFeedServiceImpl"+ e);
		}
		return news;
	}
	
	@Override
	public void deleteLPNews(long lpNewsId){
		try{
			newsFeedDAO.deleteLPNews(lpNewsId);
		}
		catch(Exception e){
			logger.error("Error in deleteLPNews() of NewsFeedServiceImpl"+ e);
		}
	}
	
	@Override
	public void saveLPNews(LPNews lpNews){
		try{
			newsFeedDAO.saveLPNews(lpNews);
		}
		catch(Exception e){
			logger.error("Error in saveLPNews() of NewsFeedServiceImpl"+ e);
		}
	}
}
