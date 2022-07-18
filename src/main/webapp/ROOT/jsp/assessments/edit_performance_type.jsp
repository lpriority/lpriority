<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<input type="hidden" id="id" name ="id" value="" />
<input type="file" id="file" name="files" style="visibility:hidden;"/>
<input type="hidden" id="questionId" name="questionId" value="0">
<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
<input type="hidden" id="assignmentId" name="assignmentId" value="">
<table width="40%" class="des" border=0 align="center"><tr><td>
<div class="Divheads"><table  align="center"><tr><td class="headsColor" align="center" style=""><font size="4">Edit your questions</font> </td></tr></table></div>
<br>
<div class="DivContents">
<table style="padding-left:5em" width="100%">
<c:if test="${count > 0}">
	 <c:forEach var="i" begin="0" end="${noOfQuestions}" step="1" varStatus="status">
		<c:set var="qNumber" value="${noOfQuestions-i}" scope="page"></c:set>
		<tr height="30">																
		   <td  width="70%" height="35" align="left" valign="middle" style="">
				${status.count}&nbsp;.&nbsp;<a href="javascript:showQuestions(${questions[qNumber].questionId});" style="width: 40px;word-wrap: break-word; overflow-wrap: break-word;color:#004379;"><font size="3">${questions[qNumber].ptName}</font></a>&nbsp;&nbsp;
				
				<form:hidden path="questionsList.questions[${qNumber}].questionId"/>
				<form:hidden path="questionsList.questions[${qNumber}].assignmentType.assignmentTypeId"/>
				<form:hidden path="questionsList.questions[${qNumber}].lesson.lessonId"/>
				<form:hidden path="questionsList.questions[${qNumber}].usedFor"/>
				<form:hidden path="questionsList.questions[${qNumber}].createdBy"/>		
		   </td>
		    <td width="5%" height="15" align="left" colspan="4" >
		   		 <i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeAssessments(${qNumber})"></i>
           </td>
 	       <td width="25%" height="15" align="left" colspan="4">
        		<b><div id="resultDiv${qNumber}" ></div></b>
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