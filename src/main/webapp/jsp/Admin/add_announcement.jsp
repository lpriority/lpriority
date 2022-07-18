
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Add/Edit Notifications</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script> 
<%@ include file="../CommonJsp/include_resources.jsp" %>
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet" href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<script type="text/javascript" src="resources/javascript/admin/announcements.js"></script>
<script type="text/javascript">
$(function() {
    $( "#announceDate" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
        minDate: 0
    });
    
    setValidation();

    $('#fileName').bind('change', function (e) { 
    	$('#downloadLink').remove();
    });
});

function setValidation(){
	 var userType = "${userReg.user.userType}";
	if(userType == 'admin')
  		$('#annoncementName').attr("onblur", "compareAnnouncement(this)");
	else
		$("#tableDiv").find("input,button,textarea,select").attr("disabled", "disabled");
}


</script>
<style>
.ui-datepicker{font-size:.8em;}
.ui-widget-header{background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00E3FF), color-stop(1,#03b7cf) );border:1px solid #36b3c4;text-shadow:0 1px 1px rgb(0, 48, 53);margin:-.12em;}
.ui-widget-overlay{background:rgba(81, 139, 146, 0.66);}
.announ-label{
	font-family: cursive;
	color: #155673;
	font-size: 18px;
}
</style>
</head>
<body>
<input type="hidden" name="userType" id="userType" value="${userReg.user.userType}" />
	<table width="100%" border=0 align="center" cellPadding=0 cellSpacing=0 style="padding-top: 1em;">
	    <tr><td width="100%" class="" style="">
			 <table align="center" width="90%"><tr><td>
			 	<div><table width="100%"><tr><td align="center">
			 			<c:choose>
							<c:when test="${userReg.user.userType == 'admin'}">
								<div class="announ-label">${announcement.announcementId > 0 ? 'Edit' : 'Add'} Notification </div>
							</c:when> 
							<c:otherwise>
							  <div class="announ-label">Notification Details</div>
							</c:otherwise>
						</c:choose>
				</td></tr>
			 </table>
			</div>
		 <div>
		  <table width="100%" id="tableDiv"><form:form id="announcementForm" modelAttribute="announcement" method="post" action="saveOrUpdateAnnouncement.htm">
		  <input type="hidden" id="operation" value="${operation}">
		  <tr>
			<td height="0" colspan="2" align="left" valign="top" class="">
				<table id="T3" width="100%" id='divtag' align="center">
						<tr>
							<td width="45%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle"></td>
						</tr>
						<tr>
							<td width="45%" height="20" align="right" valign="middle"><span
								class="tabtxt"><img src="images/Common/required.gif"> Notification Name</span></td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span
								class="tabtxt"> <form:input path="annoncementName" style="width:250px;"/><font color="red">
										<form:errors path="annoncementName"></form:errors>
								</font>
							</span></td>
						 </tr>
						 <tr>
							<td width="45%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle"></td>
						</tr>
						<tr>
							<td width="45%" height="20" align="right" valign="top"><span
								class="tabtxt"><img src="images/Common/required.gif"> Description</span></td>
							<td width="10%" height="20" align="center" valign="top">:</td>
							<td height="20" align="left" valign="middle"><span
								class="tabtxt"><form:textarea
										path="announceDescription" style="width:250px;height:50px;"></form:textarea> <font
									color="red"> <form:errors path="announceDescription"></form:errors>
								</font> </span></td>
						</tr>
						<tr>
							<td width="45%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle"></td>
						</tr>
						<tr>
							<td width="45%" height="20" align="right" valign="middle"><span
								class="tabtxt"><img src="images/Common/required.gif"> Announcement Date</span></td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle">
							<form:input	path="announceDate" readonly="true" value="${announcDate}"/><font
								color="red"> <form:errors path="announceDate"></form:errors>
							</font>
						</tr>
						<tr>
							<td width="45%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle"></td>
						</tr>
					<c:choose>
						<c:when test="${userReg.user.userType == 'admin'}">
							<tr>
								<td width="45%" height="20" align="right" valign="middle"><span
									class="tabtxt"><img src="images/Common/required.gif"> Attachment</span></td>
								<td width="10%" height="20" align="center" valign="middle">:</td>
								<td height="20" align="left" valign="middle">
									<form:input	type="file" path="fileName"/><font
										color="red"> <form:errors path="announceDate"></form:errors>
									</font>
									<c:if test="${not empty announceObj.fileName }">
										<a id="downloadLink" href="downloadFile.htm?filePath=${path}${announceObj.fileName}">Download</a>
									</c:if>
								</td>
							</tr>
							<tr>
								<td width="45%" height="10" align="right" valign="middle"></td>
								<td width="10%" height="10" align="center" valign="middle"></td>
								<td height="10" align="left" valign="middle"></td>
							</tr>
						</c:when> 
						<c:otherwise>
							<c:if test="${not empty announceObj.fileName }">
								<tr>
									<td width="45%" height="20" align="right" valign="middle"><span
										class="tabtxt"><img src="images/Common/required.gif"> Attachment</span></td>
									<td width="10%" height="20" align="center" valign="middle">:</td>
									<td height="20" align="left" valign="middle">
										<a href="downloadFile.htm?filePath=${path}${announceObj.fileName}">Download</a>
									</td>
								</tr>
								<tr>
									<td width="45%" height="10" align="right" valign="middle"></td>
									<td width="10%" height="10" align="center" valign="middle"></td>
									<td height="10" align="left" valign="middle"></td>
								</tr>
							</c:if> 
						</c:otherwise>
					</c:choose>				
						<tr>
							<td width="45%" height="20" align="right" valign="middle"><form:hidden
									path="announcementId" /><span
								class="tabtxt">URL Links</span></td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span
								class="tabtxt"><form:textarea path="urlLinks" style="width:250px;"/><font
									color="red"><form:errors path="urlLinks"></form:errors>
								</font>
							</span></td>
						</tr>
						<tr>
							<td width="45%" height="10" align="right" valign="middle"></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle"></td>
						</tr>
					<c:if test="${userReg.user.userType == 'admin'}">
						<tr>
							<td width="80%" height="20" align="right" valign="middle">
							<span class="tabtxt"><img src="images/Common/required.gif"> Show the notification to</span></td>
							<td width="10%" height="20" align="center" valign="middle">:</td>
							<td height="20" align="left" valign="middle"><span
								class="tabtxt">					
								<form:select path="createdFor.userTypeid">
									<option value="">All</option>
									<c:forEach var="cList" items="${createdFors}">
										<option value="${cList.userTypeid}"
											<c:if test="${announceObj.createdFor.userTypeid == cList.userTypeid}"> selected="Selected" </c:if>>${cList.userType}</option>
									</c:forEach>
								</form:select> 
								
								<font
									color="red"> <form:errors path="createdFor"></form:errors>
								</font>
							</span></td>
						</tr>
					</c:if>
						<tr>
							<td width="45%" height="10" align="right" valign="middle"><form:hidden
									path="operationMode" name="operationMode" id="operationMode"
									value="add" /></td>
							<td width="10%" height="10" align="center" valign="middle"></td>
							<td height="10" align="left" valign="middle"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr><td height="10"></td>	
		<c:choose>
		<c:when test="${userReg.user.userType == 'admin'}">
			<tr>
			<td width="100%" height="23" align="center">
				<table
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="8%" align="right" valign="middle">
								<input type="button" class="button_green" value="SubmitChanges" style="border:none" onclick="saveOrUpdateAnnouncement()">
							</td>
							<td width="10%" align="center" valign="middle"><div class="button_green" onclick="$('#announcementForm')[0].reset();">Cancel</div></td>
						</tr>
						<tr><td>&nbsp;</td></tr>
				</table>
				</td>
				</tr>
		</c:when> 
	</c:choose>
			</form:form>
		</table></div>
		</td>
	</tr>
	</table></td></tr></table>
</body>
</html>
