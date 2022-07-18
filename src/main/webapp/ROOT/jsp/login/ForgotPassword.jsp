<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="google-site-verification" content="LmLtSNCPLfi7dv9VmtUDmfz50ayk66Ttll7JqQ3Bmzc" />
<title>Welcome to The Learning Priority Inc | Learning Priority</title>
	<link rel="stylesheet" href="resources/css/normalize.css">
	<link rel="stylesheet" href="resources/css/registration-css.css">   
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_special_effects.css" rel="stylesheet" type="text/css" />
</head> 
<body>
<div align="center" valign="middle" style='padding-top: 1.5em;'>
		<table width="100%">
			<tr><td width="37%"  valign="middle" align="right">
				<span class="logoStyle1">Learning Priority</span>
			</td>
			<td  width="30%"  valign="middle" align="left">
				<img src="images/common_images/LpLOGO.png" width="90" height="60" style="">
			</td></tr>
			<tr>
				<td width="100%" valign="middle" align="right" colspan="2" style="padding-right:12em;">
					<button class="stylish-button button--pipaluk button--inverted  button--round-s button--text-thick" style="float:right;min-width: 100px;max-width: 100px;background: #00e3ff;text-shadow:0 1px 1px rgb(128, 128, 128);font-size: 11px;" onclick="loadPage('index.htm')"><i class="fa fa-home" aria-hidden="true" style="font-size: 18px;"></i>&nbsp; Go Home</button>
				</td>
			</tr>
		</table>
	</div>
	<div class="form" style="margin-top:-1em;">
      <div class="tab-content">
      	  <div id="forgot">   
          	<h1>Oops! Lost password?</h1>
          	<h4 style="font-style:italic;font-size: 13.8px; color:#0f2d3b; background-color: transparent; text-decoration: none; vertical-align: baseline; white-space: pre-wrap;">Answer a few question to recover your password. </h4>
			<form:form class="forgot-password-form" name="forgotpswform" method="POST"
				modelAttribute="security" action="checkForgotPassword.htm">          
				<div class="field-wrap">
				<label style="display: block;">
				    Email Address<span class="req">*</span>
				  </label>
				  <form:input path="emailId" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required="required" title="Enter valid Email Id"/>
				</div>	
				<div class="field-wrap">
					<form:select path="securityQuestionId" required="required" >
						<option value="">Select Security Question</option>
						<c:forEach items="${lists}" var="qlist">
							<option value="${qlist.securityQuestionId}">${qlist.question}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="field-wrap">
				  <label>
				    Answer<span class="req">*</span>
				  </label>
				  <form:input path="answer" required="required" title="Cannot be empty" />
				</div>		
				<button class="stylish-btn btn-1 btn-1c" style="padding:10px 75px;">Submit</button>            
			</form:form>
        </div>        
        <div id="blank">
        </div>
      </div><!-- tab-content -->
	</div> <!-- /form -->
	<script src="resources/javascript/index.js"></script>
</body>
</html>