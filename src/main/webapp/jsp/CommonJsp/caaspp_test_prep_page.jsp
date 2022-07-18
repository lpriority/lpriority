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
				<a class="navbar-brand" href="#">Goal Setting Tools</a>
			</div>
            <input type="hidden" id="caasppScoresId" value="${caasppScoresId}" />
            <c:set var="rstatus" value="" />
            <c:if test="${userType eq 'teacher' || userType eq 'parent'}">
            <c:set var="rstatus" value="readonly" />
            </c:if>
		</div>
		</nav>


		<div class="content">
			<div class="container-fluid">
				<div class="card">
					<div class="alert alert-info text-center">
						<h4 class="title">CAASPP Test Prep Fluency and Comprehension</h4>

					</div>
					<table width="90%" class="goltbl">
					<tr><td align="right" colspan="2"><a href="#" onClick="printGoalReminder(${studentId})" class="text-primary">Print</a></td></tr>
					
					</table>
					<table border=1 width="80%" align="center" class="goltbl">
					<tr><td colspan="2" class="alert-info">
					<h5 class="text-center">CAASPP Fluency </h5></td></tr>
					<tr><td class="text-center">Previous Year Score</td>
					<td class="text-center">Goal For This Year</td></tr>
					<c:choose>
					<c:when test="${fn:length(studGoalReadingScores) gt 0}">
					<c:forEach items="${studGoalReadingScores}" var="sg"> 
								<tr style="height:35px">
									
									<td class="text-center">${sg.caasppScore}</td>
									<td class="text-center">
									<c:set var="goal" value="1" />
									<c:set var="goalforyear" value="${sg.caasppScore+goal}" />
									<c:choose>
 									<c:when test="${goalforyear>=4}">
 									<c:out value="4" />
 									</c:when>
 									<c:otherwise>
									<fmt:formatNumber type="number" maxFractionDigits="1" value="${goalforyear}"/>
									</c:otherwise>
									</c:choose>
									</td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           </td>
								</tr>
					</c:forEach>
					</c:when>
					<c:otherwise>
					 <tr style="height:35px">
									<td class="text-center">
									&nbsp;											
									</td>
									<td class="text-center">
									 <c:out value="3" />
									</td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           </td>
								</tr>
					</c:otherwise>
					</c:choose>
					</table>
					<br>
					<table border=1 width="80%" align="center" class="goltbl">
					<tr><td colspan="2" class="alert-info">
					<h5 class="text-center">CAASPP Comprehension</h5></td></tr>
					<tr><td class="text-center">Previous Year Score</td>
					<td class="text-center">Goal For This Year</td></tr>
					<c:choose>
					<c:when test="${fn:length(studGoalMathScores) gt 0}">
					<c:forEach items="${studGoalMathScores}" var="sg"> 
								<tr style="height:35px">
									
									<td class="text-center">${sg.caasppScore}</td>
									<td class="text-center">
									<c:set var="goal" value="1" />
									<c:set var="goalforyear" value="${sg.caasppScore+goal}" />
									<c:choose>
 									<c:when test="${goalforyear>=4}">
 									<c:out value="4" />
 									</c:when>
 									<c:otherwise>
									<fmt:formatNumber type="number" maxFractionDigits="1" value="${goalforyear}"/>
									</c:otherwise>
									</c:choose>
									</td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           </td>
								</tr>
					</c:forEach>
					</c:when>
					<c:otherwise>
					 <tr style="height:35px">
									<td class="text-center">
									&nbsp;											
									</td>
									<td class="text-center">
									 <c:out value="3" />
									</td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           </td>
								</tr>
					</c:otherwise>
					</c:choose>
					</table><br>
					<table border=1 width="80%" align="center" class="goltbl">
					<tr><td colspan="2" class="alert-info">
					<h5 class="text-center">How do you plan on reaching your goals?</h5></td></tr></table>
					<table border=0 width="80%" align="center" class="goltbl">
					<tr><td colspan="2">
					<h5 class="text-center">Use the spaces below to write down 3 ideas on how you will accomplish your CAASPP Goals.</h5></td></tr></table>
					<table border=0 width="26%" align="center" class="goltbl">
					<tr>
					
					<c:set var="n" value="0" />
					 <c:forEach var = "i" begin = "0" end = "2"> 
					 <c:set var="i" value="${i+1}" />
					
					<td>
          			<table border=1 class="goltbl">
          			<tr><td class="text-center alert-info">Idea${i}</td></tr>
 					<tr><td><textarea id="studOwnStrategyDesc${i}" name="studOwnStrategyDesc${i}" rows="8" cols="20" onblur="autoSaveStudOwnStrategies(${studentId},${gradeId},${i})" ${rstatus}>${listStudOwnStrategies[n].studentOwnStrategDesc}</textarea></td></tr>
					</table>
					</td>
					<td width="10%" style="padding-left:40px">&nbsp;</td>
					 <c:set var="n" value="${n+1}" />
					</c:forEach>
					</tr></table>
				
					<table><tr><td style="height:30px" class="goltbl">&nbsp;</td></tr></table>
				</div>
			</div>
		</div>
	</div>

</body>
</html>