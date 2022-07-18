<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/curriculum.js"></script>	
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript">
var count = 0;
$(document).ready(function () {
	var subQuestionId = document.getElementById("subQuestionId").value;
	if(subQuestionId)
		setIndex("optionsDiv",'Question');
});
</script>
 <table width="100%"><tr><td width="100%">
 
    <form:form id="questionsForm"  action="createAssessments.htm" modelAttribute="questionsList" method="post" enctype="multipart/form-data">
      <table width="100%">
		<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}">
		<input type="hidden" id="tab" name="tab" value="${tab}">
		<input type="hidden" id="mode" name="mode" value="${mode}">
		<c:if test="${assignmentTypeId ne 19}">
		<input type="hidden" id="lessonId" name="lessonId" value="${lessonId}">
		</c:if>
		<input type="hidden" id="questionId" name="questionId" value="${questionId}">
		<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}">
		<input type="hidden" id="id" name="id" value="" />
		<input type="hidden" id="subQuestionId" name="subQuestionId" value="${questionsList.subQuestions[0].subQuestionId}">
		<input type="file" id="file" name="files" style="visibility:hidden;"/>
		<input type="hidden" id="assignmentId" name="assignmentId" value="">
		<input type="hidden" id="gradeId" name="gradeId" value="${gradeId}">
	 <c:if  test="${assignmentTypeId==19}">
	 	 <tr>
			<td width="562" align="left"><span class="tabtxt">Enter Title :</span></td>
		</tr>
		<tr>
	    	<td width="562" align="left">&nbsp;&nbsp;<form:input size="30" path="subQuestions[0].title" id="question0" required="required" style="overflow-y:auto;" value="" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}" /></td>
	    	
		</tr>
	 </c:if>
     <tr>
		<td width="562" align="left"><span class="tabtxt">Enter Reading Passage for ${assignmentValue} Questions :</span></td>
	</tr>
	<tr>
	 <c:if  test="${assignmentTypeId==7}">
		<td width="562" align="left">&nbsp;&nbsp;<form:textarea rows="4" cols="80" path="subQuestions[0].subQuestion" id="question0" required="required" style="overflow-y:auto;" value="" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}" /></td>
	 </c:if>
	 <c:if  test="${assignmentTypeId==19}">
		<td width="562" align="left">&nbsp;&nbsp;<form:textarea rows="4" cols="80" path="subQuestions[0].subQuestion" id="passage" required="required" style="overflow-y:auto;" value="" onchange="${(mode eq 'Edit')?'updateQuestion(0)':''}" /></td>
	 </c:if>	
		<form:hidden path="subQuestions[0].subQuestionId" value="${subQuestionId}" />
	</tr>
	
	<c:choose>
		<c:when  test="${assignmentTypeId==7}">
			<tr>
				<td width="562" align="left"><input type="button" class="button_green" id="btAdd" name="btAdd" height="52" width="50" value="Add Options" onclick="addOptions()" style=""></td>
				<td><font color="blue" size="3"><b><div id="resultDiv-1"  class="status-message"></div></b></font></td>
			</tr>
		</c:when>			
		<c:when  test="${assignmentTypeId==19}">
			<tr>
				<td width="562" align="left">
					<select name="comprehensionType" id="comprehensionType">
						<option value="">Select Assignment Type</option> 
						<c:forEach items="${comprehensionTypes}" var="comprehensionType">
							<option value="${comprehensionType.assignmentTypeId}">${comprehensionType.assignmentType}</option>
						</c:forEach>
					</select>
					<input type="button" class="button_green" id="btAdd" name="btAdd" height="52" width="50" value="Add Questions" onclick="addComprehensionOptions()" style="">
				</td>
				<td>
					<font color="blue" size="3"><b><div id="resultDiv-1" class="status-message" ></div></b></font>
				</td>
			</tr>
		</c:when>
	</c:choose>	 
	  <c:choose>
		<c:when test="${mode eq 'Create'}">
			<tr><td width="90%"><div id="optionsDiv" align="center" style="overflow-y: auto ;border-style: groove;border-width: 1px;overflow-x: hidden ;max-height: 320px;min-height: 320px; width: 100%;background:white;" /><input type=hidden id='currentId' name='currentId'/></td></tr>
		</c:when>
		<c:when test="${mode eq 'Edit' && assignmentTypeId == 7}">
		<tr><td width="90%">
	     <table width="100%" align="left">
            <tr><td colspan="5" align="left" width="100%"><table width="100%" >
                     <tr><td><div id="optionsDiv" align="center" style="overflow-y: auto ;border-style: groove;border-width: 1px;overflow-x: hidden ;max-height: 320px;min-height: 320px; width: 100%;background:white;">
                     <c:choose>
						<c:when test="${fn:length(questionsLt) > 0}">
						 <c:forEach items="${questionsLt}" var="question" varStatus="status">
						  <div id="div${status.index}"> 
						    <form:hidden id="questionId${status.index}" path="subQuestions[0].questionses[${status.index}].questionId" value="${question.questionId}"/> 
							<table  align="left">
								<tr><td align='left'><b>&nbsp;&nbsp;<span id='span${status.index}' style="padding-top:0.2em;" class="tabtxt">Options for blank ${status.count} .</span></b></td></tr>
								<tr><td width='300' align='left' >&nbsp;&nbsp;option 1.&nbsp;<input type='text' name='subQuestions[0].questionses[${status.index}].option1' id='option1:${status.index}' onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''}" value="${question.option1}" /></td></tr>
							    <tr><td width='300' align='left' >&nbsp;&nbsp;option 2.&nbsp;<input type='text' name='subQuestions[0].questionses[${status.index}].option2' id='option2:${status.index}' onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''}" value="${question.option2}" /></td></tr>
							    <tr><td width='300' align='left' >&nbsp;&nbsp;option 3.&nbsp;<input type='text' name='subQuestions[0].questionses[${status.index}].option3' id='option3:${status.index}' onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''}" value="${question.option3}" /></td></tr>
							    <tr><td height=5 colSpan=3></td></tr>
							    <tr><td width='300' align='left'>Correct Answer.&nbsp;
								<select name='subQuestions[0].questionses[${status.index}].answer' required='required' id='answer:${status.index}' onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''}">
									<option value=''>Select Option</option>
									<option value='option1' ${question.answer == 'option1' ? 'selected=selected' : ''}>Option 1</option>
									<option value='option2' ${question.answer == 'option2' ? 'selected=selected' : ''}>Option 2</option>
									<option value='option3' ${question.answer == 'option3' ? 'selected=selected' : ''}>Option 3</option>
								</select></td>
								<td align="left" width='100'>
								 	<i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeOption(${status.index})"></i>
								</td>
								<td id="resultDiv${status.index}" align="left" class="status-message"></td>
								<tr><td height=5 colSpan=3></td></tr>
							</table>
							<script>
							    $(function () {
							    	var currentCnt = "<c:out value='${status.index}'/>";
							    	count = parseInt(currentCnt)+1;
								});
							</script>
						 </div>
						 </c:forEach>
						</c:when>
						<c:otherwise>
						</c:otherwise>
						</c:choose>
                     </td></div> 
                     </tr>
                 </table></td></tr>
          </table></td></tr>
        </c:when>
        <c:when test="${mode eq 'Edit' && assignmentTypeId == 19 }">
        <tr><td width="90%">
	     <table width="100%" align="left">
            <tr><td colspan="5" align="left"  width="100%"><table width="100%" >
                     <tr><td  width="100%"><div id="optionsDiv" align="center" style="overflow-y: auto ;border-style: groove;border-width: 1px;overflow-x: hidden ;max-height: 320px;min-height: 320px; width: 100%;background:white;">
        				 <c:forEach items="${questionsLt}" var="question" varStatus="status">
						  <div id="div${status.index}">
						    <form:hidden id="questionId${status.index}" path="subQuestions[0].questionses[${status.index}].questionId" value="${question.questionId}"/> 
						    <form:hidden id="assignmentTypeId${status.index}" path="subQuestions[0].questionses[${status.index}].assignmentType.assignmentTypeId" value="${questionsLt[status.index].assignmentType.assignmentTypeId}"/> 
						    <form:hidden path='subQuestions[0].questionses[${status.index}].numOfOptions' id ='numOfOptions${status.index}' value='${questionsLt[status.index].numOfOptions}' />
							<table  align="left">
							<tr>
								<td>
									<c:choose>
										<c:when test="${questionsLt[status.index].assignmentType.assignmentTypeId eq 2}">
											<table>
											    <tr>
												 	<td width='500' align='left' >
													 	 <b><span id='span${status.index}' style='padding-top:0.2em;' class='tabtxt'></b><textarea name='subQuestions[0].questionses[${status.index}].question' id='questions:${status.index}' onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''};" cols="60" rows="1">${question.question}</textarea>
													 </td>
													 <td> 
													 	 <i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;vertical-align:bottom;" onclick="removeComprehensionSubQuestion(${status.index})"></i>
												 	</td>
												 	<td id="resultDiv${status.index}" class="status-message" align="left"></td>
											    </tr>
											</table>
										</c:when> 
										<c:when test="${questionsLt[status.index].assignmentType.assignmentTypeId eq 3}">
											<table width='100%' id='table${status.index}'>
												<tr>
												 	<td width='500' align='left' >
													 	 <b><span id='span${status.index}' style='padding-top:0.2em;' class='tabtxt'></b><textarea name='subQuestions[0].questionses[${status.index}].question' id='questions:${status.index}' onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''};" cols="60" rows="1">${question.question}</textarea>
													 </td>
													 <td>
													 	<i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;vertical-align:bottom;" onclick="removeComprehensionSubQuestion(${status.index})"></i>
												 	</td>
												 </tr>
												<c:forEach begin="1" end="${questionsLt[status.index].numOfOptions gt 0 ? questionsLt[status.index].numOfOptions : 5}" var="loop"> 
													<tr>
														<td width="562" align="left"  ><div id="option${loop}${status.index}Div" style="display: block;">&nbsp;&nbsp;option ${loop}.&nbsp; <form:input path="subQuestions[0].questionses[${status.index}].option${loop}" title="questions:${status.index}" id="option${loop}:${status.index}" required="required" onchange="${(mode eq 'Edit')?'updateQuestion(this.title)':''};" /></div></td>
													</tr>
												</c:forEach>
												<tr><td>Correct Answer. 
														<form:select path="subQuestions[0].questionses[${status.index}].answer" title="questions:${status.index}" required="required" id="answer:${status.index}"  onchange="${(mode eq 'Edit')?'updateQuestion(this.title)':''};">
																<option value="">Select Option</option>
															<c:forEach begin="1" end="${questionsLt[status.index].numOfOptions gt 0 ? questionsLt[status.index].numOfOptions : 5}" var="count"> 
																<c:set var="option" value="option${count}" />
																<option value="option${count}" ${questionsLt[status.index].answer == option ? 'selected="selected"' : ''}> Option ${count}</option>
															</c:forEach>
														</form:select>
												</td></tr>
												<tr>
													<td width='200' height='50' align='left' style='padding-left:1em;'>
														<input type="button" class="subButtons subButtonsWhite medium"  value="Remove Option" onclick="removeMCQOption('comprehension',${status.index});" style="width:120px;" />&nbsp;&nbsp;&nbsp;
														<input type="button" class="subButtons subButtonsWhite medium"  value="Add Option" onclick="addMCQOption('comprehension',${status.index});"/>&nbsp;&nbsp;&nbsp;
													</td>
													<td id="resultDiv${status.index}" class="status-message" align="left"></td>
											        </tr>		
											</table>
											</c:when>
											<c:when test="${questionsLt[status.index].assignmentType.assignmentTypeId eq 4}">
												<table>
												    <tr>
													 	<td width='500' align='left' >
														 	<b><span id='span${status.index}' style='padding-top:0.2em;' class='tabtxt'></b><textarea name='subQuestions[0].questionses[${status.index}].question' id='questions:${status.index}' onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''};" cols="60" rows="1">${question.question}</textarea>
														 </td>
														 <td>
														 	<i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;vertical-align:bottom;" onclick="removeComprehensionSubQuestion(${status.index})"></i>
													 	</td>
												    </tr>
													<tr>
														<td>Correct Answer.
															<input type="text" name="subQuestions[0].questionses[${status.index}].answer" required="required"
															 id="answer:${status.index}" value="${questionsLt[status.index].answer}" onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''};">														
														</td>
														<td id="resultDiv${status.index}" class="status-message" align="left"></td>
													</tr>
												</table>
											</c:when> 
											 <c:when test="${questionsLt[status.index].assignmentType.assignmentTypeId eq 5}">
											 	<table>
											 	    <tr>
													 	<td width='500' align='left' >
														 	<b><span id='span${status.index}' style='padding-top:0.2em;' class='tabtxt'></b><textarea name='subQuestions[0].questionses[${status.index}].question' id='questions:${status.index}' onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''};" cols="60" rows="1">${question.question}</textarea>
														 </td>
														 <td>
														 	<i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;vertical-align:bottom;" onclick="removeComprehensionSubQuestion(${status.index})"></i>
													 	</td>
												    </tr>
													<tr>
														<td>Correct Answer. 
															<select name="subQuestions[0].questionses[${status.index}].answer" required="required" id="answer:${status.index}" onchange="${(mode eq 'Edit')?'updateQuestion(this.id)':''};">
																	<option value="">Select Option</option>
																		<c:choose>
																			<c:when test="${fn:toLowerCase(questionsLt[status.index].answer) == 'true'}">
																				<c:set var="trueSelected" value="selected" ></c:set>
																			</c:when>
																			<c:otherwise>
																				<c:set var="falseSelected" value="selected" ></c:set>
																			</c:otherwise>
																		</c:choose>
																	<option value="true" ${trueSelected}>True</option>
																	<option value="false" ${falseSelected}>False</option>
																	<c:set var="falseSelected" value="" ></c:set>
																	<c:set var="trueSelected" value="" ></c:set>
															</select>
														</td>
														<td id="resultDiv${status.index}" class="status-message" align="left"></td>
													</tr>
												</table>
											</c:when> 
										</c:choose> 
									</td>
								</tr>
								 <tr><td>&nbsp;</td></tr>
							 </table>
							<script>
							    $(function () {
							    	var currentCnt = "<c:out value='${status.index}'/>";
							    	count = parseInt(currentCnt)+1;
								});
							</script>
						 </div>
						 </c:forEach>
						</div>
						</td> 
                     </tr>
                 </table></td></tr>
          </table></td></tr>
        </c:when>
        </c:choose>
	 </table>
	</form:form>
  
  </td></tr>
  <tr><td height=10 colSpan=3></td></tr>
<c:choose>
  <c:when test="${mode eq 'Create'}"> 
  <tr><td align="center">
             <table width="100%" border="0" cellspacing="1" cellpadding="0">
                 <tr>
                     <td colspan="4" width="50%" height="25" align="right">
                          <div class="button_green button-decrease" id="btCreate" name="btAdd" height="52" width="50" onclick="createQuestions()" >Create</div>
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
           		<div id="resultDiv" class="status-message" ></div>
           </td>
     	</tr>
	</table>