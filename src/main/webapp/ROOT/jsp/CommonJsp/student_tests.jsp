<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function () {
	 $('#returnMessage').fadeOut(3000);
});
</script>
<table>
<c:if test="${usedFor == 'rti' &&  tab != 'ViewWordWorkTests' }">
	<!-- <tr>
		<td width="100%" align="right">
			<a href="#" onclick="openVideoDialog('student','access_fluency','videoDailog1')" class="demoVideoLink" style="padding-left:5px;">
			<i class="ion-videocamera" aria-hidden="true" style="font-size:26px;font-weight:bold;color:#e23c00;"></i>&nbsp;video for access Fluency</a>
		</td>
	</tr>	 -->							
</c:if>	
<tr><td>
<c:set var="bor" value="0"></c:set>
<c:set var="de" value=""></c:set>
<c:if test="${fn:length(studentTests) > 0 }">
<c:set var="bor" value="0"></c:set>
<c:set var="de" value="des"></c:set></c:if>
</td></tr></table>
<table border="${bor}" class="${de}" style="width: 100%;" align="center"><tr><td>
<c:if test="${fn:length(studentTests) > 0 }">
<table style="width: 100%">
	<tr><td width="100%" class="Divheads">
	<table style="width: 100%">
	<tr>
		<td width="5%" height="0" align="center"><b>Select</b></td>
		<td width="15%" height="0" align="center"><b>Class </b></td>
		<td width="25%" height="0" align="center"><b>AssignmentTitle</b></td>
		<td width="10%" height="0" align="center"><b>Due Date</b></td>
		<td width="25%" height="0" align="center"><b>Instructions</b></td>
		<td width="18%" height="0" align="center"><b>Test Type</b></td>
		<td width="2%" height="0" align="center"><b></b></td>
	</tr></table>
	</td></tr>
	<tr><td width="100%">
	<table width="100%" align="center" style="padding: 2px 5px 2px 5px"> 
	   <tr><td height=5 colSpan=20></td></tr> 
	<c:forEach items="${studentTests}" var="studTests"
		varStatus="testCount">
		<tr id="row${testCount.index}">
			<td width="5%" height="25" align="center">
				<div>
		       		<input id="studentAssignmentId${testCount.index}" type="radio" name="checkbox2" value="${studTests.studentAssignmentId}" class="radio-design" onClick="getTestQuestions(${testCount.index},${studTests.assignment.assignmentId},'${studTests.assignment.assignmentType.assignmentType}')">
		        	<label for="studentAssignmentId${testCount.index}" class="radio-label-design"></label>
		       </div>
       		</td>
			<td width="15%" height="0" align="center" class="txtStyle"><c:out
				value="${studTests.assignment.classStatus.section.gradeClasses.studentClass.className}"></c:out>
				<input type="hidden" name="eltTypeId" id="eltTypeId${testCount.index}" value="${studTests.assignment.autoAssignedSets.k1AutoAssignedSetId == '' ? 0 : studTests.assignment.autoAssignedSets.k1AutoAssignedSetId}"  />		
			</td> 
			<td width="25%" height="0" align="center" class="txtStyle"><c:out
					value="${studTests.assignment.title}"></c:out></td>
			<td width="10%" height="0" align="center" class="txtStyle">
			<fmt:formatDate pattern="MM/dd/yyyy" var="dueDate"
								value="${studTests.assignment.dateDue}" />
		    <c:out
					value="${dueDate}"></c:out></td>
			<td width="25%" height="0" align="center" class="txtStyle"><c:out
					value="${studTests.assignment.instructions}"></c:out></td>
			<td width="18%" height="0" align="center" class="txtStyle"><c:out
					value="${studTests.assignment.assignmentType.assignmentType}"></c:out>
			</td>
			<td width="2%" height="0" align="center" class="txtStyle">
				<input type="hidden" id="usedFor${testCount.index}"
				value="${studTests.assignment.usedFor}" /> <input
				type="hidden" id="assignmentTypeId${testCount.index}"
				value="${studTests.assignment.assignmentType.assignmentTypeId}" />
				<input type="hidden" id="createdBy${testCount.index}"
				value="${studTests.assignment.createdBy}" />
				<input type="hidden" id="recordTime${testCount.index}"
				value="${studTests.assignment.recordTime}" />
			</td>
			<td>
				<div id="dialog${testCount.index}"
					style="display: none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"
					title="">
					<iframe id="iframe${testCount.index}" frameborder="0"
						scrolling="no" width="100%" height="99%" src="" style=""></iframe>
				</div>
			</td>
		</tr>
	</c:forEach></table></c:if></td></tr>
	<c:if test="${fn:length(studentTests) <= 0 && tab != 'current homeworks'}">
		<tr width="100%" align="center">
			<td align="center" id="returnMessage">
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
  <tr><td><div id="videoDailog1" title="Access Fluency" style="background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"></div></td></tr></table></td></tr></table>