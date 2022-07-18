package com.lp.common.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lp.admin.dao.AdminDAO;
import com.lp.common.dao.MyProfileDAO;
import com.lp.model.GradeClasses;
import com.lp.model.Interest;
import com.lp.model.RegisterForClass;
import com.lp.model.SubInterest;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserInterests;
import com.lp.model.UserRegistration;
import com.lp.teacher.dao.CommonDAO;
import com.lp.utils.WebKeys;

@RemoteProxy(name = "myProfileService")
public class MyProfileServiceImpl implements MyProfileService {

	@Autowired
	private MyProfileDAO myProfileDAO;
	@Autowired
	private CommonDAO commonDAO;
	@Autowired
	private AdminDAO adminDAO;
	
	static final Logger logger = Logger.getLogger(MyProfileServiceImpl.class);
	
	@Override
	@Transactional
	public boolean updatePersonalInfo(UserRegistration userRegistration) {
		return myProfileDAO.updatePersonalInfo(userRegistration);
	}

	@Override
	@Transactional
	public boolean updateUserName(UserRegistration userRegistration) {
		return myProfileDAO.updateUserName(userRegistration);
	}

	@Override
	@Transactional
	public boolean updatePassword(UserRegistration userRegistration) {
		return myProfileDAO.updatePassword(userRegistration);
	}

	@Override
	@Transactional
	public boolean updateHomePage(UserRegistration userRegistration) {
		return myProfileDAO.updateHomePage(userRegistration);
	}

	@Override
	public List<SubInterest> getAllUserPersonalInterests(List<Integer> interestIdsLt) {
		return myProfileDAO.getAllUserPersonalInterests(interestIdsLt);
	}

	@Override
	public List<UserInterests> getUserPersonalInterests(long regId) {
		return myProfileDAO.getUserPersonalInterests(regId);
	}

	@Override
	public Interest getInterestByInterestId(long interestId) {
		return myProfileDAO.getInterestByInterestId(interestId);
	}

	@Override
	@Transactional
	public boolean updatePersonalInterest(ArrayList<String> userInterestArray, long regId) {
		return  myProfileDAO.updatePersonalInterest(userInterestArray, regId);
	}

	@Override
	public List<TeacherSubjects> getTeacherClasses(long teacherId) {
		return myProfileDAO.getTeacherClasses(teacherId);
	}

	@Override
	@RemoteMethod
	public List<RegisterForClass> getStudentClasses(long studentId) {
		return  myProfileDAO.getStudentClasses(studentId);
	}

	@Override
	@RemoteMethod
	public boolean deleteStudentGradeClass(long studentId, long sectionId, long gradeClassId) {
		return myProfileDAO.deleteStudentGradeClass(studentId, sectionId, gradeClassId);
	}

	@Override
	@RemoteMethod
	public boolean deleteTeacherGradeClass(long teacherSubjectId) {
		return myProfileDAO.deleteTeacherGradeClass(teacherSubjectId);
	}

	@Override
	public List<GradeClasses> getTeacherGradeClasses(long gradeId, long teacherId) {
		List<TeacherSubjects> teacherSubjectsLt = new ArrayList<TeacherSubjects>();
		List<GradeClasses> gradeClassesLt = new ArrayList<GradeClasses>();
		teacherSubjectsLt = myProfileDAO.getTeacherGradeClasses(gradeId, teacherId);
		for (TeacherSubjects teacherSubjects : teacherSubjectsLt) {
			if(!teacherSubjects.getStudentClass().getClassName().equalsIgnoreCase(WebKeys.LP_CLASS_HOMEROME))
				gradeClassesLt.add(commonDAO.getTeacherGradeClassess(gradeId, teacherSubjects.getStudentClass().getClassId()));
		}
		return gradeClassesLt;
	}

	@Override
	public List<GradeClasses> getStudentGradeClasses(long gradeId, long studentId) {
		return myProfileDAO.getStudentGradeClasses(gradeId, studentId);
	}

	@Override
	@RemoteMethod
	public boolean updateStudentGradeClass(long studentId, long gradeClassId, long gradeLevelId) {
		long sectionId = adminDAO.getSectionIdByGradeClassIdAndGradeLevelId(gradeClassId, gradeLevelId);
		return myProfileDAO.updateStudentGradeClass(studentId, sectionId, gradeClassId, gradeLevelId);
	}

	@Override
	@RemoteMethod
	public boolean updateTeacherGradeClass(long teacherSubjectId, long gradeLevelId, int noSectionsPerDay, int noSectionsPerWeek) {
		return myProfileDAO.updateTeacherGradeClass(teacherSubjectId, gradeLevelId, noSectionsPerDay, noSectionsPerWeek);
	}

	@Override
	public boolean addTeacherGradeClass(long teacherId, long gradeId, long classId, long gradeLevelId, int noSectionsPerDay, int noSectionsPerWeek) {
		return myProfileDAO.addTeacherGradeClass(teacherId, gradeId, classId, gradeLevelId, noSectionsPerDay, noSectionsPerWeek);
	}

	@Override
	public boolean addStudentGradeClass(long studentId, long gradeClassId, long gradeLevelId, long sectionId) {
		return myProfileDAO.addStudentGradeClass(studentId, gradeClassId, gradeLevelId, sectionId);
	}

}
