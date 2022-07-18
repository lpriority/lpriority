<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<script src="resources/javascript/my_recorder/recorder.js"></script>
<script src="resources/javascript/my_recorder/volume-meter.js"></script>
<script type="text/javascript" src="resources/javascript/Student/accuracy_recorder.js"></script>
<script type="text/javascript" src="resources/javascript/Student/studentcurriculum.js"></script>
<link rel="stylesheet" href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/icons/ionicons/css/ionicons.min.css">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<title>Benchmark test</title>
<style type="text/css">

.timer {
	border: 1px #666666 solid;
	width: 190px;
	height: 50px;
	line-height: 50px;
	font-size: 36px;
	font-family: "Courier New", Courier, monospace;
	text-align: center;
	margin: 5px;
}
textarea {  
   font-family:"Times New Roman", Times, serif;
}
</style>
</head>
<body>
<form:form modelAttribute="accuracyAudioFileUpload" name="accuracyUploadForm" id="accuracyUploadForm">
		<form:hidden path="assignmentQuestionId" id="assignmentQuestionId"/>
		<form:hidden path="audioData" id="audioData"/>
		<form:hidden path="passageType" id="passageType"/>		
	</form:form>
	<div id="accuracyTestReady" style="text-align: center; display: block;padding-top:13em;">
		<div id="begin" name="begin" class="button_blue_round" align="right" onclick="beginAccuracyTest()">Let's Begin</div>
	</div>
	<form:hidden path="studentAssignmentStatus.studentAssignmentId"
		id="studentAssignmentId" />
	<form:hidden path="studentAssignmentStatus.operation" id="operation"
		value="submit" />
	<div id="accuracyTestDiv" align="center"  style="visibility: hidden;">
	<input type="hidden" id="tab" name="tab" value="${tab}" />
	<input type="hidden" id="audioData" name="audioData"/>
	<input type="hidden" id="testCount" name="testCount" value="${testCount}" />
		<table style="border: 2px solid #000000; width :95%; height: auto">
			<c:forEach items="${testQuestions}" var="questionsList"	varStatus="questionCount">
				<c:if test="${questionCount.index == 0}">
					<c:set var="displayDirections" value="">
					</c:set>
				</c:if>
				<form:hidden path="studentAssignmentStatus.assignmentQuestions[${questionCount.index}].assignmentQuestionsId" id="assignmentQuestionId${questionCount.index}" />
				<tr>
					<td id="accuracy${questionCount.index}" style="display: none" align="right">
					  <table width="100%">
					  <tr><td align="left">
					  		<span style="color:#006592;font-weight:bold;font-size:1.1em;"> Accuracy Passage${questionCount.count}<br><br></span>
					  </td></tr>
					  <tr><td>
							<div align="center" > 
								<font size='5'><textarea   id="questionId" style="font-family:Times New Roman sans-serif;text-align:justify;margin-top:-1em; overflow-y: auto; overflow-x: hidden;width: 1000px; height: 400px;" readonly ><c:out value="${questionsList.questions.question}"></c:out></textarea></font>
	 						</div>
  					  </td></tr>
  					  <tr><td>
  						<table width="30%" align="center">
  							<tr><td colspan="2" align="center" width="78%">
  							        <span title="record" id="accuracyRecording${questionCount.count}" class="ion-ios-mic" style="cursor: pointer;text-shadow: -3px 3px 10px rgba(91, 115, 113, 0.68);font-size: 80px;font-weight: bolder;color: #2abb07;"></span><br> 
  									<div class="button_green_round" align="right" onclick="record('${questionCount.index}', 'accuracy','${questionCount.count}')" style="padding:0.2px 10px;font-size: 14px;">Record</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  									<div class="button_pink_round" align="right" style="font-size: 14px;" onclick="stop('${questionCount.index}','accuracy', '${questionCount.count}')">Stop</div>
 								</td>
							</tr>
							<tr>
							   <td colspan="2" align="center">									
							   <input type="hidden" id="startAudio" value="false" />
							   <input type="hidden" id="stopAudio" value="false" />
							</td> 
							</tr>
							<tr><td>
							<div>
								<audio style="display: none" id="accuracy${questionCount.index}" src="" controls></audio>
							</div>
							</td></tr>
					 </table>
					</td></tr></table>
					</td></tr>					
					<tr id="submit${questionCount.index}" style="display: none" align="center"><td width="100%">
					  <c:choose>
						<c:when test="${questionCount.count == fn:length(testQuestions) }">
							<div align="center" >
								<div class="button_green" align="center" onclick="submitTest()">Submit</div>
							</div>
						</c:when>
						<c:otherwise>
						    <div align="center" >
						    	<div class="button_green" align="center" onclick="nextPassage(${questionCount.index})">Next</div>
							</div>
					  	</c:otherwise>
					</c:choose>
				 </td></tr>
			</c:forEach>
			<tr><td>
				<div align="right" id="audioDiv" style="display:none;">
					<audio id="accId" src="" controls preload="metadata"><source src="" type="audio/wav"/></audio>
				</div>
				<canvas id="canvas" width="100" height="20"></canvas>									
			</td></tr>
			<tr><td align="center"><div id="status-message"></div></td></tr>
		</table>
		<div id="loading-div-background1" class="loading-div-background" style="display:none;background:rgb(142, 228, 238);">
			<div class="loader"></div>
		    <div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
				<br><br><br><br><br><br><br>Please wait while the recorded voice is being uploaded to the server....
			</div>
		</div>
	</div>
</body>
</html>