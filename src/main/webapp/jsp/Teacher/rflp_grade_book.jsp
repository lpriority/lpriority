<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<!-- This page is used for to show Word work test result in grade book functionality -->
 <meta charset="UTF-8">
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/Student/rflp_test.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<meta name="msapplication-TileImage" content="/mstile-144x144.png">
<meta name="msapplication-TileColor" content="#333333">
<meta name="theme-color" content="#ffffff">
<script type="text/javascript">

function playRFLPAudio(id){
	var path = document.getElementById(id).value;
	var audio = new Audio(path);
	audio.play();
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
						<table width="100%" style="align: center;font-size:13px;">
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
						       <form:hidden id="rflpPracticeId${status.index}" path="rflpPractice[${status.index}].rflpPracticeId" value="${rflpPractice.rflpPracticeId}" required="required" />
						       <form:hidden id="rflpTestId${status.index}" path="rflpTestId" value="${rflpPractice.rflpTest.rflpTestId}" required="required"/>
						       <form:hidden id="gradingTypesId" path="gradingTypesId" value="${gradingTypesId}" />
						       <form:hidden id="audioData${status.index}"  path="rflpPractice[${status.index}].audioData" required="required" />
							   <input type="hidden" id="audioPath${status.index}" name="audioPath${status.index}" value="loadDirectUserFile.htm?usersFilePath=${rflpRecordsFilePath}/${rflpPractice.rflpTest.rflpTestId}/${rflpPractice.rflpPracticeId}.wav" />
							   <tr>
							    <td width="8%" align="center" height="40">${rflpPractice.wordNum}</td>
								<td width="15%" align="center"><input type="button" height="15" value="${rflpPractice.errorWord}" class="subButtons subButtonsWhite medium" style="width:150px;border-radius:0.5em;"/></td>
								<td width="20%" align="center">
									<form:input id="studentSentence${status.index}" path="rflpPractice[${status.index}].studentSentence" value="${rflpPractice.studentSentence}" 
										style="width:300px;font-size:12px;height:22px;" disabled="true"/>
								</td>
								<td width="10%" align="center">
									<form:select id="written${status.index}" path="rflpPractice[${status.index}].writtenRubricScore" class="listmenu" style="width:120px;" disabled="true">
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
									<form:select id="oral${status.index}" path="rflpPractice[${status.index}].oralRubricScore" class="listmenu" style="width:120px;" disabled="true">
										<option value="select" ${empty rflpPractice.oralRubricScore ? 'selected="selected"' : ''}>Select</option> 
										 <c:forEach var="rubricValue" begin="0" end="1" step="1" varStatus="status">
										 	<c:if test="${rubricValue eq rflpPractice.oralRubricScore}"> 
		                                        <c:set var="selected" value="true"/>
		                                    </c:if>
		                                    <c:if test="${rflpPractice.oralRubricScore > 1 and rubricValue != 0}"> 
		                                        <c:set var="selected" value="true"/>
		                                    </c:if>
		                                    <option value="${rubricValue}" ${selected == 'true' ? 'selected="selected"' : ''}>
		                                    	<c:if test="${rubricValue eq 0}"> 
		                                       		No-Pass
		                                    	</c:if>
		                                    	<c:if test="${rubricValue > 0}"> 
		                                       		Pass
		                                    	</c:if>
		                                    </option>
		                                	<c:set var="selected" value="false" />
										 </c:forEach>										
									</form:select>
								</td></tr>
						   </c:forEach>
						    <tr>
						        <td width="8%" align="center" height="30" colspan="2" rowspan="2"></td>
								<td width="20%" align="center" height="30"><b>Written average sub scores</b></td>
								<td width="10%" align="center"><input type="text" id="writtenAvg" name="writtenAvg" readonly="readonly" value="${writtenAvg}" /></td>
								<td width="10%" align="center"></td>
						    </tr>						      
						    <tr>
								<td width="20%" align="center" height="30" ><b>Percentage </b></td>
								<td width="10%" align="center" colspan="3"><input type="text" id="percentage" name="percentage" value="${percentage}" readonly="readonly"/>
								</td>
						    </tr>
						    <tr>
						        <td width="8%" align="center" height="30" colspan="2">
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
						        </td>
								<td width="15%" align="center"  colspan="4"> 
									<form:input id="teacherComment" path="teacherComment" value="${teacherComment}" 
										style="width:700px;font-size:14px;height:22px;" readonly="true"/>
								</td>
						    </tr>
					 </table>
					</div>
				</td></tr>			 
			</table>
			</td></tr>
		</table>
	</td>
  </td></tr></table> 
 </form:form>
</body>
</html>