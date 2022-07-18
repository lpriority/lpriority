<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script src="resources/javascript/TeacherJs/math_assessment.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/admin/gear_game_by_admin.js"></script>
<style>
.assignPhonicSkillsTestClass{
	color: white;
	height:30px; 
	width:240px; 
	font-size:13px;
	background-color: #663399;
	font-weight: 900;
}
</style>
<script type="text/javascript">
$(document).ready(function () {
 	$('#status').fadeOut(4000);
});
function clearDiv(){
	$("#studentMathGameDetailsPage").html(""); 
}

$(function() {
    $( "#rows" ).accordion({
      collapsible: true,
      active: false,
      activate:function(event, ui ){
  		setFooterHeight();
    }
    });    
});
</script>
<style type="text/css">
.ui-accordion > .ui-widget-header {background: #94B8FF;}
.ui-accordion > .ui-widget-content {background: #e6edee;}
.ui-accordion > .ui-accordion-header {font-size: 100%;background:linear-gradient(to bottom,#e9f3f5 7%, #ffffff 33%, #ffffff 48%, rgb(210, 228, 230) 94%);outline: none;}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{border:1px solid #b4c3c5;}
</style>
<title>Review Gear Game</title>
</head>
 <body>
	   <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
                  <tr>
                      <td vAlign=bottom align=right>
                       <div> 
				         	<%@ include file="/jsp/assessments/rti_tabs.jsp" %>
				       </div>
                      </td></tr>
        </table>
        <input type="hidden" name="tab" id="tab" value="${tab}">
        <input type="hidden" name="assignmentTypeId" id="assignmentTypeId" value="${assignmentTypeId}">
		<input type="hidden" name="page" id="page" value="Gear Game" />
		<input type="hidden" name="usedFor" id="usedFor" value="${usedFor}" />
       			<table border=0 cellSpacing=0 cellPadding=0 width="100%"  valign="middle" align=center height="100%" class="title-pad"  style="padding-top: 1em;">  
                            <tr><td align="center" valign="middle">
							<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" align="center" valign="middle">
							<tr class="label" valign="middle">
								<td width="70em" valign="middle" class="label" style="font-size: 0.98em" >Grade  </td>
							<td width="150em" valign="middle" >
								<select id="gradeId" name="gradeId" onchange="clearDiv();getGradeClasses();" class="listmenu"  style="width:120px;">
									<option value="select">select grade</option>
									<c:forEach items="${grList}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
								</select>
							</td>
							<td width="70em"  valign="middle" class="space-between-select"><label class="label" style="font-size: 0.98em">Class  </label></td>
							<td width="150em" valign="center">
								<select id="classId" name="classId" class="listmenu" onchange="clearDiv();getClassSections();" style="width:120px;">
									<option value="select">select subject</option>
								</select>
							</td>
							<td width="70em" valign="middle" class="space-between-select label" class="listmenu">Section  </td>
							<td width="200em" valign="middle" >
								 <select  valign="middle" name="csId" id="csId" class="listmenu" onchange="clearDiv();getMathAssignedDates()" style="width:150px;"
									required="required">
								</select>
							</td>
								 <td width="80">Date</td>
								  <td width="150" align="left" valign="middle">
								 	 <select id="assignedDate" name="assignedDate" class="listmenu" style="width:120px;" onchange="clearDiv();getEltPstAssignmentTitles()" >
								 		<option value="select">select date</option>
									</select>
								 </td>
								 <td width="100" align="left">Test Title </td>
								 <td width="150">
								 	 <select id="titleId" name="titleId" class="listmenu" style="width:120px;" onchange="clearDiv();showMathGameResults()" >
								 		<option value="select" >select title</option>
									</select>
								 </td>
							   <td height="30" colSpan=10></td>
			                </tr>
			                <tr><td>&nbsp;</td></tr>
			                <tr valign="middle"><td height="30" colspan="22" align="center" valign="middle" style="padding-top: 1em;">
								 <div id="studentMathGameDetailsPage"></div>
							</td></tr>
			        	</table> 
                </td></tr>
 			 </table>
	</body>
</html>
