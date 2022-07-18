<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Teacher Scheduler Details Page</title>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/TeacherScheduler.js"></script> 
<script type="text/javascript" src="resources/javascript/admin/student_reports.js"></script>
<head>
</head>
	<table align="center" width="50%">
		<tr><td height=30 colSpan=3></td></tr> 
		<tr><td>
			<table align="center" width="70%" class="des" style="font-family:'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;font-size: 13.5px;font-weight:500;">
			    <tr class="Divheads"><td colspan=2 align=center><h3>Students List</h3></td></tr>
		      		<tr><td width="562" align="center"><h4>Number of Students: ${fn:length(studentLt)}</h4></td></tr>
		       	<tr><td height=10 colSpan=3></td></tr> 
		       	<tr>
		              <td width="50%" align="center"><font color="black" size="3"><b>Student Name</b></font></td>
		              <td width="50%" height="25" align="center"><font color="black" size="3"><b>Email Address</b></font> </td>
		        </tr>
		        <tr><td style="padding-bottom: 8px"></td></tr>
		    <c:forEach items="${studentLt}" var="student" varStatus="status">
		    	<tr align="left">
			       	<td width="50%" align="center"><a style='cursor: pointer;' onClick='displayStudentCompositeChart(${csId}, ${student.studentId})'>${student.userRegistration.firstName}  ${student.userRegistration.lastName}</a></td>
			       	<td width="50%" align="center"><a href='#' onclick="getEmailWindow(${status.count}); return false;">${student.userRegistration.emailId}</a></td>
			       	<td width="50%" align="center">
			       		<div id="dialog${status.count}"  style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display:none;" title=""> <iframe  id ="iframe${status.count}" src="getEmailWindow.htm?studentEmailId=${student.userRegistration.emailId}" frameborder="0" scrolling="no" width="100%" height="95%" src=""></iframe></div>
			       	</td>
		       	</tr>
		       	<tr><td style="padding-bottom: 8px"></td></tr>
		    </c:forEach>
		    <tr><td height=10 colSpan=3></td></tr> 
			</table>
		</td></tr></table>	
	<div id="studentCompositeChart"></div>
</html>