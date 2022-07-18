package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.AssignmentType;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("assignmentTypeDao")
public class AssignmentTypeDAOImpl extends CustomHibernateDaoSupport implements AssignmentTypeDAO{
	static final Logger logger = Logger.getLogger(AssignmentTypeDAOImpl.class);

	@Override
	public AssignmentType getAssignmentType(long assignmentTypeId) {
		AssignmentType aType= (AssignmentType) super.find(AssignmentType.class, assignmentTypeId);
		  return aType;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentType> getAssignmentTypeList() {
		List<AssignmentType> assignmentTypeList = null;
		assignmentTypeList = (List<AssignmentType>) getHibernateTemplate().find("from AssignmentType order by assignmentTypeId");
		return assignmentTypeList;
	}
	@Override
	public void deleteAssignmentType(long assignmentTypeId) {
		super.delete(getAssignmentType(assignmentTypeId));
	}
	@Override
	public void saveAssignmentType(AssignmentType assignmentType) {
		 super.saveOrUpdate(assignmentType);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AssignmentType> getComprehensionTypes() {
		List<AssignmentType> assignmentTypeList = null;
		assignmentTypeList = (List<AssignmentType>) getHibernateTemplate().find("from AssignmentType where assignmentType in "
				+ "('" +WebKeys.ASSIGNMENT_TYPE_SHORT_ANSWERS +"','"+WebKeys.ASSIGNMENT_TYPE_MULTIPLE_CHOICES+"', '"
				+WebKeys.ASSIGNMENT_TYPE_FILL_IN_THE_BLANKS+"', '"+WebKeys.ASSIGNMENT_TYPE_TRUE_OR_FALSE+"') order by assignmentTypeId");
		return assignmentTypeList;
	}
}
