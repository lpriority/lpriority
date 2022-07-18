package com.lp.custom.springsecurity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.custom.exception.DataException;
import com.lp.mobile.security.jwt.JwtUser;
import com.lp.mobile.security.jwt.auth.JwtAuthenticationRequest;
import com.lp.model.UserRegistration;
import com.lp.utils.WebKeys;


//Author : PRASADBHVN DT: 18/03/2015

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRegistrationDAO userRegDAO;
	@Autowired
	private AuthenticationManager authManager;

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		UserRegistration userReg = null;
		boolean enabled = false;
		String userRole ="";
		String emalId= "";
		String password="";
		try{
			 userReg = userRegDAO.getLoginUserRegistration(userName); 
			 emalId = userReg.getEmailId();
			 password = userReg.getPassword();
			
			 if(userReg!= null){
				 if(userReg.getRegId() > 0){
					 userRole = userReg.getUser().getUserType().toUpperCase();
					 if(userReg.getStatus().equalsIgnoreCase(WebKeys.ACTIVE))
							enabled = true;
					 
					 return new User(
							    emalId, 
							    password, 
				                enabled, 
				                true, 
				                true, 
				                true,
				                getAuthorities(getAuthority(userRole))
				        );
				 } 
			 }
		}catch(Exception e){
			e.printStackTrace();	 
		}		
		return null;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(List<String> userTypes) {
		 List<GrantedAuthority> authList = getGrantedAuthorities(userTypes);
        return authList;
    }
	
	 public static List<GrantedAuthority> getGrantedAuthorities(List<String> userTypes) {
	        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        for(String userType: userTypes){
	        	 authorities.add(new SimpleGrantedAuthority(userType));
	        }
	        return authorities;
	    }
	 
	 public List<String> getAuthority(String userType) {
		 	List<String> userTypes = new ArrayList<String>();
		 		if(userType.equalsIgnoreCase(WebKeys.LP_USER_TYPE_ADMIN)){
		 			userTypes.add("ROLE_USER") ;
		 			userTypes.add("ROLE_ADMIN") ;
		 		}else {
		 			userTypes.add("ROLE_USER") ;
		        }
		 	
		 	return 	userTypes;		 		
	 }
	 
	 public UsernamePasswordAuthenticationToken getJWTAuthentication(UserDetails userDetails) throws DataException {
			
		 UsernamePasswordAuthenticationToken authentication = null;
		try{
			 authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
		}catch(UsernameNotFoundException e){
			//logger.debug("getJWTAuthentication:- jwtUser is not Found"+e.getMessage());
			return null;
		}
		return authentication;
	}

	public UserDetails CheckJWTUserAuthentication(JwtAuthenticationRequest authenticationRequest) throws DataException {
		
		final Authentication authentication = authManager.authenticate(
	               new UsernamePasswordAuthenticationToken(
	               		authenticationRequest.getUsername(),
	               		authenticationRequest.getPassword()
	               )
	      );
	       
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
		return userDetails;
	}

	
	public JwtUser loadJwtUserByUserDetails(UserDetails userDetails) throws DataException {
		JwtUser jwtUser = new JwtUser();
		try{
			jwtUser.setUsername(userDetails.getUsername());
			jwtUser.setPassword(userDetails.getPassword());
			jwtUser.setAuthorities(userDetails.getAuthorities());
        
		}catch(UsernameNotFoundException e){
		//	logger.debug("loadJwtUserByUserDetails User details not Found"+e.getMessage());
			return null;
		}
		return jwtUser;
	}
	    
	public UserDetails switchUserAuthentication(String userName) throws DataException {
		UserDetails userDetails = null;
		try{
	        userDetails = loadUserByUsername(userName);
	        if(userDetails != null){
		        Authentication auth = new UsernamePasswordAuthenticationToken (userDetails.getUsername (),userDetails.getPassword (),userDetails.getAuthorities ());
		        SecurityContextHolder.getContext().setAuthentication(auth);
	        }
	       
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return userDetails;
	}
	
	public UserDetails checkSSOUserAuthentication(String userName) throws DataException {
		 UserDetails userDetails = null;
		try{
			userDetails = loadUserByUsername(userName);
	        if(userDetails != null){
		        Authentication auth = new UsernamePasswordAuthenticationToken (userDetails.getUsername (),userDetails.getPassword (),userDetails.getAuthorities ());
		        SecurityContextHolder.getContext().setAuthentication(auth);
	        }
		}catch(Exception e){
			e.printStackTrace();	 
		}
		return userDetails;
	}

}
