<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Add/Edit Notifications</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/imageloadfunctions.js"></script>
<script type="text/javascript" src="resources/javascript/admin/announcements.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script>
$(function() {
    $( "#announceDate" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        showAnim : 'clip',
        minDate: 0
    });
});
</script>
</head>
<body>
<input type="hidden" name="userType" id="userType" value="${userReg.user.userType}" />
<input type="hidden" id="operation" value="${operation}">
<table width="100%">
	<tr><td colspan="" width="100%"> 
		 <table width="100%" class="title-pad" border="0">
			<tr>
				<td class="sub-title title-border" height="40" align="left" ><div id="title">Notifications</div></td>
			</tr>
		</table>
	</td></tr>
	<tr>
		<td style="float:right;padding-right: 3em;padding-top: 1em;" align="right" width="100%">
			<div class="button_green" id="btAdd" name="btAdd" height="52" width="50" onclick="openAnnouncementDialog(0, 0)">${announcement.announcementId > 0 ? 'Edit' : 'Add'} Notificaion</div>
		</td>
	</tr>
	<tr><td align="center">
	 <c:choose>
		<c:when test="${fn:length(announcementsList) gt 0}">
	<table width="60%" class="des">
	<tr class="Divheads">
		<td width="10%" align="center" height="50">S.No</td>
		<td width="30%" align="center">Notification Title</td>
		<td width="15%" align="center">Announcement Date</td>
		<td width="15%" align="center">Created for</td>
		<td width="10%" align="center">Edit</td>
		<td width="10%" align="center">Delete</td>
	</tr>	
	<tr><td align="center" height="10"></td></tr>
	<c:forEach var="announcement" items="${announcementsList}" varStatus="status">
		<tr class="txtStyle" id="announcement${announcement.announcementId}">
			<td width="10%" height="30" align="center">${status.count}&nbsp;.&nbsp;</td>
			<td width="30%" height="30" align="center">${announcement.annoncementName}</td>
			<td width="15%" height="30" align="center">
				<fmt:formatDate pattern="MM/dd/yyyy" var="annDate"
								value="${announcement.announceDate}" />
			${annDate}</td>
			<td width="15%" height="30" align="center">${announcement.createdFor.userType}</td>
			<td width="10%" align="center"><span style="cursor: pointer;font-size: 17px;color:#0e5b98;" class="fa fa-pencil" aria-hidden="true" onclick="openAnnouncementDialog(${announcement.announcementId},0)"></span></td>
			<td width="10%" align="center"><span class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 20px;color:#CD0000;" onclick="deleteAnnouncement(${announcement.announcementId})"></span></td>
		</tr>
	</c:forEach>
	<tr><td align="center" height="10"></td></tr>
	</table>
	</c:when>
	<c:otherwise>
		 <span  class="status-message">No Notifications found</span>
	</c:otherwise>
	</c:choose>
	</td></tr>
</table>
<!-- <div id="announcementDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='iframe1' width="98%" height="95%" style="border-radius:1em;"></iframe></div> -->
</body>
</html>
