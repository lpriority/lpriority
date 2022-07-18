<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/studentTestResultsService.js"></script>
<script type="text/javascript" src="/dwr/interface/assignPhonicSkillTestService.js"></script>
<script type="text/javascript" src="/dwr/interface/gradePhonicSkillTestService.js"></script>
<script type="text/javascript" src="/dwr/interface/phonicTestReportsService.js"></script>
<script type="text/javascript" src="/dwr/interface/earlyLiteracyTestsService.js"></script>
<script type="text/javascript" src="resources/javascript/Student/student_test_results.js"></script>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<style>
.smallContentBlueFont {
    font-size: 250%;
    color: blue;
    text-align: center;
    justify-content: center;
    font-weight: bold;
}
.bigContentBlueFont {
    font-size: 300%;
    color: blue;
    text-align: center;
    justify-content: center;
    font-weight: bold;
}
.smallContentRedFont {
    font-size: 450%;
    color: red;
    text-align: center;
    justify-content: center;
    font-weight: bold;
}
.bigContentRedFont {
    font-size: 300%;
    color: red;
    text-align: center;
    justify-content: center;
    font-weight: bold;
}
</style>
<script>
$(document).ready(function () {
    $('#returnMessage').fadeOut(3000);
});
</script>
<html>
<body>
<table><tr><td>
<input type="hidden" name="gradeName" id="gradeName" value="${gradeName}" />
<input type="hidden" name="studentName" id="studentName" value="${studentName}" />
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
 <c:if test="${fn:length(studentAssignmentStatusLt) ne 0}">
 <c:set var="bor" value="0"></c:set>
 <c:set var="de" value="des"></c:set>
 </c:if></td>
 <td style="width: 90%" colspan="5" align="center" valign="top" class="">

<table border="${bor}" class="${de}"><tr><td>


       <c:if test="${fn:length(studentAssignmentStatusLt) ne 0}">
       <div class="Divheads">
 <table>
        <tr>
       		  <th width='51'  height='25' align='center' valign='middle'>Select</th>
              <th width='140'  align='center' valign='middle'>Title</th>
              <th width='141'  align='center' valign='middle'>Assignment Type</th>
			  <th width='132' align='center'>Date Assigned</th>
              <th width='132' align='center'>Due Date</th>
              <th width='132' align='center'>Submitted Date</th>
              <th width='111'  align='center' valign='middle'>Score</th>
              <th width='141'  align='center' valign='middle'>Academic Grade</th>
          </tr></table></div>
         
           <div style="padding: 2px 5px 2px 5px"><table> 
           <tr><td height=10 colSpan=30></td></tr> 
              <c:set var="testCount" value="0"></c:set>
            <c:forEach items="${studentAssignmentStatusLt}" var="sas" varStatus="status">
            
                    <tr><td width='51'  height='25' align='center' valign='middle'>
                    <c:choose>
	                    <c:when test="${sas.assignment.assignmentType.assignmentType == 'Early Literacy Letter' or sas.assignment.assignmentType.assignmentType == 'Early Literacy Word'}">
	                    	<input type="radio" class="radio-design" name="radio" id="studentAssignmentId${testCount}" value="${sas.studentAssignmentId}"  onClick="getStudentsTestResultsDetails('${status.count}','${sas.studentAssignmentId}','${sas.assignment.assignmentType.assignmentType}','${sas.student.userRegistration.user.userType}','${sas.student.userRegistration.regId}','${sas.gradedStatus}','${sas.status}','${sas.student.studentId}','${sas.assignment.assignmentId}', '${sas.assignment.title}', '${sas.student.userRegistration.firstName } ${sas.student.userRegistration.lastName }' )" />
	                    	<label for="studentAssignmentId${testCount}" class="radio-label-design"></label>
	                    </c:when>
	                     <c:when test="${sas.assignment.assignmentType.assignmentType == 'Phonic Skill Test'}">
	                    	<input type="radio" class="radio-design" name="radio" id="studentAssignmentId${testCount}" value="${sas.studentAssignmentId}"  onClick="getStudentsTestResultsDetails('${status.count}','${sas.studentAssignmentId}','${sas.assignment.assignmentType.assignmentType}','${sas.student.userRegistration.user.userType}','${sas.student.userRegistration.regId}','${sas.gradedStatus}','${sas.status}','${sas.student.studentId}','${sas.assignment.assignmentId}', '${sas.assignment.title}', '${sas.student.userRegistration.firstName } ${sas.student.userRegistration.lastName }')" />
	                  		<label for="studentAssignmentId${testCount}" class="radio-label-design"></label>
	                    </c:when>
	                    <c:otherwise>
	                         <input type="radio" class="radio-design" name="radio" id="studentAssignmentId:${testCount}" onClick="getStudentTestResult(${sas.assignment.assignmentId},${sas.studentAssignmentId},${sas.assignment.assignmentType.assignmentTypeId},'${sas.status}',${testCount})" />
	                    	 <label for="studentAssignmentId:${testCount}" class="radio-label-design"></label>
	                    </c:otherwise>
                    </c:choose>
                    <td width='140'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.assignment.title}"></c:out></td>
                    <td width='141'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.assignment.assignmentType.assignmentType}"></c:out></td>
                    <td width='132'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.assignment.dateAssigned}"></c:out></td>
                    <td width='132'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.assignment.dateDue}"></c:out></td>
                    <td width='132'  align='center' valign='middle'  class='txtStyle'><c:out value="${sas.submitdate}"></c:out></td>                    
                    <td width='111'  align='center' valign='middle' class='txtStyle'><c:out value="${sas.percentage}"></c:out></td>
                    <td width='141'  align='center' valign='middle' class='txtStyle'>
                      <c:choose>
                       <c:when test="${sas.academicGrade.acedamicGradeName=='I'} ||${sas.academicGrade.acedamicGradeName== 'N'} || ${sas.academicGrade.acedamicGradeName=='P'}">
                          F
                        </c:when>
                       <c:otherwise>
                         <a href='javascript:void(0)' onClick="getLPSystemRubric('lpSystemRubricDiv')"><c:out value="${sas.academicGrade.acedamicGradeName}"></c:out></a>
                       </c:otherwise>
                      </c:choose>
                       
                       </td>
                       <td>	<div id='dialog${status.count}'  width="100%" height="100%" style="display:none;overflow-y:auto; overflow-x:hidden;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"  title=''>
		                	<iframe id='iframe${status.count}' width="98%" height="98%"></iframe></div>
		               </td>
                    </tr>
                    <c:set var="testCount" scope="page" value="${testCount + 1}">
					</c:set>
                    </c:forEach>
     </table></div> </c:if></td></tr></table>
     <table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>
                <tr><td align='center' valign='middle'>&nbsp;</td></tr>
                <c:if test="${fn:length(studentAssignmentStatusLt) eq 0}">
                <tr><td align="center" valign='middle' class='status-message' id="returnMessage">
                	<span  class="status-message">No Completed Tests</span></td>
                  
            <td align='center' valign='middle'>
            </td></tr></c:if>
            <tr><td height=15 colSpan=30></td></tr>
            <tr><td colspan='8' width='160' align='center'><div id='lpSystemRubricDiv' align='right' style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"/></td></tr></table>
	        <div id="performanceDailog" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></tr></table>
</body>
</html>