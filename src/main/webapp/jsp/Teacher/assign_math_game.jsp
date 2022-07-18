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
<script src="resources/javascript/TeacherJs/math_assessment.js"></script>
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
$(document).ready(function () {
    $( "#dueDate" ).datepicker({
   		changeMonth: true,
       	changeYear: true,
       	showAnim : 'clip',
       	minDate: 0
    });
});

function clearDiv(){
	document.getElementById('assignDiv').style.display = 'none';
	$("#select_all").removeAttr("checked");
}
</script>
<title>Assign Gear Game</title>
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
								<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" align="left">
								<tr>
									<th width="60" align="left" class="label">Grade</th>
									<td width="50" align="left">
										<select	id="gradeId" name="gradeId" onchange="clearDiv();getGradeClasses()" style="width:120px;" class="listmenu">
												<option value="select">select grade</option>
									 		<c:forEach items="${teacherGrades}" var="ul">
												<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
											</c:forEach> 
										</select>
									</td>
									<td></td>
									<th width="120" class="label">Class</th>
									<td width="30" align="left" valign="middle">
									<select id="classId" name="classId" class="listmenu" style="width:120px;" onchange="clearDiv();getClassSections()" >
									 	<option value="select">select subject</option>
									</select></td>
									<th width="120" class="label">Section</th>
									<td width="50" align="right" valign="right">
									<select id="csId" name="csId" class="listmenu" style="width:120px;" onchange="clearDiv();getStudentsBySection()" >
										<option value="select">select section</option> 
									</select></td>
									 <td width="120" class="label" align="center">&nbsp;&nbsp;&nbsp;Student&nbsp;&nbsp;</td>
							  <td width="120"><select name="studentId" id="studentId" multiple="multiple" required="required" style="width: 150px;height:60px; overflow-y : visible" onclick="selectOption(this,'assignDiv')">
							  <option disabled="disabled" >Student</option>
							  </select></td>
 							   <td class="tabtxt">&nbsp;&nbsp;&nbsp;&nbsp;
 							   		<input type="checkbox" class="checkbox-design" id="select_all" name="select_all" onClick="selectAllOptions(this, 'studentId','assignDiv')">
 							   		<label for="select_all" class="checkbox-label-design">Select All</label></td>
 							   </tr>
 							   <tr><td height="20"></td></tr>
				        	</table> 
                      		</td></tr>
                      		<tr>
                      			<td width="100%" align="center" >
                      				<div id="assignDiv" style="padding-top:1.5em; display: none;">
                      					<table width='100%'>
                      						<tr>
                      							<td align='center'>
                      								<table width='50%' class='des' align='center'>
                      									<tr>
                      										<td align='center'>
                      											<table width='100%' class='Divheads'>
                      												<tr>
                      													<td align='center'> Assign Game </td>
                      												</tr>
                      											</table>
                      										</td>
                      									</tr>
                      									<tr>
                      										<td align='center' width='80%'>
                      											<table width='70%' class='DivContents' align='center' >
                      												<tr><td style="padding-top: 20px;"></td></tr>
                      												<tr><td align="left" class="tabtxt" valign="middle">
                      														<img src="images/Common/required.gif">Title</td>
                      													<td>:</td> 
                      													<td><input name='titleId' type='text' id='titleId' size='20' maxlength="150" onblur="checkTitleById();" value=''/>
                      													</td>
                      												</tr>
                      												<tr><td align='left' class="tabtxt" valign="middle">
                      														<img src="images/Common/required.gif">Instructions</td>
                      													<td>:</td>
                      													<td> <textarea name='instructId' id='instructId' maxlength="100"></textarea>
                      													</td>
                      												</tr>
                      												<tr><td align='left' class="tabtxt" valign="middle">
                      														<img src="images/Common/required.gif">Due Date</td>
                      													<td>:</td> 
                      													<td>
                      														<input id="dueDate" name="dueDate" size="15" maxlength="15" value="" required="required" readonly="readonly"/>
                      													</td>
                      												</tr>
                      												<tr>
                      													<td style="padding: 15px;" colspan="3" align="center">
                      														<div id='assignGame' class='button_green' onclick='assignGameToStudents()' style='font-size:12px;width:100px;'>Assign Game</div>
                      													</td>
                      												</tr>
                      											</table>
                      										</td>
                      									</tr>
                      								</table>
                      							</td>
                      						</tr>
                      					</table>
                      				</div>
                      			</td>
                      		</tr>
                      	</table> 	
    				</form> 
 	</table>
	</body>
</html>