<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Teacher Scheduler Details Page</title>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/TeacherViewClass.js"></script>
<script type="text/javascript" src="/dwr/interface/curriculumService.js"></script>
<script>
$( document ).ready(function() {
	$( "#result" ).empty();
	$( "#submit" ).hide();
	var page = dwr.util.getValue('page');
	var noOfStudents = dwr.util.getValue('noOfStudents');
	if(page=='takeAttendance' && noOfStudents > 0){
		$("#submitDiv").append($("#submit").html());
	}else{
		$( "#submit" ).hide();	
	}
  });
</script>
<head>
</head>
<body>
    <input type="hidden" name="sectionId" id="sectionId" value="${sectionId}" />
    <input type="hidden" name="dateToUpdate" id="dateToUpdate" value="${dateToUpdate}" />
    <input type="hidden" name="page" id="page" value="${page}" />
    <input type="hidden" name="noOfStudents" id="noOfStudents" value="${noOfStudents}" />
    <table width="100%"><tr><td>
	<table align="center" class="des" border=0 ${page eq 'takeAttendance' ? 'width=35%' : 'width=45%'}>
		<tr><td> <c:choose>
	  		<c:when test="${fn:length(studentLt) > 0}">
	  			<div class="Divheads"><table>
	  		<tr>
	  			<td width="20%" align="center" class="headsColor">Student Name</td>
	  			<td width="25%" align="center" class="headsColor" style="padding-left:0em;">${dateToUpdate}</td>
	  			
	  		</tr></table></div>
	  		<div class="DivContents"><table>
 	  		<tr><td height=20 colSpan=3>&nbsp;</td></tr> 
			    <c:forEach items="${studentLt}" var="student" varStatus="status">
			    	<tr align="left" style="position: relative;">
			       	 <td width="20%" align="center" class="txtStyle"><a style='cursor: pointer;' onClick=''>${student.userRegistration.firstName}  ${student.userRegistration.lastName}</a></td>
			       	<td width="25%" align="center"  class="txtStyle">
			       	 <select id="student${status.count}" name='studentAttendance' onchange="">
			            <option value='Present-${student.studentId}'>Present</option>
			            <option value='Absent-${student.studentId}'>Absent</option>
			            <option value='ExcusedAbsent-${student.studentId}'>ExcusedAbsent</option>
			            <option value='Tardy-${student.studentId}'>Tardy</option>
			            <option value='ExcusedTardy-${student.studentId}'>ExcusedTardy</option>
			         </select>
			       	</td>
			       	</tr>
			       	<tr><td height=20 colSpan=3></td></tr> 
			    </c:forEach></table></div>
			   
		   </c:when>
	       <c:when test="${fn:length(attendanceLt) > 0}">
	        <div class="Divheads"><table>
	       <tr><td ${page eq 'takeAttendance' ? 'width=20%' : 'width=17%'}  align="center" class="headsColor">Student Name</td>
	       <td width="20%" ${page eq 'takeAttendance' ? 'align=center' : 'align=left'}  class="headsColor" style="">${dateToUpdate}</td>
	       </tr></table></div><div class="DivContents"><table width="100%">
	       	<tr><td height=20 colSpan=3></td></tr> 
	        <c:forEach items="${attendanceLt}" var="attendance" varStatus="status">
		    	<tr align="left" style="position: relative;">
		       	<td width="20%" align="center" class="txtStyle"><a style='cursor: pointer;' onClick='' >${attendance.student.userRegistration.firstName}  ${attendance.student.userRegistration.lastName}</a></td>
		       	<td width="20%" align="center"  class="txtStyle">
		       	 <select id="student${status.count}" name='studentAttendance' onchange="saveAttendance('${status.count}')">
		            <option value='Present-${attendance.student.studentId}'  ${attendance.status == 'Present' ? 'selected="selected"' : ''}>Present</option>
		            <option value='Absent-${attendance.student.studentId}' ${attendance.status == 'Absent' ? 'selected="selected"' : ''}>Absent</option>
		            <option value='ExcusedAbsent-${attendance.student.studentId}' ${attendance.status == 'ExcusedAbsent' ? 'selected="selected"' : ''}>ExcusedAbsent</option>
		            <option value='Tardy-${attendance.student.studentId}' ${attendance.status == 'Tardy' ? 'selected="selected"' : ''} >Tardy</option>
		            <option value='ExcusedTardy-${attendance.student.studentId}' ${attendance.status == 'ExcusedTardy' ? 'selected="selected"' : ''}>ExcusedTardy</option>
		         </select>
		       	</td>
		       	 <c:if test="${page eq 'updateAttendance'}">
		      	 <td width="15%" align="center" class="txtStyle"><div id="result${status.count}" class="status-message"></div></td>
		      	 </c:if>
		       	</tr>
		       	<tr><td height=20 colSpan=2></td></tr> 
		    </c:forEach></table></div>
	   	  </c:when>
	   	  <c:otherwise>
	   	  <table  align="center" valign="middle">
	   	  <tr>
	   	  	<td width="100%" align="center" colspan="10" valign="middle"><font size="3" color="#0073BF">  ${page eq 'takeAttendance' ? 'Students yet not allotted' : ''} 
	   	  		${page eq 'updateAttendance' ? 'Student Attendance not taken for this date' : ''}</font></td>
	   	  </tr></table>
	   	  </c:otherwise>
	   </c:choose>
	    <c:if test="${page ne 'updateAttendance'}">
	    <div class="DivContents"><table width="100%">
	    <tr><td width="500"> 
	     <table align="center" width="100%">
                   <tbody><tr>
                   	<td width="40%" align="center">
                   		<div class="button_green" align="right" onclick="saveAttendance('')">Submit Changes</div> 
					</td>
                     <td width="30%" valign="left">
                     	<a href="gotoDashboard.htm" class="button_green">Cancel</a> 
                     </td>
                   </tr>
              	</tbody></table>
	    </td></tr> </table></div>
	    </c:if>
	    </td></tr>
	</table>
	</td></tr>
	<tr><td>
		<table align="center"><tr><td height=40 colSpan=2  width="40%" ><b><div id="resultDiv" align="center" class="status-message"></div></b></td></tr> </table>
	</td></tr>
	</table>
	</body>
	
</html>