<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div align="center">
<table width="100%">
  <tr><td>
	 	<table align="center" width="35%">
		  <tr>
	            <td align='left'  class="tabtxt"><b>Teacher Name : </b> &nbsp;&nbsp;${sectionTeacher}</td>
	       </tr>
	 	</table></td></tr>
	
	
</table>
<table><tr><td width="700">
<table class="des" border=0 align="left"><tr><td><div class="Divheads"><table>
	        
			<tr >
				<th  align="left" width="120px">Student name</th>
				<th  align="center" width="200px">Homework Percentage</th>
				<th  align="center" width="200px">Assessment Percentage</th>								
			</tr></table></div>
			<div class="DivContents"><table>
			<tr><td height="25"></td></tr>
			<c:forEach items="${studentList}" var="cList">					
				<tr >
					<td  align="left" width="120px" style="padding-bottom: 15px;" >
						<label class="txtStyle">
							${cList.student.userRegistration.firstName} ${cList.student.userRegistration.lastName}</label>
					</td>					
					<td style="padding-bottom: 15px;"  align="center" width="200px" class="txtStyle">
					<c:choose>
						<c:when test="${homeworkAverages[cList.student.studentId] >= 0 }">
							<fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${homeworkAverages[cList.student.studentId]}" />
							${formattedPercentage}
						</c:when>
						<c:otherwise>
							0.0
						</c:otherwise>
					</c:choose>
					</td>
					<td style="padding-bottom: 15px;"  align="center" width="200px" class="txtStyle">
					<c:choose>
						<c:when test="${assessmentAverages[cList.student.studentId] >= 0 }">
							<fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${assessmentAverages[cList.student.studentId]}" />
							${formattedPercentage}
						</c:when>
						<c:otherwise>
							0.0
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:forEach>
		
		</table></div>
	</td></tr>
</table>	</td></tr></table>
</div>
</body>
</html>