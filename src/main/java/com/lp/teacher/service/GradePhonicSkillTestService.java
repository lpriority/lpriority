package com.lp.teacher.service;

import com.lp.model.StudentPhonicTestMarks;

public interface GradePhonicSkillTestService {	
	public boolean submitStudentPhonicTestMarks(long studentAssignmentId, long groupId,long lastSavedSetId, int totalMarks, int secmarks, String marksStr, String commentStr);
	public StudentPhonicTestMarks getStudentPhonicTestMarksByGroupId(long studentAssignmentId, long groupId) ;
	public long getLastSavedGroupId(long studentAssignmentId);
}
