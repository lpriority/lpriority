<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/performance_common.js"></script>

<script type="text/javascript">

function addStudent(thisVal, perGroupId, sNo, min, max, studentPer) {
	var checkStatus=thisVal.checked;
	var studentId = thisVal.value;
	var csId = document.getElementById("csId").value;
	$.ajax({  
			url : "addOrRemoveStudentToPerformance.htm", 
			data: "csId=" + csId + "&studentId=" + studentId + "&perGroupId=" + perGroupId+"&checkStatus="+checkStatus, 
	        success : function(data) { 
 	        	systemMessage(data.helloAjax);
	        }  
	    });
}

function removeStudent(thisVal, perGroupId, sNo, min, max) {

	$('#returnMessage').stop( true, true ).fadeIn();
	var studentId = thisVal.value;
	var csId = document.getElementById("csId").value;
	var numOfStudents = document.getElementsByName('studentAdd');
	var studentCount=0;
	
	for(var i = 0; i < numOfStudents.length; i++) {
		if (numOfStudents[i].checked == true) {
			studentCount++;
		}
	}
	if(studentCount>min){
		$.ajax({  
			url : "removeStudentPerformance.htm", 
			data: "csId=" + csId + "&studentId=" + studentId + "&perGroupId=" + perGroupId,
	        success : function(data) { 
	        	systemMessage(data.helloAjax);
				if(data.status == true){
					document.getElementById('add:'+sNo).checked=false;
		        	document.getElementById('add:'+sNo).disabled=false;
		            document.getElementById('remove:'+sNo).disabled=true;  
		            document.getElementById('remove:'+sNo).checked=false;
				}else{
					document.getElementById('remove:'+sNo).checked=false;
				}        	         
	        }  
	    });
	}else{
		systemMessage("The Minimum students of this group is "+min);
		thisVal.checked = false;
	}
}

</script>


</head>
<body>
<table align="center">

				<tr><td>&nbsp;</td></tr>
				<tr>
					<td id="appenddiv2" height="30" colspan="5" align="center" valign="middle">
						<label id=returnMessage class='status-message'></label>
                    </td>
                </tr>    
				<tr><td>&nbsp;</td></tr>
</table>
<form name="splitForm" id="splitForm" method="post">
 <table align=center class="des" border=0><tr><td><div class="Divheads">
		<table align="center">

					
				<tr >
						<td align="left" width="150"><b>Student Names</b></td>
						<td align="center" width="108"><font size="2"><b>ADD</b></font></td>
<!-- 						<td align="center" width="108"><font size="2"><b>REMOVE</b></font></td>								 -->
				</tr>
				</table></div>
			 	
			 	<%
					int i = 0;
				%>
			 	<div class="DivContents"><table><tr><td>&nbsp;</td></tr>
				<c:forEach items="${studentList}" var="cList">					
					<tr>
						<c:set var="studentPerId" value="0"/>
						<c:if test="${not empty fn:trim(cList.performancetaskGroups.performanceGroupId)}">
							<c:set var="studentPerId" value="${cList.performancetaskGroups.performanceGroupId}"/>
						</c:if>	
						   <c:choose>
								<c:when test="${studentPerId == perGroup.performanceGroupId}"> 
								<c:set var="checkStatus" value="checked" /> </c:when>
								<c:when test="${studentPerId == 0}">
								<c:set var="checkStatus" value="" />
								</c:when>
								<c:otherwise>
								<c:set var="checkStatus" value="disabled" />
								</c:otherwise>
						</c:choose> 					
						<td align="left" width="150" class="txtStyle" style="padding-bottom: 15px"><c:out value="${cList.student.userRegistration.firstName}" /> <c:out value="${cList.student.userRegistration.lastName}" /></td>
						<td align="center" width="108" style="padding-bottom: 15px">
							<input type="checkbox" class="checkbox-design" name="studentAdd" id="add:<%=i%>" value="${cList.student.studentId}" onclick="addStudent(this, ${perGroup.performanceGroupId}, <%=i%>, ${perGroup.minNoOfStudents}, ${perGroup.maxNoOfStudents}, ${studentPerId} )" ${checkStatus}/>
							<label for="add:<%=i%>" class="checkbox-label-design"></label>
						<input type="hidden" id="pGroupId" value="${perGroup.performanceGroupId}" />
						<input type="hidden" id="pgMin" value="${perGroup.minNoOfStudents}" />
						<input type="hidden" id="pgMax" value="${perGroup.maxNoOfStudents}" />
						</td>
<!-- 						<td align="center" width="108" style="padding-bottom: 15px"> -->
<%-- 							<input type="checkbox" name="studentRem" id="remove:<%=i%>" value="${cList.student.studentId}"  --%>
<%-- 								<c:if test="${cList.performancetaskGroups.performanceGroupId != perGroup.performanceGroupId}"> disabled </c:if>  --%>
<%-- 								onclick="removeStudent(this, ${perGroup.performanceGroupId}, <%=i%>, ${perGroup.minNoOfStudents}, ${perGroup.maxNoOfStudents})" /> --%>
<!-- 						</td>								 -->
					</tr>
					
					
					<%
						i++;
					%>
					
				</c:forEach>
				
				</table>
				<table align="center"><tr><td><tr><td align="center"><input type="button" onClick="addStudentsToGroup()"  class="button_green" id="btSubmitChanges" name="btSubmitChanges" value="Submit" height="52" width="50"></td></tr></td></tr></table>
				</div>
				
								
		</td></tr></table></form>>
</body>
</html>