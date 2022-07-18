
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Add/Edit Events</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
<%@ include file="../CommonJsp/include_resources.jsp" %>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<script type="text/javascript" src="resources/javascript/admin/events.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script>
$(function() {
    $( "#announcementDate" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
        minDate: 0
    });
    
    $( "#lastDate").datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
        minDate: 0
    });
    
    $( "#scheduleDate" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
        minDate: 0
    });
    setEventTime();
    setValidation();
});
function setEventTime(){
	 var hours =  $('#selHours').val(); 
	 var minutes =  $('#selMinutes').val(); 
	 var timeMeridian =  $('#selTimeMeridian').val(); 
	 if(hours)
		  $('#hours option[value="'+hours+'"]').attr("selected", "selected");
	 if(minutes)
		  $('#minutes option[value="'+minutes+'"]').attr("selected", "selected");
	 if(timeMeridian)
		  $('#timeMeridian option[value="'+timeMeridian+'"]').attr("selected", "selected");
}
function setValidation(){
	 var userType = "${userReg.user.userType}";
	if(userType == 'admin')
 		$('#eventName').attr("onblur", "compareEvent(this)");
	else
		$("#tableDiv").find("input,button,textarea,select").attr("disabled", "disabled");
}
</script>
<style>
	.ui-datepicker{font-size:.8em;}
	.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#03b7cf) );border:1px solid #36b3c4;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
	.ui-widget-overlay{background:rgba(81, 139, 146, 0.66);}
.event-label{
	font-family: cursive;
	color: #155673;
	font-size: 18px;
}
</style>
</head>
<body>
<input type="hidden" id="selHours" value="${hours}">
<input type="hidden" id="selMinutes" value="${minutes}">
<input type="hidden" id="selTimeMeridian" value="${timeMeridian}">
<input type="hidden" id="operation" value="${operation}">
	<table align="center"  style="padding-top: 1em;">
		<tr><td width="100%">
		 <table align="center" width="90%" id="tableDiv"><tr><td>
		 <div class="event-label"><table align="center" class="" width="100%"><tr><td align="center"><div id="subTitle">${event.eventId > 0 ? 'Edit' : 'Add'} Event</div></td></tr></table></div>
		 <div><table>
		 <form:form id="eventForm" modelAttribute="event" method="post">
		 <tr>
			<td height="0" colspan="2" align="center" valign="top" class="">
			<form:hidden path="eventId" id="eventId"/>
				<table id="T3" width="100%" id='divtag' align="center">
					<tr>
						<td width="40%" height="30" align="right" valign="middle" style="padding-top:1em;"><span
							class="tabtxt"><img src="images/Common/required.gif"> Event Name</span></td>
						<td width="10%" height="30" align="center" valign="middle" style="padding-top:1em;">:</td>
						<td height="20%" align="left" valign="middle"><span
							class="tabtxt"> <form:input path="eventName"
									id="eventName" onblur="compareEvent(this)" style="margin-top:1em;" /><font
								color="red"> <!--  --> <form:errors path="eventName"></form:errors>
							</font>
						</span></td>
					</tr>
					<tr>
						<td width="190" height="10" align="right" valign="middle"></td>
						<td width="10" height="10" align="center" valign="middle"></td>
						<td height="10" align="left" valign="middle"></td>
					</tr>
					<tr>
						<td width="190" height="30" align="right" valign="top"><span
							class="tabtxt"><img src="images/Common/required.gif"> Description</span></td>
						<td width="10" height="30" align="center" valign="top">:</td>
						<td height="30" align="left" valign="middle"><span
							class="tabtxt"><form:textarea path="description"
									id="description"></form:textarea> <font color="red">
									<form:errors path="description"></form:errors>
							</font> </span></td>
					</tr>
					<tr>
						<td width="190" height="10" align="right" valign="middle"></td>
						<td width="10" height="10" align="center" valign="middle"></td>
						<td height="10" align="left" valign="middle"></td>
					</tr>
					<tr>
						<td width="190" height="30" align="right" valign="middle"><span
							class="tabtxt"><img src="images/Common/required.gif"> Announce Date</span></td>
						<td width="10" height="30" align="center" valign="middle">:</td>
						<td height="30" align="left" valign="middle">
						<form:input	path="announcementDate" id="announcementDate" value="${announcementDate}" readonly="true" /><font
							color="red"> <form:errors path="announcementDate"></form:errors>
						</font></td>
					</tr>
					<tr>
						<td width="190" height="10" align="right" valign="middle"></td>
						<td width="10" height="10" align="center" valign="middle"></td>
						<td height="10" align="left" valign="middle"></td>
					</tr>
					<tr>
						<td width="600" height="30" align="right" valign="middle"><span
							class="tabtxt"><img src="images/Common/required.gif"> Last Date for Applying</span></td>
						<td width="10" height="30" align="center" valign="middle">:</td>
						<td height="30" align="left" valign="middle">
						<form:input	path="lastDate" id="lastDate" readonly="true" value="${lastDate}"/><font
							color="red"> <form:errors path="lastDate"></form:errors>
						</font> </td>
					</tr>
					<tr>
						<td width="190" height="10" align="right" valign="middle"></td>
						<td width="10" height="10" align="center" valign="middle"></td>
						<td height="10" align="left" valign="middle"></td>
					</tr>
					<tr>
						<td width="190" height="30" align="right" valign="middle"><span
							class="tabtxt"><img src="images/Common/required.gif"> Schedule Date</span></td>
						<td width="10" height="30" align="center" valign="middle">:</td>
						<td height="30" align="left" valign="middle">
						<form:input path="scheduleDate" id="scheduleDate" readonly="true" value="${scheduleDate}" /><font
							color="red"> <form:errors path="scheduleDate"></form:errors>
						</font> </td>
					</tr>
					<tr>
						<td width="190" height="10" align="right" valign="middle"></td>
						<td width="10" height="10" align="center" valign="middle"></td>
						<td height="10" align="left" valign="middle"></td>
					</tr>
					<tr>
						<td width="190" height="30" align="right" valign="middle"><span
							class="tabtxt"><img src="images/Common/required.gif"> Venue</span></td>
						<td width="10" height="30" align="center" valign="middle">:</td>
						<td height="30" align="left" valign="middle"><span
							class="tabtxt"> <form:input path="venue" /><font
								color="red"> <form:errors path="venue"></form:errors>
							</font>
						</span></td>
					</tr>
					<tr>
						<td width="190" height="10" align="right" valign="middle"></td>
						<td width="10" height="10" align="center" valign="middle"></td>
						<td height="10" align="left" valign="middle"></td>
					</tr>
					<tr>
						<td width="140" height="30" align="right" valign="middle"><span
							class="tabtxt"><img src="images/Common/required.gif"> Event Time </span></td>
						<td width="10" height="30" align="center" valign="middle">:</td>
						<td height="30" align="left" valign="middle"><table
								width="100" border="0" cellspacing="2" cellpadding="0">
								<tr>
									<td align="left" valign="middle"></td>
									<td width="6%" align="left" valign="middle"><form:select
											class="datelistmenu" path="hours" id="hours">
											<form:option value="01">01</form:option>
											<form:option value="02">02</form:option>
											<form:option value="03">03</form:option>
											<form:option value="04">04</form:option>
											<form:option value="05">05</form:option>
											<form:option value="06">06</form:option>
											<form:option value="07">07</form:option>
											<form:option value="08">08</form:option>
											<form:option value="09">09</form:option>
											<form:option value="10">10</form:option>
											<form:option value="11">11</form:option>
											<form:option value="12">12</form:option>
										</form:select></td>
									<td width="1%" align="center" valign="middle">:</td>
									<td width="13%" align="left" valign="middle"><form:select
										class="datelistmenu"	path="minutes" id="minutes">
											<form:option value="00">00</form:option>
											<form:option value="10">10</form:option>
											<form:option value="20">20</form:option>
											<form:option value="30">30</form:option>
											<form:option value="40">40</form:option>
											<form:option value="50">50</form:option>
										</form:select></td>
									<td width="100%" align="left" valign="middle"><form:select
										class="datelistmenu"	path="timeMeridian" id="timeMeridian">
											<form:option value="AM">AM</form:option>
											<form:option value="PM">PM</form:option>
										</form:select></td>
								</tr>
							</table></td>
					</tr>
					<tr>
						<td width="190" height="10" align="right" valign="middle"></td>
						<td width="10" height="10" align="center" valign="middle"></td>
						<td height="10" align="left" valign="middle"></td>
					</tr>
					<tr>
						<td width="190" height="30" align="right" valign="middle"><span
							class="tabtxt"><img src="images/Common/required.gif"> Contact Person</span></td>
						<td width="10" height="30" align="center" valign="middle">:</td>
						<td height="30" align="left" valign="middle"><span
							class="tabtxt"> <form:input path="contactPerson" /><font
								color="red"> <form:errors path="contactPerson"></form:errors>
							</font>
						</span></td>
					</tr>
					<tr>
						<td width="190" height="10" align="right" valign="middle"><form:hidden
								path="operationMode" name="operationMode" id="operationMode"
								value="add" /></td>
						<td width="10" height="10" align="center" valign="middle"></td>
						<td height="10" align="left" valign="middle"></td>
					</tr>
				</table>
			</td>
			</tr>
			<tr><td height="10"></td>	
	 <c:choose>
		<c:when test="${userReg.user.userType == 'admin'}">
			<tr>
				<td width="123" height="23" align="center"><table
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%" align="right" valign="middle">
								<input type="button" class="button_green" value="SubmitChanges" style="border:none" onclick="saveOrUpdateEvent()">
							</td>
							<td width="10%" align="center" valign="middle"><div class="button_green" onclick="$('#eventForm')[0].reset();">Cancel</div></td>
						</tr>
						<tr><td>&nbsp;</td></tr>
					</table>
				</td>
			</tr>
		</c:when> 
	</c:choose>	
	</form:form></table>
	</div></td></tr></table>
	</td></tr></table>
</body>
</html>
