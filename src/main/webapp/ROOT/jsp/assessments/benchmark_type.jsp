<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>	
<%@ include file="../CommonJsp/include_resources.jsp" %>
<style>
.recording{
    font-size: 100%;
    color: green;
    font-family:courier;
}
.stopped{
    font-size: 100%;
    color: red;
    font-family:courier;
}
</style>
<script>

</script>
 <table with="100%"><tr><td>
   <table with="100%">
    <form:form id="questionsForm"  action="createAssessments.htm" modelAttribute="questionsList" method="post" enctype="multipart/form-data">
		<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}">
		<input type="hidden" id="tab" name="tab" value="${tab}">
		<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
		<input type="hidden" id="mode" name="mode" value="${mode}">
		<c:if test="${assignmentTypeId ne 8 and assignmentTypeId ne 20}">
		<input type="hidden" id="lessonId" name="lessonId" value="${lessonId}">
		</c:if>
		<input type="hidden" id="gradeId" name="gradeId" value="${gradeId}">
		<input type="hidden" id="questionId" name="questionId" value="${questionId}">
		<input type="hidden" id="id" name="id" value="" />
		<input type="hidden" id="regId" name="regId" value="${regId}" />
		<input type="hidden" id="audioType" name="audioType" value="" />
		<input type="file" id="file" name="files" style="visibility:hidden;"/>
		<input type="hidden" id="assignmentId" name="assignmentId" value="">
		<span id="flashcontent"></span>
     <tr>
		<td width="500" align="left" colspan="2"><span class="label">Enter ${assignmentValue} :</span><br><br></td>
	</tr>
	<tr height="30">
		<td style="width: 25%" align="right" valign="middle" class="tabtxt"> 
			Enter Title :
		</td>
		<td width="500" align="left" style="padding-left: 1em;padding-bottom: 0.5em;">
			<form:input path="questions[0].BTitle" id="titleName" required="required" maxlength="100" style="width: 250px;" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}" />
		</td>
	</tr>
	<tr height="30">
		<td width="500" align="right" valign="middle" class="tabtxt">
			Enter GradeLevel :
		</td>
		<td width="500" align="left" style="padding-left: 1em;padding-bottom: 0.5em;">
			<form:input path="questions[0].BGradeLevel" id="BGradeLevel" required="required" maxlength="20" style="width: 250px;" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}" />
		</td>
	</tr>
	<tr height="30">
		<td width="500" align="right" valign="middle" class="tabtxt">
			Enter Reading Passage :
		</td>
		<td width="500" align="left" style="padding-left: 1em;padding-bottom: 0.5em;">
			<form:textarea path="questions[0].question" id="question0" required="required" cols="60" rows="5" style="overflow-y:auto;" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}" />
		</td>
	</tr>
	<tr><td width="500" align="right" valign="middle"></td><td><div id="fluencyDiv" align="left"></div></td></tr>

	<tr><td width="500" align="right" valign="middle"></td><td><div id="retellDiv" align="left"></div></td></tr>
	</form:form>
    </table>
  </td></tr>
<c:choose>
  <c:when test="${mode eq 'Create'}"> 
  <tr><td height="10"></td></tr>
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
      <tr><td height=5 colSpan=3></td></tr>  
      <tr>
          <td width="325" height="25" align="center" colspan="5">
          		<div id="resultDiv" class="status-message" ></div>
          		<b><div id="status" align="center"  style="display:visible;"></div></b>
          </td>
     </tr>
   </c:when>
   <c:when test="${mode eq 'Edit'}"> 
    <tr><td height=5 colSpan=3></td></tr>  
    <tr>
           <td width="325" height="25" align="center" colspan="5">
           		<div id="resultDiv0" class="status-message" ></div>
           		<b><div id="status" align="center"  style="display:visible;"></div></b>
           </td>
    </tr>
   <tr><td align="left" height="15"><font color="#8cb4cd">* Changes will apply automatically</font> </td></tr>
   </c:when>
  </c:choose> 
  </table>
     
 