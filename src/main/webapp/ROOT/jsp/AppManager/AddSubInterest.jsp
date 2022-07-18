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
	<form:form id="subInterestForm" modelAttribute="subInterest"
		action="saveSubInterest.htm">
		<!--  method="post"-->
		<table>
			<tr>
				<td><form:label path="subInterest">* Sub Interest: </form:label></td>
				<td><form:input path="subInterest" /><font color="red"> <form:errors path="subInterest"></form:errors></font></td>
			</tr>
			<tr>
			<td><form:label path="interestId">* Interest Name: </form:label></td>
				<td><form:select path="interestId">
						<option value="select">Select Interest</option>
						<c:forEach var="iList" items="${interestList}">
							<option value="${iList.interestId}" <c:if test="${interestId == iList.interestId}"> selected="Selected" </c:if>>${iList.interest}</option>
						</c:forEach>
					</form:select><font color="red"> <form:errors path="interestId"></form:errors></font></td>
			</tr>
			<tr>
				<td><form:hidden path="subInterestId" /></td>
			</tr>
			<tr>
				<td><input type="image" src="images/Common/submitChangesUp.gif"
					alt="Save" style="border: none; vertical-align: -5px" /></td>
			</tr>
			<tr>
				<td><a href="displaySubInterests.htm"><img
						src="images/Admin/cancel_button.png" alt="Close"
						style="border: none"> </a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

