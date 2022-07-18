<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript">

var lastHeight = $('#body').outerHeight();
$('#body').bind("DOMSubtreeModified",function(){
	currentHeight = $('#body').outerHeight();
	var height = $(document).height();
	var changedHeight = (currentHeight-lastHeight);
	if(lastHeight != currentHeight){
		
		lastHeight = currentHeight;
	}
});
  	$(document).ready(function () {
  		$("#returnMessage").fadeOut(5000);
	
  	});
function getStudentIOLReportCard(tab){
	$("#studentsTestResultsDiv").html("");
	var csId = $('#csId').val();
	if(csId == 'select' || csId == ''){
		return false;
	}
	else{
		
		$("#loading-div-background").show();
	$.ajax({
		type : "GET",
		url : "checkIOLReportExists.htm",
		data : "&csId="+csId,
		success : function(response) {
			 if(response.trimesterId==3 && (response.status=="false" || response.status=="true")){
				$("#loading-div-background").hide();
 			 	systemMessage("Report Already Created");
 				return false;
 				
 			}else{
 			$.ajax({
 			type : "GET",
 			url : "CreateSelfIOLReportCard.htm",
 			data : "&csId="+csId+"&trimesterId="+response.trimesterId+"&tab="+tab,
 			success : function(response) {
 				var reportContainer = $(document.getElementById('monitor'));
 				var screenWidth = $( window ).width() - 70;
 				var screenHeight = $( window ).height() - 30;
 				$(reportContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
 					  $(".ui-dialog-titlebar-close").show();},
 				 close: function( event, ui ) { 
 					window.location.reload();
 				 },
 				dialogClass: 'myTitleClass'
 				});		
 				$(reportContainer).dialog("option", "title", "Learning Indicator");
 				$(reportContainer).scrollTop("0");
 				$('#rioReportContainer').empty(); 
 				$('#rioReportContainer').append(response);
 				$("#loading-div-background").hide();
 			}});
 			}
			}});
	}
}

</script>

</head>
<body>
			<table align="center" style="width:100%" >
				<tr>
					<td style="" colspan="5" align="center" valign="top">
					<table style="width:90%" align="right">
							<tr>
								<td style="width:100%" align="right">
									<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
									<input type="hidden" id="tab" name="tab" value="${tab}" /></td>
								<td style="" align="right" valign="bottom">
								</td>
							</tr></table></td></tr>
							
							</table>
							
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
							<tr><td class="sub-title title-border" height="40" align="left" colspan="3">Learning Indicator<br> </td>
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
                                        <!--   <td><input type="text" style="width:100px;" name="gradeName" id="gradeName" value ="${grade.masterGrades.gradeName}" disabled /></td> -->
                                        <td align="left" valign="middle" style="padding-right: 8cm;" class="label">
                                        	<label>Class</label>&nbsp;&nbsp;
                                           <select name="csId" id="csId" class="listmenu" onchange="getStudentIOLReportCard('${tab}')">
															<option value="select">Select Class</option>
															<c:forEach items="${studentClasses}" var="class">
																<c:set var="itemNums" scope="request" value="${itemNums + 1}" />
																<option value="${class.classStatus.csId}" <c:if test="${class.classStatus.csId == selectedCsId}">selected="selected"</c:if>>
																	${grade.masterGrades.gradeName}_${class.classStatus.section.gradeClasses.studentClass.className}</option>
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
							<td align="left" style="padding-left: 7em;padding-bottom:3em">
							<div id="studentsTestResultsDiv" style="align:center;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td></tr></table>
							
							 <table width=100%>
				             <tr>
				             <td align="left" style="padding-left: 10em;">
				             <div id="getCompletedTestQuestions" title="Test Results" style="align:center;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td></tr>
				             </table>
					</td>
				</tr>
			</table>
			<div id="monitor" style="display: none;align:center;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
			<div id="rioReportContainer" style="height: 400px; width: 100%;"></div><br>
			<div id="audioContainer"></div>			
		</div>
		<div id="divImg">
	</div>
	<table width="100%" align="center" class="title-pad"><tr><td><br><br><div id="returnMessage" style="color:blue"></div></td></tr></table>
</body>
</html>
