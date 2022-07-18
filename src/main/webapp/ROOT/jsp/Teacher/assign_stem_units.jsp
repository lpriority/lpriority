<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/stem_curriculum.js"></script>
<link
	href="resources/javascript/jplayer/skin/blue.monday/css/jplayer.blue.monday.min.css"
	rel="stylesheet" type="text/css" />
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<title>Assign Lessons</title>
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
	$(document).ready(function() {
		$('#status').fadeOut(4000);
		$('#returnMessage').fadeOut(4000);
	});
	
	function validateForm(){
		var checked = false;
		$('input:checkbox').each(function () {
			if ($(this).is(':checked')) {
				checked = true;
	      }
		});
		if(!checked){
			alert('please select a lesson/activity');
			return false;
		}else{
			return true;
		}
	}
	function clearDiv(thisvar){
		if(thisvar.id == 'gradeId'){
			$("#classId").empty();
			$("#pathId").empty();
			$("#classId").append($("<option></option>").val("select").html("select Class"));
			$("#pathId").append($("<option></option>").val("select").html("select Path"));
		}
		$("#unitDiv").empty();
	}
	
	function assignCurriculum() {
		var status = validateForm();
		if(status){
			$("#loading-div-background").show();
			var formObj = document.getElementById("createUnits");
			var formData = new FormData(formObj);
			$.ajax({
				url: 'assignCurriculum.htm',
				type: 'POST',
				data: formData,
				mimeType:"multipart/form-data",
				contentType: false,
				cache: false,
				processData:false,
				success: function(data){
					$("#loading-div-background").hide();
					if(data != 'Assign Lessons failed')
						systemMessage(data);
					else
						systemMessage(data,"error");
					getUnitsByTeacherNAdmin();
				}
			});	
		}
	}
</script>
</head>
<body>
	<form:form id="createUnits" name="createUnits"
		action="assignCurriculum.htm" modelAttribute="createUnits"
		method="post">
		<table width="100%" height="100%" border=0 align="center"
			cellPadding=0 cellSpacing=0>
			<tr>
				<td height="20" style="color: white; font-weight: bold"><input
					type="hidden" id="tab" name="tab" value="${tab}" /></td>
				<td vAlign=bottom align=right>
					<div>
						<%@ include file="/jsp/curriculum/curriculum_tabs.jsp"%>

					</div>
				</td>
			</tr>

			<tr>
				<td height="30" colspan="2" align="left" valign="middle">
					<table width="100%" height="30" border="0" cellpadding="0"
						cellspacing="0" class="title-pad">
						<tr>

							<td width="40" align="left"><label class="label">Grade
							</label></td>
							<td width="60" align="left"><select name="gradeId"
								id="gradeId" class="listmenu"
								onChange="getGradeClasses();clearDiv(this)" required="required">
									<option value="select">Select Grade</option>
									<c:forEach var="gList" items="${teacherGrades}">
										<option value="${gList.gradeId}">${gList.masterGrades.gradeName}</option>
									</c:forEach>
							</select></td>
							<td width="40"><label class="label">Class </label></td>
							<td width="60" align="left" valign="middle">
							 <select required="required" name="classId" class="listmenu" onChange="loadPaths();clearDiv(this)" id="classId">
									<option value="select">select class</option>
							</select></td>
							<td height="40" class="label" style="width: 15em; padding-left: 2em;">Path&nbsp;&nbsp;&nbsp; 
							<select id="pathId" style="width: 120px;" name="pathId" class="listmenu" onchange="getStemUnitsByTeacherNAdmin();clearDiv(this)">
									<option value="select">select Path</option>
							</select>
							</td>
							<td width="602" align="left" valign="middle"><font
								color="blue"><%=request.getParameter("msg") == null ? "" : request.getParameter("msg")%></font></td>
						</tr>
					</table>
				</td>
			</tr>


			<!-- <tr>
				<td height="2" colspan="2">

					<div id="videoDiv">
						<a href="#"
							onClick="openVideoDialog('teacher','assign_lesson','videoDailog')"
							class="demoVideoLink"><i class="ion-videocamera"
							aria-hidden="true"
							style="font-size: 26px; font-weight: bold; color: #e23c00;"></i>&nbsp;video
							for Assign Lesson</a>
					</div>
				</td>

			</tr> -->

			<tr>
				<td colspan="2" align="center">
					<table>
						<tr>
							<td align="left" id="unitDiv"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td id="appenddiv2" height="30" colspan="2" align="center"
					valign="middle"><font color="blue"><label
						id="returnMessage" style="visibility: visible;">${helloAjax}</label></font>
				</td>
			</tr>
			<tr>
				<td width="100%" colspan="10" align="center"><label id="status"
					style="color: blue;">${hellowAjax}</label></td>
			</tr>
		</table>
	</form:form>
	<div id="videoDailog" title="Grade Fluency"
		style="background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div>
</body>
</html>