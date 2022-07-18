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
<style >
	textarea{
	width: 300px;
 	height: 50px;
	}
</style>
<!-- <script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />

<!-- <script src="/resources/javascript/notify/notify.js"></script> -->

</head>
<body>
<div style="padding-left:2em;">
 <form:form id="homePage" modelAttribute="userRegistrationForm" action="updateHomePage.htm" method="GET">
	  		   <input type="hidden" name="regId" id="regId" value="${userRegistration.regId}" />
  	           <table width="55%" class="des" style="padding-left:10em;">
                <tr>
                <td><div class="Divheads"><table><tr><td class="headsColor">Home Page</td></tr></table></div>
                <div class="DivContents">
                    <table width="95%">
	                        <tr>
	                            <td colspan="6" height="25" width="123"></td>
	                        </tr>
                            <tr>
                                
                                <th width="400"  align="right" valign="middle" class="tabtxt">Education</th>
                                <td width="50"  align="center" valign="middle" class="text1" style="padding : 0px 0.5cm; ">:</td>
                                <td width="200"  colspan="3" align="left" valign="top" class="tabtxt"><textarea path="education" name="education" id="education" cols="70" rows="7" >${userRegistration.education}</textarea></td>
                            </tr>
                            <tr>
	                            <td colspan="6" height="15" width="123"></td>
	                        </tr>
                            <tr>
                               
                                <th width="600"  align="right" valign="middle" class="tabtxt">Experience And Awards  </th>
                                <td width="5"  align="center" valign="middle" class="text1">:</td>
                                <td width="150"  colspan="3" align="left" valign="top" class="tabtxt"><textarea path="experience" name="experience" id="experience" cols="70" rows="7" >${userRegistration.experience}</textarea></td>
                            </tr>
                             <tr>
	                            <td colspan="6" height="15" width="123"></td>
	                        </tr>
                            <tr>
                              
                                <th width="450"  align="right" valign="middle" class="tabtxt">Subjects Taught  </th>
                                <td width="5"  align="center" valign="middle" class="text1">:</td>
                                <td width="150"  colspan="3" align="left" valign="top" class="tabtxt"><textarea path="subjects" name="subjects" id="subjects" cols="70" rows="7">${userRegistration.subjects}</textarea></td>
                            </tr>
                             <tr>
	                            <td colspan="6" height="15" width="123"></td>
	                        </tr>
                            <tr>
                                
                                <th width="450"  align="right" valign="middle" class="tabtxt">Interest Areas  </th>
                                <td width="5"  align="center" valign="middle" class="text1">:</td>
                                <td width="150"  colspan="3" align="left" valign="top" class="tabtxt"><textarea path="interestareas" name="interestareas" id="interestareas" cols="70" rows="7">${userRegistration.interestareas}</textarea></td>
                            </tr>
                             <tr>
	                            <td colspan="6" height="15" width="123"></td>
	                        </tr>
                            <tr>
                                
                                <th width="300"  align="right" valign="middle" class="tabtxt"> Projects </th>
                                <td width="5"  align="center" valign="middle" class="text1">:</td>
                                <td width="200"  colspan="3" align="left" valign="top" class="tabtxt"><textarea path="projects" name="projects" id="projects" cols="70"  rows="7">${userRegistration.projects}</textarea></td>
                            </tr>
                             <tr>
	                            <td colspan="6" height="15" width="123"></td>
	                        </tr>
                            <tr>
                               
                                <th width="450"  align="right" valign="middle" class="tabtxt">Contact Information </th>
                                <td width="5"  align="center" valign="middle" class="text1">:</td>
                                <td width="150"  colspan="3" align="left" valign="top" class="tabtxt"><textarea path="contactinfo" name="contactinfo" id="contactinfo" cols="70" rows="7">${userRegistration.contactinfo}</textarea></td>
                            </tr>
                           </table></div></td></tr>
                                 <tr><td height="10" >
							    	<table width="100%">
							    	<tr>
						               <td width="35%" height="80" align="right" valign="middle"> 
						              		 <div class="button_green" align="right" onclick="updateHomePage()">Submit Changes</div> 
						               </td>
						               <td width="30%" height="10" align="left" valign="middle"  style="padding-left: 2em;">
						               	 <a href="#" onclick="resetForm('homePage')" class="button_green">Clear</a> 
						               </td>
						               </tr>
						                <tr><td height=10 colSpan=30></td></tr>
						               </table>
						        </td></tr>
                            </table>
  			</form:form>
 		 </div>
</body>
</html>