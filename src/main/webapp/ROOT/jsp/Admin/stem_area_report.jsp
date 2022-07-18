<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
    <c:when test="${noOfConQues gt 0}">
		<c:choose>
		    <c:when test="${areaValue=='stem'}">
		    		<table width="50%" class="des" align="center" style="padding-left:4em;">		
						<tr class="Divheads">
						    <th width="20%" align="center">S.No</th>
							<th width="20%" align="center" height="50">First ${LP_STEM_TAB} Area</th>
							<th width="5%" align="center"></th>
							<th width="20%" align="center">
								Second ${LP_STEM_TAB} Area
							</th>
							<th width="5%" align="center"></th>
							<th width="20%" align="center">
								Frequency %
							</th>
						</tr>
						<c:forEach var="area" items="${stemAnalytics}"  varStatus="status">
							<tr>
							    <td valign="middle" align="center" class="txtStyle">
									${status.count}
								</td>
								<td valign="middle" align="center" class="txtStyle" height="40" width="20%">
									${area.stemArea1}
								</td>
								<td width="5%">--</td>
								<td valign="middle" align="center" class="txtStyle" width="20%">
									${area.stemArea2}
								</td>
								<td width="5%">:</td>
								<td valign="middle" align="center" class="txtStyle" width="20%">
									${area.percentage}
								</td>
								
							</tr>
						</c:forEach>				
					</table>  
		    </c:when>    
		    <c:when test="${areaValue=='strands'}">
		    		<table width="38%" class="des" align="center" style="padding-left:4em;">		
						<tr class="Divheads">
						    <th width="20%" align="center">S.No</th>
							<th width="60%" align="left" style="padding-left:6em;" height="50">Strands</th>
							<th width="30%" align="center">Frequency %</th>
						</tr>
						<c:forEach var="area" items="${stemAnalytics}" varStatus="status">
							<tr>
								<td valign="middle" align="center" class="txtStyle">
									${status.count}
								</td>
								<td valign="middle" align="left" class="txtStyle" height="40">
									${area.strand}
								</td>
								<td valign="middle" align="center" class="txtStyle">
									${area.percentage}%
								</td>
							</tr>
						</c:forEach>				
					</table>  
		    </c:when>
		    <c:when test="${areaValue=='strategies'}">
		    		<table width="38%" class="des" align="center" style="padding-left:4em;">		
						<tr class="Divheads">
						    <th width="20%" align="center">S.No</th>
							<th width="60%" align="left" style="padding-left:6em;" height="50">Strategies</th>
							<th width="30%" align="center">Frequency %</th>
						</tr>
						<c:forEach var="area" items="${stemAnalytics}" varStatus="status">
							<tr>
								<td valign="middle" align="center" class="txtStyle">
									${status.count}
								</td>
								<td valign="middle" align="left" class="txtStyle" height="40">
									${area.strategy}
								</td>
								<td valign="middle" align="center" class="txtStyle">
									${area.percentage}%
								</td>
							</tr>
						</c:forEach>				
					</table>  
		    </c:when>        
		</c:choose>
    </c:when> 
    <c:otherwise>
   		 <div class="label" align="center">Shared Activities Not available to show</div>
    </c:otherwise>   
 </c:choose>    