<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.StringTokenizer"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Automatic Scheduler</title>
<script type="text/javascript" src="resources/javascript/AdminScheduler.js"></script>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<script type="text/javascript">

$(function() {
	    $( "#startDate" ).datepicker({
	    	changeMonth: true,
	        changeYear: true,
	        showAnim : 'clip',
	        minDate: 0
	    });
	    $( "#endDate" ).datepicker({
	    	changeMonth: true,
	        changeYear: true,
	        showAnim : 'clip'
	        //minDate: 0
	    });
	});
	function updateCalendar(thisvar){
		var start = new Date(thisvar.value);
		var end = new Date();
		var diff = new Date(start - end);
		var days = Math.ceil(diff/1000/60/60/24);		
		$( "#endDate" ).datepicker( "option", "minDate",  days);
		 
	}
	$(document).ready(function () {
	 	$('#returnMessage').fadeOut(4000);
	});
</script>
</head>
<body>
	<c:set var="starttime" value="${per.startTime}" />
	<c:set var="endtime" value="${per.endTime}" />	
	<table align=left width="100%" border="0" cellpadding="0" cellspacing="0" style="padding-left: 8em;padding-top:2em;">
			<tr>
				<td class="sub-title title-border" height="40" align="left" style="" valign="middle">
					Automated Scheduler
	</td></tr></table>	
	<table align="center" width="100%"><tr><td align="center" class="content-box">
	<form:form id="automaticSchedulerForm" name="automaticSchedulerForm" modelAttribute="schoolSchedule"
		action="SaveAutomaticScheduler.htm" method="POST">
		<c:set var="AM" value="AM"></c:set>
		<c:set var="PM" value="PM"></c:set>
		<form:hidden path="schoolScheduleId" />
		<form:hidden path="school.schoolId" />
		<br>
		<table class="des" border=0 align="center" width="50%"><tr><td>
		<div class="Divheads"><table align="center"><tr><td>Automated Scheduler</td></tr></table></div>
		<div class="DivContents">
		<table align=center width="90%" border="0" cellpadding="0"
			cellspacing="0">
			 <tr><td height=30 colSpan=80></td></tr>
			<tr>
				<td width="300" height="30" align="right" class="tabtxt" style="padding-right: 1em;">
					<form:label path="noOfDays"><img src="images/Common/required.gif"> Number of days per week  </form:label>
				</td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" align="left" style="padding-left: 1em;"><form:input
						class="textBox1" type="number" path="noOfDays" id="noOfDays" size="10"
						maxlength="1" required="required" min="1" max="7"/></td>
			</tr>

			<tr>
				<td width="350" height="30" align="right" style="padding-right: 1em;"><form:label
						path="noOfClassPeriods" class="tabtxt"><img src="images/Common/required.gif"> Number of class periods per day  </form:label>
				</td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" align="left" style="padding-left: 1em;"><form:input
					class="textBox1"	type="number" path="noOfClassPeriods" id="noOfClassPeriods"
						size="5" maxlength="1" required="required" min="1" max="7"/></td>
			</tr>
			 <tr><td height=20 colSpan=80></td></tr>
			<tr>
				<td height="30" align="center" class="header" colspan="3">
				<u><font color="#005863">Times Per Periods  </font></u> 
				</td>
			</tr>
 			<tr><td height=10 colSpan=80></td></tr>
			<tr>
				<td height="30" align="right" style="padding-right: 1em;"><form:label
						path="periodRange" class="tabtxt"><img src="images/Common/required.gif"> Period Range  </form:label></td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" height="5" align="left" style="padding-left: 1em;"><form:input
					class="textBox1"	type="number" path="periodRange" id="periodRange" size="5"
						maxlength="2" required="required" min="10" max="60"/>&nbsp;min</td>
			</tr>
			<tr>
				<td height="30" align="right" style="padding-right: 1em;"><form:label
						path="passingTime" class="tabtxt"><img src="images/Common/required.gif"> Passing Time  </form:label></td>
				<td height="30" align="center">:</td>
				<td height="30" align="left" style="padding-left: 1em;"><form:input
					class="textBox1"	type="number" path="passingTime" id="passingTime" size="5"
						maxlength="2" required="required" min="5" max="15"/>&nbsp;min</td>
			</tr>
			<tr>
				<td height="30" align="right" style="padding-right: 1em;"><form:label path="homeRoomTime" class="tabtxt"><img src="images/Common/required.gif"> Home Room Time  </form:label></td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" align="left" style="padding-left: 1em;">
					<form:input class="textBox1" type="number" path="homeRoomTime" id="homeRoomTime" size="5" maxlength="2" required="required" min="5" max="10"/>&nbsp;min
				</td>
			</tr>
			 <tr><td height=20 colSpan=80></td></tr>
			<tr>
				<td height="30" align="center" class="header" colspan="3">
					<u><font color="#005863">Length of school day </font></u> 
				</td>
			 <tr><td height=10 colSpan=80></td></tr>
			</tr>
			<tr>
				<td height="30" align="right" valign="middle"
					 class="tabtxt" style="padding-right: 1em;"><img src="images/Common/required.gif"> Start Time  </td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" align="left" valign="middle"
					style="padding-left: 1em;">
					<table border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td align="left" valign="middle"><form:select
									class="datelistmenu" path="dayStartTime" id="starttime">
									<c:forEach var="sh" begin="8" end="12">
										<option value="${sh}"
											<c:if test="${sh==startTimehours}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>${sh}</option>
									</c:forEach>
									<c:forEach var="sh" begin="1" end="8">
										<option value="${sh}"
											<c:if test="${sh==startTimehours}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>${sh}</option>
									</c:forEach>

								</form:select></td>
							<td align="center" valign="middle">:</td>
							<td align="left" valign="middle"><form:select
									class="datelistmenu" path="dayStartTimeMin" id="starttimemin">
									<c:forEach items="${mins}" var="mn">
										<option value="${mn.minute}"
											<c:if test="${mn.minute==startTimeMinutes}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>${mn.minute}</option>

									</c:forEach>
								</form:select></td>
							<td align="left" valign="middle"><form:select
									class="datelistmenu" path="dayStartTimeMeridian" id="starttimemeridian">
									<option value="${AM}"
										<c:if test="${AM==startTimeMeridian}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>AM</option>
									<option value="${PM}"
										<c:if test="${PM==startTimeMeridian}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>PM</option>
								</form:select></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="30" align="right" valign="middle"
					 class="tabtxt" style="padding-right: 1em;"><img src="images/Common/required.gif"> End Time  </td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" align="left" valign="middle"
					style="padding-left: 1em;">
					<table border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td align="left" valign="middle"><form:select
								class="datelistmenu" path="dayEndTime" id="endtime">
									<c:forEach var="sh" begin="8" end="12">
										<option value="${sh}"
											<c:if test="${sh==endTimehours}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>${sh}</option>
									</c:forEach>
									<c:forEach var="sh" begin="1" end="8">
										<option value="${sh}"
											<c:if test="${sh==endTimehours}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>${sh}</option>
									</c:forEach>
								</form:select></td>
							<td align="center" valign="middle">:</td>
							<td height="30" align="left" valign="middle"><form:select
								class="datelistmenu"	path="dayEndTimeMin" id="endtimemin">
									<c:forEach items="${mins}" var="mn">
										<option value="${mn.minute}"
											<c:if test="${mn.minute==endTimeMinutes}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>${mn.minute}</option>
									</c:forEach>
								</form:select></td>
							<td height="30" align="left" valign="middle"><form:select
								class="datelistmenu"	path="dayEndTimeMeridian" id="endtimemeridian">

									<option value="${AM}"
										<c:if test="${AM==endTimeMeridian}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>AM</option>
									<option value="${PM}"
										<c:if test="${PM==endTimeMeridian}">
											<c:out value="selected='selected'"></c:out>
										</c:if>>PM</option>
								</form:select></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="350" height="30" align="right" class="tabtxt" style="padding-right: 1em;">
					<img src="images/Common/required.gif"> Number of subject matter teachers  </td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" align="left" style="padding-left: 1em;"><form:input
					class="textBox1"	required="required" type="number" path="noOfTeachers"
						id="noOfTeachers" size="5" maxlength="2" value="" min="1" max="100"></form:input></td>
			</tr>
			<tr>
				<td width="350" height="30" align="right" class="tabtxt" style="padding-right: 0.001em;">
					<img src="images/Common/required.gif"> Number of sections taught per day  </td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" align="left" style="padding-left: 1em;"><form:input
						class="textBox1" required="required" type="number" path="noOfSections"
						id="noOfSectionsPerDay" size="5" maxlength="1" min="1" max="5"></form:input></td>
			</tr>
			<tr>
				<td height="30" align="right" class="tabtxt" style="padding-right: 1em;">
					<img src="images/Common/required.gif"> Start Date  </td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" align="left" style="padding-left: 1em;"><form:input 
						class="textBox1" path="startDate" id="startDate" required="required" value="${startDate}" readonly="true" onchange="updateCalendar(this)"></form:input></td>
			</tr>
			<tr>
				<td height="30" align="right" class="tabtxt" style="padding-right: 1em;">
					<img src="images/Common/required.gif"> End Date  </td>
				<td height="30" width="120px" align="center" class="text1">:</td>
				<td height="30" align="left" style="padding-left: 1em;"><form:input
						class="textBox1" path="endDate" required="required" id="endDate" value="${endDate}" readonly="true"/></td>
			</tr>
			<tr>
				<td height="30" colspan="3" align="center"><br><br>
		      <div class="button_green" onClick="SaveAutomaticScheduler()">Submit Changes</div>
					
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<div class="button_green" onClick="$('#automaticSchedulerForm')[0].reset();">Clear</div>
				</td>
			</tr>
		</table></div>
		
	</td></tr></table></form:form></td></tr><tr><td>
	<div class="status-message" align="center" id="returnMessage">${returnMessage}</div>
	</td></tr></table>
	</td></tr>
	
	</table>
</body>
</html>