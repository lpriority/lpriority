
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Grade Assessments</title>

     <link href="resources/css/goal/bootstrap.min.css" rel="stylesheet" /> 
     <link href="resources/css/goal/light-bootstrap-dashboard.css" rel="stylesheet"/>
     <link href="resources/css/goal/demo.css" rel="stylesheet" /> 
	
<script src="resources/javascript/LineChart/jszip.min.js"></script>
<script src="resources/javascript/LineChart/pako_deflate.min.js"></script>
<link rel="stylesheet" href="resources/css/LineChart/kendo.material.min.css">
<link rel="stylesheet" href="resources/css/LineChart/kendo.dataviz.min.css">
<script src="resources/javascript/LineChart/kendo.all.min.js"></script>

	<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script>
function setActive(id){
	for(var i=1;i<=4;i++){
	 $("#"+i).removeClass("active");
	} 
	$("#"+id).attr('class', 'active');
}
</script>
<style>
.goalTxts{
    font-size:22px;
    color:#FFFFFF;
}
.gMainLink{
 color:blue;
 font-size:15px;
 font-weight:bold;
}

.sidebar:after, body > .navbar-collapse:after{
  background : #27c6e8;
  
}

 .sidebar .nav p{
  color: #000000;
  text-shadow:0 1px 1px rgb(115, 115, 115);
} 

.sidebar .logo .simple-text{
outline:none;
}


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

<div class="wrapper">

    <div class="sidebar" data-image="assets/img/sidebar-5.jpg">
    
    <input type="hidden" name="userRedId" id="userRedId" value="${parentRedId}" />
     <input type="hidden" name="studId" id="studId" value="${student.studentId}" />
     <input type="hidden" name="gradeId" id="gradeId" value="${student.grade.gradeId}" />
    <input type="hidden" name="userTypeId" id="userTypeId" value="${userTypeId}" />
    <input type="hidden" name="tab" id="tab" value="childGoalReport" />
    
    <!--   you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple" -->


    	<div class="sidebar-wrapper">
            <div class="logo">
                <a href="#" class="simple-text">
                    GOAL SETTING TOOL
                </a>
            </div>

            <ul class="nav">
                 <li id="1" class="active">
                    <a href="#" onClick="getBoyReport(${student.studentId},1)">
                     <p>BOY Report</p></a>
                    
                </li>
                <li id="2" class="">
                    <a href="#" onClick="getTrimesterReport(${student.studentId},1,2)">
                     <p>Trimester1 Report</p></a>
                    
                </li>
                <li id="3" class="">
                    <a href="#" onClick="getTrimesterReport(${student.studentId},2,3)">
                     <p>Trimester2 Report</p></a>
                    
                </li>
                <li id="4" class="">
                    <a href="#" onClick="getTrimesterReport(${student.studentId},3,4)">
                     <p>Trimester3 Report</p></a>
                    
                </li>
                        
               
				
            </ul>
    	</div>
    </div>
<div id="contentPage">
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
					<c:set var="stat" value="" />
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
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Year, you earned a 
					<c:choose>
					<c:when test="${empty studGoalMathScores[0].caasppScore}">  no score  </c:when>
					<c:otherwise>
						${studGoalMathScores[0].caasppScore} - 
					</c:otherwise>
					</c:choose>
					 <c:set var="stat" value="" />
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
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Earlier this year, you earned a 
					<c:choose>
					<c:when test="${empty studStarMathBoyScore.score}">  no score  </c:when>
					<c:otherwise>
						${studStarMathBoyScore.score}
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
					<tr><td>No Reports</td></tr>
					</table>
					</div>
					</c:otherwise>
					</c:choose>
					
					</div>
				    <span class="button_white" onClick='exportToPDF()' style="font-size:24px;color:red;"><i class="fa fa-file-pdf-o"></i></span>
			</div>
		</div>
	</div>

      </div> 

     </div>
    
<div id="loading-div-background-gs-main" class="loading-div-background"
	style="display: none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 17px; margin-top: -8.5em;">
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>Loading....
	</div>
</div>
<div id="signatureDiv" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display:none;" ><iframe id='iframe' width="99%" height="95%"></iframe></div>
</html>

