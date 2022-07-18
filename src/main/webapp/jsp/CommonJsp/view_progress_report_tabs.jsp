<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<title>Student Progress Reports</title>
</head>
<script>
function togglePage(thisVar,url, name){
	$("#common-loading-div-background").show();
	$.ajax({
		type : "GET",
		url : url+".htm",
		success : function(response) { 
 		 	$("#contentDiv").empty();
			$("#contentDiv").append(response);
			$("#tab_title").html(name);
			var current = document.getElementsByClassName("buttonSelect");
		    current[0].className = current[0].className.replace(" buttonSelect", " button");
		    var activeElement = $(thisVar);
		    activeElement[0].className = activeElement[0].className.replace(" button"," buttonSelect");
		    $("#common-loading-div-background").hide();
	 	}
	}); 
}
</script>
<body>
	<table width="100%" height="29" border="0" align="right" cellpadding="0"
		cellspacing="0">
		<tr>
			<td vAlign=bottom width=100% align="right">
			<ul class="button-group">
				<c:choose>	
					<c:when test="${userReg.user.userType =='parent'}">
						<li><a href="#" onclick = "togglePage(this,'parentViewProgress','Progress Reports')" class="btn ${(tab == 'progressReports')?'buttonSelect leftPill':'button leftPill'}">Progress Reports</a></li>
						<li><a href="#" onclick = "togglePage(this,'viewChildIOLReportCards','Show Reports')" class="btn ${(tab == 'showReports')?'buttonSelect':'button'}">Show Reports</a></li>
						<li><a href="#" onclick = "togglePage(this,'viewChildGoalReports','Goal Reports')" class="btn ${(tab == 'goalReports')?'buttonSelect rightPill longTitle':'button rightPill longTitle'}">Goal Reports</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="#" onclick = "togglePage(this,'studentProgress','Progress Reports')" class="btn ${(tab == 'progressReports')?'buttonSelect leftPill longTitle':'button leftPill longTitle'}">Progress Reports</a></li>
					   <li><a href="#" onclick = "togglePage(this,'viewStudentIOLReportCards','Show Reports')" class="btn ${(tab == 'showReports')?'buttonSelect rightPill longTitle':'button rightPill longTitle'}">Show Reports</a></li>
					  
					</c:otherwise>
				</c:choose>	
			</ul>
			</td>
		</tr>
	</table>
	<c:choose>	
		<c:when test="${userReg.user.userType =='parent'}">
			<table width="99.8%" style="padding-top: 1em" border="0" cellspacing="0" cellpadding="0" class="title-pad">
				<tr><td id = "tab_title" colspan="3" class="sub-title title-border" height="40" align="left">
       				Progress Reports<br>
       			</td></tr>
     		</table>
     	<div id = "contentDiv"><%@include file="../Parent/progress_reports_parent.jsp"%></div>
		</c:when>
		<c:otherwise>
			<table width="99.8%" style="padding-top: 1em" border="0" cellspacing="0" cellpadding="0" class="title-pad">
				<tr><td id = "tab_title" colspan="3" class="sub-title title-border" height="40" align="left">
       				My Progress Report<br>
       				<input type="hidden" id="studentId" value="${student.studentId}"/>
       			</td></tr>
     		</table>
     	<div id = "contentDiv"><%@include file="../Student/progress_reports.jsp"%></div>
    	 </c:otherwise>
	</c:choose>
	<div id="common-loading-div-background" class="loading-div-background" style="display:none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div" style="color:#103c51;left: 50%;padding-left:12px;">
		<br><br><br><br><br><br><br>Loading....
	</div>
	</div>
</body>
</html>