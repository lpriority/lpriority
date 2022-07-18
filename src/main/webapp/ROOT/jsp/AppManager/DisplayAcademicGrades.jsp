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
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/appAdmin3Service.js"></script>
<script type="text/javascript" src="resources/javascript/app_manager/delete_functions.js"></script>
</head>
<body>
	<table>
		<tr>
			<th>PerformanceGrade</th>
			<th>GradeName</th>
			<th>ScoreFrom</th>
			<th>ScoreTo</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${agList}" var="ag">
			<tr>
				<td>${ag.academicPerformance.academicLevel}</td>
				<td>${ag.acedamicGradeName}</td>
				<td>${ag.scoreFrom}</td>
				<td>${ag.scoreTo}</td>
				<td><a href="editAcademicGrade.htm?id=${ag.acedamicGradeId}"><img
						src="images/Admin/edit_button.png" width="30" height="30"></a></td>
				<td>&nbsp;</td>
				<td><img src="images/Admin/delete_button.png" width="40"
					onclick="deleteGrade(${ag.acedamicGradeId})" height="30"></td>
			</tr>
		</c:forEach>
		<tr>
			<td><a href="addAcademicGrade.htm"><img
					src="images/Admin/add_button.png" width="30" height="30"></a></td>
			<td><a href="appManagerHomePage.htm"><img
					src="images/Admin/cancel_button.png" width="40" height="30"></a></td>
		</tr>
	</table>
</body>
</html>
