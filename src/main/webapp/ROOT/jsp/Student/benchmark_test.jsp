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
<script type="text/javascript" src="resources/javascript/benchmark_recorder.js"></script>
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
	<form:form modelAttribute="fluencyAudioFileUpload" name="benchmarkUploadForm" id="benchmarkUploadForm">
		<form:hidden path="assignmentQuestionId" id="assignmentQuestionId"/>
		<form:hidden path="audioData" id="audioData"/>
		<form:hidden path="passageType" id="passageType"/>	
		<form:hidden path="studentAssignmentId" id="stdAssignmentId"/>	
	</form:form>
		<div id="benchmarkTestReady" style="text-align: center; display: block;padding-top:6em;">
			<div id="begin" name="begin" class="button_blue_round" align="right" onclick="beginBenchmarkTest()">Let's Begin</div>
		</div>
	<form:hidden path="studentAssignmentStatus.studentAssignmentId"	id="studentAssignmentId" />
	<form:hidden path="studentAssignmentStatus.operation" id="operation" value="submit" />
	<input type="hidden" id="testCount" name="testCount" value="${testCount}" />
	<div id="benchmarkTestDiv" align="center"  style="visibility: hidden;">
		<table style="border:2px solid rgba(0, 88, 127, 0.59); width :98%; min-height:450px;" align='center'>
			<c:set var="displayDirections" value="none">
			</c:set>
			<c:set var="displayPassages" value="none">
			</c:set>
			<tr>
				<td>
					<input type="hidden" id="tab" name="tab" value="${tab}" />
				</td>
			</tr>
			<c:forEach items="${testQuestions}" var="questionsList"	varStatus="questionCount">
				<c:if test="${questionCount.index == 0}">
					<c:set var="displayDirections" value="">
					</c:set>
				</c:if>
				<tr>
					<td>
						<form:hidden
							path="studentAssignmentStatus.assignmentQuestions[${questionCount.index}].assignmentQuestionsId"
							id="assignmentQuestionId${questionCount.index}" /></td>
				</tr>
				<tr>
					<td id="directions${questionCount.index}"
						style="display:  ${displayDirections};">
						
						<div>
						     <span style="color:#006592;font-weight:bold;font-style: italic;font-size:14px;">${studentAssignmentStatus.assignment.assignmentType.assignmentType}</span><br/><br/>
							 <span style="color:#006592;font-weight:bold;font-size:1.1em;">${studentAssignmentStatus.assignment.benchmarkCategories.benchmarkName}<c:if test="${studentAssignmentStatus.assignment.benchmarkCategories.isFluencyTest !=0}">.${questionCount.count}</span></c:if><br /><br />
						</div>
						<div>
							<input type="image" src="images/Student/audioOver.gif"
								onclick="playBench(this)"
								value="loadDirectUserFile.htm?usersFilePath=${questionsList.questions.BVoicedirectionspath}" 
								style="display:${questionsList.questions.BVoicedirectionspath != null? '': 'none'}">
								
						</div><br /><br />
						<div><I><c:out value="Directions :"></c:out></I>
							<font size='3'><c:out value="${questionsList.studentAssignmentStatus.assignment.benchmarkDirections.fluencyDirections}"></c:out></font>
						</div> <br /><br />
						<div align="center">
							<div class="button_green" align="center" onclick="record('${questionCount.index}', 'fluency')">Begin</div>
							<audio style="display: none" id="fluency${questionCount.index}"	src="" controls></audio>
						</div>
					</td>
				</tr>
				<tr>
					<td id="retellDiv${questionCount.index}" style="display: none;" align="right">
						<table width="100%">
							<tr><td>
								<div align="left" style="color:#006592;font-weight:bold;font-size:1.1em;">
								  ${studentAssignmentStatus.assignment.benchmarkCategories.benchmarkName}<c:if test="${studentAssignmentStatus.assignment.benchmarkCategories.isFluencyTest !=0}">.${questionCount.count}</c:if><br /><br />
								</div>
								<div align="left">
									<input type="image" src="images/Student/audioOver.gif"
										onclick="playBench(this)"
										value="loadDirectUserFile.htm?usersFilePath=${questionsList.questions.BRetelldirectionspath}"
										style="display:${questionsList.questions.BRetelldirectionspath != null? '': 'none'}">
								</div>
								</td></tr>
								<tr><td>
									<table width="100%" style="min-height:250px;">
										<tr><td colspan="2">
											<div align="center">
												<font size="4"><c:out value="${questionsList.studentAssignmentStatus.assignment.benchmarkDirections.retellDirections}"></c:out></font>
											</div>
										</td></tr>
										<tr>
											<td align="left" style="padding-left:5em;"></td>
											<td align="right">
												<div class="timer" align="right">
													<span class="minute" id="retellMinute${questionCount.index}">0:00</span>
												</div>
											</td>
										</tr>
									</table>
		  						</td></tr>
								<tr><td>
									<table width="100%">
										<tr><td>
											<div align="center" style="padding-bottom: 1.2em; display: block;">
												<div class="button_green" align="center" style="float:left;margin-left: 30%;" id="rRecordDiv${questionCount.index}" onclick="record('${questionCount.index}', 'retell')">Record</div>
												<div class="button_green" align="center" style="float: right;margin-right: 30%;" id="rStopDiv${questionCount.index}" onclick="stopCheckCall('${questionCount.index}', 'retell')">Stop</div>
					 						</div>
				 						</td></tr>
									</table>
								</td></tr>
								<tr><td>
									<audio style="display: none" id="retell${questionCount.index}" src="" controls></audio>
								</td></tr>
						</table>
					</td>
				</tr>
				<tr>
					<td id="passage${questionCount.index}" style="display: none;" align="right">
						<table width="100%">
							<tr><td colspan="2">
								<div align="left" style="color:#006592;font-weight:bold;margin-top:-1em;">
								   ${studentAssignmentStatus.assignment.benchmarkCategories.benchmarkName}<c:if test="${studentAssignmentStatus.assignment.benchmarkCategories.isFluencyTest !=0}">.${questionCount.count}</c:if><br/>
								</div>
							</td></tr>
							<tr><td colspan="2">
		 						<div align="center"> 
									 <font size='5'><textarea  id="questionId" cols="75" style="font-family:Times New Roman; overflow-y: auto; overflow-x: hidden;" rows="19" readonly ><c:out value="${questionsList.questions.question}"></c:out></textarea></font>
		 						</div>
	 						</td></tr>
							<tr><td align="left" style="padding-left:5em;"></td>
							    <td align="right">
								<div class="timer" align="right">
									<span class="minute" id="fluencyMinute${questionCount.index}">00</span>
								</div>
							</td></tr>
					 </table>
				  </td>
				</tr>
				<tr><td align="left"><canvas id='canvas' width='100' height='20'></canvas></td></tr>
				<c:set var="displayDirections" value="none">
				</c:set>
			</c:forEach>
			<tr><td align="center"><div id="statusMessage" class="status-message"></div>
					<div align="right" id="audioDiv" style="display:none;">
						<audio id="accId" src="" controls preload="metadata">
							<source src="" type="audio/wav"/>
						</audio>
					</div>
		   </td></tr>
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