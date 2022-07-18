<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Learning Priority|Invite Others</title>
	<link rel="stylesheet" href="resources/css/normalize.css">
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_special_effects.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="resources/css/registration-css.css"> 
<style type="text/css">
input[type="text"],input[type="email"],input[type="password"]{
	width:100%;
}
</style>
</head>
	<%
		if (session.getAttribute("userReg") == null) {
	%>
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
	<% } %>
	<div class="form" style="max-width:450px;margin-top:-1em;">
      <div class="tab-content">
      	  <div id="forgot">   
          	<h1>Invite Others to Join You</h1>
          	<h4>Invite your fellow educators, friends and associates to join Learning Priority ! </h4>
			<form:form modelAttribute="invitation" action="inviteOthers.htm" method="POST">
				<div class="top-row">          
					<div class="field-wrap">
					<label style="display: block;">
					    First Name<span class="req">*</span>
					  </label>
					  <form:input path="firstName" required="required" title="Enter First Name"/>
					</div>	
					<div class="field-wrap">
					<label style="display: block;">
					    Last Name<span class="req">*</span>
					  </label>
					  <form:input path="lastName" required="required" title="Enter Last Name"/>
					</div>	
				</div>
				<div class="top-row">    
					<div class="field-wrap">
					  <form:select path="userTypeid" required="required">
					  	<option value="">select</option>
						<c:forEach items="${userTypesList}" var="utl">
							<option value="${utl.userType}">${utl.userType}</option>
						</c:forEach>
					  </form:select>
					</div>
					<div class="field-wrap">
						<label style="display: block;">
						    Their Email Address<span class="req">*</span>
						</label>
						<form:input path="inviteeEmail" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required="required" />
					</div>		
				</div>	
				<div class="field-wrap"> 
					<label style="display: block;">
						Message<span class="req">*</span>
					</label>
					<form:textarea path="message" ></form:textarea>
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
<body>
</body>
</html>

