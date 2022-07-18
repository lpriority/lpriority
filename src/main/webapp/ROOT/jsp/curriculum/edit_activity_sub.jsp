<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>

	$(document).ready(function () {
	 	$('#rMessage').fadeOut(4000);
	});
</script>
	<table align="center">
		<tr>
			<td width="562" align="center">&nbsp;</td>
		</tr>
		<tr>
			<td width="562" align="center">&nbsp;</td>
		</tr>
	</table>
	<c:if test="${isError != true}">
		<table align="center">
		<%int i = 1; %>
			<c:forEach var="aNumber" begin="0" end="${noOfActivities}">
			<tr>
				<td width="562" align="center" valign="top">
					<font color="aeboad" size="3">Activity Description :&nbsp;&nbsp;</font>			
					<form:textarea  id="activityDescs${aNumber}" path="editActivity.activities[${aNumber}].activityDesc" required="required" cols="50" rows="4"/>
					<form:hidden path="editActivity.activities[${aNumber}].activityId"/>
					<form:hidden path="editActivity.activities[${aNumber}].lesson.lessonId"/>
					<form:hidden path="editActivity.activities[${aNumber}].userRegistration.regId"/>
				</td>
			</tr>	
			<tr>
				<td width="562" align="center">&nbsp;</td>
			</tr>  
			<%i++;%>
			</c:forEach>
			<tr>
				<td align="center" colspan="3">
					<input type="image" src="images/Common/submitChangesUp.gif" id="Submit"/> 
				</td>
			</tr> 
		</table> 
	</c:if>
	<c:if test="${isError}">
		<table align="center">
			<tr>
				<td width="562" align="center" id="rMessage"><font color="blue" size="3">${helloAjax}</font></td>
			</tr>
		</table>
	</c:if>