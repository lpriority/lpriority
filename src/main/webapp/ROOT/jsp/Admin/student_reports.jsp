<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../CommonJsp/include_dwr_teacher.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Reports</title>
<script type="text/javascript" src="resources/javascript/AdminPopups.js"></script>
<script type="text/javascript" src="resources/javascript/admin/student_reports.js"></script>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	 $('#returnMessage').fadeOut(3000);
});
$(function() {
    $( "#fromId" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
       	maxDate: new Date() 
    });
    $( "#toId" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
       	maxDate: new Date() 
    });
});

function updateCalendar(thisvar){
	var start = new Date(thisvar.value);
	var end = new Date();
	var diff = new Date(start - end);
	var days = Math.ceil(diff/1000/60/60/24);		
	$( "#toId" ).datepicker( "option", "minDate",  days);
	 
}
function clearDiv(){
	document.getElementById("checkAssigned").checked = false;
	var StudentReportList = $(document.getElementById('StudentReportList'));
	$(StudentReportList).empty(); 
}

</script>
</head>
<body>
	<table width="100%">
		<tr>
			<td  vAlign=top width="100%" colSpan=3 align=middle>
				<table width="100%" border=0 align="center" cellPadding=0
					cellSpacing=0>
					<tr><td colspan="" width="100%"> 
						 <table width="100%" class="title-pad heading-up" border="0">
							<tr>
								<td class="sub-title title-border" height="40" align="left" >Student Reports</td>
							</tr>
						</table>
					</td></tr>
					<tr><td height=0 vAlign=top colSpan=2 align=left>
 				   <div class="title-pad" style="padding-top: 1em;">
						<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0>
			                <tr>
			                    <td> <input name="textfield3" id="teacherId" type="hidden" size="20" value ="" readonly /></td></tr>
			                <tr>
			                    <td align="left" width="100%" valign="middle">
			                    	<table width="100%" border="0" cellpadding="0" cellspacing="0">
			                            <tr>
			                                <td width="220px" valign="middle" class="label">Grade &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                                   <select id="gradeId" name="gradeId"	class="listmenu" style="width:120px;" onchange="getGradeClasses();clearDiv();">
													<option value="select">select grade</option>
												<c:forEach items="${grList}" var="ul">
													<option value="${ul.gradeId}">${ul.masterGrades.gradeName}</option>
												</c:forEach>
												</select></td>
												
			                                <td width="220px" align="left" valign="middle"  class="label">Class &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                                    <select	id="classId" name="classId" class="listmenu" style="width:120px;" onchange="getClassSections();clearDiv();">
													<option value="select">select subject</option>
												</select></td>
												
			                                <td width="220px" align="left" valign="middle"  class="label">Section &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                                   <select style="width:120px;" name="csId" class="listmenu" id="csId"	onchange="clearDiv();">
													<option value="select">Select Section</option>
												</select></td>
			
			                                <td width="400px" align="left" class="label">Select Date Range &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                                 <input type="text" id="fromId" style="width:90px" onchange="clearDiv();updateCalendar(this)" maxlength="15" readonly="readonly"/>
				                                &nbsp;&nbsp;&nbsp;&nbsp;
				                                 <input type="text" id="toId" style="width:90px" onchange="clearDiv();" maxlength="15" readonly="readonly"/>
			                                </td>
			
			                                <td align="left" class="label">
			                                	<input type="checkbox" class="checkbox-design" name="checkbox" value="checkbox" id="checkAssigned"  onclick="showReports()"/><label for="checkAssigned" class="checkbox-label-design">Show Reports</label>
			                                </td>
			                            </tr>
			                        </table>
			                    </td>
			                </tr>
			                <tr>
			                    <td align="left" valign="top" id="StudentReportList" class="content-box space-between"></td>
			                </tr>
		            </table>   
 				</div>
 				</td></tr></table>
 				</td></tr></table>
			
</body>
</html>