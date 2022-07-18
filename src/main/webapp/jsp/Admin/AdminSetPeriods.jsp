<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Set/Edit Periods</title>

<link rel='stylesheet' href='resources/css/style.css'>
<link rel='stylesheet' href='resources/css/style2.css'>
<script type="text/javascript"
	src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/Adminjs.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<style>
.tabs {
	position: relative;
	min-height: 350px;
	clear: both;
	/*margin: 25px 0;*/
}

.tab {
	float: right;
}

.tab label {
	/* border: 1px solid #ccc;
	margin-left: -1px;
	position: relative;
	left: 1px; */
}
.content {
	position: absolute;
	top: 28px;
	left: 0;
	right: 0;
	bottom: 0;
/* 	padding: 20px; */
}

[type=radio]:checked ~ label {
	border-bottom: 1px solid white;
	z-index: 2;
}

[type=radio]:checked ~ label ~ .content {
	z-index: 1;
}
</style>

<script type="text/javascript">
$(document).ready(function () {
		document.getElementById("dis").style.visibility='visible';
		document.getElementById("dis1").style.visibility='hidden';
		$('input:radio[name=tab-group-1]')[0].checked = true;
	 
});
function createPeriods(){
	$("#tbl").attr('class', 'show');
	$("#create").removeClass('button').addClass('buttonSelect');
	$("#edit").removeClass('buttonSelect').addClass('button');
	document.getElementById("getperiod").innerHTML = "";
	document.getElementById("adminUpdatePeriods").reset();
	document.getElementById("dis").style.visibility='visible';
	document.getElementById("dis1").style.visibility='hidden';
	$('input:radio[name=tab-group-1]')[0].checked = true;
}

function editPeriods(){
	$("#tbl").attr('class', 'hide');
	$("#edit").removeClass('button').addClass('buttonSelect');
	$("#create").removeClass('buttonSelect').addClass('button');
	document.getElementById("edit").src = "images/Admin/edit_periods_gn.png";   
	document.getElementById("create").src = "images/Admin/set_periods_gr.png"; 
	document.getElementById("adminSetPeriodsForm").reset();
	document.getElementById("dis").style.visibility='hidden';
	document.getElementById("dis1").style.visibility='visible';
	$('input:radio[name=tab-group-1]')[1].checked = true;
	
}
</script>
</head>

<body>
<input type="hidden" id="lastChecked" name="lastChecked" value=""/>
	<table width="100%">
	<tr><td>	
	<table width="100%">
    <tr><td colspan="" width="100%" class="button-group" style="box-shadow:none;"> 
	<div id="page-wrap">
		<div width="100%" align="center" class="tabs">
			<div class="tab">
				<input type="radio" id="tab-1" name="tab-group-1" checked style="display: none;"> 
					<label for="tab-1">
						<!-- <img id="create" border=0 src="images/Admin/set_periods_gn.png" width=103 height=27 /> -->
						<li><a href="#" onclick="createPeriods()" id="create" class="buttonSelect" style="border-top-left-radius:25px;box-shadow: 0px 5px 5px #888;/* border-bottom-left-radius :90px */">Set Periods</a></li>
					</label>
				 <div class="content">
				 <div id="dis" style="visibility:visible">
					 <table width="100%" class="title-pad" border="0" style="padding-top: 1.5em;">
						<tr>
							<td class="sub-title title-border" height="40" align="left" >Set Periods</td>
						</tr>
						<tr><td height="20" colspan="2"></td></tr>
					</table>
					
					<form:form name="adminSetPeriodsForm" id="adminSetPeriodsForm" modelAttribute="Periods">
					  <div id="content-box" align="center">
						<table style="" align="center"><tr><td>
							
							<table class="des" border=0 id="cons" align="center"><tr><td><div class="Divheads"><table><tr><td class="headsColor">Set Period<td></tr></table></div>
							<div class="DivContents"><table width="450">
							<tr><td></td></tr>
							<tr>
							<td width="50" height="35" align="left" valign="top">&nbsp;</td>
							  		<td width="250" align="left" valign="middle"
									class="tabtxt">Select Grade</td>
								<td width="100" align="left" valign="middle"
									class="text1">:</td>
								<td width="100" align="left" valign="middle"
									class="tabtxt"><select id="gradeId" name="gradeId" class="listmenu">
									<option value="">Select</option>
									<c:forEach items="${sgList}" var="ul">
										 <option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td width="50" height="35" align="left" valign="top"></td>
								<td width="250" height="35" align="left" valign="middle"
									class="tabtxt">Enter Period Name</td>
								<td width="100" height="35" align="left" valign="middle"
									class="text1">:</td>
								<td height="35" width="100" align="left" valign="middle"
									class=""><input type="text" name="period" class="textBox1"
									id="period"></td>
							</tr>
							<tr>
								<td width="50" height="35" align="left" valign="top"></td>
								<td width="250" height="35" align="left" valign="middle"
									class="tabtxt">Start Time</td>
								<td width="100" height="35" align="left" valign="middle"
									class="text1">:</td>
								<td height="35" align="left" width="100" valign="middle">
									<table style="width: 20%">
										<tr>
											<td width="6%" align="left" valign="middle"><select
												name="starttime" id="starttime" value="" class="datelistmenu">
													<option value="8">8</option>
													<option value="9">9</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
												</select></td>
											<td width="1%" align="center" valign="middle">:</td>
											<td width="13%" align="left" valign="middle">
												<select id="starttimemin" name="starttimemin" class="datelistmenu">
														<c:forEach items="${mins}" var="mn">
															<option value="${mn.minute}">${mn.minute}</option>
														</c:forEach>
												</select></td>
											<td width="100%" align="left" valign="middle">
											<select name="starttimemeridian" id="starttimemeridian" class="datelistmenu">
												<option value="AM">AM</option>
												<option value="PM">PM</option>
											</select></td>
										</tr>
									</table>
								</td>
							</tr>
                            <tr>
								<td width="50" height="35" align="left" valign="top"></td>
								<td width="250" height="35" align="left" valign="middle"
									class="tabtxt">End Time</td>
								<td width="100" height="35" align="left" valign="middle"
									class="text1">:</td>
								<td height="30" align="left" valign="middle" width="100">
									<table style="width: 20%">
										<tr>
											<td width="6%" align="left" valign="middle"><select
												name="endtime" id="endtime" value="" class="datelistmenu">
													<option value="8">8</option>
													<option value="9">9</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
											</select></td>
											<td width="1%" align="center" valign="middle">:</td>
											<td width="13%" align="left" valign="middle">
												<select id="endtimemin" name="endtimemin" class="datelistmenu">
													<c:forEach items="${mins}" var="mn">
														<option value="${mn.minute}">${mn.minute}</option>
													</c:forEach>
												</select></td>
											<td width="100%" align="left" valign="middle">
											<select name="endtimemeridian" id="endtimemeridian" class="datelistmenu">
												<option value="AM">AM</option>
												<option value="PM">PM</option>
											</select></td>
										</tr>
									</table>
								</td>
							</tr>
							</table></div></td></tr>
							<tr><td align="center"><table id="tbl" width="70%" align="center">
							<tr>
								<td colspan="2" height="25" align="right" valign="middle">
									<div class="button_green" align="right" onclick="savePeriod();return false;">Submit Changes</div> 
								</td>
								<td width="50">&nbsp;</td>
								<td height="30" align="left" valign="middle" width="100"> 
									<div class="button_green" align="right" onclick="document.adminSetPeriodsForm.reset();return false;">Clear</div> 
								</td>
							</tr>
							</table></td></tr>	
						    <tr><td height=10 colSpan=30></td></tr>						
							</table></td></tr>
							 <tr><td height=10 colSpan=30></td></tr>	
							 <tr><td height="10" colspan="3" align="center"><b><div  id="result" class="status-message"></div></b></td></tr>
						</table>
					 </div>	
			</form:form>
			  </div>		
		</div>
		<div class="tab" width="100%" align="center" >
			<input type="radio" id="tab-2" name="tab-group-1" style="display: none"> 
				<label for="tab-2">
					<!-- <img id="edit" border=0 src="images/Admin/edit_periods_bl.png" width=103 height=27 />			 -->	
					<li><a href="#" onclick="editPeriods()" id="edit" class="button" style="border-top-right-radius:25px;box-shadow: 0px 5px 5px #888;/* border-bottom-right-radius :90px; */">Edit Periods</a></li>	
				</label>
			<div class="content">
			 <div id="dis1" style="visibility:visible">
				<table width="100%" class="title-pad" border="0" style="padding-top: 1.5em;">
						<tr>
							<td class="sub-title title-border" height="40" align="left" >Edit Periods</td>
						</tr>
					</table>
				<form:form name="adminUpdatePeriods" id="adminUpdatePeriods" modelAttribute="Periods">
					
					<table  style="width: 100%" class="title-pad">
						<tr>
						<td width="30" align="center"  class="label" style="font-size:15;">Grade&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td width="50" align="left" ><select id="gradeIds" name="gradeIds" onchange="getPeriods()" class="listmenu" style="width:120px;">
									
										<option value="">Select</option>
										<c:forEach items="${sgList}" var="ul">

											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>

										</c:forEach>
								</select></td>
							<td height="10" colspan="2" align="left" valign="middle">
							 <table	width="80%" height="30" border="0" cellpadding="0"	cellspacing="0">
									<tr>
										
									
										<td width="58" align="center" valign="middle" class="header"><font
											color="aeboad" size="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
										<td width="80" align="right" valign="right"></td>
										<td>&nbsp;</td>
										<td><font color="blue"> </font>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr><td height="20" colspan="2"></td></tr>
									<tr><td>&nbsp;</td></tr>
									<tr align="center"><td width="30%"></td><td align="center"></td></tr>
                                     								
								</table>
						</td></tr>
					</table>
					<div width="80%" id="getperiod" style="overflow: auto;"></div>
					<div align="center" id="returnMessage" class="status-message"></div>
				</form:form>
			</div>
			</div>
		</div>
	</div>
	</div>
 </div>
 </td></tr>	
 </table>
 </td></tr>
 </table>
</body>
</html>