
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script src="resources/javascript/my_recorder/recorder.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Take Quiz</title>
</head>
<body>
<input type="hidden" name="userType" id="userType" value="${userReg.user.userType}" />
<input type="hidden" name="regId" id="regId" value="${userReg.regId}" />
<input type="hidden" name="usersFilePath" id="usersFilePath" value="${usersFilePath}" />
<input type="hidden" name="operation" id="operation" value="${mode}" />
<input type="hidden" name="titleId" id="titleId" value="${titleId}" />
<input type="hidden" name="rating" id="rating" value="${rating}" />

<table width="100%" id="tableDiv" style="padding-top:2em;padding-left: 2em;">
<tr align="center"  style="position:relative;">
	<td align="left" class="tabtxt">
	&nbsp;&nbsp;&nbsp;  Student:&nbsp;&nbsp;&nbsp; 
	<select	id="studentId" name="studentId" class="listmenu" onchange="getStudentAllQuizQuestionByTitleId()" style="width:120px;">
		<option value="select">select</option>
 		<c:forEach items="${studentLt}" var="student">
			<option value="${student.studentId}">${student.userRegistration.firstName} ${student.userRegistration.lastName}</option>
		</c:forEach> 
	</select>
	</td> 	
</tr>
<tr align="center"  style="position:relative;"><td>
<div id="showQuizDiv"></div>
</td></tr>
</table>
</body>
</html>