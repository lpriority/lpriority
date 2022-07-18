package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.Gradeevents;

public interface GradeEventsDAO {
	
	public Gradeevents getGradeEvent(long eventId);
	
	public List<Gradeevents> getGradeEventList();
	
	public void deleteGradeEvent(long eventId);
	
	public void saveGradeEvent(Gradeevents academicGrade);

}
