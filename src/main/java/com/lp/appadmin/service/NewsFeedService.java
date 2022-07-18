package com.lp.appadmin.service;

import java.util.List;

import com.lp.model.LPNews;

public interface NewsFeedService {
	
	public List<LPNews> getLPNews();

	public LPNews getLPNewsById(long lpNewsId);

	public void deleteLPNews(long lpNewsId);

	public void saveLPNews(LPNews lpNews);

}
