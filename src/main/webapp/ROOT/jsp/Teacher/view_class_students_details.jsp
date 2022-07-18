<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page import="com.lp.utils.WebKeys"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Teacher Scheduler Details Page</title> 
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/teacherViewClassService.js"></script>

<head>
</head>
<body>
	<input type="hidden" name="gradeClassId" id="gradeClassId" value="${gradeClassId}" />
	<input type="hidden" name="gradeLevelId" id="gradeLevelId" value="${gradeLevelId}" />
	<input type="hidden" name="csId" id="csId" value="${csId}" />
	<c:choose>
        <c:when test="${divId eq 'Registration'}"> 	
	        <table align="center" width="80%" style="padding-left: 14em;">
				<tr><td height=35 colSpan=4></td></tr> 
		        <tr><th colspan=2 align=left style="padding-left: 11em;color: #078091;"  height="20" class="header"><u>Students List</u></th></tr>
		        <tr><td height=10 colSpan=1></td></tr> 
		        <tr><td width="30%" align="left" class="tabtxt" style="padding-left:12em">Number of Students: ${fn:length(studentLt)}</td></tr>
      	</c:when>
		<c:otherwise>
		      	<table align="center" width="80%" style="padding-left: 25em;">
				<tr><td height=35 colSpan=1></td></tr> 
		      	<tr><td class="header" style="padding-left:2em"><u>Students List</u></td></tr>
		      	<tr><td height=10 colSpan=1></td></tr> 
	       	<tr><td width="30%" align="left" class="tabtxt" style="padding-left:0em">Number of Students: ${fn:length(studentLt)}</td></tr>
        
		      	</c:otherwise>
	     </c:choose>
	    <tr><td height=10 colSpan=1></td></tr> 
	   	<table class="des" border=0 align="center" ${(divId eq 'Roster'|| page eq 'takeAttendance') ? 'width=40%' : 'width=60%'}><tr><td><div class="Divheads"><table>
       	<tr>
              <td width="200" align="left" class="headsColor">Student Name</td>
              <td width="200" height="10" align="center" class="headsColor" style="padding-left: 2em;">Email Address </td>
            <c:choose>
		        <c:when test="${divId eq 'Registration'}"> 	
		          <td width="200" height="25" align="left" colspan=2  class="headsColor" style="padding-left: 8em;">Reply Action </td>
		      	</c:when>
	      	</c:choose>
        </tr></table></div><div class="DivContents"><table>
        <tr><td height=10 colSpan=1></td></tr> 
	    <c:forEach items="${studentLt}" var="student" varStatus="status">
	    <tr align="left" position="relative" >
	        <input type="hidden" id ="${student.studentId}" name="${student.studentId}" value="${student.userRegistration.firstName}  ${student.userRegistration.lastName}">
	       	<td width="200" align="left" class="txtStyle"><a style='cursor: pointer;' onClick='getStudentExplore()'>${student.userRegistration.firstName}  ${student.userRegistration.lastName}</a></td>
	       	<td width="250" align="center" class="txtStyle"><a href='#' onclick="getEmailWindow(${status.count}); return false;">${student.userRegistration.emailId}</a></td>
	      	<c:choose>
				<c:when test="${divId eq 'Registration'}"> 	
			     	<td width='300' height='30' align='left' valign='middle'>
						<table width='100%' border='0' cellspacing='2' cellpadding='0'>
							 <tr>
							   	<td height='25' align='right' valign='middle'>
									<input type='checkbox' class="checkbox-design" id='${student.studentId}-<%=WebKeys.ACCEPTED%>' name='${student.studentId}-<%=WebKeys.ACCEPTED%>' onClick="check(this)"/><label for="${student.studentId}-<%=WebKeys.ACCEPTED%>" class="checkbox-label-design">Accept</label>
								</td>
								<td height='25' align='center' valign='middle'>
									<input type='checkbox' class="checkbox-design" id ='${student.studentId}-<%=WebKeys.DECLINED%>' name='${student.studentId}-<%=WebKeys.DECLINED%>' onClick="check(this)"/><label for="${student.studentId}-<%=WebKeys.DECLINED%>" class="checkbox-label-design">Decline</label>
								</td>
							</tr>
						</table>
					</td>
				</c:when>
			</c:choose>
			<div id="dialog${status.count}"  style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;display:none;" title=""> <iframe  id ="iframe${status.count}" src="getEmailWindow.htm?studentEmailId=${student.userRegistration.emailId}" frameborder="0" scrolling="no" width="100%" height="95%" src=""></iframe></div>
	     </tr>
	     <tr><td  style="padding-bottom: 7px"></td></tr>
	   </c:forEach> 
	</table></div></td></tr></table></body>
</html>