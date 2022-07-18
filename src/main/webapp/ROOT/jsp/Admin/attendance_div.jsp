<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function () {
		 $('#statusDiv').fadeOut(3000);
	});
</script>
</head>
<body>
	<div align="center" >	
		
		<c:if test="${tab == 'daily attendance'}">
				<c:choose>
					<c:when test="${fn:length(attendance) gt 0 }">
					<table style="width: 80%">
						<tr><td colspan="2" style="padding-bottom: 0.5cm">&nbsp;</td></tr>
						<tr>
							<td colspan="2" align="center"><span class="tabtxt"> Teacher Name :</span> 
								<span class="txtStyle">${attendance[0].classStatus.teacher.userRegistration.title} 
								${attendance[0].classStatus.teacher.userRegistration.firstName} 
								${attendance[0].classStatus.teacher.userRegistration.lastName}</span>
							</td>
						</tr></table><br>
						<table class="des" border=0>
						<tr><td width="400"><div class="Divheads"><table>
						<tr>
							<th width="200" align="left">Student Name</th>
							<th width="200" align="center">Status</th>
						</tr></table></div>				
<!-- 						<tr><td style="padding-bottom: 8px"></td></tr>	 -->
				<div class="DivContents"><table>
				<c:forEach items="${attendance}" var="attend">
					<tr>
					<td width="200" align="left" class="txtStyle">${attend.student.userRegistration.firstName } ${attend.student.userRegistration.lastName }</td>
					<td width="200" align="center" class="txtStyle">${attend.status }</td>	
					</tr>
					<tr><td style="padding-bottom: 8px"></td></tr>
				</c:forEach></table></div></td></tr></table>
				</c:when>									   
					<c:otherwise>
					<table width="100%"><tr><td height="60" id="statusDiv" align="center" class="status-message">No Data available.</td></tr></table>
					</c:otherwise>
				</c:choose>
			
		</c:if>
		<c:if test="${tab == 'weekly attendance'}">
			
				<c:choose>
					<c:when test="${fn:length(attendance) gt 0 }">
					<table style="width: 80%">
						<tr><td colspan="6" style="padding-bottom: 0.5cm">&nbsp;</td></tr>
						<tr>
							<td align="center" ><span  class="tabtxt">Teacher Name :</span> <span class="txtStyle"> ${attendance[0].teacherName}</span> </td>
							<td colspan="5"></td>
						</tr></table><br>
						<table class="des" border=0>
						<tr><td width="1000"><div class="Divheads"><table>
						
						<tr>
							<th width="200" align="left">Student Name</th>
							<th width="160" align="left"># Present Days</th>
							<th width="160" align="left"># Absent Days</th>
							<th width="250" align="left"># Excused Absent Days</th>
							<th width="160" align="left"># Tardy Days</th>
							<th width="200" align="left"># Excused Tardy Days</th>
						</tr>	</table></div>
						<div class="DivContents"><table>				
					
				<c:forEach items="${attendance}" var="attend">
					<tr>
					<td width="200" align="left" class="txtStyle">${attend.studentName}</td>
					<td width="160" align="center" class="txtStyle">${attend.presentDays}</td>
					<td width="160" align="center" class="txtStyle">${attend.absentDays}</td>
					<td width="250" align="center" class="txtStyle">${attend.excusedAbsentDays}</td>
					<td width="160" align="center" class="txtStyle">${attend.tardyDays}</td>
					<td width="250" align="center" class="txtStyle">${attend.excusedTardyDays}</td>	
					</tr>
					
				</c:forEach></table></div></td></tr></table>
				</c:when>									   
					<c:otherwise>
						<table width="100%"><tr><td height="60" id="statusDiv" align="center" class="status-message">No Data available.</td></tr></table>
					</c:otherwise>
				</c:choose>
			
		</c:if>
	</div>
</body>
</html>