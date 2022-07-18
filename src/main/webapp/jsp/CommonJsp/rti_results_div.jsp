<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<div align="center">
<table><tr><td>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
<c:if test="${fn:length(testResultsByStudent) > 0}">
<c:set var="bor" value="0"></c:set>
<c:set var="de" value="des"></c:set>
</c:if>
</td></tr></table>
<table border="${bor}" class="${de}"><tr><td>
	<c:choose>
		<c:when test="${type == 'student' && fn:length(testResultsByStudent) gt 0}">
			<div class='Divheads' align="center">
          <table>
			<tr>
				<td width="150">Student Id</td>
				<td width="200">Name</td>
				<td width="250">Answers By Correct Response</td>
				<td width="250">Answers By wrong Response</td>
				<td width="150" align="center">Score</td>
			</tr></table></div>
			<div class="DivContents"><table><tr><td>&nbsp;</td></tr>
			<c:forEach items="${testResultsByStudent}" var="test">
			
				<tr>
					<td width="150" align="left">${test.student.studentId}</td>
					<td width="200" align="left">${test.student.userRegistration.firstName} ${test.student.userRegistration.lastName}</td>
					<td width="250" align="center">${test.answersByRightResponse}</td>
					<td width="250" align="center">${test.answersBYWrongResponse}</td>
					<td width="150" align="center">${test.percentage}</td>
				</tr>
			</c:forEach></table></div>
		</c:when>		
		<c:when test="${type != 'student' && fn:length(testResultsByQuestion) gt 0}">
		<div class='Divheads' align="center">
          <table>
			<tr>
				<td width="150">Question</td>
				<td width="200">Number of Correct Answers</td>
				<td width="250">Percentage of Correct Answers</td>
				<td width="250">Number of Wrong Answers</td>
				<td width="150">Percentage of Wrong Answers</td>
			</tr></table></div>
			<div class="DivContents"><table><tr><td>&nbsp;</td></tr>
			<c:forEach items="${testResultsByQuestion}" var="assignQuestion">
				
				<tr>
					<td width="150" align="left">${assignQuestion.questions.question}</td>
					<td width="200" align="left">${assignQuestion.answersByRightResponse}</td>
					<td width="250" align="center">${assignQuestion.answersByRightResponsePercentage}</td>
					<td width="250" align="center">${assignQuestion.answersBYWrongResponse}</td>
					<td width="150" align="center">${assignQuestion.answersBYWrongResponsePercentage}</td>
				</tr>
			</c:forEach></table></div>			
		</c:when>
		<c:otherwise>
			<tr><td align="center" class="status-message">No data available</td></tr>
		</c:otherwise>
	</c:choose>
</td></tr></table>
</div>