package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.Interest;

public interface IneterestDAO {
	
	public Interest getInterest(long interestId);
	
	public Interest getInterest(String interest);
	
	public List<Interest> getInterestList();
	
	public void deleteInterest(long interestId);
	
	public void saveInterest(Interest interest);

}
