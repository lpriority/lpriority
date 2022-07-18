package com.lp.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.ModelAndView;

import com.lp.custom.exception.DataException;
 /**
  * 
  * 
  * @author PRASAD BHVN
  *
  */
@ControllerAdvice
public class ExceptionHandlingController extends WebApplicationObjectSupport{

	  @ExceptionHandler(DataException.class)
	  public ModelAndView handleError(HttpServletRequest req, Exception exception) {
	    logger.error("Requested URL="+req.getRequestURL());
        logger.error("Exception Raised="+exception);
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", exception);
	    mav.addObject("url", req.getRequestURL());
	    mav.setViewName("errorpages/error");
	    return mav;
	  }
	  
	  @ExceptionHandler(Exception.class)
	  public ModelAndView handleFullError(HttpServletRequest req, Exception exception) {
	    logger.error("Requested URL="+req.getRequestURL());
        logger.error("Exception Raised="+exception);
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", exception);
	    mav.addObject("url", req.getRequestURL());
	    mav.setViewName("errorpages/error");
	    return mav;
	  }

}
