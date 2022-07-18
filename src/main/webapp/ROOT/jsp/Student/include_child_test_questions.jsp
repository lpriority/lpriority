<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Insert title here</title>

<style>
.tits{
   font-family: "Georgia";
	font-size: 15px;
	font-weight:bold;
}	
body{
font-family: "Georgia";
	font-size: 13px;
}
 .des{
    border: 1px solid black;
    border-collapse: collapse;
    border-color: #d3d3d3;
    }
hr{
  border-top: 1px solid #d3d3d3;
}
.ques{
   font-family: "Georgia";
   font-size : 16px;
   
 }
</style>
<script>
function goToDiv(i){
	  document.getElementById("sho"+i).scrollIntoView(true);
	}
</script>
</head>
<body>
	<table align="center" id="shows"
		style="visibility: hidden; width: 100%">
		<tr>
			<td><div id="msg" style="color: #0000FF"></div></td>
		</tr>
	</table>


	<!-- 	<div id="testQuestions" style="visibility: visible;"> -->
	<form:form id="testQuestionsForm"
		modelAttribute="studentAssignmentStatus">

		<table border=0 align="center" width="60%" class="des">
			<tr>
				<td><c:set var="queCount" value="0"></c:set> <c:set
						var="quedisplayCount" value="1"></c:set> <c:set var="optionsCount"
						value="0" scope="page" /> <input type="hidden" id="tab"
					name="tab" value="${tab}" /> <form:hidden
						path="studentAssignmentId" /> <form:hidden
						path="assignment.assignmentType.assignmentTypeId"
						value="${assignmentTypeId}"></form:hidden> <form:hidden
						path="assignment.usedFor" value="${usedFor}"></form:hidden>
					<div class="Divheads">
						<table width="60%">
							<tr>
								<td width="50%"><font color="">
								<strong><c:out value="${assignmentType.assignmentType}"></c:out> </strong></font></td>
								<td align="right"><br></td>
							</tr>
						</table>
					</div> <c:choose>
						<c:when test="${assignmentTypeId==7}">
							<c:forEach items="${ssQuestions}" var="ssql">
								<div
									style="background-color: #DDEEDD; padding: 0.5em; margin: 0;">
									<font color="000000">
										<table>
											<tr>
										<td colspan="5" height="10" align="center" valign="middle"
											class="tabtxt">
											<table style="width: 100%;">
												<tr>
												<td width="100%" height="30" align="left">
													<c:out value="${quedisplayCount}"></c:out> .&nbsp; 
													
														<label class="tits"> <c:out value="${ssql.subQuestion}"></c:out></label>
													</td>
														
														
													
												</tr>
											</table>
										</td>
									</tr>
										</table>
									</font>
								</div>
								<br>
								<c:forEach items="${ssql.questionses}" var="ques" varStatus="count">
									<div style="padding-left: 2em">
										<form:hidden
											path="assignmentQuestions[${queCount}].questions.questionId" />
										<form:hidden
											path="assignmentQuestions[${queCount}].questions.answer" />
										<form:hidden
											path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
										<form:hidden
											path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
										<table width='60%' class="ques">
											<tr>
												<td width='20%' height='30' align='left' valign="top"
													class="txtStyle"><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option1" /> <c:out
														value="${ques.option1}"></c:out></td>
												<td width='20%' height='30' align='left' valign="top"
													class="txtStyle"><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option2" /> <c:out
														value="${ques.option2}"></c:out></td>
												<td width='20%' height='30' align='left' valign="top"
													class="txtStyle"><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option3" /> <c:out
														value="${ques.option3}"></c:out>
												</td>
											</tr>
										</table>
									</div>

									<c:set var="optionsCount" value="${optionsCount + 1}"
										scope="page" />
									<c:set var="queCount" value="${queCount+1}"></c:set>
								</c:forEach>
								<br>
								<c:set var="quedisplayCount" value="${quedisplayCount+1}"></c:set>
							</c:forEach>
						</c:when>
						<c:when test="${assignmentTypeId==8}">
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td width='100' height='25' align='left' class="tabtxt"><b>Test</b></td>
								<td class="tabtxt"><b>Mark</b></td>
							</tr>
							<c:set var="fluencyMarks" value="0"></c:set>
							<c:forEach items="${testQuestions}" var="questionList">
								<c:set var="co" value="${queCount+1}"></c:set>
								<c:set var="yes" value="0"></c:set>
								<c:if test="${empty questionList.BFluencymarks}">
									<c:set var="fluencyMarks" value="0"></c:set>
								</c:if>
								<c:if test="${not empty questionList.BFluencymarks}">
									<c:set var="fluencyMarks" value="${questionList.BFluencymarks}"></c:set>
								</c:if>
								<c:if test="${empty questionList.BRetellmarks}">
									<c:set var="retellMarks" value="0"></c:set>
								</c:if>
								<c:if test="${not empty questionList.BRetellmarks}">
									<c:set var="retellMarks" value="${questionList.BRetellmarks}"></c:set>
								</c:if>

								<tr>
									<td width='100' height='25' align='left'><a
										href='javascript:openChildWindow(${questionList.assignmentQuestionsId},${questionList.studentAssignmentStatus.studentAssignmentId},
											${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId},${co},${yes},${fluencyMarks})'>
											Fluency
											 <c:if
												test="${questionList.studentAssignmentStatus.assignment.benchmarkCategories.isFluencyTest == 1 }">
												<c:out
													value="${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId}. " />
												
											</c:if>
											<c:out value="${co}" />
									</a></td>
									<td><input type='text' id='fluency:${co}' name='fluency'
										value='${questionList.BFluencymarks}' disabled /></td>
								</tr>
								<c:set var="yes" value="1"></c:set>
								<tr>
									<td width='100' height='25' align='left'><a
										href='javascript:openChildWindow(${questionList.assignmentQuestionsId},${questionList.studentAssignmentStatus.studentAssignmentId},
										${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId},${co},${yes},${retellMarks})'>
										Retell<c:if
												test="${questionList.studentAssignmentStatus.assignment.benchmarkCategories.isFluencyTest == 1}">
												<c:out
													value="${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId}. " />
											</c:if>
											<c:out value="${co}" />
									</a></td>
									<td><input type='text' id='retell:${co}' name='retell'
										value='${questionList.BRetellmarks}' disabled /></td>
								</tr>
								<c:set var="queCount" value="${queCount+1}"></c:set>
							</c:forEach>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td width='100' height='25' align='left'><input
									type='hidden' id='booleanValue' name='booleanValue'
									value='false'><input type=button
									value='enterSentenceStructureScore'
									onclick='allowToEnterSentenceScore()'></td>

							</tr>
							<tr>
								<td width='100' height='25' align='left'><input type=text
									name='sentenceStructureScore' id='sentenceStructureScore'
									value="${sentenceStructureScore}" readonly></td>
							</tr>
							<tr>
								<td align='left'>Notes:<textarea id='teacherNotes'
										name='teacherNotes'><c:out
											value="${teacherComment}"></c:out></textarea></td>
								<td>&nbsp;</td>
							</tr>
							<div id="gradeBenchmark"></div>
							<tr>
								<td width='100' height='25' align='center'></td>
								<td width='100' align='center'></td>
							</tr>
						</c:when>
                         <c:when test="${assignmentTypeId==19}">
							<c:forEach items="${compreQuestions}" var="comql">
 
 
                             <div
										style="background-color: #DDEEDD; padding: 0.1em; margin: 0;">
										<font color="000000"><table>
									<tr>
										<td colspan="5" height="10" align="center" valign="middle"
											class="tabtxt">
											<table style="width: 100%;">
												<tr>
												<td width="100%" height="30" align="left">
												
														<label class="tits"> <c:out value="${comql.subQuestion}"></c:out></label>
													</td>
															
													
												</tr>
											</table>
										</td>
									</tr>
									
								</table>
										</font>
									</div>
									<br><c:set var="comCount" value="1"></c:set>
									<c:forEach items="${comql.questionses}" var="ques">
									 <form:hidden
											path="assignmentQuestions[${queCount}].questions.questionId" />
										<form:hidden
											path="assignmentQuestions[${queCount}].questions.answer" />
										<form:hidden
											path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
										<form:hidden
											path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
                                    <table>
									<tr>
										<td colspan="5" height="20" align="center" valign="middle"
											class="tabtxt">
											<table style="width: 100%;">
												<tr>
													
														
													<td width="100%" height="30" align="left">
													<c:out value="${comCount}"></c:out> .&nbsp; 
														<B><label class="tit"> <c:out
																	value="${ques.question}"></c:out></label></B>
													</td>
														
														
													
												</tr>
												<tr>
												<td><form:textarea required="true" rows="5" cols="70"
														path="assignmentQuestions[${queCount}].answer" class="tit" />
												</td>
											</tr>
											<tr>
													<td class='ques' style="padding-bottom: 1em"
														align="left">Max Marks&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;<form:input path="assignmentQuestions[${queCount}].maxMarks" /></td>
													
												</tr>
												<tr>
													<td class='ques' style="padding-bottom: 1em"
														align="left">Marks Secured&nbsp;&nbsp;:&nbsp;&nbsp;
													<form:input path="assignmentQuestions[${queCount}].secMarks" /></td>
												</tr>

												<tr>
													<td class='ques' style="padding-bottom: 2em"
														align="left">Comments&nbsp;&nbsp;:&nbsp;&nbsp;
													<form:textarea required="true" rows="5" cols="70"
														path="assignmentQuestions[${queCount}].teacherComment" class="tit" />
													</td>
                                            </tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>

									<tr>
										<td></td>
									</tr>
								</table>
							<c:set var="comCount" value="${comCount+1}"></c:set>
								<c:set var="queCount" value="${queCount+1}"></c:set>
                                   </c:forEach> 
								
								<br>
								<c:set var="quedisplayCount" value="${quedisplayCount+1}"></c:set>
							</c:forEach>
						</c:when>
                        
						<c:otherwise>
							<c:if
								test="${assignmentTypeId==3|| assignmentTypeId==4 || assignmentTypeId==5 }">
								<div style="background-color: #c7e4e8; padding: 1em; margin: 0;">
									<c:forEach items="${testQuestions}" varStatus="cnt"
										var="questionList">
										<c:set var="col" value="red"></c:set>
										<c:if test="${fn:length(questionList.answer) ne 0}">
											<c:set var="col" value="#0B610B"></c:set>
										</c:if>
										<label onclick="goToDiv(${cnt.count})"
											style="cursor: pointer;"> <span id="qu${cnt.count}"
											style="color:${col}"><c:out value="${cnt.count}" /></span></label>&nbsp;&nbsp;&nbsp;&nbsp;
                                          <c:if test="${cnt.count == 20}">
											<br>
											<br>
										</c:if>
									</c:forEach>
								</div>

								<!--  <div id="ee-box" style="align: center; text-align: center;">
									<p class="ques">
										<em>Each Question have <strong>One</strong> Mark.
										</em>
									</p>
									<hr>
								</div> -->
							</c:if>



							<c:forEach items="${testQuestions}" var="questionList">
								<form:hidden
									path="assignmentQuestions[${queCount}].questions.questionId" />
								<form:hidden
									path="assignmentQuestions[${queCount}].questions.answer" />
								<form:hidden
									path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
									<c:if test="${assignmentTypeId==1|| assignmentTypeId==2 || assignmentTypeId==6 }">
									  	<div style="background-color:#DDEEDD; padding:0.5em; margin: 0;"><font color="000000">
									  	<form:hidden path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
									  	<label class="tabtxt"><c:out value="${quedisplayCount}"></c:out></label>
										.&nbsp;
										<b><label class="tit"><c:out value="${questionList.questions.question}"></c:out></label></b> </font></div>
									</c:if>
									<c:if
										test="${assignmentTypeId == 3 ||assignmentTypeId == 4 ||assignmentTypeId == 5 }">
										<div id="sho${quedisplayCount}"
											style="align: center; text-align: center;">
											<table>
												<tr>
													<td colspan="5" height="20" align="center" valign="middle"
														class="tabtxt">
														<table style="width: 100%;">
															<tr>
																
																	
																		<td width="100%" height="30" align="left"><input
																			type="hidden" id="question${queCount}"
																			value="${questionList.assignmentQuestionsId}"
																			name="questions" /> <c:out value="${quedisplayCount}"></c:out>.&nbsp;&nbsp;
																			<b><c:out value="${questionList.questions.question}"></c:out></b></td>
																	
																
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td>&nbsp;</td>
												</tr>
			
												
											</table>
										</div>
									</c:if>
									
									
									<c:if
										test="${assignmentTypeId == 1 ||assignmentTypeId == 2 ||assignmentTypeId == 6 }">
										<div style="padding-left: 3em">
	 
	                                    <table style="padding-left: 1em;padding-top:0.8em" class="tit">
								          <tr>
									          <td class="ques" style="padding-left: 3.2em">	Answer</td>
														<td class='ques'
															style="padding-right: 0.5em; padding-left: 0.5em"
															valign="middle">:</td>
														<td><textarea id="${queCount}" rows=3 cols=50
																required=required><c:out
																	value="${questionList.answer}"></c:out></textarea></td>
													</tr>
													<tr>
														<td class='ques' style="padding-bottom: 1em"
															align="right">Max Marks</td>
														<td class='ques'
															style="padding-right: 0.5em; padding-left: 0.5em"
															valign="top">:</td>
														<td><input type="text" id="maxMarks${queCount}"
															value="${questionList.maxMarks}" name="maxMarks"
															required="required"></td>
													</tr>
													<tr>
														<td class='ques' style="padding-bottom: 1em"
															align="right">Marks Secured</td>
														<td class='ques'
															style="padding-right: 0.5em; padding-left: 0.5em"
															valign="top">:</td>
														<td><input type="text" id="secMarks${queCount}"
															value="${questionList.secMarks}" name="secMarks"
															required="required"></td>
													</tr>
	
													<tr>
														<td class='ques' style="padding-bottom: 2em"
															align="right">Comments</td>
														<td class='ques'
															style="padding-right: 0.5em; padding-left: 0.5em"
															valign="top">:</td>
														<td><textarea id="teacherComment${queCount}"
																name="teacherComments" rows=3 cols=50 required=required>${fn:trim(questionList.teacherComment)}</textarea>
														</td>
													</tr>
												</table></div>
											
	
	
	
									</c:if>
								
									<c:if test="${assignmentTypeId==3}">
	                                   <div style="padding-left: 3em">
	
									<table style="padding-left: 1em" class="ques">
	
										<tr>
											<td><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option1" /></td>
													<td>A:</td>
											        <td> <c:out value="${questionList.questions.option1}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;
									               </td>
									     </tr>
									     <tr><td>
												<form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option2" /></td><td>B:</td>
											          <td> <c:out
													value="${questionList.questions.option2}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;
												</td>
										 </tr>
										 <tr><td>
												<form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option3" /></td><td>C:</td><td> <c:out
													value="${questionList.questions.option3}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;
												</td>
										 </tr>
										 <tr><td>
												<form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option4" /></td><td>D:</td>
											   <td> <c:out
													value="${questionList.questions.option4}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;
												</td>
										 </tr>
										 <tr><td>
												<form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option5" /></td><td>E:</td>
											   <td> <c:out
													value="${questionList.questions.option5}"></c:out></td>
	
										</tr>
	
	                                    </table></div>
									</c:if>
									<c:if test="${assignmentTypeId == 4 }">
										 <div style="padding-left: 3em">
	 
	                                    <table style="padding-left: 1em" class="tit">
										<tr>
											<td width="100%" class='txtStyle' style="padding-left: 1cm;">Answer
												: <form:input path="assignmentQuestions[${queCount}].answer" disabled="true"/>
											</td>
										</tr></table></div>
									</c:if>
									<c:if test="${assignmentTypeId==5}">
									 <div style="padding-left: 3em">
	 
	                                  <table style="padding-left: 1em" class="tit">
										<tr>
											<td width='100%' height='30' align='left'
												style="padding-left: 1cm;" class='txtStyle'><form:radiobutton
													path="assignmentQuestions[${queCount}].answer" value="true" />True
												&nbsp;&nbsp;&nbsp;&nbsp;</td></tr><tr><td width='100%' height='30' align='left'
												style="padding-left: 1cm;" class='txtStyle'><form:radiobutton
													path="assignmentQuestions[${queCount}].answer" value="false" />False</td>
										</tr></table></div>
	
	
									</c:if>
	                                <c:if test="${fn:length(testQuestions) ne quedisplayCount}">
										<c:if test="${assignmentTypeId==3|| assignmentTypeId==4 || assignmentTypeId==5 }">
											<hr>
										</c:if>
									</c:if>
									<c:set var="queCount" value="${queCount+1}"></c:set>
									<c:set var="quedisplayCount" value="${quedisplayCount+1}"></c:set>
							</c:forEach>
						</c:otherwise>
					</c:choose>
		</td></tr>
		</table>
		<br><br>
	</form:form>
</body>
</html>