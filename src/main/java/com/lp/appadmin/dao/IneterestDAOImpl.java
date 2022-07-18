package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.Interest;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("ineterestDAO")
public class IneterestDAOImpl extends CustomHibernateDaoSupport implements
		IneterestDAO {
	static final Logger logger = Logger.getLogger(IneterestDAOImpl.class);

	@Override
	public Interest getInterest(long interestId) {
		Interest in = (Interest) super.find(Interest.class, interestId);
		return in;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Interest getInterest(String interest) {
		List<Interest> in = (List<Interest>) getHibernateTemplate().find(
				"from Interest where lcase(interest)='" + interest+"'");
	if (in.size() > 0)
			return in.get(0);
		else
			return new Interest();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Interest> getInterestList() {
		List<Interest> interestList = null;
		interestList = (List<Interest>) super.findAll(Interest.class);
		return interestList;
	}

	@Override
	public void deleteInterest(long interestId) {
		super.delete(getInterest(interestId));
	}

	@Override
	public void saveInterest(Interest interest) {
		super.saveOrUpdate(interest);
	}
}
