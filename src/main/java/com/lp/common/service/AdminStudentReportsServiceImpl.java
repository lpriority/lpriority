package com.lp.common.service;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.admin.dao.AddStudentsToClassDAO;
import com.lp.admin.dao.AdminSchedulerDAO;
import com.lp.common.dao.AdminStudentReportsDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.RegisterForClass;
import com.lp.model.Teacher;

@RemoteProxy(name = "adminStudentReportsService")
public class AdminStudentReportsServiceImpl implements AdminStudentReportsService{
	
	@Autowired
	private AdminSchedulerDAO adminSchedulerdao;
	@Autowired
	private AdminStudentReportsDAO adminStudentReportsDAO;
	@Autowired
	private AddStudentsToClassDAO addStudentsToClassDAO;
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	
	static final Logger logger = Logger.getLogger(AdminStudentReportsServiceImpl.class);
	
	public List<RegisterForClass> getStudentList(long csId)
			throws DataException {
		List<RegisterForClass> studentList = Collections.emptyList();
		try{
			studentList =   addStudentsToClassDAO.getClassStrengthByCsId(csId);
		}catch(DataException e){
			logger.error("Error in getStudentList() of AdminStudentReportsImpl"+ e);
			e.printStackTrace();			
		}
		return studentList;
	}
	@Override
	public long getCsIdBySectionId(long sectionId){
		long csId=0;
		try{
			csId = adminSchedulerdao.getCsIdBySectionId(sectionId);
		}catch(DataException e){
			logger.error("Error in getStudentList() of AdminStudentReportsImpl"+ e);
			e.printStackTrace();	
		}
		return csId;
	}
	@Override
	public HashMap<Long,Double> getStudentAssignmentsByCsId(long csId, String usedFor,String from,String to)
			throws DataException {
		List<Object[]> studentAssignments = new ArrayList<Object[]>();
		HashMap<Long,Double> hm=new HashMap<Long,Double>();  
		try{
			studentAssignments =   adminStudentReportsDAO.getStudentAssignmentsByCsId(csId, usedFor,from,to);
			for(Object[] o : studentAssignments){
				hm.put((Long) o[0], (Double) o[1]);
			}
		}catch(DataException | SQLDataException e){
			logger.error("Error in getStudentAssignmentsByCsId() of GradeBookServiceImpl"+ e);
			e.printStackTrace();			
		}
		return hm;
	}
	@Override
	public String getTeacherName(long sectionId) {
		long csId = adminSchedulerdao.getCsIdBySectionId(sectionId);
		String teacherFullName = "No teacher is scheduled for this class";
		if(csId > 0){
			long teacherId = adminSchedulerdao.getclassStatus(csId).getTeacher().getTeacherId();
			Teacher teacher = adminSchedulerdao.getTeacher(teacherId);
			String teacherTitle = teacher.getUserRegistration().getTitle();
			if(teacherTitle == null){
				teacherTitle = "";
			}
			String teacherFirstName = teacher.getUserRegistration().getFirstName();
			String teacherLastName = teacher.getUserRegistration().getLastName();
			teacherFullName = teacherTitle+". "+teacherFirstName +" "+teacherLastName;
		}
		
		return teacherFullName;
	}
	@Override
	public List<ClassStatus> getSectionTeachers(long sectionId){
		List<ClassStatus> teacherList=null;
		try{
			
			teacherList= adminStudentReportsDAO.getSectionTeachers(sectionId);
		}catch(DataException e){
			logger.error("Error in getStudentAssignmentsByCsId() of GradeBookServiceImpl"+ e);
			e.printStackTrace();			
		}
		return teacherList;
	}

	
}
