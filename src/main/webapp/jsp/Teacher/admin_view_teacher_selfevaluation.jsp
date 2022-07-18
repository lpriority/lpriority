<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Teacher Self Evaluation</title>
<script type="text/javascript" src="resources/javascript/TeacherJs/teacher_self_evaluation.js"></script>
	<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript"
	src="/dwr/interface/teacherService.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	 $('#returnMessage').fadeOut(0);
});
</script>
</head>

    <body>
	<table style="padding-left: 8em;width: 99.5%" class="heading-up heading-up">
       	<tr>
           	<td class="sub-title title-border" height="40" align="left" 
           		colspan="10">
               		Teacher Self Evaluation
            </td>
        </tr>
	</table>
	<div class="title-pad" style="padding-top: 1em;">
    	<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0 align="center" class="label">
                <form:form name="adminReportForm">
                                <tr>
                                    <td height=30 align=left style="width:20%;">
                                            Teacher &nbsp;&nbsp;&nbsp;
                                    	<select id="teacherId" name="teacherId" onchange="getReportDates(this)" class="listmenu" style="width:120px;">
											<option value="select">select Teacher</option>
											<c:forEach items="${TeacherList}" var="tl">
												<option value="${tl.teacher.teacherId}">${tl.teacher.userRegistration.title}&nbsp;${tl.teacher.userRegistration.firstName}&nbsp;${tl.teacher.userRegistration.lastName}</option>
											</c:forEach>
										</select>
									</td>
                                    <td height=30 align=left>
                                     	Date &nbsp;&nbsp;&nbsp;
                                        <select name="reportDate" id="reportDate" onChange="changeDate()" class="listmenu">
                                            <option value="select">Select Date</option>
                                        </select>
                                    </td>
                               </tr>
                               <tr>
                                	<td height=15 colSpan="10" align="center">
                                		<label id=returnMessage style="color: red;"></label>&nbsp;</td>
                            	</tr>
                            	<tr><td>&nbsp;</td></tr>
                </form:form>
            </table>
            <div width="100%" colspan="10" align="center" id="getReports" class="txtStyle"></div>
        </div>     
</body>
</html>
