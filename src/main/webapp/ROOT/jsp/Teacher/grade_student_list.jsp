<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<script type="text/javascript">

function showStudentGrades(studentId){
	var usedFor = $('#usedFor').val();
	$.ajax({
		type : "GET",
		url : "getStudentGrades.htm",
		data : "studentId=" + studentId+"&usedFor="+ usedFor,
		success : function(response) {
			var gradeContainer = $(document.getElementById('gradeDiv'));
			var screenWidth = $( window ).width() - 50;
			var screenHeight = $( window ).height() - 10;
			$('#gradeDiv').empty();  
			$(gradeContainer).append(response);
			$(gradeContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
				  $(".ui-dialog-titlebar-close").show();
				},
				close : function(event, ui) {
					$(this).empty();
					$('#gradeDiv').dialog('destroy');
					
				},
				dialogClass: 'myTitleClass'
			});				
			$(gradeContainer).dialog("option", "title", "Student Grades");
			$(gradeContainer).scrollTop("0");
		}
	});
}

function updateGrades() {
	var formObj = document.getElementById("editGradeForm");
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
			$('#gradeDiv').dialog('close'); 
			showStudents();
		}
	});		
	return false;
}

</script>


</head>
<body>
<div align="center">
<table class="des" border=0 style="width:100%;align:center"><tr><td>
<div class="Divheads">
 	<table align="center"><tr>
				<th  align="left" width="200">Student name</th>
				<th  align="left" width="200">Student's grade average</th>								
			</tr>
	</table>
	</div>
	<div style="padding: 2px 5px 2px 5px"><table align=center> 
	
			<c:forEach items="${studentList}" var="cList">					
				<tr >
					<td  align="left" width="200"  style="padding-top: 1em;" class="txtStyle">
						<a href="#" onclick="showStudentGrades(${cList.student.studentId})" style="color:#021721;">
							${cList.student.userRegistration.firstName} ${cList.student.userRegistration.lastName}</a>
					</td>					
					<td  align="center" width="200"  style="padding-top: 1em">
					<c:choose>
						<c:when test="${studentPercentage[cList.student.studentId] >= 0 }">
							<fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${studentPercentage[cList.student.studentId]}" />
							${formattedPercentage}
						</c:when>
						<c:otherwise>
							0.0
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:forEach>
	</table>
	</div>
			
		</td></tr></table>
		</div>
</body>
</html>