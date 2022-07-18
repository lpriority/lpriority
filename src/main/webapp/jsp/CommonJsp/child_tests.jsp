<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function () {
	 $('#status').fadeOut(4000);
});
</script>
<table><tr><td>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
<c:if test="${fn:length(studentTests) > 0 }">
<c:set var="bor" value="0"></c:set>
<c:set var="de" value="des"></c:set>
</c:if>
</td></tr></table>
<table border="${bor}" class="${de}"><tr><td>
<div class="Divheads">
 <table style="width: 100%">
	<tr>
		<td width="120" height="0" align="center" class="tabtxt">
			<b>Select</b></td>
		<td width="120" height="0" align="center" class="tabtxt"><b>Class </b>
		</td>
		<td width="150" height="0" align="center" class="tabtxt"><b>AssignmentTitle</b>
		</td>
		<td width="150" height="0" align="center" class="tabtxt"><b>Due
			Date</b></td>
		<td width="207" height="0" align="center" class="tabtxt"><b>Instructions</b></td>
		<td width="194" height="0" align="center" class="tabtxt"><b>Test
			Type</b></td>
		<td width="132" height="0" align="center" class="tabtxt">
		</td>
	</tr></table></div>
                    
                <div style="padding: 2px 5px 2px 5px"><table> 
	   <tr><td height=10 colSpan=30>&nbsp;</td></tr> 
	<c:forEach items="${studentTests}" var="studTests"
		varStatus="testCount">
		<tr>
			<td width="120" height="0" align="center" class="txtStyle"><input
				type="radio" class="radio-design" name="checkbox2" 
				id="studentAssignmentId${testCount.index}"
				value="${studTests.studentAssignmentId}"
				onClick="getChildTestQuestions(${testCount.index},${studTests.assignment.assignmentId})" /><label for="studentAssignmentId${testCount.index}" class="radio-label-design"></label></td>
			<td width="120" height="0" align="center" class="txtStyle"><c:out
					value="${studTests.assignment.classStatus.section.gradeClasses.studentClass.className}"></c:out></td>
			<td width="150" height="0" align="center" class="txtStyle"><c:out
					value="${studTests.assignment.title}"></c:out></td>
			<td width="150" height="0" align="center" class="txtStyle"><c:out
					value="${studTests.assignment.dateDue}"></c:out></td>
			<td width="207" height="0" align="center" class="txtStyle"><c:out
					value="${studTests.assignment.instructions}"></c:out></td>
			<td width="194" height="0" align="center" class="txtStyle"><c:out
					value="${studTests.assignment.assignmentType.assignmentType}"></c:out>
			</td>
			<td width="132" height="0" align="center" class="txtStyle">
				<input type="hidden" id="usedFor${testCount.index}"
				value="${studTests.assignment.usedFor}" /> <input
				type="hidden" id="assignmentTypeId${testCount.index}"
				value="${studTests.assignment.assignmentType.assignmentTypeId}" />
				<input type="hidden" id="createdBy${testCount.index}"
				value="${studTests.assignment.createdBy}" />
			</td>
			<td>
				<div id="dialog${testCount.index}"
					style="display: none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"
					title="">
					<iframe id="iframe${testCount.index}" frameborder="0"
						scrolling="no" width="100%" height="95%" src="" style=""></iframe>
				</div>
			</td>
		</tr>
	</c:forEach></table></div>
	<c:if test="${fn:length(studentTests) <= 0 && tab != 'current homeworks'}">
		<tr>
			<td align="center" colspan="4">
				<c:choose>
				    <c:when test="${usedFor == 'homeworks'}">
				    	<span id="status" class="status-message">No Homeworks are Assigned today.</span>
				    </c:when>
				    <c:otherwise>
				    	<span id="status" class="status-message">No Assignments are Assigned today.</span>
				    </c:otherwise>
		        </c:choose>
				
			</td>
			<td colspan="2"></td>
		</tr>
	</c:if>
</td></tr></table>