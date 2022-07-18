<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/common/jQuery.print.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript" src="resources/javascript/VoiceRecorder/recorder.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script> 
<script type="text/javascript" src="/dwr/interface/gradeAssessmentsService.js"></script>
<script type="text/javascript" src="/dwr/interface/earlyLiteracyTestsService.js"></script>
<script type="text/javascript" src="/dwr/interface/earlyLiteracyService.js"></script>
<script type="text/javascript" src="resources/javascript/Student/early_literacy_tests.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/early_literacy.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<title>Early Literacy Test</title>
<style>
.wordsClass{
	height:40px; 
	width:115px; 
	font-size: 20px;
	font-family: Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;
	font-weight: bold;
}
.letterClass{
	height:50px; 
	width:100px; 
	font-size: 20px;
	font-family: Segoe UI, Frutiger, Frutiger Linotype, Dejavu Sans, Helvetica Neue, Arial, sans-serif;
	font-weight: bold;
}
.submitButton{
	color: white;
	height:50px; 
	width:120px; 
	font-size:16px;
	background-color: #2374B0;
	moz-border-radius: 15px;
	-webkit-border-radius: 15px;
}
.ui-datepicker{font-size:.8em;}
.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#03b7cf) );border:1px solid #36b3c4;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
.ui-widget-overlay{background:rgba(81, 139, 146, 0.66);}
</style>
</head>
<script>
	$( document ).ready(function() {
		$("#resultDiv").html("");
		 $("#dueDate").datepicker({
		  	 changeMonth: true,
		      changeYear: true,
		      showAnim : 'clip',
		      minDate : 0
		  });
	});
</script>
<body>
<input type="hidden" id="studentAssignmentId" name="studentAssignmentId" value="${studentAssignmentId}">
<input type="hidden" id="studentId" name="studentId" value="${studentId}">
<input type="hidden" id="studentName" name="studentName" value="${studentName}">
<input type="hidden" id="setType" name="setType" value="${setType}">
<input type="hidden" id="userType" name="userType" value="${userType}">
<input type="hidden" id="regId" name="regId" value="${regId}">
<input type="hidden" id="assignmentId" name="assignmentId" value="${assignmentId}">
<input type="hidden" id="page" name="page" value="${page}">
<input type="hidden" id="dialogDivId" name="dialogDivId" value="${dialogDivId}">
<input type="hidden" id="totalSetsLength" name="totalSetsLength" value="${fn:length(setNameArray)}">
<input type="hidden" id="setNameArray" name="setNameArray" value="${setNameArray}">
<input type="hidden" id="earlyLiteracyFilePath" name="earlyLiteracyFilePath" value="${earlyLiteracyFilePath}">
<input type="hidden" name="hwAssignmentId" id="hwAssignmentId" value="${hwAssignmentId}"/>
<c:set var="totalContent" value="0" scope="page"/>
 	<div id="testDiv">
		 <table align="center"  width="700" style="height: 100; overflow : scroll;"  cellpadding='3' cellspacing='3'>
			<!--  <tr>
		           <td colspan="8" height="7"></td>
		     </tr> -->
		   
				<c:set var="flag" value="true" />
				<c:set var="count" value="0" scope="page"/>
				<c:forEach begin="0" end="${fn:length(setNameArray) - 1}" varStatus="loop">
						 <c:set var="contentSetArray" value="${fn:split(setsArray[loop.index], ' ')}" />
						 <input type="hidden" id="set${loop.count}" name="set${loop.count}" value="${setNameArray[loop.index]}">
						 <input type="hidden" id="setId${loop.count}" name="setIds" value="${setIdArray[loop.index]}">
						 
						 <input type="hidden" id="setLength${loop.count}" name="setLength${loop.count}" value="${fn:length(fn:split(setsArray[loop.index],' '))}">
				
					 <c:forEach var="i" begin="0" end="${fn:length(fn:split(setsArray[loop.index],' '))-1}" varStatus="status">
						  <c:set var="count" value="${count+1}"  scope="page"/>
						  <c:set var="totalContent" value="${totalContent+1}" scope="page"/>
						  <c:if test="${flag eq true}">
								<c:set var="flag" value="false" />
								<tr>
						  </c:if>
						  <td style="text-align: center; "width="30%">
						 	 <c:choose>
								 <c:when test="${fn:length(gradedMarksArray) > 0}">
								 	<input type="hidden" name="setStatus${totalContent}" id="setStatus${totalContent}"  value="${gradedMarksArray[totalContent-1]}">
								 	<input type="hidden" name="setContent${status.index}" id="setContent${status.index}"  value="${contentSetArray[status.index]}">
								 	<input type="hidden" name="setName${loop.index}" id="setName${loop.index}"  value="${setNameArray[loop.index]}">
								    <c:if test="${gradedMarksArray[totalContent-1] eq 1}">
								     	<input type="button" name="button${totalContent}" id="button${totalContent}" style="color: #001323;background : linear-gradient(to bottom,rgb(244, 244, 244) 1%, rgb(255, 255, 255) 48%, rgb(217, 222, 224) 97%, rgb(222, 217, 217) 100%);" 
								     	class="${divId == 'gradeWordSet' ? 'wordsClass' : 'letterClass'}"  value="${contentSetArray[status.index]}"  
								     	onclick="playELTAudio('${status.index}','${loop.index}','${totalContent}','${assignmentId}')" />
									</c:if>
									 <c:if test="${gradedMarksArray[totalContent-1] eq 0}">
										<input type="button" name="button${totalContent}" id="button${totalContent}" style="color: white;background : linear-gradient(to bottom, #03A9F4 5%, #005277 100%);" 
										class="${divId == 'gradeWordSet' ? 'wordsClass' : 'letterClass'}"  value="${contentSetArray[status.index]}" 
										onclick="playELTAudio('${status.index}','${loop.index}','${totalContent}','${assignmentId}')" />
									</c:if>
									 <c:if test="${gradedMarksArray[totalContent-1] eq -1}">
										<input type="button" name="button${totalContent}" id="button${totalContent}" style="background :linear-gradient(#9cd3ec 5%, rgb(116, 176, 205) 100%);color:black;" 
										class="${divId == 'gradeWordSet' ? 'wordsClass' : 'letterClass'}"  value="${contentSetArray[status.index]}"  
										onclick="playELTAudio('${status.index}','${loop.index}','${totalContent}','${assignmentId}')" />
									</c:if>
							  	 </c:when>
							  	 <c:otherwise>
							  	 	<input type="hidden" name="setStatus${totalContent}" id="setStatus${totalContent}" value="-1">
							  	 	<input type="hidden" name="setContent${status.index}" id="setContent${status.index}"  value="${contentSetArray[status.index]}">
								 	<input type="hidden" name="setName${loop.index}" id="setName${loop.index}"  value="${setNameArray[loop.index]}">
							  	 	<input type="button" name="button${totalContent}" id="button${totalContent}" class="${divId == 'gradeWordSet' ? 'wordsClass' : 'letterClass'}" 
							  	 	style="background :linear-gradient(#9cd3ec 5%, rgb(116, 176, 205) 100%);color:black;" value="${contentSetArray[status.index]}"  
							  	 	onclick="playELTAudio('${status.index}','${loop.index}','${totalContent}','${assignmentId}')" />
							  	 </c:otherwise>
							 </c:choose>
						  </td>
							<c:choose>
								<c:when test="${count == '10'}">
									 <c:set var="count" value="0"/>
									 <c:set var="flag" value="true" />
									 </tr>
							  	 </c:when>
							 </c:choose>
					</c:forEach> 
				 </c:forEach>
			  	  <!--  <tr>
		               <td colspan="8" height="15"></td>
		           </tr> -->
		           </table>
		          <c:if test="${page eq 'EarlyLiteracyGrade'}">
			           <table align="center" width="60%"  cellpadding='3' cellspacing='3'>
							   <tr>
						            <td width="50%" align="center" style="text-align: center;font-family:'Big Caslon', 'Book Antiqua', 'Palatino Linotype', Georgia, serif;font-weight: 600;" align="left">
						                Total # of Words Identified : <input type="text" name="score"  id ="score" value="${currentScore ne 0 ? currentScore:'0'}" />
						             <input type="hidden" name="maxMarks"  id ="maxMarks" value="" /></td>
						             <td align="center" width="50%" style="text-align: center;font-family:'Big Caslon', 'Book Antiqua', 'Palatino Linotype', Georgia, serif;font-weight: 600;"><img src="images/Common/required.gif">Word work Due Date : &nbsp;&nbsp; 
			                         <input type="text" id="dueDate" name="dueDate" style="width: 120px;" readonly="readonly" value="${dueDate}"></td>
						        </tr>
						        <tr>
							        <td align="right"  width="50%">
					                   <input type="hidden" name="operation" value="gradeTest">
					                   <input type="hidden" name="flag" value="words" id="flag"/>
					                   <div class="button_green" name="submit" onclick="submitGradePercentage('${totalContent}')">Submit</div>
					               </td>
					               <td height="80" align="center"  width="50%"> 
			               				<div id="printImg" onclick='printDiv()' style="${gradedStatus ne 'graded' ? 'display:none' : 'display:'};cursor: pointer; cursor: hand;font-weight:bold;text-shadow:0 2px 2px rgb(0, 0, 0);"><span style='font-size:30px;color:#1A7BC9;' class='fi-print'></span>&nbsp;<span style='font-size:20px;color:#1A7BC9;'>Print</span></div>
			                       </td>
						        </tr>
						        <tr>
						           <td></td>
					               <td height="5"  align="left" valign="bottom"><div id="resultDiv"  align="left" class="status-message"/></td>
					               
					           </tr>
					      </table>
			      </c:if>
	</div>
	<div id="printdiv" style="display: none;">
</body>
</html>