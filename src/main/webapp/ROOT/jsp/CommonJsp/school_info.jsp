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
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />\

<!-- <script src="/resources/javascript/notify/notify.js"></script> -->

</head>
<body>
  <input type="hidden" name="regId" id="regId" value="${userRegistration.regId}" />
   <!-- Content center start -->		  
  <p align="center">
 	 <table border=0 class="des" width="35%" align="center" style=""><tr><td>
 	 	<div class="Divheads"><table><tr>
           <td class="headsColor">Your Currently Selected School is </td>
       </tr></table></div>
       <div class="DivContents"><table>
        <tr><td height=10></td></tr>
      
           <tr>
               <td align="right" height="21" valign="middle"></td>
               <td align="left" valign="middle" width="18"><img src="" name="shim" width="5" height="1" hspace="0" vspace="0" border="0" id="shim" /></td>
               <td width="200" class="txtStyle">${userRegistration.school.schoolName}</td>
           </tr>
           <tr>
               <td align="right" height="21" valign="middle"></td>
               <td align="left" valign="middle" width="18"><img src="" name="shim" width="5" height="1" hspace="0" vspace="0" border="0" id="shim" /></td>
               <td width="200" class="txtStyle">${userRegistration.school.city}</td>
           </tr>
           <tr>
               <td align="right" height="21" valign="middle"></td>
               <td align="left" valign="middle" width="18"><img src="" name="shim" width="5" height="1" hspace="0" vspace="0" border="0" id="shim" /></td>
               <td width="200" class="txtStyle">${userRegistration.school.states.name}</td>
           </tr>
           
      
   </table></div></td></tr></table></p>
 
</body>
</html>