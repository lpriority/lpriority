package com.lp.common.service;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignedPtasks;
import com.lp.model.UserRegistration;

public interface WebChatService {
	public List<UserRegistration> getOnlineUsers(long gruopId, long taskId) throws DataException;
	public boolean saveMessage(String sender,long taskId,String message, String randomColor) throws DataException;
	public AssignedPtasks getMyMessage(long taskId, long pGroupStudentId);
	public UserRegistration loginToChat(long pGroupStudentId) throws DataException;
	public boolean loginOutChat(long pGroupStudentId) throws DataException ;
}
