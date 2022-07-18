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
<script type="text/javascript" src="resources/javascript/TeacherJs/assign_phonic_skill_test.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/phonic_test_reports.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
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
.radio-design:checked + .radio-label-design:before {
    font-size: 1.5em;
}
</style>
<script type="text/javascript">
	function clearDiv(thisvar){
		if(thisvar.id == 'gradeId'){
			$("#csId").empty();
			$("#csId").append($("<option></option>").val('').html('Select Section'));
		}
		$("#studentDetailsDiv").empty();
	}
</script>
<title>Phonic Multi Test Reports</title>
</head>
 <body>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.material.min.css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.dataviz.min.css" />
<script src="resources/javascript/LineChart/kendo.all.min.js"></script>
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
       <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%" style="padding-top: 1em;">                         
                          
                            <tr><td height="30" colspan="3" align="center" valign="middle">
								<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" align="center" class="title-pad">
									<tr class="label">
										<td width="80" align="left">Grade</td>
										<td width="150" align="left" valign="middle">
											<select	class="listmenu" id="gradeId" name="gradeId" onchange="getGradeClasses();clearDiv(this)" style="width:120px;">
													<option value="select">select grade</option>
										 		<c:forEach items="${teacherGrades}" var="ul">
													<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
												</c:forEach> 
											</select>
										</td>
										<td></td>
										<td width="80" align="left">&nbsp;&nbsp;Class</td>
										<td width="150" align="left" valign="middle">
										<select id="classId" name="classId" class="listmenu" style="width:120px;" onchange="getClassSections();clearDiv(this)" >
										 	<option value="select">select subject</option>
										</select></td>
										<td width="80" align="left">Section</td>
										<td width="150" align="left" valign="middle">
										<select id="csId" name="csId" class="listmenu" style="width:120px;" onchange="clearDiv(this)" >
											<option value="select">select section</option> 
										</select></td>
										<td class="label" width="60">Language&nbsp;&nbsp;</td><td width="150">
	 										<select	id="langId" name="langId" onchange="clearDiv(this);getBpstGroups();" style="width:120px;" class="listmenu">
													<option value="select">select language</option>
										 		<c:forEach items="${langList}" var="la">
													<option value="${la.languageId}">${la.language}</option>
												</c:forEach> 
											</select>
										</td>
										<td class="label" width="280" id="bpstDiv" style="display:none;">Group Type &nbsp;&nbsp;
	 										<select	id="bpstTypeId" name="bpstTypeId" onchange="clearDiv(this);getPstStudentDetails();" style="width:120px;" class="listmenu">
											</select>
										</td>
									    <td height=10 colSpan=08>&nbsp;</td>
					                </tr>
				        	</table> 
                      		</td></tr>
                      		<tr><td>&nbsp;</td></tr>
                      		<tr><td width="60" height="20"></td></tr>
                            <tr><td height="30" colspan="2" align="center" valign="middle" class="txtStyle" style="">
                               		<div id="studentDetailsDiv" ></div>
                               		
                            </td></tr>
                            <tr><td height=45 colSpan=30></td></tr>
                            <tr></tr>
                            
 			 </table>
	</body>
</html>
