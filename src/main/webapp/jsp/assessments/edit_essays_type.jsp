<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="hidden" id="id" name ="id" value="" />
<input type="file" id="file" name="files" style="visibility:hidden;"/>
<input type="hidden" id="questionId" name="questionId" value="0">
<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
<input type="hidden" id="assignmentId" name="assignmentId" value="">
<table width="45%" class="des" border=0 align="center" ><tr><td>
<div class="Divheads"><table align="center"><tr><td class="headsColor" align="center"><font size="4">Edit your questions</font> </td></tr></table></div>
<br>
<div class="DivContents">

<table width="100%" align="left" style="padding-left:3em;padding-bottom:1em;">
<c:if test="${count>0}">
	 <c:forEach var="i" begin="0" end="${noOfQuestions}" step="1" varStatus="status">
		<c:set var="qNumber" value="${noOfQuestions-i}" scope="page"></c:set>
		<tr>
			<td width="80%" align="left" height="50">${status.count}&nbsp;.&nbsp;
				<form:textarea path="questionsList.questions[${qNumber}].question" id="question${qNumber}" required="required" rows="2" cols="60" onchange="updateQuestion(${qNumber})" />
				<form:hidden path="questionsList.questions[${qNumber}].questionId" id="questionId${qNumber}"/>
				<form:hidden path="questionsList.questions[${qNumber}].assignmentType.assignmentTypeId" id="assignmentTypeId${qNumber}"/>
				<form:hidden path="questionsList.questions[${qNumber}].lesson.lessonId" id="lessonId${qNumber}"/>
				<form:hidden path="questionsList.questions[${qNumber}].usedFor" id="usedFor${qNumber}"/>
				<form:hidden path="questionsList.questions[${qNumber}].createdBy" id="createdBy${qNumber}"/>
			</td>
		    <td width='10%' align='left' style='padding-left:1em;'><i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeAssessments(${qNumber})"></i></td>
		    <td width="30%" height="15" align="center" colspan="4">
        		<b><div id="resultDiv${qNumber}"  class="status-message"></div></b>
       		</td>
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
