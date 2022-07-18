<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>View Group Assignments</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/voice_recorder.js"></script>
<script src="resources/javascript/VoiceRecorder/recorder.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function () {
		 $('#returnMessage').fadeOut(3000);
	});
	function getTestQuestions(studentAssignmentId, testCount) {
		var usedFor = $("#usedFor"+testCount).val();
		var assignmentTypeId = $("#assignmentTypeId"+testCount).val();
		var createdBy = $("#createdBy"+testCount).val();
		var tab = $("#tab").val();
		$("#testQuestionsDiv").empty();		
		if(assignmentTypeId ==13){
			/* var screenWidth = $( window ).width() - 10;
			var screenHeight = $( window ).height() - 10;
    			 jQuery.curCSS = jQuery.css;
    			 $("#performanceDailog").dialog({
    				    overflow:'auto',
    					dialogClass: 'no-close',
    				    autoOpen: false,
    				    draggable: true,
    				    resizable : true,
    				    width: screenWidth, 
    				    height: screenHeight,
    				    title: 'Group Performance Test',
    				    modal: true,
    				    open: function() {},
    				    close : function(){}  
    				});
			 var iframe = $("#iframe");
			 iframe.attr('src', "getGroupPerformanceTest.htm?studentAssignmentId=" + studentAssignmentId.value + "&assignmentTypeId="+assignmentTypeId + "&createdBy="+createdBy);
		   	$("#performanceDailog").dialog("open");
		   	$("#performanceDailog").scrollTop("0");
		   	 */
		   	
			$.ajax({
				type : "GET",				
				url : "getGroupPerformanceTest.htm",
				data : "studentAssignmentId=" + studentAssignmentId.value + "&assignmentTypeId="+assignmentTypeId + "&createdBy="+createdBy,
				async: true,
				success : function(response) {
					var screenWidth = $( window ).width() - 10;
					var screenHeight = $( window ).height() - 10;
					$('#performanceDailog').empty();  
					$("#performanceDailog").html(response);
					$("#performanceDailog").dialog({width: screenWidth, height: screenHeight,modal: true,
						open:function () {
							$(".ui-dialog-titlebar-close").show();
						},
						close: function(event, ui){
							$(this).empty();  
					    }
					});		
					$("#performanceDailog").dialog("option", "title", "Group Performance Test");
					$("#performanceDailog").scrollTop("0");
					 $("#loading-div-background").hide();
				}
			});
		}
	}
</script>
</head>
<body style="overflow: scroll;">
	
		<table style="width: 100%">
			<tr>
				<td style="" align="right"><input type="hidden" id="tab" name="tab" value="${tab}" /></td>
				<td style="width: 100%" align="right" valign="bottom">
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
		</table>
		<table width="99%" border="0" cellspacing="0" cellpadding="0" class="title-pad" style="padding-top: 1em">
							<tr>
							  		 
							  			<td class="sub-title title-border" height="40" align="left" colspan="3">Group Assessments<br> </td>
							  			
											
											
							  		</tr>
							</table>
		<div align="center" style="vertical-align: middle;" id="dailog">
			<table style="width: 80%">
				<tr>
					<td style="width: 100%" colspan="5" align="center" valign="top">
						<table style="width: 100%">
							<tr>
								<td style="width: 100%"align="left"><table style="">
										<tr>
											<td class="sub-title">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td style="" align="left" valign="middle">
												<div align="center"
													style="text-decoration: blink; color: red"
													id="alertMessage"></div>
											</td>
										</tr>
									</table></td>
							</tr></table><c:set var="bor" value="0"></c:set>
							<c:set var="de" value=""></c:set>
						<c:if test="${fn:length(studentTests) > 0}">
						<c:set var="de" value="des"></c:set>
						<c:set var="bor" value="0"></c:set>
						</c:if></td></tr></table>
							
                        <table border="${bor}" class="${de}" style="padding-bottom:3em;">
							<tr>
								<td style="" colspan="2" align="center" valign="top">
								<c:if test="${fn:length(studentTests) > 0}">
								<div class="Divheads">
									<table>
									
								
									
										<tr>
											<th width='51'  height='25' align='center' valign='middle'>
												Select</th>
											 <th width='140'  align='center' valign='middle'>Class
											</th>
											 <th width='141'  align='center' valign='middle'>AssignmentTitle
											</th>
											<th width='132' align='center'>Due
												Date</th>
											<th width='132' align='center'>Instructions</th>
											<th width='132' align='center'>Test
												Type</th>
											
										</tr>
										</table></div>
										 <div style="padding: 2px 5px 2px 5px"><table>
										 <tr><td height=10 colSpan=30></td></tr> 
										<c:set var="testCount" scope="page" value="0">
										</c:set>

										<c:set var="testCount" value="0"></c:set>
										<c:forEach items="${studentTests}" var="studTests">
											<tr>
												<td width="51" height="25" align="center" class="txtStyle"><input
													type="radio" name="checkbox2"
													id="studentAssignmentId:${testCount}"
													value="${studTests.studentAssignmentId}"
													onClick="getTestQuestions(this, ${testCount})" class="radio-design" /> 
													<label for="studentAssignmentId:${testCount}" class="radio-label-design"></label>
												</td>
												<td width="140" height="0" align="center" class="txtStyle"><c:out
														value="${studTests.assignment.classStatus.section.gradeClasses.studentClass.className}"></c:out></td>
												<td width="141" height="0" align="center" class="txtStyle"><c:out
														value="${studTests.assignment.title}"></c:out></td>
												<td width="132" height="0" align="center" class="txtStyle"><c:out
														value="${studTests.assignment.dateDue}"></c:out></td>
												<td width="132" height="0" align="center" class="txtStyle"><c:out
														value="${studTests.assignment.instructions}"></c:out></td>
												<td width="132" height="0" align="center" class="txtStyle"><c:out
														value="${studTests.assignment.assignmentType.assignmentType}"></c:out>
												
												<input
													type="hidden" id="usedFor${testCount}"
													value="${studTests.assignment.usedFor}" /> <input
													type="hidden" id="assignmentTypeId${testCount}"
													value="${studTests.assignment.assignmentType.assignmentTypeId}" />
													<input type="hidden" id="createdBy${testCount}" value="${studTests.assignment.createdBy}"/>
												
                                               </td>
											</tr>
											<c:set var="testCount" scope="page" value="${testCount + 1}">
											</c:set>
										</c:forEach></table></div></c:if></td>
				</tr>
			</table>
									
										<c:if test="${fn:length(studentTests) <= 0}">
											<tr>
												<td align="center" colspan="10">&nbsp;
												</td>												
											</tr>
											<tr>
												<td align="center" colspan="10" id="returnMessage">
												 <span class="status-message">No Assignments are Assigned today.</span>
												</td>												
											</tr>
										</c:if>
									
									<table style="width: 100%" id="testQuestionsDiv">
									</table>
								<table>
							<tr>

								<td id="appenddiv2" height="30" colspan="2" align="center"
									valign="middle"><font color="blue"><label
										id=returnMessage style="visibility: visible;">${helloAjax}</label></font>
								</td>
							</tr>
						</table>
		</div>
		<div id="performanceDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;font-size: 14px;font-family: 'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;font-weight:500;">
			<iframe  id ="iframe" src="" frameborder="0" scrolling="no" width="100%" height="98%" src=""></iframe>
		</div>
</body>
</html>