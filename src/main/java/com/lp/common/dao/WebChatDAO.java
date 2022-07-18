package com.lp.common.dao;

import java.util.List;

import com.lp.custom.exception.DataException;
import com.lp.model.AssignedPtasks;
import com.lp.model.GroupPerformanceTemp;
import com.lp.model.UserRegistration;

public interface WebChatDAO {
	public boolean  saveMessage(AssignedPtasks assingPtasks) throws DataException;
	public AssignedPtasks  getMyMessage(long assingPtasksId) throws DataException;
	public UserRegistration loginToChat(GroupPerformanceTemp groupPerformanceTemp ) throws DataException;
	public List <GroupPerformanceTemp> getPerformanceGroupStudents(long performanceGroupId, long taskId) throws DataException;
	public boolean loginOutChat(GroupPerformanceTemp groupPerformanceTemp ) throws DataException;
	public GroupPerformanceTemp getPerformanceGroupStudentsById(long performanceGroupStudentId) throws DataException;
}
