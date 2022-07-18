<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<c:if test="${divId ne 'RequestForClass'}">
	<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
</c:if>
<script type="text/javascript" src="resources/javascript/TeacherJs/TeacherViewClass.js"></script>

<script>
$(function() {
    $( "#dateToUpdate").datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
        maxDate : 0
    });
});

function clearDiv(divId){
	$('#commonDetailsPage').html("");
}
</script>
<c:if test="${divId eq 'RequestForClass'}">
<script>
 function getGradeClasses() {
 	var gradeId = $('#gradeId').val();
 	if(gradeId != 'select'){	
 		$.ajax({
 			type : "GET",
 			url : "getTeacherClasses.htm",
 			data : "gradeId=" + gradeId,
 			success : function(response) {
 				$("#classId").empty();
 				$("#classId").append($("<option></option>").val('select').html('Select Subject'));
 				var teacherClasses = response.teacherClasses;
 				$.each(teacherClasses, function(index, value) {
 					$("#classId").append($("<option></option>").val(value.classId).html(value.className));
 				});
 			}
 		});
 	}else{
// 		$("#classId").empty();
// 		$("#classId").append($("<option></option>").val('select').html('Select Subject'));
 	}
 }
 
 function loadTeacherSubjects(){
	 var gradeId = $('#gradeId').val();
	 	if(gradeId != 'select'){	
	 		$.ajax({
	 			type : "GET",
	 			url : "getTeacherSubjects.htm",
	 			data : "gradeId=" + gradeId,
	 			success : function(response) {
	 				$("#classId").empty();
	 				$("#classId").append($("<option></option>").val('select').html('Select Subject'));
	 				var teacherClasses = response.teacherClasses;
	 				$.each(teacherClasses, function(index, value) {
	 					$("#classId").append($("<option></option>").val(value.classId).html(value.className));
	 				});
	 			}
	 		});
 }
 }
function sendRequestForAClass(){
	var gradeId = dwr.util.getValue('gradeId');
	var classId = dwr.util.getValue('classId');
	if((gradeId == "select") || (classId == "select")){
		alert("Please select the values");
		return false;
	}else{
		$.ajax({
			url : "sendRequestForAClass.htm",
			data: "gradeId="+gradeId+"&classId="+classId,
			success : function(data) {
				alert(data);
				dwr.util.setValue('classId',classId);
			}
		});
	}
}
</script>
</c:if>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>view class header page</title>
</head>
<body>
<input type="hidden" name="divId" id="divId" value="${divId}" />
	<input type="hidden" name="page" id="page" value="${page}" />
	<input type="hidden" name="teacherId" id="teacherId" value="${teacherId}" />
<div id="headerPage" style="width: 100%" align="left">
		<table valign="middle" class="title-pad heading-up">
			<tr>   
				<td height="30" colspan="2" align="center" valign="middle">
					<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<th width="60" align="left" class="label" style="font-size: 15px">Grade</th>
						 <c:choose>
						<c:when test="${divId eq 'RequestForClass'}">
						<td width="30" align="left" valign="middle">
							<select	id="gradeId" name="gradeId" onchange="loadTeacherSubjects();clearDiv('${divId}')" style="width:120px;" class="listmenu">
									<option value="select">select grade</option>
						 		<c:forEach items="${teacherGrades}" var="ul">
									<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
								</c:forEach> 
							</select>
						</td>
						</c:when>
						<c:otherwise>
						<td width="30" align="left" valign="middle">
							<select	id="gradeId" name="gradeId" onchange="getGradeClasses();clearDiv('${divId}')" style="width:120px;" class="listmenu">
									<option value="select">select grade</option>
						 		<c:forEach items="${teacherGrades}" var="ul">
									<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
								</c:forEach> 
							</select>
						</td>
						</c:otherwise>
						</c:choose>
						<th width="120" class="label" style="font-size: 15px">Class</th>
						<td width="30" align="left" valign="middle">
							<select id="classId" name="classId" class="listmenu" style="width:120px;" <c:if test="${divId ne 'RequestForClass'}">onchange="getClassSections();clearDiv('${divId}')"</c:if> class="listmenu">
							 	<option value="select">select subject</option>
							</select>
						</td>
				  <c:choose>
					<c:when test="${divId ne 'RequestForClass'}">
						<th width="120" class="label" style="font-size: 15px">Section</th>
						<td width="50" align="right" valign="right">
						<select id="csId" name="csId" class="listmenu" style="width:120px;" onchange="getStudentDetails()" class="listmenu">
							<option value="select">Select Section</option> 
						</select></td>
						<c:choose>
						<c:when test="${divId eq 'Attendance'}">
						<th width="120" class="label" style="font-size: 15px"> Date </th> 
				   		<td width="50" align="left" valign="middle">
				   		<input name="dateToUpdate" type="text" id="dateToUpdate" size="10" maxlength="15"  value="${updateDate}"   onchange="getStudentDetails()" readonly="readonly"/></td>
						</c:when>
						<c:otherwise>
							<input name="dateToUpdate" type="hidden" id="dateToUpdate" size="10" maxlength="15"  value="" /></td>
						</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="${divId eq 'RequestForClass'}">
					   <td width=60 colSpan=3></td>
					   <td width="162" align="center">
					   	<div class="button_green" align="right" onclick="sendRequestForAClass()">Submit Changes</div> 
					   </td>
					   <td width=60 colSpan=3></td>
                       <td width="80" valign="left">
                      	 <a href="#" onclick="refreshRequest()" class="button_green">Clear</a>
                       </td>
					</c:when>
					<c:otherwise> 
					 	<tr><td width=100 colSpan=10>  <div id="submitDiv" align="center"></div> </td></tr> 
	    				<tr><td width=20 colSpan=3></td></tr> 
	   			    </c:otherwise>
				</c:choose>
					</tr>
	        	</table>
        		</td>
			</tr>
		</table>
	</div>
</body>
</html>