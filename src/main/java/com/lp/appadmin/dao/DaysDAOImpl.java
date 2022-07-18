package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.Days;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("daysDao")
public class DaysDAOImpl extends CustomHibernateDaoSupport implements DaysDAO {

	static final Logger logger = Logger.getLogger(DaysDAOImpl.class);

	@Override
	public Days getDay(long dayId) {
		Days day=(Days) super.find(Days.class, dayId);
		return day;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Days> getDaysList() {
		List<Days> daysList = null;
		daysList = (List<Days>) super.findAll(Days.class);
		return daysList;
	}

	@Override
	public void deleteDay(long dayId) {
		super.delete(getDay(dayId));
	}

	@Override
	public void saveDay(Days day) {
		super.saveOrUpdate(day);
	}
}
