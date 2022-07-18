<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Literacy Development Results</title>

<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet"
	href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet"
	href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>

<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/Adminjs.js"></script>

<script type="text/javascript">
	function getRTIResults(type) {
		var gradeId = $('#gradeId').val();
		var classId = $('#classId').val();
		var csId = $('#csId').val();
		var assignedDate = $('#assignedDate').val();
		var assignmentId = $('#title').val();
		var tab = $('#tab').val();
		if (gradeId == '') {
			systemMessage('Please select a grade');
			$('#gradeId').focus();
			$('input:radio[name=get]:checked').prop('checked', false)
					.checkboxradio("refresh");
			return false;
		} else if (classId == '') {
			systemMessage('Please select a class');
			$('#classId').focus();
			$('input:radio[name=get]:checked').prop('checked', false)
					.checkboxradio("refresh");
			return false;
		} else if (csId == '') {
			systemMessage('Please select a section');
			$('#csId').focus();
			$('input:radio[name=get]:checked').prop('checked', false)
					.checkboxradio("refresh");
			return false;
		} else if (assignedDate == '') {
			systemMessage('Please select a date');
			$('#assignedDate').focus();
			$('input:radio[name=get]:checked').prop('checked', false)
					.checkboxradio("refresh");
			return false;
		} else if (assignmentId == '' || assignmentId == 'select') {
			systemMessage('Please select a test title');
			$('#title').focus();
			$('input:radio[name=get]:checked').prop('checked', false)
					.checkboxradio("refresh");
			return false;
		} else {
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",
				url : "getRTIResults.htm",
				data : "assignmentId=" + assignmentId + "&type=" + type,
				async : true,
				success : function(response) {
					var dailogContainer = $(document
							.getElementById('resultsDiv'));
					dailogContainer.empty();
					$(dailogContainer).append(response);
					$("#loading-div-background").hide();
				}
			});
		}
	}

	function clears(thisvar) {
		if (thisvar.id == 'gradeId') {
			$('#classId').empty();
			$("#classId").append($("<option></option>").val('select').html('Select Subject'));
			$('#studentId').empty();
			$("#studentId").append($("<option></option>").val('select').html('Select Student'));
		}
		if (thisvar.id == 'classId' || thisvar.id == 'gradeId') {
			$('#csId').empty();
			$("#csId").append($("<option></option>").val('select').html('Select Section'));
		}
		if (thisvar.id == 'csId' || thisvar.id == 'classId' || thisvar.id == 'gradeId') {
			$('#assignedDate').empty();
			$("#assignedDate").append($("<option></option>").val('select').html('Select Date'));
		}
		if (thisvar.id == 'assignedDate' || thisvar.id == 'csId' || thisvar.id == 'classId' || thisvar.id == 'gradeId') {
			$('#title').empty();
			$("#title").append($("<option></option>").val('select').html('Select Title'));
		}
		if (thisvar.id == 'assignedDate' || thisvar.id == 'csId' || thisvar.id == 'classId' || thisvar.id == 'gradeId' || thisvar.id == 'title') {
			$(document.getElementById('resultsDiv')).empty();
			$("input:radio").attr("checked", false);
		}
	}
	function showAssessmentTitles(){
		var usedFor = $('#usedFor').val();
		var csId = $('#csId').val();
		var userType = $('#userType').val();
		var classId = $('#classId').val();
		if(userType == 'admin'){
			classId = $('#gradeClassId').val();
		}
		var assignedDate= $('#assignedDate').val();
		if(assignedDate != 'select' && csId >0 && classId > 0){
			$.ajax({
				type : "GET",
				url : "getRTIResultTitles.htm",
				data : "csId=" + csId + "&usedFor=" + usedFor+"&assignedDate="+assignedDate,
				success : function(response2) {
					var assignmentTitles = response2.assignmentTitles;
					$("#title").empty();
					$("#title").append(
							$("<option></option>").val('select').html('Select Title'));
					$.each(assignmentTitles, function(index, value) {
						$("#title").append(
								$("<option></option>").val(value.assignmentId).html(value.title));
					});
				}
			});
		}else{
			alert("Please select the filters");
		}
	}
</script>
</head>

<body>
	<div>
		<c:choose>
			<c:when test="${userReg.user.userType == 'admin'}">
				<c:set var="getClassFunction" value="getAvailableClasses(0)"/>
			</c:when>
			<c:otherwise>
				<c:set var="getClassFunction" value="getTeacherGradeClass()"/>
			</c:otherwise>
		</c:choose>		
	</div>
	<form:form name="rtiResults">
	
		<table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">
			<tr>
				<td style="color: white; font-weight: bold">
					<input type="hidden" name="tab" id="tab" value="${tab}">
					<input type="hidden" name="userType" id="userType" value="${userReg.user.userType}">
				</td>
				<td vAlign=bottom align=right>
					<div>
						<c:choose>
							<c:when test="${usedFor == 'rti'}">
								<%@ include file="/jsp/assessments/rti_tabs.jsp"%>
							</c:when>
							<c:when test="${usedFor == 'homeworks' && teacherObj != null }">
								<%@ include file="/jsp/assessments/homework_tabs.jsp"%>
							</c:when>
							<c:otherwise>
								<%@ include file="/jsp/curriculum/curriculum_tabs.jsp"%>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="left" valign="left" width="100%" class="heading-up">
					<table align="left" valign="left" width="100%" style="padding-left: 8em;" border=0 cellPadding=0 cellSpacing=0  class="title-pad heading-up heading-up">
						<tr>
							<td height="20" align="left" class="label" style="width: 12em;">Grade &nbsp;&nbsp;&nbsp;
								<select name="gradeId" class="listmenu" id="gradeId" onChange="clears(this);${getClassFunction}" required="required">
									<option value="">select grade</option>
									<c:forEach items="${grades}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
								</select>
							</td>
							<td align="left" class="label" style="width: 12em;padding-left: 2em;">Class &nbsp;&nbsp;&nbsp;
								<c:choose>								
									<c:when test="${userReg.user.userType == 'admin'}">
										<select id="gradeClassId" name="gradeClassId" class="listmenu" onchange="clears(this);getScheduledClasses()" >
											<option value="select">select subject</option>
										</select>										
									</c:when>
									<c:otherwise>
										<select	id="classId" name="classId" class="listmenu" onChange="clears(this);loadTeacherSections()" required="required">
											<option value="">select subject</option>
										</select>						
									</c:otherwise>								
								</c:choose>
							</td>	
							<td align="left" class="label" style="width: 13em;padding-left: 2em;">Section &nbsp;&nbsp;&nbsp;
								<select id="csId" class="listmenu" onChange="clears(this);loadDatesforRTIResults()" required="required">
									<option value="">select Section</option>
								</select>
							</td>
							<td align="left" class="label" style="width: 12em;padding-left: 2em;">Date &nbsp;&nbsp;&nbsp;
								<select name="assignedDate" class="listmenu" id="assignedDate" onChange="clears(this);showAssessmentTitles()">
									<option value="">Select Date</option>
								</select>
							</td>
							<td align="left" class="label" style="padding-left: 2em;">Title &nbsp;&nbsp;&nbsp;
								<select name="title" class="listmenu" id="title" onchange="clears(this)">
									<option value="">Select Title</option>
								</select>
							</td>							
							<td align="left" valign="middle">
								<input type="hidden" id="usedFor" value="${usedFor}" />
							</td>
						</tr>
						<tr>
							<td height="10" colspan="15"></td>
						</tr>
						<tr>
							<td colspan="15" style="padding-left: 30%;padding-top: 1em;padding-bottom: 1em;" class="txtStyle">
								<input type="radio" class="radio-design" name="get" id="get1" onclick="getRTIResults('student');"><label for="get1" class="radio-label-design">Get Individual Student Results</label>&nbsp;&nbsp; 
								<input type="radio" class="radio-design" name="get" id="get2" onclick="getRTIResults('group');"><label for="get2" class="radio-label-design">Get Group Results </label>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="2" colspan="2"></td>
			</tr>
			<tr>
				<td colspan="2" align="center" valign="top" id="resultsDiv"></td>
			</tr>
			<tr>
				<td id="appenddiv2" height="30" colspan="5" align="center"
					valign="middle" style="padding-left: 4cm;"><font color="blue"><label
						id=returnMessage style="visibility: visible;">${helloAjax}</label></font>
				</td>
			</tr>
			<tr>
				<td height="10" colspan="2" id="errorMessage" align="center">
					<font color="red"> ${errorMessage}</font>
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>