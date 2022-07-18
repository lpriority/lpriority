package com.lp.admin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.GradeClasses;
import com.lp.model.School;
import com.lp.model.StudentClass;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("StudentClass")
public class StudentClassesDAOImpl extends CustomHibernateDaoSupport implements
		StudentClassesDAO {
	
	static final Logger logger = Logger.getLogger(StudentClassesDAOImpl.class);
	
	@Override
	public void saveStudentclasses(StudentClass studentClass) {
		super.saveOrUpdate(studentClass);
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentClass> getClasses(School school) {
		List<StudentClass> list = (List<StudentClass>) getHibernateTemplate()
				.find("from StudentClass where school.schoolId="
						+ school.getSchoolId());
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GradeClasses> getGradeClasses(long schoolId, long gradeId) {
		List<GradeClasses> list = null;
		try {
			list = (List<GradeClasses>) getHibernateTemplate().find(
					"from GradeClasses where studentClass.school.schoolId="
							+ schoolId + " and grade.gradeId=" + gradeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public StudentClass getClass(long classId) {
		StudentClass clss = new StudentClass();
		try {
			clss = (StudentClass) super.find(StudentClass.class, classId);
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}
		return clss;
	}
}
