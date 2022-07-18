<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Learning Priority|View Questions</title>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function showHideQuestions(val) {
	var divObject = document.getElementById("divid:" + val);
	if (divObject.style.display == "") {
		divObject.style.display = "none";
		document.getElementById("anchorid:" + val).innerHTML = "Show Questions";
	} else {
		divObject.style.display = "";
		document.getElementById("anchorid:" + val).innerHTML = "Hide Questions";
	}
}
function resetForm(){
	$('#select_all').prop('checked', false);
	$("#studentId option:selected").removeAttr("selected");
	$("#title").val('');
	$("#enddate").val('');
	$("#instruct").val('');
	$('#assignmentType').val(0);
	$('#reTestId').val('');
	$('#questionDiv').remove();
}
</script>
<style type="text/css">
.link_style{
  color:#b18400;
  text-shadow:0 1px 2px rgb(199, 199, 199);
}
</style>
</head>
<body>
	<c:set var="optionsCount" value="0"></c:set>
	<c:set var="questionsCount" value="0"></c:set>
	<input type="hidden" id="assignmentTypeId" value="${assignmentTypeId}">
	<table id="questionDiv" style="width: 100%;">
		<c:if
			test="${fn:length(quesList) > 0 && assignmentTypeId != 8  && assignmentTypeId != 7 && assignmentTypeId != 13 && assignmentTypeId != 14 && assignmentTypeId != 19}">
			<tr>
				<td colspan="4" height="20" align="left" class="tabtxt"><input
					type="checkbox" class="checkbox-design" name="selectAllQuestions" id="selectAllQuestions"
					value="${fn:length(quesList)-1}" onclick="selectAllQuestion(this)" /><label for="selectAllQuestions" class="checkbox-label-design">Select All</label>
					</td>
			</tr>
		</c:if>
		<tr>
			<td style="padding-left: 1cm;font-size:13px;">
			<table>
		<c:if test="${fn:length(quesList) <= 0 && fn:length(ssQuestions) <=0}">
			<tr>
				<td align=center>No Questions to assign to students.</td>
			</tr>
		</c:if>
		<c:choose>
			<c:when test="${assignmentTypeId==7 }">
				<c:forEach items="${ssQuestions}" var="ssql" varStatus="count">
					<tr>
						<td colspan="5" height="20" align="center" valign="middle" class="tabtxt">
						 <input type="hidden" name="questions${ssql.subQuestionId}" id="questions${ssql.subQuestionId}" value="${fn:length(ssql.questionses)}">
							<table style="width: 100%;">
								<tr>
									<td width="100%" height="30" align="left" class="tabtxt"><input
										type="checkbox" class="checkbox-design" name="ssQuestions" id="ssQuestions${count.index}"
										value="${ssql.subQuestionId}" /><label for="ssQuestions${count.index}" class="checkbox-label-design"></label> 
										<a id="anchorid:${count.index}" href="javascript:showHide(${count.index})" class="link_style"> 
											<strong> + </strong>
										</a> 
											${fn:substring(ssql.subQuestion, 0, 50)}...
										<div id="divid:${count.index}" style='display: none;'>
											<textarea rows="10" cols="50" readonly="readonly"><c:out value="${ssql.subQuestion}"></c:out></textarea>
										</div>
								</tr>
							</table>
						</td>
					</tr>
					<c:if test="${fn:length(ssql.questionses) > 0}">
					<c:forEach items="${ssql.questionses}" var="question" varStatus="cnt">
						<tr>
							<td colspan="5" width="100%"><input type="hidden" name="${ssql.subQuestionId}:${cnt.index}" id="${ssql.subQuestionId}:${cnt.index}" value="${question.questionId}">
								<table style="width: 100%">
									<tr>
										<td width='20%' height='30' align='left' valign="top"><input
											type='checkbox' class="checkbox-design"  name='options' value='' disabled /><label for="options${cnt.index}" class="checkbox-label-design"></label> <c:out
												value="${question.option1}"></c:out></td>
										<td width='20%' height='30' align='left' valign="top"><input
											type='checkbox' class="checkbox-design" name='options' value='' disabled /><label for="options${cnt.index}" class="checkbox-label-design"></label> <c:out
												value="${question.option2}"></c:out></td>
										<td width='20%' height='30' align='left' valign="top"><input
											type='checkbox' class="checkbox-design" name='options' value='' disabled /><label for="options${cnt.index}" class="checkbox-label-design"></label> <c:out
												value="${question.option3}"></c:out></td>
									</tr>
								</table>
							</td>
						</tr>
						<c:set var="optionsCount" value="${optionsCount + 1}" scope="page" />
					</c:forEach>
					</c:if>
				</c:forEach>
			</c:when>
			
			<c:when test="${assignmentTypeId == 19}">
				<c:forEach items="${ssQuestions}" var="ssql" varStatus="count">
					<tr>
						<td colspan="5" width="100%"
							class="tabtxt"><input type="hidden" name="questions${ssql.subQuestionId}" id="questions${ssql.subQuestionId}" value="${fn:length(ssql.questionses)}">
							<table style="width: 50%;" align="center"> 
								<tr>
									<td>
									
									</td>
								</tr>
								<tr>
									<td width="700"><input
										type="checkbox" class="checkbox-design" id="ssQuestions${count.index}" name="ssQuestions"
										value="${ssql.subQuestionId}" /><label for="ssQuestions${count.index}" class="checkbox-label-design"><span class='label'>${count.count}. ${ssql.title}</span></label>
											<div>
												<a id="anchorid:${count.index}" href="javascript:showHideQuestions(${count.index})" class="link_style"> 
												 Show Questions 
												</a> 
											</div>
									</td>									
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="5" width="100%"
							id="divid:${count.index}" style='display: none;'>
							<table style="width: 50%;padding-left:2em;" align="center"> 
								<tr>
									<td>
										<textarea rows="10" cols="50" readonly="readonly"><c:out value="${ssql.subQuestion}"></c:out></textarea>
									</td>
								</tr>
							<c:forEach items="${ssql.questionses}" var="question" varStatus="cnt">
								<tr >
									<td><input type="hidden" name="${ssql.subQuestionId}:${cnt.index}" id="${ssql.subQuestionId}:${cnt.index}" value="${question.questionId}">
										<b> ${cnt.count}. ${question.question} </b>
									</td>
								</tr>
							<c:if test="${question.assignmentType.assignmentTypeId eq 3 || question.assignmentType.assignmentTypeId eq 4 || question.assignmentType.assignmentTypeId eq 5}">
								<tr><td width="100%"><table width="100%" class="tabtxt"><tr>
								<c:if test="${question.assignmentType.assignmentTypeId eq 3 }">
									<td height='30' align='left' valign="top" style="padding-left: 1cm;font-size:13px;" class="tabtxt">1. ${question.option1}</td>
									<td height='30' align='left' valign="top" style="padding-left: 1cm;font-size:13px;" class="tabtxt">2. ${question.option2}</td>
									<td height='30' align='left' valign="top" style="padding-left: 1cm;font-size:13px;" class="tabtxt">3. ${question.option3}</td>
									<c:if test="${question.numOfOptions eq 4}">
										<td height='30' align='left' valign="top" style="padding-left: 1cm;font-size:13px;" class="tabtxt">4. ${question.option4}</td>
									</c:if>
									<c:if test="${question.numOfOptions eq 5}">
										<td height='30' align='left' valign="top" style="padding-left: 1cm;font-size:13px;" class="tabtxt">5. ${question.option5}</td>
									</c:if>
								</c:if>
								<c:if test="${question.assignmentType.assignmentTypeId eq 5 }">
									<td style="padding-left: 1cm;font-size:13px;">
										<input type="radio" name="true${cnt.index}" id="true${cnt.index}" class="radio-design"><label for="" class="radio-label-design">True</label>&nbsp;&nbsp;&nbsp;
										<input type="radio" name="false${cnt.index}" id="false${cnt.index}" class="radio-design"><label for="" class="radio-label-design">False</label>
									</td>
								</c:if> 
								</tr></table>
								</tr>
							</c:if>
								<c:set var="questionsCount" value="${questionsCount + 1}" scope="page" />
							</c:forEach>
							</table>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:when test="${assignmentTypeId==8  || assignmentTypeId == 20}">
				<c:set var="count" value="0"></c:set>
				<c:forEach items="${quesList}" var="ql">
					<tr>
						<td  valign="middle"><a id="anchorid:${count}"
							href="javascript:showHide(${count})" class="link_style"> <strong> + </strong></a> <c:out value="${ql.BTitle}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><c:out
										value="${ql.BGradeLevel}"></c:out></b>
							<div id="divid:${count}" style='display: none;'>
								<br /><input type='checkbox' class="checkbox-design" id="questions${count}" name='questions' value="${ql.questionId}" /><label for="questions${count}" class="checkbox-label-design"></label>
									<textarea rows="7" cols="35" readonly="readonly">${ql.question}</textarea>
							</div></td>
					</tr>
					<c:set var="count" value="${count + 1}" scope="page" />
				</c:forEach>
				<c:if test="${assignmentTypeId==8 }">
					<tr>
						<td align='center' style="padding-top: 0.5em;">Choose this test as : &nbsp;&nbsp;&nbsp;<form:select
								path='assignment.benchmarkId' id='benchmarkId'
								onchange='checkBenchTitleExistForSection()' required="true">
								<option value="select">select Fluency Assessment</option>
								<c:forEach items="${benchmarkCategories}" var="bmCategory">
									<option value="${bmCategory.benchmarkCategoryId}">${bmCategory.benchmarkName}</option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					</c:if>
					<tr>
						<td>Choose Language : &nbsp;&nbsp;&nbsp;
							<form:select path="assignment.benchmarkDirectionsId" required="true">
								<option value="">Select</option> 
								<c:forEach items="${benchmarkDirections}" var="benchmarkDirection">
									<option value="${benchmarkDirection.benchmarkDirectionsId}">${benchmarkDirection.language.language}</option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
				
			</c:when>
			<c:when test="${assignmentTypeId==14}">
				<c:forEach items="${quesList}" var="ql" varStatus="outCount">
					<tr>
						<td><input type="radio" class='radio-design' name="questions" id="questions${questionsCount}" value="${ql.jacQuestionFileId}" /><label for='questions${questionsCount}' class='radio-label-design'></label>
						<td>Question File :<c:out value="${ql.filename}"></c:out>
						</td>
					</tr>				
					<c:forEach items="${ql.jacTempateList}" var="jql" varStatus="count">
						<tr>
							<td colspan='5' height='20' align='center' valign='middle'
								class='tabtxt'>
								<table  style="width=100%; border=0; cellspacing=0; cellpadding=0" >
									<tr>
										<td width='90%' height='30' align='left'><b>Task: </b>&nbsp;<c:out
												value="${jql.titleName}"></c:out></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
					<c:set var="questionsCount" value="${questionsCount + 1}" scope="page" />
				</c:forEach>
			</c:when>
			
			<c:when test="${assignmentTypeId==13}">
				<c:forEach items="${quesList}" var="ql">
					<tr>
						<td><input type="radio" class='radio-design' name="questions" value="${ql.questionId}"  id="questions${questionsCount}" />
							<label for='questions${questionsCount}' class='radio-label-design'>&nbsp;&nbsp;<b>Task :&nbsp;</b>${ql.ptName}</label>
						</td>
					</tr>					
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Subject Area :&nbsp;</b><c:out value="${ql.ptSubjectArea}"></c:out></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Directions :&nbsp;</b><c:out value="${ql.ptDirections}"></c:out></td>
					</tr>
					<c:set var="questionsCount" value="${questionsCount + 1}"
						scope="page" />
						<tr><td> &nbsp;</td></tr>
				</c:forEach>
			</c:when>
			

			<c:otherwise>
				<c:set var="mcqOptionCount" value="0" scope="page"></c:set>
				<c:forEach items="${quesList}" var="ql">
					<c:if
						test="${assignmentTypeId == 1 ||assignmentTypeId == 2 ||assignmentTypeId == 3 ||assignmentTypeId == 4 ||assignmentTypeId == 5 ||assignmentTypeId == 6 }">
						<tr>
							<td colspan="5" height="20" align="center" valign="middle"
								class="tabtxt">
								<table style="width: 100%">
									<tr>
										<td width="100%" height="30" align="left"><input
											type="checkbox" class="checkbox-design" name="questions" value="${ql.questionId}"
											id="questions${questionsCount}" /><label for="questions${questionsCount}" class="checkbox-label-design">${ql.question}</label></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:if>
					<c:if test="${assignmentTypeId==3}">
						<tr>
							<c:forEach begin="1" end="${ql.numOfOptions}">
								<td height='30' align='left' valign="top" style="padding-left: 1cm;font-size:13px;"><input
									type='checkbox' class="radio-design" id='options${mcqOptionCount}' name='options' value='' disabled /> <label for="options${mcqOptionCount}" class="radio-label-design" style="width:150%;word-wrap:break-word;display:table;">${mcqOptions[mcqOptionCount]}</label>
									<c:set var="mcqOptionCount" value="${mcqOptionCount+1}"></c:set>
								</td>
							</c:forEach>
						</tr>
					</c:if>
					<c:if test="${assignmentTypeId==5}">
						<tr>
							<td colspan="5">
								<table style="width: 100%">
									<tr>
										<td width='50%' height='30' align='left' style="padding-left: 1cm"><input
											type='radio' class="radio-design" id='True' name='answers' value='True' disabled /><label for="True" class="radio-label-design">True</label></td>
										<td width='50%' height='30' align='left'><input
											type='radio' class="radio-design" id='False' name='answers' value='False' disabled /><label for="False" class="radio-label-design">False</label></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:if>
					<c:set var="questionsCount" value="${questionsCount + 1}"
						scope="page" />
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<c:if test="${fn:length(quesList) > 0 || (assignmentTypeId == 19 && fn:length(ssQuestions) gt 0)}">

			<td height='20' colspan='5' align='center' valign='middle'
				class='tabtxt'><table style="">
					<tr>
						<td colspan='3' width='71' height='2' align='left' valign='middle'></td>
					</tr>
					<c:choose>
						<c:when test="${tab eq 'assignRtf'}">
						<tr>
						<td width='50%' align='right' valign='middle'><div class="button_green" id="btClear" onclick="assignRtfToStudents(${fn:length(quesList)})" style="width:90px;font-size:12px;">Submit Changes</div></td>
						<td width='0' align='left' valign='middle'>&nbsp;</td>
						<td width='50%' align='left' valign='middle'><div class="button_green" onclick="resetForm()"  style="font-size:12px;">Clear</div></td>
					</tr>
					  </c:when>
					  <c:otherwise>
						<tr>
							<td width='50%' align='right' valign='middle'><div class="button_green" id="btClear" onclick="assignAssessmentsToStudents(${fn:length(quesList)})" style="width:90px;font-size:12px;">Submit Changes</div></td>
							<td width='0' align='left' valign='middle'>&nbsp;</td>
							<td width='50%' align='left' valign='middle'><div class="button_green" onclick="resetForm()"  style="font-size:12px;">Clear</div></td>
						</tr>
					  </c:otherwise>					  
					</c:choose>					
				</table></td>
		</c:if>
		</table>
		</td>
		</tr>
	</table>
</body>
</html>