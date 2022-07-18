package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.RtiGroups;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("RTIGroupsDao")
public class RTIGroupsDAOImpl extends CustomHibernateDaoSupport implements
		RTIGroupsDAO {
	static final Logger logger = Logger.getLogger(RTIGroupsDAOImpl.class);

	@Override
	public RtiGroups getRTIGroup(long rtiGroupId) {
		RtiGroups rtiGroups= (RtiGroups) super.find(RtiGroups.class, rtiGroupId);
		return rtiGroups;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RtiGroups> getRTIGroupsList() {
		List<RtiGroups> rtiGroupsList = null;
		rtiGroupsList = (List<RtiGroups>) super.findAll(RtiGroups.class);
		return rtiGroupsList;
	}

	@Override
	public void deleteRTIGroup(long rtiGroupId) {
		super.delete((RtiGroups)getRTIGroup(rtiGroupId));
		
	}

	@Override
	public void saveRTIGroup(RtiGroups rtiGroups) {
		super.saveOrUpdate(rtiGroups);
	}
}
