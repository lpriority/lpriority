<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="resources/javascript/admin/common_dropdown_pull.js"></script>
<script type="text/javascript" src="resources/javascript/admin/announcements.js"></script>
<script type="text/javascript" src="resources/javascript/admin/events.js"></script>
<script>
function studentFormValidation(){
	var gradeId = document.getElementById('sGradeId').value;
	var studentRegId = document.getElementById('studentRegId').value;
	if(gradeId != 'select' && studentRegId != 'select'){
		removeStoredItem();
		return true;
	}else{
		return false;
	}
}

function teacherFormValidation(){
	var teacheremail = document.getElementById('teacheremail').value;
	if(teacheremail != 'select'){
		removeStoredItem();
		return true;
	}else{
		return false;
	}
}

function clear(){
	var gradeId = $('#gradeId').val();
	if(gradeId == 'select'){	
		$("#studentRegId").empty();
		$("#studentRegId").append($("<option></option>").val('select').html('Select Student'));
	}
}

function getStudentsBySGradeId(callback){
	  var obj =	document.getElementById("sGradeId");
	  if(obj){
		  var gradeId = obj.value;
		  if(gradeId  != 'select'){
				$("#loading-div-background").show();
				$.ajax({
					type : "GET",
					url : "getStudentsByGrade.htm",
					data : "gradeId=" + gradeId,
					success : function(response) {
						var students = response.students;
						$("#studentRegId").empty();
						$("#studentRegId").append($("<option></option>").
								val('select').html(
								'Select Student'));
						$.each(students, function(index, value) {
							$("#studentRegId").append($("<option></option>").
								val(value.userRegistration.emailId).html(
								value.userRegistration.emailId));
						});
						$("#loading-div-background").hide();
						if(callback)
						 callback();
					}
				}); 
		  }

		}
	}
</script>
<style>
.tbl {
  border-collapse: collapse;
  width: 100%;
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
        <div class="column_right">
            <c:if test="${userRegistration.user.userType == 'admin'}">
            	<div class="dashboard_form" style="padding:15px;">
            	<form name="visitstudents"  action="/authSwitchUser.htm" method="post">
            		<div class="sign_in">
            		 <table width="100%">
            		 	<tr><td width="100%" height="60" align="center">Sign in to Student Account</td></tr>
				        <tr><td width="100%" height="30" align="center" >
					 	       <select name="sGradeId" id="sGradeId" onChange="clear();getStudentsBySGradeId();" required="required" class="desktop_select" style="width:70%;border:1px solid #97ced6;color:white;">
									<option value="select" selected>Select Grade</option>
								<c:forEach items="${schoolGrades}" var="schoolGrade">
									<option value="${schoolGrade.gradeId}">${schoolGrade.masterGrades.gradeName}</option>
								</c:forEach>
							   </select>
							 </td></tr>
							  <tr><td width="100%" height="10"></td></tr>
							 <tr><td width="100%" height="32" align="center">
					 	      <select name="j_username" required="required" id="studentRegId" class="desktop_select" style="width:88%;border:1px solid #97ced6;color:white;">
									<option value="select" selected>Select Student</option>
								</select>
							</td></tr><tr><td width="100%" align="center" height="60" class="center_of_div">
								<button class="stylish-button button--wayra button--border-thicker button--text-upper button--size-s" onclick="return studentFormValidation()"><span>Sign In</span></button>
							</td></tr>
							</table>
          		       </div>
          		 </form>
			   </div><div style="margin-bottom:2em;"></div>
			   <div class="dashboard_form" style="background:linear-gradient(235deg,rgb(39, 221, 243) 25%, #e3e8ea 25%);">
				   <form name="visitteachers"  action="/authSwitchUser.htm" method="post">
				    <table width="100%" align="center">
				  	  <tr><td width="100%" height="60" align="center">Sign in to Teacher</td></tr>
				  	  <tr><td width="100%" height="32" align="center">
				    	   <select name="j_username" id="teacheremail" required class="desktop_select" style="width:80%;border:1px solid #97ced6;color:white;">
								<option value="select">Select Email id</option>
								<c:forEach items="${teacherEmails}" var="teacherEmail">
									<option value="${teacherEmail.userRegistration.emailId}">${teacherEmail.userRegistration.emailId}</option>
								</c:forEach>
							</select>
						</td></tr><tr><td width="100%" align="center" height="60" class="center_of_div">
							<button class="stylish-button button--wayra button--border-thicker button--text-upper button--size-s" onclick="return teacherFormValidation()"><span>Sign In</span></button>
						</td></tr>
						</table>
				   </form>
			     </div><div style="margin-bottom:2em;"></div>
			     </c:if>
			      <div class="dashboard-box" style="background:#e3e8ea;">
			       <div class="ribbon-left"><span>Reading Suite</span></div>
				   <form name="visitteachers"  action="<c:url value='j_spring_security_switch_user'/>" method="post">
				    <table width="100%" align="center">
						   <c:set var="pmLink" value="viewProgressMonitor.htm" ></c:set>
							<c:set var="gfLink" value="gotoGradeRti.htm" ></c:set>
							<c:set var="gfTitle" value="GF - Graded Fluency" ></c:set>
								<c:choose>
					                 <c:when test="${userRegistration.user.userType == 'student above 13' || userRegistration.user.userType == 'student below 13'}">
						             	<c:set var="rdLink" value="goToStudentRSDD.htm" ></c:set>
						             	<c:set var="gfLink" value="studentBenchmarkResults.htm" ></c:set>
					                 </c:when>	
					                 <c:when test="${userRegistration.user.userType == 'admin'}">
						             	<c:set var="rdLink" value="goToAdminRSDD.htm" ></c:set>		
						             	<c:set var="gfTitle" value="GF - Grade Fluency" ></c:set>	             	
					                 </c:when>
					                 <c:when test="${userRegistration.user.userType == 'teacher'}">
						            	 <c:set var="rdLink" value="goToTeacherRSDD.htm" ></c:set>
						            	 <c:set var="gfTitle" value="GF - Grade Fluency" ></c:set>
					                 </c:when>
					                 <c:when test="${userRegistration.user.userType == 'parent'}">
						             	<c:set var="rdLink" value="goToParentRSDD.htm" ></c:set>
						             	<c:set var="gfTitle" value="GF - Grade Fluency" ></c:set>
						             	<c:set var="gfLink" value="childBenchmarkResults.htm" ></c:set>						             	
					                 </c:when>
					                 <c:otherwise>
						             	<c:set var="rdLink" value="#" ></c:set>
					                 </c:otherwise>
				            	</c:choose>
				            		<tr><td align="center" height="30"><a style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 13px;" href="${rdLink}" class="menu">RD - Reading Dashboard</a></td></tr>
				            		<tr><td align="center" height="30"><a style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 13px;" href="${pmLink}" class="menu">PM - Progress Monitoring</a></td></tr>
					            	<tr><td align="center" height="30"><a style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 13px;" href="${gfLink}" class="menu">${gfTitle}</a><br></td></tr>
				            	    <c:if test="${userRegistration.user.userType == 'admin'}">
				            			<tr><td align="center" height="30"><a style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 13px;" href="getFluencyResults.htm" class="menu">Download Results</a><br></td></tr>
 				            			<tr><td align="center" height="30"><a style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 13px;" href="goalSettingDownload.htm" class="menu">Goal Setting Download</a><br></td></tr>
				            		</c:if>
				            		 <c:if test="${userRegistration.user.userType == 'teacher'}">
				            		 	<tr><td align="center" height="30"><a style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 13px;" href="rti.htm" class="menu">TCP - Teacher Created Passage</a></td></tr>
				            		 	<tr><td align="center" height="30"><a style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 13px;" href="assignRti.htm" class="menu">AF - Assign Fluency</a></td></tr>
				            			<tr><td align="center" height="30"><a style="color: #005b84;text-shadow: 0 1px 1px rgb(191, 191, 191);font-size: 13px;" href="benchmarkResults.htm" class="menu">CF - Class Fluency Scores</a></td></tr>
				            		</c:if>
									<tr>
										<td height="7"></td>
									</tr>
								</table>
						</form>
			     </div>
			     <c:if test="${userRegistration.user.userType ne 'admin'}">
			     <div style="margin-bottom:2em;"></div>
         		   <div class="dashboard-box" style="background:#dee4e6;width:100%;height:220px;">
			        <div class="dashboard-label"><div class="back-side-right"></div><span class="front-side">Notifications</span></div>
		         <c:choose>
					<c:when test="${fn:length(notificationStatusLt) gt 0}">
			   		 <div style="width: 100%;overflow: hidden;height: 180px;font-size:13px;margin-top: 2em;float:left;">
		                  <div class="marquee-vert" style="height:160px;float:left;" data-gap="50" data-speed="3000" data-direction="down" pause-on-hover="false" data-duplicated="true">
							   <table width="100%" class="tbl">
									<c:forEach items="${notificationStatusLt}" var="notificationStatus" varStatus="announceCount">
										<tr id="announcement${notificationStatus.announcements.announcementId}" style="width: 100%;">
											 <td height="40" valign="middle" style="padding-bottom:1em;padding:.5em;width:10%;text-align:left;color:#002c40;font-size:20px;text-decoration: none;font-weight: 400;"><i class="fa fa-bullhorn" aria-hidden="true"></i></td>
										     <td style="width:90%;text-align:left;padding-bottom:1em;" height="40" valign="middle">
											     <a href="#" onClick="openAnnouncementDialog(${notificationStatus.announcements.announcementId},${notificationStatus.notificationStatusId})">
												     <span class="text" style="padding-left:5px;font-size:13.2px;color:#6b6800;"> ${notificationStatus.announcements.annoncementName}&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-right" aria-hidden="true"></i>
												     <span class="text" style="padding-left:5px;font-weight:500;font-size: 12.2px;text-shadow: 0 1px 1px rgb(138, 138, 138);" >${notificationStatus.announcements.announceDescription}</span></span>&nbsp;&nbsp;&nbsp;&nbsp;
											     </a>
										     </td>
										 </tr>
									  </c:forEach>
								 </table>
			              </div>
		               </div>
                 </c:when>
                  <c:otherwise>
                   	<div style="font-size:2em;color:#6b6800;padding-top:3em;text-align: center;"> <i class="ion-speakerphone" aria-hidden="true"></i>&nbsp;&nbsp;<span style="color:#000000;text-shadow: 0 1px 1px rgb(204, 204, 204);font-weight: bold;font-size:12px;font-family:cursive;">No Notifications found</span></div>
                  </c:otherwise>
                </c:choose>
	                 <div class="archieve-div"><a href="#" class="tooltip-left" data-tooltip="Archived notifications" onclick="getAllArchivedNotifications()"><i class="fa fa-archive" style="color:#05b1c5;text-shadow: 0 1px 1px rgba(189, 189, 189, 0.78);"></i></a></div>
				  </div>
				  <div style="margin-bottom:2em;"></div>
  				  <div class="dashboard-box" style="background:#dee4e6;width:100%;height:220px;">
  				    <div class="dashboard-label" style="left: 10px;"><div class="back-side-left"></div><span class="front-side" style="padding: 6px 30px;">Events</span></div>
				  <c:choose>
					<c:when test="${fn:length(events) gt 0}">
  						<div style="width: 99.5%;overflow: hidden;height: 180px;font-size:13px;margin-top: 2em;float:left;">
  						  <div class="marquee-vert" style="height:160px;" data-gap="50" data-speed="3000" data-direction="down" pause-on-hover="false" data-duplicated="true">
	 						<table width="100%" class="tbl" style="margin-right:.5em;">
	   						  <c:forEach items="${events}" var="event" varStatus="eventCount">
	   						     <tr>
									<td height="10" width="15%" style="padding-left:2em;padding-top:1em;"><p class="calendar"><fmt:formatDate pattern="dd" dateStyle="long" value="${event.announcementDate}"/><em> <fmt:formatDate pattern="MMM" dateStyle="long" value="${event.announcementDate}"/></em></p></td>
									<td height="10" width="80%" style="padding: 0.5em;" align="left">
										<a href="#" style="font-family:Raleway,Open Sans,sans-serif;" onClick="openEventDialog(${event.eventId})"><span style="padding-left:5px;font-size:13.2px;color:#6b6800;">${event.eventName}&nbsp;&nbsp;<i class="fa fa-caret-right" aria-hidden="true"></i></span>&nbsp;&nbsp;&nbsp;
											<span class="text" style="padding-left:2px;font-weight:500;font-size:13px;text-shadow: 0 1px 1px rgb(165, 165, 165);">${event.description}</span>
									   </a>
									</td>
								</tr>
							 </c:forEach>
		         		   </table>
	         		     </div>
	         		   </div>
	         	   </c:when>
         		   <c:otherwise>
                   	<div style="font-size:2em;color:#6b6800;padding-top:3em;text-align: center;"> <i class="ion-calendar" aria-hidden="true"></i>&nbsp;&nbsp;<span style="color:#000000;text-shadow: 0 1px 1px rgb(204, 204, 204);font-weight: bold;font-size:12px;font-family:cursive;">No Events found.</span></div>
                  </c:otherwise>
                 </c:choose>
  					</div>
			     </c:if>
			     <div style="margin-bottom:2em;"></div>
	             <div class="video_palyer">
	                 <table style="font-size: 14px;" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					<!-- <tr>
					<td align="center">
						<div id="jp_container_1" class="jp-video jp-video-270p" role="application" aria-label="media player" style="width:100%;">
							<div class="jp-type-single">
								<div style="width:100%" id="jquery_jplayer_1" class="jp-jplayer" ></div>
								<div align="center" id="loadId" style="background:linear-gradient(to bottom,rgb(238, 238, 238) 1%, rgb(255, 255, 255) 48%, rgb(188, 190, 191) 97%, rgb(146, 146, 146) 100%);height:2em;" valign="middle">
						    		<a href="javascript:loadDemo()" style='text-decoration: none;color:rgba(48, 45, 45, 0.77);font-weight:bold;font-size:13px;font-family:"Open Sans", Arial, sans-serif; text-shadow:0 1px 1px rgb(183, 183, 183);'>
						    			Click here to load the demo video
						    		</a>
						    	</div>
								<div class="jp-gui" style="pointer-events:none;">
									<div class="jp-interface">
										<div class="jp-progress">
											<div class="jp-seek-bar">
												<div class="jp-play-bar"></div>
											</div>
										</div>
										<div class="jp-current-time" role="timer" aria-label="time">&nbsp;</div>
										<div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>
										<div class="jp-controls-holder" style="width: 300px; margin: 0 0;">
											<div class="jp-controls">
												<button class="jp-play" role="button" tabindex="0">play</button>
												<button class="jp-stop" role="button" tabindex="0">stop</button>
											</div>
											<div class="jp-volume-controls" style="left:5px;">
												<button class="jp-mute" role="button" tabindex="0">mute</button>
												<button class="jp-volume-max" role="button" tabindex="0">max volume</button>
												<div class="jp-volume-bar">
													<div class="jp-volume-bar-value"></div>
												</div>
											</div>
											<div class="jp-toggles" style="right:80px;">
												<button class="jp-repeat" role="button" tabindex="0">repeat</button>
												<button class="jp-full-screen" role="button" tabindex="0">full screen</button>
											</div>
										</div>
										<div class="jp-details">
											<div class="jp-title" aria-label="title">&nbsp;</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr> -->
			</table>
           </div>   
         </div>
    	<div class="clear"></div>
    	<div id="loading-div-background2" class="loading-div-background"
	style="display: none;">
	<div class="loader"></div>
	<div id="loading-div" class="ui-corner-all loading-div"
		style="color: #103c51; left: 50%; padding-left: 12px; font-size: 17px; margin-top: -8.5em;">
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>Loading....
	</div>
</div>