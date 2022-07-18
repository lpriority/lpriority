package com.lp.common.service;

import java.util.HashMap;
import java.util.List;

import com.lp.model.ClassStatus;
import com.lp.model.RegisterForClass;

public interface AdminStudentReportsService {
	public List<RegisterForClass> getStudentList(long csId);
	public long getCsIdBySectionId(long sectionId);
	public HashMap<Long,Double> getStudentAssignmentsByCsId(long csId, String usedFor,String from,String to);
	public String getTeacherName(long sectionId);
	public List<ClassStatus> getSectionTeachers(long sectionId); 
}
