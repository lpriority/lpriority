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
			<th>Teacher Performance ID</th>
			<th>Teacher Performance</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${teacherPerformancesList}" var="tPList">
			<tr>
				<td>${tPList.id}</td>
				<td>${tPList.performance}</td>
				<td><a href="editTeacherPerformance.htm?id=${tPList.id}"><img
						src="images/Admin/edit_button.png" width="30" height="30"></a></td>
				<td>&nbsp;</td>
				<td><img src="images/Admin/delete_button.png" width="40" height="30" onclick="deleteTeacherPerformance()"></td>
			</tr>
		</c:forEach>
		<tr>
			<td><a href="addTeacherPerformance.htm"><img
					src="images/Admin/add_button.png" width="30" height="30"></a></td>
			<td><a href="appManagerHomePage.htm"><img src="images/Admin/cancel_button.png" width="40"
				height="30"></a></td>
		</tr>
	</table>
</body>
</html>
