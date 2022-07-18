package com.lp.common.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.common.dao.WebChatDAO;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignedPtasks;
import com.lp.model.GroupPerformanceTemp;
import com.lp.model.UserRegistration;

@RemoteProxy(name = "webChatService")
public class WebChatServiceImpl implements WebChatService {

	@Autowired
	private WebChatDAO webChatDAO;
	
	static final Logger logger = Logger.getLogger(WebChatServiceImpl.class);

	@Override
	public List<UserRegistration> getOnlineUsers(long performanceGroupId,long taskId)
			throws DataException {
		// TODO Auto-generated method stub
		List<UserRegistration> onLineUsers = new ArrayList<UserRegistration>();
		try{
			List<GroupPerformanceTemp> pgUsers = webChatDAO.getPerformanceGroupStudents(performanceGroupId,taskId);
			for(GroupPerformanceTemp pgUser: pgUsers)
				if(pgUser.getChatLoginStatus()!=null)
					if(pgUser.getChatLoginStatus().equalsIgnoreCase("login"))
						onLineUsers.add(pgUser.getPerformanceGroupStudents().getStudent().getUserRegistration());
		}catch(DataException e){
			
		}
		return onLineUsers;
	}

	@Override
	public boolean saveMessage(String sender,long taskId,String message, String randomColor)
			throws DataException {
		
		  String months[] = {
			      "Jan", "Feb", "Mar", "Apr",
			      "May", "Jun", "Jul", "Aug",
			      "Sep", "Oct", "Nov", "Dec"};
			      
		 GregorianCalendar gcalendar = new GregorianCalendar();
		 String am_pm="AM";
		 if(gcalendar.get(Calendar.AM_PM)==1)
			 am_pm= "PM";
		 
		// TODO Auto-generated method stub
		AssignedPtasks assingPtasks = webChatDAO.getMyMessage(taskId);
		String mychatContent = assingPtasks.getChatcontents();
		if(mychatContent == null)
			mychatContent = "";
		String preMsg = "<font size='3' color='"+randomColor+"'><b><i>"+sender +"</i></b></font></br><font size='2'>"+message+"</font></br>"
				+ "<font size='1' color='#767676' >"+months[gcalendar.get(Calendar.MONTH)] +" " + gcalendar.get(Calendar.DATE)+"&nbsp;at&nbsp;"
				+gcalendar.get(Calendar.HOUR) + ":"+gcalendar.get(Calendar.MINUTE) +"&nbsp;"+ am_pm+"</font></br>";
		assingPtasks.setChatcontents(mychatContent+preMsg);
		webChatDAO.saveMessage(assingPtasks);
		return false;
	}

	@Override
	public AssignedPtasks getMyMessage(long taskId, long pGroupStudentId) {
		// TODO Auto-generated method stub
		AssignedPtasks assignedPtasks = new AssignedPtasks();
		GroupPerformanceTemp pgStudent = new GroupPerformanceTemp();
		pgStudent = webChatDAO.getPerformanceGroupStudentsById(pGroupStudentId);
		if(pgStudent.getChatLoginStatus().equalsIgnoreCase("login"))
					assignedPtasks =  webChatDAO.getMyMessage(taskId);
		
		return assignedPtasks;
	}

	@Override
	public UserRegistration loginToChat(long pGroupStudentId)
			throws DataException {
		UserRegistration loginUser;
		GroupPerformanceTemp pgStudent = new GroupPerformanceTemp();
		// TODO Auto-generated method stub
		try{
			pgStudent = webChatDAO.getPerformanceGroupStudentsById(pGroupStudentId);
			
			//set student login status
			pgStudent.setChatLoginStatus("login");
			//save student status
			loginUser = webChatDAO.loginToChat(pgStudent);
		}catch(Exception e){
			logger.error("Error in loginToChat() of WebChatServiceImpl"+ e);
			throw new DataException("Error in loginToChat() of WebChatServiceImpl",e);
		}
		
		return loginUser;
	}

	@Override
	public boolean loginOutChat(long pGroupStudentId)
			throws DataException {
		GroupPerformanceTemp pgStudent = new GroupPerformanceTemp();
		// TODO Auto-generated method stub
		try{
			pgStudent = webChatDAO.getPerformanceGroupStudentsById(pGroupStudentId);
			pgStudent.setChatLoginStatus("logout");
			webChatDAO.loginToChat(pgStudent);
		}catch(Exception e){
			logger.error("Error in loginToChat() of WebChatServiceImpl"+ e);
			throw new DataException("Error in loginToChat() of WebChatServiceImpl",e);
		}
		
		return true;
	}	
}