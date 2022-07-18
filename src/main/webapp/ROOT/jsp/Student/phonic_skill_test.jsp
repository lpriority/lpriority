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
<script src="resources/javascript/my_recorder/recorder.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/Student/phonic_skill_tests.js"></script>
<script type="text/javascript" src="/dwr/interface/assignPhonicSkillTestService.js"></script>
<script type="text/javascript" src="/dwr/interface/gradePhonicSkillTestService.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<style>
 .headerContentFont {
    text-align: -moz-center;
    text-align: center;
    height: 85px;
    font-size: 500%;
    color: #FFF;
    margin-top: 20px;
    margin-left: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.recording{
    font-size: 120%;
    color: #4a8900;
    font-family:courier;
}
.stopped{
    font-size: 120%;
    color: #c30042;
    font-family:courier;
}
.smallContentFont {
    font-size: 450%;
    color: #0c1b6d;
    text-align: center;
    justify-content: center;
}
.ps-font-size {
    font-size: 220%;
    color: #000000;
    text-align: center;
    justify-content: center;
}
.recordingImg{
height: 150px; width: 150px; overflow: hidden;
}
.ui-dialog-titlebar-close {
  visibility: hidden;
}
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
</style>
<title>Phonic Skills Test</title>
<script type="text/javascript">
$(document).ready(function () {
	 $("#loading-div-background1").css({ opacity: 0.8 });
});
</script>
</head>
<body onload="">
  <span id="flashcontent"></span>
  <input type="hidden" name="phonicSkillTestFilePath" id="phonicSkillTestFilePath" value="${phonicSkillTestFilePath}" />
  <input type="hidden" name="regId" id="regId" value="${regId}" />
  <table align="left" height="100%" width="100%" border=0>
      	<tr><td>
      	   	<div id="takeTest" style="display:none;">
	      	    <table width="100%" height="100%" cellpadding='1' cellspacing='1' style='border:2px solid #00545e;'>
		   	    	<tr align="center">
		   	    	 	<td height="100%"><div id="headerDiv" style="margin-top:-0.5em;"></div></td>
		   	    	 </tr>
					 <tr><td colSpan=30 align="center" class="contentFont" style="min-height:80px;height:280px;"><div id="testContentDiv" style="text-shadow: 0 1px 1px rgb(156, 156, 156);font-family: 'Lato', Calibri, Arial, sans-serif;" ></div></td></tr> 
	      	     </table>
      	   	 </div>
      	  </td>
      	  <td>
      	    <table width="100%" height="100%" cellpadding='3' cellspacing='3'>
      	        <tr><td height=180 colSpan=70></td></tr> 
			   	 <tr align="center"><td>
		      	  	<div id="getReady" style="text-align: center; ">
		      	  		<div id="beginTest" name="beginTest" class="button_blue_round" align="right" onclick="beginPhonicTest()">Let's Begin</div>
		      	 	</div>
	      	 	</td></tr>
	      	</table>
      	  </td>
      	  <th><div id="completed" align="center"></div></th>
    	</tr>
    	<tr><td><div id="submitDiv" align="center" style=""></div> </td></tr>
    	<tr><td  class="blink-text"><font color='red' face='courier' ><b><div id="status" align="center" style="display:visible;"></div></b></font></td></tr>
      </table>
      <form:form id="phonicSkillTestForm"	modelAttribute="earlyLiteracyTestsForm" action="recordPhonicTest.htm" method="POST"> 
   			<form:hidden  path="audioData" id="audioData" required="required" />
   			<form:hidden  path="title" id="title" required="required" />
   			<form:hidden  path="status" id="statusVal" required="required" /> 
   			<form:hidden  path="groupId" id="groupId" required="required" />
   			<form:hidden  path="studentAssignmentId" id="studentAssignmentId" value="${studentAssignmentId}" required="required" />
   			<form:hidden  path="assignmentId" id="assignmentId" value="${assignmentId}" required="required" />
   			<form:hidden  path="lastValue" id="lastValue" required="required" /> 
   	</form:form>      
	<div id="loading-div-background1" class="loading-div-background" style="display:none;">
		<div class="loader"></div>
		<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
			<br><br><br><br><br><br><br>Loading....
		</div>
	</div>
</body>
</html>


