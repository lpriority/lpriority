package com.lp.teacher.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lp.teacher.service.CAASPPService;
import com.lp.utils.WebKeys;

@Controller
public class CAASPPController {

	static final Logger logger = Logger.getLogger(CAASPPController.class);
	@Autowired
	private CAASPPService caasppService;

	@RequestMapping(value = "/addCAASPPScores",  headers="content-type=multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody void goToAssignAssessment( @RequestParam("file") MultipartFile file, HttpServletResponse response) {
		if (!file.isEmpty()) {
			try {
				boolean status = caasppService.addCAASPPScores(file.getInputStream());
				if(status){
					response.getWriter().write( WebKeys.UPLOAD_LOGO_SUCCESS);
				}
				else{
					response.getWriter().write( WebKeys.UPLOAD_LOGO_FAILURE);  
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}
		}		
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/html");  
	}
	
	
	@RequestMapping(value = "/uploadCAASPPScores", method = RequestMethod.GET)
	public ModelAndView uploadCAASPPScores() {
		return new ModelAndView("Admin/upload_caaspp_scores");		 
	}	
}
