package com.lp.teacher.dao;

import java.util.HashSet;
import java.util.List;

import com.lp.model.CAASPPScores;
import com.lp.model.Grade;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;

public interface CAASPPDAO {
	public boolean addCAASPPScores(HashSet<CAASPPScores> caasppScores);
	public List<Student> getActiveStudents();
	public List<Teacher> getActiveTeachers();
	public List<Grade> getActiveGrades();
	public List<School> getSchools();
	public List<StudentClass> getActiveClasses();
}
