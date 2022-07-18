package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.Minutes;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("minutesDao")
public class MinutesDAOImpl extends CustomHibernateDaoSupport implements
		MinutesDAO {
	static final Logger logger = Logger.getLogger(MinutesDAOImpl.class);
	
	@Override
	public Minutes getMinute(long minuteId) {
		Minutes min = (Minutes) super.find(Minutes.class, minuteId);
		return min;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Minutes> getMinutesList() {
		List<Minutes> minutesList = null;
		minutesList = (List<Minutes>) super.findAll(Minutes.class);
		return minutesList;
	}

	@Override
	public void deleteMinute(long minuteId) {
		super.delete(getMinute(minuteId));
	}

	@Override
	public void saveMinute(Minutes minute) {
		super.saveOrUpdate(minute);
	}
}
