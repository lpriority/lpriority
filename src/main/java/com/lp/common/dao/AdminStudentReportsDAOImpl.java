
package com.lp.common.dao;

import java.sql.SQLDataException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("adminStudentReportsDAO")
public class AdminStudentReportsDAOImpl extends CustomHibernateDaoSupport implements AdminStudentReportsDAO {

	@Autowired
	public void setdataSource(DataSource datasource) {
		new JdbcTemplate(datasource);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object[]> getStudentAssignmentsByCsId(long csId, String usedFor,String fromDate,String toDate)
			throws SQLDataException {
		List studentAssignments = Collections.emptyList();
		try {
			studentAssignments = (List<Object[]>) getHibernateTemplate()
					.find("select student.studentId, avg(percentage) From StudentAssignmentStatus where student.studentId != null "
							+ "and assignment.usedFor = '"+ usedFor + "' and assignment.classStatus.csId="+ csId +" and assignment.dateAssigned>='"+fromDate+"'"
					+ " and assignment.dateDue<='"+toDate+"'GROUP BY student.studentId");
		} catch (HibernateException e) {
			logger.error("Error in getStudentAssignmentsByCsId() of GradeBookDAOImpl", e);
			throw new DataException("Error in getStudentAssignmentsByCsId() of GradeBookDAOImpl",e);
			
		}
		return studentAssignments;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassStatus> getSectionTeachers(long sectionId){
		List<ClassStatus> teachers = null;
		try {
			teachers = (List<ClassStatus>) getHibernateTemplate().find(
					"from ClassStatus where section.sectionId=" + sectionId
							+ " and availStatus='"+WebKeys.AVAILABLE+"'");
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return teachers;
	}
}

	
