package com.lp.admin.dao;

import java.util.List;

import com.lp.model.GradeClasses;
import com.lp.model.School;
import com.lp.model.StudentClass;

public interface StudentClassesDAO {

	public void saveStudentclasses(StudentClass studentClass);

	public List<StudentClass> getClasses(School school);

	public List<GradeClasses> getGradeClasses(long schoolId, long gradeId);
	
	public StudentClass getClass(long classId);
}
