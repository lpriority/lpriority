<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
/*   $( function() {
	  viewSubCriteria(0);
  } ); */
</script>
<input type="hidden" id="count" value="${fn:length(legendCriteria)}">
<table width="100%" align="center">
<tr><td>
<table width="80%" align="center" style="padding-top: 20px;">	
	<tr>
		<c:forEach var="qNumber" begin="0" end="${fn:length(legendCriteria)-1}">
			<td  align="center" >
				<div class="button_green" align="center" id="lCriteriaBut${qNumber}" onclick="viewSubCriteria(${qNumber})">${legendCriteria[qNumber].legendCriteriaName}</div>
				<input type="hidden" id="lCriteriaId${qNumber}" value="${legendCriteria[qNumber].legendCriteriaId}">
			</td>
		</c:forEach>
	</tr>		
</table> </td></tr></table>
<table align="center"><tr><td> 
<div id="subCriteriaDiv" style="padding-top: 30px;"></div>
</td></tr></table>	
