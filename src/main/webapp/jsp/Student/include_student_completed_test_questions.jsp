<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>

<title>Insert title here</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<style>
.tits{
   font-family: "Georgia";
	font-size: 15px;
	font-weight:bold;
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
 .tts{
     font-family: 'Big Caslon', 'Book Antiqua', 'Palatino Linotype', Georgia, serif;
    font-size: 15px;
    font-weight: bold;
 }
</style>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<link rel="stylesheet" href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/icons/ionicons/css/ionicons.min.css">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<meta name="msapplication-TileImage" content="/mstile-144x144.png">
<meta name="msapplication-TileColor" content="#333333">
<meta name="theme-color" content="#ffffff">
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
			<td><div id="msg" style="color: #0000FF"></div>
                          <c:set var="mcqOptionCount" value="0"> </c:set></td>
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
									style="background-color: #c7e4e8; padding: 1em; margin: 0;">
									<font color="000000">
										<table>
											<tr>
										<td colspan="5" height="10" align="center" valign="middle"
											class="tabtxt">
											<table style="width: 100%;">
												<tr>
												<td width="100%" height="30" align="left">
													<c:out value="${quedisplayCount}"></c:out> .&nbsp; 
														<p class="tits" style="text-align : justify"> ${ssql.subQuestion}</p>
													</td>
														
														
													
												</tr>
											</table>
										</td>
									</tr>
										</table>
									</font>
								</div>
								<br>
						<c:forEach var="count" begin="0" end="${fn:length(ssql.questionses)-1}">
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
														value="${testQuestions[optionsCount].questions.option1}"></c:out></td>
												<td width='20%' height='30' align='left' valign="top"
													class="txtStyle"><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option2" /> <c:out
														value="${testQuestions[optionsCount].questions.option2}"></c:out></td>
												<td width='20%' height='30' align='left' valign="top"
													class="txtStyle"><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option3" /> <c:out
														value="${testQuestions[optionsCount].questions.option3}"></c:out>
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
										href='javascript:openChildWindow(${questionList.assignmentQuestionsId},${questionList.studentAssignmentStatus.studentAssignmentId},${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId},${co},${yes},${fluencyMarks})'>Fluency
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
										href='javascript:openChildWindow(${questionList.assignmentQuestionsId},${questionList.studentAssignmentStatus.studentAssignmentId},${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId},${co},${yes},${retellMarks})'>Retell
											<c:if
												test="${questionList.studentAssignmentStatus.assignment.benchmarkCategories.isFluencyTest == 1 }">
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
                      <c:when test="${assignmentTypeId == 19}">
						<c:forEach items="${compreQuestions}" var="ssql">
							<div style="background-color: #c7e4e8; padding: 1em; margin: 0;">
									<table style="width: 100%;">
											<tr>
												<td colspan="5" height="10" align="center" valign="middle"
													class="tabtxt">
													<table style="width: 100%;">
														<tr><td><h2 align="center"> <c:out value="${ssql.title}"></c:out></h2></td></tr>
														<tr>
														<td width="80%" height="30" align="left">
															<c:out value="${quedisplayCount}"></c:out> .&nbsp; 
															
																<p class="tits" align="justify">${ssql.subQuestion}</p>
															</td>													
														</tr>
													</table>
												</td>
											</tr>
										</table>
								</div>
								<br>
							<c:forEach var="count" begin="0" end="${ssql.numOfOptions-1}">
									<div style="padding-left: 2em;width: 100%" >
										<input type="hidden" id="assignmentQuestions${queCount}" 
 											value="${testQuestions[queCount].assignmentQuestionsId}" name="questions" /> 
 										<form:hidden
											path="assignmentQuestions[${queCount}].questions.questionId" />
										<form:hidden
											path="assignmentQuestions[${queCount}].questions.answer" />
										<form:hidden
											path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
										<form:hidden
											path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
										<table style="width:100%" class="ques">
										 	<tr>
												<td colspan="2"  width="100%" height='30' align='left' valign="top"
													class="txtStyle"> <b> Question :<c:out value="${queCount+1}"></c:out></b>&nbsp;&nbsp;<c:out
														value="${testQuestions[queCount].questions.question} "></c:out>
														<c:if test="${testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 3 || testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 4 || testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 5}">
														<c:if test="${testQuestions[queCount].secMarks==0}">
														 &nbsp;<font color="red">Correct Answer&nbsp;:&nbsp;<c:out value="${testQuestions[queCount].questions.answer}"></c:out></font>
														</c:if>
														</c:if>
														</td>
											</tr> 
											<tr>
												<td colspan="2" width="100%" height='30' align='left' valign="top"
													class="txtStyle">
														<c:choose>
															<c:when test="${testQuestions[queCount].questions.assignmentType.assignmentType == 'Multiple Choices'}">
																<table style="width: 100%;">
																	<tr>
																		<c:forEach begin="1" end="${testQuestions[queCount].questions.numOfOptions}" var="count">
																			<td width="10%">${count}: <form:radiobutton path="assignmentQuestions[${queCount}].answer"
																					value="option${count}"/>
																					 
																					<c:if test="${count == 1 }">
																						<c:out value="${testQuestions[queCount].questions.option1}"> </c:out>
																					</c:if>
																					<c:if test="${count ==2 }">
																						<c:out value="${testQuestions[queCount].questions.option2}"></c:out>
																					</c:if>
																					<c:if test="${count ==3 }">
																						<c:out value="${testQuestions[queCount].questions.option3}"></c:out>
																					</c:if>
																					<c:if test="${count ==4 }">
																						<c:out value="${testQuestions[queCount].questions.option4}"></c:out>
																					</c:if>
																					<c:if test="${count ==5 }">
																						<c:out value="${testQuestions[queCount].questions.option5}"></c:out>
																					</c:if>
																				</td>
																		</c:forEach>																		
																	</tr>
																</table>
															</c:when>
															<c:otherwise>
															<table style="width: 100%;">
																	<tr><td>
																<b>Answer : </b><c:out value="${testQuestions[queCount].answer}"></c:out>
																</td></tr></table>
															</c:otherwise>
															
													</c:choose>
													<c:if test="${testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 3 || testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 4 || testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 5}">
													<input type="hidden" id="maxMarks${queCount}" name="maxMarks" value="${testQuestions[queCount].maxMarks}" />
													<input type="hidden" id="secMarks${queCount}" name="secMarks" value="${testQuestions[queCount].secMarks}" />
												    <input type="hidden" id="teacherComment${queCount}" name="teacherComments" value="--" />
												</c:if>
												</td>
											</tr>
											<c:if test="${testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 2}" >
											<tr>
												<td width="20%"  class='ques' style="width: 7em;padding-top: 1em;" align="right">Max Marks :</td>
												<td width="80%"  class='ques' style="padding-left: 1em;">
													<input type="text" id="maxMarks${queCount}" value="${testQuestions[queCount].maxMarks}" name="maxMarks" /></td>
											</tr>
											<tr>
												<td width="20%" class='ques' style="width: 8em;padding-top: 1em;" align="right">Marks Secured :</td>
												<td width="80%" class='ques' style="padding-left: 1em;">
													<input type="text" id="secMarks${queCount}" value="${testQuestions[queCount].secMarks}" name="secMarks" />
													</td>
											</tr>
											<tr>
												<td width="20%" class='ques' style="width: 7em;padding-top: 1em;" align="right">Comments :</td>
												<td width="80%" class='ques' style="padding-left: 1em;">
													<textarea id="teacherComment${queCount}" name="teacherComments" rows="3" cols="50" required="required" >${fn:trim(testQuestions[queCount].teacherComment)}</textarea>
			                                    </td>
											</tr>
											<tr>
												<td width="20%" class='ques' style="width: 7em;padding-top: 1em;" align="right"></td>
												<td width="80%" class='ques' style="padding-left: 1em;">
			                                        <audio controls>
														  <source src="loadDirectUserFile.htm?usersFilePath=${comprehensionFilePath}/${testQuestions[queCount].assignmentQuestionsId}.wav" type="audio/wav">
														Your browser does not support the audio element.
													</audio>
												</td>
											</tr>
											</c:if>
										</table>
									</div>
									<c:set var="queCount" value="${queCount+1}"></c:set>
								</c:forEach>
								<br/>
								<c:set var="quedisplayCount" value="${quedisplayCount+1}"></c:set>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:if test="${assignmentTypeId==3|| assignmentTypeId==4 || assignmentTypeId==5 }">
								<div style="background-color: #c7e4e8; padding: 1em; margin: 0;">
									<c:forEach items="${testQuestions}" varStatus="cnt"
										var="questionList">
										<c:set var="col" value="red"></c:set>
										<c:if test="${fn:length(questionList.answer) ne 0}">
											<c:set var="col" value="#0B610B"></c:set>
										</c:if>
										<label onclick="goToDiv(${cnt.count})"
											style="cursor: pointer;"> <span id="qu${cnt.count}"
											style="color:${col}" class="tts"><c:out value="${cnt.count}" /></span></label>&nbsp;&nbsp;&nbsp;&nbsp;
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
									  	<div style="background-color: #c7e4e8; padding: 1em; margin: 0;"><font color="000000">
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
													<c:choose>
														<c:when test="${questionList.secMarks==1}">
															<td width="100%" height="30" align="left"><input
																type="hidden" id="question${queCount}"
																value="${questionList.assignmentQuestionsId}"
																name="questions" /> <c:out value="${quedisplayCount}"></c:out>.&nbsp;&nbsp;
																<b><c:out value="${questionList.questions.question}"></c:out></b></td>
														</c:when>
														<c:otherwise>
															<td width="100%" height="30" align="left"><input
																type="hidden" id="question${queCount}"
																value="${questionList.assignmentQuestionsId}"
																name="questions" /> <c:out value="${quedisplayCount}"></c:out>.&nbsp;&nbsp;
																<b><c:out value="${questionList.questions.question}"></c:out></b>&nbsp;&nbsp;&nbsp;
																<font color="#FF0000">Correct Answer :&nbsp;<c:out
																		value="${questionList.questions.answer}"></c:out>

															</font></td>
														</c:otherwise>
													</c:choose>
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
									
																		<c:forEach begin="1" end="${testQuestions[queCount].questions.numOfOptions}" var="count">
																			<tr>
																			<td width="10%">${count}: <form:radiobutton path="assignmentQuestions[${queCount}].answer"
																					value="option${count}"/>
																					 
																					<c:if test="${count == 1 }">
																						<c:out value="${testQuestions[queCount].questions.option1}"> </c:out>
																					</c:if>
																					<c:if test="${count ==2 }">
																						<c:out value="${testQuestions[queCount].questions.option2}"></c:out>
																					</c:if>
																					<c:if test="${count ==3 }">
																						<c:out value="${testQuestions[queCount].questions.option3}"></c:out>
																					</c:if>
																					<c:if test="${count ==4 }">
																						<c:out value="${testQuestions[queCount].questions.option4}"></c:out>
																					</c:if>
																					<c:if test="${count ==5 }">
																						<c:out value="${testQuestions[queCount].questions.option5}"></c:out>
																					</c:if>
																				</td>
																				</tr>
																		</c:forEach>																		
																	
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
</table><br><br>
	</form:form>
	<!-- 	</div> -->
</body>
</html>