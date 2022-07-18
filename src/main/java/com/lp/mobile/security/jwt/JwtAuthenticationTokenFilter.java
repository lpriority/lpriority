package com.lp.mobile.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.lp.custom.exception.DataException;
import com.lp.custom.springsecurity.CustomUserDetailsService;


public class JwtAuthenticationTokenFilter extends GenericFilterBean  {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

   // @Value("${jwt.header}")
    private final String tokenHeader = "JWT-AUTH-TOKEN";


    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(this.tokenHeader);
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        //response.setHeader("Access-Control-Allow-Credentials", "true");
        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
			try {
				userDetails = customUserDetailsService.loadUserByUsername(username);
				if (jwtTokenUtil.validateToken(authToken, userDetails)) {
	                UsernamePasswordAuthenticationToken authentication = customUserDetailsService.getJWTAuthentication(userDetails);
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }

        chain.doFilter(request, response);
     
    }
    

  
  
}