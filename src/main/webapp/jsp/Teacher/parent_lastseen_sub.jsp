<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
</head>
<body>
<table><tr><td>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
<c:if test="${fn:length(parentLastseen) > 0}">
<c:set var="bor" value="0"></c:set>
<c:set var="de" value="des"></c:set>
</c:if>
</td></tr></table>
<table border="${bor}" class="${de}"><tr><td algin='center'>
     
<c:if test="${fn:length(parentLastseen) > 0}">
<div class="Divheads">
 <table align=center>
<!-- 		<table name="classes" border="0" cellpadding="0" cellspacing="0" -->
<!-- 			vspace="0" width="80%" hspace="0" align="center"> -->

			<tbody>
						
				<tr>
						<td  align="left" width="200">Student Name</td>
						<td  align="center" width="250">Parent Email</td>
						<td  align="center" width="200">Logged In</td>	
						<td  align="center" width="200">Logged Out</td>
						<td  align="center" width="250">Last Seen</td>							
				</tr>
				</tbody></table></div>
			 	 <div style="padding: 2px 5px 2px 5px"><table align=center> 
				<c:forEach items="${parentLastseen}" var="cList">					
					<tr>				
						<td  align="left" width="200" class="txtStyle"> 
							${cList.childName}
						</td>
						<td  align="center" width="250" class="txtStyle">
							<c:choose>
                       			<c:when test="${cList.parentEmail != null}">
                       				${cList.parentEmail}
                       			 </c:when>
                       			<c:otherwise>
                            		---
                       			</c:otherwise>
                     		 </c:choose>							
						</td>
						<td  align="center" width="200" class="txtStyle">
							<c:choose>
                       			<c:when test="${cList.lastLoggedIn != null}">
	                       			<c:set var="now" value="${cList.lastLoggedIn}" />
	                       			<fmt:formatDate type="date" value="${now}" />&nbsp;
									<fmt:formatDate type="time" value="${now}" />
                       			 </c:when>
                       			<c:otherwise>
                            		---
                       			</c:otherwise>
                     		 </c:choose>							
						</td>	
						<td  align="center" width="200" class="txtStyle">
							<c:choose>
                       			<c:when test="${cList.lastLoggedOut != null}">
                       				<c:set var="now" value="${cList.lastLoggedOut}" />
	                       			<fmt:formatDate type="date" value="${now}" />&nbsp;
									<fmt:formatDate type="time" value="${now}" />	                       				
                       			 </c:when>
                       			<c:otherwise>
                            		---
                       			</c:otherwise>
                     		 </c:choose>							
						</td>	
						<td  align="center" width="250" class="txtStyle">
							<c:choose>
                       			<c:when test="${cList.lastSeenFeature != null}">
                       				${cList.lastSeenFeature}
                       			 </c:when>
                       			<c:otherwise>
                            		---
                       			</c:otherwise>
                     		 </c:choose>							
						</td>								
					</tr>
					<tr><td><td>&nbsp;</td></tr>					
				</c:forEach></table></div></c:if>
				 <table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>
                <tr><td align='center' valign='middle'></td>
                <c:if test="${fn:length(parentLastseen) <= 0}">
			          
                <td align='center' valign='middle' class='tabtxt'>No Parent Last seen</td>
                  </c:if>
            <td align='center' valign='middle'>
            </td></tr></table>
					
			
		</table>
							

   

</body>
</html>