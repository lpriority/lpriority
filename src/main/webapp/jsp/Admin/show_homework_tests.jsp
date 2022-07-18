<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />


<c:if test="${fn:length(studentAssessmentTestList) > 0}">
<table class="des" border=0><tr><td><div class="Divheads">
 <table width="100%">
        <tr><td width='51'  height='25' align='left' valign='middle' class='headsColor'><b>Select</b> </td>
                  <td width='111' align='center' valign='middle' class='headsColor'><b>Late Submission</b> </td>
                  
                 <td width='111' align='center' valign='middle' class='headsColor'><b>Student Id </b></td>
                 <td width='111'  align='center' valign='middle' class='headsColor'><b>Student Name</b> </td>
                                       
                                  
                  <td width='111'  align='center' valign='middle' class='headsColor'><b>Unit No</b> </td>
                  <td width='111'  align='center' valign='middle' class='headsColor'> <b>Lesson No </b></td>
                  <td width='111'  align='center' valign='middle' class='headsColor'><b>Lesson Name </b></td>
                  <td width='141'  align='center' valign='middle' class='headsColor'><b>Assignment Type</b> </td>
                   <td width='140'  align='center' valign='middle' class='headsColor'><b>H/W Status </b></td>
                  <td width='111'  align='center' valign='middle' class='headsColor'><b>Graded</b> </td>
                  <td width='111'  align='center' valign='middle' class='headsColor'><b>Score</b> </td>
                  <td width='111'  align='center' valign='middle' class='headsColor'><b>Academic Grade</b> </td>
                    </tr></table></div><div class="DivContents"><table>
            <c:set var="testCount" value="0"></c:set>
            <c:forEach items="${studentAssessmentTestList}" var="al">
                    <tr><td width='51'  height='25' align='left' valign='middle'  class=''>
	                    <input type="radio" class='radio-design' name="checkbox2" id="studentAssignmentId:${testCount}" onClick="getStudentTestQuestions(${al.assignment.assignmentId},${al.studentAssignmentId},${al.assignment.assignmentType.assignmentTypeId},'${al.status}',${testCount},'${al.assignment.assignmentType.assignmentType}')" />
	                   	<label for='studentAssignmentId:${testCount}' class='radio-label-design'></label>
                   	</td>
                    <td width='111' align='center' valign='middle'  class='txtStyle'>
                     <c:choose>
                       <c:when test="${al.assignment.dateDue < al.submitdate}">
                       LateSubmission
                        </c:when>
                       <c:otherwise>
                            -
                       </c:otherwise>
                      </c:choose>
                    
                    <c:out value=""></c:out></td>
                    
                    <c:choose>
                  	<c:when test="${tab ne 'gradeGroup'}">
                    	<td width='111' align='center' valign='middle'  class='txtStyle'><c:out value="${al.student.userRegistration.regId}"></c:out></td>
                    	<td width='111'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.student.userRegistration.firstName}"></c:out></td>
                    </c:when>
                    <c:otherwise>
                    	<td width='111' align='center' valign='middle'  class='txtStyle'><c:out value="${al.performanceGroup.performanceGroupId}"></c:out></td>
                    	<td width='111'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.performanceGroup.groupName}"></c:out></td>
                    </c:otherwise>
                   </c:choose> 
                    <td width='111'  align='center' valign='middle'  class='txtStyle'><c:out value="${not empty al.assignment.lesson.unit.unitNo? al.assignment.lesson.unit.unitNo : 'N/A'}"></c:out></td>
                    <td width='111'  align='center' valign='middle' class='txtStyle'><c:out value="${not empty al.assignment.lesson.lessonNo ?  al.assignment.lesson.lessonNo : 'N/A'}"></c:out></td>
                    <td width='111' align='center' valign='middle' class='txtStyle'><c:out value="${not empty al.assignment.lesson.lessonName ? al.assignment.lesson.lessonName : 'N/A'}"></c:out></td>
                    <td width='140'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.assignment.assignmentType.assignmentType}"></c:out></td>
                    <td width='140'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.status}"></c:out></td>
                    <td width='111'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.gradedStatus}"></c:out></td>
                    <td width='111'  align='center' valign='middle' class='txtStyle'>
                    <fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${al.percentage}" />
                                                               
                    <c:out value="${formattedPercentage}"></c:out></td>
                    <td width='111'  align='center' valign='middle' class='txtStyle'>
                      <c:choose>
                       <c:when test="${al.academicGrade.acedamicGradeName=='I'} ||${al.academicGrade.acedamicGradeName== 'N'} || ${al.academicGrade.acedamicGradeName=='P'}">
                          F
                        </c:when>
                       <c:otherwise>
                          <c:out value="${al.academicGrade.acedamicGradeName}"></c:out>
                       </c:otherwise>
                      </c:choose>
                       
                       </td>
                    </tr>
                    <c:set var="testCount" scope="page" value="${testCount + 1}">
					</c:set>
                    </c:forEach></table></div>
   </td></tr>  </table>
</c:if>
     <table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>
                <tr><td align='center' valign='middle'></td>
                <c:if test="${fn:length(studentAssessmentTestList) <= 0}">
			          
                <td align='center' valign='middle' class="status-message">No Student Reports</td>
                  </c:if>
            <td align='center' valign='middle'>
            </td></tr></table>
	<div id="performanceDailog"></div>