<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form:form id="formativeAssessmentForm"  action="saveFormativeAssessment.htm" modelAttribute="stemList" method="post" enctype="multipart/form-data">
	<table align="center" style="padding-top: 2em;">	
		<c:forEach var="main" items="${stemFormativeAssGradeList}" varStatus="status">
			<c:set var="check" value=""></c:set>
			<c:set var="forId" value="0"></c:set>
			<c:set var="forStatus" value="inactive"></c:set>
			<c:forEach var="sub" items="${stemList.formativeAssessmentsUnitWise}">
			
				<c:if test="${main.formativeAssessments.formativeAssessmentsId == sub.formativeAssessments.formativeAssessmentsId}">					
					<c:set var="forId" value="${sub.formativeAssessmentsUnitWiseId}"></c:set>
					<c:set var="forStatus" value="${sub.status}"></c:set>
					<c:if test="${sub.status == 'active'}">
						<c:set var="check" value="checked"></c:set>
					</c:if>
				</c:if>
			</c:forEach>
			<input type="hidden" id="formativeUnitId${status.index}" name="formativeUnitId${status.index}" value="${forId}"/>
			<input type="hidden" id="formativeUnitStatus${status.index}" name="formativeUnitStatus${status.index}" value="${forStatus}"/>
			<tr>
				<td  align="center" valign="middle" style="padding-bottom : 1em;">						
					<input type="checkbox" id="checkIdfa${status.index}" name="checkIdfa${status.index}" 
						class="checkbox-design" ${check} onclick="updateFormativeUnit(this,${status.index})" style="position: relative; ">
						<label for="checkIdfa${status.index}" class="checkbox-label-design"></label>
						
					<input type="hidden" id="formativeAssessmentId${status.index}" name="formativeAssessmentId${status.index}" 
						value="${stemFormativeAssGradeList[status.index].formativeAssessments.formativeAssessmentsId}">
				</td>		
				<td valign="top">
					<button onclick="displayAssessmentDetail(${stemFormativeAssGradeList[status.index].formativeAssessments.formativeAssessmentsId});return false;" 
						style="font-size: 16px;font-weight:bold;background-color: rgb(0, 197, 222);color: black;text-shadow:rgb(191, 191, 191) 0px 1px 1px;">
						${stemFormativeAssGradeList[status.index].formativeAssessments.title}
					</button>    
				</td>		
			</tr> 
			
		</c:forEach>		
	</table>  
	<div id="formativeAssessmentDailog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title="">
  		<div id ="assessmentDetail"></div>
  	</div>
  <div class="active-users-div" id="tab6-active-users-div"></div>
</form:form>