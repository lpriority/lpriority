package com.lp.common.dao;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.BenchmarkCategories;
import com.lp.model.BenchmarkCutOffMarks;
import com.lp.model.BenchmarkDirections;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("benchmarkcutoff")

public class BenchmarkCutOffMarksDAOImpl extends CustomHibernateDaoSupport implements BenchmarkCutOffMarksDAO{

	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	
	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BenchmarkCategories> getBenchmarkCategories(){
		List<BenchmarkCategories> benchmarkCategories = Collections.emptyList();
		try{
			benchmarkCategories = (List<BenchmarkCategories>) getHibernateTemplate().find("from BenchmarkCategories where isFluencyTest=1");
		}catch (HibernateException e) {
			logger.error("Error in getBenchmarkCategories() of BenchmarkCutOffMarksDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getBenchmarkCategories() of BenchmarkCutOffMarksDAOImpl",e);
		}
		return benchmarkCategories;
		 
	 }
	@SuppressWarnings("unchecked")
	@Override
	 public List<BenchmarkCutOffMarks> getBenchmarkCutOffMarks(long gradeId)throws DataException{
		 List<BenchmarkCutOffMarks> benchmarkCutOffMarks = Collections.emptyList();
			try{
				benchmarkCutOffMarks = (List<BenchmarkCutOffMarks>) getHibernateTemplate().find("from BenchmarkCutOffMarks where grade.gradeId="+gradeId+"");
			}catch (HibernateException e) {
				logger.error("Error in getBenchmarkCutOffMarks() of BenchmarkCutOffMarksDAOImpl" + e);
				e.printStackTrace();
				throw new DataException("Error in getBenchmarkCutOffMarks() of BenchmarkCutOffMarksDAOImpl",e);
			}
			return benchmarkCutOffMarks;
	 }
	
	@SuppressWarnings("unchecked")
	@Override
	 public BenchmarkCategories getbenchmarkCategories(long benchmarkCategoryId){
		List<BenchmarkCategories> benchmarkCategories = Collections.emptyList();
		try{
			benchmarkCategories = (List<BenchmarkCategories>) getHibernateTemplate().find("from BenchmarkCategories where benchmarkCategoryId="+benchmarkCategoryId+"");
		}catch (HibernateException e) {
			logger.error("Error in getbenchmarkCategories() of BenchmarkCutOffMarksDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getbenchmarkCategories() of BenchmarkCutOffMarksDAOImpl",e);
		}
		if (benchmarkCategories.size() > 0)
			return benchmarkCategories.get(0);
		else
			return new BenchmarkCategories();
	 }
	@Override
	public boolean setBenchmarkCutOff(List<BenchmarkCutOffMarks> benchmarkCutOffs){
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			for(int i=0;i<benchmarkCutOffs.size();i++)
			session.saveOrUpdate(benchmarkCutOffs.get(i));
			tx.commit();
			session.close();
			
		} catch (Exception e) {
			logger.error("Error in setBenchmarkCutOff() of BenchmarkCutOffMarksDAOImpl" + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean checkBenchmarkCutOffExists(long gradeId){
		String query = "select * from bm_ss_cut_off_marks where grade_id="+gradeId+"";
		boolean check = false;
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query);
		if (rs.next()) {
          	check = true;
		}
		return check;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BenchmarkCategories> getMainBenchmarkTestTypes(){
		List<BenchmarkCategories> benchmarkCategories = Collections.emptyList();
		try{
			benchmarkCategories = (List<BenchmarkCategories>) getHibernateTemplate().find("from BenchmarkCategories order by isFluencyTest ");
		}catch (HibernateException e) {
			logger.error("Error in getMainBenchmarkTestTypes() of BenchmarkCutOffMarksDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getMainBenchmarkTestTypes() of BenchmarkCutOffMarksDAOImpl",e);
		}
		return benchmarkCategories;
		 
	 }
	@SuppressWarnings("unchecked")
	@Override
	public List<BenchmarkDirections> getBenchmarkDirections(){
		List<BenchmarkDirections> benchmarkDirections = Collections.emptyList();
		try{
			benchmarkDirections = (List<BenchmarkDirections>) getHibernateTemplate().find("from BenchmarkDirections");
		}catch (HibernateException e) {
			logger.error("Error in getBenchmarkDirections() of BenchmarkCutOffMarksDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Error in getBenchmarkDirections() of BenchmarkCutOffMarksDAOImpl",e);
		}
		return benchmarkDirections;
		 
	 }
}
