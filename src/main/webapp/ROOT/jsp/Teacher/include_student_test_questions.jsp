<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Insert title here</title>
<script>
function goToDiv(i){
	document.getElementById("sho"+i).scrollIntoView(true);
}
</script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<meta name="msapplication-TileImage" content="/mstile-144x144.png">
<meta name="msapplication-TileColor" content="#333333">
<meta name="theme-color" content="#ffffff">
<style>
.index {
	font-family: "Georgia";
	font-size: 15px;
}
.indexNo {
	font-family: "Georgia";
	font-size: 17px;
}
.tts {
	font-family: 'Big Caslon', 'Book Antiqua', 'Palatino Linotype', Georgia, serif;
	font-size: 15px;
	font-weight: bold;
}
.queshead {
	font-weight: bold;
	padding-bottom: 6px;
	text-align:left;
	padding-right:5px;
}
.question {
	padding-bottom: 6px;
	text-align:left;
}
.idIndex {
	font-weight: bold;
	padding-right:5px;
}
.txtStyle {
	text-align:left;
	padding-bottom: 6px;
	font-size: 15px;
	font-weight: normal;
}
.paraTxtStyle0 {
   
    font-weight: normal;
    text-align: justify;
    word-break: unset;
	line-height: 28px;
}
</style>
</head>
<body>
<div class="container-fluid">
  <div class="row" style="padding-bottom:20px;">
    <div class="col-md-1"> </div>
    <div class="col-md-10 des">
      <div class="row txtStyle" id="shows" style="visibility: hidden;">
        <div class="col-md-12">
          <div id="msg" style="color: #0000FF"></div>
          <c:set var="mcqCount" value="0">
          </c:set>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <form:form id="testQuestionsForm" modelAttribute="studentAssignmentStatus">
            <c:set var="queCount" value="0">
            </c:set>
            <c:set var="quedisplayCount" value="1">
            </c:set>
            <c:set var="optionsCount" value="0" scope="page" />
            <input type="hidden" id="lessonId" name="lessonId" value="${lessonId}" />
            <input type="hidden" id="studentId" name="studentId" value="${studentId}" />
            <input type="hidden" id="assignmentId" name="assignmentId" value="${assignmentId}" />
            <input type="hidden" id="tab" name="tab" value="${tab}" />
            <form:hidden path="studentAssignmentId" />
            <form:hidden path="assignment.assignmentType.assignmentTypeId" value="${assignmentTypeId}"></form:hidden>
            <form:hidden path="assignment.usedFor" value="${usedFor}"></form:hidden>
            <div class="Divheads">
              <div class="row">
                <div class="row">
                  <div class="col-md-1"></div>
                  <div class="col-md-10"> <font color="" class="index"><strong>
                    <c:out value="${assignmentType.assignmentType}">
                    </c:out>
                    </strong></font> </div>
                </div>
              </div>
            </div>
            <c:choose>
              <c:when test="${assignmentTypeId==7}">
                <c:forEach items="${ssQuestions}" var="ssql">
                  <div style="background-color: #c7e4e8; padding: 1em; margin: 0;"> <font color="000000">
                    <div class="row tabtxt">
                      <div class="col-md-12">
                        <c:out value="${quedisplayCount}">
                        </c:out>
                        .&nbsp;
                        <label class="tits">
                          <c:out value="${ssql.subQuestion}">
                          </c:out>
                        </label>
                      </div>
                    </div>
                    </font> </div>
                  <br>
                  <c:forEach var="count" begin="0" end="${ssql.numOfOptions - 1}">
                    <div style="padding-left: 2em">
                      <form:hidden path="assignmentQuestions[${queCount}].questions.questionId" />
                      <form:hidden path="assignmentQuestions[${queCount}].questions.answer" />
                      <form:hidden path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
                      <form:hidden path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
                      <div class="row ques">
                        <div class="col-md-3 txtStyle">
                          <form:radiobutton path="assignmentQuestions[${queCount}].answer" value="option1" />
                          <c:out value="${testQuestions[optionsCount].questions.option1}">
                          </c:out>
                        </div>
                        <div class="col-md-3 txtStyle">
                          <form:radiobutton path="assignmentQuestions[${queCount}].answer" value="option2" />
                          <c:out value="${testQuestions[optionsCount].questions.option2}">
                          </c:out>
                        </div>
                        <div class="col-md-3 txtStyle">
                          <form:radiobutton path="assignmentQuestions[${queCount}].answer" value="option3" />
                          <c:out value="${testQuestions[optionsCount].questions.option3}">
                          </c:out>
                        </div>
                      </div>
                    </div>
                    <c:set var="optionsCount" value="${optionsCount + 1}" scope="page" />
                    <c:set var="queCount" value="${queCount+1}">
                    </c:set>
                  </c:forEach>
                  <br>
                  <c:set var="quedisplayCount" value="${quedisplayCount+1}">
                  </c:set>
                </c:forEach>
              </c:when>
              <c:when test="${assignmentTypeId == 19}">
                <c:set var="exis" value="0" />
                <c:forEach items="${ssQuestions}" var="ssql">
                  <div style="background-color: #c7e4e8; padding: 1em; margin: 0;"> <font color="000000">
                    <div class="row tabtxt">
                      <div class="col-md-12">
                        <pre class="paraTxtStyle0"> ${quedisplayCount}. ${ssql.subQuestion}</pre>
                      </div>
                    </div>
                    </font> </div>
                  <br>
                  <c:forEach var="count" begin="0" end="${ssql.numOfOptions-1}">
                    <div>
                      <input type="hidden" id="assignmentQuestions${queCount}" value="${testQuestions[queCount].assignmentQuestionsId}" name="questions" />
                      <form:hidden path="assignmentQuestions[${queCount}].questions.questionId" />
                      <form:hidden path="assignmentQuestions[${queCount}].questions.answer" />
                      <form:hidden path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
                      <form:hidden path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
                      <div class="row ques">
                        <div class="col-md-12 txtStyle text-left"> <span class="queshead">Question
                          <c:out value="${queCount+1}">
                          </c:out>
                          :</span> <span class="question">
                          <c:out value="${testQuestions[queCount].questions.question} ">
                          </c:out>
                          </span>
                          <c:if test="${testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 3 || testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 4 || testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 5}">
                            <c:if test="${testQuestions[queCount].secMarks==0}"> &nbsp;<font color="red"><br/>Correct Answer&nbsp;:&nbsp;
                              <c:out value="${testQuestions[queCount].questions.answer}">
                              </c:out>
                              </font> </c:if>
                          </c:if>
                        </div>
                        <div class="col-md-12 txtStyle">
                          <c:choose>
                            <c:when test="${testQuestions[queCount].questions.assignmentType.assignmentType == 'Multiple Choices'}">
                              <div class="row">
                                <c:forEach begin="1" end="${testQuestions[queCount].questions.numOfOptions}" var="count">
                                  <div class="col-md-3"> <span class="idIndex">${count}:</span>
                                    <form:radiobutton path="assignmentQuestions[${queCount}].answer"
																					value="option${count}"/>
                                    <c:if test="${count == 1 }">
                                      <c:out value="${testQuestions[queCount].questions.option1}">
                                      </c:out>
                                    </c:if>
                                    <c:if test="${count ==2 }">
                                      <c:out value="${testQuestions[queCount].questions.option2}">
                                      </c:out>
                                    </c:if>
                                    <c:if test="${count ==3 }">
                                      <c:out value="${testQuestions[queCount].questions.option3}">
                                      </c:out>
                                    </c:if>
                                    <c:if test="${count ==4 }">
                                      <c:out value="${testQuestions[queCount].questions.option4}">
                                      </c:out>
                                    </c:if>
                                    <c:if test="${count ==5 }">
                                      <c:out value="${testQuestions[queCount].questions.option5}">
                                      </c:out>
                                    </c:if>
                                  </div>
                                </c:forEach>
                              </div>
                            </c:when>
                            <c:otherwise>
                              <div class="row">
                                <div class="col-md-12"><b>Answer : </b>
                                  <c:out value="${testQuestions[queCount].answer}">
                                  </c:out>
                                </div>
                              </div>
                            </c:otherwise>
                          </c:choose>
                        </div>
                      </div>
                      <div class="row ques">
                        <div class="col-md-12 txtStyle text-left">
                          <c:if test="${testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 3 || testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 4 || testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 5}">
                            <input type="hidden" id="maxMarks${queCount}" name="maxMarks" value="${testQuestions[queCount].maxMarks}" />
                            <input type="hidden" id="secMarks${queCount}" name="secMarks" value="${testQuestions[queCount].secMarks}" />
                            <input type="hidden" id="teacherComment${queCount}" name="teacherComments" value="--" />
                          </c:if>
                        </div>
                      </div>
                      <c:if test="${testQuestions[queCount].questions.assignmentType.assignmentTypeId eq 2}" >
                        <c:set var="exis" value="1" />
                        <div class="row ques">
                          <div class="col-md-2">Max Marks :</div>
                          <div class="col-md-10">
                            <input type="text" id="maxMarks${queCount}" value="${testQuestions[queCount].maxMarks}" name="maxMarks" />
                          </div>
                        </div>
                        <div class="row ques">
                          <div class="col-md-2">Marks Secured :</div>
                          <div class="col-md-10">
                            <input type="text" id="secMarks${queCount}" value="${testQuestions[queCount].secMarks}" name="secMarks" />
                          </div>
                        </div>
                        <div class="row ques">
                          <div class="col-md-2">Comments :</div>
                          <div class="col-md-10">
                            <textarea id="teacherComment${queCount}" name="teacherComments" rows="3" cols="50" required >${fn:trim(testQuestions[queCount].teacherComment)}</textarea>
                            <form:hidden id="audioData${queCount}"  path="${testQuestions[queCount].audioData}" required="required" />
                          </div>
                        </div>
                        <div class="row ques">
                          <div class="col-md-2"></div>
                          <div class="col-md-10">
                            <audio controls>
                              <source src="loadDirectUserFile.htm?usersFilePath=${comprehensionFilePath}/${testQuestions[queCount].assignmentQuestionsId}.wav" type="audio/wav">
                              Your browser does not support the audio element. </audio>
                          </div>
                        </div>
                      </c:if>
                    </div>
                    <br>
                    <c:set var="queCount" value="${queCount+1}">
                    </c:set>
                  </c:forEach>
                  <br/>
                  <c:set var="quedisplayCount" value="${quedisplayCount+1}">
                  </c:set>
                </c:forEach>
              </c:when>
              <c:when test="${assignmentTypeId==8}"> <br>
                <input type="hidden" id="csId" name="csId" value="${csId}" />
                <div class="row">
                  <div class="col-md-2">Select Grading</div>
                  <div class="col-md-10">
                    <select name="gradeTypeId" class="listmenu" id="gradeTypeId" onChange="getGradingDetails(${studentAssignmentId},${userTypeId})" required="required">
                      <option value="0">select GradeType</option>
                      <c:forEach items="${gradingTypes}" var="ul">
                        <option value="${ul.gradingTypesId}">${ul.gradingTypes}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12" id="displayGrading"></div>
                </div>
              </c:when>
              <c:when test="${assignmentTypeId==20}">
                <div class="row">
                  <div class="col-md-3"><b>Test</b></div>
                  <div class="col-md-9"><b>Accuracy Score</b></div>
                </div>
                <c:set var="marks" value="0">
                </c:set>
                <c:set var="coun" value="0">
                </c:set>
                <c:set var="co" value="0" />
                <c:forEach items="${benchQuestions}" var="questionList"
								varStatus="count">
                  <c:forEach items="${questionList.fluencyMarks}" var="typesList">
                    <input type="hidden" id="marks" name="marks"
										value="${typesList.marks}" />
                    <c:if test="${queCount%2==0}">
                      <c:set var="co" value="${co+1}">
                      </c:set>
                    </c:if>
                    <c:set var="yes" value="0">
                    </c:set>
                    <c:if test="${empty typesList.marks}">
                      <c:set var="marks" value="0">
                      </c:set>
                    </c:if>
                    <c:if test="${not empty typesList.marks}">
                      <c:set var="marks" value="${typesList.marks}">
                      </c:set>
                    </c:if>
                    <div class="row">
                      <div class="col-md-3"><a
											href='javascript:openChildWindow(${questionList.assignmentQuestionsId},${questionList.studentAssignmentStatus.studentAssignmentId},4,${typesList.readingTypes.readingTypesId},${typesList.gradingTypes.gradingTypesId},${userTypeId})'>
                        <c:out value="${typesList.readingTypes.readingTypes}" />
                        <c:out value="${co}" />
                        </a></div>
                      <div class="col-md-9">
                        <input type='hidden'
											id='marks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}'
											name='marks' value='${typesList.marks}'/>
                        <input type='text' id='accuracyMarks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}' name='accuracyMarks' value='${typesList.accuracyScore}' maxlength='4' size='4' disabled />
                        <label class="tabtxt">%</label>
                        <input type="hidden" name="assignmentQuesList" id="assignQuesList:${co}" value="${questionList.assignmentQuestionsId}" />
                      </div>
                    </div>
                    <c:set var="queCount" value="${queCount1}">
                    </c:set>
                  </c:forEach>
                </c:forEach>
                <br>
                <c:if test="${gradeTypesId==1}">
                  <div class="row">
                    <div class="col-md-3">Notes:</div>
                    <div class="col-md-9">
                      <textarea id='teacherNotes'
											name='teacherNotes'><c:out
												value="${teacherComment}"></c:out>
</textarea>
                    </div>
                  </div>
                </c:if>
                <div id="gradeBenchmark" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;align:center"></div>
              </c:when>
              <c:otherwise>
                <c:if test="${assignmentTypeId == 3 ||assignmentTypeId == 4 ||assignmentTypeId == 5}">
                  <div style="background-color:#c7e4e8;padding:1em;margin:0;">
                    <c:forEach items="${testQuestions}" varStatus="cnt" var="questionList">
                      <c:set var="col" value="#0B610B">
                      </c:set>
                      <label onClick="goToDiv(${cnt.count})"
												style="cursor: pointer;" class="indexNo"> <span id="qu${cnt.count}"
												style="color:${col}" class="tts">
                        <c:out value="${cnt.count}" />
                        </span></label>
                      &nbsp;&nbsp;&nbsp;&nbsp;
                      <c:if test="${cnt.count == 20}"> <br>
                        <br>
                      </c:if>
                    </c:forEach>
                  </div>
                </c:if>
                <c:forEach items="${testQuestions}" var="questionList">
                  <form:hidden path="assignmentQuestions[${queCount}].questions.questionId" />
                  <form:hidden path="assignmentQuestions[${queCount}].questions.answer" />
                  <form:hidden path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
                  <c:if test="${assignmentTypeId == 1 ||assignmentTypeId == 2 ||assignmentTypeId == 6 }">
                    <div style="background-color: #c7e4e8; padding: 1em; margin: 0;"><font color="000000">
                      <input type="hidden" id="question${queCount}" 
												value="${questionList.assignmentQuestionsId}" name="questions" />
                      <label class="ques">
                        <c:out value="${quedisplayCount}">
                        </c:out>
                        .&nbsp;&nbsp; </label>
                      </font> <b>
                      <label class="ques">
                        <c:out value="${questionList.questions.question}">
                        </c:out>
                      </label>
                      </b> </div>
                  </c:if>
                  <c:if
								test="${assignmentTypeId == 3 ||assignmentTypeId == 4 ||assignmentTypeId == 5 }">
                    <div id="sho${quedisplayCount}"
									style="align: center; text-align: center;">
                      <div class="row">
                        <div class="col-md-12">
                          <c:choose>
                            <c:when test="${questionList.secMarks==1}">
                              <input type="hidden" id="question${queCount}"
																	value="${questionList.assignmentQuestionsId}"
																	name="questions" />
                              <c:out value="${quedisplayCount}">
                              </c:out>
                              .&nbsp;&nbsp; <b>
                              <c:out value="${questionList.questions.question}">
                              </c:out>
                              </b> </c:when>
                            <c:otherwise>
                              <input
																	type="hidden" id="question${queCount}"
																	value="${questionList.assignmentQuestionsId}"
																	name="questions" />
                              <c:out value="${quedisplayCount}">
                              </c:out>
                              .&nbsp;&nbsp; <b>
                              <c:out value="${questionList.questions.question}">
                              </c:out>
                              </b>&nbsp;&nbsp;&nbsp; <font color="#FF0000">Correct Answer :&nbsp;
                              <c:out
																			value="${questionList.questions.answer}">
                              </c:out>
                              </font> </c:otherwise>
                          </c:choose>
                        </div>
                      </div>
                    </div>
                  </c:if>
                  <c:if
									test="${assignmentTypeId==1|| assignmentTypeId==2 || assignmentTypeId==6 }">
                    <div style="padding-left: 3em" class="tit">
                      <div class="row">
                        <div class="col-md-6 ques">Answer :</div>
                        <div class="col-md-6 ques">
                          <textarea id="${queCount}" rows=3 cols=50 required=required><c:out
													value="${questionList.answer}"></c:out>
</textarea>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-md-6 ques">Max Marks :</div>
                        <div class="col-md-6 ques">
                          <input type="text" id="maxMarks${queCount}"
												value="${questionList.maxMarks}" name="maxMarks"
												required="required">
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-md-6 ques">Marks Secured :</div>
                        <div class="col-md-6 ques">
                          <input type="text" id="secMarks${queCount}" value="${questionList.secMarks}"
											name="secMarks" required>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-md-6 ques">Comments :</div>
                        <div class="col-md-6 ques">
                          <textarea id="teacherComment${queCount}" name="teacherComments" rows=3 cols=50 required=required>${fn:trim(questionList.teacherComment)}</textarea>
                        </div>
                      </div>
                    </div>
                  </c:if>
                  <c:if test="${assignmentTypeId==3}">
                    <div style="padding-left: 3em" class="ques">
                      <c:forEach begin="1" end="${testQuestions[queCount].questions.numOfOptions}" var="count">
                        <div class="row">
                          <div class="col-md-3">${count}:</div>
                          <div class="col-md-9">
                            <form:radiobutton path="assignmentQuestions[${queCount}].answer" value="option${count}"/>
                            <c:if test="${count == 1 }">
                              <c:out value="${testQuestions[queCount].questions.option1}">
                              </c:out>
                            </c:if>
                            <c:if test="${count ==2 }">
                              <c:out value="${testQuestions[queCount].questions.option2}">
                              </c:out>
                            </c:if>
                            <c:if test="${count ==3 }">
                              <c:out value="${testQuestions[queCount].questions.option3}">
                              </c:out>
                            </c:if>
                            <c:if test="${count ==4 }">
                              <c:out value="${testQuestions[queCount].questions.option4}">
                              </c:out>
                            </c:if>
                            <c:if test="${count ==5 }">
                              <c:out value="${testQuestions[queCount].questions.option5}">
                              </c:out>
                            </c:if>
                            <</div>
                        </div>
                      </c:forEach>
                      <c:set var="mcqCount" value="${mcqCount+1}">
                      </c:set>
                    </div>
                  </c:if>
                  <c:if test="${assignmentTypeId == 4 }">
                    <div style="padding-left: 3em">
                      <div class="row">
                        <div class="col-md-3">Answer:</div>
                        <div class="col-md-9">
                          <form:input path="assignmentQuestions[${queCount}].answer" />
                        </div>
                      </div>
                    </div>
                  </c:if>
                  <c:if test="${assignmentTypeId==5}">
                    <div class="row">
                      <div class="col-md-12">
                        <form:radiobutton
													path="assignmentQuestions[${queCount}].answer" value="true" />
                        True</div>
                      <div class="col-md-12">
                        <form:radiobutton
													path="assignmentQuestions[${queCount}].answer" value="false" />
                        False</div>
                    </div>
                  </c:if>
                  <c:if test="${fn:length(testQuestions) ne quedisplayCount}">
                    <c:if test="${assignmentTypeId==3|| assignmentTypeId==4 || assignmentTypeId==5 }">
                      <hr>
                    </c:if>
                  </c:if>
                  <c:set var="queCount" value="${queCount+1}">
                  </c:set>s
                  <c:set var="quedisplayCount" value="${quedisplayCount+1}">
                  </c:set>
                </c:forEach>
              </c:otherwise>
            </c:choose>
            <br>
            <br>
            <c:if test="${assignmentTypeId==1|| assignmentTypeId==2 || assignmentTypeId==6 || assignmentTypeId==8 || assignmentTypeId==20}">
              <div class="row" id="showButton" style="visibility:visible;">
                <div class="col-md-12">
                  <div class="button_green" id="btSubmitChanges" name="btSubmitChanges" onClick="gradeTest(${assignmentId},${assignmentTypeId}); return false;" style="width:90px;">Submit Changes</div>
                </div>
              </div>
            </c:if>
            <c:if test="${assignmentTypeId == 19 && exis==1}" >
              <div class="row">
                <div class="col-md-12">
                  <div class="button_green" id="btSubmitChanges" name="btSubmitChanges" onClick="gradeTest(${assignmentId},${assignmentTypeId}); return false;" style="width:90px;">Submit Changes</div>
                </div>
              </div>
            </c:if>
          </form:form>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>