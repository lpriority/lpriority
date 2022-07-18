package com.lp.student.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.ClassStatus;
import com.lp.model.RegisterForClass;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("registerForClassDAO")
public class RegisterForClassDAOImpl extends CustomHibernateDaoSupport implements
		RegisterForClassDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getAllRegisterForClass(){
		List<RegisterForClass> regForClasses = (List<RegisterForClass>) getHibernateTemplate().find("FROM RegisterForClass where classStatus_1='"+WebKeys.ALIVE+"'");
		return regForClasses;
	}
	
	@Override
	public boolean saveRegisterForClass(List<RegisterForClass> registerForClasses){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		long i = 0;
		try{
		for (RegisterForClass rfc : registerForClasses) {
			tx = session.beginTransaction();			
			session.saveOrUpdate(rfc.getStudent().getUserRegistration());
			session.saveOrUpdate(rfc.getStudent());
			session.saveOrUpdate(rfc);
			i++;
			if (i % 20 == 0) { // 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				session.flush();
				session.clear();
			}
			tx.commit();
		}
		
		}
		catch(HibernateException e){
			tx.rollback();
			e.printStackTrace();
		}
		session.close();
		return true;
	}
	@Override
	public boolean expireAllClasses(long studentId) throws DataException{
		boolean updateStuas = false;
		try {
			String query = "update register_for_class set class_status='"+WebKeys.EXPIRED+"' where student_id=?";

			int out = jdbcTemplate.update(query, studentId);
			if (out != 0) {
				updateStuas = true;				
			} else {
				updateStuas = false; 
			}

		} catch (DataAccessException e) {
			logger.error("Error in expireAllClasses() of RegisterForClassDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Unexpected System error....");
		}
		return updateStuas;
	}
	@Override
	public boolean expireAllClasses(UserRegistration userRegistration) throws DataException{
		boolean updateStuas = false;
		try {
			String query = "update register_for_class set class_status='"+WebKeys.EXPIRED+"' where student_id = (select student_id from student where reg_id=?)";

			int out = jdbcTemplate.update(query, userRegistration.getRegId());
			if (out != 0) {
				updateStuas = true;				
			} else {
				updateStuas = false; 
			}

		} catch (DataAccessException e) {
			logger.error("Error in expireAllClasses() of RegisterForClassDAOImpl" + e);
			e.printStackTrace();
			throw new DataException("Unexpected System error....");
		}
		return updateStuas;
	}
	
	@Override
	public boolean uploadClassRosters(List<RegisterForClass> registerForClasses){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		long i = 0;
		try{
		for (RegisterForClass rfc : registerForClasses) {
			
			tx = session.beginTransaction();
			
			session.saveOrUpdate(rfc.getStudent().getUserRegistration());
			session.saveOrUpdate(rfc.getStudent());
			session.saveOrUpdate(rfc);
			
			i++;
			if (i % 20 == 0) { // 20, same as the JDBC batch size
				// flush a batch of inserts and release memory:
				session.flush();
				session.clear();
			}
			tx.commit();
		}
		
		}
		catch(HibernateException e){
			tx.rollback();
			e.printStackTrace();
		}
		session.close();
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassStatus> getScheduledClasses(){
		List<ClassStatus> classStatus = new ArrayList<ClassStatus>();
		classStatus = (List<ClassStatus>) getHibernateTemplate().find("from ClassStatus where availStatus='"+WebKeys.AVAILABLE+"'");
		return classStatus;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterForClass> getNewStudents(){
		List<RegisterForClass> studentList = (List<RegisterForClass>) getHibernateTemplate().find("FROM RegisterForClass where classStatus_1='"+WebKeys.ALIVE +"' and status='"+WebKeys.LP_STATUS_NEW+"' and teacher.teacherId is not null");
		return studentList;
	}
	
	@Override
	public boolean saveRegisterForClasses(List<RegisterForClass> registerForClasses){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		long i = 0;
		try{
			for (RegisterForClass rfc : registerForClasses) {
				tx = session.beginTransaction();
				session.saveOrUpdate(rfc);
				i++;
				if (i % 20 == 0) { // 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
				tx.commit();
			}		
		}
		catch(HibernateException e){
			tx.rollback();
			e.printStackTrace();
		}
		session.close();
		return true;
	}

	
}
