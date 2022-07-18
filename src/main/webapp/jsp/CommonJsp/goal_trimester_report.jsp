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
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/iol_report.css" rel="stylesheet" type="text/css" />
 <script src="resources/javascript/canvasjs/html2canvas.js"></script> 
<!--  <link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />  -->
<link href="resources/css/common/accordin.css" rel="stylesheet" />
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<script src="resources/javascript/common/jQuery.print.min.js"></script>
<script src="resources/javascript/common/goal_setting_tool.js"></script>
<script src="resources/javascript/jquery/jquery.PrintArea.js"></script> 
<script src="resources/javascript/jquery/jqueryFancy.js"></script>

<style>

li {
   
    margin-left: 10px;
    list-style-type: dash
    
 }
 txtNor{
 font-size: 14px;
 margin-left: 10px;
 }

body{
 -webkit-print-color-adjust:exact;
}
</style>

    
<script>
 $(document).ready(function() {
	 if(document.getElementById('chkbox1') != undefined){
		 document.getElementById('chkbox1').checked = true;
		 showGraph(1);
	 } 	
	 if(document.getElementById('chkbox2') != undefined){		
		 document.getElementById('chkbox2').checked = true;
		 showGraph(2);
	 } 	
 	//checkParentSignExists('Trimester_Report');	 
 });

</script>
<style>
body{
 -webkit-print-color-adjust:exact;
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
    .bor{
    border-radius: initial;
    border-right: 0px;
    border-left:0px;
    white-space:nowrap;
    border-bottom:0px;
    border-top:0px;
    }
    .borTr{
      border-right: 1px;
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
             <input type="hidden" id="gradeId" value="${student.grade.gradeId}" />
             <input type="hidden" id="trimesterId" value="${trimesterId}" />
             
                          
             <c:set var="index" value="1" /> 
             <c:set var="m" value="1" /> 
             
             <c:forEach items="${trimesterList}" var="tl">
								
									<c:set var="st" value="0" /> <c:forEach
										items="${lstStarReadScors}" var="sk">
										<c:choose>
											<c:when test="${sk.trimester.orderId eq tl.orderId}">
												<fmt:formatNumber var="boy" type="number"
													maxFractionDigits="2" value="${sk.score}" />
												<input type="hidden" id="rscore${index}" name="rscore"
													value="${boy}" />
												<c:set var="st" value="1" />
											</c:when>
										</c:choose>
									</c:forEach> <c:if test="${st eq 0}">
										<input type="hidden" name="rscore" id="rscore${index}" value="0" />
									</c:if>
									<c:set var="index" value="${index+1}" />
			</c:forEach>
             <c:forEach items="${trimesterList}" var="tl">
								
									<c:set var="st1" value="0" /> <c:forEach
										items="${lstStarMathScors}" var="sk">
										<c:choose>
											<c:when test="${sk.trimester.orderId eq tl.orderId}">
												<fmt:formatNumber var="boy" type="number"
													maxFractionDigits="2" value="${sk.score}" />
												<input type="hidden" id="mscore${index1}" name="mscore"
													value="${boy}" />
												<c:set var="st1" value="1" />
											</c:when>
										</c:choose>
									</c:forEach> <c:if test="${st1 eq 0}">
										<input type="hidden" name="mscore" id="mscore${index1}" value="0" />
									</c:if>
									<c:set var="index" value="${index1+1}" />
			</c:forEach>
          
            <input type="hidden" id="len1" value="${fn:length(lstStarReadScors)}" />
            <input type="hidden" id="len2" value="${fn:length(lstStarMathScors)}" />
           
            
            
		</div>
		</nav>

       
		<div class="content">
			<div class="container-fluid">
				<div class="card page" id="printBoy">
				<c:choose> 
				<c:when test="${(fn:length(listReadingGoalStrategies) eq 3) and (fn:length(listMathGoalStrategies) eq 3)}">
					<div class="alert alert-info text-center">
						<h4 class="title">Trimester${trimesterId} Goal Sheet</h4>

					</div>
					<table id="" width="100%">
					<tr><td>
					<div id="dd">
					<table width="100%">
					<tr><td style="font-weight:bold">Dear ${studName},</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td class="txtNor"><p align="justify">We are excited about the previous goals that you set. Remember, students that set 
					goals consistently outperform students that do not set goals. Part of what makes goal setting effective is monitoring progress toward goals. 
					Frequently check your goals and the strategies that YOU determined in order to reach your goals. Share this information with your parents and seek support from you teacher to ensure success. </p></td></tr>
					<tr><td>&nbsp;</td></tr>
					</table>
					
					<table border="1" width="100%" class="goltbl">
					<tr><td colspan="4" class="text-center alert-info">STAR READING</td></tr>
					<tr><td width="17%">Beginning of Year Score</td>
					<td width="17%">Year Long Goal</td>
					<td width="17%">
					<table width="17%" border=1  class="goltbl bor" height="100"><tr class="borTr"><td colspan="3">Trimester Scores</td></tr><tr><td class="text-center">Tri1</td><td class="text-center">Tri2</td ><td class="text-center">Eoy</td></tr></table></td>
					<td width="49%" align="center">READING STRATEGIES I COMMIT TO:</td>
					</tr>
					<tr>
					<td>${studStarReadingBoyScore.score}</td>
					<td><c:set var="goal" value="1" />
					<c:choose>   
					<c:when test="${studStarReadingBoyScore.score gt 0}">
					<c:set var="goalforyear" value="${studStarReadingBoyScore.score+goal}" />
					</c:when>
					<c:otherwise>
					<c:set var="goalforyear" value="${student.grade.masterGrades.masterGradesId+goal}" /> 
					</c:otherwise>
					</c:choose>
						<fmt:formatNumber var="gol" type="number" maxFractionDigits="2" value="${goalforyear}"/>
						<c:out value="${gol}" />
						<input type="hidden" id="rgolSc1" value="${gol}" /> 
						</td>
					<%-- <td>
 						<c:forEach items="${studStarReadingTrimesterScore}" var="rc" varStatus="count"> 
							<c:out value="${rc.score }" />
 							<c:if test="${ count.index < fn:length(studStarReadingTrimesterScore) -1 }">, </c:if> 
 						</c:forEach> 
					</td> --%>
					<td><table border=1 class="goltbl bor" width="100%" style="border-collapse: collapse">
								
 						<tr height="120" style="border-collapse: collapse">
 						<c:forEach items="${trimesterList}" var="tl">
 						
 						<c:if test="${tl.orderId ne 1}">
 						<c:set var="set" value="0" />
 						<c:forEach items="${studStarReadingTrimesterScore}" var="sk">
							<c:choose>
							<c:when test="${sk.trimester.orderId eq tl.orderId}">
							<td width="33%" align="center" class="borTr"><c:out value="${sk.score}" />
							<c:set var="set" value="1" />
							</td>
							</c:when>
							</c:choose>
						</c:forEach> 
						<c:if test="${set eq 0 }">
							<td width="33%" align="center">-</td>
						</c:if>
						</c:if>
						
						</c:forEach>
						</tr>
					</table></td>
					<td height="100">
					<!-- <p align="justify"> -->
					<ol>
					<c:forEach items="${listReadingGoalStrategies}" var="lrs">  
					<li>${lrs.goalStrategies.goalStrategiesDesc}</li>
					</c:forEach>
					</ol>
					<!-- </p> -->
					</td>
					</tr>
					</table>
					<table><tr height="10px"><td>&nbsp;</td></tr>
					</table>
					</div>
					<div id="dd2">
					<table>
					<tr><td><input type="checkbox" name="chkbox1" id="chkbox1" onClick="showGraph(1)" />Star Reading Report</td></tr>
					</table>
					<table border=0 width="80%" align="center">
					<tr id="chartContainer1">
					</tr>
					<tr><td>&nbsp;</td></tr>
					</table>
					</div>
					<div id="dd3">
					<table border="1" class="goltbl">
					<tr><td colspan="4" class="text-center alert-info">STAR MATH</td></tr>
					<tr><td width="17%">Beginning of Year Score</td>
					<td width="17%">Year Long Goal</td>
					<td width="17%">
					<table width="17%" border=1  class="goltbl bor" height="100"><tr class="borTr"><td colspan="3">Trimester Scores</td></tr><tr><td class="text-center">Tri1</td><td class="text-center">Tri2</td ><td class="text-center">Eoy</td></tr></table></td>
					<td width="49%" align="center">MATH STRATEGIES I COMMIT TO:</td>
					</tr>
					<tr>
					<td>${studStarMathBoyScore.score}</td>
					<td><c:set var="goal" value="1" />
						<c:choose>   
					<c:when test="${studStarMathBoyScore.score gt 0}">
					<c:set var="goalforyear" value="${studStarMathBoyScore.score+goal}" />
					</c:when>
					<c:otherwise>
					<c:set var="goalforyear" value="${student.grade.masterGrades.masterGradesId+goal}" /> 
					</c:otherwise>

					</c:choose>
						<fmt:formatNumber var="mgol" type="number" maxFractionDigits="2" value="${goalforyear}"/>
						<c:out value="${mgol}" />
						<input type="hidden" id="rgolSc2" value="${mgol}" />
						</td>
					<%-- <td>
 						<c:forEach items="${studStarMathTrimesterScore}" var="mc" varStatus="count"> 
							<c:out value="${mc.score}" />
 							<c:if test="${ count.index < fn:length(studStarMathTrimesterScore)-1}">, </c:if> 
						</c:forEach> 
					</td> border=1 style="white-space:nowrap;" class="goltbl"--%>
					<td><table border=1 class="goltbl bor" width="100%" height="100%">
					 <tr height="120">
 						<c:forEach items="${trimesterList}" var="tl">
 						<c:if test="${tl.orderId ne 1}">
 						<c:set var="set" value="0" />
 						<c:forEach items="${studStarMathTrimesterScore}" var="sk">
							<c:choose>
							<c:when test="${sk.trimester.orderId eq tl.orderId}">
							<td width="33%" align="center" class="borTr"><c:out value="${sk.score}" />
							<c:set var="set" value="1" />
							</td>
							</c:when>
							</c:choose>
						</c:forEach> 
						<c:if test="${set eq 0 }">
							<td width="33%" align="center">-</td>
						</c:if>
						</c:if>
						</c:forEach>
						</tr>
					</table></td>
					<td height="100">
					<ol>
 					<c:forEach items="${listMathGoalStrategies}" var="lms">  
					<li>${lms.goalStrategies.goalStrategiesDesc}</li>
					</c:forEach>
					</ol></td>
					</tr>
					</table>
					<table><tr height="10px"><td>&nbsp;</td></tr>
					</table>
					<div class="new-page">
					<table>
					<tr><td><input type="checkbox" name="chkbox2" id="chkbox2" onClick="showGraph(2)" />Star Math Report</td></tr>
					</table>
					</div>
					<div id="dd4">
					<table border=0 width="80%" align="center">
					<tr id="chartContainer2">
					</tr>
					</table>
					<table>
					<tr><td>&nbsp;</td></tr>
					<tr><td colspan="4">Your  principal, counselor and teacher are here to help.</td></tr>
					<c:if test="${userTypeId != 5 && userTypeId != 6 || tab=='showReports'}">
						<tr height="20px"><td width="80%" colspan="3" align="center"></td><td width="20%" align="center"><div id="signDiv" ${userTypeId == 4 ? 'onclick=openSignaturePad("Trimester_Report")' : ''}></div></td></tr>
						<tr><td width="80%" colspan="3" align="center"></td><td width="20%" align="right" valign="top" ${userTypeId == 4 ? 'onclick=openSignaturePad("Trimester_Report")' : ''}>___________________________</td></tr>
						<tr><td width="80%" colspan="3" align="center"></td><td width="20%"  align="center">Parent's Signature</td></tr>
					</c:if>
					<tr><td>&nbsp;
					<input type="hidden" name="range2" id="range2" value="${maxStarMathScore}" /> 
					<input type="hidden" name="range1" id="range1" value="${maxStarReadScore}" />
					</td></tr>
					<tr><td>&nbsp;</td></tr>
					</table>	</div>	
					</div>								
					</td></tr></table>
					</c:when>
					<c:otherwise>
					<div>
					<table align="center" class="alert alert-info text-center" width="80%">
					<c:choose>
					<c:when test="${userTypeId != 5 && userTypeId != 6 || tab=='showReports'}">
					<tr><td>No Reports</td></tr>
					</c:when>
					<c:otherwise>
					<tr><td>You Should Select Three Strategies For Fluency/Comprehension Strategies</td></tr>
					</c:otherwise>
					</c:choose>
					</table>
					</div>
					</c:otherwise>
					</c:choose>
					</div>
				    <span class="button_white" onClick='exportToPDF()' style="font-size:24px;color:red;"><i class="fa fa-file-pdf-o"></i></span>
<!-- 				    <span class="button_white" style="font-size:24px;color:black;" onClick="javascript:printDiv('printBoy')"><i class="fa fa-print"></i></span> -->
<!-- 				    <span class="button_white" style="font-size:24px;color:black;" onClick="javascript:printGra()"><i class="fa fa-print"></i></span> -->
<!-- 				    <li> -->
<!-- 		            <a class="various" href="#chartContainer2">PRINT GRAPH</a> -->
	                </li>
				    
			</div>
		</div>
	</div>	
					
</body>
</html>