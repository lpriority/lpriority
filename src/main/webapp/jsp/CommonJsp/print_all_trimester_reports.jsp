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
/* Print Page */
/*     .page { */
/*         width: 210mm; */
/*         min-height: 297mm; */
/*         padding: 20mm; */
/*         margin: 10mm auto; */
/*         border-radius: 5px; */
/*         background: white; */
/*         box-shadow: 0 0 5px rgba(0, 0, 0, 0.1); */
/*     } */
       
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
    border-width: 1pt;
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
<script>

$(document).ready(function() {
	
 	var x = document.getElementsByName("chkbox1");
 	var y = document.getElementsByName("chkbox2");
 	for(var i=0;i<x.length;i++){
 	document.getElementById('chkbox1:'+i).checked = true;
 	document.getElementById('chkbox2:'+i).checked = true;
 	showGraphs(i,1);
 	showGraphs(i,2);
 	}
	
// 	$(".various").fancybox({
// 		maxWidth	: 800,
// 		maxHeight	: 600,
// 		fitToView	: false,
// 		width		: '70%',
// 		height		: '70%',
// 		autoSize	: false,
// 		closeClick	: false,
// 		openEffect	: 'none',
// 		closeEffect	: 'none',
//         afterShow   : function() {
//             // alert('You are about to print the graph!');
//              self.print();
//           // $("#printBoy").print();
//         	//$('#').html($(this)[0].innerHTML).printArea();
//         },
//         afterClose  : function() {
//             // alert('We need to refresh the page!');
//             window.location.reload(false); 
//         }        
// 	});
	 checkParentSignExists('Trimester_Report');
	 
});

function showGraphs(a,type){
	
	var x;
		if(document.getElementById('chkbox'+type+":"+a).checked) {
			document.getElementById("chartContainer"+type+":"+a).style.display = "block";
			    var gtype="";
		        if(type==1)
		        	gtype="STAR Reading Scores";
		        else
		        	gtype="STAR Math Scores";					        
  				var len=document.getElementById('len'+type+":"+a).value;
  				if(len>0){
					
					$('#chartContainer'+type+":"+a).empty(); 
										
					var score=[];
					if(type==1)
 					 x = document.getElementsByName("rscore"+a);
					else
					 x = document.getElementsByName("mscore"+a);	
				
					var m;
					for (m = 0; m < x.length; m++) {
	                  score[m]=	Number(x[m].value);	
	                 }
						
				    var goal=[],smax;
				    goal[0]=Number(score[0]);
				    if(type==1){
				    	goal[3]=Number(document.getElementById("rgolSc:"+a).value);
				    	var gsc=document.getElementById("rRange:"+a).value;
				    	if(gsc>goal[3])
				    		smax=gsc;
				    	else
				    		smax=goal[3]+1;
				    	
				    }
				    else{
				    	goal[3]=Number(document.getElementById("mgolSc:"+a).value);
				    	var gsc=document.getElementById("mRange:"+a).value;
				    	if(gsc>=goal[3])
				    		smax=gsc;
				    	else
				    		smax=goal[3]+1;
				    	
				    }
				         
					var name=["boy", "tri1", "tri2","eoy"];
										
					var starScores = [];
					var goals = [];
					fLen = name.length;
					for (var i = 0; i < fLen; i++) {
					    	var xname,goalScores=null;					
							var fScore =score[i];	
							var gScore =goal[i];
							if(fScore!=0){
								goalScores=score[i];
							}
							var xname=name[i];
							var color = "#0000ff";
							var color1="#ff0000";
							var size = 20;
							starScores.push({ 
								x:i,
								y: goalScores,
								markerType: "circle", 
								markerColor: color,
								markerBorderColor : "#0000ff", 
								markerSize: size,
								markerBorderThickness: 2,
								label:xname
								 
						    });			
					}
					for(var k=0;k<4;k++){
						
						size = 16;
						if(k==0 || k==3){							
							var yname=name[k];
							var gScore =goal[k];
							goals.push({ 
								x:k,
								y: gScore,
								markerType: "circle", 
								markerColor: color1,
								markerBorderColor : "#ff0000", 
								markerSize: size,
								markerBorderThickness: 2,
								label:yname
						    });
						}
					}
					

					CanvasJS.addColorSet("greenShades",
									                [
							                        "#0000ff",
									                "#ff0000",
									                "#ff69b4",
									                "#2E8B57",
									                "#3CB371",
									                "#90EE90"                
									                ]);
					
					var chart = new CanvasJS.Chart("chartContainer"+type+":"+a,{
						colorSet: "greenShades",
						toolTip:{   
							content: "{label}<b>:</b> {y}"      
						},
						width:700,
						height:400,
						axisX:{  
						    labelAngle: -30
						},
						axisY: {
							minimum: 0,
						    maximum: smax,
						    interval: 0.5,
						    gridThickness: 0.5,
						 },
						data: [
	                       	{        
								type: "line",
								connectNullData:true,
							    nullDataLineDashType:"solid",
								name:gtype,
								showInLegend: true,
								legendMarkerType: "square",
							    dataPoints: starScores,
							},
	 						{       
	 							type: "line",
	 							connectNullData:true,
 	 							nullDataLineDashType:"solid",
	  							name:"Goal",
	  							showInLegend: true,
	  							legendMarkerType: "square",
	  							dataPoints: goals,
	  							
	  						}
	  												
						]
					});
					
					//$("#chartContainer"+type+":"+a).css("height", "400px");
					chart.render();
										
			}else{
				$("#chartContainer"+type+":"+a).html("<center><font color='red'>No Datas Available</font></center>");
				document.getElementById('chartContainer'+type+':'+a).style.display = 'none';
			}
		
 	}else{
 		$('#chartContainer'+type+":"+a).html("");
 		document.getElementById('chartContainer'+type+':'+a).style.display = 'none';
  	}
 	}
</script>
<style>
body{
 -webkit-print-color-adjust:exact;
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
				<a class="navbar-brand" href="#">Goal Setting Tool Reports</a>
			</div>
            <input type="hidden" id="studId" value="${student.studentId}" />
             <input type="hidden" id="gradeId" value="${student.grade.gradeId}" />
           
           
            
            
		</div>
		</nav>

       
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
				<div class="card page">
				</c:otherwise>
				</c:choose>
					<div class="alert alert-info text-center">
						<h4 class="title">Trimester<c:out value="${trimesterId}" /> Goal Sheet</h4>

					</div>
					<table width="100%"><tr><td class="col-md-1">&nbsp;</td>
					<td>
					<div id="dd">
					<table width="100%">
					<tr><td style="font-weight:bold">Dear <c:out value="${studlt.userRegistration.firstName}" />&nbsp;<c:out value="${studlt.userRegistration.lastName}" />,</td></tr>
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
					<td width="17%"><table width="17%" border=1  class="goltbl bor"><tr class="borTr"><td colspan="3">Trimester Scores</td></tr><tr><td class="text-center">Tri1</td><td class="text-center">Tri2</td ><td class="text-center">Eoy</td></tr></table></td>
					<td width="49%" align="center">READING STRATEGIES I COMMIT TO:</td>
					</tr>
					<tr>
					<td>${studStarReadingBoyScore[i].score}</td>
					<td><c:set var="goal" value="1" />
					<c:choose>   
					<c:when test="${studStarReadingBoyScore[i].score gt 0}">
					<c:set var="goalforyear" value="${studStarReadingBoyScore[i].score+goal}" />
					</c:when>
					<c:otherwise>
					<c:set var="goalforyear" value="${studlt.grade.masterGrades.masterGradesId+goal}" /> 
					</c:otherwise>
					</c:choose>
						<fmt:formatNumber var="gol" type="number" maxFractionDigits="1" value="${goalforyear}"/>
						<c:out value="${gol}" />
						<input type="hidden" name="rgolSc:${i}" id="rgolSc:${i}" value="${gol}" /> 
						
						</td>
					<%-- <td>
					
  						<c:forEach items="${studStarReadingTrimesterScore[s]}" var="sco" varStatus="count">  
 							<c:out value="${sco.score }" />
   							<c:if test="${ count.index < fn:length(studStarReadingTrimesterScore[s]) -1 }">, </c:if>   
  						</c:forEach>  
					</td> --%>
					<td><table border=1 class="goltbl bor" width="100%">
					 <tr height="120">
 						<c:forEach items="${trimesterList}" var="tl">
 						<c:if test="${tl.orderId ne 1}">
 						<c:set var="set" value="0" />
 						<c:forEach items="${studStarReadingTrimesterScore[s]}" var="sk">
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
					<td>
					<ol>
					 <c:forEach var="secCategories" items="${listReadingGoalStrategies[s]}">
 				   		<li> ${secCategories.goalStrategies.goalStrategiesDesc} </li>
      				 </c:forEach>
       				 </ol>
					
					</td>
					</tr>
					</table>
					<table><tr height="10px"><td>
					<input type="hidden" name="rRange:${i}" id="rRange:${i}" value="${starMaxRead[s]}" /> 
					&nbsp;</td></tr>
					</table>
					</div>
					<div id="dd2">
					<table>
					<tr><td><input type="checkbox" name="chkbox1" id="chkbox1:${i}" onClick="showGraphs(${i},1)" />Star Reading Report </td></tr>
					</table>
					<table border=0 width="80%" align="center">
					<tr id="chartContainer1:${i}" height="400" style="display:none;">
					</tr>
					<tr><td>&nbsp;</td></tr>
					</table>
					</div>
					<div id="dd3">
					<table border="1" class="goltbl">
					<tr><td colspan="4" class="text-center alert-info">STAR MATH</td></tr>
					<tr><td width="17%">Beginning of Year Score</td>
					<td width="17%">Year Long Goal</td>
					<td width="17%"><table width="17%" border=1  class="goltbl bor"><tr class="borTr"><td colspan="3">Trimester Scores</td></tr><tr><td class="text-center">Tri1</td><td class="text-center">Tri2</td ><td class="text-center">Eoy</td></tr></table></td>
					<td width="49%" align="center">MATH STRATEGIES I COMMIT TO:</td>
					</tr>
					<tr>
					<td>${studStarMathBoyScore[i].score}</td>
					<td><c:set var="goal" value="1" />
						<c:choose>   
					<c:when test="${studStarMathBoyScore[i].score gt 0}">
					<c:set var="goalforyear" value="${studStarMathBoyScore[i].score+goal}" />
					</c:when>
					<c:otherwise>
					<c:set var="goalforyear" value="${studlt.grade.masterGrades.masterGradesId+goal}" /> 
					</c:otherwise>

					</c:choose>
						<fmt:formatNumber var="mgol" type="number" maxFractionDigits="1" value="${goalforyear}"/>
						<c:out value="${mgol}" />
						<input type="hidden" name="mgolSc:${i}" id="mgolSc:${i}" value="${mgol}" /> 
						 
						</td>
					<%-- <td>
					<c:forEach var="sco" items="${studStarMathTrimesterScore[s]}" varStatus="count">   
 							<c:out value="${sco.score}" />  
 							<c:if test="${ count.index < fn:length(studStarMathTrimesterScore[s])-1}">, </c:if>   
  						</c:forEach> 
					</td> --%>
					<td><table border=1 class="goltbl bor" width="100%">
					 <tr height="120">
 						<c:forEach items="${trimesterList}" var="tl">
 						<c:if test="${tl.orderId ne 1}">
 						<c:set var="set" value="0" />
 						<c:forEach items="${studStarMathTrimesterScore[s]}" var="sk">
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
					<td>
					
					<ol>
					 <c:forEach var="mathCategories" items="${listMathGoalStrategies[s]}">
 						<li>${mathCategories.goalStrategies.goalStrategiesDesc} </li>
 					 </c:forEach>
       				 </ol>
					</td>
					</tr>
					</table>
					<table><tr height="10px"><td>
					<input type="hidden" name="mRange:${i}" id="mRange:${i}" value="${starMaxMath[s]}" />
					&nbsp;</td></tr>
					</table>
					</div>
					<div class="new-page"></div>
					<div id="dd4">
					<table>
					<tr><td><input type="checkbox" name="chkbox2" id="chkbox2:${i}" onClick="showGraphs(${i},2)" />Star Math Report</td></tr>
					</table>
							
					<table border=0 width="80%" align="center">
					<tr id="chartContainer2:${i}" height="400" style="display:none;">
					</tr>
					</table>
					<table>
					<tr><td>&nbsp;</td></tr>
					<tr><td colspan="4">Your  principal, counselor and teacher are here to help.</td></tr>
					
					<tr height="20px"><td width="80%" colspan="3" align="center"></td><td width="20%" align="center"><div id="signDiv:${i}">
							 <c:set var="path" value="${userSignMap[studlt.studentId]}"/>
							<c:if test="${not empty userSignMap[studlt.studentId]}">
							<img id='signatureId' width='130' height='85' src="${userSignMap[studlt.studentId]}"/>
							</c:if>
							</div></td></tr>
							<tr><td width="80%" colspan="3" align="center"></td><td width="20%" align="right" valign="top">___________________________</td></tr>
							<tr><td width="80%" colspan="3" align="center"></td><td width="20%"  align="center">Parent's Signature</td></tr>
					
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					</table>	</div>									
					</td></tr></table>
					
					</div>
					<div class="new-page"></div>
					<c:set var="r" value="1" />
           		    <c:set var="m" value="1" />
           		    
           		    <c:forEach items="${trimesterList}" var="tl">
								
									<c:set var="st" value="0" /> <c:forEach
										items="${allStarReadScs[s]}" var="sk">
										<c:choose>
											<c:when test="${sk.trimester.orderId eq tl.orderId}">
												<fmt:formatNumber var="boy" type="number"
													maxFractionDigits="2" value="${sk.score}" />
												<input type="hidden" name="rscore${i}" id="rscore${i}:${r}" value="${boy}" />
												
												<c:set var="st" value="1" />
											</c:when>
										</c:choose>
									</c:forEach> <c:if test="${st eq 0}">
										<input type="hidden" name="rscore${i}" id="rscore${i}:${r}" value="0" />
									</c:if>
									<c:set var="r" value="${r+1}" />
			      </c:forEach>
           			 <c:forEach items="${trimesterList}" var="tn">
								
									<c:set var="st1" value="0" /> <c:forEach
										items="${allStarMathScs[s]}" var="sm">
										<c:choose>
											<c:when test="${sm.trimester.orderId eq tn.orderId}">
												<fmt:formatNumber var="boy" type="number"
													maxFractionDigits="2" value="${sm.score}" />
												<input type="hidden" name="mscore${i}" id="mscore${i}:${m}" value="${boy}" />
																							
												<c:set var="st1" value="1" />
											</c:when>
										</c:choose>
									</c:forEach> <c:if test="${st1 eq 0}">
										<input type="hidden" name="mscore${i}" id="mscore${i}:${m}" value="0" />
									</c:if>
									<c:set var="m" value="${m+1}" />
			     </c:forEach>



<%-- 					<c:forEach var="ms" items="${allStarMathScs[s]}"> --%>
<%-- 						<input type="hidden" name="mscore${i}" id="mscore${i}:${m}" value="${ms.score}" /> --%>
<%--  						<c:out value="${ms.score}" /> --%> 
<%-- 						<c:set var="m" value="${m+1}" /> --%>
						
<%-- 				</c:forEach> --%>
            		<input type="hidden"  id="len1:${i}" value="${fn:length(trimesterList)}" />
           			 <input type="hidden" id="len2:${i}" value="${fn:length(trimesterList)}" />
					<c:set var="i" value="${i+1}" />	
<!--  					<br clear="all" style="page-break-before:always" />  -->
		
					</c:forEach>
				    <span class="button_white" onClick='exportToPDF()' style="font-size:24px;color:red;"><i class="fa fa-file-pdf-o"></i></span>
<!-- 				    <span class="button_white" style="font-size:24px;color:black;" onClick="javascript:printDiv('printBoy')"><i class="fa fa-print"></i></span> -->
<!-- 				    <span class="button_white" style="font-size:24px;color:black;" onClick="javascript:printGra()"><i class="fa fa-print"></i></span> -->
<!-- 				    <li> -->
<!-- 		            <a class="various" href="#chartContainer2">PRINT GRAPH</a> -->
	                </li>
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

</body>
</html>