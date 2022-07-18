<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	<table align="center">	
		<c:forEach var="main" items="${legendSubCriteria}" varStatus="status">
			<tr>
				<td  align="center" valign="middle" style="padding-bottom : 2em;">						
					<input type="hidden" id="stemSubCriteriaId${status.index}" name=stemSubCriteriaId${status.index} value="${legendSubCriteria[status.index].legendSubCriteriaId}">
				</td>		
				<td valign="top" style="padding-bottom : 1em;">
					<button onclick="displayIndicators(${status.index});return false;" style="font-size: 20px;background-color: #02BED6;color: white;" 
						onmouseover="this.style.background='#1179A3'" onmouseout="this.style.background='#02BED6'">
						${legendSubCriteria[status.index].legendSubCriteriaName}
					</button>    
				</td>		
			</tr>
			
		</c:forEach>		
	</table>  
	<div id="rubricDailog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title="">
  		<div id ="rubricDiv"></div>
  	</div>