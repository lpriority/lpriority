package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.RtiGroups;

public interface RTIGroupsDAO {
	
	public RtiGroups getRTIGroup(long rtiGroupId);
	
	public List<RtiGroups> getRTIGroupsList();
	
	public void deleteRTIGroup(long RTIGroupsId);
	
	public void saveRTIGroup(RtiGroups RTIGroups);

}
