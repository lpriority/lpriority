<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
		<%@ include file="../CommonJsp/include_resources.jsp" %>
		<script type="text/javascript">
		var maxTime;
		var currentSeconds = 0;
		var currentMinutes = 0;
		var pauseTimer = false;
		$(document).ready(function() {
			$('#statusMessage').fadeOut(3000);
			
		});
		 $( function() {
			maxTime = document.getElementById('testTime').value*60;
			if(maxTime && maxTime != 0){
				$('#timerDiv').show();
		    	countDown();
			}
		  } );
		 function countDown() { 	
			currentMinutes = Math.floor(maxTime / 60);
			currentSeconds = maxTime % 60;
		    if(currentSeconds <= 9) currentSeconds = "0" + currentSeconds;
		    if(document.getElementById("minute"))
		   		 document.getElementById("minute").innerHTML = currentMinutes + ":" + currentSeconds; //Set the element id you need the time put into.
		    if(maxTime > 0){ 
		    	if(!pauseTimer){
			    	setTimeout('countDown()',1000);
			        maxTime--;
		    	}
		    }else{
		    	submitMathAssessmentTest();
		    }		   
		 }
		 function submitMathAssessmentTest(){
			 pauseTimer = true; 
			 var testCount = document.getElementById('testCount').value;
			 var formObj = document.getElementById("mathTestForm");
			 var formData = new FormData(formObj);
			 $("#loading-div-background").show();
	   		 $.ajax({  
	   			url: 'submitMathAssessmentTest.htm',
				data: formData,
				type: 'POST',
				mimeType:"multipart/form-data",
				contentType: false,
				cache: false,
				processData:false,
				success: function(data){
					$("#loading-div-background").hide();
					window.parent.$('#row'+testCount).remove();
					$('#testDiv').html("");
					$('#testDiv').html(data);
					var returnMessage = document.getElementById('returnMessage').value;
		   	    	systemMessage(returnMessage);
		   	    	
	   	    	},  
		   		error : function(error) {
		           $("#loading-div-background").hide();
		       }	
	   		 });
		 }
		 function autoSaveMathAnswer(id){
			 var answer = document.getElementsByName('mathAssessMarks['+id+'].answer')[0].value;
			 var mathAssessMarksId = document.getElementsByName('mathAssessMarks['+id+'].studentMathAssessMarksId')[0].value;
			 $.ajax({
					type : "GET",
					url : "autoSaveMathAnswer.htm",
					data : "mathAssessMarksId=" +mathAssessMarksId+"&answer=" +encodeURIComponent(answer),
					success : function(response) {
						//systemMessage(response);
					}
				});	
		 }
		</script>
		<style type="text/css">
			.timer {
				border: 2px #03C2DA solid;
				width: 190px;
				height: 50px;
				line-height: 50px;
				font-size: 36px;
				font-family: "Courier New", Courier, monospace;
				text-align: center;
				color : #4BA6CC;
				margin: 5px;
			}
		</style>
	</head>
	<title></title>
	<body>
	<div id="testDiv" style="max-height: 100%; overflow: auto;">
		<input type="hidden" id="testTime" value="${mathQuizQuestions[0].studentAssignmentStatus.assignment.recordTime}">
		<input type="hidden" id="returnMessage" name="returnMessage" value="${returnMessage}">
		<c:set var="rows" value="${fn:length(mathConversionTypes)}"></c:set>
		<c:set var="oldValue" value="0"></c:set>
		<table style="width: 100%; border: 1;">
			<tr>	
				<c:forEach items="${mathConversionTypes}" var="mcType" >
					<th style="padding-top: 1em;padding-bottom: 1em;" align="center">
						${mcType.conversionType}
					</th>
				</c:forEach>
			</tr>
			<form:form id="mathTestForm" name="mathTestForm" modelAttribute="sAssignmentStatus" action="submitMathAssessmentTest.htm" method="POST">
			<input type="hidden" id="testCount" name="testCount" value="${testCount}">
			<c:set var="cnt" value="0"></c:set>		
				<c:forEach items="${mathQuizQuestions}" var="que" >
					<c:choose>
						 <c:when test="${oldValue !=  que.mathQuizQuestions.mathQuiz.mathQuizId}">							
							 <c:set var="oldValue" value="${que.mathQuizQuestions.mathQuiz.mathQuizId}"></c:set>			
							 <tr>
								<c:forEach items="${que.mathQuizQuestions.mathQuiz.mathQuizQuestions}" var="question">						    
									<td align="center">							
										<c:choose>
											<c:when test="${question.isBlank == 'false'}">
												${question.actualAnswer}
											</c:when>
											<c:otherwise>	
												<c:choose>
													<c:when test="${empty mathQuizQuestions[cnt].mark}">
														<c:set var="brColor" value=""></c:set>
													</c:when>
													<c:when test="${mathQuizQuestions[cnt].mark eq 0 and sAssignmentStatus.status ne 'pending'}">
														<c:set var="brColor" value="red"></c:set>
													</c:when>
													<c:when test="${mathQuizQuestions[cnt].mark eq 1 and sAssignmentStatus.status ne 'pending'}">
														<c:set var="brColor" value="blue"></c:set>
													</c:when>
													<c:otherwise>
														<c:set var="brColor" value=""></c:set>
													</c:otherwise>													
												</c:choose>								
												<form:hidden path="assignment.assignmentId"/> 
												<form:hidden path="studentAssignmentId"/> 
												<form:hidden path="mathAssessMarks[${cnt}].mathQuizQuestions.mathQuiz.fraction"/> 
												<form:hidden path="mathAssessMarks[${cnt}].mathQuizQuestions.mathConversionTypes.conversionTypeId"/>
												<form:hidden path="student.studentId"/>  
												<form:hidden path="mathAssessMarks[${cnt}].studentMathAssessMarksId"/>  
												<form:hidden path="mathAssessMarks[${cnt}].mark"/>  
												<c:choose>
												<c:when test="${mathQuizQuestions[0].studentAssignmentStatus.assignment.recordTime eq 0}">
												<form:input path="mathAssessMarks[${cnt}].answer" name="mathAssessMarks[${cnt}].answer" title="${sAssignmentStatus.status=='submitted'?mathQuizQuestions[cnt].mathQuizQuestions.actualAnswer:''}" size="5" required="required" style="border-color : ${brColor }; color: ${brColor }; text-align : center" onBlur="autoSaveMathAnswer(${cnt})"/>
												</c:when>
												<c:otherwise>
												<form:input path="mathAssessMarks[${cnt}].answer" name="mathAssessMarks[${cnt}].answer" title="${sAssignmentStatus.status=='submitted'?mathQuizQuestions[cnt].mathQuizQuestions.actualAnswer:''}" size="5" required="required" style="border-color : ${brColor }; color: ${brColor }; text-align : center" />
												</c:otherwise>
												</c:choose>
												<form:hidden path="mathAssessMarks[${cnt}].actualAnswer" size="5" value="${question.actualAnswer}" required="required" />
												<c:set var="cnt" value="${cnt+1}"></c:set>	
											</c:otherwise>
										</c:choose>
										<c:set var="brColor" value=""></c:set>								
									</td>
									
								</c:forEach>
							 </tr>	
						 </c:when>
					 	 <c:otherwise>					
						 	<c:set var="oldValue" value="${que.mathQuizQuestions.mathQuiz.mathQuizId}"></c:set>
						 </c:otherwise> 
					</c:choose>
				</c:forEach>
				<c:choose>
					<c:when test="${sAssignmentStatus.status eq 'pending'}">
					<tr>
							    <td align="right"  colspan="${rows}"  style="padding-top: 3em">
								<div id="timerDiv" class="timer" align="right" style="display:none;">
									<span class="minute" id="minute">00</span>
								</div>
							</td></tr>
						<tr>
							<td colspan="${rows}" align="center" style="padding-top: 3em">
								<input type="button" class="button_green" value="Submit" style="border:none" onclick="submitMathAssessmentTest()">
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td  colspan="${rows}" align="left" style="padding-left:60%;"  >
								<ul>
									<li  style="color : blue; font: italic; font: bold; font: 200;">Text boxes in blue color represent the correct answers</li>
								</ul>
								<ul>
									<li style="color : red; font: italic; font: bold; font: 200;">Text boxes in red color represent the wrong answers</li>
								</ul>
							</td>
						</tr>
						<tr><td colspan="${rows}" align="center"><h2  id="percentageMessage">You have scored ${sAssignmentStatus.percentage}% </h2></td></tr>
						<tr><td colspan="${rows}" align="center"><h1  id="statusMessage">${status}</h1></td></tr>
					</c:otherwise> 
				</c:choose>
			</form:form> 
		</table>
	</div>
	<div id="loading-div-background" class="loading-div-background"
		style="display: none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div"
			style="color: #103c51; left: 50%; padding-left: 12px; font-size: 16px;">
			<br><br><br>
			<br>Loading....
		</div>
	</div>
	</body>
</html>