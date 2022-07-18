<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>September Goal Sheet</title>
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<script src="resources/javascript/common/jQuery.print.min.js"></script>
 <!----Start Notifications -------->
 <link rel="stylesheet" type="text/css" href="resources/css/notifications/ns-default.css">
<!--   <link rel="stylesheet" href="resources/css/kendo/kendo.common-material.min.css" /> -->
 <link rel="stylesheet" type="text/css" href="resources/css/notifications/ns-style-attached.css">
 <script src="resources/javascript/notifications/classie.js"></script>
 <script src="resources/javascript/notifications/notificationFx.js"></script>
 <!----End Notifications -------->
 <script>
 $(document).ready(function(){
	// checkParentSignExists('BOY_Report');
 });
</script>
<style>
.ns-attached {
   left: 50%;
   min-width: 30%;
   max-width: 50%;
   min-height: 15%;
   max-height: 50%;
   top: 25%;
   bottom: 35%;
}
tbl{
border-style:solid;
border-width:1px;
border-collapse:collapse;
}
tbls{
border:none;
}
li {
   
    margin-left: 10px;
    list-style-type: dash
    
 }
 txtNor{
  font-size: 12px;
  margin-left: 10px;
 }
.goltbl { 
 	border-spacing: 0; 
 	border-collapse: collapse 
 } 
 /* Print Page */
    .page {
        width: 210mm;
        min-height: 297mm;
        padding: 20mm;
        margin: 10mm auto;
        border-radius: 5px;
        background: white;
        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
    }
       
    @page {
        size: A4;
        margin: 0;
    }
    @media print {
        html, body {
            width: 210mm;
            height: 297mm;        
        }
        .page {
            margin: 0;
            border: initial;
            border-radius: initial;
            width: initial;
            min-height: initial;
            box-shadow: initial;
            background: initial;
            page-break-after: always;
        }
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
				<a class="navbar-brand" href="#">Goal Setting Tools Reading</a>
			</div>
            <input type="hidden" id="studId" value="${student.studentId}" />
            <input type="hidden" id="trimesterId" value="${trimesterId}" />
            <input type="hidden" id="gradeId" value="${student.grade.gradeId}" />
            <input type="hidden" id="len" value="${fn:length(studStarReadingScores)}" />
            
		</div>
		</nav>
		<div class="content">
			<div class="container-fluid">
				<div class="card page" id="printBoy">
						
				<c:choose>
				<c:when test="${(fn:length(listReadingGoalStrategies) eq 3) and (fn:length(listMathGoalStrategies) eq 3)}">
					<div class="alert alert-info text-center">
						<h4 class="title">${studName} Goals for ${currentAcademicYr.academicYear }</h4>
					</div>
					<table id="" width="100%">
					<tr><td>
					<table width="100%">
					<tr><td style="font-weight:bold">Dear ${studName},</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td class="txtNor"><p align="justify">Thank you for completing the goal setting form. The data, goals and action plan below should be used to keep you on track and moving toward your long range goal.</p></td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">SBAC Reading</td></tr>
					<tr><td style="padding-left:1.5em">
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Year, you earned  
					<c:choose>
					<c:when test="${empty studGoalReadingScores[0].caasppScore}">  no score  </c:when>
					<c:otherwise>
						a ${studGoalReadingScores[0].caasppScore} -
					</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${studGoalReadingScores[0].caasppScore>3}">
							<c:set var="stat" value="Exceeds" />
						</c:when>
						<c:when test="${studGoalReadingScores[0].caasppScore==3}">
							<c:set var="stat" value="Met" />
						</c:when>
						<c:when test="${studGoalReadingScores[0].caasppScore>=1.5 && studGoalReadingScores[0].caasppScore<3}">
							<c:set var="stat" value="Almost Met" />
						</c:when>
						<c:when test="${studGoalReadingScores[0].caasppScore>=1 && studGoalReadingScores[0].caasppScore<1.5}">
							<c:set var="stat" value="Not Met" />
						</c:when>
					</c:choose>
					${stat} on the state SBAC Reading Test.<br>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your goal for this year is to earn a 
					<c:choose>
							<c:when test="${studGoalReadingScores[0].caasppScore gt 0}">
								
								
									<c:set var="goal" value="1" /> <c:set
												var="goalforyear" value="${studGoalReadingScores[0].caasppScore+goal}" />
												 <c:choose>
												<c:when test="${goalforyear>=4}">.
													<c:out value="4" />
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="number" maxFractionDigits="1"
														value="${goalforyear}" />.
												</c:otherwise>
											</c:choose>
										
								
							</c:when>
							<c:otherwise>
								<c:out value="3" />.
							</c:otherwise>
						</c:choose>
					<br>
					</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">STAR Reading Grade Equivalent</td></tr>
					<tr><td style="padding-left:1.5em">
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Earlier this year, you earned  
					<c:choose>
					<c:when test="${empty studStarReadingBoyScore.score}">  no score  </c:when>
					<c:otherwise>
					a ${studStarReadingBoyScore.score}
					</c:otherwise>
					</c:choose>
					 on the state STAR Reading Test.<br>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your goal for this year is to earn a 
					<c:set var="goal" value="1" />
					<c:choose>
					<c:when test="${studStarReadingBoyScore.score gt 0}">
 					<c:set var="goalforyear" value="${studStarReadingBoyScore.score+goal}" />
					</c:when>
				<c:otherwise>
					<c:set var="goalforyear" value="${student.grade.masterGrades.masterGradesId+goal}" /> 
					</c:otherwise>
					</c:choose>
					<fmt:formatNumber type="number" maxFractionDigits="1" value="${goalforyear}"/> grade equivalent.
					<br>					
					</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">You plan on reaching your Reading goals by:</td></tr>
					<tr><td>&nbsp;</td></tr>
					</table>
					
					<table border="1" width="100%" class="goltbl">
					<tr><td class="txtNor">				
					<c:forEach items="${listReadingGoalStrategies}" var="lrs"> 
					${lrs.goalStrategies.goalStrategiesDesc} <br>
					</c:forEach>
					</td></tr>						
					</table>
					
					<table width="100%">
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">SBAC Math</td></tr>
					<tr><td style="padding-left:1.5em">
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Year, you earned 
					<c:choose>
					<c:when test="${empty studGoalMathScores[0].caasppScore}">  no score  </c:when>
					<c:otherwise>
						a ${studGoalMathScores[0].caasppScore} -
					</c:otherwise>
					</c:choose>
					 
					<c:choose>
						<c:when test="${studGoalMathScores[0].caasppScore>3}">
							<c:set var="stat" value="Exceeds" />
						</c:when>
						<c:when test="${studGoalMathScores[0].caasppScore==3}">
							<c:set var="stat" value="Met" />
						</c:when>
						<c:when test="${studGoalMathScores[0].caasppScore>=1.5 && studGoalMathScores[0].caasppScore<3}">
							<c:set var="stat" value="Almost Met" />
						</c:when>
						<c:when test="${studGoalMathScores[0].caasppScore>=1 && studGoalMathScores[0].caasppScore<1.5}">
							<c:set var="stat" value="Not Met" />
						</c:when>
					</c:choose>
					${stat} on the state SBAC Math Test.<br>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your goal for this year is to earn a 
					<c:choose>
							<c:when test="${studGoalMathScores[0].caasppScore gt 0}">
													
									<c:set var="goal" value="1" /> <c:set
												var="goalforyear" value="${studGoalMathScores[0].caasppScore+goal}" />
												 <c:choose>
												<c:when test="${goalforyear>=4}">.
													<c:out value="4" />
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="number" maxFractionDigits="1"
														value="${goalforyear}" />.
												</c:otherwise>
											</c:choose>
										
								
							</c:when>
							<c:otherwise>
								<c:out value="3" />.
							</c:otherwise>
						</c:choose>
					<br>
					</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">STAR Math Grade Equivalent</td></tr>
					<tr><td style="padding-left:1.5em">
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Earlier this year, you earned  
					<c:choose>
					<c:when test="${empty studStarMathBoyScore.score}">  no score  </c:when>
					<c:otherwise>
					a ${studStarMathBoyScore.score}
					</c:otherwise>
					</c:choose>
					 on the state STAR Math Test.<br>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your goal for this year is to earn a 
					<c:set var="goal" value="1" />
					<c:choose>
					<c:when test="${studStarMathBoyScore.score gt 0}">
 					<c:set var="goalforyear" value="${studStarMathBoyScore.score+goal}" />
					</c:when>
				<c:otherwise>
				<c:set var="goalforyear" value="${student.grade.masterGrades.masterGradesId+goal}" /> 
					</c:otherwise>
					</c:choose>
					<fmt:formatNumber type="number" maxFractionDigits="1" value="${goalforyear}"/> grade equivalent.
					<br></td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">You plan on reaching your Math goals by:</td></tr>
					<tr><td>&nbsp;</td></tr>
					</table>
					<table border="1" width="100%" class="goltbl">
					<tr><td class="txtNor" width="100%">
					<c:forEach items="${listMathGoalStrategies}" var="lms">  
					${lms.goalStrategies.goalStrategiesDesc} <br>
					</c:forEach>
				    </td></tr>
					</table>
					<table>
						<tr><td>&nbsp;</td></tr>
						<tr><td class="txtNor" colspan="4">Please review this document daily and stay on track to meeting your goals. We know you can do it!!!.</td></tr>
						<tr><td>&nbsp;</td></tr>
						<tr><td class="txtNor" colspan="4">Your  principal, counselor and teacher are here to help.</td></tr>
						<c:if test="${userTypeId != 5 && userTypeId != 6 || tab=='showReports'}">
							<tr height="20px"><td width="80%" colspan="3" align="center"></td><td width="20%" align="center"><div id="signDiv" ${userTypeId == 4 ? 'onclick=openSignaturePad("BOY_Report")' : ''}></div></td></tr>
							<tr><td width="80%" colspan="3" align="center"></td><td width="20%" align="right" valign="top" ${userTypeId == 4 ? 'onclick=openSignaturePad("BOY_Report")' : ''}>___________________________</td></tr>
							<tr><td width="80%" colspan="3" align="center"></td><td width="20%"  align="center">Parent's Signature</td></tr>
						</c:if>
						<tr height="40px"><td>&nbsp;</td></tr>
					</table>
					</td></tr>
					</table>
					</c:when>
					<c:otherwise>
					<div>
					<table align="center" class="alert alert-info text-center" width="80%">
					<c:choose>
					<c:when test="${userTypeId != 5 && userTypeId != 6 || tab=='showReports'}">
					<tr><td>No Reports are available</td></tr>
					</c:when>
					<c:otherwise>
					<tr><td>
					You Should Select Three Strategies For Fluency/Comprehension Strategies</td></tr>
					</c:otherwise>
					</c:choose>
					</table>
					</div>
					</c:otherwise>
					</c:choose>
					
					</div>
				    <span class="button_white" onClick='exportToPDF()' style="font-size:24px;color:red;"><i class="fa fa-file-pdf-o"></i></span>
			</div>
		</div>
	</div>
</body>
</html>