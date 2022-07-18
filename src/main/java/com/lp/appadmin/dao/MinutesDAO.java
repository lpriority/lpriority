package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.Minutes;

public interface MinutesDAO {
	
	public Minutes getMinute(long minutesId);
	
	public List<Minutes> getMinutesList();
	
	public void deleteMinute(long minutesId);
	
	public void saveMinute(Minutes minutes);

}
