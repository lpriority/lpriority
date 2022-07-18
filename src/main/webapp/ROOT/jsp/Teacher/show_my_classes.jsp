<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="showMyClassesTimeTable">
<table width="100%" >
        <c:if test="${divId eq 'showMyClasses'}">
		<c:choose>
			<c:when test="${fn:length(casLt) gt 0}">
			<tr><td id="clsShow">
			   	<table valign="middle" align="center" class="space-between">
					<tr>   
						<td height="30" colspan="2" align="left" valign="middle"> 
						<table valign="middle" align="right" border="1" class="designTblCls">
						    <tr></tr>
						 	<c:forEach items="${schoolDaysLt}" var="schoolDays">
					            <tr>
						           	<th align="center" valign="middle" class="header" width="120" style="color:white;font-size:20;background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#007B8C) )"><b> ${schoolDays.days.day}</b></th>
						           	 <c:forEach items="${casLt}" var="cas" > 
				                        <c:if test="${cas.days.dayId eq schoolDays.days.dayId}"> 
				                            <td align="center" width="180px">
				                             <table border="1">
				                               <tr><td><b>${cas.periods.startTime}-${cas.periods.endTime}</b><br/></td></tr> 
				                               <tr><td align="center">
				                                  ${cas.periods.periodName} <br/>
					                          	  ${cas.periods.grade.masterGrades.gradeName}<br/>
					                          	  ${cas.classStatus.section.section}<br/> 
					                          	  ${cas.classStatus.section.gradeClasses.studentClass.className}<br/> 
					                          	 </td></tr>
					                          </table>  
					                          </td>
		   	                          </c:if>
				                     </c:forEach>
						        </tr>
						   </c:forEach>
						</table>
					</td></tr>
				</table>
			</td></tr>	
		</c:when>
		<c:otherwise> 
		<tr><td align="center" height="100">
		 	<font color="blue" size="3">Teacher not yet scheduled..</font>
		</td></tr>
		</c:otherwise>
		</c:choose>
        </c:if>
      </table>
</div>    
</body>
</html>