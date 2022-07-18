<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Progress Monitoring</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/CommonPopup.js"></script>
<script type="text/javascript" src="resources/javascript/teacher_popup.js"></script>
<script type="text/javascript" src="resources/javascript/ParentJs.js"></script>
<script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
<style type="text/css">
#atten, #module {
    border: 0px solid black;
}
</style>
<script type="text/javascript">
function displayStudentList() {
	var csId = $('#csId').val();
	var studentId = $('#studentId').val();	
	if(!studentId){
		studentId = 0;
	}
	
	var studentContainer = $(document.getElementById('studentDiv'));
	$(studentContainer).html("");  
	if(csId > 0){
		$("#loading-div-background").show();
		$.ajax({
			type : "GET",
			url : "getStudentsProgres.htm",
			data : "csId="+ csId+"&studentId="+ studentId,
			success : function(response) {
				$(studentContainer).html('');
				$(studentContainer).append(response);
				$("#loading-div-background").hide();
			}
		}); 	
	}
}

function adminTeacherFlow(){
	$("#studentDiv").html(""); 
	var gradeId = $('#gradeId').val();
	var classId = $('#classId').val();
	if(gradeId > 0 && classId > 0){
		displayStudentList();
	}else{
		alert("Please select the filters");
	}
}

function parentFlow(){
	$("#studentDiv").html(""); 
	var studentId = $('#studentId').val();
	if(studentId > 0){
		displayStudentList();
	}else{
		alert("Please select a child");
	}
}

function clearDiv(){
	$("#studentDiv").html("");  
}
</script>
</head>
<body>
		
	<table width="100%" height="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
		<tr>	       
	        <td align=right >
	                 <div> 
	                 	<c:choose>
	 						<c:when test="${userType == 'admin' || userType == 'teacher'}">
	    						<%@ include file="/jsp/assessments/rti_tabs.jsp" %>
	 						</c:when>    									
	 						<c:when test="${userType == 'parent'}">
	    						<%@ include file="/jsp/Student/view_rti_tabs.jsp" %>
	 						</c:when>
					<c:otherwise>								
						<%@ include file="/jsp/Student/view_rti_tabs.jsp" %>
					</c:otherwise>
				</c:choose>
	                 </div>
	        </td>
	    </tr>
	</table>
	<c:choose>
		<c:when test="${userType == 'admin'}">
		    <script src="resources/javascript/admin/common_dropdown_pull.js"></script>
		</c:when>
		<c:when test="${userType == 'teacher'}">
		    <script src="resources/javascript/TeacherJs/common_dropdown_pull.js"></script>
		</c:when>
		<c:otherwise>

		</c:otherwise>
	</c:choose>	
	<table width="99.8%" border="0" cellspacing="0" cellpadding="0" class="title-pad">
	 <tr>
	       		<c:if test="${userType != 'admin' && userType != 'teacher'}">
					<td class="sub-title title-border" height="40" width="100%" align="left">Progress Monitoring</td>
		       </c:if>
	       		<td>
		       		<input type="hidden" id="tab" name ="tab" value="${tab}" />
		       		<input type="hidden" id="teacherObj" value="${teacherObj}" />
		       		<input type="hidden" id="userType" value="${userType}" />       		
		       </td>
	       </tr>   
	</table> 
	<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
		<tr>
			<td colspan="2" align="left" valign="left" width="100%" >
				<table align="left" valign="left" width="100%" class="title-pad" border=0 cellPadding=0 cellSpacing=0 >	          
				   <tr>
				   		<c:choose>
			    			<c:when test="${userType == 'admin' || userType == 'teacher'}">    			
						   		<td height="20" align="left" class="label" style="width: 13em;">Grade &nbsp;&nbsp;&nbsp;
						   			<select name="gradeId" id="gradeId" class="listmenu" onChange="clearDiv();getGradeClasses()" 
										required="required">
										<option value="select">select grade</option>
										<c:forEach items="${grList}" var="ul">
											<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
										</c:forEach>
									</select>
						   		</td>
						   		<td align="left" class="label" style="width: 13em;padding-left: 2em;">Class &nbsp;&nbsp;&nbsp;
						   			<%-- <c:set var="functionCall" value="getClassSections()"></c:set>
						   			<c:if test="${userType == 'admin'}">
						   				<c:set var="functionCall" value="getTeachersByClass()"></c:set>
						   			</c:if> --%>
						   			<select id="classId" name="classId" class="listmenu" onChange="clearDiv();getClassSections()"
																required="required">
										<option value="select">select subject</option>
									</select>
								</td>
								<%-- <c:if test="${userType == 'admin'}">
									<td align="left" class="label" style="width: 14em;padding-left: 2em;">Teacher &nbsp;&nbsp;&nbsp;
							   			<select id="teacherId" name="teacherId" class="listmenu" 
							   				onChange="clearDiv();getSectionsByTeacher()" required="required">
											<option value="select">select Teacher</option>
										</select>
									</td>
								</c:if> --%>
								<td align="left" class="label" style="padding-left: 2em;">Section &nbsp;&nbsp;&nbsp;
									<select id="csId" class="listmenu" onChange=";clearDiv();adminTeacherFlow()" required="required">
										<option value="select">select Section</option>
									</select>
								</td>
							</c:when>	
							<c:when test="${userType == 'parent'}">
								<td align="left" valign="middle" style="padding-top: 1em;">
			                        <label style="font-size:3;" class="label">Child&nbsp;&nbsp;&nbsp;</label>
						   			<select name="studentId" id="studentId" class="listmenu" onChange="clearDiv();loadGradeClasess()" 
										required="required">
										<option value="select">Select Child</option>
										<c:forEach items="${students}" var="st">
											<option value="${st.studentId}">${st.userRegistration.title} ${st.userRegistration.firstName} ${st.userRegistration.lastName}</option>
										</c:forEach>
									</select>
						   		</td>
								<td style="padding-top: 1em;" align="left"><label style="font-size:3;" class="label">Grade&nbsp;&nbsp;</label>&nbsp;
									<input type="text" style="width: 100px;" name="gradeName" id="gradeName" disabled />
								</td>					
								<td style="padding-top: 1em;"><label style="font-size:3;" class="label">Class&nbsp;</label>
						   			<select id="csId" name="csId" class="listmenu" onchange="clearDiv();parentFlow()" style="width: 120px;">
										<option value="select">Select Subject</option>
									</select>
								</td>
								<td width='480' align=left>&nbsp;&nbsp;</td>
								<td width="150" align="left" valign="middle">&nbsp;</td>
			    			</c:when>
						
				   			<c:otherwise>
				   			
							<!--  	<td align="left" valign="middle" width="100" style="padding-top: 1em;"><label class="label">Grade&nbsp;&nbsp;&nbsp;</label>
									<input type="text"  name="gradeName" style="width:100px;" id="gradeName" value="${grade.masterGrades.gradeName}" disabled />
								</td> -->	
				
								<td align="left" valign="middle" width="150" style="padding-top: 1em;">
									<label class="label">Class&nbsp;&nbsp;&nbsp;</label>
									<select id="csId" class="listmenu" name="csId" onchange="clearDiv();displayStudentList()" style="width: 120px;">
										<option value="select">select class</option>
										<c:forEach items="${GradeClasses}" var="ul">
											<c:set var="itemNums" scope="request" value="${itemNums + 1}" />
											<option value="${ul.classStatus.csId}">${grade.masterGrades.gradeName}_${ul.gradeClasses.studentClass.className}
										</c:forEach>
										<script>
										<c:if test='${itemNums >0}' >
											csId.options[1].selected = true;
											clearDiv();displayStudentList();
											$(function () {
											    $("select#csId").change();
											});
										</c:if>	
										</script>
								</select></td>
								<td width="400" align="left" valign="middle">&nbsp;</td>
								<td width="20" align="left" valign="middle">&nbsp;</td>
							</c:otherwise>
						</c:choose>			
					</tr>
						
					<tr>
						<td height="30" colspan="2">&nbsp;<br><br></td>
				   </tr>
				</table>
	   			<div id="studentDiv" style=""></div>	
			</td>
		</tr>
	</table>
</body>
</html>