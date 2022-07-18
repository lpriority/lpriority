
package com.lp.student.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.model.MathGameScores;
import com.lp.student.service.MathGameService;
import com.lp.utils.WebKeys;

@Controller
public class MathGameController  extends WebApplicationObjectSupport{	
	@Autowired
	private MathGameService mathGameService;	
	static final Logger logger = Logger.getLogger(MathGameController.class);
	
	@RequestMapping(value = "/startGame", method = RequestMethod.GET)
	public ModelAndView startGameLevel1(
			HttpSession session,
			@RequestParam("studentAssignmentId") long studentAssignmentId) {
		ModelAndView model = new ModelAndView("Ajax/Student/game_plan");
		MathGameScores mathGameScores = null;
		try{
			mathGameScores = mathGameService.getPendingMathGameScoreByStudentAssignmentId(studentAssignmentId);
		}
		catch(Exception e){
			mathGameScores = new MathGameScores();
			logger.error("Error in startGame() of  MathGameController" +e.getStackTrace());
		}		
		model.addObject("mathGameScores", mathGameScores);
		model.addObject("studentAssignmentId", studentAssignmentId);
		return model;
	}
	
	@RequestMapping(value = "/gameLevel", method = RequestMethod.GET)
	public ModelAndView gameLevel(
			HttpSession session,
			@RequestParam("studentAssignmentId") long studentAssignmentId, 
			@RequestParam("gameLevelId") long gameLevelId, 
			@RequestParam("mathGearId") long mathGearId, 
			ModelAndView model) {
		if(gameLevelId > 0)
			 model = new ModelAndView("Ajax/Student/game"+mathGearId+"_level"+gameLevelId);
		MathGameScores mathGameScores = new MathGameScores();
		try{
			mathGameScores = mathGameService.getMathGameScores(studentAssignmentId, mathGearId, gameLevelId);
		}
		catch(Exception e){
			mathGameScores = new MathGameScores();
			logger.error("Error in gameLevel() of  MathGameController" +e.getStackTrace());
		}		
		model.addObject("mathGameScores", mathGameScores);
		model.addObject("studentAssignmentId", studentAssignmentId);
		return model;
	}
	
	@RequestMapping(value = "/submitLevel1", method = RequestMethod.POST)
	public ModelAndView submitLevel1(
			@RequestParam("noOfAttempts") long attempts, 
			@RequestParam("studentAssignmentId") long studentAssignmentId, 
			@RequestParam("noOfInCorrects") long noOfInCorrects, 
			@RequestParam("timeTaken") String timeTaken, 
			@RequestParam("mathGearId") long mathGearId, 
			@RequestParam("gameLevelId") long gameLevelId, 
			HttpServletRequest request, 
			ModelAndView model) {
		try{
			@SuppressWarnings("unused")
			boolean status = mathGameService.submitGameLevel1(studentAssignmentId, attempts, noOfInCorrects, timeTaken, mathGearId, gameLevelId);
			if(request.getServerName().equalsIgnoreCase(WebKeys.LOCAL_HOST))
				model = new ModelAndView("redirect:/startGame.htm?studentAssignmentId="+studentAssignmentId);
			else
				model = new ModelAndView("redirect:https:/startGame.htm?studentAssignmentId="+studentAssignmentId);
		}catch(Exception e){
			logger.error("Error in submitLevel1() of MathGameController" + e.getStackTrace());
		}	
		return model;
	}
	
	@RequestMapping(value = "/updateAttempts", method = RequestMethod.POST)
	public View updateAttempts(
			@RequestParam("noOfAttempts") long noOfAttempts, 
			@RequestParam("mathGameScoreId") long mathGameScoreId, 
			@RequestParam("mathGearId") long mathGearId, 
			Model model) {
		try{
			mathGameService.updateAttempts(noOfAttempts, mathGameScoreId, mathGearId);			
		}catch(Exception e){
			logger.error("Error in updateAttempts() of MathGameController" + e.getStackTrace());
		}	
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/submitLevel2", method = RequestMethod.POST)
	public ModelAndView submitLevel2(@RequestParam("time") String time, 
			 @RequestParam("failCount") long failCount,
			 @RequestParam("studentAssignmentId") long studentAssignmentId,
			 @RequestParam("noOfAttempts") long noOfAttempts, 
			 @RequestParam("mathGearId") long mathGearId, 
			 @RequestParam("gameLevelId") long gameLevelId, 
			 HttpServletRequest request, 
			 ModelAndView model) {
		@SuppressWarnings("unused")
		boolean status = false;
		try{
			status = mathGameService.submitLevel2(time, failCount, studentAssignmentId, noOfAttempts, mathGearId, gameLevelId);	
			if(request.getServerName().equalsIgnoreCase(WebKeys.LOCAL_HOST))
				model = new ModelAndView("redirect:/startGame.htm?studentAssignmentId="+studentAssignmentId);
			else
				model = new ModelAndView("redirect:https:/startGame.htm?studentAssignmentId="+studentAssignmentId);
		}catch(Exception e){
			logger.error("Error in submitLevel2() of MathGameController" + e.getStackTrace());
		}	
		return model;
	}
	
	@RequestMapping(value = "/submitLevel3", method = RequestMethod.POST)
	public ModelAndView submitLevel3(
			 @RequestParam("time") String time,
			 @RequestParam("mathGameScoreId") long mathGameScoreId,
			 @RequestParam("mathGearId") long mathGearId,
			 @RequestParam("gameLevelId") long gameLevelId,
			 @RequestParam("studentAssignmentId") long studentAssignmentId,
			 HttpServletRequest request, 
			 ModelAndView model) {
		@SuppressWarnings("unused")
		boolean status = false;		
		try{
			status = mathGameService.submitLevel3(time, mathGameScoreId, mathGearId, gameLevelId);	
			if(request.getServerName().equalsIgnoreCase(WebKeys.LOCAL_HOST))
				model = new ModelAndView("redirect:/startGame.htm?studentAssignmentId="+studentAssignmentId);
			else
				model = new ModelAndView("redirect:https:/startGame.htm?studentAssignmentId="+studentAssignmentId);			
		}catch(Exception e){
			logger.error("Error in submitLevel3() of MathGameController " + e.getStackTrace());
		}	
		return model;
	}
}