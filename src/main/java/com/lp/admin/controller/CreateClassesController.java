package com.lp.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.CommonService;
import com.lp.model.Legend;
import com.lp.model.School;
import com.lp.model.StudentClass;
import com.lp.model.SubInterest;
import com.lp.model.UserRegistration;
import com.lp.teacher.service.IOLReportCardService;
import com.lp.utils.WebKeys;

@Controller
public class CreateClassesController {

	@Autowired
	private CommonService reportService;
	@Autowired
	private AdminService adminservice;
	@Autowired
	private IOLReportCardService iolReportCardService;
	
	static final Logger logger = Logger.getLogger(CreateClassesController.class);

	@RequestMapping(value = "/AdminCreateClass", method = RequestMethod.GET)
	public ModelAndView getdata(HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/CreateClasses","studentclass",new StudentClass());
		return model; 
	}
	
	@RequestMapping(value = "/CreateClass", method = RequestMethod.GET)
	public @ResponseBody 
	void AdminCreateClass(HttpServletResponse response,HttpSession session,@ModelAttribute("stuclass") StudentClass stuclass,
    		BindingResult result,@RequestParam("classname") String classname,SubInterest subinterest) throws Exception { 
		String helloAjax = "";
		UserRegistration userReg = (UserRegistration)session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			long adminRegId = userReg.getRegId();
			School school = reportService.getSchoolIdByRegId(adminRegId);
			stuclass.setSchool(school);
			stuclass.setClassName(classname);
			int check = adminservice.CheckExistClass(stuclass);
			if(check == 0){
				adminservice.CreateClasses(stuclass);
				helloAjax = "Class Created Successfully";
			}else{	
				helloAjax = "Class Already Exists";
			}
		}
		catch(Exception e){
			logger.error("Error in CreateClass() of CreateClassesController" +e);
		}
        response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html");  
        response.getWriter().write(helloAjax);  
	}
	
	@RequestMapping(value = "/getLegendSubCriterias", method = RequestMethod.GET)
	public View getLegendSubCriterias(HttpSession session,
			@RequestParam("criteriaId") long criteriaId,
		Model model) {
		try{
			model.addAttribute("legendSubCriteriaList", adminservice.getLegendSubCriteriasByCriteriaId(criteriaId));
		}
		catch(Exception e){
			logger.error("Error in getLegendSubCriterias() of CreateClassesController" +e);
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/createLIRubric", method = RequestMethod.POST)
	public @ResponseBody  
    void createLIRubric(HttpServletResponse response,HttpSession session, @RequestParam("criteriaId") 
    		long criteriaId, @RequestParam("subCriteriaId") long subCriteriaId,	@RequestParam("rubricScore") 
    		long rubricScore, @RequestParam("rubricDesc") String rubricDesc, @RequestParam("tab") String tab)
    		throws Exception { 
		String helloAjax="";
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			if(tab.equalsIgnoreCase(WebKeys.LP_TAB_CREATE_LE_RUBRIC)){
				long stat = adminservice.createLIRubric(criteriaId,subCriteriaId,rubricScore,rubricDesc, userReg);
			   	if(stat == 1)
			   		helloAjax = "Rubric Created Successfully";
				else
					helloAjax = "Rubric Creation Failure";
		    }else{
				long check1 = adminservice.editLIRubric(subCriteriaId,rubricScore,rubricDesc, userReg.getRegId());
				if(check1 == 1)
					helloAjax = "Rubric Edited Successfully";
				else
					helloAjax = "Failure";
			}
		}
		catch(Exception e){
			logger.error("Error in createLIRubric() of CreateClassesController" +e);
		}
        response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html");  
        response.getWriter().write(helloAjax);  
	}
	
	@RequestMapping(value = "/getRubricValuesBySubCriteriaId", method = RequestMethod.GET)
	   public ModelAndView getRubricValuesBySubCriteriaId(HttpSession session,@RequestParam("criteriaId") long criteriaId,
			   @RequestParam("subCriteriaId") long subCriteriaId) {
		ModelAndView model = new ModelAndView("Ajax/Admin/show_subcriteria_rubric","Legend",new Legend());
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try{
			model.addObject("schoolgrades",adminservice.getSchoolGrades(userReg.getSchool().getSchoolId()));
			model.addObject("subCriteriaRubricValues",adminservice.getRubricValuesBySubCriteriaId(subCriteriaId,userReg.getRegId()));
		}
		catch(Exception e){
			logger.error("Error in getRubricValuesBySubCriteriaId() of CreateClassesController" +e);
		}
		return model; 
	}
	
	@RequestMapping(value = "/assignLIRubricToGrade", method = RequestMethod.GET)
	public @ResponseBody  
    void assignLIRubricToGrade(HttpServletResponse response,HttpSession session,
    	@RequestParam("subCriteriaId") long subCriteriaId,
    	@RequestParam("legendIds") List<Long> legendIds,@RequestParam("gradeId") long gradeId) throws Exception { 
		try{
			long check = iolReportCardService.assignLIRubricToGrade(subCriteriaId,legendIds,gradeId);
			if(check == 1)
				response.getWriter().write("Rubric Assigned Successfully");
			else
				response.getWriter().write("Rubric Assign Failure");
		}
		catch(Exception e){
			logger.error("Error in assignLIRubricToGrade() of CreateClassesController" +e);
		}
    }
}
