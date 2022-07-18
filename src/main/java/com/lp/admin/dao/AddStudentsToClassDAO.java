package com.lp.admin.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.ClassActualSchedule;
import com.lp.model.Grade;
import com.lp.model.RegisterForClass;
import com.lp.model.Student;

public interface AddStudentsToClassDAO {

	public Grade getSchoolGrade(long gradeId);

	public List<Grade> getGradesList();

	public void deleteGrades(long gradeId);

	public void saveGrades(Grade Grade);

	public List<Grade> getGradesList(long schoolId);

	public void UpdateGrades(Grade grade);

	public int checkgradeExists(Grade grade);

	public Grade getGrade(long gradeId);
	
	public long getMasterGradeIdbyGradeId(long gradeId);

	public List<RegisterForClass> getAllClassStudents(long gradeLevelId,
			long gradeclassId, long csId) throws DataException;

	public boolean addStudentToClass(long studentId, long gClassId,long csId) throws DataException;

	public boolean removeStudentFromClass(long studentId, long gClassId,long csId);

	public List<ClassActualSchedule> getDayIdPeriodIds(long csId) throws DataException;
	
	public List<RegisterForClass> getClassStrengthByCsId(long csId) throws DataException;
	
	public Student getStudent(long studentId) throws DataException;

	public List<RegisterForClass> getAllClassStudentsWithoutLevel(long gradeclassId, long csId) throws DataException; 
}
