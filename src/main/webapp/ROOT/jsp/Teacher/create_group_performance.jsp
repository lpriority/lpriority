
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Performance Group</title>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/teacher_popup.js"></script>	
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script>

$(document).ready(function () {
	 $('#returnMessage').fadeOut(5000);
});
	

function showGroup(){
	var gradeId = document.getElementById("gradeId").value;
	var classId = document.getElementById("classId").value;
	var csId = document.getElementById("csId").value;
	document.getElementById("groupDetails").style.visibility = "hidden";
	resetValues();
	if(gradeId > 0 && classId > 0 && csId > 0){
		document.getElementById("groupDetails").style.visibility = "visible";
		document.getElementById("showsub").style.visibility = "visible";
	}else{
		alert("Please select the filters");
	}
		
}

function createPerformanceTaskGroup(){
	var groupName = document.getElementById("groupName").value;
	var minNo = document.getElementById("minNoOfStudents").value;
	var maxNo = document.getElementById("maxNoOfStudents").value;
	
	if(!groupName){
		alert("Please enter group name");
		return false;
	}
	if(minNo <= 0 || isNaN(minNo)){
		alert("Please enter a value for min number of students");
		return false;
	}
	if(maxNo <= 0 || isNaN(maxNo)){
		alert("Please enter a value for max max number students");
		return false;
	}
	var form = document.getElementById("createGroupPerformance1");
	var formData = new FormData(form);
	$.ajax({
		url: 'createGroupPerformance.htm',
		type: 'POST',
		data: formData,
		contentType: false,
		cache: false,
		processData:false,
	    success: function(data){
	    	 showGroup();
	    	 systemMessage(data);
	     }
	});
}

function clearDiv(){
	$("#csId").empty();
	$("#csId").append(
			$("<option></option>").val('').html('Select Section'));
	document.getElementById("groupDetails").style.visibility = "hidden";
	document.getElementById("showsub").style.visibility = "hidden";
	
	resetValues();
}

function resetValues(){
	$("#groupName").val('');
	$("#minNoOfStudents").val('');
	$("#maxNoOfStudents").val('');
}
    
</script>

</head>

<body>
<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
        <tr>
            <td vAlign=bottom align=right>
                <div> 
                	<c:choose>
						<c:when test="${usedFor == 'rti'}">
							<%@ include file="/jsp/assessments/rti_tabs.jsp" %>
						</c:when>    									
						<c:when test="${usedFor == 'homeworks' && teacherObj != null }">
							<%@ include file="/jsp/assessments/homework_tabs.jsp" %>
						</c:when>
						<c:otherwise>
							<%@ include file="/jsp/curriculum/curriculum_tabs.jsp" %>
						</c:otherwise>
					</c:choose>
                </div>
            </td>
        </tr>
</table>                        
	<form:form id="createGroupPerformance1"  action="createGroupPerformance.htm" modelAttribute="performancetaskGroups" method="post">
	       <table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad">                        
                        <tr>
                            <td height="30" colspan="2" align="left" valign="left"  width="100%">
                               
                                    <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">

                                        <tr>
                                            <td height="30" align="left" valign="center">
                                                <table width="60%" border="0" cellspacing="0" cellpadding="0">                                                                                                     
                                                    <tr><td>&nbsp;</td>
                                                    <td>
                                                    		<input type="hidden" id="teacherObj" name ="teacherObj" value="${teacherObj}" />
                                                    		<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
                                                    		<input type="hidden" id="tab" name ="tab" value="${tab}" />
                                                    	</td>
                                                    
                                                    </tr>
                                                    
                                                    <tr>
                                                        <td width='100' height="20" align="left">
                                                        	<label class="label">Grade&nbsp;</label>
                                                            <select name="gradeId" class="listmenu" id="gradeId" onChange="clearDiv();getGradeClasses()">
                                                               <option value="select">Select Grade</option>
																	<c:forEach items="${grList}" var="ul">
			                											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
																	</c:forEach>
                                                            </select>
                                                        </td>
                                                        <td width='100' height="20" align="center">
                                                        	<label class="label">Class&nbsp;</label>
															<select id="classId" name="classId" class="listmenu" onchange="clearDiv();getClassSections()">
																<option value="select">Select Class</option>
															</select>
														</td>	
														<td width='100' height="20" align="center">
														<label class="label">Section&nbsp;</label>
															<select id="csId" name="csId" class="listmenu" onchange="showGroup()">
																<option value="select">Select Section</option>
															</select>
														</td>
														<td width='100'>&nbsp;</td>
                                                    </tr>                                                    
                                             	</table>
                                             </td>

                                        </tr>
										 <tr><td>&nbsp;</td></tr>
                                       </table></td></tr><tr><td>
                                      	<div align="center">
                                      	<table align="center" class="des" border=0 id="groupDetails" style="visibility:hidden;"><tr>
			                               <td width="450"><div class="Divheads">
						                <table align=center><tr><td><label class="headsColor">Performance Task Group</label></font></td></tr></table></div>
						                
						                <div class="DivContents"><table >
						                <tr><td>&nbsp;</td></tr>
                                       <tr height="30">
                                   			<td width="562" align="left" style="padding-top:1em" class="tabtxt">Enter Group Name&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;
                                   				<form:input path="groupName" id="groupName" onblur="checkGroupName()" required="required"/>
                                   			</td>
                                   		</tr>
                                   		<tr height="30">
                                   			<td width="562" align="left" style="padding-top:1em" class="tabtxt">Min No.Of Students&nbsp;&nbsp;:&nbsp;
                                   				<form:input path="minNoOfStudents" id="minNoOfStudents" required="required"/>
                                   			</td>
                                   		</tr>
                                   		<tr height="30">
                                   			<td width="562" align="left" style="padding-top:1em" class="tabtxt">Max No.Of Students&nbsp;:&nbsp;
                                   				<form:input path="maxNoOfStudents" id="maxNoOfStudents" required="required"/>
                                   			</td>
                                   		</tr>
                                   		 <tr><td>&nbsp;</td></tr>
                                   		 	<tr>
                                   			<td align="center">
                                   			<div class="button_green" align="right" id="btSubmitChanges" name="btSubmitChanges" onclick="createPerformanceTaskGroup()">Create Group</div>
                                   			</td>
                                   		</tr>
                                   		</table></div></td></tr></table>
                                   		</div></td></tr><tr><td>
                                   		<table align="center" id="showsub" style="visibility:hidden;">
                                   		<tr><td >&nbsp;</td></tr>
                                   	</table></td></tr>
                                   	<tr><td align="center">
                                   		<label id=returnMessage style="visibility:visible;" class="status-message"> ${helloAjax}</label>
                                   	</td></tr>
                           </table>
          </form:form>
	<div id="subViewDiv"></div>
</body>
</html>