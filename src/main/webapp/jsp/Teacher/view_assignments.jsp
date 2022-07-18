<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
<c:if test="${fn:length(assessmentList) > 0}">
	<c:set var="de" value="des"></c:set>
	<c:set var="bor" value="0"></c:set>
</c:if>
<table border="${bor}" class="${de}">
	<tr>
		<td><c:if test="${fn:length(assessmentList) > 0}">
				<div class="Divheads">
					<table style="width: 100%">
						<tr>
							<c:if test="${tab == 'showAssigned'}">
								<td width='150' height='20' align='center' valign='middle'>Unit
									Name</td>
								<td width='160' height='20' align='center' valign='middle'>Lesson
									Name</td>
							</c:if>
							<td width='150' height='20' align='center' valign='middle'>Title</td>
							<td width='190' align='center'>Assignment Type</td>
							<td width='150' align='center'>Start Assignment</td>
							<td width='190' align='center'>Finish Assignment</td>
							<td width='101' align='center'>Edit</td>
							<td width='111' align='center'>Delete</td>
						</tr>
					</table>
				</div>
				<div style="padding: 2px 5px 2px 5px">
					<table>
						<c:forEach items="${assessmentList}" var="al" varStatus="cnt">
							<fmt:formatDate pattern="MM/dd/yyyy" var="startDate"
								value="${al.dateAssigned}" />
							<fmt:formatDate pattern="MM/dd/yyyy" var="endDate"
								value="${al.dateDue}" />
							<tr id="row${cnt.index}">
								<c:if test="${tab == 'showAssigned'}">
									<td width='150' height='30' align='center' valign='middle'
										class='txtStyle'><c:out
											value="${al.lesson.unit.unitName}"></c:out> <input
										type='hidden' id="unitno:${cnt.index} %>"
										value="${al.lesson.unit.unitNo}" /></td>
									<td width='160' height='30' align='center' valign='middle'
										class='txtStyle'><c:out value="${al.lesson.lessonName}"></c:out></td>
								</c:if>
								<td width='150' height='30' align='center' valign='middle'
									class='txtStyle' id="title:${cnt.index}"><c:out
										value="${al.title}"></c:out></td>
								<td width='190' height='30' align='center' valign='middle'
									class='txtStyle' id="assTypeId${cnt.index}"><c:out
										value="${al.assignmentType.assignmentType}"></c:out></td>		
								<td width='150' align='center' valign='middle' class='txtStyle'><input
									type="hidden" id="startdate${cnt.index}" value="${startDate}">${startDate}</td>
								<td width='200' align='center' valign='middle' class='txtStyle'><input
									type='text' name='enddate' id="enddate${cnt.index}"	value="${endDate}" style="width:80px;" readonly="readonly" /></td>
								<td width='101' align='center' valign='middle'><input
									type='checkbox' class="checkbox-design" align='center' valign='middle' name='edit'
									id="edit${cnt.index}" value="${al.assignmentId}"
									onclick='editAssignment(${cnt.index})' /><label for="edit${cnt.index}" class="checkbox-label-design"></label></td>
								<td width='111' align='center' valign='middle'><input
									type='checkbox' class="checkbox-design" align='center' valign='middle' name='delete'
									id="delete${cnt.index}" value="${al.assignmentId}"
									onClick='deleteAssignment(${cnt.index})' /><label for="delete${cnt.index}" class="checkbox-label-design"></label></td>
							<script>
				            	 $("#enddate"+${cnt.index}).datepicker({
				         	    	changeMonth: true,
				         	        changeYear: true,
				         	        showAnim : 'clip',
				         	        minDate : 0
				         	    });
			            	</script>
	            	</tr>
						</c:forEach>

					</table>
				</div>
			</c:if></td>
	</tr>
</table>
<table width='100%' height='25' border='0' cellpadding='0'
	cellspacing='0'>
	<tr>
		<td align='center' valign='middle'></td>
		<c:if test="${fn:length(assessmentList) <= 0}">
			<c:choose>
				<c:when test="${tab == 'showAssigned'}">

					<td align='center' valign='middle' class='status-message'>No
						Assessment has been assigned for this Grade and Class</td>
				</c:when>
				<c:otherwise>
					<td align='center' valign='middle' class='status-message'>No Literacy Development has
						been assigned for this Grade and Class</td>
				</c:otherwise>
			</c:choose>
		</c:if>
		<td align='center' valign='middle'></td>
	</tr>
</table>
