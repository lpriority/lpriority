
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
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
	for(var i=1;i<=6;i++){
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
    <input type="hidden" name="path" id="path" value="" />
    <input type="hidden" name="userTypeId" id="userTypeId" value="${userTypeId}" />
    <input type="hidden" name="gradeId" id="gradeId" value="${gradeId}">
    <!--   you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple" -->


    	<div class="sidebar-wrapper">
            <div class="logo">
                <a href="#" class="simple-text">
                    GOAL SETTING REPORTS
                </a>
            </div>

            <ul class="nav">
                <li class="active" id="1">
                    <a href="#" onClick="getAllStudsGoalReportByReportId(4,1)">
                       
                        <p>BOY Report</p>
                    </a>
                </li>
                 <li id="2" class="">
                    <a href="#" onClick="getAllStudsTrimesterReports(1,2)">
                     <p>Trimester1 Report</p></a>
                 </li>
                 <li id="3" class="">
                    <a href="#" onClick="getAllStudsTrimesterReports(2,3)">
                     <p>Trimester2 Report</p></a>
                    
                </li>
                 <li id="4" class="">
                    <a href="#" onClick="getAllStudsTrimesterReports(3,4)">
                     <p>Trimester3 Report</p></a>
                    
                </li>
                 <li id="5" class="">
                    <a href="#" onClick="getAllStudsGoalReminderReports(0,5)">
                     <p>Goal Reminder</p></a>
                    
                </li>
                 
                        
               
				
            </ul>
    	</div>
    </div>
<div id="contentPage">
    <div class="main-panel">
        <nav class="navbar navbar-default navbar-fixed">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"> Goal Setting Tool Reports</a>
                </div>
               
            </div>
        </nav>

		<div align="left" style="padding-left: 5em"><span class="button_white" onClick='exportToPDF()' style="font-size:24px;color:red;"><i class="fa fa-file-pdf-o"></i></span></div>
       <div class="content">
			<div class="container-fluid" id="printBoy">
				<c:set var="i" value="0" />
				<c:choose>
				<c:when test="${fn:length(studentList) gt 0}">
				<c:forEach items="${studentList}" var="studlt">  
				<c:set var="s" value="stud${i}" />
				<c:choose>
				<c:when test="${i eq 0}">	
				<div class="card page">
				</c:when>
				<c:otherwise>
				<div class="card page new-page">
				</c:otherwise>
				</c:choose>
					<div class="alert alert-info text-center">
						<h4 class="title">${studlt.userRegistration.firstName }&nbsp;${studlt.userRegistration.lastName } Goals for ${currentAcademicYr.academicYear }</h4>

					</div>
					<table id="" width="100%"><tr><td class="col-md-1">&nbsp;</td>
					<td>
					<table width="100%">
					<tr><td style="font-weight:bold">Dear <c:out value="${studlt.userRegistration.firstName}" />&nbsp;<c:out value="${studlt.userRegistration.lastName}" />,</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td class="txtNor"><p>Thank you for completing the goal setting form. The data, goals and action plan below should be used to keep you on track and moving toward your long range goal.</p></td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">SBAC Reading</td></tr>
					<tr><td><i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Year, you earned 
					<c:choose>
					<c:when test="${empty studGoalReadingScores[i].caasppScore}">  no score  </c:when>
					<c:otherwise>
						 a ${studGoalReadingScores[i].caasppScore} - 
					</c:otherwise>
					</c:choose>
					<c:set var="stat" value="" />
					 <c:choose>
						<c:when test="${studGoalReadingScores[i].caasppScore>3}">
							<c:set var="stat" value="Exceeds" />
						</c:when>
						<c:when test="${studGoalReadingScores[i].caasppScore==3}">
							<c:set var="stat" value="Met" />
						</c:when>
						<c:when test="${studGoalReadingScores[i].caasppScore>=1.5 && studGoalReadingScores[i].caasppScore<3}">
							<c:set var="stat" value="Almost Met" />
						</c:when>
						<c:when test="${studGoalReadingScores[i].caasppScore>=1 && studGoalReadingScores[i].caasppScore<1.5}">
							<c:set var="stat" value="Not Met" />
						</c:when>
					</c:choose>
					${stat} on the state SBAC Reading Test.<br>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your goal for this year is to earn a 
					<c:choose>
							<c:when test="${studGoalReadingScores[i].caasppScore gt 0}">
								
								
									<c:set var="goal" value="1" /> <c:set
												var="goalforyear" value="${studGoalReadingScores[i].caasppScore+goal}" />
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
					<br>
					</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">STAR Reading Grade Equivalent</td></tr>
					<tr><td>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Earlier this year, you earned  
					<c:choose>
					<c:when test="${empty studStarReadingBoyScore[i].score}">  no score  </c:when>
					<c:otherwise>
					a ${studStarReadingBoyScore[i].score}
					</c:otherwise>
					</c:choose>
					 on the state STAR Reading Test.<br>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your goal for this year is to earn a 
					<c:set var="goal" value="1" />
					<c:choose>
					<c:when test="${studStarReadingBoyScore[i].score gt 0}">
					<c:set var="goalforyear" value="${studStarReadingBoyScore[i].score+goal}" />
					</c:when>
					<c:otherwise>
					<c:set var="goalforyear" value="${studlt.grade.masterGrades.masterGradesId+goal}" /> 
					</c:otherwise>
					</c:choose>
					
					<fmt:formatNumber type="number" maxFractionDigits="1" value="${goalforyear}"/> grade equivalent.
					<br>
					</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">You plan on reaching your reading goals by:</td></tr>
					<tr><td>&nbsp;</td></tr>
					</table>
					
					<table border="1" width="100%" class="goltbl">
					<tr><td class="txtNor">				
					 <c:forEach var="secCategories" items="${listReadingGoalStrategies[s]}">
 						 ${secCategories.goalStrategies.goalStrategiesDesc} <br>
      					
       				 </c:forEach>
					</td></tr>
									
					</table>
					
					<table width="100%">
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">SBAC Math</td></tr>
					<tr><td>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Year, you earned  
				    <c:choose>
					<c:when test="${empty studGoalMathScores[i].caasppScore}">  no score  </c:when>
					<c:otherwise>
						a ${studGoalMathScores[i].caasppScore} - 
					</c:otherwise>
					</c:choose>
					<c:set var="stat" value="" />
					<c:choose>
						<c:when test="${studGoalMathScores[i].caasppScore>3}">
							<c:set var="stat" value="Exceeds" />
						</c:when>
						<c:when test="${studGoalMathScores[i].caasppScore==3}">
							<c:set var="stat" value="Met" />
						</c:when>
						<c:when test="${studGoalMathScores[i].caasppScore>=1.5 && studGoalMathScores[i].caasppScore<3}">
							<c:set var="stat" value="Almost Met" />
						</c:when>
						<c:when test="${studGoalMathScores[i].caasppScore>=1 && studGoalMathScores[i].caasppScore<1.5}">
							<c:set var="stat" value="Not Met" />
						</c:when>
					</c:choose>
					${stat} on the state SBAC Math Test.<br>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your goal for this year is to earn a 
					<c:choose>
							<c:when test="${studGoalMathScores[i].caasppScore gt 0}">
								
								
									<c:set var="goal" value="1" /> <c:set
												var="goalforyear" value="${studGoalMathScores[i].caasppScore+goal}" />
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
                      <br>
					</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td style="font-weight:bold">STAR Math Grade Equivalent</td></tr>
					<tr><td>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Earlier this year, you earned  
					<c:choose>
					<c:when test="${empty studStarMathBoyScore[i].score}">  no score  </c:when>
					<c:otherwise>
					a ${studStarMathBoyScore[i].score}
					</c:otherwise>
					</c:choose>
					 on the state STAR Math Test.<br>
					<i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your goal for this year is to earn a 
					<c:set var="goal" value="1" />
					<c:choose>
					<c:when test="${studStarMathBoyScore[i].score gt 0}">
					<c:set var="goalforyear" value="${studStarMathBoyScore[i].score+goal}" />
					</c:when>
					<c:otherwise>
					<c:set var="goalforyear" value="${studlt.grade.masterGrades.masterGradesId+goal}" /> 
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
					 <c:forEach var="mathCategories" items="${listMathGoalStrategies[s]}">
 						 ${mathCategories.goalStrategies.goalStrategiesDesc} <br>
      					
       				 </c:forEach>
					   </td></tr>
					</table>
					<table>
					<tr><td>&nbsp;</td></tr>
								<tr><td class="txtNor" colspan="4">Please review this document daily and stay on track to meeting your goals. We know you can do it!!!.</td></tr>
						<tr><td>&nbsp;</td></tr>
						<tr><td class="txtNor" colspan="4">Your  principal, counselor and teacher are here to help.</td></tr>
						
							<tr height="20px"><td width="80%" colspan="3" align="center"></td><td width="20%" align="center"><div id="signDiv:${i}">
							 <c:set var="path" value="${userSignMap[studlt.studentId]}"/>
							<c:if test="${not empty userSignMap[studlt.studentId]}">
							<img id='signatureId' width='130' height='85' src="${userSignMap[studlt.studentId]}"/>
							</c:if>
							</div></td></tr>
							<tr><td width="80%" colspan="3" align="center"></td><td width="20%" align="right" valign="top">___________________________</td></tr>
							<tr><td width="80%" colspan="3" align="center"></td><td width="20%"  align="center">Parent's Signature</td></tr>
					
						<tr height="40px"><td>&nbsp;</td></tr>
					</table>
					</td></tr>
					</table>
					
					</div>
					 <input type="hidden" name="userRedId" id="userRedId${i}" value="${studlt.userRegistration.parentRegId}" />
     				<input type="hidden" name="studId" id="studId${i}" value="${studlt.studentId}" />
					<c:set var="i" value="${i+1}" />	
 					<br clear="all" style="page-break-before:always" /> 
					</c:forEach>
					
			</div>
			</c:when>
				<c:otherwise>
				<div>
				<table align="center" class="alert alert-info text-center" width="80%">
				<tr><td>No Reports Are Availble</td></tr>
				</table>
				</div>
				</c:otherwise>
				</c:choose>  
		</div>
        </div>


       

    </div>
    </div>
<div id="loading-div-background1" class="loading-div-background"
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
