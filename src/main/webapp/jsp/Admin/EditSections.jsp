<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Add/Edit Sections</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript"
	src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/Adminjs.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function resetSelect(id,option){
	var obj = document.getElementById(id);
	$("#"+id).empty();
	$("#"+id).append($("<option></option>").val('Select').html('Select '+option));
	$("#getsection").html("");
}
$(document).ready(function () {
		document.getElementById("dis").style.visibility='visible';
		document.getElementById("dis1").style.visibility='hidden';
		$('input:radio[name=tab-group-1]')[1].checked = true;
});
function createTab(){
	$("#tbl").attr('class', 'show');
	$("#create").removeClass('button').addClass('buttonSelect');
	$("#edit").removeClass('buttonSelect').addClass('button');
	$('input:radio[name=tab-group-1]')[1].checked = true;
	document.getElementById("getsection").innerHTML = "";
	document.getElementById("EditSections").reset();
	document.getElementById("dis").style.visibility='visible';
	document.getElementById("dis1").style.visibility='hidden';
}

function getGrades(){
	$("#tbl").attr('class', 'hide');
	$("#edit").removeClass('button').addClass('buttonSelect');
	$("#create").removeClass('buttonSelect').addClass('button');
	$('input:radio[name=tab-group-1]')[0].checked = true;
	document.getElementById("CreateSections").reset();
	document.getElementById("dis").style.visibility='hidden';
	document.getElementById("dis1").style.visibility='visible';

}
</script>
<style>
.tabs {
	position: relative;
	min-height: 300px;
	clear: both;
}

.tab {
	float: right;
}

.tab label {
	/* background: #eee;
	border: 1px solid #ccc;
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
	/* padding: 20px; */
}

[type=radio]:checked ~ label {
	border-bottom: 1px solid white;
	z-index: 2;
}

[type=radio]:checked ~ label ~ .content {
	z-index: 1;
}
</style>
</head>
<body>
	<table width="100%">
	    <tr><td colspan="" width="100%" class="button-group" style="box-shadow:none;"> 
	<div id="page-wrap" width="100%" class="tabs">
			<div class="tab">
				<input type="radio" id="tab-2" name="tab-group-1" style="display: none;"> 
						<label for="tab-1"> 
							<li><a href="#" onclick="createTab()" id="create" class="buttonSelect" style="border-top-left-radius:25px;box-shadow: 0px 5px 5px #888;/* border-bottom-left-radius :90px */">Create Sections</a></li>
						</label>
						<div class="content">
						 <div id="dis1" style="visibility:visible">
						
							<table width="100%" height="100%">
							    <tr><td colspan="" width="100%" valign="top"> 
							    	 <table width="100%" class="title-pad heading-up" border="0" style="padding-top:1em;">
										<tr>
											<td class="sub-title title-border" height="40" align="left" >Edit Section</td>
										</tr>
									</table>
								</td></tr>
							    <tr><td colspan="" width="100%" valign="top"> 
								<table width="100%">
							 	<form:form name="EditSections" id="EditSections" modelAttribute="section">
								<tr><td colspan="" width="100%" class="label title-pad heading-up" valign="top" align="left">
									<table	width="30%" height="30" border="0" cellpadding="0" cellspacing="0">
											<tr>										
												<td align="left" class="label" style="font-size:15;">
												   Grade&nbsp;&nbsp;&nbsp;&nbsp;
													<select class="listmenu" id="gradeIds" name="gradeIds" onClick="resetSelect('classIds','Subject')" 
														onchange="loadSubjects()">
														<option value="select">select grade</option>
														<c:forEach items="${grList}" var="ul">
		
															<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
		
														</c:forEach>
													</select>
												</td>
												<td align="center" class="label" style="font-size:15;">
												Class&nbsp;&nbsp;&nbsp;&nbsp;
													<select id="classIds" name="classIds" class="listmenu"
													onchange="getSection()">
														<option value="select">select subject</option>
													</select>
												</td>										
											</tr>
											</table>	
								  </td></tr> 
								  <tr><td colspan="" width="100%" align="center">
								   <!-- Content center center -->		  
									<div class="heading-up">
										<table style="width: 80%;" height="30" border="0" cellpadding="0" align="center" cellspacing="0">
												<tr><td style="width: 80%;" colspan="5" align="center" height="100%">
																<div id="getsection" style="margin-bottom:1em;margin-top:2.5em;"></div>
															</td></tr>
												</table>
										
									</div>  
								  </td></tr>  
							  	</form:form>  
						</table>
						</td></tr>
						<tr><td align="center" height="20"><div id="resultDiv"  class="status-message"></div></td></tr>
					</table></div>
					</div>
					<div class="tab">
						<input type="radio" id="tab-1" name="tab-group-1" checked
							style="display: none">
						
						<label for="tab-2">
							<li><a href="#" onclick="getGrades()" id="edit" class="button" style="border-top-right-radius:25px;box-shadow: 0px 5px 5px #888;/* border-bottom-right-radius :90px; */">Edit Sections</a></li>
						</label>
		
						<div class="content">
						 <div id="dis" style="visibility:visible">
						<table width="100%" align="center">
						    <tr><td colspan="" width="100%" valign="top"> 
								 <table width="100%" class="title-pad heading-up" border="0" style="padding-top:1em;">
									<tr>
										<td class="sub-title title-border" height="40" align="left" >Create Section</td>
									</tr>
								</table>
							</td></tr>
						    <tr><td colspan="" width="100%" valign="top"> 
						    <table width="100%">
						    <tr><td colspan="" width="100%" class="heading-up label" valign="top">
							<form:form name="CreateSections" id="CreateSections" modelAttribute="section" action="" onsubmit="return false;">
								<table style="width: 100%" height="30" border="0" cellpadding="0" align="center" cellspacing="0">
								     <tr>
										<td height="30" colspan="2" align="left" valign="middle"><table
												width="100%" height="30" border="0" cellpadding="0"
												cellspacing="0">										
												<tr>
													<td width="30" align="center" valign="middle" class="label" style="padding-left:9em;font-size:15;">
														Grade&nbsp;&nbsp;&nbsp;&nbsp;</td>
													<td width="50" align="left" valign="middle"><select
														id="gradeId" name="gradeId" class="listmenu" onClick="resetSelect('classId','Subject')" onchange="loadClasses()">
															<option value="select">select grade</option>
															<c:forEach items="${grList}" var="ul">
		
																<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
		
															</c:forEach>
		
													</select> <c:out value="${ul.gradeId}"></c:out></td>
													<td width="58" align="center" valign="middle" class="label" style="font-size:15;">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Class&nbsp;&nbsp;</td>
													<td width="122" align="center" valign="center"><select
														id="classId" name="classId" onClick="resetSelect('gradeLevelId','GradeLevel')" class="listmenu"
														onchange="loadGradeLevels()">
															<option value="select">select subject</option>
		
													</select></td>
													<td width="58" align="center" valign="middle" class="tabtxt">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Group&nbsp;&nbsp;&nbsp;</td>
													<td width="80" align="center" valign="right"><select
														name="gradeLevelId" class="listmenu" id="gradeLevelId">
															<option value="select">select gradeLevel</option>
													</select></td>
													<td>&nbsp;</td>
													<td><font color="blue"> 
													</font>&nbsp;</td>
													<td>&nbsp;</td>
												</tr>
											</table></td>
									</tr>
									<tr><td height=15 colSpan=80></td></tr><tr><td class="heading-up">
									<table class="des" border=0 align="center" width="32%"><tr><td><div class="Divheads"><table><tr><td class="headsColor">Create Section</td></tr></table></div>
									<div class="DivContents"><table  style="width: 100%;">
									<tr><td colspan="" width="100%" valign="top" class="space-between">
											<table width="100%" align="left" id="tbl">
								   				 <tr><td colspan="" width="100%" valign="top"> 
											<tr>
				
												<td height="40" align="center" valign="top" class="tabtxt" style="color:black;">Enter Section
													Name&nbsp;&nbsp;:&nbsp;&nbsp; <input type="text" name="sectionName" id="sectionName"
													value=""></input>
												</td>
											</tr>
											<tr>
												<td align='center' valign='middle'>
												<br><div class="button_green" align="right" onclick="submitSection()">Submit Changes</div> 
												<div class="button_green" align="right" onclick="document.CreateSections.reset();return false;">Clear</div> 
				                                </td> 
											</tr>
										</table>
								</td></tr></table></div></td></tr></table>
							
							</td></tr>
							<tr>
								<td align="center" height="50"><div id="result" class="status-message"></div></td>
							</tr>
							</table>
							</form:form>
							</td></tr></table></td></tr></table>
						</div>
					</div>
				</div>
 </div>
 </td></tr>
	</table>
</body>
</html>