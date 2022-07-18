<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>IOL Reports</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<!-- <script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> -->
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<style type="text/css">

.ui-dialog > .ui-widget-content {background: white;}

.linkSty{
font-color:blue;
font-size:10;
}
</style>
 
<script type="text/javascript">
 function getStudentIOLReportCard(iolReportId,studentId,reportDate,status){
	 $("#loading-div-background").show();
 	var tab=$('#tab').val();
 	$.ajax({
 		type : "GET",
 		url : "showStudentIOLReportCard.htm",
 		data : "studentId=" + studentId+"&iolReportId="+iolReportId+"&reportDate="+reportDate+"&status="+status+"&tab="+tab,
 		success : function(response) {
 				var reportContainer = $(document.getElementById('rioReportsContainer'));
 				var screenWidth = $( window ).width() - 10;
 				var screenHeight = $( window ).height() - 10;
 				$('#rioReportsContainer').empty(); 
 				$(reportContainer).dialog({width: screenWidth, height: screenHeight,modal: true,open:function () {
 					  $(".ui-dialog-titlebar-close").show();
 				},close: function( event, ui ) { 
					  $(this).dialog('destroy');
					  if ($("#CriteriaDialog").hasClass("ui-dialog-content")){
					  $('#CriteriaDialog').dialog('destroy');
					  }
					  if ($("#LEFileDailog").hasClass("ui-dialog-content")){
						$('#LEFileDailog').dialog('destroy');
					  }
				 }
 				});		
 				$(reportContainer).dialog("option", "title", "Learning Indicator");
 				$(reportContainer).scrollTop("0");
 				$('#rioReportsContainer').append(response);
 				$("#loading-div-background").hide();
			
 		}})
 }


</script>
</head>
<body>
 <input type="hidden" id="tab" name ="tab" value="${tab}" />
 <input type="hidden" id="studentId" value="${student.studentId}"/>
<%-- <table class="lp-menu-tabs">
	<tr>
	       <td>
               
           </td>       
           <td vAlign=bottom align=right colspan="20" width="100%">
                    <div> 
                    	<%@ include file="/jsp/CommonJsp/view_progress_report_tabs.jsp" %>
                    </div>
           </td>
       </tr>
</table> --%>
<%-- <table width="100%" border="0" class="title-pad" style="padding-bottom:3em;">
<tr><td colspan="3" class="sub-title title-border" height="40" align="left">
       		Show Reports<br>       		
       </td></tr></table> --%>
 <br>
        <table><tr><td width="600" style="padding-left:37em">
        <c:if test="${fn:length(reportDates) > 0}">
       
	   <table width="75%" border='0' class="des" align="center"><tr><td><div class="Divheads"><table><tr><td align='center' class="headsColor">REPORTS</td></tr></table></div>
                    <br>
                     <div class="DivContents"><table>
                     
                    <tr><td align='center' class="tabtxt"><font color="blue">
                     <c:set var="i" value="0"/>
                     <c:forEach items="${reportDates}" var="dList">	
                        <c:set var="i" value="${i+1}"/>
        
                     <c:out value="${i}" />.&nbsp;&nbsp;<a href="#" onClick="getStudentIOLReportCard(${dList.iolReportId},${studentId},'${dList.reportDate}',${dList.status})">${dList.reportDate}</a><br><br>
        
                   </c:forEach> </font>
                     
                     
                    </td></tr>
                    
                    
          </table></div></td></tr></table></c:if>
          <c:if test="${fn:length(reportDates) <= 0}">
                     <span class="status-message">No Reports</span><br> </c:if>
                   <div id="monitor" style="display: none;">
			<div id="rioReportsContainer" style="height: 400px; width: 100%;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div><br>
					
		</div><td></tr></table>
	   	
</body>
</html>