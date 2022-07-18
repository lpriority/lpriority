<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Insert title here</title>

<script>
function goToDiv(i){
	  document.getElementById("sho"+i).scrollIntoView(true);
	}
</script>
<script src="resources/javascript/TeacherJs/grade_assessments.js"></script>
<style>
.index{
font-family: "Georgia";
	font-size: 15px;
}
.indexNo{
font-family: "Georgia";
	font-size: 17px;
}
 .tts{
     font-family: 'Big Caslon', 'Book Antiqua', 'Palatino Linotype', Georgia, serif;
    font-size: 15px;
    font-weight: bold;
 }
.ui-dialog > .ui-widget-content {background: white;}
</style>
</head>
<body>
	<table align="center" id="shows" style="visibility: hidden;" class="txtStyle">
		<tr>
			<td><div id="msg" style="color: #0000FF"></div></td>
		</tr>
	</table>
<!-- 	<div id="testQuestions" style="visibility: visible;"> -->
<table border=0 align="center" width="60%" class="des">
			<tr>
				<td>
		<form:form id="testQuestionsForm" modelAttribute="studentAssignmentStatus">
			<c:set var="queCount" value="0"></c:set>
			<c:set var="quedisplayCount" value="1"></c:set>
			<c:set var="optionsCount" value="0" scope="page" />
			<input type="hidden" id="lessonId" name="lessonId" value="${lessonId}" />
			<input type="hidden" id="studentId" name="studentId" value="${studentId}" />
			<input type="hidden" id="assignmentId" name="assignmentId" value="${assignmentId}" />
			<input type="hidden" id="tab" name="tab" value="${tab}" />
			<form:hidden path="studentAssignmentId" />
			<form:hidden path="assignment.assignmentType.assignmentTypeId"
				value="${assignmentTypeId}"></form:hidden>
			<form:hidden path="assignment.usedFor" value="${usedFor}"></form:hidden>
			
			
			<div class="Divheads">
						<table width="60%"><tr>
								<td width="50%"><font color="" class="index">
								<strong><c:out value="${assignmentType.assignmentType}"></c:out> </strong></font></td>
								<td align="right"><br></td>
							</tr>
						</table>
					</div>
				<c:choose>
					
					<c:when test="${assignmentTypeId==8 or assignmentTypeId==20}">
					<br><input type="hidden" id="csId" name="csId" value="${csId}" />
					<tr><td class="tabtxt" style="padding-left:2em">Select Grading&nbsp;&nbsp;&nbsp;: &nbsp; <select name="gradeTypeId"
												class="listmenu" id="gradeTypeId"
												onChange="getGradingDetails(${studentAssignmentId},${userTypeId})" required="required">
													<option value="0">select GradeType</option>
													<c:forEach items="${gradingTypes}" var="ul">
														<option value="${ul.gradingTypesId}">${ul.gradingTypes}</option>
													</c:forEach>
											</select></td></tr>
											<tr><td>&nbsp;
						<div style="padding-left: 2em" id="displayGrading"></div>
						</td></tr>
						
					</c:when>


				</c:choose>
				
                  <br><br>
				
				
						
						</form:form></td></tr></table>
			
		
<!-- 	</div> -->
</body>
</html>