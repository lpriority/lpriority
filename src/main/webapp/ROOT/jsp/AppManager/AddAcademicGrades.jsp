<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form:form id="AcademicGradeForm" modelAttribute="academicGrade"
		method="post" action="saveAcademicGrade.htm">
		<table>
			<tr>
				<td><form:label path="acedamicGradeName">* Academic Grade Name:</form:label></td>
				<td><form:input path="acedamicGradeName" />  <font color="red"> <form:errors path="acedamicGradeName"></form:errors></font></td>
			</tr>
			<tr>
				<td><form:label path="scoreFrom">* Score From:</form:label></td>
				<td><form:input path="scoreFrom" /> <font color="red"> <form:errors path="scoreFrom"></form:errors></font></td>
			</tr>
			<tr>
				<td><form:label path="scoreTo">* Score To:</form:label></td>
				<td><form:input path="scoreTo" /> <font color="red"> <form:errors path="scoreTo"></form:errors></font> </td>
			</tr>
			<tr>
			<td><form:label path="academicId">* Academic Performance:</form:label></td>
				<td><form:select path="academicId">
						<option value="select">Select Academic Performance</option>
						<c:forEach var="apList" items="${academicPerformanceList}">
							<option value="${apList.academicId}"
								<c:if test="${id eq apList.academicId}"> selected="Selected" </c:if>>${apList.academicLevel}</option>
						</c:forEach>
					</form:select>
					<font color="red"><form:errors path="academicId"></form:errors></font></td>
			</tr>
			<tr>
				<td><form:hidden path="acedamicGradeId" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="image" src="images/Common/submitChangesUp.gif"
					alt="Save" style="border: none; vertical-align: -5px" /></td>
				<td><a href="displayAcademicGrades.htm"><img
						src="images/Admin/cancel_button.png" alt="Close"
						style="border: none"> </a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

