<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/Student/student_test_results.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/early_literacy.js"></script>
<script src="resources/javascript/TeacherJs/math_assessment.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/studentTestResultsService.js"></script>
<script type="text/javascript" src="/dwr/interface/earlyLiteracyTestsService.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/simptip/simptip.css" />

<style>
    .des{
    border: 1px solid black;
    border-collapse: collapse;
    border-color: #d3d3d3;
    }
hr{
  border-top: 1px solid #d3d3d3;
}
.ques{
   font-family: "Georgia";
   font-size : 16px;
   
 }
 .tit
 {
 font-family: "Georgia";
   font-size : 15px;
 }
 .answ{
   font-family: "Georgia";
   font-size : 16px;
   font-weight:bold;
 }
 
</style>



<script type="text/javascript">
	function getStudentTestResult(assignmentId,studentAssignmentId,assignmentTypeId,status,testCount){
		if(status == "pending"){
			alert("This test is not submitted yet");
			return;
		}
		var usedFor=$('#usedFor').val();
		$("#StuAssessQuestionsList").empty();
		$("#getCompletedTestQuestions").html("");
		var tab=$('#tab').val();
		if(assignmentTypeId == 13){
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",				
				url : "gradePerformanceTest.htm",
				data : "studentAssignmentId=" + studentAssignmentId + "&assignmentTypeId="+assignmentTypeId + "&tab="+tab,
				async: true,
				success : function(response) {
					var dailogContainer = $(document.getElementById('performanceDailog'));
					var screenWidth = $( window ).width() - 10;
					var screenHeight = $( window ).height() - 10;
					$('#performanceDailog').empty();  
					$(dailogContainer).append(response);
					$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,
						open:function () {
							$(".ui-dialog-titlebar-close").show();
						},
						close: function(event, ui){
							$(this).empty();
							getStudentsTestResults();
						},
						dialogClass: 'myTitleClass'
					});		
					$(dailogContainer).dialog("option", "title", "Performance Test Result");
					$(dailogContainer).scrollTop("0");
					$("#loading-div-background").hide();
				}
			});
		}else{
			$("#loading-div-background").show();
			$.ajax({
				type : "GET",	
				url : "getCompletedTestQuestions.htm",
				data : "studentAssignmentId=" + studentAssignmentId + "&usedFor="+usedFor +"&assignmentTypeId="+assignmentTypeId+"&assignmentId="+assignmentId+"&tab="+ tab,
				success : function(response) {
					var dailogContainer = $(document.getElementById('getCompletedTestQuestions'));
						var screenWidth = $(window).width() - 10;
						var screenHeight = $(window).height() - 10;
						$('#getCompletedTestQuestions').empty();  
						$(dailogContainer).append(response);
						$(dailogContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
							  $(".ui-dialog-titlebar-close").show();
						},
						dialogClass: 'myTitleClass'
						});	
						if(assignmentTypeId==14){
							$(dailogContainer).dialog("option", "title", "Spelling");
						}else{
						$(dailogContainer).dialog("option", "title", "Test Results");}
						$(dailogContainer).scrollTop("0");	
						$("#loading-div-background").hide();
			
				}
			});
		}		
	}
	
	function clearDiv(){
		$('#studentsTestResultsDiv').empty();  
		$('#getCompletedTestQuestions').empty();  
	}
	function goToDiv(i){
		  document.getElementById("sho"+i).scrollIntoView(true);
	}

</script>
<title>Student Test Results</title>
</head>
<body>
		<table align="center" style="width:100%" >
				<tr>
					<td style="" colspan="5" align="center" valign="top">
					<table style="width:90%" align="right">
					        <tr><td style="width: 100%;float: right;" align="right"  valign="bottom">
									<div>
										<c:choose>
											<c:when test="${usedFor == 'rti'}">
												<%@ include file="view_rti_tabs.jsp"%>
											</c:when>
											<c:when test="${usedFor == 'homeworks'}">
												<%@ include file="view_homework_tabs.jsp"%>
											</c:when>
											<c:otherwise>
												<%@ include file="view_assessment_tabs.jsp"%>
											</c:otherwise>
										</c:choose>

									</div>
								</td>
							</tr>	
							<tr>
								<td style="width:100%" align="right">
									<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
									<input type="hidden" id="tab" name="tab" value="${tab}" /></td>
							</tr></table></td></tr>
							
							</table>
							
							<table width="99.8%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
							<tr>
							  		  <c:choose>
											<c:when test="${usedFor == 'rti'}">
							  			<td class="sub-title title-border" height="40" align="left" colspan="3">Literacy Development Test Results<br> </td>
							  			</c:when>
											<c:otherwise>
											<td colspan="3" class="sub-title title-border" height="40" align="left">Assessments completed<br> </td>
											</c:otherwise>
								      </c:choose>
							  		</tr>
							</table>
							<table align="center" style="width:100%" >
				           <tr>
				             <td align='left' width='50'>&nbsp;</td>
				              <td style="" colspan="5" align="left" valign="top">
				              <table align="left" style="width:80%">
				               <tr><td>&nbsp;</td></tr>
							<tr>
							  <td height="30" colspan="2" align="left" style="width:100%">
							  <table width="100%" border="0" cellpadding="0" cellspacing="0">
													  		
                                    <tr> 
                                        <td width="60" align="left" valign="middle" >&nbsp;</td>                                                                  
                                        <td width="50" align="left" valign="middle" class="label">&nbsp;&nbsp;Grade&nbsp;&nbsp;&nbsp;</td>
                                        <td><input type="text" style="width:100px;" name="gradeName" id="gradeName" value ="${grade.masterGrades.gradeName}" disabled /></td>
                                        <td align="left" valign="middle" style="padding-right: 8cm;" class="label">
                                        	<label>Class</label>
                                            <select	id="classId" class="listextmenu" name="classId" onchange="clearDiv();getStudentsTestResults();" style="width:120px;">
												<option value="select">select class</option>
										 		<c:forEach items="${GradeClasses}" var="ul">
													<option value="${ul.gradeClasses.studentClass.classId}">${ul.gradeClasses.studentClass.className}</option>
												</c:forEach> 
											</select>
                                            </td>
                                            <td width="75" align="left" valign="middle" class="">&nbsp;</td>   
                                            <td width="75" align="left" valign="middle" class="">&nbsp;</td>   
                                             <td width="75" align="left" valign="middle" class="">&nbsp;</td>   
                                    </tr>
                                </table>
                                </td>
							</tr>
							 <tr><td>&nbsp;</td></tr></table>
							 <table width=100% align=left>
							<tr>
							<tr>
							<td align="left" style="padding-left: 7em;"><div id="studentsTestResultsDiv" style="align:center;"></div></td></tr></table>
							 <table width=100%>
				             <tr>
				             <td align="left" style="padding-left: 10em;">
				             <div id="getCompletedTestQuestions" title="Test Results" style="align:center;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td></tr>
				             </table>
					
					</td>
				</tr>
			</table>
</body>
</html>
