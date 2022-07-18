<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form id="stemSharedActivitiesForm"  action="saveStemActivities.htm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="parentActId">
	<table width="100%">	
		<tr>
			<td class = "label">Score</td>
			<td class = "label" align="center">Description</td>
		</tr>
		<c:forEach var="ind" items="${stemPerIndicatorList}" varStatus="status">
			<tr>
				<td>${ind.legend.legendValue}</td>
				<td width="" class="label" style="padding-bottom: 10px;color: white;background-color: #02BED6;" 
				onmouseover="this.style.background='#1179A3'" onmouseout="this.style.background='#02BED6'">${ind.legend.legendName}</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</c:forEach>		
	</table>  
	
</form>