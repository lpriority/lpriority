<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<script type="text/javascript">
</script>
</head>
<body>
	<table align="center" id="shows" style="visibility: hidden;"><tr>
		<td><div id="msg" style="color:#0000FF"></div></td></tr>
	</table>
<div id="jactemplate" style="visibility: visible;">
	<form:form name="studentassignmentform" modelAttribute="studentAssignmentStatus">
		<table border=0 align="center">
			<tr>
				<td>&nbsp;
				<input type="hidden" id="studentAssignmentId" value="${studentAssignmentId}"/>
				<input type="hidden" id="assignmentId" value="${assignmentId}"/>
		        <input type="hidden"  id="assignmentTypeId" value="${assignmentTypeId}" />
		        <input type="hidden" value="${usedFor}" id="usedFor" />
		       </td>
			</tr>
			<tr>
				<td class="tabtxt">Question File :<a href="downloadFile.htm?filePath=${jacQuestionFilePath}"><c:out value="${jacTitles[0].jacQuestionFile.filename}"></c:out></a>
				</td>
			</tr>
			</table>
			 <table border=1 align="center">
             <tr><td class="tabtxt">Title</td><td class="tabtxt">Student Answers</td><td class="tabtxt">Correct Answer</td><td class="tabtxt">Marks</td></tr>
		
			<c:set var="a" value="0" />
			<c:set var="b" value="0" />
			<c:set var="s" value="0" />
			<c:forEach items="${jacTitles}" var="jtl">
			<tr>
				<td class="txtStyle">
												
				<input type="hidden" name="titleid" id='titleid' value="${jtl.jacTemplateId}" /> 
					<c:out value="${jtl.titleName}"></c:out>
				</td>
				<td class="txtStyle">
				<c:forEach var="n" begin="0" end="${jtl.noOfQuestions-1}" >
				<c:choose>
				<c:when test="${testQuestions[a].secMarks==1}"> 
				<input type='text' name='answer' value='${testQuestions[a].answer}' id='answer' size='35' readonly /><br>
				 </c:when>
				 <c:otherwise>
				 <input type='text' name='answer' value='${testQuestions[a].answer}' style='color:red' id='answer' size='35' readonly /><br>
				 </c:otherwise>
				 </c:choose>
				  <c:set var="a" value="${a+1}" />
				</c:forEach>
				</td>
				<td class="txtStyle">
				<c:forEach var="n" begin="0" end="${jtl.noOfQuestions-1}" >
				<input type='text' name='answer' value='${testQuestions[b].questions.answer}' id='originalAnswer' size='35' readonly/><br>
				 <c:set var="b" value="${b+1}" />
				</c:forEach>
				 </td><td class="txtStyle">
				 <c:forEach var="n" begin="0" end="${jtl.noOfQuestions-1}" >
				 <input type="hidden" name="qid" id="qid${s}" value="${testQuestions[s].assignmentQuestionsId}" />
				 <input type='text' name='marks' value='${testQuestions[s].secMarks}' id='marks${s}' size='20' onblur="updateJacMarks(${s})"/><br>
				 <c:set var="s" value="${s+1}" />
				 </c:forEach>
				 </td>
			</tr>
			</c:forEach>
			</table>

			 <table align="center">
			<tr>

				<td height='20' align='center' valign='middle' class='tabtxt'><table
						style="">
						<tr>
							<td colspan='3' width='71' height='2' align='left'
								valign='middle'></td>
						</tr>
						<tr>
							<td width='50%' align='right' valign='middle'></td>
							<td width='0' align='left' valign='middle'>
								<input type="submit" class="button_green" id="btSubmitChanges" name="btSubmitChanges" height="52" width="50" value="Submit Changes" onclick="submitJacTemplate(${assignmentId}); return  false;" >
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
		
		
	</form:form>
	</div>
</body>
</html>