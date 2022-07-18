<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet"
	href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeIn();
		$('#returnMessage').fadeOut(4000);
	});
</script>
<c:if test="${fn:length(studentActivities) gt 0}">
	<div align="left"><c:if test="${classView == 'yes'}"><h3 style="color: #10646D; padding-left: 2em">Class View</h3></c:if></div>
	<table>
		<c:forEach items="${studentActivities}" var="studentActivity"
			varStatus="index">
			<c:if test="${index.index%7 == 0 }">
				<tr>
			</c:if>
			<td style="width: 5em; height: 1.5em"><c:set var="iconStyle"
					value="" /> 
				<c:if test="${classView == 'yes'}"><c:set var="studentName" value="Student Name : ${studentActivity.student.userRegistration.firstName} ${studentActivity.student.userRegistration.lastName}"></c:set></c:if>
				<fmt:formatDate pattern="MM/dd/yyyy" var="submitDate" value="${studentActivity.readRegMaster.createDate}" />
				<c:set var="titleContent" value="${studentName }
							Activity : ${studentActivity.readRegActivity.actitvity}
		   					Book Title : ${studentActivity.readRegMaster.bookTitle}
		   				    Author : ${studentActivity.readRegMaster.author}
		   				    SubmitDate : ${submitDate}
		  				    Number of Pages : ${studentActivity.readRegMaster.numberOfPages}
		   				    Points earned: ${studentActivity.pointsEarned}  (pages of the book = ${studentActivity.readRegMaster.readRegPageRange.range} x Activity Value = ${studentActivity.readRegActivity.activityValue } x Rubric value = ${studentActivity.readRegRubric.score} )"></c:set>	
					
				<c:choose>
					<c:when
						test="${studentActivity.readRegActivity.actitvity == 'review' }">
						<i class="ion-chatbox-working" class="tooltip" style="padding: 0.2px 10px;font-size: 40px;color: #5d9c13;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"
							title="${titleContent }"
							onclick="getActivityDetails('${studentActivity.readRegActivityScoreId}','${studentActivity.readRegMaster.titleId}')"></i>
					</c:when>
					<c:when
						test="${studentActivity.readRegActivity.actitvity == 'create a quiz' }">
						<i class="fa fa-list-ol" class="tooltip"
							style="padding: 0.2px 10px;font-size: 33px;color:#88523e;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"
							title="${titleContent }"
							onclick="getActivityDetails('${studentActivity.readRegActivityScoreId}','${studentActivity.readRegMaster.titleId}')"></i>
					</c:when>
					<c:when
						test="${studentActivity.readRegActivity.actitvity == 'retell' }">
						<i class="ion-ios-mic" class="tooltip"
							style="padding: 0.2px 10px;font-size: 40px;color:#3F51B5;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"
							title="${titleContent }"
							onclick="getActivityDetails('${studentActivity.readRegActivityScoreId}','${studentActivity.readRegMaster.titleId}')"></i>
					</c:when>
					<c:when
						test="${studentActivity.readRegActivity.actitvity == 'take a quiz' }">
						<i class="ion-clipboard" class="tooltip"
							style="padding: 0.2px 10px;font-size: 40px;color: #0078af;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"
							title="${titleContent }"
							onclick="getActivityDetails('${studentActivity.readRegActivityScoreId}')"></i>
					</c:when>
					<c:when
						test="${studentActivity.readRegActivity.actitvity == 'upload a picture' }">
						<i class="fa fa-upload" class="tooltip"
							style="padding: 0.2px 10px;font-size: 33px;color: #273f4a;cursor: pointer;text-shadow: 0 1px 2px rgb(154, 154, 154);"
							title="${titleContent }"
							onclick="getActivityDetails('${studentActivity.readRegActivityScoreId}','${studentActivity.readRegMaster.titleId}')"></i>
					</c:when>
				</c:choose>
				<c:set var="titleContent" value=""></c:set>
				</td>
			<c:if test="${index.index >=6 and index.index%7 == 6}">
				</tr>
			</c:if>
		</c:forEach>
	</table>
</c:if>
<c:if test="${fn:length(studentActivities) == 0}">
	<span id="returnMessage" class="status-message">Activities are
		not available</span>
</c:if>



