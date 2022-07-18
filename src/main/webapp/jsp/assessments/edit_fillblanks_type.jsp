<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<input type="hidden" id="id" name ="id" value="" />
<input type="file" id="file" name="files" style="visibility:hidden;"/>
<input type="hidden" id="questionId" name="questionId" value="0">
<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
<input type="hidden" id="assignmentId" name="assignmentId" value="">
<table width="52%" class="des" border=0 align="center"><tr><td>
<div class="Divheads"><table  align="center"><tr><td class="headsColor" align="center" style=""><font size="4">Edit your questions</font> </td></tr></table></div>
<br>
<div class="DivContents">
<table align="center" width="75%">
<c:if test="${count > 0}">
 <c:forEach var="i" begin="0" end="${noOfQuestions}" step="1" varStatus="status">
		<c:set var="qNumber" value="${noOfQuestions-i}" scope="page"></c:set>
				<form:hidden path="questionsList.questions[${qNumber}].questionId"/>
				<form:hidden path="questionsList.questions[${qNumber}].assignmentType.assignmentTypeId"/>
				<form:hidden path="questionsList.questions[${qNumber}].lesson.lessonId"/>
				<form:hidden path="questionsList.questions[${qNumber}].usedFor"/>
				<form:hidden path="questionsList.questions[${qNumber}].createdBy"/>	
	<tr>
		<td width="120" align="left"><span class="tabtxt">Question ${status.count} :</span></td>
		<td><form:textarea path="questionsList.questions[${qNumber}].question" id="question${qNumber}" required="required" cols="50" rows="2" onchange="updateQuestion(${qNumber})"/></td>
		<td width="60"  height="40" align="left" align='left' colspan='3' style='padding-left:1em'><i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeAssessments(${qNumber})"></i></td>
     	<td width="200" height="15" align="left" colspan="4"><b><div id="resultDiv${qNumber}"  class="status-message"></div></b></td>
	</tr>
	<tr>
		<td width="120" height="40" valign="top" align="left"><span class="tabtxt">Correct Answer :</span></td>
		<td width="200" height="40" valign="top" align="left" style=""><form:input path="questionsList.questions[${qNumber}].answer" id="answer${qNumber}" required="required" onchange="updateQuestion(${qNumber})"/></td>
		<td width='60' height="40" valign="top" align='left'></td>
		<td width="200" height="15" align="left" colspan="4"></td>
	</tr>
	</c:forEach>  
</c:if>	</table></div><div class="DivContents"><table align="center">
<c:if test="${count == 0}">
	<tr>
		<td width="100%" align="center" class="status-message" style="font-weight: 400;">
			${helloAjax}
		</td>
	</tr>
</c:if>
</table> </div></td></tr></table>