<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form id="stemSharedActivitiesForm"  action="saveStemActivities.htm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="parentActId">
	<table width="100%">	
		<tr>
			<td class = "label">No</td>
			<td class = "label" align="center">Link</td>
		</tr>
		<c:forEach var="ipal" items="${iPalResources}" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td width="" align="center" class="label" style="padding-bottom: 10px;" 
					><a href="${ipal.resourcesLink}" target="_blank" onMouseOver="this.style.color='#800000'" onMouseOut="this.style.color=''">${ipal.resourcesLink}></a></td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</c:forEach>		
	</table>  
	
</form>