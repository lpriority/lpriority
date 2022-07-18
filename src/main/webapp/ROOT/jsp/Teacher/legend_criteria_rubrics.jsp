<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<c:if test="${fn:length(legendrList) gt 0}">
		<table width="100%">	
			<tr>
				<td class = "label">Score</td>
				<td class = "label" align="center">Description</td>
			</tr>
			<c:forEach var="legend" items="${legendrList}" varStatus="status">
				<tr>
					<td>${legend.legendValue}</td>
					<td width="" class="label" style="padding-bottom: 10px;color: white;background-color: #02BED6;" 
					onmouseover="this.style.background='#1179A3'" onmouseout="this.style.background='#02BED6'">${legend.legendName}</td>
				</tr>
				<tr>
					<td></td>
				</tr>
			</c:forEach>		
		</table> 
	</c:if> 
	<c:if test="${fn:length(legendrList) eq 0}">
		<table width="100%">	
			<tr>
				<td class = "label">No Rubric values to display</td>
			</tr>
		</table>
	</c:if>