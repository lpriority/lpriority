package com.lp.common.service;

import java.util.ArrayList;
import java.util.List;

import com.lp.model.GradeClasses;
import com.lp.model.Interest;
import com.lp.model.RegisterForClass;
import com.lp.model.SubInterest;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserInterests;
import com.lp.model.UserRegistration;

public interface MyProfileService {

	public boolean updatePersonalInfo(UserRegistration userRegistration);
	public boolean updateUserName(UserRegistration userRegistration);
	public boolean updatePassword(UserRegistration userRegistration);
	public boolean updateHomePage(UserRegistration userRegistration);
	public List<SubInterest> getAllUserPersonalInterests(List<Integer> interestIdsLt);
	public List<UserInterests> getUserPersonalInterests(long regId);
	public Interest getInterestByInterestId(long interestId);
	public boolean updatePersonalInterest(ArrayList<String>  userInterestArray,long regId);
	public List<TeacherSubjects> getTeacherClasses(long teacherId);
	public List<RegisterForClass> getStudentClasses(long studentId);
	public boolean deleteStudentGradeClass(long studentId,long sectionId, long gradeClassId);
	public boolean deleteTeacherGradeClass(long teacherSubjectId);
	public List<GradeClasses> getTeacherGradeClasses(long gradeId,long teacherId);
	public List<GradeClasses> getStudentGradeClasses(long gradeId, long studentId);
	public boolean updateStudentGradeClass(long studentId, long gradeClassId, long gradeLevelId);
	public boolean updateTeacherGradeClass(long teacherSubjectId,long gradeLevelId,int noSectionsPerDay,int noSectionsPerWeek);
	public boolean addTeacherGradeClass(long teacherId,long gradeId,long classId, long gradeLevelId,int noSectionsPerDay,int noSectionsPerWeek);
	public boolean addStudentGradeClass(long studentId, long gradeClassId, long gradeLevelId, long sectionId);
}
