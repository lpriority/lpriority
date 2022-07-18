<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
.goltbl { 
 	border-spacing: 0; 
 	border-collapse: collapse 
 } 
 
</style>
</head>
<body>
	<div class="main-panel">
		<nav class="navbar navbar-default navbar-fixed">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#navigation-example-2">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#" class="report-headers">GOAL SETTING TOOLS</a>
			</div>
            <input type="hidden" id="caasppScoresId" value="caasppScoresId" />
		</div>
		</nav>


		<div class="content">
			<div class="container-fluid">
				<div class="card">
					<div class="alert alert-info text-center">
						<h4 class="title">Strategies to achieve goals ${gtype}</h4>

					</div>
					<table border=1 width="80%" align="center" class="goltbl">
					 <c:set var="i" value="1" />
					 <c:forEach items="${listGoalStrategies}" var="ls">
         					<tr><td class="col-md-1"><c:out value = "${i}"/></td>
								<td class="listStrateg">
									${ls.goalStrategiesDesc}
								</td></tr>
						 <c:set var="i" value="${i+1}" />	 
     				</c:forEach>
<!-- 					<tr><td>&nbsp;</td></tr> -->
<!-- 					<tr><td></td></tr> -->
					
					</table>
					<br>
				
				</div>
			</div>
		</div>
	</div>

</body>
</html>