
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true"%>
<link rel="stylesheet" href="resources/css/LineChart/kendo.material.min.css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.dataviz.min.css" />
<script src="resources/javascript/LineChart/jszip.min.js"></script>
<script src="resources/javascript/LineChart/kendo.all.min.js"></script>
<script src="resources/javascript/LineChart/pako_deflate.min.js"></script>
<script type="text/javascript">
var TSort_Data = new Array ('sortTable', 's', 's', 'i', 'i', 'i', 'i', 'f', 'i');
function getExcel(){
		var dateAssigned=$("#assignedDate").val();
		var workbook = new kendo.ooxml.Workbook({
			 sheets: [{
			          columns: [
					        { width: 250 },
					        { width: 200 },
					        { width: 200 },
					        { width: 150 },
					        { width: 150 },
					        { width: 150 },
					        { width: 150 }
					      ],
					     	title: "Fluency Reading",
					     	sortable: true,
				            pageable: true,
				            groupable: true,
				            filterable: true,
				            columnMenu: true,
				            reorderable: true,
				            resizable: true,
				            mergedCells: [
				                          "A1:C1",
				                          "D1:F1",
				                          "A2:C2",
				                          "D2:F2",
				                          "A3:G3",
				                      ],
					      rows: [
							  {
							    cells: [
							      { value: "Teacher   :      ${teacher.title}. ${teacher.firstName} ${teacher.lastName}", fontSize: 12, bold: true,textAlign: "center"},
							      {},
							      {},
							      { value: "Grade Level   :      ${GradeLevel}", fontSize: 12 , bold: true,textAlign: "center"}
							    ]
						     }, 
						     {
							    cells: [
							      { value: "Class    :      ${ClsStatus.section.gradeClasses.studentClass.className}", fontSize: 12, bold: true,textAlign: "center"},
							      {},
							      {},
							      { value: "School   :      ${teacher.school.schoolName}", fontSize: 12 , bold: true,textAlign: "center"}
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
					            { value: "Student Name", fontSize: 12, bold: true},
					            <!-- { value: "Intervention	group" ,fontSize: 12, bold: true}, -->
					            { value: "Fluency WCPM Score", fontSize: 12 , bold: true},
					            { value: "Retell Score", fontSize: 12, bold: true },
					            { value: "Accuracy",fontSize: 12 ,bold: true},
					          ]
					        },
						<c:forEach items="${benchmarkResults}" var="br" varStatus="status">
						   {
							   <c:set var = "accuracy" value = "${br.accuracy}" />
						    cells:[
						    	
						    	<c:set var="fname" value="${br.studentAssignmentStatus.student.userRegistration.firstName}" scope="page"/>
									<c:set var="singlequote" value="'"/>
								    <c:set var="backslash" value="\\"/>
								    <c:if test="${fn:contains(fname, singlequote)}">
								            <c:set var="search" value="'" />
								            <c:set var="replace" value="\\'" />
								            <c:set var="fname" value="${fn:replace(fname, search, replace)}"/>
								    </c:if>
						    	
									{value: '${fname} ${br.studentAssignmentStatus.student.userRegistration.lastName}'},
									<!-- {value: '${rtiGroupNames[status.index]}'}, -->
									{value: '${br.medianFluencyScore}'},
									{value: '${br.qualityOfResponse.qorId}'},
									{value: <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value ="${accuracy}" />},
								 ]
					         },
						</c:forEach>
			            ]
			          }
			        ]
		});
		kendo.saveAs({
		    dataURI: workbook.toDataURL(),
		    fileName: dateAssigned+".xlsx"
		});
		}
		 		
</script>
<style>
	 label {
	   display: inline-block;
	   width: 5em;
	 }
</style>
<table align="right">
	<tr>
		<td><div class="button_green" align="right" onclick="getAllStudentFluencyResults(${AssignmentId},1)">Error Words/Item analysis</div></td>
		
<div id="fluencyDailog"></div>
</tr></table>
<table width="100%" style="font-size:16px;height:100%">
<tr><td>	
<table width="100%" id="contentDiv" class="demo" >
	<tr><td>	  
		<table width="75%" align="right" style="">
		<tr><td class="tabtxt"><font color="#4C7901">Teacher</font> &nbsp;&nbsp;:&nbsp;&nbsp;${teacher.title}&nbsp;${teacher.firstName}&nbsp;${teacher.lastName}</td>
		<td class="tabtxt"><font color="#4C7901">Grade Level </font>&nbsp;&nbsp;:&nbsp;&nbsp;${GradeLevel}</td></tr>
		<tr><td class="tabtxt"><font color="#4C7901">Class</font> &nbsp;&nbsp; : &nbsp;&nbsp;${ClsStatus.section.gradeClasses.studentClass.className}</td><td class="tabtxt"><font color="#4C7901">School</font> &nbsp;&nbsp;: &nbsp;&nbsp;${teacher.school.schoolName}</td>
		</tr>
		</table>
	</td></tr>
	<tr><td>
		<table width="100%" border="1" cellpadding="0" cellspacing="0" border="0" id="sortTable" style="border-collapse: collapse;text-decoration:none;">
			<thead>
				<tr>
					<th style="text-align: center" width="20%" valign="middle">Student&nbsp;Name</th>
					
					<!--  <th width="12%" align="center" valign="middle">Intervention	group</th> -->
					<th width="15%" style="text-align: center" valign="middle">Fluency WCPM Score</th>
					<th width="12%" style="text-align: center" valign="middle">Retell Score</th>
					<th width="12%" style="text-align: center" valign="middle">Accuracy</th>
					<!-- <th width="20%" align="center" valign="middle">Composite Score</th> -->
				</tr>
			</thead>
			<c:set var="a" value="0" />
			<c:forEach items="${benchmarkResults}" var="br">
			<c:set var = "accuracy" value = "${br.accuracy}" />
			<tr>
				<td width="20%" align="center" valign="middle">${br.studentAssignmentStatus.student.userRegistration.firstName}&nbsp;${br.studentAssignmentStatus.student.userRegistration.lastName}</td>
				<!--  <td width="12%" align="center" valign="middle"><c:out value="${rtiGroupNames[a]}"></c:out></td> -->
				<!--  <td width="15%" align="center" valign="middle">${br.studentAssignmentStatus.assignment.title}</td> -->
				<td width="15%" align="center" valign="middle">${br.medianFluencyScore}</td>
				<td width="12%" align="center" valign="middle"><div onmouseover="" style="cursor: pointer;" title="${br.qualityOfResponse.response}">${br.qualityOfResponse.qorId}</div></td>
				
				<td width="12%" align="center" valign="middle"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value ="${accuracy}" /></td>
				<!--  <td width="12%" align="center" valign="middle"> <c:out value="${compositeScores[a]}"></c:out></td>-->
			</tr>
			  <c:set var="a" value="${a+1}" />
			</c:forEach>
			</td></tr></table>
		</td></tr>
</table>
</td></tr>
<tr>
<td><span class="button_white" onClick='exportToPDF()' style="font-size:24px;color:red;"><i class="fa fa-file-pdf-o"></i></span><span style="font-size:24px;color:green;" class="button_white" onClick='getExcel()'><i class="fa fa-file-excel-o"></i></span></span><span class="button_white" style="font-size:24px;color:black;" onClick="javascript:printDiv('contentDiv')"><i class="fa fa-print"></i></span></span></td>
</tr>
</table>
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