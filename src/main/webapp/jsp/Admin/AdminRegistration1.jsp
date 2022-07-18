<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript"
	src="/dwr/interface/regService.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
</head>
<body>
	<table width="1000" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="33" colspan="10" align="left" valign="middle"><img
				src="images/Login/logoBlackUp.gif" width="222" height="21"
				name="logoblack" vspace="0" hspace="7" border="0"
				alt="The Learning Priority"></td>
		</tr>
		<tr>
			<td valign="top"><table name="content" vspace="0" width="100%"
					border="0" cellpadding="0" cellspacing="0" hspace="0">
					<tbody>
						<tr>
							<td colspan="4" valign="middle" class="text">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4" valign="middle" class="text"><img
								src="images/Admin/header-generalinfo.jpg" width="300"
								height="27" /></td>
						</tr>
						<tr>
							<td colspan="4" valign="middle" class="text">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4" valign="middle" class="text">Welcome! Your
								privacy is important to us. Personal informaiton is used only
								within our site and not shared with outside sources</td>
						</tr>
						<tr>
							<td colspan="4" valign="middle" class="text">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4" valign="middle" class="text"><img
								src="images/Admin/header-tell.jpg" width="300" height="27" /></td>
						</tr>
						<tr>
							<td class="text" valign="middle" width="170" align="right">&nbsp;</td>
							<td valign="middle" width="10" align="center">&nbsp;</td>
							<td colspan="2" valign="middle" width="434" align="left">&nbsp;</td>
						</tr>
						<form:form name="adminRegForm1" modelAttribute="adminReg1"
							action="adminRegForm1Validate.htm" method="POST">
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span><font style="color: black"> Title:</font>
								</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:select
										path="title">
										<form:option value="select" selected="selected">Select</form:option>
										<form:option value="Mr">Mr.</form:option>
										<form:option value="Mrs">Mrs.</form:option>
										<form:option value="Ms">Ms.</form:option>
										<form:option value="Dr">Dr.</form:option>
									</form:select> <font color="red"><form:errors path="title" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right">&nbsp;</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> First Name:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:input
										path="firstName" size="20" /><font color="red"><form:errors
											path="firstName" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"
									height="20">&nbsp;</td>
								<td valign="middle" width="10" align="center" height="20">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"
									height="20">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> Last Name:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:input
										path="lastName" size="20" /><font color="red"><form:errors
											path="lastName" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"
									height="20">&nbsp;</td>
								<td valign="middle" width="10" align="center" height="20">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"
									height="20">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> Home Address1:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:input
										path="address1" size="30" type="text" maxlength="30" /><font color="red"><form:errors
											path="address1" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right">&nbsp;</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right">
									Home Address2:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:input
										path="address2" size="30" type="text" maxlength="30" /></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"
									height="20">&nbsp;</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> Country:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><font
									size="2" face="Arial, Helvetica, sans-serif"> <form:select
											path="countryId" onChange="loadStates()">
											<option value="select">Select Country</option>
											<c:forEach var="cList" items="${countryIds}">
												<option value="${cList.countryId}"
													<c:if test="${countryId == cList.countryId}"> selected="Selected" </c:if>>${cList.country}</option>
											</c:forEach>
										</form:select>										
								</font><font color="red"><form:errors
											path="countryId" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"
									height="20">&nbsp;</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> State:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><font
									size="2" face="Arial, Helvetica, sans-serif"> <form:select
											path="stateId">
											<option value="select">Select State</option>
											<c:forEach var="sList" items="${stateIds}">
												<option value="${sList.stateId}"
													<c:if test="${stateId == sList.stateId}"> selected="Selected" </c:if>>${sList.name}</option>
											</c:forEach>
										</form:select>
								</font><font color="red"><form:errors
											path="stateId" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"
									height="20">&nbsp;</td>
								<td valign="middle" width="10" align="center" height="20">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"
									height="20">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> City:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:input
										path="city" /><font color="red"><form:errors
											path="city" /></font></td>
							</tr>

							<tr>
								<td class="text" valign="middle" width="170" align="right"
									height="20">&nbsp;</td>
								<td valign="middle" width="10" align="center" height="20">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"
									height="20">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> Zip Code:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:input
										path="zipcode" size="10" maxlength="5" /><font color="red"><form:errors
											path="zipcode" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"
									height="20">&nbsp;</td>
								<td valign="middle" width="10" align="center" height="20">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"
									height="20">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right">&nbsp;</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td class="text1" valign="middle" width="434" align="left">Your
									email address will be your username. To protect your privacy,
									please create a password using any combination of letters
									and/or numbers.<br /> <br />
								</td>
								<td class="instructions" valign="middle" width="99" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> Your Email Address:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:input
										path="emailId" size="30" readonly="true"/>
										<font color="red"><form:errors
											path="emailId" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"
									height="20">&nbsp;</td>
								<td valign="middle" width="10" align="center" height="20">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"
									height="20">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> Create Password:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:password
										path="password" size="20" /><font color="red"><form:errors
											path="password" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right">&nbsp;</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> Re-Enter Password:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:password
										path="confirmPassword" size="20" /><font color="red"><form:errors
											path="confirmPassword" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"></td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> Security Question:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:select
										path="securityQuestionId">
										<option value="select">select</option>
										<c:forEach items="${securityQuestions}" var="secQues">
											<form:option value="${secQues.securityQuestionId}" > ${secQues.question}</form:option> 
										</c:forEach>
									</form:select><font color="red"><form:errors
											path="securityQuestionId" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"></td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"
									class="text1">If you forget your password we will ask for
									the answer<br /> to your security question. <br />
									<h1></h1>
								</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="170" align="right"><span
									class="star">*</span> Answer:</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td colspan="2" valign="middle" width="434" align="left"><form:input
										path="answer" size="36" /><font color="red"><form:errors
											path="answer" /></font></td>
							</tr>

							<tr>
								<td class="formlabel" valign="middle" width="170" align="right">&nbsp;</td>
								<td valign="middle" width="10" align="center">&nbsp;</td>
								<td width="434" colspan="2" align="right" valign="middle"><table
										width="100%" height="115" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="80%" align="right" valign="middle"><img
												src="images/Login/3dots.gif" width="42" height="15" /></td>
											<td width="20%" align="right" valign="middle"><input
												type="image" src="images/Login/next.jpg" alt="Save"
												width="100" height="40"
												style="border: none; vertical-align: -5px"
												/><form:hidden path="regId" /></td>
										</tr>

									</table></td>
							</tr>
						</form:form>
					</tbody>
				</table></td>
			<td><img src="images/Login/spacer.gif" width="1" height="513"
				border="0" alt="" /></td>
		</tr>
		<tr>
			<td colspan="9"></td>
			<td><img src="images/Login/spacer.gif" width="1" height="10"
				border="0" alt="" /></td>
		</tr>
		<tr>
			<td colspan="9"></td>
			<td><img src="images/Login/spacer.gif" width="1" height="70"
				border="0" alt="" /></td>
		</tr>
	</table>

</body>
</html>