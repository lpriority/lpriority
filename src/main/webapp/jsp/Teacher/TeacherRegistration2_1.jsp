<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<table name="classes" border="0" cellpadding="0" cellspacing="0"
			vspace="0" width="439" hspace="0">

			<tbody>
			<tr><td colspan="6" style="font-size: 14px;text-shadow:0 1px 1px rgb(212, 212, 212);color:white;"><u><h1><font color="#006471" size="4"> Select Your Subjects For <c:out value="${gradeName}"></c:out></font></h1></u></td></tr>
				<%
					int i = 0;		
				%>
				<c:forEach items="${classes}" var="cList">
					<%
						if (i == 0) {
							
					%>
					<tr>
						<%
							}
						%>
						<%
							i++;
						%>
						<td width="10"><input type="checkbox" name="${count}" id="${count}"
								value="${cList.studentClass.classId}:${cList.studentClass.className}" /></td>
						<td class="instructions" width="108" style="color:black;text-shadow:0 1px 2px rgb(138, 138, 138);">${cList.studentClass.className}</td>
						<%
							if (i == 3) {
						%>
					</tr>
					<%
						i = 0;
								}
					%>
				</c:forEach>
			</tbody>
		</table>
 </body>
</html>
