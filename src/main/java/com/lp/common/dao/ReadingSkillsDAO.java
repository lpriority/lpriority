package com.lp.common.dao;

import java.util.List;

import com.lp.model.Assignment;
import com.lp.model.BenchmarkResults;
import com.lp.model.StudentAssignmentStatus;

public interface ReadingSkillsDAO {
	public List<BenchmarkResults> getFluencyResults(long fluencyId);
	public List<StudentAssignmentStatus> getComprehensionResults(long comId);
	public List<Assignment> getTaskForceTitles(long csId, String usedFor);		
}
