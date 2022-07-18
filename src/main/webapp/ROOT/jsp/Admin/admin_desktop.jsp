<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<head>
<title>Admin Desktop</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="resources/css/dashboard/style.css" rel="stylesheet" type="text/css" media="all"/>
<link href="resources/css/dashboard/nav.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" src="resources/javascript/dashboard/login.js"></script>
<script type="text/javascript" src="resources/javascript/dashboard/Chart.js"></script>
<script type="text/javascript" src="resources/javascript/dashboard/jquery.easing.js"></script>
<script type="text/javascript" src="resources/javascript/dashboard/jquery.ulslide.js"></script>
 <!----Start Calender -------->
  <link href="resources/css/dashboard/clndr.css" rel="stylesheet" type="text/css" media="all"/>
  <script type="text/javascript" src="resources/javascript/dashboard/underscore-min.js"></script>
  <script type="text/javascript" src="resources/javascript/dashboard/moment-2.2.1.js"></script>
  <script type="text/javascript" src="resources/javascript/dashboard/clndr.js"></script>
  <script type="text/javascript" src="resources/javascript/dashboard/site.js"></script>
  <script type="text/javascript" src="resources/javascript/dashboard/jquery.marquee.js"></script>
  <!----End Calender -------->
<script type="text/javascript" src="resources/javascript/common/img_viewer.js"></script>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<style>
.notify{
	width:auto;
	padding:none;
}
.tooltip-left:before,
.tooltip-right:before {
  top: 0px;
}
.tbl {
  border-collapse: collapse;
  width: 100%;
  margin-top:0.5em;
}
.tbl th, .tbl td {
  padding: 0.25rem;
  text-align: left;
  border: 1px solid #dee4e6;
}
.tbl tbody tr:nth-child(odd) {
   background: rgb(237, 241, 243);
}
.tbl tbody tr:nth-child(even) {
   background: rgb(127, 231, 243);
}
</style>
</head>
<body>
	<c:set var="userName" value="${userReg.title == null ? userReg.title : ''}${userReg.firstName} ${userReg.lastName }"></c:set>	
<input type="hidden" name="userType" id="userType" value="${userReg.user.userType}" />
	<div class="main">  
	    <div class="wrap">  
	    <%@include file="/jsp/CommonJsp/homepage_left.jsp"%>		 
           <div class="column_middle">
              <div class="column_middle_grid1">
		         <div class="profile_picture">
		        	<table width="100%">
		        	 <tr>
		        	   <td width="40%">
			        	  <form name="uploadProfilePicForm" id="uploadProfilePicForm" novalidate> 
							 <div class="show-image">
								<a href="#" class="tooltip" data-tooltip="Click here to view your Profile Picture"><img id="imgDiv" class="imgCls" style="display:none;width: 85px;height: 85px;border-radius: 50%;" onClick="openPreviewImage()"/></a>
								<div id="iconDiv" class="fa fa-user-circle-o" aria-hidden="true" style="display:block;font-size: 80px;text-shadow: 0 1px 4px rgb(140, 156, 158);color:#00cae2;margin-top:5px;margin-bottom:5px;"></div>
							     <a href="#" class="tooltip-left" data-tooltip="Upload"><button id="upload" class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick image-buttons" style="margin-left:10%;"><i class="ion-edit" style="font-size:16px;font-weight:bold;"></i></button></a>
								<input type="file" id="file" name="file" required="required" style="display:none;" onChange="showimagepreview(this)"/>
							    <a href="#" class="tooltip-right" data-tooltip="Remove"><button id="remove" class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick image-buttons" onClick="deleteProfileImage()" style="margin-left:70%;"><i class="fa fa-times" style="font-size:14px;font-weight:bold;"></i></button></a>
						    </div>
						  </form>
					   </td>
		        	   <td width="60%" style="font-family: 'Open Sans Condensed', sans-serif;"> 
		        	      <table width="100%">
		        	        <tr><td align="left" width="100%" height="20">
		        	        <span>Hello..!<br><span style="font-size: 13px;letter-spacing:.2px;color:#525000;margin:.2em;"> ${userName}</span></span>
		        	        </td></tr>
		        	        <tr><td align="left" width="100%">
		        	         <table width="100%">
			        	        <tr>
			        	        	<td align="left" width="16%">
			        	        	<div class="hghlghtr hghlghtr-link"></div>
					        	         <div class="txtStyle sonar-wrapper" style="${notificationLength > 0 ? 'margin-top:-2em;margin-left: -1.5em;display:block;': 'display:none;'}">
											<div class="sonar-emitter">
										    <div class="sonar-wave"></div>
										  </div>
										 </div>
									 </td>
								  <td align="left" width="80%" style="margin-top:1.8em;" height="30">
							  	 	 <a href="#" onclick="openNotification()" style="${notificationLength > 0 ? 'margin-top:2em;margin-left:-2em;display:block;text-decoration: underline;': 'display:none;'}">You have <font size="2" color="#008a9a">${notificationLength}</font> message${notificationLength > 1 ? 's':''}</a>
							  	   </td></tr>
						  	  </table>
		        	        </td></tr>
		        	      </table>
		        	   </td>
		        	</tr>
		        	</table>
		         </div>
		       </div>
		       <div class="middle_ribbon" style="margin-left: -3em;margin-top: .5em;"><span class="middle_text">Admin's Desktop</span></div>
         	      <div style="padding-top: 2em;"></div>
         		  <div class="tsc_ribbon_wrap dashboard-center-box tab_fold" style="height:230px;">
			   		 <div class="ribbon-wrap left-edge fork lred"><span>Notifications</span></div>
			   		 <button class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick" style="float:right;min-width: 80px;max-width: 120px;font-size:12px;" onclick="openAnnouncementDialog(0,0)">Add&nbsp;&nbsp;<i class="fa fa-bullhorn fa-rotate-180" style="font-size:14px;"></i></button>
			   		 <c:choose>
						<c:when test="${fn:length(announcements) gt 0}">
							<div class="scrollbox scrollbox_delayed flexcroll" tabindex="0">
  								<div class="scrollbox-content"> 
					              	 <table width="100%" class="tbl">
										<c:forEach items="${announcements}" var="announcement" varStatus="announceCount">
											<tr id="announcement${announcement.announcementId}" style="width: 100%;">
												 <td height="40" valign="middle" style="padding-bottom:0.5em;padding-left:1em;width:10%;text-align:left;padding-left:1em;color:#002c40;font-size:20px;text-decoration: none;font-weight: 400;"><i class="fa fa-bullhorn" aria-hidden="true"></i></td>
											     <td style="width:90%;text-align:left;padding-bottom:0.5em;" height="40" valign="middle">
												     <a href="#" onClick="openAnnouncementDialog(${announcement.announcementId},0)">
													     <span class="text" style="padding-left:5px;font-size:13.2px;color:#6b6800;"> ${announcement.annoncementName}&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-right" aria-hidden="true"></i>
													     <span class="text" style="padding-left:5px;font-weight:500;font-size: 12px;text-shadow: 0 1px 1px rgb(171, 171, 171);letter-spacing: .5px;" >${announcement.announceDescription}</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
													     <a class="tooltip-left" data-tooltip="Edit"><span style="cursor: pointer;font-size: 17px;color:#0e5b98;" class="fa fa-pencil" aria-hidden="true" onClick="openAnnouncementDialog(${announcement.announcementId},0)"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;
													     <a class="tooltip-right" data-tooltip="Delete"><i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 20px;color:#e4306e;" onclick="deleteAnnouncement(${announcement.announcementId})"></i></a>
												     </a>
											     </td>
											 </tr>
										  </c:forEach>
									  </table>
				               </div>
			               </div>
	                  </c:when>
	                  <c:otherwise>
	                   <div style="font-size:2em;color:#6b6800;padding-top:3em;"> <i class="ion-speakerphone" aria-hidden="true"></i>&nbsp;&nbsp;<span style="color:#000000;text-shadow: 0 1px 1px rgb(204, 204, 204);font-weight: bold;font-size:12px;font-family:cursive;">No Notifications found</span></div>
	                  </c:otherwise>
	               </c:choose>
				  </div>
				  <div style="padding-top: 2em;"></div>
					<div class="tsc_ribbon_wrap dashboard-center-box tab_fold" style="height: 230px;">
   						<div class="ribbon-wrap right-edge fork lblue">      
   							<span>Events</span>
   						</div>
   						<span><button class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick" style="float:left;min-width: 80px;max-width: 120px;font-size:12px;" onclick="openEventDialog(0)">Add&nbsp;&nbsp;<i class="fa fa-calendar-plus-o" style="font-size:14px;"></i></button></span>
  						<c:choose>
							<c:when test="${fn:length(events) gt 0}">
		  					 <div class="scrollbox scrollbox_delayed flexcroll" tabindex="0">
  								<div class="scrollbox-content"> 
			 						<table width="100%" class="tbl" style="margin-right:.5em;">
			   						  <c:forEach items="${events}" var="event" varStatus="eventCount">
			   						     <tr id="event${event.eventId}">
											<td height="10" width="15%" style="padding-left:2em;padding-top:1em;"><p class="calendar"><fmt:formatDate pattern="dd" dateStyle="long" value="${event.announcementDate}"/><em> <fmt:formatDate pattern="MMM" dateStyle="long" value="${event.announcementDate}"/></em></p></td>
											<td height="10" width="80%" style="padding: 0.5em;" align="left">
												<a href="#" style="font-family:Raleway,Open Sans,sans-serif;" onClick="openEventDialog(${event.eventId})"><span style="padding-left:5px;font-size:13.2px;color:#6b6800;">${event.eventName}&nbsp;&nbsp;<i class="fa fa-caret-right" aria-hidden="true"></i></span>&nbsp;&nbsp;&nbsp;
													<span class="text" style="padding-left:5px;font-weight:500;font-size: 12px;text-shadow: 0 1px 1px rgb(134, 134, 134);">${event.description}</span>&nbsp;&nbsp;&nbsp;
													<a class="tooltip-left" data-tooltip="Edit"><span style="cursor: pointer;color:#0e5b98;font-size:17px;" class="fa fa-pencil" aria-hidden="true" onClick="openEventDialog(${event.eventId})"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;
													<a class="tooltip-right" data-tooltip="Delete"><i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 20px;color:#e4306e;" onclick="deleteEvent(${event.eventId})"></i></a>
												</a>
											</td>
										</tr>
									 </c:forEach>
				         		   </table>
			         		     </div>
			         		   </div>
		         		     </c:when>
			                 <c:otherwise>
		                     	<div style="font-size:2em;color:#6b6800;padding-top:3em;"> <i class="ion-calendar" aria-hidden="true"></i>&nbsp;&nbsp;<span style="color:#000000;text-shadow: 0 1px 1px rgb(204, 204, 204);font-weight: bold;font-size:12px;font-family:cursive;">No Events found.</span></div>
		                    </c:otherwise>
			            </c:choose>
				</div>
    	   </div>
    	    <%@include file="/jsp/CommonJsp/homepage_right.jsp"%>
     	</div>
     </div>
</body>
</html>

 