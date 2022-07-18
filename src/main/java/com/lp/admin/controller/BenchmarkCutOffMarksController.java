package com.lp.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.AdminService;
import com.lp.common.service.BenchmarkCutOffMarksService;
import com.lp.model.BenchmarkCategories;
import com.lp.model.BenchmarkCutOffMarks;
import com.lp.model.Grade;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;

@Controller
public class BenchmarkCutOffMarksController {

	@Autowired
	private BenchmarkCutOffMarksService benchmarkCutOffMarksService;
	@Autowired
	private AdminService adminService;
	
	static final Logger logger = Logger.getLogger(BenchmarkCutOffMarksController.class);
	
	@RequestMapping(value = "/benchmarkCutOffMarks", method = RequestMethod.GET)
	public ModelAndView benchmarkCutOffMarks(HttpSession session) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Admin/benchmark_cut_off_marks", "benchmarkCutOffMarks", new BenchmarkCutOffMarks());
		try{
			List<Grade> schoolgrades = adminService.getSchoolGrades(userReg.getSchool().getSchoolId());			
			model.addObject("grList", schoolgrades);
		}catch(Exception e){
			logger.error("Error in benchmarkCutOffMarks() of BenchmarkCutOffMarksController"+ e);
		}
		return model;
	}
		
	@RequestMapping(value = "/getBenchmarkCategories", method = RequestMethod.GET)
	public ModelAndView getBenchmarkCategories(HttpSession session,@RequestParam("gradeId") long gradeId,
			HttpServletResponse response, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("Ajax/Admin/show_benchmark_categories", "benchmarkCategories", new BenchmarkCategories());
		try{
			List<BenchmarkCategories> benchmarkCategories =benchmarkCutOffMarksService.getBenchmarkCategories();
			List<BenchmarkCutOffMarks> benchmarkCutOffmarks=benchmarkCutOffMarksService.getBenchmarkCutOffMarks(gradeId);
			model.addObject("benchmarkCategoriesList", benchmarkCategories);
			model.addObject("benchmarkCutOffMarksList",benchmarkCutOffmarks);
		}catch(Exception e){
			logger.error("Error in getBenchmarkCategories() of BenchmarkCutOffMarksController"+ e);
		}		
		return model;
	}
	
	@RequestMapping(value = "/setBenchmarkCutOffMarks", method = RequestMethod.GET)
	public @ResponseBody
	void setBenchmarkCutOffMarks(HttpSession session,
			HttpServletResponse response,
			@RequestParam("gradeId") long gradeId,
			@RequestParam("categorys") long categorys[],@RequestParam("fluencyCutOffs") Integer fluencyCutOffs[],
			@RequestParam("retellCutOffs") Integer retellCutOffs[],
			Model model) {
		try {
			String helloAjax = "";
		    Grade grade=adminService.getGrade(gradeId);
			List<BenchmarkCutOffMarks> benchmarkCutOffs= new ArrayList<BenchmarkCutOffMarks>();
			benchmarkCutOffs=benchmarkCutOffMarksService.getBenchmarkCutOffMarks(gradeId);
			if(!benchmarkCutOffs.isEmpty()){
				for(int i=0;i<categorys.length;i++){					
					benchmarkCutOffs.get(i).setFluencyCutOff(fluencyCutOffs[i]);
					benchmarkCutOffs.get(i).setRetellCutOff(retellCutOffs[i]);					
				}
			}else{
				for(int i=0;i<categorys.length;i++){
					BenchmarkCutOffMarks bcm=new BenchmarkCutOffMarks();
					bcm.setGrade(grade);
					bcm.setBenchmarkCategories(benchmarkCutOffMarksService.getbenchmarkCategories(categorys[i]));
					bcm.setFluencyCutOff(fluencyCutOffs[i]);
					bcm.setRetellCutOff(retellCutOffs[i]);
					benchmarkCutOffs.add(bcm);
				}
			}
			boolean check=benchmarkCutOffMarksService.setBenchmarkCutOff(benchmarkCutOffs);
						
			if (check)
				helloAjax = WebKeys.TEST_BENCUTOFF_SUCCESS;
			else
				helloAjax = WebKeys.TEST_BENCUTOFF_FAILURE;
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(helloAjax);

		} catch (Exception e) {
			logger.error("Error in setBenchmarkCutOffMarks() of BenchmarkCutOffMarksController"+ e);
		}
	}	
}
