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
<script src="resources/javascript/common/jQuery.print.min.js"></script>
<style>

</style>
<script type="text/javascript">

function clearDiv(){
	var taskForceContainer = $(document.getElementById('taskForceResultDiv'));
	$(taskForceContainer).empty(); 
}

function clearDate(){
	$("#fluId").empty();
	$("#fluId").append($("<option></option>").val('select').html('Select Title'));	
	$("#comId").empty();
	$("#comId").append($("<option></option>").val('select').html('Select Title'));	
}

function getTaskForceTitles(){
	var csId = $('#csId').val();
	console.debug("jasdjkasdjajsd"+ csId);
	$("#fluId").empty();
	$("#fluId").append($("<option></option>").val('select').html('Select Title'));	
	$("#comId").empty();
	$("#comId").append($("<option></option>").val('select').html('Select Title'));	
	var taskForceContainer = $(document.getElementById('taskForceResultDiv'));
	$(taskForceContainer).empty(); 
	if(csId != 'select'){
		var usedFor = $('#usedFor').val();
		$("#loading-div-background").show();
		$.ajax({
			url : "getTaskForceTitles.htm",
			type : "POST",
			data : "csId=" + csId+"&usedFor="+usedFor,
			success : function(response) {					
				var fluency = response.fluency;
				$.each(fluency, function(index, value) {
					$("#fluId").append(
							$("<option></option>")
									.val(value.assignmentId).html(
											value.title));
				});
				var com = response.com;
				$.each(com, function(index, value) {
					$("#comId").append(
							$("<option></option>")
									.val(value.assignmentId).html(
											value.title));
				});
				$("#loading-div-background").hide();
			}
		});
	}
}

function getTaskForceResults(){
	var fluencyId = $('#fluId').val();
	var comId = $('#comId').val();
	var taskForceContainer = $(document.getElementById('taskForceResultDiv'));
	$(taskForceContainer).empty(); 
	if(fluencyId != 'select'){
		if(comId != 'select'){	
			$("#loading-div-background").show();
			$.ajax({
				url : "getTaskForceResults.htm",
				type : "POST",
				data : "fluencyId=" + fluencyId+"&comId="+comId,
				success : function(response) {			
					$("#loading-div-background").hide();
					$(taskForceContainer).append(response);
				}
			});
		}else{
			//alert("Please select Comprehension title");
		}
	}else{
		//alert("Please select Fluency title");
	}
}
</script>
<title>Literacy Support Team Results</title>
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
  <form id="taskForceResultForm" name="taskForceResultForm" method="post">	
  	 <input type="hidden" id="teacherObj" name="teacherObj" value="${teacherObj}"/>   
  	 <input type="hidden" id="usedFor" name="usedFor" value="${usedFor}"/> 
 <!-- Content center box -->		  
       <table border=0 cellSpacing=0 cellPadding=0 width="100%" class="title-pad">                         
                                <tr><td height="30" width="100%" align="left" valign="middle">
								<table width="90%" height="30" border="0" cellpadding="0" cellspacing="0" align="left">
								<tr>
									<td height="40" align="left" class="label" style="width: 12em;">Grade &nbsp;
										<select name="gradeId" class="listmenu" id="gradeId"
											onChange="clearDiv();getGradeClasses()" required="required">
												<option value="select">select grades</option>
												<c:forEach items="${grList}" var="ul">
													<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
												</c:forEach>
										</select>
									</td>
									<td align="left" class="label" style="width: 12em;">Class &nbsp;
										<select id="classId"
											name="classId" class="listmenu"
											onChange="clearDiv();clearDate();getClassSections()" required="required">
											<option value="select">select subject</option>
									</select>
									</td>
				
									<td align="left" class="label" style="width: 14em;">Section &nbsp;
										<select	id="csId" name="csId" class="listmenu" onChange="clearDiv();clearDate();getTaskForceTitles();" required="required">
											<option value="select">select Section</option>
										</select>
									</td>	
									<td align="left" class="label" style="width: 13em;">Fluency &nbsp;
										<select	id="fluId" name="fluId" class="listmenu" onChange="clearDiv();getTaskForceResults();" required="required">
											<option value="select">select Title</option>
										</select>
									</td>	
									<td align="left" class="label" style="width: 15em;">Comprehension &nbsp;
										<select	id="comId" name="comId" class="listmenu" onChange="clearDiv();getTaskForceResults();" required="required">
											<option value="select">select Title</option>
										</select>
									</td>													
 							   </tr>
 							   <tr><td height="20"></td></tr>
				        	</table> 
                      		</td></tr>
                      		<tr>
                      			<td width="100%" align="center" >
                      				<div id="taskForceResultDiv"></div>
                      			</td>
                      		</tr>
                      	</table> 	
    				</form> 
 	</table>
	</body>
</html>