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
			<th>ID</th>
			<th>Sub Interest</th>
			<th>Interest Name:</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${siList}" var="si">
			<tr>
				<td>${si.subInterestId}</td>
				<td>${si.subInterest}</td>
				<td>${si.interest.interest}</td>
				<td><a href="editSubInterest.htm?id=${si.subInterestId}"><img
						src="images/Admin/edit_button.png" width="30" height="30"></a></td>
				<td><img src="images/Admin/delete_button.png" width="40"
					height="30" onclick="deleteSubInterest()"></td>
			</tr>
		</c:forEach>
		<tr>
			<td ><a href="addSubInterest.htm"><img
					src="images/Admin/add_button.png" width="30" height="30"></a></td>
			<td><a href="appManagerHomePage.htm"><img
					src="images/Admin/cancel_button.png" width="40" height="30"></a></td>
					<td></td>
					<td></td>
					<td></td>
		</tr>
	</table>
</body>
</html>
