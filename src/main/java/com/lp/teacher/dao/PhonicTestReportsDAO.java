package com.lp.teacher.dao;

import java.util.List;

import com.lp.model.BpstTypes;
import com.lp.model.StudentPhonicTestMarks;

public interface PhonicTestReportsDAO {
	public  List<StudentPhonicTestMarks>  getStudentPhonicTestMarks(long studentId,long csId, long langId, long bpstTypeId);
	public List<StudentPhonicTestMarks> getStudentPhonicTestMarksByStudentAssignmentId(long studentAssignmentId);
	public  List<BpstTypes>  getBpstTypes();
}
