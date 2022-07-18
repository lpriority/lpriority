package com.lp.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.lp.appadmin.service.SchoolAdminService;
import com.lp.custom.exception.DataException;
import com.lp.custom.springsecurity.SessionContext;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;
/**
 * 
 * 
 * @author PRASAD BHVN 09 July 2015
 *
 */
@Controller
public class SwitchUserController {
	
	static final Logger logger = Logger.getLogger(SwitchUserController.class);
	@Autowired
	private SchoolAdminService schoolAdminService;
	
	@RequestMapping(value = "/switchUser", method = RequestMethod.GET)
	public View switchUser(Model model,HttpServletRequest request,HttpSession session){
		String previousUser = null;
		String currentUser = null;	
		RedirectView rv = new RedirectView("gotoDashboard.htm");
		rv.setExposeModelAttributes(false);
		try {
			previousUser= getPreviousUser();
			currentUser= getUsername();	
			SessionContext signInAs = SessionContext.getSessionContext();
			//Back to Admin
			if(previousUser != null){			
				if(previousUser.equalsIgnoreCase(currentUser)){
					UserRegistration userReg = schoolAdminService
							.getUserRegistration(previousUser);
					if (userReg != null) {
						session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
					}
					SessionContext.getSignedInAsSessionContext().destroySignedInAsSessionContext();
					model.addAttribute("message", "SignIn as different User");
					RedirectView rv1 = new RedirectView("gotoDashboard.htm");
					rv1.setExposeModelAttributes(false);
					return  rv1;
				}
			}
			//Switch User
			if(previousUser == null){
				SessionContext.setSignedOutAsSessionContext();
				signInAs = SessionContext.getSessionContext();
				model.addAttribute("SC", signInAs);
			}else{
				SessionContext.getSignedInAsSessionContext();
				signInAs = SessionContext.getSessionContext();
				model.addAttribute("SC", signInAs);
			}
			UserRegistration userReg = schoolAdminService
					.getUserRegistration(currentUser);
			if (userReg != null) {
				session.setAttribute(WebKeys.USER_REGISTRATION_OBJ, userReg);
				session.removeAttribute(WebKeys.TEACHER_OBJECT);
				session.removeAttribute(WebKeys.STUDENT_OBJECT);
			}
		} catch (DataException e) {
			logger.error("Error in switching user :"+ e.getMessage());
		}catch (Exception e) {
			logger.error("Error in switching user :"+ e.getMessage());
		}
		return rv;
    } 
	
	
	
	public String getUsername() throws DataException {
		String currentUser = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() instanceof UserDetails) {			
			currentUser = ((UserDetails) auth.getPrincipal()).getUsername();
			logger.debug("CurrentUsername:-CurrentUsername User :"+currentUser);
			return currentUser;
		} else {
			return auth.getPrincipal().toString();
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPreviousUser() throws DataException {
		String previousUser = null;
		List<GrantedAuthority> auth = (List) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		if(auth!=null && auth.size()>0){
			for(GrantedAuthority grantedAuthority:auth){
				if(grantedAuthority instanceof SwitchUserGrantedAuthority){
					previousUser = ((SwitchUserGrantedAuthority) grantedAuthority).getSource().getName();
				}
			}
		}
		logger.debug("getPreviousUser:-Previouse User :"+ previousUser);		
		return previousUser;
		
	}

	
}
