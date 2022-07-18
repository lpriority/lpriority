<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Insert title here</title>
<style>

.des {
	border: 1px solid black;
	border-collapse: collapse;
	border-color: #d3d3d3;
}
.AudioPlay
{
    background-image: url('images/Teacher/audioOver.gif');
    background-color: white;
    background-repeat: no-repeat;
    border: none;
    cursor: pointer;
    width :120px;
    height :35px;
    vertical-align: middle;
    }
    </style>
<script>
var audio = new Audio("");
function playTestAudio(id){
	audio.pause();
	var path = document.getElementById(id).value;
	audio = new Audio(path);
	audio.play();
}

</script>
</head>
<body>
	<table align="center" id="shows" style="visibility: hidden;">
		<tr>
			<td><div id="msg" style="color: #0000FF"></div></td>
		</tr>
	</table>
		<form:form id="testQuestionsForm" modelAttribute="studentAssignmentStatus">
			<c:set var="queCount" value="0"></c:set>
			<c:set var="quedisplayCount" value="1"></c:set>
			<c:set var="optionsCount" value="0" scope="page" />
			<input type="hidden" id="tab" name="tab" value="${tab}" />
			<form:hidden path="studentAssignmentId" />
			<form:hidden path="assignment.assignmentType.assignmentTypeId"
				value="${assignmentTypeId}"></form:hidden>
			<form:hidden path="assignment.usedFor" value="${usedFor}"></form:hidden>
			<table><tr><td width="800" style="padding-left:20em">
			<table border=0 style="padding-left:20em" width="85%" class="des">
			<tr>
				<td>
			<div class="Divheads">
						<table width="60%">
							<tr>
								<td width="50%"><b><c:out
												value="Benchmark Test Results"></c:out> </b></td>
								<td align="right"><br></td>
							</tr>
						</table>
					</div>
			<div style="padding-left: 2em">
			<table style="width:100%">
				<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width='100' height='25' align='left'><b>Test</td>
							<td><b>Mark</td>
						</tr>
						<c:set var="marks" value="0"></c:set>
						<c:set var="coun" value="0"></c:set>
						<c:forEach items="${testQuestions}" var="questionList" varStatus="count">
							<c:forEach items="${questionList.fluencyMarks}" var="typesList">
							<input type="hidden" id="marks" name="marks" value="${typesList.marks}" />
							<c:set var="co" value="${queCount+1}"></c:set>
							<c:set var="yes" value="0"></c:set>
							<c:if test="${empty typesList.marks}">
                                 <c:set var="marks" value="0"></c:set>
                            </c:if>
							<c:if test="${not empty typesList.marks}">
                               <c:set var="marks" value="${typesList.marks}"></c:set>
                           </c:if>
							<tr>
								<td width='100' height='25' align='left'><a
									href='javascript:openChildWindow(${questionList.assignmentQuestionsId},${questionList.studentAssignmentStatus.studentAssignmentId},${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId},${typesList.readingTypes.readingTypesId})'>
									<c:out value="${typesList.readingTypes.readingTypes}" />
											 <c:if
												test="${questionList.studentAssignmentStatus.assignment.benchmarkCategories.isFluencyTest == 1 }">
												<c:out
													value="${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId}. " />
												
											</c:if>
											<c:out value="${co}" />
										</a>
								   </td>
								<td><input type='text' id='marks:${typesList.readingTypes.readingTypesId}' name='marks'
									value='${typesList.marks}' disabled /></td>
								<td width='100' height='25' align='center' valign='middle'>
                                     &nbsp;
                                    </td>
							</tr>

							<c:set var="queCount" value="${queCount+1}"></c:set>
							</c:forEach>
						</c:forEach>
						<tr><td>&nbsp;</td></tr>
						<tr><td width='100' height='25' align='center'>
						</td> 
						<td width='100' align='center'></td> 
		                </tr> </table></div>
		                 </td></tr></table>
						<div id="gradeBenchmark"></div>
						</td></tr></table>
		</form:form>
	
</body>
</html>