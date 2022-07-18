package com.lp.admin.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.lp.custom.exception.DataException;
import com.lp.model.RegisterForClass;


public interface AddStudentsToClassService {
	
	public List<RegisterForClass> getStudentList(long gradeId,long classId,long csId) throws DataException;

	//This method returns true only when Students successfully added to Class
	public boolean  addStudentToClass(HttpSession session, long studentId, long gClassId,
			long csId) throws DataException;
	
	//This method returns true only when Students removed from Class
	public boolean removeStudentFromClass(long studentId, long gClassId,long csId) throws DataException;

	public String getTeacherName(long sectionId);
}
