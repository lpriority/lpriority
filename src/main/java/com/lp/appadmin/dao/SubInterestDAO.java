package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.SubInterest;

public interface SubInterestDAO {

	public SubInterest getSubInterest(long subInterestId);

	public List<SubInterest> getSubInterestList();

	public void deleteSubInterest(long subInterestId);

	public void saveSubInterest(SubInterest subInterest);

	public List<SubInterest> getSubInterests(long interestId);

}
