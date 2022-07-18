<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<style>
.assignPhonicSkillsTestClass{
	color: white;
	height:30px; 
	width:240px; 
	font-size:13px;
	background-color: #663399;
	font-weight: 900;
}
</style>
<script type="text/javascript">

function clearDiv(){
	var errorWordContainer = $(document.getElementById('errorWordDiv'));
	$(errorWordContainer).empty(); 
	document.getElementById('errorTypeId').value = "select";
}

function getAnalysis(){
	var gradeId = document.getElementById('gradeId').value;
	var errorWordContainer = $(document.getElementById('errorWordDiv'));
	$(errorWordContainer).empty(); 
	if(gradeId == "select"){
		alert("Please select a grade");
		document.getElementById('errorTypeId').value = "select";
		return;
	}
	var typeId = document.getElementById('errorTypeId').value;
	if(typeId == "select"){		
		return;
	}	
	$("#loading-div-background").show();
	$.ajax({
		url : "getErrorAnalysis.htm",
		type : "POST",
		data : "typeId=" + typeId+"&gradeId="+gradeId,
		success : function(data) {	
			$("#loading-div-background").hide();
 			$(errorWordContainer).append(data);
		}
	});
	
}
</script>
<title>Error Word Item Analysis</title>
</head>
 <body>
	  <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
	  <tr><td>   
	   <table border=0 cellSpacing=0 cellPadding=0 width="100%" align=center height="100%">  
            <tr>
                <td style="color:white;font-weight:bold" >
                	<input type="hidden" name="tab" id="tab" value="${tab}">
                </td>
                <td vAlign=bottom align=right>
		        <div> 
		         	<%@ include file="/jsp/assessments/rti_tabs.jsp" %>
		        </div>
     		  </td>
     		</tr>
       </table>
  </td></tr>
  <tr><td>
  <form action="assignGameToStudents.htm" id="assignGameForm" name="assignGameForm" method="post">	
  	 <input type="hidden" id="teacherId" name="teacherId" value="${teacherId}"/>   
  	 <input type="hidden" id="usedFor" name="usedFor" value="${usedFor}"/> 
 <!-- Content center box -->		  
       <table border=0 cellSpacing=0 cellPadding=0 width="100%" class="title-pad">                         
                                <tr><td height="30" width="100%" align="left" valign="middle">
								<table width="32%" height="30" border="0" cellpadding="0" cellspacing="0" align="left">
								<tr>
									<th width="60" align="left" class="label">Grade</th>
									<td width="50" align="left">
										<select	id="gradeId" name="gradeId" onchange="clearDiv();" style="width:120px;" class="listmenu">
												<option value="select">Select grade</option>
									 		<c:forEach items="${grList}" var="ul">
												<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
											</c:forEach> 
										</select>
									</td>
									<td></td>
									<th width="120" class="label">Error Type</th>
									<td width="30" align="left" valign="middle">
									<select id="errorTypeId" name="errorTypeId" class="listmenu" style="width:120px;" onchange="getAnalysis();" >
									 	<option value="select">Select Error Type</option>
									 	<c:forEach items="${errorTypes}" var="et">
												<option value="${et.errorTypeId}">${et.errorType}</option>
											</c:forEach> 
									</select></td>									
 							   </tr>
 							   <tr><td height="40"></td></tr>
				        	</table> 
                      		</td></tr>
                      		<tr>
                      			<td width="100%" align="center" >
                      				<div id="errorWordDiv"></div>
                      			</td>
                      		</tr>
                      	</table> 	
    				</form> 
 	</table>
	</body>
</html>