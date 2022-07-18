package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.Days;

public interface DaysDAO {

	public Days getDay(long dayId);

	public List<Days> getDaysList();

	public void deleteDay(long dayId);

	public void saveDay(Days day);

}
