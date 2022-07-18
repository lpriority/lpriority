<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript" src="resources/javascript/common/md5.js"></script>
<script src="resources/javascript/imageloadfunctions.js"></script>
<!-- <script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />

<!-- <script src="/resources/javascript/notify/notify.js"></script> -->

</head>
<body>
 <form:form id="changePassword" modelAttribute="userRegistrationForm" action="updatePassword.htm" method="GET">
		  	<input type="hidden" id="currentPassword"  name="currentPassword" value="${password}"/>
	  		<input type="hidden" name="regId" id="regId" value="${regId}" />
             <!-- Content center start -->		  
  	         <table class="des" width="32%"><tr><td><div class="Divheads"><table><tr><td class="headsColor">Change Password</td></tr></table></div>
  	         <div class="DivContents">
  	        	<table>
					<tr><td height=10 colSpan=80>&nbsp;</td></tr>
                       <tr>
                           <td width="1111" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Enter Current Password</td>
                           <td width="1000" height="30" align="center" valign="middle" class="text1">:</td>
                           <td width="300" height="30" align="left" valign="middle" class="tabtxt"><input  type="password" name="password" id="password" value="" required="required"  onblur="checkPassword()"/></td>
                       </tr>
                       <tr>
                           <td width="300" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Enter New Password </td>
                           <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                           <td width="200" height="30" align="left" valign="middle" class="tabtxt"><input path="newPassword" type="password" name="newPassword" id="newPassword" required="required"  onblur="passwordValidation(this);" /></td>
                       </tr>
                       <tr>
                           <td width="300" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Re Enter New Password</td>
                           <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                           <td width="200" height="30" align="left" valign="middle" class="tabtxt"><input path="confirmPassword" type="password" name="reEnteredPassword" id="reEnteredPassword" required="required"  onblur="passwordValidation(this)"  /></td>
                      </tr>
             </table></div></td></tr>
                <tr><td height="10" ><table width="100%"><tr>
                           <td width="35%" height="10" align="right" valign="middle"> 
                          		 <div class="button_green" align="right" onclick="updatePassword()">Submit Changes</div> 
                           </td>
                           <td width="30%" height="10" align="left" valign="middle"  style="padding-left: 2em;">
                           	 <a href="#" onclick="resetForm('changePassword')" class="button_green">Clear</a> 
                           </td>
                           </tr>
                            <tr><td height=10 colSpan=30></td></tr>
                           </table>
                       </td></tr>
                </table>
</form:form>
</body>
</html>