<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
    <c:when test="${noOfConQues gt 0}">
   		<table width="50%" class="des" align="center" style="padding-left:4em;">		
			<tr class="Divheads">
			    <th width="20%" align="center">S.No</th>
				<th width="60%" align="center" height="50">Goal Strategy</th>
				<th width="20%" align="center">Frequency %</th>
			</tr>
			<c:forEach var="sss" items="${sssSortedMap}"  varStatus="status">
				<tr>
				    <td valign="middle" align="center" class="txtStyle">
						${status.count}
					</td>
					<td valign="middle" align="left" class="txtStyle" height="40" width="20%">
						${sss.key}
					</td>
					<td valign="middle" align="center" class="txtStyle" width="20%">
						<fmt:formatNumber type="percent" value="${sss.value/noOfConQues}"/>
					</td>
				</tr>
			</c:forEach>
		</table>  
	</c:when>    
    <c:otherwise>
   		 <div class="label" align="center">No data found</div>
    </c:otherwise>   
 </c:choose>    