
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Split Students to Group</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/teacher_popup.js"></script>	
<script>
$(document).ready(function () {
	 $('#returnMessage').fadeOut(5000);
});
	
function getStudentsByGroupId() {
	var gradeId = document.getElementById("gradeId").value;
	var classId = document.getElementById("classId").value;
	var csId = document.getElementById("csId").value;
	var perGroupId = document.getElementById("perGroupId").value;
	var studentContainer = $(document.getElementById('studentDiv'));
	$(studentContainer).empty(); 
	if(csId > 0 && perGroupId > 0 && gradeId > 0 && classId > 0){
		$("#loading-div-background").show();
		$.ajax({  
			url : "getStudents.htm", 
			data: "csId=" + csId + "&perGroupId="+perGroupId, 
	        success : function(data) {
	        	$(studentContainer).append(data);
	        	$("#loading-div-background").hide();
	        }  
	    }); 
	}else{
		alert("Please select the filters");
	}
}

function clearDiv(){
	$("#perGroupId").empty();
	$("#perGroupId").append($("<option></option>").val('').html('Select Group'));
	$("#studentDiv").empty(); 	
	
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
<form:form id="createGroupPerformance"  action="createGroupPerformance.htm" modelAttribute="performancetaskGroups" method="post">
	       <table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0 class="title-pad">
                        
                        <tr>
                            <td height="30" colspan="2" align="left" valign="left"  width="100%">
                               
                                    <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">

                                        <tr>
                                            <td height="30" align="left" valign="center">
                                                <table width="70%" border="0" cellspacing="0" cellpadding="0">                                                                                                     
                                                    <tr><td>&nbsp;</td>
                                                    <td>
                                                    		<input type="hidden" id="teacherObj" name ="teacherObj" value="${teacherObj}" />
                                                    		<input type="hidden" id="usedFor" name="usedFor" value="${usedFor}" />
                                                    		<input type="hidden" id="tab" name ="tab" value="${tab}" />
                                                    	</td>
                                                    </tr>
                                                   
                                                    <tr>
                                                        <td height="20" align="left">
                                                        	<label class="label">Grade&nbsp;&nbsp;</label>
                                                            <select name="gradeId" class="listmenu" id="gradeId" onChange="clearDiv();getGradeClasses()">
                                                               <option value="select">Select Grade</option>
																	<c:forEach items="${grList}" var="ul">
			                											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
																	</c:forEach>
                                                            </select><c:out value="${ul.gradeId}"></c:out>
                                                        </td>
                                                        <td height="20" align="left">
                                                        	<label class="label">Class&nbsp;&nbsp;</label>
															<select id="classId" name="classId" class="listmenu" onchange="clearDiv();getClassSections()">
																<option value="select">Select Class</option>
															</select>
														</td>	
														<td height="20" align="left">
															<label class="label">Section&nbsp;&nbsp;</label>
															<select id="csId" name="csId" class="listmenu" onchange="clearDiv();getPerformanceGroups()">
																<option value="select">Select Section</option>
															</select>
														</td>
														<td height="20" align="left">
															<label class="label">Group&nbsp;&nbsp;</label>
															<select id="perGroupId" name="perGroupId" class="listmenu" onchange="getStudentsByGroupId()">
																<option value="select">Select Group</option>
															</select>
														</td>
                                                    </tr>                                                    
                                             	</table>
                                             </td>

                                        </tr>
										 
                                                                            
                                        <tr>
                                        	<td>
                                        		<div id="studentDiv"></div>
                                        	</td>
                                        </tr>


                                    </table></td></tr>
                    </table>
          </form:form>
	<div id="subViewDiv"></div>
</body>
</html>