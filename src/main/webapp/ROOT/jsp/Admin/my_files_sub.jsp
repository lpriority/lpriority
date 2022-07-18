<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>

<script >
$(function() {	
	var teacherId;
	var fileType = document.getElementById('fileType').value;
	if(fileType=="private")
		teacherId = 0;
	else
		teacherId = document.getElementById('teacherId').value;
			loadFolders(teacherId);
});

</script>
</head>
<body>
  
			<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0 >
				<tr><td>
						
				</td></tr>
				<tr align="center"><td colspan="3"><font color="red" size="4px" ><label  id=returnMessage ></label></font></td></tr>
				<tr><td style="padding-top:1em;">
				<div style="width: 100%;">    
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
					<c:if test="${fileType == 'public'}">
						<td width="20%" align="left" valign="middle" class="label" style="font-size:12pt;">Teacher &nbsp;&nbsp;&nbsp;
							<select id="teacherId" style="width:120px;" name="teacherId"  onchange="loadFolders();" class="listmenu">
	                   			<option value="select" selected>Select Teacher</option>
	                   			<c:forEach items="${teachersList}" var="teacher">
	                   			<option value="${teacher.teacher.teacherId}" ${teacher.teacher.teacherId == teacherId ? 'selected="selected"' : ''}>${teacher.teacher.userRegistration.title} ${teacher.teacher.userRegistration.firstName} ${teacher.teacher.userRegistration.lastName}</option>
	                   			</c:forEach>
	               			</select>                        			
						</td>
						
						<td width="58%"   align="right" valign="middle">
							<div id="createFolderDiv" style="display: ${fileType == 'private'?'block':'none'};">
								<div class="button_green" align="right" onclick="adminCreateFolderDialog()">Create Folder</div>
							</div>
						</td>
						<td align="left" valign="middle" class="tabtxt"></td>  
					</c:if>
					<c:if test="${fileType eq 'private'}">
						<td width="20%" align="left" valign="middle" class="tabtxt"></td>  
						<td align="left" valign="middle" class="tabtxt"></td>  			
						<td width="58%" align="right" valign="middle">
							<div id="createFolderDiv" style="display: ${fileType == 'private'?'block':'none'};">
								<div class="button_green" align="right" onclick="adminCreateFolderDialog()">Create Folder</div>
							</div>
						</td>
						<td colspan="1" align="left" valign="middle" class="tabtxt"></td>  
					</c:if>
					<input type="hidden" id="fileType" name="fileType" value="${fileType}" />
					</tr>
					<tr>
						<td height="0" colspan="4" align="center" valign="top" style="">
							<div id="displayFolders"></div>	
						</td>
					</tr>
					<tr>
						<td height="2" colspan="2"><div id="browser"></div></td>
					</tr>
					</table></div></td></tr>
			</table>
			
</body>
</html>