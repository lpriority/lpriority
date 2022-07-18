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
<form:form id="districtForm" modelAttribute="district"> 
	<table>
			<tr>
				<td><form:label path="districtName">* District Name:</form:label></td>
				<td><form:input path="districtName" id="districtName"/> <br /></td>
			</tr>
			<tr>
				<td><form:label path="noSchools">* No Of Schools:</form:label></td>
				<td><form:input path="noSchools" id="noSchools"/> <font color="red">
						
				</font><br /></td>
			</tr>

			<tr>
				<td><form:label path="countryId">* Country:</form:label></td>
				<td><form:select id="countryId" path="countryId"
						onchange="loadStates(); return false;">
						<option value="select">Select Country</option>
						<c:forEach var="cList" items="${countryIds}">
							<option value="${cList.countryId}"
								<c:if test="${countryId == cList.countryId}"> selected="Selected" </c:if>>${cList.country}</option>
						</c:forEach>
					</form:select><font color="red"> <form:errors path="countryId"></form:errors>
				</font></td>
			</tr>
			<tr>
				<td><form:label path="stateId">* State:</form:label></td>
				<td><form:select id="stateId" path="stateId">
						<option value="select">Select State</option>
						<c:forEach var="sList" items="${stateIds}">
							<option value="${sList.stateId}"
								<c:if test="${stateId == sList.stateId}"> selected="Selected" </c:if>>${sList.name}</option>
						</c:forEach>
					</form:select></td>
			</tr>
			<tr>
				<td><form:label path="city">*  City:</form:label></td>
				<td><form:input path="city" id="city" /><br /></td>
			</tr>
			<tr>
				<td><form:label path="address">*  Address:</form:label></td>
				<td>
				<form:input path="address" maxlength="50" id="address" />
				</td>
			</tr>
			<tr>
				<td><form:label path="phoneNumber">* Phone Number:</form:label></td>
				<td><form:input path="phoneNumber" id="phoneNumber"/>
						
				<br /></td>
			</tr>
			<tr>
				<td><form:label path="faxNumber">* Fax Number:</form:label></td>
				<td><form:input path="faxNumber" id="faxNumber" />
					
				<br /></td>
			</tr>
			<tr>
				<td><form:hidden path="districtId" /></td>
			</tr>
			<tr>
				<td><img src="images/Common/submitChangesUp.gif"
					alt="Save" style="border: none; vertical-align: -5px" onclick="SaveDistrict()" /></td>
				<td><a href="displayDistricts.htm"><img
						src="images/Admin/cancel_button.png" alt="Close"
						style="border: none"></a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

