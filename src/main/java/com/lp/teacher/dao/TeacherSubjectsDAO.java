package com.lp.teacher.dao;


import java.util.List;

import com.lp.model.TeacherSubjects;

public interface TeacherSubjectsDAO {
	public boolean saveTeacherSubject(TeacherSubjects teacherSub);
	public boolean saveTeacherSubject(List<TeacherSubjects> teacherSub);
	
	public List<TeacherSubjects> getAllTeacherSubjects();
	public List<TeacherSubjects> getTeachersByGrade(long gradeId, long schoolId);
}
