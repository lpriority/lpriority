<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html>
<link rel="stylesheet" href="resources/css/LineChart/kendo.material.min.css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.dataviz.min.css" />
<script src="resources/javascript/LineChart/jszip.min.js"></script>
<script src="resources/javascript/LineChart/kendo.all.min.js"></script>
<script src="resources/javascript/LineChart/pako_deflate.min.js"></script>
<script src="resources/javascript/common/jQuery.print.min.js"></script>
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>




<script type="text/javascript">
function exportToPDF(){
     kendo.drawing.drawDOM($("#taskResultDiv"))
     .then(function(group) {
         return kendo.drawing.exportPDF(group, {
             paperSize: "auto",
             margin: { left: "1cm", top: "1cm", right: "1cm", bottom: "1cm" }
         });
     })
     .done(function(data) {
         kendo.saveAs({
             dataURI: data,
             fileName: "Task_Force"
         });
     });
}
function printThisDivContents(divId){
	$("#loading-div-background").show();
	$("#taskResultDiv").print({
        globalStyles: false,
        mediaPrint: false,
        iframe: true,
        noPrintSelector: ".avoid-this"
   });
	$("#loading-div-background").hide();
}

function showGraph()
{
	<c:forEach items="${studentList}" var="sl" varStatus="status">
		console.log('${sl.lastName}');
		console.log('${sl.comGraded}');
	</c:forEach>
	
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
	
	
	var comprehension=[];
	var accuracy=[];
	var studentNames=[];
	
	var dpFluencyCutoff=[];
	var dpRetellCutoff=[];
	
	
	
	var chart = new CanvasJS.Chart("chartContainer", {
		animationEnabled: true,
		theme: "light2",
		colorSet: "greenShades",
		toolTip:{   
			content: "<b>{name}</b>:<br><b>Date</b> {x}<br> <b>Score</b>: {y}"      
		},
		title:{
			text: "Comprehension Chat"
		},
		axisX:{  
			  //Try Changing to MMMM
				valueFormatString: "Name",
			    interval: 1,
			    labelAngle: -45,
			},
			axisY: {
				minimum: 0,
			   // maximum: 200,
			    interval: 20,
			    gridThickness: 0.5,
			 },
		data: [{        
			type: "line",
	      	indexLabelFontSize: 12,
	      	dataPoints: [
			      	<c:forEach items="${studentList}" var="sl" varStatus="status">
			      	{x: '${sl.lastName}'},
			      	<c:choose>
			      		<c:when test="${sl.comprehension ge 80}">
			      			{y: ${sl.comprehension},indexLabel: "\u2191 Comprehension",markerColor: "red", markerType: "triangle" },
			      		</c:when>
						<c:otherwise>
							{y: ${sl.accuracy},indexLabel: "\u2193 Accuracy",markerColor: "DarkSlateGrey", markerType: "cross"},
						</c:otherwise>
					</c:choose>	
			      	</c:forEach>
				]
			
		}]
	});
	chart.render();
		
	
}

function getExcel(){
		var workbook = new kendo.ooxml.Workbook({
			 sheets: [{
			          columns: [
					        { width: 200 },
					        { width: 200 },
					        { width: 150 },
					        { width: 150 },
					        { width: 150 },
					        { width: 200 }
					      ],
					     	title: "Literacy Support Team Results",
					     	sortable: true,
				            pageable: true,
				            groupable: true,
				            filterable: true,
				            columnMenu: true,
				            reorderable: true,
				            resizable: true,
				            mergedCells: [
				                          "A1:F1",
				                          "A2:F2"
				                      ],
					      rows: [
							  {
							    cells: [
							      { value: "Literacy Support Team Results", fontSize: 12, bold: true,textAlign: "center"},
							      {},
							      {}
							    ]
						     }, 
						     {
							    cells: [
							      {},
							      {},
							      {}
							    ]
							 }, 
					         {
					          cells: [
					            { value: "First Name", fontSize: 12, bold: true},
					            { value: "Last Name" ,fontSize: 12, bold: true},
					            { value: "WCPM Score", fontSize: 12 , bold: true},
					            { value: "Retell Score", fontSize: 12, bold: true },
					            { value: "Accuracy",fontSize: 12 ,bold: true},
					            { value: "Comprehension Score",fontSize: 12, bold: true}
					          ]
					        },
						<c:forEach items="${studentList}" var="sl" varStatus="status">
						   {
						    cells:[
						    	
						    	<c:set var="fname" value="${sl.firstName}" scope="page"/>
									<c:set var="singlequote" value="'"/>
								    <c:set var="backslash" value="\\"/>
								    <c:if test="${fn:contains(fname, singlequote)}">
								            <c:set var="search" value="'" />
								            <c:set var="replace" value="\\'" />
								            <c:set var="fname" value="${fn:replace(fname, search, replace)}"/>
								    </c:if>
						    	
									{value: '${fname}'},
									{value: '${sl.lastName}'},
									<c:choose>
										<c:when test='${sl.wcpm ge sl.checkFluencyGrade and sl.checkFluencyGrade ne 0 and sl.fluencyGraded eq "graded"}'>
											{value: '${sl.wcpm}', background: '#00FF00'},
										</c:when>
										<c:when test="${sl.fluencyGraded ne 'graded' and sl.checkFluencyGrade ne 0}">
											{value: 'N/A'},
										</c:when>
										<c:when test="${sl.checkFluencyGrade eq 0}">
											{value: 'N/A'},
										</c:when>
										<c:otherwise>
											{value: '${sl.wcpm}'},
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test='${sl.retellScore ge 3}'>
											{value: '${sl.retellScore}', background: '#00FF00'},	
										</c:when>
										<c:when test="${sl.fluencyGraded ne 'graded' and sl.checkFluencyGrade ne 0}">
											{value: 'N/A'},	
										</c:when>
										<c:when test="${sl.checkFluencyGrade eq 0}">
											{value: 'N/A'},
										</c:when>
										<c:otherwise>
											{value: '${sl.retellScore}'},
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${sl.accuracy ge 90}">
											{value: '${sl.accuracy}', background: '#00FF00'},
										</c:when>
										<c:when test="${sl.fluencyGraded ne 'graded' and sl.checkFluencyGrade ne 0}">
											{value: 'N/A'},
										</c:when>
										<c:when test="${sl.checkFluencyGrade eq 0}">
											{value: 'N/A'},
										</c:when>
										<c:otherwise>
											{value: '${sl.accuracy}'},
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${sl.comprehension ge 80}">
											{value: '${sl.comprehension}', background: '#00FF00'},
										</c:when>
										<c:when test="${sl.comGraded ne 'graded' and sl.checkComGrade ne 0}">
											{value: 'N/A'},
										</c:when>
										<c:when test="${sl.checkComGrade eq 0}">
											{value: 'N/A'},
										</c:when>
										<c:otherwise>
											{value: '${sl.comprehension}'},
										</c:otherwise>
									</c:choose>								
									
								 ]
					         },
						</c:forEach>
			            ]
			          }
			        ]
		});
		kendo.saveAs({
		    dataURI: workbook.toDataURL(),
		    fileName: "Task_Force.xlsx"
		});
		}
		 		
</script>
<style>
	body{
	  -webkit-print-color-adjust:exact;
	}
</style>
<body>
	<table width="90%" height="100%">
		<tr><td>
		
		<!-- 							
	<tr>
       <td height="40" align="left" class="tablelabel" style="width: 12em;">
									&nbsp;&nbsp;<a href="#" onclick="showGraph()">Fluency Graph</a>
									
	   </td>
       </tr>
       
       
       -->
       
			
			<div id="taskResultDiv">
			<table border="0" cellSpacing=10 cellPadding=10 width="90%" align=left height="100%" style="background-color: white;">  
				<tr bgcolor="#f7efcf">
					<th class="tablelabel">First Name</th>
					<th class="tablelabel">Last Name</th>
					<th class="tablelabel">WCPM</th>
					<th class="tablelabel">Retell</th>
					<th class="tablelabel">Accuracy</th>
					<th class="tablelabel">Comprehension</th>
				</tr>
				<c:forEach items="${studentList}" var="sl">
			  		<tr>
						<td>${sl.firstName}</td>
						<td>${sl.lastName}</td>
						<c:choose>
							<c:when test="${sl.wcpm ge sl.checkFluencyGrade and sl.checkFluencyGrade ne 0 and sl.fluencyGraded eq 'graded'}">
								<td style="background-color: #00FF00;">${sl.wcpm}</td>	
							</c:when>
							<c:when test="${sl.fluencyGraded ne 'graded' and sl.checkFluencyGrade ne 0}">
								<td>N/A</td>	
							</c:when>
							<c:when test="${sl.checkFluencyGrade eq 0}">
								<td>N/A</td>	
							</c:when>
							<c:otherwise>
								<td>${sl.wcpm}</td>	
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${sl.retellScore ge 3}">
								<td style="background-color: #00FF00;">${sl.retellScore}</td>	
							</c:when>
							<c:when test="${sl.fluencyGraded ne 'graded' and sl.checkFluencyGrade ne 0}">
								<td>N/A</td>	
							</c:when>
							<c:when test="${sl.checkFluencyGrade eq 0}">
								<td>N/A</td>	
							</c:when>
							<c:otherwise>
								<td>${sl.retellScore}</td>	
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${sl.accuracy ge 90}">
								<td style="background-color: #00FF00;">${sl.accuracy}</td>	
							</c:when>
							<c:when test="${sl.fluencyGraded ne 'graded' and sl.checkFluencyGrade ne 0}">
								<td>N/A</td>	
							</c:when>
							<c:when test="${sl.checkFluencyGrade eq 0}">
								<td>N/A</td>	
							</c:when>
							<c:otherwise>
								<td>${sl.accuracy}</td>	
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${sl.comprehension ge 80}">
								<td style="background-color: #00FF00;">${sl.comprehension}</td>	
							</c:when>
							<c:when test="${sl.comGraded ne 'graded' and sl.checkComGrade ne 0}">
								<td>N/A</td>	
							</c:when>
							<c:when test="${sl.checkComGrade eq 0}">
								<td>N/A</td>	
							</c:when>
							<c:otherwise>
								<td>${sl.comprehension}</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
		 	</table>
		 	</div>
	 	</td></tr>
	 	<tr><td>	
		 	<table>
		 		<tr>
					<td><span class="button_white" onClick='exportToPDF()' style="font-size:24px;color:red;"><i class="fa fa-file-pdf-o"></i></span>
						<span style="font-size:24px;color:green;" class="button_white" onClick='getExcel()'><i class="fa fa-file-excel-o"></i></span>
						<span class="button_white" style="font-size:24px;color:black;" onClick="javascript:printThisDivContents('taskResultDiv')"><i class="fa fa-print"></i></span></span></td>
				</tr>
		 	</table>
	 	</td></tr>
 	</table>
 	
 	
 	<div id="monitor" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display: front;">
				<table align="center"><tr><td>
				 <div id="titleContainer"></div>
				</td></tr></table>
				<table>
				<tr><td>
					<div id="chartContainer" style="height: 600px; width: 80%;"></div>
					
				</table>		
			</div>					
					
					<br>
					<table border=0 width="80%" align="center" class="goltbl">
						<tr id="chartContainer">
						</tr>
					</table>
	</body>
</html>