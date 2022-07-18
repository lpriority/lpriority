<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table border="0" width="100%">
	<tr class="text">
		<td align="center">Class</td>
		<td align="center">Lesson</td>
		<td align="center">Assignment Type</td>
		<td align="center">Start Date</td>
		<td align="center">Due Date </td>
	</tr>
<c:forEach items="${childTests}" var="test">
	<c:if test="${test.usedFor eq 'assessments'}">
		<tr class="txtStyle" >
			<td align="center">
				${test.classStatus.section.gradeClasses.studentClass.className}
			</td>
			<td align="center">${test.lesson.lessonName}</td>
			<td align="center">${test.assignmentType.assignmentType}</td>
			<td align="center">${test.dateAssigned}</td>
			<td align="center">${test.dateDue}</td>
		</tr>
	</c:if>
</c:forEach> 
</table>