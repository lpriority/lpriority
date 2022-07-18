<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Signup</title>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/regService.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student.js"></script>
<link rel="stylesheet" href="resources/css/normalize.css">
<link rel="stylesheet" href="resources/css/registration-css.css">
<link rel="stylesheet" href="resources/css/common_registration.css" >
<style type="text/css">
input, textarea{
  display: inline;
  width: auto;
}
select{
  display: inline;
  width: auto;
  background:rgb(250, 251, 251);
  color:rgb(76, 75, 75);
}
</style>
</head>
<body>
	<div align="center">
		<table width="765" height="784" border="0" cellpadding="0"
			cellspacing="0">
			<%
				if (session.getAttribute("userReg") == null) {
			%>
			<tr>
				<td height="33" colspan="6" align="left" valign="middle"><img
					src="images/Login/logoBlackUp.gif" width="222" height="21"
					name="logoblack" vspace="0" sessionpace="7" border="0"
					alt="The Learning Priority" /></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td width="20" height="739" rowspan="8"></td>
				<td width="745" colspan="5"></td>
			</tr>
			<tr>
				<td width="450" height="41" align="left" valign="middle"></td>
			</tr>
			<tr>
				<td height="521" colspan="5" align="center" valign="top" style="max-width:550px;"><table
							width="550" height="521" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="484" align="left" valign="top" class="form"><form
									action="SaveStudentDetails3.htm" name="aboveStudentReg4" method="POST" onsubmit="above13StudentRegVal3()">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tbody>
											<tr>
												<td class="text" valign="middle" width="173" align="right"></td>
												<td valign="middle" width="8" align="center">&nbsp;</td>
												<td colspan="3" valign="middle" width="441" align="left"></td>
											</tr>
											<tr>
												<td class="text" valign="middle" width="173" align="center"
													colspan="3" height="19"  style="text-shadow: 0 1px 4px rgb(0, 0, 0);padding-top:3em;color:white;"><h1>Grade Level Info</h1></td>
											</tr>


											<tr>
												<td class="subject" valign="center" width="173"
													align="right">&nbsp;</td>
												<td valign="center" width="8" align="middle">&nbsp;</td>
												<td valign="center" width="441" colspan="3" align="left">&nbsp;</td>
											</tr>


											<tr>
												<td class="subject" valign="center" width="173"
													align="right">&nbsp;</td>
												<td valign="center" width="8" align="middle">&nbsp;</td>
												<td valign="center" width="441" colspan="3" align="left">&nbsp;</td>
											</tr>
											<tr>
												<td align="center" colspan="5">
													<table align="center" width="100%" style="padding-left: 4em;">
														<%
															String[] classnames = null;
															String[] classIds = null;
															classnames = (String[]) session.getAttribute("classNames");
															classIds = (String[]) session.getAttribute("classIds");
															for (int cnt = 0; cnt < classnames.length; cnt++) {
														%>
														<tr>
															<td><input type="hidden"
																name="classIds" id="classIds<%=cnt%>"
																value="<%=classIds[cnt]%>" /> <%=classnames[cnt]%></td>
															<td><select name="gradeLevels"
																id="gradeLevels<%=cnt%>" required="required">
																	<option value="">Select Grade Level</option>
																	<option value="1">Above Grade Level</option>
																	<option value="2">At Grade Level</option>
																	<option value="3">Below Grade Level</option>
															</select></td>

														</tr>
														<%
															}
														%>
													</table>


												</td>
											</tr>

											<tr>
												<td valign="middle" width="173" align="right"><table
														width="100%" height="100%" border="0" cellpadding="0"
														cellspacing="0">
													</table></td>
												<td valign="middle" width="8" align="center">&nbsp;</td>
												<td colspan="3" valign="middle" width="441" align="right"><table
														width="100%" height="115" border="0" cellpadding="0"
														cellspacing="0">
														<tr>
															<td width="95%" align="right" valign="middle">
																<button class="for-back-ward-button-style button-block-inline" >Done</button></td>																	
															<td width="5%" align="right" valign="middle"></td>																	
														</tr>
													</table></td> 
											</tr>
										</tbody>
									</table>
								</form></td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td height="47" colspan="5"></td>
			</tr>
			<tr>
				<td colspan="5"></td>
			</tr>
		</table>
	</div>
</body>
</html>