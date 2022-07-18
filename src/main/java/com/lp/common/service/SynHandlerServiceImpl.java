package com.lp.common.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.SynHandlerDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.SynHandler;
import com.lp.model.SynHistoryHandler;

@RemoteProxy(name = "synHandlerService")
public class SynHandlerServiceImpl implements SynHandlerService {
	@Autowired
	HttpSession session;
	@Autowired
	private SynHandlerDAO synHandlerDAO;
	
	static final Logger logger = Logger.getLogger(SynHandlerServiceImpl.class);
	
	@Override
	@RemoteMethod
	public boolean synchronizeTab(long synContentId, String status,	String currentTab, String currentElement, String synControl, String updatedVal, String event) throws DataException {
		return synHandlerDAO.synchronizeTab(synContentId, status, currentTab, currentElement, synControl, updatedVal, event);
	}
	
	@Override
	@RemoteMethod
	public boolean closeSynchronizeTab(long synContentId) throws DataException{
		return synHandlerDAO.closeSynchronizeTab(synContentId);
	}
	
	@Override
	@RemoteMethod
	public List<SynHistoryHandler> getAllUsersOnContent(long synContentId) throws DataException{
		return synHandlerDAO.getAllUsersOnContent(synContentId);
	}
	@Override
	public SynHandler getSynHandler(long synContentId, long userRegId) throws DataException {
		return synHandlerDAO.getSynHandler(synContentId, userRegId);
	}
}
