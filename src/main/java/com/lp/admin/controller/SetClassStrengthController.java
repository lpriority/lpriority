package com.lp.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.CommonService;
import com.lp.model.School;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class SetClassStrengthController {

	@Autowired
	private CommonService reportService;
	@Autowired
	private AdminService adminservice;
	
	static final Logger logger = Logger.getLogger(SetClassStrengthController.class);
	
	@RequestMapping(value = "/AdminClassStrenth", method = RequestMethod.GET)
	public ModelAndView getdata(HttpSession session,School school) {
			UserRegistration userReg=(UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			long adminRegId=userReg.getRegId();
			school=reportService.getSchoolIdByRegId(adminRegId);
			List<School> classStrengths = adminservice.getClassStrengthDetails(school);
		ModelAndView model = new ModelAndView("Admin/SetClassStrength","School",new School());
		model.addObject("classStrengths",classStrengths);
		return model; 
	}
	
	@RequestMapping(value = "/SetClassStrength", method = RequestMethod.GET)
	public @ResponseBody  
    void AdminCreateClass(HttpServletResponse response,HttpSession session,@ModelAttribute("school") School school,
			BindingResult result,@RequestParam("classStrength") int classStrength,@RequestParam("genderEquity") String genderEquity,@RequestParam("Leveling") String Leveling) throws Exception { 
		String helloAjax="";
		UserRegistration userReg=(UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		long adminRegId=userReg.getRegId();					
		school=reportService.getSchoolIdByRegId(adminRegId);
		school.setClassStrength(classStrength);
		school.setLeveling(Leveling);
		school.setGenderEquity(genderEquity);
		adminservice.setClassStrength(school);
        helloAjax = "Set Class strength Successfully";
		response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html");  
        response.getWriter().write(helloAjax);  
	}
		
}
