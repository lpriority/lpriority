package com.lp.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lp.admin.service.StemAnalyticsService;
import com.lp.model.StemAnalytics;
import com.lp.utils.WebKeys;

@Controller
public class STEMAnalyticsController {

	@Autowired
	private StemAnalyticsService stemAnalyticsService;
	
	static final Logger logger = Logger.getLogger(STEMAnalyticsController.class);
	
	@RequestMapping(value = "/stemAnalytics", method = RequestMethod.GET)
	public ModelAndView getStemFiveCs(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/stem_analytics_reports");
		model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		return model;
	}	
	
	@RequestMapping(value = "/getStemReport", method = RequestMethod.GET)
	public ModelAndView getStemReport(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,@RequestParam("areaValue") String areaValue) {
		List<StemAnalytics> stemAnalytics = new ArrayList<StemAnalytics>();
		ModelAndView model = new ModelAndView("Ajax/Admin/stem_area_report");
		try {
			if(areaValue.equalsIgnoreCase(WebKeys.LP_STEM)){
				stemAnalytics = stemAnalyticsService.getStemReportByStemPair();
			}else{
				stemAnalytics =  stemAnalyticsService.getStemReport(areaValue);
			}
			model.addObject("stemAnalytics", stemAnalytics);
			model.addObject("noOfConQues", stemAnalytics.size());
			model.addObject("areaValue", areaValue);
			model.addObject("LP_STEM_TAB", WebKeys.LP_STEM_TAB);
		} catch (Exception e) {
			logger.error("Error in getStemReport() of STEMAnalyticsController"+ e);
			e.printStackTrace();
		}
		return model;
	}	
	
}


