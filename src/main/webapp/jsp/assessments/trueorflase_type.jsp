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
		<input type="hidden" id="lessonId" name="lessonId" value="${lessonId}">
		<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
		<input type="hidden" id="assignmentId" name="assignmentId" value="">
		<input type="file" id="file" name="files" style="visibility:hidden;"/>
		<input type="hidden" id="gradeId" name="gradeId" value="${gradeId}">
     <tr>
		<td width="562" align="left"><span class="tabtxt">Enter ${assignmentValue} Question :</span><br><br></td>
	</tr>
	<tr>
		<td width="562" align="center"><form:textarea rows="4" cols="65" path="questions[0].question" id="question0" required="required" /></td>
	</tr>
	<tr><td height=10 colSpan=3></td></tr> 
	<tr>
		<td width="562" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;True<form:radiobutton path="questions[0].answer" value="true" checked="checked" />
		&nbsp;False<form:radiobutton path="questions[0].answer" value="false"/></td>
	</tr>
      </form:form>
      </table>

  </td></tr> 
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
       <tr>
           <td width="325" height="25" align="center" colspan="5">
           		<div id="resultDiv" class="status-message" ></div>
           </td>
     	</tr>
	</table>