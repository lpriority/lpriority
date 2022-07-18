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
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
 <script type="text/javascript" src="resources/javascript/AppAdminJs.js"></script>
</head><body>
<form:form id="districtAdmin" modelAttribute="userRegistration"> 
	<table>
			<tr>
				<td><form:label path="districtId" >Districts</form:label></td>
				<td><form:select path="districtId">
						<option value="select">Select district</option>
						<c:forEach var="sList" items="${districtList}">
							<option value="${sList.districtId}"
								<c:if test="${districtId eq sList.districtId}"> selected="Selected" </c:if>>${sList.districtName}</option>
						</c:forEach>
					</form:select> <font color="red"><form:errors path="schoolId"></form:errors></font></td>
			</tr>
			<tr>
				<td><form:label path="emailId">* Email Id:</form:label></td>
				<td> <form:input path="emailId" id="emailId"/></td>
			</tr>
				
			<tr>
				<td></td>
				<td><form:hidden path="regId" /></td>
				
			</tr>
			<tr>
				<td><img src="images/Common/submitChangesUp.gif"
					alt="Save" style="border: none; vertical-align: -5px" onclick="SaveDistrictAdmin()" /></td>
				<td><a href="displayDistrictUsers.htm"><img
						src="images/Admin/cancel_button.png" alt="Close"
						style="border: none"></a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

