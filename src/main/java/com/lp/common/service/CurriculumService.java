package com.lp.common.service;

import java.sql.SQLDataException;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.Activity;
import com.lp.model.AssignLessons;
import com.lp.model.ClassStatus;
import com.lp.model.Grade;
import com.lp.model.Lesson;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.model.Unit;
import com.lp.model.UserRegistration;


public interface CurriculumService {
	
	
	//This method create units retuns true on successful creation
	public boolean  createUnit(long gradeId,long classId, Unit units,UserRegistration userReg) throws DataException;

	public List<Unit> getUnitsByGradeIdClassId(long gradeId, long classId) throws DataException;

	public boolean createLesson(long unitId, Lesson lesson) throws DataException;

	public List<Grade> getTeacherGrades(Teacher teacherObj) throws DataException;
	
	public List<StudentClass> getAssingedTeacherClasses(long gradeId) throws DataException, SQLDataException;
	
    public List<Lesson> getLessonsByGradeIdClassId(long gradeId,long ClassId) throws DataException;
	
	public boolean saveActivity(Activity activity) throws DataException;
	
	public Activity updateActivity(Activity activity) throws DataException;
	
	public boolean removeActivity(long activityId);

	public Lesson getLessonObj(long lessonId);
	
	public List<ClassStatus> getTeacherSections(Teacher teacher, long gradeId, long classId);
	
	public Unit getUnitbyUnitId(long unitId) throws DataException;	

	public List<AssignLessons> getAssignedCurriculum(long csId, UserRegistration userReg );

	public List<ClassStatus> getAdminSections(long gradeId, long classId) throws DataException;	
	
	public List<Unit> getUnitsByGradeClassUser(long gradeId, long classId) throws DataException;

	public boolean updateUnit(Unit unit, long gradeId, long classId) throws DataException;

	public List<Lesson> getLessonsByUnitIdUserId(long unitId) throws DataException;

	public boolean updateLesson(Lesson lesson) throws DataException;

	public boolean removeUnit(long unitId) throws DataException;
	
	public boolean removeLesson(long lessonId)	throws DataException;

	public List<Activity> getActivityByLesson(long lessonId) throws DataException;

	public boolean saveActivities(List<Activity> activities) throws DataException;
	
	public boolean checkLessonExists(long lessonId) throws DataException;
	
	public boolean checkUnitExists(long unitId) throws DataException;
	
	public List<Grade> getTeacherGradesByAcademicYr(Teacher teacherObj) throws DataException;
	
	public List<StudentClass> getTeacherSubjects(long gradeId,long teacherId) throws DataException, SQLDataException;
}
