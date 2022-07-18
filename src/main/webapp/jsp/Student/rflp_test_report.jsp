<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
 <meta charset="UTF-8">
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="resources/javascript/Student/rflp_test.js"></script>
<script src="resources/javascript/common/js_speech_synthesis.js"></script>
<link rel="stylesheet" href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/icons/ionicons/css/ionicons.min.css">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<meta name="msapplication-TileImage" content="/mstile-144x144.png">
<meta name="msapplication-TileColor" content="#333333">
<meta name="theme-color" content="#ffffff">
<script type="text/javascript">
$(document).ready(function () {
	 $("#loading-div-background1").css({ opacity: 0.8 });
});
function playRFLPAudio(id){
	var path = document.getElementById(id).value;
	var audio = new Audio(path);
	audio.play();
}

function calculateAverage(type){
	var totalLength = document.getElementById("totalLength").value;
	var sum = 0;
	var avg = 0;
	var count = 0;
	 for(var i=0;i<parseInt(totalLength);i++){
		 var val = $("#"+(type+i)+" option:selected").text();
		 if(val != 'Select'){
			 count = count+1;
			 sum = parseInt(val)+sum; 
		 }else{
			 count = 0;
			 break;
		 }
	 }
	 if(count == parseInt(totalLength)){
		 avg = sum/parseInt(totalLength)
		 document.getElementById(type+"Avg").value = avg.toFixed(1);
		 var oralAvg = parseFloat(document.getElementById("oralAvg").value);
		 var writtenAvg = parseFloat(document.getElementById("writtenAvg").value);
		 if(oralAvg && writtenAvg){
			var totalAvgScore = (writtenAvg + oralAvg)/2;
			document.getElementById("totalAvgScore").value = totalAvgScore.toFixed(1);
		 }
	 }else{
		 document.getElementById(type+"Avg").value = "";
		 document.getElementById("totalAvgScore").value = "";
	 }
}
</script>
</head>
<body>
 <form:form id="questionsForm" modelAttribute="rflpTest"	action="submitRflpTest.htm" method="POST"> 
   <form:hidden id="operation" path="operation" value="" />
   <form:hidden id="action" path="action" value="gradeRflp" />
   <input type="hidden" id="totalLength" name="totalLength" value="${fn:length(rflpPracticeLt)}" />
   <input type="hidden" id="rflpRecordsFilePath" name="rflpRecordsFilePath" value="${rflpRecordsFilePath}" />
   <input type="hidden" id="dateDue" name="dateDue" value="${dateDue}" />
   <input type="hidden" id="studentAssignmentId" name="studentAssignmentId" value="${studentAssignmentId}" />
   <table border=0 align="center" width="100%" >
   	   <tr><td width="75%">
	   <table border=0 align="left" width="100%" >
			<tr><td width="100%" align="left"> 
				<table border=0 align="center" width="100%" class="des">
		   		<tr><td>
					<div class="Divheads">
						<table width="100%" style="align: center;font-size:14px;">
							<tr>
								<td width="5%" style="padding-left:1em;">Word #</td>
								<td width="15%" style="padding-left:2em;">Error Word</td>
								<td width="20%"  style="padding-left:1em;">Student authored sentences</td>
								<td width="10%">Written Rubric score</td>
								<td width="10%">Student Recorded Sentence</td>
								<td width="10%" style="padding-left:2em;">Oral Rubric Score</td>
								</tr>
						</table>
					</div>
				</td></tr>
				<tr><td height="60">
					<div style="align: center" class="" >
					 <table width="100%"  style="font-size:13.5px; border-collapse: collapse;" border=1 >
						  <c:forEach items="${rflpPracticeLt}" var="rflpPractice" varStatus="status">
						     <c:if test="${not empty rflpPractice.errorWord}">
						       <form:hidden id="rflpPracticeId${status.index}" path="rflpPractice[${status.index}].rflpPracticeId" value="${rflpPractice.rflpPracticeId}" required="required" />
						       <form:hidden id="rflpTestId${status.index}" path="rflpTestId" value="${rflpPractice.rflpTest.rflpTestId}" required="required"/>
						       <form:hidden id="audioData${status.index}"  path="rflpPractice[${status.index}].audioData" required="required" />
							   <input type="hidden" id="audioPath${status.index}" name="audioPath${status.index}" value="loadDirectUserFile.htm?usersFilePath=${rflpRecordsFilePath}/${rflpPractice.rflpTest.rflpTestId}/${rflpPractice.rflpPracticeId}.wav" />
							   <tr>
							    <td width="8%" align="center" height="40">${rflpPractice.wordNum}</td>
								<td width="15%" align="center"><input type="button" height="15" onclick="spellIt('${rflpPractice.errorWord}')" value="${rflpPractice.errorWord}" style="border-radius: 0.5em;" class="subButtons subButtonsWhite medium"/></td>
								<td width="20%" align="center"><form:input id="studentSentence${status.index}" path="rflpPractice[${status.index}].studentSentence" value="${rflpPractice.studentSentence}" style="width:300px;font-size:12px;height:22px;" readonly="true" /></td>
								<td width="10%" align="center">
									<form:select id="written${status.index}" path="rflpPractice[${status.index}].writtenRubricScore" class="listmenu" style="width:120px;" onchange="calculateAverage('written')" disabled="true" required="required">
										<option value="select"  ${empty rflpPractice.writtenRubricScore ? 'selected="selected"' : ''}>Select</option> 
										 <c:forEach items="${rflpRubricLt}" var="rflpRubric"> 
		                                    <c:if test="${rflpRubric.rflpRubricValue eq rflpPractice.writtenRubricScore}"> 
		                                          <c:set var="selected" value="true"/>
		                                    </c:if>
			                                <option value="${rflpRubric.rflpRubricValue}" ${selected == 'true' ? 'selected="selected"' : ''}>
			                                    ${rflpRubric.rflpRubricValue}
			                                </option>
		                                <c:set var="selected" value="false" />
		                                </c:forEach>  
									</form:select>
								</td>
								<td width="10%" align="center"><img src="images/Student/audioOver.gif" onclick="playRFLPAudio('audioPath${status.index}')" /></td>
								<td width="10%" align="center">
									<form:select id="oral${status.index}" path="rflpPractice[${status.index}].oralRubricScore" class="listmenu" style="width:120px;" onchange="calculateAverage('oral')" disabled="true" required="required" >
										<option value="select" ${empty rflpPractice.oralRubricScore ? 'selected="selected"' : ''}>Select</option> 
										 <c:forEach items="${rflpRubricLt}" var="rflpRubric"> 
		                                    <c:if test="${rflpRubric.rflpRubricValue eq rflpPractice.oralRubricScore}"> 
		                                        <c:set var="selected" value="true"/>
		                                    </c:if>
		                                    <option value="${rflpRubric.rflpRubricValue}" ${selected == 'true' ? 'selected="selected"' : ''}>
		                                       <c:if test="${rflpRubric.rflpRubricValue eq 0}"> 
		                                       		No-Pass
		                                    	</c:if>
		                                    	<c:if test="${rflpRubric.rflpRubricValue > 0}"> 
		                                       		Pass
		                                    	</c:if>
		                                    </option>
		                                <c:set var="selected" value="false" />
		                                </c:forEach>  
									</form:select>
								</td></tr>
							  </c:if>
						   </c:forEach>
						    <tr>
						        <td width="8%" align="center" height="30" colspan="2" rowspan="2"></td>
								<td width="20%" align="center" height="30"><b>Written and Oral average sub scores</b></td>
								<td width="10%" align="center"><input type="text" id="writtenAvg" name="writtenAvg" readonly="readonly" value="${writtenAvg}" /></td>
								<td width="10%" align="center"></td>
								<td width="10%" align="center"><input type="text" id="oralAvg" name="oralAvg" readonly="readonly" value="${oralAvg} "/></td>
						    </tr>
						      <tr>
								<td width="20%" align="center" height="30" ><b>Total Average of all Scores </b></td>
								<td width="10%" align="center" colspan="3"><form:input id="totalAvgScore" path="totalAvgScore" value="${totalAvgScore}" readonly="true" />
								</td>
						    </tr>
						    <tr>
						        <td width="8%" align="center" height="30" colspan="2"><b>
						         <c:choose>
						         <c:when test="${gradingTypesId==1}">
						           <b>Teacher comment</b>
						          </c:when>
						          <c:when test="${gradingTypesId==2}">
						             <b>Student comment</b>
						          </c:when>
						          <c:otherwise>
						          <b>Peer comment</b>
						          </c:otherwise>
						        </c:choose>
						        </b></td>
								<td width="15%" align="center"  colspan="4"> <form:input id="teacherComment" path="teacherComment" value="${teacherComment}" style="width:700px;font-size:14px;height:22px;" readonly="true"/></td>
						    </tr>
						    <tr height="60"></tr>
					 </table>
					</div>
				</td></tr>
			</table>
		    </td></tr>
			<tr><td height="60"></td></tr>
		</table>
	</td><td valign="top" style="padding-top:5em;">
	  <table border=0 align="right" width="100%" class="des" style="padding-top:1em;" >
		 <tr><td>
		  <div class="Divheads" style="background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#B5E3F8), color-stop(1,#021E35) );padding:12px 12px 12px 12px;">
 			<table width="100%" style="align: center;font-size:14px;">
				<tr>
					<td width="28%" align="center">Rubric Score</td>
					<td align="center">Description</td>
				</tr>
			</table>
		  </div>
		 </td></tr>
		 <tr><td class="DivContents" style="padding:0px 3px 8px 3px;">
		  <table width="100%"  style="font-size:12px; border-collapse: collapse;color:#002064;font-weight:600;font-family:'Trebuchet MS', 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Tahoma, sans-serif;" border=1 >
			 <c:forEach items="${rflpRubricLt}" var="rflpRubric"> 
			  <tr>
				  <td width="28%" align="center" height="35px">${rflpRubric.rflpRubricValue}</td>
				  <td align="left" height="35px">${rflpRubric.rflpDesc}</td>
			   </tr>
             </c:forEach> 
		  </table>
		 </td></tr>
		 </div>
		 </table>
  </td></tr></table> 
 </form:form>
   <div id="loading-div-background1" class="loading-div-background" style="display:none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
			<br><br><br><br><br><br><br>Loading....
		</div>
   </div>
</body>
</html>