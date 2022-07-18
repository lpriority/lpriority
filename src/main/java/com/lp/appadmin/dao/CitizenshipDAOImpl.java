package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.Citizenship;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("citizenshipDao")
public class CitizenshipDAOImpl extends CustomHibernateDaoSupport implements
		CitizenshipDAO {
	static final Logger logger = Logger.getLogger(CitizenshipDAO.class);

	@Override
	public Citizenship getCitizenship(long citizenshipId) {
		Citizenship cShip = (Citizenship) super.find(Citizenship.class,
				citizenshipId);
		return cShip;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Citizenship> getCitizenshipList() {
		List<Citizenship> citizenshipList = null;
		citizenshipList = (List<Citizenship>) super.findAll(Citizenship.class);
		return citizenshipList;
	}

	@Override
	public void deleteCitizenship(long citizenshipId) {
		super.delete(getCitizenship(citizenshipId));
	}

	@Override
	public void saveCitizenship(Citizenship citizenship) {
		super.saveOrUpdate(citizenship);
	}
}
