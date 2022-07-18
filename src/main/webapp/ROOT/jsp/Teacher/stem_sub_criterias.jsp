<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form:form id="stemFiveCForm"  action="saveStemFiveC.htm" modelAttribute="stemList" method="post" enctype="multipart/form-data">
	<table align="center">	
		<c:forEach var="main" items="${legendSubCriteria}" varStatus="status">
			<c:set var="check" value=""></c:set>
			<c:set var="indId" value="0"></c:set>
			<c:set var="indStatus" value="inactive"></c:set>
			<c:forEach var="sub" items="${stemList.stemUnitPerformanceIndicator}">
			
				<c:if test="${main.legendSubCriteriaId == sub.legendSubCriteria.legendSubCriteriaId}">					
					<c:set var="indId" value="${sub.stemUnitPerformanceIndicatorId}"></c:set>
					<c:set var="indStatus" value="${sub.status}"></c:set>
					<c:if test="${sub.status == 'active'}">
						<c:set var="check" value="checked"></c:set>
					</c:if>
				</c:if>
			</c:forEach>
			<input type="hidden" id="indicatorId${status.index}" name="indicatorId${status.index}" value="${indId}"/>
			<input type="hidden" id="indicatorStatus${status.index}" name="indicatorStatus${status.index}" value="${indStatus}"/>
			<tr>
				<td  align="center" valign="middle" style="padding-bottom : 1em;">						
					<input type="checkbox" id="checkId${status.index}" name="checkId${status.index}" value="${legendSubCriteria[qNumber].legendSubCriteriaId}" 
						class="checkbox-design" ${check} onclick="updateIndicator(this,${status.index})">
						<label for="checkId${status.index}" class="checkbox-label-design"></label>
						
					<input type="hidden" id="stemSubCriteriaId${status.index}" name=stemSubCriteriaId${status.index} value="${legendSubCriteria[status.index].legendSubCriteriaId}">
				</td>		
				<td valign="top">
					<button onclick="displayIndicators(${status.index});return false;" style="font-size: 20px;background-color: #02BED6;color: white;" 
						onmouseover="this.style.background='#1179A3'" onmouseout="this.style.background='#02BED6'">
						${legendSubCriteria[status.index].legendSubCriteriaName}
					</button>    
				</td>		
			</tr>
			
		</c:forEach>	
		<c:if test="${fn:length(legendSubCriteria) eq 0 }">
			No Data available
		</c:if>
			
	</table>  
	<div id="stemPerIndicator"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title="">
  		<div id ="indicatorDiv"></div>
  	</div>
</form:form>