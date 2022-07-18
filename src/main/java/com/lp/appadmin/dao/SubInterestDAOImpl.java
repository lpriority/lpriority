package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.SubInterest;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("subInterestDao")
public class SubInterestDAOImpl extends CustomHibernateDaoSupport implements SubInterestDAO{
	
	static final Logger logger = Logger.getLogger(SubInterestDAOImpl.class);

	@Override
	public SubInterest getSubInterest(long subInterestId) {
		SubInterest subInterest= (SubInterest) super.find(SubInterest.class, subInterestId);
		 return subInterest;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubInterest> getSubInterestList() {
		List<SubInterest> subInterestList = null;
		subInterestList = (List<SubInterest>) super.findAll(SubInterest.class);
		return subInterestList;
	}

	@Override
	public void deleteSubInterest(long subInterestId) {
		  SubInterest subInterest= getSubInterest(subInterestId);
		  super.delete(subInterest);
	}

	@Override
	public void saveSubInterest(SubInterest subInterest) {
		super.saveOrUpdate(subInterest);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubInterest> getSubInterests(long interestId){
		List<SubInterest> subInterestList = null;
		subInterestList = (List<SubInterest>) getHibernateTemplate().find("from SubInterest where interest.interestId="+interestId);
		return subInterestList;
	}
}
