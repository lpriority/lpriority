<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript"
	src="resources/javascript/canvasjs/canvasjs.min.js"></script>
 
<style>
.goltbl {
	border-spacing: 0;
	border-collapse: collapse
}

.goala {
	color: #000000;
}

.goLink {
	color: blue;
}
</style>

<script>
  
  function myfunc(){
	var ss=document.getElementById("tri1Sc").value;
	  $("#chartContainer").html(ss);
  }
  
  function showGraph(studentId,csId,studentName){
	  console.log(studentId);
	  var csId = $('#csId').val();
	  //studentName = $('#studentName').val();
	  var studentName=$('#studentName').val();
	  console.log("csid "+csId);
	  console.log("name "+studentName);
		 $("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getBanchResults.htm",
			data : "studentId=" + studentId+"&csId="+csId,
			success : function(response) {
						
				if(response.size == 0){
					alert("No reports available for this student/class");
					$("#loading-div-background").hide();
					return;
				}else{	
				    $("#loading-div-background").hide();
					$('#titleContainer').empty(); 
					var codes="";
					codes="<table align='center'><tr><td class='tits'>Student Name&nbsp;&nbsp;:&nbsp;&nbsp;<label class=''>"+studentName+"</label></td></tr></table><br>";
					$(titleContainer).append(codes);
					$('#monitor').show();
					var graphContainer = $(document.getElementById('monitor'));
					var screenWidth = $( window ).width() - 10;
					var screenHeight = $( window ).height() - 10;
					//$(gradeContainer).append(response);
					$(graphContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
						  $(".ui-dialog-titlebar-close").show();
					}, close: function(event, ui) {
			       		 $(this).dialog('destroy');
			       		$('#monitor').hide();
	 		       			} 
	 		       			});				
					$(graphContainer).dialog("option", "title", "Student Progress Monitoring");
					$(graphContainer).scrollTop("0");
					$('#chartContainer').empty(); 
					var dpFluency = [];
					var dpReTell = [];
					var dpFluencyCutoff=[];
					var dpRetellCutoff=[];
					var fluDates=[];
					var colorss=[];
					var m=0;
					var benchResults = response.benchResults;
					
					$.each(benchResults,
						function(index, value) {
							var date = new Date(value.studentAssignmentStatus.assignment.dateAssigned);
							var color = "";
							var color1="";
							var border = "";
							var size = 0;
							var fScore = value.medianFluencyScore;	
							
							var rScore = value.qualityOfResponse.qorId;
							if(value.studentAssignmentStatus.assignment.benchmarkCategories.isFluencyTest == 0){
								color = "white";
								color1="white";
								border = "#ffffff";
								size = 10;
							}else{
								color = "#0000ff";
								color1="#0000ff";
								size = 25;
								fluDates.push(date);
							}
							//fluff.push(date);
							dpFluency.push({ 
								x: new Date(date),
								y: fScore,
								markerType: "circle", 
								markerColor: color,
								markerBorderColor : "#0000ff", 
								markerSize: size,
								markerBorderThickness: 2,
								 
						    });
							dpReTell.push({ 
								x: new Date(date),
								y: rScore,
								markerType: "circle", 
								markerColor: color1,
								markerBorderColor : "#0000FF", 
								markerSize: size,
								markerBorderThickness: 2 
						    });	
							m++;
					});
					var benchCutOffMarks = response.benchCutOffMarks;	
					
					var n=0,j=0;
					n=fluDates.length;
					$.each(benchCutOffMarks,
						function(index, value) {
						var fluencyCutoff = value.fluencyCutOff;
						var retellCutoff=value.retellCutOff;
						if(j<n){
						dpFluencyCutoff.push({ 
							x: new Date(fluDates[j]),
							y: fluencyCutoff,
							markerType: "square", 
							markerColor: "#ff0000",
							markerBorderColor : "#ff0000", 
							markerSize: 20,
							markerBorderThickness: 2,
							
							 
					    });	
						dpRetellCutoff.push({ 
							x: new Date(fluDates[j]),
							y: retellCutoff,
							markerType: "square", 
							markerColor: "#ff0000",
							markerBorderColor : "#ff0000", 
							markerSize: 20,
							markerBorderThickness: 2,
											 
					    });	
						j++;
						}
					});
					

					CanvasJS.addColorSet("greenShades",
									                [//colorSet Array
							                        "#0000ff",
									                "#ff0000",
									                "#ff69b4",
									                "#2E8B57",
									                "#3CB371",
									                "#90EE90"                
									                ]);
					CanvasJS.addColorSet("retellShades",
			                [//colorSet Array
	                        "#0000FF",
			                "#ff0000",
			                "#ff69b4",
			                "#2E8B57",
			                "#3CB371",
			                "#90EE90"                
			                ]);
					var chart = new CanvasJS.Chart("chartContainer",{
						colorSet: "greenShades",
						toolTip:{   
							content: "<b>{name}</b>:<br><b>Date</b> {x}<br> <b>Score</b>: {y}"      
						},
						axisX:{  
						  //Try Changing to MMMM
							valueFormatString: "MMM-YYYY",
						    interval: 1,
						    intervalType: "month",
						    labelAngle: -45,
						},
						axisY: {
							minimum: 0,
						   // maximum: 200,
						    interval: 40,
						    gridThickness: 0.5,
						 },
						data: [
	                       	{        
								type: "line",
								name:"Fluency",
								showInLegend: true,
								legendMarkerType: "square",
							    dataPoints: dpFluency,
							    name: "FluencyScore",
							    
	 						},
	  						{       
	 							type: "line",
	  							name:"Grade Level",
	  							showInLegend: true,
	  							legendMarkerType: "square",
	  							dataPoints: dpFluencyCutoff,
	  							name: "Grade Level",
	  						}

							
						]
					});
					chart.render();
					
					var chart1 = new CanvasJS.Chart("chartContainer1",{
						colorSet: "retellShades",
						toolTip:{   
							content: "<b>{name}</b>:<br><b>Date</b> {x}<br> <b>Score</b>: {y}"         
						},
						axisX:{  
						  //Try Changing to MMMM
							valueFormatString: "MMM-YYYY",
						    interval: 1,
						    intervalType: "month",
						    labelAngle: -45,
						},
						axisY: {
							minimum: 0,
						    maximum: 4,
						    interval: 1,
						    gridThickness: 0.5,
						},
						data: [
	                       	{       
								type: "line",
								name:"Retell",
								showInLegend: true,
								legendMarkerType: "square",
								dataPoints: dpReTell,
								name: "RetellScore",
							},
							{       
	 							type: "line",
	  							name:"Grade Level",
	  							showInLegend: true,
	  							legendMarkerType: "square",
	  							dataPoints: dpRetellCutoff,
	  							name: "Grade Level",
	  						}

							
						]
					});
					chart1.render();
					//For Audio details
					var audioContainer = $(document.getElementById('audioContainer'));
					$('#audioContainer').empty();
					var code = "";
					code ='<table align="center" class="des" border=0><tr><td><table class="Divheads"><tr><td style="width: 7cm;">Title</td>';
					code = code + '<td style="width: 7cm;">Fluency Audio</td><td style="width: 7cm;">Retell Audio</td></tr></table>';	
					var benchAssignments = response.benchAssignments;	
					$.each(benchAssignments,
						function(index, value) {
						code = code + '<table class="DivContents"><tr><td style="width: 7cm;">'+value.studentAssignmentStatus.assignment.title+'</td>';
						code = code + '<td align="left" style="width: 7cm;"><audio id="playerId" style="width: 7cm;" controls="" preload="auto"><source src="loadDirectUserFile.htm?usersFilePath='+value.baudioPath+'"></audio>';
						code = code + '<input type="hidden" id="flueId'+index+'" value="loadDirectUserFile.htm?usersFilePath='+value.baudioPath+'"></td>';
						code = code + '<td align="left" style="width: 7cm;"><audio id="playerId" style="width: 7cm;" controls="" preload="auto"><source src="loadDirectUserFile.htm?usersFilePath='+value.bretellpath+'"></audio>';
						code = code + '<input type="hidden" id="retellId'+index+'" value="loadDirectUserFile.htm?usersFilePath='+value.bretellpath+'"></td></tr>';
					});
					code = code + '</table></td></tr></table>';
						
					$(audioContainer).append(code);
									//$("#loading-div-background").hide();
				}	
			}
		});
		
	}
  
  
/*  function showGraph(){
 	var stat=$('#stat').val();
 	if(stat){
 		$('#chartContainer').empty();
 		$("#chartContainer").css("height", "");
 		$('#stat').val("");
 	}else{
 		        var gty=$('#gty').val();
 		        var type="";
 		        if(gty=="Fluency")
 		        	type="STAR Fluency Scores";
 		        else
 		        	type="STAR Comprehension Scores";
 		        		        
  				var len=$('#len').val();
  				if(len>0){
					
					$('#chartContainer').empty(); 
					$('#stat').val("true");
					
					var score=[];
 					var x = document.getElementsByName("score");
 					var m;
					for (m = 0; m < x.length; m++) {
	                  score[m]=	Number(x[m].value);	
	            
					}
	
					
					var goal=[],smax;
				    goal[0]=Number(score[0]);
				    goal[3]=Number($('#golSc').val());
				    
				    var gsc=document.getElementById("starMax").value;
				    
					 if(gsc>goal[3])
				    	smax=gsc;
				     else
				    	smax=goal[3]+1;
					 
					 			    				      
					var name=["boy", "tri1", "tri2","eoy"];
					var yname="goal";					
					var starScores = [];
					var goals = [];
					fLen = name.length;
					
					for (var i = 0; i < fLen; i++) {
						   
					    	var xname,goalScores=null;				
							var fScore =score[i];
							var gScore =goal[i];
							var xname=name[i];
							if(fScore!=0){
								goalScores=score[i];
							}
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
 						color1="#ff0000";
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
							
					var chart = new CanvasJS.Chart("chartContainer",{
						colorSet: "greenShades",
						toolTip:{   
							content: "{label}<b>:</b> {y}"      
						},
						width:700,
						height:400,
						axisX:{  
						    labelAngle: -30,
						   // interval:"{label}",
						    
						},
						axisY: {
							minimum: 0,
						    maximum: smax,
						    interval: 0.5,
						    gridThickness: 0.5,
						    includeNull: true,
						 },
						data: [
	                       	{        
								type: "line",
								connectNullData:true,
							    nullDataLineDashType:"solid",
								name:type,
								showInLegend: true,
								legendMarkerType: "square",
							    dataPoints: starScores,
							}
 	                       	,
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
					$("#chartContainer").css("height", "400");
					chart.render();
					
					
			}else{
				$("#chartContainer").html("<center><font color='red'>No Datas Available</font></center>");
			}
	}
 	}
	*/
</script>

</head>
<body>
	<c:set var="gtype" value="Fluency" />
	<c:if test="${goalTypeId==2}">
		<c:set var="gtype" value="Comprehension" />
	</c:if>
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
				<a class="navbar-brand" href="#">Goal Setting Tools <c:out
						value="${gtype}"></c:out></a>
			</div>
			<input type="hidden" id="caasppScoresId" value="caasppScoresId" /> <input
				type="hidden" id="len" value="${fn:length(studStarReadingScores)}" />
			<input type="hidden" id="gty" value="${gtype}" /> 
			<input type="hidden" id="studentName" id="studentName" value="${student.userRegistration.firstName} ${student.userRegistration.lastName}" />
			<input type="hidden" name="studentId" id="studentId" value="${studentId}" /> <input
				type="hidden" id="gradeId" value="${student.grade.gradeId}" />
               <input type="hidden" name="starMax" id="starMax" value="${starMax}" />
		</div>
		</nav>


		<div class="content">
			<div class="container-fluid">
				<div class="card">
					<div class="alert alert-info text-center">
						<h4 class="title">
							Goal Setting Tools
							<c:out value="${gtype}"></c:out>
							
						</h4>

					</div>
					<table border=1 width="80%" align="center" class="goltbl">
						<tr>
							<td colspan="2" class="alert-info">
								<h5 class="text-center">
									CAASPP
									<c:out value="${gtype}"></c:out>
								</h5>
							</td>
						</tr>
						<tr>
							<td class="text-center">Previous Year Score</td>
							<td class="text-center">Goal For This Year</td>
						</tr>
						<c:choose>
							<c:when test="${fn:length(studGoalScores) gt 0}">
								<c:forEach items="${studGoalScores}" var="sg">

									<tr style="height: 35px">
										<td class="text-center">${sg.caasppScore}</td>
										<td class="text-center"><c:set var="goal" value="1" /> <c:set
												var="goalforyear" value="${sg.caasppScore+goal}" /> <c:choose>
												<c:when test="${goalforyear>=4}">
													<c:out value="4" />
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="number" maxFractionDigits="2"
														value="${goalforyear}" />
												</c:otherwise>
											</c:choose></td>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr style="height: 35px">
									<td class="text-center">&nbsp;</td>
									<td class="text-center"><c:out value="3" />
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<br>
					<table border=1 width="80%" align="center" class="goltbl">
						<tr>
							<td colspan="2" class="alert-info">
								<h5 class="text-center">
									<c:out value="${gtype}"></c:out>
									&nbsp;&nbsp;<a href="#" onclick="showGraph(${student.studentId},${csId})" class="goala"><i
										class="fa fa-caret-down"></i></a> 
							
								</h5> <input type="hidden" name="stat" id="stat" value="" />
							</td>
						</tr>
					</table>
			<div id="monitor" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display: front;">
				<table align="center"><tr><td>
				 <div id="titleContainer"></div>
				</td></tr></table>
				<table>
				<tr><td>
					<div id="chartContainer" style="height: 400px; width: 50%;"></div>
					
					<div id="chartContainer1" style="height: 400px; width: 50%;"></div><br></td></tr>
					<tr><td><div id="audioContainer" style="height: 400px; width: 50%;"></div></td></tr>
				</table>		
			</div>					
					
					<br>
					<table border=0 width="80%" align="center" class="goltbl">
						<tr id="chartContainer">
						</tr>
					</table>

					<table border=1 width="80%" align="center" class="goltbl">
						<tr class="alert-info">
							<td class="text-center">BOY Score</td>
							<td class="text-center">Year End Goal</td>
							<td class="text-center">Tri 1 Score</td>
							<td class="text-center">Tri 2 Score</td>
							<td class="text-center">Year End Score</td>
						</tr>
						<c:set var="index" value="1" />
						<c:set var="maxOrderId" value="${maxOrderId+1}" />
						<tr style="height: 35px">
							<c:forEach items="${trimesterList}" var="tl">
								<td class="text-center">
									<c:set var="st" value="0" /> 
									
									<c:if test="${tl.orderId le maxOrderId or tl.orderId eq 1}">
									<input type="radio" name="star"
												value="4"
												onClick="getStarStrategies(${starCaasppTypeId},${index},${tl.trimesterId},${student.studentId})">
									</c:if>
								<c:forEach items="${studStarReadingScores}" var="sk">
								<c:choose>
											<c:when test="${sk.trimester.orderId eq tl.orderId}">
											
												<fmt:formatNumber var="boy" type="number"
													maxFractionDigits="2" value="${sk.score}" />
												<input type="hidden" id="score${index}" name="score"
													value="${boy}" />
												<input type="hidden" id="starScoresId${index}"
													value="${sk.starScoresId}" />
												<c:out value="${boy}" />R
												<c:set var="st" value="1" />
											</c:when>
										</c:choose>
									</c:forEach> <c:if test="${st eq 0}">
									
										<input type="hidden" id="score${index}" name="score" value="0" />
									</c:if></td>
								<c:if test="${tl.orderId eq 1}">
									<td class="text-center"><c:set var="goal" value="1" /> <c:choose>
											<c:when test="${st eq 0}">
												<c:set var="goalforyear"
													value="${student.grade.masterGrades.masterGradesId+goal}" />
												<fmt:formatNumber var="gol" type="number"
													maxFractionDigits="2" value="${goalforyear}" />
												<c:out value="${gol}" />
												<input type="hidden" id="golSc" value="${gol}" />
											</c:when>
											<c:otherwise>
												<c:set var="goalforyear" value="${boy+goal}" />
												<fmt:formatNumber var="gol" type="number"
													maxFractionDigits="2" value="${goalforyear}" />
												<c:out value="${gol}" />
												<input type="hidden" id="golSc" value="${gol}" />
											</c:otherwise>
										</c:choose></td>
								</c:if>

								<c:set var="index" value="${index+1}" />
							</c:forEach>

							<c:forEach var="indx" begin="${index}" end="4" step="1">
								<c:if test="${indx eq 1}">
									<td>&nbsp;</td>
								</c:if>
								<td>&nbsp;
								
								<input type="hidden" id="starScoresId${indx}" value="0" />
								</td>
							</c:forEach>
						</tr>
					</table>
					<br>
					<table border=1 width="80%" align="center" id="starStrategy"
						class="goltbl">

					</table>
					<table class="goltbl">
						<tr style="padding-top: 25px">
							<td>&nbsp;</td>
					</table>
				</div>
			</div>
		</div>
	</div>

</body>
</html>