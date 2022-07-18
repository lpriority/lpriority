package com.lp.custom.springsecurity;

import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;



//Author : PRASADBHVN DT: 08/07/2015


public class CustomSwitchUserFilter extends SwitchUserFilter{
    
	
	public CustomSwitchUserFilter() {
		
		super.setUserDetailsService(new CustomUserDetailsService());
		super.setTargetUrl("/switchUser.htm");
	}
	
	 } 
