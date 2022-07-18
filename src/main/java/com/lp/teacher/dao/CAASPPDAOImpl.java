package com.lp.teacher.dao;

import java.util.HashSet;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataIntegrityViolationException;

import com.lp.model.CAASPPScores;
import com.lp.model.Grade;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

public class CAASPPDAOImpl extends CustomHibernateDaoSupport implements
		CAASPPDAO {
	@Override
	public boolean addCAASPPScores(HashSet<CAASPPScores> caasppScores) {
		Transaction transaction = null;
		try {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			transaction = session.beginTransaction();
			for (CAASPPScores scores : caasppScores) {
				try{
					session.save(scores);
				}
				catch (DataIntegrityViolationException ex) {
					session.flush();
					//ex.printStackTrace();
				}
			}
			transaction.commit();
		} catch (HibernateException ex) {
			transaction.rollback();
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getActiveStudents() {
		List<Student> students = null;
		students = (List<Student>) getHibernateTemplate().find("FROM Student WHERE gradeStatus='"+WebKeys.ACTIVE+"' and studentScId is not null");
		return students;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> getActiveTeachers() {
		List<Teacher> teachers = null;
		teachers = (List<Teacher>) getHibernateTemplate().find("FROM Teacher WHERE status='"+WebKeys.ACTIVE+"' and teacherScId is not null");
		return teachers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grade> getActiveGrades() {
		List<Grade> grades = null;
		grades = (List<Grade>) getHibernateTemplate().find("FROM Grade WHERE status='"+WebKeys.ACTIVE+"' order by schoolId");
		return grades;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<School> getSchools() {
		List<School> students = null;
		students = (List<School>) getHibernateTemplate().find("FROM School order by schoolName");
		return students;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentClass> getActiveClasses() {
		List<StudentClass> classes = null;
		classes = (List<StudentClass>) getHibernateTemplate().find("FROM StudentClass order by school.schoolId");
		return classes;
	}
	
}
