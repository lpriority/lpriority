<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<table width="459"  align="center"   class="header" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center"  width="60" height="60">
			<table width="100%" border="1"cellpadding="1" cellspacing="1" style="background:#E3EDEF;"  >
			<tbody  class="designTblCls"   >
              	<c:forEach items="${schoolDaysLt}" var="schoolDays" varStatus="status">
             	   <tr> 
             	    <th width="30" height="30" align="center" style="color:white;font-size:20;background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#007B8C) );"><font size="4"><b>${schoolDays.days.day}</b></font></th>
              	 	 <c:forEach items="${casLt}" var="cas" > 
                     <c:if test="${cas.days.dayId eq schoolDays.days.dayId}">
                         <td align="center" width=60" style="background:white;"  >
                          <table border="1" style="" >
                            <tr><td align="center" width="60"><b>${cas.periods.startTime}-${cas.periods.endTime}</b><br/></td></tr> 
                            <tr><td align="center" width="60" style="background:white;color:black;text-align:center;font-weight:500;padding:1.5em 1em;">
                             <font color="black">  ${cas.periods.periodName}</font>
                         </td></tr>
                        </table>  
                        </td>
                         </c:if>
               </c:forEach>
  				</tr> 
 			</c:forEach> 
 			</tbody>
        </table>  
     </td></tr>
      <tr><td height=45 colSpan=4><div align="right"></div></td></tr>       
     <tr>
      <td width="35%" height="55" align="center" valign="middle"><div id="sendRequest" name="sendRequest" alt="sendRequest" class="button_green" align="right" onclick="setStatusForClassRegistration()">Send Request</div>
      </td>
     </tr>    
</table>  
</body>
</html>