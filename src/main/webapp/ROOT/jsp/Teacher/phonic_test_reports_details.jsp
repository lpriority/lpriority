<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html>
<head>
<script src="resources/javascript/LineChart/jszip.min.js"></script>
<script src="resources/javascript/LineChart/pako_deflate.min.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.material.min.css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.dataviz.min.css" />
<script src="resources/javascript/LineChart/kendo.all.min.js"></script>
<script type="text/javascript">
	 function getExcel(){
		    var assignmentId =  $("#titleId").val();
			 if(assignmentId){
					$.ajax({  
						type : "GET",
						url : "getAllStudentsAssessmentTest.htm", 
					    data: "assignmentId="+assignmentId,
					    success : function(response) {
							var title = window.parent.$('#titleId :selected').text();
							var assignedDate =  $('#assignedDate').val();
							var max = 0;
					    	var workbook = new kendo.ooxml.Workbook({
								 sheets: [{
								          columns: [
										        { width: 250 },
										        { width: 200 },
										        { width: 200 },
										        { width: 150 },
										        { width: 250 }
										      ],
										     	title: "Phonic Skill Test Report",
										     	sortable: true,
									            pageable: true,
									            groupable: true,
									            filterable: true,
									            columnMenu: true,
									            reorderable: true,
									            resizable: true,
									            mergedCells: [
									                          "A1:F1",
									                          "A2:C2",
									                          "D2:F2",
									                          "A3:G3",
									                      ],
										      rows: [
												  {
												    cells: [
												      { value: "Teacher Name   :    ${teacherName}", fontSize: 12 , bold: true,textAlign: "center"},
												      {},
												      {},
												      {}
												    ]
											     }, 
											     {
												    cells: [
												      { value: "Test Title  :    "+title, fontSize: 12, bold: true,textAlign: "center"},
												      {},
												      {},
												      { value: "Test Date   :    "+assignedDate, fontSize: 12 , bold: true,textAlign: "center"}
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
										            { value: "Student Id", fontSize: 12, bold: true},
										            { value: "Student Name" ,fontSize: 12, bold: true},
										            { value: "Test Status", fontSize: 12 , bold: true},
										            { value: "Graded Status", fontSize: 12 , bold: true},
										            { value: "Secured Marks / Total Marks", fontSize: 12, bold: true }
										          ]
										        },
										       
											<c:forEach items="${studentAssignmentStatusLt}" var="sas" varStatus="status">
											   {
											    cells:[
														{value: '${sas.student.studentId}'},
														{value: '${sas.student.userRegistration.firstName} ${sas.student.userRegistration.lastName}'},
														{value: '${sas.status}'},
														{value: '${sas.gradedStatus}'},
													  <c:set var='secMarks' value='0' scope='session' />			
													  <c:set var='maxMarks' value='0' scope='session' />	
												  <c:forEach items="${sas.studentPhonicTestMarksList}" var="spm" >
														  <c:set var='secMarks' value='${secMarks + spm.secMarks}' scope='session'/>
														  <c:set var='maxMarks' value='${maxMarks + spm.maxMarks}' scope='session'/>
												   </c:forEach>
												   <c:choose>
												    <c:when test="${maxMarks > 0}">
														{value: '${secMarks}/${maxMarks}'}
													</c:when>
													 <c:otherwise>
													 	{value: ''}
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
							    fileName: title+".xlsx"
							});
					    }
					}); 
				}
	}
function exportToPDF(){
	 var title=$('#titleId :selected').text();
     kendo.drawing.drawDOM($("#studentDetailsPage"))
     .then(function(group) {
         return kendo.drawing.exportPDF(group, {
             paperSize: "auto",
             margin: { left: "1cm", top: "1cm", right: "1cm", bottom: "1cm" }
         });
     })
     .done(function(data) {
         kendo.saveAs({
             dataURI: data,
             fileName: title
         });
     });
}
function printPage() {
	$("#loading-div-background").show();
 	$("#printMainDiv").print({
       globalStyles: false,
       mediaPrint: false,
       iframe: true,
       noPrintSelector: ".avoid-this"
  }); 
	$("#loading-div-background").hide();
} 
</script>
<title>Phonic Test Reports</title>
</head>
 <body>
 <input type="hidden" id="assignmentId" name="assignmentId" value="${assignmentId}" />
 <input type="hidden" id="langId" name="langId" value="${langId}" />
 <input type="hidden" id="bpstTypeId" name="bpstTypeId" value="${bpstTypeId}" />
  <table border=0 cellSpacing=0 cellPadding=0 width="100%"  valign="top" align=center height="100%" class="title-pad" id="printMainDiv">  
 						<tr valign="top">
								<td width="100%"><table class="des" border=0 width="100%"> <tr><td>
								<div class='Divheads' style='text-shadow:none;color:black;'>
								<table align='center' cellpadding='5' cellspacing='5'> 
									<tr>
										<th width='106' align='center'>Student Id</th>
										<th width='150' align='center'>Student Name</th>
										<th width='120' align='center'>Test Status</th>
										<th width='150' align='center'>Graded Status</th>
										<th width='120' align='center'>Secured Marks / Total Marks</th>
								</table>
								</div></td></tr><tr><td>
								<div class='DivContents'>
							 <table align=center height="100%"><tr><td>&nbsp;</td>
							 <c:forEach items="${studentAssignmentStatusLt}" var="sas"  varStatus="status">
								<tr><td  width='130' align='center' class='txtStyle'>${sas.student.studentId}</td>
								   <td width='150' align='center' class='txtStyle'>${sas.student.userRegistration.firstName} ${sas.student.userRegistration.lastName}</td>
								   <td width='150' align='center' class='txtStyle'>${sas.status}</td>
								   <td width='150' align='center' class='txtStyle'>${sas.gradedStatus}</td>
								   <c:choose>
									  <c:when test="${sas.gradedStatus eq 'graded' || sas.gradedStatus eq 'live graded'}">
									     <c:set var="totalMaxMarks" scope="session" value="0"/>
										 <c:set var="totalSecMarks" scope="session" value="0"/>
									    <c:forEach items="${sas.studentPhonicTestMarksList}" var="spm" >
									     	<c:set var="totalSecMarks" scope="session" value="${totalSecMarks+spm.secMarks}"/>
									     	<c:set var="totalMaxMarks" scope="session" value="${totalMaxMarks+spm.maxMarks}"/>
									   </c:forEach>
										    <c:set var="marksStr" scope="session" value="${totalSecMarks}/${totalMaxMarks}"/>
										    <c:set var="studentFullName" scope="session" value="${sas.student.userRegistration.firstName} ${sas.student.userRegistration.lastName}"/>
									     <td width='150' align='center' class='txtStyle'>
									     	<a href='#' onClick="getMarksInDetails(${status.index},${sas.studentAssignmentId},${sas.assignment.assignmentId},'${studentFullName}','${sas.assignment.dateAssigned}',${sas.student.studentId},${sas.student.userRegistration.regId},'${sas.student.userRegistration.user.userType}','${sas.gradedStatus}','${sas.assignment.title}')">${totalMaxMarks > 0 ? marksStr : ''}</a>
									     </td>
									  </c:when>
									  <c:otherwise>
									    <td width='150' align='center'></td>
									  </c:otherwise>
									</c:choose>
								   <td><div id='dialog${status.index}' style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display:none;" title=''></div></td><td height=25 colSpan=30>
							   	</tr>
							</c:forEach>
							</tr></table></div> 
							</td></tr>
			        	</table> 
	               </td></tr>
 		</table>
	</body>
</html>
