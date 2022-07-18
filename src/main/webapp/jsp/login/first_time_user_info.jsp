
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="google-site-verification" content="LmLtSNCPLfi7dv9VmtUDmfz50ayk66Ttll7JqQ3Bmzc" />
<title>Welcome to The Learning Priority Inc | First Time User Information</title>
	<link rel="stylesheet" href="resources/css/normalize.css">
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_special_effects.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="resources/css/registration-css.css"> 
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
      	  <div id="ftui">   
          	<h1>First Time User Information</h1>
          	<ol style="padding-left:30px;padding-right:30px;font-style:italic;font-size: 13.8px; color:#0f2d3b; background-color: transparent; text-decoration: none;text-align:justify;color:#002440;">
				<li>					
					Enter your Email Address. Before signup.<br/> 
					(<strong>Note:</strong>
					Your email must already be entered into the Learning Priority
					database by the school before you can receive your Password.)
				</li>
				&nbsp;&nbsp;
				<li>					
					If your email address matches the
					email address that the school has recorded, a random
					password will be emailed to you at that address.
				</li>							
			</ol>
			<form:form class="first-user-form" name="FirstTimeForm" id="FirstTimeForm" modelAttribute="security" action="FirstTimeUserInfo.htm" method="POST">
				<div class="field-wrap">
				<label>
				    Email Address<span class="req">*</span>
				  </label>
				  <input type="email" name="homeEmail" id="homeEmail" required="required"/>
				</div>			
				<div class="field-wrap">
					<select id="userType" name="userType" required="required">
						<option value="">Select User Type</option>
						<c:forEach items="${userlists}" var="ul">
							<option value="${ul.userTypeid}"> ${ul.userType}</option>
						</c:forEach>
					</select>
				</div>		
				<button class="stylish-btn btn-1 btn-1c" style="padding:10px 75px;">Submit</button>            
			</form:form>
       	</div>
       	<div id="blank">    
       	</div>
       </div><!-- tab-content -->
	</div> <!-- /form -->
	<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src="resources/javascript/index.js"></script>
</body>
</html>