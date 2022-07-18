package com.lp.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.model.Grade;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("GradesDao")
public class GradesDAOImpl extends CustomHibernateDaoSupport implements
		GradesDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(GradesDAOImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public Grade getSchoolGrade(long gradeId) {
		Session session = sessionFactory.openSession();
		Grade grade = (Grade) session.load(Grade.class, gradeId);
		return grade;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grade> getGradesList() {
		List<Grade> agList = new ArrayList<Grade>();
		try {
			agList = (List<Grade>) getHibernateTemplate().find("from Grade where status='"+WebKeys.ACTIVE+"'");
		} catch (DataAccessException e) {
			logger.error("Error in getGradesList() of GradesDAOImpl" + e);
			e.printStackTrace();
		}
		return agList;
	}

	@Override
	public void deleteGrades(long GradeId) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			Grade aGrades = (Grade) session.load(Grade.class, GradeId);
			session.delete(aGrades);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in deleteGrades() of GradesDAOImpl" + e);
			e.printStackTrace();
		}

	}

	@Override
	public void saveGrades(Grade grade) {
		try {
			super.saveOrUpdate(grade);
		} catch (HibernateException e) {
			logger.error("Error in saveGrades() of GradesDAOImpl" + e);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grade> getGradesList(long schoolId) {
		List<Grade> agList = new ArrayList<Grade>();
		try {
			agList = (List<Grade>) getHibernateTemplate().find(
					"from Grade where status='"+WebKeys.ACTIVE+"' and school_id="
							+ schoolId);
		} catch (DataAccessException e) {
			logger.error("Error in getGradesList() of GradesDAOImpl" + e);
			e.printStackTrace();
		}
		return agList;
	}

	@Override
	public void UpdateGrades(Grade grade) {
		String sql = "UPDATE grade set status = ?,change_date=? where master_grades_id = ? and school_id=?";
		jdbcTemplate.update(
				sql,
				new Object[] { grade.getStatus(), grade.getChangeDate(),
						grade.getMasterGrades().getMasterGradesId(),
						grade.getSchoolId() });
	}

	@Override
	public int checkgradeExists(Grade grade) {
		String query = "select grade_id from grade where school_id="
				+ grade.getSchoolId() + " and master_grades_id="
				+ grade.getMasterGrades().getMasterGradesId() + "";
		int check = 0;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			check = 1;

		}
		return check;
	}

	@Override
	public Grade getGrade(long gradeId) {
		Grade grade = (Grade) super.find(Grade.class, gradeId);
		return grade;
	}

	@Override
	public long getMasterGradeIdbyGradeId(long gradeId) {
		String query = "select master_grades_id from grade where grade_id="
				+ gradeId + "";
		long masgradeId = 0;

		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
			masgradeId = rs.getInt(1);

		}
		return masgradeId;
	}

}
