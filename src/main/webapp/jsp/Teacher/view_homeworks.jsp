<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />

<table><tr><td>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
<c:if test="${fn:length(homeworkList) > 0}">
<c:set var="bor" value="0"></c:set>
<c:set var="de" value="des"></c:set>
</c:if>
</td></tr></table>
<table border="${bor}" class="${de}" align="center"><tr><td>
<c:if test="${fn:length(homeworkList) > 0}"><div class='Divheads' align="center">
 <table>
            <tr><td width='132' height='20' align='center' valign='middle'>Select</td>
            <td width='132' height='20' align='center' valign='middle'>Unit Name</td>
            <td width='134' height='20' align='center' valign='middle'>Lesson Name</td>
            <td width='133' height='20' align='center' valign='middle'>Title</td>
             <td width='133' height='20' align='center' valign='middle'>Instruction</td>
            <td width='133' height='20' align='center' valign='middle'> Assignment Type</td>
            <td width='170' align='center'>Assigned Date</td>
            <td width='155' align='center'>Due Date</td>
            </tr></table></div>
            
            <div class="DivContents"><table><tr><td>&nbsp;</td></tr> 
            <c:forEach items="${homeworkList}" var="al" varStatus="i">
            	<fmt:formatDate pattern="MM/dd/yyyy" var="startDate"
								value="${al.dateAssigned}" />
							<fmt:formatDate pattern="MM/dd/yyyy" var="endDate"
								value="${al.dateDue}" />
                 
                  <tr><td align='center' width='132'><input type='checkbox' class="checkbox-design" name='checkAssigned' id="checkAssigned${i.index}"  onClick="getHomeworkQuestions(${al.assignmentId},${al.assignmentType.assignmentTypeId},${i.index})" <c:if test="${al.assignmentType.assignmentTypeId == 18}"> disabled='disabled'</c:if> /><label for="checkAssigned${i.index}" class="checkbox-label-design"></label></td>
                  <td width='132' height='30' align='center' valign='middle' class='txtStyle'><c:out value="${not empty al.lesson.unit.unitName ? al.lesson.unit.unitName : 'N/A'}"></c:out>
                  <input type='hidden' id="unitno:${i.index} %>" value="${not empty al.lesson.unit.unitNo ? al.lesson.unit.unitNo : 'N/A'}"/></td>
                    <td width='134' height='30' align='center' valign='middle' class='txtStyle'><c:out value="${not empty al.lesson.lessonName ? al.lesson.lessonName : 'N/A'}"></c:out></td>
                    <td width='133' height='30' align='center' valign='middle' class='txtStyle' id="title:${i.index}"><c:out value="${al.title}"></c:out></td>
                    <td width='133' height='30' align='center' valign='middle' class='txtStyle' id="instruction:${i.index}"><c:out value="${al.instructions}"></c:out></td>
                    <td width='133' height='30' align='center' valign='middle' class='txtStyle' id="assignmentType:${i.index}"><c:out value="${al.assignmentType.assignmentType}"></c:out></td>
                    <td width='170' align='center' valign='middle' class='txtStyle'>${startDate}</td>
                    <td width='155' align='center' valign='middle' class='txtStyle'>${endDate}</td>                   
                    </tr>
			</c:forEach>
                                                        
              </table></div>
           </c:if></td></tr></table>
                <table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>
                <tr><td align='center' valign='middle'></td>
                <c:if test="${fn:length(homeworkList) <= 0}">
			          
                <td align='center' valign='middle' class='tabtxt'><font color="blue">No homework has been assigned for this Grade and Class</font></td>
                  </c:if>
            <td align='center' valign='middle'>
            </td></tr></table>
            
            
           
