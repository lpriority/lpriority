<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/studentTestResultsService.js"></script>
<script type="text/javascript" src="/dwr/interface/earlyLiteracyTestsService.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student_test_results.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script>
$(document).ready(function () {
     $('#returnMessage').fadeOut(3000);
});
</script>
<table><tr><td>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
<c:if test="${fn:length(studentAssignmentStatusLt) > 0 }">
<c:set var="bor" value="0"></c:set>
<c:set var="de" value="des"></c:set></c:if>
</td></tr>
<tr><td style="padding-left:8em">
 <c:if test="${fn:length(studentAssignmentStatusLt) > 0}">
 <table border="${bor}" class="${de}" align="center"><tr><td>
 
     <div class="Divheads">
     <table>
       
        <tr>
       		  <th width='51'  height='25' align='center' valign='middle'>Select</th>
              <th width='140'  align='center' valign='middle'>Title</th>
              <th width='141'  align='center' valign='middle'>Assignment Type</th>
			  <th width='132' align='center'>Date Assigned</th>
              <th width='132' align='center'>Due Date</th>
              <th width='132' align='center'>Submitted Date</th>
              <th width='111'  align='center' valign='middle'>Status</th>
              <th width='111'  align='center' valign='middle'>Graded Status</th>
          </tr>
          </table>
      </div>
      <div style="padding: 2px 5px 2px 5px"><table>
              <c:set var="testCount" value="0"></c:set>
            <c:forEach items="${studentAssignmentStatusLt}" var="sas" varStatus="status">
            
                    <tr><td width='51'  height='25' align='center' valign='middle'  class='txtStyle'>
	                         <input type="radio" class="radio-design" name="radio" id="studentAssignmentId:${testCount}" onClick="getStudentBenchmarkQuestions(${sas.assignment.assignmentId},${sas.studentAssignmentId},${sas.assignment.assignmentType.assignmentTypeId},${gradingTypesId})" />
	                 		 <label for="studentAssignmentId:${testCount}" class="radio-label-design"></label>
	                  </td> 
                    <td width='140'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.assignment.title}"></c:out></td>
                    <td width='141'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.assignment.assignmentType.assignmentType}"></c:out></td>
                    <td width='132'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.assignment.dateAssigned}"></c:out></td>
                    <td width='132'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.assignment.dateDue}"></c:out></td>
                    <td width='132'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.submitdate}"></c:out></td>                    
                    <td width='111'  align='center' valign='middle' class='txtStyle'><c:out value="${sas.status}"></c:out></td>
                    <td width='111'  align='center' valign='middle' class='txtStyle'>
                     <c:choose>
                     <c:when test="${gradingTypesId==2}">
                        <c:out value="${sas.selfGradedStatus}"></c:out>
                     </c:when>
                     <c:when test="${gradingTypesId==3}">
                        <c:out value="${sas.peerGradedStatus}"></c:out>
                     </c:when>
                    </c:choose>
                    </td>
                    </tr>
                    <c:set var="testCount" scope="page" value="${testCount + 1}">
					</c:set>
                    </c:forEach></table></div>
    </td></tr> </table>
    </c:if>
     <table width='100%' height='25' border='0' cellpadding='0' cellspacing='0' aling="center">
                <tr><td align='center' valign='middle'></td>
                <c:if test="${fn:length(studentAssignmentStatusLt) == 0}">
                	<td align='center' valign='middle' class='tabtxt' id="returnMessage"><span  class="status-message">No Completed Tests</span></td>
                  </c:if>
            <td align='center' valign='middle'>
            </td></tr>
            <tr><td height=15 colSpan=30></td></tr>
            <tr><td colspan='8' width='160' align='center'><div id='lpSystemRubricDiv' align='right'/></td></tr></table>
            </td></tr></table>
	