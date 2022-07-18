<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<title>Composite Chart</title>
		<!-- <link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
		<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
		<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
		<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
		
		<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
		<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
		<script type="text/javascript" src="resources/javascript/teacher_popup.js"></script>-->
		
		<!-- <script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>  -->
		<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
				$('#returnMessage').fadeOut(3000);
			});
			function getCompositeChartValues() {
				var csId = $('#csId').val();
				var compositChartContainer = $(document
						.getElementById('compositChartDiv'));
				$(compositChartContainer).empty();
				var classId = $('#classId').val();
				var gradeId = $('#gradeId').val();
				if(gradeId > 0 && classId > 0 && csId > 0){
				 	$("#loading-div-background").show();
					$.ajax({
						type : "GET",
						url : "getCompositeChartValues.htm",
						data : "csId=" + csId,
						success : function(response) {
							$(compositChartContainer).append(response);
							$("#loading-div-background").hide();
						}
					});
				}else{
					alert("Please fill all the filters");
				}
			}
			
			$(document).ready(function () {
			 	$('#returnMessage').fadeOut(5000);
			});
			
			function clearDiv(){
				$("#compositChartDiv").empty();
			}
			
		</script>
	</head>
	<body>
		<form:form name="gradeBook" action="gradeBookSubmit.htm" modelAttribute="studentAssignmentStatus" method="post">
			<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad heading-up">
         <tr>
		 <td height="30" colspan="2" align="left" valign="middle"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
  
	     <tr>
	   		
							<td width="14%" colspan="2">
								<label class="label">Grade&nbsp;&nbsp;&nbsp;</label>
								
									<select name="" class="listmenu" id="gradeId" 
										onChange="getGradeClasses();clearDiv();" required="required">
										<option value="">select grade</option>
										<c:forEach items="${grList}" var="ul">
											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
										</c:forEach>
									</select>
								</td>
								<td width="14%" align="left" valign="middle">
									<label class="label">Class&nbsp;&nbsp;&nbsp;</label>
								
									<select id="classId" name="classId" class="listmenu" 
										onChange="getClassSections();clearDiv();" required="required">
										<option value="">select subject</option>
									</select>
								</td>
	
								<td width="14%" align="left" valign="middle">
									<label class="label">Section&nbsp;&nbsp;&nbsp;</label>
								
									<select id="csId" class="listmenu" onChange="getCompositeChartValues();clearDiv();" required="required">
										<option value="">select Section</option>
									</select>
								</td>
								<td width='40%'>&nbsp;</td>	
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="top" bgcolor="#cccccc"></td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td height="2" colspan="5" align="center">
						<div id="compositChartDiv"></div>
					</td>
				</tr>
				<tr>
					<td id="appenddiv2" height="30" colspan="7" align="center" valign="middle">
						<font color="blue"> <label id=returnMessage style="visibility: visible;">${returnMessage}</label></font>
					</td>
				</tr>
				<tr>
					<td height="30" colspan="2"></td>
				</tr>
			</table>
			<div id="gradeDiv"></div>
		</form:form>
	</body>
</html>