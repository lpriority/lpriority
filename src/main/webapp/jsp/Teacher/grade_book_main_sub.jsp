<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
	<head>
	<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
		<script type="text/javascript">
			$(document).ready(function () {
				 $('#returnMessage').fadeOut(3000);
			});
			
			function showStudents(){
				var usedFor = $('#usedFor').val();
				var csId = $('#csId').val();
				var gradeId = $('#gradeId').val();
				var classId = $('#classId').val();
				var studentContainer = $(document.getElementById('studentDiv'));
				$(studentContainer).empty(); 
				if(usedFor != "select" && csId != "select" && gradeId != "" && classId != "select"){
					$.ajax({
						type : "GET",
						url : "getGradeStudentList.htm",
						data : "csId=" + csId+"&usedFor="+ usedFor,
						success : function(response) {
							$(studentContainer).append(response); 
						}
					}); 	
				}
			}
			
			function clearFilters(){
				//$("#usedFor").empty();
				//$("#usedFor").append($("<option></option>").val('').html('Select Assignment'));
				$("#studentDiv").empty(); 
			}
		</script>
	</head>
	<body>
		<form:form name="gradeBook"	action="gradeBookSubmit.htm" modelAttribute="studentAssignmentStatus" method="post">
			<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad heading-up">
				<td height="30" colspan="2" align="left" valign="middle"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" class="heading-up">
					<tr>
						<td width="18%" colspan="2">
							<label class="label">Grade&nbsp;&nbsp;&nbsp;</label> 
							<select name="gradeId" class="listmenu" id="gradeId" onChange="getGradeClasses();clearFilters();" required="required">
								<option value="">select grade</option>
								<c:forEach items="${grList}" var="ul">
									<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
								</c:forEach>
							</select>
						</td>
						<td width="18%" align="left" valign="middle" style="padding-left:2em;">
							<label class="label">Class&nbsp;&nbsp;&nbsp;</label>
							<select id="classId" name="classId" class="listmenu" onChange="getClassSections();clearFilters()" required="required">
								<option value="">select subject</option>
							</select>
						</td>
						<td width="20%" align="left" valign="middle" style="padding-left:2em;">
							<label class="label"> Section&nbsp;&nbsp;&nbsp;</label>
							<select id="csId" class="listmenu" onChange="loadAssignment()" required="required">
								<option value="">select Section</option>
							</select>
						</td>
						<td width="22%" align="left" valign="middle" style="padding-left:2em;">
							<label class="label">Assignment&nbsp;&nbsp;&nbsp;</label>
							<select class="listmenu" id="usedFor"  onChange="showStudents()" required="required">
								<option value="">Select Assignment</option>
							</select>
						</td>	
						<td width='30%'>&nbsp;</td>									
					</tr>
				</table>
			</td>
			<tr>
				<td colspan="2" align="center" valign="top" bgcolor="#cccccc"></td>
			</tr>
			<tr>
				<td id="appenddiv2" height="30" colspan="7" align="center" valign="middle">
					<font color="blue">
						<label id=returnMessage style="visibility: visible;">${helloAjax}</label>
					</font>
				</td>
			</tr>
			<tr>
				<td height="30" colspan="2"></td>
			</tr>
		</table>
		<table style="width=100%;">
			<tr>
				<td width="100%">
					<div id="studentDiv" ></div>
				</td>
			</tr>
		</table>
		<div id="gradeDiv"></div>
		</form:form>
	</body>
</html>