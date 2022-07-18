<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Progress Reports</title>
<!-- <script type="text/javascript"
	src="resources/javascript/jquery/jquery-2.1.4.js"></script> -->
<script type="text/javascript">

function loadReportDates(){
	var csId = $('#csId').val();
	var studentId = $('#studentId').val();
	var previousContainer = $(document.getElementById('previousDiv'));
	$(previousContainer).empty(); 
	$("#dateId").empty();
	$("#dateId").append(
			$("<option></option>").val('').html('Select Date'));
	if(csId == ""){
		alert("Please select a class");
	}
	$.ajax({
		type : "GET",
		url : "getReportDates.htm",
		data : "csId="+ csId+"&studentId="+studentId,
		success : function(response) {
			if(response.size == 0){
				alert("No Reports are found for this class");
			}else{
				var reports = response.reports;				
				$.each(reports,
					function(index, value) {
						var d = new Date(value.releaseDate);
						var dateFormat = d.getMonth()+1+"/"+d.getDate()+"/"+d.getFullYear();
						$("#dateId").append(
							$("<option></option>").val(value.reportId).html(dateFormat));
				});				
			}
			
		}
	});
}

function showPrevious(){
	var reportId = $('#dateId').val();
	var previousContainer = $(document.getElementById('previousDiv'));
	$(previousContainer).empty();  
	if(reportId > 0){
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getPreviousReports.htm",
			data : "reportId="+ reportId,
			success : function(response) {
				$(previousContainer).append(response);
				$("#loading-div-background").hide();
			}
		}); 	
	}
}
</script>
</head>
<body>
<input type="hidden" id="studentId" value="${student.studentId}"/>
<%-- <table class="lp-menu-tabs">
	<tr>
	       <td>
                <input type="hidden" id="tab" name ="tab" value="${tab}" />
           </td>       
           <td vAlign=bottom align=right colspan="20" width="100%">
                    <div> 
                    	<%@ include file="/jsp/CommonJsp/view_progress_report_tabs.jsp" %>
                    </div>
           </td>
       </tr>
</table> --%>
<%-- <table width="99.8%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
<tr><td id = "tab_title" colspan="3" class="sub-title title-border" height="40" align="left">
       		My Progress Report<br>
       		
       </td></tr></table> --%>
<table width="80%" height="100%" border=0 align="left" cellPadding=0 cellSpacing=0 style="padding-top: 0.5em">
       <tr><td>&nbsp;</td></tr>
       
       
	   <tr>
	   		<td style="padding-left: 3.5cm;" colspan="5">
	   			<label class="label">Class&nbsp;&nbsp;</label> 
	   			<select	name="csId" id= "csId" class="listmenu" onchange="loadReportDates()">
					<option>Select Class</option>
					<c:forEach items="${studentClassList}" var="studentClasses">
						<option value="${studentClasses.classStatus.csId}"> ${studentClasses.gradeClasses.studentClass.className}
						</option>
					</c:forEach>
				</select>
	   		</td>
	   		<td align="left" style="padding-right: 15cm;"  colspan="5"><label class="label">Date&nbsp;&nbsp;</label>
				<select id="dateId" class="listmenu" onChange="showPrevious()" required="required">
					<option value="">Select Date</option>
				</select>
			</td>			
	   </tr>
	  
	   <tr>
			<td height="30" colspan="2">&nbsp;<br><br></td>
	   </tr>
	</table>
		
	<div id="previousDiv"></div>	   
</body>

</html>