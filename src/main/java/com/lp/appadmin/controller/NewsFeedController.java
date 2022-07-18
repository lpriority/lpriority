package com.lp.appadmin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lp.appadmin.service.NewsFeedService;
import com.lp.model.LPNews;

@Controller
public class NewsFeedController {
	
	static final Logger logger = Logger.getLogger(NewsFeedController.class);
	@Autowired
	private NewsFeedService newsFeedService;

	/* This method redirects to News Feed Page */
	@RequestMapping(value = "/goToLPNewsPage", method = RequestMethod.GET)
	public ModelAndView goToNewsFeed(HttpSession session) {		
		ModelAndView model = new ModelAndView("AppManager/lp_news");
		List<LPNews> lpNews = newsFeedService.getLPNews();
		if(lpNews==null){
			model.addObject("lpNews", new ArrayList<LPNews>());			
		}
		else{
			model.addObject("lpNews", lpNews);
		}
		return model;
	}
	
	@RequestMapping(value = "/addLPNews", method = RequestMethod.GET)
	public ModelAndView addLPNews(HttpSession session) {
		ModelAndView model = new ModelAndView("AppManager/add_lp_news", "lpNewsObj", new LPNews());
		return model;
	}
	@RequestMapping(value = "/saveLPNews", method = RequestMethod.POST)
	public ModelAndView saveLPNews(HttpSession session,
			@ModelAttribute LPNews lpNewsObj) {
		newsFeedService.saveLPNews(lpNewsObj);
		ModelAndView model = new ModelAndView("redirect:/addLPNews.htm");
		return model;
	}
	
	@RequestMapping(value = "/deleteLPNews", method = RequestMethod.POST)
	public ModelAndView deleteLPNews(HttpSession session,
			@RequestParam("lpNewsId") long lpNewsId) {
		newsFeedService.deleteLPNews(lpNewsId);
		ModelAndView model = new ModelAndView("redirect:/goToLPNewsPage.htm");
		return model;
	}
	
	@RequestMapping(value = "/editLPNews", method = RequestMethod.GET)
	public ModelAndView editLPNews(HttpSession session, 
			@RequestParam long lpNewsId, Model model ) {
		LPNews lpNewsObj = newsFeedService.getLPNewsById(lpNewsId);	
		ModelAndView model1 = new ModelAndView("AppManager/add_lp_news", "lpNewsObj", lpNewsObj);		
		return model1;
	}	
}
