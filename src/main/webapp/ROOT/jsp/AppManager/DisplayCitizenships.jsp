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
			<th>Citizenship ID</th>
			<th>Citizenship</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${citizenshipList}" var="cList">
			<tr>
				<td>${cList.citizenshipId}</td>
				<td>${cList.citizenshipName}</td>
				<td><a href="editCitizenship.htm?id=${cList.citizenshipId}"><img
						src="images/Admin/edit_button.png" width="30" height="30"></a></td>
				<td>&nbsp;</td>
				<td><img src="images/Admin/delete_button.png" width="40" height="30" onclick="deleteCitizenship()"></td>
			</tr>
		</c:forEach>
		<tr>
			<td><a href="addCitizenship.htm"><img
					src="images/Admin/add_button.png" width="30" height="30"></a></td>
			<td><a href="appManagerHomePage.htm"><img src="images/Admin/cancel_button.png" width="40"
				height="30"></a></td>
		</tr>
	</table>
</body>
</html>
