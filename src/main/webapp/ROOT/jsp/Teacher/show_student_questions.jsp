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
.ui-dialog > .ui-widget-content {background: white;}
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
			<td><div id="msg" style="color: #0000FF"></div> <c:set var="mcqCount" value="0"></c:set></td>
		</tr>
	</table>


	<!-- 	<div id="testQuestions" style="visibility: visible;"> -->
	<form:form id="testQuestionsForm"
		modelAttribute="studentAssignmentStatus">

		<table border=0 align="center" width="60%" class="des">
			<tr>
				<td><c:set var="queCount" value="0"></c:set> <c:set
						var="quedisplayCount" value="1"></c:set> <c:set var="optionsCount"
						value="0" scope="page" />  <form:hidden
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
						<c:when test="${assignmentTypeId==14}">
							
						<table align="center"><tr>
				<td class="tabtxt">Question File :<a href="downloadFile.htm?filePath=${jacQuestionFilePath}"><c:out value="${jacTitles[0].jacQuestionFile.filename}"></c:out></a>
				</td>	</tr>			
			
			</table>
			 <table border=1 align="center">
             <tr><td class="tabtxt">Title</td><td class="tabtxt">Student Answers</td><td class="tabtxt">Correct Answer</td><td class="tabtxt">Marks</td></tr>
		
			<c:set var="a" value="0" />
			<c:set var="b" value="0" />
			<c:set var="s" value="0" />
			<c:forEach items="${jacTitles}" var="jtl">
			<tr>
				<td class="txtStyle">
												
				<input type="hidden" name="titleid" id='titleid' value="${jtl.jacTemplateId}" /> 
					<c:out value="${jtl.titleName}"></c:out>
				</td>
				<td class="txtStyle">
				<c:forEach var="n" begin="0" end="${jtl.noOfQuestions-1}" >
				<c:choose>
				<c:when test="${testQuestions[a].secMarks==1}"> 
				<input type='text' name='answer' value='${testQuestions[a].answer}' id='answer' size='35' readonly /><br>
				 </c:when>
				 <c:otherwise>
				 <input type='text' name='answer' value='${testQuestions[a].answer}' style='color:red' id='answer' size='35' readonly /><br>
				 </c:otherwise>
				 </c:choose>
				  <c:set var="a" value="${a+1}" />
				</c:forEach>
				</td>
				<td class="txtStyle">
				<c:forEach var="n" begin="0" end="${jtl.noOfQuestions-1}" >
				<input type='text' name='answer' value='${testQuestions[b].questions.answer}' id='originalAnswer' size='35' readonly/><br>
				 <c:set var="b" value="${b+1}" />
				</c:forEach>
				 </td><td class="txtStyle">
				 <c:forEach var="n" begin="0" end="${jtl.noOfQuestions-1}" >
				 <input type="hidden" name="qid" id="qid${s}" value="${testQuestions[s].assignmentQuestionsId}" />
				 <input type='text' name='marks' value='${testQuestions[s].secMarks}' id='marks${s}' size='20' onblur="updateJacMarks(${s})"/><br>
				 <c:set var="s" value="${s+1}" />
				 </c:forEach>
				 </td>
			</tr>
			</c:forEach>
			</table>
										
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
									
										<%-- <tr>
											<td><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option1" /></td>
													<td>A:</td>
											        <td> <c:out value="${questionList.questions.option1}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;
									               </td>
									     </tr> --%>
									     <c:forEach begin="0" end="${questionList.questions.numOfOptions-1}" varStatus="count">
										<tr>
											<td><form:radiobutton
													path="assignmentQuestions[${queCount}].answer"
													value="option${count.count }" /></td>
													<td>${mcqOptionsTitles[count.index]}:</td>
											        <td> <c:out value="${mcqOptions[mcqCount]}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;
									               </td>
									     </tr>
									     
								     </c:forEach>
								     <c:set var="mcqCount" value="${mcqCount+1}"></c:set>
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