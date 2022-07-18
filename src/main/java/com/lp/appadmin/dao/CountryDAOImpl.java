package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.Country;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("countryDao")
public class CountryDAOImpl extends CustomHibernateDaoSupport implements
		CountryDAO {
	static final Logger logger = Logger.getLogger(CountryDAOImpl.class);

	@Override
	public Country getCountry(long countryId) {
		Country country = (Country) super.find(Country.class, countryId);
		return country;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getCountryList() {
		List<Country> countryList = null;
		countryList = (List<Country>) super.findAll(Country.class);
		return countryList;
	}

	@Override
	public void deleteCountry(long countryId) {
		super.delete(getCountry(countryId));
	}

	@Override
	public void saveCountry(Country country) {
		super.saveOrUpdate(country);
	}

}
