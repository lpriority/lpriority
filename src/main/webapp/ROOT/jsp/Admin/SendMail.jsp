<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
    <style>
    .mySubmitButton {
	width: 130px;
	color:white;
	background-color: #0080FF;
	moz-border-radius: 15px;
	-webkit-border-radius: 15px;
	border: 5px solid #0080FF;
	padding: 5px;
	font-weight: 900;
	text-align: center;
	vertical-align: middle;
}
    </style>
		<script type="text/javascript" src="resources/javascript/TeacherScheduler.js"></script>
		<script type="text/javascript" src="/dwr/engine.js"></script>
		<script type="text/javascript" src="/dwr/util.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
		<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
		<script src="resources/javascript/common/jQuery.print.js"></script>
		<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
		
		<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
		<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
		<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
		<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
    </head>
    <title>Student Email</title>
    <body onload="emailBodyController('${studentEmailId}','${status}')">
       <div id="ComposeMail">
         <form action="sendGroupMail.htm" name="sendEmailForm" method="GET" enctype="multipart/form-data">
           <table width="100%" align="center" style="padding-left:3em;">
                <tr><td height=10 colSpan=30></td></tr> 
                <tr><td width="10%" class="label"><b>To : </b></td><td width="90%"><input type="text" maxlength="25" size="25" value='${studentEmailId}' name="studentEmailId" readonly>
                  <c:if test="${parentEmailId ne ''}"> 
                  	<input type="text" maxlength="25" size="25" value='${parentEmailId}' name="parentEmailId" readonly>
                  </c:if>
                	  <input type="hidden" maxlength="25" size="25" value='${parentEmailId2}' name="parentEmailId2">
                   <c:if test="${parentEmailId2 ne ''}"> 
                  	<input type="text" maxlength="25" size="25" value='${parentEmailId2}' name="parentEmailId2" readonly>
                  </c:if>
                  </td></tr>
                <tr> <td class="label"><b>Subject : </b></td><td> <textarea name="subject" id="subject" cols="70" rows="1"></textarea></td></tr>
                <tr> <td></td><td> <textarea name="body" id="body"  cols="70" rows="15"></textarea></td></tr>
                 <tr><td height=30 colSpan=30></td></tr> 
                <tr><td></td><td  align="center" style="padding-right:8em;"> <input type="submit" value="Send" class="button_green"></td></tr>
            </table>
        </form>
      </div> 
      <div id="Success" align="center" style="display:none;">
 		<font color="blue" size="5"><b>Mail Sent Successfully..</b> </font>
      </div>
    </body>
    <div id="fail" align="center" style="display:none;">
 		<font color="red" size="5"><b>Mail send failed...</b> </font>
      </div>
    </body>
</html>

