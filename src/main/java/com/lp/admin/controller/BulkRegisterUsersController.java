package com.lp.admin.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lp.appadmin.service.AppAdminService;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class BulkRegisterUsersController {
	
	@Autowired
	private  AppAdminService  appAdminService;
	
	static final Logger logger = Logger.getLogger(BulkRegisterUsersController.class);

	
	@RequestMapping(value = "/bulkregisterusers", method = RequestMethod.GET)
	public ModelAndView bulkregisterusers(HttpSession session) {		
		ModelAndView model = new ModelAndView("Admin/bulk_register_users","user",new UserRegistration());
	    return model; 
	}
		
		
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody ModelAndView upload(@RequestParam("userType") String userType, 
            @RequestParam("file") MultipartFile file){
    	ModelAndView model = new ModelAndView("Admin/bulk_register_users","user",new UserRegistration());		    
        if (!file.isEmpty()) {
            try {
            	long check = 0;
            	if(userType.equals(WebKeys.LP_USER_TYPE_TEACHER))
            		check = 3;
            	else if(userType.equals(WebKeys.student))
            		check = 4;
            	
            	if(appAdminService.bulkRegisterUsers(file.getInputStream(), check)){
            		model.addObject("status", "Registered the users successfully !!!");
            		return model;
            	} else {
            		model.addObject("status", "Registration failed !!!");
            		return model;
            	}
            } catch (Exception e) {
            	e.printStackTrace();
            	model.addObject("status", "Registration failed !!!" + e.getMessage());
            	return model;
            }
        } else {
        	model.addObject("status", "Registration failed, because the file uploaded is empty !!!");
        	return model; 
        }
    }
	    
    @RequestMapping(value="/massScheduleStudents", method=RequestMethod.GET)
    public ModelAndView massScheduleStudents(){	 
    	ModelAndView model = new ModelAndView("redirect:/gotoDashboard.htm");
        try {
        	@SuppressWarnings("unused")
			long savedObjects = appAdminService.massScheduleStudents();
        } catch (Exception e) {
			logger.error("Error in massScheduleStudents() of BulkRegisterUsersController"+ e);
        }
    	return model;
    }

    @RequestMapping(value="/updateStudentGradeAndSchool", method=RequestMethod.POST)
    public ModelAndView updateStudentGradeAndSchool(@RequestParam("file") MultipartFile file){	 
    	ModelAndView model = new ModelAndView("redirect:/gotoDashboard.htm");
        try {
        	appAdminService.updateStudentGradeAndSchool(file.getInputStream());
        } catch (Exception e) {
			logger.error("Error in updateStudentGradeAndSchool() of BulkRegisterUsersController"+ e);          	
        }
    	return model;
    }
}
