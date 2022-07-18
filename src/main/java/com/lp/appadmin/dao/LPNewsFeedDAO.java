package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.LPNews;

public interface LPNewsFeedDAO {
	public List<LPNews> getLPNews();

	public LPNews getLPNewsById(long lpNewsId);

	public void deleteLPNews(long lpNewsId);

	public void saveLPNews(LPNews lpNews);

}
