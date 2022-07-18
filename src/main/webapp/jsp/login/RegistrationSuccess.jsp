<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Learning Priority|Registration Success</title>
<link rel="stylesheet" href="resources/css/common_registration.css" >
</head>
<body>
		<div align="center">
			<table width="550" height="484" border="0" cellpadding="0" style="max-width:550px;"
				cellspacing="0" >
				<%
					if (session.getAttribute("userReg") == null) {
				%>
				<tr>
					<td height="45" colspan="6" style='padding-top: 1.5em;'> <img
						src="images/Login/logoBlackUp.gif" width="222" height="21"
						name="logoblack" vspace="0" hspace="7" border="0"
						alt="The Learning Priority" /></td>
				</tr>
				<%
					}
				%>
					  <tr>
								<td colspan="2" height="50" align="center" valign="top"
									class="form"><table width="100%" height="441"
										border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td height="302" align="center" valign="middle"><img
												src="images/Login/smile1 copy.png" width="320" height="302" /></td>
										</tr>
										<tr>
											<td height="60" align="center" valign="middle"><span
												class="style1">You have Successfully Registered </span></td>
										</tr>
										<tr>
											<td align="center" valign="top"><table
													width="100%" height="79" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td colspan="2" align="center" valign="top" class="text">Want
															to Invite other</td>
													</tr>
													<tr>
														<td width="50%" align="right" valign="middle"
															style="padding-right: 5px"><a href="invitation.htm"><img
																src="images/Login/invite.gif" width="50" height="18"
																border="0" /></a></td>
														<td width="50%" align="left" valign="middle"
															style="padding-left: 5px"><a
															href="skip.htm?emailId=${userReg.emailId}"><img
																src="images/Login/skip.gif" width="50" height="18"
																border="0" /></a></td>
													</tr>
												</table></td>
										</tr>
									</table></td>
							</tr>
						</table>
		</div>
</body>
</html>
