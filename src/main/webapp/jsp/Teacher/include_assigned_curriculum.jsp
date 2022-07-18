<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style type="text/css">
	
	.ui-dialog > .ui-widget-content {background: white;}
	.ui-widget {font-size:0.9em;font-family:'Segoe UI', Frutiger, 'Frutiger Linotype', 'Dejavu Sans', 'Helvetica Neue', Arial, sans-serif;}
	
	.ui-accordion > .ui-widget-content {background: #f4f9fd;}
	.ui-accordion .ui-accordion-icons{background: rgb(235, 244, 251);border: 1px solid #a0daf6;}
	.ui-accordion .ui-accordion-header{font-size:100%;}
	.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{border: 1px solid #0057AF;color:#0057AF;}
	.ui-state-active a, .ui-state-active a:link, .ui-state-active a:visited,.ui-state-hover a, .ui-state-hover a:hover, .ui-state-hover a:link, .ui-state-hover a:visited, .ui-state-focus a, .ui-state-focus a:hover, .ui-state-focus a:link, .ui-state-focus a:visited{color:#0057AF;}
	.ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button{font-size:1em;}
	.ui-widget-content{border: 1px solid #a0daf6;}
	.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus{border:1px solid #1880C9;color:#1880C9;}
</style>
<script type="text/javascript">
	$(function() {
		$("#lessonDetails").accordion({
			collapsible : true,
			active : false
		});
	});
</script>
<c:if test="${fn:length(assignedCurriculum) > 0}">
<div id="lessonDetails" align="left"
	style="padding-left: 8.5cm; padding-top: 1cm; width: 50%">	
		<c:forEach varStatus="lNumber" items="${assignedCurriculum}"
			var="assignedLessons">
			<h3>${assignedLessons.lesson.unit.unitNo}.${assignedLessons.lesson.lessonNo}
				${assignedLessons.lesson.lessonName}</h3>
			<div id="detailsDiv${assignedLessons.lesson.lessonId}"
				style="display: none; padding-left: 1cm;" align="left" class="txtStyle">
				<table><tr><td width='150' align='left' class="tabtxt">Unit Name</td><td width='10' align='left'>:</td><td width='150' align='left' class="txtStyle"> ${assignedLessons.lesson.unit.unitName} </td></tr> 
				<tr><td width='150' align='left' class="tabtxt">Assigned Date</td><td width='10' align='left'>:</td><td width='150' align='left' class="txtStyle">${assignedLessons.assignedDate}</td></tr> 
				<tr><td width='150' align='left' class="tabtxt">Due Date</td><td width='10' align='left'>:</td><td width='150' align='left' class="txtStyle">${assignedLessons.dueDate}</td></tr>
				<tr><td width='150' align='left' class="tabtxt">Lesson Objective </td><td width='20' align='left'>:</td><td width='150' align='left' class="txtStyle">${assignedLessons.lesson.lessonDesc}</td></tr>
				<c:if test="${assignedLessons.assessmentStatus == 'true'}">
				<c:choose>
					<c:when test="${user == 'teacher'}">
							<c:set var="assessmentPath" value="showAssignedAssessments.htm"></c:set>
							<c:set var="homeworkPath" value="gotoCurrentHomework.htm"></c:set>
					</c:when>
					<c:when test="${user == 'student above 13' || user == 'student below 13'}">
							<c:set var="assessmentPath" value="goToStudentAssessmentsPage.htm"></c:set>
							<c:set var="homeworkPath" value="goToStudentHomeworksPage.htm"></c:set>
					</c:when>
					<c:when test="${user == 'parent'}">
							<c:set var="assessmentPath" value="childAssessments.htm"></c:set>
							<c:set var="homeworkPath" value="getChildCurrentHomeworks.htm"></c:set>
					</c:when>
				 </c:choose>
					<tr><td width='150' align='left' class="tabtxt">Assessments</td><td width='10' align='left'>:</td><td width='150' align='left' class="txtStyle"><a href="${assessmentPath}" target="_blank">yes</a></td></tr>
				</c:if>
				<c:if test="${assignedLessons.homeworkStatus == 'true'}">
					<tr><td width='150' align='left' class="tabtxt">Homeworks</td><td width='20' align='left'>:</td><td width='150' align='left' class="txtStyle"> <a href="${homeworkPath}" target="_blank">yes</a></td>
				</c:if>
				<tr>
				<c:if test="${fn:length(assignedLessons.lessonPathses) gt 0}">
					<td width='150' align='left' class="tabtxt">Files</td><td width='10' align='left'>:</td> </c:if>
					<td width='150' align='left' class="txtStyle">
				<c:forEach varStatus="aNumber"
					items="${assignedLessons.lessonPathses}" var="lessonPaths">
				<c:set var="lesssonPath" value="${fn:split(lessonPaths.lessonPath, '\\\\')}"/>
				<a href="downloadFile.htm?filePath=${filePath}${lessonPaths.lessonPath}">${lesssonPath[fn:length(lesssonPath)-1]}</a> <br>
				</c:forEach>
				</td>
				</tr>
				 <tr>
				<c:if test="${fn:length(assignedLessons.activityList) gt 0}">
				<td width='150' align='left' class="tabtxt"> Activities </td><td width='10' align='left'>:</td></c:if>
				<c:forEach varStatus="aNumber"
					items="${assignedLessons.activityList}" var="activityList">
					<td width='150' align='left' class="txtStyle"><font
						color="${activityList.activity.userRegistration.user.userTypeid == 2 ? 'blue' : 'black' }">
						${activityList.activity.activityDesc}</font></td>
					
				</c:forEach>
				</tr>
				</table>
			</div>
		</c:forEach>	
</div>
</c:if>
<c:if test="${fn:length(assignedCurriculum) == 0}">
	<div align="center" style="padding: 15px;">
		<label class="status-message">No Curriculum Assigned</label>
	</div>
</c:if>