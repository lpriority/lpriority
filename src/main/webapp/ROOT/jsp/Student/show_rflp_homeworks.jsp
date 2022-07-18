<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />

<table><tr><td>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
 <c:if test="${fn:length(rflpHomeworksList) > 0}">
 <c:set var="bor" value="0"></c:set>
 <c:set var="de" value="des"></c:set>
 </c:if></td></tr></table>
 <table border="${bor}" class="${de}" width="100%"><tr><td>
<c:if test="${fn:length(rflpHomeworksList) > 0}">
<div class="Divheads">
 <table width="100%">
        <tr><td width='20%'  height='25' align='center' valign='middle'><b>Select</b> </td>
                      <td width='20%'  align='center' valign='middle'><b>AssignmentTitle</b> </td>                            
                    <td width='20%'  align='center' valign='middle'><b>Assignment Type</b> </td>
                   <td width='20%'  align='center' valign='middle'><b>H/W Status </b></td>
                  <td width='20%'  align='center' valign='middle'><b>Graded</b> </td>
<!--               <td width='111'  align='center' valign='middle'><b>Score</b> </td> -->
<!--                   <td width='151'  align='center' valign='middle'><b>Academic Grade</b> </td> -->
                    </tr></table></div>
                    <div style="padding: 2px 5px 2px 5px"><table>
                    
              <c:set var="testCount" value="0"></c:set>
              <tr><td>&nbsp;</td></tr>
            <c:forEach items="${rflpHomeworksList}" var="al">
                    
                    <tr><td width='20%' height='25' align='center' valign='middle' class='txtStyle' class="radio-design">
	                    <input type="radio" class="radio-design" name="checkbox2" id="studentAssignmentId:${testCount}" onClick="getStudentRFLPTestQuestions(${al.studentAssignmentStatus.assignment.assignmentId},${al.studentAssignmentStatus.studentAssignmentId},${al.studentAssignmentStatus.assignment.assignmentType.assignmentTypeId},'${al.studentAssignmentStatus.status}',${testCount},${al.studentAssignmentStatus.student.studentId})"/>
	                   	<label for="studentAssignmentId:${testCount}" class="radio-label-design"></label>
                    <td width='20%'  align='center' valign='middle' class='txtStyle'><c:out value="${al.studentAssignmentStatus.assignment.title}"></c:out> </td> 
                    <td width='20%'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.studentAssignmentStatus.assignment.assignmentType.assignmentType}"></c:out></td>
                    <td width='20%'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.studentAssignmentStatus.status}"></c:out></td>
                    <td width='20%'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.studentAssignmentStatus.gradedStatus}"></c:out></td>
<!--                     <td width='111'  align='center' valign='middle' class='txtStyle'> -->
<%--                     <fmt:formatNumber var="formattedPercentage" type="number" minFractionDigits="2" maxFractionDigits="2" value="${al.studentAssignmentStatus.percentage}" /> --%>
                                                               
<%--                     <c:out value="${formattedPercentage}"></c:out></td> --%>
<!--                     <td width='151'  align='center' valign='middle' class='txtStyle'> -->
<%--                       <c:choose> --%>
<%--                        <c:when test="${al.studentAssignmentStatus.academicGrade.acedamicGradeName=='I'} ||${al.studentAssignmentStatus.academicGrade.acedamicGradeName== 'N'} || ${al.studentAssignmentStatus.academicGrade.acedamicGradeName=='P'}"> --%>
<!--                           F -->
<%--                         </c:when> --%>
<%--                        <c:otherwise> --%>
<%--                           <c:out value="${al.studentAssignmentStatus.academicGrade.acedamicGradeName}"></c:out> --%>
<%--                        </c:otherwise> --%>
<%--                       </c:choose> --%>
                       
<!--                        </td> -->
                    </tr>
                    <c:set var="testCount" scope="page" value="${testCount + 1}">
					</c:set>
                    </c:forEach>
     </table></div>
</c:if></td></tr></table>
     <table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>
                <tr><td align='center' valign='middle'></td>
                <c:if test="${fn:length(rflpHomeworksList) <= 0}">
			          
                <td align='center' valign='middle' class="status-message">No Student Reports</td>
                  </c:if>
            <td align='center' valign='middle'>
            </td></tr></table>
	<div id="performanceDailog"></div>