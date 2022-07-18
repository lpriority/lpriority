package com.lp.common.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.SynHandler;
import com.lp.model.SynHistoryHandler;

public interface SynHandlerDAO {
	public boolean synchronizeTab(long synContentId, String status, String currentTab, String currentElement, String synControl, String updatedVal, String event) throws DataException;
	public boolean closeSynchronizeTab(long synContentId) throws DataException;
	public List<SynHistoryHandler> getAllUsersOnContent(long synContentId) throws DataException;
	public SynHandler getSynHandler(long synContentId, long userRegId) throws DataException;
}
