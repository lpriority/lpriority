<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function () {
		 $('#statusMessage').fadeOut(3000);
	});
</script>
</head>
<body>
	<form:form>
		<c:if test="${fn:length(PeriodList) > 0}">
		<table class="des" border=0  id="togglePage"><tr><td>
         <div class="Divheads"><table>
					
						<tr>
							<td align="center" valign="middle" class="header"><font
								color="white"></font></td>
							<td width="200" height="20" align="center" valign="middle"><font
								color="white">Period Name</font></td>

							<td width="210" height="20" align="center" valign="middle"><font
								color="white">Start Time</font></td>

							<td width="210" height="20" align="center" valign="middle"><font
								color="white">End Time</font></td>

							<td width="50" height="20" align="center" valign="middle"><font
								color="white">Edit</font></td>
							<td width="50" align="center" valign="middle"><font
								color="white">Delete</font></td>
							<td width="100" height="20" align="center" valign="middle"
								maxsize="30"><font color="white"></font></td>
						</tr></table></div>
						<%
							int count1 = -1;
								int i = 0;

								//count1 = rs1.getInt(1);
						%>
						<div class="DivContents"><table>
						<tr><td>&nbsp;</td></tr>
						<c:forEach items="${PeriodList}" var="per">
							<%
								i = i + 1;
										//String starttime = "";
										//String endtime = "";
										//String period = "";
										// String periodId = "";
										//String starttimemin = "";
										// String starttimemeridian = "";
										// String endtimemin = "";
										//  String endtimemeridian = "";
										// String insert = "";
										// String del = "";

										String startselect8 = "";
										String startselect9 = "";
										String startselect10 = "";
										String startselect11 = "";
										String startselect12 = "";
										String startselect13 = "";
										String startselect14 = "";
										String startselect15 = "";
										String startselect16 = "";
										String startselect17 = "";
										String startselect18 = "";
										String startselect19 = "";
										String startselect20 = "";

										String endselect8 = "";
										String endselect9 = "";
										String endselect10 = "";
										String endselect11 = "";
										String endselect12 = "";
										String endselect13 = "";
										String endselect14 = "";
										String endselect15 = "";
										String endselect16 = "";
										String endselect17 = "";
										String endselect18 = "";
										String endselect19 = "";
										String endselect20 = "";

										String startmin = "";
										String endmin = "";

										String startAM = "";
										String endAM = "";
										String startPM = "";
										String endPM = "";

										String startTimeDb = "";
										String endTimeDb = "";
										String startTimeArray[] = new String[3];
										String endTimeArray[] = new String[3];
										String StartTime = "";
										String StartTimeMin = "";
										String StartTimeMeridian = "";
										String EndTime = "";
										String EndTimeMin = "";
										String EndTimeMeridian = "";
										String STime = "";
										String ETime = "";
							%>

							<c:set var="starttime" value="${per.startTime}" />
							<c:set var="endtime" value="${per.endTime}" />
							<%
								ETime = pageContext.getAttribute("endtime").toString();
										STime = pageContext.getAttribute("starttime").toString();
										if (STime != null || STime != "") {
											startTimeArray = STime.split(":");
										}
										if (ETime != null || ETime != "") {
											endTimeArray = ETime.split(":");
										}
										int arr_starttime = -1;
										int arr_endtime = -1;
										if (startTimeArray[0] != null || startTimeArray[0] != "") {
											arr_starttime = Integer.parseInt(startTimeArray[0]);
										}
										if (endTimeArray[0] != null || endTimeArray[0] != "") {
											arr_endtime = Integer.parseInt(endTimeArray[0]);
										}
										if (arr_starttime >= 12) {
											if (arr_starttime == 12) {
												StartTime = arr_starttime + "";
											} else {
												StartTime = (arr_starttime - 12) + "";
											}
											StartTimeMin = startTimeArray[1];
											StartTimeMeridian = "PM";
										} else {
											StartTime = startTimeArray[0];
											StartTimeMin = startTimeArray[1];
											StartTimeMeridian = "AM";

										}
										if (arr_endtime >= 12) {
											if (arr_endtime == 12) {
												EndTime = arr_endtime + "";
											} else {
												EndTime = (arr_endtime - 12) + "";
											}
											EndTimeMin = endTimeArray[1];
											EndTimeMeridian = "PM";
										} else {
											EndTime = endTimeArray[0];
											EndTimeMin = endTimeArray[1];
											EndTimeMeridian = "AM";

										}

										StartTime = StartTime.trim();
										EndTime = EndTime.trim();

										StartTimeMeridian = StartTimeMeridian.trim();
										EndTimeMeridian = EndTimeMeridian.trim();

										if (StartTime.equals("08")) {
											startselect8 = "selected";

										} else if (StartTime.equals("09") || StartTime.equals("9")) {
											startselect9 = "selected";
										} else if (StartTime.equals("10")) {
											startselect10 = "selected";
										} else if (StartTime.equals("11")) {
											startselect11 = "selected";
										} else if (StartTime.equals("12")) {
											startselect12 = "selected";
										} else if (StartTime.equals("1") || StartTime.equals("01")) {
											startselect13 = "selected";
										} else if (StartTime.equals("2") || StartTime.equals("02")) {
											startselect14 = "selected";
										} else if (StartTime.equals("3") || StartTime.equals("03")) {
											startselect15 = "selected";
										} else if (StartTime.equals("4") || StartTime.equals("04")) {
											startselect16 = "selected";
										} else if (StartTime.equals("5") || StartTime.equals("05")) {
											startselect17 = "selected";
										} else if (StartTime.equals("6") || StartTime.equals("06")) {
											startselect18 = "selected";
										} else if (StartTime.equals("7") || StartTime.equals("07")) {
											startselect19 = "selected";
										} else if (StartTime.equals("8") || StartTime.equals("08")) {
											startselect20 = "selected";
										}
										if (EndTime.equals("08")) {
											endselect8 = "selected";
										} else if (EndTime.equals("09") || EndTime.equals("9")) {
											endselect9 = "selected";
										} else if (EndTime.equals("10")) {
											endselect10 = "selected";
										} else if (EndTime.equals("11") || EndTime.equals("11")) {
											endselect11 = "selected";
										} else if (EndTime.equals("12")) {
											endselect12 = "selected";
										} else if (EndTime.equals("1") || EndTime.equals("01")) {
											endselect13 = "selected";
										} else if (EndTime.equals("2") || EndTime.equals("02")) {
											endselect14 = "selected";
										} else if (EndTime.equals("3") || EndTime.equals("03")) {
											endselect15 = "selected";
										} else if (EndTime.equals("4") || EndTime.equals("04")) {
											endselect16 = "selected";
										} else if (EndTime.equals("5") || EndTime.equals("05")) {
											endselect17 = "selected";
										} else if (EndTime.equals("6") || EndTime.equals("06")) {
											endselect18 = "selected";
										} else if (EndTime.equals("7") || EndTime.equals("07")) {
											endselect19 = "selected";
										} else if (EndTime.equals("8")) {
											endselect20 = "selected";
										}

										if (StartTimeMeridian.equals("AM")) {
											startAM = "selected";
										} else if (StartTimeMeridian.equals("PM")) {

											startPM = "selected";
										}
										if (EndTimeMeridian.equals("AM")) {
											endAM = "selected";
										} else if (EndTimeMeridian.equals("PM")) {

											endPM = "selected";

										}
							%>
							<tr>
								<td height="30" valign="middle" align="right"></td>
								<td height="30" valign="middle" align="center"><input
									type="text" class="textBox" name="period<%=i%>" id="period<%=i%>"
									value="${per.periodName}" /> <input type="hidden"
									name="periodId<%=i%>" id="periodId<%=i%>"
									value="${per.periodId}" /></td>
								<td width="210" height="30" align="center" valign="middle">
									<table width="100" border="0" cellspacing="2" cellpadding="0">
										<tr>
											<td align="left" valign="middle"></td>
											<td width="6%" align="left" valign="middle"><select class="datelistmenu"
												name="starttime<%=i%>" id="starttime<%=i%>">
													<option value="08" <%=startselect8%>>8</option>
													<option value="09" <%=startselect9%>>9</option>
													<option value="10" <%=startselect10%>>10</option>
													<option value="11" <%=startselect11%>>11</option>
													<option value="12" <%=startselect12%>>12</option>
													<option value="1" <%=startselect13%>>1</option>
													<option value="2" <%=startselect14%>>2</option>
													<option value="3" <%=startselect15%>>3</option>
													<option value="4" <%=startselect16%>>4</option>
													<option value="5" <%=startselect17%>>5</option>
													<option value="6" <%=startselect18%>>6</option>
													<option value="7" <%=startselect19%>>7</option>
													<option value="8" <%=startselect20%>>8</option>
											</select></td>
											<td width="1%" align="center" valign="middle" class="text1" >:</td>
											<td width="13%" align="left" valign="middle"><select  class="datelistmenu"
												name="starttimemin<%=i%>" id="starttimemin<%=i%>">
													<%
														String selc = "";
													%>

													<c:forEach items="${mins}" var="mn">
														<c:set var="starttimemins" value="${mn.minute}" />
														<%
															String starttimemins = pageContext.getAttribute(
																				"starttimemins").toString();
																		if (starttimemins.equalsIgnoreCase(StartTimeMin)) {
																			selc = "selected";
																		} else
																			selc = "";
														%>
														<option value="${mn.minute}" <%=selc%>>${mn.minute}</option>

													</c:forEach>
											</select></td>
											<td width="100%" align="left" valign="middle"><select  class="datelistmenu"
												name="starttimemeridian<%=i%>" id="starttimemeridian<%=i%>">
													<option value="AM" <%=startAM%>>AM</option>
													<option value="PM" <%=startPM%>>PM</option>
											</select></td>
										</tr>
									</table>
								</td>
								<td width="210" height="30" align="center" valign="middle"><table
										width="100" border="0" cellspacing="2" cellpadding="0">
										<tr>
											<td align="left" valign="middle"></td>
											<td width="6%" align="left" valign="middle"><select  class="datelistmenu"
												name="endtime<%=i%>" id="endtime<%=i%>">

													<option value="08" <%=endselect8%>>8</option>
													<option value="09" <%=endselect9%>>9</option>
													<option value="10" <%=endselect10%>>10</option>
													<option value="11" <%=endselect11%>>11</option>
													<option value="12" <%=endselect12%>>12</option>
													<option value="1" <%=endselect13%>>1</option>
													<option value="2" <%=endselect14%>>2</option>
													<option value="3" <%=endselect15%>>3</option>
													<option value="4" <%=endselect16%>>4</option>
													<option value="5" <%=endselect17%>>5</option>
													<option value="6" <%=endselect18%>>6</option>
													<option value="7" <%=endselect19%>>7</option>
													<option value="8" <%=endselect20%>>8</option>
											</select></td>
											<td width="1%" align="center" valign="middle" class="text1">:</td>
											<td width="13%" align="left" valign="middle"><select  class="datelistmenu"
												name="endtimemin<%=i%>" id="endtimemin<%=i%>">
													<c:forEach items="${mins}" var="mn">
														<c:set var="endtimemins" value="${mn.minute}" />
														<%
															String endtimemins = pageContext.getAttribute(
																				"endtimemins").toString();
																		if (endtimemins.equalsIgnoreCase(EndTimeMin)) {
																			selc = "selected";
																		} else
																			selc = "";
														%>
														<option value="${mn.minute}" <%=selc%>>${mn.minute}</option>

													</c:forEach>
													<!-- 														  }%> -->
											</select></td>
											<td width="100%" align="left" valign="middle"><select  class="datelistmenu"
												name="endtimemeridian<%=i%>" id="endtimemeridian<%=i%>">

													<option value="AM" <%=endAM%>>AM</option>
													<option value="PM" <%=endPM%>>PM</option>
											</select></td>
										</tr>
									</table></td>
								<td width="50" height="30" align="center" valign="middle"><input
									type="checkbox" class="checkbox-design" id="edit<%=i%>" name="edit<%=i%>"
									value="${per.periodId}" data-confirm onClick="updatePeriod(<%=i%>)" /><label for="edit<%=i%>" class="checkbox-label-design"></label></td>
								<td width="50" height="30" align="center" valign="middle"><input
									type="checkbox" class="checkbox-design" id="del<%=i%>" name="del<%=i%>"
									value="${per.periodId}" onClick="DeleteCheckPeriod(<%=i%>)" /><label for="del<%=i%>" class="checkbox-label-design"></label></td>
							</tr>
							<%
								startTimeDb = "";
										endTimeDb = "";
										startTimeArray = null;
										endTimeArray = null;
										StartTime = "";
										StartTimeMin = "";
										StartTimeMeridian = "";
										EndTime = "";
										EndTimeMin = "";
										EndTimeMeridian = "";
										STime = "";
										ETime = "";
							%>
						</c:forEach>

					</table></div></td></tr></table>
					<br><br>
		</c:if>
		<c:if test="${fn:length(PeriodList) <= 0}">
			<table align="center" >
				<tr>
					<td align="center" id="statusMessage"><font color="blue">
						No Periods for this grade</font>
					</td>
				</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>