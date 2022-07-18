package com.lp.common.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.lp.model.Assignment;
import com.lp.model.Student;
import com.lp.model.TaskForceResult;


public interface ReadingSkillsService {
	public void expireCurrentFiles();
	public File[] getStudentRRDashboardFiles(Student student);	
	public void renameFiles();
	public File[] getSpecialDashboardFiles();
	public List<TaskForceResult> getTaskForceResults(long fluencyId, long comId);
	public HashMap<String, List<Assignment>> getTaskForceTitles(long csId, String usedFor);	
}
