package com.lp.teacher.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lp.model.TeacherSubjects;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("teacherSubjectsDao")
public class TeacherSubjectsDAOImpl extends CustomHibernateDaoSupport implements
		TeacherSubjectsDAO {
	@Override
	public boolean saveTeacherSubject(TeacherSubjects teacherSub) {
		super.saveOrUpdate(teacherSub);
		return true;
	}

	@Override
	public boolean saveTeacherSubject(List<TeacherSubjects> teacherSubs) {
		for (TeacherSubjects teacherSub : teacherSubs) {
			super.saveOrUpdate(teacherSub);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeacherSubjects> getAllTeacherSubjects() {
		return super.findAll(TeacherSubjects.class);
	}

	@SuppressWarnings("unchecked")
	public List<TeacherSubjects> getTeachersByGrade(long gradeId, long schoolId) {
		List<TeacherSubjects> teacherSubLt = (List<TeacherSubjects>) getHibernateTemplate()
				.find("from TeacherSubjects where grade.gradeId=" + gradeId
						+ " and teacher.userRegistration.school.schoolId="
						+ schoolId);
		return teacherSubLt;
	}
}
