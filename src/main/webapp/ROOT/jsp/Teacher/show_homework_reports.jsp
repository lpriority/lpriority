<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<table><tr><td>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
 <c:if test="${fn:length(homeworkReportList) > 0}">
 <c:set var="bor" value="0"></c:set>
 <c:set var="de" value="des"></c:set>
 </c:if></td></tr></table>
 <table border="${bor}" class="${de}" align="center"><tr><td>
 <c:if test="${fn:length(homeworkReportList) > 0}"><div class="Divheads">
 <table style="width:100%;">
        <tr><th width='51'  height='25' align='center' valign='middle' class='tabtxt'>Sl No </th>
                  <th width='111' align='center' valign='middle' class='tabtxt'>Reg Id </th>
                  <th width='111'  align='center' valign='middle' class='tabtxt'>Student Name </th>
                  <th width='111'  align='center' valign='middle' class='tabtxt'>Unit No </th>
                  <th width='111'  align='center' valign='middle' class='tabtxt'> Lesson No </th>
                  <th width='111'  align='center' valign='middle' class='tabtxt'>Lesson Name </th>
                  <th width='140'  align='center' valign='middle' class='tabtxt'> Assignment Type </th>
                  <th width='141'  align='center' valign='middle' class='tabtxt'>Student H/W Status </th>
                  <th width='111'  align='center' valign='middle' class='tabtxt'>Graded </th>
                  <th width='111'  align='center' valign='middle' class='tabtxt'>Score </th>
                  <th width='111'  align='center' valign='middle' class='tabtxt'>Academic Grade </th>
                    </tr></table></div>
                    <div class="DivContents"><table><tr><td>&nbsp;</td></tr>
            <c:forEach items="${homeworkReportList}" var="al" varStatus="i">
                    <tr><td width='51'  height='25' align='center' valign='middle'  class='txtStyle'>${i.count}</td>
                    <td width='111' align='center' valign='middle'  class='txtStyle'><c:out value="${al.student.userRegistration.regId}"></c:out></td>
                    <td width='111'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.student.userRegistration.firstName} ${al.student.userRegistration.lastName}"></c:out></td>
                    <td width='111'  align='center' valign='middle'  class='txtStyle'><c:out value="${not empty al.assignment.lesson.unit.unitNo? al.assignment.lesson.unit.unitNo : 'N/A'}"></c:out></td>
                    <td width='111'  align='center' valign='middle' class='txtStyle'><c:out value="${not empty al.assignment.lesson.lessonNo ?  al.assignment.lesson.lessonNo : 'N/A'}"></c:out></td>
                    <td width='111' align='center' valign='middle' class='txtStyle'><c:out value="${not empty al.assignment.lesson.lessonName ? al.assignment.lesson.lessonName : 'N/A'}"></c:out></td>
                    <td width='140'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.assignment.assignmentType.assignmentType}"></c:out></td>
                    <td width='141'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.status}"></c:out></td>
                    <td width='111'  align='center' valign='middle'  class='txtStyle'><c:out value="${al.gradedStatus}"></c:out></td>
                    <td width='111'  align='center' valign='middle' class='txtStyle'><c:out value="${al.percentage}"></c:out></td>
                    <td width='111'  align='center' valign='middle' class='txtStyle'><c:out value="${not empty al.academicGrade.acedamicGradeName ? al.academicGrade.acedamicGradeName :'N/A'}"></c:out>
                       </td>
                    </tr>
                    </c:forEach>
     </table></div>
     </c:if></td></tr></table>
     <table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>
     			<tr><td>&nbsp;</td></tr>
                <tr><td align='center' valign='middle'></td>
                <c:if test="${fn:length(homeworkReportList) <= 0}">
			          
                	<td align='center' valign='middle' class="status-message">Tests Not yet Submitted</td>
                  </c:if>
            <td align='center' valign='middle'>
            </td></tr></table>
