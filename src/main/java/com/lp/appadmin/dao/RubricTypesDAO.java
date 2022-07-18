package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.RubricTypes;

public interface RubricTypesDAO {
	
	public RubricTypes getRubric(long rubricTypeId);
	
	public List<RubricTypes> getRubricTypeList();
	
	public void deleteRubricType(long rubricTypeId);
	
	public void saveRubricType(RubricTypes RubricType);

}
