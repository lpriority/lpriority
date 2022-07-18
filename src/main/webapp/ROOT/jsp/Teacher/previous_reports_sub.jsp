

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Previous Progress Reports</title>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<style type="text/css">
#atten, #module {
    border: 0px solid black;
}
</style>
<script type="text/javascript">
function saveComments(){
	var comment = document.getElementById("comments").value;
	var reportId = document.getElementById("reportId").value;
	$.ajax({
		type : "GET",
		url : "saveParentComments.htm",
		data : "reportId="+ reportId+"&comment="+ comment,
		success : function(response) {
			if(response.status){
				alert("Comment saved successfully");
			}else{
				alert("Comment not saved successfully");
			}
		}
	});
}
</script>
</head>
<body>
<div align="center">
<table align="center" class="txtStyle des">
		<tr>
			<td colspan="30" align="center" class='Divheads'>
				<font size="3"><b>Student Progress Report</b></font>
				<input type="hidden" id="reportId" value="${studentReport.reportId}">
			</td>
		</tr>
		<tr>
			<td colspan="30" align="left" style="padding-top:2em;">
				<b>${studentReport.student.userRegistration.school.schoolName}</b>
				<hr>
			</td>
		</tr>
		<tr>
			<td colspan="25" align="left">
				Student: <i>${studentReport.student.userRegistration.firstName} ${studentReport.student.userRegistration.lastName}</i>
			</td>
			<td colspan="25" align="left">
				Class: <i>${studentReport.classStatus.section.gradeClasses.studentClass.className}</i>
			</td>
		</tr>
		<tr>
			<td colspan="25" align="left">
				Teacher: <i>${studentReport.classStatus.teacher.userRegistration.firstName} ${studentReport.classStatus.teacher.userRegistration.lastName}</i>
			</td>
			<td colspan="25" align="left">
				Date: <i>${studentReport.releaseDate}</i><br><br>
			</td>
		</tr>
		<tr>
			<td colspan="20">
				<table id="module" style="width: 10cm; height: 8cm;">
					<tr>
						<th style="background-color: #adc7d0;color:#00293b;">
							<b>Module</b>
						</th>
						<th style="background-color: #adc7d0;color:#00293b;">
							<b>Result</b>
						</th>
					</tr>
					<tr>
						<td>
							Academic Grade
						</td>
						<td>
							${studentReport.academicGrades.acedamicGradeName}
						</td>
					</tr>
					<tr>
						<td>
							Citizenship
						</td>
						<td>
							${studentReport.citizenship.citizenshipName} 
						</td>
					</tr>	
					<tr>
						<td>
							Assessments 
						</td>
						<td>
							<fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${studentReport.assignmentPerc}" />
								${formattedPercentage}% 
						</td>
					</tr>
					<tr>
						<td>
							Homework 
						</td>
						<td>
							<fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${studentReport.homeworkPerc}" />
								${formattedPercentage}% 
						</td>
					</tr>
					<tr>
						<td>
							Performance Task 
						</td>
						<td>
							<fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${studentReport.performancePerc}" />
								${formattedPercentage}% 							 
						</td>
					</tr>
					<tr>
						<td>
							Teacher Comments 
						</td>
						<td>
							${studentReport.comments.comment} 
						</td>
					</tr>					
				</table>
			</td>
			<td colspan="20">
				<table id="atten"  style="width: 8cm; height: 8cm; padding-left: 20px;">
					<tr>
						<th colspan="2" style="background-color: #adc7d0;color:#00293b;">
							<b>Summery Of Attendance </b>
						</th>
					</tr>
					<tr>
						<td>
							Present
						</td>
						<td>
							${studentReport.presentdays}
						</td>
					</tr>
					<tr>
						<td>
							Absent
						</td>
						<td>
							${studentReport.absentdays} 
						</td>
					</tr>	
					<tr>
						<td>
							Excused Absent 
						</td>
						<td>
							${studentReport.excusedabsentdays}
						</td>
					</tr>
					<tr>
						<td>
							Tardy 
						</td>
						<td>
							${studentReport.tardydays}
						</td>
					</tr>	
					<tr>
						<td>
							Excused Tardy
						</td>
						<td>
							${studentReport.excusedtardydays}
						</td>
					</tr>			
				</table>
			</td>
		</tr>
		<c:choose>
	    	<c:when test="${userReg.user.userType=='parent'}">
	       		<tr>
					<td style="padding-top: 20px;" align="center" colspan="30">
						Parent Comments: &nbsp; <textarea id="comments" rows="1" cols="30">${studentReport.parentComments}</textarea>
					</td>
				</tr>
				<tr>
					<td style="padding-top: 20px;" align="center" colspan="30">
						<input type="image" onclick="saveComments()" src="images/Common/submitChangesUp.gif">
					</td>
				</tr>
	    	</c:when>  
			<c:otherwise>								
				<tr>
					<td style="padding-top: 20px;" align="center" colspan="30">
						Parent Comments: 
						<i>
							<c:choose>
								<c:when test="${studentReport.parentComments != null}">
									${studentReport.parentComments}
								</c:when>
								<c:otherwise>
									&nbsp;No Parent Comments yet.
								</c:otherwise>
							</c:choose> </i>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>			
	</table>
	</div>

</body>
</html>