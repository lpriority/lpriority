package com.lp.teacher.service;

import java.util.List;

import com.lp.model.BpstTypes;
import com.lp.model.RegisterForClass;
import com.lp.model.StudentPhonicTestMarks;

public interface PhonicTestReportsService {

	public List<RegisterForClass> getStudentsByCsId(long csId);
	public  List<StudentPhonicTestMarks>  getStudentPhonicTestMarks(long studentId,long sectionId, long langId, long bpstTypeId);
	public List<StudentPhonicTestMarks> getStudentPhonicTestMarksByStudentAssignmentId(long studentAssignmentId);
	public  List<BpstTypes>  getBpstTypes();
}
