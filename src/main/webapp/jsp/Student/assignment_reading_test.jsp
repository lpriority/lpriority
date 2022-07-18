<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Record Audio</title>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>

<script type="text/javascript" src="resources/javascript/Student/early_literacy_tests.js"></script>
<script src="resources/javascript/my_recorder/recorder.js"></script>
<link rel="stylesheet" href="resources/css/recorder/style.css"> 
<link rel="stylesheet" href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/icons/ionicons/css/ionicons.min.css">
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
<style>
.loading-div-background {
     display:none;
     position:fixed;
     top:0;
     left:0;
     background:#f3f2f2;
     width:100%;
     height:100%;
 }
  .loading-div{
      width: 300px;
      height: 200px;
      text-align:center;
      position:absolute;
      left: 50%;
      top: 50%;
      margin-left:-150px;
      margin-top: -100px;
 }
 
 .loading-wait{
	color: #0079F1;	
	font-weight:normal;
}
.contentFont {
    text-align: -moz-center;
    text-align: center;
    height: 145px;
    font-size: 900%;
    color: #FFF;
    text-align: center;
    margin-top: 20px;
    margin-left: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-family:"Open Sans", Arial, sans-serif;
    color:#0c1b6d;
    font-weight:bold;
}.successFont {
    font-size: 200%;
}
.recordingImg{
	height: 150px;
	width: 150px; 
	overflow: hidden;
	font-size:10em;
	/* color:#e41093; */
	text-shadow:0 1px 2px rgba(0, 0, 0, 0.55);
	cursor:pointer;
}
.ui-dialog-titlebar-close {
  visibility: hidden;
}
</style>
<script type="text/javascript">
	$(document).ready(function () {
		 $("#loading-div-background1").css({ opacity: 0.7 });
	});
</script>
</head>
<body>
	<c:forEach begin="0" end="${fn:length(setNameArray) - 1}" varStatus="loop">
		<input type="hidden" id="set${loop.count}" name="${setNameArray[loop.index]}" value="${setsArray[loop.index]}">
 	</c:forEach>
	<span id="flashcontent"></span>
	<input type="hidden" id="studentId" name="studentId" value="${studentAssignmentId}">
	<input type="hidden" id="setType" name="setType" value="${setType}">
	<input type="hidden" id="recordTime" name="recordTime" value="${recordTime}">
	<input type="hidden" id="dialogDivId" name="dialogDivId" value="${dialogDivId}">
	<input type="hidden" id="setNameArrayLength" name="setNameArrayLength" value='${fn:length(setNameArray)}'>
 	<table align="left" hieght="100%" width="100%" border=0>
        <tr><td height=10 colSpan=10><div class="details"></div></td></tr> 
	    <tr><td height=70 colSpan=30><div id="setNameDiv"></div></td></tr> 
      	<tr><td  align="center" >
      	 	<div id="getReady" style="text-align: center; vertical-align: middle;padding-top:8em;">
      	 		<div id="begin" name="begin" class="button_blue_round" align="right" onclick="recordVoiceData()">Let's Begin</div>
      	 	</div>
      	   	<div id="takeTest" style="display:none;">
	      	    <table width="100%" height="100%" cellpadding='3' cellspacing='3'>
		   	    	<tr align="center">
		   	    	 	<td><div id="contentDiv"  class="contentFont"></div></td>
		   	    	 </tr>
		   	    	 <tr><td height=50></td></tr> 
					 <tr align="center"  style="position:relative;">
					 	<td><div class="fa fa-microphone recordingImg" id="recordImg" name="recordImg" onclick="recordVoiceData()" style="color:#e41093;"></div></td>  
					</tr>  
					<tr><td height=5 colSpan=10></td></tr> 
	      	     </table>
      	   	 </div>
      	  </td></tr>
      	 <tr><td height=10 colSpan=3></td></tr> 
      	 <tr><td class="successFont"><div id="completed" align="center"></div></td></tr>
      </table>
      <form:form id="assignmentReadingTestForm"	modelAttribute="earlyLiteracyTestsForm" action="recordAssessment.htm" method="POST"> 
   			<form:hidden  path="audioData" id="audioData" required="required" />
   			<form:hidden  path="setType" id="setType" value="${setType}" required="required" />
   			<form:hidden  path="content" id="content" required="required" />
   			<form:hidden  path="setName" id="setName" required="required" />
   			<form:hidden  path="status" id="statusVal" required="required" />
   			<form:hidden  path="studentAssignmentId" id="studentAssignmentId" value="${studentAssignmentId}" required="required" />
   			<form:hidden  path="assignmentId" id="assignmentId" value="${assignmentId}" required="required" />
   			<form:hidden  path="lastValue" id="lastValue" required="required" /> 
   			<form:hidden path="eltTypeId" id="eltTypeId" value="${eltTypeId}"/>
   	</form:form>  
	<div id="loading-div-background1" class="loading-div-background" style="display:none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
			<br><br><br><br><br><br><br>Loading....
		</div>
	</div>
 </body>
</html>