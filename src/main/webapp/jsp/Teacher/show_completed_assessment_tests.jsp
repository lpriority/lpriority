<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />

<html>
<link rel="stylesheet" href="resources/css/LineChart/kendo.material.min.css" />
<link rel="stylesheet" href="resources/css/LineChart/kendo.dataviz.min.css" />
<script src="resources/javascript/LineChart/jszip.min.js"></script>
<script src="resources/javascript/LineChart/kendo.all.min.js"></script>
<script src="resources/javascript/LineChart/pako_deflate.min.js"></script>
<script src="resources/javascript/common/jQuery.print.min.js"></script>


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
function getExcel(){
	console.log("hello");
	var msgArray = [];
    var q1="";
    var q2="";
    var q3="";
    var qnumber = 0;
	<c:if test="${studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId eq 4}">   
		var workbook = new kendo.ooxml.Workbook({
			//var col1 = msgArray[0];
		//console.log("col 1 is "+q1);
			<c:set var="row" value="0" />
			 sheets: [{
				 	freezePane: {
				        rowSplit: 7
				      },
			          columns: [
					        { width: 150 },
					        { width: 80 },
					        { width: 350 },
					        { width: 350 },
					        { width: 350 },
					        { width: 350 },
					        { width: 350 },
					        { width: 350 },
					        { width: 350 },
					        { width: 350 },
					      ],
					     	title: "Math Quiz Results",
					     	sortable: true,
				            pageable: true,
				            groupable: true,
				            filterable: true,
				            columnMenu: true,
				            reorderable: true,
				            resizable: true,
				            mergedCells: [
				                          "A1:E1",
				                          "A2:E2",
				                          "A3:E3",
				                          "A4:E4",
				                          "A5:E5",
				                          "A6:E6",
				                      ],
				                      
					      rows: [
							  {
							    cells: [
							      { value: "Math Quiz Team Results", fontSize: 12, bold: true,textAlign: "center"},
							      {},
							      {},
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
							      {value: "${userReg.school.schoolName} - ${studentQuestionTestList[0].grade.masterGrades.gradeName} Grade", fontSize: 12, bold: true,textAlign: "center"},
							    ]
							 },
							 {
							    cells: [
							      {value: "Math Quiz", fontSize: 12, bold: true,textAlign: "center"},
							    ]
							 },
							 {
							    cells: [
							      {value: new Date(), format: "dd-MM-yyyy", fontSize: 12, bold: true,textAlign: "center"},
							    ]
							 },
							 {
							    cells: [
							    	{value: "${userReg.firstName} ${userReg.lastName }", fontSize: 12, bold: true,textAlign: "center"},
							    ]
							 },
						     {
					          cells: [
					            { value: "Student Name",fontSize: 12, bold: true},
					            { value: "Percentage",fontSize: 12, bold: true},
					            <c:forEach items="${studentQuestionTestList}" var="quest">
				            		<c:set var="numberOfRows" value="${numberOfRows+1}"/>
				            		
				            			<c:set var="qtext" value="${quest.question}" scope="page"/>
											<c:set var="singlequote" value="'"/>
										    <c:set var="backslash" value="\\"/>
										    <c:if test="${fn:contains(qtext, singlequote) && !fn:contains(qtext,backslash)}">
										            <c:set var="search" value="'" />
										            <c:set var="replace" value="\\'" />
										            <c:set var="qtext" value="${fn:replace(qtext, search, replace)}"/>
										    </c:if>
										    <c:if test="${fn:contains(qtext, backslash) && !fn:contains(qtext,singlequote)}">
										            <c:set var="search" value="\\" />
										            <c:set var="replace" value="\\\\" />
										            <c:set var="qtext" value="${fn:replace(qtext, search, replace)}"/>
										    </c:if>
										    <c:if test="${fn:contains(qtext, singlequote) && fn:contains(qtext,backslash)}">
										            <c:set var="search" value="\\"/>
										            <c:set var="replace" value="\\\\" />
										            <c:set var="qtext" value="${fn:replace(qtext, search, replace)}"/>
										            <c:set var="find" value="'"/>
										            <c:set var="change" value="\\'" />
										            <c:set var="qtext" value="${fn:replace(qtext, find, change)}"/>
										    </c:if>
		            		
					            	{value: '${numberOfRows}. ${qtext}',fontSize: 12, bold: true},
						        </c:forEach>
					          ]
					        },
					        <c:set var="count" value="0" scope="request" />
					        <c:set var="rowcorrect" value="0" scope="request" />
					        <c:set var="rowwrong" value="0" scope="request" />
					        <c:set var="col1totcorrect" value="0" scope="page" />
					        <c:set var="col2totcorrect" value="0" scope="page" />
					        <c:set var="col3totcorrect" value="0" scope="page" />
					        <c:set var="col4totcorrect" value="0" scope="page" />
					        <c:set var="col5totcorrect" value="0" scope="page" />
					        <c:set var="col6totcorrect" value="0" scope="page" />
					        <c:set var="col7totcorrect" value="0" scope="page" />
					        <c:set var="col8totcorrect" value="0" scope="page" />
					       	<c:set var="q1correct" value="0" scope="page" />
						   	<c:set var="q2correct" value="0" scope="page" />
						   	<c:set var="q3correct" value="0" scope="page" />
						   	<c:set var="q4correct" value="0" scope="page" />
						   	<c:set var="q5correct" value="0" scope="page" />
						   	<c:set var="q6correct" value="0" scope="page" />
						   	<c:set var="q7correct" value="0" scope="page" />
						   	<c:set var="q8correct" value="0" scope="page" />
					        		
						<c:forEach items="${studentAssessmentTestList}" var="al" varStatus="status">
						   {
							   <c:set var="anscount" value="0" scope="page" />
							   <c:set var="correct" value="0" scope="page" />
							   <c:set var="wrong" value="0" scope="page" />
							   <c:set var="loopcount" value="0" scope="page" />
							  
							   
						    cells:[
									{value: '${al.student.userRegistration.firstName} ${al.student.userRegistration.lastName}'},
									{value: '${al.percentage}'},
									<c:forEach var="i" begin="${count}" end="${count + 7}" >
									<c:set var="studAns" value="${allStudentAssignmentQuestions[count].answer}" scope="page"/>
									<c:set var="actualAns" value="${studentQuestionTestList[anscount].answer}" scope="page"/>

											<c:set var="singlequote" value="'"/>
										    <c:set var="backslash" value="\\"/>
										    <c:if test="${fn:contains(studAns, singlequote) && !fn:contains(studAns,backslash)}">
										            <c:set var="search" value="'" />
										            <c:set var="replace" value="\\'" />
										            <c:set var="studAns" value="${fn:replace(studAns, search, replace)}"/>
										    </c:if>
										    
										    <c:if test="${fn:contains(actualAns, singlequote) && !fn:contains(actualAns,backslash)}">
										            <c:set var="search" value="'" />
										            <c:set var="replace" value="\\'" />
										            <c:set var="actualAns" value="${fn:replace(actualAns, search, replace)}"/>
								    		</c:if>
										    
										    <c:if test="${fn:contains(studAns, backslash) && !fn:contains(studAns,singlequote)}">
										            <c:set var="search" value="\\" />
										            <c:set var="replace" value="\\\\" />
										            <c:set var="studAns" value="${fn:replace(studAns, search, replace)}"/>
										    </c:if>
										    
										    <c:if test="${fn:contains(actualAns, backslash) && !fn:contains(actualAns,singlequote)}">
										            <c:set var="search" value="\\" />
										            <c:set var="replace" value="\\\\" />
										            <c:set var="actualAns" value="${fn:replace(actualAns, search, replace)}"/>
								    		</c:if>
										    
										    <c:if test="${fn:contains(studAns, singlequote) && fn:contains(studAns,backslash)}">
										            <c:set var="search" value="\\"/>
										            <c:set var="replace" value="\\\\" />
										            <c:set var="studAns" value="${fn:replace(studAns, search, replace)}"/>
										            <c:set var="find" value="'"/>
										            <c:set var="change" value="\\'" />
										            <c:set var="studAns" value="${fn:replace(studAns, find, change)}"/>
										    </c:if>
										    
										    <c:if test="${fn:contains(actualAns, singlequote) && fn:contains(actualAns,backslash)}">
										            <c:set var="search" value="\\"/>
										            <c:set var="replace" value="\\\\" />
										            <c:set var="actualAns" value="${fn:replace(actualAns, search, replace)}"/>
										            <c:set var="find" value="'"/>
										            <c:set var="change" value="\\'" />
										            <c:set var="actualAns" value="${fn:replace(actualAns, find, change)}"/>
								    		</c:if>
										    
										    <c:choose>
										    	<c:when test="${actualAns eq studAns}">
									    			<c:set var="correct" value="${correct + 1}" scope="page"/>
									    			<c:if test="${loopcount == '0'}">
									    				<c:set var="q1correct" value="${col1totcorrect + 1}" scope="page"/>
									    			</c:if>
									    			
									    			<c:if test="${loopcount eq '1'}">
									    				<c:set var="q2correct" value="${col2totcorrect + 1}" scope="page"/>
									    			</c:if>
									    			<c:if test="${loopcount eq '2'}">
									    				<c:set var="q3correct" value="${col3totcorrect + 1}" scope="page"/>
									    			</c:if>
									    			<c:if test="${loopcount eq '3'}">
									    				<c:set var="q4correct" value="${col4totcorrect + 1}" scope="page"/>
									    			</c:if>
									    			<c:if test="${loopcount eq '4'}">
									    				<c:set var="q5correct" value="${col5totcorrect + 1}" scope="page"/>
									    			</c:if>
									    			<c:if test="${loopcount eq '5'}">
									    				<c:set var="q6correct" value="${col6totcorrect + 1}" scope="page"/>
									    			</c:if>
									    			<c:if test="${loopcount eq '6'}">
									    				<c:set var="q7correct" value="${col7totcorrect + 1}" scope="page"/>
									    			</c:if>
									    			<c:if test="${loopcount eq '7'}">
									    				<c:set var="q8correct" value="${col8totcorrect + 1}" scope="page"/>
									    			</c:if>
									    			
									    			{value: value="Ö",fontSize: 14, bold: true,fontFamily: "Symbol",color: "#008000",textAlign: "center"},
										    	</c:when>

										    	<c:otherwise>
										    		<c:set var="wrong" value="${wrong + 1}" scope="page"/>
									    			{value: "X",fontSize: 14, bold: true,color: "#FF0000",textAlign: "center"},
										    	</c:otherwise>
											</c:choose>
											
											
									<c:set var="count" value="${count + 1}" scope="request"/>
									<c:set var="anscount" value="${anscount + 1}" scope="page"/>
									<c:set var="loopcount" value="${loopcount + 1}" scope="page"/>
									
									<c:set var="col1totcorrect" value="${q1correct}" scope="page"/>
									<c:set var="col2totcorrect" value="${q2correct}" scope="page"/>
									<c:set var="col3totcorrect" value="${q3correct}" scope="page"/>
									<c:set var="col4totcorrect" value="${q4correct}" scope="page"/>
									<c:set var="col5totcorrect" value="${q5correct}" scope="page"/>
									<c:set var="col6totcorrect" value="${q6correct}" scope="page"/>
									<c:set var="col7totcorrect" value="${q7correct}" scope="page"/>
									<c:set var="col8totcorrect" value="${q8correct}" scope="page"/>
									
									</c:forEach>
									
								 ],
								 <c:set var="row" value="${count}" />
								<c:set var="col1totcorrect" value="${col1totcorrect}" scope="page"/>
							     <c:set var="rowcorrect" value="${col1totcorrect}" />
							     <c:set var="rowwrong" value="${loopcount}" />
					         },
						</c:forEach>
					         {
					        	 cells: [
								      {},
								      {},
								      {}
								    ],
								    cells: [
								    	   {value: "Class Total",fontSize: 14, bold: true,color: "#000000",background: "#149251"},
								    	   {value: "",fontSize: 14, bold: true,color: "#000000",background: "#149251"},
							               { value: "${col1totcorrect}/${fn:length(studentAssessmentTestList)}",fontSize: 14, bold: true,textAlign: "center",color: "#000000",background: "#149251"},
							               { value: "${col2totcorrect}/${fn:length(studentAssessmentTestList)}",fontSize: 14, bold: true,textAlign: "center",color: "#000000",background: "#149251"},
							               { value: "${col3totcorrect}/${fn:length(studentAssessmentTestList)}",fontSize: 14, bold: true,textAlign: "center",color: "#000000",background: "#149251"},
							               { value: "${col4totcorrect}/${fn:length(studentAssessmentTestList)}",fontSize: 14, bold: true,textAlign: "center",color: "#000000",background: "#149251"},
							               { value: "${col5totcorrect}/${fn:length(studentAssessmentTestList)}",fontSize: 14, bold: true,textAlign: "center",color: "#000000",background: "#149251"},
							               { value: "${col6totcorrect}/${fn:length(studentAssessmentTestList)}",fontSize: 14, bold: true,textAlign: "center",color: "#000000",background: "#149251"},
							               { value: "${col7totcorrect}/${fn:length(studentAssessmentTestList)}",fontSize: 14, bold: true,textAlign: "center",color: "#000000",background: "#149251"},
							               { value: "${col8totcorrect}/${fn:length(studentAssessmentTestList)}",fontSize: 14, bold: true,textAlign: "center",color: "#000000",background: "#149251"},
				            	],
					         }
					         
			            ]
			          }
			        ]
		});
		</c:if>
		kendo.saveAs({
		    dataURI: workbook.toDataURL(),
		    fileName: "FillInBlank.xlsx"
		});
		}


</script>
<style>
	body{
	  -webkit-print-color-adjust:exact;
	}
</style>
<body>
<c:if test="${fn:length(studentAssessmentTestList) > 0}">
	<table width="100%" align="right">
		<tr>
			<td align="right"><c:if
					test="${userReg.user.userType eq 'teacher' && 
							(studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId eq 19 || studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId eq 3)}">
					<div class="button_green">
						<!-- onclick="window.location.href='itemAnalysisReport.htm'"> -->
						<a href="gradeBook.htm?page=itemAnalysisReport" onclick="gotoItenAnalysisReport()">
						Item Analysis Report</div>
				</c:if></td>
		</tr>
	</table>
</c:if>
<table>
	<tr>
		<td><c:set var="bor" value="0"></c:set> <c:set var="de" value=""></c:set>
			<c:if test="${fn:length(studentAssessmentTestList) > 0}">
				<c:set var="bor" value="0"></c:set>
				<c:set var="de" value="des"></c:set>
			</c:if></td>
	</tr>
</table>
<table border="${bor}" class="${de}" align="center">
	<tr>
		<td><c:if test="${fn:length(studentAssessmentTestList) > 0}">
				<div class='Divheads' align="center">


					<table style="font-size: 14px;">
						<tr>
							<td width='51' height='25' align='center' valign='middle'
								class='titleHead'><b>Select</b></td>
							<td width='111' align='center' valign='middle' class='titleHead'><b>Submitted</b>
							</td>
							<c:choose>
								<c:when test="${tab ne 'gradeGroup'}">
									<td width='120' align='center' valign='middle'
										class='titleHead'><b>Student Id </b></td>
									<td width='151' align='left' valign='middle' class='titleHead'><b>Student
											Name</b></td>
								</c:when>
								<c:otherwise>
									<td width='111' align='center' valign='middle'
										class='titleHead'><b>Group Id</b></td>
									<td width='111' align='center' valign='middle'
										class='titleHead'><b>Group Name</b></td>
								</c:otherwise>
							</c:choose>
							<c:if
								test="${studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId ne 19 and studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId ne 20 and studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId ne 8}">

						<!--  This three column commented by rajendra to fix some issue -->								
							<!--  <td width='111' align='center' valign='middle' class='titleHead'><b>Unit
									No</b></td>
							<td width='111' align='center' valign='middle' class='titleHead'>
								<b>Lesson No </b>
							</td>
							<td width='111' align='center' valign='middle' class='titleHead'><b>Lesson
									Name </b></td> -->
							</c:if>
							<td width='140' align='center' valign='middle' class='titleHead'><b>Title
							</b></td>
							<td width='141' align='center' valign='middle' class='titleHead'><b>Assignment
									Type</b></td>
							<td width='111' align='center' valign='middle' class='titleHead'><b>Graded</b>
							</td>
							<c:if
								test="${studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId eq 4}">
								<td width='140' align='center' valign='middle' class='titleHead'><b>Percentage</b>
								</td>
							</c:if>	
							<c:if
								test="${studentAssessmentTestList[0].assignment.usedFor ne 'rti' }">
								<td width='111' align='center' valign='middle' class='titleHead'><b>Score</b>
								</td>
								<td width='151' align='center' valign='middle' class='titleHead'><b>Academic
										Grade</b></td>
							</c:if>
							<c:if
								test="${studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId eq 19}">
								<td width='111' align='center' valign='middle' class='titleHead'><b>Score</b>
								</td>
								<td width='151' align='center' valign='middle' class='titleHead'><b>Academic
										Grade</b></td>
							</c:if>
						</tr>
					</table>
				</div>

				<div class='DivContents'>
					<table>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<c:set var="testCount" value="0"></c:set>
						<c:set var="lessonId" value="0"></c:set>
						<c:choose>
							<c:when
								test="${studentAssessmentTestList[0].assignment.assignmentType.assignmentType == 'Reading Fluency Learning Practice'  }">
								<c:forEach items="${rflpTests}" var="al">
									<c:if
										test="${not empty al.studentAssignmentStatus.assignment.lesson.lessonId}">
										<c:set var="lessonId"
											value="${al.studentAssignmentStatus.assignment.lesson.lessonId}"></c:set>
									</c:if>
									<tr>
										<td width='51' height='25' align='center' valign='middle'
											class=''><input type="radio" class="radio-design"
											name="checkbox2" id="studentAssignmentId:${testCount}"
											onClick="getStudentTestQuestions(${al.studentAssignmentStatus.assignment.assignmentId},${al.studentAssignmentStatus.studentAssignmentId},${al.studentAssignmentStatus.assignment.assignmentType.assignmentTypeId},'${al.studentAssignmentStatus.status}',${testCount},${lessonId},${al.studentAssignmentStatus.student.studentId})" />
											<label for="studentAssignmentId:${testCount}"
											class="radio-label-design"></label></td>
										<td width='111' align='center' valign='middle'
											class='txtStyle'><c:choose>
												<c:when
													test="${al.dateDue >= al.studentAssignmentStatus.submitdate}">
													<font color='blue'> Yes</font>
												</c:when>
												<c:when
													test="${al.dateDue < al.studentAssignmentStatus.submitdate}">
													<font color='red'> LateSubmission</font>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose> <c:out value=""></c:out></td>
										<td width='120' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.studentAssignmentStatus.student.userRegistration.regId}"></c:out></td>
										<td width='151' align='left' valign='middle' class='txtStyle'><c:out
												value="${al.studentAssignmentStatus.student.userRegistration.firstName} ${al.studentAssignmentStatus.student.userRegistration.lastName}"></c:out></td>
										<c:if test="${studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId ne 19 and studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId ne 20 and studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId ne 8}">
										<!--  <td width='111' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${not empty al.studentAssignmentStatus.assignment.lesson.unit.unitNo ? al.studentAssignmentStatus.assignment.lesson.unit.unitNo: 'N/A'}"></c:out></td>
										<td width='111' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${not empty al.studentAssignmentStatus.assignment.lesson.lessonNo? al.studentAssignmentStatus.assignment.lesson.lessonNo: 'N/A'}"></c:out></td>
										<td width='111' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${not empty al.studentAssignmentStatus.assignment.lesson.lessonName? al.studentAssignmentStatus.assignment.lesson.lessonName: 'N/A'}"></c:out></td> -->
									   </c:if>
										<td width='140' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.studentAssignmentStatus.assignment.title}"></c:out></td>
										<td width='141' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.studentAssignmentStatus.assignment.assignmentType.assignmentType}"></c:out></td>
										<td width='111' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.studentAssignmentStatus.gradedStatus}"></c:out></td>
										<c:if
											test="${studentAssessmentTestList[0].assignment.usedFor ne 'rti' }">
											<td width='111' align='center' valign='middle'
												class='txtStyle'><fmt:formatNumber
													var="formattedPercentage" type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${al.studentAssignmentStatus.percentage}" /> <c:out
													value="${formattedPercentage}"></c:out></td>
											<td width='111' align='center' valign='middle'
												class='txtStyle'><c:choose>
													<c:when test="${al.studentAssignmentStatus.academicGrade.acedamicGradeName=='I'} ||${al.studentAssignmentStatus.academicGrade.acedamicGradeName== 'N'} || ${al.studentAssignmentStatus.academicGrade.acedamicGradeName=='P'}">
	                          							F
	                        						</c:when>
													<c:otherwise>
														<c:out
															value="${not empty al.studentAssignmentStatus.academicGrade.acedamicGradeName? al.studentAssignmentStatus.academicGrade.acedamicGradeName: academicNames[al.studentAssignmentStatus.studentAssignmentId]}"></c:out>
													</c:otherwise>
												</c:choose></td>
										</c:if>
										<c:if test="$studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId eq 4}">
										<td width='140' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.percentage}"></c:out></td>
										</c:if>		
									</tr>
									<c:set var="testCount" scope="page" value="${testCount + 1}">
									</c:set>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${studentAssessmentTestList}" var="al">
									<c:if test="${not empty al.assignment.lesson.lessonId}">
										<c:set var="lessonId" value="${al.assignment.lesson.lessonId}"></c:set>
									</c:if>
									<tr>
										<td width='51' height='25' align='center' valign='middle'
											class=''><c:choose>
												<c:when test="${tab ne 'gradeGroup'}">
													<input type="radio" class="radio-design" name="checkbox2"
														id="studentAssignmentId:${testCount}"
														onClick="getStudentTestQuestions(${al.assignment.assignmentId},${al.studentAssignmentId},${al.assignment.assignmentType.assignmentTypeId},'${al.status}',${testCount},${lessonId},${al.student.studentId})" />
													<label for="studentAssignmentId:${testCount}"
														class="radio-label-design"></label>
												</c:when>
												<c:otherwise>
													<input type="radio" class="radio-design" name="checkbox2"
														id="studentAssignmentId:${testCount}"
														onClick="getStudentTestQuestions(${al.assignment.assignmentId},${al.studentAssignmentId},${al.assignment.assignmentType.assignmentTypeId},'${al.status}',${testCount},${lessonId},0)" />
													<label for="studentAssignmentId:${testCount}"
														class="radio-label-design"></label>
												</c:otherwise>
											</c:choose></td>
										<td width='111' align='center' valign='middle'
											class='txtStyle'><c:choose>
												<c:when test="${al.assignment.dateDue >= al.submitdate}">
													<font color='blue'> Yes</font>
												</c:when>
												<c:when test="${al.assignment.dateDue < al.submitdate}">
													<font color='red'> LateSubmission</font>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose> <c:out value=""></c:out></td>

										<c:choose>
											<c:when test="${tab ne 'gradeGroup'}">
												<td width='120' align='center' valign='middle'
													class='txtStyle'><c:out
														value="${al.student.userRegistration.regId}"></c:out></td>
												<td width='151' align='left' valign='middle'
													class='txtStyle'><c:out
														value="${al.student.userRegistration.firstName} ${al.student.userRegistration.lastName}"></c:out></td>
											</c:when>
											<c:otherwise>
												<td width='111' align='center' valign='middle'
													class='txtStyle'><c:out
														value="${al.performanceGroup.performanceGroupId}"></c:out></td>
												<td width='111' align='center' valign='middle'
													class='txtStyle'><c:out
														value="${al.performanceGroup.groupName}"></c:out></td>
											</c:otherwise>
										</c:choose>
										<c:if test="${studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId ne 19 and studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId ne 20 and studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId ne 8}">
										<!--  <td width='111' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.assignment.lesson.unit.unitNo}"></c:out></td>
										<td width='111' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.assignment.lesson.lessonNo}"></c:out></td>
										<td width='111' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.assignment.lesson.lessonName}"></c:out></td>-->
										</c:if>
										<td width='140' align='center' valign='middle'
											class='txtStyle'><c:out value="${al.assignment.title}"></c:out></td>
										<td width='141' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.assignment.assignmentType.assignmentType}"></c:out></td>
										<td width='111' align='center' valign='middle'
											class='txtStyle'><c:out value="${al.gradedStatus}"></c:out></td>
										<c:if
											test="${studentAssessmentTestList[0].assignment.usedFor ne 'rti' }">
											<td width='111' align='center' valign='middle'
												class='txtStyle'><fmt:formatNumber
													var="formattedPercentage" type="number"
													minFractionDigits="2" maxFractionDigits="2"
													value="${al.percentage}" /> <c:out
													value="${formattedPercentage}"></c:out></td>
											<td width='111' align='center' valign='middle'
												class='txtStyle'><c:choose>
													<c:when
														test="${al.academicGrade.acedamicGradeName=='I'} ||${al.academicGrade.acedamicGradeName== 'N'} || ${al.academicGrade.acedamicGradeName=='P'}">
	                          F
	                        </c:when>
													<c:otherwise>
														<c:out value="${al.academicGrade.acedamicGradeName}"></c:out>
													</c:otherwise>
												</c:choose></td>
										</c:if>
										<c:if
											test="${al.assignment.assignmentType.assignmentTypeId eq 19}">
											<td width='111' align='center' valign='middle'
												class='txtStyle'>${al.percentage}</td>
											<td width='151' align='center' valign='middle'
												class='txtStyle'>${al.academicGrade.acedamicGradeName}</td>
										</c:if>
										<c:if test="${studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId eq 4}">
										<td width='140' align='center' valign='middle'
											class='txtStyle'><c:out
												value="${al.percentage}"></c:out></td>
										</c:if>		
									</tr>
									<c:set var="testCount" scope="page" value="${testCount + 1}">
									</c:set>
								</c:forEach>
							</c:otherwise>
						</c:choose>

					</table>

			<c:if test="${studentAssessmentTestList[0].assignment.assignmentType.assignmentTypeId eq 4}">					
					<table>
				 		<tr>
							<td><span class="button_white" onClick='exportToPDF()' style="font-size:24px;color:red;"><i class="fa fa-file-pdf-o"></i></span>
								<span style="font-size:24px;color:green;" class="button_white" onClick='getExcel()'><i class="fa fa-file-excel-o"></i></span>
								<span class="button_white" style="font-size:24px;color:black;" onClick="javascript:printThisDivContents('taskResultDiv')"><i class="fa fa-print"></i></span></span></td>
						</tr>
		 			</table>					
			</c:if>		
				</div>
			</c:if>
			<table width='100%' height='25' border='0' cellpadding='0'
				cellspacing='0'>
				<tr>
					<td align='center' valign='middle'></td>
					<c:if test="${fn:length(studentAssessmentTestList) <= 0}">

						<td align='center' valign='middle' class="status-message">No
							Student Reports</td>
					</c:if>
					<td align='center' valign='middle'></td>
				</tr>
			</table>
			<div id="performanceDailog"
				style="background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center; align: center"></div></td>
	</tr>
</table>
</body>
</html>