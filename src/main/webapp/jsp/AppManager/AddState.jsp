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
	<form:form id="statesForm" modelAttribute="states"
		action="saveState.htm">
		<!--  method="post"-->
		<table>
			<tr>
				<td><form:label path="name">* State Name: </form:label></td>
				<td><form:input path="name" /><font color="red"> <form:errors
							path="name"></form:errors></font></td>
			</tr>
			<tr>
				<td><form:hidden path="stateId" /></td>
			</tr>
			<tr>
				<td>* Country Name:</td>
				<td><form:select path="countryId">
						<option value="select">Select Country</option>
						<c:forEach var="cList" items="${countryIds}">
							<option value="${cList.countryId}"
								<c:if test="${countryId == cList.countryId}"> selected="Selected" </c:if>>${cList.country}</option>
						</c:forEach>
					</form:select> <font color="red"><form:errors path="countryId"></form:errors></font></td>
			</tr>
			<tr>
				<td><input type="image" src="images/Common/submitChangesUp.gif"
					alt="Save" style="border: none; vertical-align: -5px" /></td>
			</tr>
			<tr>
				<td><a href="displayStates.htm"><img
						src="images/Admin/cancel_button.png" alt="Close"
						style="border: none"> </a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

