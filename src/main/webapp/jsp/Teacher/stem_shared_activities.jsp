<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form id="stemSharedActivitiesForm"  action="saveStemActivities.htm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="parentActId">
	<c:if test="${noOfSharedAct gt 0}">
	<table width="70%" align="center">		
		<tr>
			<th width="10%" class="label" align="right"></th>
			<th width="30%" class="label" align="center">
				Description
			</th>
			<th width="30%" class="label" align="center">
				Link
			</th>
			<!-- <th width="20%" class="label" align="center">
				File
			</th> -->
		</tr>		
		<c:forEach var="qNumber" begin="0" end="${noOfSharedAct-1}">
			<tr>
				<td valign="middle" width="10%" class="label" align="right">
					<input type="radio" name="activity" style="width: 15px;height: 15px;" value="${qNumber}"/>
					<input type="hidden" id="activity${qNumber}" 
						style="width: 15px;height: 15px;" value="${stemSharedAct[qNumber].stemActivityId}"/>
				</td>
				<td width="" align="center">
					<textarea rows="2" cols="30" readonly="readonly" id="shareDesc${qNumber}">${stemSharedAct[qNumber].activityDesc}</textarea>
				</td>
				<td width="" align="center">
					<textarea rows="2" cols="20" id="shareLink${qNumber}" readonly="readonly">${stemSharedAct[qNumber].activityLink}</textarea>
				</td>
				<%-- <td width=""><label id="shareFile${qNumber}">${stemSharedAct[qNumber].fileName}</label></td> --%>
			</tr>
		</c:forEach>				
	</table>  
	<table width="90%" align="center" style="padding-top: 50px;">
		<tr>
			<td  height="10" align="center" valign="middle"> 
	        	<div class="button_green" align="right" onclick="addSharedActivity()">Add Activity</div> 
	        </td>	 	    	       
	    </tr>
	</table>
	</c:if>
	<c:if test="${noOfSharedAct eq 0}">
		<div class="label">Shared Activities Not available to show</div>
	</c:if>
</form>