<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="resources/javascript/CommonValidation.js"></script>
<c:if test="${noOfLessons >= 0}">
	<table class="des" border=0><tr><td><div class='Divheads'>
		<table align="center" width="100%">
			<tr>
			    <td width='50' align='center'>S.No</td>
				<td align="center" width="180" >Lesson Name</td>
				<td align="center" width="250">Objective/Outcome</td>
				<td align="center" width="120">Edit Activities</td>
				<td align="center" width="280">Add/Edit Assessments</td>
				<td align="center" width="100">Remove</td>
				<td align="left" width='100'>&nbsp;</td>
			</tr></table></div>
			<div class='DivContents'><table>
		    <tr><td height="10" colspan="" >&nbsp;</td></tr>
 		 <c:forEach var="i" begin="0" end="${noOfLessons}" step="1">
 		        <c:set var="lNumber" value="${noOfLessons-i}" scope="page"></c:set>
				<tr>
				    <td width='50' align='center'>${i+1} .</td>																
					<td align="center" width="180"><form:input id="lessonName${lNumber}" path="editLessons.lessons[${lNumber}].lessonName" required="required" onchange="lessonAutoUpdate(${lNumber})"/></td>
					<td align="center" width="280">
						<form:textarea  id="lessonDesc${lNumber}" path="editLessons.lessons[${lNumber}].lessonDesc" required="required" onchange="lessonAutoUpdate(${lNumber})" />
						<form:hidden path="editLessons.lessons[${lNumber}].lessonId" id="lessonId${lNumber}"/>
						<form:hidden path="editLessons.lessons[${lNumber}].lessonNo" id="lessonNo${lNumber}"/>
						<form:hidden path="editLessons.lessons[${lNumber}].unit.unitId" id="unitId${lNumber}"/>
						<form:hidden path="editLessons.lessons[${lNumber}].userRegistration.regId" id="regId${lNumber}"/>
					</td>
					<td align="left" width="100"><i class="fa fa-pencil-square-o" aria-hidden="true"  onclick="dynamicAdd(${lNumber})"  style="cursor: hand; cursor: pointer;font-size: 22px;font-weight:bold;color:#185C64;"></i></td>
					<td align="center" width="150"><a href='#' onclick="goToAssessmentsTab(${lNumber},'assessments')" class='subButtons subButtonsWhite medium' style='text-decoration: none;'>Assesments</a>&nbsp;<a href='#' onclick="goToAssessmentsTab(${lNumber},'rti')"  style="text-decoration: none;" class='subButtons subButtonsWhite medium'>Literacy</a>&nbsp;<a href='#' onclick="goToAssessmentsTab(${lNumber},'homeworks')"  style="text-decoration: none;" class='subButtons subButtonsWhite medium'>HomeWork</a></td>
					<td align="center" width="100"><i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeLesson(${lNumber})" id="remove${lNumber}" ></i></td>
		    		<td align="left" width="100"><div id="resultDiv${lNumber}"></div></td>	
		    		<script type="text/javascript">
						var lNumber = '${lNumber}';
						var regId = '${regId}';
						var id = document.getElementById("regId"+lNumber).value;
						if(id != regId){
							$('#lessonName'+lNumber).css({ 'color': '#8B8B8B','pointer-events': 'none' });
							$('#lessonDesc'+lNumber).css({ 'color': '#8B8B8B','pointer-events': 'none' });
							$('#remove'+lNumber).css({ 'color': '#8B8B8B','pointer-events': 'none' });
						}
	  				 </script>
				</tr>
		</c:forEach>   
	<tr><td height='10'></td></tr></table> </div></td></tr></table>
</c:if>



