package com.lp.teacher.service;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.model.BpstTypes;
import com.lp.model.RegisterForClass;
import com.lp.model.StudentPhonicTestMarks;
import com.lp.teacher.dao.PhonicTestReportsDAO;
import com.lp.teacher.dao.TeacherDAO;

@RemoteProxy(name = "phonicTestReportsService")
public class PhonicTestReportsServiceImpl implements PhonicTestReportsService {
	@Autowired
	private TeacherDAO teacherDAO;
	@Autowired
	private PhonicTestReportsDAO phonicTestReportsDAO;
	
	@Override
	@RemoteMethod
	public List<RegisterForClass> getStudentsByCsId(long csId) {
		return teacherDAO.getStudentsByCsId(csId);
	}

	@Override
	@RemoteMethod
	public List<StudentPhonicTestMarks> getStudentPhonicTestMarks(long studentId, long csId, long langId, long bpstTypeId) {
		return phonicTestReportsDAO.getStudentPhonicTestMarks(studentId, csId, langId, bpstTypeId);
	}

	@Override
	@RemoteMethod
	public List<StudentPhonicTestMarks> getStudentPhonicTestMarksByStudentAssignmentId(long studentAssignmentId) {
		return phonicTestReportsDAO.getStudentPhonicTestMarksByStudentAssignmentId(studentAssignmentId);
	}
	
	@Override
	@RemoteMethod
	public  List<BpstTypes> getBpstTypes(){
		return phonicTestReportsDAO.getBpstTypes();
	}
}
