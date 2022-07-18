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
<title>Goal Reminder</title>
<script src="resources/javascript/common/jQuery.print.min.js"></script>
<style>
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
 font-size: 14px;
 margin-left: 10px;
 }

</style>
<script type="text/javascript">
$(document).ready(function(){
	 checkParentSignExists('Goal_Reminder');
});
</script>
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
             <input type="hidden" id="gradeId" value="${student.grade.gradeId}" />
            <input type="hidden" id="len" value="${fn:length(studStarReadingScores)}" />
		</div>
		</nav>

       
		<div class="content">
			<div class="container-fluid">
				<div class="card" id="printBoy">
					<div class="alert alert-info text-center">
						<h4 class="title">Goal Reminder</h4>

					</div>
					<table id="" width="100%"><tr><td class="col-md-1">&nbsp;</td>
					<td>
					<table width="100%">
					<tr><td style="font-weight:bold">Dear ${studName},</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td class="txtNor"><p>Below are you scores from last year and attainable goals for improvement. Scores range from 1 to 4 with being the highest possible score.</p></td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">SBAC Reading</td></tr>
					<tr><td>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Year, you earned a ${studGoalReadingScores[0].caasppScore} - 
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
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A reasonable goal for this year's test is a :
					<c:choose>
							<c:when test="${studGoalReadingScores[0].caasppScore gt 0}">
								
								
									<c:set var="goal" value="1" /> <c:set
												var="goalforyear" value="${studGoalReadingScores[0].caasppScore+goal}" />
												 <c:choose>
												<c:when test="${goalforyear>=4}">
													<c:out value="4" />.
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
					
					
					</td></tr>
					<tr><td>&nbsp;</td></tr>
					</table>
										
					<table width="100%">
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">SBAC Math</td></tr>
					<tr><td>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Year, you earned a ${studGoalMathScores[0].caasppScore} - 
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
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A reasonable goal for this year's test is a :
					<c:choose>
							<c:when test="${studGoalMathScores[0].caasppScore gt 0}">
													
									<c:set var="goal" value="1" /> <c:set
												var="goalforyear" value="${studGoalMathScores[0].caasppScore+goal}" />
												 <c:choose>
												<c:when test="${goalforyear>=4}">
													<c:out value="4" />.
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
					
					</td></tr>
					<tr><td>&nbsp;</td></tr>
					</table>
					<table>
					<tr><td>&nbsp;</td></tr>
					<tr><td class="txtNor" colspan="4">How do you plan on reaching your goals? Discuss this with your parent and write a few ideas down(Many ideas are listed on the attached flyer).</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td class="txtNor" colspan="4">Idea 1: _____________________________________________________________________________</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td class="txtNor" colspan="4">Idea 2: _____________________________________________________________________________</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td class="txtNor" colspan="4">Idea 3: _____________________________________________________________________________</td></tr>
					<tr height="10px"><td>&nbsp;</td></tr>
					<tr><td class="txtNor">Please return this with a parent signature. The first class to get 100% of goal sheets returned will earn a pizza party!.</td></tr>
					<tr><td height="30px"></td></tr>
					</table>
					<table>
					<tr><td width="30%">&nbsp;</td><td width="25%" style="vertical-align: bottom;text-transform: uppercase;font-weight: bold;">${parentName}</td><td width="25%">
<%-- 					<div id="signDiv" ${userTypeId == 4 ? 'onclick=openSignaturePad("Goal_Reminder")' : ''}></div> --%>
					</td></tr>
					<tr><td width="30%">&nbsp;</td><td width="25%">_______________________</td><td width="25%">
<%-- 					${userTypeId == 4 ? 'onclick=openSignaturePad("Goal_Reminder")' : ''} --%>
					__________________________</td></tr>
					<tr><td width="30%">&nbsp;</td><td width="25%">Parent Name</td><td width="25%">Parent Signature</td></tr>
					<tr><td style="height:30px">&nbsp;</td></tr>
					</table>
					</td></tr>
					</table>
					
					</div>
					<table><tr><td width="20%">
				      <span class="button_white" onClick='exportToPDF()' style="font-size:24px;color:red;"><i class="fa fa-file-pdf-o"></i></span>
<!-- 				    <span  class="button_white" style="font-size:24px;color:black;" onClick="javascript:printDiv('printBoy')"><i class="fa fa-print"></i></span> -->
				    </td>
				    <td width="20%"></td>
				    <td width="20%" align="center">
				      <span style="font-size:50px;color:red;align:center;" onClick="gotoIdeasList()">&#10150;</span>Attached Flyer</td>
				     <td width="20%">&nbsp;</td></tr>
				    </table>
			</div>
		</div>
	</div>

</body>
</html>