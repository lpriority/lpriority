<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center" style="vertical-align: middle;" id="dailog">
<html>
<head>
<title>View Assessments</title>
<style type="text/css">
</style>

 <script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/benchmark_recorder.js"></script>
<script src="resources/javascript/VoiceRecorder/recorder.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/Student/early_literacy_tests.js"></script>
<script type="text/javascript" src="resources/javascript/Student/phonic_skill_tests.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/earlyLiteracyTestsService.js"></script>
<script type="text/javascript" src="/dwr/interface/assignPhonicSkillTestService.js"></script>
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
<link href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css"
	rel="stylesheet" type="text/css" />
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/math_assessment.js"></script>
<style>
.myTitleClass .ui-dialog-titlebar {
	background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00e3ff), color-stop(1,#0ea8bd) );
	height:35px;
	font-weight:900;
 	font-size:15px;
 	font-color:red;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeOut(3000);
		var usedFor = $("#usedFor").val();
		var userType = $('#userType').val();
		if (userType != 'parent' && usedFor == 'homeworks') {
			getTests(document.getElementById("csId"));
		}
	});
	function getTestQuestions(testCount, assignmentId, assignmentType) {
		var usedFor = $("#usedFor" + testCount).val();
		var studentAssignmentId = $("#studentAssignmentId" + testCount).val();
		var assignmentTypeId = $("#assignmentTypeId" + testCount).val();
		var createdBy = $("#createdBy" + testCount).val();
		var recordTime = $("#recordTime" + testCount).val();
		var eltTypeId = $("#eltTypeId" + testCount).val();
		var tab = $("#tab").val();
		$("#testQuestionsDiv").empty();
		if (assignmentTypeId == 13) {
			$("#loading-div-background").show();		   	
			$.ajax({
				type : "GET",				
				url : "getPerformanceTest.htm",
				data : "studentAssignmentId="+studentAssignmentId+"&assignmentTypeId=" + assignmentTypeId+"&createdBy=" + createdBy,
				async: true,
				success : function(response) {
					var screenWidth = $( window ).width() - 10;
					var screenHeight = $( window ).height() - 10;
					$('#performanceDailog').empty();  
					$("#performanceDailog").html(response);
					$("#performanceDailog").dialog({width: screenWidth, height: screenHeight,modal: true,
						open:function () {
							$(".ui-dialog-titlebar-close").show();
						},
						close: function(event, ui){
							$(this).empty();  
							$("#performanceDailog").dialog('destroy');
					    }
					});		
					$("#performanceDailog").dialog("option", "title", "Performance Test");
					$("#performanceDailog").scrollTop("0");
					 $("#loading-div-background").hide();
				}
			});
		} else if (assignmentTypeId == 15 || assignmentTypeId == 16) {
			goForAssignment('dialog' + testCount, studentAssignmentId,
					testCount, assignmentId, recordTime, eltTypeId);
		} else if (assignmentTypeId == 17) {
			goForPhonicSkillTest('dialog' + testCount, studentAssignmentId,
					testCount, assignmentId);
		}
		else if (assignmentTypeId == 21) {
		
			goForMathAssessmentTest('dialog' + testCount, studentAssignmentId,
					testCount);
			
		}else if(assignmentTypeId==30){
			$("#loading-div-background").show();
			$.ajax({
	 			type : "GET",				
	 			url : "startGame.htm",
	 			data : "studentAssignmentId=" + studentAssignmentId,
	 			async: true,
	 			success : function(response) {
	 				document.getElementById('mathGameAssignRow').value = testCount;
					var screenWidth = $( window ).width() - 10;
					var screenHeight = $( window ).height();
					$('#mathGameDiv').empty();  
					$("#mathGameDiv").html(response);
					$("#mathGameDiv").dialog({width: screenWidth, height: screenHeight,modal: true,
						open:function (event, ui) {
							$(".ui-dialog-titlebar-close").show();
							$(event.target).dialog('widget').css({ position: 'fixed' }).position({ my: 'center', at: 'center', of: window });
						},
						close: function(event, ui){
							$(this).empty();  
							$(this).dialog('destroy');
					    }
					});		
					$("#mathGameDiv").dialog("option", "title", "Gear Game");
					$("#mathGameDiv").scrollTop("0");
	 				$("#loading-div-background").hide();
	 			}
	 		});
		}
		else {
			if (assignmentTypeId == 8 || assignmentTypeId == 14) {
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getTestQuestions.htm",
					data : "studentAssignmentId=" + studentAssignmentId
							+ "&usedFor=" + usedFor + "&assignmentTypeId="
							+ assignmentTypeId + "&tab=" + tab + "&testCount="
							+ testCount + "&assignmentId=" + assignmentId,
					success : function(response) {
						var dailogContainer = $(document
								.getElementById('benchmarkDailog'));
						var screenWidth = $(window).width() - 10;
						var screenHeight = $(window).height() - 10;
						$('#benchmarkDailog').empty();
						$(dailogContainer).append(response);
						$(dailogContainer).dialog({
							width : screenWidth,
							height : screenHeight,
							modal : true,
							open : function() {
								$(".ui-dialog-titlebar-close").show();
							},
							close: function(event, ui){
								$(this).empty();  
								$(this).dialog('destroy');
						    }
						});
						$(dailogContainer).dialog("option", "title",
								assignmentType);
						$(dailogContainer).scrollTop("0");
						$("#loading-div-background").hide();
					}
				});

			} else {
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getTestQuestions.htm",
					data : "studentAssignmentId=" + studentAssignmentId
							+ "&usedFor=" + usedFor + "&assignmentTypeId="
							+ assignmentTypeId + "&tab=" + tab + "&testCount="
							+ testCount + "&assignmentId=" + assignmentId,
					success : function(response) {
						var dailogContainer = $(document
								.getElementById('autoGradeTest'));
						var screenWidth = $(window).width() - 10;
						var screenHeight = $(window).height() - 10;
						$('#autoGradeTest').empty();
						$(dailogContainer).append(response);
						$(dailogContainer).dialog({
							width : 1300,
							height : 700,
							draggable: true,
							position: {my: "center", at: "center", of:window ,within: $("body") },
							resizable : true,
							modal : true,
							open : function() {
								$(".ui-dialog-titlebar-close").show();
							},
							close: function(event, ui){
								$(this).empty();  
								$(this).dialog('destroy');
						    },
						    dialogClass: 'myTitleClass'
						});
						$(dailogContainer).dialog("option", "title",
								assignmentType);
						$(dailogContainer).scrollTop("0");
						$("#loading-div-background").hide();
					}
				});
			}
		}
	}

	function validateSubmitForm(loopCnt) {
		var allFilled = true;
		//var formData = new FormData(document.querySelector("questionsForm"));
		for (i = 0; i < loopCnt; i++) {
			var ele = document.getElementsByName("assignmentQuestions[" + i
					+ "].answer")[0];
			if (ele.type == "radio") {
				var isChecked = $(
						'[name="assignmentQuestions[' + i + '].answer"]').is(
						':checked');
				if (!isChecked){
					allFilled = false;
					break;
				}
			} else if (ele.type == "text") {
				if (ele.value === ''){
					allFilled = false;
					break;
				}
			}else if (ele.type == "textarea") {
				if (ele.value === ''){
					allFilled = false;
					break;
				}
			}
		}
		if (allFilled) {
			document.getElementById('operation').value = "submit";
			document.getElementById('selectedCsId').value = $('#csId').val();
			return true;
		} else {
			alert("Please answer all the questions");
			return false;
		}
	}
	
	function submitForm(loopCnt,testCount){
		var status = validateSubmitForm(loopCnt);
		if(status){
			$("#loading-div-background").show();
			var formObj = document.getElementById("questionsForm");
			var formData = new FormData(formObj);
			$.ajax({
				url: 'submitTest.htm',
				type: 'POST',
				data: formData,
				mimeType:"multipart/form-data",
				contentType: false,
				cache: false,
				processData:false,
				success: function(data){
					$("#loading-div-background").hide();
					$('#row'+testCount).remove();
					$(".ui-dialog-content").dialog("close");
					setFooterHeight();
					//systemMessage(data);
					swal(data);
				}
			});	
		}
	}

	function saveForm(){
		validateSaveForm();
	}
	function validateSaveForm() {
		document.getElementById('operation').value = "save";
		document.getElementById('selectedCsId').value = $('#csId').val();
	}

	function clearForm(loopCnt) {
		var formData = new FormData(document.querySelector("questionsForm"));
		for (i = 0; i < loopCnt; i++) {
			var ele = document.getElementsByName("assignmentQuestions[" + i
					+ "].answer")[0];
			if (ele.type == "radio") {
				var isChecked = $(
						'[name="assignmentQuestions[' + i + '].answer"]').is(
						':checked');
				if (isChecked)
					$('input[name="assignmentQuestions[' + i + '].answer"]')
							.attr("checked", false);
			} else if (ele.type == "text") {
				if (ele.value != '') {
					document.getElementById(ele.id).value = "";
				}
			}
		}
	}
	function getTests(thisvar) {
		var usedFor = $("#usedFor").val();
		var userType = $('#userType').val();
		var tab = $('#tab').val();
		var studentId = $('#studentId').val();
		$("#studentTestDiv").html("");
		$("#testQuestionsDiv").empty();
		if (userType == 'parent' && usedFor == 'homeworks') {
			var csId = $('#csId').val();
			if (csId != "select" && csId != "") {
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getChildHomeworkTests.htm",
					data : "studentId=" + studentId + "&usedFor=" + usedFor
							+ "&csId=" + csId + "&tab=" + tab,
					success : function(response) {
						$("#loading-div-background").hide();
						$("#studentTestDiv").html(response);
						
					}
				});
			} else {
				return false;
			}
		} else if (userType == 'parent'
				&& (usedFor == 'assessments' || usedFor == 'rti')) {
			$("#studentTestDiv").empty();
			if (thisvar.value != "select" && thisvar != '') {
				$("#loading-div-background").show();
				$
						.ajax({
							type : "GET",
							url : "getChildTests.htm",
							data : "studentId=" + thisvar.value + "&usedFor="
									+ usedFor,
							success : function(response) {
								$("#loading-div-background").hide();
								$("#studentTestDiv").html(response);
								
							}
						});
			} else {
				return false;
			}
		} else if (userType != 'parent'
				&& (usedFor == 'homeworks' || usedFor == 'RFLP')) {
			var csId = $('#csId').val();
			if (thisvar.value != "select") {
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getStudentHomeworkTests.htm",
					data : "studentId=" + thisvar.value + "&usedFor=" + usedFor
							+ "&csId=" + csId + "&tab=" + tab,
					success : function(response) {
						$("#loading-div-background").hide();
						$("#studentTestDiv").html(response);
					}
				});
			} else {
				$("#testQuestionsDiv").html("");
				$("#studentTestDiv").html("");
				return false;
			}
		} else if (userType != 'parent'
				&& (usedFor == 'assessments' || usedFor == 'rti')) {
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",
				url : "getStudentTests.htm",
				data : "studentId=" + thisvar.value + "&usedFor=" + usedFor,
				success : function(response) {
					$("#loading-div-background").hide();
					$("#studentTestDiv").html(response);
					
				}
			});
		}

	}

	function clearDiv() {
		$("#studentTestDiv").html("");
		$("#testQuestionsDiv").empty();
	}
	function getChildTestQuestions(testCount, assignmentId) {
		var usedFor = $("#usedFor" + testCount).val();
		var studentAssignmentId = $("#studentAssignmentId" + testCount).val();
		var assignmentTypeId = $("#assignmentTypeId" + testCount).val();
		var createdBy = $("#createdBy" + testCount).val();
		var tab = $("#tab").val();
		$("#testQuestionsDiv").empty();
		if (assignmentTypeId == 13) {
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",
				url : "getPerformanceTest.htm",
				data : "studentAssignmentId=" + studentAssignmentId
						+ "&assignmentTypeId=" + assignmentTypeId
						+ "&createdBy=" + createdBy,
				success : function(response) {
					var dailogContainer = $(document
							.getElementById('performanceDailog'));
					var screenWidth = $(window).width() - 10;
					var screenHeight = $(window).height() - 10;
					$('#performanceDailog').empty();
					$(dailogContainer).append(response);
					$(dailogContainer).dialog({
						width : screenWidth,
						height : screenHeight,
						modal : true,
						open : function() {
							$(".ui-dialog-titlebar-close").show();
						},
						close : function(ev, ui) {
							window.location.reload()
						}
					});
					$(dailogContainer).dialog("option", "title",
							"Performance Test");
					$(dailogContainer).scrollTop("0");
					$("#loading-div-background").hide();
				}
			});
		} else if (assignmentTypeId == 15 || assignmentTypeId == 16) {
			document.getElementById('dailog').style.pointerEvents = 'none';
			goForAssignment('dialog' + testCount, studentAssignmentId,
					testCount, assignmentId);
		} else if (assignmentTypeId == 17) {
			document.getElementById('dailog').style.pointerEvents = 'none';
			goForPhonicSkillTest('dialog' + testCount, studentAssignmentId,
					testCount, assignmentId);
		} else {
			if (assignmentTypeId == 8 || assignmentTypeId == 14) {
				$.ajax({
					type : "GET",
					url : "getTestQuestions.htm",
					data : "studentAssignmentId=" + studentAssignmentId
							+ "&usedFor=" + usedFor + "&assignmentTypeId="
							+ assignmentTypeId + "&tab=" + tab + "&testCount="
							+ testCount + "&assignmentId=" + assignmentId,
					success : function(response) {
						var dailogContainer = $(document
								.getElementById('benchmarkDailog'));
						var screenWidth = $(window).width() - 10;
						var screenHeight = $(window).height() - 10;
						$('#benchmarkDailog').empty();
						$(dailogContainer).append(response);
						$(dailogContainer).dialog({
							width : screenWidth,
							height : screenHeight,
							modal : true,
							open : function() {
								$(".ui-dialog-titlebar-close").show();
							}
						});
						if (assignmentTypeId == 8) {
							$(dailogContainer).dialog("option", "title",
									"Benchmark Test");
						} else {
							$(dailogContainer).dialog("option", "title",
									"Spelling");
						}
						$(dailogContainer).scrollTop("0");
					}
				});

			} else {
				$.ajax({
					type : "GET",
					url : "getChildTestQuestions.htm",
					data : "studentAssignmentId=" + studentAssignmentId
							+ "&usedFor=" + usedFor + "&assignmentTypeId="
							+ assignmentTypeId + "&tab=" + tab + "&testCount="
							+ testCount + "&assignmentId=" + assignmentId,
					success : function(response) {
						var dailogContainer = $(document
								.getElementById('autoGradeTest'));
						var screenWidth = $(window).width() - 10;
						var screenHeight = $(window).height() - 10;
						$('#autoGradeTest').empty();
						$(dailogContainer).append(response);
						$(dailogContainer).dialog({
							width : screenWidth,
							height : screenHeight,
							modal : true,
							open : function() {
								$(".ui-dialog-titlebar-close").show();
							}
						});

						$(dailogContainer).dialog("option", "title",
								"View Test");

						$(dailogContainer).scrollTop("0");

					}
				});
			}
		}
	}
</script>
</head>
<body>
	<input type="hidden" id="userType" value="${userReg.user.userType}" />

	<form:form id="testQuestionsForm"
		modelAttribute="studentAssignmentStatus">
		<table width="100%">
			<tr>
				<td style="" align="right"><input type="hidden" id="tab"
					name="tab" value="${tab}" /></td>
				<td style="width: 100%; float: right;" align="right" valign="bottom">
					<input type="hidden" id="usedFor" value="${usedFor}" />
					<div>
						<c:choose>
							<c:when test="${usedFor == 'rti'}">
								<%@ include file="view_rti_tabs.jsp"%>
							</c:when>
							<c:when test="${usedFor == 'homeworks'}">
								<%@ include file="view_homework_tabs.jsp"%>
							</c:when>
							<c:otherwise>
								<%@ include file="view_assessment_tabs.jsp"%>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</tr>
		</table>
		<table width="99.8%" border="0" cellspacing="0" cellpadding="0"
			class="title-pad">
			<tr>
				<c:choose>
					<c:when
						test="${usedFor == 'homeworks' && tab=='current homeworks'}">
						<td class="sub-title title-border" height="40" align="left"
							colspan="10">Current Homeworks<br></td>
					</c:when>
					<c:when test="${usedFor == 'homeworks' && tab=='due homeworks'}">
						<td class="sub-title title-border" height="40" align="left"
							colspan="10">Past Due Homeworks<br></td>
					</c:when>
					<c:when test="${usedFor == 'rti'}">
						<td class="sub-title title-border" height="40" align="left"
							colspan="10">View Literacy Development Tests<br></td>
					</c:when>
					<c:otherwise>
						<td colspan="10" class="sub-title title-border" height="40"
							align="left">View Assessments<br></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>
		<td style="width: 100%" colspan="5" align="center" valign="top"
			class="">
			<table style="width: 100%" class="title-pad">
				<tr>
					<c:if test="${userReg.user.userType == 'parent'}">

						<td width='17%' align="left" height="20" class="label"
							valign="bottom">
							<div align="left" style="text-decoration: blink;"
								id="alertMessage">

								<c:set var="functionCall" value="getTests(this)"></c:set>
								<c:if test="${usedFor == 'homeworks'}">
									<c:set var="functionCall" value="loadChildClasess();clearDiv()"></c:set>
								</c:if>
								<label class="label">Child</label>&nbsp;&nbsp;&nbsp;<select
									name="studentId" id="studentId" class="listmenu"
									onchange="${functionCall}">
									<option value="select">Select Child</option>
									<c:forEach items="${childs}" var="child">
										<option value="${child.studentId}">${child.userRegistration.firstName}${child.userRegistration.lastName}</option>
									</c:forEach>
								</select>

							</div>
						</td>
					</c:if>
					<c:if test="${usedFor == 'homeworks'}">
						<td id="message" width='17%' align="left" valign="bottom"
							height="20" class="label"><label class="label">Class&nbsp;&nbsp;</label>
							<select name="csId" id="csId" class="listmenu"
							onchange="getTests(this)">
								<option value="select">Select Class</option>
								<c:forEach items="${studentClasses}" var="class">
									<option value="${class.classStatus.csId}"
										<c:if test="${class.classStatus.csId == selectedCsId}">selected="selected"</c:if>>
										${class.classStatus.section.gradeClasses.studentClass.className}</option>
								</c:forEach>
						</select></td>
					</c:if>
					<%-- <c:if
						test="${usedFor == 'homeworks' || tab == 'ViewWordWorkTests'}">
						<td width="60%" align="left"><a href="#"
							onclick="openVideoDialog('student','access_homework','videoDailog')"
							class="demoVideoLink" style="padding-left: 5px;"><i
								class="ion-videocamera" aria-hidden="true"
								style="font-size: 26px; font-weight: bold; color: #e23c00;"></i>&nbsp;video
								for access Homeworks</a></td>
					</c:if> --%>
					<td width="20%" align="left" valign="bottom" class="perinfohead">&nbsp;
					</td>

					<td width="50%" align="left" valign="bottom">&nbsp;</td>
				</tr>
			</table>
		</td>
		<c:if test="${userReg.user.userType ne 'parent' }">
			<c:if test="${usedFor == 'homeworks'}">
				<table>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</c:if>
		</c:if>
		<c:if test="${userReg.user.userType eq 'parent' }">
			<table>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
		</c:if>
		<table style="width: 90%" align="center">
			<tr>
				<td width="150" align=left>&nbsp;</td>
				<td width="800" align="left" id="studentTestDiv"><c:if
						test="${userReg.user.userType ne 'parent' }">
						<%@include file="/jsp/CommonJsp/student_tests.jsp"%>
					</c:if></td>
				<td width="100"></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		<tr>
			<td width="45" align=left>&nbsp;</td>
			<td width="90%" align="center"><div align="center"
					style="width: 80%" id="testQuestionsDiv"></div></td>
		</tr>
		<tr>
			<td id="appenddiv2" width="80%" align="center">
				<div align="center" style="width: 80%; visibility: visible;"
					id="returnMessage" class="status-message">${helloAjax}</div>
			</td>
		</tr>
		</table>

		<div id="benchmarkDailog" title="Fluency Reading Test"
			style="background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
		<div id="performanceDailog" title="Performance Test" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;font-size: 14px;font-family: 'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;font-weight:500;">
			<iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="98%" src=""></iframe>
		</div>
		<div id="autoGradeTest" title="Take Test"
			style="background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center; align: center"></div>
		<tr>
			<td><div id="videoDailog" title="Access Homework"
					style="background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td>
		</tr>
	</form:form>
	<div id="mathGameDiv" align=center style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
	<input type="hidden" id="mathGameAssignRow" value="">
	<div id="loading-div-background" class="loading-div-background"
	style="display: none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 17px; margin-top: -8.5em;">
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>Loading....
	</div>
</div>
</body>
	</html>
</div>