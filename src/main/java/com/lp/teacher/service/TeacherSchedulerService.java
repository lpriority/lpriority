package com.lp.teacher.service;

import java.util.List;

import com.lp.model.ClassActualSchedule;
import com.lp.model.Section;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.TeacherSubjects;
import com.lp.model.UserRegistration;

public interface TeacherSchedulerService {
	
	public List<UserRegistration> getTeachersByReg(long schoolId, long userTypeId);
	public List<Teacher> getTeachers(long gradeId, long classId);
	public List<Section> getSections(long gradeId, long classId);
	public List<ClassActualSchedule> getCSIds(long gradeId, long classId, long teacherId, long schoolId);
	public boolean planSchedule(long gradeId, long classId, long teacherId, long dayId, long schoolId,String startDate, String endDate, String periodIds,String sectionIds,String casIds);
	public boolean checkTeacherAvailability(long gradeId, long classId, long teacherId, long dayId,long periodId);
	public boolean checkSectionAvailabilityforTeacher(long sectionId,long teacherId);
	public List<TeacherSubjects> getTeachersRequests(long schoolId);
	public boolean setTeacherReplyAction(long gradeId, long classId, long teacherId, String status);
	public List<StudentClass> getStudentClasses(long gradeId);
}
