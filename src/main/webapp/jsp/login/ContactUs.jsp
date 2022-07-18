<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to The Learning Priority Inc | Contact Us</title>
	<link rel="stylesheet" href="resources/css/normalize.css">
	<link rel="stylesheet" href="resources/css/registration-css.css"> 
	<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/common_special_effects.css" rel="stylesheet" type="text/css" />
	<c:if test="${userReg ne null}">
		<link rel="stylesheet" href="resources/css/common_registration.css"> 
	</c:if>
</head>
<body>
<c:if test="${userReg == null}">
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
</c:if>	
	<div class="form" style="margin-top:-1em;">
      <div class="tab-content">
      	  <div id="ftui">   
          	<h1>Contact Us</h1>
			<form:form class="contact-us-form" name="contactusform" method="POST"
				action="contactUsSuccess.htm" autocomplete="off">  
				<div class="top-row">
					<div class="field-wrap">
					<label style="display: block;">
				    	Name<span class="req">*</span>
				  	</label>
				  	<input type="text" name="name" id="name" pattern="[a-zA-Z]{3,20}" required="required"  />
					</div> 				
					<div class="field-wrap">
					<label style="display: block;">
					    Email Address<span class="req">*</span>
					  </label>
					  <input type="email" name="email" id="email" required="required"/>
					</div>	
				</div>	
				<div class="top-row">
					<div class="field-wrap">
						<label style="display: block;">
					    	Phone Number<span class="req">*</span>
					  	</label>
					  	<input type="text" name="phone"
							id="phone" pattern="[0-9]{10}" maxlength="10" required="required"
							title="Enter Valid Phone Number" />
					</div>		
					<div class="field-wrap">
						<select name="iam" id="iam" required="required" title="Mandatory">
							<option value="">Select one</option>
							<option value="A School Administrator">A School
								Administrator</option>
							<option value="A Parent">A Parent</option>
							<option value="A Teacher">A Teacher</option>
							<option value="An Investor">An Investor</option>
							<option value="A Guest">A Guest</option>
						</select> 
					</div>
				</div>
				<div class="field-wrap">
					<label>
				    	Message<span class="req">*</span>
				 	</label>
					<textarea name="comment" id="comment"
					required="required"></textarea>
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