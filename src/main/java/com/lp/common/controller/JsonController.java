package com.lp.common.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.lp.admin.service.AdminService;
import com.lp.model.GradeClasses;
/**
 * 
 * @author PRASAD BHVN & SANTHOSH
 *
 */
@Controller
public class JsonController extends WebApplicationObjectSupport{

	@Autowired
	private AdminService adminService;
	
	static final Logger logger = Logger.getLogger(JsonController.class);
	
	@RequestMapping(value="/getAvailableClassesJson", method = RequestMethod.GET)
	public @ResponseBody List<GradeClasses> getShopInJSON(@PathVariable long gradeId) {
		List<GradeClasses>  list = adminService.getGradeClasses(gradeId);		
		return list;
	}
		   	
}
