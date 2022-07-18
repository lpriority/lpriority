package com.lp.common.dao;

import java.sql.SQLDataException;
import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.Activity;
import com.lp.model.AssignActivity;
import com.lp.model.AssignLessons;
import com.lp.model.Assignment;
import com.lp.model.Lesson;
import com.lp.model.LessonPaths;
import com.lp.model.SchoolSchedule;
import com.lp.model.StudentClass;
import com.lp.model.Unit;


public interface CurriculumDAO {	
	public boolean createUnit(Unit unit) throws DataException;
	public boolean updateUnit(Unit unit) throws DataException;
	public List<Unit>  getUnitsbygradeClassId(long gradeClassId) throws DataException;
	public List<Lesson> getLessonssbyUnitId(long unitId) throws DataException;
	public Unit getUnitbyUnitId(long unitId)throws DataException;
	public boolean createLesson(Lesson lesson) throws DataException;
	public boolean updateLesson(Lesson lesson) throws DataException;
	public Activity updateActivity(Activity activity) throws DataException;
	public boolean removeActivity(long activityId);
	public List<Lesson>  getLessonsbygradeClassId(long gradeClassId) throws DataException;
	public boolean saveActivity(Activity activity) throws DataException;
	public Lesson getLessonObj(long lessonId);
	public List<AssignLessons> getAssignedLesson(long csId);
	public List<AssignActivity> getAssignedActivities(long csId);
	public List<Assignment> getAssignedAssignments(long csId);
	public List<LessonPaths> getLessonPaths(long regId);
	public List<Lesson> getLessonssbyUnitIdUserId(long unitId) throws DataException;
	public boolean removeUnit(long unitId) throws DataException;
	public boolean removeLesson(long lessonId)	throws DataException;
	public List<Activity> getActivityByLesson(long lessonId) throws DataException;
	public List<Lesson> getTeacherLessonssbyUnitId(long unitId, long teacherRegId,	long adminRegId) throws DataException;
	public boolean saveActivities(List<Activity> activities) throws DataException;
	public boolean checkLessonExists(long lessonId);
	public boolean checkUnitExists(long unitId);
	public List<Unit>  getTeacherUnits(long gradeClassId, long createdBy) throws DataException;
	public List<Lesson> getUnAssignedLessonssbyUnitId(long unitId, long regId, long gradeId, long classId, SchoolSchedule schedule) throws DataException;
	public SchoolSchedule getSchoolSchedule(long schoolId) throws DataException;
	public List<StudentClass> getTeacherSubjects(long gradeId,long teacherId) throws DataException, SQLDataException;
}
