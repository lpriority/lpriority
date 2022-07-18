<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<style>
.des {
	border: 1px solid black;
	border-collapse: collapse;
	border-color: #d3d3d3;
}

hr {
	border-top: 1px solid #d3d3d3;
}

.ques {
	font-family: "Georgia";
	font-size: 16px;
}

.tit {
	font-family: "Georgia";
	font-size: 15px;
}
.tits{
     font-family: "Georgia";
	font-size: 15px;
	font-weight:bold;
}

.answ {
	font-family: "Georgia";
	font-size: 16px;
	font-weight: bold;
}

body {
	font-family: "Georgia";
	font-size: 16px;
}
	.ui-widget {
    font-family: Verdana,Arial,sans-serif;
    font-size: 0.8em;
}
 .tts{
     font-family: 'Big Caslon', 'Book Antiqua', 'Palatino Linotype', Georgia, serif;
    font-size: 15px;
    font-weight: bold;
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
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/my_recorder/recorder.js"></script>
<script src="resources/javascript/common/js_speech_synthesis.js"></script>
<script type="text/javascript">
function autoSave(id) {
	var assignmentTypeId=document.getElementById("assignmentTypeId").value;
	document.getElementById('operation').value="save";
	document.getElementById('selectedCsId').value=$('#csId').val();
	var answer="";
	var questionId = document.getElementsByName('assignmentQuestions['+id+'].questions.questionId')[0].value;
	var assignmentQuestionId = document.getElementsByName('assignmentQuestions['+id+'].assignmentQuestionsId')[0].value;
	var studentAssignmentId = document.getElementsByName('studentAssignmentId')[0].value;
	var userType = document.getElementById("userType").value;
	var ele = document.getElementsByName("assignmentQuestions["+id+"].answer")[0];
	if(ele.type == "radio"){
		var isChecked = $('[name="assignmentQuestions['+id+'].answer"]').is(':checked');
		if(isChecked)
		 	answer = $('input[name="assignmentQuestions['+id+'].answer"]:checked').val();

	}else if(ele.type == "text"){
		answer = document.getElementsByName('assignmentQuestions['+id+'].answer')[0].value;
	}else if(ele.type == "textarea"){
		answer = document.getElementsByName('assignmentQuestions['+id+'].answer')[0].value;
		
	}
	if(ele.type != "textarea" && assignmentTypeId!=7 && assignmentTypeId!=19)	{
 	if(answer.length!=0){
 	 var co=parseInt(id)+1;
 	 var x = document.getElementById("qu"+co);
 	x.style.color = '#0B610B';
 	}}
	if(userType == "student above 13" || userType == "student below 13"){
	$.ajax({
		type : "GET",
		url : "autoSaveAssignment.htm",
		data : "assignmentQuestionId=" +assignmentQuestionId+"&answer=" + encodeURIComponent(answer),
		success : function(response) {
			 systemMessage(response);
		}
	});
}
}	
var isRecording = false;
function recordCompreAnswer(id,audioFileName,count){	 
	if(isRecording == false){
		//systemMessage("Recording.....");
 		$("#resultAns"+count).fadeOut(100, function() {
			  $(this).html("<font color='green' size='2'><b>Recording...</b></font>").fadeIn(100, function() {
 				  $("#resultAns"+count).show();
 			  })
 		});
		id = id+count;
		recordAudio();
		isRecording = true;
	 }else{
		  alert("Recording already started. Click on Stop button to proceed further");  
		  return false;
	  }
 } 
 
 function stopAnswer(id,count){
	 if(isRecording == true){
		 console.debug("Recoding done.");
		 isRecording = false;		 
		 $('#span'+count).show();
		 $("#resultAns"+count).fadeOut(100, function() {
			 			  $(this).html("<font color='red' size='2'><b>Stopped !!</b></font>").fadeIn(100, function() {
			 			    $(this).fadeOut(2000, function(){			    	
			 			    })
			 			  })
			 		 });
		 $("#loading-div-background1").show();
		 stopRecording(function(base64){ 
			 document.getElementById(id+""+count).value = base64;
			 autoSaveCompreAudio(count,'save');
			 id = id+count;
		 });
	 }else{
		 alert("Recording not yet started !!");
		 return false;
	 }
  }
 function autoSaveCompreAudio(index,operation){
	 document.getElementById("index").value = index;
	 var formObj = document.getElementById("questionsForm");
	 var formData = new FormData(formObj);
		$.ajax({
			url: 'autoSaveCompreAudio.htm',
			type: 'POST',
			data: formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(data){
				$("#loading-div-background1").hide();
				var parsedData = JSON.parse(data);
				 systemMessage("Saved !!");
			}
		});	
 }
</script>
<script type="text/javascript">
function goToDiv(i){
  document.getElementById("sho"+i).scrollIntoView(true);
}
</script>

</head>
<body>
	<form:form id="questionsForm" modelAttribute="studentAssignmentStatus"
		action="submitTest.htm" method="POST">

		<table border=0 align="center" width="80%" class="des">
			<tr>
				<td><form:hidden path="operation" id="operation" /> <input
					type="hidden" id="selectedCsId" name="selectedCsId" /> 
					<input type="hidden" id="studentAssignmentId" name="studentAssignmentId" value="${studentAssignmentId}" />
					<input type="hidden" name="userType" id="userType" value="${userReg.user.userType}" />
					<input type="hidden" id="assignmentTypeId" name="assignmentTypeId" value="${assignmentTypeId}" />
					<input type="hidden" id="testCount" name="testCount" value="${testCount}" />
					    <c:set var="queCount" value="0"></c:set>
						 <c:set var="quedisplayCount"
						value="1"></c:set> <c:set var="optionsCount" value="0"
						scope="page" /> <input type="hidden" id="tab" name="tab"
					value="${tab}" /> <form:hidden path="studentAssignmentId" /> <form:hidden
						path="assignment.assignmentType.assignmentTypeId"
						value="${assignmentTypeId}"></form:hidden> <form:hidden
						path="assignment.usedFor" value="${usedFor}"></form:hidden>

					<div style="background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#0696aa) ); padding: 1em;font-size: 15px; align: center">
						<table width="60%">
							<tr>
								<td width="50%"><font color="white"><strong><c:out
												value="${assignmentType.assignmentType}"></c:out> </strong></font></td>
								<td align="right"><br></td>
							</tr>
						</table>
					</div>
					 <c:choose>
						<c:when test="${assignmentTypeId==7}">
							<c:forEach items="${ssQuestions}" var="ssql">
 
 
                             <div
										style="background-color: #cce3e7; padding: 0.1em; margin: 0;">
										<font color="000000"><table>
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
												<td width='20%' height='30' align='left' valign="top"><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option1" onchange="autoSave('${queCount}')" /> <c:out
														value="${ques.option1}"></c:out></td>
												<td width='20%' height='30' align='left' valign="top"><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option2" onchange="autoSave('${queCount}')" /> <c:out
														value="${ques.option2}"></c:out></td>
												<td width='20%' height='30' align='left' valign="top"><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option3" onchange="autoSave('${queCount}')" /> <c:out
														value="${ques.option3}" ></c:out>
												</td>
												<td align="right"><font color="blue" size="3"><b>
												<div id="resultDiv${queCount}" class="status-message"></div></b></font></td>
											   <td width="2%"><span id="span${status.index}" style="display:none;font-size: 200%;color:green;text-decoration:none;font-size:26px;" class="fa fa-check"></span></td>
											</tr>
										</table>
									</div>

									<c:set var="optionsCount" value="${optionsCount + 1}"
										scope="page" />
									<c:set var="queCount" value="${queCount+1}"></c:set>
									</div>
								</c:forEach>
								<br>
								<c:set var="quedisplayCount" value="${quedisplayCount+1}"></c:set>
							</c:forEach>
						</c:when>

                        <c:when test="${assignmentTypeId==19}">
                         <input type="hidden" id="index" name="index" value=-1 />
							<c:forEach items="${compreQuestions}" var="comql">
 
 
                             <div
										style="background-color: #cce3e7; padding: 0.1em; margin: 0;">
										<table  style="width: 100%;">
									<tr>
										<td height="10" align="center" valign="middle"
											class="tabtxt">
											<table style="width: 100%;">
												<tr>
														<td align="center"><h3>${comql.title}</h3></td>
													</tr>
												<tr>
												<td width="100%" height="30" align="left">
												<div>		
													 <pre class="paraTxtStyle0"> ${comql.subQuestion}</pre>
												</dv>
												</td>
												</tr>
											</table>
										</td>
									</tr>
									
								</table>
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
														<B><label style="font-family: Georgia;font-size: 12px;"> <c:out
																	value="${ques.question}"></c:out></label></B>
													</td>
														
														
													
												</tr>
											<tr><td><c:choose>
												<c:when test="${ques.assignmentType.assignmentType == 'Short Que'}">
													<form:hidden id="audioData${queCount}"  path="assignmentQuestions[${queCount}].audioData" required="required" />
													<table style="width: 100%;">
														
														<tr>
															<td>
																<form:textarea required="true" rows="5" cols="70" path="assignmentQuestions[${queCount}].answer" 
																class="tit" onblur="autoSave('${queCount}')"/>
															</td>
															<td width="20%">
															<div id="recordImg" name="recordImg" class="button_light_green" align="right" onclick="recordCompreAnswer('audioData','audioVoice.wav',${queCount})">RECORD</div>&nbsp;&nbsp;
															<div id="stopImg" name="stopImg" class="button_pink" align="right" onclick="stopAnswer('audioData',${queCount})">STOP</div></td>
														<td width="10%" align="center" style="font-size:11px;"><font color="blue"><b><div id="resultAns${queCount}"></div></b></font></td>
														</tr>
													</table>
												</c:when>
												<c:when test="${ques.assignmentType.assignmentType == 'Multiple Choices' }">
													<table>
														<c:forEach begin="1" end="${ques.numOfOptions}" var="count">														
															<tr style="width: 100%;">
																<td><form:radiobutton
																		path="assignmentQuestions[${queCount}].answer"
																		value="option${count}" onchange="autoSave('${queCount}')" /></td>
																<td>${count}:</td>
																<c:if test="${count == 1 }">
																	<td><c:out value="${ques.option1}"></c:out></td>
																</c:if>
																<c:if test="${count ==2 }">
																	<td><c:out value="${ques.option2}"></c:out></td>
																</c:if>
																<c:if test="${count ==3 }">
																	<td><c:out value="${ques.option3}"></c:out></td>
																</c:if>
																<c:if test="${count ==4 }">
																	<td><c:out value="${ques.option4}"></c:out></td>
																</c:if>
																<c:if test="${count ==5 }">
																	<td><c:out value="${ques.option5}"></c:out></td>
																</c:if>
															</tr>
															<tr>
																<td colspan="3">&nbsp;</td>
															</tr>
														</c:forEach>														
													</table>
												</c:when>
												<c:when test="${ques.assignmentType.assignmentType == 'Fill in the Blanks'}">
													<table style="width: 100%;">
														<tr>
															<td><div>
																	<lable class="ques">Answer :</lable>
																	<form:input path="assignmentQuestions[${queCount}].answer"
																		onblur="autoSave('${queCount}')" />
																</div> <br></td>
															<td width="50%">&nbsp;</td>
														</tr>
													</table>
												</c:when>
												<c:when test="${ques.assignmentType.assignmentType == 'True or False'}">
													<table style="width: 100%;">
														<tr>
															<td><form:radiobutton
																	path="assignmentQuestions[${queCount}].answer"
																	value="true" onchange="autoSave('${queCount}')" />&nbsp;True
															</td>
															<td><form:radiobutton
																	path="assignmentQuestions[${queCount}].answer"
																	value="false" onchange="autoSave('${queCount}')" />&nbsp;False</td>
														</tr>
													</table>
												</c:when>
												<c:otherwise>
													<!--  -->
												</c:otherwise>
											</c:choose></td>
											<td align="right">
												<font color="blue" size="3"><b>
													<div id="resultDiv${queCount}" class="status-message"></div>
													</b></font>
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
								<div style="background-color: #cce3e7; padding: 1em; margin: 0;">
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

								<div id="ee-box" style="align: center; text-align: center;">
									<p class="ques">
										<em>Each Question have <strong>One</strong> Mark.
										</em>
									</p>
									<hr>
								</div>
							</c:if>
							<c:set var="mcqCount" value="0"> </c:set>
							<c:forEach items="${testQuestions}" var="questionList">
								<form:hidden
									path="assignmentQuestions[${queCount}].questions.questionId" />
								<form:hidden
									path="assignmentQuestions[${queCount}].questions.answer" />
								<form:hidden
									path="assignmentQuestions[${queCount}].studentAssignmentStatus.studentAssignmentId" />
								<c:if
									test="${assignmentTypeId==1|| assignmentTypeId==2 || assignmentTypeId==6 }">
									<div
										style="background-color: #cce3e7; padding: 0.1em; margin: 0;">
										<font color="000000"><table>
									<tr>
										<td colspan="5" height="10" align="center" valign="middle"
											class="tabtxt">
											<table style="width: 100%;">
												<tr>
												<td width="100%" height="30" align="left"><form:hidden
														path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
													<B><c:out value="${quedisplayCount}"></c:out> .&nbsp; 
														
														<c:set var="rowCount" value="${fn:length(questionList.questions.question)/100}" scope="page">  </c:set>
														<label class="tits">
														<textarea rows="${rowCount}" cols="100" style="overflow-y: auto;font-size:13px; " readonly="readonly" > ${questionList.questions.question}</textarea>
														</label>
														</B>
													</td>
														
														
													
												</tr>
											</table>
										</td>
									</tr>
									
								</table>
										</font>
									</div>
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
													
														
															<td width="100%" height="30" align="left"><form:hidden
														path="assignmentQuestions[${queCount}].assignmentQuestionsId" />
													<c:out value="${quedisplayCount}"></c:out> .&nbsp; <c:if
														test="${assignmentTypeId == 4 ||assignmentTypeId == 5 || assignmentTypeId == 3 }">
														<B><label class="tit"> <c:out
																	value="${questionList.questions.question}"></c:out></label></B>
													</c:if> </td>
														
														
													
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
							</div>
						</c:if>
											
								<c:if
									test="${assignmentTypeId==1|| assignmentTypeId==2 || assignmentTypeId==6 }">
									<div style="padding-left: 3em;background-color: #cce3e7">

										<table style="padding-left: 1em; padding-top: 0.8em"
											class="tit">
											<tr>
												<td class="ques">Answer :</td>
												<td>
											<tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td><form:textarea required="true" rows="5" cols="70"
														path="assignmentQuestions[${queCount}].answer" class="tit" onblur="autoSave('${queCount}')"/>
												</td>
												<td align="right"><font color="blue" size="3"><b>
															<div id="resultDiv${queCount}" class="status-message"></div>
													</b></font></td>
											</tr>
											<tr><td>&nbsp;</td></tr>
											<tr><td>&nbsp;</td></tr>
										</table>
									</div>
									
								</c:if>
								<c:if test="${assignmentTypeId==3}">
									<div style="padding-left: 3em">

										<table style="padding-left: 1em" class="ques">
											<c:forEach var="var" begin="0" end="${questionList.questions.numOfOptions-1}" varStatus="count"> 			
												<tr>
													<td><form:radiobutton
															path="assignmentQuestions[${queCount}].answer"
															value="option${count.count}" onchange="autoSave('${queCount}')" /></td>
													<td>${mcqOptionsTitles[count.index]}:</td>
													<td><c:out value="${mcqOptions[mcqCount]}"></c:out></td>
													 <td align="right"><font color="blue" size="3"><b>
															<div id="resultDiv${queCount}" class="status-message"></div>
													</b></font></td>
												</tr>
												<c:set var="mcqCount" value="${mcqCount+1}"> </c:set>
											</c:forEach><%-- 
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
											<tr>
												<td><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option2" onchange="autoSave('${queCount}')" /></td>
												<td>B:</td>
												<td><c:out value="${questionList.questions.option2}"></c:out></td>
											</tr>
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
											<tr>
												<td><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option3" onchange="autoSave('${queCount}')" /></td>
												<td>C:</td>
												<td><c:out value="${questionList.questions.option3}"></c:out></td>
											</tr>
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
											<tr>
												<td><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option4" onchange="autoSave('${queCount}')" /></td>
												<td>D:</td>
												<td><c:out value="${questionList.questions.option4}"></c:out></td>
											</tr>
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
											<tr>
												<td><form:radiobutton
														path="assignmentQuestions[${queCount}].answer"
														value="option5" onchange="autoSave('${queCount}')" /></td>
												<td>E:</td>
												<td><c:out value="${questionList.questions.option5}"></c:out></td>
												<td width="50%">&nbsp;</td>
												<td align="right"><font color="blue" size="3"><b>
															<div id="resultDiv${queCount}"></div>
													</b></font></td>
											</tr> --%>

										</table>
									</div>



								</c:if>
								<c:if test="${assignmentTypeId == 4 }">
									<div style="padding-left: 3em">

										<table style="padding-left: 1em" class="tit">
											<tr>
												<td><div>
														<lable class="ques">Answer :</lable>
														<form:input path="assignmentQuestions[${queCount}].answer"
															onblur="autoSave('${queCount}')" />
													</div> <br></td>
												<td width="50%">&nbsp;</td>

												<td align="right"><font color="blue" size="3"> <b>
												<div id="resultDiv${queCount}" class="status-message"></div></b></font></td>

											</tr>
										</table>
									</div>

								</c:if>
								<c:if test="${assignmentTypeId==5}">
									<div style="padding-left: 3em">
										<table style="padding-left: 1em">
											<tr>
												<td>
													<table class="ques">
														<tr>
															<td><form:radiobutton
																	path="assignmentQuestions[${queCount}].answer"
																	value="true" onchange="autoSave('${queCount}')" />&nbsp;True
															</td>
														</tr>
														<tr>
															<td colspan="3">&nbsp;</td>
														</tr>
														<tr>
															<td><form:radiobutton
																	path="assignmentQuestions[${queCount}].answer"
																	value="false" onchange="autoSave('${queCount}')" />&nbsp;False</td>
														</tr>
													</table>
												</td>
												<td width="50%">&nbsp;</td>

												<td align="right"><font color="blue" size="3"><b>
												<div id="resultDiv${queCount}" class="status-message"></div></b></font></td>
											</tr>
										</table>
									</div>
								</c:if>
								<c:if test="${fn:length(testQuestions) ne quedisplayCount}">
									<c:if
										test="${assignmentTypeId==3|| assignmentTypeId==4 || assignmentTypeId==5 }">
										<hr>
									</c:if>
								</c:if>
								<c:set var="queCount" value="${queCount+1}"></c:set>
								<c:set var="quedisplayCount" value="${quedisplayCount+1}"></c:set>


							</c:forEach>
						</c:otherwise>
					</c:choose></td>
			</tr>			
		</table>
		<br>
		<br>
		<c:if test="${userReg.user.userType != 'parent'}">
			<table align="center" style="background-color: #FFFFFF;font-size:14px;" id="submitDiv">
				<tr>
					<td><div class="button_green" onclick="submitForm('${queCount}','${testCount}');" height="52" width="50" >Submit Changes</div></td>
					<td align='center'><div class="button_green" onClick="clearForm('${queCount}'); return false;" height="52" width="50">Clear</div></td>
				</tr>
			</table>
		</c:if>
	</form:form>

</body>
</html>