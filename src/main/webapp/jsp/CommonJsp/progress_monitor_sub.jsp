<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.ui-dialog > .ui-widget-content {background: white;}
</style>
<script type="text/javascript">
function showGraph(studentId,csId,studentName){
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
var audio = new Audio("");
function playAudio(id){
	audio.pause();
	var path = document.getElementById(id).value;
	audio = new Audio(path);
	audio.play();
}


</script>


</head>
<body>
    <div align="center">
	<c:choose>
    	<c:when test="${userType == 'admin' || userType == 'teacher'}">
    	<table class="des" border=0 align="center"><tr><td>
			<div class="Divheads"><table>
				<tr >
					<th  align="left" width="108">Student name</th>
					<th  align="center" width="200">View Progress</th>							
				</tr></table></div>
				<div class="DivContents"><table>
					<tr><td><td>&nbsp;</td></tr>
				<c:forEach items="${studentList}" var="cList" varStatus="listStatus">					
					<tr >
						<td  align="left" width="150" style="padding-bottom: 15px;font-size:13px;" class='txtStyle'>
							<c:set var="studentName" value="${cList.student.userRegistration.firstName} ${cList.student.userRegistration.lastName}"></c:set>
							<a href="#" style="color: #000000;font-weight:500;">
								${cList.student.userRegistration.firstName} ${cList.student.userRegistration.lastName}</a>
						</td>					
						<td  align="center" width="150">
							<input type="radio" class="radio-design" id="test${listStatus.index}" name="tests" onclick="showGraph(${cList.student.studentId},${csId},'${studentName}')"><label for="test${listStatus.index}" class="radio-label-design"></label>
				</td>
					</tr>
	</c:forEach></table></div></td></tr></table>
				
		</c:when>
		<c:when test="${userType == 'parent'}">
		<table name="classes" border="0" cellpadding="0" cellspacing="0" vspace="0" width="1100" hspace="0" align="center">	
				<tr>
             <td colspan="2" align='center'>
						<a href="#" onclick="showGraph(${student.studentId},${csId},'${studentName}')"> 
							<span class="status-message">View Child Test Report</span>
						</a>
					</td>						
				</tr>
					<tr><td><td>&nbsp;</td></tr>
			</table>
		</c:when>
    	<c:otherwise>
    		<table name="classes" border="0" cellpadding="0" cellspacing="0" vspace="0" width="1100" hspace="0" align="center">	
				<tr>
             <td colspan="2" align='center'>
						<a href="#" onclick="showGraph(${student.studentId},${csId},'${studentName}')"> 
							<span class="status-message">My Test Report</span>
						</a>
					</td>						
				</tr>
					<tr><td><td>&nbsp;</td></tr>
			</table>
    	</c:otherwise>
	</c:choose>
		</div>
		<div id="monitor" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display: none;">
		<table align="center"><tr><td>
		 <div id="titleContainer"></div>
		</td></tr></table>
		<table>
		<tr><td>
			<div id="chartContainer" style="height: 400px; width: 50%;"></div>
			</td><td>
			<div id="chartContainer1" style="height: 400px; width: 50%;"></div><br></td></tr>
			<tr><td><div id="audioContainer" style="align:center"></div></td></tr>
		</table>		
		</div>
</body>
</html>