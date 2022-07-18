package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.States;

public interface StatesDAO {

	public States getState(long stateId);

	public List<States> getStatesList();

	public List<States> getStatesList(long countryId);

	public List<States> loadStatesList(long countryId);
	
	public void deleteState(long stateId);

	public void saveState(States state);
	
	public long getCountrybyStateId(long stateId);

}
