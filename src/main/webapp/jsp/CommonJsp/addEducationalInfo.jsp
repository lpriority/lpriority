<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Educational Info</title>
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/adminService1.js"></script>
<script type="text/javascript" src="/dwr/interface/myProfileService.js"></script>
<script type="text/javascript" src="resources/javascript/common/my_profile.js"></script>
<style>
.submitEducationInfoButton{
	color: white;
	height:30px; 
	width:150px; 
	font-size:14px;
	background-color: #663399;
	font-weight: 900;
	font-family: Gill Sans, Verdana;
}
</style>
<script>
$(document).ready(function(){
	var userTypeId = dwr.util.getValue('userTypeId');
	if(userTypeId == 5){
		var hiddenGradeId = dwr.util.getValue('hiddenGradeId');
		var studentId = dwr.util.getValue('userId');
		if(hiddenGradeId){
			$('#gradeId').val(hiddenGradeId).attr("selected", "selected");	
			$('#gradeId').val(hiddenGradeId).attr("disabled",true);
			loadSubjects();
			var studentClassesCallBack = function(list){
				for (i=0;i<list.length;i++){
				 if(list.length > 0)
					document.getElementById("submitDynamicContentDiv").style.visibility = "visible";
					var classId = list[i].gradeClasses.studentClass.classId;
					$('div#gradeClassesDiv input[type=checkbox]').each(function() {
						   if($(this).attr('id') == classId)	{
							   $(this).attr('checked',true);
						   } 
						});
					var div = $("<div id='div"+classId+"' align='center'>");
					var str = " <table width='60%' align='center' cellpadding='4' cellspacing='4'> <tr><td width='140' align='left'>"+list[i].gradeClasses.studentClass.className+"</td>";
					str +="<td width='200'><select id='gradeLevelId"+classId+"' name='gradeLevelId"+classId+"'>"+
		              "<option value='select'>Select Grade Level</option>"+
		              "<option value='1'>above grade level</option>"+
		              "<option value='2'>at grade level</option>"+
		              "<option value='3'>below grade level</option>"+
		              "</select></td>";
		            str += "<td width='140'><input type='image'  id=remove"+classId+" width='15' height='15' src='images/Common/word_remove_icon.jpg' onClick='removeDynamicFields("+classId+")'/></td>";
		      		str += "<td height=26 colSpan=3></td></tr></table>";
		            div.append(str);
		            div.append("</div>");
		            $("#dynamicContentDiv").append(div);
		            $('#gradeLevelId'+classId).val(list[i].gradeLevel.gradeLevelId).attr("selected", "selected");	
				}
			}
			 if(studentId){
				myProfileService.getStudentClasses(studentId,{
					callback : studentClassesCallBack
				});
			} 
		}
	}
});
</script>
</head>
<body>
<input type="hidden" id="userTypeId" name="userTypeId" value="${userTypeId}" />
<input type="hidden" id="userId" name="userId" value="${userId}" />
<input type="hidden" id="hiddenGradeId" name="hiddenGradeId" value="${hiddenGradeId}" />
<table border=0 cellSpacing=0 cellPadding=0 width="100%" height=99>
       <tr>
	       <td>
		       <table width="100%" border="0" cellspacing="0" cellpadding="0" class="space-between" >
	            	<tr><td height=16 colSpan=3></td></tr> 
	            	<tr><td> 
		               	 <table table width="70%" border="0" cellspacing="0" cellpadding="0" >
		          			<tr>
								<th  height=30 width=120><font color="black" size="3"  class="label">Grade  </font></th>
								<td>
									<select	id="gradeId" name="gradeId" onchange="loadSubjects()" style="width:120px;" class="listmenu">
											<option value="select">select grade</option>
								 		<c:forEach items="${gradesLt}" var="gl">
											<option value="${gl.gradeId}">${gl.masterGrades.gradeName}</option>
										</c:forEach> 
									</select>
								</td>
							</tr>
		               	</table>
		              </td></tr>
		              <tr><td height=30 colSpan=30></td></tr>
		              <tr><td align="center" width="100%" >
		              <div id="gradeClassesDiv"></div>
		              <td></tr> 
		              <tr><td class="heading-up">
		              	<div id="dynamicContentDiv"></div>
		              <td></tr> 
		              <tr><td height=30 colSpan=30></td></tr>
		               <c:if test="${userTypeId eq '3'}">
			               <tr><td>
			              	 <div id="submitDynamicContentDiv" style="visibility:hidden;" align='center' width="250" > 
			              	</c:if>
			              	 <c:if test="${userTypeId eq '5'}">
			              	 <tr><td>
			              	 <div id="submitDynamicContentDiv" style="visibility:hidden;" align='center'> 
			              	</c:if>
			              	   <table width="40%" align="center"> 
			              	   <tr><td>
				              	<input  width="30"  type="button" class="button_green" value="Submit" onclick="submitEducationalInfo()" /> </td>
				                 <td><input  width="30"  type="button" class="button_green" value="Re-Set" onclick="resetEducationInfo()" /></td></tr>
				              	</table>
			              	</div>
			              </td></tr> 
			              <tr><td height=30 colSpan=30></td></tr>
			              <tr><td width="325" height="35" align="center" colspan="5">
	                     		<font color="blue" size="4"><b><div id="resultDiv" ></div></b></font>
	                      </td></tr> 
		      </table>	
	      </td>
   	</tr>
  </table>
	
</body>
</html>