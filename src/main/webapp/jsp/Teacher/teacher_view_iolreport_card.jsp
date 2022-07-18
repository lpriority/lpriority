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
<title>Show Reports</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<!-- <link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/teacher_popup.js"></script> -->
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script> 
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>

<script type="text/javascript" src="resources/javascript/admin/le_rubric.js"></script>

<style type="text/css">
#atten,#module {
	border: 0px solid black;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeOut(5000);
	});
function clearsAll(){
	$("#reportDatesDiv").empty();
	$("#classId").val("select");
	$("#studentId").val("select");
	$("#csId").val("select");
}
function clearDiv(){
	$("#reportDatesDiv").empty();
}
</script>
</head>
<body>
	<table width="100%" height="100%" border="0" cellspacing="0"
		cellpadding="0">
		<tr>



			<td><input type="hidden" id="tab" name="tab" value="${tab}" />
				<input type="hidden" id="teacherObj" value="${teacherObj}" /> <input
				type="hidden" id="userType" value="${userType}" />
				<c:set var="viewStatus" value="show" />
				</td>
		</tr>
	</table>
	<table width="100%" height="100%" border=0 align="center" cellPadding=0
		cellSpacing=0 class="title-pad heading-up">

		<td height="30" colspan="2" align="left" valign="middle"><table
				width="100%" height="30" border="0" cellpadding="0" cellspacing="0"
				class="heading-up">


				<tr>
					<td width="20%" colspan="2"><label class="label">Grade&nbsp;&nbsp;&nbsp;</label>
						<select name="gradeId" class="listmenu" id="gradeId"
						onChange="getGradeClasses();clearsAll();" required="required">
							<option value="">select grade</option>
							<c:forEach items="${grList}" var="ul">
								<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
							</c:forEach>
					</select></td>
					<td width="17%" align='left' valign='middle'><label
						class="label">Class&nbsp;&nbsp;&nbsp;</label> <select id="classId"
						name="classId" class="listmenu"
						onChange="getClassSections();clearDiv();" required="required">
							<option value="select">select subject</option>
					</select></td>
					<td width="18%" align='left' valign='middle'><label
						class="label">Section&nbsp;&nbsp;&nbsp;</label> <select id="csId"
						class="listmenu" onChange="getStudentsBySection();clearDiv();"
						required="required">
							<option value="select">select Section</option>
					</select></td>
					<td width="20%" align='left' valign='middle'><label
						class="label">Student&nbsp;&nbsp;&nbsp;</label> <select
						id="studentId" class="listmenu"
						onChange="getStudentIOLReportDates('${viewStatus}','student')" required="required">
							<option value="select">Select Student</option>
					</select></td>

					<td width='20%'>&nbsp;</td>
					<td width='20%'>&nbsp;</td>

				</tr>

				<tr>
					<td height="30" colspan="2">&nbsp;<br>
					<br></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>
						<div id="reportDatesDiv" style="align: center" class="tabtxt"></div>
					</td>
					<td width="100"></td>
				</tr>
			</table></td>
		</tr>
		<tr>
			<td>
				<table width="100%" style="align: center" class="title-pad">
					<tr>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

</body>
</html>