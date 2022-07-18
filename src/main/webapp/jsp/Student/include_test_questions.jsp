<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
function autoSave(id) {
	document.getElementById('operation').value="save";
	document.getElementById('selectedCsId').value=$('#csId').val();
	var answer;
	var questionId = document.getElementsByName('assignmentQuestions['+id+'].questions.questionId')[0].value;
	var assignmentQuestionId = document.getElementsByName('assignmentQuestions['+id+'].assignmentQuestionsId')[0].value;
	var studentAssignmentId = document.getElementsByName('studentAssignmentId')[0].value;
	var ele = document.getElementsByName("assignmentQuestions["+id+"].answer")[0];
	if(ele.type == "radio"){
		var isChecked = $('[name="assignmentQuestions['+id+'].answer"]').is(':checked');
		if(isChecked)
		 	answer = $('input[name="assignmentQuestions['+id+'].answer"]:checked').val();

	}else if(ele.type == "text"){
		answer = document.getElementsByName('assignmentQuestions['+id+'].answer')[0].value;
	}
	
	$.ajax({
		type : "GET",
		url : "autoSaveAssignment.htm",
		data : "assignmentQuestionId=" +assignmentQuestionId+"&answer=" +encodeURIComponent(answer),
		success : function(response) {
			systemMessage(response);
		}
	});
}
</script>
<form:form id="questionsForm"
		modelAttribute="studentAssignmentStatus" action="submitTest.htm" method="POST">
		<form:hidden path="operation" id="operation" />
		<input type="hidden" id="selectedCsId" name="selectedCsId"/>
		<c:set var="queCount" value="0"></c:set>
		<c:set var="quedisplayCount" value="1"></c:set>
		<c:set var="optionsCount" value="0" scope="page" />
		<input type="hidden" id="tab" name="tab" value="${tab}" />
		<form:hidden path="studentAssignmentId" />
		<form:hidden path="assignment.assignmentType.assignmentTypeId"
			value="${assignmentTypeId}"></form:hidden>
		<form:hidden path="assignment.usedFor" value="${usedFor}"></form:hidden>
		<table style="width: 80%;">
			<c:choose>
				<c:when test="${assignmentTypeId==7}">
					<c:forEach items="${ssQuestions}" var="ssql">
						<tr>
							<td colspan="5" height="20" align="center" valign="middle"
								class="tabtxt">
								<table style="width: 100%;">
									<tr>
										<td width="100%" height="30" align="left"><c:out
												value="${quedisplayCount}"></c:out>.&nbsp;<c:out
												value="${ssql.subQuestion}"></c:out>
									</tr>
								</table>
							</td>
						</tr>
						<c:forEach var="count" begin="0" end="${ssql.numOfOptions - 1}">
							<tr>
								<td colspan="5" width="100%"><form:hidden
										path="assignmentQuestions[${queCount}].questions.questionId" />
									<form:hidden 
										path="assignmentQuestions[${queCount}].questions.answer" /> <form:hidden
										path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
									<form:hidden
										path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
									<table style="width: 100%" class="txtStyle">
										<tr>
											<td width='20%' height='30' align='left' valign="top"><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option1" /> <c:out
													value="${testQuestions[optionsCount].questions.option1}"></c:out></td>
											<td width='20%' height='30' align='left' valign="top"><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option2" /> <c:out
													value="${testQuestions[optionsCount].questions.option2}"></c:out></td>
											<td width='20%' height='30' align='left' valign="top"><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option3" /> <c:out
													value="${testQuestions[optionsCount].questions.option3}"></c:out>
											</td>
										</tr>
									</table></td>
							</tr>
							<c:set var="optionsCount" value="${optionsCount + 1}"
								scope="page" />
							<c:set var="queCount" value="${queCount+1}"></c:set>
						</c:forEach>
						<c:set var="quedisplayCount" value="${quedisplayCount+1}"></c:set>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach items="${testQuestions}" var="questionList">
						<form:hidden
							path="assignmentQuestions[${queCount}].questions.questionId" />
						<form:hidden
							path="assignmentQuestions[${queCount}].questions.answer" />
						<form:hidden
							path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
						<c:if test="${assignmentTypeId == 1 ||assignmentTypeId == 2 ||assignmentTypeId == 3 ||assignmentTypeId == 4 ||assignmentTypeId == 5 ||assignmentTypeId == 6 }">
							<tr>
								<td height="35" align="left" valign="middle" class="tabtxt" colspan="4" width="425">
									<form:hidden
										path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
									
										<c:out value="${quedisplayCount}"></c:out>
										.&nbsp;
										<c:out value="${questionList.questions.question}"></c:out>
									
                                
								</td>
							</tr>
						</c:if>
						<c:if
							test="${assignmentTypeId==1|| assignmentTypeId==2 || assignmentTypeId==6 }">
							<tr>
								<td width="100%" class="tabtxt">
									<div>
										Answer :
										<form:textarea required="true" rows="5" cols="70" class="txtStyle" path="assignmentQuestions[${queCount}].answer" />
									</div></td>
							</tr>
						</c:if>
						<c:if test="${assignmentTypeId==3}">
							<tr>
								<td width="425" colspan="5" class="txtStyle"><table style=" width: 100%;padding-left: 1cm;" id="formTbl" class="txtStyle">
										<tr>
											<td width='20%' height='30' align='left' valign="top" class="txtStyle"><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option1" onchange="autoSave('${queCount}')"/> <c:out
													value="${questionList.questions.option1}"></c:out></td>
											<td width='20%' height='30' align='left' valign="top" class="txtStyle"><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option2" onchange="autoSave('${queCount}')" /> <c:out
													value="${questionList.questions.option2}"></c:out></td>
											<td width='20%' height='30' align='left' valign="top" class="txtStyle"><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option3" onchange="autoSave('${queCount}')"/> <c:out
													value="${questionList.questions.option3}"></c:out></td>
											<td width='20%' height='30' align='left' valign="top" class="txtStyle"><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option4" onchange="autoSave('${queCount}')"/> <c:out
													value="${questionList.questions.option4}" ></c:out></td>
											<td width='20%' height='30' align='left' valign="top" class="txtStyle"><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option5" onchange="autoSave('${queCount}')" /> <c:out
													value="${questionList.questions.option5}"></c:out></td>
											
										</tr>
									</table></td>
									  <td width="80" height="35" align="left">
							                     	<font color="blue" size="3"><b><div id="resultDiv${queCount}" ></div></b></font>
							                 </td>
							</tr>
						</c:if>
						<c:if test="${assignmentTypeId == 4 }">
							<tr>
								<td width="100%" style="padding-left: 1cm;" class="txtStyle"><div>
										Answer :
										<form:input path="assignmentQuestions[${queCount}].answer" onblur="autoSave('${queCount}')" class="txtStyle" />
									</div><br></td>
							    <td width="80" height="35" align="left">
				                     	<font color="blue" size="3"><b><div id="resultDiv${queCount}" ></div></b></font>
				                 </td>
									
							</tr>
						</c:if>
						<c:if test="${assignmentTypeId==5}">
							<tr>
								<td width="325" colspan="4" class="txtStyle">
									<table style="width: 100%; padding-left: 1cm;" class="txtStyle">
										<tr>
											<td height='30' align='left' valign="top" class="txtStyle">
												<form:radiobutton path="assignmentQuestions[${queCount}].answer" value="true"  onchange="autoSave('${queCount}')" />True 
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<form:radiobutton path="assignmentQuestions[${queCount}].answer" value="false"  onchange="autoSave('${queCount}')" />False</td>
										</tr>
									</table>
								</td>
			                       <td width="80" height="35" align="left">
				                     	<font color="blue" size="3"><b><div id="resultDiv${queCount}" ></div></b></font>
				                  </td>
     						</tr>
						</c:if>

						<c:set var="queCount" value="${queCount+1}"></c:set>
						<c:set var="quedisplayCount" value="${quedisplayCount+1}"></c:set>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<c:if test="${userReg.user.userType != 'parent'}">
			<tr>

				<td width="40%" height='20'  align='center'
								valign='middle'>

				<table style="position:relative;" align='center'>
						<tr>
							<td colspan='3' width='71' height='2' align='left'
								valign='middle'></td>
						</tr>
						<tr>
						<c:if test="${assignmentTypeId ne 3 && assignmentTypeId ne 4 && assignmentTypeId ne 5 }">
							<td width='50%' align='right' valign='middle'><input type='image'
								src="images/Common/saveChangesUp.gif" onclick="saveForm()"></td>
						</c:if>	
							<td width='0' align='center' valign='middle'><input
								type='image' src='images/Common/submitChangesUp.gif'
								onclick="return submitForm('${queCount}');"> </td>
							<td align='center'><input 
								type='image' src="images/Common/clear.gif" onClick="clearForm('${queCount}'); return false;" height="19" /></td>
						</tr>
					</table></td>
					<td height="20" align="center" valign="middle" class="tabtxt"> </td>
			</tr>
			</c:if>
		</table>
	</form:form>
