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
		<!-- <script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
		<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
		<script type="text/javascript" src="resources/javascript/teacher_popup.js"></script>		
		<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> -->
		<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
		<script src="/resources/javascript/notify/notify.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function () {
				 $('#returnMessage').fadeOut(3000);
			});
			
			$(function() {
			   $( "#fromId" ).datepicker({
			    	changeMonth: true,
			        changeYear: true,
			        showAnim : 'clip'
			    });
			    $( "#toId" ).datepicker({
			    	changeMonth: true,
			        changeYear: true,
			        showAnim : 'clip'
			    });
			});
		
			function getReports(){
				if(document.getElementById("getResult").checked == true){
					var gradeId = $('#gradeId').val();
					var classId = $('#classId').val();
					var csId = $('#csId').val();
					var fromId = $('#fromId').val();
					var toId = $('#toId').val();
					var reportContainer = $(document.getElementById('reportDiv'));
					$(reportContainer).empty(); 
					if(gradeId == ""){
						alert("Please select grade");
						return;
					}
					if(classId == ""){
						alert("Please select class");
						return;
					}
					if(csId == "" || csId == 'select'){
						alert("Please select section");
						return;
					}
					if(fromId == ""){
						alert("Please select from-date");
						return;
					}
					if(toId == ""){
						alert("Please select to-date");
						return;
					}
					if(gradeId > 0 && classId > 0 && csId > 0){	
						$("#loading-div-background").show();
						$.ajax({
							type : "GET",
							url : "getReportStudentList.htm",
							data : "csId=" + csId+"&fromId="+ fromId+"&toId="+ toId,
							success : function(response) {
								$(reportContainer).append(response); 
								 $("#loading-div-background").hide();
							}
						});
					}else{
						alert("Please select the filters");
					}
				}		
			}
		
			function clearDiv(){
				$("#getResult").removeAttr('checked');
				$("#reportDiv").empty(); 
			}
		
		</script>
	</head>
	<body>
		<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0  class="title-pad" style="padding-top: 1em;">
			<tr>
				<td>
					<label class="label">Grade&nbsp;&nbsp;</label> 
		   			<select name="gradeId" class="listmenu" id="gradeId" onChange="clearDiv();getGradeClasses()" required="required">
						<option value="">select grade</option>
						<c:forEach items="${grList}" var="ul">
							<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
						</c:forEach>
					</select>
		   		</td>
		   		<td><label class="label">Class&nbsp;&nbsp;</label> 
		   			<select id="classId" name="classId" class="listmenu" onChange="clearDiv();getClassSections()"
												required="required">
						<option value="">select subject</option>
					</select>
				</td>
				<td><label class="label">Section&nbsp;&nbsp;</label> 
					<select id="csId" class="listmenu" onChange="clearDiv();"
													required="required">
						<option value="">select Section</option>
					</select>
				</td>
				<td><label class="label"> Date Range &nbsp;From&nbsp;&nbsp;</label>
					<input type="text" id="fromId" readonly="readonly" onchange="clearDiv()" style="width:7em;"/>
					<label class="label"> To&nbsp;&nbsp;</label>
					<input type="text" id="toId" readonly="readonly" onchange="clearDiv()" style="width:7em;"/>
				</td>	
				<td>
					<input type="radio" id="getResult" class="radio-design" onclick="getReports()"/><label for="getResult" class="radio-label-design label">Get Results</label>&nbsp;&nbsp;
				</td>			
		   </tr>
		</table>
		<table width="84%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td height="2" width="100%" colspan="14" align='left'>
					<div id="reportDiv"></div>
				</td>
			</tr>
			<tr>
				<td height="30" colspan="2"></td>
			</tr>
		</table>
		<div id="gradeDiv"></div>
	</body>
</html>