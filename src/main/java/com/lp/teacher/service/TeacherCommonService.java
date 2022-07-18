package com.lp.teacher.service;

import java.util.Date;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.Activity;
import com.lp.model.CreateUnits;
import com.lp.model.Grade;
import com.lp.model.Lesson;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.Unit;
import com.lp.model.UserRegistration;


public interface TeacherCommonService {
	public Teacher getTeacher(UserRegistration userReg);
	public List<Grade> getTeacherAssignedGrades(Teacher teacher);
	public List<StudentClass> getTeacherClasses(Teacher teacher, long gradeId);
	public List<Unit> getUnits(long gradeId, long classId, Teacher teacher);
	public List<Lesson> getLessonsByUnit(long unitId);
	public List<Activity> getActivitiesByLesson(long lessonId);
	public boolean  assignCurriculum(long csId, CreateUnits assignCurriculum, Date dueDate) throws DataException;
	public CreateUnits getTeacherCurriculum(long gradeId, long classId, Teacher teacher);	
	public List<Grade> getTeacherGradesByAcademicYr(Teacher teacher);	
}
