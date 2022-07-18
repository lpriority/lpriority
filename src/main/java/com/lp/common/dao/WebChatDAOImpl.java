package com.lp.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignedPtasks;
import com.lp.model.GroupPerformanceTemp;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("WebChat")
public class WebChatDAOImpl extends CustomHibernateDaoSupport implements WebChatDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(WebChatDAOImpl.class);


	@Override
	public boolean saveMessage(AssignedPtasks assingPtasks)
			throws DataException {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
				session.saveOrUpdate(assingPtasks);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in getMyMessage() of WebChatDAOImpl" + e);
			e.printStackTrace();
			throw new DataException(
					"Error in getMyMessage() of WebChatDAOImpl", e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AssignedPtasks getMyMessage(long assingPtasksId)
			throws DataException {
		// TODO Auto-generated method stub
		List<AssignedPtasks> assignedPtasks = new ArrayList<AssignedPtasks>();
		try {
			assignedPtasks = (List<AssignedPtasks>) getHibernateTemplate().find(
					"from AssignedPtasks where assignedTaskId=" + assingPtasksId);
		} catch (DataAccessException e) {
			logger.error("Error in getMyMessage() of WebChatDAOImpl"+ e);
			throw new DataException("Error in getMyMessage() of WebChatDAOImpl",e);
		}
		if(assignedPtasks.size()>0)
			return assignedPtasks.get(0);
		else{
			logger.error("Performance Task doesn't exist getMyMessage() of WebChatDAOImpl"					);
			throw new DataException("Performance Task doesn't exist getMyMessage() of WebChatDAOImpl");
		}
	}


	@SuppressWarnings("rawtypes")
	@Override
	public UserRegistration loginToChat(GroupPerformanceTemp groupPerformanceTemp )
			throws DataException {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = (Transaction) session.beginTransaction();
			String hql = "UPDATE GroupPerformanceTemp set chatLoginStatus = :chatLoginStatus "
					+ "WHERE performanceGroupStudents.performanceGroupStudentsId = :performanceGroupStudentsId";
			Query query = session.createQuery(hql);
			query.setParameter("chatLoginStatus",groupPerformanceTemp.getChatLoginStatus());
			query.setParameter("performanceGroupStudentsId",groupPerformanceTemp.getPerformanceGroupStudents().getPerformanceGroupStudentsId());
			query.executeUpdate();			
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			logger.error("Error in loginToChat() of WebChatDAOImpl" + e);
			throw new DataException(
					"Error in loginToChat() of WebChatDAOImpl", e);
		}
		return groupPerformanceTemp.getPerformanceGroupStudents().getStudent().getUserRegistration();
	}	
		
		@SuppressWarnings("rawtypes")
		@Override
		public boolean loginOutChat(GroupPerformanceTemp groupPerformanceTemp )
				throws DataException {
			// TODO Auto-generated method stub
			try {
				Session session = sessionFactory.openSession();
				Transaction tx = (Transaction) session.beginTransaction();
				String hql = "UPDATE GroupPerformanceTemp set chatLoginStatus = :chatLoginStatus "
						+ "WHERE performanceGroupStudents.performanceGroupStudentsId = :performanceGroupStudentsId";
				Query query = session.createQuery(hql);
				query.setParameter("chatLoginStatus",groupPerformanceTemp.getChatLoginStatus());
				query.setParameter("performanceGroupStudentsId",groupPerformanceTemp.getPerformanceGroupStudents().getPerformanceGroupStudentsId());
				query.executeUpdate();
				tx.commit();
				session.close();
			} catch (HibernateException e) {
				logger.error("Error in loginToChat() of WebChatDAOImpl" + e);
				e.printStackTrace();
				throw new DataException(
						"Error in loginToChat() of WebChatDAOImpl", e);
			}
			return true;
	}


		@SuppressWarnings("unchecked")
		@Override
		public List<GroupPerformanceTemp> getPerformanceGroupStudents(
				long performanceGroupId,long taskId) throws DataException {
			// TODO Auto-generated method stub
			List<GroupPerformanceTemp> pgStudents = new ArrayList<GroupPerformanceTemp>();
			try {
				pgStudents = (List<GroupPerformanceTemp>) getHibernateTemplate().find(
						"from GroupPerformanceTemp where performanceGroupStudents.performancetaskGroups.performanceGroupId=" + performanceGroupId+" "
								+ "and assignedPtasks.assignedTaskId="+taskId);
			} catch (DataAccessException e) {
				logger.error("Error in getPerformanceGroupStudents() of WebChatDAOImpl"+ e);
				throw new DataException("Error in getPerformanceGroupStudents() of WebChatDAOImpl",e);
			}
			if(pgStudents.size()>0){
					return pgStudents;
			}
			else{
				logger.error("Performance group student  doesn't exist getPerformanceGroupStudents() of WebChatDAOImpl");
				throw new DataException("Error in getMyMessage() of WebChatDAOImpl");
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public GroupPerformanceTemp getPerformanceGroupStudentsById(
				long performanceGroupStudentId) throws DataException {
			// TODO Auto-generated method stub
			List<GroupPerformanceTemp> pgStudents = new ArrayList<GroupPerformanceTemp>();
			try {
				pgStudents = (List<GroupPerformanceTemp>) getHibernateTemplate().find(
						"from GroupPerformanceTemp where performanceGroupStudents.performanceGroupStudentsId=" + performanceGroupStudentId);
			} catch (DataAccessException e) {
				logger.error("Error in getPerformanceGroupStudentsById() of WebChatDAOImpl"+ e);
				throw new DataException("Error in getPerformanceGroupStudentsById() of WebChatDAOImpl",e);
			}
			if(pgStudents.size()>0){
					return pgStudents.get(0);
			}
			else{
				logger.error("Performance group student  doesn't exist getPerformanceGroupStudentsById() of WebChatDAOImpl");
				throw new DataException("Error in getPerformanceGroupStudentsById() of WebChatDAOImpl");
			}
		}
	
}
