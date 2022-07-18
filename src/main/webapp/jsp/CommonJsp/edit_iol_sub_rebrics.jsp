<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html>
<script type="text/javascript">
function updateIOLRubrics(){
	var formObj = document.getElementById("editIOLForm");
	var formData = new FormData(formObj);
	$("#loading-div-background").show();
	   $.ajax({  
	    	url : "updateIOLRubrics.htm",
	    	type : "POST",
	      	data: formData,
	      	mimeType:"multipart/form-data",
	   	contentType: false,
	   	cache: false,
	   	processData:false
	   }).done(function(data) {  
	   	$("#loading-div-background").hide();
	   	var res = JSON.parse(data);
	   	systemMessage(res.status);
	});
}
</script>
<body>
	<c:if test="${count ne 0}">	
	<form:form id="editIOLForm" name="editIOLForm"	action="updateIOLRubrics.htm" modelAttribute="stemUnitActList" method="post">	
		<table width="90%" height="100%">
			<tr><td>	
				<table border="0" cellSpacing=10 cellPadding=10 width="80%" align="center" height="100%" style="background-color: white;">  
					<tr bgcolor="#f7efcf">
						<th class="label">Score</th>
						<th class="label">Rubric</th>					
					</tr>
					<c:forEach var="i" begin="0" end="${count-1}" step="1" varStatus="status">
				  		<tr>
							<td align="center" >
								${stemUnitActList.legendList[i].legendValue}
								<form:hidden path="legendList[${i}].legendId" id="id${i}" />
								<form:hidden path="legendList[${i}].legendValue" id="value${i}" />
								<form:hidden path="legendList[${i}].legendSubCriteria.legendSubCriteriaId" id="subId${i}" />
								<form:hidden path="legendList[${i}].createdBy.regId" id="regId${i}" />
								<form:hidden path="legendList[${i}].status" id="status${i}" />
								<form:hidden path="legendList[${i}].color" id="color${i}" />
								<form:hidden path="legendList[${i}].isDefault" id="isDefault${i}" />																								
							</td>
							<td align="center" >
								<form:textarea path="legendList[${i}].legendName" id="name${i}" required="required" rows="3" cols="80" onblur="updateRubric(${i})" />
							</td>						
						</tr>
					</c:forEach>
			 	</table>
		 	</td></tr>
		 	<tr><td height="20px;">&nbsp;</td></tr>
		 	<tr><td>	
			 	<table align="center">
			 		<tr>
						<td><div class="button_green" height="52" width="50" onclick="updateIOLRubrics()">Update</div></td>
					</tr>
			 	</table>
		 	</td></tr>
	 	</table>
 	</form:form>
 	</c:if>
 	<c:if test="${count eq 0}">	
 		<label class="label">No Data Found</label>
 	</c:if>
	</body>
</html>