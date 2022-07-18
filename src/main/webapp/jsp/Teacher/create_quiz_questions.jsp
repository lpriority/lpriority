<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz Questions</title>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script src="resources/javascript/TeacherJs/math_assessment.js"></script>
<style>
.button-cls{
	background:linear-gradient(to bottom,rgb(244, 244, 244) 1%, rgb(255, 255, 255) 48%, rgb(204, 209, 212) 97%, rgb(173, 173, 173) 100%);
	color: black;
	padding:12px;
	font-weight:bold;
	font-size:16px;
	border: 1px solid #b9c1c5;
	text-shadow: 0 1px 2px rgb(195, 201, 203);
}
.blank-button-cls{
	background: linear-gradient(to bottom, #03A9F4 5%, #005277 100%);
	color: white;
	padding:12px;
	font-weight:bold;
	font-size:16px;
	border: 1px solid #b9c1c5;
	text-shadow:0 1px 2px rgb(28, 28, 28);
}
.title-txt{
    font-family: Roboto,-apple-system,BlinkMacSystemFont,"Helvetica Neue","Segoe UI","Oxygen","Ubuntu","Cantarell","Open Sans",sans-serif;
    font-weight: 500;
    font-size: 15px;
    text-shadow: 0 1px 1px #a6afaf;
    color:#056cbd; 
}
.box {
	position:absolute;
	width:80px;
	height:30px;
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
</style>
  <script>
	  $(document).ready(function(){
		  var mode = $('#mode').val(); 
		  if(mode == "edit")
		 	 makeQuestionContent();
	  });
  </script>
</head>
<body>
<input type="hidden" id="quizId" name="quizId" value="${quizId}" />
<input type="hidden" id="mode" name="mode" value="${mode}" />
<table width="100%">
	<tr><td height="10" colspan="2"></td></tr>
	<tr>
		<td class="tabtxt" width="30%" style="text-align:center;">Enter Fraction : </td>
		<td><input type="text" id="fractionId" name="fractionId" onblur="makeQuestionContent()" ${mode == 'edit' ? 'readonly' : ''}></td>
	</tr>
	<tr><td colspan="2" align="left"></td></tr>
	<tr><td colspan="2" align="center">
		<div id="QuestionContentDiv" style="margin-top:0.5em;"></div>
	</td></tr>
	<tr><td colspan="2" align="left"></td></tr>
	<tr><td colspan="2" align="center">
		<div id="submitDiv" style="display:none;">
			<div class="button_green" align="right" onclick="saveQuizQuestion()">Create</div>&nbsp;&nbsp;&nbsp;&nbsp;
			<div class="button_green" align="right" onclick="clearFraction()">Clear</div>
		</div>
	</td></tr>
	<tr><td colspan="2" align="center"><div id="status" class="status-message"></div></td></tr>
</table>
</body>
</html>