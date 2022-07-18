<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Literacy Development Results</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student_test_results.js"></script>
<script src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/Adminjs.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/simptip/simptip.css" />
<script type="text/javascript" src="resources/javascript/TeacherJs/rti_results.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeOut(4000);
	});

	function clears(thisvar) {
		if (thisvar.id == 'gradeId') {
			$('#classId').empty();
			$("#classId").append($("<option></option>").val('select').html('Select Subject'));
			$('#studentId').empty();
			$("#studentId").append($("<option></option>").val('select').html('Select Student'));
			clearDiv();
		}
		if (thisvar.id == 'classId' || thisvar.id == 'gradeId') {
			$('#csId').empty();
			$("#csId").append($("<option></option>").val('select').html('Select Section'));
			clearDiv();
		}
		if (thisvar.id == 'csId' || thisvar.id == 'classId' || thisvar.id == 'gradeId') {
			$('#assignedDate').empty();
			$("#assignedDate").append($("<option></option>").val('select').html('Select Date'));
			clearDiv();
		}
		if (thisvar.id == 'assignedDate' || thisvar.id == 'csId' || thisvar.id == 'classId' || thisvar.id == 'gradeId') {
			$('#title').empty();
			$("#title").append($("<option></option>").val('select').html('Select Title'));
			clearDiv();
		}
		if (thisvar.id == 'assignedDate' || thisvar.id == 'csId' || thisvar.id == 'classId' || thisvar.id == 'gradeId' || thisvar.id == 'title') {
			$(document.getElementById('resultsDiv')).empty();
			$("input:radio").attr("checked", false);
			
		}
		
	}
	function getStudentAllRTITestResults(){
		var csId = $('#csId').val();
		var usedFor = document.getElementById("usedFor").value;
		var studentId = 0;
		if(document.getElementById("studentId") != null){
			studentId = document.getElementById("studentId").value;
		}
		if(csId != 'select'){
			$("#loading-div-background").show();
			$.ajax({
				url : "studentRTITestGraded.htm",
				data: "csId="+csId+"&usedFor="+usedFor+"&studentId="+studentId,
				success : function(data) {
					$('#resultsDiv').html(data);
					$("#loading-div-background").hide();
					
				}
			}); 	
		}else{
			return false;
		}
	}
	function getStudentRTITestResult(assignmentId,studentAssignmentId,assignmentTypeId,status,testCount){
		if(status == "pending"){
			alert("The test is not submitted yet.");
			return;
		}
		var usedFor=$('#usedFor').val();
		var studentId=$('#studentId').val();
		$("#StuAssessQuestionsList").empty();
		$("#getCompletedTestQuestions").html("");
		var tab=$('#tab').val();
		if(assignmentTypeId == 13){
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",				
				url : "gradePerformanceTest.htm",
				data : "studentAssignmentId=" + studentAssignmentId + "&assignmentTypeId="+assignmentTypeId + "&tab="+tab,
				async: true,
				success : function(response) {
					var dailogContainer = $(document.getElementById('performanceDailog'));
					var screenWidth = $( window ).width() - 10;
					var screenHeight = $( window ).height() - 10;
					$('#performanceDailog').empty();  
					$(dailogContainer).append(response);
					$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,
						open:function () {
							$(".ui-dialog-titlebar-close").show();
						},
						close: function(event, ui){
							$(this).empty();
							getStudentsTestResults();
					    }	
					});		
					$(dailogContainer).dialog("option", "title", "Performance Test Result");
					$(dailogContainer).scrollTop("0");
					$("#loading-div-background").hide();
				}
			});
		}else{
			$.ajax({
				type : "GET",	
				url : "getCompletedTestQuestions.htm",
				data : "studentAssignmentId=" + studentAssignmentId + "&usedFor="+usedFor +"&assignmentTypeId="+assignmentTypeId+"&assignmentId="+assignmentId+"&tab="+ tab,
				success : function(response) {
					var dailogContainer = $(document.getElementById('getCompletedTestQuestions'));
						var screenWidth = $( window ).width() - 10;
						var screenHeight = $( window ).height() - 10;
						$('#getCompletedTestQuestions').empty();  
						$(dailogContainer).append(response);
						$(dailogContainer).dialog({width: screenWidth, height: screenHeight, modal: true, dialogClass: 'myTitleClass', open:function () {
							  $(".ui-dialog-titlebar-close").show();
						},
						close: function(event, ui){
							$(this).dialog('destroy');
							$("#getCompletedTestQuestions").html("");
							//getStudentsTestResults();
					    }	
						}
					    
					    );	
						if(assignmentTypeId==14){
							$(dailogContainer).dialog("option", "title", "Spelling");
						}else{
						$(dailogContainer).dialog("option", "title", "Test Results");}
						$(dailogContainer).scrollTop("0");	
						$("#loading-div-background").hide();
			
				}
			});
		}		
	}

 	function clearDiv(){
 		$('#resultsDiv').html("");
 		$('#studentsTestResultsDiv').html("");  
 		$('#getCompletedTestQuestions').html("");  
 	}
	function goToDiv(i){
		  document.getElementById("sho"+i).scrollIntoView(true);
	}
</script>
</head>
<body>
	<div>
		<c:choose>
			<c:when test="${userReg.user.userType == 'admin'}">
			    <script src="resources/javascript/admin/common_dropdown_pull.js"></script>
				<c:set var="getClassFunction" value="getGradeClasses()"/>
			</c:when>
			<c:otherwise>
			    <script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
				<c:set var="getClassFunction" value="getGradeClasses()"/>
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
								<select name="gradeId" class="listmenu" id="gradeId" onChange="clears(this);clearDiv();${getClassFunction}" required="required">
									<option value="">select grade</option>
									<c:forEach items="${grades}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
								</select>
							</td>
							<td align="left" class="label" style="width: 12em;padding-left: 2em;">Class &nbsp;&nbsp;&nbsp;
									<select	id="classId" name="classId" class="listmenu" onChange="clears(this);clearDiv();getClassSections()" required="required">
										<option value="select">select subject</option>
									</select>	
								<%-- <c:choose>								
									<c:when test="${userReg.user.userType == 'admin'}">
										<select id="classId" name="classId" class="listmenu" onchange="clears(this);getClassSections()" >
											<option value="select">select subject</option>
										</select>										
									</c:when>
									<c:otherwise>
															
									</c:otherwise>								
								</c:choose> --%>
							</td>	
							<td align="left" class="label" style="width: 13em;padding-left: 2em;">Section &nbsp;&nbsp;&nbsp;
								<select id="csId" name="csId" class="listmenu" onChange="clears(this);clearDiv();getStudentsBySection()" required="required">
									<option value="select">select Section</option>
								</select>
							</td>
							<td align="left" class="label" style="width: 15em;padding-left: 2em;">Student &nbsp;&nbsp;&nbsp;
								<select name="studentId" class="listmenu" id="studentId" onChange="clears(this);clearDiv();getAllRTITestResultsByStudent();">
									<option value="select">Select Student</option>
								</select>
							</td>
							<td style="width: 12em;padding-left: 2em;">
							</td>				
							<td align="left" valign="middle">
								<input type="hidden" id="usedFor" value="${usedFor}" />
							</td>
						</tr>
						<tr>
							<td height="10" colspan="15"></td>
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
			<tr><td>&nbsp;</td></tr></table>
							 <table width=100% align=left>
							<tr>
							<tr>
							<td align="left" style="padding-left: 7em;"><div id="studentsTestResultsDiv" style="align:center;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td></tr></table>
							 <table width=100%>
				             <tr>
				             <td align="left" style="padding-left: 10em;">
				             <div id="getCompletedTestQuestions" title="Test Results" style="align:center;"></div></td></tr>
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