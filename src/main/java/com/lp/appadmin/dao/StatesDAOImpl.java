package com.lp.appadmin.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lp.model.States;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("statesDao")
public class StatesDAOImpl extends CustomHibernateDaoSupport implements
		StatesDAO {
	static final Logger logger = Logger.getLogger(StatesDAOImpl.class);

	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setdataSource(DataSource datasource){
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	@Override
	public States getState(long stateId) {
		States state= (States) super.find(States.class, stateId);
		return state;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<States> getStatesList() {
		List<States> statesList = null;
		statesList = (List<States>) super.findAll(States.class);
		return statesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<States> getStatesList(long countryId) {
		List<States> statesList = null;
		statesList = (List<States>) getHibernateTemplate().find(
				"from States where country.countryId =" + countryId);
		return statesList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<States> loadStatesList(long countryId) {
		List<States> statesList = null;
		statesList = (List<States>) getHibernateTemplate().load(States.class,countryId);
		return statesList;
	}

	@Override
	public void deleteState(long stateId) {
		States state = getState(stateId);
		super.delete(state);
	}

	@Override
	public void saveState(States state) {
		super.saveOrUpdate(state);
	}
	
	@Override
	public long getCountrybyStateId(long stateId){
		long countryId =   jdbcTemplate.queryForObject("select country_id from states where state_id= "+stateId, new Object[] {}, Long.class);  //jdbcTemplate.queryForLong("select country_id from states where state_id= "+stateId);
		return countryId;
	}
}
