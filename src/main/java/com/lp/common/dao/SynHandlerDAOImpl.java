package com.lp.common.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lp.custom.exception.DataException;
import com.lp.model.SynHandler;
import com.lp.model.SynHistoryHandler;
import com.lp.model.UserRegistration;
import com.lp.utils.CustomHibernateDaoSupport;
import com.lp.utils.WebKeys;

@Repository("synHandlerDAO")
public class SynHandlerDAOImpl extends CustomHibernateDaoSupport  implements SynHandlerDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	HttpSession session;

	@Autowired
	public void setdataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);

	}
	static final Logger logger = Logger.getLogger(SynHandlerDAOImpl.class);


	@Override
	public boolean synchronizeTab(long synContentId, String status, String currentTab, String currentElement, String synControl, String updatedVal, String event) throws DataException {
		boolean success = true;
		SynHandler sh = null;
		SynHistoryHandler shh = null;
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		try {
			if(userReg != null){
				    long regId = userReg.getRegId();
					closeSynchronizeTab(synContentId);
					sh = getSynHandler(synContentId, regId);
					if(sh.getSynHandlerId() == 0){
						sh = new SynHandler();
						sh.setStatus(WebKeys.ACTIVE);
						sh.setSynContentId(synContentId);
						sh.setUserRegistration(userReg);
						session.saveOrUpdate(sh);
					}
					if(currentTab.length() > 0){
						if(currentElement.length() > 0){
							shh = getSynHistoryHandlerCurrentTab(synContentId, regId, currentTab, currentElement);
							if(shh.getSynHistoryHandlerId() > 0){
								if(status.equalsIgnoreCase(WebKeys.LP_STATUS_INACTIVE)){
			                	    String query1 = "update syn_history_handler set syn_status='"+WebKeys.ACTIVE+"' where syn_handler_id="+sh.getSynHandlerId()+" and current_tab='"+currentTab+"' and current_element=''";
								    jdbcTemplate.update(query1);
								}
								if(!shh.getBeforeUpdate().equalsIgnoreCase(updatedVal)){
									String updateQuery = "update syn_history_handler set syn_status='"+status+"', current_element='"+currentElement+"', after_update='"+updatedVal+"', content_status='"+WebKeys.LP_CHANGED+"', syn_control='"+WebKeys.LP_YES+"' where syn_history_handler_id="+shh.getSynHistoryHandlerId();
									jdbcTemplate.update(updateQuery);
							    	return success;
								}else{
									String updateQuery = "update syn_history_handler set syn_status='"+status+"', current_element='"+currentElement+"', content_status='"+ WebKeys.LP_NOT_CHANGED+"', syn_control='"+WebKeys.LP_YES+"' where syn_history_handler_id="+shh.getSynHistoryHandlerId();
									jdbcTemplate.update(updateQuery);
							    	return success;
								}
							}else{
								shh = new SynHistoryHandler();
								shh.setSynHandler(sh);
								shh.setCurrentTab(currentTab);
								shh.setSynStatus(status);
								shh.setCurrentElement(currentElement);
								shh.setSynControl(synControl);
								shh.setBeforeUpdate(updatedVal);
								
								GregorianCalendar cal = new GregorianCalendar();
								long millis = cal.getTimeInMillis();
								Timestamp currentTimeStamp = new Timestamp(millis);
								shh.setTimeStamp(currentTimeStamp);
								
								session.saveOrUpdate(shh);
								tx.commit();
							}
						}else{
							shh = getSynHistoryHandlerCurrentTab(synContentId, regId, currentTab, "");
							if(shh.getSynHistoryHandlerId() > 0){
								String query = "update syn_history_handler set syn_status='"+status+"' where syn_history_handler_id="+shh.getSynHistoryHandlerId();
							    jdbcTemplate.update(query);
							}else{
								shh = new SynHistoryHandler();
								shh.setSynHandler(sh);
								shh.setCurrentTab(currentTab);
								shh.setSynStatus(status);
								shh.setCurrentElement(currentElement);
								shh.setSynControl(synControl);
								shh.setBeforeUpdate(updatedVal);
								
								GregorianCalendar cal = new GregorianCalendar();
								long millis = cal.getTimeInMillis();
								Timestamp currentTimeStamp = new Timestamp(millis);
								shh.setTimeStamp(currentTimeStamp);
								
								session.saveOrUpdate(shh);
								tx.commit();
							}
					 }
				}
			}
		} catch (HibernateException e) {
			success = false;
			logger.error("Error in synchronizeTab() of SynHandlerDAOImpl" + e);
		}
		return success;
	}
	
	@SuppressWarnings("unchecked")
	public SynHandler getSynHandler(long synContentId, long userRegId) throws DataException {
		List<SynHandler> synHandlerLt = new ArrayList<SynHandler>();
		try {
			synHandlerLt = (List<SynHandler>) getHibernateTemplate().find("from SynHandler where synContentId="+ synContentId+" and userRegistration.regId="+userRegId);
		} catch (Exception e) {
			logger.error("Error in getSynHandler() of SynHandlerDAOImpl"+ e);
			e.printStackTrace();
		}
		if(synHandlerLt.size() > 0){
			return synHandlerLt.get(0);
		}else{
			return new SynHandler();
		}
	}

	@SuppressWarnings("unchecked")
	public SynHistoryHandler getSynHistoryHandlerCurrentTab(long synContentId, long regId, String currentTab, String currentElement) throws DataException {
		List<SynHistoryHandler> synHistoryHandlerLt = new ArrayList<SynHistoryHandler>();
		try {
			if(currentTab.length() > 0)
				synHistoryHandlerLt = (List<SynHistoryHandler>) getHibernateTemplate().find("from SynHistoryHandler where synHandler.synContentId="+ synContentId+" and synHandler.userRegistration.regId="+regId+" and current_tab= '"+currentTab+"' and current_element = '"+currentElement+"'");
			} catch (Exception e) {
			logger.error("Error in getSynHistoryHandlerCurrentTab() of SynHandlerDAOImpl"+ e);
			e.printStackTrace();
		}
		if(synHistoryHandlerLt.size() > 0){
			return synHistoryHandlerLt.get(0);
		}else{
			return new SynHistoryHandler();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean closeSynchronizeTab(long synContentId) throws DataException {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		if(userReg != null){
			List<SynHistoryHandler> synHistoryHandlerLt = new ArrayList<SynHistoryHandler>();
			try {
				if(synContentId > 0){
					synHistoryHandlerLt = (List<SynHistoryHandler>) getHibernateTemplate().find("from SynHistoryHandler where synHandler.synContentId="+ synContentId+" and synHandler.userRegistration.regId="+userReg.getRegId()+" and syn_status='"+WebKeys.ACTIVE+"'");
				}
				if(synHistoryHandlerLt.size() > 0){
					for (SynHistoryHandler synHistoryHandler : synHistoryHandlerLt) {
						 String query = "update syn_history_handler set syn_status='"+WebKeys.LP_STATUS_INACTIVE+"' where syn_history_handler_id="+synHistoryHandler.getSynHistoryHandlerId();
						 jdbcTemplate.update(query);
					}
				}
			} catch (Exception e) {
				logger.error("Error in closeSynchronizeTab() of SynHandlerDAOImpl"+ e);
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<SynHistoryHandler> getAllUsersOnContent(long synContentId) throws DataException {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		List<SynHistoryHandler> synHistoryHandlerLt = new ArrayList<SynHistoryHandler>();
		if(userReg != null){
		   try {
				synHistoryHandlerLt = (List<SynHistoryHandler>) getHibernateTemplate().find("from SynHistoryHandler where synHandler.synContentId="+ synContentId+"  and synHandler.userRegistration.regId !="+userReg.getRegId()+" and syn_status='"+WebKeys.ACTIVE+"'");
			} catch (Exception e) {
				logger.error("Error in getAllUsersOnContent() of SynHandlerDAOImpl"+ e);
				e.printStackTrace();
			}
		}
		return synHistoryHandlerLt;
	}
}
