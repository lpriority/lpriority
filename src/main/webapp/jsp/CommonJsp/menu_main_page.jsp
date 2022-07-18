
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	for(var i=1;i<=10;i++){
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
</style>

</head>
<body>

<div class="wrapper">

    <div class="sidebar" data-image="assets/img/sidebar-5.jpg">
    
    <input type="hidden" name="userRedId" id="userRedId" value="${parentRedId}" />
     <input type="hidden" name="studId" id="studId" value="${studentId}" />
     <input type="hidden" name="gradeId" id="gradeId" value="${gradeId}" />
    <input type="hidden" name="userTypeId" id="userTypeId" value="${userTypeId}" />
    <input type="hidden" name="tab" id="tab" value="${tab}" />
    
    <!--   you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple" -->


    	<div class="sidebar-wrapper">
            <div class="logo">
                <a href="#" class="simple-text">
                    GOAL SETTING TOOL
                </a>
            </div>

            <ul class="nav">
                <li class="active" id="1">
                    <a href="#" onClick="goToDashBoard(1,${studentId})">
                       
                        <p>Dashboard</p>
                    </a>
                </li>
                 <li id="2" class="">
                    <a href="#" onClick="goToGoalReadingPage(1,2,${studentId},3)">
                     <p>Fluency</p></a>
                 </li>
                 <li id="3" class="">
                    <a href="#" onClick="goToGoalReadingPage(2,3,${studentId},4)">
                     <p>Comprehension</p></a>
                    
                </li>
                 <li id="4" class="">
                    <a href="#" onClick="goToGoalCAASPPTestPrepPage(4,${studentId})">
                     <p>CAASPP Test Prep</p></a>
                    
                </li>
                 <li id="5" class="">
                    <a href="#" onClick="goToGoalStrategies(3,5)">
                     <p>Strategies-Fluency</p></a>
                    
                </li>
                 <li id="6" class="">
                    <a href="#" onClick="goToGoalStrategies(4,6)">
                     <p>Strategies-Comprehension</p></a>
                    
                </li>
                <li id="7" class="">
                    <a href="#" onClick="getBoyReport(${studentId},7)">
                     <p>BOY Report</p></a>
                    
                </li>
                <li id="8" class="">
                    <a href="#" onClick="getTrimesterReport(${studentId},1,8)">
                     <p>Trimester1 Report</p></a>
                    
                </li>
                <li id="9" class="">
                    <a href="#" onClick="getTrimesterReport(${studentId},2,9)">
                     <p>Trimester2 Report</p></a>
                    
                </li>
                <li id="10" class="">
                    <a href="#" onClick="getTrimesterReport(${studentId},3,10)">
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
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation-example-2">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Goal Setting Tool</a>
                </div>
               
            </div>
        </nav>


        <div class="content">
       
            <div class="container-fluid">
                <div class="card">
                    <div class="alert alert-info text-center">
                        <h4 class="title">Goal Setting Tool</h4>
                 
                   </div>
                    <table width="50%" class="text-center center-block" border=0>
                    <tr><td>&nbsp;<br><br></td>
                    </tr>
                    <tr><td class="col-md-4 col-md-offset-4"> <button type="button" id="but1" value="Fluency" class="btn-goal btn-xll"  style="background-color:${legendColor}" onClick="goToGoalReadingPage(1,2,${studentId},3)">
									<div id="set1"> 
										Fluency
									</div></button></td>
									
					<td class="col-md-4 col-md-offset-4"> &nbsp;</td>
					<td class="col-md-4 col-md-offset-4"><button type="button" id="but2" class="btn-goal btn-xll" value="Comprehension" style="background-color:${legendColor}" onClick="goToGoalReadingPage(2,3,${studentId},4)">
										<div id="set2"> 
											Comprehension
									</div></button></td>
					</tr>
					<tr><td>&nbsp;<br><br><br></td></tr>
					<tr><td>&nbsp;<br></td></tr>
					</table>
					<table width="100%" class="text-center" border=0>
					<tr><td><button type="button" id="but3" class="btn-caaspp btn-xl"  style="background-color:${legendColor}" onClick="goToGoalCAASPPTestPrepPage(4,${studentId})" >CAASPP Test Prep</button></td></tr>
					<tr><td>&nbsp;<br></td></tr>
					 </table>
					 <table width="100%" class="text-center" border=0>
					 <tr><td class="col-md-4 col-md-offset-4"><a href="#" onClick="getBoyReport(${studentId},7)"><p class="gMainLink">BOY Report</p></a></td>
                      <td class="col-md-4 col-md-offset-4"><a href="#"  onClick="getTrimesterReport(${studentId},1,8)"><p class="gMainLink">Trimester Report</p></a></td></tr>
                      <tr><td>&nbsp;<br><br></td></tr>
                      </table>
                    
                     
                       
                    </div>
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
