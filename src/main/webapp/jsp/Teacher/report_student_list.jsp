<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
.ui-accordion > .ui-widget-header {background: #94B8FF;}
.ui-accordion > .ui-widget-content {background: white;}
.ui-accordion > .ui-accordion-header {font-size: 100%;background: white;}
</style>
<script type="text/javascript">

$(function() {
    $( "#rows" ).accordion({
      collapsible: true,
      active: false,
      activate:function(event, ui ){
  		setFooterHeight();
    }
    });    
    var len = $('#lengthId').val();
    for(var i=0;i<len;i++){
    	$( "#releaseId"+i ).datepicker({
        	changeMonth: true,
            changeYear: true,
            showAnim : 'clip'
        });	
    }
});

function submitChanges(count){
	if($('#aGradeId'+count).val()== ""){
		alert("Please choose academic grade");
		return false;
	}
	if($('#citizenId'+count).val()== ""){
		alert("Please choose citizenship");
		return false;
	}
	if($('#comId'+count).val()== ""){
		alert("Please choose comments");
		return false;
	}
	if($('#releaseId'+count).val()== ""){
		alert("Please choose date of release");
		return false;
	}
	document.getElementById("acedamicGradeId"+count).value = $('#aGradeId'+count).val();
	document.getElementById("citizenshipId"+count).value = $('#citizenId'+count).val();
	document.getElementById("commentId"+count).value = $('#comId'+count).val();
	document.getElementById("releaseDate"+count).value = $('#releaseId'+count).val();
	document.getElementById("csId"+count).value = $('#csId').val();
	var formObj = document.getElementById("reports"+count);
	var formURL = formObj.action;
	var formData = new FormData(formObj);
	$.ajax({
		url: formURL,
		type: 'POST',
		data: formData,
		mimeType:"multipart/form-data",
		contentType: false,
		cache: false,
		processData:false,
		success: function(data, textStatus, jqXHR){
			var obj = JSON.parse(data);
			systemMessage(obj.status);
		}
	});	
	$( "#rows" ).accordion({
	      collapsible: true,
	      active: false,
	      activate:function(event, ui ){
	    		setFooterHeight();
	      }
	});
	return false;
}


function showStudentGrades(studentId){
	var usedFor = $('#usedFor').val();
	$.ajax({
		type : "GET",
		url : "getStudentGrades.htm",
		data : "studentId=" + studentId+"&usedFor="+ usedFor,
		success : function(response) {
			var gradeContainer = $(document.getElementById('gradeDiv'));
			var screenWidth = $( window ).width() - 10;
			var screenHeight = $( window ).height() - 10;
			$('#gradeDiv').empty();  
			$(gradeContainer).append(response);
			$(gradeContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
				  $(".ui-dialog-titlebar-close").show();
			} });		
			$(gradeContainer).dialog("option", "title", "Student Grades");
			$(gradeContainer).scrollTop("0");
		}
	});
}
</script>
<link rel="stylesheet" href="resources/css/notify/notify.css">
<link rel="stylesheet" href="resources/css/notify/prettify.css">
<script src="resources/javascript/notify/notify.js"></script>
<script src="resources/javascript/notify/prettify.js"></script>
<script type="text/javascript" src="resources/javascript/template/common_template.js"></script>
<style>
.notify-backdrop{
background-color: #e6e6e6;
}
.notify{
	width:auto;
	padding:none;
}
.notify.center{margin-left:-100px;}
.notify > button.close{
   opacity:1;
   color: white;
   text-shadow:0 1px 2px rgb(37, 37, 37);
}
</style>
</head>
<body>
<div align="center" >
		<table name="classes" border="0" cellpadding="0" cellspacing="10" vspace="0" width="100%" hspace="0" align="center" class="txtStyle des" style="font-size:16px;">
			<tr class="Divheads">
				<th  align="center" width="50" height="60">&nbsp;&nbsp;&nbsp;</th>
				<th  align="center" width="150" >Reg.Id</th>
				<th  align="center" width="150" >Student name</th>
				<th  align="center" width="150" style="padding-left: 5px;" >Homework Percentage Average</th>	
				<th  align="center" width="150" >Assessment Percentage Average</th>	
				<th  align="center" width="150" >Performance Task Average</th>	
				<th  align="center" width="150" >Present</th>
				<th  align="center" width="150" >Absent</th>
				<th  align="center" width="150" >Excused Absent</th>
				<th  align="center" width="150" >Tardy</th>
				<th  align="center" width="150" >Excused Tardy</th>						
			</tr>	
			<tr><td colspan="14">	
				<label id=submitStatus style="visibility: visible;padding-left: 15cm;" class="status-message"></label>			
				<input type="hidden" id="lengthId" value="${fn:length(studentList)}">
			<div id="rows">
			<c:set var="count" value="0"/>			
			<c:forEach items="${studentList}" var="cList">
				<form:form id="reports${count}"  action="reportSubmit.htm" modelAttribute="report" method="post">
				<table><tr>
						<td  align="center" width="150" style="padding-bottom: 5px;">
							${cList.student.studentId}
							<form:hidden path="student.studentId" value="${cList.student.studentId}"/> 
						</td>
						<td  align="center" width="150" style="padding-bottom: 5px;">
							${cList.student.userRegistration.firstName} ${cList.student.userRegistration.lastName}
						</td>					
						<td  align="center" width="150">
						<c:choose>
							<c:when test="${homeworkAverages[cList.student.studentId] >= 0 }">
								<fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${homeworkAverages[cList.student.studentId]}" />
								${formattedPercentage}
								<form:hidden path="homeworkPerc" value="${formattedPercentage}"/>
							</c:when>
							<c:otherwise>
								0.0
							</c:otherwise>
						</c:choose>
						</td>
						<td  align="center" width="150">
						<c:choose>
							<c:when test="${assessmentAverages[cList.student.studentId] >= 0 }">
								<fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${assessmentAverages[cList.student.studentId]}" />
								${formattedPercentage}
								<form:hidden path="assignmentPerc" value="${formattedPercentage}"/>
							</c:when>
							<c:otherwise>
								0.0
							</c:otherwise>
						</c:choose>
						</td>
						<td  align="center" width="150" style="padding-left: 1cm;">
						<c:choose>
							<c:when test="${performanceAverages[cList.student.studentId] >= 0 }">
								<fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${performanceAverages[cList.student.studentId]}" />
								${formattedPercentage}
								<form:hidden path="performancePerc" value="${formattedPercentage}"/>
							</c:when>
							<c:otherwise>
								0.0
							</c:otherwise>
						</c:choose>
						</td>
						<td  align="center" width="150">
						<c:choose>
							<c:when test="${studentAttendance[cList.student.studentId].presentCount > 0 }">
								${studentAttendance[cList.student.studentId].presentCount}
								<form:hidden path="presentdays" value="${studentAttendance[cList.student.studentId].presentCount}"/>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
						</td>
						<td  align="center" width="150">
						<c:choose>
							<c:when test="${studentAttendance[cList.student.studentId].absentCount > 0 }">
								${studentAttendance[cList.student.studentId].absentCount}
								<form:hidden path="absentdays" value="${studentAttendance[cList.student.studentId].absentCount}"/>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
						</td>
						<td  align="center" width="150">
						<c:choose>
							<c:when test="${studentAttendance[cList.student.studentId].excusedAbsentCount > 0 }">
								${studentAttendance[cList.student.studentId].excusedAbsentCount}
								<form:hidden path="excusedabsentdays" value="${studentAttendance[cList.student.studentId].excusedAbsentCount}"/>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
						</td>
						<td  align="center" width="150">
						<c:choose>
							<c:when test="${studentAttendance[cList.student.studentId].tardyCount > 0 }">
								${studentAttendance[cList.student.studentId].tardyCount}
								<form:hidden path="tardydays" value="${studentAttendance[cList.student.studentId].tardyCount}"/>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
						</td>
						<td  align="center" width="150">
						<c:choose>
							<c:when test="${studentAttendance[cList.student.studentId].excusedTardyCount > 0 }">
								${studentAttendance[cList.student.studentId].excusedTardyCount}
								<form:hidden path="excusedtardydays" value="${studentAttendance[cList.student.studentId].excusedTardyCount}"/>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
						<form:hidden path="academicGrades.acedamicGradeId" id="acedamicGradeId${count}" value="0"/>
						<form:hidden path="citizenship.citizenshipId" id="citizenshipId${count}" value="0"/>
						<form:hidden path="comments.commentId" id="commentId${count}" value="0"/>
						<form:hidden path="classStatus.csId" id="csId${count}" value="0"/>
						<form:hidden path="releaseDate" id="releaseDate${count}"/>
						</td>
					</tr></table></form:form>
					<div><!--  style="font-size: small;" -->
						Academic Grade:	<select name="aGradeId" class="listmenu" id="aGradeId${count}" required="required">
											<option value="">select Academic Grade</option>
										<c:forEach items="${academicGrades}" var="ag">
											<option value="${ag.acedamicGradeId}">${ag.acedamicGradeName}</option>
										</c:forEach>
										</select><br><br>
						Citizenship: <select name="citizenId" class="listmenu" id="citizenId${count}" required="required">
											<option value="">Select Citizenship</option>
										<c:forEach items="${citizen}" var="ct">
											<option value="${ct.citizenshipId}">${ct.citizenshipName}</option>
										</c:forEach>
									</select><br><br>
						Comments: <select name="comId" class="listmenu" id="comId${count}" required="required">
											<option value="">Select Comments</option>
										<c:forEach items="${comments}" var="cm">
											<option value="${cm.commentId}">${cm.comment}</option>
										</c:forEach>
								 </select><br><br>
						Date of Release: <input type="text" id="releaseId${count}" name="releaseId"/><br><br>
						<div class="button_green" id="btSubmitChanges" name="btSubmitChanges" height="52" width="50" onclick="return submitChanges(${count})" style="font-size:12px;width:90px;" >Submit Changes</div>
					</div>
					<c:set var="count" value="${count+1}"/>					
					</c:forEach></div></td>
				</tr>
			
			
		</table>
		</div>
</body>
</html>