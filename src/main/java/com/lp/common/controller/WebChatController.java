package com.lp.common.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.common.service.WebChatService;
import com.lp.custom.exception.DataException;
import com.lp.model.AssignedPtasks;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;


/**
 * 
 * @author PRASAD BHVN & SANTHOSH
 *
 */
	
@Controller
public class WebChatController {
	
	static final Logger logger = Logger.getLogger(WebChatController.class);
	
	@Autowired
	private WebChatService webChatService;
               
	@RequestMapping("/loginToChat")
	public ModelAndView login(@RequestParam("pGroupStudentId") long pGroupStudentId,HttpSession session) {		
		ModelAndView model = new ModelAndView("WebChat/web_chat");
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		webChatService.loginToChat(pGroupStudentId);
		model.addObject("user", userReg);
		return model;
	}

	@RequestMapping("/logOutChat")
	public ModelAndView logout(@RequestParam("pGroupStudentId") long pGroupStudentId,HttpSession session) {		
		ModelAndView model = new ModelAndView("WebChat/web_chat");
		webChatService.loginOutChat(pGroupStudentId);
		return model;
	}

	@RequestMapping("/saveMessage")
	public View sendMessage(@RequestParam("taskId") long taskId,@RequestParam("pGroupStudentId") long pGroupStudentId,
			@RequestParam("usermsg") String usermsg,@RequestParam("randomColor") String randomColor,HttpServletRequest request, HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String sender = userReg.getLastName() + "&nbsp; "
				+ userReg.getFirstName();
		webChatService.saveMessage(sender, taskId, usermsg, randomColor);
		return new MappingJackson2JsonView();
	}

	@RequestMapping("/getMyMessages")
	public View getMyMessages(@RequestParam("taskId") long taskId,@RequestParam("pGroupStudentId") long pGroupStudentId,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<AssignedPtasks> messages = new ArrayList<>();
		AssignedPtasks assignedPtask = null;
		try {
			assignedPtask = webChatService.getMyMessage(taskId, pGroupStudentId);
			if(assignedPtask.getChatcontents() == null)
				assignedPtask.setChatcontents("");
			messages.add(assignedPtask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("messages", messages);
		return new MappingJackson2JsonView();
	}


	@RequestMapping("/getOnlineUsers")
	public View getOnlineUsers(@RequestParam("pGroupId") long pGroupId,@RequestParam("taskId") long taskId,
			HttpServletRequest request,	HttpServletResponse response, Model model) {
		List<UserRegistration> onlineUsers = Collections.emptyList();
		try {
			onlineUsers = webChatService.getOnlineUsers(pGroupId,taskId);
		} catch (DataException e) {
		}
		model.addAttribute("onlineUsers", onlineUsers);
		return new MappingJackson2JsonView();

	}
             
}
