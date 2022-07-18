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
<script type="text/javascript" src="resources/javascript/app_manager/delete_functions.js"></script>

</head>
<body>
	<table>
		<tr>
			<th>School Level ID</th>
			<th>School Level</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${slList}" var="slList">
			<tr>
				<td>${slList.schoolLevelId}</td>
				<td>${slList.schoolLevelName}</td>
				<td><a href="editSchoolLevel.htm?id=${slList.schoolLevelId}"><img
						src="images/Admin/edit_button.png" width="30" height="30"></a></td>
				<td>&nbsp;</td>
				<td><img src="images/Admin/delete_button.png" width="40" height="30" onclick="deleteSchoolLevel()"></td>
			</tr>
		</c:forEach>
		<tr>
			<td><a href="addSchoolLevel.htm"><img
					src="images/Admin/add_button.png" width="30" height="30"></a></td>
			<td><a href="appManagerHomePage.htm"><img src="images/Admin/cancel_button.png" width="40"
				height="30"></a></td>
		</tr>
	</table>
</body>
</html>
