<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<link href="resources/javascript/jplayer/css/jplayer.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/earlyLiteracyTestsService.js"></script>
<script type="text/javascript" src="/dwr/interface/assignPhonicSkillTestService.js"></script>
<script type="text/javascript" src="/dwr/interface/gradePhonicSkillTestService.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/grade_phonic_skill_test.js"></script>
<title>Grade Phonic Skill</title>
</head>
<script>
$( document ).ready(function() {
	insertStudentsTestDetails();
}); 
</script>
<style>
.groupNameCls {
    font-size: 150%;
    font-weight: bold;
    padding-top:0.5em;
}
.smallContentBlueFont {
    font-size: 350%;
    color: #001cb5;
    text-align: center;
    justify-content: center;
    font-weight: bold;
}
.bigContentBlueFont {
    font-size: 250%;
    color: #001cb5;
    text-align: center;
    justify-content: center;
    font-weight: bold;
}
.smallContentRedFont {
    font-size: 350%;
    color: red;
    text-align: center;
    justify-content: center;
    font-weight: bold;
}
.bigContentRedFont {
    font-size: 250%;
    color: red;
    text-align: center;
    justify-content: center;
    font-weight: bold;
}


.ps-font-size {
    font-size: 200%;
    color: #000000;
    text-align: center;
    justify-content: center;
}
.explore-bars{
	font-size:4.5em;
	color:#73bad8;
	text-shadow: 0px 1px 0px #43a2ca,0px 2px 0px #43a2ca,0px 3px 0px #43a2ca,0px 4px 0px #43a2ca,0px 5px 0px #555,0px 6px 0px #43a2ca,0px 0px 0px #43a2ca,0px 8px 17px #3f768e;
}
.box {
	position:absolute;
	width:150px;
	height:60px;
	background:#ffffff;
	z-index:51;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border-radius: 2px;
	-moz-box-shadow:0px 0px 5px 1px #BCE2E7;
	-webkit-box-shadow:0px 0px 5px 1px #BCE2E7;
	box-shadow:0px 0px 5px 1px #BCE2E7;
	display:none;
	resize: none;
	border:solid 1px #C6C6C6;
	color:#ff0057;
}
	
.blue-content{
	color: #000000;
	font-size: 200%;
 	text-align: center;
 	justify-content: center;
}
.red-content{
 	color: #ec0000;
 	font-size: 200%;
 	text-align: center;
 	justify-content: center;
}
</style>
<body>
<input type="hidden" name="phonicSkillTestFilePath" id="phonicSkillTestFilePath" value="${phonicSkillTestFilePath}" />
<input type="hidden" name="studentAssignmentId" id="studentAssignmentId" value="${studentAssignmentId}" />
<input type="hidden" name="status" id="status" value="${status}" />
<input type="hidden" name="lastSavedSet" id="lastSavedSet" value="${lastSavedSet}" />
<input type="hidden" name="gradedStatus" id="gradedStatus" value="${gradedStatus}" />
<input type="hidden" name="studentRegId" id="studentRegId" value="${studentRegId}" />
<div id="dialogDiv">
  <table align="center" hieght="100%" width="100%" style="border:1px solid #9E9E9E;">
    <tr><td>
    	<div id="testContentDiv" style="min-height:80px;height:280px;text-shadow: 0 1px 1px rgb(156, 156, 156);font-family: 'Lato', Calibri, Arial, sans-serif;"></div>
    </td></tr>
      <tr><td>
    	<div id="commentsDiv"></div>
    </td></tr>
    <tr align="left"><td>
     <div id="audioDiv" style="visibility: hidden;"> 
	 </div>
    </td></tr>
    <tr><td>
    <tr><td height=10 colSpan=8></td></tr> 
     <table align="center" hieght="60%" width="60%" border=0>
      <tr><td>
   		 	<div id="marksAndSubmitDiv"></div>
     </td></tr>
      </table>
    </td></tr>
     <tr><td>
     	<div id="controllerDiv"></div>
     </td></tr>
     <tr><td>
	     <table align="center" hieght="100%" width="100%" border=0>
		     <tr><td align="center">
		     	<div id='resultDiv' class="status-message"  style="font-size:18px;"></div>
		     </td></tr>
	      </table>
    </td></tr>
   </table>
</div>
</body>
</html>