<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Teacher Signup</title>
<link rel="stylesheet" href="resources/css/normalize.css">
<link rel="stylesheet" href="resources/css/registration-css.css">
<link rel="stylesheet" href="resources/css/common_registration.css" >
<script>
	function submitchanges2() {
		var gradeLevelsArray = new Array();
		var noOfSectionsPerDayArray = new Array();
		var noOfSectionsPerWeekArray = new Array();
		var gradeLevels = document.getElementsByName('gradeLevels');
		var noOfSectionsPerDay = document
				.getElementsByName('noOfSectionsPerDay');
		var noOfSectionsPerWeek = document
				.getElementsByName('noOfSectionsPerWeek');

		for (var i = 0; i < gradeLevels.length; i++) {

			if (gradeLevels[i].value == 'select') {
				alert('Select a grade level ');
				$("#gradeLevels" + i).focus();
				return false;
			} else if (noOfSectionsPerDay[i].value == '') {
				alert('Please enter a value ');
				$("#noOfSectionsPerDay" + i).focus();
				return false;
			} else if (noOfSectionsPerWeek[i].value == '') {
				alert('Please enter a value ');
				$("#noOfSectionsPerWeek" + i).focus();
				return false;
			} else if (noOfSectionsPerWeek[i].value <= noOfSectionsPerDay[i].value) {
				alert('Sections per week should grater than section per day ');
				$("#noOfSectionsPerWeek" + i).focus();
				return false;
			}
		}
		return true;
	}
</script>
</head>
<body>
	<table style="width:100%"  border="0" cellpadding="0" cellspacing="0">
		<c:if test="${userReg.regId <= 0 }">
			<tr>
				<td height="33" colspan="6" align="left" valign="middle"><img
					src="images/Login/logoBlackUp.gif" width="222" height="21"
					name="logoblack" vspace="0" hspace="7" border="0"
					alt="The Learning Priority" /></td>
			</tr>
		</c:if>
		<tr>
			<td  style="width: 80%" colspan="5" align="center" valign="top">
				<table width="100%"  border="0" cellpadding="0" cellspacing="0">
					
					<tr>
						<td width="100%" align="center" valign="top"><form action="teacherRegVal3.htm" method="POST" onsubmit="return submitchanges2()" >
								<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form" style="max-width:650px;">
									<tbody>
									<tr><td colspan="5" align="center"  width="100%" ><h1>Grade Level Information</h1></td></tr>
										<tr>
											<td colspan="5" align="center">
												<table>
													<tr>
														<td align="center" colspan="2">
														</td>
														<td><input type="hidden" name="noOfGrades"
															id="noOfGrades" value=""></input></td>
													</tr>
												</table>
											</td>

										</tr>
										<%
											String[] gradeList = null;
											String[] classList = null;
											String[] classLengths = null;
											if (session.getAttribute("gradeIds") != null) {
												gradeList = (String[]) session.getAttribute("gradeIds");
												classList = (String[]) session.getAttribute("classIds");
												classLengths = (String[]) session.getAttribute("classLengths");

											}
										%>
										<tr>
											<td align="center" colspan="5">
												<table align="center" width="100%">
													<tr>
														<td >Grade</td>
														<td > Class</td>
														<td > Grade Level</td>
														<td > # Sections<br />
																Per Day
														</td>
														<td > # Sections<br />
																Per Week
														</td>

													</tr>
													<%
														int classLength = 0;
														int count = -1;
														for (int i = 0; i < gradeList.length; i++) {
															classLength = Integer.parseInt(classLengths[i]);
													%> 
													<tr>
														<td style="font-size: 14px;color:black;">
															<%String gradeName = "";
																StringTokenizer st = new StringTokenizer(gradeList[i],":");
																while(st.hasMoreTokens()){
																	gradeName = st.nextToken();
																}
															%>* <%=gradeName%>
														</td>
														<%
															for (int cnt = 0; cnt < classLength; cnt++) {
																	count++;
														%>

														<%
															if (cnt != 0) {
														%>
														<td></td>
														<%
															}
														%>
														<td ><input type="hidden"
															name="noOfSubjects" id="noOfSubjects<%=cnt%>" value=""></input>
															<%String className = "";
																st = new StringTokenizer(classList[count],":");
																while(st.hasMoreTokens()){
																	className = st.nextToken();
																}
															%>
															<%=className%> &nbsp;:</td>
														<td><select name="gradeLevels" style="color: rgb(128, 128, 128);"
															id="gradeLevels<%=cnt%>">
																<option value="select">Select Grade Level</option>
																<option value="1">Above Grade Level</option>
																<option value="2">At Grade Level</option>
																<option value="3">Below Grade Level</option>
														</select></td>
														<td><input type="text" name="noOfSectionsPerDay"
															size="5" id="noOfSectionsPerDay<%=cnt%>"></input> &nbsp;
														</td>
														<td><input type="text" name="noOfSectionsPerWeek"
															size="5" id="noOfSectionsPerWeek<%=cnt%>"></input> &nbsp;
														</td>


													</tr>
													<%
														}
														}
													%>
												</table>
											</td>
										</tr>
											<tr>
											<td valign="middle" width="10" align="center">&nbsp;</td>
											<td colspan="4" valign="middle" width="472" align="right">
												<table width="100%" height="115" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="5%" align="right" valign="middle">&nbsp;</td>
														<td width="95%" align="center" valign="middle" >
															<button class="for-back-ward-button-style button-block-inline"  >Register</button>	
														</td>
													</tr>
												</table>
											</td>
										</tr> 
									</tbody>
								</table>
							</form></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>