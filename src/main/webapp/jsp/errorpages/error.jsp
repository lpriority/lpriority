
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/resources/javascript/notify/notify.js"></script>

<title>Error Page</title>





</head>

<body>
<body>
	<Center><h1>Learning Priority Support Error Page</h1></Center>

	<!--  As we are using Thymeleaf, you might consider using
	      ${#httpServletRequest.requestURL}. But that returns the path
	      to this error page.  Hence we explicitly add the url to the
	      Model in some of the example code. -->
	
	<Center><p>Application has encountered an error. Please contact support on
		...<div th:utext="'Failed URL: ' +  ${url}" th:remove="tag">${url}</div></p>

	<p>Support may ask you to steps to reproduce.</p>
	
	<a href="gotoDashboard.htm">Go to Desktop</a> 
	
	</Center>
	
</body>
</body>
</html>