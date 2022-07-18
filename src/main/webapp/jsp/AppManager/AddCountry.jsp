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
<form:form id="countryForm" modelAttribute="country"  action="saveCountry.htm"> <!--  method="post"-->
<table>  
<tr>  
    <td><form:label path="country">* Country:</form:label></td>
	<td><form:input path="country"/> <font color="red"> <form:errors path="country"></form:errors></font></td>
</tr>
<tr>  
    <td><form:label path="continent">* Continent:</form:label></td>
	<td><form:input path="continent"/> <font color="red"> <form:errors path="continent"></form:errors></font></td>
</tr>
<tr>
<td><form:hidden path="countryId"/></td>
</tr>
<tr>
<td>
<input type="image" src="images/Common/submitChangesUp.gif" alt="Save" style="border: none; vertical-align: -5px" />
</td>
</tr> 
<tr> 
<td>
<a href="displayCountries.htm"><img src="images/Admin/cancel_button.png" alt="Close" style="border: none"> </a>
</td>
</tr>  
</table>
</form:form>
</body>
</html>

  