<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Lessons</title>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
 <script type="text/javascript" src="resources/javascript/Student/studentcurriculum.js"></script> 
 
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
	function clearDiv(){
		$("#csId").empty();
		$("#csId").append(
				$("<option></option>").val('select').html('Select Class'));
		$("#curriculumDiv").empty();
	}
	
</script>
</head>
<body>
	 <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
	  <tr><td>   
     <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
            <tr>
                <td style="color:white;font-weight:bold" >
                	<input type="hidden" name="tab" id="tab" value="${tab}">
                </td>
                <td vAlign=bottom align=right>
		        <div> 
		         	<%@ include file="/jsp/CommonJsp/lesson_tab.jsp" %>
		        </div>
     		  </td>
     		</tr>
       </table>
     </td></tr>
  	<tr><td>
	<div>
	<label class="">&nbsp;</label><br />
	</div>
	<%-- <div style="padding-left: 3.5cm;" id ="lessonDiv">
				
		<div style="padding-left: 0px; padding-top: 5px">
			<c:if test="${userReg.user.userType == 'parent'}">
			<label class="label">Child&nbsp;&nbsp;</label>
				<select name="studentId" id="studentId" class="listmenu" onchange="clearDiv();loadGradeClasess()">
					<option value="select">Select Child</option>
					<c:forEach items="${childs}" var="child">
						<option value="${child.studentId}">${child.userRegistration.firstName}${child.userRegistration.lastName}</option>
					</c:forEach>
				</select>&nbsp;&nbsp;&nbsp;
			</c:if><label class="label">Grade&nbsp;&nbsp;</label>
			<input type="text" style="width:100px;" name="gradeName" value="${grade.masterGrades.gradeName}" disabled="disabled" id="gradeName">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label class="label">Class&nbsp;&nbsp;</label>
			<select name="csId" id= "csId" class="listmenu" onchange="getStudentAssignedCurriculum()">
				<option value="select">Select Class</option>
				<c:forEach items="${studentClassList}" var="studentClasses">
					<option value="${studentClasses.classStatus.csId}"> ${studentClasses.gradeClasses.studentClass.className}</option>
				</c:forEach>
			</select>
		</div>
		<div id="curriculumDiv">
		</div> 
	</div> --%>
	<div style="padding-left: 3.5cm;" id ="lessonDiv">
			         	<%@ include file="/jsp/Student/lesson_div.jsp" %>
	</div>
	</td></tr>
	</table>
</body>

</html>