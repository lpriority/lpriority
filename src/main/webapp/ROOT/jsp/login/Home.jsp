<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="google-site-verification" content="LmLtSNCPLfi7dv9VmtUDmfz50ayk66Ttll7JqQ3Bmzc" />
<meta name="google-site-verification" content="xB7IbiDZQDoAv3NUF1dYY1FXfHTDfWDEFTxjHU5GFpc" />
<meta name="globalsign-domain-verification" content="JrYEmhSzYh_msylv1paS_orAFEcEIBMJxmKF5p5Wy2">
<title>Welcome to The Learning Priority Inc | Learning Priority</title>
	<link rel="stylesheet" href="resources/css/normalize.css">
	<link rel="stylesheet" href="resources/css/registration-css.css">    
	<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/common_special_effects.css" rel="stylesheet" type="text/css" />
	<% session.setAttribute("userReg", null); %>
	<script>
	  $(document).ready(function(){
		  storeValue("itemName",'');
		  storeValue("menuLevelTitle",'');
	  });
	</script>
	<style>
	.google-sign{
		background:-webkit-gradient(linear, left top, left bottom, from(#F44336), to(#d23428));
		float: right;
		height: 50px;
		min-width: 50px;
		max-width: 50px;
		padding: 0;
		font-size: 20px;
        margin-top: 1em;
	} 

	</style>
</head> 
<body>
<div class="loginroot">
	<div align="center" valign="middle" style='padding-top: 0.5em;'>
		<table width="100%">
			<tr><td width="37%"  valign="middle" align="right">
				<span class="logoStyle1">Learning Priority</span>
			</td>
			<td  width="30%"  valign="middle" align="left">
				<img src="images/common_images/LpLOGO.png" width="80" height="60" style="">
			</td></tr>
		</table>
	</div>
	
	<div class="form">
      <ul class="tab-group">
        <li class="tab active"><a href="#login">Sign In</a></li>
        <li class="tab"><a href="#signup">Sign Up</a></li>
      </ul>
      <div class="tab-content">
      	  <div id="login" style="margin:-25px 0 25px;">   
          <h1>Welcome!</h1>
			<form action="j_spring_security_check" method="POST" autocomplete="off">          
				<div class="field-wrap">
				<label id="emailLabel" style="display: block;">
				    Email Address<span class="req">*</span>
				  </label>
				  <input  id="login-username" type="email" required="required"  name="j_username" required/>
				</div>			
				<div class="field-wrap">
				  <label id="passwordLabel" style="display: block;">
				    Password<span class="req">*</span>
				  </label>
				  <input id="login-password" type="password" name="j_password" required  style="font:small-caption;font-size:14px"/>
				</div>		
				<p class="forgot"><a href="forgotPassword.htm">Forgot Password?</a></p>
				<span><button class="stylish-btn btn-1 btn-1c" style="padding:10px 80px;">Login &nbsp;<i class="fa fa-user-circle-o" aria-hidden="true" style="font-size: 20px;"></i></button></span> 				        
			    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
			<button style="" class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick google-sign"  onclick="loadPage('https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=https://learningpriority.com/auth2callback.htm&response_type=code&client_id=917074430981-t4893cqjouiil5u1ve27iag5dhs4ato2.apps.googleusercontent.com&approval_prompt=force')"><i class="fa fa-google-plus"></i></button>
        </div>
        <div id="signup" style="margin:-25px 0 25px;">   
        	<h1>Sign Up here!</h1>          
          	<form:form name="verificationfrm" modelAttribute="userRegistration" method="GET" action="checkVerification.htm">
	           <div class="field-wrap">
	           	<label>
	            	Email Address<span class="req">*</span>
	            </label>  
	            <form:input style="display:n0ne;" required="required" path="emailId"  autocomplete="off" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" title="Please enter valid email"  /> 
	           </div>
	           <div class="field-wrap">
	           	<label>
	            	Verification Code<span class="req">*</span> 
	           	</label>  
	            <form:input path="verificationCode" required="required" autocomplete="off"/>
	            <form:hidden path="regId"></form:hidden>
	           </div>
           		<p class="forgot"><a href="firstTimeUserInfo.htm">Get Verification Code?</a></p>          
          		<button type="submit" class="stylish-btn btn-1 btn-1c" style="padding:10px 80px;">Get Started</button>
        	</form:form>  
       	</div>
      </div><!-- tab-content -->
</div> <!-- /form -->
	</div>
	<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
	<script src="resources/javascript/index.js"></script>
</body>
</html>