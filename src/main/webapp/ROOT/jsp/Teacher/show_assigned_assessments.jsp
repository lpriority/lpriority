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
<title>Show Assigned Assessments</title>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<style>
.notify-backdrop {
	background-color: #e6e6e6;
}
.notify {
	width:auto;
	padding:none;
}
.notify.center {
	margin-left:-100px;
}
.notify > button.close {
	opacity:1;
	color: white;
	text-shadow:0 1px 2px rgb(37, 37, 37);
}
</style>
<script type="text/javascript">
	function getTeacherGradeClass() {
		var gradeId = $('#gradeId').val();
		$("#csId").empty();
		$("#classId").empty();
		$("#csId").append($("<option></option>").val("select").html("Select Section"));
		$("#ShowAssignmentsList").html("");
		$.ajax({
			type : "GET",
			url : "getTeacherClasses.htm",
			data : "gradeId=" + gradeId,
			success : function(response) {
				var teacherClasses = response.teacherClasses;
				$("#classId").empty();
				$("#classId").append(
						$("<option></option>").val('').html('Select Subject'));
				$.each(teacherClasses, function(index, value) {
					$("#classId").append(
							$("<option></option>").val(value.classId).html(
									value.className));
				});

			}
		});
	}
	function loadSections() {
		var gradeId = $('#gradeId').val();
		var classId = $('#classId').val();
		$("#csId").empty();   
		$("#csId").append($("<option></option>").val("select").html("Select Section"));
	     if(gradeId== '' || gradeId==null || gradeId=='select'){
			 alert("Please select the gradename");
	       $('#gradeId').next().show();
	       return false;
	     }
	     if(classId== '' || classId==null || classId=='select'){
			 alert("Please select a class");
	       $('#classId').next().show();
	       return false;
	     }
		$("#ShowAssignmentsList").html("");
		$.ajax({
			type : "GET",
			url : "getTeacherSections.htm",
			data : "gradeId=" + gradeId + "&classId=" + classId,
			success : function(response1) {
				var teacherSections = response1.teacherSections;
				$("#csId").empty();
				$("#csId").append(
						$("<option></option>").val('').html('Select Section'));
				$.each(teacherSections, function(index, value) {
					$("#csId").append(
							$("<option></option>").val(value.csId).html(
									value.section.section));
				});
			}

		});

	}

	function showAssignedClasses(tab) {
		var gradeId = $('#gradeId').val();
		var classId = $('#classId').val();
		var csId = $('#csId').val();
		 if(gradeId== '' || gradeId==null || gradeId=='select'){
	       $('#gradeId').next().show();
	       return false;
	     }
	     if(classId== '' || classId==null || classId=='select'){
	       $('#classId').next().show();
	       return false;
	     }if(csId== '' || csId==null || csId=='select'){
			 $("#ShowAssignmentsList").html("");
		       $('#csId').next().show();
		       return false;
		     }
		if (csId == "") {
			$("#ShowAssignmentsList").html("");
			return false;
		}
		$("#loading-div-background").show();
		$.ajax({
			url : "getTeacherAssignedAssessments.htm",
			data : "gradeId=" + gradeId + "&classId=" + classId + "&csId="
					+ csId+"&tab="+tab,
			success : function(response) {
				$("#ShowAssignmentsList").html(response);
				 $("#loading-div-background").hide();
			}
		});
	}
	function editAssignment(index) {
		var endDate = $('#enddate' + index).val();
		var startdate = $('#startdate' + index).val();

		if (endDate != '') {
			var endD = new Date(endDate);
			var curD = new Date(startdate);
			if (endD.getTime() < curD.getTime()) {
				if (endD.getFullYear() === curD.getFullYear()
						&& endD.getDate() === curD.getDate()
						&& endD.getMonth() === curD.getMonth()) {
					//do nothing
				} else {
					alert("End date should not older than today");
					$('#edit' + index).attr('checked', false);
					return;
				}
			}
		} else {
			alert("Please enter end date");
			return;
		}

		var assignmentId = $('#edit' + index).val();
	if(confirm("Do you want to update assignment Due date?",function(status){
		if(status){
			$.ajax({
				url : "UpdateAssignment.htm",
				data : "assignmentId=" + assignmentId + "&enddate=" + endDate,
				success : function(data) {
					systemMessage(data);
					$('#edit' + index).attr('checked', false);

				}
			});
		}
		})); 
	}
	function deleteAssignment(index) {
		var csId = $('#csId').val();
		var assignmentId = $('#delete' + index).val();
		if(confirm("Do you want to delete the assignment?",function(status){
			if(status){
				$.ajax({
					url : "DeleteAssignments.htm",
					data : "assignmentId=" + assignmentId + "&csId=" + csId,
					success : function(response) {
						$('#row'+index).remove()
						systemMessage(response);
						
					}
				});
			}
		})); 
	}
	function datepick(i) {
		
	    $( "#enddate"+i).datepicker({
	    	changeMonth: true,
	        changeYear: true,
	        showAnim : 'clip',
	        minDate : 0
	    });
	}
	function clearDiv(){
		$("#ShowAssignmentsList").empty();
	 }
</script>
</head>

<body>
<c:choose>
  <c:when test="${tab == 'showAssignedRTI'}">
    <div>
      <%@ include file="/jsp/assessments/rti_tabs.jsp"%>
    </div>
  </c:when>
  <c:otherwise>
    <div>
      <%@ include file="/jsp/curriculum/curriculum_tabs.jsp"%>
    </div>
  </c:otherwise>
</c:choose>
<form:form name="assignAssessmentsForm" modelAttribute="assignment">
  <div class="row">
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Grade</label>
        <div class="col-sm-9">
          <select name="gradeId" class="listmenu" id="gradeId"
											onChange="clearDiv();getGradeClasses()" required="required">
            <option value="select">select grade</option>
            <c:forEach items="${teacherGrades}" var="ul">
              <option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Class</label>
        <div class="col-sm-9">
          <select id="classId" name="classId" class="listmenu" onChange="clearDiv();getClassSections()" required="required">
            <option value="select">select subject</option>
          </select>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="form-group row">
        <label class="col-sm-3 col-form-label label">Section</label>
        <div class="col-sm-9">
          <form:select id="csId" path="classStatus.csId" class="listmenu" onChange="clearDiv();showAssignedClasses('${tab}')" required="required">
            <option value="select">select Section</option>
          </form:select>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <label id="returnMessage" style="color: blue;">&nbsp;${hellowAjax}</label>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12" id="ShowAssignmentsList"> </div>
  </div>
</form:form>
</body>
</html>