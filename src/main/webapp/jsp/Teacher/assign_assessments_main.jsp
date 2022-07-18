<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Assign Assessments</title>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link
	href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css"
	rel="stylesheet" type="text/css" />
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
<link
	href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<!-- <link href="resources/css/style2.css" rel="stylesheet" type="text/css" /> -->
<script type="text/javascript">

var lastHeight = $('#body').outerHeight();
	$('#body').bind("DOMSubtreeModified",function(){
		currentHeight = $('#body').outerHeight();
		var height = $(document).height();
		var changedHeight = (currentHeight-lastHeight);
		if(lastHeight != currentHeight){
			
			lastHeight = currentHeight;
		}
	});
	
	$(document).ready(function () {
		 $('#returnMessage').fadeOut(3000);
		 $('#status').fadeOut(4000);
	     $( "#enddate" ).datepicker({
	    	changeMonth: true,
	        changeYear: true,
	        showAnim : 'clip',
	        minDate: 0
	     });
	});
	function getStudentsAndAssignmentTypes() {
		var usedFor = $('#usedFor').val();
		var isGroup = $('#isGroup').val();		
		var lessonId = $('#lessonId').val();
		$("#questionList").empty();
		if(lessonId == ''){
			document.getElementById('assignasssessments').style.visibility = "hidden";
			document.getElementById('reTestDiv').style.display = 'none';
			return false;
		}
		
		var csId = $('#csId').val();
		$("#loading-div-background").show();
		$.ajax({
					type : "GET",
					url : "getStudentsList.htm",
					data : "csId=" + csId + "&usedFor=" + usedFor,
					success : function(response) {
						var studentList = response.studentList;
						var rtiList = response.rtiList;
						var perGroupList = response.perGroupList;
						$("#studentId").empty();
						$.each(studentList,
							function(index, value) {
								var studentName = value.student.userRegistration.firstName
													+ " "
													+ value.student.userRegistration.lastName;
								$("#studentId").append(
									$("<option></option>").val(
											value.student.studentId).html(studentName));
										});
						$("#rtiGroupId").empty();
						$.each(rtiList, function(index, value) {
							$("#rtiGroupId").append(
									$("<option></option>")
											.val(value.rtiGroupId).html(
													value.rtiGroupName));
						});
						$("#perGroupId").empty();
						$.each(perGroupList, function(index, value) {
							$("#perGroupId").append(
									$("<option></option>")
											.val(value.performanceGroupId).html(value.groupName));
						});
						$.ajax({
									type : "GET",
									url : "getAssignmentTypes.htm",
									data : "csId=" + csId + "&usedFor="
											+ usedFor,
									success : function(response1) {
										var assignmentTypes = response1.assignmentTypes;
										$("#assignmentType").empty();
										$("#assignmentType")
												.append(
														$("<option></option>").val('0').html('Select AssignmentType'));
										$.each(assignmentTypes,
											function(index, value) {
															if(isGroup=="true" && value.assignmentTypeId==13){
																$("#assignmentType").append(
																		$("<option></option>").val(value.assignmentTypeId).html(value.assignmentType));
															}
															if(isGroup!="true"){
															$("#assignmentType").append(
																$("<option></option>").val(value.assignmentTypeId).html(value.assignmentType));
															}
											});
										if (usedFor == 'rti') {
											document.getElementById('getrtigroups').style.visibility = "visible";
										}
										document.getElementById('assignasssessments').style.visibility = "visible";
										if (usedFor == 'rti' || isGroup == "true") {
											document.getElementById("reTestDiv").style.display = 'none';
										} else {
											document.getElementById("reTestDiv").style.display = '';
										}
										$("#loading-div-background").hide();
									}
								});
					}
				});
		
	}

	function changeAssignmentType() {
		var assignmentType = $('#assignmentType').val();
		var gradeId=$('#gradeId').val();
		if(assignmentType!=8 && assignmentType!=20 && assignmentType!=19){
		var lessonId = $('#lessonId').val();
		if(lessonId == ""){
			alert("Please select a lesson");
			return false;
		}
		}else{
		var lessonId=-1;	
		}
		var usedFor = $('#usedFor').val();
		var csId = $('#csId').val();
		var tab=$('#tab').val();
		$("#questionList").empty();
		if(assignmentType == ""){
			return false;
		}
		var endDate = $('#enddate').val();
		if(endDate != ''){
			var endD = new Date(endDate);
			var curD = new Date();
			if(endD.getTime() < curD.getTime()){
				if(endD.getFullYear() === curD.getFullYear()
			    	&& endD.getDate() === curD.getDate()
			    	&& endD.getMonth() === curD.getMonth()){
					//do nothing
				}else{
					systemMessage("End date should not be greater than today");
					//document.getElementById('enddate').value = "";
					$('#enddate').focus();
					return;
				}
			}
		}
		
		if(assignmentType == 13 || usedFor == 'rti' || document.getElementById('assignasssessments').style.visibility != "visible"){
			document.getElementById("reTestDiv").style.display = 'none';
		}else{
			document.getElementById("reTestDiv").style.display = '';
		}			
		$("#loading-div-background").show();
		console.log("lessonId="+lessonId+"assignmentTypeId="+assignmentType+"&userFor="+usedFor);
		if(lessonId!="select"){
			console.log("without lesson");
		$.ajax({
			type : "GET",
			url : "getQuestions.htm",
			data : "lessonId=" + lessonId + "&assignmentTypeId="
					+ assignmentType + "&usedFor=" + usedFor+"&gradeId="+gradeId+"&tab="+tab,
			success : function(response) {
				$("#questionList").html(response);
				if(assignmentType == 8){
					$("#videoDiv").show();
				}else{
					$("#videoDiv").hide();
				}
				$.ajax({
					type : "GET",
					url : "getPreviousTestDates.htm",
					data : "csId=" + csId + "&usedFor=" + usedFor
							+ "&assignmentTypeId=" + assignmentType,
					success : function(response1) {
						var previousDates = response1.previousDates;
						$("#reTestId").empty();
						$("#reTestId").append(
								$("<option></option>").val('').html('Select Title'));
						$.each(previousDates, function(index, value) {
							$("#reTestId").append(
									$("<option></option>").val(value.assignmentId)
											.html(value.title));
						});
					}
				});					
				$("#loading-div-background").hide();
			}
		});	
		}else{
			document.getElementById('assignasssessments').style.visibility = "hidden";
			document.getElementById('reTestDiv').style.display = 'none';
		}
		smoothScroll(document.getElementById('questionList'));
	}

	function getRTIGroups() {
		document.getElementById('rtiDiv').style.display = '';
		document.getElementById('studentDiv').style.display = 'none';
		$('#studentId').attr("disabled", true);
		$('#rtiGroupId').attr("disabled", false);

	}
	function getStudents() {
		document.getElementById('rtiDiv').style.display = 'none';
		if(document.getElementById('studentDiv'))
			document.getElementById('studentDiv').style.display = '';
		$('#studentId').attr("disabled", false);
		$('#rtiGroupId').attr("disabled", true);

		
	}
	function selectAllQuestion(thisVar) {
		if (thisVar.checked == true) {
			for (var k = 0; k <= thisVar.value; k++) {
				document.getElementById('questions' + k).checked = true;
			}
		} else {
			for (k = 0; k <= thisVar.value; k++) {
				document.getElementById('questions' + k).checked = false;
			}
		}
	}
	function showHide(val) {
		var divObject = document.getElementById("divid:" + val);
		if (divObject.style.display == "") {
			divObject.style.display = "none";
			document.getElementById("anchorid:" + val).innerHTML = "+";
		} else {
			divObject.style.display = "";
			document.getElementById("anchorid:" + val).innerHTML = "-";

		}
	}
	
	function validateThisForm(count){
		var status = false;
		 var isvalidate = $("#assignAssessmentsForm")[0].checkValidity();
		 if (!isvalidate){     
        	alert("Please fill all mandatory fields (*)");
        	return false;
		 }else{
			var assignmentTypeId = document.getElementById("assignmentTypeId");
			if(assignmentTypeId.value == 7 || assignmentTypeId.value == 19){
				var questions = document.getElementsByName("ssQuestions");
				for(var i = 0; i < questions.length; i++) {
					status = questions[i].checked;
					if(status == true){
						break;
					}				
				}
			}else{
				var questions = document.getElementsByName("questions");
				for(var i = 0; i < questions.length; i++) {
					status = questions[i].checked;
					if(status == true){
						break;
					}				
				}
			}		
			if(status == false){
				alert("Please select a question");
				return false;
			}else{
				if(assignmentTypeId.value==8 ){
					benchmarkId=document.getElementById("benchmarkId").value;
					if(benchmarkId=="select" || benchmarkId==null || benchmarkId==""){
						alert("Please select benchmark type");
				    	status=false;
						return false;
			        }
					benchmarkDirectionsId=document.getElementById("benchmarkDirectionsId").value;
					if(benchmarkDirectionsId=="select" || benchmarkDirectionsId==""){
						alert("Please select benchmark direction");
						status=false;
				    	return false;
				    }
				}
			}
			if(status==true)
				return true;
		 }
	}
	
	
	function clrAssTy(){
		$("#assignmentType").empty();
		$("#assignmentType").append(
				$("<option></option>").val('').html('Select assignmentType'));
	}
	function clearDiv(){
		$("#lessonId").empty();
		$("#lessonId").append(
				$("<option></option>").val('').html('Select Lesson'));
		document.getElementById('getrtigroups').style.visibility = "hidden";
		document.getElementById('assignasssessments').style.visibility = "hidden";
		document.getElementById("reTestDiv").style.display = 'none';
		$("#questionList").empty();
	}
	
	function checkBenchTitleExist()
	{
		alert("checkbTitle");
		var csId = $('#csId').val();
		var usedFor = $('#usedFor').val();
		benchmarkId = $("#benchmarkId").val();
		if(benchmarkId != 1 && benchmarkId!=2 && benchmarkId!=3 && !benchmarkId!=4){
			return false;
		}   
		var selected=[];
		 $('#studentId :selected').each(function(){
			 selected.push($(this).val());
		    });
		 
		 $.ajax({
				type : "POST",
				url : "checkBenchTitleExists.htm",
				data : "csId=" + csId + "&benchmarkId="+ benchmarkId + "&usedFor="+usedFor+"&students="+selected,
				success : function(response) {
					if(response==true){	
						if(confirm("This test has already been assigned. Do you want to assign the test to the new students?",function(status){
							if(!status){
							$('#benchmarkId').val('select');
					 	}
					})); 
					}
				}
			});
	}	

	function assignAssessmentsToStudents(count){
		var status = validateThisForm(count);
		if(status){
			$("#loading-div-background").show();
			var formObj = document.getElementById("assignAssessmentsForm");
			var formData = new FormData(formObj);
			$.ajax({
				url: 'assignAssessmentsToStudents.htm',
				type: 'POST',
				data: formData,
				mimeType:"multipart/form-data",
				contentType: false,
				cache: false,
				processData:false,
				success: function(data){
					
					$("#loading-div-background").hide();
					var tab = '${tab}';
					if(data == 'Assignment Assigned Successfully')
						systemMessage(data);
					else{
						systemMessage("Please fill all mandatory fields (*)");
						return false;
					}
					setTimeout(function() {
						if(tab == 'assignRti')
						 	window.location = "assignRti.htm";
						else if(tab == 'assignHome')
							window.location = "assignHomeworks.htm";
						else if(tab == 'assignAssessments')
							window.location = "assignAssessments.htm";
						else if(tab == 'groupPerformance')
							window.location = "groupPerformanceAssign.htm";
					}, 1500);
				}
			});	
		}
	}
</script>
</head>
<body>
	<table width="100%" height="100%" border=0 align="center" cellPadding=0
		cellSpacing=0>
		<tr>
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
	</table>
	<form:form id="assignAssessmentsForm" name="assignAssessmentsForm"
		action="assignAssessmentsToStudents.htm" modelAttribute="assignment"
		method="post">
		<table width="100%" height="100%" border=0 align="center"
			cellPadding=0 cellSpacing=0>
			<tr>
				<td>${type} <input type="hidden" id="tab" name="tab"
					value="${tab}" /> <input type="hidden" id="subTab" name="subTab"
					value="${subTab}" /> <input type="hidden" id="isGroup"
					name="isGroup" value="${isGroup}" /> <input type="hidden"
					id="teacherObj" value="${teacherObj}" /> <input type="hidden"
					id="lastSelectedAssignTypeId" name="lastSelectedAssignTypeId"
					value="0" />
				</td>
			</tr>
			<tr>
				<td height="30" align="left" width="100%" class="heading-up">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						align="left" class="title-pad heading-up heading-up">
						<tr>
							<td height="20" width="210px" valign="middle" class="label">Grade
								&nbsp;&nbsp;&nbsp; <select name="gradeId" class="listmenu"
								id="gradeId" onChange="clearDiv();clrAssTy();getGradeClasses()"
								required="required">
									<option value="select">select grades</option>
									<c:forEach items="${teacherGrades}" var="ul">
										<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
									</c:forEach>
							</select>

							</td>
							<td height="20" width="210px" valign="middle" class="label">Class
								&nbsp;&nbsp;&nbsp; <select id="classId" name="classId"
								class="listmenu" onChange="clearDiv();clrAssTy();getClassSections()"
								required="required">
									<option value="select">select subject</option>
							</select>
							</td>
							<td height="20" width="220px" valign="middle" class="label">Section
								&nbsp;&nbsp;&nbsp; <form:select id="csId"
									path="classStatus.csId" class="listmenu"
									onChange="clearDiv();getAllAssignmentTypes()"
									required="required">
									<option value="select">select Section</option>
								</form:select>
							</td>

							<td height="20" width="310px" valign="middle" class="label">Assignment
								Type &nbsp;&nbsp;&nbsp; <form:select
									path="assignmentType.assignmentTypeId" id="assignmentType"
									onChange="clearDiv();getLessonsBycsId();" required="required">
									<option value="0">Select AssignmentType</option>
								</form:select>
							</td>
							<td height="20" width="230px" valign="middle" class="label"
								id="showAssLessons">Lesson &nbsp;&nbsp;&nbsp; <form:select
									class="listmenu" id="lessonId" path="lesson.lessonId"
									onChange="getAllAssignmentsByAssignmentTypes();changeAssignmentType()"
									required="required">
									<option value="select">Select Lesson</option>
								</form:select>
							</td>
							<td height="20" class="label" width="150px" align="left"></td>
							<td align="center" style="width: 4em;">
								<div id="backBtn"
									style="display: none; cursor: hand; cursor: pointer;"
									onclick="backToLessonsTab()">
									<table>
										<tr>
											<td height="20" class="label"
												style="width: 12em; font-family: Candara, Calibri, Segoe, Segoe UI, Optima, Arial, sans-serif;"
												align="left"><i class="fa fa-arrow-left"
												aria-hidden="true"></i> Back</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>

					</table>
				</td>
			</tr>
			 <tr>
				<td><c:if test="${usedFor == 'rti'}">
						<!-- <div id="videoDiv">
							<a href="#"
								onClick="openVideoDialog('teacher','assign_fluency','videoDailog')"
								class="demoVideoLink"><i class="ion-videocamera"
								aria-hidden="true"
								style="font-size: 26px; font-weight: bold; color: #e23c00;"></i>&nbsp;video
								for Assign Fluency</a>
						</div> -->
					</c:if></td>
				<td width="80%" colspan="10" align="center"><label id="status"
					style="color: blue;">${hellowAjax}</label></td>
			</tr> 

			<tr>
				<td id="appenddiv2" colspan="7" align="center" valign="middle"
					style="padding: 0.5em;"><font color="blue"> <label
						id="returnMessage" style="visibility: visible;">${helloAjax}</label>
				</font></td>
			</tr>
		</table>

		<table align="center" width="15%" id="getrtigroups"
			style="visibility: hidden">
			<tr>
				<td style="padding-left: 2em;" class="label"><input
					type="radio" name="type" id="type0" value="groups"
					onclick="getRTIGroups();" class="radio-design"><label
					for="type0" class="radio-label-design">Get Literacy Groups</label>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td style="padding-left: 2em;" class="label"><input
					type="radio" name="type" id="type1" value="students"
					checked="checked" onclick="getStudents();" class="radio-design"><label
					for="type1" class="radio-label-design">Get Students</label></td>
			</tr>
		</table>
		<table id="assignasssessments" style="visibility: hidden;" class="des"
			border=0 align="center" width="40%">
			<tr>
				<td colspan="2" align="center" valign="top">
					<div class="Divheads">
						<table>
							<tr>
								<td><c:choose>
										<c:when test="${usedFor == 'rti'}">
       										Assign Literacy
    									</c:when>
										<c:when
											test="${usedFor == 'homeworks' && teacherObj != null }">
       										Assign Homework
    									</c:when>
										<c:otherwise>
											Assign Assessments
										</c:otherwise>
									</c:choose></td>
							</tr>
						</table>
					</div>
					<div class="DivContents" align="center">
						<table>

							<tr>
								<td width="125" height="30" align="right" valign="middle"
									class="tabtxt"></td>
								<td width="10" height="30" align="center" valign="middle"></td>
								<td height="30" align="left" valign="middle" class="tabtxt"></td>

							</tr>
							<c:choose>
								<c:when test="${isGroup==true}">
									<tr id="groupDiv">

										<td width="125" height="30" align="right" valign="middle"
											class="tabtxt"><img src="images/Common/required.gif">
											Select group</td>
										<td width="10" height="30" align="center" valign="middle"
											style="padding-left: 0.5em; padding-right: 0.5em;">:</td>
										<td height="30" align="left" valign="middle" class="tabtxt"><select
											name="perGroupId" id="perGroupId" multiple="multiple"
											required="required"
											style="width: 200px; overflow-y: visible; height: 50px;">
										</select></td>

									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="3" style="display:" id="studentDiv">
											<table>
												<tr>
													<td width="125" height="30" align="right" valign="middle"
														class="tabtxt"><img src="images/Common/required.gif">Students</td>
													<td width="10" height="30" align="center" valign="middle"
														style="padding-left: 0.5em; padding-right: 0.5em;">:</td>
													<td height="30" align="left" valign="middle" class="tabtxt">
														<select name="studentId" id="studentId"
														multiple="multiple"
														style="overflow-y: visible; height: 100px;"
														onclick="selectOption(this,'')" required="required">
													</select>
													</td>
												</tr>
												<tr>
													<td width="125" height="30" align="right" valign="middle"
														class="tabtxt"></td>
													<td width="10" height="30" align="center" valign="middle"
														style="padding-left: 0.5em; padding-right: 0.5em;"></td>
													<td class="tabtxt"><input type="checkbox"
														class="checkbox-design" id="select_all" name="select_all"
														onClick="selectAllOptions(this, 'studentId', '')"><label
														for="select_all" class="checkbox-label-design">Select
															All</label></td>
												</tr>
											</table>
										</td>
									</tr>
								</c:otherwise>
							</c:choose>
							<tr>
								<td colspan="3" style="display: none;" id="rtiDiv">
									<table>
										<tr>

											<td width="125" height="30" align="right" valign="middle"
												class="tabtxt"><img src="images/Common/required.gif">
												Select Literacy Group</td>
											<td width="10" height="30" align="center" valign="middle"
												style="padding-left: 0.5em; padding-right: 0.5em;">:</td>
											<td height="30" align="left" valign="middle" class="tabtxt"><select
												name="rtiGroupId" id="rtiGroupId">
											</select></td>
										</tr>
									</table>
								</td>

							</tr>
							<tr>
								<td width="125" height="30" align="right" valign="middle"
									class="tabtxt"><img src="images/Common/required.gif">
									Title</td>
								<td width="10" height="30" align="center" valign="middle"
									style="padding-left: 0.5em; padding-right: 0.5em;">:</td>
								<td height="30" align="left" valign="middle" class="tabtxt"><form:input
										path="title" id="title" required="required"
										onblur="checkTitle();" /></td>

							</tr>

							<tr>
								<td width="125" height="30" align="right" valign="middle"
									class="tabtxt"><img src="images/Common/required.gif">
									Instruction</td>
								<td width="10" height="30" align="center" valign="middle"
									style="padding-left: 0.5em; padding-right: 0.5em;">:</td>
								<td height="30" align="left" valign="middle" class="tabtxt"><form:textarea
										rows="2" cols="20" path="instructions" id="instruct"
										required="required"></form:textarea></td>

							</tr>
							<tr>


								<td width="125" height="30" align="right" valign="middle"
									class="tabtxt"><img src="images/Common/required.gif">
									EndDate</td>
								<td width="10" height="30" align="center" valign="middle"
									style="padding-left: 0.5em; padding-right: 0.5em;">:</td>
								<td height="30" align="left" valign="middle" class="tabtxt"><form:input
										path="dateDue" id="enddate" size="15" maxlength="15" value=""
										required="required" readonly="true" /></td>

							</tr>
							<%-- <tr>
					<td width="125" height="30" align="right" valign="middle"
						class="tabtxt"><img src="images/Common/required.gif"> AssignmentType</td>
					<td width="10" height="30" align="center" valign="middle"
						style="padding-left: 0.5em;padding-right: 0.5em;">:</td>
					<td height="30" align="left" valign="middle" class="tabtxt"><span
						class="menu"> <form:select
								path="assignmentType.assignmentTypeId" id="assignmentType"
								onChange="changeAssignmentType();"
								required="required">
								<option value="0">Select AssignmentType</option>
							</form:select>
					</span></td>

				</tr> --%>
						</table>
						<table id="reTestDiv" style="display: none; width: 100%;"
							align="left">
							<tr>
								<td height="30" align="right" valign="middle" class="tabtxt">Take
									this test as retest on</td>
								<td width="10" height="30" align="center" valign="middle"
									style="padding-left: 0.5em; padding-right: 0.5em;">:</td>
								<td height="30" align="left" valign="middle" class="tabtxt"
									style="padding-right: 4.0em"><span class="menu"> <select
										name="reTestId" id="reTestId">
											<option value="0" selected>Select Title</option>
									</select>
								</span></td>
							</tr>
						</table>
						<table>
							<tr>
								<td colspan="7" height="20" align="center" valign="middle"
									class="tabtxt"><form:hidden path="usedFor" id="usedFor" />
								</td>
							</tr>
							<tr>
								<td colspan="7" id="questionList"></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td><div id="videoDailog" title="Assign Fluency"
						style="background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td>
			</tr>
		</table>
	</form:form>
</body>
</html>