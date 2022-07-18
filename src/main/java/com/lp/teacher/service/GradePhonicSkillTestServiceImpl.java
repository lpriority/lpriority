package com.lp.teacher.service;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.model.StudentPhonicTestMarks;
import com.lp.teacher.dao.GradePhonicSkillTestDAO;

@RemoteProxy(name = "gradePhonicSkillTestService")
public class GradePhonicSkillTestServiceImpl implements
		GradePhonicSkillTestService {

	@Autowired
	private GradePhonicSkillTestDAO gradePhonicSkillTestDAO;
	
	public boolean submitStudentPhonicTestMarks(long studentAssignmentId, long groupId, long lastSavedSetId, int totalMarks, int secmarks, String marksStr, String commentStr){
		return gradePhonicSkillTestDAO.submitStudentPhonicTestMarks(studentAssignmentId, groupId, lastSavedSetId, totalMarks, secmarks, marksStr, commentStr);
	}

	@Override
	@RemoteMethod
	public StudentPhonicTestMarks getStudentPhonicTestMarksByGroupId(
			long studentAssignmentId, long groupId) {
		return gradePhonicSkillTestDAO.getStudentPhonicTestMarksByGroupId(studentAssignmentId, groupId);
	}
	
	@Override
	@RemoteMethod
	public long getLastSavedGroupId(long studentAssignmentId){
		return gradePhonicSkillTestDAO. getLastSavedGroupId(studentAssignmentId);
	}
}
