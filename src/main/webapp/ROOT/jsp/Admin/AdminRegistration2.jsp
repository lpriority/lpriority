<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
</head>
<body>
	<table width="1000" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="10" colspan="9" align="left" valign="middle"><img
				src="images/Login/logoBlackUp.gif" width="222" height="21"
				name="logoblack" vspace="0" hspace="7" border="0"
				alt="The Learning Priority" /></td>
		</tr>

		<tr>
			<td colspan="9"></td>
			<td></td>
		</tr>
		<tr>
			<td rowspan="3" valign="top"></td>
			<td rowspan="3">&nbsp;</td>
			<td colspan="7"></td>
			<td></td>
		</tr>
		<tr>
			<td rowspan="2" valign="top"><table width="100%" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<td>&nbsp;</td>
					</tr>

					<tr>
						<td><table width="100%" border="0" cellspacing="0"
								cellpadding="0">

								<tr>
									<td><table width="100%" height="178" border="0"
											cellpadding="0" cellspacing="0">


										</table></td>
								</tr>

							</table></td>
					</tr>

				</table></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2" valign="top"></td>
			<td rowspan="2">&nbsp;</td>
			<td valign="top"></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="3" valign="top"></td>
			<td></td>
		</tr>
		<tr>
			<td valign="top"><table name="content" vspace="0" width="100%"
					border="0" cellpadding="0" cellspacing="0" hspace="0">
					<tbody>
						<tr>
							<td colspan="5" align="right" valign="middle" class="text">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="5" align="right" valign="middle" class="text"><div
									align="left">
									<img src="images/Admin/header-generalinfo.jpg" width="300"
										height="27" />
								</div></td>
						</tr>
						<tr>
							<td colspan="5" align="right" valign="middle" class="text">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="5" align="left" valign="middle" class="text">Learning
								priority provide customized materials based upon your individual
								infomration and interests.</td>
						</tr>
						<tr>
							<td colspan="5" align="left" valign="middle" class="text">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="5" align="left" valign="middle" class="text"><img
								src="images/Admin/header-tell.jpg" width="300" height="27" /></td>
						</tr>
						<tr>
							<td colspan="5" align="right" valign="middle" class="text">&nbsp;</td>
						</tr>
						<tr>
							<td class="text" valign="middle" width="213" align="right">&nbsp;</td>
							<td valign="middle" width="9" align="center">&nbsp;</td>
							<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
						</tr>
						<form:form name="adminRegForm2" modelAttribute="adminReg2"
							method="POST" action="adminRegForm2Validate.htm">
							<tr>
								<td class="text" valign="middle" width="213" align="right"><span
									class="star">*</span> School Name:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left"><form:input
										path="schoolName" size="30" /><font color="red"><form:errors
											path="schoolName" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="213" align="right"><span
									class="star">*</span> Country:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left"><font
									size="2" face="Arial, Helvetica, sans-serif"> <form:select
											path="countryId" onChange="loadStates()">
											<option value="select">Select Country</option>
											<c:forEach var="cList" items="${countryIds}">
												<option value="${cList.countryId}"
													<c:if test="${countryId == cList.countryId}"> selected="Selected" </c:if>>${cList.country}</option>
											</c:forEach>
										</form:select>
								</font><font color="red"><form:errors path="countryId" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="213" align="right"><span
									class="star">*</span> State:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left"><font
									size="2" face="Arial, Helvetica, sans-serif"> <form:select
											path="stateId">
											<option value="select">Select State</option>
											<c:forEach var="sList" items="${stateIds}">
												<option value="${sList.stateId}"
													<c:if test="${stateId == sList.stateId}"> selected="Selected" </c:if>>${sList.name}</option>
											</c:forEach>
										</form:select>
								</font><font color="red"><form:errors path="stateId" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="213" align="right"><span
									class="star">*</span> City:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left"><form:input
										path="city" size="30" /><font color="red"><form:errors
											path="city" /></font></td>
							</tr>

							<tr>
								<td class="text" valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right" class="text">*
									Type Of School:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left"><form:select
										path="schoolTypeId">
										<option value="select">Select School Type</option>
										<c:forEach var="stList" items="${schoolTypeIds}">
											<option value="${stList.schoolTypeId}"
												<c:if test="${schoolTypeId == stList.schoolTypeId}"> selected="Selected" </c:if>>${stList.schoolTypeName}</option>
										</c:forEach>
									</form:select><font color="red"><form:errors path="schoolTypeId" /></font></td>
							</tr>
							<tr>
								<td valign="top" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right" class="text">*
									Level Of School:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left"><form:select
										id="schoolLevelId" path="schoolLevelId">
										<option value="select">Select School Level</option>
										<c:forEach var="slList" items="${schoolLevelIds}">
											<option value="${slList.schoolLevelId}"
												<c:if test="${schoolLevelId == slList.schoolLevelId}"> selected="Selected" </c:if>>${slList.schoolLevelName}</option>
										</c:forEach>
									</form:select><font color="red"><form:errors path="schoolLevelId" /></font></td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right" class="text">*
									Number of Students:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left"><form:input
										path="noOfStudents" /><font color="red"><form:errors
											path="noOfStudents" /></font></td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right" class="text"><span
									class="star">*</span> Work Phone Number:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td width="401" align="left" valign="middle"><form:input
										path="phoneNumber" size="20" /><font color="red"><form:errors
											path="phoneNumber" /></font></td>
								<td width="190" align="left" valign="middle"
									class="instructions"></td>
								<td width="109" align="left" valign="middle">&nbsp;</td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right" class="text">*
									Work Fax Number:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td valign="middle" align="left"><form:input
										path="faxNumber" size="20" /><font color="red"><form:errors
											path="faxNumber" /></font></td>
								<td class="instructions" valign="middle" align="left"></td>
								<td valign="middle" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right"><span
									class="text">* Registration Date:</span></td>

								<td valign="middle" width="9" align="center">&nbsp;</td>

								<td colspan="3" valign="middle" width="401" align="left"><form:input
										path="registrationDate" size="20" id="registrationDate"
										readonly="true" /><font color="red"><form:errors
											path="registrationDate" /></font></td>
								<td class="instructions" valign="middle" align="left"></td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;
									<form:hidden path="schoolId" /> <form:hidden path="schoolAbbr" />
								</td>
							</tr>
							<tr>
								<td valign="top" width="213" align="right" class="text">Sports
									Offered:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left"><table
										name="sport" vspace="0" width="401" border="0" cellpadding="0"
										cellspacing="0" hspace="0">
										<tbody>
											<c:set var="i" value="0"></c:set>
											<c:forEach items="${sportsOffered}" var="spList">
												<c:if test="${i==0 }">
													<tr>
												</c:if>
												<c:set var="i" value="${i+1 }"></c:set>
												<td width="25"><input name="sport" type="checkbox"
													value="${spList.subInterestId}" /></td>
												<td class="instructions" width="108">${spList.subInterest}</td>
												<c:if test="${i==3 }">
													</tr>
													<c:set var="i" value="0"></c:set>
												</c:if>
											</c:forEach>
										</tbody>
									</table></td>
							</tr>
							<tr>
								<td valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<c:forEach begin="1" end="3" var="count">
								<tr>
									<td valign="middle" width="213" align="right" class="text">Other:</td>
									<td valign="middle" width="9" align="center">&nbsp;</td>
									<td colspan="3" valign="middle" width="401" align="left"><input
										name="adminSportsOther${count}" size="20" type="text" /></td>
								</tr>
								<tr>
									<td valign="middle" width="213" align="right">&nbsp;</td>
									<td valign="middle" width="9" align="center">&nbsp;</td>
									<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
								</tr>
							</c:forEach>
							<tr>
								<td valign="top" width="213" align="right" class="text">Extra-Curricular
									Activities Offered:</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left"><table
										name="x_tra" vspace="0" width="401" border="0" cellpadding="0"
										cellspacing="0" hspace="0">
										<tbody>
											<c:set var="i" value="0"></c:set>
											<c:forEach items="${extraCurricularList}" var="eClist">
												<c:if test="${i==0 }">
													<tr>
												</c:if>
												<c:set var="i" value="${i+1 }"></c:set>
												<td width="25"><input name="xtra" type="checkbox"
													value="${eClist.subInterestId}" /></td>
												<td class="instructions" width="108">${eClist.subInterest}</td>
												<c:if test="${i==3 }">
													</tr>
													<c:set var="i" value="0"></c:set>
												</c:if>
											</c:forEach>
										</tbody>
									</table></td>
							</tr>

							<c:forEach begin="1" end="3" var="count">

								<tr>
									<td valign="top" width="213" align="right">&nbsp;</td>
									<td valign="middle" width="9" align="center">&nbsp;</td>
									<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
								</tr>
								<tr>
									<td valign="middle" width="213" align="right" class="text">Other:</td>
									<td valign="middle" width="9" align="center">&nbsp;</td>
									<td colspan="3" valign="middle" width="401" align="left"><input
										name="adminExtraCurricular${count}" size="20" type="text" /></td>
								</tr>
							</c:forEach>
							<tr>
								<td class="formlabel" valign="middle" width="213" align="right"><table
										width="100%" height="100%" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="40%" align="center" valign="middle"><a
												href="javascript:window.history.back(1)"
												onmouseout="MM_swapImgRestore()"
												onmouseover="MM_swapImage('Image21','','images/Login/back.jpg',1)"><img
													src="images/Login/back.jpg" name="Image21" width="100"
													height="40" border="0" id="Image21" /></a></td>
											<td width="60%" align="left" valign="middle"
												style="padding-left: 8px"><img
												src="images/Login/3dots.gif" width="42" height="15" /></td>
										</tr>
									</table></td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="right"><table
										width="100%" height="115" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="80%" align="right" valign="middle"><img
												src="images/Login/3dots.gif" width="42" height="15" /></td>
											<td width="20%" align="right" valign="middle"><input
												type="image" src="images/Login/next.jpg" alt="Save"
												width="100" height="40"
												style="border: none; vertical-align: -5px" /></td>
										</tr>

									</table></td>
							</tr>
						</form:form>
					</tbody>
				</table></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="9"></td>
		</tr>
		<tr>
			<td colspan="9"></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="9">
				<div id="footer">
					<table width="98%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="62%">&nbsp;</td>
							<td width="38%">&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</div>
			</td>
			<td></td>
		</tr>
	</table>
</body>
</html>