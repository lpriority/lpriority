<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<title>Insert title here</title>
</head>
<body>
	<table width="1000" border="0" align="center" cellpadding="0"
		cellspacing="0">

		<tr>
			<td height="33" colspan="9" align="left" valign="middle"><img
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
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>

				</table></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="2" valign="top"></td>
			<td rowspan="2">&nbsp;</td>
			<td valign="top"></td>
			<td rowspan="2">&nbsp;</td>
			<td rowspan="3" valign="top"><table width="100%" border="0"
					cellspacing="0" cellpadding="0">

					<tr>
						<td>&nbsp;</td>
					</tr>
				</table></td>
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
						<form:form modelAttribute="adminReg3" name="adminRegForm3"
							action="adminRegForm3Validate.htm" method="POST">
							<tr>
								<td valign="middle" align="right"><span class="text">*
										Qualification :</span></td>
								<td valign="middle" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" align="left"><form:input
										path="qualification" size="20" /><font color="red"><form:errors
											path="qualification" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td valign="middle" align="right"><span class="text">*
										Phone no :</span></td>
								<td valign="middle" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" align="left"><form:input
										path="phonenumber" size="20" /><font color="red"><form:errors
											path="phonenumber" /></font></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<tr>
								<td valign="top" align="right"><span class="text">Areas
										Of Interest :</span></td>
								<td valign="middle" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" align="left"><table
										name="personal_interest" vspace="0" width="439" border="0"
										cellpadding="0" cellspacing="0" hspace="0">
										<tbody>
											<%
												int i = 0;
											%>
											<c:forEach items="${interestAreasList}" var="iaList">
												<%
													if (i == 0) {
												%>
												<tr>
													<%
														}
													%>
													<%
														i++;
													%>
													<td width="25"><input name="sport" type="checkbox"
														value="${iaList.subInterestId}" /></td>
													<td class="instructions" width="108">${iaList.subInterest}</td>
													<%
														if (i == 3) {
													%>
												</tr>
												<%
													i = 0;
															}
												%>
											</c:forEach>
										</tbody>
									</table></td>
							</tr>
							<tr>
								<td class="text" valign="middle" width="213" align="right">&nbsp;</td>
								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="left">&nbsp;</td>
							</tr>
							<%
								i = 0;
							%>
							<c:forEach begin="1" end="5">
								<%
									i++;
								%>
								<tr>
									<td valign="middle" width="213" align="right" class="text">Other:</td>
									<td valign="middle" width="9" align="center">&nbsp;</td>
									<td colspan="3" valign="middle" width="401" align="left"><input
										name="adminOtherAreasOfInterest<%=i%>" size="20" type="text" /></td>
								</tr>
								<tr>
									<td valign="middle" width="213" align="right">&nbsp;</td>
									<td valign="middle" width="9" align="center">&nbsp;</td>
									<td colspan="3" valign="middle" width="401" align="left">&nbsp;<form:hidden path="regId" /></td>
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

										</tr>
									</table></td>

								<td valign="middle" width="9" align="center">&nbsp;</td>
								<td colspan="3" valign="middle" width="401" align="right"><table
										width="100%" height="115" border="0" cellpadding="0"
										cellspacing="0">
										<tr>

											<td width="60%" align="right" valign="middle"
												style="padding-left: 8px"><input type="image"
												src="images/Login/3dots.gif" width="42" height="15" /></td>
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