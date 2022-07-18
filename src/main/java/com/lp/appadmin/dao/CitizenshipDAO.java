package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.Citizenship;

public interface CitizenshipDAO {
	
	public Citizenship getCitizenship(long citizenshipId);
	
	public List<Citizenship> getCitizenshipList();
	
	public void deleteCitizenship(long citizenshipId);
	
	public void saveCitizenship(Citizenship citizenship);

}
