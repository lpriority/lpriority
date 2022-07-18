<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>	
<%@ include file="../CommonJsp/include_resources.jsp" %>
 <table><tr><td>
   <table>
    <form:form id="questionsForm"  action="createAssessments.htm" modelAttribute="questionsList" method="post" enctype="multipart/form-data">
		<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}">
		<input type="hidden" id="tab" name="tab" value="${tab}">
		<input type="hidden" id="mode" name="mode" value="${mode}">
		<input type="hidden" id="lessonId" name="lessonId" value="${lessonId}">
		<input type="hidden" id="questionId" name="questionId" value="${questionId}">
		<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
		<input type="hidden" id="assignmentId" name="assignmentId" value="">
		<input type="hidden" id="id" name="id" value="" />
		<input type="file" id="file" name="files" style="visibility:hidden;"/>
		<input type="hidden" id="gradeId" name="gradeId" value="${gradeId}">
     <tr>
		<td width="562" align="left"><span class="tabtxt">Enter ${assignmentValue} Question :</span><br><br></td>
	</tr>
	<tr>
		<td width="562" align="left">&nbsp;&nbsp;<form:textarea rows="2" cols="70" path="questions[0].question" id="question0" required="required" value="" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}" /></td>
	</tr>
	<tr><td height=10 colSpan=3> <form:input type="hidden" path="questions[0].numOfOptions" id ="numOfOptions" value="${questionsList.questions[0].numOfOptions gt 0 ? questionsList.questions[0].numOfOptions : 5}" /></td></tr>
	<tr>
		<td  id="optiontd">
			<table id="table" width="100%">
				<c:forEach begin="1" end="${questionsList.questions[0].numOfOptions gt 0 ? questionsList.questions[0].numOfOptions : 5}" var="count"> 
					<tr>
						<td width="562" align="left"  ><div id="option${count}Div" style="display: block;">&nbsp;&nbsp;option ${count}.&nbsp;<form:input path="questions[0].option${count}" id="option${count}" required="required" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}" /></div></td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
	<tr><td height=10 colSpan=3></td></tr> 
	<tr>
		<td width="562" align="left">Correct Answer.&nbsp;
			<form:select path="questions[0].answer" required="required" id="answer" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}">
				<option value="">Select Option</option>
				<c:forEach begin="1" end="${questionsList.questions[0].numOfOptions gt 0 ? questionsList.questions[0].numOfOptions : 5}" var="count">
					<c:set var="optionValue" value="option${count}" ></c:set>
					<option value="option${count}" ${answer == optionValue ? 'selected="selected"' : ''}>Option ${count}</option>	
				</c:forEach>	
			</form:select>	
			<input type="button" class="subButtons subButtonsWhite medium"  value="Remove Option" onclick="removeMCQOption('multiplechoice',0);" style="width:120px;" />
			<input type="button" class="subButtons subButtonsWhite medium"  value="Add Option" onclick="addMCQOption('multiplechoice',0);"/>
		</td>
	</tr>
	</form:form>
   </table>
  </td></tr>
  <tr><td height=10 colSpan=3></td></tr>  
<c:choose>
  <c:when test="${mode eq 'Create'}"> 
  <tr><td align="center">
             <table width="100%" border="0" cellspacing="1" cellpadding="0">
                 <tr>
                     <td colspan="4" width="50%" height="25" align="right">
                          <div class="button_green" id="btCreate" name="btAdd" height="52" width="50" onclick="createQuestions()" >Create</div>
                     </td>
                     <td width="5%" align="center"></td>
                     <td colspan="4" width="50%" align="left" valign="middle">
                        <div class="button_green"  onclick="$('#questionsForm')[0].reset(); return false;">Clear</div>
                     </td>
                 </tr>
             </table></td>
      </tr>
   </c:when>
   <c:when test="${mode eq 'Edit'}"> 
   <tr><td align="left" height="15"><font color="#8cb4cd">* Changes will apply automatically</font> </td></tr>
   </c:when>
  </c:choose> 
      <tr><td height=5 colSpan=3></td></tr>  
       <tr>
           <td width="325" height="25" align="center" colspan="5">
           	<div id="resultDiv0" class="status-message" ></div><div id="resultDiv" class="status-message" ></div>
           	<!-- 	<font color="blue" size="3"><b><div id="resultDiv0" ></div></b></font><font color="blue" size="3"><b><div id="resultDiv" ></div></b></font> -->
           </td>
     	</tr>
	</table>
