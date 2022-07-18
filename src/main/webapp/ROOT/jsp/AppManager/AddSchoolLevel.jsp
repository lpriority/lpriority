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
	<form:form id="SchoolLevelForm" modelAttribute="schoolLevel"
		action="saveSchoolLevel.htm">
		<!--  method="post"-->
		<table>
			<tr>
				<td><form:label path="schoolLevelName">* School Level Name:</form:label></td>
				<td><form:input path="schoolLevelName" />  <font color="red"> <form:errors path="schoolLevelName"></form:errors></font></td>
			</tr>
			<tr>
				<td><form:hidden path="schoolLevelId" /></td>
			</tr>
			<tr>
				<td><input type="image" src="images/Common/submitChangesUp.gif"
					alt="Save" style="border: none; vertical-align: -5px" /></td>
			</tr>
			<tr>
				<td><a href="displaySchoolLevels.htm"><img
						src="images/Admin/cancel_button.png" alt="Close"
						style="border: none"> </a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

