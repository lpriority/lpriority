package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.AssignmentType;

public interface AssignmentTypeDAO {
	
	public AssignmentType getAssignmentType(long userTypeId);
	
	public List<AssignmentType> getAssignmentTypeList();
	
	public void deleteAssignmentType(long assignmentTypeId);
	
	public void saveAssignmentType(AssignmentType assignmentType);
	
	public List<AssignmentType> getComprehensionTypes();

}
