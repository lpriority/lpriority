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
<title>Grade Assessments</title>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link
	href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css"
	rel="stylesheet" type="text/css" />
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/teacher_popup.js"></script>
<script src="resources/javascript/TeacherJs/grade_assessments.js"></script>
<script src="resources/javascript/TeacherJs/retell_fluency_test.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeOut(4000);
	});

	function getStudentTestQuestions(assignmentId, studentAssignmentId,
			assignmentTypeId, status, testCount, lessonId, studentId) {

		$("#StuAssessQuestionsList").empty();
		if (status == "pending") {
			alert("This task is not submitted yet");
			return;
		}
		var usedFor = $('#usedFor').val();
		var tab = $('#tab').val();
		if (assignmentTypeId == 13) {
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",
				url : "gradePerformanceTest.htm",
				data : "studentAssignmentId=" + studentAssignmentId
						+ "&assignmentTypeId=" + assignmentTypeId + "&tab="
						+ tab,
				async : true,
				success : function(response) {
					var screenWidth = $(window).width() - 10;
					var screenHeight = $(window).height() - 10;
					$('#performanceDailog').empty();
					$("#performanceDailog").html(response);
					$("#performanceDailog").dialog({
						width : screenWidth,
						height : screenHeight,
						modal : true,
						open : function() {
							$(".ui-dialog-titlebar-close").show();
						},
						close : function(event, ui) {
							$(this).empty();
							$('#performanceDailog').dialog('destroy');
							//getStudentAllPerformanceTest(assignmentId);
							getStudentAsessments();
						},
						dialogClass: 'myTitleClass'
					});
					$("#performanceDailog").dialog("option", "title",
							"Grade Performance Tests");
					$("#performanceDailog").scrollTop("0");
					$("#loading-div-background").hide();
				}
			});
		} else {
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",
				url : "getStudentTestQuestions.htm",
				data : "studentAssignmentId=" + studentAssignmentId
						+ "&usedFor=" + usedFor + "&assignmentTypeId="
						+ assignmentTypeId + "&assignmentId=" + assignmentId
						+ "&lessonId=" + lessonId + "&studentId=" + studentId,
				success : function(response) {
					if (assignmentTypeId == 8 || assignmentTypeId == 20) {
						$('#StuAssessQuestionsList').append(response);
						$('#StuAssessQuestionsList').show();
						$("#loading-div-background").hide();
					} else {
						var dailogContainer = $(document
								.getElementById('StuAssessQuestionsList'));
						var screenWidth = $(window).width() - 40;
						var screenHeight = $(window).height() - 40;
						$('#StuAssessQuestionsList').empty();
						$(dailogContainer).append(response);
						$(dailogContainer).dialog({
							width : screenWidth,
							height : screenHeight,
							modal : true,
							open : function() {
								$(".ui-dialog-titlebar-close").show();
							}, close: function(event, ui) {
									$("#StuAssessQuestionsList").empty();  
								    $(dailogContainer).dialog('destroy');	
								    
							},
							dialogClass: 'myTitleClass'
						});
						if (assignmentTypeId == 14) {
							$(dailogContainer).dialog("option", "title",
									"Spelling");
						} else {
							$(dailogContainer).dialog("option", "title",
									"Grade Tests");
						}
						$(dailogContainer).scrollTop("0");
						$("#loading-div-background").hide();
					}
				}
			});
		}
	}
	
	function getStudentAllPerformanceTest(assignmentId){
		
			 $.ajax({  
					url : "getStudentAssessmentTests.htm", 
				    data: "assignmentId="+assignmentId,
				    success: function(data) {
				    	 $("#Evaluate").empty(); 
				    	 $("#Evaluate").html(data);
				    	 return false;
				    }
				}); 		
		
	}
 
	function getGroupAssignedDates(callback) {
		var usedFor = $('#usedFor').val();
		var csId = $('#csId').val();
		$("#assignedDate").empty();
		$("#assignedDate").append(
				$("<option></option>").val('select').html('Select Date'));
		$("#title").empty();
		$("#title").append(
				$("<option></option>").val('select').html('Select Title'));
		$("#ReportList").html("");
		if (usedFor && csId != 'select') {
			$
					.ajax({
						type : "GET",
						url : "getGroupAssignedDates.htm",
						data : "csId=" + csId + "&usedFor=" + usedFor,
						success : function(response2) {
							var teacherAssignedDates = response2.teacherAssignedDates;
							$("#assignedDate").empty();
							$("#assignedDate").append(
									$("<option></option>").val('select').html(
											'Select Date'));
							$
									.each(
											teacherAssignedDates,
             								function(index, value) {
												$("#assignedDate")
														.append(
																$(
																		"<option></option>")
																		.val(getDBFormattedDate(value.dateAssigned))
																		.html(
																				getFormattedDate(value.dateAssigned)));
											});
							if (callback)
								callback();
						}
					});
		}
	}

	function getGroupAssignmentTitles(callback) {
		var usedFor = $('#usedFor').val();
		var csId = $('#csId').val();
		var classId = $('#classId').val();
		var assignedDate = $('#assignedDate').val();
		if (assignedDate != 'select' && csId != 'select' && classId != 'select') {
			$.ajax({
				type : "GET",
				url : "getGroupAssignmentTitles.htm",
				data : "csId=" + csId + "&usedFor=" + usedFor
						+ "&assignedDate=" + assignedDate,
				success : function(response2) {
					var assignmentTitles = response2.assignmentTitles;
					$("#titleId").empty();
					$("#titleId").append(
							$("<option></option>").val('select').html(
									'Select Title'));
					$.each(assignmentTitles, function(index, value) {
						$("#titleId").append(
								$("<option></option>").val(value.assignmentId)
										.html(value.title));
					});
					if (callback)
						callback();
				}
			});
		}
	}
	
</script>
</head>
<body>
<div class="container-fluid dboard">
  <div class="row">
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
  <div class="row">
    <div class="col-md-12">
      <form:form name="homeworkReports" modelAttribute="assignment">
      <div class="row">
        <input type="hidden" name="tab" id="tab" value="${tab}">
        <input type="hidden" id="teacherObj" value="${teacherObj}" />
		<input type="hidden" id="usedFor" value="${usedFor}" />         
        <div class="col-md-1"></div>  
        <div class="col-md-2">
          <label class="col-xs-12 label text-left">Grade</label>
          <div class="col-xs-12">
            <select name="gradeId" class="listmenu" id="gradeId" onChange="getGradeClasses();clears()" required="required">
              <option value="select">select grade</option>
              <c:forEach items="${teacherGrades}" var="ul">
                <option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="col-md-2">
          <label class="col-xs-12 label text-left">Class</label>
          <div class="col-xs-12">
            <select id="classId" name="classId" class="listmenu" onChange="getClassSections();clears()" required="required">
              <option value="select">select subject</option>
            </select>
          </div>
        </div>
        <div class="col-md-2">
          <c:set var="forDates" value="getAssignedDates()">
          </c:set>
          <c:set var="forTitles" value="getAssignmentTitles()">
          </c:set>
          <c:if test="${tab == 'groupPerformance'}">
            <c:set var="forDates" value="getGroupAssignedDates()">
            </c:set>
            <c:set var="forTitles" value="getGroupAssignmentTitles()">
            </c:set>
          </c:if>
          <label class="col-xs-12 label text-left">Section</label>
          <div class="col-xs-12">
            <form:select id="csId" path="classStatus.csId" class="listmenu" onChange="${forDates};clears()" required="required">
              <option value="select">select Section</option>
            </form:select>
          </div>
        </div>
        <div class="col-md-2">
          <label class="col-xs-12 label text-left">Date</label>
          <div class="col-xs-12">
            <select name="assignedDate" class="listmenu" id="assignedDate" onChange="clears();${forTitles};">
              <option value="select">Select Date</option>
            </select>
          </div>
        </div>
        <div class="col-md-2">
          <label class="col-xs-12 label text-left">Title</label>
          <div class="col-xs-12">
            <select name="titleId" class="listmenu" id="titleId" onChange="getStudentAsessments();">
              <option value="select">Select Title</option>
            </select>
          </div>
        </div>
      </div>
      <!-- 
      <div class="row">
        <div class="col-md-12">
          <c:if test="${usedFor == 'rti'}">             
            	<div id="videoDiv">
                    <a href="#" onClick="openVideoDialog('teacher','grade_fluency','videoDailog')" class="demoVideoLink"><i class="ion-videocamera"
                        aria-hidden="true" style="font-size: 26px; font-weight: bold; color: #e23c00;"></i>&nbsp;video for Grade Fluency</a>
                </div> 
          </c:if>
        </div>
      </div>
      -->
      <div class="row">
        <div class="col-md-12">
          <label id="status" style="color: blue;">${hellowAjax}</label>
        </div>
      </div>
      <br>
      <div class="row">
        <div class="col-md-12 text-center">
          <div class="row">
            <div class="col-sm-12" id="Evaluate" class="title-pad">
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12">
            <div id="StuAssessQuestionsList" class="text-center" style="padding-top:20px;"></div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12">
            <div id="videoDailog" title="Grade Fluency" style="background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12" id="appenddiv2"> <font color="blue"><label id=returnMessage style="visibility: visible;">${helloAjax}</label>
            </font> </div>
        </div>
      </div>
    </div>
    </form:form>
  </div>
</div>
</body>
</html>