
package com.lp.common.dao;

import java.sql.SQLDataException;
import java.util.List;

import com.lp.model.ClassStatus;

public interface AdminStudentReportsDAO {

	public List<Object[]> getStudentAssignmentsByCsId(long csId, String usedFor,String fromDate,String toDate)
			throws SQLDataException ;
	
	public List<ClassStatus> getSectionTeachers(long sectionId);
}
