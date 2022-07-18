package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.Country;

public interface CountryDAO {
	
	public Country getCountry(long countryId);
	
	public List<Country> getCountryList();
	
	public void deleteCountry(long countryId);
	
	public void saveCountry(Country country);

}
