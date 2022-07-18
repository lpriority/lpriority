<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<table border="0"  width="100%">
	<tr class="text">							
		<td width="20%" align="left"><b>Class</b></td>
		<td width="20%" align="left"><b>Period</b></td>
		<td width="20%" align="left"><b>Start Time</b></td>
		<td width="20%" align="left"><b>End Time</b></td>
		<td width="20%" align="left"><b>Teacher Name</b></td>
	</tr> 
<c:forEach items="${childClasses}" var = "class">
	<tr class="txtStyle">
		<td width="20%" align="left"><a href="javascript:void(0);" onclick="getChildCompositeChart(${class.classStatus.csId})">${class.classStatus.section.gradeClasses.studentClass.className}</a></td>
		<td width="20%" align="left">${class.periods.periodName}</td>
		<td width="20%" align="left">${class.periods.startTime}</td>
		<td width="20%" align="left">${class.periods.endTime}</td>
		<td width="20%" align="left">	
			${class.classStatus.teacher.userRegistration.title}
			${class.classStatus.teacher.userRegistration.firstName}${class.classStatus.teacher.userRegistration.lastName}
		</td>
	</tr>
</c:forEach>
</table>