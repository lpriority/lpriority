<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<input type="hidden" id="id" name ="id" value="" />
<input type="file" id="file" name="files" style="visibility:hidden;"/>
<input type="hidden" id="questionId" name="questionId" value="0">
<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
<input type="hidden" id="assignmentId" name="assignmentId" value="">
<table width="50%" class="des" border=0 align="center"><tr><td>
<div class="Divheads"><table align="center"><tr><td class="headsColor" align="center" style=""><font size="4">Edit your questions</font> </td></tr></table></div>
<br>
<div class="DivContents">
<table style="padding-left:5em" width="100%">
<c:if test="${quesCount > 0}">
	 <c:forEach var="i" begin="0" end="${numOfOptions}" step="1" varStatus="status">
		<c:set var="qNumber" value="${numOfOptions-i}" scope="page"></c:set>
		<tr height="30">																
		   <td  width="425" height="35" align="left" valign="middle" style="">
				${status.count}&nbsp;.&nbsp;<a href="javascript:showQuestions(${questionsList.subQuestions[qNumber].subQuestionId});" style="width: 40px;word-wrap: break-word; overflow-wrap: break-word;text-decoration:none;color:#004379;"><font size="3"><c:out value="${empty questionsList.subQuestions[qNumber].title ?fn:substring(questionsList.subQuestions[qNumber].subQuestion, 0, 50):questionsList.subQuestions[qNumber].title}"></c:out>...</font></a>&nbsp;&nbsp;
		   </td>
		    <td width="30" height="15" align="left" colspan="4" >
		    	<i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeAssessments(${questionsList.subQuestions[qNumber].subQuestionId})"></i>
           </td>
 	       <td width="225" height="15" align="left" colspan="4">
        		<b><div id="resultDiv${questionsList.subQuestions[qNumber].subQuestionId}"  class="status-message"></div></b>
           </td>	
		</tr>			
	</c:forEach>  
</c:if>	</table></div><div class="DivContents"><table align="center">
<c:if test="${quesCount == 0}">
	<tr>
		<td width="100%" align="center" class="status-message" style="font-weight: 400;">
			${helloAjax}
		</td>
	</tr>
</c:if>
</table> </div></td></tr></table>