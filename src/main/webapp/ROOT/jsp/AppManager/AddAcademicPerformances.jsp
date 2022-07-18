<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<form:form id="AcademicGradeForm" modelAttribute="academicPerformance" method="post" action="saveAcademicPerformance.htm">
<table>  
<tr>  
    <td><form:label path="academicLevel">* Academic Level:</form:label></td>
	<td><form:input path="academicLevel"/> <font color="red"> <form:errors path="academicLevel"></form:errors></font></td>
</tr>
<tr>
<td><form:label path="academicGrade">* Academic Grade:</form:label></td>
<td><form:input path="academicGrade"/> <font color="red"> <form:errors path="academicGrade"></form:errors></font></td>
</tr>
<tr>
<td><form:label path="academicDescription">* Description:</form:label></td>
<td><form:input path="academicDescription"/> <font color="red"> <form:errors path="academicDescription"></form:errors></font></td>
</tr>
<tr>
<td><form:hidden path="academicId"/></td>
</tr>
<tr>
<td></td>
<td>  
<input type="image" src="images/Common/submitChangesUp.gif" alt="Save" style="border: none; vertical-align: -5px" />
</td>
<td>
<a href="displayAcademicPerformances.htm"><img src="images/Admin/cancel_button.png" alt="Close" style="border: none"> </a>
</td>
</tr>  
</table>
</form:form>
</body>
</html>

  