<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
       <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
       <link href="resources/css/chatstyle.css" rel="stylesheet" type="text/css" />
       <script type="text/javascript" src="resources/javascript/Student/web_chat.js"></script>
       <script type="text/javascript">
	       $(document).ready(function() {            	  	
	  	  	   loginChat();
	           getOnlineUsers();
	           getMyMessages();
	           setInterval(function(){getMyMessages()},1000);
	           setInterval(function(){getOnlineUsers()},20000);
	  	  });
       </script>
</head>
<body> 
<div id="wrapper">
    <div id="chatMenu">
        <p class="welcome">Welcome, <b>${user.firstName}&nbsp;${user.lastName}</b></p>
        <div style="clear:both"></div>
    </div>
    <div id="onlineusers"></div>
    <div id="chatbox"></div>     
        <textarea  name="usermsg" id="usermsg" rows="2" cols="60" ></textarea>
        <input name="submitmsg" type="submit"  id="submitmsg" class="button_violet" value="Send" onclick="sendMessage();return false;" style="margin:auto;text-shadow:0 1px 2px rgb(23, 47, 59);" />
</div>
</html>