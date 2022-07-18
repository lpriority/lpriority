<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AddStudentsToClassList</title>
<script type="text/javascript">
$(document).ready(function () {
 	$('#returnMessage').fadeOut(4000);
});
</script>
</head>
<body>

		<table name="classes" border="0" cellpadding="0" cellspacing="0"
			vspace="0" width="439" hspace="0" align="center" class="txtStyle des">

			<tbody>
				<c:if test="${fn:length(allPromoteStudents) > 0}">				
					<tr class="Divheads">
							<td  align="center" width="150" height="60" class="tabtxt">Student Name</td>
							<td align="center" width="108" class="tabtxt"><a href="#" onclick="checkAllStudents()">Check All</a></td>							
					</tr>
					<tr><td><td>&nbsp;</td></tr>
				 	
				 	<%
						int i = 0;
					%>
				 	
					<c:forEach items="${allPromoteStudents}" var="cList">					
						<tr>
							<td align="center" width="150" class="txtStyle">${cList.userRegistration.firstName} ${cList.userRegistration.lastName}</td>
							<td align="center" width="108"><input type="checkbox" class="checkbox-design" name="studentId" id="add:<%=i%>" value="${cList.studentId}"/><label for="add:<%=i%>" class="checkbox-label-design"></label></td>
						</tr>
						<tr><td style="padding-bottom: 10px"></td></tr>
						
						<%
							i++;
						%>
						
					</c:forEach>
					<tr>
						<td align="center" colspan="2" style="padding-top: 2em;">
						<div class="button_green" align="right" onclick="return validationCheck();">Submit</div>
						</td>
					</tr>
				</c:if>
				<c:if test="${fn:length(allPromoteStudents) == 0}">				
					<tr>
						<td align="center" colspan="2" style="padding-top: 2em;">
							<label style="color: blue;" class="txtStyle">No Students in this Grade</label>
						</td>
					</tr>
				</c:if>
			</tbody>		
			
		</table>
</body>
</html>