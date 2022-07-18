package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.RubricTypes;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("RubricTypeDao")
public class RubricTypesDAOImpl extends CustomHibernateDaoSupport implements
		RubricTypesDAO {
	
	static final Logger logger = Logger.getLogger(RubricTypesDAOImpl.class);

	@Override
	public RubricTypes getRubric(long rubricTypeId) {
		RubricTypes rtypes= (RubricTypes) super.find(RubricTypes.class, rubricTypeId);
		return rtypes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RubricTypes> getRubricTypeList() {
		List<RubricTypes> RubricTypeList = null;
		RubricTypeList = (List<RubricTypes>) super.findAll(RubricTypes.class);
		return RubricTypeList;
	}

	@Override
	public void deleteRubricType(long rubricTypeId) {
		RubricTypes rtypes =getRubric(rubricTypeId);
		super.delete(rtypes);
	}

	@Override
	public void saveRubricType(RubricTypes rubricType) {
		super.saveOrUpdate(rubricType);
	}
}
