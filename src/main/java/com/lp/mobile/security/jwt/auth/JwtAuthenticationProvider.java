package com.lp.mobile.security.jwt.auth;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.appadmin.service.SchoolAdminService;
import com.lp.custom.exception.DataException;
import com.lp.custom.springsecurity.CustomUserDetailsService;
import com.lp.mobile.model.UserDetailsApp;
import com.lp.mobile.security.jwt.JwtTokenUtil;
import com.lp.mobile.security.jwt.JwtUser;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;


@RestController
public class JwtAuthenticationProvider {
	
    private final String jwtUserToken = "JWT-AUTH-TOKEN";
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private SchoolAdminService schoolAdminService;

   @RequestMapping(value={"/jwtUserAuthentication","/*/jwtUserAuthentication"}, method = RequestMethod.POST)
   public View createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,ServletRequest request, Model model) throws AuthenticationException, DataException {
	   HttpServletRequest httpRequest = (HttpServletRequest) request;
	   @SuppressWarnings("unused")
	   String authToken = httpRequest.getHeader(jwtUserToken);
	   final UserDetails userDetails = customUserDetailsService.CheckJWTUserAuthentication(new JwtAuthenticationRequest(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
	   UserRegistration userReg = new UserRegistration();
	   userReg = schoolAdminService.getUserRegistration(userDetails.getUsername());
	   boolean isParent = false;
	   if(userReg.getUser().getUserType().equalsIgnoreCase(WebKeys.USER_TYPE_PARENT)){
		   final String token = jwtTokenUtil.generateToken(userDetails, null);
		   model.addAttribute(jwtUserToken, token);
		   UserDetailsApp ud = new UserDetailsApp();
		   ud.setUserName(userReg.getFirstName()+ " " +userReg.getLastName());
		   ud.setUserEmail(userReg.getEmailId());
		   ud.setSchoolName(userReg.getSchool().getSchoolName());
		   ud.setRegId(userReg.getRegId());
		   ud.setSchoolId(userReg.getSchool().getSchoolId());
		   model.addAttribute("userReg", ud);
		   isParent = true;
	   }
	   model.addAttribute("isParent", isParent);
	   return new MappingJackson2JsonView();
   }
   
   @RequestMapping(value={"/jwtWebAuthentication","/*/jwtWebAuthentication"}, method = RequestMethod.POST)
   public View createWebAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,ServletRequest request, Model model) throws AuthenticationException, DataException {
	   HttpServletRequest httpRequest = (HttpServletRequest) request;
	   @SuppressWarnings("unused")
	   String authToken = httpRequest.getHeader(jwtUserToken);
	   final UserDetails userDetails = customUserDetailsService.CheckJWTUserAuthentication(new JwtAuthenticationRequest(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
	   UserRegistration userReg = new UserRegistration();
	   userReg = schoolAdminService.getUserRegistration(userDetails.getUsername());
	   final String token = jwtTokenUtil.generateToken(userDetails, null);
	   model.addAttribute(jwtUserToken, token);
	   UserDetailsApp ud = new UserDetailsApp();
	   ud.setUserName(userReg.getFirstName()+ " " +userReg.getLastName());
	   ud.setUserEmail(userReg.getEmailId());
	   ud.setSchoolName(userReg.getSchool().getSchoolName());
	   ud.setRegId(userReg.getRegId());
	   ud.setSchoolId(userReg.getSchool().getSchoolId());
	   model.addAttribute("userReg", ud);
	   return new MappingJackson2JsonView();
   }

   @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
   public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
	   String token = request.getHeader(jwtUserToken);
	   String username = jwtTokenUtil.getUsernameFromToken(token);
	   JwtUser user = null;
	   try {
		   user = (JwtUser) customUserDetailsService.loadUserByUsername(username);

		   if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			   String refreshedToken = jwtTokenUtil.refreshToken(token);
			   return new ResponseEntity<>(new JwtAuthenticationResponse(refreshedToken), HttpStatus.OK); 
		   } else {
			   return new ResponseEntity<>("Bad Request",HttpStatus.BAD_REQUEST);
		   }
	   } catch (DataException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
	   return null;	   	
   }
}
