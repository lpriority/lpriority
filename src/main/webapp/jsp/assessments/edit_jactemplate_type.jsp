<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<table width="40%" class="des" border=0 align="center"><tr><td>
<div class="Divheads"><table align="center" ><tr><td class="headsColor" align="center" style=""><font size="4">Edit your questions</font> </td></tr></table></div>
<br>
<div class="DivContents">
<table style="padding-left:5em;">
<input type="hidden" id="id" name ="id" value="" />
<input type="file" id="file" name="files" style="visibility:hidden;"/>
<input type="hidden" id="questionId" name="questionId" value="0">
<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
<input type="hidden" id="assignmentId" name="assignmentId" value="">
<c:if test="${jacCount>0}">
 <c:forEach var="i" begin="1" end="${jacCount}" step="1" varStatus="status">
	<c:set var="qNumber" value="${jacCount-i}" scope="page"></c:set>
	  <input type="hidden" id="filename${jacFileQuestions[qNumber].jacQuestionFileId}" name ="filename${jacFileQuestions[qNumber].jacQuestionFileId}" value="${jacFileQuestions[qNumber].filename}"/>
		<tr height="30">																
			<td width="300" align="left">
				${status.index}&nbsp;.&nbsp;<a href="javascript:showQuestions(${jacFileQuestions[qNumber].jacQuestionFileId});"><c:choose><c:when test="${!empty jacFileQuestions[qNumber].filename}">${jacFileQuestions[qNumber].filename}</c:when><c:otherwise>Passage ${jacFileQuestions[qNumber].jacQuestionFileId}</c:otherwise></c:choose></a><br><br>
				<div id="jacDiv${status.index}"></div>
			</td>
			 <td width='60' align='left'>
			 	<i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeAssessments(${jacFileQuestions[qNumber].jacQuestionFileId})"></i>
			 </td>
		    <td width="120" height="15" align="left" colspan="4">
        		<b><div id="resultDiv${jacFileQuestions[qNumber].jacQuestionFileId}" ></div></b>
       		</td>	
		</tr>
	</c:forEach>  
</c:if>	</table></div><div class="DivContents"><table align="center">
<c:if test="${jacCount == 0}">
	<tr>
		<td width="100%" align="center" class="status-message" style="font-weight: 400;">
			${helloAjax}
		</td>
	</tr>
</c:if>
</table> </div></td></tr></table>