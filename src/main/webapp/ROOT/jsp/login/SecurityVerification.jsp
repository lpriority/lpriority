<%@page isErrorPage="false" errorPage="../Fail.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="resources/css/homepage_design.css" rel="stylesheet" type="text/css" />
<title>Learning Priority|Security Verification</title>
</head>
<body>
<div align="center"><img src="images/Login/logoBlackUp.gif" style="padding-top: 1em" width="222" height="21" name="logoblack" vspace="0" hspace="7" border="0" alt="The Learning Priority"></div>
<div align="center" style="font-style: normal;"><h2 >Sign up with Learning Priority<h2></div>
    <div class="">
   	   <div class="verification-form">
		    <form:form  name="verificationfrm" modelAttribute="userReg" method="GET" action="checkVerification.htm">
	  			<table width="100%" height="104" border="0" cellpadding="0" >
					<tr>
						<td height="35" align="center" valign="middle">
							<form:input path="emailId"  placeholder="Enter Email/User Name" required="required" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" />
						</td>
					</tr>
					<tr>
						<td width="51%" height="35" align="center" valign="middle">
							<form:input path="verificationCode"  placeholder="Enter Verification Code" required="required" />
							<form:hidden path="regId"></form:hidden>
						</td>
					</tr>
					<tr>
						<td height="50" align="center" valign="middle">
							<input name="image2" type="submit" class="button_green" value="Submit" />
							<a href="index.htm" ><input type="button" value="Home Page" class="button_green"></a>
						</td>
					</tr>
				</table>
				 <p class="font-style"> <a href="firstTimeUserInfo.htm" class="forgotPassword">First Time User?</a></p>
		    </form:form>
		</div>
	</div>
</body>
</html>
