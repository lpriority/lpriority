/**
 * 
 */
package com.lp.custom.springsecurity;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;




/**
 * @author Prasad BHVN
 *
 */
public class SessionContext extends ApplicationObjectSupport {
	

	private String signIn;
	

	public static final String SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS = "SC";
	public static final String SESSION_ATTRIBUTE_NAME = "SC";
	
	private SessionContext() {}
	
	public static SessionContext getSessionContext(){
		SessionContext signedInAsSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS);
		if(signedInAsSC != null){
			getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,signedInAsSC);
			return signedInAsSC;
		}
		SessionContext sc = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME);
		if(sc == null){
			sc = new SessionContext();
			getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,sc);
		}
		return sc;
	}
	
	/**
	 * Will get and/or create the signed in as Session Context
	 * @return
	 */
	public static SessionContext getSignedInAsSessionContext(){
		SessionContext signedInAsSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS);
		if(signedInAsSC != null){
			signedInAsSC.destroySignedInAsSessionContext();
		}
		signedInAsSC = new SessionContext();
		signedInAsSC.setSignIn("yes");
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,signedInAsSC);
		return signedInAsSC;
	}
	
	public static SessionContext setSignedOutAsSessionContext(){
		SessionContext signedInAsSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS);
		if(signedInAsSC != null){
			signedInAsSC.destroySignedInAsSessionContext();
		}
		signedInAsSC = new SessionContext();
		signedInAsSC.setSignIn("no");
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,signedInAsSC);
		return signedInAsSC;
	}
	
	public void destroySignedInAsSessionContext(){
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS,null);
	}
	
	
	
	/**
	 * Destory Session Context
	 * @return
	 */

	public void destroySessionContext(){
		SessionContext signedInAsSC = (SessionContext)getSession().getAttribute(SESSION_ATTRIBUTE_NAME_SIGNED_IN_AS);
		
		if(signedInAsSC.getSignIn()!= null){
			destroySignedInAsSessionContext();
		}
		getSession().setAttribute(SESSION_ATTRIBUTE_NAME,null);
		getSession().invalidate();
	}
	public String getSignIn() {
		return signIn;
	}

	public void setSignIn(String signIn) {
		this.signIn = signIn;
	}
	
		public static HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession();
	}
	
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest();
	}
	
	
	
}
