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
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<style type="text/css">

.ui-dialog > .ui-widget-content {background: white;}
</style>
 <link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

 function getStudentIOLReportCard(iolReportId,studentId,reportDate,status){
	 $("#loading-div-background").show();
 	$('#monitor').show();
 	var tab=$('#tab').val();
 	
 	$.ajax({
 		type : "GET",
 		url : "showStudentIOLReportCard.htm",
 		data : "studentId=" + studentId+"&iolReportId="+iolReportId+"&reportDate="+reportDate+"&status="+status+"&tab="+tab,
 		success : function(response) {
 				var reportContainer = $(document.getElementById('rioReportsContainer'));
 				var screenWidth = $( window ).width() - 10;
 				var screenHeight = $( window ).height() - 10;
 				//$(gradeContainer).append(response);
 				$(reportContainer).dialog({width: screenWidth, height: screenHeight,modal: true,title: "Learning Indicator",
 					open:function () {
 					  $(".ui-dialog-titlebar-close").show();
 				} ,close: function (ev, ui) { 
 					/*  if ($("#CriteriaDialog").hasClass("ui-dialog-content")){
 					 $('#CriteriaDialog').dialog('destroy');
 					 } */
					  if ($("#LEFileDailog").hasClass("ui-dialog-content")){
						$('#LEFileDailog').dialog('destroy');
					  }
				} });			
 				//$(reportContainer).dialog("option", "title", "Learning Indicator");
 				$(reportContainer).scrollTop("0");
 				$(reportContainer).empty(); 
 				$(reportContainer).append(response);
 				$("#loading-div-background").hide();
			
 		}})
 }



</script>
<style>
.golChildLink{
 color:blue;
}
</style>
</head>
<body>


        <table><tr><td width="600" style="padding-left:37em">
        <input type="hidden" name="tab" id="tab" value="${tab}" />
        <input type="hidden" name="studentId" id="studentId" value="${studentId}" />
       
	  <table width="75%" border='0' class="des" align="center"><tr><td><div class="Divheads"><table><tr><td align='center' class="headsColor">Reports</td></tr></table></div>
                    <br>
                     <div class="DivContents"><table>
                     
                    <tr class="txtStyle"><td align='center'><font color='black'>
                   <c:choose>
                    <c:when test="${fn:length(reportDates) > 0}">
					 <c:set var="i" value="0"/>
                     <c:forEach items="${reportDates}" var="dList">	
                        <c:set var="i" value="${i+1}"/>
        
                     <c:out value="${i}"></c:out>.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="golChildLink" onClick="getStudentIOLReportCard(${dList.iolReportId},${studentId},'${dList.reportDate}',${dList.status})">${dList.reportDate}</a><br><br>
        
                   </c:forEach></c:when> 
                    <c:otherwise>
                     <span class="status-message">No Reports</span><br> </c:otherwise>   
                     </c:choose>                  
                    </font></td></tr>
                    
                    
          </table></div></td></tr></table>
                   <div id="monitor" style="display: none;">
			<div id="rioReportsContainer" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div><br>
					
		</div><td></tr></table>
	   	
</body>
</html>