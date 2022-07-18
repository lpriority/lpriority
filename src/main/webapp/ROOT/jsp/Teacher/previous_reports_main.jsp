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
		<!-- <link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
		<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
		<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
		<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
		<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
		<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
		<script type="text/javascript" src="resources/javascript/teacher_popup.js"></script> -->
		<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
		
		<style type="text/css">
			#atten, #module {
			    border: 0px solid black;
			}
		</style>
		
		<script type="text/javascript">
			function loadReportDates(callback){
				var csId = $('#csId').val();
				var studentId = $('#studentId').val();
				var previousContainer = $(document.getElementById('previousDiv'));
				$(previousContainer).empty(); 
				$("#dateId").empty();
				$("#dateId").append(
						$("<option></option>").val('select').html('Select Date'));
				if(csId == "" || csId == "select"){
					alert("Please select a section");
					return;
				}
				if(studentId > 0){
					$.ajax({
						type : "GET",
						url : "getReportDates.htm",
						data : "csId="+ csId+"&studentId="+studentId,
						success : function(response) {
							if(response.size == 0){
								alert("No reports found for this student");
							}else{
								var reports = response.reports;				
								$.each(reports,
									function(index, value) {
										var d = new Date(value.releaseDate);
										var dateFormat = d.getMonth()+1+"/"+d.getDate()+"/"+d.getFullYear();
										$("#dateId").append($("<option></option>").val(value.reportId).html(dateFormat));
								});				
							}
							if(callback)
								callback();
						}
					});
				}
			}
			
			function showPrevious(){
				var reportId = $('#dateId').val();
				var csId = $('#csId').val();
				var studentId = $('#studentId').val();
				var classId = $('#classId').val();
				var gradeId = $('#gradeId').val();
				var previousContainer = $(document.getElementById('previousDiv'));
				$(previousContainer).empty();  
				if(reportId > 0 && csId > 0 && studentId > 0 && classId >0 && gradeId >0){
					$("#loading-div-background").show();
					$.ajax({
						type : "GET",
						url : "getPreviousReports.htm",
						data : "reportId="+ reportId,
						success : function(response) {
							$(previousContainer).append(response);
							$("#loading-div-background").hide();
						}
					});
				}else{
					alert("Please fill all the filters");
				}
			}
			
			function clearDiv(){
				$("#dateId").empty();
				$("#dateId").append(
						$("<option></option>").val('').html('Select Date'));
				$("#previousDiv").empty();
			}
		</script>
	</head>
	<body>
		<table width="100%" align=right>
	       <tr>
		       <td style="color:white;font-weight:bold" >
	                <input type="hidden" id="tab" name ="tab" value="${tab}" />
	           </td>
	       </tr>
	    </table>
		<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad heading-up">
			<td height="30" colspan="2" align="left" valign="middle">
				<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0"  class="heading-up">
					<tr>
				   		<td width="18%" colspan="2">
				   			<label class="label">Grade&nbsp;&nbsp;&nbsp;</label>
				   			<select name="gradeId" class="listmenu" id="gradeId" onChange="getGradeClasses();clearDiv();" required="required">
								<option value="">select grade</option>
								<c:forEach items="${grList}" var="ul">
									<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
								</c:forEach>
							</select>
				   		</td>
				   		<td width="18%" align='left' valign='middle'> <label class="label">Class&nbsp;&nbsp;&nbsp;</label>
				   			<select id="classId" name="classId" class="listmenu" onChange="getClassSections();clearDiv();" required="required">
								<option value="">select subject</option>
							</select>
						</td>
						<td width="18%" align='left' valign='middle'><label class="label">Section&nbsp;&nbsp;&nbsp;</label>
							<select id="csId" class="listmenu" onChange="getStudentsBySection();clearDiv();" required="required">
								<option value="">select Section</option>
							</select>
						</td>
						<td width="18%" align='left' valign='middle'><label class="label">Student&nbsp;&nbsp;&nbsp;</label>
							<select id="studentId" class="listmenu" onChange="loadReportDates()" required="required">
								<option value="">Select Student</option>
							</select>
						</td>
						<td width="18%" align='left' valign='middle'><label class="label"> Date&nbsp;&nbsp;&nbsp;</label>
							<select id="dateId" class="listmenu" onChange="showPrevious()" required="required">
								<option value="">Select Date</option>
							</select>
						</td>	
						<td width='20%'>&nbsp;</td>	
					</tr>
					<tr>
						<td height="30" colspan="2">&nbsp;<br><br></td>
					</tr>
				</table>
			</td>
		</table>
		<div id="previousDiv"></div>
	</body>
</html>