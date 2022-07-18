<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
<script src="resources/javascript/common/jQuery.print.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/assign_phonic_skill_test.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/grade_phonic_skill_test.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/phonic_test_reports.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>

<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<style>
.assignPhonicSkillsTestClass{
	color: white;
	height:30px; 
	width:240px; 
	font-size:13px;
	background-color: #663399;
	font-weight: 900;
}
#loading-div-background{
	opacity: 0.8;
}
</style>
<script type="text/javascript">
	function clearDiv(thisvar){
		if(thisvar.id == 'gradeId'){
			$("#csId").empty();
			$("#csId").append($("<option></option>").val('').html('Select Section'));
		}
		$("#contentDiv").hide();
	}
</script>
<title>Phonic Test Reports</title>
</head>
 <body>
  	   <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
	      <tr>
	          <td style="color:white;font-weight:bold" >
	          	<input type="hidden" name="tab" id="tab" value="${tab}">
	          </td>
	          <td vAlign=bottom align=right>
	              <div> 
	              	<c:choose>
						<c:when test="${usedFor == 'rti'}">
						<%@ include file="/jsp/assessments/rti_tabs.jsp" %>
						</c:when>    									
						<c:when test="${usedFor == 'homeworks' && teacherObj != null }">
						<%@ include file="/jsp/assessments/homework_tabs.jsp" %>
						</c:when>
						<c:otherwise>
						<%@ include file="/jsp/curriculum/curriculum_tabs.jsp" %>
						</c:otherwise>
					</c:choose>
	          	</div>
	          </td>
	       </tr>
       </table>
	   <input type="hidden" id="teacherId" name="teacherId" value="${teacherId}"/>
	   <input type="hidden" id="teacherName" name="teacherName" value="${teacherName}"/>
	   <input type="hidden" id="groupsLength" name="groupsLength" value="${fn:length(phonicGroupsLt)}"/>
	   <input type="hidden" name="usedFor" id="usedFor" value="rti" />
	    <input type="hidden" name="page" id="page" value="Phonic Skill Test" />
       <table border=0 cellSpacing=0 cellPadding=0 width="100%"  valign="middle" align=center height="100%" class="title-pad" style="padding-top: 1em;">  
                            <tr><td align="center" valign="middle">
							<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" align="center" valign="top">
							<tr class="label" valign="top">
								<td width="70" align="left">Grade</td>
								<td width="150" align="left" valign="middle">
									<select	id="gradeId" name="gradeId" onchange="getGradeClasses();clearDiv(this)" style="width:120px;" class="listmenu">
											<option value="select">select grade</option>
								 		<c:forEach items="${teacherGrades}" var="ul">
											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
										</c:forEach> 
									</select>
								</td>
								<td></td>
								<td width="70" align="left">Class</td>
								<td width="150" align="left" valign="middle">
								<select id="classId" name="classId" class="listmenu" style="width:120px;" onchange="clearDiv(this);getClassSections();" >
								 	<option value="select">select subject</option>
								</select></td>
								<td width="80" align="left">Section</td>
								<td width="150" align="left" valign="middle">
								<select id="csId" name="csId" class="listmenu" style="width:120px;" onchange="clearDiv(this);getPstAssignedDates();" >
									<option value="select">select section</option> 
								</select></td>
								 <td width="70" align="left">Date</td>
								  <td width="150" align="left" valign="middle">
								 	 <select id="assignedDate" name="assignedDate" class="listmenu" style="width:120px;" onchange="clearDiv(this);getEltPstAssignmentTitles()" >
								 		<option value="select">select date</option>
									</select>
								 </td>
								 <td width="100">Test Title </td>
								 <td>
								 	 <select id="titleId" name="titleId" class="listmenu" style="width:120px;" onchange="getPhonicTestReport()" >
								 		<option value="select" >select title</option>
									</select>
								 </td>
							   <td height="30" colSpan=10></td>
			                </tr>
			                <tr valign="middle"><td height="30" colspan="10" align="left" valign="middle" style="padding-top: 2em;padding-left:18em">
			                  <table id="contentDiv" width="100%" height="30" border="0" cellpadding="0" cellspacing="0" align="center" valign="top"><tr><td> 
								 <div id="studentDetailsPage"></div></td></tr>
								 <tr><td height="30" colSpan=10></td></tr>
				               <tr><td width="100%" colspan="10" align="right" height="30" style="padding-right:25em;display:none;" id="downloadDiv"><span class='button_white' onClick='exportToPDF()' style='font-size:24px;color:red;'><i class='fa fa-file-pdf-o'></i></span><span style='font-size:24px;color:green;' class='button_white' onClick='getExcel()'><i class='fa fa-file-excel-o'></i></span><span class='button_white' style='font-size:24px;color:black;' onClick="javascript:printPage()"><i class='fa fa-print'></i></span></td></tr>
				               <tr><td width="100%" colspan="10" align="center"><label id="status" style="color: blue;">${hellowAjax}</label></td></tr>
								 </table>
							  </td></tr>
				        	</table> 
                </td></tr>
            </table>
	</body>
</html>
