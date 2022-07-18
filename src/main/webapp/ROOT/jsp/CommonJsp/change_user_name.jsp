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
<script src="resources/javascript/imageloadfunctions.js"></script>
<!-- <script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<!-- 
<!-- <script src="/resources/javascript/notify/notify.js"></script> -->
</head>
<body>
	
 <form:form id="changeUserName" modelAttribute="userRegistrationForm" action="updateUserName.htm" method="GET">
  	              <input id="currentUserName"  name="currentUserName" type="hidden" value="${userRegistration.emailId}"/>
				  <input type="hidden" name="regId" id="regId" value="${userRegistration.regId}" />		
				  <!-- Content center start -->		  
  	              <div class="center_of_div" style="padding-top:0px;">
  	              <table width="80%" align="left" border="0" cellspacing="0" cellpadding="0" style="padding:0px; margin:0px;">
  	              			      <tr>
                                    <td width="1000" height="25" colspan="4" align="left" valign="middle" class="text" style="color:#002064;font-weight:400px;font-family: Candara, Calibri, Segoe, Segoe UI, Optima, Arial, sans-serif;font-size: 12px;">Changing your username is equivalent to changing your email address. This means
                                        in the future all Learning<br/>Priority's correspondences will be direct to this  new mail 
                                        address. We discourage changing your username<br/>because of the confusion that can result for this
                                </tr>                         
                               <tr><td  width="1000" align="left" style="padding-left:2em;"><br>
                                <table class="des" width="65%" align="left"><tr><td><div class="Divheads"><table><tr><td class="headsColor">Change User Name</td></tr></table></div>
                                <div class="DivContents"><table>
                                <tr><td height=20 colSpan=30>&nbsp;</td></tr>
                                    <tr>
                                        
                                        <td width="250" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Current Username</td>
                                        <td width="80" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="200" height="30" align="left" valign="middle" class="tabtxt"><input  path="emailId" type="email" name="emailId" id="emailId" value="" required="required"  onblur="checkUsername()"/></td>
                                    </tr>
                                    <tr>
                                        
                                        <td width="200" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Enter New Username</td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="200" height="30" align="left" valign="middle" class="tabtxt"><input path="newUserName" type="email" name="newUserName" id="newUserName" required="required"  onblur="userNameValidation(this);" /></td>
                                    </tr>
                                    <tr>
                                        
                                        <td width="200" height="30" align="right" valign="middle" class="tabtxt"><img src="images/Common/required.gif"> Re-Enter New Username</td>
                                        <td width="10" height="30" align="center" valign="middle" class="text1">:</td>
                                        <td width="200" height="30" align="left" valign="middle" class="tabtxt"><input type="email" name="reEnteredUserName" id="reEnteredUserName"  required="required"  onblur="userNameValidation(this)"  /></td>
                                    </tr>
	                                </table></div></td></tr>
	                                 <tr><td height="10" ><table width="100%"><tr>
	                                    <td width="35%" height="10" align="right" valign="middle"> 
	                                   		 <div class="button_green" align="right" onclick="updateUserName()">Submit Changes</div> 
	                                    </td>
	                                    <td width="30%" height="10" align="left" valign="middle"  style="padding-left: 2em;">
	                                    	 <a href="#" onclick="resetForm('changeUserName')" class="button_green">Clear</a> 
	                                    </td>
	                                    </tr>
	                                     <tr><td height=10 colSpan=30></td></tr>
	                                    </table>
	                                </td></tr>
	                                </table></td></tr>
                            </table>  
                          </div>  
  </form:form>
</body>
</html>